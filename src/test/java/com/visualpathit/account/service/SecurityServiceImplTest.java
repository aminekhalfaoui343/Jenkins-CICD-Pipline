package com.visualpathit.account.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.After;
import org.junit.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class SecurityServiceImplTest {
    @After
    public void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void returnsNullWhenNoAuthenticationExists() {
        AuthenticationManager authenticationManager =
                mock(AuthenticationManager.class);
        UserDetailsService userDetailsService =
                mock(UserDetailsService.class);

        assertNull(new SecurityServiceImpl(
                authenticationManager, userDetailsService)
                .findLoggedInUsername());
    }

    @Test
    public void returnsUsernameFromAuthenticationDetails() {
        UserDetails details = User.withUsername("sample")
                .password("encoded")
                .authorities(Collections.emptyList())
                .build();
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        "principal", "credentials");
        authentication.setDetails(details);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        SecurityServiceImpl service = new SecurityServiceImpl(
                mock(AuthenticationManager.class),
                mock(UserDetailsService.class));

        assertEquals("sample", service.findLoggedInUsername());
    }

    @Test
    public void authenticatesAndStoresUserDetailsInContext() {
        AuthenticationManager authenticationManager =
                mock(AuthenticationManager.class);
        UserDetailsService userDetailsService =
                mock(UserDetailsService.class);
        UserDetails details = User.withUsername("sample")
                .password("encoded")
                .authorities("ROLE_USER")
                .build();
        when(userDetailsService.loadUserByUsername("sample"))
                .thenReturn(details);
        when(authenticationManager.authenticate(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        boolean authenticated = new SecurityServiceImpl(
                authenticationManager, userDetailsService)
                .autologin("sample", "password");

        assertTrue(authenticated);
        assertEquals(
                "sample",
                SecurityContextHolder.getContext()
                        .getAuthentication().getName());
        verify(authenticationManager).authenticate(
                any(UsernamePasswordAuthenticationToken.class));
    }
}
