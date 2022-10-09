package com.example.Reddit_clone.config;

import java.util.Collections;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.example.Reddit_clone.model.User;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@Component
public class JwtToUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {
	
	@Override
	public UsernamePasswordAuthenticationToken convert(Jwt jwt) {
        User user = new User();
        user.setUserId(Long.valueOf(jwt.getSubject()));
        return new UsernamePasswordAuthenticationToken(user, jwt, Collections.emptyList());
	}

}


