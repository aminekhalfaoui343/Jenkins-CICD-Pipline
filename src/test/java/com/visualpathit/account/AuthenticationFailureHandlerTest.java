package com.visualpathit.account;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;

public class AuthenticationFailureHandlerTest {
    @Test
    public void redirectsFailedAuthenticationToLoginError() throws Exception {
        MockHttpServletResponse response = new MockHttpServletResponse();

        new AuthenticationFailureHandler().onAuthenticationFailure(
                new MockHttpServletRequest(),
                response,
                new BadCredentialsException("invalid"));

        assertEquals(302, response.getStatus());
        assertEquals("/login?error", response.getRedirectedUrl());
    }
}
