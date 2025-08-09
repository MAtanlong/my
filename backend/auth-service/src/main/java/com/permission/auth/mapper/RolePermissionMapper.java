package com.permission.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.permission.auth.entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色权限关联Mapper接口
 *
 * @author system
 * @since 2024-01-01
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    
    /**
     * 根据角色ID删除角色权限关联
     *
     * @param roleId 角色ID
     * @return 删除数量
     */
    int deleteByRoleId(@Param("roleId") Long roleId);
    
    /**
     * 根据权限ID删除角色权限关联
     *
     * @param permissionId 权限ID
     * @return 删除数量
     */
    int deleteByPermissionId(@Param("permissionId") Long permissionId);
    
    /**
     * 批量插入角色权限关联
     *
     * @param rolePermissions 角色权限关联列表
     * @return 插入数量
     */
    int batchInsert(@Param("rolePermissions") List<RolePermission> rolePermissions);
    
    /**
     * 根据角色ID查询权限ID列表
     *
     * @param roleId 角色ID
     * @return 权限ID列表
     */
    List<Long> selectPermissionIdsByRoleId(@Param("roleId") Long roleId);
    
    /**
     * 根据权限ID查询角色ID列表
     *
     * @param permissionId 权限ID
     * @return 角色ID列表
     */
    List<Long> selectRoleIdsByPermissionId(@Param("permissionId") Long permissionId);
    
}