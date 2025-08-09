package com.permission.menu.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 菜单创建DTO
 *
 * @author system
 * @since 2024-01-01
 */
@Data
public class MenuCreateDTO {
    
    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    @Size(max = 50, message = "菜单名称长度不能超过50个字符")
    private String menuName;
    
    /**
     * 菜单编码
     */
    @NotBlank(message = "菜单编码不能为空")
    @Size(max = 100, message = "菜单编码长度不能超过100个字符")
    private String menuCode;
    
    /**
     * 菜单类型（1-目录，2-菜单，3-按钮）
     */
    @NotNull(message = "菜单类型不能为空")
    private Integer type;
    
    /**
     * 父级菜单ID
     */
    private Long parentId;
    
    /**
     * 路由路径
     */
    @Size(max = 200, message = "路由路径长度不能超过200个字符")
    private String path;
    
    /**
     * 组件路径
     */
    @Size(max = 200, message = "组件路径长度不能超过200个字符")
    private String component;
    
    /**
     * 菜单图标
     */
    @Size(max = 100, message = "菜单图标长度不能超过100个字符")
    private String icon;
    
    /**
     * 权限标识
     */
    @Size(max = 100, message = "权限标识长度不能超过100个字符")
    private String permission;
    
    /**
     * 菜单状态（0-禁用，1-启用）
     */
    @NotNull(message = "菜单状态不能为空")
    private Integer status;
    
    /**
     * 是否可见（0-隐藏，1-显示）
     */
    private Integer visible;
    
    /**
     * 是否缓存（0-不缓存，1-缓存）
     */
    private Integer keepAlive;
    
    /**
     * 排序
     */
    private Integer sort;
    
    /**
     * 备注
     */
    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;
    
}