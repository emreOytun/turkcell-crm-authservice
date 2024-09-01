package com.turkcell.authservice.services.concretes;

import com.turkcell.authservice.entities.Role;
import com.turkcell.authservice.entities.User;
import com.turkcell.authservice.entities.factories.UserFactory;
import com.turkcell.authservice.repositories.RoleRepository;
import com.turkcell.authservice.repositories.UserRepository;
import com.turkcell.authservice.services.abstracts.UserService;
import com.turkcell.pair3.core.events.RegisterEvent;
import com.turkcell.pair3.core.exception.factories.AccessDeniedExceptionFactory;
import com.turkcell.pair3.core.messages.Messages;
import com.turkcell.pair3.core.services.abstracts.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageService messageService;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> AccessDeniedExceptionFactory.createWithMessage(messageService.getMessage(Messages.BusinessErrors.BILL_ACCOUNT_HAS_PRODUCT)));
    }

    @Override
    public Integer add(RegisterEvent request) {
        return userRepository.save(UserFactory.create(request.getEmail(), passwordEncoder.encode(request.getPassword()))).getId();
    }

    @Override
    public void giveRole(Integer id, Integer roleId) {
        User user = userRepository.findById(id).orElseThrow(() -> AccessDeniedExceptionFactory.createWithMessage(messageService.getMessage(Messages.BusinessErrors.NO_USER_FOUND)));
        //find role with roleId
        Role role = roleRepository.findById(roleId).orElseThrow(() -> AccessDeniedExceptionFactory.createWithMessage(messageService.getMessage(Messages.BusinessErrors.NO_ROLE_FOUND)));
        user.getAuthorities().add(role);
        userRepository.save(user);
    }

    @Override
    public void updateEmail(Integer id, String email) {
        User user = userRepository.findById(id).orElseThrow(() -> AccessDeniedExceptionFactory.createWithMessage(messageService.getMessage(Messages.BusinessErrors.NO_USER_FOUND)));
        user.setEmail(email);
        userRepository.save(user);
    }
}