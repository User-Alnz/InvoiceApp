package com.InvoiceAppBackend.user.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateClientResponse 
{
    private Long id;
    private String name;
}