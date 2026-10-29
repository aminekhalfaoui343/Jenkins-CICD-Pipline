package com.visualpathit.account.modelTest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.visualpathit.account.model.User;

public class UserProfilePropertiesTest {
    @Test
    public void storesAllProfileProperties() {
        User user = new User();
        user.setId(42L);
        user.setUsername("sample");
        user.setPassword("encoded");
        user.setUserEmail("sample@example.com");
        user.setPasswordConfirm("confirmation");
        user.setProfileImg("profile.png");
        user.setProfileImgPath("images/profile.png");
        user.setDateOfBirth("1990-01-02");
        user.setFatherName("father");
        user.setMotherName("mother");
        user.setGender("value");
        user.setMaritalStatus("single");
        user.setPermanentAddress("permanent");
        user.setTempAddress("temporary");
        user.setPrimaryOccupation("primary");
        user.setSecondaryOccupation("secondary");
        user.setSkills("Java");
        user.setPhoneNumber("111");
        user.setSecondaryPhoneNumber("222");
        user.setNationality("nationality");
        user.setLanguage("language");
        user.setWorkingExperience("experience");

        assertEquals(Long.valueOf(42L), user.getId());
        assertEquals("sample", user.getUsername());
        assertEquals("encoded", user.getPassword());
        assertEquals("sample@example.com", user.getUserEmail());
        assertEquals("confirmation", user.getPasswordConfirm());
        assertEquals("profile.png", user.getProfileImg());
        assertEquals("images/profile.png", user.getProfileImgPath());
        assertEquals("1990-01-02", user.getDateOfBirth());
        assertEquals("father", user.getFatherName());
        assertEquals("mother", user.getMotherName());
        assertEquals("value", user.getGender());
        assertEquals("single", user.getMaritalStatus());
        assertEquals("permanent", user.getPermanentAddress());
        assertEquals("temporary", user.getTempAddress());
        assertEquals("primary", user.getPrimaryOccupation());
        assertEquals("secondary", user.getSecondaryOccupation());
        assertEquals("Java", user.getSkills());
        assertEquals("111", user.getPhoneNumber());
        assertEquals("222", user.getSecondaryPhoneNumber());
        assertEquals("nationality", user.getNationality());
        assertEquals("language", user.getLanguage());
        assertEquals("experience", user.getWorkingExperience());
    }
}
