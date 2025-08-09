package com.permission.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 网关服务启动类
 *
 * @author system
 * @since 2024-01-01
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }
    
}