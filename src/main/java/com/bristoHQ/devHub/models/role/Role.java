package com.bristoHQ.devHub.models.role;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class Role implements Serializable {

    @Id
    String id;
    RoleName roleName;
    String username;

    public Role(RoleName roleName, String username) {
        this.roleName = roleName;
        this.username = username;
    }

    public String getRoleName() {
        return roleName.toString();
    }
}
