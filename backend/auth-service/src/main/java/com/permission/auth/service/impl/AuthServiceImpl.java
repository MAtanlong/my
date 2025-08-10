package com.permission.auth.service.impl;

import com.permission.auth.dto.LoginRequest;
import com.permission.auth.dto.LoginResponse;
import com.permission.auth.service.AuthService;
import com.permission.common.exception.BusinessException;
import com.permission.common.result.ResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 认证服务实现类
 *
 * @author system
 * @since 2024-01-01
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    
    @Override
    public LoginResponse login(LoginRequest request, String clientIp) {
        log.info("用户登录请求：{}, IP：{}", request.getUsername(), clientIp);
        
        // 简单的用户名密码验证（临时实现）
        if ("admin".equals(request.getUsername()) && "123456".equals(request.getPassword())) {
            // 生成令牌
            String accessToken = generateToken();
            String refreshToken = generateToken();
            
            // 构建用户信息
            LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo();
            userInfo.setUserId(1L);
            userInfo.setUsername("admin");
            userInfo.setRealName("管理员");
            userInfo.setEmail("admin@example.com");
            userInfo.setRoles("ADMIN");
            userInfo.setPermissions("*:*:*");
            userInfo.setLastLoginTime(LocalDateTime.now());
            
            // 构建登录响应
            LoginResponse response = new LoginResponse();
            response.setAccessToken(accessToken);
            response.setRefreshToken(refreshToken);
            response.setTokenType("Bearer");
            response.setExpiresIn(7200L); // 2小时
            response.setUserInfo(userInfo);
            
            log.info("用户 {} 登录成功", request.getUsername());
            return response;
        } else {
            throw new BusinessException(ResultCode.PASSWORD_ERROR, "用户名或密码错误");
        }
    }
    
    @Override
    public void logout(String token) {
        log.info("用户登出，令牌：{}", token);
        // TODO: 实现令牌黑名单或缓存清除
    }
    
    @Override
    public LoginResponse refreshToken(String refreshToken) {
        log.info("刷新令牌：{}", refreshToken);
        
        // 简单验证刷新令牌
        if (refreshToken != null && refreshToken.startsWith("refresh_")) {
            // 生成新的令牌
            String newAccessToken = generateToken();
            String newRefreshToken = generateToken();
            
            // 构建用户信息（临时实现）
            LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo();
            userInfo.setUserId(1L);
            userInfo.setUsername("admin");
            userInfo.setRealName("管理员");
            userInfo.setEmail("admin@example.com");
            userInfo.setRoles("ADMIN");
            userInfo.setPermissions("*:*:*");
            userInfo.setLastLoginTime(LocalDateTime.now());
            
            // 构建响应
            LoginResponse response = new LoginResponse();
            response.setAccessToken(newAccessToken);
            response.setRefreshToken(newRefreshToken);
            response.setTokenType("Bearer");
            response.setExpiresIn(7200L);
            response.setUserInfo(userInfo);
            
            return response;
        } else {
            throw new BusinessException(ResultCode.UNAUTHORIZED, "刷新令牌无效");
        }
    }
    
    @Override
    public LoginResponse.UserInfo getCurrentUserInfo() {
        log.info("获取当前用户信息");
        
        // 临时实现，返回默认用户信息
        LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo();
        userInfo.setUserId(1L);
        userInfo.setUsername("admin");
        userInfo.setRealName("管理员");
        userInfo.setEmail("admin@example.com");
        userInfo.setRoles("ADMIN");
        userInfo.setPermissions("*:*:*");
        userInfo.setLastLoginTime(LocalDateTime.now());
        
        return userInfo;
    }
    
    @Override
    public boolean validateToken(String token) {
        log.info("验证令牌：{}", token);
        
        // 简单验证令牌格式
        return token != null && token.startsWith("token_");
    }
    
    /**
     * 生成令牌（临时实现）
     */
    private String generateToken() {
        return "token_" + UUID.randomUUID().toString().replace("-", "");
    }
    
    /**
     * 生成刷新令牌（临时实现）
     */
    private String generateRefreshToken() {
        return "refresh_" + UUID.randomUUID().toString().replace("-", "");
    }
}