package com.permission.system.controller;

import com.permission.common.result.Result;
import com.permission.system.dto.DictDataDTO;
import com.permission.system.service.DictDataService;
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
 * 字典数据控制器
 *
 * @author system
 * @since 2024-01-01
 */
// @Tag(name = "字典数据管理", description = "字典数据相关接口")
@RestController
@RequestMapping("/api/system/dict/data")
@RequiredArgsConstructor
@Validated
public class DictDataController {
    
    private final DictDataService dictDataService;
    
    // @Operation(summary = "根据字典类型查询字典数据")
    @GetMapping("/type/{dictType}")
    @PreAuthorize("hasAuthority('system:dict:list')")
    public Result<List<DictDataDTO>> getDictDataByType(
            /* @Parameter(description = "字典类型") */ @PathVariable @NotEmpty String dictType) {
        List<DictDataDTO> list = dictDataService.getDictDataByType(dictType);
        return Result.success(list);
    }
    
    // @Operation(summary = "根据字典类型查询启用的字典数据")
    @GetMapping("/type/{dictType}/enabled")
    public Result<List<DictDataDTO>> getEnabledDictDataByType(
            /* @Parameter(description = "字典类型") */ @PathVariable @NotEmpty String dictType) {
        List<DictDataDTO> list = dictDataService.getEnabledDictDataByType(dictType);
        return Result.success(list);
    }
    
    // @Operation(summary = "根据ID查询字典数据详情")
    @GetMapping("/{dictDataId}")
    @PreAuthorize("hasAuthority('system:dict:query')")
    public Result<DictDataDTO> getDictDataById(
            /* @Parameter(description = "字典数据ID") */ @PathVariable @NotNull Long dictDataId) {
        DictDataDTO dictData = dictDataService.getDictDataById(dictDataId);
        return Result.success(dictData);
    }
    
    // @Operation(summary = "根据字典类型和字典值查询字典数据")
    @GetMapping("/type/{dictType}/value/{dictValue}")
    public Result<DictDataDTO> getDictDataByTypeAndValue(
            /* @Parameter(description = "字典类型") */ @PathVariable @NotEmpty String dictType,
            /* @Parameter(description = "字典值") */ @PathVariable @NotEmpty String dictValue) {
        DictDataDTO dictData = dictDataService.getDictDataByTypeAndValue(dictType, dictValue);
        return Result.success(dictData);
    }
    
    // @Operation(summary = "创建字典数据")
    @PostMapping
    @PreAuthorize("hasAuthority('system:dict:add')")
    public Result<Long> createDictData(@Valid @RequestBody DictDataDTO dictDataDTO) {
        Long dictDataId = dictDataService.createDictData(dictDataDTO);
        return Result.success(dictDataId);
    }
    
    // @Operation(summary = "更新字典数据")
    @PutMapping("/{dictDataId}")
    @PreAuthorize("hasAuthority('system:dict:edit')")
    public Result<Boolean> updateDictData(
            /* @Parameter(description = "字典数据ID") */ @PathVariable @NotNull Long dictDataId,
            @Valid @RequestBody DictDataDTO dictDataDTO) {
        Boolean result = dictDataService.updateDictData(dictDataId, dictDataDTO);
        return Result.success(result);
    }
    
    // @Operation(summary = "删除字典数据")
    @DeleteMapping("/{dictDataId}")
    @PreAuthorize("hasAuthority('system:dict:remove')")
    public Result<Boolean> deleteDictData(
            /* @Parameter(description = "字典数据ID") */ @PathVariable @NotNull Long dictDataId) {
        Boolean result = dictDataService.deleteDictData(dictDataId);
        return Result.success(result);
    }
    
    // @Operation(summary = "批量删除字典数据")
    @DeleteMapping("/batch")
    @PreAuthorize("hasAuthority('system:dict:remove')")
    public Result<Boolean> deleteDictDataList(
            /* @Parameter(description = "字典数据ID列表") */ @RequestBody @NotEmpty List<Long> dictDataIds) {
        Boolean result = dictDataService.deleteDictDataList(dictDataIds);
        return Result.success(result);
    }
    
    // @Operation(summary = "根据字典类型删除字典数据")
    @DeleteMapping("/type/{dictType}")
    @PreAuthorize("hasAuthority('system:dict:remove')")
    public Result<Boolean> deleteDictDataByType(
            /* @Parameter(description = "字典类型") */ @PathVariable @NotEmpty String dictType) {
        Boolean result = dictDataService.deleteDictDataByType(dictType);
        return Result.success(result);
    }
    
    // @Operation(summary = "修改字典数据状态")
    @PutMapping("/{dictDataId}/status")
    @PreAuthorize("hasAuthority('system:dict:edit')")
    public Result<Boolean> changeDictDataStatus(
            /* @Parameter(description = "字典数据ID") */ @PathVariable @NotNull Long dictDataId,
            /* @Parameter(description = "状态") */ @RequestParam @NotNull Integer status) {
        Boolean result = dictDataService.changeDictDataStatus(dictDataId, status);
        return Result.success(result);
    }
    
    // @Operation(summary = "获取字典标签")
    @GetMapping("/label")
    public Result<String> getDictLabel(
            /* @Parameter(description = "字典类型") */ @RequestParam @NotEmpty String dictType,
            /* @Parameter(description = "字典值") */ @RequestParam @NotEmpty String dictValue) {
        String label = dictDataService.getDictLabel(dictType, dictValue);
        return Result.success(label);
    }
    
}