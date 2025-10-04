package com.InvoiceAppBackend.Auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest 
{
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
