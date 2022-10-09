package com.example.Reddit_clone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Reddit_clone.model.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{
	
    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
}
