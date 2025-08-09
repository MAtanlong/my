package com.permission.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.permission.system.dto.DictDataDTO;
import com.permission.system.entity.DictData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典数据Mapper接口
 *
 * @author system
 * @since 2024-01-01
 */
@Mapper
public interface DictDataMapper extends BaseMapper<DictData> {
    
    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据列表
     */
    List<DictDataDTO> selectDictDataByType(@Param("dictType") String dictType);
    
    /**
     * 根据字典类型查询启用的字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据列表
     */
    List<DictDataDTO> selectEnabledDictDataByType(@Param("dictType") String dictType);
    
    /**
     * 根据字典类型和字典值查询字典数据
     *
     * @param dictType  字典类型
     * @param dictValue 字典值
     * @return 字典数据
     */
    DictDataDTO selectDictDataByTypeAndValue(@Param("dictType") String dictType, @Param("dictValue") String dictValue);
    
    /**
     * 批量删除字典数据
     *
     * @param dictType 字典类型
     * @return 删除数量
     */
    int deleteDictDataByType(@Param("dictType") String dictType);
    
}