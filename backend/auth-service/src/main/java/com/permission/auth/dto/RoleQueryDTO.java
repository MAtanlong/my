package com.permission.auth.dto;

import lombok.Data;

/**
 * 角色查询DTO
 *
 * @author system
 * @since 2024-01-01
 */
@Data
public class RoleQueryDTO {
    
    /**
     * 当前页码
     */
    private Integer current = 1;
    
    /**
     * 每页大小
     */
    private Integer size = 10;
    
    /**
     * 搜索关键字（角色名称或编码）
     */
    private String keyword;
    
    /**
     * 角色状态（0-禁用，1-启用）
     */
    private Integer status;
    
}