package com.visualpathit.account.service;

/**
 * Authentication operations for application users.
 */
public interface SecurityService {
    /**
     * Finds the currently authenticated username.
     *
     * @return logged-in username, or {@code null} if unavailable
     */
    String findLoggedInUsername();

    /**
     * Authenticates a user and sets the current security context.
     *
     * @param username account username
     * @param password raw password
     * @return {@code true} if authentication succeeds
     */
    boolean autologin(String username, String password);
}
