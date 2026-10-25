package com.visualpathit.account.controller;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.visualpathit.account.dto.UserFormDto;
import com.visualpathit.account.dto.UserViewDto;
import com.visualpathit.account.model.User;
import com.visualpathit.account.service.SecurityService;
import com.visualpathit.account.service.UserService;
import com.visualpathit.account.utils.MemcachedUtils;
import com.visualpathit.account.validator.UserValidator;

@Controller
public final class UserController {
    /** Model attribute used to render login and lookup errors. */
    private static final String ERROR_ATTRIBUTE = "error";
    /** View rendered when a user cannot be found. */
    private static final String NOT_FOUND_VIEW = "error/404";

    /** User persistence service. */
    private final UserService userService;

    /** Service responsible for authentication and session setup. */
    private final SecurityService securityService;

    /** Registration validator. */
    private final UserValidator userValidator;

    /**
     * Creates the user controller.
     *
     * @param userServiceDependency user persistence service
     * @param securityServiceDependency authentication service
     * @param userValidatorDependency registration validator
     */
    public UserController(
            final UserService userServiceDependency,
            final SecurityService securityServiceDependency,
            final UserValidator userValidatorDependency) {
        this.userService = userServiceDependency;
        this.securityService = securityServiceDependency;
        this.userValidator = userValidatorDependency;
    }

    /**
     * Displays the registration form.
     *
     * @param model view model
     * @return registration view
     */
    @GetMapping("/registration")
    public String registration(final Model model) {
        model.addAttribute("userForm", new UserFormDto());
        return "registration";
    }

    /**
     * Validates and saves a new user, then authenticates the new account.
     *
     * @param userForm registration form data
     * @param bindingResult validation results
     * @param model view model
     * @return registration view or redirect after registration
     */
    @PostMapping("/registration")
    public String registration(
            @ModelAttribute("userForm") @Valid final UserFormDto userForm,
            final BindingResult bindingResult,
            final Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        User user = userForm.toNewUser();
        userService.save(user);
        boolean loginSuccessful = securityService.autologin(
                user.getUsername(), userForm.getPasswordConfirm());
        if (!loginSuccessful) {
            return "redirect:/login?error";
        }

        return "redirect:/welcome";
    }

    /**
     * Displays login status messages.
     *
     * @param model view model
     * @param error login error request parameter
     * @param logout logout request parameter
     * @return login view
     */
    @GetMapping({"/", "/login"})
    public String login(
            final Model model,
            @RequestParam(value = ERROR_ATTRIBUTE, required = false)
            final String error,
            @RequestParam(value = "logout", required = false)
            final String logout) {
        if (error != null) {
            model.addAttribute(ERROR_ATTRIBUTE,
                    "Your username and password is invalid.");
        }
        if (logout != null) {
            model.addAttribute("message",
                    "You have been logged out successfully.");
        }
        return "login";
    }

    /**
     * Authenticates the submitted login form.
     *
     * @param user login form data
     * @param model view model
     * @return login view or welcome redirect
     */
    @PostMapping("/login")
    public String loginPost(
            @ModelAttribute("user") final UserFormDto user,
            final Model model) {
        boolean loginSuccessful = securityService.autologin(
                user.getUsername(), user.getPassword());
        if (!loginSuccessful) {
            model.addAttribute(ERROR_ATTRIBUTE,
                    "Your username and password is invalid.");
            return "login";
        }
        return "redirect:/welcome";
    }

    /**
     * Displays the welcome page.
     *
     * @return welcome view
     */
    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    /**
     * Displays the index page.
     *
     * @return index view
     */
    @GetMapping("/index")
    public String indexHome() {
        return "index_home";
    }

    /**
     * Lists users using safe view DTOs.
     *
     * @param model view model
     * @return user list view
     */
    @GetMapping("/users")
    public String getAllUsers(final Model model) {
        List<UserViewDto> users = userService.getList().stream()
                .map(UserViewDto::fromUser)
                .collect(Collectors.toList());
        model.addAttribute("users", users);
        return "userList";
    }

    /**
     * Displays a user's details, using Memcached when available.
     *
     * @param id user identifier
     * @param model view model
     * @return user details view or not-found view
     */
    @GetMapping("/users/{id}")
    public String getOneUser(
            @PathVariable("id") final String id,
            final Model model) {
        String result;
        try {
            User userData = MemcachedUtils.memcachedGetData(id);
            if (userData != null) {
                result = "Data is From Cache";
                model.addAttribute("user", UserViewDto.fromUser(userData));
            } else {
                User user = userService.findById(Long.parseLong(id));
                if (user == null) {
                    model.addAttribute(ERROR_ATTRIBUTE, "User not found");
                    return NOT_FOUND_VIEW;
                }
                result = MemcachedUtils.memcachedSetData(user, id);
                if (result == null) {
                    result = "Memcached Connection Failure !!";
                }
                model.addAttribute("user", UserViewDto.fromUser(user));
            }
            model.addAttribute("Result", result);
        } catch (NumberFormatException exception) {
            model.addAttribute(ERROR_ATTRIBUTE, "Unable to load user");
            return NOT_FOUND_VIEW;
        }
        return "user";
    }

    /**
     * Displays the profile update form for the named user.
     *
     * @param username username to edit
     * @param model view model
     * @return profile form view or not-found view
     */
    @GetMapping("/user/{username}")
    public String userUpdate(
            @PathVariable("username") final String username,
            final Model model) {
        User user = userService.findByUsername(username);
        if (user == null) {
            model.addAttribute(ERROR_ATTRIBUTE, "User not found");
            return NOT_FOUND_VIEW;
        }
        model.addAttribute("user", UserFormDto.fromUser(user));
        return "userUpdate";
    }

    /**
     * Applies profile form data and saves the updated user.
     *
     * @param username username identifying the account
     * @param userForm submitted profile data
     * @return welcome view or not-found view
     */
    @PostMapping("/user/{username}")
    public String userUpdateProfile(
            @PathVariable("username") final String username,
            @ModelAttribute("user") final UserFormDto userForm) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return NOT_FOUND_VIEW;
        }
        userForm.copyProfileTo(user);
        userService.save(user);
        return "welcome";
    }
}
