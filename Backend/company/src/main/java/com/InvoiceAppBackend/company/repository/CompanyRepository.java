package com.InvoiceAppBackend.company.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.InvoiceAppBackend.company.entity.Company;


public interface CompanyRepository extends JpaRepository<Company, Long>
{
    Optional<Company> findByTenantId(UUID tenantId);
}
