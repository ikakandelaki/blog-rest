package com.blog.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    private String name;

    @NotBlank(message = "username should be non-blank")
    private String username;

    @NotBlank(message = "email should be non-blank")
    private String email;

    @NotBlank(message = "password should be non-blank")
    private String password;
}
