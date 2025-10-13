package com.InvoiceAppBackend.company.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.InvoiceAppBackend.company.dto.CreateClientRequest;
import com.InvoiceAppBackend.company.dto.ResponsePattern;
import com.InvoiceAppBackend.company.entity.Client;
import com.InvoiceAppBackend.company.service.ClientService;

import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/company/{companyId}/client")
public class ClientController 
{
 
    private final ClientService service;

    @GetMapping()
    public ResponseEntity<ResponsePattern<Page<Client>>> getClients(
        Authentication authentication,
        @PathVariable long companyId,
        @RequestParam(defaultValue = "0") int page) 
    {
        Claims claims = (Claims) authentication.getPrincipal();

        //typecast to UUID. safe
        UUID tenantId = UUID.fromString((String) claims.get("tenantId"));

        //enforce limit - front can't define limit. Only part of Backend logic.
        int maxSize = 20;

        Page<Client> response = service.getClients(tenantId, companyId ,page, maxSize);

        return ResponseEntity.ok(new ResponsePattern<>("succes", 200, response));
    }

    @PostMapping()
    public ResponseEntity<ResponsePattern<Client>> createClient(
        Authentication authentication, 
        @PathVariable long companyId, 
        @Valid @RequestBody CreateClientRequest request)
    {
        Claims claims = (Claims) authentication.getPrincipal();

        //typecast to UUID. safe
        UUID tenantId = UUID.fromString((String) claims.get("tenantId"));


        Client response = service.createClient(tenantId, companyId, request);
        
        return ResponseEntity.ok(new ResponsePattern<>("succes", 200, response));
    }
    

}
