package com.test.oauth2.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.oauth2.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

}
