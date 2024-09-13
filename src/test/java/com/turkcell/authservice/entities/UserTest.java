package com.turkcell.authservice.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

public class UserTest {

    @Test
    public void testGetUsername() {
        User user = new User();
        user.setEmail("test@example.com");
        assertEquals("test@example.com", user.getUsername());
    }

    @Test
    public void testIsAccountNonExpired() {
        User user = new User();
        assertTrue(user.isAccountNonExpired());
    }

    @Test
    public void testIsAccountNonLocked() {
        User user = new User();
        assertTrue(user.isAccountNonLocked());
    }

    @Test
    public void testIsCredentialsNonExpired() {
        User user = new User();
        assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    public void testIsEnabled() {
        User user = new User();
        assertTrue(user.isEnabled());
    }

    @Test
    public void testNoArgsConstructor() {
        User user = new User();
        assertNotNull(user);
    }

    @Test
    public void testAllArgsConstructor() {
        User user = new User(1, "password", "test@example.com", "http://example.com", new HashSet<>());
        assertEquals(1, user.getId());
        assertEquals("password", user.getPassword());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("http://example.com", user.getProfilePictureUrl());
    }

    @Test
    public void testAuthorities() {
        Role role = new Role();
        role.setName("USER");

        User user = new User();
        user.setAuthorities(new HashSet<>());
        user.getAuthorities().add(role);

        assertEquals(1, user.getAuthorities().size());
        assertTrue(user.getAuthorities().contains(role));
    }
}
