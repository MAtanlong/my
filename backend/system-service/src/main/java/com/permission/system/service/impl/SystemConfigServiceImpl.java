package com.permission.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.permission.common.exception.BusinessException;
import com.permission.common.result.ResultCode;
import com.permission.system.dto.SystemConfigCreateDTO;
import com.permission.system.dto.SystemConfigDTO;
import com.permission.system.dto.SystemConfigQueryDTO;
import com.permission.system.entity.SystemConfig;
import com.permission.system.mapper.SystemConfigMapper;
import com.permission.system.service.SystemConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统配置服务实现类
 *
 * @author system
 * @since 2024-01-01
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SystemConfigServiceImpl implements SystemConfigService {
    
    private final SystemConfigMapper systemConfigMapper;
    
    @Override
    public IPage<SystemConfigDTO> getConfigPage(SystemConfigQueryDTO query) {
        Page<SystemConfigDTO> page = new Page<>(query.getPage(), query.getSize());
        return systemConfigMapper.selectConfigPage(page, query);
    }
    
    @Override
    public SystemConfigDTO getConfigById(Long configId) {
        if (configId == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "配置ID不能为空");
        }
        
        SystemConfig config = systemConfigMapper.selectById(configId);
        if (config == null) {
            throw new BusinessException(ResultCode.CONFIG_NOT_FOUND);
        }
        
        SystemConfigDTO configDTO = new SystemConfigDTO();
        BeanUtils.copyProperties(config, configDTO);
        
        return configDTO;
    }
    
    @Override
    @Cacheable(value = "system:config", key = "#configKey")
    public SystemConfigDTO getConfigByKey(String configKey) {
        if (configKey == null || configKey.trim().isEmpty()) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "配置键名不能为空");
        }
        
        return systemConfigMapper.selectConfigByKey(configKey);
    }
    
    @Override
    @Cacheable(value = "system:config:value", key = "#configKey")
    public String getConfigValue(String configKey) {
        SystemConfigDTO config = getConfigByKey(configKey);
        return config != null ? config.getConfigValue() : null;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"system:config", "system:config:value"}, allEntries = true)
    public Long createConfig(SystemConfigCreateDTO createDTO) {
        // 检查配置键名是否已存在
        if (existsConfigKey(createDTO.getConfigKey(), null)) {
            throw new BusinessException(ResultCode.CONFIG_KEY_EXISTS);
        }
        
        // 创建配置
        SystemConfig config = new SystemConfig();
        BeanUtils.copyProperties(createDTO, config);
        config.setCreatedAt(LocalDateTime.now());
        config.setUpdatedAt(LocalDateTime.now());
        
        systemConfigMapper.insert(config);
        
        return config.getId();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"system:config", "system:config:value"}, allEntries = true)
    public Boolean updateConfig(Long configId, SystemConfigCreateDTO createDTO) {
        if (configId == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "配置ID不能为空");
        }
        
        // 检查配置是否存在
        SystemConfig existingConfig = systemConfigMapper.selectById(configId);
        if (existingConfig == null) {
            throw new BusinessException(ResultCode.CONFIG_NOT_FOUND);
        }
        
        // 检查配置键名是否已存在（排除当前配置）
        if (existsConfigKey(createDTO.getConfigKey(), configId)) {
            throw new BusinessException(ResultCode.CONFIG_KEY_EXISTS);
        }
        
        // 更新配置信息
        SystemConfig config = new SystemConfig();
        BeanUtils.copyProperties(createDTO, config);
        config.setId(configId);
        config.setUpdatedAt(LocalDateTime.now());
        
        return systemConfigMapper.updateById(config) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"system:config", "system:config:value"}, allEntries = true)
    public Boolean deleteConfig(Long configId) {
        if (configId == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "配置ID不能为空");
        }
        
        // 检查配置是否存在
        SystemConfig config = systemConfigMapper.selectById(configId);
        if (config == null) {
            throw new BusinessException(ResultCode.CONFIG_NOT_FOUND);
        }
        
        // 检查是否为系统内置配置
        if (config.getConfigType() != null && config.getConfigType() == 1) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "系统内置配置不能删除");
        }
        
        return systemConfigMapper.deleteById(configId) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"system:config", "system:config:value"}, allEntries = true)
    public Boolean deleteConfigs(List<Long> configIds) {
        if (CollectionUtils.isEmpty(configIds)) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "配置ID列表不能为空");
        }
        
        // 检查是否包含系统内置配置
        LambdaQueryWrapper<SystemConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SystemConfig::getId, configIds)
                .eq(SystemConfig::getConfigType, 1);
        
        Long systemConfigCount = systemConfigMapper.selectCount(wrapper);
        if (systemConfigCount > 0) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "不能删除系统内置配置");
        }
        
        return systemConfigMapper.deleteBatchIds(configIds) > 0;
    }
    
    @Override
    @CacheEvict(value = {"system:config", "system:config:value"}, allEntries = true)
    public Boolean changeConfigStatus(Long configId, Integer status) {
        if (configId == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "配置ID不能为空");
        }
        
        if (status == null || (status != 0 && status != 1)) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "状态值无效");
        }
        
        // 检查配置是否存在
        SystemConfig config = systemConfigMapper.selectById(configId);
        if (config == null) {
            throw new BusinessException(ResultCode.CONFIG_NOT_FOUND);
        }
        
        config.setStatus(status);
        config.setUpdatedAt(LocalDateTime.now());
        
        return systemConfigMapper.updateById(config) > 0;
    }
    
    @Override
    @Cacheable(value = "system:config:enabled")
    public List<SystemConfigDTO> getEnabledConfigs() {
        return systemConfigMapper.selectEnabledConfigs();
    }
    
    @Override
    public List<SystemConfigDTO> getConfigsByType(Integer configType) {
        if (configType == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "配置类型不能为空");
        }
        
        return systemConfigMapper.selectConfigsByType(configType);
    }
    
    @Override
    public Boolean existsConfigKey(String configKey, Long excludeId) {
        if (configKey == null || configKey.trim().isEmpty()) {
            return false;
        }
        
        LambdaQueryWrapper<SystemConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SystemConfig::getConfigKey, configKey);
        if (excludeId != null) {
            wrapper.ne(SystemConfig::getId, excludeId);
        }
        
        return systemConfigMapper.selectCount(wrapper) > 0;
    }
    
    @Override
    @CacheEvict(value = {"system:config", "system:config:value", "system:config:enabled"}, allEntries = true)
    public void refreshConfigCache() {
        log.info("系统配置缓存已刷新");
    }
    
}