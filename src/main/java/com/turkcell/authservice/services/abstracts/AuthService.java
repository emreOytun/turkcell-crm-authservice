package com.turkcell.authservice.services.abstracts;

import com.turkcell.authservice.services.dtos.requests.LoginRequest;
import com.turkcell.pair3.core.events.RegisterEvent;

public interface AuthService {
    Integer register(RegisterEvent request);
    String login(LoginRequest request);
    void giveRole(Integer id, Integer roleId);
    void updateEmail(Integer id, String email);
}
