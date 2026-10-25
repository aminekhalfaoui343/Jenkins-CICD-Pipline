package com.visualpathit.account;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public final class GlobalExceptionHandler {

    /**
     * Maps a missing user exception to the application not-found page.
     *
     * @param exception exception carrying the missing account details
     * @param request current servlet request
     * @return forwarding route for the not-found page
     */
    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException(
            final UserNotFoundException exception,
            final HttpServletRequest request) {
        request.setAttribute("errorMessage", exception.getMessage());
        return "forward:/WEB-INF/views/error/404.jsp";
    }

    /**
     * Maps invalid credentials to the application error page.
     *
     * @param exception authentication failure
     * @param request current servlet request
     * @return forwarding route for the error page
     */
    @ExceptionHandler(BadCredentialsException.class)
    public String handleBadCredentialsException(
            final BadCredentialsException exception,
            final HttpServletRequest request) {
        request.setAttribute("errorMessage", "Invalid username or password.");
        return "forward:/WEB-INF/views/error/500.jsp";
    }

    /**
     * Maps uncaught application exceptions to the generic error page.
     *
     * @param exception unexpected application exception
     * @param request current servlet request
     * @return forwarding route for the generic error page
     */
    @ExceptionHandler(Exception.class)
    public String handleGenericException(
            final Exception exception,
            final HttpServletRequest request) {
        request.setAttribute(
                "errorMessage",
                "An unexpected error occurred. Please try again later.");
        return "forward:/WEB-INF/views/error/500.jsp";
    }
}
