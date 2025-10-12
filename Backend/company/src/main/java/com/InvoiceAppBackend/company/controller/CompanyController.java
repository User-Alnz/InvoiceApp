package com.InvoiceAppBackend.company.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.InvoiceAppBackend.company.dto.CreateCompanyRequest;
import com.InvoiceAppBackend.company.dto.ResponsePattern;
import com.InvoiceAppBackend.company.dto.UpdateCompanyRequest;
import com.InvoiceAppBackend.company.entity.Company;
import com.InvoiceAppBackend.company.service.CompanyService;

import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/company")
public class CompanyController
{
    private final CompanyService service;

    @GetMapping()
    public ResponseEntity<ResponsePattern<Company>> getCompany(Authentication authentication) 
    {
        Claims claims = (Claims) authentication.getPrincipal();

        //typecast to UUID. safe
        UUID tenantId = UUID.fromString((String) claims.get("tenantId"));

        Company response = service.getCompany(tenantId);

        return ResponseEntity.ok(new ResponsePattern<>("success",200 , response));
    }

    @PostMapping()
    public ResponseEntity<ResponsePattern<Company>> createCompany(Authentication authentication, @Valid @RequestBody CreateCompanyRequest request) 
    {
        Claims claims = (Claims) authentication.getPrincipal();

        //typecast to UUID. safe
        UUID tenantId = UUID.fromString((String) claims.get("tenantId"));

        Company response = service.createCompany(tenantId, request);

        return ResponseEntity.ok(new ResponsePattern<>("success",200 , response));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ResponsePattern<Company>> updateCompany(Authentication authentication, @PathVariable Long id, @RequestBody UpdateCompanyRequest request) 
    {
        Claims claims = (Claims) authentication.getPrincipal();

        //typecast to UUID. safe
        UUID tenantId = UUID.fromString((String) claims.get("tenantId"));

        Company response = service.updateCompany(tenantId, id, request);
        
        return ResponseEntity.ok(new ResponsePattern<>("success",200 , response));
    }
}
