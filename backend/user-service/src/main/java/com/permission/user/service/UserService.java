package com.permission.user.service;

import com.permission.common.result.PageResult;
import com.permission.user.dto.UserCreateDTO;
import com.permission.user.dto.UserDTO;
import com.permission.user.dto.UserQueryDTO;
import com.permission.user.entity.User;

import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService {
    
    /**
     * 分页查询用户列表
     */
    PageResult<UserDTO> getUserPage(UserQueryDTO query);
    
    /**
     * 根据ID查询用户详情
     */
    UserDTO getUserById(String userId);
    
    /**
     * 创建用户
     */
    String createUser(UserCreateDTO createDTO);
    
    /**
     * 更新用户信息
     */
    void updateUser(String userId, UserDTO userDTO);
    
    /**
     * 删除用户
     */
    void deleteUser(String userId);
    
    /**
     * 批量删除用户
     */
    void batchDeleteUsers(List<String> userIds);
    
    /**
     * 启用/禁用用户
     */
    void updateUserStatus(String userId, String status);
    
    /**
     * 重置用户密码
     */
    void resetPassword(String userId, String newPassword);
    
    /**
     * 分配用户角色
     */
    void assignRoles(String userId, List<String> roleIds);
    
    /**
     * 根据用户名查询用户
     */
    User getUserByUsername(String username);
    
    /**
     * 根据邮箱查询用户
     */
    User getUserByEmail(String email);
    
    /**
     * 根据部门ID查询用户列表
     */
    List<UserDTO> getUsersByDepartmentId(String departmentId);
    
    /**
     * 根据角色ID查询用户列表
     */
    List<UserDTO> getUsersByRoleId(String roleId);
    
    /**
     * 检查用户名是否存在
     */
    boolean existsByUsername(String username);
    
    /**
     * 检查邮箱是否存在
     */
    boolean existsByEmail(String email);
}