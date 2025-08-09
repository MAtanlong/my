package com.permission.system.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 系统配置创建DTO
 *
 * @author system
 * @since 2024-01-01
 */
@Data
public class SystemConfigCreateDTO {
    
    /**
     * 配置名称
     */
    @NotBlank(message = "配置名称不能为空")
    @Size(max = 100, message = "配置名称长度不能超过100个字符")
    private String configName;
    
    /**
     * 配置键名
     */
    @NotBlank(message = "配置键名不能为空")
    @Size(max = 100, message = "配置键名长度不能超过100个字符")
    private String configKey;
    
    /**
     * 配置键值
     */
    @NotBlank(message = "配置键值不能为空")
    @Size(max = 500, message = "配置键值长度不能超过500个字符")
    private String configValue;
    
    /**
     * 配置类型（1-系统内置 2-用户自定义）
     */
    @NotNull(message = "配置类型不能为空")
    private Integer configType;
    
    /**
     * 状态（0-禁用 1-启用）
     */
    private Integer status = 1;
    
    /**
     * 备注
     */
    @Size(max = 200, message = "备注长度不能超过200个字