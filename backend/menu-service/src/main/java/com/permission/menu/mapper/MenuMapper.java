package com.permission.menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.permission.menu.dto.MenuDTO;
import com.permission.menu.dto.MenuQueryDTO;
import com.permission.menu.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单Mapper接口
 *
 * @author system
 * @since 2024-01-01
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    
    /**
     * 查询菜单树
     *
     * @param query 查询条件
     * @return 菜单列表
     */
    List<MenuDTO> selectMenuTree(@Param("query") MenuQueryDTO query);
    
    /**
     * 根据菜单编码查询菜单
     *
     * @param menuCode 菜单编码
     * @return 菜单信息
     */
    Menu selectByMenuCode(@Param("menuCode") String menuCode);
    
    /**
     * 根据父级ID查询子菜单列表
     *
     * @param parentId 父级ID
     * @return 菜单列表
     */
    List<MenuDTO> selectMenusByParentId(@Param("parentId") Long parentId);
    
    /**
     * 根据用户ID查询菜单列表
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<MenuDTO> selectMenusByUserId(@Param("userId") Long userId);
    
    /**
     * 根据角色ID查询菜单列表
     *
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<MenuDTO> selectMenusByRoleId(@Param("roleId") Long roleId);
    
    /**
     * 查询所有启用的菜单
     *
     * @return 菜单列表
     */
    List<MenuDTO> selectEnabledMenus();
    
    /**
     * 查询用户权限标识列表
     *
     * @param userId 用户ID
     * @return 权限标识列表
     */
    List<String> selectPermissionsByUserId(@Param("userId") Long userId);
    
}