package com.permission.auth.service;

import com.permission.auth.dto.RoleCreateDTO;
import com.permission.auth.dto.RoleDTO;
import com.permission.auth.dto.RoleQueryDTO;
import com.permission.common.result.PageResult;

import java.util.List;

/**
 * 角色服务接口
 *
 * @author system
 * @since 2024-01-01
 */
public interface RoleService {
    
    /**
     * 分页查询角色列表
     *
     * @param query 查询条件
     * @return 分页结果
     */
    PageResult<RoleDTO> getRolePage(RoleQueryDTO query);
    
    /**
     * 根据角色ID查询角色详情
     *
     * @param roleId 角色ID
     * @return 角色详情
     */
    RoleDTO getRoleById(Long roleId);
    
    /**
     * 创建角色
     *
     * @param createDTO 创建参数
     * @return 角色ID
     */
    Long createRole(RoleCreateDTO createDTO);
    
    /**
     * 更新角色
     *
     * @param roleId    角色ID
     * @param createDTO 更新参数
     * @return 是否成功
     */
    Boolean updateRole(Long roleId, RoleCreateDTO createDTO);
    
    /**
     * 删除角色
     *
     * @param roleId 角色ID
     * @return 是否成功
     */
    Boolean deleteRole(Long roleId);
    
    /**
     * 批量删除角色
     *
     * @param roleIds 角色ID列表
     * @return 是否成功
     */
    Boolean batchDeleteRoles(List<Long> roleIds);
    
    /**
     * 修改角色状态
     *
     * @param roleId 角色ID
     * @param status 状态
     * @return 是否成功
     */
    Boolean changeRoleStatus(Long roleId, Integer status);
    
    /**
     * 分配权限给角色
     *
     * @param roleId        角色ID
     * @param permissionIds 权限ID列表
     * @return 是否成功
     */
    Boolean assignPermissions(Long roleId, List<Long> permissionIds);
    
    /**
     * 根据用户ID查询角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<RoleDTO> getRolesByUserId(Long userId);
    
    /**
     * 查询所有启用的角色
     *
     * @return 角色列表
     */
    List<RoleDTO> getEnabledRoles();
    
    /**
     * 检查角色编码是否存在
     *
     * @param roleCode 角色编码
     * @param excludeId 排除的角色ID
     * @return 是否存在
     */
    Boolean existsRoleCode(String roleCode, Long excludeId);
    
}