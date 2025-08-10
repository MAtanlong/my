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
    
    // Getter and Setter methods
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getDictSort() {
        return dictSort;
    }
    
    public void setDictSort(Integer dictSort) {
        this.dictSort = dictSort;
    }
    
    public String getDictLabel() {
        return dictLabel;
    }
    
    public void setDictLabel(String dictLabel) {
        this.dictLabel = dictLabel;
    }
    
    public String getDictValue() {
        return dictValue;
    }
    
    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }
    
    public String getDictType() {
        return dictType;
    }
    
    public void setDictType(String dictType) {
        this.dictType = dictType;
    }
    
    public String getCssClass() {
        return cssClass;
    }
    
    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }
    
    public String getListClass() {
        return listClass;
    }
    
    public void setListClass(String listClass) {
        this.listClass = listClass;
    }
    
    public Integer getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
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