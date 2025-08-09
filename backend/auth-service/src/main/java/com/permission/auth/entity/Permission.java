package com.permission.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.permission.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限实体类
 *
 * @author system
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_permission")
public class Permission extends BaseEntity {
    
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
    
}