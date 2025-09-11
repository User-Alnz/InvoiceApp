package com.InvoiceAppBackend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.InvoiceAppBackend.user.model.UserCompany;

public interface CompanyRepository extends JpaRepository<UserCompany, Long>{}
