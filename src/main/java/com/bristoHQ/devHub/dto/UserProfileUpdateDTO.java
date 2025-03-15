package com.bristoHQ.devHub.dto;

import java.util.Date;
import java.util.Map;

import org.bson.types.Binary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileUpdateDTO {

    String fullName;
    String username;
    String email;

    private Binary userAvatar;
    private transient String userAvatarBase64;
    private Binary userBanner;
    private transient String userBannerBase64;

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

}
