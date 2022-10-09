package com.example.Reddit_clone.dataTransferObject;

import lombok.Data;

@Data
public class LoginRequest {
	private String username;
	private String password;
}
