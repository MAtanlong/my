package com.permission.menu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.permission.common.exception.BusinessException;
import com.permission.common.result.ResultCode;
import com.permission.menu.dto.MenuCreateDTO;
import com.permission.menu.dto.MenuDTO;
import com.permission.menu.dto.MenuQueryDTO;
import com.permission.menu.entity.Menu;
import com.permission.menu.mapper.MenuMapper;
import com.permission.menu.service.MenuService;
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
 * 菜单服务实现类
 *
 * @author system
 * @since 2024-01-01
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    
    private final MenuMapper menuMapper;
    
    @Override
    public List<MenuDTO> getMenuTree(MenuQueryDTO query) {
        List<MenuDTO> allMenus = menuMapper.selectMenuTree(query);
        return buildMenuTree(allMenus, 0L);
    }
    
    @Override
    public MenuDTO getMenuById(Long menuId) {
        if (menuId == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "菜单ID不能为空");
        }
        
        Menu menu = menuMapper.selectById(menuId);
        if (menu == null) {
            throw new BusinessException(ResultCode.MENU_NOT_FOUND);
        }
        
        MenuDTO menuDTO = new MenuDTO();
        BeanUtils.copyProperties(menu, menuDTO);
        
        return menuDTO;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createMenu(MenuCreateDTO createDTO) {
        // 检查菜单编码是否已存在
        if (existsMenuCode(createDTO.getMenuCode(), null)) {
            throw new BusinessException(ResultCode.MENU_CODE_EXISTS);
        }
        
        // 如果有父级菜单，检查父级菜单是否存在
        if (createDTO.getParentId() != null && createDTO.getParentId() > 0) {
            Menu parentMenu = menuMapper.selectById(createDTO.getParentId());
            if (parentMenu == null) {
                throw new BusinessException(ResultCode.MENU_PARENT_NOT_FOUND);
            }
        }
        
        // 创建菜单
        Menu menu = new Menu();
        BeanUtils.copyProperties(createDTO, menu);
        if (menu.getParentId() == null) {
            menu.setParentId(0L);
        }
        if (menu.getVisible() == null) {
            menu.setVisible(1);
        }
        if (menu.getKeepAlive() == null) {
            menu.setKeepAlive(0);
        }
        menu.setCreatedAt(LocalDateTime.now());
        menu.setUpdatedAt(LocalDateTime.now());
        
        menuMapper.insert(menu);
        
        return menu.getId();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateMenu(Long menuId, MenuCreateDTO createDTO) {
        if (menuId == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "菜单ID不能为空");
        }
        
        // 检查菜单是否存在
        Menu existingMenu = menuMapper.selectById(menuId);
        if (existingMenu == null) {
            throw new BusinessException(ResultCode.MENU_NOT_FOUND);
        }
        
        // 检查菜单编码是否已存在（排除当前菜单）
        if (existsMenuCode(createDTO.getMenuCode(), menuId)) {
            throw new BusinessException(ResultCode.MENU_CODE_EXISTS);
        }
        
        // 如果有父级菜单，检查父级菜单是否存在且不能是自己
        if (createDTO.getParentId() != null && createDTO.getParentId() > 0) {
            if (createDTO.getParentId().equals(menuId)) {
                throw new BusinessException(ResultCode.PARAM_ERROR, "父级菜单不能是自己");
            }
            Menu parentMenu = menuMapper.selectById(createDTO.getParentId());
            if (parentMenu == null) {
                throw new BusinessException(ResultCode.MENU_PARENT_NOT_FOUND);
            }
        }
        
        // 更新菜单信息
        Menu menu = new Menu();
        BeanUtils.copyProperties(createDTO, menu);
        menu.setId(menuId);
        if (menu.getParentId() == null) {
            menu.setParentId(0L);
        }
        if (menu.getVisible() == null) {
            menu.setVisible(1);
        }
        if (menu.getKeepAlive() == null) {
            menu.setKeepAlive(0);
        }
        menu.setUpdatedAt(LocalDateTime.now());
        
        return menuMapper.updateById(menu) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteMenu(Long menuId) {
        if (menuId == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "菜单ID不能为空");
        }
        
        // 检查菜单是否存在
        Menu menu = menuMapper.selectById(menuId);
        if (menu == null) {
            throw new BusinessException(ResultCode.MENU_NOT_FOUND);
        }
        
        // 检查是否有子菜单
        List<MenuDTO> children = menuMapper.selectMenusByParentId(menuId);
        if (!CollectionUtils.isEmpty(children)) {
            throw new BusinessException(ResultCode.MENU_HAS_CHILDREN);
        }
        
        // 删除菜单
        return menuMapper.deleteById(menuId) > 0;
    }
    
    @Override
    public Boolean changeMenuStatus(Long menuId, Integer status) {
        if (menuId == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "菜单ID不能为空");
        }
        
        if (status == null || (status != 0 && status != 1)) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "状态值无效");
        }
        
        // 检查菜单是否存在
        Menu menu = menuMapper.selectById(menuId);
        if (menu == null) {
            throw new BusinessException(ResultCode.MENU_NOT_FOUND);
        }
        
        menu.setStatus(status);
        menu.setUpdatedAt(LocalDateTime.now());
        
        return menuMapper.updateById(menu) > 0;
    }
    
    @Override
    public List<MenuDTO> getMenusByParentId(Long parentId) {
        if (parentId == null) {
            parentId = 0L;
        }
        
        return menuMapper.selectMenusByParentId(parentId);
    }
    
    @Override
    public List<MenuDTO> getMenuTreeByUserId(Long userId) {
        if (userId == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "用户ID不能为空");
        }
        
        List<MenuDTO> allMenus = menuMapper.selectMenusByUserId(userId);
        return buildMenuTree(allMenus, 0L);
    }
    
    @Override
    public List<MenuDTO> getMenusByRoleId(Long roleId) {
        if (roleId == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "角色ID不能为空");
        }
        
        return menuMapper.selectMenusByRoleId(roleId);
    }
    
    @Override
    public List<MenuDTO> getEnabledMenus() {
        return menuMapper.selectEnabledMenus();
    }
    
    @Override
    public List<String> getPermissionsByUserId(Long userId) {
        if (userId == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "用户ID不能为空");
        }
        
        return menuMapper.selectPermissionsByUserId(userId);
    }
    
    @Override
    public Boolean existsMenuCode(String menuCode, Long excludeId) {
        if (menuCode == null || menuCode.trim().isEmpty()) {
            return false;
        }
        
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getMenuCode, menuCode);
        if (excludeId != null) {
            wrapper.ne(Menu::getId, excludeId);
        }
        
        return menuMapper.selectCount(wrapper) > 0;
    }
    
    /**
     * 构建菜单树
     *
     * @param allMenus 所有菜单列表
     * @param parentId 父级ID
     * @return 菜单树
     */
    private List<MenuDTO> buildMenuTree(List<MenuDTO> allMenus, Long parentId) {
        if (CollectionUtils.isEmpty(allMenus)) {
            return new ArrayList<>();
        }
        
        Map<Long, List<MenuDTO>> menuMap = allMenus.stream()
                .collect(Collectors.groupingBy(menu -> 
                        menu.getParentId() == null ? 0L : menu.getParentId()));
        
        return buildTree(menuMap, parentId);
    }
    
    /**
     * 递归构建树结构
     *
     * @param menuMap  菜单分组Map
     * @param parentId 父级ID
     * @return 菜单树
     */
    private List<MenuDTO> buildTree(Map<Long, List<MenuDTO>> menuMap, Long parentId) {
        List<MenuDTO> children = menuMap.get(parentId);
        if (CollectionUtils.isEmpty(children)) {
            return new ArrayList<>();
        }
        
        for (MenuDTO menu : children) {
            List<MenuDTO> subChildren = buildTree(menuMap, menu.getId());
            menu.setChildren(subChildren);
        }
        
        return children;
    }
    
}