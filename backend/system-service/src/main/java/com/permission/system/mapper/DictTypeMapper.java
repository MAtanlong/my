package com.permission.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.permission.system.dto.DictTypeDTO;
import com.permission.system.entity.DictType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典类型Mapper接口
 *
 * @author system
 * @since 2024-01-01
 */
@Mapper
public interface DictTypeMapper extends BaseMapper<DictType> {
    
    /**
     * 查询字典类型列表（包含字典数据）
     *
     * @return 字典类型列表
     */
    List<DictTypeDTO> selectDictTypeList();
    
    /**
     * 根据字典类型查询字典类型信息
     *
     * @param dictType 字典类型
     * @return 字典类型信息
     */
    DictTypeDTO selectDictTypeByType(@Param("dictType") String dictType);
    
    /**
     * 查询所有启用的字典类型
     *
     * @return 字典类型列表
     */
    List<DictTypeDTO> selectEnabledDictTypes();
}