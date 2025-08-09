package com.permission.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * MyBatis-Plus配置
 *
 * @author system
 * @since 2024-01-01
 */
@Slf4j
@Configuration
public class MyBatisPlusConfig implements MetaObjectHandler {
    
    /**
     * 分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }
    
    /**
     * 插入时自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("开始插入填充...");
        
        // 填充创建时间
        this.strictInsertFill(metaObject, "createdAt", LocalDateTime.class, LocalDateTime.now());
        // 填充更新时间
        this.strictInsertFill(metaObject, "updatedAt", LocalDateTime.class, LocalDateTime.now());
        // 填充删除标记
        this.strictInsertFill(metaObject, "deleted", Integer.class, 0);
        
        // TODO: 从当前登录用户获取用户ID
        Long currentUserId = getCurrentUserId();
        if (currentUserId != null) {
            // 填充创建人
            this.strictInsertFill(metaObject, "createdBy", Long.class, currentUserId);
            // 填充更新人
            this.strictInsertFill(metaObject, "updatedBy", Long.class, currentUserId);
        }
    }
    
    /**
     * 更新时自动填充
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("开始更新填充...");
        
        // 填充更新时间
        this.strictUpdateFill(metaObject, "updatedAt", LocalDateTime.class, LocalDateTime.now());
        
        // TODO: 从当前登录用户获取用户ID
        Long currentUserId = getCurrentUserId();
        if (currentUserId != null) {
            // 填充更新人
            this.strictUpdateFill(metaObject, "updatedBy", Long.class, currentUserId);
        }
    }
    
    /**
     * 获取当前登录用户ID
     */
    private Long getCurrentUserId() {
        return com.permission.common.utils.SecurityUtils.getCurrentUserId();
    }
    
}