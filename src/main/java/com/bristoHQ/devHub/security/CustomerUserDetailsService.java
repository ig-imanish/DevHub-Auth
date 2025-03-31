package com.bristoHQ.devHub.security;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bristoHQ.devHub.models.User;
import com.bristoHQ.devHub.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

    @Lazy
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> users = userRepository.findByEmailOrUsername(username, username);
        List<User> userList = users.stream().toList();

        if (userList.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        } else if (userList.size() > 1) {
            throw new RuntimeException("Multiple users found for username: " + username);
        }

        User user = userList.get(0);
        System.out.println("User: " + user);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRoles().get(0).getRoleName())));
    }
}
