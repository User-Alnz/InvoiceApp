package com.InvoiceAppBackend.user.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.InvoiceAppBackend.user.model.UserClient;

public interface ClientRepository extends JpaRepository<UserClient,Long>{}
