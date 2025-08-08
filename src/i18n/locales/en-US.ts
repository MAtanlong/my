export default {
  // Common
  common: {
    confirm: 'Confirm',
    cancel: 'Cancel',
    save: 'Save',
    delete: 'Delete',
    edit: 'Edit',
    add: 'Add',
    search: 'Search',
    reset: 'Reset',
    refresh: 'Refresh',
    export: 'Export',
    import: 'Import',
    view: 'View',
    copy: 'Copy',
    submit: 'Submit',
    back: 'Back',
    close: 'Close',
    loading: 'Loading...',
    noData: 'No Data',
    operation: 'Operation',
    status: 'Status',
    enabled: 'Enabled',
    disabled: 'Disabled',
    createTime: 'Create Time',
    updateTime: 'Update Time',
    remark: 'Remark',
    sort: 'Sort',
    total: 'Total {count} records',
    batchDelete: 'Batch Delete',
    batchDeleteConfirm: 'Batch Delete Confirmation',
    batchDeleteSuccess: 'Batch delete successful',
    normal: 'Normal',
    cancelled: 'Cancelled',
    featureInDevelopment: 'Feature under development',
    logout: 'Logout'
  },

  // Navigation Menu
  menu: {
    dashboard: 'Dashboard',
    userManagement: 'User Management',
    userList: 'User List',
    roleManagement: 'Role Management',
    roleList: 'Role List',
    permissionManagement: 'Permission Management',
    permissionList: 'Permission List',
    menuManagement: 'Menu Management',
    menuList: 'Menu List',
    departmentManagement: 'Department Management',
    departmentList: 'Department List',
    systemSettings: 'System Settings',
    personalCenter: 'Personal Center'
  },

  // Login Page
  login: {
    title: 'Permission Management System',
    subtitle: 'Unified Identity Authentication Platform',
    accountLogin: 'Account Login',
    phoneLogin: 'Phone Login',
    qrLogin: 'QR Code Login',
    wechatLogin: 'WeChat Login',
    username: 'Username',
    password: 'Password',
    phone: 'Phone Number',
    captcha: 'Captcha',
    smsCode: 'SMS Code',
    rememberMe: 'Remember Me',
    forgotPassword: 'Forgot Password?',
    loginButton: 'Login',
    sendSms: 'Send Code',
    resendSms: 'Resend ({count}s)',
    loginSuccess: 'Login Successful',
    usernameRequired: 'Please enter username',
    passwordRequired: 'Please enter password',
    phoneRequired: 'Please enter phone number',
    captchaRequired: 'Please enter captcha',
    smsCodeRequired: 'Please enter SMS code',
    phoneFormat: 'Please enter correct phone format',
    refreshCaptcha: 'Click to refresh captcha'
  },

  // Role Management
  role: {
    title: 'Role Management',
    description: 'Manage system roles and permission configuration',
    add: 'Add Role',
    edit: 'Edit Role',
    view: 'View',
    copy: 'Copy Role',
    delete: 'Delete',
    export: 'Export Roles',
    configPermission: 'Configure Permission',
    name: 'Role Name',
    code: 'Role Code',
    status: 'Status',
    sort: 'Sort',
    remark: 'Remark',
    permissionCount: 'Permissions',
    userCount: 'Users',
    createTime: 'Create Time',
    actions: 'Actions',
    enabled: 'Enabled',
    disabled: 'Disabled',
    namePlaceholder: 'Please enter role name',
    codePlaceholder: 'Please enter role code',
    remarkPlaceholder: 'Please enter remark',
    roleInfo: 'Role Information',
    permissionConfig: 'Permission Configuration',
    expandAll: 'Expand All',
    collapseAll: 'Collapse All',
    selectAll: 'Select All',
    unselectAll: 'Unselect All',
    validation: {
      nameRequired: 'Please enter role name',
      nameLength: 'Role name length should be between 2 and 50 characters',
      codeRequired: 'Please enter role code',
      codeFormat: 'Role code can only contain uppercase letters and underscores'
    },
    messages: {
      loadSuccess: 'Role list loaded successfully',
      loadFailed: 'Failed to load role list',
      viewRole: 'View role: {name}',
      cannotDeleteWithUsers: 'Cannot delete role with associated users',
      deleteConfirm: 'Are you sure to delete role "{name}"?',
      batchDeleteWithUsers: 'Some selected roles have associated users and cannot be deleted',
      batchDeleteConfirm: 'Are you sure to delete {count} selected roles?',
      statusChanged: 'Role status has been {status}',
      statusChangeFailed: 'Failed to update status',
      configPermission: 'Configure permissions for role: {name}',
      saveSuccess: 'Role saved successfully',
      saveFailed: 'Failed to save role',
      updateSuccess: 'Role updated successfully',
      createSuccess: 'Role created successfully',
      permissionSaveSuccess: 'Permissions configured successfully',
      permissionSaveFailed: 'Failed to configure permissions',
      exportList: 'Export role list',
      exportInDevelopment: 'Export feature is under development'
    }
  },

  // Permission Management
  permission: {
    module: 'Module',
    menu: 'Menu',
    button: 'Button'
  },

  // User Management
  user: {
    title: 'User Management',
    subtitle: 'Manage system user information and permission settings',
    form: {
      username: 'Username',
      realName: 'Real Name',
      phone: 'Phone Number',
      email: 'Email',
      password: 'Password',
      confirmPassword: 'Confirm Password',
      gender: 'Gender',
      status: 'Status',
      department: 'Department',
      remark: 'Remark',
      male: 'Male',
      female: 'Female',
      enabled: 'Enabled',
      disabled: 'Disabled'
    },
    table: {
      avatar: 'Avatar',
      username: 'Username',
      realName: 'Real Name',
      phone: 'Phone',
      email: 'Email',
      role: 'Role',
      status: 'Status',
      lastLogin: 'Last Login',
      createTime: 'Create Time',
      actions: 'Actions'
    },
    actions: {
      view: 'View Details',
      edit: 'Edit',
      assignRole: 'Assign Role',
      delete: 'Delete'
    },
    dialog: {
      addUser: 'Add User',
      editUser: 'Edit User',
      assignRole: 'Assign Role',
      userInfo: 'User Information'
    },
    validation: {
      usernameRequired: 'Please enter username',
      realNameRequired: 'Please enter real name',
      phoneRequired: 'Please enter phone number',
      phoneFormat: 'Please enter correct phone format',
      emailRequired: 'Please enter email',
      emailFormat: 'Please enter correct email format',
      passwordRequired: 'Please enter password',
      passwordLength: 'Password length should be at least 6 characters',
      confirmPasswordRequired: 'Please confirm password',
      passwordMismatch: 'Passwords do not match'
    },
    messages: {
      loadSuccess: 'User list loaded successfully',
      loadFailed: 'Failed to load user list',
      viewUser: 'View user information',
      deleteConfirm: 'Are you sure to delete user "{name}"?',
      deleteSuccess: 'Delete successful',
      deleteCanceled: 'Delete canceled',
      batchDeleteConfirm: 'Are you sure to delete {count} selected users?',
      statusChanged: 'User status has been {status}',
      statusChangeFailed: 'Failed to update status',
      updateSuccess: 'User updated successfully',
      createSuccess: 'User created successfully',
      roleAssignSuccess: 'Role assigned successfully',
      roleAssignFailed: 'Failed to assign role',
      exportInDevelopment: 'Export function is under development'
    }
  },

  // Menu Management
  menuMgmt: {
    title: 'Menu Management',
    subtitle: 'Manage system menu structure, routing configuration and icon settings',
    actions: {
      add: 'Add Menu',
      addChild: 'Add Child Menu',
      export: 'Export Menu',
      preview: 'Route Preview'
    },
    form: {
      name: 'Menu Name',
      namePlaceholder: 'Please enter menu name',
      type: 'Menu Type',
      typePlaceholder: 'Please select menu type',
      parentMenu: 'Parent Menu',
      parentMenuPlaceholder: 'Please select parent menu',
      icon: 'Menu Icon',
      iconPlaceholder: 'Please select icon',
      path: 'Route Path',
      pathPlaceholder: 'Please enter route path',
      component: 'Component Path',
      componentPlaceholder: 'Please enter component path',
      permission: 'Permission Code',
      permissionPlaceholder: 'Please enter permission code',
      sort: 'Sort Order',
      status: 'Display Status',
      statusPlaceholder: 'Please select status',
      visible: 'Visible',
      show: 'Show',
      hide: 'Hide',
      enable: 'Enable',
      disable: 'Disable',
      to: 'to',
      unknown: 'Unknown',
      cache: 'Cache',
      cached: 'Cached',
      notCached: 'Not Cached',
      external: 'External Link',
      remark: 'Remark',
      remarkPlaceholder: 'Please enter menu description'
    },
    types: {
      catalog: 'Catalog',
      menu: 'Menu',
      button: 'Button'
    },
    status: {
      visible: 'Visible',
      hidden: 'Hidden'
    },
    table: {
      name: 'Menu Name',
      type: 'Menu Type',
      path: 'Route Path',
      component: 'Component Path',
      permission: 'Permission Code',
      status: 'Status',
      sort: 'Sort',
      createTime: 'Create Time',
      actions: 'Actions',
      external: 'External',
      cached: 'Cached'
    },
    dialog: {
      addTitle: 'Add Menu',
      editTitle: 'Edit Menu',
      addChildTitle: 'Add Child Menu'
    },
    routePreview: {
      title: 'Route Preview',
      alertTitle: 'Route Configuration Preview',
      alertDescription: 'The following is the route structure generated based on menu configuration, which can be used as a reference for frontend route configuration',
      copy: 'Copy Configuration'
    },
    validation: {
      nameRequired: 'Please enter menu name',
      nameLength: 'Menu name length should be between 2 and 50 characters',
      typeRequired: 'Please select menu type',
      pathRequired: 'Please enter route path'
    },
    messages: {
      hasChildren: 'This menu has child menus and cannot be deleted',
      deleteConfirm: 'Are you sure you want to delete menu "{name}"?',
      deleteTitle: 'Delete Confirmation',
      deleteSuccess: 'Deleted successfully',
      deleteCancelled: 'Deletion cancelled',
      statusUpdateSuccess: 'Menu has been {status}',
      statusUpdateFailed: 'Status update failed',
      createSuccess: 'Menu created successfully',
      updateSuccess: 'Menu updated successfully',
      copySuccess: 'Route configuration copied to clipboard',
      copyFailed: 'Copy failed, please copy manually',
      syncSuccess: 'Route synchronization successful'
    },
    icons: {
      dashboard: 'Dashboard',
      user: 'User',
      lock: 'Lock',
      key: 'Key',
      menu: 'Menu',
      document: 'Document',
      operation: 'Operation',
      files: 'Files',
      setting: 'Setting',
      building: 'Building',
      monitor: 'Monitor',
      analysis: 'Data Analysis',
      notification: 'Notification'
    }
  },

  // Department Management
  department: {
    title: 'Department Management',
    subtitle: 'Manage organizational structure tree and department information',
    form: {
      name: 'Department Name',
      code: 'Department Code',
      leader: 'Leader',
      phone: 'Phone',
      email: 'Email',
      address: 'Address',
      description: 'Description',
      parentDepartment: 'Parent Department',
      namePlaceholder: 'Please enter department name',
      codePlaceholder: 'Please enter department code',
      leaderPlaceholder: 'Please enter leader',
      phonePlaceholder: 'Please enter phone',
      emailPlaceholder: 'Please enter email',
      addressPlaceholder: 'Please enter address',
      descriptionPlaceholder: 'Please enter description',
      statusPlaceholder: 'Please select status'
    },
    table: {
      name: 'Department Name',
      code: 'Department Code',
      leader: 'Leader',
      contact: 'Contact',
      memberCount: 'Members',
      headquarters: 'Headquarters',
      notSet: 'Not Set',
      memberCountValue: '{count} members',
      totalCount: 'Total {count} departments'
    },
    actions: {
      add: 'Add Department',
      addChild: 'Add Child Department',
      export: 'Export Departments',
      orgChart: 'Organization Chart',
      expandAll: 'Expand All',
      collapseAll: 'Collapse All',
      refresh: 'Refresh',
      sync: 'Sync Organization',
      exportImage: 'Export Image'
    },
    orgChart: {
      title: 'Organization Chart',
      description: 'The following is a visual representation of the current organizational structure, clearly showing the hierarchical relationships of each department',
      company: 'Company',
      ceo: 'CEO'
    },
    validation: {
      nameRequired: 'Please enter department name',
      nameLength: 'Department name length should be between 2 and 50 characters',
      codeRequired: 'Please enter department code',
      codeFormat: 'Department code can only contain uppercase letters and numbers, length 2-10 characters'
    },
    messages: {
      loadSuccess: 'Department list loaded successfully',
      loadError: 'Failed to load department list',
      hasChildDepartments: 'This department has child departments and cannot be deleted',
      hasEmployees: 'This department has employees and cannot be deleted',
      deleteConfirm: 'Are you sure to delete department "{name}"? This action cannot be undone.',
      deleteTitle: 'Delete Confirmation',
      deleteSuccess: 'Department deleted successfully',
      deleteError: 'Failed to delete department',
      statusUpdateSuccess: 'Department status updated successfully',
      statusUpdateError: 'Failed to update department status',
      updateSuccess: 'Department updated successfully',
      createSuccess: 'Department created successfully',
      updateError: 'Failed to update department',
      createError: 'Failed to create department',
      syncSuccess: 'Organization structure synchronized successfully'
    }
  },

  // System Settings
  settings: {
    title: 'System Settings',
    subtitle: 'Manage system configuration, parameter settings and security policies',
    
    // Page action buttons
    actions: {
      exportConfig: 'Export Config',
      importConfig: 'Import Config',
      saveAll: 'Save All Settings'
    },
    
    // Website information
    website: {
      title: 'Website Information',
      siteName: 'Site Name',
      siteNamePlaceholder: 'Please enter site name',
      siteDescription: 'Site Description',
      siteDescriptionPlaceholder: 'Please enter site description',
      siteLogo: 'Site Logo',
      logoHelpText: 'Recommended size: 200x60px',
      logoFormatText: 'Supported formats: JPG, PNG',
      favicon: 'Favicon',
      faviconHelpText: 'Recommended size: 32x32px',
      copyright: 'Copyright',
      copyrightPlaceholder: 'Please enter copyright information',
      icp: 'ICP Registration',
      icpPlaceholder: 'Please enter ICP registration number'
    },
    
    // Contact information
    contact: {
      title: 'Contact Information',
      phone: 'Contact Phone',
      phonePlaceholder: 'Please enter contact phone',
      email: 'Contact Email',
      emailPlaceholder: 'Please enter contact email',
      address: 'Contact Address',
      addressPlaceholder: 'Please enter contact address',
      workTime: 'Work Time',
      workTimePlaceholder: 'Please enter work time'
    },
    
    // System configuration
    system: {
      title: 'System Configuration',
      timezone: 'Timezone',
      timezonePlaceholder: 'Please select timezone',
      defaultLanguage: 'Default Language',
      languagePlaceholder: 'Please select default language',
      dateFormat: 'Date Format',
      dateFormatPlaceholder: 'Please select date format',
      pageSize: 'Page Size',
      pageSizePlaceholder: 'Please enter page size',
      maintenance: 'Maintenance Mode',
      maintenanceDesc: 'When enabled, the frontend will display maintenance page',
      registration: 'Allow Registration',
      registrationDesc: 'Whether to allow user self-registration',
      guestAccess: 'Guest Access',
      guestAccessDesc: 'Whether to allow guest access to some pages'
    },
    
    // Login security
    loginSecurity: {
      title: 'Login Security',
      passwordComplexity: 'Password Complexity',
      passwordComplexityDesc: 'Require passwords to contain uppercase, lowercase, numbers and special characters',
      loginFailureLock: 'Login Failure Lock',
      loginFailureLockDesc: 'Lock account after consecutive login failures',
      maxAttempts: 'Max Attempts',
      maxAttemptsPlaceholder: 'Please enter max attempts',
      lockDuration: 'Lock Duration (minutes)',
      lockDurationPlaceholder: 'Please enter lock duration',
      forceTwoFactor: 'Force Two-Factor Auth',
      forceTwoFactorDesc: 'Require all users to enable two-factor authentication',
      sessionTimeout: 'Session Timeout (minutes)',
      sessionTimeoutPlaceholder: 'Please enter session timeout'
    },
    
    // Session management
    session: {
      title: 'Session Management',
      maxSessions: 'Max Concurrent Sessions',
      maxSessionsPlaceholder: 'Please enter max sessions',
      kickoutType: 'Kickout Strategy',
      kickoutTypePlaceholder: 'Please select kickout strategy',
      kickoutOld: 'Kickout Old Session',
      kickoutNew: 'Reject New Session',
      rememberMe: 'Remember Me Feature',
      rememberMeDesc: 'Allow users to choose to remember login status',
      rememberDuration: 'Remember Duration (days)',
      rememberDurationPlaceholder: 'Please enter remember duration'
    },
    
    // IP access control
    ipControl: {
      title: 'IP Access Control',
      enableWhitelist: 'Enable Whitelist',
      enableWhitelistDesc: 'Only allow IPs in whitelist to access',
      whitelist: 'IP Whitelist',
      whitelistPlaceholder: 'Please enter IP addresses, separated by newlines',
      enableBlacklist: 'Enable Blacklist',
      enableBlacklistDesc: 'Prohibit IPs in blacklist from accessing',
      blacklist: 'IP Blacklist',
      blacklistPlaceholder: 'Please enter IP addresses, separated by newlines'
    },
    
    // Email settings
    email: {
      title: 'Email Settings',
      smtpConfig: 'SMTP Configuration',
      smtpHost: 'SMTP Server',
      smtpHostPlaceholder: 'Please enter SMTP server address',
      smtpPort: 'SMTP Port',
      smtpPortPlaceholder: 'Please enter SMTP port',
      encryption: 'Encryption',
      encryptionPlaceholder: 'Please select encryption method',
      username: 'Username',
      usernamePlaceholder: 'Please enter email username',
      password: 'Password',
      passwordPlaceholder: 'Please enter email password',
      senderName: 'Sender Name',
      senderNamePlaceholder: 'Please enter sender name',
      senderEmail: 'Sender Email',
      senderEmailPlaceholder: 'Please enter sender email',
      testConnection: 'Test Connection',
      testConnectionSuccess: 'Email server connection successful',
      testConnectionFailed: 'Email server connection failed',
      
      // Email template switches
      templates: {
        welcome: 'Welcome Email',
        welcomeDesc: 'Send welcome email when new user registers',
        resetPassword: 'Password Reset',
        resetPasswordDesc: 'Send email when user requests password reset',
        loginAlert: 'Login Alert',
        loginAlertDesc: 'Send security alert email for abnormal login',
        systemNotice: 'System Notice',
        systemNoticeDesc: 'Push important system notification emails'
      }
    },
    
    // Storage configuration
    storage: {
      title: 'Storage Configuration',
      uploadLimit: 'File Upload Limit',
      maxFileSize: 'Max File Size (MB)',
      maxFileSizePlaceholder: 'Please enter max file size',
      allowedTypes: 'Allowed File Types',
      allowedTypesPlaceholder: 'Please enter allowed file extensions, separated by commas',
      uploadPath: 'Upload Path',
      uploadPathPlaceholder: 'Please enter file upload path',
      
      cloudStorage: 'Cloud Storage Configuration',
      storageType: 'Storage Type',
      storageTypePlaceholder: 'Please select storage type',
      localStorage: 'Local Storage',
      aliOss: 'Alibaba Cloud OSS',
      tencentCos: 'Tencent Cloud COS',
      qiniuKodo: 'Qiniu Cloud Kodo',
      
      accessKey: 'Access Key',
      accessKeyPlaceholder: 'Please enter Access Key',
      secretKey: 'Secret Key',
      secretKeyPlaceholder: 'Please enter Secret Key',
      bucket: 'Bucket Name',
      bucketPlaceholder: 'Please enter bucket name',
      region: 'Region',
      regionPlaceholder: 'Please enter region',
      domain: 'Access Domain',
      domainPlaceholder: 'Please enter access domain'
    },
    
    // Cache settings
    cache: {
      title: 'Cache Settings',
      cacheType: 'Cache Type',
      cacheTypePlaceholder: 'Please select cache type',
      memory: 'Memory Cache',
      redis: 'Redis Cache',
      
      redisConfig: 'Redis Configuration',
      redisHost: 'Redis Host',
      redisHostPlaceholder: 'Please enter Redis host address',
      redisPort: 'Redis Port',
      redisPortPlaceholder: 'Please enter Redis port',
      redisPassword: 'Redis Password',
      redisPasswordPlaceholder: 'Please enter Redis password',
      redisDatabase: 'Database Index',
      redisDatabasePlaceholder: 'Please enter database index',
      
      expiration: 'Cache Expiration',
      defaultExpiration: 'Default Expiration (seconds)',
      defaultExpirationPlaceholder: 'Please enter default expiration time',
      userCacheExpiration: 'User Cache Expiration (seconds)',
      userCacheExpirationPlaceholder: 'Please enter user cache expiration time',
      menuCacheExpiration: 'Menu Cache Expiration (seconds)',
      menuCacheExpirationPlaceholder: 'Please enter menu cache expiration time'
    },
    
    // Log settings
    log: {
      title: 'Log Settings',
      logLevel: 'Log Level',
      logLevelPlaceholder: 'Please select log level',
      debug: 'Debug',
      info: 'Info',
      warn: 'Warning',
      error: 'Error',
      
      retention: 'Log Retention Policy',
      retentionDays: 'Retention Days',
      retentionDaysPlaceholder: 'Please enter retention days',
      maxLogSize: 'Max Log File Size (MB)',
      maxLogSizePlaceholder: 'Please enter max size',
      maxLogFiles: 'Max Log Files',
      maxLogFilesPlaceholder: 'Please enter max file count',
      
      auditLog: 'Audit Log',
      auditLogDesc: 'Record user operations and system changes',
      errorLog: 'Error Log',
      errorLogDesc: 'Record system errors and exceptions',
      accessLog: 'Access Log',
      accessLogDesc: 'Record user access and API calls',
      
      cleanupLogs: 'Cleanup Logs',
      cleanupLogsDesc: 'Clean up expired log files',
      cleanupSuccess: 'Log cleanup successful',
      cleanupFailed: 'Log cleanup failed'
    },
    
    // Messages
    messages: {
      saveSuccess: 'Settings saved successfully',
      saveFailed: 'Failed to save settings',
      configExported: 'Configuration exported successfully',
      configImported: 'Configuration imported successfully',
      invalidConfig: 'Invalid configuration file format',
      confirmSave: 'Are you sure to save all settings?',
      confirmExport: 'Are you sure to export current configuration?',
      confirmImport: 'Are you sure to import configuration? This will overwrite current settings',
      logoUploadSuccess: 'Logo uploaded successfully',
      logoUploadFailed: 'Logo upload failed',
      logoSizeError: 'Image size cannot exceed 2MB',
      logoFormatError: 'Only JPG and PNG formats are supported'
    }
  },

  // Personal Center
  profile: {
    title: 'Personal Center',
    description: 'Personal information management and preference settings',
    changeAvatar: 'Click to change avatar',
    username: 'Username',
    email: 'Email',
    phone: 'Phone',
    department: 'Department',
    role: 'Role',
    lastLogin: 'Last Login',
    editProfile: 'Edit Profile',
    realName: 'Real Name',
    bio: 'Bio',
    enterRealName: 'Please enter real name',
    enterEmail: 'Please enter email',
    enterPhone: 'Please enter phone',
    enterBio: 'Please enter bio',
    
    tabs: {
      settings: 'Settings',
      security: 'Security',
      logs: 'Activity Logs'
    },
    
    settings: {
      interface: 'Interface Settings',
      theme: 'Theme Mode',
      selectTheme: 'Please select theme',
      lightMode: 'Light Mode',
      darkMode: 'Dark Mode',
      autoMode: 'Follow System',
      language: 'Display Language',
      selectLanguage: 'Please select language',
      simplifiedChinese: 'Simplified Chinese',
      traditionalChinese: 'Traditional Chinese',
      sidebarCollapse: 'Sidebar Collapse',
      defaultCollapsed: 'Default Collapsed',
      defaultExpanded: 'Default Expanded',
      breadcrumbNav: 'Breadcrumb Navigation',
      notifications: 'Notification Settings',
      emailNotifications: 'Email Notifications',
      systemNotifications: 'System Notifications',
      securityAlerts: 'Security Alerts',
      updateNotifications: 'Update Notifications',
      marketingPromotion: 'Marketing Promotion',
      desktopNotifications: 'Desktop Notifications',
      soundAlerts: 'Sound Alerts',
      privacy: 'Privacy Settings',
      onlineStatus: 'Online Status',
      profileVisibility: 'Profile Visibility',
      selectVisibility: 'Please select visibility',
      everyone: 'Everyone',
      colleagues: 'Colleagues',
      onlyMe: 'Only Me'
    },
    
    security: {
      status: 'Security Status',
      passwordGood: 'Password Security',
      passwordSecure: 'Current password security is good',
      twoFactorDisabled: 'Two-Factor Authentication Disabled',
      recommendEnable: 'Recommend enabling for better security',
      loginProtectionNormal: 'Login Protection Normal',
      accountSecure: 'Account security status is good',
      actions: 'Security Actions',
      changePassword: 'Change Password',
      changePasswordDesc: 'Regularly changing passwords can improve account security',
      twoFactorAuth: 'Two-Factor Authentication',
      twoFactorDesc: 'Enable two-factor authentication for additional account protection',
      deviceManagement: 'Device Management',
      deviceManagementDesc: 'View and manage logged-in devices',
      loginLogs: 'Login Logs',
      loginLogsDesc: 'View account login history',
      manage: 'Manage',
      enable: 'Enable',
      viewDevices: 'View Devices',
      viewLogs: 'View Logs',
      currentPassword: 'Current Password',
      newPassword: 'New Password',
      confirmPassword: 'Confirm Password',
      enterCurrentPassword: 'Please enter current password',
      enterNewPassword: 'Please enter new password',
      enterConfirmPassword: 'Please confirm new password'
    },
    
    logs: {
      operationType: 'Operation Type',
      selectType: 'Please select type',
      login: 'Login',
      logout: 'Logout',
      profileEdit: 'Profile Edit',
      passwordChange: 'Password Change',
      systemOperation: 'System Operation',
      timeRange: 'Time Range',
      startDate: 'Start Date',
      endDate: 'End Date',
      description: 'Description',
      ipAddress: 'IP Address',
      deviceInfo: 'Device Info',
      operationTime: 'Operation Time',
      status: 'Status',
      success: 'Success',
      failed: 'Failed'
    },
    
    validation: {
      realNameRequired: 'Please enter real name',
      emailRequired: 'Please enter email',
      emailFormat: 'Please enter correct email format',
      phoneRequired: 'Please enter phone number',
      phoneFormat: 'Please enter correct phone format',
      currentPasswordRequired: 'Please enter current password',
      newPasswordRequired: 'Please enter new password',
      passwordMinLength: 'Password length should be at least 8 characters',
      confirmPasswordRequired: 'Please confirm password',
      passwordMismatch: 'Passwords do not match'
    }
  },

  // Dashboard
  dashboard: {
    title: 'Dashboard',
    subtitle: 'System overview and data statistics',
    
    // Welcome information
    welcome: {
      title: 'Welcome back!',
      message: 'It\'s a beautiful day, let\'s get to work!'
    },
    
    // Statistics cards
    stats: {
      totalUsers: 'Total Users',
      onlineUsers: 'Online Users',
      totalRoles: 'Total Roles',
      totalMenus: 'Total Menus',
      totalDepartments: 'Total Departments',
      todayVisits: 'Today\'s Visits',
      monthlyVisits: 'Monthly Visits',
      systemLoad: 'System Load',
      
      // Trend descriptions
      trend: {
        compared: 'vs yesterday',
        increase: 'increase',
        decrease: 'decrease',
        unchanged: 'unchanged'
      }
    },
    
    // Chart areas
    charts: {
      userGrowth: {
        title: 'User Growth Trend',
        subtitle: 'Recent user registration and activity',
        timeRange: {
          week: 'Last 7 days',
          month: 'Last 30 days',
          quarter: 'Last 90 days'
        },
        legend: {
          newUsers: 'New Users',
          activeUsers: 'Active Users'
        }
      },
      
      systemMetrics: {
        title: 'System Metrics',
        subtitle: 'Server performance and resource usage',
        metrics: {
          cpu: 'CPU Usage',
          memory: 'Memory Usage',
          disk: 'Disk Usage',
          network: 'Network Traffic'
        }
      },
      
      operationStats: {
        title: 'Operation Statistics',
        subtitle: 'User operations and system events statistics',
        operations: {
          login: 'Login Count',
          logout: 'Logout Count',
          create: 'Create Operations',
          update: 'Update Operations',
          delete: 'Delete Operations'
        }
      }
    },
    
    // Quick actions
    quickActions: {
      title: 'Quick Actions',
      addUser: 'Add User',
      addRole: 'Add Role',
      addMenu: 'Add Menu',
      addDepartment: 'Add Department',
      systemSettings: 'System Settings',
      viewLogs: 'View Logs',
      backup: 'Data Backup',
      maintenance: 'System Maintenance'
    },
    
    // Latest activities
    activities: {
      title: 'Latest Activities',
      viewAll: 'View All',
      noActivities: 'No activities',
      types: {
        userLogin: 'User Login',
        userRegister: 'User Registration',
        roleCreate: 'Role Creation',
        menuUpdate: 'Menu Update',
        systemConfig: 'System Configuration',
        dataBackup: 'Data Backup'
      },
      timeAgo: {
        justNow: 'Just now',
        minutesAgo: '{minutes} minutes ago',
        hoursAgo: '{hours} hours ago',
        daysAgo: '{days} days ago'
      }
    },
    
    // System notices
    notices: {
      title: 'System Notices',
      viewAll: 'View All',
      noNotices: 'No notices',
      types: {
        system: 'System Notification',
        maintenance: 'Maintenance Notice',
        update: 'Update Notification',
        security: 'Security Alert'
      },
      priority: {
        high: 'Important',
        medium: 'Normal',
        low: 'Low'
      }
    },
    
    // Todo items
    todos: {
      title: 'Todo Items',
      viewAll: 'View All',
      noTodos: 'No todos',
      addTodo: 'Add Todo',
      markComplete: 'Mark Complete',
      priority: {
        high: 'High Priority',
        medium: 'Medium Priority',
        low: 'Low Priority'
      },
      status: {
        pending: 'Pending',
        inProgress: 'In Progress',
        completed: 'Completed'
      }
    },
    
    // Weather information
    weather: {
      title: 'Weather Information',
      location: 'Current Location',
      temperature: 'Temperature',
      humidity: 'Humidity',
      windSpeed: 'Wind Speed',
      conditions: {
        sunny: 'Sunny',
        cloudy: 'Cloudy',
        rainy: 'Rainy',
        snowy: 'Snowy',
        foggy: 'Foggy'
      }
    },
    
    // System status
    systemStatus: {
      title: 'System Status',
      services: {
        database: 'Database',
        cache: 'Cache Service',
        storage: 'Storage Service',
        email: 'Email Service',
        backup: 'Backup Service'
      },
      status: {
        online: 'Online',
        offline: 'Offline',
        warning: 'Warning',
        error: 'Error'
      },
      uptime: 'Uptime',
      lastCheck: 'Last Check'
    }
  }
}