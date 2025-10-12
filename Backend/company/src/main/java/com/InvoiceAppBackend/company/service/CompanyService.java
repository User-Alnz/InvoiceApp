package com.InvoiceAppBackend.company.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.InvoiceAppBackend.company.dto.CreateCompanyRequest;
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

    
    public Company getCompany(UUID tenantId)
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
      
       return repository.findByTenantId(tenantId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No company registered yet"));
    }
    

    public Company createCompany(UUID tenantId ,CreateCompanyRequest request)
    {

        Tenant tenant = tenantRepository.findById(tenantId)
        .orElseThrow(() -> new BadCredentialsException("Invalid request"));

        boolean doesCompanyExist = repository.findByTenantId(tenantId).isPresent();

        if(doesCompanyExist) 
        throw new IllegalStateException("No more than one company per user");
        
        Company company = Company.builder()
        .name(request.getName())
        .tenant(tenant)
        .address(request.getName())
        .postalCode(request.getPostalCode())
        .country(request.getCountry())
        .tel(request.getTel())
        .email(request.getEmail())
        .legalStatus(request.getLegalStatus())
        .shareCapital(request.getShareCapital())
        .siren(request.getSiren())
        .siret(request.getSiret())
        .rcs(request.getRcs())
        .tvaNumber(request.getTvaNumber())
        .websiteUrl(request.getWebsiteUrl())
        .build();
         
        return repository.save(company);
    }


}
