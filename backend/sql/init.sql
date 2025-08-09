-- 权限管理系统数据库初始化脚本
-- 创建数据库
CREATE DATABASE IF NOT EXISTS `permission_system` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `permission_system`;

-- 用户表
CREATE TABLE `sys_user` (
  `id` bigint NOT NULL COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `gender` tinyint DEFAULT '0' COMMENT '性别：0-未知，1-男，2-女',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `department_id` bigint DEFAULT NULL COMMENT '部门ID',
  `status` tinyint DEFAULT '1' COMMENT '状态：0-禁用，1-启用',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(50) DEFAULT NULL COMMENT '最后登录IP',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `created_by` bigint DEFAULT NULL COMMENT '创建人',
  `updated_by` bigint DEFAULT NULL COMMENT '更新人',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标记：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  KEY `idx_department_id` (`department_id`),
  KEY `idx_status` (`status`),
  KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 角色表
CREATE TABLE `sys_role` (
  `id` bigint NOT NULL COMMENT '角色ID',
  `role_name` varchar(50) NOT NULL COMMENT '角色名称',
  `role_code` varchar(50) NOT NULL COMMENT '角色编码',
  `description` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `status` tinyint DEFAULT '1' COMMENT '状态：0-禁用，1-启用',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `created_by` bigint DEFAULT NULL COMMENT '创建人',
  `updated_by` bigint DEFAULT NULL COMMENT '更新人',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标记：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_code` (`role_code`),
  KEY `idx_status` (`status`),
  KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';

-- 权限表
CREATE TABLE `sys_permission` (
  `id` bigint NOT NULL COMMENT '权限ID',
  `permission_name` varchar(50) NOT NULL COMMENT '权限名称',
  `permission_code` varchar(100) NOT NULL COMMENT '权限编码',
  `permission_type` tinyint NOT NULL COMMENT '权限类型：1-菜单，2-按钮，3-接口',
  `parent_id` bigint DEFAULT '0' COMMENT '父权限ID',
  `path` varchar(255) DEFAULT NULL COMMENT '路径',
  `component` varchar(255) DEFAULT NULL COMMENT '组件',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `sort_order` int DEFAULT '0' COMMENT '排序',
  `status` tinyint DEFAULT '1' COMMENT '状态：0-禁用，1-启用',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `created_by` bigint DEFAULT NULL COMMENT '创建人',
  `updated_by` bigint DEFAULT NULL COMMENT '更新人',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标记：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_permission_code` (`permission_code`),
  KEY `idx_parent_id` (`parent_id`),
  KEY `idx_permission_type` (`permission_type`),
  KEY `idx_status` (`status`),
  KEY `idx_sort_order` (`sort_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='权限表';

-- 用户角色关联表
CREATE TABLE `sys_user_role` (
  `id` bigint NOT NULL COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` bigint DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_role` (`user_id`,`role_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色关联表';

-- 角色权限关联表
CREATE TABLE `sys_role_permission` (
  `id` bigint NOT NULL COMMENT 'ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `permission_id` bigint NOT NULL COMMENT '权限ID',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` bigint DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_permission` (`role_id`,`permission_id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_permission_id` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色权限关联表';

-- 部门表
CREATE TABLE `sys_department` (
  `id` bigint NOT NULL COMMENT '部门ID',
  `department_name` varchar(50) NOT NULL COMMENT '部门名称',
  `department_code` varchar(50) NOT NULL COMMENT '部门编码',
  `parent_id` bigint DEFAULT '0' COMMENT '父部门ID',
  `leader_id` bigint DEFAULT NULL COMMENT '负责人ID',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `sort_order` int DEFAULT '0' COMMENT '排序',
  `status` tinyint DEFAULT '1' COMMENT '状态：0-禁用，1-启用',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `created_by` bigint DEFAULT NULL COMMENT '创建人',
  `updated_by` bigint DEFAULT NULL COMMENT '更新人',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标记：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_department_code` (`department_code`),
  KEY `idx_parent_id` (`parent_id`),
  KEY `idx_leader_id` (`leader_id`),
  KEY `idx_status` (`status`),
  KEY `idx_sort_order` (`sort_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='部门表';

-- 菜单表
CREATE TABLE `sys_menu` (
  `id` bigint NOT NULL COMMENT '菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `menu_code` varchar(100) NOT NULL COMMENT '菜单编码',
  `parent_id` bigint DEFAULT '0' COMMENT '父菜单ID',
  `path` varchar(255) DEFAULT NULL COMMENT '路由路径',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `icon` varchar(100) DEFAULT NULL COMMENT '菜单图标',
  `sort_order` int DEFAULT '0' COMMENT '排序',
  `menu_type` tinyint DEFAULT '1' COMMENT '菜单类型：1-目录，2-菜单，3-按钮',
  `visible` tinyint DEFAULT '1' COMMENT '是否显示：0-隐藏，1-显示',
  `status` tinyint DEFAULT '1' COMMENT '状态：0-禁用，1-启用',
  `permission_code` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `created_by` bigint DEFAULT NULL COMMENT '创建人',
  `updated_by` bigint DEFAULT NULL COMMENT '更新人',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标记：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_menu_code` (`menu_code`),
  KEY `idx_parent_id` (`parent_id`),
  KEY `idx_menu_type` (`menu_type`),
  KEY `idx_status` (`status`),
  KEY `idx_sort_order` (`sort_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜单表';

-- 系统配置表
CREATE TABLE `sys_config` (
  `id` bigint NOT NULL COMMENT '配置ID',
  `config_name` varchar(100) NOT NULL COMMENT '配置名称',
  `config_key` varchar(100) NOT NULL COMMENT '配置键名',
  `config_value` text COMMENT '配置值',
  `config_type` tinyint DEFAULT '0' COMMENT '配置类型：0-用户配置，1-系统配置',
  `status` tinyint DEFAULT '1' COMMENT '状态：0-禁用，1-启用',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `created_by` bigint DEFAULT NULL COMMENT '创建人',
  `updated_by` bigint DEFAULT NULL COMMENT '更新人',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标记：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_config_key` (`config_key`),
  KEY `idx_config_type` (`config_type`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统配置表';

-- 字典类型表
CREATE TABLE `sys_dict_type` (
  `id` bigint NOT NULL COMMENT '字典类型ID',
  `dict_name` varchar(100) NOT NULL COMMENT '字典名称',
  `dict_type` varchar(100) NOT NULL COMMENT '字典类型',
  `status` tinyint DEFAULT '1' COMMENT '状态：0-禁用，1-启用',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `created_by` bigint DEFAULT NULL COMMENT '创建人',
  `updated_by` bigint DEFAULT NULL COMMENT '更新人',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标记：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_dict_type` (`dict_type`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='字典类型表';

-- 字典数据表
CREATE TABLE `sys_dict_data` (
  `id` bigint NOT NULL COMMENT '字典数据ID',
  `dict_sort` int DEFAULT '0' COMMENT '字典排序',
  `dict_label` varchar(100) NOT NULL COMMENT '字典标签',
  `dict_value` varchar(100) NOT NULL COMMENT '字典键值',
  `dict_type` varchar(100) NOT NULL COMMENT '字典类型',
  `css_class` varchar(100) DEFAULT NULL COMMENT '样式属性',
  `list_class` varchar(100) DEFAULT NULL COMMENT '表格回显样式',
  `is_default` tinyint DEFAULT '0' COMMENT '是否默认：0-否，1-是',
  `status` tinyint DEFAULT '1' COMMENT '状态：0-禁用，1-启用',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `created_by` bigint DEFAULT NULL COMMENT '创建人',
  `updated_by` bigint DEFAULT NULL COMMENT '更新人',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标记：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_dict_type` (`dict_type`),
  KEY `idx_dict_value` (`dict_value`),
  KEY `idx_status` (`status`),
  KEY `idx_dict_sort` (`dict_sort`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='字典数据表';

-- 插入初始数据

-- 插入超级管理员用户
INSERT INTO `sys_user` (`id`, `username`, `password`, `nickname`, `email`, `status`, `created_at`, `updated_at`) VALUES
(1, 'admin', '$2a$10$7JB720yubVSOfvVWbazBuOWShWzhxyiV5c/xCj/QdaOJT8B.mbaWO', '超级管理员', 'admin@example.com', 1, NOW(), NOW());

-- 插入基础角色
INSERT INTO `sys_role` (`id`, `role_name`, `role_code`, `description`, `status`, `created_at`, `updated_at`) VALUES
(1, '超级管理员', 'SUPER_ADMIN', '系统超级管理员，拥有所有权限', 1, NOW(), NOW()),
(2, '系统管理员', 'SYSTEM_ADMIN', '系统管理员，拥有系统管理权限', 1, NOW(), NOW()),
(3, '部门管理员', 'DEPT_ADMIN', '部门管理员，拥有部门管理权限', 1, NOW(), NOW()),
(4, '普通用户', 'USER', '普通用户，拥有基础权限', 1, NOW(), NOW());

-- 插入基础权限
INSERT INTO `sys_permission` (`id`, `permission_name`, `permission_code`, `permission_type`, `parent_id`, `path`, `component`, `icon`, `sort_order`, `status`, `created_at`, `updated_at`) VALUES
(1, '系统管理', 'system', 1, 0, '/system', NULL, 'system', 1, 1, NOW(), NOW()),
(2, '用户管理', 'system:user', 1, 1, '/system/user', 'system/user/index', 'user', 1, 1, NOW(), NOW()),
(3, '角色管理', 'system:role', 1, 1, '/system/role', 'system/role/index', 'role', 2, 1, NOW(), NOW()),
(4, '权限管理', 'system:permission', 1, 1, '/system/permission', 'system/permission/index', 'permission', 3, 1, NOW(), NOW()),
(5, '菜单管理', 'system:menu', 1, 1, '/system/menu', 'system/menu/index', 'menu', 4, 1, NOW(), NOW()),
(6, '部门管理', 'system:dept', 1, 1, '/system/dept', 'system/dept/index', 'dept', 5, 1, NOW(), NOW()),
(7, '系统配置', 'system:config', 1, 1, '/system/config', 'system/config/index', 'config', 6, 1, NOW(), NOW()),
(8, '字典管理', 'system:dict', 1, 1, '/system/dict', 'system/dict/index', 'dict', 7, 1, NOW(), NOW());

-- 插入用户角色关联
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`, `created_at`) VALUES
(1, 1, 1, NOW());

-- 插入角色权限关联（超级管理员拥有所有权限）
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`, `created_at`) VALUES
(1, 1, 1, NOW()),
(2, 1, 2, NOW()),
(3, 1, 3, NOW()),
(4, 1, 4, NOW()),
(5, 1, 5, NOW()),
(6, 1, 6, NOW()),
(7, 1, 7, NOW()),
(8, 1, 8, NOW());

-- 插入基础部门
INSERT INTO `sys_department` (`id`, `department_name`, `department_code`, `parent_id`, `sort_order`, `status`, `created_at`, `updated_at`) VALUES
(1, '总公司', 'ROOT', 0, 1, 1, NOW(), NOW()),
(2, '技术部', 'TECH', 1, 1, 1, NOW(), NOW()),
(3, '产品部', 'PRODUCT', 1, 2, 1, NOW(), NOW()),
(4, '运营部', 'OPERATION', 1, 3, 1, NOW(), NOW());

-- 插入基础菜单
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_code`, `parent_id`, `path`, `component`, `icon`, `sort_order`, `menu_type`, `visible`, `status`, `permission_code`, `created_at`, `updated_at`) VALUES
(1, '系统管理', 'system', 0, '/system', NULL, 'system', 1, 1, 1, 1, 'system', NOW(), NOW()),
(2, '用户管理', 'system:user', 1, '/system/user', 'system/user/index', 'user', 1, 2, 1, 1, 'system:user:list', NOW(), NOW()),
(3, '角色管理', 'system:role', 1, '/system/role', 'system/role/index', 'role', 2, 2, 1, 1, 'system:role:list', NOW(), NOW()),
(4, '权限管理', 'system:permission', 1, '/system/permission', 'system/permission/index', 'permission', 3, 2, 1, 1, 'system:permission:list', NOW(), NOW()),
(5, '菜单管理', 'system:menu', 1, '/system/menu', 'system/menu/index', 'menu', 4, 2, 1, 1, 'system:menu:list', NOW(), NOW()),
(6, '部门管理', 'system:dept', 1, '/system/dept', 'system/dept/index', 'dept', 5, 2, 1, 1, 'system:dept:list', NOW(), NOW()),
(7, '系统配置', 'system:config', 1, '/system/config', 'system/config/index', 'config', 6, 2, 1, 1, 'system:config:list', NOW(), NOW()),
(8, '字典管理', 'system:dict', 1, '/system/dict', 'system/dict/index', 'dict', 7, 2, 1, 1, 'system:dict:list', NOW(), NOW());

-- 插入系统配置
INSERT INTO `sys_config` (`id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `remark`, `created_at`, `updated_at`) VALUES
(1, '系统名称', 'system.name', '权限管理系统', 1, 1, '系统名称配置', NOW(), NOW()),
(2, '系统版本', 'system.version', '1.0.0', 1, 1, '系统版本配置', NOW(), NOW()),
(3, '用户默认密码', 'user.default.password', '123456', 1, 1, '用户默认密码', NOW(), NOW()),
(4, '密码最小长度', 'password.min.length', '6', 1, 1, '密码最小长度限制', NOW(), NOW()),
(5, '登录失败锁定次数', 'login.fail.count', '5', 1, 1, '登录失败锁定次数', NOW(), NOW());

-- 插入字典类型
INSERT INTO `sys_dict_type` (`id`, `dict_name`, `dict_type`, `status`, `remark`, `created_at`, `updated_at`) VALUES
(1, '用户性别', 'sys_user_sex', 1, '用户性别列表', NOW(), NOW()),
(2, '菜单状态', 'sys_show_hide', 1, '菜单状态列表', NOW(), NOW()),
(3, '系统开关', 'sys_normal_disable', 1, '系统开关列表', NOW(), NOW()),
(4, '任务状态', 'sys_job_status', 1, '任务状态列表', NOW(), NOW()),
(5, '任务分组', 'sys_job_group', 1, '任务分组列表', NOW(), NOW()),
(6, '系统是否', 'sys_yes_no', 1, '系统是否列表', NOW(), NOW());

-- 插入字典数据
INSERT INTO `sys_dict_data` (`id`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `remark`, `created_at`, `updated_at`) VALUES
(1, 1, '男', '1', 'sys_user_sex', '', '', 1, 1, '性别男', NOW(), NOW()),
(2, 2, '女', '2', 'sys_user_sex', '', '', 0, 1, '性别女', NOW(), NOW()),
(3, 3, '未知', '0', 'sys_user_sex', '', '', 0, 1, '性别未知', NOW(), NOW()),
(4, 1, '显示', '1', 'sys_show_hide', '', 'primary', 1, 1, '显示菜单', NOW(), NOW()),
(5, 2, '隐藏', '0', 'sys_show_hide', '', 'danger', 0, 1, '隐藏菜单', NOW(), NOW()),
(6, 1, '正常', '1', 'sys_normal_disable', '', 'primary', 1, 1, '正常状态', NOW(), NOW()),
(7, 2, '停用', '0', 'sys_normal_disable', '', 'danger', 0, 1, '停用状态', NOW(), NOW()),
(8, 1, '正常', '1', 'sys_job_status', '', 'primary', 1, 1, '正常状态', NOW(), NOW()),
(9, 2, '暂停', '0', 'sys_job_status', '', 'danger', 0, 1, '停用状态', NOW(), NOW()),
(10, 1, '默认', 'DEFAULT', 'sys_job_group', '', '', 1, 1, '默认分组', NOW(), NOW()),
(11, 2, '系统', 'SYSTEM', 'sys_job_group', '', '', 0, 1, '系统分组', NOW(), NOW()),
(12, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 1, 1, '系统默认是', NOW(), NOW()),
(13, 2, '否', 'N', 'sys_yes_no', '', 'danger', 0, 1, '系统默认否', NOW(), NOW());

COMMIT;