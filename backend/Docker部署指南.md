# 权限管理系统 Docker 部署指南

## 📋 概述

本指南将帮助您使用 Docker 快速部署权限管理系统的基础服务环境，包括 MySQL、Redis、Nacos 等核心组件。

## 🛠️ 环境要求

### 必需软件
- **Docker Desktop**: 版本 4.0 或以上
- **操作系统**: Windows 10/11, macOS, Linux
- **内存**: 至少 4GB 可用内存
- **磁盘空间**: 至少 2GB 可用空间

### 端口要求
确保以下端口未被占用：
- `3306` - MySQL 数据库
- `6379` - Redis 缓存
- `8848` - Nacos 注册中心
- `9848` - Nacos gRPC 端口
- `80` - Nginx 反向代理（可选）

## 🚀 快速开始

### 1. 启动服务

**🚀 推荐方式 - 一键启动（自动配置Nacos）：**
```bash
# Windows 用户 - 一键启动并自动配置
docker-start-auto.bat

# 或使用增强版启动脚本
docker-start.bat
```

**📋 传统方式：**
```bash
# Windows 用户
docker-start.bat

# Linux/macOS 用户
chmod +x docker-start.sh
./docker-start.sh

# 或直接使用 docker-compose
docker-compose up -d
```

**🔧 手动配置Nacos（如需要）：**
```bash
# 如果自动配置失败，可手动运行
powershell -ExecutionPolicy Bypass -File "auto-config-nacos.ps1"
```

### 2. 验证服务状态

```bash
# 查看所有服务状态
docker-compose ps

# 查看服务日志
docker-compose logs -f

# 查看特定服务日志
docker-compose logs -f mysql
docker-compose logs -f redis
docker-compose logs -f nacos
```

### 3. 停止服务

**Windows 用户：**
```bash
docker-stop.bat
```

**Linux/macOS 用户：**
```bash
docker-compose down
```

## 📊 服务详情

### MySQL 8.0 数据库

| 配置项 | 值 |
|--------|----|
| **端口** | 3306 |
| **用户名** | root |
| **密码** | 123456 |
| **数据库** | permission_system, nacos_config |
| **字符集** | utf8mb4 |
| **时区** | Asia/Shanghai |

**连接示例：**
```bash
# 使用 MySQL 客户端连接
mysql -h localhost -P 3306 -u root -p123456

# 使用 Docker 容器连接
docker exec -it permission-mysql mysql -u root -p123456
```

**数据持久化：**
- 数据卷：`mysql_data`
- 配置文件：`./docker/mysql/conf.d/my.cnf`
- 初始化脚本：`./sql/init.sql`, `./sql/nacos-init.sql`

### Redis 7.0 缓存

| 配置项 | 值 |
|--------|----|
| **端口** | 6379 |
| **密码** | 无（可在配置文件中设置） |
| **最大内存** | 256MB |
| **持久化** | AOF + RDB |

**连接示例：**
```bash
# 使用 Redis 客户端连接
redis-cli -h localhost -p 6379

# 使用 Docker 容器连接
docker exec -it permission-redis redis-cli
```

**数据持久化：**
- 数据卷：`redis_data`
- 配置文件：`./docker/redis/redis.conf`

### Nacos 2.2 注册中心

| 配置项 | 值 |
|--------|----|
| **Web端口** | 8848 |
| **gRPC端口** | 9848 |
| **用户名** | nacos |
| **密码** | nacos |
| **控制台** | http://localhost:8848/nacos |

**访问控制台：**
1. 打开浏览器访问：http://localhost:8848/nacos
2. 使用账号：`nacos` / `nacos` 登录

**数据持久化：**
- 数据卷：`nacos_data`, `nacos_logs`
- 数据库：使用 MySQL 存储配置信息

## 🤖 自动配置功能

### Nacos 自动配置

系统提供了自动配置Nacos的功能，在Docker服务启动后会自动：

1. **等待Nacos服务就绪** - 智能检测Nacos是否完全启动
2. **清理旧配置** - 删除可能存在的旧配置文件
3. **创建微服务配置** - 自动创建所有微服务的配置文件
4. **验证配置结果** - 确保所有配置都成功创建

**自动创建的配置文件：**
- `gateway-service.yml` - 网关服务配置
- `user-service.yml` - 用户服务配置
- `auth-service.yml` - 认证服务配置
- `menu-service.yml` - 菜单服务配置
- `system-service.yml` - 系统服务配置

**配置特点：**
- 🔗 **服务发现**: 自动配置Nacos服务注册与发现
- 🗄️ **数据库连接**: 使用Docker内部网络连接MySQL
- 📦 **Redis缓存**: 为每个服务分配独立的Redis数据库
- 🌐 **网关路由**: 自动配置API网关路由规则
- 🔒 **CORS支持**: 配置跨域访问支持

