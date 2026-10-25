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
public class FileUploadController {
	private final UserService userService;
	private static final Logger logger = LoggerFactory
			.getLogger(FileUploadController.class);

	public FileUploadController(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Upload single file using Spring Controller
	 */
	@GetMapping("/upload")
    public final String upload(final Model model) {
        return "upload";
    }
	@PostMapping("/uploadFile")
	public @ResponseBody
	String uploadFileHandler(@RequestParam("name") String name,@RequestParam("userName") String userName,
			@RequestParam("file") MultipartFile file) {

		if (!file.isEmpty()) {
			try {
				String rootPath = System.getProperty("catalina.home");
				File directory = new File(rootPath + File.separator + "tmpFiles");
				if (!directory.exists()) {
					directory.mkdirs();
				}
				File serverFile = new File(directory.getAbsolutePath() + File.separator + name + ".png");
				User user = userService.findByUsername(userName);
				byte[] bytes = file.getBytes();
				try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
					stream.write(bytes);
				}
				user.setProfileImg(name +".png");
				user.setProfileImgPath(serverFile.getAbsolutePath());
				userService.save(user);

				logger.info("Server File Location={}", serverFile);

				return String.format("You successfully uploaded file=%s.png", name);
			} catch (Exception e) {
				return String.format("You failed to upload %s.png => %s", name, e.getMessage());
			}
		} else {
			return String.format("You failed to upload %s.png because the file was empty.", name);
		}
	}

}
