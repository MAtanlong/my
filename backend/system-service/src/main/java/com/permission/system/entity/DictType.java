package com.permission.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.permission.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典类型实体类
 *
 * @author system
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict_type")
public class DictType extends BaseEntity {
    
    /**
     * 字典名称
     */
    @TableField("dict_name")
    private String dictName;
    
    /**
     * 字典类型
     */
    @TableField("dict_type")
    private String dictType;
    
    /**
     * 状态（0-禁用 1-启用）
     */
    @TableField("status")
    private Integer status;
    
    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
    
}