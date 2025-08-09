@echo off
chcp 65001 >nul
echo ========================================
echo    权限管理系统 Docker 环境停止脚本
echo ========================================
echo.

:: 检查Docker是否运行
echo [1/3] 检查Docker环境...
docker version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Docker未运行或未安装
    pause
    exit /b 1
)
echo ✅ Docker环境正常

:: 显示当前运行的服务
echo [2/3] 当前运行的服务：
docker-compose ps
echo.

:: 停止服务
echo [3/3] 停止服务...
docker-compose down

if %errorlevel% equ 0 (
    echo.
    echo ========================================
    echo ✅ 所有服务已停止！
    echo ========================================
    echo.
    echo 💡 提示：
    echo   - 数据已保存在Docker卷中，下次启动时数据不会丢失
    echo   - 如需完全清理（包括数据），请运行：docker-compose down -v
    echo   - 重新启动服务：docker-start.bat
    echo.
) else (
    echo ❌ 停止服务时出现错误
)

echo 按任意键退出...
pause >nul