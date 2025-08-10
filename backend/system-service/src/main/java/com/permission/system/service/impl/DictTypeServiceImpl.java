package com.permission.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.permission.common.exception.BusinessException;
import com.permission.common.result.ResultCode;
import com.permission.system.dto.DictTypeDTO;
import com.permission.system.entity.DictType;
import com.permission.system.mapper.DictTypeMapper;
import com.permission.system.service.DictDataService;
import com.permission.system.service.DictTypeService;
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
import java.util.stream.Collectors;

/**
 * 字典类型服务实现类
 *
 * @author system
 * @since 2024-01-01
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DictTypeServiceImpl implements DictTypeService {
    
    private final DictTypeMapper dictTypeMapper;
    private final DictDataService dictDataService;
    
    @Override
    public List<DictTypeDTO> getDictTypeList() {
        List<DictType> dictTypes = dictTypeMapper.selectList(null);
        return dictTypes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public IPage<DictTypeDTO> getDictTypePage(Long page, Long size, String keyword) {
        Page<DictType> pageParam = new Page<>(page, size);
        
        LambdaQueryWrapper<DictType> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            wrapper.like(DictType::getDictName, keyword)
                    .or()
                    .like(DictType::getDictType, keyword);
        }
        wrapper.orderByDesc(DictType::getCreatedAt);
        
        IPage<DictType> dictTypePage = dictTypeMapper.selectPage(pageParam, wrapper);
        
        // 转换为DTO
        IPage<DictTypeDTO> result = new Page<>(page, size, dictTypePage.getTotal());
        List<DictTypeDTO> dtoList = dictTypePage.getRecords().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        result.setRecords(dtoList);
        
        return result;
    }
    
    @Override
    public List<DictTypeDTO> getDictTypeListWithData() {
        List<DictType> dictTypes = dictTypeMapper.selectList(null);
        return dictTypes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public DictTypeDTO getDictTypeById(Long dictTypeId) {
        if (dictTypeId == null) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "字典类型ID不能为空");
        }
        
        DictType dictType = dictTypeMapper.selectById(dictTypeId);
        if (dictType == null) {
            throw new BusinessException(ResultCode.DICT_TYPE_NOT_FOUND);
        }
        
        DictTypeDTO dto = convertToDTO(dictType);
        // 加载字典数据
        dto.setDictDataList(dictDataService.getDictDataByType(dictType.getDictType()));
        
        return dto;
    }
    
    @Override
    @Cacheable(value = "system:dict:type", key = "#dictType")
    public DictTypeDTO getDictTypeByType(String dictType) {
        if (!StringUtils.hasText(dictType)) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "字典类型不能为空");
        }
        
        return dictTypeMapper.selectDictTypeByType(dictType);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"system:dict:type", "system:dict:enabled"}, allEntries = true)
    public Long createDictType(DictTypeDTO dictTypeDTO) {
        // 检查字典类型是否已存在
        if (existsDictType(dictTypeDTO.getDictType(), null)) {
            throw new BusinessException(ResultCode.DICT_TYPE_EXISTS);
        }
        
        // 创建字典类型
        DictType dictType = new DictType();
        BeanUtils.copyProperties(dictTypeDTO, dictType);
        dictType.setCreatedAt(LocalDateTime.now());
        dictType.setUpdatedAt(LocalDateTime.now());
        
        dictTypeMapper.insert(dictType);
        
        return dictType.getId();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"system:dict:type", "system:dict:enabled"}, allEntries = true)
    public Boolean updateDictType(Long dictTypeId, DictTypeDTO dictTypeDTO) {
        if (dictTypeId == null) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "字典类型ID不能为空");
        }
        
        // 检查字典类型是否存在
        DictType existingDictType = dictTypeMapper.selectById(dictTypeId);
        if (existingDictType == null) {
            throw new BusinessException(ResultCode.DICT_TYPE_NOT_FOUND);
        }
        
        // 检查字典类型是否已存在（排除当前字典类型）
        if (existsDictType(dictTypeDTO.getDictType(), dictTypeId)) {
            throw new BusinessException(ResultCode.DICT_TYPE_EXISTS);
        }
        
        // 更新字典类型信息
        DictType dictType = new DictType();
        BeanUtils.copyProperties(dictTypeDTO, dictType);
        dictType.setId(dictTypeId);
        dictType.setUpdatedAt(LocalDateTime.now());
        
        return dictTypeMapper.updateById(dictType) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"system:dict:type", "system:dict:enabled"}, allEntries = true)
    public Boolean deleteDictType(Long dictTypeId) {
        if (dictTypeId == null) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "字典类型ID不能为空");
        }
        
        // 检查字典类型是否存在
        DictType dictType = dictTypeMapper.selectById(dictTypeId);
        if (dictType == null) {
            throw new BusinessException(ResultCode.DICT_TYPE_NOT_FOUND);
        }
        
        // 删除关联的字典数据
        dictDataService.deleteDictDataByType(dictType.getDictType());
        
        // 删除字典类型
        return dictTypeMapper.deleteById(dictTypeId) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"system:dict:type", "system:dict:enabled"}, allEntries = true)
    public Boolean deleteDictTypes(List<Long> dictTypeIds) {
        if (CollectionUtils.isEmpty(dictTypeIds)) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "字典类型ID列表不能为空");
        }
        
        // 获取字典类型列表
        List<DictType> dictTypes = dictTypeMapper.selectBatchIds(dictTypeIds);
        if (dictTypes.isEmpty()) {
            throw new BusinessException(ResultCode.DICT_TYPE_NOT_FOUND);
        }
        
        // 删除关联的字典数据
        for (DictType dictType : dictTypes) {
            dictDataService.deleteDictDataByType(dictType.getDictType());
        }
        
        // 批量删除字典类型
        return dictTypeMapper.deleteBatchIds(dictTypeIds) > 0;
    }
    
    @Override
    @CacheEvict(value = {"system:dict:type", "system:dict:enabled"}, allEntries = true)
    public Boolean changeDictTypeStatus(Long dictTypeId, Integer status) {
        if (dictTypeId == null) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "字典类型ID不能为空");
        }
        
        if (status == null || (status != 0 && status != 1)) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "状态值无效");
        }
        
        // 检查字典类型是否存在
        DictType dictType = dictTypeMapper.selectById(dictTypeId);
        if (dictType == null) {
            throw new BusinessException(ResultCode.DICT_TYPE_NOT_FOUND);
        }
        
        dictType.setStatus(status);
        dictType.setUpdatedAt(LocalDateTime.now());
        
        return dictTypeMapper.updateById(dictType) > 0;
    }
    
    @Override
    @Cacheable(value = "system:dict:enabled")
    public List<DictTypeDTO> getEnabledDictTypes() {
        return dictTypeMapper.selectEnabledDictTypes();
    }
    
    @Override
    public Boolean existsDictType(String dictType, Long excludeId) {
        if (!StringUtils.hasText(dictType)) {
            return false;
        }
        
        LambdaQueryWrapper<DictType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DictType::getDictType, dictType);
        if (excludeId != null) {
            wrapper.ne(DictType::getId, excludeId);
        }
        
        return dictTypeMapper.selectCount(wrapper) > 0;
    }
    
    @Override
    @CacheEvict(value = {"system:dict", "system:dict:type", "system:dict:data"}, allEntries = true)
    public void refreshDictCache() {
        log.info("字典缓存已刷新");
    }
    
    /**
     * 转换为DTO
     */
    private DictTypeDTO convertToDTO(DictType dictType) {
        DictTypeDTO dto = new DictTypeDTO();
        BeanUtils.copyProperties(dictType, dto);
        return dto;
    }
    
}