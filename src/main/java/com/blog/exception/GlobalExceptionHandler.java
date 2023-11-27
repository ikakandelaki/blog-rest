package com.blog.exception;

import com.blog.dto.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorDetails handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {
        return getErrorDetails(exception, webRequest);
    }

    @ExceptionHandler(BlogException.class)
    public ResponseEntity<ErrorDetails> handleBlogException(BlogException exception, WebRequest webRequest) {
        return new ResponseEntity<>(getErrorDetails(exception, webRequest), exception.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> (FieldError) error)
                .filter(fieldError -> fieldError.getDefaultMessage() != null)
                .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> new ArrayList<>(List.of(error.getDefaultMessage())),
                        (oldList, newList) -> {
                            oldList.addAll(newList);
                            return oldList;
                        }));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorDetails handleAccessDeniedException(AccessDeniedException exception, WebRequest webRequest) {
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
