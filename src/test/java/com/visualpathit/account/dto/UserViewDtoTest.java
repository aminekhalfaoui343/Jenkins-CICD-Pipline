package com.visualpathit.account.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.visualpathit.account.model.User;

public class UserViewDtoTest {
    @Test
    public void mapsSafeProfileFieldsFromUser() {
        User user = new User();
        user.setId(42L);
        user.setUsername("sample-user");
        user.setPassword("secret");
        user.setUserEmail("sample@example.com");
        user.setDateOfBirth("1990-01-02");
        user.setFatherName("father");
        user.setMotherName("mother");
        user.setGender("value");
        user.setMaritalStatus("single");
        user.setPermanentAddress("permanent address");
        user.setTempAddress("temporary address");
        user.setPrimaryOccupation("primary job");
        user.setSecondaryOccupation("secondary job");
        user.setSkills("Java");
        user.setPhoneNumber("111");
        user.setSecondaryPhoneNumber("222");
        user.setNationality("nationality");
        user.setLanguage("language");
        user.setWorkingExperience("experience");

        UserViewDto view = UserViewDto.fromUser(user);

        assertEquals(Long.valueOf(42L), view.getId());
        assertEquals("sample-user", view.getUsername());
        assertEquals("sample@example.com", view.getUserEmail());
        assertEquals("1990-01-02", view.getDateOfBirth());
        assertEquals("father", view.getFatherName());
        assertEquals("mother", view.getMotherName());
        assertEquals("value", view.getGender());
        assertEquals("single", view.getMaritalStatus());
        assertEquals("permanent address", view.getPermanentAddress());
        assertEquals("temporary address", view.getTempAddress());
        assertEquals("primary job", view.getPrimaryOccupation());
        assertEquals("secondary job", view.getSecondaryOccupation());
        assertEquals("Java", view.getSkills());
        assertEquals("111", view.getPhoneNumber());
        assertEquals("222", view.getSecondaryPhoneNumber());
        assertEquals("nationality", view.getNationality());
        assertEquals("language", view.getLanguage());
        assertEquals("experience", view.getWorkingExperience());
    }

    @Test
    public void returnsNullWhenNoUserWasProvided() {
        assertNull(UserViewDto.fromUser(null));
    }
}
