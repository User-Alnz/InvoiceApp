package com.InvoiceAppBackend.Auth.model;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tenants")
public class Tenant 
{
    @Id
    @GeneratedValue
    private UUID id;

    //Can be extend later in developpement 
}
