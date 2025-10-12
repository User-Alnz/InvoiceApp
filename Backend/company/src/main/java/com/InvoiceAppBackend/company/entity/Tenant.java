package com.InvoiceAppBackend.company.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table( name = "tenant" )
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tenant 
{
    @Id
    private UUID id;

    @Column(name ="created_at")
    private LocalDateTime createdAt;
}
