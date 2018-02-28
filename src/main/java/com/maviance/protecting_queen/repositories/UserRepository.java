package com.maviance.protecting_queen.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maviance.protecting_queen.domain.User;

/**
 * This class represents the repository responsible to deal all the operations related to a user with the database
 * @author Rostow
 *
 */
public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);
}
