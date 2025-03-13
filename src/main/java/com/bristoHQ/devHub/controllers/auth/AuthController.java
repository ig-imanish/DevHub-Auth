package com.bristoHQ.devHub.controllers.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bristoHQ.devHub.dto.LoginDto;


@Controller
public class AuthController {
    // @GetMapping("/")
    // public String home(Principal principal, Model model) {
    // if (principal != null) {
    // model.addAttribute("username", principal.getName());
    // }
    // return "index";
    // }

    @GetMapping("/auth/oauth/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new LoginDto());
    return "login";
    }

 
    // @GetMapping("/oauth-success")
    // public String oauthSuccess(@RequestParam String token) {
    //     System.out.println("Token: " + token);
    //      return "Your Token: " + token;
    // }

    // // Show registration page
    // @GetMapping("/register")
    // public String showRegistrationForm(Model model, Principal principal) {
    // if (principal != null) {
    // return "redirect:/";
    // }
    // model.addAttribute("user", new RegisterDto());
    // return "register";
    // }
}
