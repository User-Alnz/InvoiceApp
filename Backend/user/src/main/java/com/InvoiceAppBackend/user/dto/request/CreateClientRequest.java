package com.InvoiceAppBackend.user.dto.request;

import lombok.Data;

@Data
public class CreateClientRequest
{
    private String name;
    private String address;
    private String postalCode;
    private String country;
    private String tel;
    private String email;
    private Long companyId;
}
