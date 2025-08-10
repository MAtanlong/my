# 修复Nacos配置脚本
# 重新创建正确格式的配置文件

Write-Host "开始修复Nacos配置..." -ForegroundColor Cyan

# 获取访问令牌
$token = (Invoke-RestMethod -Uri "http://localhost:8848/nacos/v1/auth/login" -Method POST -Body @{username="nacos"; password="nacos"}).accessToken
Write-Host "成功获取访问令牌" -ForegroundColor Green

# 创建配置文件函数
function Create-Config {
    param(
        [string]$dataId,
        [string]$content
    )
    
    $body = @{
        dataId = $dataId
        group = "DEFAULT_GROUP"
        content = $content
        type = "yaml"
        tenant = ""
        accessToken = $token
    }
    
    try {
        $result = Invoke-RestMethod -Uri "http://localhost:8848/nacos/v1/cs/configs" -Method POST -Body $body
        if ($result -eq "true") {
            Write-Host "成功创建配置: $dataId" -ForegroundColor Green
        } else {
            Write-Host "创建配置失败: $dataId" -ForegroundColor Red
        }
    }
    catch {
        Write-Host "创建配置异常 $dataId : $($_.Exception.Message)" -ForegroundColor Red
    }
}

# 1. Gateway Service 配置
$gatewayConfig = @"
spring:
  cloud:
    nacos:
      discovery:
        server-addr: nacos:8848
        namespace: 
        group: DEFAULT_GROUP
      config:
        server-addr: nacos:8848
        namespace: 
        group: DEFAULT_GROUP
        file-extension: yml
    gateway:
      routes:
        - id: user-service
          uri: lb://user-service
          predicates:
            - Path=/api/user/**
          filters:
            - StripPrefix=1
        - id: auth-service
          uri: lb://auth-service
          predicates:
            - Path=/api/auth/**
          filters:
            - StripPrefix=1
        - id: menu-service
          uri: lb://menu-service
          predicates:
            - Path=/api/menu/**
          filters:
            - StripPrefix=1
        - id: system-service
          uri: lb://system-service
          predicates:
            - Path=/api/system/**
          filters:
            - StripPrefix=1
      globalcors:
        cors-configurations:
          "[/**]":
            allowedOriginPatterns: "*"
            allowedMethods: "*"
            allowedHeaders: "*"
            allowCredentials: true
            maxAge: 3600
  redis:
    host: localhost
    port: 6379
    password: 
    database: 0
    timeout: 5000ms
    lettuce:
      pool:
        max-active: 20
        max-wait: 3000ms
        max-idle: 10
        min-idle: 5

logging:
  level:
    com.permission: debug
    org.springframework.cloud.gateway: debug

management:
  endpoints:
    web:
      exposure:
        include: "*"
  endpoint:
    health:
      show-details: always
"@

# 先删除现有配置
Write-Host "删除现有配置..." -ForegroundColor Yellow
$configs = @("gateway-service.yml", "user-service.yml", "auth-service.yml", "menu-service.yml", "system-service.yml")
foreach ($config in $configs) {
    try {
        Invoke-RestMethod -Uri "http://localhost:8848/nacos/v1/cs/configs?dataId=$config&group=DEFAULT_GROUP&tenant=&accessToken=$token" -Method DELETE
        Write-Host "删除配置: $config" -ForegroundColor Yellow
    }
    catch {
        Write-Host "删除配置失败或不存在: $config" -ForegroundColor Gray
    }
}

# 创建新配置
Write-Host "创建新配置..." -ForegroundColor Cyan
Create-Config "gateway-service.yml" $gatewayConfig

Write-Host "Gateway配置修复完成!" -ForegroundColor Green
Write-Host "请访问 http://localhost:8848/nacos 查看配置" -ForegroundColor Cyan