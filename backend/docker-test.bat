@echo off
chcp 65001 >nul
echo ========================================
echo    权限管理系统 Docker 环境测试脚本
echo ========================================
echo.

:: 检查Docker服务状态
echo [1/6] 检查Docker服务状态...
docker-compose ps
echo.

:: 测试MySQL连接
echo [2/6] 测试MySQL连接...
docker exec permission-mysql mysqladmin -u root -p123456 ping 2>nul
if %errorlevel% equ 0 (
    echo ✅ MySQL连接正常
) else (
    echo ❌ MySQL连接失败
)
echo.

:: 测试Redis连接
echo [3/6] 测试Redis连接...
docker exec permission-redis redis-cli ping 2>nul
if %errorlevel% equ 0 (
    echo ✅ Redis连接正常
) else (
    echo ❌ Redis连接失败
)
echo.

:: 测试Nacos健康检查
echo [4/6] 测试Nacos健康检查...
curl -s -f http://localhost:8848/nacos/v1/console/health/readiness >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ Nacos服务正常
) else (
    echo ❌ Nacos服务异常（可能还在启动中，请稍等片刻）
)
echo.

:: 检查数据库初始化
echo [5/6] 检查数据库初始化...
docker exec permission-mysql mysql -u root -p123456 -e "USE permission_system; SELECT COUNT(*) as user_count FROM sys_user;" 2>nul
if %errorlevel% equ 0 (
    echo ✅ 数据库初始化完成
) else (
    echo ❌ 数据库初始化失败
)
echo.

:: 检查端口占用
echo [6/6] 检查端口占用情况...
echo MySQL端口(3306):
netstat -an | findstr :3306 | findstr LISTENING >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ 端口3306正常监听
) else (
    echo ❌ 端口3306未监听
)

echo Redis端口(6379):
netstat -an | findstr :6379 | findstr LISTENING >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ 端口6379正常监听
) else (
    echo ❌ 端口6379未监听
)

echo Nacos端口(8848):
netstat -an | findstr :8848 | findstr LISTENING >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ 端口8848正常监听
) else (
    echo ❌ 端口8848未监听
)

echo.
echo ========================================
echo 🔍 详细信息
echo ========================================
echo 📊 服务访问地址：
echo   MySQL:    localhost:3306 (root/123456)
echo   Redis:    localhost:6379
echo   Nacos:    http://localhost:8848/nacos (nacos/nacos)
echo.
echo 📝 查看服务日志：
echo   docker-compose logs -f mysql
echo   docker-compose logs -f redis
echo   docker-compose logs -f nacos
echo.
echo 🔧 常用命令：
echo   重启服务: docker-compose restart [服务名]
echo   进入容器: docker exec -it [容器名] bash
echo   查看状态: docker-compose ps
echo.
echo 按任意键退出...
pause >nul