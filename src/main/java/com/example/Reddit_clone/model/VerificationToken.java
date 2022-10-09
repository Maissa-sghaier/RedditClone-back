package com.example.Reddit_clone.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.Instant;

import static javax.persistence.FetchType.LAZY;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "token")
@AllArgsConstructor
@NoArgsConstructor
public class VerificationToken {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long verificationTokenId;
	private String token;
	private Instant expiryDate;
	@OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
	private User user;
}
