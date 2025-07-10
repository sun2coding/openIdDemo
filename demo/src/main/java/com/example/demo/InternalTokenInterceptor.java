package com.example.demo;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Component
public class InternalTokenInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Principal principal = request.getUserPrincipal();
        if (principal instanceof CustomOAuth2User) {
            CustomOAuth2User user = (CustomOAuth2User) principal;
            String internalToken = user.getInternalToken();
            if (internalToken != null && !internalToken.isEmpty()) {
                request.setAttribute("internalToken", internalToken);
            }
        }
        return true;
    }
}