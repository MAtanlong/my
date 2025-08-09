package com.permission.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.permission.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户角色关联实体类
 *
 * @author system
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_role")
public class UserRole extends BaseEntity {
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 角色ID
     */
    private Long roleId;
    
}