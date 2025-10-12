package com.InvoiceAppBackend.company.dto;

import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class UpdateCompanyRequest 
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

    @NotBlank(message = "Legal status is required")
    @Size(max = 64, message = "Legal status must be at most 64 characters")
    private String legalStatus;

    @DecimalMin(value = "0.0", inclusive = true, message = "Share capital must be positive or zero")
    @Digits(integer = 15, fraction = 2, message = "Share capital must be a valid monetary value")
    private double shareCapital;

    @NotBlank(message = "SIREN is required")
    @Pattern(regexp = "\\d{9}", message = "SIREN must be exactly 9 digits")
    private String siren;

    @NotBlank(message = "SIRET is required")
    @Pattern(regexp = "\\d{14}", message = "SIRET must be exactly 14 digits")
    private String siret;

    @NotBlank(message = "RCS is required")
    @Size(max = 64, message = "RCS must be at most 64 characters")
    private String rcs;

    @NotBlank(message = "TVA number is required")
    @Size(max = 32, message = "TVA number must be at most 32 characters")
    private String tvaNumber;

    @Size(max = 255, message = "Website URL must be at most 255 characters")
    private String websiteUrl;    
}
