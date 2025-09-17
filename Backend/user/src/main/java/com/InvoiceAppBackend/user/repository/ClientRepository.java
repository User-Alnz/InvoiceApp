package com.InvoiceAppBackend.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.InvoiceAppBackend.user.model.UserClient;


public interface ClientRepository extends JpaRepository<UserClient,Long>
{
    // SELECT * FROM company_client WHERE company_id = ?
    List<UserClient> findByCompany_Id(Long companyId);

    // DELETE FROM company_client WHERE company_id = ?
    void deleteByCompany_Id(Long companyId);
}
