package com.InvoiceAppBackend.company.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.InvoiceAppBackend.company.entity.Company;
import com.InvoiceAppBackend.company.entity.Tenant;
import com.InvoiceAppBackend.company.repository.CompanyRepository;
import com.InvoiceAppBackend.company.repository.TenantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyService 
{
    private final CompanyRepository repository;
    private final TenantRepository tenantRepository;

    
    public Optional<Company> getCompany(UUID tenantId)
    {
       
       Optional<Tenant> tenant = tenantRepository.findById(tenantId);

       if(tenant.isEmpty())
       {
            Tenant createTenant = new Tenant();
            createTenant.setId(tenantId);
            createTenant.setCreatedAt(LocalDateTime.now());
            
            tenantRepository.save(createTenant);

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No company registered yet");
       }
      
       return repository.findByTenantId(tenantId);
    }
    
    
}
