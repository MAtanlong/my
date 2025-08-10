package com.permission.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.permission.system.dto.DictTypeDTO;

import java.util.List;

/**
 * 字典类型服务接口
 *
 * @author system
 * @since 2024-01-01
 */
public interface DictTypeService {
    
    /**
     * 查询字典类型列表
     *
     * @return 字典类型列表
     */
    List<DictTypeDTO> getDictTypeList();
    
    /**
     * 分页查询字典类型
     *
     * @param page 页码
     * @param size 每页大小
     * @param keyword 搜索关键词
     * @return 分页结果
     */
    IPage<DictTypeDTO> getDictTypePage(Long page, Long size, String keyword);
    
    /**
     * 查询字典类型列表（包含字典数据）
     *
     * @return 字典类型列表
     */
    List<DictTypeDTO> getDictTypeListWithData();
    
    /**
     * 根据字典类型ID查询详情
     *
     * @param dictTypeId 字典类型ID
     * @return 字典类型详情
     */
    DictTypeDTO getDictTypeById(Long dictTypeId);
    
    /**
     * 根据字典类型查询字典类型信息
     *
     * @param dictType 字典类型
     * @return 字典类型信息
     */
    DictTypeDTO getDictTypeByType(String dictType);
    
    /**
     * 创建字典类型
     *
     * @param dictTypeDTO 字典类型信息
     * @return 字典类型ID
     */
    Long createDictType(DictTypeDTO dictTypeDTO);
    
    /**
     * 更新字典类型
     *
     * @param dictTypeId  字典类型ID
     * @param dictTypeDTO 字典类型信息
     * @return 是否成功
     */
    Boolean updateDictType(Long dictTypeId, DictTypeDTO dictTypeDTO);
    
    /**
     * 删除字典类型
     *
     * @param dictTypeId 字典类型ID
     * @return 是否成功
     */
    Boolean deleteDictType(Long dictTypeId);
    
    /**
     * 批量删除字典类型
     *
     * @param dictTypeIds 字典类型ID列表
     * @return 是否成功
     */
    Boolean deleteDictTypes(List<Long> dictTypeIds);
    
    /**
     * 修改字典类型状态
     *
     * @param dictTypeId 字典类型ID
     * @param status     状态
     * @return 是否成功
     */
    Boolean changeDictTypeStatus(Long dictTypeId, Integer status);
    
    /**
     * 查询所有启用的字典类型
     *
     * @return 字典类型列表
     */
    List<DictTypeDTO> getEnabledDictTypes();
    
    /**
     * 检查字典类型是否存在
     *
     * @param dictType  字典类型
     * @param excludeId 排除的字典类型ID
     * @return 是否存在
     */
    Boolean existsDictType(String dictType, Long excludeId);
    
    /**
     * 刷新字典缓存
     */
    void refreshDictCache();
    
}