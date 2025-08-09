@echo off
chcp 65001 >nul
echo ========================================
echo    权限管理系统 Docker 环境重置脚本
echo ========================================
echo.
echo ⚠️  警告：此操作将删除所有数据！
echo.
set /p confirm=确认要重置环境吗？(输入 yes 继续): 
if /i not "%confirm%"=="yes" (
    echo 操作已取消
    pause
    exit /b 0
)

echo.
echo [1/4] 停止所有服务...
docker-compose down

echo [2/4] 删除所有数据卷...
docker-compose down -v
docker volume prune -f

echo [3/4] 删除相关镜像（可选）...
set /p remove_images=是否删除Docker镜像以节省空间？(y/n): 
if /i "%remove_images%"=="y" (
    docker rmi mysql:8.0 redis:7.0-alpine nacos/nacos-server:v2.2.3 nginx:1.24-alpine 2>nul
    echo ✅ 镜像已删除
) else (
    echo ⏭️  保留镜像
)

echo [4/4] 清理未使用的资源...
docker system prune -f

echo.
echo ========================================
echo ✅ 环境重置完成！
echo ========================================
echo.
echo 💡 下次启动时将重新创建所有服务和数据
echo 🚀 运行 docker-start.bat 重新启动环境
echo.
echo 按任意键退出...
pause >nul