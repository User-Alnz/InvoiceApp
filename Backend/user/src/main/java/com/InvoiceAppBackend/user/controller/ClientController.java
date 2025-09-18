package com.InvoiceAppBackend.user.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.InvoiceAppBackend.user.dto.ResponsePattern;
import com.InvoiceAppBackend.user.dto.request.CreateClientRequest;
import com.InvoiceAppBackend.user.dto.response.CreateClientResponse;
import com.InvoiceAppBackend.user.model.UserClient;
import com.InvoiceAppBackend.user.service.ClientService;

@RestController
@RequestMapping("/api/client")
public class ClientController 
{
    private ClientService service;

    public ClientController(ClientService service)
    {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePattern<UserClient>> getClientById(@PathVariable Long id) 
    {
        UserClient response = service.getClientById(id);

        return ResponseEntity.ok(new ResponsePattern<>("success", 200, response));
    }

    @PostMapping
    public ResponseEntity<ResponsePattern<CreateClientResponse>> createClient(@Valid @RequestBody CreateClientRequest request)
    {   
        CreateClientResponse response = service.createClient(request);

        return ResponseEntity.ok(new ResponsePattern<>("success", 200, response));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponsePattern<String>> deleteClient(@PathVariable Long id)
    {
        service.deleteClient(id);
        return ResponseEntity.ok(new ResponsePattern<>("success", 200, "Client deleted"));   
    }   
}
