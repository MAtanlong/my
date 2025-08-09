package com.permission.common.result;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果
 *
 * @author system
 * @since 2024-01-01
 */
@Data
public class PageResult<T> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 当前页码
     */
    private Long pageNum;
    
    /**
     * 每页数量
     */
    private Long pageSize;
    
    /**
     * 总记录数
     */
    private Long total;
    
    /**
     * 总页数
     */
    private Long pages;
    
    /**
     * 数据列表
     */
    private List<T> list;
    
    public PageResult() {
    }
    
    public PageResult(Long pageNum, Long pageSize, Long total, List<T> list) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.list = list;
        this.pages = (total + pageSize - 1) / pageSize;
    }
    
    /**
     * 创建分页结果
     *
     * @param pageNum  当前页码
     * @param pageSize 每页数量
     * @param total    总记录数
     * @param list     数据列表
     * @return 分页结果
     */
    public static <T> PageResult<T> of(Long pageNum, Long pageSize, Long total, List<T> list) {
        return new PageResult<>(pageNum, pageSize, total, list);
    }
    
    /**
     * 创建空的分页结果
     *
     * @param pageNum  当前页码
     * @param pageSize 每页数量
     * @return 分页结果
     */
    public static <T> PageResult<T> empty(Long pageNum, Long pageSize) {
        return new PageResult<>(pageNum, pageSize, 0L, null);
    }
    
}