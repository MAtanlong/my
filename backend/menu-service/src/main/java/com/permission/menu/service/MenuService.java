package com.permission.menu.service;

import com.permission.menu.dto.MenuCreateDTO;
import com.permission.menu.dto.MenuDTO;
import com.permission.menu.dto.MenuQueryDTO;

import java.util.List;

/**
 * 菜单服务接口
 *
 * @author system
 * @since 2024-01-01
 */
public interface MenuService {
    
    /**
     * 查询菜单树
     *
     * @param query 查询条件
     * @return 菜单树
     */
    List<MenuDTO> getMenuTree(MenuQueryDTO query);
    
    /**
     * 根据菜单ID查询菜单详情
     *
     * @param menuId 菜单ID
     * @return 菜单详情
     */
    MenuDTO getMenuById(Long menuId);
    
    /**
     * 创建菜单
     *
     * @param createDTO 创建参数
     * @return 菜单ID
     */
    Long createMenu(MenuCreateDTO createDTO);
    
    /**
     * 更新菜单
     *
     * @param menuId    菜单ID
     * @param createDTO 更新参数
     * @return 是否成功
     */
    Boolean updateMenu(Long menuId, MenuCreateDTO createDTO);
    
    /**
     * 删除菜单
     *
     * @param menuId 菜单ID
     * @return 是否成功
     */
    Boolean deleteMenu(Long menuId);
    
    /**
     * 修改菜单状态
     *
     * @param menuId 菜单ID
     * @param status 状态
     * @return 是否成功
     */
    Boolean changeMenuStatus(Long menuId, Integer status);
    
    /**
     * 根据父级ID查询子菜单列表
     *
     * @param parentId 父级ID
     * @return 菜单列表
     */
    List<MenuDTO> getMenusByParentId(Long parentId);
    
    /**
     * 根据用户ID查询菜单树
     *
     * @param userId 用户ID
     * @return 菜单树
     */
    List<MenuDTO> getMenuTreeByUserId(Long userId);
    
    /**
     * 根据角色ID查询菜单列表
     *
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<MenuDTO> getMenusByRoleId(Long roleId);
    
    /**
     * 查询所有启用的菜单
     *
     * @return 菜单列表
     */
    List<MenuDTO> getEnabledMenus();
    
    /**
     * 根据用户ID查询权限标识列表
     *
     * @param userId 用户ID
     * @return 权限标识列表
     */
    List<String> getPermissionsByUserId(Long userId);
    
    /**
     * 检查菜单编码是否存在
     *
     * @param menuCode  菜单编码
     * @param excludeId 排除的菜单ID
     * @return 是否存在
     */
    Boolean existsMenuCode(String menuCode, Long excludeId);
    
}