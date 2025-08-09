package com.permission.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.permission.auth.dto.RoleDTO;
import com.permission.auth.dto.RoleQueryDTO;
import com.permission.auth.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色Mapper接口
 *
 * @author system
 * @since 2024-01-01
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    
    /**
     * 分页查询角色列表
     *
     * @param page  分页参数
     * @param query 查询条件
     * @return 角色列表
     */
    IPage<RoleDTO> selectRolePage(Page<RoleDTO> page, @Param("query") RoleQueryDTO query);
    
    /**
     * 根据角色ID查询角色详情
     *
     * @param roleId 角色ID
     * @return 角色详情
     */
    RoleDTO selectRoleById(@Param("roleId") Long roleId);
    
    /**
     * 根据角色编码查询角色
     *
     * @param roleCode 角色编码
     * @return 角色信息
     */
    Role selectByRoleCode(@Param("roleCode") String roleCode);
    
    /**
     * 根据用户ID查询角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<RoleDTO> selectRolesByUserId(@Param("userId") Long userId);
    
    /**
     * 查询所有启用的角色
     *
     * @return 角色列表
     */
    List<RoleDTO> selectEnabledRoles();
    
}