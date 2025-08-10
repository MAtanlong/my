@echo off
chcp 65001 >nul
echo ========================================
echo    权限管理系统 - 微服务启动脚本
echo ========================================
echo.

:: 检查Java环境
echo [1/7] 检查Java环境...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Java未安装或未配置环境变量
    pause
    exit /b 1
)
echo ✅ Java环境正常

:: 检查Maven环境
echo [2/7] 检查Maven环境...
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Maven未安装或未配置环境变量
    pause
    exit /b 1
)
echo ✅ Maven环境正常

:: 检查基础服务状态
echo [3/7] 检查基础服务状态...
echo 检查Nacos服务...
curl -s http://localhost:8848/nacos/v1/console/health/readiness >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Nacos服务未启动，请先运行 docker-start-auto.bat
    pause
    exit /b 1
)
echo ✅ Nacos服务正常

echo 检查MySQL服务...
mysql -h localhost -P 3306 -u root -p123456 -e "SELECT 1" >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ MySQL服务未启动或连接失败
    pause
    exit /b 1
)
echo ✅ MySQL服务正常

echo 检查Redis服务...
redis-cli -h localhost -p 6379 ping >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Redis服务未启动或连接失败
    pause
    exit /b 1
)
echo ✅ Redis服务正常

:: 编译所有微服务
echo [4/7] 编译所有微服务...
echo 正在编译项目...
mvn clean compile -DskipTests
if %errorlevel% neq 0 (
    echo ❌ 项目编译失败
    pause
    exit /b 1
)
echo ✅ 项目编译成功

:: 启动微服务
echo [5/7] 启动微服务...
echo.

:: 启动Gateway Service
echo 🚀 启动Gateway Service (端口: 8080)...
start "Gateway Service" cmd /c "cd gateway-service && mvn spring-boot:run"
timeout /t 10 /nobreak >nul

:: 启动User Service
echo 🚀 启动User Service (端口: 8081)...
start "User Service" cmd /c "cd user-service && mvn spring-boot:run"
timeout /t 5 /nobreak >nul

:: 启动Auth Service
echo 🚀 启动Auth Service (端口: 8082)...
start "Auth Service" cmd /c "cd auth-service && mvn spring-boot:run"
timeout /t 5 /nobreak >nul

:: 启动System Service
echo 🚀 启动System Service (端口: 8083)...
start "System Service" cmd /c "cd system-service && mvn spring-boot:run"
timeout /t 5 /nobreak >nul

:: 启动Menu Service
echo 🚀 启动Menu Service (端口: 8084)...
start "Menu Service" cmd /c "cd menu-service && mvn spring-boot:run"
timeout /t 5 /nobreak >nul

echo.
echo [6/7] 等待服务启动完成...
echo 正在等待所有服务启动，这可能需要1-2分钟...
timeout /t 60 /nobreak >nul

:: 检查服务状态
echo [7/7] 检查服务状态...
echo.

set "services_ok=0"
set "total_services=5"

:: 检查Gateway Service
echo 检查Gateway Service (8080)...
curl -s http://localhost:8080/actuator/health >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ Gateway Service 运行正常
    set /a services_ok+=1
) else (
    echo ❌ Gateway Service 启动失败或未就绪
)

:: 检查User Service
echo 检查User Service (8081)...
curl -s http://localhost:8081/actuator/health >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ User Service 运行正常
    set /a services_ok+=1
) else (
    echo ❌ User Service 启动失败或未就绪
)

:: 检查Auth Service
echo 检查Auth Service (8082)...
curl -s http://localhost:8082/actuator/health >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ Auth Service 运行正常
    set /a services_ok+=1
) else (
    echo ❌ Auth Service 启动失败或未就绪
)

:: 检查System Service
echo 检查System Service (8083)...
curl -s http://localhost:8083/actuator/health >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ System Service 运行正常
    set /a services_ok+=1
) else (
    echo ❌ System Service 启动失败或未就绪
)

:: 检查Menu Service
echo 检查Menu Service (8084)...
curl -s http://localhost:8084/actuator/health >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ Menu Service 运行正常
    set /a services_ok+=1
) else (
    echo ❌ Menu Service 启动失败或未就绪
)

echo.
echo ========================================
if %services_ok% equ %total_services% (
    echo ✅ 所有微服务启动成功！(%services_ok%/%total_services%)
    echo ========================================
    echo.
    echo 🌐 服务访问地址：
    echo   Gateway:  http://localhost:8080
    echo   User:     http://localhost:8081
    echo