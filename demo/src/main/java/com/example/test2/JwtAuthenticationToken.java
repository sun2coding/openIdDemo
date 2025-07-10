package com.example.test2;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String principal;
    private Object credentials;

    public JwtAuthenticationToken(String principal, List<String> roles) {
        super(roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()));
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}