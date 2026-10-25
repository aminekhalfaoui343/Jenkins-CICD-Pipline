package com.visualpathit.account.service;

import com.visualpathit.account.model.User;
import com.visualpathit.account.repository.RoleRepository;
import com.visualpathit.account.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * Implements user persistence and password encoding operations.
 */
@Service
public final class UserServiceImpl implements UserService {
    /** Repository used to store and retrieve users. */
    private final UserRepository userRepository;
    /** Repository used to load role assignments. */
    private final RoleRepository roleRepository;
    /** Encoder used to hash raw passwords. */
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Creates the user service.
     *
     * @param userRepositoryDependency user repository
     * @param roleRepositoryDependency role repository
     * @param passwordEncoder password encoder
     */
    @Autowired
    public UserServiceImpl(
            final UserRepository userRepositoryDependency,
            final RoleRepository roleRepositoryDependency,
            final BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = Objects.requireNonNull(userRepositoryDependency);
        this.roleRepository = Objects.requireNonNull(roleRepositoryDependency);
        this.bCryptPasswordEncoder = Objects.requireNonNull(passwordEncoder);
    }

    /**
     * Encodes a supplied password and persists the user with current roles.
     *
     * @param user user to persist
     */
    @Override
    public void save(final User user) {
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }

    /**
     * Finds an account by username.
     *
     * @param username username to find
     * @return matching user, or {@code null} if no match exists
     */
    @Override
    public User findByUsername(final String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Returns all persisted users.
     *
     * @return all users
     */
    @Override
    public List<User> getList() {
        return userRepository.findAll();
    }

    /**
     * Finds a user by its persistent identifier.
     *
     * @param id user identifier
     * @return matching user, or {@code null} if no match exists
     */
    @Override
    public User findById(final long id) {
        return userRepository.findById(id).orElse(null);
    }
}
