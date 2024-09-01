package com.turkcell.authservice.entities.factories;

import com.turkcell.authservice.entities.User;

public class UserFactory {
    public static User create() {
        return new User();
    }

    public static User create(String email, String password) {
        User user = create();
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}
