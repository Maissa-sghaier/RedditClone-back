package com.example.Reddit_clone.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Reddit_clone.exception.SpringRedditException;
import com.example.Reddit_clone.model.RefreshToken;
import com.example.Reddit_clone.repository.RefreshTokenRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {
	
	private final RefreshTokenRepository refreshTokenRepository;
	
	public RefreshToken generateRefreshToken() {
		
	        RefreshToken refreshToken = new RefreshToken();
	        refreshToken.setToken(UUID.randomUUID().toString());
	        refreshToken.setCreatedDate(Instant.now());
	        return refreshTokenRepository.save(refreshToken);
	        
	    }
    void validateRefreshToken(String token) {
        refreshTokenRepository.findByToken(token).orElseThrow(() -> new SpringRedditException("Invalid refresh Token"));
    }

    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
