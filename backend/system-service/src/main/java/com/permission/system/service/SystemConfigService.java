package com.permission.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.permission.system.dto.SystemConfigCreateDTO;
import com.permission.system.dto.SystemConfigDTO;
import com.permission.system.dto.SystemConfigQueryDTO;

import java.util.List;

/**
 * 系统配置服务接口
 *
 * @author system
 * @since 2024-01-01
 */
public interface SystemConfigService {
    
    /**
     * 分页查询系统配置
     *
     * @param query 查询条件
     * @return 系统配置分页列表
     */
    IPage<SystemConfigDTO> getConfigPage(SystemConfigQueryDTO query);
    
    /**
     * 根据配置ID查询配置详情
     *
     * @param configId 配置ID
     * @return 配置详情
     */
    SystemConfigDTO getConfigById(Long configId);
    
    /**
     * 根据配置键名查询配置
     *
     * @param configKey 配置键名
     * @return 配置信息
     */
    SystemConfigDTO getConfigByKey(String configKey);
    
    /**
     * 根据配置键名获取配置值
     *
     * @param configKey 配置键名
     * @return 配置值
     */
    String getConfigValue(String configKey);
    
    /**
     * 创建系统配置
     *
     * @param createDTO 创建参数
     * @return 配置ID
     */
    Long createConfig(SystemConfigCreateDTO createDTO);
    
    /**
     * 更新系统配置
     *
     * @param configId  配置ID
     * @param createDTO 更新参数
     * @return 是否成功
     */
    Boolean updateConfig(Long configId, SystemConfigCreateDTO createDTO);
    
    /**
     * 删除系统配置
     *
     * @param configId 配置ID
     * @return 是否成功
     */
    Boolean deleteConfig(Long configId);
    
    /**
     * 批量删除系统配置
     *
     * @param configIds 配置ID列表
     * @return 是否成功
     */
    Boolean deleteConfigs(List<Long> configIds);
    
    /**
     * 修改配置状态
     *
     * @param configId 配置ID
     * @param status   状态
     * @return 是否成功
     */
    Boolean changeConfigStatus(Long configId, Integer status);
    
    /**
     * 查询所有启用的配置
     *
     * @return 配置列表
     */
    List<SystemConfigDTO> getEnabledConfigs();
    
    /**
     * 根据配置类型查询配置列表
     *
     * @param configType 配置类型
     * @return 配置列表
     */
    List<SystemConfigDTO> getConfigsByType(Integer configType);
    
    /**
     * 检查配置键名是否存在
     *
     * @param configKey 配置键名
     * @param excludeId 排除的配置ID
     * @return 是否存在
     */
    Boolean existsConfigKey(String configKey, Long excludeId);
    
    /**
     * 刷新配置缓存
     */
    void refreshConfigCache();
    
}