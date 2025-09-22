package com.InvoiceAppBackend.Auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponsePattern<T> 
{
    private String status;  // "success" or "error"
    private int code;       // HTTP status code
    private T data;         // payload (DTO, list, or message)
}
