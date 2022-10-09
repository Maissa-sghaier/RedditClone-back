package com.example.Reddit_clone.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Reddit_clone.config.AuthenticationTokenGenerator;
import com.example.Reddit_clone.dataTransferObject.AuthenticationResponse;
import com.example.Reddit_clone.dataTransferObject.LoginRequest;
import com.example.Reddit_clone.dataTransferObject.RefreshTokenRequest;
import com.example.Reddit_clone.dataTransferObject.RegisterRequest;
import com.example.Reddit_clone.exception.SpringRedditException;
import com.example.Reddit_clone.model.NotificationEmail;
import com.example.Reddit_clone.model.User;
import com.example.Reddit_clone.model.VerificationToken;
import com.example.Reddit_clone.repository.UserRepository;
import com.example.Reddit_clone.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
	
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final VerificationTokenRepository verificationTokenRepository;
	private final MailService mailService;
	private final RefreshTokenService refreshTokenService;
	
	@Autowired
    DaoAuthenticationProvider daoAuthenticationProvider;
	@Autowired
    AuthenticationTokenGenerator authenticationTokenGenerator;
	@Autowired
	UserDetailsManager userDetailsManager;
	
	@Transactional
	public void signup(RegisterRequest registerRequest) {
		
		User user = new User();
		user.setUsername(registerRequest.getUsername());
		user.setEmail(registerRequest.getEmail());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setCreatedDate(Instant.now());
		user.setEnabled(false);
		userDetailsManager.createUser(user);
		
		generateVerificationToken(user);
		
		String token = generateVerificationToken(user);
		
		mailService.sendMail(new NotificationEmail("Please activate your account", user.getEmail(), "Thank you for signing up to Spring Reddit, " + "please click on the below url to activate your account : " + "http://localhost:8080/api/auth/accountVerification/" + token));
		
	}
	
	private String generateVerificationToken(User user) {
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);
		verificationTokenRepository.save(verificationToken);
		return token;
	}

	public void verifyAccount(String token) {
		Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
		verificationToken.orElseThrow(()-> new SpringRedditException("Invalid token"));
		fetchUserAndEnable(verificationToken.get());
	}
	
	@Transactional
	private void fetchUserAndEnable(VerificationToken verificationToken) {
		String username = verificationToken.getUser().getUsername();
		User user = userRepository.findByUsername(username).orElseThrow(()-> new SpringRedditException("Username: " + username + " not found"));
		user.setEnabled(true);
		userRepository.save(user);
	}
	
	public AuthenticationResponse login (LoginRequest loginRequest) {
		Authentication authentication = daoAuthenticationProvider.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = authenticationTokenGenerator.createAccessToken(authentication);
		
		return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plus(15, ChronoUnit.MINUTES))
                .username(loginRequest.getUsername())
                .build();
	}

	@Transactional(readOnly = true)
    public User getCurrentUser() {
		Jwt principal = (Jwt) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
		
        return userRepository.findById(Long.valueOf(principal.getSubject()))
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getSubject()));
    }

	public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = authenticationTokenGenerator.generateTokenWithUserName(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plus(15, ChronoUnit.MINUTES))
                .username(refreshTokenRequest.getUsername())
                .build();
    }
	
	public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }


}
