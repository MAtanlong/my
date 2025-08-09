package com.permission.common.controller;

import com.permission.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuator.health.Health;
import org.springframework.boot.actuator.health.HealthEndpoint;
import org.springframework.boot.actuator.health.HealthIndicator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 健康检查控制器
 *
 * @author system
 * @since 2024-01-01
 */
@Slf4j
@RestController
@RequestMapping("/health")
@Tag(name = "健康检查", description = "应用程序健康状态检查接口")
public class HealthController {
    
    @Autowired(required = false)
    private HealthEndpoint healthEndpoint;
    
    @Autowired
    private Map<String, HealthIndicator> healthIndicators;
    
    @Operation(summary = "获取应用健康状态")
    @GetMapping("/status")
    public Result<Map<String, Object>> getHealthStatus() {
        Map<String, Object> healthInfo = new HashMap<>();
        
        try {
            // 获取整体健康状态
            if (healthEndpoint != null) {
                Health health = healthEndpoint.health();
                healthInfo.put("status", health.getStatus().getCode());
                healthInfo.put("details", health.getDetails());
            }
            
            // 获取各个组件的健康状态
            Map<String, Object> components = new HashMap<>();
            for (Map.Entry<String, HealthIndicator> entry : healthIndicators.entrySet()) {
                String name = entry.getKey().replace("HealthIndicator", "").toLowerCase();
                Health componentHealth = entry.getValue().health();
                
                Map<String, Object> componentInfo = new HashMap<>();
                componentInfo.put("status", componentHealth.getStatus().getCode());
                componentInfo.put("details", componentHealth.getDetails());
                
                components.put(name, componentInfo);
            }
            healthInfo.put("components", components);
            
            // 添加检查时间
            healthInfo.put("timestamp", LocalDateTime.now());
            
            return Result.success(healthInfo);
            
        } catch (Exception e) {
            log.error("获取健康状态失败", e);
            
            Map<String, Object> errorInfo = new HashMap<>();
            errorInfo.put("status", "DOWN");
            errorInfo.put("error", e.getMessage());
            errorInfo.put("timestamp", LocalDateTime.now());
            
            return Result.failed("健康检查失败").data(errorInfo);
        }
    }
    
    @Operation(summary = "简单健康检查")
    @GetMapping("/ping")
    public Result<String> ping() {
        return Result.success("pong");
    }
    
    @Operation(summary = "获取应用信息")
    @GetMapping("/info")
    public Result<Map<String, Object>> getApplicationInfo() {
        Map<String, Object> info = new HashMap<>();
        
        // 应用信息
        info.put("application", "Permission Management System");
        info.put("version", "1.0.0");
        info.put("timestamp", LocalDateTime.now());
        
        // JVM信息
        Runtime runtime = Runtime.getRuntime();
        Map<String, Object> jvmInfo = new HashMap<>();
        jvmInfo.put("java_version", System.getProperty("java.version"));
        jvmInfo.put("java_vendor", System.getProperty("java.vendor"));
        jvmInfo.put("processors", runtime.availableProcessors());
        jvmInfo.put("max_memory", formatBytes(runtime.maxMemory()));
        jvmInfo.put("total_memory", formatBytes(runtime.totalMemory()));
        jvmInfo.put("free_memory", formatBytes(runtime.freeMemory()));
        
        info.put("jvm", jvmInfo);
        
        return Result.success(info);
    }
    
    private String formatBytes(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        } else if (bytes < 1024 * 1024) {
            return String.format("%.2f KB", bytes / 1024.0);
        } else if (bytes < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", bytes / (1024.0 * 1024));
        } else {
            return String.format("%.2f GB", bytes / (1024.0 * 1024 * 1024));
        }
    }
}