package com.permission.common.health;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuator.health.Health;
import org.springframework.boot.actuator.health.HealthIndicator;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

/**
 * Redis健康检查指示器
 *
 * @author system
 * @since 2024-01-01
 */
@Slf4j
@Component
public class RedisHealthIndicator implements HealthIndicator {
    
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    
    @Override
    public Health health() {
        try {
            return checkRedisHealth();
        } catch (Exception e) {
            log.error("Redis健康检查失败", e);
            return Health.down()
                    .withDetail("error", e.getMessage())
                    .withDetail("redis", "连接失败")
                    .build();
        }
    }
    
    private Health checkRedisHealth() throws Exception {
        RedisConnection connection = null;
        try {
            connection = redisConnectionFactory.getConnection();
            
            // 执行PING命令测试连接
            String pong = connection.ping();
            
            if ("PONG".equals(pong)) {
                // 获取Redis信息
                String info = connection.info("server").getProperty("redis_version");
                
                return Health.up()
                        .withDetail("redis", "连接正常")
                        .withDetail("ping", "PONG")
                        .withDetail("version", info != null ? info : "未知")
                        .build();
            } else {
                return Health.down()
                        .withDetail("redis", "PING测试失败")
                        .withDetail("ping", pong)
                        .build();
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    log.warn("关闭Redis连接时发生异常", e);
                }
            }
        }
    }
}