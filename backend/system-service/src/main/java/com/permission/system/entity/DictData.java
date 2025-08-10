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
     * 列表样式
     */
    @TableField("list_class")
    private String listClass;
    
    /**
     * 是否默认（Y是 N否）
     */
    @TableField("is_default")
    private String isDefault;
    
    /**
     * 状态（0正常 1停用）
     */
    @TableField("status")
    private Integer status;
    
    // Getter and Setter methods
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
    
    public String getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
}