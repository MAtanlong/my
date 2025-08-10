package com.permission.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.permission.common.result.Result;
import com.permission.system.dto.DictTypeDTO;
import com.permission.system.service.DictTypeService;
// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameter;
// import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 字典类型控制器
 *
 * @author system
 * @since 2024-01-01
 */
// @Tag(name = "字典类型管理", description = "字典类型相关接口")
@RestController
@RequestMapping("/api/system/dict/type")
@RequiredArgsConstructor
@Validated
public class DictTypeController {
    
    private final DictTypeService dictTypeService;
    
    // @Operation(summary = "分页查询字典类型")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('system:dict:list')")
    public Result<IPage<DictTypeDTO>> getDictTypePage(
            /* @Parameter(description = "页码") */ @RequestParam(defaultValue = "1") Integer page,
            /* @Parameter(description = "每页大小") */ @RequestParam(defaultValue = "10") Integer size,
            /* @Parameter(description = "搜索关键词") */ @RequestParam(required = false) String keyword) {
        IPage<DictTypeDTO> pageResult = dictTypeService.getDictTypePage(page.longValue(), size.longValue(), keyword);
        return Result.success(pageResult);
    }
    
    // @Operation(summary = "查询字典类型列表（包含字典数据）")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('system:dict:list')")
    public Result<List<DictTypeDTO>> getDictTypeListWithData() {
        List<DictTypeDTO> list = dictTypeService.getDictTypeListWithData();
        return Result.success(list);
    }
    
    // @Operation(summary = "根据ID查询字典类型详情")
    @GetMapping("/{dictTypeId}")
    @PreAuthorize("hasAuthority('system:dict:query')")
    public Result<DictTypeDTO> getDictTypeById(
            /* @Parameter(description = "字典类型ID") */ @PathVariable @NotNull Long dictTypeId) {
        DictTypeDTO dictType = dictTypeService.getDictTypeById(dictTypeId);
        return Result.success(dictType);
    }
    
    // @Operation(summary = "根据字典类型查询详情")
    @GetMapping("/type/{dictType}")
    @PreAuthorize("hasAuthority('system:dict:query')")
    public Result<DictTypeDTO> getDictTypeByType(
            /* @Parameter(description = "字典类型") */ @PathVariable @NotEmpty String dictType) {
        DictTypeDTO dictTypeDTO = dictTypeService.getDictTypeByType(dictType);
        return Result.success(dictTypeDTO);
    }
    
    // @Operation(summary = "创建字典类型")
    @PostMapping
    @PreAuthorize("hasAuthority('system:dict:add')")
    public Result<Long> createDictType(@Valid @RequestBody DictTypeDTO dictTypeDTO) {
        Long dictTypeId = dictTypeService.createDictType(dictTypeDTO);
        return Result.success(dictTypeId);
    }
    
    // @Operation(summary = "更新字典类型")
    @PutMapping("/{dictTypeId}")
    @PreAuthorize("hasAuthority('system:dict:edit')")
    public Result<Boolean> updateDictType(
            /* @Parameter(description = "字典类型ID") */ @PathVariable @NotNull Long dictTypeId,
            @Valid @RequestBody DictTypeDTO dictTypeDTO) {
        Boolean result = dictTypeService.updateDictType(dictTypeId, dictTypeDTO);
        return Result.success(result);
    }
    
    // @Operation(summary = "删除字典类型")
    @DeleteMapping("/{dictTypeId}")
    @PreAuthorize("hasAuthority('system:dict:remove')")
    public Result<Boolean> deleteDictType(
            /* @Parameter(description = "字典类型ID") */ @PathVariable @NotNull Long dictTypeId) {
        Boolean result = dictTypeService.deleteDictType(dictTypeId);
        return Result.success(result);
    }
    
    // @Operation(summary = "批量删除字典类型")
    @DeleteMapping("/batch")
    @PreAuthorize("hasAuthority('system:dict:remove')")
    public Result<Boolean> deleteDictTypes(
            /* @Parameter(description = "字典类型ID列表") */ @RequestBody @NotEmpty List<Long> dictTypeIds) {
        Boolean result = dictTypeService.deleteDictTypes(dictTypeIds);
        return Result.success(result);
    }
    
    // @Operation(summary = "修改字典类型状态")
    @PutMapping("/{dictTypeId}/status")
    @PreAuthorize("hasAuthority('system:dict:edit')")
    public Result<Boolean> changeDictTypeStatus(
            /* @Parameter(description = "字典类型ID") */ @PathVariable @NotNull Long dictTypeId,
            /* @Parameter(description = "状态") */ @RequestParam @NotNull Integer status) {
        Boolean result = dictTypeService.changeDictTypeStatus(dictTypeId, status);
        return Result.success(result);
    }
    
    // @Operation(summary = "查询所有启用的字典类型")
    @GetMapping("/enabled")
    public Result<List<DictTypeDTO>> getEnabledDictTypes() {
        List<DictTypeDTO> dictTypes = dictTypeService.getEnabledDictTypes();
        return Result.success(dictTypes);
    }
    
    // @Operation(summary = "检查字典类型是否存在")
    @GetMapping("/exists")
    @PreAuthorize("hasAuthority('system:dict:query')")
    public Result<Boolean> existsDictType(
            /* @Parameter(description = "字典类型") */ @RequestParam @NotEmpty String dictType,
            /* @Parameter(description = "排除的字典类型ID") */ @RequestParam(required = false) Long excludeId) {
        Boolean exists = dictTypeService.existsDictType(dictType, excludeId);
        return Result.success(exists);
    }
    
    // @Operation(summary = "刷新字典类型缓存")
    @PostMapping("/refresh")
    @PreAuthorize("hasAuthority('system:dict:edit')")
    public Result<Void> refreshDictTypeCache() {
        dictTypeService.refreshDictCache();
        return Result.success();
    }
    
}