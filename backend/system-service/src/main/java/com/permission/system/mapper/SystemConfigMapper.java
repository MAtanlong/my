package com.permission.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.permission.system.dto.SystemConfigDTO;
import com.permission.system.dto.SystemConfigQueryDTO;
import com.permission.system.entity.SystemConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统配置Mapper接口
 *
 * @author system
 * @since 2024-01-01
 */
@Mapper
public interface SystemConfigMapper extends BaseMapper<SystemConfig> {
    
    /**
     * 分页查询系统配置
     *
     * @param page  分页参数
     * @param query 查询条件
     * @return 系统配置分页列表
     */
    IPage<SystemConfigDTO> selectConfigPage(Page<SystemConfigDTO> page, @Param("query") SystemConfigQueryDTO query);
    
    /**
     * 根据配置键名查询配置
     *
     * @param configKey 配置键名
     * @return 系统配置
     */
    SystemConfigDTO selectConfigByKey(@Param("configKey") String configKey);
    
    /**
     * 查询所有启用的配置
     *
     * @return 配置列表
     */
    List<SystemConfigDTO> selectEnabledConfigs();
    
    /**
     * 根据配置类型查询配置列表
     *
     * @param configType 配置类型
     * @return 配置列表
     */
    List<SystemConfigDTO> selectConfigsByType(@Param("configType") Integer configType);
    
}