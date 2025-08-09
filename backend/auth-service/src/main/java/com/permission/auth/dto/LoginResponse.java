package com.permission.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 登录响应DTO
 *
 * @author system
 * @since 2024-01-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 访问令牌
     */
    private String accessToken;
    
    /**
     * 刷新令牌
     */
    private String refreshToken;
    
    /**
     * 令牌类型
     */
    private String tokenType = "Bearer";
    
    /**
     * 过期时间（秒）
     */
    private Long expiresIn;
    
    /**
     * 用户信息
     */
    private UserInfo userInfo;
    
    /**
     * 用户信息内部类
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfo {
        /**
         * 用户ID
         */
        private Long userId;
        
        /**
         * 用户名
         */
        private String username;
        
        /**
         * 真实姓名
         */
        private String realName;
        
        /**
         * 邮箱
         */
        private String email;
        
        /**
         * 头像
         */
        private String avatar;
        
        /**
         * 角色列表
         */
        private String roles;
        
        /**
         * 权限列表
         */
        private String permissions;
        
        /**
         * 最后登录时间
         */
        private LocalDateTime lastLoginTime;
    }
    
}