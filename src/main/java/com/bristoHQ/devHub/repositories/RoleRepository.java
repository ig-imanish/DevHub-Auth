package com.bristoHQ.devHub.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bristoHQ.devHub.models.role.Role;
import com.bristoHQ.devHub.models.role.RoleName;

public interface RoleRepository extends MongoRepository<Role, Integer> {

    Role findByRoleName(String name);

    List<Role> findByRoleName(RoleName name);

    Role findById(Long id);
}
