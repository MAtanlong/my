package com.permission.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 角色创建DTO
 *
 * @author system
 * @since 2024-01-01
 */
@Data
public class RoleCreateDTO {
    
    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    @Size(max = 50, message = "角色名称长度不能超过50个字符")
    private String roleName;
    
    /**
     * 角色编码
     */
    @NotBlank(message = "角色编码不能为空")
    @Size(max = 50, message = "角色编码长度不能超过50个字符")
    private String roleCode;
    
    /**
     * 角色描述
     */
    @Size(max = 200, message = "角色描述长度不能超过200个字符")
    private String description;
    
    /**
     * 角色状态（0-禁用，1-启用）
     */
    @NotNull(message = "角色状态不能为空")
    private Integer status;
    
    /**
     * 排序
     */
    private Integer sort;
    
    /**
     * 权限ID列表
     */
    private List<Long> permissionIds;
    
}