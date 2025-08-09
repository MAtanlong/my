package com.permission.common.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户主体信息
 * 用于在Spring Security上下文中存储当前登录用户的信息
 *
 * @author system
 * @since 2024-01-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPrincipal implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 角色列表（逗号分隔）
     */
    private String roles;
    
}