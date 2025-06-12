package com.fairshare.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fairshare.user.model.User;
import com.fairshare.user.repository.UserRepository;
import com.fairshare.user.security.JwtUtil;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtUtil jwtUtil;

	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	// ✅ Register a new user
	public String registerUser(User user) {
		Optional<User> existing = userRepository.findByEmail(user.getEmail());
		if (existing.isPresent()) {
			throw new RuntimeException("User already exists with this email");
		}

		// Hash password and save user
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return "User registered successfully!";
	}

	// ✅ Login user and return JWT
	public String login(String email, String rawPassword) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
			throw new RuntimeException("Invalid credentials");
		}

		return jwtUtil.generateToken(email);
	}

	// ✅ Extract email from token
	public String extractEmailFromToken(String token) {
		return jwtUtil.extractEmail(token);
	}

	// ✅ (Optional) Validate password manually
	public boolean validatePassword(String rawPassword, String hashedPassword) {
		return passwordEncoder.matches(rawPassword, hashedPassword);
	}
}
