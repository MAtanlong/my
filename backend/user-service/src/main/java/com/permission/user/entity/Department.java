package com.permission.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.permission.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门实体
 *
 * @author system
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_department")
public class Department extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 部门名称
     */
    @TableField("dept_name")
    private String deptName;
    
    /**
     * 部门编码
     */
    @TableField("dept_code")
    private String deptCode;
    
    /**
     * 父部门ID
     */
    @TableField("parent_id")
    private Long parentId;
    
    /**
     * 部门层级
     */
    @TableField("level")
    private Integer level;
    
    /**
     * 部门路径
     */
    @TableField("path")
    private String path;
    
    /**
     * 负责人
     */
    @TableField("leader")
    private String leader;
    
    /**
     * 联系电话
     */
    @TableField("phone")
    private String phone;
    
    /**
     * 邮箱
     */
    @TableField("email")
    private String email;
    
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