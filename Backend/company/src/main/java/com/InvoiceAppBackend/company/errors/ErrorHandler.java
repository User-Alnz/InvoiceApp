package com.InvoiceAppBackend.company.errors;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.InvoiceAppBackend.company.dto.ResponsePattern;

import jakarta.persistence.EntityNotFoundException;


@RestControllerAdvice
public class ErrorHandler 
{
    /* * *
     *
     * DTO validation errors
     * 
     * handleValidationExceptions(MethodArgumentNotValidException ex)
     * 
     * MethodArgumentNotValidException catches Validation errors => from OjectClass using import jakarta.validation.constraints.*; 
     * like @NotBlank @Pattern @size etc.. 
     * 
     * use hashmap to write object with {"key", "value"};  
     * 
     * * */
    @ExceptionHandler(MethodArgumentNotValidException.class) 
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) 
    { 
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
        .forEach(
            error -> errors.put(error.getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST); 
    }

    /*
     * This is simpler ExceptionHandler. Simply use ex : 
     * 
     *  throw new EntityNotFoundException("Tenant not found");
     *  throw new IllegalStateException("Company already exists");
     * 
     * Less control on http status, but simpler to use in service
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponsePattern<String>> handleIllegalArgument(IllegalArgumentException ex) 
    {
       return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ResponsePattern<>("error", 400, ex.getMessage()));
    }
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponsePattern<String>> handleNotFound(EntityNotFoundException ex) 
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ResponsePattern<>("error", 404, ex.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ResponsePattern<String>> handleIllegalState(IllegalStateException ex) 
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ResponsePattern<>("error", 400, ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponsePattern<String>> handleBadCredentials(BadCredentialsException ex) 
    {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body (new ResponsePattern<>("error", 401,"Invalid credentials"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponsePattern<String>> handleGeneric(Exception ex) 
    {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ResponsePattern<>("error", 500, "Unexpected error occurred"));
    }

    /*
     * This is for costumed ResponseStatusException like :
     * 
     * throw new ResponseStatusException(HttpStatus.NOT_FOUND, "data not found");
     * throw new ResponseStatusException(HttpStatus.CONFLICT, "data already exists");
     * 
     * Give you more controll over HttpStatus. other way to do it directly in service.
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ResponsePattern<String>> handleResponseStatus(ResponseStatusException ex) 
    {
        return ResponseEntity.status(ex.getStatusCode())
            .body(new ResponsePattern<>("error", ex.getStatusCode().value(), ex.getReason()));
    }
}
