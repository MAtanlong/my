package com.permission.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 权限创建DTO
 *
 * @author system
 * @since 2024-01-01
 */
@Data
public class PermissionCreateDTO {
    
    /**
     * 权限名称
     */
    @NotBlank(message = "权限名称不能为空")
    @Size(max = 50, message = "权限名称长度不能超过50个字符")
    private String permissionName;
    
    /**
     * 权限编码
     */
    @NotBlank(message = "权限编码不能为空")
    @Size(max = 100, message = "权限编码长度不能超过100个字符")
    private String permissionCode;
    
    /**
     * 权限类型（1-菜单，2-按钮）
     */
    @NotNull(message = "权限类型不能为空")
    private Integer type;
    
    /**
     * 父级权限ID
     */
    private Long parentId;
    
    /**
     * 权限路径
     */
    @Size(max = 200, message = "权限路径长度不能超过200个字符")
    private String path;
    
    /**
     * 权限描述
     */
    @Size(max = 200, message = "权限描述长度不能超过200个字符")
    private String description;
    
    /**
     * 权限状态（0-禁用，1-启用）
     */
    @NotNull(message = "权限状态不能为空")
    private Integer status;
    
    /**
     * 排序
     */
    private Integer sort;
    
}