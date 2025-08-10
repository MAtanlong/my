package com.permission.menu.controller;

import com.permission.common.result.Result;
import com.permission.menu.dto.MenuCreateDTO;
import com.permission.menu.dto.MenuDTO;
import com.permission.menu.dto.MenuQueryDTO;
import com.permission.menu.service.MenuService;
// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameter;
// import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 菜单管理控制器
 *
 * @author system
 * @since 2024-01-01
 */
// @Tag(name = "菜单管理", description = "菜单管理相关接口")
@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
@Validated
public class MenuController {
    
    private final MenuService menuService;
    
    // @Operation(summary = "查询菜单树", description = "根据查询条件获取菜单树结构")
    @PreAuthorize("hasAuthority('menu:list')")
    @GetMapping("/tree")
    public Result<List<MenuDTO>> getMenuTree(MenuQueryDTO query) {
        List<MenuDTO> menuTree = menuService.getMenuTree(query);
        return Result.success(menuTree);
    }
    
    // @Operation(summary = "获取菜单详情", description = "根据菜单ID获取菜单详细信息")
    @PreAuthorize("hasAuthority('menu:view')")
    @GetMapping("/{menuId}")
    public Result<MenuDTO> getMenuById(
            // @Parameter(description = "菜单ID", required = true)
            @PathVariable @NotNull Long menuId) {
        MenuDTO menu = menuService.getMenuById(menuId);
        return Result.success(menu);
    }
    
    // @Operation(summary = "创建菜单", description = "创建新的菜单")
    @PreAuthorize("hasAuthority('menu:create')")
    @PostMapping
    public Result<Long> createMenu(
            // @Parameter(description = "菜单创建信息", required = true)
            @RequestBody @Valid MenuCreateDTO createDTO) {
        Long menuId = menuService.createMenu(createDTO);
        return Result.success(menuId);
    }
    
    // @Operation(summary = "更新菜单", description = "更新指定菜单的信息")
    @PreAuthorize("hasAuthority('menu:update')")
    @PutMapping("/{menuId}")
    public Result<Boolean> updateMenu(
            // @Parameter(description = "菜单ID", required = true)
            @PathVariable @NotNull Long menuId,
            // @Parameter(description = "菜单更新信息", required = true)
            @RequestBody @Valid MenuCreateDTO createDTO) {
        Boolean result = menuService.updateMenu(menuId, createDTO);
        return Result.success(result);
    }
    
    // @Operation(summary = "删除菜单", description = "删除指定的菜单")
    @PreAuthorize("hasAuthority('menu:delete')")
    @DeleteMapping("/{menuId}")
    public Result<Boolean> deleteMenu(
            // @Parameter(description = "菜单ID", required = true)
            @PathVariable @NotNull Long menuId) {
        Boolean result = menuService.deleteMenu(menuId);
        return Result.success(result);
    }
    
    // @Operation(summary = "修改菜单状态", description = "启用或禁用指定菜单")
    @PreAuthorize("hasAuthority('menu:update')")
    @PutMapping("/{menuId}/status")
    public Result<Boolean> changeMenuStatus(
            // @Parameter(description = "菜单ID", required = true)
            @PathVariable @NotNull Long menuId,
            // @Parameter(description = "状态值(0-禁用,1-启用)", required = true)
            @RequestParam @NotNull Integer status) {
        Boolean result = menuService.changeMenuStatus(menuId, status);
        return Result.success(result);
    }
    
    // @Operation(summary = "获取子菜单列表", description = "根据父级ID获取子菜单列表")
    @PreAuthorize("hasAuthority('menu:list')")
    @GetMapping("/children/{parentId}")
    public Result<List<MenuDTO>> getMenusByParentId(
            // @Parameter(description = "父级菜单ID", required = true)
            @PathVariable @NotNull Long parentId) {
        List<MenuDTO> menus = menuService.getMenusByParentId(parentId);
        return Result.success(menus);
    }
    
    // @Operation(summary = "获取用户菜单树", description = "根据用户ID获取用户可访问的菜单树")
    @GetMapping("/user/{userId}/tree")
    public Result<List<MenuDTO>> getMenuTreeByUserId(
            // @Parameter(description = "用户ID", required = true)
            @PathVariable @NotNull Long userId) {
        List<MenuDTO> menuTree = menuService.getMenuTreeByUserId(userId);
        return Result.success(menuTree);
    }
    
    // @Operation(summary = "获取角色菜单列表", description = "根据角色ID获取菜单列表")
    @PreAuthorize("hasAuthority('menu:list')")
    @GetMapping("/role/{roleId}")
    public Result<List<MenuDTO>> getMenusByRoleId(
            // @Parameter(description = "角色ID", required = true)
            @PathVariable @NotNull Long roleId) {
        List<MenuDTO> menus = menuService.getMenusByRoleId(roleId);
        return Result.success(menus);
    }
    
    // @Operation(summary = "获取所有启用菜单", description = "获取所有状态为启用的菜单")
    @GetMapping("/enabled")
    public Result<List<MenuDTO>> getEnabledMenus() {
        List<MenuDTO> menus = menuService.getEnabledMenus();
        return Result.success(menus);
    }
    
    // @Operation(summary = "获取用户权限标识", description = "根据用户ID获取权限标识列表")
    @GetMapping("/user/{userId}/permissions")
    public Result<List<String>> getPermissionsByUserId(
            // @Parameter(description = "用户ID", required = true)
            @PathVariable @NotNull Long userId) {
        List<String> permissions = menuService.getPermissionsByUserId(userId);
        return Result.success(permissions);
    }
    
    // @Operation(summary = "检查菜单编码", description = "检查菜单编码是否已存在")
    @PreAuthorize("hasAuthority('menu:view')")
    @GetMapping("/check-code")
    public Result<Boolean> checkMenuCode(
            // @Parameter(description = "菜单编码", required = true)
            @RequestParam String menuCode,
            // @Parameter(description = "排除的菜单ID")
            @RequestParam(required = false) Long excludeId) {
        Boolean exists = menuService.existsMenuCode(menuCode, excludeId);
        return Result.success(exists);
    }
    
}