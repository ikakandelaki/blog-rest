package com.blog.service;

import com.blog.dto.LoginDto;
import com.blog.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
