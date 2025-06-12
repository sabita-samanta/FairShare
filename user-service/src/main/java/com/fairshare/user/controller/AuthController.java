package com.fairshare.user.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fairshare.user.model.User;
import com.fairshare.user.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user) {
		String message = authService.registerUser(user);
		return ResponseEntity.ok(message);
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
		String email = credentials.get("email");
		String password = credentials.get("password");

		String token = authService.login(email, password);
		return ResponseEntity.ok(Map.of("token", token));
	}

	@GetMapping("/me")
	public ResponseEntity<String> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.replace("Bearer ", "");
		String email = authService.extractEmailFromToken(token);
		return ResponseEntity.ok("Authenticated user: " + email);
	}
}