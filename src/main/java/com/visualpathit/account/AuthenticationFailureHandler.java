package com.visualpathit.account;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;

/**
 * Redirects failed authentication attempts to the login page.
 */
public final class AuthenticationFailureHandler
        extends SimpleUrlAuthenticationFailureHandler {

    /**
     * Redirects the response after an authentication failure.
     *
     * @param request current servlet request
     * @param response servlet response to redirect
     * @param exception authentication failure
     * @throws IOException if writing the redirect fails
     */
    @Override
    public void onAuthenticationFailure(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final AuthenticationException exception) throws IOException {
        // Redirect to custom error page
        getRedirectStrategy().sendRedirect(request, response, "/login?error");
    }
}
