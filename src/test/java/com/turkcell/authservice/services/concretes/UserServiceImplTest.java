package com.turkcell.authservice.services.concretes;

import com.turkcell.authservice.entities.Role;
import com.turkcell.authservice.entities.User;
import com.turkcell.authservice.testfactories.TestRegisterEventFactory;
import com.turkcell.authservice.testfactories.TestRoleFactory;
import com.turkcell.authservice.testfactories.TestUserFactory;
import com.turkcell.authservice.repositories.RoleRepository;
import com.turkcell.authservice.repositories.UserRepository;
import com.turkcell.pair3.core.events.RegisterEvent;
import com.turkcell.pair3.core.messages.Messages;
import com.turkcell.pair3.core.services.abstracts.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MessageService messageService;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository, passwordEncoder, messageService, roleRepository);
    }

    @Test
    void testLoadUserByUsernameSuccess() {
        User user = TestUserFactory.defaultUser();
        when(userRepository.findByEmail(TestUserFactory.defaultUserEmail)).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername(TestUserFactory.defaultUserEmail);

        assertEquals(TestUserFactory.defaultUserEmail, userDetails.getUsername());
        verify(userRepository, times(1)).findByEmail(TestUserFactory.defaultUserEmail);
    }

    @Test
    void testLoadUserByUsernameFailure() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(messageService.getMessage(Messages.BusinessErrors.NO_USER_FOUND)).thenReturn(TestUserFactory.userNotFoundMsg);

        Exception exception = assertThrows(AccessDeniedException.class,
                () -> userService.loadUserByUsername(TestUserFactory.defaultUserEmail));

        assertEquals(exception.getMessage(), TestUserFactory.userNotFoundMsg);
        verify(userRepository, times(1)).findByEmail(TestUserFactory.defaultUserEmail);
        verify(messageService, times(1)).getMessage(Messages.BusinessErrors.NO_USER_FOUND);
    }

    @Test
    void testAddUser() {
        User user = TestUserFactory.defaultUser();
        RegisterEvent registerEvent = TestRegisterEventFactory.defaultRegisterEvent();
        when(passwordEncoder.encode(registerEvent.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        Integer userId = userService.add(registerEvent);

        assertEquals(TestUserFactory.defaultUserId, userId);
        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode(registerEvent.getPassword());
    }

    @Test
    void testGiveRoleSuccess() {
        User user = TestUserFactory.defaultUser();
        Role role = TestRoleFactory.defaultRole();
        when(userRepository.findById(TestUserFactory.defaultUserId)).thenReturn(Optional.of(user));
        when(roleRepository.findById(TestRoleFactory.defaultRoleId)).thenReturn(Optional.of(role));

        userService.giveRole(TestUserFactory.defaultUserId, TestRoleFactory.defaultRoleId);

        assertTrue(user.getAuthorities().contains(role));
        verify(userRepository, times(1)).findById(TestUserFactory.defaultUserId);
        verify(roleRepository, times(1)).findById(TestRoleFactory.defaultRoleId);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGiveRoleUserNotFound() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(messageService.getMessage(Messages.BusinessErrors.NO_USER_FOUND)).thenReturn(TestUserFactory.userNotFoundMsg);

        Exception exception = assertThrows(AccessDeniedException.class, () -> {
            userService.giveRole(TestUserFactory.defaultUserId, TestRoleFactory.defaultRoleId);
        });

        assertEquals(exception.getMessage(), TestUserFactory.userNotFoundMsg);
        verify(userRepository, times(1)).findById(TestUserFactory.defaultUserId);
        verify(messageService, times(1)).getMessage(Messages.BusinessErrors.NO_USER_FOUND);
    }

    @Test
    void testGiveRoleRoleNotFound() {
        User user = TestUserFactory.defaultUser();
        when(userRepository.findById(TestUserFactory.defaultUserId)).thenReturn(Optional.of(user));
        when(roleRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(messageService.getMessage(Messages.BusinessErrors.NO_ROLE_FOUND)).thenReturn(TestRoleFactory.roleNotFoundMsg);

        Exception exception = assertThrows(AccessDeniedException.class, () -> {
            userService.giveRole(TestUserFactory.defaultUserId, TestRoleFactory.defaultRoleId);
        });

        assertEquals(exception.getMessage(), TestRoleFactory.roleNotFoundMsg);
        verify(userRepository, times(1)).findById(TestUserFactory.defaultUserId);
        verify(roleRepository, times(1)).findById(TestRoleFactory.defaultRoleId);
        verify(messageService, times(1)).getMessage(Messages.BusinessErrors.NO_ROLE_FOUND);
    }

    @Test
    void testUpdateEmailSuccess() {
        User user = TestUserFactory.defaultUser();
        when(userRepository.findById(TestUserFactory.defaultUserId)).thenReturn(Optional.of(user));

        userService.updateEmail(TestUserFactory.defaultUserId, TestUserFactory.newEmailToUpdate);

        assertEquals(TestUserFactory.newEmailToUpdate, user.getEmail());
        verify(userRepository, times(1)).findById(TestUserFactory.defaultUserId);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateEmailUserNotFound() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(messageService.getMessage(Messages.BusinessErrors.NO_USER_FOUND)).thenReturn(TestUserFactory.userNotFoundMsg);

        Exception exception = assertThrows(AccessDeniedException.class, () ->
            userService.updateEmail(TestUserFactory.defaultUserId, TestUserFactory.newEmailToUpdate));

        assertEquals(exception.getMessage(), TestUserFactory.userNotFoundMsg);
        verify(userRepository, times(1)).findById(TestUserFactory.defaultUserId);
        verify(messageService, times(1)).getMessage(Messages.BusinessErrors.NO_USER_FOUND);
    }
}
