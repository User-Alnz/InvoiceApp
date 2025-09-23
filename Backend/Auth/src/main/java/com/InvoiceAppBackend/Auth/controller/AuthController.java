package com.InvoiceAppBackend.Auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.InvoiceAppBackend.Auth.dto.AuthRequest;
import com.InvoiceAppBackend.Auth.dto.RequestCreateUser;
import com.InvoiceAppBackend.Auth.dto.ResponsePattern;
//import com.InvoiceAppBackend.Auth.service.JWTService;
import com.InvoiceAppBackend.Auth.service.UserInfoService;

import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/auth")
public class AuthController 
{
    private UserInfoService service;
    private AuthenticationManager authManager;
    //private JWTService jwtservice;
    
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    AuthController(
        UserInfoService service,
        AuthenticationManager authManager)
        //JWTService jwtservice)
    {
        this.service = service;
        this.authManager = authManager;
        //this.jwtservice = jwtservice;
    }

    @GetMapping
    public String runTest() {
        return "Controller ok";
    }

    @PostMapping("/register")
    public ResponseEntity<ResponsePattern<String>> register(@Valid @RequestBody RequestCreateUser request) 
    {
        String response = service.addUser(request);

        return ResponseEntity.ok(new ResponsePattern<>("success", 200, response));
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) 
    {
        log.info("AuthRequest: {}", request);

        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(), 
                request.getPassword()
            )
        );

        log.info("AuthRequest: {}", auth);
        
        if(auth.isAuthenticated())
            return "login sucess"; //remove for JWT hashing
        else
            throw new UsernameNotFoundException("invalid credentials");
    }
    
}
