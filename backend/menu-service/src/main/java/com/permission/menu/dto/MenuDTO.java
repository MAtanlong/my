package com.permission.menu.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜单DTO
 *
 * @author system
 * @since 2024-01-01
 */
@Data
public class MenuDTO {
    
    /**
     * 菜单ID
     */
    private Long id;
    
    /**
     * 菜单名称
     */
    private String menuName;
    
    /**
     * 菜单编码
     */
    private String menuCode;
    
    /**
     * 菜单类型（1-目录，2-菜单，3-按钮）
     */
    private Integer type;
    
    /**
     * 父级菜单ID
     */
    private Long parentId;
    
    /**
     * 路由路径
     */
    private String path;
    
    /**
     * 组件路径
     */
    private String component;
    
    /**
     * 菜单图标
     */
    private String icon;
    
    /**
     * 权限标识
     */
    private String permission;
    
    /**
     * 菜单状态（0-禁用，1-启用）
     */
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
    private String remark;
    
    /**
     * 子菜单列表
     */
    private List<MenuDTO> children;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
    
}