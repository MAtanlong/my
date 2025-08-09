package com.permission.auth.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 权限DTO
 *
 * @author system
 * @since 2024-01-01
 */
@Data
public class PermissionDTO {
    
    /**
     * 权限ID
     */
    private Long id;
    
    /**
     * 权限名称
     */
    private String permissionName;
    
    /**
     * 权限编码
     */
    private String permissionCode;
    
    /**
     * 权限类型（1-菜单，2-按钮）
     */
    private Integer type;
    
    /**
     * 父级权限ID
     */
    private Long parentId;
    
    /**
     * 权限路径
     */
    private String path;
    
    /**
     * 权限描述
     */
    private String description;
    
    /**
     * 权限状态（0-禁用，1-启用）
     */
    private Integer status;
    
    /**
     * 排序
     */
    private Integer sort;
    
    /**
     * 子权限列表
     */
    private List<PermissionDTO> children;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
    
}