package com.permission.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.permission.common.exception.BusinessException;
import com.permission.common.result.PageResult;
import com.permission.common.result.ResultCode;
import com.permission.user.dto.UserCreateDTO;
import com.permission.user.dto.UserDTO;
import com.permission.user.dto.UserQueryDTO;
import com.permission.user.entity.User;
import com.permission.user.mapper.UserMapper;
import com.permission.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 用户服务实现类
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @Override
    public PageResult<UserDTO> getUserPage(UserQueryDTO query) {
        Page<UserDTO> page = new Page<>(query.getCurrent(), query.getSize());
        IPage<UserDTO> result = baseMapper.selectUserPage(page, query);
        return PageResult.of(result.getCurrent(), result.getSize(), result.getTotal(), result.getRecords());
    }
    
    @Override
    public UserDTO getUserById(String userId) {
        UserDTO userDTO = baseMapper.selectUserById(userId);
        if (userDTO == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }
        return userDTO;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createUser(UserCreateDTO createDTO) {
        // 检查用户名是否存在
        if (existsByUsername(createDTO.getUsername())) {
            throw new BusinessException(ResultCode.USER_ALREADY_EXISTS, "用户名已存在");
        }
        
        // 检查邮箱是否存在
        if (existsByEmail(createDTO.getEmail())) {
            throw new BusinessException(ResultCode.USER_ALREADY_EXISTS, "邮箱已存在");
        }
        
        // 创建用户
        User user = new User();
        BeanUtils.copyProperties(createDTO, user);
        user.setPassword(passwordEncoder.encode(createDTO.getPassword()));
        user.setStatus(1); // 1-启用
        
        save(user);
        
        // TODO: 分配角色
        // assignRoles(user.getId(), createDTO.getRoleIds());
        
        log.info("创建用户成功: {}", user.getUsername());
        return String.valueOf(user.getId());
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(String userId, UserDTO userDTO) {
        User existingUser = getById(Long.valueOf(userId));
        if (existingUser == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }
        
        // 检查邮箱是否被其他用户使用
        if (StringUtils.hasText(userDTO.getEmail()) && !userDTO.getEmail().equals(existingUser.getEmail())) {
            User emailUser = getUserByEmail(userDTO.getEmail());
            if (emailUser != null && !emailUser.getId().equals(Long.valueOf(userId))) {
                throw new BusinessException(ResultCode.USER_ALREADY_EXISTS, "邮箱已被其他用户使用");
            }
        }
        
        // 更新用户信息
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setId(Long.valueOf(userId));
        user.setUsername(existingUser.getUsername()); // 用户名不允许修改
        user.setPassword(existingUser.getPassword()); // 密码不在此处修改
        
        updateById(user);
        
        // TODO: 更新角色分配
        // if (userDTO.getRoleIds() != null) {
        //     assignRoles(userId, userDTO.getRoleIds());
        // }
        
        log.info("更新用户成功: {}", user.getUsername());
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(String userId) {
        User user = getById(Long.valueOf(userId));
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }
        
        removeById(Long.valueOf(userId));
        
        // TODO: 删除用户角色关联
        
        log.info("删除用户成功: {}", user.getUsername());
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDeleteUsers(List<String> userIds) {
        List<Long> longUserIds = userIds.stream().map(Long::valueOf).collect(java.util.stream.Collectors.toList());
        removeByIds(longUserIds);
        
        // TODO: 批量删除用户角色关联
        
        log.info("批量删除用户成功，数量: {}", userIds.size());
    }
    
    @Override
    public void updateUserStatus(String userId, String status) {
        User user = getById(Long.valueOf(userId));
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }
        
        user.setStatus(Integer.valueOf(status));
        updateById(user);
        
        log.info("更新用户状态成功: {} -> {}", user.getUsername(), status);
    }
    
    @Override
    public void resetPassword(String userId, String newPassword) {
        User user = getById(Long.valueOf(userId));
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }
        
        user.setPassword(passwordEncoder.encode(newPassword));
        updateById(user);
        
        log.info("重置用户密码成功: {}", user.getUsername());
    }
    
    @Override
    public void assignRoles(String userId, List<String> roleIds) {
        // TODO: 实现角色分配逻辑
        log.info("分配用户角色: {} -> {}", userId, roleIds);
    }
    
    @Override
    public User getUserByUsername(String username) {
        return baseMapper.selectByUsername(username);
    }
    
    @Override
    public User getUserByEmail(String email) {
        return baseMapper.selectByEmail(email);
    }
    
    @Override
    public List<UserDTO> getUsersByDepartmentId(String departmentId) {
        return baseMapper.selectUsersByDepartmentId(departmentId);
    }
    
    @Override
    public List<UserDTO> getUsersByRoleId(String roleId) {
        return baseMapper.selectUsersByRoleId(roleId);
    }
    
    @Override
    public boolean existsByUsername(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        return count(wrapper) > 0;
    }
    
    @Override
    public boolean existsByEmail(String email) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail, email);
        return count(wrapper) > 0;
    }
}