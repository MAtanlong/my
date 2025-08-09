package com.permission.system.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统配置DTO
 *
 * @author system
 * @since 2024-01-01
 */
@Data
public class SystemConfigDTO {
    
    /**
     * 配置ID
     */
    private Long id;
    
    /**
     * 配置名称
     */
    private String configName;
    
    /**
     * 配置键名
     */
    private String configKey;
    
    /**
     * 配置键值
     */
    private String configValue;
    
    /**
     * 配置类型（1-系统内置 2-用户自定义）
     */
    private Integer configType;
    
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