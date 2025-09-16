package com.InvoiceAppBackend.user.errors;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.InvoiceAppBackend.user.dto.ResponsePattern;

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

    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponsePattern<String>> handleNotFound(EntityNotFoundException ex) 
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponsePattern<>("error", 404, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponsePattern<String>> handleGeneric(Exception ex) 
    {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponsePattern<>("error", 500, "Unexpected error occurred"));
    }
}
