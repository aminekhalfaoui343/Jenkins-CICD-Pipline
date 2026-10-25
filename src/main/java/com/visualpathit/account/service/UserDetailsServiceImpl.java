package com.visualpathit.account.service;

import com.visualpathit.account.model.Role;
import com.visualpathit.account.model.User;
import com.visualpathit.account.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Loads application users and maps their roles to Spring Security authorities.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    /** Repository used to resolve account names. */
    private final UserRepository userRepository;

    /**
     * Creates the user details service.
     *
     * @param repository user repository
     */
    public UserDetailsServiceImpl(final UserRepository repository) {
        this.userRepository = repository;
    }

    /**
     * Loads the requested user's credentials and authorities.
     *
     * @param username account username
     * @return Spring Security user details
     * @throws UsernameNotFoundException if the account does not exist
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(
                    "User not found with username: " + username);
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if (user.getRoles() != null) {
            for (Role role : user.getRoles()) {
                if (role != null && role.getName() != null) {
                    grantedAuthorities.add(
                            new SimpleGrantedAuthority(role.getName()));
                }
            }
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
