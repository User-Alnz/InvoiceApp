package com.InvoiceAppBackend.Auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/auth")
public class AuthController 
{
    @GetMapping
    public String runTest() {
        return "Controller ok";
    }
}
