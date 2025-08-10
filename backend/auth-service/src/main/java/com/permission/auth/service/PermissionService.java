package com.permission.auth.service;

import com.permission.auth.dto.PermissionCreateDTO;
import com.permission.auth.dto.PermissionDTO;

import java.util.List;

/**
 * 权限服务接口
 *
 * @author system
 * @since 2024-01-01
 */
public interface PermissionService {
    
    /**
     * 查询权限树
     *
     * @return 权限树
     */
    List<PermissionDTO> getPermissionTree();
    
    /**
     * 根据权限ID查询权限详情
     *
     * @param permissionId 权限ID
     * @return 权限详情
     */
    PermissionDTO getPermissionById(Long permissionId);
    
    /**
     * 创建权限
     *
     * @param createDTO 创建参数
     * @return 权限ID
     */
    Long createPermission(PermissionCreateDTO createDTO);
    
    /**
     * 更新权限
     *
     * @param permissionId 权限ID
     * @param createDTO    更新参数
     * @return 是否成功
     */
    Boolean updatePermission(Long permissionId, PermissionCreateDTO createDTO);
    
    /**
     * 删除权限
     *
     * @param permissionId 权限ID
     * @return 是否成功
     */
    Boolean deletePermission(Long permissionId);
    
    /**
     * 修改权限状态
     *
     * @param permissionId 权限ID
     * @param status       状态
     * @return 是否成功
     */
    Boolean changePermissionStatus(Long permissionId, Integer status);
    
    /**
     * 根据角色ID查询权限列表
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    List<PermissionDTO> getPermissionsByRoleId(Long roleId);
    
    /**
     * 根据用户ID查询权限列表
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    List<PermissionDTO> getPermissionsByUserId(Long userId);
    
    /**
     * 根据父级ID查询子权限列表
     *
     * @param parentId 父级ID
     * @return 权限列表
     */
    List<PermissionDTO> getPermissionsByParentId(Long parentId);
    
    /**
     * 查询所有启用的权限
     *
     * @return 权限列表
     */
    List<PermissionDTO> getEnabledPermissions();
    
    /**
     * 检查权限编码是否存在
     *
     * @param permissionCode 权限编码
     * @param excludeId      排除的权限ID
     * @return 是否存在
     */
    Boolean existsPermissionCode(String permissionCode, String excludeId);
}