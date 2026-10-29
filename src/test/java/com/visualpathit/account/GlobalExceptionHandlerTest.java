package com.visualpathit.account;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;

public class GlobalExceptionHandlerTest {
    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    public void mapsMissingUserToNotFoundPage() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        String view = handler.handleUserNotFoundException(
                new UserNotFoundException("missing"), request);

        assertEquals("forward:/WEB-INF/views/error/404.jsp", view);
        assertEquals("missing", request.getAttribute("errorMessage"));
    }

    @Test
    public void mapsBadCredentialsToGenericErrorPage() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        String view = handler.handleBadCredentialsException(
                new BadCredentialsException("invalid"), request);

        assertEquals("forward:/WEB-INF/views/error/500.jsp", view);
        assertEquals(
                "Invalid username or password.",
                request.getAttribute("errorMessage"));
    }

    @Test
    public void mapsUnexpectedExceptionToGenericErrorPage() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        String view = handler.handleGenericException(
                new IllegalStateException("failure"), request);

        assertEquals("forward:/WEB-INF/views/error/500.jsp", view);
        assertEquals(
                "An unexpected error occurred. Please try again later.",
                request.getAttribute("errorMessage"));
    }
}
