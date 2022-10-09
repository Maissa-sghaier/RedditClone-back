package com.example.Reddit_clone.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data	
@Entity
@RequiredArgsConstructor
public class User implements UserDetails{
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long userId;
	@NotBlank(message = "Username is required")
	private String username;
	@NotBlank(message = "Password is required")
	private String password;
	@Email
	@NotEmpty(message = "Email is required")
	private String email;
	private boolean enabled;
	private Instant createdDate;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList();
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
}
