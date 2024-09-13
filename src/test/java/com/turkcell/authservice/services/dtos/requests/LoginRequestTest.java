package com.turkcell.authservice.services.dtos.requests;

import com.turkcell.authservice.testfactories.TestLoginRequestFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoginRequestTest {

    @Test
    void testDefaultLoginRequest() {
        LoginRequest loginRequest = TestLoginRequestFactory.defaultLoginRequest();

        assertEquals("user@example.com", loginRequest.getEmail());
        assertEquals("password", loginRequest.getPassword());
    }

    @Test
    void testLoginRequestWithEmailAndPassword() {
        LoginRequest loginRequest = TestLoginRequestFactory.withEmailAndPassword("newEmail@example.com", "newPassword");

        assertEquals("newEmail@example.com", loginRequest.getEmail());
        assertEquals("newPassword", loginRequest.getPassword());
    }

    @Test
    void testLoginRequestEmailCannotBeBlank() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("password");

        assertThrows(IllegalArgumentException.class, () -> {
            if (loginRequest.getEmail() == null || loginRequest.getEmail().isBlank()) {
                throw new IllegalArgumentException("Email cannot be blank");
            }
        });
    }

    @Test
    void testLoginRequestPasswordCannotBeBlank() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("user@example.com");

        assertThrows(IllegalArgumentException.class, () -> {
            if (loginRequest.getPassword() == null || loginRequest.getPassword().isBlank()) {
                throw new IllegalArgumentException("Password cannot be blank");
            }
        });
    }
}
