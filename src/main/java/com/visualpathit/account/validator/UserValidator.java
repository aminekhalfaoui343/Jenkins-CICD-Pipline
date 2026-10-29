package com.visualpathit.account.validator;

import com.visualpathit.account.dto.UserFormDto;
import com.visualpathit.account.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validates registration form data and checks account name uniqueness.
 */
@Component
public final class UserValidator implements Validator {
    /** Minimum username size accepted for registration. */
    private static final int MIN_USERNAME_LENGTH = 6;
    /** Maximum username size accepted for registration. */
    private static final int MAX_USERNAME_LENGTH = 32;
    /** Minimum password size accepted for registration. */
    private static final int MIN_PASSWORD_LENGTH = 8;
    /** Maximum password size accepted for registration. */
    private static final int MAX_PASSWORD_LENGTH = 32;
    /** Registration form field that carries the account name. */
    private static final String USERNAME_FIELD = "username";
    /** Validation code used when a required field is blank. */
    private static final String NOT_EMPTY_CONSTRAINT = "NotEmpty";

    /** User lookup service used for duplicate-name validation. */
    private final UserService userService;

    /**
     * Creates a validator using the user lookup service.
     *
     * @param service user lookup service
     */
    public UserValidator(final UserService service) {
        this.userService = service;
    }

    /**
     * Checks whether this validator supports the supplied class.
     *
     * @param supportedClass class to validate
     * @return {@code true} for {@link UserFormDto}
     */
    @Override
    public boolean supports(final Class<?> supportedClass) {
        return UserFormDto.class.equals(supportedClass);
    }

    /**
     * Validates required fields, lengths, uniqueness, and password matching.
     *
     * @param target object being validated
     * @param errors validation errors to populate
     */
    @Override
    public void validate(final Object target, final Errors errors) {
        if (!(target instanceof UserFormDto user)) {
            return;
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, USERNAME_FIELD, NOT_EMPTY_CONSTRAINT);
        if (StringUtils.hasText(user.getUsername())) {
            int usernameLength = user.getUsername().length();
            if (usernameLength < MIN_USERNAME_LENGTH
                    || usernameLength > MAX_USERNAME_LENGTH) {
                errors.rejectValue(
                        USERNAME_FIELD, "Size.userForm.username");
            }
            if (userService.findByUsername(user.getUsername()) != null) {
                errors.rejectValue(
                        USERNAME_FIELD, "Duplicate.userForm.username");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "password", NOT_EMPTY_CONSTRAINT);
        if (StringUtils.hasText(user.getPassword())) {
            int passwordLength = user.getPassword().length();
            if (passwordLength < MIN_PASSWORD_LENGTH
                    || passwordLength > MAX_PASSWORD_LENGTH) {
                errors.rejectValue("password", "Size.userForm.password");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "passwordConfirm", NOT_EMPTY_CONSTRAINT);
        if (user.getPasswordConfirm() != null
                && !user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue(
                    "passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }
}
