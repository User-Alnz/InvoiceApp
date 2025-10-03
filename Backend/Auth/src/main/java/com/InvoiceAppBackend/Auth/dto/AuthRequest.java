package com.InvoiceAppBackend.Auth.dto;

import lombok.*;
import jakarta.validation.constraints.*;

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
