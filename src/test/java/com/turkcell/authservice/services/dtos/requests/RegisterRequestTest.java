package com.turkcell.authservice.services.dtos.requests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RegisterRequestTest {

    @Test
    void testDefaultRegisterRequest() {
        RegisterRequest registerRequest = new RegisterRequest();

        assertEquals(null, registerRequest.getEmail());
        assertEquals(null, registerRequest.getPassword());
    }

    @Test
    void testRegisterRequestWithEmailAndPassword() {
        RegisterRequest registerRequest = new RegisterRequest("newEmail@example.com", "newPassword");

        assertEquals("newEmail@example.com", registerRequest.getEmail());
        assertEquals("newPassword", registerRequest.getPassword());
    }

    @Test
    void testRegisterRequestEmailCannotBeBlank() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPassword("password");

        assertThrows(IllegalArgumentException.class, () -> {
            if (registerRequest.getEmail() == null || registerRequest.getEmail().isBlank()) {
                throw new IllegalArgumentException("Email cannot be blank");
            }
        });
    }

    @Test
    void testRegisterRequestPasswordCannotBeBlank() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("user@example.com");

        assertThrows(IllegalArgumentException.class, () -> {
            if (registerRequest.getPassword() == null || registerRequest.getPassword().isBlank()) {
                throw new IllegalArgumentException("Password cannot be blank");
            }
        });
    }
}
