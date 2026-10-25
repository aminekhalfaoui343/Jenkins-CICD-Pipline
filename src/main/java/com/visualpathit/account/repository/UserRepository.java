package com.visualpathit.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.visualpathit.account.model.User;

/**
 * Persistence operations for user entities.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a user by account username.
     *
     * @param username username to look up
     * @return matching user, or {@code null} if no account matches
     */
    User findByUsername(String username);
}
