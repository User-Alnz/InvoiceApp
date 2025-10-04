package com.InvoiceAppBackend.Auth.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.InvoiceAppBackend.Auth.dto.AuthRequest;
import com.InvoiceAppBackend.Auth.dto.RequestCreateUser;
import com.InvoiceAppBackend.Auth.dto.ResponsePattern;
import com.InvoiceAppBackend.Auth.service.JWTService;
import com.InvoiceAppBackend.Auth.service.UserInfoDetails;
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
    public ResponseEntity<ResponsePattern<String>> login(@Valid @RequestBody AuthRequest request) 
    {

        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(), 
                request.getPassword()
            )
        );
        
        UserInfoDetails userDetails = (UserInfoDetails) auth.getPrincipal();

        if(auth.isAuthenticated())
        {
            String jwt = jwtService.generateToken(userDetails);

            ResponseCookie cookie = ResponseCookie.from("jwt", jwt)
            .httpOnly(true)
            //.secure(true) //https only - remove comment for prod mod !
            .sameSite("Strict")
            .path("/")
            .maxAge(3600) //equiv to 1 hour
            .build();

            return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(new ResponsePattern<>("success", 200, "Login successful"));
        }
        else
        throw new UsernameNotFoundException("invalid credentials");
    }
    
}
