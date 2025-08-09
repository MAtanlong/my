package com.permission.system.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 字典数据DTO
 *
 * @author system
 * @since 2024-01-01
 */
@Data
public class DictDataDTO {
    
    /**
     * 字典数据ID
     */
    private Long id;
    
    /**
     * 字典排序
     */
    private Integer dictSort;
    
    /**
     * 字典标签
     */
    private String dictLabel;
    
    /**
     * 字典键值
     */
    private String dictValue;
    
    /**
     * 字典类型
     */
    private String dictType;
    
    /**
     * 样式属性
     */
    private String cssClass;
    
    /**
     * 表格回显样式
     */
    private String listClass;
    
    /**
     * 是否默认（1-是 0-否）
     */
    private Integer isDefault;
    
    /**
     * 状态（0-禁用 1-启用）
     */
    private Integer status;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
    
}