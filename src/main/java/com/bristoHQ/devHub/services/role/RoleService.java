package com.bristoHQ.devHub.services.role;

import java.util.List;

import com.bristoHQ.devHub.models.role.Role;
import com.bristoHQ.devHub.models.role.RoleName;

public interface RoleService {
    Role saveRole(Role role);

    List<Role> getAllRoles();

    Role getRoleById(Long id);

    Role getRoleByName(String name);

    List<Role> getRoleByRoleName(RoleName roleName);
}
