# 自动配置Nacos配置文件脚本
# 整合了fix-nacos-config.ps1和create-other-configs.ps1的功能
# 包含等待Nacos就绪和完整的错误处理

param(
    [string]$NacosUrl = "http://localhost:8848",
    [string]$Username = "nacos",
    [string]$Password = "nacos",
    [int]$MaxRetries = 30,
    [int]$RetryInterval = 10
)

# 设置控制台编码
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "    Nacos 自动配置脚本" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# 等待Nacos就绪的函数
function Wait-NacosReady {
    param(
        [string]$Url,
        [int]$MaxRetries,
        [int]$Interval
    )
    
    Write-Host "[1/4] 等待Nacos服务就绪..." -ForegroundColor Yellow
    
    for ($i = 1; $i -le $MaxRetries; $i++) {
        try {
            $healthUrl = "$Url/nacos/v1/console/health/readiness"
            $response = Invoke-RestMethod -Uri $healthUrl -Method GET -TimeoutSec 5
            
            if ($response -eq "UP" -or $response.status -eq "UP") {
                Write-Host "✅ Nacos服务已就绪 (尝试 $i/$MaxRetries)" -ForegroundColor Green
                return $true
            }
        }
        catch {
            Write-Host "⏳ 等待Nacos启动... (尝试 $i/$MaxRetries)" -ForegroundColor Gray
        }
        
        if ($i -lt $MaxRetries) {
            Start-Sleep -Seconds $Interval
        }
    }
    
    Write-Host "❌ Nacos服务未能在预期时间内就绪" -ForegroundColor Red
    return $false
}

# 获取访问令牌的函数
function Get-NacosToken {
    param(
        [string]$Url,
        [string]$Username,
        [string]$Password
    )
    
    Write-Host "[2/4] 获取Nacos访问令牌..." -ForegroundColor Yellow
    
    try {
        $loginUrl = "$Url/nacos/v1/auth/login"
        $body = @{
            username = $Username
            password = $Password
        }
        
        $response = Invoke-RestMethod -Uri $loginUrl -Method POST -Body $body -TimeoutSec 10
        
        if ($response.accessToken) {
            Write-Host "✅ 成功获取访问令牌" -ForegroundColor Green
            return $response.accessToken
        } else {
            throw "未能获取访问令牌"
        }
    }
    catch {
        Write-Host "❌ 获取访问令牌失败: $($_.Exception.Message)" -ForegroundColor Red
        throw
    }
}

# 创建配置文件的函数
function Create-NacosConfig {
    param(
        [string]$Url,
        [string]$Token,
        [string]$DataId,
        [string]$Content
    )
    
    $body = @{
        dataId = $DataId
        group = "DEFAULT_GROUP"
        content = $Content
        type = "yaml"
        tenant = ""
        accessToken = $Token
    }
    
    try {
        $configUrl = "$Url/nacos/v1/cs/configs"
        $result = Invoke-RestMethod -Uri $configUrl -Method POST -Body $body -TimeoutSec 15
        
        if ($result -eq "true") {
            Write-Host "  ✅ 成功创建配置: $DataId" -ForegroundColor Green
            return $true
        } else {
            Write-Host "  ❌ 创建配置失败: $DataId" -ForegroundColor Red
            return $false
        }
    }
    catch {
        Write-Host "  ❌ 创建配置异常 $DataId : $($_.Exception.Message)" -ForegroundColor Red
        return $false
    }
}

# 删除现有配置的函数
function Remove-ExistingConfigs {
    param(
        [string]$Url,
        [string]$Token
    )
    
    Write-Host "[3/4] 清理现有配置..." -ForegroundColor Yellow
    
    $configs = @("gateway-service.yml", "user-service.yml", "auth-service.yml", "menu-service.yml", "system-service.yml")
    
    foreach ($config in $configs) {
        try {
            $deleteUrl = "$Url/nacos/v1/cs/configs?dataId=$config&group=DEFAULT_GROUP&tenant=&accessToken=$Token"
            Invoke-RestMethod -Uri $deleteUrl -Method DELETE -TimeoutSec 10
            Write-Host "  🗑️ 删除配置: $config" -ForegroundColor Gray
        }
        catch {
            Write-Host "  ℹ️ 配置不存在或删除失败: $config" -ForegroundColor Gray
        }
    }
}

