package com.visualpathit.account.dto;

import com.visualpathit.account.model.User;

/**
 * Form data accepted by user-facing endpoints. Passwords are kept separate
 * from the user entity returned to views.
 */
public class UserFormDto {
    private String username;
    private String password;
    private String passwordConfirm;
    private String userEmail;
    private String dateOfBirth;
    private String fatherName;
    private String motherName;
    private String gender;
    private String maritalStatus;
    private String permanentAddress;
    private String tempAddress;
    private String primaryOccupation;
    private String secondaryOccupation;
    private String skills;
    private String phoneNumber;
    private String secondaryPhoneNumber;
    private String nationality;
    private String language;
    private String workingExperience;

    public static UserFormDto fromUser(User user) {
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

    public User toNewUser() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setUserEmail(userEmail);
        copyProfileTo(user);
        return user;
    }

    public void copyProfileTo(User user) {
        user.setUsername(username);
        user.setUserEmail(userEmail);
        user.setDateOfBirth(dateOfBirth);
        user.setFatherName(fatherName);
        user.setMotherName(motherName);
        user.setGender(gender);
        user.setMaritalStatus(maritalStatus);
        user.setPermanentAddress(permanentAddress);
        user.setTempAddress(tempAddress);
        user.setPrimaryOccupation(primaryOccupation);
        user.setSecondaryOccupation(secondaryOccupation);
        user.setSkills(skills);
        user.setPhoneNumber(phoneNumber);
        user.setSecondaryPhoneNumber(secondaryPhoneNumber);
        user.setNationality(nationality);
        user.setLanguage(language);
        user.setWorkingExperience(workingExperience);
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPasswordConfirm() { return passwordConfirm; }
    public void setPasswordConfirm(String passwordConfirm) { this.passwordConfirm = passwordConfirm; }
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getFatherName() { return fatherName; }
    public void setFatherName(String fatherName) { this.fatherName = fatherName; }
    public String getMotherName() { return motherName; }
    public void setMotherName(String motherName) { this.motherName = motherName; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getMaritalStatus() { return maritalStatus; }
    public void setMaritalStatus(String maritalStatus) { this.maritalStatus = maritalStatus; }
    public String getPermanentAddress() { return permanentAddress; }
    public void setPermanentAddress(String permanentAddress) { this.permanentAddress = permanentAddress; }
    public String getTempAddress() { return tempAddress; }
    public void setTempAddress(String tempAddress) { this.tempAddress = tempAddress; }
    public String getPrimaryOccupation() { return primaryOccupation; }
    public void setPrimaryOccupation(String primaryOccupation) { this.primaryOccupation = primaryOccupation; }
    public String getSecondaryOccupation() { return secondaryOccupation; }
    public void setSecondaryOccupation(String secondaryOccupation) { this.secondaryOccupation = secondaryOccupation; }
    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getSecondaryPhoneNumber() { return secondaryPhoneNumber; }
    public void setSecondaryPhoneNumber(String secondaryPhoneNumber) { this.secondaryPhoneNumber = secondaryPhoneNumber; }
    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public String getWorkingExperience() { return workingExperience; }
    public void setWorkingExperience(String workingExperience) { this.workingExperience = workingExperience; }
}
