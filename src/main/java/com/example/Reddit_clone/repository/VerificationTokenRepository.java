package com.example.Reddit_clone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Reddit_clone.model.VerificationToken;
@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

	Optional<VerificationToken> findByToken(String token);

}
