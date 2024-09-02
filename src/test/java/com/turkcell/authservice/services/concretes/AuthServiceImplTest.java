package com.turkcell.authservice.services.concretes;

import com.turkcell.authservice.entities.User;
import com.turkcell.authservice.testfactories.TestRegisterEventFactory;
import com.turkcell.authservice.testfactories.TestRoleFactory;
import com.turkcell.authservice.testfactories.TestUserFactory;
import com.turkcell.authservice.services.abstracts.UserService;
import com.turkcell.authservice.services.dtos.requests.LoginRequest;
import com.turkcell.pair3.core.events.RegisterEvent;
import com.turkcell.pair3.core.exception.types.BusinessException;
import com.turkcell.pair3.core.jwt.JwtService;
import com.turkcell.pair3.core.messages.Messages;
import com.turkcell.pair3.core.services.abstracts.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserService userService;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authService = new AuthServiceImpl(authenticationManager, jwtService, userService, messageService);
    }

    @Test
    void testRegisterSuccess() {
        RegisterEvent registerEvent = TestRegisterEventFactory.defaultRegisterEvent();
        when(userService.add(registerEvent)).thenReturn(TestUserFactory.defaultUserId);

        Integer userId = authService.register(registerEvent);

        assertEquals(TestUserFactory.defaultUserId, userId);
        verify(userService, times(1)).add(registerEvent);
    }

    @Test
    void testLoginSuccess() {
        LoginRequest loginRequest = new LoginRequest(TestUserFactory.defaultUserEmail, TestRegisterEventFactory.defaultRegisterEventPassword);
        Authentication authenticationMock = mock(Authentication.class);
        User user = TestUserFactory.defaultUser();

        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authenticationMock);
        when(userService.loadUserByUsername(loginRequest.getEmail())).thenReturn(user);
        when(jwtService.generateToken(anyString(), anyList())).thenReturn("generatedToken");

        String token = authService.login(loginRequest);

        assertEquals("generatedToken", token);
        verify(authenticationManager, times(1)).authenticate(any(Authentication.class));
        verify(userService, times(1)).loadUserByUsername(loginRequest.getEmail());
        verify(jwtService, times(1)).generateToken(user.getUsername(), user.getAuthorities().stream().map(role -> role.toString()).collect(Collectors.toList()));
    }

    @Test
    void testLoginFailure() {
        LoginRequest loginRequest = new LoginRequest(TestUserFactory.defaultUserEmail, TestRegisterEventFactory.defaultRegisterEventPassword);
        when(authenticationManager.authenticate(any(Authentication.class))).thenThrow(new RuntimeException("Authentication failed"));
        when(messageService.getMessage(Messages.BusinessErrors.WRONG_USERNAME_OR_PASSWORD)).thenReturn(TestUserFactory.wrongCredentialsMsg);

        BusinessException exception = assertThrows(BusinessException.class, () -> authService.login(loginRequest));

        assertEquals(exception.getMessage(), TestUserFactory.wrongCredentialsMsg);
        verify(authenticationManager, times(1)).authenticate(any(Authentication.class));
        verify(messageService, times(1)).getMessage(Messages.BusinessErrors.WRONG_USERNAME_OR_PASSWORD);
    }

    @Test
    void testGiveRole() {
        Integer userId = TestUserFactory.defaultUserId;
        Integer roleId = TestRoleFactory.defaultRoleId;

        authService.giveRole(userId, roleId);

        verify(userService, times(1)).giveRole(userId, roleId);
    }

    @Test
    void testUpdateEmail() {
        Integer userId = TestUserFactory.defaultUserId;
        String newEmail = TestUserFactory.defaultUserEmail;

        authService.updateEmail(userId, newEmail);

        verify(userService, times(1)).updateEmail(userId, newEmail);
    }
}
