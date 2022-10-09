package com.example.Reddit_clone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Reddit_clone.model.User;
import com.example.Reddit_clone.repository.UserRepository;

import lombok.AllArgsConstructor;

import java.text.MessageFormat;
@Service
@AllArgsConstructor
public class UserManager implements UserDetailsManager{
	
	@Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) {
		
		return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        MessageFormat.format("username {0} not found", username)
                ));
    }

	@Override
	public void createUser(UserDetails user) {
        userRepository.save((User) user);
	}

	@Override
	public void updateUser(UserDetails user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(String username) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean userExists(String username) {
		return userRepository.existsByUsername(username);
	}
	
}
