package com.visualpathit.account.dto;

import com.visualpathit.account.model.User;

/**
 * Mutable form payload for registration, login, and profile updates.
 */
public final class UserFormDto {
    /** Account username. */
    private String username;
    /** Raw account password for authentication or registration. */
    private String password;
    /** Password confirmation used during registration. */
    private String passwordConfirm;
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
     * Creates form data populated from a persistent user.
     *
     * @param user source user
     * @return a form populated with the user's editable profile fields
     */
    public static UserFormDto fromUser(final User user) {
        UserFormDto form = new UserFormDto();
        form.setUsername(user.getUsername());
        form.setUserEmail(user.getUserEmail());
        form.setDateOfBirth(user.getDateOfBirth());
        form.setFatherName(user.getFatherName());
        form.setMotherName(user.getMotherName());
        form.setGender(user.getGender());
        form.setMaritalStatus(user.getMaritalStatus());
        form.setPermanentAddress(user.getPermanentAddress());
        form.setTempAddress(user.getTempAddress());
        form.setPrimaryOccupation(user.getPrimaryOccupation());
        form.setSecondaryOccupation(user.getSecondaryOccupation());
        form.setSkills(user.getSkills());
        form.setPhoneNumber(user.getPhoneNumber());
        form.setSecondaryPhoneNumber(user.getSecondaryPhoneNumber());
        form.setNationality(user.getNationality());
        form.setLanguage(user.getLanguage());
        form.setWorkingExperience(user.getWorkingExperience());
        return form;
    }

    /**
     * Creates a new persistent user from the submitted form.
     *
     * @return a new user containing the submitted account and profile fields
     */
    public User toNewUser() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setUserEmail(userEmail);
        copyProfileTo(user);
        return user;
    }

    /**
     * Copies profile fields to an existing persistent user.
     *
     * @param target user whose profile fields are updated
     */
    public void copyProfileTo(final User target) {
        target.setUsername(username);
        target.setUserEmail(userEmail);
        target.setDateOfBirth(dateOfBirth);
        target.setFatherName(fatherName);
        target.setMotherName(motherName);
        target.setGender(gender);
        target.setMaritalStatus(maritalStatus);
        target.setPermanentAddress(permanentAddress);
        target.setTempAddress(tempAddress);
        target.setPrimaryOccupation(primaryOccupation);
        target.setSecondaryOccupation(secondaryOccupation);
        target.setSkills(skills);
        target.setPhoneNumber(phoneNumber);
        target.setSecondaryPhoneNumber(secondaryPhoneNumber);
        target.setNationality(nationality);
        target.setLanguage(language);
        target.setWorkingExperience(workingExperience);
    }

    /** @return the username */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param newUsername username from the form
     */
    public void setUsername(final String newUsername) {
        this.username = newUsername;
    }

    /** @return the raw password */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the raw password.
     *
     * @param newPassword password from the form
     */
    public void setPassword(final String newPassword) {
        this.password = newPassword;
    }

    /** @return the password confirmation */
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    /**
     * Sets the password confirmation.
     *
     * @param newPasswordConfirm confirmation from the form
     */
    public void setPasswordConfirm(final String newPasswordConfirm) {
        this.passwordConfirm = newPasswordConfirm;
    }

    /** @return the email address */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * Sets the email address.
     *
     * @param newUserEmail email address from the form
     */
    public void setUserEmail(final String newUserEmail) {
        this.userEmail = newUserEmail;
    }

    /** @return the date of birth */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the date of birth.
     *
     * @param newDateOfBirth date of birth from the form
     */
    public void setDateOfBirth(final String newDateOfBirth) {
        this.dateOfBirth = newDateOfBirth;
    }

    /** @return the father's name */
    public String getFatherName() {
        return fatherName;
    }

    /**
     * Sets the father's name.
     *
     * @param newFatherName father's name from the form
     */
    public void setFatherName(final String newFatherName) {
        this.fatherName = newFatherName;
    }

    /** @return the mother's name */
    public String getMotherName() {
        return motherName;
    }

    /**
     * Sets the mother's name.
     *
     * @param newMotherName mother's name from the form
     */
    public void setMotherName(final String newMotherName) {
        this.motherName = newMotherName;
    }

    /** @return the gender */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender.
     *
     * @param newGender gender from the form
     */
    public void setGender(final String newGender) {
        this.gender = newGender;
    }

    /** @return the marital status */
    public String getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Sets the marital status.
     *
     * @param newMaritalStatus marital status from the form
     */
    public void setMaritalStatus(final String newMaritalStatus) {
        this.maritalStatus = newMaritalStatus;
    }

    /** @return the permanent address */
    public String getPermanentAddress() {
        return permanentAddress;
    }

    /**
     * Sets the permanent address.
     *
     * @param newPermanentAddress address from the form
     */
    public void setPermanentAddress(final String newPermanentAddress) {
        this.permanentAddress = newPermanentAddress;
    }

    /** @return the temporary address */
    public String getTempAddress() {
        return tempAddress;
    }

    /**
     * Sets the temporary address.
     *
     * @param newTempAddress address from the form
     */
    public void setTempAddress(final String newTempAddress) {
        this.tempAddress = newTempAddress;
    }

    /** @return the primary occupation */
    public String getPrimaryOccupation() {
        return primaryOccupation;
    }

    /**
     * Sets the primary occupation.
     *
     * @param newPrimaryOccupation occupation from the form
     */
    public void setPrimaryOccupation(final String newPrimaryOccupation) {
        this.primaryOccupation = newPrimaryOccupation;
    }

    /** @return the secondary occupation */
    public String getSecondaryOccupation() {
        return secondaryOccupation;
    }

    /**
     * Sets the secondary occupation.
     *
     * @param newSecondaryOccupation occupation from the form
     */
    public void setSecondaryOccupation(final String newSecondaryOccupation) {
        this.secondaryOccupation = newSecondaryOccupation;
    }

    /** @return the skills */
    public String getSkills() {
        return skills;
    }

    /**
     * Sets the skills.
     *
     * @param newSkills skills from the form
     */
    public void setSkills(final String newSkills) {
        this.skills = newSkills;
    }

    /** @return the primary phone number */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the primary phone number.
     *
     * @param newPhoneNumber phone number from the form
     */
    public void setPhoneNumber(final String newPhoneNumber) {
        this.phoneNumber = newPhoneNumber;
    }

    /** @return the secondary phone number */
    public String getSecondaryPhoneNumber() {
        return secondaryPhoneNumber;
    }

    /**
     * Sets the secondary phone number.
     *
     * @param newSecondaryPhoneNumber phone number from the form
     */
    public void setSecondaryPhoneNumber(final String newSecondaryPhoneNumber) {
        this.secondaryPhoneNumber = newSecondaryPhoneNumber;
    }

    /** @return the nationality */
    public String getNationality() {
        return nationality;
    }

    /**
     * Sets the nationality.
     *
     * @param newNationality nationality from the form
     */
    public void setNationality(final String newNationality) {
        this.nationality = newNationality;
    }

    /** @return the language */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the language.
     *
     * @param newLanguage language from the form
     */
    public void setLanguage(final String newLanguage) {
        this.language = newLanguage;
    }

    /** @return the working experience */
    public String getWorkingExperience() {
        return workingExperience;
    }

    /**
     * Sets the working experience.
     *
     * @param newWorkingExperience experience from the form
     */
    public void setWorkingExperience(final String newWorkingExperience) {
        this.workingExperience = newWorkingExperience;
    }
}
