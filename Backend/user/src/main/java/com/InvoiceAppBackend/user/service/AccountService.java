package com.InvoiceAppBackend.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.InvoiceAppBackend.user.model.UserAccount;
import com.InvoiceAppBackend.user.repository.AccountRepository;

@Service
public class AccountService 
{
	private final AccountRepository repository;
	
	// Constructor injection
    public AccountService(AccountRepository repository)
    {
        this.repository = repository;
    }
    
	public UserAccount getUserById(Long id) 
	{
		return repository.findById(id).orElse(null);
	}

	 // Add new user
    public UserAccount createUser(UserAccount user)
	{
        return repository.save(user);
    }

	public List<UserAccount> getAllUser()
	{
		return repository.findAll();
	}
}
