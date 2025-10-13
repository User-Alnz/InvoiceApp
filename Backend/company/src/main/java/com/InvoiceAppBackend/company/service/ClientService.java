package com.InvoiceAppBackend.company.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.InvoiceAppBackend.company.dto.CreateClientRequest;
import com.InvoiceAppBackend.company.dto.UpdateClientRequest;
import com.InvoiceAppBackend.company.entity.Client;
import com.InvoiceAppBackend.company.entity.Company;
import com.InvoiceAppBackend.company.entity.Tenant;
import com.InvoiceAppBackend.company.repository.ClientRepository;
import com.InvoiceAppBackend.company.repository.CompanyRepository;
import com.InvoiceAppBackend.company.repository.TenantRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService 
{
    private final ClientRepository repository;
    private final CompanyRepository companyRepository;
    private final TenantRepository tenantRepository;

    public Page<Client> getClients(UUID tenantId, long companyId, int page, int maxSize)
    {
        Pageable pagination = PageRequest.of(page, maxSize) ;

        // rely on tenantId and companyId - relatively secure as long as token cannot be forged by other other or UUID guessed.
        return repository.findByTenantIdAndCompanyId(tenantId, companyId, pagination);        
    }

    public Client createClient(UUID tenantId, long companyId, CreateClientRequest request)
    {
        Tenant tenant = tenantRepository.findById(tenantId)
        .orElseThrow(()-> new BadCredentialsException("invalid Request"));

        //Tenant_Id => secure SQL query -> where tenant.id = tenant.getId(). This secure scoping.
        Company company = companyRepository.findByIdAndTenant_Id(companyId, tenant.getId())
        .orElseThrow(()-> new EntityNotFoundException("Company id not found"));

        Client client = Client.builder()
        .tenant(tenant)
        .company(company)
        .name(request.getName())
        .address(request.getAddress())
        .postalCode(request.getPostalCode())
        .country(request.getCountry())
        .tel(request.getTel())
        .email(request.getEmail())
        .build();

        return repository.save(client);
    }

    public Client updateClient(UUID tenantId, long companyId, long clientId, UpdateClientRequest request)
    {
        //check all on one querry -> WHERE id = ? AND company_id = ? AND tenant_id = ?; in client table
        Client client = repository.findByIdAndCompany_IdAndTenant_Id(clientId, companyId, tenantId)
        .orElseThrow(() -> new EntityNotFoundException("Client not found or invalid company. Or wrong scope."));

        client.setName(request.getName());
        client.setAddress(request.getAddress());
        client.setPostalCode(request.getPostalCode());
        client.setCountry(request.getCountry());
        client.setTel(request.getTel());
        client.setEmail(request.getEmail());

        return repository.save(client);
    }
}
