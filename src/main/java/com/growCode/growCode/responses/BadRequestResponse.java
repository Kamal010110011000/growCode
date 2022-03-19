package com.growCode.growCode.responses;

public class BadRequestResponse implements Response{
    public int code = 400;
    public boolean status = false;
    public String message = "Request forbidden";
    public Exception error;
    public BadRequestResponse() {
    }
    public BadRequestResponse(Exception error) {
        this.error = error;
    }
    public BadRequestResponse(String message, Exception error) {
        this.message = message;
        this.error = error;
    }
    
    
    
}
