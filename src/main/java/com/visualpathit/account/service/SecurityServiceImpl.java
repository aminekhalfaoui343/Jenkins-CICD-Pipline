package com.visualpathit.account.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public final class SecurityServiceImpl implements SecurityService {
    /** Performs Spring Security authentication. */
    private final AuthenticationManager authenticationManager;

    /** Loads user details for the submitted username. */
    private final UserDetailsService userDetailsService;

    /** Logger for automatic login outcomes. */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(SecurityServiceImpl.class);

    /**
     * Creates the security service.
     *
     * @param authenticationManagerDependency authentication manager
     * @param userDetailsServiceDependency user details service
     */
    public SecurityServiceImpl(
            final AuthenticationManager authenticationManagerDependency,
            final UserDetailsService userDetailsServiceDependency) {
        this.authenticationManager = authenticationManagerDependency;
        this.userDetailsService = userDetailsServiceDependency;
    }

    /**
     * Finds the username associated with the current authentication details.
     *
     * @return logged-in username, or {@code null} when no user details exist
     */
    @Override
    public String findLoggedInUsername() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object userDetails = authentication.getDetails();
        if (userDetails instanceof UserDetails) {
            return ((UserDetails) userDetails).getUsername();
        }
        return null;
    }

    /**
     * Authenticates the supplied credentials and stores the resulting context.
     *
     * @param username account username
     * @param password raw account password
     * @return {@code true} when authentication succeeds
     */
    @Override
    public boolean autologin(final String username, final String password) {
        UserDetails userDetails =
                userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userDetails, password, userDetails.getAuthorities());

        authenticationManager.authenticate(authenticationToken);

        if (authenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext()
                    .setAuthentication(authenticationToken);
            LOGGER.debug("Auto login {} successfully!", username);
            return true;
        }
        LOGGER.debug("Auto login {} failed!", username);
        return false;
    }
}
