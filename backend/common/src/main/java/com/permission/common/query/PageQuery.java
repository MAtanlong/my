package com.permission.common.query;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 分页查询基础类
 *
 * @author system
 * @since 2024-01-01
 */
@Data
public class PageQuery implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 当前页码
     */
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为1")
    private Integer pageNum = 1;
    
    /**
     * 每页数量
     */
    @NotNull(message = "每页数量不能为空")
    @Min(value = 1, message = "每页数量最小值为1")
    @Max(value = 100, message = "每页数量最大值为100")
    private Integer pageSize = 10;
    
    /**
     * 排序字段
     */
    private String orderBy;
    
    /**
     * 排序方向：asc-升序，desc-降序
     */
    private String orderDirection = "desc";
    
    /**
     * 搜索关键字
     */
    private String keyword;
    
}