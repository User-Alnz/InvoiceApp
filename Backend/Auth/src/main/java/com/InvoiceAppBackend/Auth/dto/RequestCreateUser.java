package com.InvoiceAppBackend.Auth.dto;


import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class RequestCreateUser 
{
    @NotBlank
    @Size(min=2, max=50,message = "firstname must be at most 50 and at least 2 characters")
    private String firstName;

    @NotBlank
    @Size(min=2, max=50,message = "lastname must be at most 50 and at least 2 characters")
    private String lastName;

    @Email(message = "Invalid email format")
    @Size(max = 254, message = "Email must be at most 254 characters")
    private String email;


    @NotBlank
    @Size(min=8, max=128,message = "password is minimum 8 and max 128 characters length")
    private String password;


}
