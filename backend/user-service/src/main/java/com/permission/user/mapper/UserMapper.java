package com.permission.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.permission.user.dto.UserDTO;
import com.permission.user.dto.UserQueryDTO;
import com.permission.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户Mapper接口
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    
    /**
     * 分页查询用户列表
     */
    IPage<UserDTO> selectUserPage(Page<UserDTO> page, @Param("query") UserQueryDTO query);
    
    /**
     * 根据用户ID查询用户详情
     */
    UserDTO selectUserById(@Param("userId") String userId);
    
    /**
     * 根据用户名查询用户
     */
    User selectByUsername(@Param("username") String username);
    
    /**
     * 根据邮箱查询用户
     */
    User selectByEmail(@Param("email") String email);
    
    /**
     * 根据部门ID查询用户列表
     */
    List<UserDTO> selectUsersByDepartmentId(@Param("departmentId") String departmentId);
    
    /**
     * 根据角色ID查询用户列表
     */
    List<UserDTO> selectUsersByRoleId(@Param("roleId") String roleId);
}