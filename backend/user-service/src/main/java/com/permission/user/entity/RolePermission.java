package com.permission.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.permission.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色权限关联实体
 *
 * @author system
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role_permission")
public class RolePermission extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 角色ID
     */
    @TableField("role_id")
    private Long roleId;
    
    /**
     * 权限ID
     */
    @TableField("permission_id")
    private Long permissionId;
    
}