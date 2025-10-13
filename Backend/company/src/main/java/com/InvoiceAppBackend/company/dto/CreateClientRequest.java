package com.InvoiceAppBackend.company.dto;

import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class CreateClientRequest 
{
    @NotBlank(message = "Company name is required")
    @Size(max = 128, message = "Name must be at most 128 characters")
    private String name;

    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address must be at most 255 characters")
    private String address;

    @NotBlank(message = "Postal code is required")
    @Size(min = 5, max = 20, message = "Postal code must be at most 20 characters at least 5 charaters")
    private String postalCode;

    @NotBlank(message = "Country is required")
    @Size(max = 64, message = "Country must be at most 64 characters")
    private String country;

    @Size(max = 20, message = "Telephone must be at most 20 characters")
    private String tel;

    @Email(message = "Invalid email format")
    @Size(max = 254, message = "Email must be at most 254 characters")
    private String email;
}
