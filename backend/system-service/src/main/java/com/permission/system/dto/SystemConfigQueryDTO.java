package com.permission.system.dto;

import lombok.Data;

/**
 * 系统配置查询DTO
 *
 * @author system
 * @since 2024-01-01
 */
@Data
public class SystemConfigQueryDTO {
    
    /**
     * 搜索关键词（配置名称或键名）
     */
    private String keyword;
    
    /**
     * 配置类型（1-系统内置 2-用户自定义）
     */
    private Integer configType;
    
    /**
     * 状态（0-禁用 1-启用）
     */
    private Integer status;
    
    /**
     * 页码
     */
    private Integer page = 1;
    
    /**
     * 每页大小
     */
    private Integer size = 10;
    
}