### 配置脚本说明

| 脚本文件 | 功能描述 |
|----------|----------|
| `auto-config-nacos.ps1` | 主配置脚本，包含完整的自动配置逻辑 |
| `docker-start.bat` | 增强版启动脚本，包含自动配置功能 |
| `docker-start-auto.bat` | 简化版一键启动脚本 |
| `fix-nacos-config.ps1` | 传统配置脚本（仅网关配置） |
| `create-other-configs.ps1` | 传统配置脚本（其他服务配置） |

### 故障排除

**如果自动配置失败：**
1. 检查Nacos服务是否正常启动：`docker-compose logs nacos`
2. 手动运行配置脚本：`powershell -ExecutionPolicy Bypass -File "auto-config-nacos.ps1"`
3. 访问Nacos控制台检查配置：http://localhost:8848/nacos

**常见问题：**
- **Nacos未就绪**: 脚本会自动等待最多5分钟，如果仍未就绪请检查Docker资源
- **网络连接问题**: 确保Docker网络正常，重启Docker Desktop
- **权限问题**: 确保PowerShell执行策略允许运行脚本

## 🔧 配置说明

### 网络配置

所有服务运行在自定义网络 `permission-network` 中：
- 网络类型：bridge
- 子网：172.20.0.0/16
- 服务间可通过容器名互相访问

### 数据卷管理

```bash
# 查看所有数据卷
docker volume ls

# 查看特定数据卷详情
docker volume inspect backend_mysql_data

# 备份数据卷
docker run --rm -v backend_mysql_data:/data -v $(pwd):/backup alpine tar czf /backup/mysql_backup.tar.gz -C /data .

# 恢复数据卷
docker run --rm -v backend_mysql_data:/data -v $(pwd):/backup alpine tar xzf /backup/mysql_backup.tar.gz -C /data
```

## 🔍 故障排除

### 常见问题

**1. 端口被占用**
```bash
# Windows 查看端口占用
netstat -ano | findstr :3306

# Linux/macOS 查看端口占用
lsof -i :3306
```

**2. 服务启动失败**
```bash
# 查看详细错误日志
docker-compose logs [服务名]

# 重新构建并启动
docker-compose up --build -d
```

**3. 数据库连接失败**
- 检查 MySQL 容器是否正常运行
- 确认端口映射是否正确
- 验证用户名密码是否正确

**4. Nacos 无法访问**
- 等待 2-3 分钟让 Nacos 完全启动
- 检查 MySQL 数据库是否正常
- 确认防火墙设置

### 健康检查

```bash
# 检查所有服务健康状态
docker-compose ps

# 手动健康检查
curl -f http://localhost:8848/nacos/v1/console/health/readiness
redis-cli -h localhost ping
mysqladmin -h localhost -u root -p123456 ping
```

## 🔄 维护操作

### 更新服务

```bash
# 拉取最新镜像
docker-compose pull

# 重新创建容器
docker-compose up -d --force-recreate
```

### 清理环境

```bash
# 停止并删除容器（保留数据）
docker-compose down

# 完全清理（删除数据）
docker-compose down -v

# 清理未使用的资源
docker system prune -f
```

### 重置环境

**Windows 用户：**
```bash
docker-reset.bat
```

**Linux/macOS 用户：**
```bash
# 完全重置（谨慎操作）
docker-compose down -v
docker volume prune -f
docker system prune -f
```

## 📈 性能优化

### MySQL 优化

编辑 `./docker/mysql/conf.d/my.cnf`：
```ini
# 根据服务器内存调整
innodb_buffer_pool_size = 512M  # 可用内存的 70-80%
max_connections = 500           # 根据并发需求调整
```

### Redis 优化

编辑 `./docker/redis/redis.conf`：
```ini
# 根据服务器内存调整
maxmemory 512mb
maxmemory-policy allkeys-lru
```

### Nacos 优化

在 `docker-compose.yml` 中调整 JVM 参数：
```yaml
environment:
  JVM_XMS: 512m
  JVM_XMX: 512m
  JVM_XMN: 256m
```

## 🔐 安全配置

### 生产环境建议

1. **修改默认密码**
   - MySQL root 密码
   - Redis 密码（在配置文件中启用）
   - Nacos 管理员密码

2. **网络安全**
   - 使用防火墙限制端口访问
   - 配置 SSL/TLS 证书
   - 使用 VPN 或内网访问

3. **数据备份**
   - 定期备份数据库
   - 备份配置文件
   - 监控磁盘空间

## 📞 技术支持

如果遇到问题，请：

1. 查看本文档的故障排除部分
2. 检查 Docker 和 docker-compose 版本
3. 查看服务日志获取详细错误信息
4. 确保系统资源充足

---

**