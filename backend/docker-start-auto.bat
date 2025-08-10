@echo off
chcp 65001 >nul
echo ========================================
echo    权限管理系统 一键启动脚本
echo ========================================
echo.

:: 检查Docker是否运行
echo [1/3] 检查Docker环境...
docker version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Docker未运行或未安装，请先启动Docker Desktop
    pause
    exit /b 1
)
echo ✅ Docker环境正常

:: 启动Docker服务
echo [2/3] 启动Docker服务...
docker-compose up -d

if %errorlevel% neq 0 (
    echo ❌ Docker服务启动失败
    pause
    exit /b 1
)

echo ✅ Docker服务启动成功

:: 自动配置Nacos
echo [3/3] 自动配置Nacos...
echo 正在等待Nacos服务就绪并配置微服务...
echo.

if exist "auto-config-nacos.ps1" (
    powershell -ExecutionPolicy Bypass -File "auto-config-nacos.ps1"
    
    if %errorlevel% equ 0 (
        echo.
        echo ========================================
        echo 🎉 一键启动完成！
        echo ========================================
        echo.
        echo 🌐 Nacos控制台: http://localhost:8848/nacos
        echo 🔑 登录账号: nacos/nacos
        echo.
        echo 现在可以启动您的微服务应用了！
        echo.
    ) else (
        echo ⚠️ Nacos配置失败，请手动配置
    )
) else (
    echo ❌ 配置脚本不存在，请检查auto-config-nacos.ps1文件
)

echo 按任意键退出...
pause >nul