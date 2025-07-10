package com.example.test2;

import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserService {

    public User processUserAfterLogin(String email, String name) {
        // 这里可以查询数据库或调用其他服务处理用户信息
        // 示例中简化处理，实际项目中需要根据业务需求实现
        
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setRoles(Arrays.asList("ROLE_USER")); // 设置默认角色
        
        // 可以在这里保存用户信息到数据库或调用其他服务
        // userRepository.save(user);
        
        return user;
    }
}