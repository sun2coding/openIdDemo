package com.example.test2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends OidcUserService {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserService userService;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        
        // 从微软认证信息中获取email
        String email = oidcUser.getEmail();
        if (email == null || email.isEmpty()) {
            throw new OAuth2AuthenticationException("Email not found in OIDC claims");
        }
        
        // 使用email进行业务处理（例如：创建本地用户或更新用户信息）
        // 这里可以调用其他服务处理业务逻辑
        User user = userService.processUserAfterLogin(email, oidcUser.getFullName());
        
        // 生成JWT token用于后续请求
        String token = jwtTokenProvider.createToken(email, user.getRoles());
        
        // 将token存储在响应中（可以通过前端存储）
        // 实际项目中可能需要通过其他方式传递token给前端
        
        return oidcUser;
    }
}