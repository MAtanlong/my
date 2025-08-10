package com.permission.system.dto;

import lombok.Data;

/**
 * 字典数据查询DTO
 *
 * @author system
 * @since 2024-01-01
 */
@Data
public class DictDataQueryDTO {
    
    /**
     * 页码
     */
    private Long current = 1L;
    
    /**
     * 每页大小
     */
    private Long size = 10L;
    
    /**
     * 字典类型
     */
    private String dictType;
    
    /**
     * 字典标签
     */
    private String dictLabel;
    
    /**
     * 状态（0-禁用 1-启用）
     */
    private Integer status;
    
    // Getter and Setter methods
    public Long getCurrent() {
        return current;
    }
    
    public void setCurrent(Long current) {
        this.current = current;
    }
    
    public Long getSize() {
        return size;
    }
    
    public void setSize(Long size) {
        this.size = size;
    }
    
    public String getDictType() {
        return dictType;
    }
    
    public void setDictType(String dictType) {
        this.dictType = dictType;
    }
    
    public String getDictLabel() {
        return dictLabel;
    }
    
    public void setDictLabel(String dictLabel) {
        this.dictLabel = dictLabel;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
}