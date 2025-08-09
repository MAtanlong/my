package com.permission.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.permission.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典数据实体类
 *
 * @author system
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict_data")
public class DictData extends BaseEntity {
    
    /**
     * 字典排序
     */
    @TableField("dict_sort")
    private Integer dictSort;
    
    /**
     * 字典标签
     */
    @TableField("dict_label")
    private String dictLabel;
    
    /**
     * 字典键值
     */
    @TableField("dict_value")
    private String dictValue;
    
    /**
     * 字典类型
     */
    @TableField("dict_type")
    private String dictType;
    
    /**
     * 样式属性（其他样式扩展）
     */
    @TableField("css_class")
    private String cssClass;
    
    /**
     * 表格回显样式
     */
    @TableField("list_class")
    private String listClass;
    
    /**
     * 是否默认（1-是 0-否）
     */
    @TableField("is_default")
    private Integer isDefault;
    
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