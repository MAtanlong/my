package com.permission.menu.dto;

import lombok.Data;

/**
 * 菜单查询DTO
 *
 * @author system
 * @since 2024-01-01
 */
@Data
public class MenuQueryDTO {
    
    /**
     * 搜索关键字（菜单名称或编码）
     */
    private String keyword;
    
    /**
     * 菜单类型（1-目录，2-菜单，3-按钮）
     */
    private Integer type;
    
    /**
     * 菜单状态（0-禁用，1-启用）
     */
    private Integer status;
    
    /**
     * 是否可见（0-隐藏，1-显示）
     */
    private Integer visible;
    
    /**
     * 父级菜单ID
     */
    private Long parentId;
    
}