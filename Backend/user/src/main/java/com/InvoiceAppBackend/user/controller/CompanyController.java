package com.InvoiceAppBackend.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.InvoiceAppBackend.user.service.CompanyService;

import jakarta.validation.Valid;

import com.InvoiceAppBackend.user.model.UserCompany;
import com.InvoiceAppBackend.user.model.UserClient;
import com.InvoiceAppBackend.user.dto.ResponsePattern;
import com.InvoiceAppBackend.user.dto.request.CreateCompanyRequest;
import com.InvoiceAppBackend.user.dto.request.UpdateCompanyRequest;

import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("api/company")
public class CompanyController
{
    private CompanyService service;

    public CompanyController (CompanyService service)
    {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePattern<UserCompany>> getCompanyById(@PathVariable Long id) 
    {    
        UserCompany response = service.getCompanyById(id);

        return ResponseEntity.ok(new ResponsePattern<>("success",200 , response));
    }

    @GetMapping("/{id}/clients")
    public ResponseEntity<ResponsePattern<List<UserClient>>> getCompanyClients(@PathVariable Long id) 
    {
        List<UserClient> response = service.getCompanyClients(id);

        return ResponseEntity.ok(new ResponsePattern<>("success",200 , response));
    }

    @PostMapping //@Valid allows validation over Object properties CreateCompanyRequest
    public ResponseEntity<ResponsePattern<UserCompany>> createCompany(@Valid @RequestBody CreateCompanyRequest request) 
    {   
        UserCompany response = service.createCompany(request);

        return ResponseEntity.ok(new ResponsePattern<>("success", 200, response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponsePattern<String>> deleteCompany(@PathVariable Long id)
    {
        service.deleteCompany(id);

        return ResponseEntity.ok(new ResponsePattern<>("success", 200, "Company deleted"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePattern<UserCompany>> updateCompany(@PathVariable Long id, @Valid @RequestBody UpdateCompanyRequest request)
    {
        UserCompany response = service.updateCompany(id, request);

        return ResponseEntity.ok(new ResponsePattern<>("success", 200, response));
    }
}
