package com.InvoiceAppBackend.Auth.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest 
{
    private String email;
    private String password;
}
