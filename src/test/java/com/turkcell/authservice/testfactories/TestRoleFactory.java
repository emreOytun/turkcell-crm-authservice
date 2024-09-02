package com.turkcell.authservice.testfactories;

import com.turkcell.authservice.entities.Role;

public class TestRoleFactory {
    public static final Integer defaultRoleId = 2;
    public static final String roleNotFoundMsg = "Role not found";
    public static Role defaultRole() {
        Role role = new Role();
        role.setId(2);
        return role;
    }
}
