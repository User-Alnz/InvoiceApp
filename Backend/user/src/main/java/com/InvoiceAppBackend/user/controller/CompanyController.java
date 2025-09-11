package com.InvoiceAppBackend.user.controller;

import org.springframework.web.bind.annotation.*;

import com.InvoiceAppBackend.user.service.CompanyService;
import com.InvoiceAppBackend.user.model.UserCompany;

import java.util.List;


@RestController
@RequestMapping("api/company")
public class CompanyController
{
    private CompanyService service;

    public CompanyController (CompanyService service)
    {
        this.service = service;
    }

    @PostMapping
    public UserCompany createCompany(@RequestBody UserCompany entity) 
    {    
        return service.createCompany(entity);
    }

    @GetMapping
    public List<UserCompany> getAllComapny() 
    {
        return service.getAllCompanies();
    }
    
}
