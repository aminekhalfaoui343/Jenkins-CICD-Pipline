package com.visualpathit.account.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.visualpathit.account.model.User;
import com.visualpathit.account.service.UserService;

@Controller
public final class FileUploadController {
    /** Logger for upload diagnostics. */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(FileUploadController.class);

    /** Service used to look up and update upload owners. */
    private final UserService userService;

    /**
     * Creates the file upload controller.
     *
     * @param service user persistence service
     */
    public FileUploadController(final UserService service) {
        this.userService = service;
    }

    /**
     * Displays the file upload form.
     *
     * @param model view model supplied by Spring MVC
     * @return the upload form view
     */
    @GetMapping("/upload")
    public String upload(final Model model) {
        return "upload";
    }

    /**
     * Saves an uploaded image and updates the associated user's profile.
     *
     * @param name image name used for the saved file
     * @param userName username that owns the uploaded image
     * @param file uploaded image content
     * @return the upload status message
     */
    @PostMapping("/uploadFile")
    @ResponseBody
    public String uploadFileHandler(
            @RequestParam("name") final String name,
            @RequestParam("userName") final String userName,
            @RequestParam("file") final MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String rootPath = System.getProperty("catalina.home");
                File directory = new File(
                        rootPath + File.separator + "tmpFiles");
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                File serverFile = new File(
                        directory.getAbsolutePath()
                                + File.separator + name + ".png");
                User user = userService.findByUsername(userName);
                byte[] bytes = file.getBytes();
                try (BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile))) {
                    stream.write(bytes);
                }
                user.setProfileImg(name + ".png");
                user.setProfileImgPath(serverFile.getAbsolutePath());
                userService.save(user);

                LOGGER.info("Server File Location={}", serverFile);

                return String.format(
                        "You successfully uploaded file=%s.png", name);
            } catch (Exception exception) {
                return String.format("You failed to upload %s.png => %s",
                        name, exception.getMessage());
            }
        }
        return String.format("You failed to upload %s.png because "
                + "the file was empty.", name);
    }

}
