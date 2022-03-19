package com.growCode.growCode.responses;

public class ServerErrorResponse implements Response{
    public int code =  500;
    public boolean status = false;
    public String message = "Internal Server Error";
    public Exception error;
    public ServerErrorResponse() {
    }
    public ServerErrorResponse(Exception error) {
        this.error = error;
    }
    public ServerErrorResponse(String message, Exception error) {
        this.message = message;
        this.error = error;
    }
    
    
}
