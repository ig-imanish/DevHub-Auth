package com.bristoHQ.devHub.dto.user;

import java.util.Date;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;


@AllArgsConstructor
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileUpdateDTO {

    String fullName;
    String username;
    String email;

    private String userAvatar;
    private String userAvatarpublicId;

    private String userBanner;
    private String userBannerpublicId;

    // Social media profile details
    String bio;
    String countryName;
    String city;
    String recoveryPhone;
    String recoveryEmail;

    // Social media links
    Map<String, String> socialLinks; // e.g., {"twitter": "https://twitter.com/username", "github":
                                     // "https://github.com/username"}

    // Additional profile data
    String jobTitle;
    String company;
    String website;
    Date birthDate;
    String gender;
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUserAvatar() {
        return userAvatar;
    }
    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }
    public String getUserAvatarpublicId() {
        return userAvatarpublicId;
    }
    public void setUserAvatarpublicId(String userAvatarpublicId) {
        this.userAvatarpublicId = userAvatarpublicId;
    }
    public String getUserBanner() {
        return userBanner;
    }
    public void setUserBanner(String userBanner) {
        this.userBanner = userBanner;
    }
    public String getUserBannerpublicId() {
        return userBannerpublicId;
    }
    public void setUserBannerpublicId(String userBannerpublicId) {
        this.userBannerpublicId = userBannerpublicId;
    }
    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public String getCountryName() {
        return countryName;
    }
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getRecoveryPhone() {
        return recoveryPhone;
    }
    public void setRecoveryPhone(String recoveryPhone) {
        this.recoveryPhone = recoveryPhone;
    }
    public String getRecoveryEmail() {
        return recoveryEmail;
    }
    public void setRecoveryEmail(String recoveryEmail) {
        this.recoveryEmail = recoveryEmail;
    }
    public Map<String, String> getSocialLinks() {
        return socialLinks;
    }
    public void setSocialLinks(Map<String, String> socialLinks) {
        this.socialLinks = socialLinks;
    }
    public String getJobTitle() {
        return jobTitle;
    }
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public String getWebsite() {
        return website;
    }
    public void setWebsite(String website) {
        this.website = website;
    }
    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }



}
