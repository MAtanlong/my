<template>
  <div class="system-settings-page">
    <!-- 页面标题和操作栏 -->
    <div class="page-header flex items-center justify-between mb-6">
      <div>
        <h1 class="text-2xl font-bold text-gray-800 mb-2">{{ t('systemSettings.title') }}</h1>
        <p class="text-gray-600">{{ t('systemSettings.description') }}</p>
      </div>
      <div class="flex items-center space-x-3">
        <el-button type="success" :icon="Download" @click="exportSettings">
          {{ t('systemSettings.actions.exportConfig') }}
        </el-button>
        <el-button type="warning" :icon="Upload" @click="importSettings">
          {{ t('systemSettings.actions.importConfig') }}
        </el-button>
        <el-button type="primary" :icon="Check" @click="saveAllSettings" :loading="saveLoading">
          {{ t('systemSettings.actions.saveAllSettings') }}
        </el-button>
      </div>
    </div>

    <!-- 设置选项卡 -->
    <div class="settings-tabs bg-white rounded-lg shadow-sm border border-gray-200">
      <el-tabs v-model="activeTab" type="border-card">
        <!-- 基础设置 -->
        <el-tab-pane :label="t('systemSettings.tabs.basic')" name="basic">
          <div class="tab-content p-6">
            <el-form :model="basicSettings" label-width="150px" class="settings-form">
              <div class="form-section">
                <h3 class="section-title">{{ t('systemSettings.basic.websiteInfo.title') }}</h3>
                <el-form-item :label="t('systemSettings.basic.websiteInfo.siteName')">
                  <el-input v-model="basicSettings.siteName" :placeholder="t('systemSettings.basic.websiteInfo.siteNamePlaceholder')" style="width: 400px" />
                </el-form-item>
                <el-form-item :label="t('systemSettings.basic.websiteInfo.siteTitle')">
                  <el-input v-model="basicSettings.siteTitle" :placeholder="t('systemSettings.basic.websiteInfo.siteTitlePlaceholder')" style="width: 400px" />
                </el-form-item>
                <el-form-item :label="t('systemSettings.basic.websiteInfo.siteDescription')">
                  <el-input
                    v-model="basicSettings.siteDescription"
                    type="textarea"
                    :rows="3"
                    :placeholder="t('systemSettings.basic.websiteInfo.siteDescriptionPlaceholder')"
                    style="width: 400px"
                  />
                </el-form-item>
                <el-form-item :label="t('systemSettings.basic.websiteInfo.siteKeywords')">
                  <el-input v-model="basicSettings.siteKeywords" :placeholder="t('systemSettings.basic.websiteInfo.siteKeywordsPlaceholder')" style="width: 400px" />
                </el-form-item>
                <el-form-item :label="t('systemSettings.basic.websiteInfo.siteLogo')">
                  <div class="flex items-center space-x-4">
                    <el-upload
                      class="logo-uploader"
                      action="#"
                      :show-file-list="false"
                      :before-upload="beforeLogoUpload"
                    >
                      <img v-if="basicSettings.siteLogo" :src="basicSettings.siteLogo" class="logo-preview" />
                      <el-icon v-else class="logo-uploader-icon"><Plus /></el-icon>
                    </el-upload>
                    <div class="text-sm text-gray-500">
                      <p>{{ t('systemSettings.basic.websiteInfo.logoSizeHint') }}</p>
                      <p>{{ t('systemSettings.basic.websiteInfo.logoFormatHint') }}</p>
                    </div>
                  </div>
                </el-form-item>
              </div>

              <div class="form-section">
                <h3 class="section-title">{{ t('systemSettings.basic.contactInfo.title') }}</h3>
                <el-form-item :label="t('systemSettings.basic.contactInfo.phone')">
                  <el-input v-model="basicSettings.contactPhone" :placeholder="t('systemSettings.basic.contactInfo.phonePlaceholder')" style="width: 300px" />
                </el-form-item>
                <el-form-item :label="t('systemSettings.basic.contactInfo.email')">
                  <el-input v-model="basicSettings.contactEmail" :placeholder="t('systemSettings.basic.contactInfo.emailPlaceholder')" style="width: 300px" />
                </el-form-item>
                <el-form-item :label="t('systemSettings.basic.contactInfo.address')">
                  <el-input v-model="basicSettings.companyAddress" :placeholder="t('systemSettings.basic.contactInfo.addressPlaceholder')" style="width: 400px" />
                </el-form-item>
              </div>

              <div class="form-section">
                <h3 class="section-title">{{ t('systemSettings.basic.systemConfig.title') }}</h3>
                <el-form-item :label="t('systemSettings.basic.systemConfig.timezone')">
                  <el-select v-model="basicSettings.timezone" :placeholder="t('systemSettings.basic.systemConfig.timezonePlaceholder')" style="width: 300px">
                    <el-option :label="t('systemSettings.basic.systemConfig.timezones.beijing')" value="Asia/Shanghai" />
                    <el-option :label="t('systemSettings.basic.systemConfig.timezones.tokyo')" value="Asia/Tokyo" />
                    <el-option :label="t('systemSettings.basic.systemConfig.timezones.newYork')" value="America/New_York" />
                    <el-option :label="t('systemSettings.basic.systemConfig.timezones.london')" value="Europe/London" />
                  </el-select>
                </el-form-item>
                <el-form-item :label="t('systemSettings.basic.systemConfig.defaultLanguage')">
                  <el-select v-model="basicSettings.defaultLanguage" :placeholder="t('systemSettings.basic.systemConfig.defaultLanguagePlaceholder')" style="width: 300px">
                    <el-option :label="t('systemSettings.basic.systemConfig.languages.zhCN')" value="zh-CN" />
                    <el-option :label="t('systemSettings.basic.systemConfig.languages.zhTW')" value="zh-TW" />
                    <el-option :label="t('systemSettings.basic.systemConfig.languages.enUS')" value="en-US" />
                    <el-option :label="t('systemSettings.basic.systemConfig.languages.jaJP')" value="ja-JP" />
                  </el-select>
                </el-form-item>
                <el-form-item :label="t('systemSettings.basic.systemConfig.pageSize')">
                  <el-input-number v-model="basicSettings.pageSize" :min="10" :max="100" :step="10" style="width: 200px" />
                </el-form-item>
              </div>
            </el-form>
          </div>
        </el-tab-pane>

        <!-- 安全设置 -->
        <el-tab-pane :label="t('systemSettings.tabs.security')" name="security">
          <div class="tab-content p-6">
            <el-form :model="securitySettings" label-width="150px" class="settings-form">
              <div class="form-section">
                <h3 class="section-title">{{ t('systemSettings.security.loginSecurity.title') }}</h3>
                <el-form-item :label="t('systemSettings.security.loginSecurity.passwordComplexity')">
                  <el-checkbox-group v-model="securitySettings.passwordComplexity">
                    <el-checkbox label="uppercase">{{ t('systemSettings.security.loginSecurity.includeUppercase') }}</el-checkbox>
                    <el-checkbox label="lowercase">{{ t('systemSettings.security.loginSecurity.includeLowercase') }}</el-checkbox>
                    <el-checkbox label="number">{{ t('systemSettings.security.loginSecurity.includeNumber') }}</el-checkbox>
                    <el-checkbox label="special">{{ t('systemSettings.security.loginSecurity.includeSpecial') }}</el-checkbox>
                  </el-checkbox-group>
                </el-form-item>
                <el-form-item :label="t('systemSettings.security.loginSecurity.minPasswordLength')">
                  <el-input-number v-model="securitySettings.minPasswordLength" :min="6" :max="20" style="width: 200px" />
                </el-form-item>
                <el-form-item :label="t('systemSettings.security.loginSecurity.passwordExpireDays')">
                  <div class="flex items-center space-x-2">
                    <el-input-number v-model="securitySettings.passwordExpireDays" :min="0" :max="365" style="width: 150px" />
                    <span class="text-gray-500">{{ t('systemSettings.security.loginSecurity.daysNeverExpire') }}</span>
                  </div>
                </el-form-item>
                <el-form-item :label="t('systemSettings.security.loginSecurity.loginFailureLockout')">
                  <div class="space-y-2">
                    <div class="flex items-center space-x-2">
                      <span class="text-sm">{{ t('systemSettings.security.loginSecurity.consecutiveFailures') }}</span>
                      <el-input-number v-model="securitySettings.maxLoginAttempts" :min="3" :max="10" style="width: 120px" />
                      <span class="text-sm">{{ t('systemSettings.security.loginSecurity.timesLockAccount') }}</span>
                    </div>
                    <div class="flex items-center space-x-2">
                      <span class="text-sm">{{ t('systemSettings.security.loginSecurity.lockoutTime') }}</span>
                      <el-input-number v-model="securitySettings.lockoutDuration" :min="5" :max="1440" style="width: 120px" />
                      <span class="text-sm">{{ t('systemSettings.security.loginSecurity.minutes') }}</span>
                    </div>
                  </div>
                </el-form-item>
                <el-form-item :label="t('systemSettings.security.loginSecurity.requireTwoFactor')">
                  <el-switch
                    v-model="securitySettings.requireTwoFactor"
                    :active-text="t('systemSettings.security.loginSecurity.enable')"
                    :inactive-text="t('systemSettings.security.loginSecurity.disable')"
                  />
                </el-form-item>
              </div>

              <div class="form-section">
                <h3 class="section-title">{{ t('systemSettings.security.sessionManagement.title') }}</h3>
                <el-form-item :label="t('systemSettings.security.sessionManagement.sessionTimeout')">
                  <div class="flex items-center space-x-2">
                    <el-input-number v-model="securitySettings.sessionTimeout" :min="30" :max="1440" style="width: 150px" />
                    <span class="text-gray-500">{{ t('systemSettings.security.sessionManagement.minutes') }}</span>
                  </div>
                </el-form-item>
                <el-form-item :label="t('systemSettings.security.sessionManagement.maxSessions')">
                  <div class="flex items-center space-x-2">
                    <el-input-number v-model="securitySettings.maxSessions" :min="1" :max="10" style="width: 150px" />
                    <span class="text-gray-500">{{ t('systemSettings.security.sessionManagement.sessions') }}</span>
                  </div>
                </el-form-item>
                <el-form-item :label="t('systemSettings.security.sessionManagement.rememberMe')">
                  <div class="flex items-center space-x-2">
                    <el-switch
                      v-model="securitySettings.allowRememberMe"
                      :active-text="t('systemSettings.security.sessionManagement.allow')"
                      :inactive-text="t('systemSettings.security.sessionManagement.forbid')"
                    />
                    <span class="text-gray-500 ml-4">{{ t('systemSettings.security.sessionManagement.validityPeriod') }}</span>
                    <el-input-number v-model="securitySettings.rememberMeDays" :min="1" :max="30" style="width: 120px" />
                    <span class="text-gray-500">{{ t('systemSettings.security.sessionManagement.days') }}</span>
                  </div>
                </el-form-item>
              </div>

              <div class="form-section">
                <h3 class="section-title">{{ t('systemSettings.security.ipAccessControl.title') }}</h3>
                <el-form-item :label="t('systemSettings.security.ipAccessControl.ipWhitelist')">
                  <div class="space-y-2">
                    <el-switch
                      v-model="securitySettings.enableIpWhitelist"
                      :active-text="t('systemSettings.security.ipAccessControl.enable')"
                      :inactive-text="t('systemSettings.security.ipAccessControl.disable')"
                    />
                    <el-input
                      v-if="securitySettings.enableIpWhitelist"
                      v-model="securitySettings.ipWhitelist"
                      type="textarea"
                      :rows="3"
                      :placeholder="t('systemSettings.security.ipAccessControl.whitelistPlaceholder')"
                      style="width: 400px"
                    />
                  </div>
                </el-form-item>
                <el-form-item :label="t('systemSettings.security.ipAccessControl.ipBlacklist')">
                  <div class="space-y-2">
                    <el-switch
                      v-model="securitySettings.enableIpBlacklist"
                      :active-text="t('systemSettings.security.ipAccessControl.enable')"
                      :inactive-text="t('systemSettings.security.ipAccessControl.disable')"
                    />
                    <el-input
                      v-if="securitySettings.enableIpBlacklist"
                      v-model="securitySettings.ipBlacklist"
                      type="textarea"
                      :rows="3"
                      :placeholder="t('systemSettings.security.ipAccessControl.blacklistPlaceholder')"
                      style="width: 400px"
                    />
                  </div>
                </el-form-item>
              </div>
            </el-form>
          </div>
        </el-tab-pane>

        <!-- 邮件设置 -->
        <el-tab-pane :label="t('systemSettings.tabs.email')" name="email">
          <div class="tab-content p-6">
            <el-form :model="emailSettings" label-width="150px" class="settings-form">
              <div class="form-section">
                <h3 class="section-title">{{ t('systemSettings.email.smtpConfig.title') }}</h3>
                <el-form-item :label="t('systemSettings.email.smtpConfig.smtpHost')">
                  <el-input v-model="emailSettings.smtpHost" :placeholder="t('systemSettings.email.smtpConfig.smtpHostPlaceholder')" style="width: 300px" />
                </el-form-item>
                <el-form-item :label="t('systemSettings.email.smtpConfig.smtpPort')">
                  <el-input-number v-model="emailSettings.smtpPort" :min="1" :max="65535" style="width: 200px" />
                </el-form-item>
                <el-form-item :label="t('systemSettings.email.smtpConfig.encryption')">
                  <el-select v-model="emailSettings.smtpEncryption" :placeholder="t('systemSettings.email.smtpConfig.encryptionPlaceholder')" style="width: 200px">
                    <el-option :label="t('systemSettings.email.smtpConfig.encryptionTypes.none')" value="none" />
                    <el-option :label="t('systemSettings.email.smtpConfig.encryptionTypes.ssl')" value="ssl" />
                    <el-option :label="t('systemSettings.email.smtpConfig.encryptionTypes.tls')" value="tls" />
                  </el-select>
                </el-form-item>
                <el-form-item :label="t('systemSettings.email.smtpConfig.username')">
                  <el-input v-model="emailSettings.smtpUsername" :placeholder="t('systemSettings.email.smtpConfig.usernamePlaceholder')" style="width: 300px" />
                </el-form-item>
                <el-form-item :label="t('systemSettings.email.smtpConfig.password')">
                  <el-input
                    v-model="emailSettings.smtpPassword"
                    type="password"
                    :placeholder="t('systemSettings.email.smtpConfig.passwordPlaceholder')"
                    show-password
                    style="width: 300px"
                  />
                </el-form-item>
                <el-form-item :label="t('systemSettings.email.smtpConfig.fromEmail')">
                  <el-input v-model="emailSettings.fromEmail" :placeholder="t('systemSettings.email.smtpConfig.fromEmailPlaceholder')" style="width: 300px" />
                </el-form-item>
                <el-form-item :label="t('systemSettings.email.smtpConfig.fromName')">
                  <el-input v-model="emailSettings.fromName" :placeholder="t('systemSettings.email.smtpConfig.fromNamePlaceholder')" style="width: 300px" />
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="testEmailConnection" :loading="testingEmail">
                    {{ t('systemSettings.email.smtpConfig.testConnection') }}
                  </el-button>
                </el-form-item>
              </div>

              <div class="form-section">
                <h3 class="section-title">{{ t('systemSettings.email.emailTemplates.title') }}</h3>
                <el-form-item :label="t('systemSettings.email.emailTemplates.welcomeEmail')">
                  <el-switch
                    v-model="emailSettings.enableWelcomeEmail"
                    :active-text="t('systemSettings.email.emailTemplates.enable')"
                    :inactive-text="t('systemSettings.email.emailTemplates.disable')"
                  />
                </el-form-item>
                <el-form-item :label="t('systemSettings.email.emailTemplates.passwordResetEmail')">
                  <el-switch
                    v-model="emailSettings.enablePasswordResetEmail"
                    :active-text="t('systemSettings.email.emailTemplates.enable')"
                    :inactive-text="t('systemSettings.email.emailTemplates.disable')"
                  />
                </el-form-item>
                <el-form-item :label="t('systemSettings.email.emailTemplates.systemNotificationEmail')">
                  <el-switch
                    v-model="emailSettings.enableSystemNotificationEmail"
                    :active-text="t('systemSettings.email.emailTemplates.enable')"
                    :inactive-text="t('systemSettings.email.emailTemplates.disable')"
                  />
                </el-form-item>
              </div>
            </el-form>
          </div>
        </el-tab-pane>

        <!-- 存储设置 -->
        <el-tab-pane :label="t('systemSettings.tabs.storage')" name="storage">
          <div class="tab-content p-6">
            <el-form :model="storageSettings" label-width="150px" class="settings-form">
              <div class="form-section">
                <h3 class="section-title">{{ t('systemSettings.storage.fileUpload.title') }}</h3>
                <el-form-item :label="t('systemSettings.storage.fileUpload.maxFileSize')">
                  <div class="flex items-center space-x-2">
                    <el-input-number v-model="storageSettings.maxFileSize" :min="1" :max="1024" style="width: 150px" />
                    <span class="text-gray-500">{{ t('systemSettings.storage.fileUpload.mb') }}</span>
                  </div>
                </el-form-item>
                <el-form-item :label="t('systemSettings.storage.fileUpload.allowedFileTypes')">
                  <el-checkbox-group v-model="storageSettings.allowedFileTypes">
                    <el-checkbox label="image">{{ t('systemSettings.storage.fileUpload.fileTypes.image') }}</el-checkbox>
                    <el-checkbox label="document">{{ t('systemSettings.storage.fileUpload.fileTypes.document') }}</el-checkbox>
                    <el-checkbox label="archive">{{ t('systemSettings.storage.fileUpload.fileTypes.archive') }}</el-checkbox>
                    <el-checkbox label="video">{{ t('systemSettings.storage.fileUpload.fileTypes.video') }}</el-checkbox>
                    <el-checkbox label="audio">{{ t('systemSettings.storage.fileUpload.fileTypes.audio') }}</el-checkbox>
                  </el-checkbox-group>
                </el-form-item>
                <el-form-item :label="t('systemSettings.storage.fileUpload.storagePath')">
                  <el-input v-model="storageSettings.uploadPath" :placeholder="t('systemSettings.storage.fileUpload.storagePathPlaceholder')" style="width: 400px" />
                </el-form-item>
              </div>

              <div class="form-section">
                <h3 class="section-title">{{ t('systemSettings.storage.cloudStorage.title') }}</h3>
                <el-form-item :label="t('systemSettings.storage.cloudStorage.storageType')">
                  <el-select v-model="storageSettings.storageType" :placeholder="t('systemSettings.storage.cloudStorage.storageTypePlaceholder')" style="width: 200px">
                    <el-option :label="t('systemSettings.storage.cloudStorage.storageTypes.local')" value="local" />
                    <el-option :label="t('systemSettings.storage.cloudStorage.storageTypes.aliyun')" value="aliyun" />
                    <el-option :label="t('systemSettings.storage.cloudStorage.storageTypes.tencent')" value="tencent" />
                    <el-option :label="t('systemSettings.storage.cloudStorage.storageTypes.qiniu')" value="qiniu" />
                    <el-option :label="t('systemSettings.storage.cloudStorage.storageTypes.aws')" value="aws" />
                  </el-select>
                </el-form-item>
                <template v-if="storageSettings.storageType !== 'local'">
                  <el-form-item :label="t('systemSettings.storage.cloudStorage.accessKey')">
                    <el-input v-model="storageSettings.accessKey" :placeholder="t('systemSettings.storage.cloudStorage.accessKeyPlaceholder')" style="width: 400px" />
                  </el-form-item>
                  <el-form-item :label="t('systemSettings.storage.cloudStorage.secretKey')">
                    <el-input
                      v-model="storageSettings.secretKey"
                      type="password"
                      :placeholder="t('systemSettings.storage.cloudStorage.secretKeyPlaceholder')"
                      show-password
                      style="width: 400px"
                    />
                  </el-form-item>
                  <el-form-item :label="t('systemSettings.storage.cloudStorage.bucketName')">
                    <el-input v-model="storageSettings.bucketName" :placeholder="t('systemSettings.storage.cloudStorage.bucketNamePlaceholder')" style="width: 300px" />
                  </el-form-item>
                  <el-form-item :label="t('systemSettings.storage.cloudStorage.domain')">
                    <el-input v-model="storageSettings.domain" :placeholder="t('systemSettings.storage.cloudStorage.domainPlaceholder')" style="width: 400px" />
                  </el-form-item>
                </template>
              </div>

              <div class="form-section">
                <h3 class="section-title">{{ t('systemSettings.storage.cacheSettings.title') }}</h3>
                <el-form-item :label="t('systemSettings.storage.cacheSettings.cacheType')">
                  <el-select v-model="storageSettings.cacheType" :placeholder="t('systemSettings.storage.cacheSettings.cacheTypePlaceholder')" style="width: 200px">
                    <el-option :label="t('systemSettings.storage.cacheSettings.cacheTypes.memory')" value="memory" />
                    <el-option :label="t('systemSettings.storage.cacheSettings.cacheTypes.redis')" value="redis" />
                    <el-option :label="t('systemSettings.storage.cacheSettings.cacheTypes.memcached')" value="memcached" />
                  </el-select>
                </el-form-item>
                <template v-if="storageSettings.cacheType === 'redis'">
                  <el-form-item :label="t('systemSettings.storage.cacheSettings.redisHost')">
                    <el-input v-model="storageSettings.redisHost" :placeholder="t('systemSettings.storage.cacheSettings.redisHostPlaceholder')" style="width: 300px" />
                  </el-form-item>
                  <el-form-item :label="t('systemSettings.storage.cacheSettings.redisPort')">
                    <el-input-number v-model="storageSettings.redisPort" :min="1" :max="65535" style="width: 200px" />
                  </el-form-item>
                  <el-form-item :label="t('systemSettings.storage.cacheSettings.redisPassword')">
                    <el-input
                      v-model="storageSettings.redisPassword"
                      type="password"
                      :placeholder="t('systemSettings.storage.cacheSettings.redisPasswordPlaceholder')"
                      show-password
                      style="width: 300px"
                    />
                  </el-form-item>
                </template>
                <el-form-item :label="t('systemSettings.storage.cacheSettings.expireTime')">
                  <div class="flex items-center space-x-2">
                    <el-input-number v-model="storageSettings.cacheExpire" :min="60" :max="86400" style="width: 150px" />
                    <span class="text-gray-500">{{ t('systemSettings.storage.cacheSettings.seconds') }}</span>
                  </div>
                </el-form-item>
              </div>
            </el-form>
          </div>
        </el-tab-pane>

        <!-- 日志设置 -->
        <el-tab-pane :label="t('systemSettings.tabs.logs')" name="logs">
          <div class="tab-content p-6">
            <el-form :model="logSettings" label-width="150px" class="settings-form">
              <div class="form-section">
                <h3 class="section-title">{{ t('systemSettings.logs.logConfig.title') }}</h3>
                <el-form-item :label="t('systemSettings.logs.logConfig.logLevel')">
                  <el-select v-model="logSettings.logLevel" :placeholder="t('systemSettings.logs.logConfig.logLevelPlaceholder')" style="width: 200px">
                    <el-option label="DEBUG" value="debug" />
                    <el-option label="INFO" value="info" />
                    <el-option label="WARN" value="warn" />
                    <el-option label="ERROR" value="error" />
                  </el-select>
                </el-form-item>
                <el-form-item :label="t('systemSettings.logs.logConfig.retentionDays')">
                  <div class="flex items-center space-x-2">
                    <el-input-number v-model="logSettings.logRetentionDays" :min="1" :max="365" style="width: 150px" />
                    <span class="text-gray-500">{{ t('systemSettings.logs.logConfig.days') }}</span>
                  </div>
                </el-form-item>
                <el-form-item :label="t('systemSettings.logs.logConfig.maxFileSize')">
                  <div class="flex items-center space-x-2">
                    <el-input-number v-model="logSettings.maxLogFileSize" :min="1" :max="1024" style="width: 150px" />
                    <span class="text-gray-500">{{ t('systemSettings.logs.logConfig.mb') }}</span>
                  </div>
                </el-form-item>
              </div>

              <div class="form-section">
                <h3 class="section-title">{{ t('systemSettings.logs.auditLog.title') }}</h3>
                <el-form-item :label="t('systemSettings.logs.auditLog.enableAuditLog')">
                  <el-switch
                    v-model="logSettings.enableAuditLog"
                    :active-text="t('systemSettings.logs.auditLog.enable')"
                    :inactive-text="t('systemSettings.logs.auditLog.disable')"
                  />
                </el-form-item>
                <el-form-item :label="t('systemSettings.logs.auditLog.logLogin')">
                  <el-switch
                    v-model="logSettings.logLogin"
                    :active-text="t('systemSettings.logs.auditLog.enable')"
                    :inactive-text="t('systemSettings.logs.auditLog.disable')"
                  />
                </el-form-item>
                <el-form-item :label="t('systemSettings.logs.auditLog.logOperation')">
                  <el-switch
                    v-model="logSettings.logOperation"
                    :active-text="t('systemSettings.logs.auditLog.enable')"
                    :inactive-text="t('systemSettings.logs.auditLog.disable')"
                  />
                </el-form-item>
                <el-form-item :label="t('systemSettings.logs.auditLog.logApiCall')">
                  <el-switch
                    v-model="logSettings.logApiCall"
                    :active-text="t('systemSettings.logs.auditLog.enable')"
                    :inactive-text="t('systemSettings.logs.auditLog.disable')"
                  />
                </el-form-item>
              </div>

              <div class="form-section">
                <h3 class="section-title">{{ t('systemSettings.logs.logCleanup.title') }}</h3>
                <el-form-item :label="t('systemSettings.logs.logCleanup.autoCleanup')">
                  <el-switch
                    v-model="logSettings.autoCleanup"
                    :active-text="t('systemSettings.logs.logCleanup.enable')"
                    :inactive-text="t('systemSettings.logs.logCleanup.disable')"
                  />
                </el-form-item>
                <el-form-item :label="t('systemSettings.logs.logCleanup.cleanupTime')">
                  <el-time-picker
                    v-model="logSettings.cleanupTime"
                    format="HH:mm"
                    :placeholder="t('systemSettings.logs.logCleanup.cleanupTimePlaceholder')"
                    style="width: 200px"
                  />
                </el-form-item>
                <el-form-item>
                  <el-button type="danger" @click="cleanupLogs" :loading="cleaningLogs">
                    {{ t('systemSettings.logs.logCleanup.cleanupNow') }}
                  </el-button>
                </el-form-item>
              </div>
            </el-form>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useI18n } from 'vue-i18n'
