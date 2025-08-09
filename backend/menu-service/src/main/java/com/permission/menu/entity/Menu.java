package com.permission.menu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.permission.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单实体
 *
 * @author system
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu")
public class Menu extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 菜单名称
     */
    @TableField("menu_name")
    private String menuName;
    
    /**
     * 菜单编码
     */
    @TableField("menu_code")
    private String menuCode;
    
    /**
     * 父菜单ID
     */
    @TableField("parent_id")
    private Long parentId;
    
    /**
     * 菜单类型：1-目录，2-菜单，3-按钮
     */
    @TableField("menu_type")
    private Integer menuType;
    
    /**
     * 菜单路径
     */
    @TableField("path")
    private String path;
    
    /**
     * 组件路径
     */
    @TableField("component")
    private String component;
    
    /**
     * 权限标识
     */
    @TableField("permission")
    private String permission;
    
    /**
     * 菜单图标
     */
    @TableField("icon")
    private String icon;
    
    /**
     * 是否外链：0-否，1-是
     */
    @TableField("is_external")
    private Integer isExternal;
    
    /**
     * 是否缓存：0-否，1-是
     */
    @TableField("is_cache")
    private Integer isCache;
    
    /**
     * 是否显示：0-隐藏，1-显示
     */
    @TableField("is_visible")
    private Integer isVisible;
    
    /**
     * 状态：0-禁用，1-启用
     */
    @TableField("status")
    private Integer status;
    
    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;
    
    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
    
}