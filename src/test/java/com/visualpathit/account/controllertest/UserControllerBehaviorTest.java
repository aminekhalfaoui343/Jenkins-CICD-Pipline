package com.visualpathit.account.controllerTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;

import com.visualpathit.account.controller.UserController;
import com.visualpathit.account.dto.UserFormDto;
import com.visualpathit.account.model.User;
import com.visualpathit.account.service.SecurityService;
import com.visualpathit.account.service.UserService;
import com.visualpathit.account.utils.MemcachedUtils;
import com.visualpathit.account.validator.UserValidator;

public class UserControllerBehaviorTest {
    private UserService userService;
    private SecurityService securityService;
    private UserValidator userValidator;
    private MemcachedUtils memcachedUtils;
    private UserController controller;

    @Before
    public void setUp() {
        userService = mock(UserService.class);
        securityService = mock(SecurityService.class);
        userValidator = mock(UserValidator.class);
        memcachedUtils = mock(MemcachedUtils.class);
        controller = new UserController(
                userService, securityService, userValidator, memcachedUtils);
    }

    @Test
    public void listsUsersAsSafeViewDtos() {
        User user = user("sample");
        when(userService.getList()).thenReturn(List.of(user));
        Model model = mock(Model.class);

        assertEquals("userList", controller.getAllUsers(model));

        verify(model).addAttribute(
                org.mockito.ArgumentMatchers.eq("users"),
                org.mockito.ArgumentMatchers.argThat(
                        users -> users instanceof List<?> list
                                && list.size() == 1
                                && list.get(0).getClass().getSimpleName()
                                        .equals("UserViewDto")));
    }

    @Test
    public void loadsUserFromCacheWhenAvailable() {
        User user = user("sample");
        when(memcachedUtils.memcachedGetData("7")).thenReturn(user);
        Model model = mock(Model.class);

        assertEquals("user", controller.getOneUser("7", model));

        verify(model).addAttribute("Result", "Data is From Cache");
        verify(userService, never()).findById(7L);
    }

    @Test
    public void loadsUserFromDatabaseAndCachesItAfterCacheMiss() {
        User user = user("sample");
        when(userService.findById(7L)).thenReturn(user);
        when(memcachedUtils.memcachedSetData(user, "7"))
                .thenReturn("cached");
        Model model = mock(Model.class);

        assertEquals("user", controller.getOneUser("7", model));

        verify(model).addAttribute("Result", "cached");
        verify(memcachedUtils).memcachedSetData(user, "7");
    }

    @Test
    public void returnsNotFoundForMissingOrMalformedUserId() {
        Model model = mock(Model.class);
        when(userService.findById(404L)).thenReturn(null);

        assertEquals("error/404", controller.getOneUser("404", model));
        verify(model).addAttribute("error", "User not found");
        assertEquals("error/404", controller.getOneUser("invalid", model));
        verify(model).addAttribute("error", "Unable to load user");
    }

    @Test
    public void handlesRegistrationAndAuthenticationOutcomes() {
        UserFormDto form = new UserFormDto();
        form.setUsername("sample");
        form.setPassword("password");
        form.setPasswordConfirm("password");
        BeanPropertyBindingResult errors =
                new BeanPropertyBindingResult(form, "userForm");
        Model model = mock(Model.class);
        when(securityService.autologin("sample", "password"))
                .thenReturn(true, false);

        assertEquals(
                "redirect:/welcome",
                controller.registration(form, errors, model));
        assertEquals(
                "redirect:/login?error",
                controller.registration(form, errors, model));

        verify(userValidator, org.mockito.Mockito.times(2))
                .validate(form, errors);
    }

    @Test
    public void returnsFormAgainWhenRegistrationValidationFails() {
        UserFormDto form = new UserFormDto();
        BeanPropertyBindingResult errors =
                new BeanPropertyBindingResult(form, "userForm");
        errors.reject("invalid");

        assertEquals(
                "registration",
                controller.registration(form, errors, mock(Model.class)));

        verify(userService, never()).save(any(User.class));
    }

    @Test
    public void handlesLoginMessagesAndAuthenticationFailure() {
        Model model = mock(Model.class);

        assertEquals("login", controller.login(model, "error", "logout"));
        verify(model).addAttribute(
                "error", "Your username and password is invalid.");
        verify(model).addAttribute(
                "message", "You have been logged out successfully.");

        UserFormDto form = new UserFormDto();
        form.setUsername("sample");
        form.setPassword("wrong");
        when(securityService.autologin("sample", "wrong")).thenReturn(false);
        assertEquals("login", controller.loginPost(form, model));
        verify(model, org.mockito.Mockito.times(2)).addAttribute(
                "error", "Your username and password is invalid.");
    }

    @Test
    public void updatesExistingUserProfileAndHandlesMissingUser() {
        User user = user("sample");
        when(userService.findByUsername("sample")).thenReturn(user);
        UserFormDto form = new UserFormDto();
        form.setUsername("sample");
        form.setUserEmail("updated@example.com");
        assertEquals("welcome", controller.userUpdateProfile("sample", form));
        assertEquals("updated@example.com", user.getUserEmail());
        verify(userService).save(user);

        when(userService.findByUsername("missing")).thenReturn(null);
        assertEquals(
                "error/404",
                controller.userUpdateProfile("missing", form));
    }

    @Test
    public void displaysProfileFormForExistingUser() {
        User user = user("sample");
        when(userService.findByUsername("sample")).thenReturn(user);
        Model model = mock(Model.class);

        assertEquals("userUpdate", controller.userUpdate("sample", model));

        verify(model).addAttribute(
                org.mockito.ArgumentMatchers.eq("user"),
                org.mockito.ArgumentMatchers.any(UserFormDto.class));
    }

    private static User user(final String username) {
        User user = new User();
        user.setUsername(username);
        return user;
    }
}
