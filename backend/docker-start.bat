@echo off
chcp 65001 >nul
echo ========================================
echo    权限管理系统 Docker 环境启动脚本
echo ========================================
echo.

:: 检查Docker是否运行
echo [1/5] 检查Docker环境...
docker version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Docker未运行或未安装，请先启动Docker Desktop
    pause
    exit /b 1
)
echo ✅ Docker环境正常

:: 检查docker-compose.yml文件
echo [2/5] 检查配置文件...
if not exist "docker-compose.yml" (
    echo ❌ docker-compose.yml文件不存在
    pause
    exit /b 1
)
echo ✅ 配置文件存在

:: 停止并删除已存在的容器
echo [3/5] 清理旧容器...
docker-compose down -v
echo ✅ 旧容器清理完成

:: 拉取最新镜像
echo [4/5] 拉取Docker镜像...
docker-compose pull
echo ✅ 镜像拉取完成

:: 启动服务
echo [5/6] 启动服务...
docker-compose up -d

if %errorlevel% equ 0 (
    echo.
    echo ========================================
    echo ✅ Docker服务启动成功！
    echo ========================================
    echo.
    echo 📋 服务信息：
    echo   MySQL:    localhost:3306
    echo   Redis:    localhost:6379
    echo   Nacos:    http://localhost:8848/nacos
    echo.
    echo 🔑 默认账号信息：
    echo   MySQL:    root/123456
    echo   Nacos:    nacos/nacos
    echo.
    
    :: 自动配置Nacos
    echo [6/6] 自动配置Nacos...
    echo 正在等待Nacos服务完全启动并配置微服务配置文件...
    echo.
    
    :: 检查PowerShell配置脚本是否存在
    if exist "auto-config-nacos.ps1" (
        :: 使用PowerShell执行配置脚本
        powershell -ExecutionPolicy Bypass -File "auto-config-nacos.ps1"
        
        if %errorlevel% equ 0 (
            echo.
            echo ========================================
            echo 🎉 环境启动和配置完成！
            echo ========================================
            echo.
            echo 📊 查看服务状态：docker-compose ps
            echo 📝 查看服务日志：docker-compose logs -f [服务名]
            echo 🌐 访问Nacos控制台：http://localhost:8848/nacos
            echo 🛑 停止所有服务：docker-stop.bat
            echo.
        ) else (
            echo.
            echo ⚠️ Nacos配置失败，但Docker服务已启动
            echo 您可以手动运行以下命令配置Nacos：
            echo   powershell -ExecutionPolicy Bypass -File "auto-config-nacos.ps1"
            echo.
        )
    ) else (
        echo ⚠️ 未找到auto-config-nacos.ps1配置脚本
        echo 请手动配置Nacos或运行fix-nacos-config.ps1
        echo.
        echo 📊 查看服务状态：docker-compose ps
        echo 📝 查看服务日志：docker-compose logs -f [服务名]
        echo 🌐 访问Nacos控制台：http://localhost:8848/nacos
        echo 🛑 停止所有服务：docker-stop.bat
        echo.
    )
) else (
    echo ❌ 服务启动失败，请检查错误信息
    docker-compose logs
)

echo 按任意键查看服务状态...
pause >nul
docker-compose ps

echo.
echo 按任意键退出...
pause >nul