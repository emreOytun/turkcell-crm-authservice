package com.turkcell.authservice.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RoleTest {

    @Test
    public void testGetAuthority() {
        Role role = new Role();
        role.setName("Admin");
        assertEquals("admin", role.getAuthority());
    }

    @Test
    public void testEqualsAndHashCode() {
        Role role1 = new Role(1, "Admin", null);
        Role role2 = new Role(2, "Admin", null);
        Role role3 = new Role(1, "User", null);

        assertEquals(role1, role2); // Same name, should be equal
        assertNotEquals(role1, role3); // Different name, should not be equal
        assertEquals(role1.hashCode(), role2.hashCode()); // Same name, should have same hash code
    }

    @Test
    public void testNoArgsConstructor() {
        Role role = new Role();
        assertNotNull(role);
    }

    @Test
    public void testAllArgsConstructor() {
        Role role = new Role(1, "Admin", null);
        assertEquals(1, role.getId());
        assertEquals("Admin", role.getName());
    }
}