import {
  Plus,
  Download,
  Upload,
  Check,
  Setting
} from '@element-plus/icons-vue'

const { t } = useI18n()

// 响应式数据
const activeTab = ref('basic')
const saveLoading = ref(false)
const testingEmail = ref(false)
const cleaningLogs = ref(false)

// 基础设置
const basicSettings = reactive({
  siteName: '权限管理系统',
  siteTitle: '企业级权限管理平台',
  siteDescription: '基于Vue3和Spring Boot的企业级权限管理系统，提供完整的用户、角色、权限管理功能',
  siteKeywords: '权限管理,用户管理,角色管理,Vue3,Spring Boot',
  siteLogo: '',
  contactPhone: '400-123-4567',
  contactEmail: 'contact@company.com',
  companyAddress: '北京市朝阳区xxx大厦',
  timezone: 'Asia/Shanghai',
  defaultLanguage: 'zh-CN',
  pageSize: 20
})

// 安全设置
const securitySettings = reactive({
  passwordComplexity: ['lowercase', 'number'],
  minPasswordLength: 8,
  passwordExpireDays: 90,
  maxLoginAttempts: 5,
  lockoutDuration: 30,
  requireTwoFactor: false,
  sessionTimeout: 120,
  maxSessions: 3,
  allowRememberMe: true,
  rememberMeDays: 7,
  enableIpWhitelist: false,
  ipWhitelist: '',
  enableIpBlacklist: false,
  ipBlacklist: ''
})

