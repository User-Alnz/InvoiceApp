package com.InvoiceAppBackend.Auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig 
{
    /**
     * Minimal Spring Security configuration:
     * - Permits access to /auth/ without authentication
     * - Secures all other endpoints
     * - Disables CSRF (needed for REST clients / curl)
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception 
    {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers( "/auth", "/auth/register", "/auth/login").permitAll()
                .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Bean //inject BCryptPasswordEncoder from sping security context.
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean// this create DAOProvider
    AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) 
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean //this inject DAO Authentication class and other provider like JwtAuthentication. 
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception 
    {
        return config.getAuthenticationManager();
    }
}
