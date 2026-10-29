package com.visualpathit.account.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.visualpathit.account.model.User;

public class UserFormDtoTest {
    @Test
    public void mapsFormFieldsToNewAndExistingUsers() {
        UserFormDto form = sampleForm();

        User newUser = form.toNewUser();
        assertEquals("sample-user", newUser.getUsername());
        assertEquals("secret", newUser.getPassword());
        assertEquals("sample@example.com", newUser.getUserEmail());
        assertProfile(newUser);

        User existingUser = new User();
        existingUser.setPassword("unchanged");
        form.copyProfileTo(existingUser);
        assertEquals("unchanged", existingUser.getPassword());
        assertEquals("sample-user", existingUser.getUsername());
        assertProfile(existingUser);
    }

    @Test
    public void mapsUserProfileToFormWithoutCopyingCredentials() {
        User user = sampleForm().toNewUser();
        user.setId(42L);
        user.setPasswordConfirm("confirmation");

        UserFormDto form = UserFormDto.fromUser(user);

        assertEquals("sample-user", form.getUsername());
        assertEquals("sample@example.com", form.getUserEmail());
        assertEquals("1990-01-02", form.getDateOfBirth());
        assertEquals("father", form.getFatherName());
        assertEquals("mother", form.getMotherName());
        assertEquals("value", form.getGender());
        assertEquals("single", form.getMaritalStatus());
        assertEquals("permanent address", form.getPermanentAddress());
        assertEquals("temporary address", form.getTempAddress());
        assertEquals("primary job", form.getPrimaryOccupation());
        assertEquals("secondary job", form.getSecondaryOccupation());
        assertEquals("Java", form.getSkills());
        assertEquals("111", form.getPhoneNumber());
        assertEquals("222", form.getSecondaryPhoneNumber());
        assertEquals("nationality", form.getNationality());
        assertEquals("language", form.getLanguage());
        assertEquals("experience", form.getWorkingExperience());
        assertNull(form.getPassword());
        assertNull(form.getPasswordConfirm());
    }

    private static UserFormDto sampleForm() {
        UserFormDto form = new UserFormDto();
        form.setUsername("sample-user");
        form.setPassword("secret");
        form.setPasswordConfirm("secret");
        form.setUserEmail("sample@example.com");
        form.setDateOfBirth("1990-01-02");
        form.setFatherName("father");
        form.setMotherName("mother");
        form.setGender("value");
        form.setMaritalStatus("single");
        form.setPermanentAddress("permanent address");
        form.setTempAddress("temporary address");
        form.setPrimaryOccupation("primary job");
        form.setSecondaryOccupation("secondary job");
        form.setSkills("Java");
        form.setPhoneNumber("111");
        form.setSecondaryPhoneNumber("222");
        form.setNationality("nationality");
        form.setLanguage("language");
        form.setWorkingExperience("experience");
        return form;
    }

    private static void assertProfile(final User user) {
        assertEquals("1990-01-02", user.getDateOfBirth());
        assertEquals("father", user.getFatherName());
        assertEquals("mother", user.getMotherName());
        assertEquals("value", user.getGender());
        assertEquals("single", user.getMaritalStatus());
        assertEquals("permanent address", user.getPermanentAddress());
        assertEquals("temporary address", user.getTempAddress());
        assertEquals("primary job", user.getPrimaryOccupation());
        assertEquals("secondary job", user.getSecondaryOccupation());
        assertEquals("Java", user.getSkills());
        assertEquals("111", user.getPhoneNumber());
        assertEquals("222", user.getSecondaryPhoneNumber());
        assertEquals("nationality", user.getNationality());
        assertEquals("language", user.getLanguage());
        assertEquals("experience", user.getWorkingExperience());
    }
}
