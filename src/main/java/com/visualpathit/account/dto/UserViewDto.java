package com.visualpathit.account.dto;

import com.visualpathit.account.model.User;

/**
 * Safe user profile details exposed to application views.
 */
public final class UserViewDto {
    /** Persistent user identifier. */
    private Long id;
    /** Account username. */
    private String username;
    /** User email address. */
    private String userEmail;
    /** User date of birth. */
    private String dateOfBirth;
    /** User's father's name. */
    private String fatherName;
    /** User's mother's name. */
    private String motherName;
    /** User gender. */
    private String gender;
    /** User marital status. */
    private String maritalStatus;
    /** User permanent address. */
    private String permanentAddress;
    /** User temporary address. */
    private String tempAddress;
    /** User primary occupation. */
    private String primaryOccupation;
    /** User secondary occupation. */
    private String secondaryOccupation;
    /** User skills. */
    private String skills;
    /** User primary phone number. */
    private String phoneNumber;
    /** User secondary phone number. */
    private String secondaryPhoneNumber;
    /** User nationality. */
    private String nationality;
    /** User spoken language. */
    private String language;
    /** User working experience. */
    private String workingExperience;

    /**
     * Creates a safe view DTO from a persistent user.
     *
     * @param user source user
     * @return view DTO, or {@code null} if the source is {@code null}
     */
    public static UserViewDto fromUser(final User user) {
        if (user == null) {
            return null;
        }

        UserViewDto view = new UserViewDto();
        view.id = user.getId();
        view.username = user.getUsername();
        view.userEmail = user.getUserEmail();
        view.dateOfBirth = user.getDateOfBirth();
        view.fatherName = user.getFatherName();
        view.motherName = user.getMotherName();
        view.gender = user.getGender();
        view.maritalStatus = user.getMaritalStatus();
        view.permanentAddress = user.getPermanentAddress();
        view.tempAddress = user.getTempAddress();
        view.primaryOccupation = user.getPrimaryOccupation();
        view.secondaryOccupation = user.getSecondaryOccupation();
        view.skills = user.getSkills();
        view.phoneNumber = user.getPhoneNumber();
        view.secondaryPhoneNumber = user.getSecondaryPhoneNumber();
        view.nationality = user.getNationality();
        view.language = user.getLanguage();
        view.workingExperience = user.getWorkingExperience();
        return view;
    }

    /** @return persistent user identifier */
    public Long getId() {
        return id;
    }

    /** @return username */
    public String getUsername() {
        return username;
    }

    /** @return email address */
    public String getUserEmail() {
        return userEmail;
    }

    /** @return date of birth */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /** @return father's name */
    public String getFatherName() {
        return fatherName;
    }

    /** @return mother's name */
    public String getMotherName() {
        return motherName;
    }

    /** @return gender */
    public String getGender() {
        return gender;
    }

    /** @return marital status */
    public String getMaritalStatus() {
        return maritalStatus;
    }

    /** @return permanent address */
    public String getPermanentAddress() {
        return permanentAddress;
    }

    /** @return temporary address */
    public String getTempAddress() {
        return tempAddress;
    }

    /** @return primary occupation */
    public String getPrimaryOccupation() {
        return primaryOccupation;
    }

    /** @return secondary occupation */
    public String getSecondaryOccupation() {
        return secondaryOccupation;
    }

    /** @return skills */
    public String getSkills() {
        return skills;
    }

    /** @return primary phone number */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /** @return secondary phone number */
    public String getSecondaryPhoneNumber() {
        return secondaryPhoneNumber;
    }

    /** @return nationality */
    public String getNationality() {
        return nationality;
    }

    /** @return spoken language */
    public String getLanguage() {
        return language;
    }

    /** @return working experience */
    public String getWorkingExperience() {
        return workingExperience;
    }
}
