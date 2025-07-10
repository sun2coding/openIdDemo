package com.example.test2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomOAuth2UserService customOAuth2UserService = new CustomOAuth2UserService();
        http
                .csrf().disable() // For API endpoints, CSRF might be disabled
                .authorizeRequests()
                .antMatchers("/", "/login", "/error", "/public/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .oidcUserService(customOAuth2UserService)
                .and()
                .defaultSuccessUrl("/home", true)
                .and()
                .logout()
                .logoutUrl("/logout") // Custom logout URL
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID", "JWT_TOKEN")
                .permitAll()
                .and()
                // Add JWT filters
                .addFilterBefore(
                        new JwtAuthenticationFilter(new AntPathRequestMatcher("/api/**"), jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(
                        new JwtAuthorizationFilter(authenticationManager(), jwtTokenProvider),
                        JwtAuthenticationFilter.class);
    }
}