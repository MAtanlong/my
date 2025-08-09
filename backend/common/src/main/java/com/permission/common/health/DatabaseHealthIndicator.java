package com.permission.common.health;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuator.health.Health;
import org.springframework.boot.actuator.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 数据库健康检查指示器
 *
 * @author system
 * @since 2024-01-01
 */
@Slf4j
@Component
public class DatabaseHealthIndicator implements HealthIndicator {
    
    @Autowired
    private DataSource dataSource;
    
    @Override
    public Health health() {
        try {
            return checkDatabaseHealth();
        } catch (Exception e) {
            log.error("数据库健康检查失败", e);
            return Health.down()
                    .withDetail("error", e.getMessage())
                    .withDetail("database", "连接失败")
                    .build();
        }
    }
    
    private Health checkDatabaseHealth() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            // 检查数据库连接
            if (connection.isValid(3)) {
                // 执行简单查询测试
                try (PreparedStatement statement = connection.prepareStatement("SELECT 1");
                     ResultSet resultSet = statement.executeQuery()) {
                    
                    if (resultSet.next() && resultSet.getInt(1) == 1) {
                        return Health.up()
                                .withDetail("database", "连接正常")
                                .withDetail("connection_valid", true)
                                .withDetail("query_test", "通过")
                                .build();
                    }
                }
            }
            
            return Health.down()
                    .withDetail("database", "连接异常")
                    .withDetail("connection_valid", false)
                    .build();
        }
    }
}