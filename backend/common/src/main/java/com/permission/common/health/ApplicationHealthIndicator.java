package com.permission.common.health;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuator.health.Health;
import org.springframework.boot.actuator.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.RuntimeMXBean;

/**
 * 应用程序健康检查指示器
 *
 * @author system
 * @since 2024-01-01
 */
@Slf4j
@Component
public class ApplicationHealthIndicator implements HealthIndicator {
    
    private static final double MEMORY_THRESHOLD = 0.9; // 内存使用率阈值90%
    
    @Override
    public Health health() {
        try {
            return checkApplicationHealth();
        } catch (Exception e) {
            log.error("应用程序健康检查失败", e);
            return Health.down()
                    .withDetail("error", e.getMessage())
                    .withDetail("application", "检查失败")
                    .build();
        }
    }
    
    private Health checkApplicationHealth() {
        Health.Builder builder = Health.up();
        
        // 检查JVM内存使用情况
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemory = memoryBean.getHeapMemoryUsage();
        
        long used = heapMemory.getUsed();
        long max = heapMemory.getMax();
        double memoryUsage = (double) used / max;
        
        builder.withDetail("memory", String.format("%.2f%%", memoryUsage * 100));
        builder.withDetail("memory_used", formatBytes(used));
        builder.withDetail("memory_max", formatBytes(max));
        
        // 检查运行时间
        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
        long uptime = runtimeBean.getUptime();
        builder.withDetail("uptime", formatUptime(uptime));
        
        // 检查线程数
        int threadCount = ManagementFactory.getThreadMXBean().getThreadCount();
        builder.withDetail("thread_count", threadCount);
        
        // 根据内存使用率判断健康状态
        if (memoryUsage > MEMORY_THRESHOLD) {
            builder = Health.down();
            builder.withDetail("status", "内存使用率过高");
        } else {
            builder.withDetail("status", "运行正常");
        }
        
        return builder.build();
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
    
    private String formatUptime(long uptime) {
        long seconds = uptime / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        
        if (days > 0) {
            return String.format("%d天%d小时%d分钟", days, hours % 24, minutes % 60);
        } else if (hours > 0) {
            return String.format("%d小时%d分钟", hours, minutes % 60);
        } else {
            return String.format("%d分钟", minutes);
        }
    }
}