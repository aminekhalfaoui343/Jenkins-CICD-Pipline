package com.visualpathit.account.controller;

import com.visualpathit.account.model.User;
import com.visualpathit.account.dto.UserFormDto;
import com.visualpathit.account.dto.UserViewDto;
import com.visualpathit.account.service.SecurityService;
import com.visualpathit.account.service.UserService;
import com.visualpathit.account.utils.MemcachedUtils;
import com.visualpathit.account.validator.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final UserService userService;
    private final SecurityService securityService;
    private final UserValidator userValidator;

    public UserController(UserService userService, SecurityService securityService, UserValidator userValidator) {
        this.userService = userService;
        this.securityService = securityService;
        this.userValidator = userValidator;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new UserFormDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") @Valid UserFormDto userForm,
                               BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        User user = userForm.toNewUser();
        userService.save(user);
        boolean loginSuccessful = securityService.autologin(user.getUsername(), userForm.getPasswordConfirm());
        if (!loginSuccessful) {
            return "redirect:/login?error";
        }

        return "redirect:/welcome";
    }

    @GetMapping({"/", "/login"})
    public String login(Model model, @RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout) {
        if (error != null) {
            model.addAttribute("error", "Your username and password is invalid.");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@ModelAttribute("user") UserFormDto user, Model model) {
        boolean loginSuccessful = securityService.autologin(user.getUsername(), user.getPassword());
        if (!loginSuccessful) {
            model.addAttribute("error", "Your username and password is invalid.");
            return "login";
        }
        return "redirect:/welcome";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/index")
    public String indexHome() {
        return "index_home";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<UserViewDto> users = userService.getList().stream()
                .map(UserViewDto::fromUser)
                .collect(Collectors.toList());
        model.addAttribute("users", users);
        return "userList";
    }

    @GetMapping("/users/{id}")
    public String getOneUser(@PathVariable("id") String id, Model model) {
        String result;
        try {
            User userData = MemcachedUtils.memcachedGetData(id);
            if (userData != null) {
                result = "Data is From Cache";
                model.addAttribute("user", UserViewDto.fromUser(userData));
            } else {
                User user = userService.findById(Long.parseLong(id));
                if (user == null) {
                    model.addAttribute("error", "User not found");
                    return "error/404";
                }
                result = MemcachedUtils.memcachedSetData(user, id);
                if (result == null) {
                    result = "Memcached Connection Failure !!";
                }
                model.addAttribute("user", UserViewDto.fromUser(user));
            }
            model.addAttribute("Result", result);
        } catch (NumberFormatException e) {
            model.addAttribute("error", "Unable to load user");
            return "error/404";
        }
        return "user";
    }

    @GetMapping("/user/{username}")
    public String userUpdate(@PathVariable("username") String username, Model model) {
        User user = userService.findByUsername(username);
        if (user == null) {
            model.addAttribute("error", "User not found");
            return "error/404";
        }
        model.addAttribute("user", UserFormDto.fromUser(user));
        return "userUpdate";
    }

    @PostMapping("/user/{username}")
    public String userUpdateProfile(@PathVariable("username") String username,
                                    @ModelAttribute("user") UserFormDto userForm) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return "error/404";
        }
        userForm.copyProfileTo(user);
        userService.save(user);
        return "welcome";
    }

//    @GetMapping("/user/rabbit")
//    public String rabbitmqSetUp() {
//        for (int i = 0; i < 20; i++) {
//            producerService.produceMessage(generateString());
//        }
//        return "rabbitmq";
//    }
}
