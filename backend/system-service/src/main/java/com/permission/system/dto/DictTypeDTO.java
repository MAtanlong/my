package com.permission.system.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 字典类型DTO
 *
 * @author system
 * @since 2024-01-01
 */
@Data
public class DictTypeDTO {
    
    /**
     * 字典类型ID
     */
    private Long id;
    
    /**
     * 字典名称
     */
    private String dictName;
    
    /**
     * 字典类型
     */
    private String dictType;
    
    /**
     * 状态（0-禁用 1-启用）
     */
    private Integer status;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 字典数据列表
     */
    private List<DictDataDTO> dictDataList;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    private Local