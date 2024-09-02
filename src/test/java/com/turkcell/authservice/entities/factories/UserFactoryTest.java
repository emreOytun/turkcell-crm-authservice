package com.turkcell.authservice.entities.factories;

import com.turkcell.authservice.entities.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserFactoryTest {

    @Test
    void testCreateUserWithoutParameters() {
        User user = UserFactory.create();

        assertNotNull(user, "User should not be null");
        assertNull(user.getEmail(), "User email should be null");
        assertNull(user.getPassword(), "User password should be null");
    }

    @Test
    void testCreateUserWithEmailAndPassword() {
        String email = "test@example.com";
        String password = "password123";

        User user = UserFactory.create(email, password);

        assertNotNull(user, "User should not be null");
        assertEquals(email, user.getEmail(), "User email should match the provided email");
        assertEquals(password, user.getPassword(), "User password should match the provided password");
    }
}
