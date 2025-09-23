package com.InvoiceAppBackend.Auth.service;

import org.springframework.stereotype.Service;

import com.InvoiceAppBackend.Auth.model.UserInfo;
import com.InvoiceAppBackend.Auth.repository.UserInfoRepository;
import com.InvoiceAppBackend.Auth.dto.RequestCreateUser;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.User; //security adapter object.

@Service
public class UserInfoService implements UserDetailsService
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

    @Override //override to create bridge with DB and build object from model UserInfo.
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException 
    {
        //this write User Object from spring security. from unique user email.
        return repository.findByEmail(email)
            .map(user -> User.withUsername(user.getEmail())
                             .password(user.getPassword())  //get encoded password from DB
                             .roles(user.getRole().name())  //get role from enum.name(() to get it as string
                             .build()) 
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }
}
