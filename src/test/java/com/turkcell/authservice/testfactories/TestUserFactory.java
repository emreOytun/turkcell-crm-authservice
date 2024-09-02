package com.turkcell.authservice.testfactories;

import com.turkcell.authservice.entities.User;

import java.util.HashSet;

public class TestUserFactory {
    public static final String defaultUserEmail = "user@example.com";
    public static final Integer defaultUserId = 1;
    public static final String newEmailToUpdate = "newEmail@example.com";
    public static final String userNotFoundMsg = "User not found";
    public static final String wrongCredentialsMsg = "Wrong username or password";

    public static User defaultUser() {
        User user = new User();
        user.setId(1);
        user.setEmail(defaultUserEmail);
        user.setAuthorities(new HashSet<>());
        return user;
    }
}
