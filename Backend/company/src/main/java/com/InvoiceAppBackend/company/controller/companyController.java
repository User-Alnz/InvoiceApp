package com.InvoiceAppBackend.company.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api/company")
public class companyController 
{
    @GetMapping()
    public String test() 
    {
        return "OK this work - JWT accepted";
    }
       
}
