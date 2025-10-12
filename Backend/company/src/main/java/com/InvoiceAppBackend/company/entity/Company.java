package com.InvoiceAppBackend.company.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table( name = "company" )
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company 
{
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn( name = "tenant_id", nullable = false)
    private Tenant tenant;

    @Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String address;

	@Column(name="postal_code", nullable = false)
	private String postalCode;

	@Column(nullable = false)
	private String country;

	private String tel;

	private String email;

	@Column(name="legal_status", nullable = false)
	private String legalStatus;

	@Column(name="share_capital", nullable = false)
	private double shareCapital;	

	@Column(length = 9, nullable = false)
    private String siren;

    @Column(length = 14, nullable = false)
    private String siret;

    @Column(nullable = false)
    private String rcs;

    @Column(name = "tva_number", nullable = false)
    private String tvaNumber;

    @Column(name = "website_url")
    private String websiteUrl; 
}
