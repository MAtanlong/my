package com.permission.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 认证服务启动类
 *
 * @author system
 * @since 2024-01-01
 */
@SpringBootApplication(scanBasePackages = {"com.permission.auth", "com.permission.common"})
@EnableDiscoveryClient
@EnableFeignClients
@EnableCaching
@MapperScan("com.permission.auth.mapper")
public class AuthServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }
    
}