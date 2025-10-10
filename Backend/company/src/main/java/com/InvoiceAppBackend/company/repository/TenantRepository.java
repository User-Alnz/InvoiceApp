package com.InvoiceAppBackend.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.InvoiceAppBackend.company.entity.Tenant;

import java.util.UUID;

public interface TenantRepository extends JpaRepository<Tenant, UUID> {}