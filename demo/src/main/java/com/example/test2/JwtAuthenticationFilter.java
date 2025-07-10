package com.example.test2;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final JwtTokenProvider tokenProvider;

    public JwtAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher, 
                                 JwtTokenProvider tokenProvider) {
        super(requiresAuthenticationRequestMatcher);
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, 
                                              HttpServletResponse response) 
            throws AuthenticationException, IOException, ServletException {
        
        String token = resolveToken(request);
        if (token == null || !tokenProvider.validateToken(token)) {
            return null;
        }
        
        String username = tokenProvider.getUsernameFromToken(token);
        List<String> roles = tokenProvider.getRolesFromToken(token);
        
        return new JwtAuthenticationToken(username, roles);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, 
                                          HttpServletResponse response, 
                                          FilterChain chain, 
                                          Authentication authResult) 
            throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}