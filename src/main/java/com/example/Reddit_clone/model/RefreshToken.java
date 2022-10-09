package com.example.Reddit_clone.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import static javax.persistence.GenerationType.IDENTITY;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long RefreshTokenId;
	@Lob
	private String token;
	private Instant createdDate;
}
