package com.turkcell.authservice.services.abstracts;

import com.turkcell.pair3.core.events.RegisterEvent;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    Integer add(RegisterEvent request);

    void giveRole(Integer id, Integer roleId);

    void updateEmail(Integer id, String email);
}
