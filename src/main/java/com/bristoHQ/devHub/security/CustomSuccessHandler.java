package com.bristoHQ.devHub.security;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.bristoHQ.devHub.dto.auth.RegisterDto;
import com.bristoHQ.devHub.dto.user.UserDTO;
import com.bristoHQ.devHub.repositories.UserRepository;
import com.bristoHQ.devHub.security.jwt.JwtUtilities;
import com.bristoHQ.devHub.services.user.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private JwtUtilities jwtUtilities;

	@Value("${oauth.success.url}")
	private String oauthSuccessUrl;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		String email = null;

		if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
			DefaultOAuth2User userDetails = (DefaultOAuth2User) authentication.getPrincipal();

			email = userDetails.getAttribute("email");

			System.out.println(userRepo.findByEmail(email) == null);
			System.out.println("Email: " + email);

			userDetails.getAttributes().forEach((k, v) -> System.out.println(k + " : " + v));
			if (userRepo.findByEmail(email).isEmpty()) {
				RegisterDto user = new RegisterDto();
				user.setEmail(email);
				user.setUsername(email);
				user.setProvider("GOOGLE");
				user.setFullName(userDetails.getAttribute("name"));
				user.setPassword(("NoPass"));

				System.out.println("User Details: in CustomSuccessHandler - " + user);
				userService.register(user);
			}
		}

		System.out.println("1 email " + email);
		List<String> rolesNames = new ArrayList<>();
		UserDTO user = userService.findByEmail(email);

		System.out.println("user " + user);
		user.getRoles().forEach(r -> rolesNames.add(r.getRoleName()));

		System.out.println("User is already authenticated. Redirecting to dashboard.");
		String token = jwtUtilities.generateToken(email, rolesNames);

		System.out.println("Jwt Token for " + user.getEmail() + "  : " + token);
		// Redirect user to frontend with token
		String redirectUrl = oauthSuccessUrl + "?token=" + URLEncoder.encode(token, "UTF-8");
		// String redirectUrl = "http://localhost:8080/api/v1/users/info";

		System.out.println("Redirecting to: " + redirectUrl);
		new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
	}

}
