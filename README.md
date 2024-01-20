# MineskinClient
Client for [api.mineskin.org](https://mineskin.org/)

The original project: [MineskinClient by Inventivetalent](https://github.com/InventivetalentDev/MineskinClient)

Todo:
- [ ] Add API Key support
- [ ] Add File Upload support
- [ ] Add Queue support for Rate Limit

Usage:
```java
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
```

## Maven
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io/</url>
    </repository>
</repositories>
<dependencies>
    <dependency>
	    <groupId>com.github.FrederikHeinrich</groupId>
	    <artifactId>MineskinClient</artifactId>
	    <version>1.0.0-SNAPSHOT</version>
	</dependency>
</dependencies>
```

## Gradle
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
dependencies {
    implementation 'com.github.FrederikHeinrich:MineskinClient:1.0.0-SNAPSHOT'
}
```