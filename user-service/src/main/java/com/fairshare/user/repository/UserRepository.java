package com.fairshare.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fairshare.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	// Find user by email for authentication
	Optional<User> findByEmail(String email);

	// Optionally, add other query methods here
}
