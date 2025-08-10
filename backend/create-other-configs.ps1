# 创建其他微服务配置

Write-Host "开始创建其他微服务配置..." -ForegroundColor Cyan

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
    url: jdbc:mysql://localhost:3306/permission_system?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false
    username: root
    password: 123456
  redis:
    host: localhost
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
    url: jdbc:mysql://localhost:3306/permission_system?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false
    username: root
    password: 123456
  redis:
    host: localhost
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
    url: jdbc:mysql://localhost:3306/permission_system?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false
    username: root
    password: 123456
  redis:
    host: localhost
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
    url: jdbc:mysql://localhost:3306/permission_system?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false
    username: root
    password: 123456
  redis:
    host: localhost
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

# 创建配置
Create-Config "user-service.yml" $userConfig
Create-Config "auth-service.yml" $authConfig
Create-Config "menu-service.yml" $menuConfig
Create-Config "system-service.yml" $systemConfig

Write-Host "所有微服务配置创建完成!" -ForegroundColor Green
Write-Host "请访问 http://localhost:8848/nacos 查看配置" -ForegroundColor Cyan