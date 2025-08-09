package com.permission.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 系统服务启动类
 *
 * @author system
 * @since 2024-01-01
 */
@SpringBootApplication(scanBasePackages = {"com.permission.system", "com.permission.common"})
@EnableDiscoveryClient
@EnableCaching
@MapperScan("com.permission.system.mapper")
public class SystemServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(SystemServiceApplication.class, args);
    }
    
}