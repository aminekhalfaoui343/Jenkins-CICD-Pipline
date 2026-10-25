package com.visualpathit.account.dto;

import com.visualpathit.account.model.User;

/** Safe user details exposed to the application's views. */
public class UserViewDto {
    private Long id;
    private String username;
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

    public static UserViewDto fromUser(User user) {
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

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getUserEmail() { return userEmail; }
    public String getDateOfBirth() { return dateOfBirth; }
    public String getFatherName() { return fatherName; }
    public String getMotherName() { return motherName; }
    public String getGender() { return gender; }
    public String getMaritalStatus() { return maritalStatus; }
    public String getPermanentAddress() { return permanentAddress; }
    public String getTempAddress() { return tempAddress; }
    public String getPrimaryOccupation() { return primaryOccupation; }
    public String getSecondaryOccupation() { return secondaryOccupation; }
    public String getSkills() { return skills; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getSecondaryPhoneNumber() { return secondaryPhoneNumber; }
    public String getNationality() { return nationality; }
    public String getLanguage() { return language; }
    public String getWorkingExperience() { return workingExperience; }
}
