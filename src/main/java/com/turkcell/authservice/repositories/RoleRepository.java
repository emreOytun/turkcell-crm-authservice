package com.turkcell.authservice.repositories;

import com.turkcell.authservice.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
}
