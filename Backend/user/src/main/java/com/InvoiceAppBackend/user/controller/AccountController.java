package com.InvoiceAppBackend.user.controller;

import org.springframework.web.bind.annotation.*;
import com.InvoiceAppBackend.user.model.UserAccount;
import com.InvoiceAppBackend.user.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class AccountController 
{
	private AccountService service;
	
	public AccountController(AccountService service)
	{
		this.service = service;
	}
	
	// Add new user
    @PostMapping
    public UserAccount createUser(@RequestBody UserAccount user) {
        return service.createUser(user);
    }

	@GetMapping
	public List<UserAccount> getAllUser() {
		return service.getAllUser();
	}
	
}
