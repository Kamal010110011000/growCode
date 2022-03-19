package com.growCode.growCode.exceptions;

public class NotFoundException extends Exception {

    static String message = "Not found";
    public NotFoundException(String message) {
        super(message);
    }
    public NotFoundException(){
        super(message);
    }
    
}
