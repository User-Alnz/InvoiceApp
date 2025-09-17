package com.InvoiceAppBackend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.InvoiceAppBackend.user.model.UserCompany;

public interface CompanyRepository extends JpaRepository<UserCompany, Long>
{
    // SELECT * FROM user_client WHERE user_id = ?
    long countByUserAccount_Id(Long userId);
}
