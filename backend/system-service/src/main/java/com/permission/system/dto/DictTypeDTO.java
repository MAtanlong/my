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
     * 主键ID
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
     * 状态：0-禁用，1-启用
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
    private LocalDateTime updatedAt;
    
    // Getter and Setter methods
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getDictName() {
        return dictName;
    }
    
    public void setDictName(String dictName) {
        this.dictName = dictName;
    }
    
    public String getDictType() {
        return dictType;
    }
    
    public void setDictType(String dictType) {
        this.dictType = dictType;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public List<DictDataDTO> getDictDataList() {
        return dictDataList;
    }
    
    public void setDictDataList(List<DictDataDTO> dictDataList) {
        this.dictDataList = dictDataList;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}