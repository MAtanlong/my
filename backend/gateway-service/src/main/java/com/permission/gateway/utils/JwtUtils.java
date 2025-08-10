package com.permission.gateway.utils;

import org.springframework.stereotype.Component;

/**
 * JWT工具类 - Gateway简化版本
 */
@Component
public class JwtUtils {
    
    private static final String SECRET = "permission-management-system-secret-key";
    
    /**
     * 验证token是否有效
     */
    public boolean validateToken(String token) {
        try {
            // 简化版本，实际应该解析JWT
            return token != null && token.length() > 10;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * 检查token是否过期
     */
    public boolean isTokenExpired(String token) {
        try {
            // 简化版本，实际应该检查JWT过期时间
            return !validateToken(token);
        } catch (Exception e) {
            return true;
        }
    }
    
    /**
     * 从token中获取用户ID
     */
    public Long getUserIdFromToken(String token) {
        // 简化版本，实际应该从JWT中解析
        return 1L;
    }
    
    /**
     * 从token中获取用户名
     */
    public String getUsernameFromToken(String token) {
        // 简化版本，实际应该从JWT中解析
        return "admin";
    }
}