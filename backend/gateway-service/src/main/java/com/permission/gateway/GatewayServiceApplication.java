package com.permission.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 网关服务启动类
 *
 * @author system
 * @since 2024-01-01
 */
@SpringBootApplication(scanBasePackages = {"com.permission.gateway"}, exclude = {DataSourceAutoConfiguration.class, WebMvcAutoConfiguration.class})
@EnableDiscoveryClient
public class GatewayServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }
    
}