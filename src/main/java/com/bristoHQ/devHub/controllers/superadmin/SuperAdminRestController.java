package com.bristoHQ.devHub.controllers.superadmin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/superadmins")
@RequiredArgsConstructor
public class SuperAdminRestController {

    // RessourceEndPoint:http://localhost:8087/api/superadmin/hi
    @GetMapping
    public String sayHi() {
        return "superadmin supported";
    }

}
