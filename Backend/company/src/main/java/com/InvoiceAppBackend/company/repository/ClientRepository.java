package com.InvoiceAppBackend.company.repository;


import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.InvoiceAppBackend.company.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> 
{
    Page<Client> findByTenantIdAndCompanyId(UUID tenantId, long companyId, Pageable pagination);
}
