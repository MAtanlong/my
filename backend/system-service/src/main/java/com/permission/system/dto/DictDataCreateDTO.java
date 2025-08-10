package com.permission.system.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 字典数据创建DTO
 *
 * @author system
 * @since 2024-01-01
 */
@Data
public class DictDataCreateDTO {
    
    /**
     * 字典排序
     */
    @NotNull(message = "字典排序不能为空")
    private Integer dictSort;
    
    /**
     * 字典标签
     */
    @NotBlank(message = "字典标签不能为空")
    @Size(max = 100, message = "字典标签长度不能超过100个字符")
    private String dictLabel;
    
    /**
     * 字典键值
     */
    @NotBlank(message = "字典键值不能为空")
    @Size(max = 100, message = "字典键值长度不能超过100个字符")
    private String dictValue;
    
    /**
     * 字典类型
     */
    @NotBlank(message = "字典类型不能为空")
    @Size(max = 100, message = "字典类型长度不能超过100个字符")
    private String dictType;
    
    /**
     * 样式属性
     */
    @Size(max = 100, message = "样式属性长度不能超过100个字符")
    private String cssClass;
    
    /**
     * 表格回显样式
     */
    @Size(max = 100, message = "表格回显样式长度不能超过100个字符")
    private String listClass;
    
    /**
     * 是否默认（1-是 0-否）
     */
    private Integer isDefault = 0;
    
    /**
     * 状态（0-禁用 1-启用）
     */
    @NotNull(message = "状态不能为空")
    private Integer status;
    
    /**
     * 备注
     */
    @Size(max = 200, message = "备注长度不能超过200个字符")
    private String remark;
    
}