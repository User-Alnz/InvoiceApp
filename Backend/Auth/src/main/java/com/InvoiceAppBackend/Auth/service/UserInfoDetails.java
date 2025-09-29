package com.InvoiceAppBackend.Auth.service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
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
    private final UUID tenantId;

    public UserInfoDetails(UserInfo userInfo) 
    {
        this.userEmail = userInfo.getEmail();
        this.password = userInfo.getPassword();
        this.tenantId = userInfo.getTenant().getId();
        this.authorities = List.of(userInfo.getRole().name()) // if enum Role {ADMIN, USER}
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public UUID getTenantId() 
    {
        return tenantId;
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

    /* 
     * Use method below :
     * 
     * @Override
     * public String toString()  
     * 
     * Only for debugging or check if data got well written through UserInfoDetails
     * when calling => public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
     * in ./UserInfoSevice.
     * 
     * + Can use this method if you need to add extra info like getSubscriptionPlan() or whatever. 
     * 
     * log.info will find tostring() method which is usefull. you can use : 
     * 
     * log.info("auth:{}", auth.getPrincipal());
    */
    @Override
    public String toString() 
    {
        return "UserInfoDetails{" +
            "email='" + userEmail + '\'' +
            ", tenantId=" + tenantId +
            ", authorities=" + authorities +
            '}';
    }
}
