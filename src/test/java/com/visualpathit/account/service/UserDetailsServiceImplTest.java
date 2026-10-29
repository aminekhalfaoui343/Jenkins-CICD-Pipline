package com.visualpathit.account.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.visualpathit.account.model.Role;
import com.visualpathit.account.model.User;
import com.visualpathit.account.repository.UserRepository;

public class UserDetailsServiceImplTest {
    @Test
    public void loadsUserAndNonNullRoleAuthorities() {
        UserRepository repository = mock(UserRepository.class);
        User user = new User();
        user.setUsername("sample-user");
        user.setPassword("encoded");
        Role role = new Role();
        role.setName("ROLE_USER");
        Role unnamedRole = new Role();
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        roles.add(unnamedRole);
        roles.add(null);
        user.setRoles(roles);
        when(repository.findByUsername("sample-user")).thenReturn(user);

        UserDetails details =
                new UserDetailsServiceImpl(repository)
                        .loadUserByUsername("sample-user");

        assertEquals("sample-user", details.getUsername());
        assertEquals("encoded", details.getPassword());
        assertEquals(1, details.getAuthorities().size());
        assertEquals(
                "ROLE_USER",
                details.getAuthorities().iterator().next().getAuthority());
    }

    @Test
    public void loadsUserWithoutRoles() {
        UserRepository repository = mock(UserRepository.class);
        User user = new User();
        user.setUsername("sample-user");
        user.setPassword("encoded");
        when(repository.findByUsername("sample-user")).thenReturn(user);

        UserDetails details =
                new UserDetailsServiceImpl(repository)
                        .loadUserByUsername("sample-user");

        assertTrue(details.getAuthorities().isEmpty());
    }

    @Test
    public void rejectsUnknownUser() {
        UserRepository repository = mock(UserRepository.class);
        when(repository.findByUsername("missing")).thenReturn(null);

        try {
            new UserDetailsServiceImpl(repository)
                    .loadUserByUsername("missing");
            fail("Expected an unknown-user exception");
        } catch (UsernameNotFoundException exception) {
            assertTrue(exception.getMessage().contains("missing"));
        }
    }
}
