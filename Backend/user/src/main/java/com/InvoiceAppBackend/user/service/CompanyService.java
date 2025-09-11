package com.InvoiceAppBackend.user.service;

import org.springframework.stereotype.Service;
import com.InvoiceAppBackend.user.model.UserCompany;
import com.InvoiceAppBackend.user.repository.CompanyRepository;

import java.util.List;

@Service
public class CompanyService 
{
    private final CompanyRepository repository;
    
    public CompanyService(CompanyRepository repository)
    {
        this.repository = repository;
    }

    public List<UserCompany> getAllCompanies() 
    {
        return repository.findAll();
    }

    public UserCompany createCompany(UserCompany company) 
    {
        return repository.save(company);
    }
}
