# 🚀 Docker 自动配置 Nacos 功能说明

## 📋 概述

本项目已升级支持 Docker 启动时自动配置 Nacos 配置文件，无需手动配置即可快速启动完整的微服务环境。

## 🆕 新增功能

### 1. 自动配置脚本
- **`auto-config-nacos.ps1`** - 主配置脚本，整合了所有配置逻辑
- **`docker-start-auto.bat`** - 一键启动脚本，最简化的启动方式
- **增强版 `docker-start.bat`** - 在原有启动脚本基础上添加自动配置功能

### 2. 智能等待机制
- 自动检测 Nacos 服务是否完全启动
- 最多等待 5 分钟（30次重试，每次间隔10秒）
- 实时显示等待状态和进度

### 3. 完整配置管理
- 自动清理旧配置，避免冲突
- 创建所有微服务的配置文件
- 验证配置创建结果
- 详细的成功/失败反馈

## 🎯 使用方式

### 方式一：一键启动（推荐）
```bash
# 双击运行或命令行执行
docker-start-auto.bat
```

### 方式二：增强版启动
```bash
# 使用增强版启动脚本
docker-start.bat
```

### 方式三：手动配置
```bash
# 先启动 Docker 服务
docker-compose up -d

# 然后手动运行配置脚本
powershell -ExecutionPolicy Bypass -File "auto-config-nacos.ps1"
```

## 📊 配置内容

### 自动创建的配置文件

| 配置文件 | 服务 | 端口 | Redis DB | 描述 |
|----------|------|------|----------|------|
| `gateway-service.yml` | 网关服务 | 8080 | 0 | API网关，路由配置 |
| `user-service.yml` | 用户服务 | 8081 | 1 | 用户管理 |
| `auth-service.yml` | 认证服务 | 8082 | 2 | 身份认证 |
| `menu-service.yml` | 菜单服务 | 8083 | 3 | 菜单权限 |
| `system-service.yml` | 系统服务 | 8084 | 4 | 系统管理 |

### 配置特点

✅ **服务发现**: 所有服务自动注册到 Nacos  
✅ **数据库连接**: 使用 Docker 内部网络 `mysql:3306`  
✅ **Redis 缓存**: 每个服务使用独立的 Redis 数据库  
✅ **网关路由**: 自动配置 `/api/{service}/**` 路由规则  
✅ **CORS 支持**: 配置跨域访问，支持前端开发  
✅ **日志配置**: 开启 debug 日志，便于开发调试  

## 🔧 故障排除

### 常见问题

**1. Nacos 配置失败**
```bash
# 检查 Nacos 服务状态
docker-compose logs nacos

# 手动重新配置
powershell -ExecutionPolicy Bypass -File "auto-config-nacos.ps1"
```

**2. PowerShell 执行策略问题**
```bash
# 临时允许脚本执行
Set-ExecutionPolicy -ExecutionPolicy Bypass -Scope Process

# 然后运行配置脚本
.\auto-config-nacos.ps1
```

**3. Docker 网络问题**
```bash
# 重启 Docker 服务
docker-compose down
docker-compose up -d
```

**4. 端口占用**
```bash
# 检查端口占用
netstat -ano | findstr :8848
netstat -ano | findstr :3306
netstat -ano | findstr :6379
```

### 验证配置

1. **访问 Nacos 控制台**: http://localhost:8848/nacos
2. **登录账号**: `nacos` / `nacos`
3. **检查配置列表**: 应该看到 5 个 `.yml` 配置文件
4. **查看服务列表**: 启动微服务后应该能看到服务注册信息

## 📝 日志说明

### 配置脚本日志
- ✅ 绿色：成功操作
- ⚠️ 黄色：警告信息
- ❌ 红色：错误信息
- ℹ️ 蓝色：信息提示
- ⏳ 灰色：等待状态

### Docker 服务日志
```bash
# 查看所有服务状态
docker-compose ps

# 查看特定服务日志
docker-compose logs -f nacos
docker-compose logs -f mysql
docker-compose logs -f redis
```

## 🔄 升级说明

### 从旧版本升级

如果您之前使用的是手动配置方式：

1. 保留现有的 `docker-compose.yml` 文件
2. 使用新的启动脚本 `docker-start-auto.bat`
3. 旧的配置脚本 `fix-nacos-config.ps1` 和 `create-other-configs.ps1` 仍然可用

### 配置文件变更

主要变更：
- Redis 连接地址从 `localhost` 改为 `redis`（Docker 内部网络）
- MySQL 连接地址从 `localhost` 改为 `mysql`（Docker 内部网络）
- 为每个服务分配了独立的 Redis 数据库编号

## 🎉 总结

现在您可以通过以下简单步骤启动完整的微服务环境：

1. 确保 Docker Desktop 正在运行
2. 双击 `docker-start-auto.bat`
3. 等待自动配置完成
4. 启动您的微服务应用

整个过程完全自动化，无需手动配置 Nacos！

---

**如有问题，请查看 `Docker部署指南.md` 获取更详细的说明。**