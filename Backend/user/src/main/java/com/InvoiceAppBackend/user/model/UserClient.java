package com.InvoiceAppBackend.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "company_client")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserClient
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    @JsonIgnore
    private UserCompany company;

    @Column(nullable = false, length = 128)
    private String name;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(name = "postal_code", nullable = false, length = 20)
    private String postalCode;

    @Column(nullable = false, length = 64)
    private String country;

    @Column(length = 20)
    private String tel;

    @Column(length = 254)
    private String email;
}