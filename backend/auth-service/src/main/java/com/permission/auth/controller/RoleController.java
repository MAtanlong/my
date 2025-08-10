package com.permission.auth.controller;

import com.permission.auth.dto.RoleCreateDTO;
import com.permission.auth.dto.RoleDTO;
import com.permission.auth.dto.RoleQueryDTO;
import com.permission.auth.service.RoleService;
import com.permission.common.result.PageResult;
import com.permission.common.result.Result;
// import io.swagger.annotations.Api;
// import io.swagger.annotations.ApiOperation;
// import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色管理控制器
 *
 * @author system
 * @since 2024-01-01
 */
// @Api(tags = "角色管理")
@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@Validated
public class RoleController {
    
    private final RoleService roleService;
    
    // @ApiOperation("分页查询角色列表")
    @PreAuthorize("hasAuthority('role:list')")
    @PostMapping("/page")
    public Result<PageResult<RoleDTO>> getRolePage(@Valid @RequestBody RoleQueryDTO query) {
        PageResult<RoleDTO> result = roleService.getRolePage(query);
        return Result.success(result);
    }
    
    // @ApiOperation("根据角色ID查询角色详情")
    @PreAuthorize("hasAuthority('role:view')")
    @GetMapping("/{roleId}")
    public Result<RoleDTO> getRoleById(
            /* @ApiParam(value = "角色ID", required = true) */ @PathVariable @NotNull Long roleId) {
        RoleDTO result = roleService.getRoleById(roleId);
        return Result.success(result);
    }
    
    // @ApiOperation("创建角色")
    @PreAuthorize("hasAuthority('role:create')")
    @PostMapping
    public Result<Long> createRole(@Valid @RequestBody RoleCreateDTO createDTO) {
        Long roleId = roleService.createRole(createDTO);
        return Result.success(roleId);
    }
    
    // @ApiOperation("更新角色")
    @PreAuthorize("hasAuthority('role:update')")
    @PutMapping("/{roleId}")
    public Result<Boolean> updateRole(
            /* @ApiParam(value = "角色ID", required = true) */ @PathVariable @NotNull Long roleId,
            @Valid @RequestBody RoleCreateDTO createDTO) {
        Boolean result = roleService.updateRole(roleId, createDTO);
        return Result.success(result);
    }
    
    // @ApiOperation("删除角色")
    @PreAuthorize("hasAuthority('role:delete')")
    @DeleteMapping("/{roleId}")
    public Result<Boolean> deleteRole(
            /* @ApiParam(value = "角色ID", required = true) */ @PathVariable @NotNull Long roleId) {
        Boolean result = roleService.deleteRole(roleId);
        return Result.success(result);
    }
    
    // @ApiOperation("批量删除角色")
    @PreAuthorize("hasAuthority('role:delete')")
    @DeleteMapping("/batch")
    public Result<Boolean> batchDeleteRoles(
            /* @ApiParam(value = "角色ID列表", required = true) */ @RequestBody @NotEmpty List<Long> roleIds) {
        Boolean result = roleService.batchDeleteRoles(roleIds);
        return Result.success(result);
    }
    
    // @ApiOperation("修改角色状态")
    @PreAuthorize("hasAuthority('role:update')")
    @PutMapping("/{roleId}/status")
    public Result<Boolean> changeRoleStatus(
            /* @ApiParam(value = "角色ID", required = true) */ @PathVariable @NotNull Long roleId,
            /* @ApiParam(value = "状态", required = true) */ @RequestParam @NotNull Integer status) {
        Boolean result = roleService.changeRoleStatus(roleId, status);
        return Result.success(result);
    }
    
    // @ApiOperation("分配权限给角色")
    @PreAuthorize("hasAuthority('role:assign_permission')")
    @PutMapping("/{roleId}/permissions")
    public Result<Boolean> assignPermissions(
            /* @ApiParam(value = "角色ID", required = true) */ @PathVariable @NotNull Long roleId,
            /* @ApiParam(value = "权限ID列表", required = true) */ @RequestBody List<Long> permissionIds) {
        Boolean result = roleService.assignPermissions(roleId, permissionIds);
        return Result.success(result);
    }
    
    // @ApiOperation("根据用户ID查询角色列表")
    @PreAuthorize("hasAuthority('role:list')")
    @GetMapping("/user/{userId}")
    public Result<List<RoleDTO>> getRolesByUserId(
            /* @ApiParam(value = "用户ID", required = true) */ @PathVariable @NotNull Long userId) {
        List<RoleDTO> result = roleService.getRolesByUserId(userId);
        return Result.success(result);
    }
    
    // @ApiOperation("查询所有启用的角色")
    @PreAuthorize("hasAuthority('role:list')")
    @GetMapping("/enabled")
    public Result<List<RoleDTO>> getEnabledRoles() {
        List<RoleDTO> result = roleService.getEnabledRoles();
        return Result.success(result);
    }
    
    // @ApiOperation("检查角色编码是否存在")
    @PreAuthorize("hasAuthority('role:view')")
    @GetMapping("/exists")
    public Result<Boolean> existsRoleCode(
            /* @ApiParam(value = "角色编码", required = true) */ @RequestParam String roleCode,
            /* @ApiParam(value = "排除的角色ID") */ @RequestParam(required = false) Long excludeId) {
        Boolean result = roleService.existsRoleCode(roleCode, excludeId);
        return Result.success(result);
    }
    
}