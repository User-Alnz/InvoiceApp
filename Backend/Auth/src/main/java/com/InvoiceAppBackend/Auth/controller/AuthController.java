package com.InvoiceAppBackend.Auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.InvoiceAppBackend.Auth.dto.RequestCreateUser;
import com.InvoiceAppBackend.Auth.dto.ResponsePattern;
import com.InvoiceAppBackend.Auth.service.UserInfoService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/auth")
public class AuthController 
{
    private UserInfoService service;
    
    AuthController(UserInfoService service)
    {
        this.service = service;
    }

    @GetMapping
    public String runTest() {
        return "Controller ok";
    }

    @PostMapping("/register")
    public ResponseEntity<ResponsePattern<String>> postMethodName(@Valid @RequestBody RequestCreateUser request) 
    {
        String response = service.addUser(request);

        return ResponseEntity.ok(new ResponsePattern<>("success", 200, response));
    }
    
}
