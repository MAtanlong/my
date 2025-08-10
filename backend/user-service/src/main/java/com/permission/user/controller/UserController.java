package com.permission.user.controller;

import com.permission.common.result.PageResult;
import com.permission.common.result.Result;
import com.permission.user.dto.UserCreateDTO;
import com.permission.user.dto.UserDTO;
import com.permission.user.dto.UserQueryDTO;
import com.permission.user.service.UserService;
// Swagger imports removed
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 用户控制器
 */
@Slf4j
// @Tag(name = "用户管理", description = "用户管理相关接口")
@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    
    @Autowired
    private UserService userService;
    
    // @Operation(summary = "分页查询用户列表")
    @PreAuthorize("hasAuthority('user:list')")
    @GetMapping("/list")
    public Result<PageResult<UserDTO>> getUserPage(UserQueryDTO query) {
        PageResult<UserDTO> result = userService.getUserPage(query);
        return Result.success(result);
    }
    
    // @Operation(summary = "根据ID查询用户详情")
    @PreAuthorize("hasAuthority('user:view')")
    @GetMapping("/{userId}")
    public Result<UserDTO> getUserById(
            @PathVariable @NotBlank String userId) {
        UserDTO user = userService.getUserById(userId);
        return Result.success(user);
    }
    
    // @Operation(summary = "创建用户")
    @PreAuthorize("hasAuthority('user:create')")
    @PostMapping("/create")
    public Result<String> createUser(@Valid @RequestBody UserCreateDTO createDTO) {
        String userId = userService.createUser(createDTO);
        return Result.success("用户创建成功", userId);
    }
    
    // @Operation(summary = "更新用户信息")
    @PreAuthorize("hasAuthority('user:update')")
    @PutMapping("/{userId}")
    public Result<Void> updateUser(
            @PathVariable @NotBlank String userId,
            @Valid @RequestBody UserDTO userDTO) {
        userService.updateUser(userId, userDTO);
        return Result.success();
    }
    
    // @Operation(summary = "删除用户")
    @PreAuthorize("hasAuthority('user:delete')")
    @DeleteMapping("/{userId}")
    public Result<Void> deleteUser(
            @PathVariable @NotBlank String userId) {
        userService.deleteUser(userId);
        return Result.success();
    }
    
    // @Operation(summary = "批量删除用户")
    @PreAuthorize("hasAuthority('user:delete')")
    @DeleteMapping("/batch")
    public Result<Void> batchDeleteUsers(
            @RequestBody @NotEmpty List<String> userIds) {
        userService.batchDeleteUsers(userIds);
        return Result.success();
    }
    
    // @Operation(summary = "启用/禁用用户")
    @PreAuthorize("hasAuthority('user:update')")
    @PutMapping("/{userId}/status")
    public Result<Void> updateUserStatus(
            @PathVariable @NotBlank String userId,
            @RequestParam @NotBlank String status) {
        userService.updateUserStatus(userId, status);
        return Result.success();
    }
    
    // @Operation(summary = "重置用户密码")
    @PreAuthorize("hasAuthority('user:reset_password')")
    @PutMapping("/{userId}/password/reset")
    public Result<Void> resetPassword(
            @PathVariable @NotBlank String userId,
            @RequestParam @NotBlank String newPassword) {
        userService.resetPassword(userId, newPassword);
        return Result.success();
    }
    
    // @Operation(summary = "分配用户角色")
    @PreAuthorize("hasAuthority('user:assign_role')")
    @PutMapping("/{userId}/roles")
    public Result<Void> assignRoles(
            @PathVariable @NotBlank String userId,
            @RequestBody @NotEmpty List<String> roleIds) {
        userService.assignRoles(userId, roleIds);
        return Result.success();
    }
    
    // @Operation(summary = "根据部门ID查询用户列表")
    @PreAuthorize("hasAuthority('user:list')")
    @GetMapping("/department/{departmentId}")
    public Result<List<UserDTO>> getUsersByDepartmentId(
            @PathVariable @NotBlank String departmentId) {
        List<UserDTO> users = userService.getUsersByDepartmentId(departmentId);
        return Result.success(users);
    }
    
    // @Operation(summary = "根据角色ID查询用户列表")
    @PreAuthorize("hasAuthority('user:list')")
    @GetMapping("/role/{roleId}")
    public Result<List<UserDTO>> getUsersByRoleId(
            @PathVariable @NotBlank String roleId) {
        List<UserDTO> users = userService.getUsersByRoleId(roleId);
        return Result.success(users);
    }
    
    // @Operation(summary = "检查用户名是否存在")
    @PreAuthorize("hasAuthority('user:view')")
    @GetMapping("/check/username")
    public Result<Boolean> checkUsername(
            @RequestParam @NotBlank String username) {
        boolean exists = userService.existsByUsername(username);
        return Result.success(exists);
    }
    
    // @Operation(summary = "检查邮箱是否存在")
    @PreAuthorize("hasAuthority('user:view')")
    @GetMapping("/check/email")
    public Result<Boolean> checkEmail(
            @RequestParam @NotBlank String email) {
        boolean exists = userService.existsByEmail(email);
        return Result.success(exists);
    }
}