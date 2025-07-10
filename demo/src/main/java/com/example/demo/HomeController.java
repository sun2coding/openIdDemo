package com.example.demo;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal CustomOAuth2User user) {
        return "Welcome, " + user.getEmail() + "! Your internal token: " + user.getInternalToken();
    }
}