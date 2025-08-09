package com.permission.user.dto;

import lombok.Data;

/**
 * 用户查询请求DTO
 */
@Data
public class UserQueryDTO {
    
    /**
     * 页码
     */
    private Long current = 1L;
    
    /**
     * 每页大小
     */
    private Long size = 10L;
    
    /**
     * 搜索关键词
     */
    private String keyword;
    
    /**
     * 用户状态
     */
    private String status;
    
    /**
     * 部门ID
     */
    private String departmentId;
    
    /**
     * 角色ID
     */
    private String roleId;
}