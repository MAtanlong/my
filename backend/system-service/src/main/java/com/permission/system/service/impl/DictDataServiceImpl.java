package com.permission.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.permission.common.exception.BusinessException;
import com.permission.common.result.ResultCode;
import com.permission.system.dto.DictDataDTO;
import com.permission.system.entity.DictData;
import com.permission.system.mapper.DictDataMapper;
import com.permission.system.service.DictDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 字典数据服务实现类
 *
 * @author system
 * @since 2024-01-01
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DictDataServiceImpl implements DictDataService {
    
    private final DictDataMapper dictDataMapper;
    
    @Override
    @Cacheable(value = "system:dict:data", key = "#dictType")
    public List<DictDataDTO> getDictDataByType(String dictType) {
        if (!StringUtils.hasText(dictType)) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "字典类型不能为空");
        }
        
        return dictDataMapper.selectDictDataByType(dictType);
    }
    
    @Override
    @Cacheable(value = "system:dict:data:enabled", key = "#dictType")
    public List<DictDataDTO> getEnabledDictDataByType(String dictType) {
        if (!StringUtils.hasText(dictType)) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "字典类型不能为空");
        }
        
        return dictDataMapper.selectEnabledDictDataByType(dictType);
    }
    
    @Override
    public DictDataDTO getDictDataById(Long dictDataId) {
        if (dictDataId == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "字典数据ID不能为空");
        }
        
        DictData dictData = dictDataMapper.selectById(dictDataId);
        if (dictData == null) {
            throw new BusinessException(ResultCode.DICT_DATA_NOT_FOUND);
        }
        
        DictDataDTO dto = new DictDataDTO();
        BeanUtils.copyProperties(dictData, dto);
        
        return dto;
    }
    
    @Override
    @Cacheable(value = "system:dict:data:value", key = "#dictType + ':' + #dictValue")
    public DictDataDTO getDictDataByTypeAndValue(String dictType, String dictValue) {
        if (!StringUtils.hasText(dictType) || !StringUtils.hasText(dictValue)) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "字典类型和字典值不能为空");
        }
        
        return dictDataMapper.selectDictDataByTypeAndValue(dictType, dictValue);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"system:dict:data", "system:dict:data:enabled", "system:dict:data:value"}, allEntries = true)
    public Long createDictData(DictDataDTO dictDataDTO) {
        // 检查字典值是否已存在
        if (existsDictValue(dictDataDTO.getDictType(), dictDataDTO.getDictValue(), null)) {
            throw new BusinessException(ResultCode.DICT_VALUE_EXISTS);
        }
        
        // 创建字典数据
        DictData dictData = new DictData();
        BeanUtils.copyProperties(dictDataDTO, dictData);
        dictData.setCreatedAt(LocalDateTime.now());
        dictData.setUpdatedAt(LocalDateTime.now());
        
        dictDataMapper.insert(dictData);
        
        return dictData.getId();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"system:dict:data", "system:dict:data:enabled", "system:dict:data:value"}, allEntries = true)
    public Boolean updateDictData(Long dictDataId, DictDataDTO dictDataDTO) {
        if (dictDataId == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "字典数据ID不能为空");
        }
        
        // 检查字典数据是否存在
        DictData existingDictData = dictDataMapper.selectById(dictDataId);
        if (existingDictData == null) {
            throw new BusinessException(ResultCode.DICT_DATA_NOT_FOUND);
        }
        
        // 检查字典值是否已存在（排除当前字典数据）
        if (existsDictValue(dictDataDTO.getDictType(), dictDataDTO.getDictValue(), dictDataId)) {
            throw new BusinessException(ResultCode.DICT_VALUE_EXISTS);
        }
        
        // 更新字典数据信息
        DictData dictData = new DictData();
        BeanUtils.copyProperties(dictDataDTO, dictData);
        dictData.setId(dictDataId);
        dictData.setUpdatedAt(LocalDateTime.now());
        
        return dictDataMapper.updateById(dictData) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"system:dict:data", "system:dict:data:enabled", "system:dict:data:value"}, allEntries = true)
    public Boolean deleteDictData(Long dictDataId) {
        if (dictDataId == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "字典数据ID不能为空");
        }
        
        // 检查字典数据是否存在
        DictData dictData = dictDataMapper.selectById(dictDataId);
        if (dictData == null) {
            throw new BusinessException(ResultCode.DICT_DATA_NOT_FOUND);
        }
        
        return dictDataMapper.deleteById(dictDataId) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"system:dict:data", "system:dict:data:enabled", "system:dict:data:value"}, allEntries = true)
    public Boolean deleteDictDataList(List<Long> dictDataIds) {
        if (CollectionUtils.isEmpty(dictDataIds)) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "字典数据ID列表不能为空");
        }
        
        return dictDataMapper.deleteBatchIds(dictDataIds) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"system:dict:data", "system:dict:data:enabled", "system:dict:data:value"}, allEntries = true)
    public Boolean deleteDictDataByType(String dictType) {
        if (!StringUtils.hasText(dictType)) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "字典类型不能为空");
        }
        
        LambdaQueryWrapper<DictData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DictData::getDictType, dictType);
        
        return dictDataMapper.delete(wrapper) > 0;
    }
    
    @Override
    @CacheEvict(value = {"system:dict:data", "system:dict:data:enabled", "system:dict:data:value"}, allEntries = true)
    public Boolean changeDictDataStatus(Long dictDataId, Integer status) {
        if (dictDataId == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "字典数据ID不能为空");
        }
        
        if (status == null || (status != 0 && status != 1)) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "状态值无效");
        }
        
        // 检查字典数据是否存在
        DictData dictData = dictDataMapper.selectById(dictDataId);
        if (dictData == null) {
            throw new BusinessException(ResultCode.DICT_DATA_NOT_FOUND);
        }
        
        dictData.setStatus(status);
        dictData.setUpdatedAt(LocalDateTime.now());
        
        return dictDataMapper.updateById(dictData) > 0;
    }
    
    @Override
    @Cacheable(value = "system:dict:label", key = "#dictType + ':' + #dictValue")
    public String getDictLabel(String dictType, String dictValue) {
        if (!StringUtils.hasText(dictType) || !StringUtils.hasText(dictValue)) {
            return null;
        }
        
        DictDataDTO dictData = getDictDataByTypeAndValue(dictType, dictValue);
        return dictData != null ? dictData.getDictLabel() : null;
    }
    
    /**
     * 检查字典值是否存在
     */
    private Boolean existsDictValue(String dictType, String dictValue, Long excludeId) {
        if (!StringUtils.hasText(dictType) || !StringUtils.hasText(dictValue)) {
            return false;
        }
        
        LambdaQueryWrapper<DictData> wrapper = new LambdaQueryWrapper