# 主执行逻辑
try {
    # 等待Nacos就绪
    if (-not (Wait-NacosReady -Url $NacosUrl -MaxRetries $MaxRetries -Interval $RetryInterval)) {
        throw "Nacos服务未就绪，无法继续配置"
    }
    
    # 获取访问令牌
    $token = Get-NacosToken -Url $NacosUrl -Username $Username -Password $Password
    
    # 删除现有配置
    Remove-ExistingConfigs -Url $NacosUrl -Token $token
    
    Write-Host "[4/4] 创建新配置..." -ForegroundColor Yellow
    
    # 配置计数器
    $successCount = 0
    $totalCount = 0
    
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
    host: redis
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
    
    $totalCount++
    if (Create-NacosConfig -Url $NacosUrl -Token $token -DataId "gateway-service.yml" -Content $gatewayConfig) {
        $successCount++
    }
    
    # 2. User Service 配置
    $userConfig = @"
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
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://mysql:3306/permission_system?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false
    username: root
    password: 123456
  redis:
    host: redis
    port: 6379
    password:
    database: 1
    timeout: 5000ms
    lettuce:
      pool:
        max-active: 20
        max-wait: 3000ms
        max-idle: 10
        min-idle: 5

mybatis-plus:
  configuration:
    map-underscore-to-camel-case: true
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  global-config:
    db-config:
      id-type: auto
      logic-delete-field: deleted
      logic-delete-value: 1
      logic-not-delete-value: 0

logging:
  level:
    com.permission: debug

server:
  port: 8081
"@
    
    $totalCount++
    if (Create-NacosConfig -Url $NacosUrl -Token $token -DataId "user-service.yml" -Content $userConfig) {
        $successCount++
    }
    
    # 3. Auth Service 配置
    $authConfig = @"
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
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://mysql:3306/permission_system?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false
    username: root
    password: 123456
  redis:
    host: redis
    port: 6379
    password:
    database: 2
    timeout: 5000ms
    lettuce:
      pool:
        max-active: 20
        max-wait: 3000ms
        max-idle: 10
        min-idle: 5

mybatis-plus:
  configuration:
    map-underscore-to-camel-case: true
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  global-config:
    db-config:
      id-type: auto
      logic-delete-field: deleted
      logic-delete-value: 1
      logic-not-delete-value: 0

logging:
  level:
    com.permission: debug

server:
  port: 8082
"@
    
    $totalCount++
    if (Create-NacosConfig -Url $NacosUrl -Token $token -DataId "auth-service.yml" -Content $authConfig) {
        $successCount++
    }
    
    # 4. Menu Service 配置
    $menuConfig = @"
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
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://mysql:3306/permission_system?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false
    username: root
    password: 123456
  redis:
    host: redis
    port: 6379
    password:
    database: 3
    timeout: 5000ms
    lettuce:
      pool:
        max-active: 20
        max-wait: 3000ms
        max-idle: 10
        min-idle: 5

mybatis-plus:
  configuration:
    map-underscore-to-camel-case: true
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  global-config:
    db-config:
      id-type: auto
      logic-delete-field: deleted
      logic-delete-value: 1
      logic-not-delete-value: 0

logging:
  level:
    com.permission: debug

server:
  port: 8083
"@
    
    $totalCount++
    if (Create-NacosConfig -Url $NacosUrl -Token $token -DataId "menu-service.yml" -Content $menuConfig) {
        $successCount++
    }
    
    # 5. System Service 配置
    $systemConfig = @"
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
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://mysql:3306/permission_system?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false
    username: root
    password: 123456
  redis:
    host: redis
    port: 6379
    password:
    database: 4
    timeout: 5000ms
    lettuce:
      pool:
        max-active: 20
        max-wait: 3000ms
        max-idle: 10
        min-idle: 5

mybatis-plus:
  configuration:
    map-underscore-to-camel-case: true
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  global-config:
    db-config:
      id-type: auto
      logic-delete-field: deleted
      logic-delete-value: 1
      logic-not-delete-value: 0

logging:
  level:
    com.permission: debug

server:
  port: 8084
"@
    
    $totalCount++
    if (Create-NacosConfig -Url $NacosUrl -Token $token -DataId "system-service.yml" -Content $systemConfig) {
        $successCount++
    }
    
    # 输出结果
    Write-Host "" 
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host "✅ Nacos配置完成!" -ForegroundColor Green
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host "📊 配置结果: $successCount/$totalCount 成功" -ForegroundColor $(if ($successCount -eq $totalCount) { "Green" } else { "Yellow" })
    Write-Host "🌐 Nacos控制台: $NacosUrl/nacos" -ForegroundColor Cyan
    Write-Host "🔑 登录账号: $Username / $Password" -ForegroundColor Cyan
    Write-Host ""
    
    if ($successCount -eq $totalCount) {
        Write-Host "🎉 所有配置创建成功，可以启动微服务了！" -ForegroundColor Green
        exit 0
    } else {
        Write-Host "⚠️ 部分配置创建失败，请检查Nacos控制台" -ForegroundColor Yellow
        exit 1
    }
}
catch {
    Write-Host "" 
    Write-Host "========================================" -ForegroundColor Red
    Write-Host "❌ Nacos配置失败!" -ForegroundColor Red
    Write-Host "========================================" -ForegroundColor Red
    Write-Host "错误信息: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "" 
    Write-Host "🔧 故障排除建议:" -ForegroundColor Yellow
    Write-Host "  1. 确认Nacos服务已完全启动" -ForegroundColor Gray
    Write-Host "  2. 检查网络连接和端口访问" -ForegroundColor Gray
    Write-Host "  3. 验证Nacos登录凭据" -ForegroundColor Gray
    Write-Host "  4. 查看Nacos服务日志: docker-compose logs nacos" -ForegroundColor Gray
    Write-Host ""
    exit 1
}