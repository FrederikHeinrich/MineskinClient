package io.freddi.mineskin.exceptions;

public class RateLimitException extends Exception{

    public String error;
    public Integer nextRequest;
    public Integer nextRequestRelative;

    public RateLimitException(String error, Integer nextRequest, Integer nextRequestRelative) {
        this.error = error;
        this.nextRequest = nextRequest;
        this.nextRequestRelative = nextRequestRelative;
    }


}
