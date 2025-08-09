package com.permission.auth.controller;

import com.permission.auth.dto.PermissionCreateDTO;
import com.permission.auth.dto.PermissionDTO;
import com.permission.auth.service.PermissionService;
import com.permission.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 权限管理控制器
 *
 * @author system
 * @since 2024-01-01
 */
@Tag(name = "权限管理", description = "权限管理相关接口")
@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
@Validated
public class PermissionController {
    
    private final PermissionService permissionService;
    
    @Operation(summary = "查询权限树")
    @PreAuthorize("hasAuthority('permission:list')")
    @GetMapping("/tree")
    public Result<List<PermissionDTO>> getPermissionTree() {
        List<PermissionDTO> result = permissionService.getPermissionTree();
        return Result.success(result);
    }
    
    @Operation(summary = "根据权限ID查询权限详情")
    @PreAuthorize("hasAuthority('permission:view')")
    @GetMapping("/{permissionId}")
    public Result<PermissionDTO> getPermissionById(
            @Parameter(description = "权限ID", required = true) @PathVariable @NotNull Long permissionId) {
        PermissionDTO result = permissionService.getPermissionById(permissionId);
        return Result.success(result);
    }
    
    @Operation(summary = "创建权限")
    @PreAuthorize("hasAuthority('permission:create')")
    @PostMapping
    public Result<Long> createPermission(@Valid @RequestBody PermissionCreateDTO createDTO) {
        Long permissionId = permissionService.createPermission(createDTO);
        return Result.success(permissionId);
    }
    
    @Operation(summary = "更新权限")
    @PreAuthorize("hasAuthority('permission:update')")
    @PutMapping("/{permissionId}")
    public Result<Boolean> updatePermission(
            @Parameter(description = "权限ID", required = true) @PathVariable @NotNull Long permissionId,
            @Valid @RequestBody PermissionCreateDTO createDTO) {
        Boolean result = permissionService.updatePermission(permissionId, createDTO);
        return Result.success(result);
    }
    
    @Operation(summary = "删除权限")
    @PreAuthorize("hasAuthority('permission:delete')")
    @DeleteMapping("/{permissionId}")
    public Result<Boolean> deletePermission(
            @Parameter(description = "权限ID", required = true) @PathVariable @NotNull Long permissionId) {
        Boolean result = permissionService.deletePermission(permissionId);
        return Result.success(result);
    }
    
    @Operation(summary = "修改权限状态")
    @PreAuthorize("hasAuthority('permission:update')")
    @PutMapping("/{permissionId}/status")
    public Result<Boolean> changePermissionStatus(
            @Parameter(description = "权限ID", required = true) @PathVariable @NotNull Long permissionId,
            @Parameter(description = "状态", required = true) @RequestParam @NotNull Integer status) {
        Boolean result = permissionService.changePermissionStatus(permissionId, status);
        return Result.success(result);
    }
    
    @Operation(summary = "根据角色ID查询权限列表")
    @PreAuthorize("hasAuthority('permission:list')")
    @GetMapping("/role/{roleId}")
    public Result<List<PermissionDTO>> getPermissionsByRoleId(
            @Parameter(description = "角色ID", required = true) @PathVariable @NotNull Long roleId) {
        List<PermissionDTO> result = permissionService.getPermissionsByRoleId(roleId);
        return Result.success(result);
    }
    
    @Operation(summary = "根据用户ID查询权限列表")
    @PreAuthorize("hasAuthority('permission:list')")
    @GetMapping("/user/{userId}")
    public Result<List<PermissionDTO>> getPermissionsByUserId(
            @Parameter(description = "用户ID", required = true) @PathVariable @NotNull Long userId) {
        List<PermissionDTO> result = permissionService.getPermissionsByUserId(userId);
        return Result.success(result);
    }
    
    @Operation(summary = "根据父级ID查询子权限列表")
    @PreAuthorize("hasAuthority('permission:list')")
    @GetMapping("/parent/{parentId}")
    public Result<List<PermissionDTO>> getPermissionsByParentId(
            @Parameter(description = "父级ID", required = true) @PathVariable @NotNull Long parentId) {
        List<PermissionDTO> result = permissionService.getPermissionsByParentId(parentId);
        return Result.success(result);
    }
    
    @Operation(summary = "查询所有启用的权限")
    @PreAuthorize("hasAuthority('permission:list')")
    @GetMapping("/enabled")
    public Result<List<PermissionDTO>> getEnabledPermissions() {
        List<PermissionDTO> result = permissionService.getEnabledPermissions();
        return Result.success(result);
    }
    
    @Operation(summary = "检查权限编码是否存在")
    @PreAuthorize("hasAuthority('permission:view')")
    @GetMapping("/exists")
    public Result<Boolean> existsPermissionCode(
            @Parameter(description = "权限编码", required = true) @RequestParam String permissionCode,
            @Parameter(description = "排除的权限ID") @RequestParam(required = false) Long excludeId) {
        Boolean result = permissionService.existsPermissionCode(permissionCode, excludeId);
        return Result.success(result);
    }
    
}