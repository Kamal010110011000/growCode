package com.growCode.growCode.responses;

public class NotFoundResponse implements Response{
    public int code = 404;
    public boolean status = false;
    public String message = "Not Found";
    public Exception error;
    public NotFoundResponse() {
    }
    public NotFoundResponse(Exception error) {
        this.error = error;
    }
    public NotFoundResponse(String message, Exception error) {
        this.message = message;
        this.error = error;
    }
}
