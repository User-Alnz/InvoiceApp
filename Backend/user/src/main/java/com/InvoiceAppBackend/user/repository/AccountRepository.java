package com.InvoiceAppBackend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.InvoiceAppBackend.user.model.UserAccount;

public interface AccountRepository extends JpaRepository<UserAccount, Long>{}
