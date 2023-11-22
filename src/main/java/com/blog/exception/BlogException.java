package com.blog.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class BlogException extends RuntimeException {
    private final HttpStatus status;
    private final String message;
}
