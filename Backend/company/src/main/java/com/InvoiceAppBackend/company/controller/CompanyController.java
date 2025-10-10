package com.InvoiceAppBackend.company.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.InvoiceAppBackend.company.dto.ResponsePattern;
import com.InvoiceAppBackend.company.entity.Company;
import com.InvoiceAppBackend.company.service.CompanyService;

import io.jsonwebtoken.Claims;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/company")
public class CompanyController
{
    private final CompanyService service;

    @GetMapping()
    public ResponseEntity<ResponsePattern<Optional<Company>>> getCompany(Authentication authentication) 
    {
        Claims claims = (Claims) authentication.getPrincipal();

        //typecast to UUID. safe
        UUID tenantId = UUID.fromString((String) claims.get("tenantId"));

        Optional<Company> response = service.getCompany(tenantId);

        return ResponseEntity.ok(new ResponsePattern<>("success",200 , response));
    }

}
