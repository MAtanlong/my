package com.permission.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.permission.common.result.Result;
import com.permission.system.dto.SystemConfigCreateDTO;
import com.permission.system.dto.SystemConfigDTO;
import com.permission.system.dto.SystemConfigQueryDTO;
import com.permission.system.service.SystemConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 系统配置控制器
 *
 * @author system
 * @since 2024-01-01
 */
@Tag(name = "系统配置管理", description = "系统配置相关接口")
@RestController
@RequestMapping("/api/system/config")
@RequiredArgsConstructor
@Validated
public class SystemConfigController {
    
    private final SystemConfigService systemConfigService;
    
    @Operation(summary = "分页查询系统配置")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('system:config:list')")
    public Result<IPage<SystemConfigDTO>> getConfigPage(@Valid SystemConfigQueryDTO query) {
        IPage<SystemConfigDTO> page = systemConfigService.getConfigPage(query);
        return Result.success(page);
    }
    
    @Operation(summary = "根据ID查询系统配置详情")
    @GetMapping("/{configId}")
    @PreAuthorize("hasAuthority('system:config:query')")
    public Result<SystemConfigDTO> getConfigById(
            @Parameter(description = "配置ID") @PathVariable @NotNull Long configId) {
        SystemConfigDTO config = systemConfigService.getConfigById(configId);
        return Result.success(config);
    }
    
    @Operation(summary = "根据配置键名查询系统配置")
    @GetMapping("/key/{configKey}")
    @PreAuthorize("hasAuthority('system:config:query')")
    public Result<SystemConfigDTO> getConfigByKey(
            @Parameter(description = "配置键名") @PathVariable @NotEmpty String configKey) {
        SystemConfigDTO config = systemConfigService.getConfigByKey(configKey);
        return Result.success(config);
    }
    
    @Operation(summary = "根据配置键名查询配置值")
    @GetMapping("/value/{configKey}")
    public Result<String> getConfigValue(
            @Parameter(description = "配置键名") @PathVariable @NotEmpty String configKey) {
        String configValue = systemConfigService.getConfigValue(configKey);
        return Result.success(configValue);
    }
    
    @Operation(summary = "创建系统配置")
    @PostMapping
    @PreAuthorize("hasAuthority('system:config:add')")
    public Result<Long> createConfig(@Valid @RequestBody SystemConfigCreateDTO createDTO) {
        Long configId = systemConfigService.createConfig(createDTO);
        return Result.success(configId);
    }
    
    @Operation(summary = "更新系统配置")
    @PutMapping("/{configId}")
    @PreAuthorize("hasAuthority('system:config:edit')")
    public Result<Boolean> updateConfig(
            @Parameter(description = "配置ID") @PathVariable @NotNull Long configId,
            @Valid @RequestBody SystemConfigCreateDTO createDTO) {
        Boolean result = systemConfigService.updateConfig(configId, createDTO);
        return Result.success(result);
    }
    
    @Operation(summary = "删除系统配置")
    @DeleteMapping("/{configId}")
    @PreAuthorize("hasAuthority('system:config:remove')")
    public Result<Boolean> deleteConfig(
            @Parameter(description = "配置ID") @PathVariable @NotNull Long configId) {
        Boolean result = systemConfigService.deleteConfig(configId);
        return Result.success(result);
    }
    
    @Operation(summary = "批量删除系统配置")
    @DeleteMapping("/batch")
    @PreAuthorize("hasAuthority('system:config:remove')")
    public Result<Boolean> deleteConfigs(
            @Parameter(description = "配置ID列表") @RequestBody @NotEmpty List<Long> configIds) {
        Boolean result = systemConfigService.deleteConfigs(configIds);
        return Result.success(result);
    }
    
    @Operation(summary = "修改系统配置状态")
    @PutMapping("/{configId}/status")
    @PreAuthorize("hasAuthority('system:config:edit')")
    public Result<Boolean> changeConfigStatus(
            @Parameter(description = "配置ID") @PathVariable @NotNull Long configId,
            @Parameter(description = "状态") @RequestParam @NotNull Integer status) {
        Boolean result = systemConfigService.changeConfigStatus(configId, status);
        return Result.success(result);
    }
    
    @Operation(summary = "查询所有启用的系统配置")
    @GetMapping("/enabled")
    public Result<List<SystemConfigDTO>> getEnabledConfigs() {
        List<SystemConfigDTO> configs = systemConfigService.getEnabledConfigs();
        return Result.success(configs);
    }
    
    @Operation(summary = "根据配置类型查询系统配置")
    @GetMapping("/type/{configType}")
    @PreAuthorize("hasAuthority('system:config:query')")
    public Result<List<SystemConfigDTO>> getConfigsByType(
            @Parameter(description = "配置类型") @PathVariable @NotNull Integer configType) {
        List<SystemConfigDTO> configs = systemConfigService.getConfigsByType(configType);
        return Result.success(configs);
    }
    
    @Operation(summary = "检查配置键名是否存在")
    @GetMapping("/exists")
    @PreAuthorize("hasAuthority('system:config:query')")
    public Result<Boolean> existsConfigKey(
            @Parameter(description = "配置键名") @RequestParam @NotEmpty String configKey,
            @Parameter(description = "排除的配置ID") @RequestParam(required = false) Long excludeId) {
        Boolean exists = systemConfigService.existsConfigKey(configKey, excludeId);
        return Result.success(exists);
    }
    
    @Operation(summary = "刷新系统配置缓存")
    @PostMapping("/refresh")
    @PreAuthorize("hasAuthority('system:config:edit')")
    public Result<Void> refreshConfigCache() {
        systemConfigService.refreshConfigCache();
        return Result.success();
    }