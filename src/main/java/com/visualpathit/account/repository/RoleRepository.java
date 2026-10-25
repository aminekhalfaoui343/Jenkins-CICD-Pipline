package com.visualpathit.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.visualpathit.account.model.Role;

/**
 * Persistence operations for security roles.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
}
