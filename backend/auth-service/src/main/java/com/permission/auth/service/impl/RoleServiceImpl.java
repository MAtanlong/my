package com.permission.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.permission.auth.dto.RoleCreateDTO;
import com.permission.auth.dto.RoleDTO;
import com.permission.auth.dto.RoleQueryDTO;
import com.permission.auth.entity.Role;
import com.permission.auth.entity.RolePermission;
import com.permission.auth.entity.UserRole;
import com.permission.auth.mapper.RoleMapper;
import com.permission.auth.mapper.RolePermissionMapper;
import com.permission.auth.mapper.UserRoleMapper;
import com.permission.auth.service.RoleService;
import com.permission.common.exception.BusinessException;
import com.permission.common.result.PageResult;
import com.permission.common.result.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色服务实现类
 *
 * @author system
 * @since 2024-01-01
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    
    private final RoleMapper roleMapper;
    private final RolePermissionMapper rolePermissionMapper;
    private final UserRoleMapper userRoleMapper;
    
    @Override
    public PageResult<RoleDTO> getRolePage(RoleQueryDTO query) {
        Page<RoleDTO> page = new Page<>(query.getCurrent(), query.getSize());
        IPage<RoleDTO> result = roleMapper.selectRolePage(page, query);
        return PageResult.of(result.getCurrent(), result.getSize(), result.getTotal(), result.getRecords());
    }
    
    @Override
    public RoleDTO getRoleById(Long roleId) {
        if (roleId == null) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "角色ID不能为空");
        }
        
        RoleDTO roleDTO = roleMapper.selectRoleById(roleId);
        if (roleDTO == null) {
            throw new BusinessException(ResultCode.ROLE_NOT_FOUND);
        }
        
        // 查询角色关联的权限ID列表
        List<Long> permissionIds = rolePermissionMapper.selectPermissionIdsByRoleId(roleId);
        roleDTO.setPermissionIds(permissionIds);
        
        return roleDTO;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createRole(RoleCreateDTO createDTO) {
        // 检查角色编码是否已存在
        if (existsRoleCode(createDTO.getRoleCode(), null)) {
            throw new BusinessException(ResultCode.ROLE_CODE_EXISTS);
        }
        
        // 创建角色
        Role role = new Role();
        BeanUtils.copyProperties(createDTO, role);
        role.setCreatedAt(LocalDateTime.now());
        role.setUpdatedAt(LocalDateTime.now());
        
        roleMapper.insert(role);
        
        // 分配权限
        if (!CollectionUtils.isEmpty(createDTO.getPermissionIds())) {
            assignPermissions(role.getId(), createDTO.getPermissionIds());
        }
        
        return role.getId();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateRole(Long roleId, RoleCreateDTO createDTO) {
        if (roleId == null) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "角色ID不能为空");
        }
        
        // 检查角色是否存在
        Role existingRole = roleMapper.selectById(roleId);
        if (existingRole == null) {
            throw new BusinessException(ResultCode.ROLE_NOT_FOUND);
        }
        
        // 检查角色编码是否已存在（排除当前角色）
        if (existsRoleCode(createDTO.getRoleCode(), roleId)) {
            throw new BusinessException(ResultCode.ROLE_CODE_EXISTS);
        }
        
        // 更新角色信息
        Role role = new Role();
        BeanUtils.copyProperties(createDTO, role);
        role.setId(roleId);
        role.setUpdatedAt(LocalDateTime.now());
        
        roleMapper.updateById(role);
        
        // 重新分配权限
        rolePermissionMapper.deleteByRoleId(roleId);
        if (!CollectionUtils.isEmpty(createDTO.getPermissionIds())) {
            assignPermissions(roleId, createDTO.getPermissionIds());
        }
        
        return true;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteRole(Long roleId) {
        if (roleId == null) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "角色ID不能为空");
        }
        
        // 检查角色是否存在
        Role role = roleMapper.selectById(roleId);
        if (role == null) {
            throw new BusinessException(ResultCode.ROLE_NOT_FOUND);
        }
        
        // 检查是否有用户关联此角色
        List<Long> userIds = userRoleMapper.selectUserIdsByRoleId(roleId);
        if (!CollectionUtils.isEmpty(userIds)) {
            throw new BusinessException(ResultCode.ROLE_HAS_USERS);
        }
        
        // 删除角色权限关联
        rolePermissionMapper.deleteByRoleId(roleId);
        
        // 删除角色
        roleMapper.deleteById(roleId);
        
        return true;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDeleteRoles(List<Long> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "角色ID列表不能为空");
        }
        
        for (Long roleId : roleIds) {
            deleteRole(roleId);
        }
        
        return true;
    }
    
    @Override
    public Boolean changeRoleStatus(Long roleId, Integer status) {
        if (roleId == null) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "角色ID不能为空");
        }
        
        if (status == null || (status != 0 && status != 1)) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "状态值无效");
        }
        
        // 检查角色是否存在
        Role role = roleMapper.selectById(roleId);
        if (role == null) {
            throw new BusinessException(ResultCode.ROLE_NOT_FOUND);
        }
        
        role.setStatus(status);
        role.setUpdatedAt(LocalDateTime.now());
        
        return roleMapper.updateById(role) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean assignPermissions(Long roleId, List<Long> permissionIds) {
        if (roleId == null) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "角色ID不能为空");
        }
        
        // 删除原有权限关联
        rolePermissionMapper.deleteByRoleId(roleId);
        
        // 添加新的权限关联
        if (!CollectionUtils.isEmpty(permissionIds)) {
            List<RolePermission> rolePermissions = permissionIds.stream()
                    .map(permissionId -> {
                        RolePermission rolePermission = new RolePermission();
                        rolePermission.setRoleId(roleId);
                        rolePermission.setPermissionId(permissionId);
                        rolePermission.setCreatedAt(LocalDateTime.now());
                        rolePermission.setUpdatedAt(LocalDateTime.now());
                        return rolePermission;
                    })
                    .collect(Collectors.toList());
            
            rolePermissionMapper.batchInsert(rolePermissions);
        }
        
        return true;
    }
    
    @Override
    public List<RoleDTO> getRolesByUserId(Long userId) {
        if (userId == null) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "用户ID不能为空");
        }
        
        return roleMapper.selectRolesByUserId(userId);
    }
    
    @Override
    public List<RoleDTO> getEnabledRoles() {
        return roleMapper.selectEnabledRoles();
    }
    
    @Override
    public Boolean existsRoleCode(String roleCode, Long excludeId) {
        if (roleCode == null || roleCode.trim().isEmpty()) {
            return false;
        }
        
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getRoleCode, roleCode);
        if (excludeId != null) {
            wrapper.ne(Role::getId, excludeId);
        }
        
        return roleMapper.selectCount(wrapper) > 0;
    }
    
}