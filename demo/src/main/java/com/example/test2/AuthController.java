package com.example.test2;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController {

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal OidcUser user) {
        return "Welcome, " + user.getEmail();
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // 实际登出逻辑由Spring Security处理
        // 这里可以添加额外的清理逻辑
        return "redirect:/";
    }
}