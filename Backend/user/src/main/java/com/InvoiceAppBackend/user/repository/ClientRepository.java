package com.InvoiceAppBackend.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.InvoiceAppBackend.user.model.UserClient;


public interface ClientRepository extends JpaRepository<UserClient,Long>
{
    List<UserClient> findByCompany_Id(Long companyId);
}
