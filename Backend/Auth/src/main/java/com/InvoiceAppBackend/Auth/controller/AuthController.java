package com.InvoiceAppBackend.Auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.InvoiceAppBackend.Auth.dto.AuthRequest;
import com.InvoiceAppBackend.Auth.dto.RequestCreateUser;
import com.InvoiceAppBackend.Auth.dto.ResponsePattern;
import com.InvoiceAppBackend.Auth.service.JWTService;
import com.InvoiceAppBackend.Auth.service.UserInfoService;

import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@RestController
@RequestMapping("/auth")
public class AuthController 
{
    private UserInfoService service;
    private AuthenticationManager authManager;
    private JWTService jwtService;
    

    AuthController(
        UserInfoService service,
        AuthenticationManager authManager,
        JWTService jwtService)
    {
        this.service = service;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    @GetMapping
    public String runTest() {
        return "Controller ok";
    }

    @PostMapping("/register")
    public ResponseEntity<ResponsePattern<String>> register(@Valid @RequestBody RequestCreateUser request) 
    {
        String response = service.createUser(request);

        return ResponseEntity.ok(new ResponsePattern<>("success", 200, response));
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) 
    {

        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(), 
                request.getPassword()
            )
        );
        
        if(auth.isAuthenticated())
            return jwtService.generateToken(auth.getName());
        else
        throw new UsernameNotFoundException("invalid credentials");
    }
    
}
