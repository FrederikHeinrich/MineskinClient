package io.freddi.mineskin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.freddi.mineskin.data.*;
import io.freddi.mineskin.exceptions.RateLimitException;
import io.freddi.mineskin.exceptions.ServerErrorException;

import javax.security.auth.callback.Callback;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class MineskinClient {

    String BASE_URL = "https://api.mineskin.org/";
    HttpClient client = HttpClient.newHttpClient();
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {
        var client = new MineskinClient();
        client.get(UUID.fromString("e792ea42-3a97-46f5-9520-98946a51fdea")).thenAccept(skinInfo -> {
            System.out.println("get by uuid: " + gson.toJson(skinInfo));
        });

        client.validate("test").thenAccept(validate -> {
            System.out.println("validate by name: " + gson.toJson(validate));
        });
        client.validate(UUID.fromString("d8d5a923-7b20-43d8-883b-1150148d6955")).thenAccept(validate -> {
            System.out.println("validate by uuid: " + gson.toJson(validate));
        });

        try {
            client.generate(Variant.AUTO, "test", Visibility.PUBLIC, "https://media.discordapp.net/attachments/1042824359766671410/1197211759497445456/decbf7e3083b40cf885cf22c43c532e3.png").thenAccept(skinInfo -> {
                System.out.println("generate by url: " + gson.toJson(skinInfo));
            });
        } catch (RateLimitException e) {
            e.printStackTrace();
        } catch (ServerErrorException e) {
            e.printStackTrace();
        }

        client.getDelay().thenAccept(delay -> {
            System.out.println("delay: " + gson.toJson(delay));
        });

        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public MineskinClient(){

    }

    public CompletableFuture<SkinInfo> generate(Variant variant, String name, Visibility visibility, String url) throws RateLimitException, ServerErrorException {
        JsonObject body = new JsonObject();
        body.addProperty("variant", variant.getName());
        body.addProperty("name", name);
        body.addProperty("visibility", visibility.getCode());
        body.addProperty("url", url);

        var request = client
                .sendAsync(
                        HttpRequest.newBuilder()
                                .uri(
                                        URI.create(BASE_URL + "generate/url")
                                )
                                .header("Content-Type", "application/json")
                                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(body), StandardCharsets.UTF_8))
                                .build(),
                        HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).join();
        System.out.println(request);
        if(request.contains("error")){
            if(request.contains("nextRequest")){
                throw gson.fromJson( request, RateLimitException.class);
            }
            if (request.contains("errorCode")){
                throw gson.fromJson( request, ServerErrorException.class);
            }
        }
        return CompletableFuture
                .supplyAsync(() -> gson.fromJson(request, SkinInfo.class));
    }

    public CompletableFuture<Delay> getDelay(){
        return CompletableFuture.supplyAsync(() -> gson.fromJson(client.sendAsync(HttpRequest.newBuilder()
                        .uri(URI.create(BASE_URL + "get/delay")).GET().build(),
                HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).join(), Delay.class));
    }
    public CompletableFuture<SkinInfo> get(UUID uuid){
        return CompletableFuture.supplyAsync(() -> gson.fromJson(client.sendAsync(HttpRequest.newBuilder()
                        .uri(URI.create(BASE_URL + "get/uuid/"+ uuid.toString())).GET().build(),
                HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).join(), SkinInfo.class));
    }

    public CompletableFuture<Validate> validate(String name){
        return CompletableFuture.supplyAsync(() -> gson.fromJson(client.sendAsync(HttpRequest.newBuilder()
                        .uri(URI.create(BASE_URL + "validate/name/"+ name)).GET().build(),
                HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).join(), Validate.class));
    }

    public CompletableFuture<Validate> validate(UUID uuid){
        return CompletableFuture.supplyAsync(() -> gson.fromJson(client.sendAsync(HttpRequest.newBuilder()
                        .uri(URI.create(BASE_URL + "validate/uuid/"+ uuid.toString())).GET().build(),
                HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).join(), Validate.class));
    }

}
