package com.visualpathit.account.service;

import java.util.List;

import com.visualpathit.account.model.User;

/**
 * Business operations for user accounts.
 */
public interface UserService {
    /**
     * Saves a user, encoding its raw password when present.
     *
     * @param user user to save
     */
    void save(User user);

    /**
     * Finds a user by account username.
     *
     * @param username username to find
     * @return matching user or {@code null}
     */
    User findByUsername(String username);

    /**
     * Finds a user by persistent identifier.
     *
     * @param id user identifier
     * @return matching user or {@code null}
     */
    User findById(long id);

    /**
     * Retrieves all users.
     *
     * @return all persisted users
     */
    List<User> getList();
}
