package io.freddi.mineskin.exceptions;

public class ServerErrorException extends Exception{
    public String errorCode;
    public String error;

    public ServerErrorException(String errorCode, String error) {
        this.errorCode = errorCode;
        this.error = error;
    }
}
