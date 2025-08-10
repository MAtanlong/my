package com.permission.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.permission.common.exception.BusinessException;
import com.permission.common.result.PageResult;
import com.permission.common.result.ResultCode;
import com.permission.system.dto.DictDataCreateDTO;
import com.permission.system.dto.DictDataDTO;
import com.permission.system.dto.DictDataQueryDTO;
import com.permission.system.entity.DictData;
import com.permission.system.mapper.DictDataMapper;
import com.permission.system.service.DictDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典数据服务实现类
 *
 * @author system
 * @since 2024-01-01
 */
@Service
@RequiredArgsConstructor
public class DictDataServiceImpl implements DictDataService {
    
    private final DictDataMapper dictDataMapper;
    
    @Override
    @Cacheable(value = "system:dict:data", key = "#dictDataId")
    public DictDataDTO getDictDataById(Long dictDataId) {
        if (dictDataId == null) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "字典数据ID不能为空");
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
    public List<DictDataDTO> getDictDataByType(String dictType) {
        if (!StringUtils.hasText(dictType)) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "字典类型不能为空");
        }
        
        LambdaQueryWrapper<DictData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DictData::getDictType, dictType)
                .orderByAsc(DictData::getDictSort);
        
        List<DictData> dictDataList = dictDataMapper.selectList(wrapper);
        
        return dictDataList.stream()
                .map(dictData -> {
                    DictDataDTO dto = new DictDataDTO();
                    BeanUtils.copyProperties(dictData, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public DictDataDTO getDictDataByTypeAndValue(String dictType, String dictValue) {
        if (!StringUtils.hasText(dictType) || !StringUtils.hasText(dictValue)) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "字典类型和字典值不能为空");
        }
        
        LambdaQueryWrapper<DictData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DictData::getDictType, dictType)
                .eq(DictData::getDictValue, dictValue);
        
        DictData dictData = dictDataMapper.selectOne(wrapper);
        if (dictData == null) {
            throw new BusinessException(ResultCode.DICT_DATA_NOT_FOUND);
        }
        
        DictDataDTO dto = new DictDataDTO();
        BeanUtils.copyProperties(dictData, dto);
        return dto;
    }
    
    @Override
    public List<DictDataDTO> getEnabledDictDataByType(String dictType) {
        if (!StringUtils.hasText(dictType)) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "字典类型不能为空");
        }
        
        LambdaQueryWrapper<DictData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DictData::getDictType, dictType)
                .eq(DictData::getStatus, 1)
                .orderByAsc(DictData::getDictSort);
        
        List<DictData> dictDataList = dictDataMapper.selectList(wrapper);
        
        return dictDataList.stream()
                .map(dictData -> {
                    DictDataDTO dto = new DictDataDTO();
                    BeanUtils.copyProperties(dictData, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public String getDictLabel(String dictType, String dictValue) {
        if (!StringUtils.hasText(dictType) || !StringUtils.hasText(dictValue)) {
            return "";
        }
        
        LambdaQueryWrapper<DictData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DictData::getDictType, dictType)
                .eq(DictData::getDictValue, dictValue)
                .eq(DictData::getStatus, 1);
        
        DictData dictData = dictDataMapper.selectOne(wrapper);
        return dictData != null ? dictData.getDictLabel() : "";
    }
    
    @Override
    public PageResult<DictDataDTO> getDictDataPage(DictDataQueryDTO queryDTO) {
        Page<DictData> page = new Page<>(queryDTO.getCurrent(), queryDTO.getSize());
        
        LambdaQueryWrapper<DictData> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(queryDTO.getDictType())) {
            wrapper.eq(DictData::getDictType, queryDTO.getDictType());
        }
        if (StringUtils.hasText(queryDTO.getDictLabel())) {
            wrapper.like(DictData::getDictLabel, queryDTO.getDictLabel());
        }
        if (queryDTO.getStatus() != null) {
            wrapper.eq(DictData::getStatus, queryDTO.getStatus());
        }
        wrapper.orderByAsc(DictData::getDictSort);
        
        IPage<DictData> result = dictDataMapper.selectPage(page, wrapper);
        
        List<DictDataDTO> dtoList = result.getRecords().stream()
                .map(dictData -> {
                    DictDataDTO dto = new DictDataDTO();
                    BeanUtils.copyProperties(dictData, dto);
                    return dto;
                })
                .collect(Collectors.toList());
        
        return PageResult.of(result.getCurrent(), result.getSize(), result.getTotal(), dtoList);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"system:dict:data", "system:dict:data:enabled", "system:dict:data:value"}, allEntries = true)
    public Long createDictData(DictDataDTO dictDataDTO) {
        // 检查字典值是否已存在
        if (existsDictValue(dictDataDTO.getDictType(), dictDataDTO.getDictValue(), null)) {
            throw new BusinessException(ResultCode.DICT_DATA_VALUE_EXISTS);
        }
        
        DictData dictData = new DictData();
        BeanUtils.copyProperties(dictDataDTO, dictData);
        dictData.setCreatedAt(LocalDateTime.now());
        
        dictDataMapper.insert(dictData);
        return dictData.getId();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"system:dict:data", "system:dict:data:enabled", "system:dict:data:value"}, allEntries = true)
    public Boolean updateDictData(Long dictDataId, DictDataDTO dictDataDTO) {
        if (dictDataId == null) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "字典数据ID不能为空");
        }
        
        DictData existingDictData = dictDataMapper.selectById(dictDataId);
        if (existingDictData == null) {
            throw new BusinessException(ResultCode.DICT_DATA_NOT_FOUND);
        }
        
        // 检查字典值是否已存在（排除当前数据）
        if (existsDictValue(dictDataDTO.getDictType(), dictDataDTO.getDictValue(), dictDataId)) {
            throw new BusinessException(ResultCode.DICT_DATA_VALUE_EXISTS);
        }
        
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
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "字典数据ID不能为空");
        }
        
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
        if (dictDataIds == null || dictDataIds.isEmpty()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "字典数据ID列表不能为空");
        }
        
        return dictDataMapper.deleteBatchIds(dictDataIds) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"system:dict:data", "system:dict:data:enabled", "system:dict:data:value"}, allEntries = true)
    public Boolean deleteDictDataByType(String dictType) {
        if (!StringUtils.hasText(dictType)) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "字典类型不能为空");
        }
        
        LambdaQueryWrapper<DictData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DictData::getDictType, dictType);
        
        return dictDataMapper.delete(wrapper) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"system:dict:data", "system:dict:data:enabled", "system:dict:data:value"}, allEntries = true)
    public Boolean changeDictDataStatus(Long dictDataId, Integer status) {
        if (dictDataId == null) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "字典数据ID不能为空");
        }
        
        if (status == null || (status != 0 && status != 1)) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "状态值无效");
        }
        
        DictData dictData = dictDataMapper.selectById(dictDataId);
        if (dictData == null) {
            throw new BusinessException(ResultCode.DICT_DATA_NOT_FOUND);
        }
        
        dictData.setStatus(status);
        dictData.setUpdatedAt(LocalDateTime.now());
        
        return dictDataMapper.updateById(dictData) > 0;
    }
    
    private boolean existsDictValue(String dictType, String dictValue, Long excludeId) {
        LambdaQueryWrapper<DictData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DictData::getDictType, dictType)
                .eq(DictData::getDictValue, dictValue);
        
        if (excludeId != null) {
            wrapper.ne(DictData::getId, excludeId);
        }
        
        return dictDataMapper.selectCount(wrapper) > 0;
    }
    
    private boolean existsDictValue(String dictType, String dictValue) {
        return existsDictValue(dictType, dictValue, null);
    }
}