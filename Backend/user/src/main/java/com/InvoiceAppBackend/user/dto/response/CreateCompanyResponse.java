package com.InvoiceAppBackend.user.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCompanyResponse 
{
    private Long id;
    private String name;
}
