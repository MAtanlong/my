package com.permission.common.utils;

import com.permission.common.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 安全工具类
 * 用于获取当前登录用户的信息
 *
 * @author system
 * @since 2024-01-01
 */
public class SecurityUtils {
    
    /**
     * 获取当前登录用户的认证信息
     *
     * @return 认证信息
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
    
    /**
     * 获取当前登录用户的用户名
     *
     * @return 用户名
     */
    public static String getCurrentUsername() {
        Authentication authentication = getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return null;
    }
    
    /**
     * 获取当前登录用户的用户ID
     *
     * @return 用户ID
     */
    public static Long getCurrentUserId() {
        Authentication authentication = getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object details = authentication.getDetails();
            if (details instanceof UserPrincipal) {
                return ((UserPrincipal) details).getUserId();
            }
        }
        return null;
    }
    
    /**
     * 获取当前登录用户的角色
     *
     * @return 角色列表（逗号分隔）
     */
    public static String getCurrentUserRoles() {
        Authentication authentication = getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object details = authentication.getDetails();
            if (details instanceof UserPrincipal) {
                return ((UserPrincipal) details).getRoles();
            }
        }
        return null;
    }
    
    /**
     * 获取当前登录用户的详细信息
     *
     * @return 用户主体信息
     */
    public static UserPrincipal getCurrentUserPrincipal() {
        Authentication authentication = getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object details = authentication.getDetails();
            if (details instanceof UserPrincipal) {
                return (UserPrincipal) details;
            }
        }
        return null;
    }
    
    /**
     * 判断当前用户是否已登录
     *
     * @return 是否已登录
     */
    public static boolean isAuthenticated() {
        Authentication authentication = getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName());
    }
    
}