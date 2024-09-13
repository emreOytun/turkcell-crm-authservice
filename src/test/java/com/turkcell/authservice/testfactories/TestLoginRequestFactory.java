package com.turkcell.authservice.testfactories;

import com.turkcell.authservice.services.dtos.requests.LoginRequest;

public class TestLoginRequestFactory {

    public static final String defaultEmail = "user@example.com";
    public static final String defaultPassword = "password";

    public static LoginRequest defaultLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(defaultEmail);
        loginRequest.setPassword(defaultPassword);
        return loginRequest;
    }

    public static LoginRequest withEmailAndPassword(String email, String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);
        return loginRequest;
    }
}

