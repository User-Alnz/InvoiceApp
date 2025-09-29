package com.InvoiceAppBackend.Auth.service;

import org.springframework.stereotype.Service;

import com.InvoiceAppBackend.Auth.model.Role;
import com.InvoiceAppBackend.Auth.model.Tenant;
import com.InvoiceAppBackend.Auth.model.UserInfo;
import com.InvoiceAppBackend.Auth.repository.UserInfoRepository;
import com.InvoiceAppBackend.Auth.dto.RequestCreateUser;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    public String createUser(RequestCreateUser request)
    {   
        if(repository.findByEmail(request.getEmail()).isPresent())
        throw new IllegalArgumentException("Email already in used");

        Tenant tenant = new Tenant();//object model generate UUID key under the hood later by Hibernate.

        UserInfo user = new UserInfo();
        user.setTenant(tenant); //handled by Hibernate in UserInfo model => cascade = CascadeType.PERSIST. avoid double querry.
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword())); //hash password from PasswordEncoder. Defined in SecurityConfig class.
        user.setRole(Role.ADMIN);

        repository.save(user); // Hibernate insert tenant, first next user.

        return "User created";
    }

    @Override //override to create bridge with DB and build object from model UserInfo.
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException 
    {
        //UserInfoDetails insteadsof User as adapter for authenticate() from DaoProvider
        return repository.findByEmail(email)
            .map(UserInfoDetails::new)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }
}
