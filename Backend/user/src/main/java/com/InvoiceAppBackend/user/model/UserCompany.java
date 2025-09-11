package com.InvoiceAppBackend.user.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_company")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCompany 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserAccount userAccount;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String address;

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
