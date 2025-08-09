package com.permission.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.permission.auth.dto.PermissionCreateDTO;
import com.permission.auth.dto.PermissionDTO;
import com.permission.auth.entity.Permission;
import com.permission.auth.entity.RolePermission;
import com.permission.auth.mapper.PermissionMapper;
import com.permission.auth.mapper.RolePermissionMapper;
import com.permission.auth.service.PermissionService;
import com.permission.common.exception.BusinessException;
import com.permission.common.result.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 权限服务实现类
 *
 * @author system
 * @since 2024-01-01
 */
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    
    private final PermissionMapper permissionMapper;
    private final RolePermissionMapper rolePermissionMapper;
    
    @Override
    public List<PermissionDTO> getPermissionTree() {
        List<PermissionDTO> allPermissions = permissionMapper.selectPermissionTree();
        return buildPermissionTree(allPermissions, 0L);
    }
    
    @Override
    public PermissionDTO getPermissionById(Long permissionId) {
        if (permissionId == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "权限ID不能为空");
        }
        
        Permission permission = permissionMapper.selectById(permissionId);
        if (permission == null) {
            throw new BusinessException(ResultCode.PERMISSION_NOT_FOUND);
        }
        
        PermissionDTO permissionDTO = new PermissionDTO();
        BeanUtils.copyProperties(permission, permissionDTO);
        
        return permissionDTO;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createPermission(PermissionCreateDTO createDTO) {
        // 检查权限编码是否已存在
        if (existsPermissionCode(createDTO.getPermissionCode(), null)) {
            throw new BusinessException(ResultCode.PERMISSION_CODE_EXISTS);
        }
        
        // 如果有父级权限，检查父级权限是否存在
        if (createDTO.getParentId() != null && createDTO.getParentId() > 0) {
            Permission parentPermission = permissionMapper.selectById(createDTO.getParentId());
            if (parentPermission == null) {
                throw new BusinessException(ResultCode.PERMISSION_PARENT_NOT_FOUND);
            }
        }
        
        // 创建权限
        Permission permission = new Permission();
        BeanUtils.copyProperties(createDTO, permission);
        if (permission.getParentId() == null) {
            permission.setParentId(0L);
        }
        permission.setCreatedAt(LocalDateTime.now());
        permission.setUpdatedAt(LocalDateTime.now());
        
        permissionMapper.insert(permission);
        
        return permission.getId();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updatePermission(Long permissionId, PermissionCreateDTO createDTO) {
        if (permissionId == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "权限ID不能为空");
        }
        
        // 检查权限是否存在
        Permission existingPermission = permissionMapper.selectById(permissionId);
        if (existingPermission == null) {
            throw new BusinessException(ResultCode.PERMISSION_NOT_FOUND);
        }
        
        // 检查权限编码是否已存在（排除当前权限）
        if (existsPermissionCode(createDTO.getPermissionCode(), permissionId)) {
            throw new BusinessException(ResultCode.PERMISSION_CODE_EXISTS);
        }
        
        // 如果有父级权限，检查父级权限是否存在且不能是自己
        if (createDTO.getParentId() != null && createDTO.getParentId() > 0) {
            if (createDTO.getParentId().equals(permissionId)) {
                throw new BusinessException(ResultCode.PARAM_ERROR, "父级权限不能是自己");
            }
            Permission parentPermission = permissionMapper.selectById(createDTO.getParentId());
            if (parentPermission == null) {
                throw new BusinessException(ResultCode.PERMISSION_PARENT_NOT_FOUND);
            }
        }
        
        // 更新权限信息
        Permission permission = new Permission();
        BeanUtils.copyProperties(createDTO, permission);
        permission.setId(permissionId);
        if (permission.getParentId() == null) {
            permission.setParentId(0L);
        }
        permission.setUpdatedAt(LocalDateTime.now());
        
        return permissionMapper.updateById(permission) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deletePermission(Long permissionId) {
        if (permissionId == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "权限ID不能为空");
        }
        
        // 检查权限是否存在
        Permission permission = permissionMapper.selectById(permissionId);
        if (permission == null) {
            throw new BusinessException(ResultCode.PERMISSION_NOT_FOUND);
        }
        
        // 检查是否有子权限
        List<PermissionDTO> children = permissionMapper.selectPermissionsByParentId(permissionId);
        if (!CollectionUtils.isEmpty(children)) {
            throw new BusinessException(ResultCode.PERMISSION_HAS_CHILDREN);
        }
        
        // 检查是否有角色关联此权限
        List<Long> roleIds = rolePermissionMapper.selectRoleIdsByPermissionId(permissionId);
        if (!CollectionUtils.isEmpty(roleIds)) {
            throw new BusinessException(ResultCode.PERMISSION_HAS_ROLES);
        }
        
        // 删除权限
        return permissionMapper.deleteById(permissionId) > 0;
    }
    
    @Override
    public Boolean changePermissionStatus(Long permissionId, Integer status) {
        if (permissionId == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "权限ID不能为空");
        }
        
        if (status == null || (status != 0 && status != 1)) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "状态值无效");
        }
        
        // 检查权限是否存在
        Permission permission = permissionMapper.selectById(permissionId);
        if (permission == null) {
            throw new BusinessException(ResultCode.PERMISSION_NOT_FOUND);
        }
        
        permission.setStatus(status);
        permission.setUpdatedAt(LocalDateTime.now());
        
        return permissionMapper.updateById(permission) > 0;
    }
    
    @Override
    public List<PermissionDTO> getPermissionsByRoleId(Long roleId) {
        if (roleId == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "角色ID不能为空");
        }
        
        return permissionMapper.selectPermissionsByRoleId(roleId);
    }
    
    @Override
    public List<PermissionDTO> getPermissionsByUserId(Long userId) {
        if (userId == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "用户ID不能为空");
        }
        
        return permissionMapper.selectPermissionsByUserId(userId);
    }
    
    @Override
    public List<PermissionDTO> getPermissionsByParentId(Long parentId) {
        if (parentId == null) {
            parentId = 0L;
        }
        
        return permissionMapper.selectPermissionsByParentId(parentId);
    }
    
    @Override
    public List<PermissionDTO> getEnabledPermissions() {
        return permissionMapper.selectEnabledPermissions();
    }
    
    @Override
    public Boolean existsPermissionCode(String permissionCode, Long excludeId) {
        if (permissionCode == null || permissionCode.trim().isEmpty()) {
            return false;
        }
        
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Permission::getPermissionCode, permissionCode);
        if (excludeId != null) {
            wrapper.ne(Permission::getId, excludeId);
        }
        
        return permissionMapper.selectCount(wrapper) > 0;
    }
    
    /**
     * 构建权限树
     *
     * @param allPermissions 所有权限列表
     * @param parentId       父级ID
     * @return 权限树
     */
    private List<PermissionDTO> buildPermissionTree(List<PermissionDTO> allPermissions, Long parentId) {
        if (CollectionUtils.isEmpty(allPermissions)) {
            return new ArrayList<>();
        }
        
        Map<Long, List<PermissionDTO>> permissionMap = allPermissions.stream()
                .collect(Collectors.groupingBy(permission -> 
                        permission.getParentId() == null ? 0L : permission.getParentId()));
        
        return buildTree(permissionMap, parentId);
    }
    
    /**
     * 递归构建树结构
     *
     * @param permissionMap 权限分组Map
     * @param parentId      父级ID
     * @return 权限树
     */
    private List<PermissionDTO> buildTree(Map<Long, List<PermissionDTO>> permissionMap, Long parentId) {
        List<PermissionDTO> children = permissionMap.get(parentId);
        if (CollectionUtils.isEmpty(children)) {
            return new ArrayList<>();
        }
        
        for (PermissionDTO permission : children) {
            List<PermissionDTO> subChildren = buildTree(permissionMap, permission.getId());
            permission.setChildren(subChildren);
        }
        
        return children;
    }
    
}