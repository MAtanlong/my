package com.permission.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.permission.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色实体
 *
 * @author system
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role")
public class Role extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;
    
    /**
     * 角色编码
     */
    @TableField("role_code")
    private String roleCode;
    
    /**
     * 角色描述
     */
    @TableField("description")
    private String description;
    
    /**
     * 数据范围：1-全部数据权限，2-自定数据权限，3-本部门数据权限，4-本部门及以下数据权限，5-仅本人数据权限
     */
    @TableField("data_scope")
    private Integer dataScope;
    
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