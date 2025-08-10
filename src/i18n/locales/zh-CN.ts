export default {
  // 通用
  common: {
    id: 'ID',
    confirm: '确定',
    cancel: '取消',
    save: '保存',
    delete: '删除',
    edit: '编辑',
    add: '新增',
    search: '搜索',
    reset: '重置',
    refresh: '刷新',
    export: '导出',
    import: '导入',
    view: '查看',
    copy: '复制',
    submit: '提交',
    back: '返回',
    close: '关闭',
    loading: '加载中...',
    noData: '暂无数据',
    operation: '操作',
    status: '状态',
    enabled: '启用',
    disabled: '禁用',
    createTime: '创建时间',
    updateTime: '更新时间',
    remark: '备注',
    sort: '排序',
    total: '共 {count} 条记录',
    batchDelete: '批量删除',
    batchDeleteConfirm: '批量删除确认',
    batchDeleteSuccess: '批量删除成功',
    normal: '正常',
    cancelled: '已取消',
    featureInDevelopment: '功能开发中',
    logout: '退出登录',
    allRightsReserved: '版权所有'
  },

  // 导航菜单
  menu: {
    dashboard: '首页仪表板',
    userManagement: '用户管理',
    userList: '用户列表',
    roleManagement: '角色管理',
    roleList: '角色列表',
    permissionManagement: '权限管理',
    permissionList: '权限列表',
    menuManagement: '菜单管理',
    menuList: '菜单列表',
    departmentManagement: '部门管理',
    departmentList: '部门列表',
    systemSettings: '系统设置',
    personalCenter: '个人中心'
  },

  // 登录页面
  login: {
    title: '权限管理系统',
    subtitle: '统一身份认证平台',
    usernameLogin: '用户名登录',
    accountLogin: '账号登录',
    phoneLogin: '手机登录',
    qrLogin: '扫码登录',
    wechatLogin: '微信登录',
    username: '用户名',
    password: '密码',
    phone: '手机号',
    captcha: '验证码',
    smsCode: '短信验证码',
    usernamePlaceholder: '请输入用户名',
    passwordPlaceholder: '请输入密码',
    phonePlaceholder: '请输入手机号',
    captchaPlaceholder: '请输入验证码',
    smsCodePlaceholder: '请输入短信验证码',
    rememberMe: '记住登录状态',
    forgotPassword: '忘记密码？',
    login: '登录',
    loginButton: '登录',
    loggingIn: '登录中...',
    getSmsCode: '获取验证码',
    sendSms: '发送验证码',
    resendSms: '重新发送({count}s)',
    otherLoginMethods: '其他登录方式',
    loginSuccess: '登录成功',
    usernameRequired: '请输入用户名',
    passwordRequired: '请输入密码',
    phoneRequired: '请输入手机号',
    captchaRequired: '请输入验证码',
    smsCodeRequired: '请输入短信验证码',
    phoneFormat: '请输入正确的手机号格式',
    refreshCaptcha: '点击刷新验证码',
    validation: {
      usernameRequired: '请输入用户名',
      usernameLength: '用户名长度在 3 到 20 个字符',
      passwordRequired: '请输入密码',
      passwordLength: '密码长度在 6 到 20 个字符',
      captchaRequired: '请输入验证码',
      captchaLength: '验证码长度为 4 位',
      phoneRequired: '请输入手机号',
      phoneFormat: '请输入正确的手机号格式',
      smsCodeRequired: '请输入短信验证码',
      smsCodeLength: '短信验证码长度为 6 位'
    },
    messages: {
      loginSuccess: '登录成功',
      captchaError: '验证码错误',
      enterPhoneFirst: '请先输入手机号',
      smsCodeSent: '短信验证码已发送',
      smsCodeSendFailed: '短信验证码发送失败',
      contactAdmin: '请联系管理员重置密码',
      qrLoginDeveloping: '二维码登录功能开发中',
      wechatLoginDeveloping: '微信登录功能开发中'
    }
  },

  // 角色管理
  role: {
    title: '角色管理',
    description: '管理系统角色信息和权限配置',
    add: '新增角色',
    edit: '编辑角色',
    view: '查看',
    copy: '复制角色',
    delete: '删除',
    export: '导出角色',
    configPermission: '配置权限',
    name: '角色名称',
    code: '角色编码',
    status: '状态',
    sort: '排序',
    remark: '备注',
    permissionCount: '权限数',
    userCount: '用户数',
    createTime: '创建时间',
    actions: '操作',
    enabled: '启用',
    disabled: '禁用',
    namePlaceholder: '请输入角色名称',
    codePlaceholder: '请输入角色编码',
    remarkPlaceholder: '请输入备注信息',
    roleInfo: '角色信息',
    permissionConfig: '权限配置',
    expandAll: '展开全部',
    collapseAll: '收起全部',
    selectAll: '全选',
    unselectAll: '取消全选',
    validation: {
      nameRequired: '请输入角色名称',
      nameLength: '角色名称长度在 2 到 50 个字符',
      codeRequired: '请输入角色编码',
      codeFormat: '角色编码只能包含大写字母和下划线'
    },
    messages: {
      loadSuccess: '角色列表加载成功',
      loadFailed: '加载角色列表失败',
      viewRole: '查看角色：{name}',
      cannotDeleteWithUsers: '该角色下还有用户，无法删除',
      deleteConfirm: '确定要删除角色 "{name}" 吗？',
      batchDeleteWithUsers: '选中的角色中有角色下还有用户，无法删除',
      batchDeleteConfirm: '确定要删除选中的 {count} 个角色吗？',
      statusChanged: '角色状态已{status}',
      statusChangeFailed: '状态更新失败',
      configPermission: '配置角色权限：{name}',
      saveSuccess: '角色保存成功',
      saveFailed: '角色保存失败',
      updateSuccess: '角色更新成功',
      createSuccess: '角色创建成功',
      permissionSaveSuccess: '权限配置成功',
      permissionSaveFailed: '权限配置失败',
      exportList: '导出角色列表',
      exportInDevelopment: '导出功能开发中'
    }
  },

  // 权限管理
  permission: {
    module: '模块',
    menu: '菜单',
    button: '按钮'
  },

  // 用户管理
  user: {
    title: '用户管理',
    description: '管理系统用户信息和权限设置',
    export: '导出用户',
    add: '新增用户',
    view: '查看',
    edit: '编辑',
    assignRole: '分配角色',
    username: '用户名',
    lastLoginTime: '最后登录时间',
    createTime: '创建时间',
    phonePlaceholder: '请输入手机号',
    passwordPlaceholder: '请输入密码',
    confirmPasswordPlaceholder: '请确认密码',
    department: '部门',
    userInfo: '用户信息：{realName}（{username}）',
    selectRoleTip: '请选择要分配的角色',
    form: {
      username: '用户名',
      realName: '真实姓名',
      phone: '手机号',
      email: '邮箱',
      password: '密码',
      confirmPassword: '确认密码',
      gender: '性别',
      status: '状态',
      department: '部门',
      remark: '备注',
      male: '男',
      female: '女',
      enabled: '启用',
      disabled: '禁用'
    },
    table: {
      avatar: '头像',
      username: '用户名',
      realName: '真实姓名',
      phone: '手机号',
      email: '邮箱',
      role: '角色',
      status: '状态',
      lastLogin: '最后登录',
      createTime: '创建时间',
      actions: '操作'
    },
    actions: {
      view: '查看详情',
      edit: '编辑',
      assignRole: '分配角色',
      delete: '删除'
    },
    dialog: {
      addUser: '新增用户',
      editUser: '编辑用户',
      assignRole: '分配角色',
      userInfo: '用户信息'
    },
    validation: {
      usernameRequired: '请输入用户名',
      realNameRequired: '请输入真实姓名',
      phoneRequired: '请输入手机号',
      phoneFormat: '请输入正确的手机号格式',
      emailRequired: '请输入邮箱',
      emailFormat: '请输入正确的邮箱格式',
      passwordRequired: '请输入密码',
      passwordLength: '密码长度至少6位',
      confirmPasswordRequired: '请确认密码',
      passwordMismatch: '两次输入的密码不一致'
    },
    messages: {
      loadSuccess: '用户列表加载成功',
      loadFailed: '用户列表加载失败',
      viewUser: '查看用户信息',
      deleteConfirm: '确定要删除用户 "{name}" 吗？',
      deleteSuccess: '删除成功',
      deleteCanceled: '已取消删除',
      batchDeleteConfirm: '确定要删除选中的 {count} 个用户吗？',
      statusChanged: '用户状态已{status}',
      statusChangeFailed: '状态更新失败',
      updateSuccess: '用户更新成功',
      createSuccess: '用户创建成功',
      roleAssignSuccess: '角色分配成功',
      roleAssignFailed: '角色分配失败',
      exportInDevelopment: '导出功能开发中'
    }
  },

  // 菜单管理
  menuMgmt: {
    title: '菜单管理',
    subtitle: '管理系统菜单结构、路由配置和图标设置',
    actions: {
      add: '新增菜单',
      addChild: '新增子菜单',
      export: '导出菜单',
      preview: '路由预览'
    },
    form: {
      name: '菜单名称',
      namePlaceholder: '请输入菜单名称',
      type: '菜单类型',
      typePlaceholder: '请选择菜单类型',
      parentMenu: '上级菜单',
      parentMenuPlaceholder: '请选择上级菜单',
      icon: '菜单图标',
      iconPlaceholder: '请选择图标',
      path: '路由路径',
      pathPlaceholder: '请输入路由路径',
      component: '组件路径',
      componentPlaceholder: '请输入组件路径',
      permission: '权限标识',
      permissionPlaceholder: '请输入权限标识',
      sort: '排序',
      status: '显示状态',
      statusPlaceholder: '请选择状态',
      visible: '是否显示',
      show: '显示',
      hide: '隐藏',
      enable: '启用',
      disable: '禁用',
      to: '至',
      unknown: '未知',
      cache: '是否缓存',
      cached: '缓存',
      notCached: '不缓存',
      external: '是否外链',
      remark: '备注',
      remarkPlaceholder: '请输入菜单描述'
    },
    types: {
      catalog: '目录',
      menu: '菜单',
      button: '按钮'
    },
    status: {
      visible: '显示',
      hidden: '隐藏'
    },
    table: {
      name: '菜单名称',
      type: '菜单类型',
      path: '路由路径',
      component: '组件路径',
      permission: '权限标识',
      status: '状态',
      sort: '排序',
      createTime: '创建时间',
      actions: '操作',
      external: '外链',
      cached: '缓存'
    },
    dialog: {
      addTitle: '新增菜单',
      editTitle: '编辑菜单',
      addChildTitle: '新增子菜单'
    },
    routePreview: {
      title: '路由预览',
      alertTitle: '路由配置预览',
      alertDescription: '以下是根据菜单配置生成的路由结构，可用于前端路由配置参考',
      copy: '复制配置'
    },
    validation: {
      nameRequired: '请输入菜单名称',
      nameLength: '菜单名称长度在 2 到 50 个字符',
      typeRequired: '请选择菜单类型',
      pathRequired: '请输入路由路径'
    },
    messages: {
      hasChildren: '该菜单下还有子菜单，无法删除',
      deleteConfirm: '确定要删除菜单 "{name}" 吗？',
      deleteTitle: '删除确认',
      deleteSuccess: '删除成功',
      deleteCancelled: '已取消删除',
      statusUpdateSuccess: '菜单已{status}',
      statusUpdateFailed: '状态更新失败',
      createSuccess: '菜单创建成功',
      updateSuccess: '菜单更新成功',
      copySuccess: '路由配置已复制到剪贴板',
      copyFailed: '复制失败，请手动复制',
      syncSuccess: '路由同步成功'
    },
    icons: {
      dashboard: '仪表板',
      user: '用户',
      lock: '锁定',
      key: '钥匙',
      menu: '菜单',
      document: '文档',
      operation: '操作',
      files: '文件',
      setting: '设置',
      building: '办公楼',
      monitor: '监控',
      analysis: '数据分析',
      notification: '通知'
    }
  },

  // 部门管理
  department: {
    title: '部门管理',
    subtitle: '管理组织架构树和部门信息',
    form: {
      name: '部门名称',
      code: '部门编码',
      leader: '负责人',
      phone: '联系电话',
      email: '邮箱',
      address: '地址',
      description: '描述',
      parentDepartment: '上级部门',
      namePlaceholder: '请输入部门名称',
      codePlaceholder: '请输入部门编码',
      leaderPlaceholder: '请输入负责人',
      phonePlaceholder: '请输入联系电话',
      emailPlaceholder: '请输入邮箱',
      addressPlaceholder: '请输入地址',
      descriptionPlaceholder: '请输入描述',
      statusPlaceholder: '请选择状态'
    },
    table: {
      name: '部门名称',
      code: '部门编码',
      leader: '负责人',
      contact: '联系方式',
      memberCount: '人员数量',
      headquarters: '总部',
      notSet: '未设置',
      memberCountValue: '{count}人',
      totalCount: '共 {count} 个部门'
    },
    actions: {
      add: '新增部门',
      addChild: '新增子部门',
      export: '导出部门',
      orgChart: '组织架构图',
      expandAll: '展开全部',
      collapseAll: '收起全部',
      refresh: '刷新',
      sync: '同步组织架构',
      exportImage: '导出图片'
    },
    orgChart: {
      title: '组织架构图',
      description: '以下是当前组织架构的可视化展示，可以清晰查看各部门的层级关系',
      company: '公司',
      ceo: '总经理'
    },
    validation: {
      nameRequired: '请输入部门名称',
      nameLength: '部门名称长度在 2 到 50 个字符',
      codeRequired: '请输入部门编码',
      codeFormat: '部门编码只能包含大写字母和数字，长度2-10位'
    },
    messages: {
      loadSuccess: '部门列表加载成功',
      loadError: '加载部门列表失败',
      hasChildDepartments: '该部门下还有子部门，无法删除',
      hasEmployees: '该部门下还有员工，无法删除',
      deleteConfirm: '确定要删除部门 "{name}" 吗？删除后无法恢复。',
      deleteTitle: '删除确认',
      deleteSuccess: '部门删除成功',
      deleteError: '删除部门失败',
      statusUpdateSuccess: '部门状态更新成功',
      statusUpdateError: '更新部门状态失败',
      updateSuccess: '部门更新成功',
      createSuccess: '部门创建成功',
      updateError: '部门更新失败',
      createError: '部门创建失败',
      syncSuccess: '组织架构同步成功'
    }
  },

  // 系统设置
  settings: {
    title: '系统设置',
    subtitle: '管理系统配置、参数设置和安全策略',
    
    // 页面操作按钮
    actions: {
      exportConfig: '导出配置',
      importConfig: '导入配置',
      saveAll: '保存所有设置'
    },
    
    // 网站信息
    website: {
      title: '网站信息',
      siteName: '网站名称',
      siteNamePlaceholder: '请输入网站名称',
      siteDescription: '网站描述',
      siteDescriptionPlaceholder: '请输入网站描述',
      siteLogo: '网站Logo',
      logoHelpText: '建议尺寸：200x60px',
      logoFormatText: '支持格式：JPG、PNG',
      favicon: '网站图标',
      faviconHelpText: '建议尺寸：32x32px',
      copyright: '版权信息',
      copyrightPlaceholder: '请输入版权信息',
      icp: 'ICP备案号',
      icpPlaceholder: '请输入ICP备案号'
    },
    
    // 联系信息
    contact: {
      title: '联系信息',
      phone: '联系电话',
      phonePlaceholder: '请输入联系电话',
      email: '联系邮箱',
      emailPlaceholder: '请输入联系邮箱',
      address: '联系地址',
      addressPlaceholder: '请输入联系地址',
      workTime: '工作时间',
      workTimePlaceholder: '请输入工作时间'
    },
    
    // 系统配置
    system: {
      title: '系统配置',
      timezone: '时区设置',
      timezonePlaceholder: '请选择时区',
      defaultLanguage: '默认语言',
      languagePlaceholder: '请选择默认语言',
      dateFormat: '日期格式',
      dateFormatPlaceholder: '请选择日期格式',
      pageSize: '分页大小',
      pageSizePlaceholder: '请输入分页大小',
      maintenance: '维护模式',
      maintenanceDesc: '开启后前台将显示维护页面',
      registration: '允许注册',
      registrationDesc: '是否允许用户自主注册',
      guestAccess: '游客访问',
      guestAccessDesc: '是否允许游客访问部分页面'
    },
    
    // 登录安全
    loginSecurity: {
      title: '登录安全',
      passwordComplexity: '密码复杂度',
      passwordComplexityDesc: '要求密码包含大小写字母、数字和特殊字符',
      loginFailureLock: '登录失败锁定',
      loginFailureLockDesc: '连续登录失败后锁定账户',
      maxAttempts: '最大尝试次数',
      maxAttemptsPlaceholder: '请输入最大尝试次数',
      lockDuration: '锁定时长（分钟）',
      lockDurationPlaceholder: '请输入锁定时长',
      forceTwoFactor: '强制双因子认证',
      forceTwoFactorDesc: '要求所有用户启用双因子认证',
      sessionTimeout: '会话超时（分钟）',
      sessionTimeoutPlaceholder: '请输入会话超时时间'
    },
    
    // 会话管理
    session: {
      title: '会话管理',
      maxSessions: '最大并发会话数',
      maxSessionsPlaceholder: '请输入最大会话数',
      kickoutType: '踢出策略',
      kickoutTypePlaceholder: '请选择踢出策略',
      kickoutOld: '踢出旧会话',
      kickoutNew: '拒绝新会话',
      rememberMe: '记住我功能',
      rememberMeDesc: '允许用户选择记住登录状态',
      rememberDuration: '记住时长（天）',
      rememberDurationPlaceholder: '请输入记住时长'
    },
    
    // IP访问控制
    ipControl: {
      title: 'IP访问控制',
      enableWhitelist: '启用白名单',
      enableWhitelistDesc: '只允许白名单内的IP访问',
      whitelist: 'IP白名单',
      whitelistPlaceholder: '请输入IP地址，多个用换行分隔',
      enableBlacklist: '启用黑名单',
      enableBlacklistDesc: '禁止黑名单内的IP访问',
      blacklist: 'IP黑名单',
      blacklistPlaceholder: '请输入IP地址，多个用换行分隔'
    },
    
    // 邮件设置
    email: {
      title: '邮件设置',
      smtpConfig: 'SMTP配置',
      smtpHost: 'SMTP服务器',
      smtpHostPlaceholder: '请输入SMTP服务器地址',
      smtpPort: 'SMTP端口',
      smtpPortPlaceholder: '请输入SMTP端口',
      encryption: '加密方式',
      encryptionPlaceholder: '请选择加密方式',
      username: '用户名',
      usernamePlaceholder: '请输入邮箱用户名',
      password: '密码',
      passwordPlaceholder: '请输入邮箱密码',
      senderName: '发件人名称',
      senderNamePlaceholder: '请输入发件人名称',
      senderEmail: '发件人邮箱',
      senderEmailPlaceholder: '请输入发件人邮箱',
      testConnection: '测试连接',
      testConnectionSuccess: '邮件服务器连接成功',
      testConnectionFailed: '邮件服务器连接失败',
      
      // 邮件模板开关
      templates: {
        welcome: '欢迎邮件',
        welcomeDesc: '新用户注册时发送欢迎邮件',
        resetPassword: '密码重置',
        resetPasswordDesc: '用户申请密码重置时发送邮件',
        loginAlert: '登录提醒',
        loginAlertDesc: '异常登录时发送安全提醒邮件',
        systemNotice: '系统通知',
        systemNoticeDesc: '系统重要通知邮件推送'
      }
    },
    
    // 存储配置
    storage: {
      title: '存储配置',
      uploadLimit: '文件上传限制',
      maxFileSize: '最大文件大小（MB）',
      maxFileSizePlaceholder: '请输入最大文件大小',
      allowedTypes: '允许的文件类型',
      allowedTypesPlaceholder: '请输入允许的文件扩展名，用逗号分隔',
      uploadPath: '上传路径',
      uploadPathPlaceholder: '请输入文件上传路径',
      
      cloudStorage: '云存储配置',
      storageType: '存储类型',
      storageTypePlaceholder: '请选择存储类型',
      localStorage: '本地存储',
      aliOss: '阿里云OSS',
      tencentCos: '腾讯云COS',
      qiniuKodo: '七牛云Kodo',
      
      accessKey: 'Access Key',
      accessKeyPlaceholder: '请输入Access Key',
      secretKey: 'Secret Key',
      secretKeyPlaceholder: '请输入Secret Key',
      bucket: 'Bucket名称',
      bucketPlaceholder: '请输入Bucket名称',
      region: '地域',
      regionPlaceholder: '请输入地域',
      domain: '访问域名',
      domainPlaceholder: '请输入访问域名'
    },
    
    // 缓存设置
    cache: {
      title: '缓存设置',
      cacheType: '缓存类型',
      cacheTypePlaceholder: '请选择缓存类型',
      memory: '内存缓存',
      redis: 'Redis缓存',
      
      redisConfig: 'Redis配置',
      redisHost: 'Redis主机',
      redisHostPlaceholder: '请输入Redis主机地址',
      redisPort: 'Redis端口',
      redisPortPlaceholder: '请输入Redis端口',
      redisPassword: 'Redis密码',
      redisPasswordPlaceholder: '请输入Redis密码',
      redisDatabase: '数据库索引',
      redisDatabasePlaceholder: '请输入数据库索引',
      
      expiration: '缓存过期时间',
      defaultExpiration: '默认过期时间（秒）',
      defaultExpirationPlaceholder: '请输入默认过期时间',
      userCacheExpiration: '用户缓存过期时间（秒）',
      userCacheExpirationPlaceholder: '请输入用户缓存过期时间',
      menuCacheExpiration: '菜单缓存过期时间（秒）',
      menuCacheExpirationPlaceholder: '请输入菜单缓存过期时间'
    },
    
    // 日志设置
    log: {
      title: '日志设置',
      logLevel: '日志级别',
      logLevelPlaceholder: '请选择日志级别',
      debug: '调试',
      info: '信息',
      warn: '警告',
      error: '错误',
      
      retention: '日志保留策略',
      retentionDays: '保留天数',
      retentionDaysPlaceholder: '请输入保留天数',
      maxLogSize: '单个日志文件最大大小（MB）',
      maxLogSizePlaceholder: '请输入最大大小',
      maxLogFiles: '最大日志文件数',
      maxLogFilesPlaceholder: '请输入最大文件数',
      
      auditLog: '审计日志',
      auditLogDesc: '记录用户操作和系统变更',
      errorLog: '错误日志',
      errorLogDesc: '记录系统错误和异常',
      accessLog: '访问日志',
      accessLogDesc: '记录用户访问和API调用',
      
      cleanupLogs: '清理日志',
      cleanupLogsDesc: '清理过期的日志文件',
      cleanupSuccess: '日志清理成功',
      cleanupFailed: '日志清理失败'
    },
    
    // 消息提示
    messages: {
      saveSuccess: '设置保存成功',
      saveFailed: '设置保存失败',
      configExported: '配置导出成功',
      configImported: '配置导入成功',
      invalidConfig: '配置文件格式无效',
      confirmSave: '确定要保存所有设置吗？',
      confirmExport: '确定要导出当前配置吗？',
      confirmImport: '确定要导入配置吗？这将覆盖当前设置',
      logoUploadSuccess: 'Logo上传成功',
      logoUploadFailed: 'Logo上传失败',
      logoSizeError: '图片大小不能超过2MB',
      logoFormatError: '只支持JPG、PNG格式的图片'
    }
  },

  // 个人中心
  profile: {
    title: '个人中心',
    description: '个人信息管理和偏好设置',
    changeAvatar: '点击更换头像',
    username: '用户名',
    email: '邮箱',
    phone: '手机号',
    department: '部门',
    role: '角色',
    lastLogin: '最后登录',
    editProfile: '编辑资料',
    realName: '真实姓名',
    bio: '个人简介',
    enterRealName: '请输入真实姓名',
    enterEmail: '请输入邮箱',
    enterPhone: '请输入手机号',
    enterBio: '请输入个人简介',
    
    tabs: {
      settings: '个人设置',
      security: '安全中心',
      logs: '操作日志'
    },
    
    settings: {
      interface: '界面设置',
      theme: '主题模式',
      selectTheme: '请选择主题',
      lightMode: '浅色模式',
      darkMode: '深色模式',
      autoMode: '跟随系统',
      language: '显示语言',
      selectLanguage: '请选择语言',
      simplifiedChinese: '简体中文',
      traditionalChinese: '繁体中文',
      sidebarCollapse: '侧边栏折叠',
      defaultCollapsed: '默认折叠',
      defaultExpanded: '默认展开',
      breadcrumbNav: '面包屑导航',
      notifications: '通知设置',
      emailNotifications: '邮件通知',
      systemNotifications: '系统通知',
      securityAlerts: '安全提醒',
      updateNotifications: '更新通知',
      marketingPromotion: '营销推广',
      desktopNotifications: '桌面通知',
      soundAlerts: '声音提醒',
      privacy: '隐私设置',
      onlineStatus: '在线状态',
      profileVisibility: '资料可见性',
      selectVisibility: '请选择可见性',
      everyone: '所有人',
      colleagues: '同事',
      onlyMe: '仅自己'
    },
    
    security: {
      status: '安全状态',
      passwordGood: '密码安全',
      passwordSecure: '当前密码安全性良好',
      twoFactorDisabled: '双因子认证未启用',
      recommendEnable: '建议启用以提高安全性',
      loginProtectionNormal: '登录保护正常',
      accountSecure: '账户安全状态良好',
      actions: '安全操作',
      changePassword: '修改密码',
      changePasswordDesc: '定期修改密码可以提高账户安全性',
      twoFactorAuth: '双因子认证',
      twoFactorDesc: '启用双因子认证，为您的账户提供额外保护',
      deviceManagement: '设备管理',
      deviceManagementDesc: '查看和管理已登录的设备',
      loginLogs: '登录日志',
      loginLogsDesc: '查看账户的登录历史记录',
      manage: '管理',
      enable: '启用',
      viewDevices: '查看设备',
      viewLogs: '查看日志',
      currentPassword: '当前密码',
      newPassword: '新密码',
      confirmPassword: '确认密码',
      enterCurrentPassword: '请输入当前密码',
      enterNewPassword: '请输入新密码',
      enterConfirmPassword: '请确认新密码'
    },
    
    logs: {
      operationType: '操作类型',
      selectType: '请选择类型',
      login: '登录',
      logout: '登出',
      profileEdit: '修改资料',
      passwordChange: '修改密码',
      systemOperation: '系统操作',
      timeRange: '时间范围',
      startDate: '开始日期',
      endDate: '结束日期',
      description: '操作描述',
      ipAddress: 'IP地址',
      deviceInfo: '设备信息',
      operationTime: '操作时间',
      status: '状态',
      success: '成功',
      failed: '失败'
    },
    
    validation: {
      realNameRequired: '请输入真实姓名',
      emailRequired: '请输入邮箱',
      emailFormat: '请输入正确的邮箱格式',
      phoneRequired: '请输入手机号',
      phoneFormat: '请输入正确的手机号格式',
      currentPasswordRequired: '请输入当前密码',
      newPasswordRequired: '请输入新密码',
      passwordMinLength: '密码长度至少8位',
      confirmPasswordRequired: '请确认密码',
      passwordMismatch: '两次输入的密码不一致'
    }
  },

  // 首页仪表板
  dashboard: {
    title: '首页仪表板',
    subtitle: '系统概览和数据统计',
    
    // 欢迎信息
    welcome: {
      title: '欢迎回来！',
      message: '今天是美好的一天，让我们开始工作吧！'
    },
    
    // 统计卡片
    stats: {
      totalUsers: '用户总数',
      onlineUsers: '在线用户',
      totalRoles: '角色数量',
      totalMenus: '菜单数量',
      totalDepartments: '部门数量',
      todayVisits: '今日访问',
      monthlyVisits: '月度访问',
      systemLoad: '系统负载',
      
      // 趋势描述
      trend: {
        compared: '较昨日',
        increase: '增长',
        decrease: '下降',
        unchanged: '持平'
      }
    },
    
    // 图表区域
    charts: {
      userGrowth: {
        title: '用户增长趋势',
        subtitle: '最近用户注册和活跃情况',
        timeRange: {
          week: '最近7天',
          month: '最近30天',
          quarter: '最近90天'
        },
        legend: {
          newUsers: '新增用户',
          activeUsers: '活跃用户'
        }
      },
      
      systemMetrics: {
        title: '系统指标',
        subtitle: '服务器性能和资源使用情况',
        metrics: {
          cpu: 'CPU使用率',
          memory: '内存使用率',
          disk: '磁盘使用率',
          network: '网络流量'
        }
      },
      
      operationStats: {
        title: '操作统计',
        subtitle: '用户操作和系统事件统计',
        operations: {
          login: '登录次数',
          logout: '登出次数',
          create: '创建操作',
          update: '更新操作',
          delete: '删除操作'
        }
      }
    },
    
    // 快捷操作
    quickActions: {
      title: '快捷操作',
      addUser: '新增用户',
      addRole: '新增角色',
      addMenu: '新增菜单',
      addDepartment: '新增部门',
      systemSettings: '系统设置',
      viewLogs: '查看日志',
      backup: '数据备份',
      maintenance: '系统维护'
    },
    
    // 最新动态
    activities: {
      title: '最新动态',
      viewAll: '查看全部',
      noActivities: '暂无动态',
      types: {
        userLogin: '用户登录',
        userRegister: '用户注册',
        roleCreate: '角色创建',
        menuUpdate: '菜单更新',
        systemConfig: '系统配置',
        dataBackup: '数据备份'
      },
      timeAgo: {
        justNow: '刚刚',
        minutesAgo: '{minutes}分钟前',
        hoursAgo: '{hours}小时前',
        daysAgo: '{days}天前'
      }
    },
    
    // 系统公告
    notices: {
      title: '系统公告',
      viewAll: '查看全部',
      noNotices: '暂无公告',
      types: {
        system: '系统通知',
        maintenance: '维护公告',
        update: '更新通知',
        security: '安全提醒'
      },
      priority: {
        high: '重要',
        medium: '一般',
        low: '普通'
      }
    },
    
    // 待办事项
    todos: {
      title: '待办事项',
      viewAll: '查看全部',
      noTodos: '暂无待办',
      addTodo: '添加待办',
      markComplete: '标记完成',
      priority: {
        high: '高优先级',
        medium: '中优先级',
        low: '低优先级'
      },
      status: {
        pending: '待处理',
        inProgress: '进行中',
        completed: '已完成'
      }
    },
    
    // 天气信息
    weather: {
      title: '天气信息',
      location: '当前位置',
      temperature: '温度',
      humidity: '湿度',
      windSpeed: '风速',
      conditions: {
        sunny: '晴天',
        cloudy: '多云',
        rainy: '雨天',
        snowy: '雪天',
        foggy: '雾天'
      }
    },
    
    // 系统状态
    systemStatus: {
      title: '系统状态',
      services: {
        database: '数据库',
        cache: '缓存服务',
        storage: '存储服务',
        email: '邮件服务',
        backup: '备份服务'
      },
      status: {
        online: '在线',
        offline: '离线',
        warning: '警告',
        error: '错误'
      },
      uptime: '运行时间',
      lastCheck: '最后检查'
    }
  },
}
