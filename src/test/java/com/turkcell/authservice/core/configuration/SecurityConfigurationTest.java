package com.turkcell.authservice.core.configuration;

import com.turkcell.authservice.controllers.AuthController;
import com.turkcell.authservice.services.abstracts.AuthService;
import com.turkcell.pair3.core.configuration.BaseSecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Configuration
class TestConfig {
    // Injected to AuthController
//    @MockBean
//    private JwtService jwtService;

    // Injected to AuthService
    @MockBean
    private AuthService authService;
}

@SpringJUnitConfig
@ContextConfiguration(classes ={TestConfig.class})
@WebMvcTest(AuthController.class)
@Import(SecurityConfiguration.class)
public class SecurityConfigurationTest {

    @Autowired
    private MockMvc mockMvc;

    // This is injected
    @MockBean
    private BaseSecurityService baseSecurityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Mock configurations are handled by @MockBean and @WebMvcTest
    }

    // Test to see if SecurityConfiguration which is imported to the Spring context as a bean provides BaseSecurityService bean.
    @Test
    void testRegisterEndpointPermitAll() throws Exception {
        mockMvc.perform(post("/api/v1/auth/register"));

        verify(baseSecurityService, times(1)).configureCoreSecurity(any(HttpSecurity.class));
    }
}
