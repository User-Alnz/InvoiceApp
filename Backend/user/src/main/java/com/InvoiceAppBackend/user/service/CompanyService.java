package com.InvoiceAppBackend.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.InvoiceAppBackend.user.dto.request.CreateCompanyRequest;
import com.InvoiceAppBackend.user.dto.request.UpdateCompanyRequest;
import com.InvoiceAppBackend.user.dto.response.CreateCompanyResponse;

import com.InvoiceAppBackend.user.model.UserAccount;
import com.InvoiceAppBackend.user.model.UserClient;
import com.InvoiceAppBackend.user.model.UserCompany;
import com.InvoiceAppBackend.user.repository.AccountRepository;
import com.InvoiceAppBackend.user.repository.ClientRepository;
import com.InvoiceAppBackend.user.repository.CompanyRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService 
{
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final CompanyRepository repository;
    
    public UserCompany getCompanyById(Long id)
    {
        return repository.findById(id)
        .orElseThrow(()-> new EntityNotFoundException("Company not found with id :" + id));
    }

    public List<UserClient> getCompanyClients(Long id)
    {
        if(!repository.existsById(id))
        throw new EntityNotFoundException("Company not found with id: " + id);

        return clientRepository.findByCompany_Id(id);
    }

    public CreateCompanyResponse createCompany(CreateCompanyRequest request) 
    {
        UserAccount user = accountRepository.findById(request.getUserId())
        .orElseThrow(() -> new EntityNotFoundException("User id not found"));

        long companyCount = repository.countByUserAccount_Id(user.getId());

        int maxCompaniesPerUser = 3;

        if(companyCount >= maxCompaniesPerUser)
        throw new IllegalStateException("User has reached the maximum number of companies allowed. max is " + maxCompaniesPerUser + " per user");

        UserCompany company = UserCompany.builder()
        .userAccount(user) // FK to user table
        .name(request.getName())
        .address(request.getAddress())
        .country(request.getCountry())
        .postalCode(request.getPostalCode())
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

        repository.save(company);

        return CreateCompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .build();
    }

    @Transactional //-> secure deletion and rollback if one function in scope fail
    public void deleteCompany(Long id)
    {
        if(!repository.existsById(id))
        throw new EntityNotFoundException("Company not found with id: " + id);

        clientRepository.deleteByCompany_Id(id); //delete client rows containing FK company_Id
        repository.deleteById(id); //delete company row
    }

    public UserCompany updateCompany(Long id, UpdateCompanyRequest request)
    {
        UserCompany company = repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Company id not found"));

        company.setName(request.getName());
        company.setAddress(request.getAddress());
        company.setPostalCode(request.getPostalCode());
        company.setCountry(request.getCountry());
        company.setTel(request.getTel());
        company.setEmail(request.getEmail());
        company.setLegalStatus(request.getLegalStatus());
        company.setShareCapital(request.getShareCapital());
        company.setSiren(request.getSiren());
        company.setSiret(request.getSiret());
        company.setRcs(request.getRcs());
        company.setTvaNumber(request.getTvaNumber());
        company.setWebsiteUrl(request.getWebsiteUrl());

        return repository.save(company);
    }
}
