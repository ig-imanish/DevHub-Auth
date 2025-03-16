package com.bristoHQ.devHub.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bristoHQ.devHub.models.User;
import com.bristoHQ.devHub.models.role.Role;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Integer> {

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmailOrUsername(String email, String username);

    List<User> findByRoles(List<Role> roles);

    User findById(Long id);

    Boolean existsByUsername(String username);

    Boolean existsByEmailOrUsername(String email, String username);

    List<User> findByIsPremium(boolean isPremium);

    Optional<User> findByUsernameAndIsPremium(String username, boolean isPremium);

    Optional<User> findByEmailAndIsPremium(String email, boolean isPremium);

    Optional<User> findByEmailOrUsernameAndIsPremium(String email, String username, boolean isPremium);
}
