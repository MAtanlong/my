package com.permission.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.permission.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统配置实体类
 *
 * @author system
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_config")
public class SystemConfig extends BaseEntity {
    
    /**
     * 配置名称
     */
    @TableField("config_name")
    private String configName;
    
    /**
     * 配置键名
     */
    @TableField("config_key")
    private String configKey;
    
    /**
     * 配置键值
     */
    @TableField("config_value")
    private String configValue;
    
    /**
     * 配置类型（1-系统内置 2-用户自定义）
     */
    @TableField("config_type")
    private Integer configType;
    
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