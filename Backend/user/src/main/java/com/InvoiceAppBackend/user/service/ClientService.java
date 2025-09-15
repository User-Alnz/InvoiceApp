package com.InvoiceAppBackend.user.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.InvoiceAppBackend.user.dto.request.CreateClientRequest;
import com.InvoiceAppBackend.user.model.UserClient;
import com.InvoiceAppBackend.user.model.UserCompany;
import com.InvoiceAppBackend.user.repository.ClientRepository;
import com.InvoiceAppBackend.user.repository.CompanyRepository;

import jakarta.persistence.EntityNotFoundException;

import com.InvoiceAppBackend.user.dto.response.CreateClientResponse;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ClientService 
{
    private final ClientRepository repository;
    private final CompanyRepository companyRepository;

    public List<UserClient> getClientByCompanyId(Long id)
    {
        UserCompany company = companyRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + id));

        return repository.findByCompanyId(company);
    }

    public CreateClientResponse createClient(CreateClientRequest request)
    {
        //check if id company is right
        UserCompany company = companyRepository.findById(request.getCompanyId())
        .orElseThrow(() -> new EntityNotFoundException("Company not found"));

        // Build a new client object linking the company
        UserClient client = UserClient.builder()
                .name(request.getName())
                .address(request.getAddress())
                .postalCode(request.getPostalCode())
                .country(request.getCountry())
                .tel(request.getTel())
                .email(request.getEmail())
                .companyId(company) // link to fetched entity
                .build();

        repository.save(client);

        return CreateClientResponse.builder()
                .id(client.getId())
                .name(client.getName())
                .build();

    }

   public void deleteClient(Long id)
   {
        if (!repository.existsById(id))
        throw new EntityNotFoundException("Client not found with id: " + id);

        repository.deleteById(id);
   }

}
