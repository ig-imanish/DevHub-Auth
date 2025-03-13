package com.bristoHQ.devHub.dto;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.bristoHQ.devHub.models.premium.RedeemCode;
import com.bristoHQ.devHub.models.role.Role;

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
public class UserDTO {

    @Id
    String id;
    String fullName;
    String username;
    String email;
    List<Role> roles;
    String provider;
    private boolean isPremium;
    private RedeemCode redeemCode;

    private Date accountCreatedAt;

    public UserDTO(String username, String email,List<Role> roles) {
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}

