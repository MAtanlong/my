package com.permission.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.permission.auth.dto.PermissionDTO;
import com.permission.auth.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 权限Mapper接口
 *
 * @author system
 * @since 2024-01-01
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    
    /**
     * 查询所有权限（树形结构）
     *
     * @return 权限树
     */
    List<PermissionDTO> selectPermissionTree();
    
    /**
     * 根据权限编码查询权限
     *
     * @param permissionCode 权限编码
     * @return 权限信息
     */
    Permission selectByPermissionCode(@Param("permissionCode") String permissionCode);
    
    /**
     * 根据角色ID查询权限列表
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    List<PermissionDTO> selectPermissionsByRoleId(@Param("roleId") Long roleId);
    
    /**
     * 根据用户ID查询权限列表
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    List<PermissionDTO> selectPermissionsByUserId(@Param("userId") Long userId);
    
    /**
     * 根据父级ID查询子权限列表
     *
     * @param parentId 父级ID
     * @return 权限列表
     */
    List<PermissionDTO> selectPermissionsByParentId(@Param("parentId") Long parentId);
    
    /**
     * 查询所有启用的权限
     *
     * @return 权限列表
     */
    List<PermissionDTO> selectEnabledPermissions();
    
}