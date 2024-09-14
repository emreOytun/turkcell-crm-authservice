package com.turkcell.authservice.services.concretes;

import com.turkcell.authservice.services.abstracts.AuthService;
import com.turkcell.authservice.services.abstracts.UserService;
import com.turkcell.authservice.services.dtos.requests.LoginRequest;
import com.turkcell.pair3.core.events.RegisterEvent;
import com.turkcell.pair3.core.exception.types.BusinessExceptionFactory;
import com.turkcell.pair3.core.jwt.JwtService;
import com.turkcell.pair3.core.messages.Messages;
import com.turkcell.pair3.core.security.factories.AuthenticationTokenFactory;
import com.turkcell.pair3.core.services.abstracts.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final MessageService messageService;

    @Override
    public Integer register(RegisterEvent request) {
        return userService.add(request);
    }

    @Override
    public String login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                        AuthenticationTokenFactory.createWithUsernamePassword(request.getEmail(), request.getPassword())
            );

            UserDetails user = userService.loadUserByUsername(request.getEmail());
            return jwtService.generateToken(
                    user.getUsername(),
                    user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()
            );
        } catch (Exception ex) {
            throw BusinessExceptionFactory.createWithMessage(messageService.getMessage(Messages.BusinessErrors.WRONG_USERNAME_OR_PASSWORD));
        }
    }

    @Override
    public void giveRole(Integer id, Integer roleId) {
        userService.giveRole(id, roleId);
    }

    @Override
    public void updateEmail(Integer id, String email) {
        userService.updateEmail(id, email);
    }
}