// 邮件设置
const emailSettings = reactive({
  smtpHost: 'smtp.qq.com',
  smtpPort: 587,
  smtpEncryption: 'tls',
  smtpUsername: '',
  smtpPassword: '',
  fromEmail: 'noreply@company.com',
  fromName: '权限管理系统',
  enableWelcomeEmail: true,
  enablePasswordResetEmail: true,
  enableSystemNotificationEmail: true
})

// 存储设置
const storageSettings = reactive({
  maxFileSize: 10,
  allowedFileTypes: ['image', 'document'],
  uploadPath: '/uploads',
  storageType: 'local',
  accessKey: '',
  secretKey: '',
  bucketName: '',
  domain: '',
  cacheType: 'memory',
  redisHost: 'localhost',
  redisPort: 6379,
  redisPassword: '',
  cacheExpire: 3600
})

// 日志设置
const logSettings = reactive({
  logLevel: 'info',
  logRetentionDays: 30,
  maxLogFileSize: 100,
  enableAuditLog: true,
  logLogin: true,
  logOperation: true,
  logApiCall: false,
  autoCleanup: true,
  cleanupTime: new Date(2024, 0, 1, 2, 0) // 凌晨2点
})

// 方法
const beforeLogoUpload = (file: File) => {
  const isImage = file.type.startsWith('image/')
  const isLt2M = file.size / 1024 / 1024 < 2

  if (!isImage) {
    ElMessage.error('只能上传图片文件!')
    return false
  }
  if (!isLt2M) {
    ElMessage.error('图片大小不能超过 2MB!')
    return false
  }

  // 模拟上传
  const reader = new FileReader()
  reader.onload = (e) => {
    basicSettings.siteLogo = e.target?.result as string
  }
  reader.readAsDataURL(file)
  
  return false // 阻止自动上传
}

