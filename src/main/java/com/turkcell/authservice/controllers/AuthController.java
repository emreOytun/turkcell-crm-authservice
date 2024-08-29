package com.turkcell.authservice.controllers;


import com.turkcell.authservice.services.abstracts.AuthService;
import com.turkcell.authservice.services.dtos.requests.LoginRequest;
import com.turkcell.pair3.core.events.RegisterEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Integer register(@RequestBody RegisterEvent request)
    {
        return authService.register(request);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String login(@RequestBody LoginRequest request)
    {
        return authService.login(request);
    }

    //give role to user
    @PostMapping("/role/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void giveRole(@PathVariable Integer id, @RequestParam Integer roleId)
    {
        authService.giveRole(id, roleId);
    }

    //update email
    @PutMapping("/email/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateEmail(@PathVariable Integer id, @RequestParam String email)
    {
        authService.updateEmail(id, email);
    }
}
