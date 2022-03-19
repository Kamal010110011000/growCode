package com.growCode.growCode.responses;

public class CreatedResponse<T> implements Response {
    public int status = 201;
    public String message = "Data is saved Sucessfully";
    public T data;
    public CreatedResponse() {
    }
    public CreatedResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
    public CreatedResponse(T data) {
        this.data = data;
    }
    

    
}
