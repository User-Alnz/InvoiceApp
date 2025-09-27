package com.InvoiceAppBackend.Auth.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.InvoiceAppBackend.Auth.model.UserInfo;

public class UserInfoDetails implements UserDetails
{
    
    private String userEmail;
    private String password;
    private List<GrantedAuthority> authorities;

    public UserInfoDetails(UserInfo userInfo) 
    {
        this.userEmail = userInfo.getEmail();
        this.password = userInfo.getPassword();
        this.authorities = List.of(userInfo.getRole().name()) // if enum Role {ADMIN, USER}
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    
    @Override
    public String getUsername() 
    {
        return userEmail;
    }

    @Override
    public String getPassword() 
    {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() 
    {
        return true; // customize if you want to disable expired accounts
    }

    @Override
    public boolean isAccountNonLocked() 
    {
        return true; // customize if you want to handle locked accounts
    }

    @Override
    public boolean isCredentialsNonExpired() 
    {
        return true; // customize if you want password expiry
    }

    @Override
    public boolean isEnabled() 
    {
        return true; // customize if you want soft-delete/disabled users
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() 
    {
        return authorities;
    }
}
