package com.growCode.growCode.responses;

public class OkResponse<T> implements Response{
    public int code = 200;   
    public boolean status = true;
    public String message = "Data fetched Sucessfully";
    public T data = null;
    public OkResponse() {
    }
    public OkResponse(boolean status, int code, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.code = code;
    }
    
    public OkResponse(T data) {
        this.data = data;
    }
    
}
