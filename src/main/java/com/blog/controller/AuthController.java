package com.blog.controller;

import com.blog.dto.JWTAuthResponse;
import com.blog.dto.LoginDto;
import com.blog.dto.RegisterDto;
import com.blog.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = {"/login", "/signin"})
    public JWTAuthResponse login(@RequestBody @Valid LoginDto loginDto) {
        return authService.login(loginDto);
    }

    @PostMapping(value = {"/register", "/signup"})
    @ResponseStatus(HttpStatus.CREATED)
    public String register(@RequestBody @Valid RegisterDto registerDto) {
        return authService.register(registerDto);
    }

}
