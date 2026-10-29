package com.visualpathit.account.controllerTest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Files;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.Model;

import com.visualpathit.account.controller.FileUploadController;
import com.visualpathit.account.model.User;
import com.visualpathit.account.service.UserService;

public class FileUploadControllerTest {
    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    private String previousCatalinaHome;
    private UserService userService;
    private FileUploadController controller;

    @Before
    public void setUp() {
        previousCatalinaHome = System.getProperty("catalina.home");
        System.setProperty(
                "catalina.home", temporaryFolder.getRoot().getAbsolutePath());
        userService = mock(UserService.class);
        controller = new FileUploadController(userService);
    }

    @After
    public void restoreCatalinaHome() {
        if (previousCatalinaHome == null) {
            System.clearProperty("catalina.home");
        } else {
            System.setProperty("catalina.home", previousCatalinaHome);
        }
    }

    @Test
    public void returnsUploadView() {
        assertEquals("upload", controller.upload(mock(Model.class)));
    }

    @Test
    public void savesUploadedImageAndUpdatesUser() throws Exception {
        User user = new User();
        when(userService.findByUsername("sample")).thenReturn(user);
        byte[] content = {1, 2, 3};
        MockMultipartFile file =
                new MockMultipartFile("file", "input.png", "image/png", content);

        String result =
                controller.uploadFileHandler("photo", "sample", file);

        assertThat(
                result, containsString("successfully uploaded file=photo.png"));
        assertArrayEquals(
                content,
                Files.readAllBytes(
                        temporaryFolder.getRoot().toPath()
                                .resolve("tmpFiles").resolve("photo.png")));
        assertEquals("photo.png", user.getProfileImg());
        verify(userService).save(user);
    }

    @Test
    public void reportsEmptyUpload() {
        MockMultipartFile file =
                new MockMultipartFile("file", new byte[0]);

        String result =
                controller.uploadFileHandler("photo", "sample", file);

        assertThat(result, containsString("because the file was empty"));
    }

    @Test
    public void reportsUploadReadFailure() throws Exception {
        org.springframework.web.multipart.MultipartFile file =
                mock(org.springframework.web.multipart.MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(file.getBytes()).thenThrow(new IOException("read failed"));

        String result =
                controller.uploadFileHandler("photo", "sample", file);

        assertThat(result, containsString("read failed"));
    }
}
