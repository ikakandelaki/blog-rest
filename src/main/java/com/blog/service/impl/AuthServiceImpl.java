package com.blog.service.impl;

import com.blog.dto.LoginDto;
import com.blog.dto.RegisterDto;
import com.blog.entity.User;
import com.blog.exception.BlogException;
import com.blog.repository.RoleRepository;
import com.blog.repository.UserRepository;
import com.blog.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "User Logged-in successfully";
    }

    @Override
    public String register(RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new BlogException(HttpStatus.BAD_REQUEST, "Username already exists!");
        }
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new BlogException(HttpStatus.BAD_REQUEST, "Email already exists!");
        }
        User user = new User(
                registerDto.getName(),
                registerDto.getUsername(),
                registerDto.getEmail(),
                passwordEncoder.encode(registerDto.getPassword())
        );

        roleRepository.findByName("ROLE_USER")
                .ifPresent(role -> user.setRoles(List.of(role)));
        userRepository.save(user);

        return "User registered successfully";
    }
}
