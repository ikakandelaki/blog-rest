package com.blog.exception;

import com.blog.dto.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorDetails handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {
        return getErrorDetails(exception, webRequest);
    }

    @ExceptionHandler(BlogException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDetails handleBlogException(BlogException exception, WebRequest webRequest) {
        return getErrorDetails(exception, webRequest);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDetails handleGlobalException(Exception exception, WebRequest webRequest) {
        return getErrorDetails(exception, webRequest);
    }

    private ErrorDetails getErrorDetails(Exception exception, WebRequest webRequest) {
        return new ErrorDetails(
                new Date(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );
    }
}
