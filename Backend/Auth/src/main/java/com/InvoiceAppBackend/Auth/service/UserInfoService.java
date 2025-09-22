package com.InvoiceAppBackend.Auth.service;

import org.springframework.stereotype.Service;

import com.InvoiceAppBackend.Auth.model.UserInfo;
import com.InvoiceAppBackend.Auth.repository.UserInfoRepository;
import com.InvoiceAppBackend.Auth.dto.RequestCreateUser;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserInfoService 
{
    private UserInfoRepository repository;
    private PasswordEncoder encoder;

    UserInfoService(UserInfoRepository repository,PasswordEncoder encoder)
    {
        this.repository = repository;
        this.encoder = encoder;
    }

    public String addUser(RequestCreateUser request)
    {   
        UserInfo user = new UserInfo();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        //hash password from PasswordEncoder. Defined in SecurityConfig class.
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        repository.save(user);

        return "User created";
    }
}
