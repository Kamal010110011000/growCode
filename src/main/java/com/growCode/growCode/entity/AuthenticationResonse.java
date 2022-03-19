package com.growCode.growCode.entity;

public class AuthenticationResonse {

    private final String token;

    public AuthenticationResonse(String token) {
        this.token = token;
    }

    public String getJwt() {
        return token;
    }


}
