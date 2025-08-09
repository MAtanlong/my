package com.permission.auth.service;

import com.permission.auth.dto.LoginRequest;
import com.permission.auth.dto.LoginResponse;

/**
 * 认证服务接口
 *
 * @author system
 * @since 2024-01-01
 */
public interface AuthService {
    
    /**
     * 用户登录
     *
     * @param request  登录请求
     * @param clientIp 客户端IP
     * @return 登录响应
     */
    LoginResponse login(LoginRequest request, String clientIp);
    
    /**
     * 用户登出
     *
     * @param token JWT令牌
     */
    void logout(String token);
    
    /**
     * 刷新令牌
     *
     * @param refreshToken 刷新令牌
     * @return 新的登录响应
     */
    LoginResponse refreshToken(String refreshToken);
    
    /**
     * 获取当前用户信息
     *
     * @return 用户信息
     */
    LoginResponse.UserInfo getCurrentUserInfo();
    
    /**
     * 验证令牌
     *
     * @param token JWT令牌
     * @return 是否有效
     */
    boolean validateToken(String token);
    
}