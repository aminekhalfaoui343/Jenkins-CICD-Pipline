package com.visualpathit.account;

/**
 * Indicates that a requested user account could not be found.
 */
public final class UserNotFoundException extends RuntimeException {
    /**
     * Creates an exception for a missing user.
     *
     * @param message description of the missing user
     */
    public UserNotFoundException(final String message) {
        super(message);
    }
}
