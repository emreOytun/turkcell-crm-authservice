package com.turkcell.authservice.controllers;

import com.turkcell.authservice.services.abstracts.AuthService;
import com.turkcell.authservice.services.dtos.requests.LoginRequest;
import com.turkcell.authservice.testfactories.TestLoginRequestFactory;
import com.turkcell.authservice.testfactories.TestRegisterEventFactory;
import com.turkcell.authservice.testfactories.TestUserFactory;
import com.turkcell.pair3.core.events.RegisterEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerTest() {
        // Arrange
        RegisterEvent registerEvent = TestRegisterEventFactory.defaultRegisterEvent();
        when(authService.register(registerEvent)).thenReturn(TestUserFactory.defaultUserId);

        // Act
        Integer userId = authController.register(registerEvent);

        // Assert
        assertEquals(TestUserFactory.defaultUserId, userId);
        verify(authService).register(registerEvent);
    }

    @Test
    void loginTest() {
        // Arrange
        LoginRequest loginRequest = TestLoginRequestFactory.defaultLoginRequest();
        when(authService.login(loginRequest)).thenReturn("token");

        // Act
        String token = authController.login(loginRequest);

        // Assert
        assertEquals("token", token);
        verify(authService).login(loginRequest);
    }


    @Test
    void giveRoleTest() {
        // Arrange
        Integer userId = TestUserFactory.defaultUserId;
        Integer roleId = 2;

        // Act
        authController.giveRole(userId, roleId);

        // Assert
        verify(authService).giveRole(userId, roleId);
    }

    @Test
    void updateEmailTest() {
        // Arrange
        Integer userId = TestUserFactory.defaultUserId;
        String newEmail = TestUserFactory.newEmailToUpdate;

        // Act
        authController.updateEmail(userId, newEmail);

        // Assert
        verify(authService).updateEmail(userId, newEmail);
    }
}