const testEmailConnection = async () => {
  testingEmail.value = true
  try {
    // 模拟测试邮件连接
    await new Promise(resolve => setTimeout(resolve, 2000))
    ElMessage.success('邮件服务器连接测试成功')
  } catch (error) {
    ElMessage.error('邮件服务器连接测试失败')
  } finally {
    testingEmail.value = false
  }
}

const cleanupLogs = async () => {
  try {
    await ElMessageBox.confirm(
      '确定要清理历史日志吗？此操作不可恢复。',
      '清理确认',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    
    cleaningLogs.value = true
    // 模拟清理日志
    await new Promise(resolve => setTimeout(resolve, 3000))
    ElMessage.success('日志清理完成')
  } catch {
    ElMessage.info('已取消清理')
  } finally {
    cleaningLogs.value = false
  }
}

const saveAllSettings = async () => {
  saveLoading.value = true
  try {
    // 模拟保存所有设置
    await new Promise(resolve => setTimeout(resolve, 2000))
    ElMessage.success('所有设置保存成功')
  } catch (error) {
    ElMessage.error('设置保存失败')
  } finally {
    saveLoading.value = false
  }
}

const exportSettings = () => {
  const settings = {
    basic: basicSettings,
    security: securitySettings,
    email: emailSettings,
    storage: storageSettings,
    logs: logSettings
  }
  
  const dataStr = JSON.stringify(settings, null, 2)
  const dataBlob = new Blob([dataStr], { type: 'application/json' })
  const url = URL.createObjectURL(dataBlob)
  
  const link = document.createElement('a')
  link.href = url
  link.download = `system-settings-${new Date().toISOString().split('T')[0]}.json`
  link.click()
  
  URL.revokeObjectURL(url)
  ElMessage.success('配置导出成功')
}

const importSettings = () => {
  const input = document.createElement('input')
  input.type = 'file'
  input.accept = '.json'
  input.onchange = (e) => {
    const file = (e.target as HTMLInputElement).files?.[0]
    if (file) {
      const reader = new FileReader()
      reader.onload = (e) => {
        try {
          const settings = JSON.parse(e.target?.result as string)
          Object.assign(basicSettings, settings.basic || {})
          Object.assign(securitySettings, settings.security || {})
          Object.assign(emailSettings, settings.email || {})
          Object.assign(storageSettings, settings.storage || {})
          Object.assign(logSettings, settings.logs || {})
          ElMessage.success('配置导入成功')
        } catch (error) {
          ElMessage.error('配置文件格式错误')
        }
      }
      reader.readAsText(file)
    }
  }
  input.click()
}

// 组件挂载时加载设置
onMounted(() => {
  // 这里可以从API加载设置
})
</script>

<style scoped>
.system-settings-page {
  animation: fadeIn 0.6s ease-out;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.settings-tabs {
  min-height: 600px;
}

.tab-content {
  max-height: 70vh;
  overflow-y: auto;
}

.settings-form {
  max-width: 800px;
}

.form-section {
  margin-bottom: 40px;
  padding-bottom: 30px;
  border-bottom: 1px solid #f0f0f0;
}

.form-section:last-child {
  border-bottom: none;
}

.section-title {
  font-size: 16px;
  font-weight: 600;
  color: #333;
  margin-bottom: 20px;
  padding-bottom: 8px;
  border-bottom: 2px solid #409eff;
  display: inline-block;
}

.logo-uploader {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: border-color 0.3s;
}

.logo-uploader:hover {
  border-color: #409eff;
}

.logo-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 60px;
  text-align: center;
  line-height: 60px;
}

.logo-preview {
  width: 120px;
  height: 60px;
  object-fit: contain;
  display: block;
}

.el-tabs :deep(.el-tab-pane) {
  padding: 0;
}

.el-form-item {
  margin-bottom: 20px;
}

.el-checkbox-group {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.el-checkbox-group .el-checkbox {
  margin-right: 0;
}
</style>