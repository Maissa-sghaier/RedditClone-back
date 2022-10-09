package com.example.Reddit_clone.config;

import java.text.MessageFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.Reddit_clone.dataTransferObject.AuthenticationResponse;
import com.example.Reddit_clone.model.RefreshToken;
import com.example.Reddit_clone.model.User;
import com.example.Reddit_clone.repository.RefreshTokenRepository;

@Component
public class AuthenticationTokenGenerator {
	@Autowired
    JwtEncoder accessTokenEncoder;
	
	@Autowired
    @Qualifier("jwtRefreshTokenEncoder")
    JwtEncoder refreshTokenEncoder;
	
	@Autowired
	RefreshTokenRepository refreshTokenRepository;
	
	public String createAccessToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Instant now = Instant.now();

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("myApp")
                .issuedAt(now)
                .expiresAt(Instant.now().plus(15, ChronoUnit.MINUTES))
                .subject(user.getUserId().toString())
                .build();

        return accessTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }
	
	
	public String generateTokenWithUserName(String username) {
		
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("myApp")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(15, ChronoUnit.MINUTES))
                .subject(username)
                .build();

        return accessTokenEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}
