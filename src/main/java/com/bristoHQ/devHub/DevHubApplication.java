package com.bristoHQ.devHub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.AllArgsConstructor;

@SpringBootApplication
@AllArgsConstructor
public class DevHubApplication {

    // private final JwtUtilities jwtUtilities;

    public static void main(String[] args) {
        SpringApplication.run(DevHubApplication.class, args);
        System.out.println("DevHubApplication started");
    }

    // @Bean
    // CommandLineRunner run(UserService iUserService, RoleRepository iRoleRepository, UserRepository iUserRepository,
    //         PasswordEncoder passwordEncoder) {
    //     return _ -> {

    //         // Check if users exist before saving
    //         if (iUserRepository.findByEmail("admin@gmail.com").isEmpty()) {
    //             User adminUser = new User("admin", "admin@gmail.com",
    //                     passwordEncoder.encode("adminPassword"),
    //                     new ArrayList<>());

    //             Role adminRole = new Role(RoleName.ADMIN, adminUser.getUsername());
    //             iRoleRepository.save(adminRole);
    //             List<Role> roles = adminUser.getRoles();
    //             roles.add(adminRole);
    //             adminUser.setRoles(roles);
    //             iUserService.saverUser(adminUser);

    //             System.out.println("\nAdmin User saved successfully");
    //             System.out.println("\nAdmin Role saved successfully");
    //             System.out.println("\nAdmin User: " + adminUser);
    //             System.out.println("\nAdmin Role: " + adminRole);
    //         }

    //         if (iUserRepository.findByEmail("user@gmail.com").isEmpty()) {
    //             User normalUser = new User("user", "user@gmail.com",
    //                     passwordEncoder.encode("userPassword"),
    //                     new ArrayList<>());

    //             Role userRole = new Role(RoleName.USER, normalUser.getUsername());
    //             iRoleRepository.save(userRole);
    //             List<Role> roles = normalUser.getRoles();
    //             roles.add(userRole);
    //             normalUser.setRoles(roles);
    //             iUserService.saverUser(normalUser);

    //             System.out.println("\nUser User saved successfully");
    //             System.out.println("\nUser Role saved successfully");
    //             System.out.println("\nUser User: " + normalUser);
    //             System.out.println("\nUser Role: " + userRole);
    //         }

    //         if (iUserRepository.findByEmail("superadminadmin@gmail.com").isEmpty()) {
    //             User superAdminUser = new User("superadmin", "superadminadmin@gmail.com",
    //                     passwordEncoder.encode("superadminPassword"),
    //                     new ArrayList<>());

    //             Role superAdminRole = new Role(RoleName.SUPERADMIN, superAdminUser.getUsername());

    //             iRoleRepository.save(superAdminRole);

    //             superAdminUser.setRoles(List.of(superAdminRole));
    //             iUserService.saverUser(superAdminUser);

    //             System.out.println("\nSuper Admin User saved successfully");
    //             System.out.println("\nSuper Admin Role saved successfully");
    //             System.out.println("\nSuper Admin User: " + superAdminUser);
    //             System.out.println("\nSuper Admin Role: " + superAdminRole);

    //         }

    //         String token = jwtUtilities.generateToken("admin@gmail.com", List.of("ADMIN"));
    //         System.out.println("Admin Token: " + token);

    //         System.out.println("--------------------------------");

    //         String token2 = jwtUtilities.generateToken("admin@gmail.com", List.of("ADMIN", "USER"));
    //         System.out.println("Admin Token 2: " + token2);

    //         System.out.println("--------------------------------");

    //         String token3 = jwtUtilities.generateToken("user@gmail.com", List.of("USER"));
    //         System.out.println("User Token 3: " + token3);
    //     };
    // }

}
