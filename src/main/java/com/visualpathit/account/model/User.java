package com.visualpathit.account.model;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

/**
 * Persistent account and profile information for an application user.
 */
@Entity
@Table(name = "user")
public class User implements Serializable {
    /** Persistent user identifier. */
    private Long id;
    /** Account username. */
    private String username;
    /** Encoded account password. */
    private String password;
    /** Account email address. */
    private String userEmail;
    /** Non-persistent password confirmation. */
    private String passwordConfirm;
    /** Name of the profile image. */
    private String profileImg;
    /** Filesystem path to the profile image. */
    private String profileImgPath;
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
    /** Roles assigned to the user. */
    private Set<Role> roles;

    /**
     * Returns the persistent identifier.
     *
     * @return user identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    /**
     * Sets the persistent identifier.
     *
     * @param newId user identifier
     */
    public void setId(final Long newId) {
        this.id = newId;
    }

    /**
     * Returns the account username.
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the account username.
     *
     * @param newUsername username
     */
    public void setUsername(final String newUsername) {
        this.username = newUsername;
    }

    /**
     * Returns the encoded account password.
     *
     * @return encoded password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the encoded account password.
     *
     * @param newPassword encoded password
     */
    public void setPassword(final String newPassword) {
        this.password = newPassword;
    }

    /**
     * Returns the account email address.
     *
     * @return email address
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * Sets the account email address.
     *
     * @param newUserEmail email address
     */
    public void setUserEmail(final String newUserEmail) {
        this.userEmail = newUserEmail;
    }

    /**
     * Returns the transient password confirmation.
     *
     * @return password confirmation
     */
    @Transient
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    /**
     * Sets the transient password confirmation.
     *
     * @param newPasswordConfirm password confirmation
     */
    public void setPasswordConfirm(final String newPasswordConfirm) {
        this.passwordConfirm = newPasswordConfirm;
    }

    /**
     * Returns the roles assigned to this account.
     *
     * @return assigned roles
     */
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Sets the roles assigned to this account.
     *
     * @param newRoles assigned roles
     */
    public void setRoles(final Set<Role> newRoles) {
        this.roles = newRoles;
    }

    /**
     * Returns the profile image name.
     *
     * @return profile image name
     */
    public String getProfileImg() {
        return profileImg;
    }

    /**
     * Sets the profile image name.
     *
     * @param newProfileImg profile image name
     */
    public void setProfileImg(final String newProfileImg) {
        this.profileImg = newProfileImg;
    }

    /**
     * Returns the profile image filesystem path.
     *
     * @return profile image path
     */
    public String getProfileImgPath() {
        return profileImgPath;
    }

    /**
     * Sets the profile image filesystem path.
     *
     * @param newProfileImgPath profile image path
     */
    public void setProfileImgPath(final String newProfileImgPath) {
        this.profileImgPath = newProfileImgPath;
    }

    /**
     * Returns the date of birth.
     *
     * @return date of birth
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the date of birth.
     *
     * @param newDateOfBirth date of birth
     */
    public void setDateOfBirth(final String newDateOfBirth) {
        this.dateOfBirth = newDateOfBirth;
    }

    /**
     * Returns the father's name.
     *
     * @return father's name
     */
    public String getFatherName() {
        return fatherName;
    }

    /**
     * Sets the father's name.
     *
     * @param newFatherName father's name
     */
    public void setFatherName(final String newFatherName) {
        this.fatherName = newFatherName;
    }

    /**
     * Returns the mother's name.
     *
     * @return mother's name
     */
    public String getMotherName() {
        return motherName;
    }

    /**
     * Sets the mother's name.
     *
     * @param newMotherName mother's name
     */
    public void setMotherName(final String newMotherName) {
        this.motherName = newMotherName;
    }

    /**
     * Returns the user's gender.
     *
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the user's gender.
     *
     * @param newGender gender
     */
    public void setGender(final String newGender) {
        this.gender = newGender;
    }

    /**
     * Returns the marital status.
     *
     * @return marital status
     */
    public String getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Sets the marital status.
     *
     * @param newMaritalStatus marital status
     */
    public void setMaritalStatus(final String newMaritalStatus) {
        this.maritalStatus = newMaritalStatus;
    }

    /**
     * Returns the permanent address.
     *
     * @return permanent address
     */
    public String getPermanentAddress() {
        return permanentAddress;
    }

    /**
     * Sets the permanent address.
     *
     * @param newPermanentAddress permanent address
     */
    public void setPermanentAddress(final String newPermanentAddress) {
        this.permanentAddress = newPermanentAddress;
    }

    /**
     * Returns the temporary address.
     *
     * @return temporary address
     */
    public String getTempAddress() {
        return tempAddress;
    }

    /**
     * Sets the temporary address.
     *
     * @param newTempAddress temporary address
     */
    public void setTempAddress(final String newTempAddress) {
        this.tempAddress = newTempAddress;
    }

    /**
     * Returns the primary occupation.
     *
     * @return primary occupation
     */
    public String getPrimaryOccupation() {
        return primaryOccupation;
    }

    /**
     * Sets the primary occupation.
     *
     * @param newPrimaryOccupation primary occupation
     */
    public void setPrimaryOccupation(final String newPrimaryOccupation) {
        this.primaryOccupation = newPrimaryOccupation;
    }

    /**
     * Returns the secondary occupation.
     *
     * @return secondary occupation
     */
    public String getSecondaryOccupation() {
        return secondaryOccupation;
    }

    /**
     * Sets the secondary occupation.
     *
     * @param newSecondaryOccupation secondary occupation
     */
    public void setSecondaryOccupation(final String newSecondaryOccupation) {
        this.secondaryOccupation = newSecondaryOccupation;
    }

    /**
     * Returns the user's skills.
     *
     * @return skills
     */
    public String getSkills() {
        return skills;
    }

    /**
     * Sets the user's skills.
     *
     * @param newSkills skills
     */
    public void setSkills(final String newSkills) {
        this.skills = newSkills;
    }

    /**
     * Returns the primary phone number.
     *
     * @return primary phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the primary phone number.
     *
     * @param newPhoneNumber primary phone number
     */
    public void setPhoneNumber(final String newPhoneNumber) {
        this.phoneNumber = newPhoneNumber;
    }

    /**
     * Returns the secondary phone number.
     *
     * @return secondary phone number
     */
    public String getSecondaryPhoneNumber() {
        return secondaryPhoneNumber;
    }

    /**
     * Sets the secondary phone number.
     *
     * @param newSecondaryPhoneNumber secondary phone number
     */
    public void setSecondaryPhoneNumber(
            final String newSecondaryPhoneNumber) {
        this.secondaryPhoneNumber = newSecondaryPhoneNumber;
    }

    /**
     * Returns the user's nationality.
     *
     * @return nationality
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Sets the user's nationality.
     *
     * @param newNationality nationality
     */
    public void setNationality(final String newNationality) {
        this.nationality = newNationality;
    }

    /**
     * Returns the user's spoken language.
     *
     * @return language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the user's spoken language.
     *
     * @param newLanguage language
     */
    public void setLanguage(final String newLanguage) {
        this.language = newLanguage;
    }

    /**
     * Returns the user's working experience.
     *
     * @return working experience
     */
    public String getWorkingExperience() {
        return workingExperience;
    }

    /**
     * Sets the user's working experience.
     *
     * @param newWorkingExperience working experience
     */
    public void setWorkingExperience(final String newWorkingExperience) {
        this.workingExperience = newWorkingExperience;
    }
}
