package com.blog.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class BlogException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public BlogException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }
}
