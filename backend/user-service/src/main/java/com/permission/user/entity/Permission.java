package com.permission.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.permission.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限实体
 *
 * @author system
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_permission")
public class Permission extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 权限名称
     */
    @TableField("permission_name")
    private String permissionName;
    
    /**
     * 权限编码
     */
    @TableField("permission_code")
    private String permissionCode;
    
    /**
     * 权限类型：1-菜单，2-按钮，3-接口
     */
    @TableField("permission_type")
    private Integer permissionType;
    
    /**
     * 父权限ID
     */
    @TableField("parent_id")
    private Long parentId;
    
    /**
     * 权限路径
     */
    @TableField("path")
    private String path;
    
    /**
     * 权限描述
     */
    @TableField("description")
    private String description;
    
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