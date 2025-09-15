package com.InvoiceAppBackend.user.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.InvoiceAppBackend.user.dto.ResponsePattern;

import jakarta.persistence.EntityNotFoundException;


@RestControllerAdvice
public class ErrorHandler 
{
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
