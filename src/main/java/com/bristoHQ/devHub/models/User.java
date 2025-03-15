package com.bristoHQ.devHub.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bristoHQ.devHub.models.premium.RedeemCode;
import com.bristoHQ.devHub.models.role.Role;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Document(collection = "my_users")
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements UserDetails {

    @Id
    String id;
    String fullName;
    String username;
    String email;
    String password;

    List<Role> roles;

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

    // Account stats
    int followersCount;
    int followingCount;
    List<String> followers;
    List<String> following;

    String provider;
    private boolean isPremium;
    private RedeemCode redeemCode;

    private Date accountCreatedAt;
    private Date lastActiveAt;
    private Date profileUpdatedAt;

    // Email verification
    private boolean verified = false;
    private String otp;
    private LocalDateTime otpGeneratedTime;

    public User(String username, String email, String password, List<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.accountCreatedAt = new Date();
        this.lastActiveAt = new Date();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        this.roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName())));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Helper methods for profile management
    public void updateLastActive() {
        this.lastActiveAt = new Date();
    }

    public void addFollower(String userId) {
        if (this.followers == null) {
            this.followers = new ArrayList<>();
        }
        if (!this.followers.contains(userId)) {
            this.followers.add(userId);
            this.followersCount = this.followers.size();
        }
    }

    public void removeFollower(String userId) {
        if (this.followers != null && this.followers.contains(userId)) {
            this.followers.remove(userId);
            this.followersCount = this.followers.size();
        }
    }

    public void follow(String userId) {
        if (this.following == null) {
            this.following = new ArrayList<>();
        }
        if (!this.following.contains(userId)) {
            this.following.add(userId);
            this.followingCount = this.following.size();
        }
    }

    public void unfollow(String userId) {
        if (this.following != null && this.following.contains(userId)) {
            this.following.remove(userId);
            this.followingCount = this.following.size();
        }
    }
}