package com.visualpathit.account.validator;

import com.visualpathit.account.dto.UserFormDto;
import com.visualpathit.account.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserValidatorTest {
    private UserService userService;
    private UserValidator validator;

    @Before
    public void setUp() {
        userService = mock(UserService.class);
        validator = new UserValidator(userService);
    }

    @Test
    public void supportsRegistrationFormDto() {
        assertTrue(validator.supports(UserFormDto.class));
    }

    @Test
    public void rejectsMissingFieldsWithoutThrowing() {
        UserFormDto form = new UserFormDto();
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(form, "userForm");

        validator.validate(form, errors);

        assertTrue(errors.hasFieldErrors("username"));
        assertTrue(errors.hasFieldErrors("password"));
        assertTrue(errors.hasFieldErrors("passwordConfirm"));
    }

    @Test
    public void acceptsValidUniqueCredentials() {
        UserFormDto form = new UserFormDto();
        form.setUsername("valid-user");
        form.setPassword("secure-pass");
        form.setPasswordConfirm("secure-pass");
        when(userService.findByUsername("valid-user")).thenReturn(null);
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(form, "userForm");

        validator.validate(form, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    public void rejectsDuplicateUsernameAndMismatchedPasswords() {
        UserFormDto form = validForm();
        form.setPasswordConfirm("different-pass");
        when(userService.findByUsername("valid-user")).thenReturn(
                new com.visualpathit.account.model.User());
        BeanPropertyBindingResult errors =
                new BeanPropertyBindingResult(form, "userForm");

        validator.validate(form, errors);

        assertTrue(errors.hasFieldErrors("username"));
        assertTrue(errors.hasFieldErrors("passwordConfirm"));
    }

    @Test
    public void rejectsCredentialValuesOutsideSupportedSizes() {
        UserFormDto form = validForm();
        form.setUsername("short");
        form.setPassword("short");
        BeanPropertyBindingResult errors =
                new BeanPropertyBindingResult(form, "userForm");

        validator.validate(form, errors);

        assertTrue(errors.hasFieldErrors("username"));
        assertTrue(errors.hasFieldErrors("password"));
    }

    @Test
    public void ignoresObjectsItDoesNotSupport() {
        BeanPropertyBindingResult errors =
                new BeanPropertyBindingResult(new Object(), "object");

        validator.validate(new Object(), errors);

        assertFalse(errors.hasErrors());
    }

    private static UserFormDto validForm() {
        UserFormDto form = new UserFormDto();
        form.setUsername("valid-user");
        form.setPassword("secure-pass");
        form.setPasswordConfirm("secure-pass");
        return form;
    }
}
