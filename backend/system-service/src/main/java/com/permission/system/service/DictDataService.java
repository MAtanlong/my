package com.permission.system.service;

import com.permission.system.dto.DictDataDTO;

import java.util.List;

/**
 * 字典数据服务接口
 *
 * @author system
 * @since 2024-01-01
 */
public interface DictDataService {
    
    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据列表
     */
    List<DictDataDTO> getDictDataByType(String dictType);
    
    /**
     * 根据字典类型查询启用的字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据列表
     */
    List<DictDataDTO> getEnabledDictDataByType(String dictType);
    
    /**
     * 根据字典数据ID查询详情
     *
     * @param dictDataId 字典数据ID
     * @return 字典数据详情
     */
    DictDataDTO getDictDataById(Long dictDataId);
    
    /**
     * 根据字典类型和字典值查询字典数据
     *
     * @param dictType  字典类型
     * @param dictValue 字典值
     * @return 字典数据
     */
    DictDataDTO getDictDataByTypeAndValue(String dictType, String dictValue);
    
    /**
     * 创建字典数据
     *
     * @param dictDataDTO 字典数据信息
     * @return 字典数据ID
     */
    Long createDictData(DictDataDTO dictDataDTO);
    
    /**
     * 更新字典数据
     *
     * @param dictDataId  字典数据ID
     * @param dictDataDTO 字典数据信息
     * @return 是否成功
     */
    Boolean updateDictData(Long dictDataId, DictDataDTO dictDataDTO);
    
    /**
     * 删除字典数据
     *
     * @param dictDataId 字典数据ID
     * @return 是否成功
     */
    Boolean deleteDictData(Long dictDataId);
    
    /**
     * 批量删除字典数据
     *
     * @param dictDataIds 字典数据ID列表
     * @return 是否成功
     */
    Boolean deleteDictDataList(List<Long> dictDataIds);
    
    /**
     * 根据字典类型删除字典数据
     *
     * @param dictType 字典类型
     * @return 是否成功
     */
    Boolean deleteDictDataByType(String dictType);
    
    /**
     * 修改字典数据状态
     *
     * @param dictDataId 字典数据ID
     * @param status     状态
     * @return 是否成功
     */
    Boolean changeDictDataStatus(Long dictDataId, Integer status);
    
    /**
     * 获取字典标签
     *
     * @param dictType  字典类型
     * @param dictValue 字典值
     * @return 字典标签
     */
    String getDictLabel(String dictType, String dictValue);
    
}