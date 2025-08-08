<template>
  <div class="profile-page">
    <!-- 页面标题 -->
    <div class="page-header mb-6">
      <h1 class="text-2xl font-bold text-gray-800 mb-2">{{ t('profile.title') }}</h1>
      <p class="text-gray-600">{{ t('profile.description') }}</p>
    </div>

    <!-- 个人信息卡片 -->
    <div class="profile-card bg-white rounded-lg shadow-sm border border-gray-200 p-6 mb-6">
      <div class="flex items-center space-x-6">
        <!-- 头像 -->
        <div class="avatar-section">
          <el-upload
            class="avatar-uploader"
            action="#"
            :show-file-list="false"
            :before-upload="beforeAvatarUpload"
          >
            <img v-if="userInfo.avatar" :src="userInfo.avatar" class="avatar" />
            <div v-else class="avatar-placeholder">
              <el-icon class="avatar-uploader-icon"><User /></el-icon>
            </div>
          </el-upload>
          <p class="text-sm text-gray-500 mt-2 text-center">{{ t('profile.changeAvatar') }}</p>
        </div>
        
        <!-- 基本信息 -->
        <div class="user-info flex-1">
          <h2 class="text-xl font-semibold text-gray-800 mb-2">{{ userInfo.realName }}</h2>
          <div class="info-grid grid grid-cols-2 gap-4 text-sm">
            <div class="info-item">
              <span class="label text-gray-500">{{ t('profile.username') }}：</span>
              <span class="value">{{ userInfo.username }}</span>
            </div>
            <div class="info-item">
              <span class="label text-gray-500">{{ t('profile.email') }}：</span>
              <span class="value">{{ userInfo.email }}</span>
            </div>
            <div class="info-item">
              <span class="label text-gray-500">{{ t('profile.phone') }}：</span>
              <span class="value">{{ userInfo.phone }}</span>
            </div>
            <div class="info-item">
              <span class="label text-gray-500">{{ t('profile.department') }}：</span>
              <span class="value">{{ userInfo.department }}</span>
            </div>
            <div class="info-item">
              <span class="label text-gray-500">{{ t('profile.role') }}：</span>
              <el-tag type="primary" size="small">{{ userInfo.role }}</el-tag>
            </div>
            <div class="info-item">
              <span class="label text-gray-500">{{ t('profile.lastLogin') }}：</span>
              <span class="value">{{ userInfo.lastLoginTime }}</span>
            </div>
          </div>
        </div>
        
        <!-- 操作按钮 -->
        <div class="actions">
          <el-button type="primary" @click="showEditProfile = true">
            {{ t('profile.editProfile') }}
          </el-button>
        </div>
      </div>
    </div>

    <!-- 功能选项卡 -->
    <div class="profile-tabs bg-white rounded-lg shadow-sm border border-gray-200">
      <el-tabs v-model="activeTab" type="border-card">
        <!-- 个人设置 -->
        <el-tab-pane :label="t('profile.tabs.settings')" name="settings">
          <div class="tab-content p-6">
            <el-form :model="profileSettings" label-width="120px" class="settings-form">
              <div class="form-section">
                <h3 class="section-title">{{ t('profile.settings.interface') }}</h3>
                <el-form-item :label="t('profile.settings.theme')">
                  <el-select v-model="profileSettings.theme" :placeholder="t('profile.settings.selectTheme')" style="width: 200px">
                    <el-option :label="t('profile.settings.lightMode')" value="light" />
                    <el-option :label="t('profile.settings.darkMode')" value="dark" />
                    <el-option :label="t('profile.settings.autoMode')" value="auto" />
                  </el-select>
                </el-form-item>
                <el-form-item :label="t('profile.settings.language')">
                  <el-select v-model="profileSettings.language" :placeholder="t('profile.settings.selectLanguage')" style="width: 200px">
                    <el-option :label="t('profile.settings.simplifiedChinese')" value="zh-CN" />
                    <el-option :label="t('profile.settings.traditionalChinese')" value="zh-TW" />
                    <el-option label="English" value="en-US" />
                  </el-select>
                </el-form-item>
                <el-form-item :label="t('profile.settings.sidebarCollapse')">
                  <el-switch
                    v-model="profileSettings.sidebarCollapsed"
                    :active-text="t('profile.settings.defaultCollapsed')"
                    :inactive-text="t('profile.settings.defaultExpanded')"
                  />
                </el-form-item>
                <el-form-item :label="t('profile.settings.breadcrumbNav')">
                  <el-switch
                    v-model="profileSettings.showBreadcrumb"
                    :active-text="t('common.show')"
                    :inactive-text="t('common.hide')"
                  />
                </el-form-item>
              </div>

              <div class="form-section">
                <h3 class="section-title">{{ t('profile.settings.notifications') }}</h3>
                <el-form-item :label="t('profile.settings.emailNotifications')">
                  <el-checkbox-group v-model="profileSettings.emailNotifications">
                    <el-checkbox label="system">{{ t('profile.settings.systemNotifications') }}</el-checkbox>
                    <el-checkbox label="security">{{ t('profile.settings.securityAlerts') }}</el-checkbox>
                    <el-checkbox label="update">{{ t('profile.settings.updateNotifications') }}</el-checkbox>
                    <el-checkbox label="marketing">{{ t('profile.settings.marketingPromotion') }}</el-checkbox>
                  </el-checkbox-group>
                </el-form-item>
                <el-form-item :label="t('profile.settings.desktopNotifications')">
                  <el-switch
                    v-model="profileSettings.desktopNotifications"
                    :active-text="t('common.enable')"
                    :inactive-text="t('common.disable')"
                  />
                </el-form-item>
                <el-form-item :label="t('profile.settings.soundAlerts')">
                  <el-switch
                    v-model="profileSettings.soundNotifications"
                    :active-text="t('common.enable')"
                    :inactive-text="t('common.disable')"
                  />
                </el-form-item>
              </div>

              <div class="form-section">
                <h3 class="section-title">{{ t('profile.settings.privacy') }}</h3>
                <el-form-item :label="t('profile.settings.onlineStatus')">
                  <el-switch
                    v-model="profileSettings.showOnlineStatus"
                    :active-text="t('common.show')"
                    :inactive-text="t('common.hide')"
                  />
                </el-form-item>
                <el-form-item :label="t('profile.settings.profileVisibility')">
                  <el-select v-model="profileSettings.profileVisibility" :placeholder="t('profile.settings.selectVisibility')" style="width: 200px">
                    <el-option :label="t('profile.settings.everyone')" value="public" />
                    <el-option :label="t('profile.settings.colleagues')" value="colleagues" />
                    <el-option :label="t('profile.settings.onlyMe')" value="private" />
                  </el-select>
                </el-form-item>
              </div>

              <el-form-item>
                <el-button type="primary" @click="saveProfileSettings" :loading="savingSettings">
                  {{ t('common.save') }}
                </el-button>
                <el-button @click="resetProfileSettings">
                  {{ t('common.reset') }}
                </el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-tab-pane>

        <!-- 安全中心 -->
        <el-tab-pane :label="t('profile.tabs.security')" name="security">
          <div class="tab-content p-6">
            <!-- 安全状态 -->
            <div class="security-status mb-6">
              <h3 class="section-title">{{ t('profile.security.status') }}</h3>
              <div class="status-grid grid grid-cols-1 md:grid-cols-3 gap-4">
                <div class="status-card bg-green-50 border border-green-200 rounded-lg p-4">
                  <div class="flex items-center">
                    <el-icon class="text-green-500 mr-2" :size="20"><Check /></el-icon>
                    <span class="text-green-700 font-medium">{{ t('profile.security.passwordGood') }}</span>
                  </div>
                  <p class="text-sm text-green-600 mt-1">{{ t('profile.security.passwordSecure') }}</p>
                </div>
                <div class="status-card bg-yellow-50 border border-yellow-200 rounded-lg p-4">
                  <div class="flex items-center">
                    <el-icon class="text-yellow-500 mr-2" :size="20"><Warning /></el-icon>
                    <span class="text-yellow-700 font-medium">{{ t('profile.security.twoFactorDisabled') }}</span>
                  </div>
                  <p class="text-sm text-yellow-600 mt-1">{{ t('profile.security.recommendEnable') }}</p>
                </div>
                <div class="status-card bg-blue-50 border border-blue-200 rounded-lg p-4">
                  <div class="flex items-center">
                    <el-icon class="text-blue-500 mr-2" :size="20"><Lock /></el-icon>
                    <span class="text-blue-700 font-medium">{{ t('profile.security.loginProtectionNormal') }}</span>
                  </div>
                  <p class="text-sm text-blue-600 mt-1">{{ t('profile.security.accountSecure') }}</p>
                </div>
              </div>
            </div>

            <!-- 安全操作 -->
            <div class="security-actions">
              <h3 class="section-title">{{ t('profile.security.actions') }}</h3>
              <div class="action-list space-y-4">
                <div class="action-item flex items-center justify-between p-4 border border-gray-200 rounded-lg">
                  <div class="action-info">
                    <h4 class="font-medium text-gray-800">{{ t('profile.security.changePassword') }}</h4>
                    <p class="text-sm text-gray-500">{{ t('profile.security.changePasswordDesc') }}</p>
                  </div>
                  <el-button type="primary" @click="showChangePassword = true">
                    {{ t('profile.security.changePassword') }}
                  </el-button>
                </div>
                <div class="action-item flex items-center justify-between p-4 border border-gray-200 rounded-lg">
                  <div class="action-info">
                    <h4 class="font-medium text-gray-800">{{ t('profile.security.twoFactorAuth') }}</h4>
                    <p class="text-sm text-gray-500">{{ t('profile.security.twoFactorDesc') }}</p>
                  </div>
                  <el-button type="success" @click="setupTwoFactor">
                    {{ userInfo.twoFactorEnabled ? t('profile.security.manage') : t('profile.security.enable') }}
                  </el-button>
                </div>
                <div class="action-item flex items-center justify-between p-4 border border-gray-200 rounded-lg">
                  <div class="action-info">
                    <h4 class="font-medium text-gray-800">{{ t('profile.security.deviceManagement') }}</h4>
                    <p class="text-sm text-gray-500">{{ t('profile.security.deviceManagementDesc') }}</p>
                  </div>
                  <el-button @click="showLoginDevices = true">
                    {{ t('profile.security.viewDevices') }}
                  </el-button>
                </div>
                <div class="action-item flex items-center justify-between p-4 border border-gray-200 rounded-lg">
                  <div class="action-info">
                    <h4 class="font-medium text-gray-800">{{ t('profile.security.loginLogs') }}</h4>
                    <p class="text-sm text-gray-500">{{ t('profile.security.loginLogsDesc') }}</p>
                  </div>
                  <el-button @click="showLoginLogs = true">
                    {{ t('profile.security.viewLogs') }}
                  </el-button>
                </div>
              </div>
            </div>
          </div>
        </el-tab-pane>

        <!-- 操作日志 -->
        <el-tab-pane :label="t('profile.tabs.logs')" name="logs">
          <div class="tab-content p-6">
            <!-- 筛选条件 -->
            <div class="filter-section mb-4">
              <el-form :model="logFilter" :inline="true">
                <el-form-item :label="t('profile.logs.operationType')">
                  <el-select v-model="logFilter.type" :placeholder="t('profile.logs.selectType')" clearable style="width: 150px">
                    <el-option :label="t('profile.logs.login')" value="login" />
                    <el-option :label="t('profile.logs.logout')" value="logout" />
                    <el-option :label="t('profile.logs.profileEdit')" value="profile" />
                    <el-option :label="t('profile.logs.passwordChange')" value="password" />
                    <el-option :label="t('profile.logs.systemOperation')" value="system" />
                  </el-select>
                </el-form-item>
                <el-form-item :label="t('profile.logs.timeRange')">
                  <el-date-picker
                    v-model="logFilter.dateRange"
                    type="daterange"
                    :range-separator="t('common.to')"
                    :start-placeholder="t('profile.logs.startDate')"
                    :end-placeholder="t('profile.logs.endDate')"
                    style="width: 240px"
                  />
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="loadOperationLogs">
                    {{ t('common.search') }}
                  </el-button>
                  <el-button @click="resetLogFilter">
                    {{ t('common.reset') }}
                  </el-button>
                </el-form-item>
              </el-form>
            </div>

            <!-- 日志列表 -->
            <el-table v-loading="loadingLogs" :data="operationLogs" stripe>
              <el-table-column :label="t('profile.logs.operationType')" width="120">
                <template #default="{ row }">
                  <el-tag :type="getLogTypeTag(row.type)" size="small">
                    {{ getLogTypeName(row.type) }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="description" :label="t('profile.logs.description')" min-width="200" />
              <el-table-column prop="ip" :label="t('profile.logs.ipAddress')" width="140" />
              <el-table-column prop="userAgent" :label="t('profile.logs.deviceInfo')" min-width="200" show-overflow-tooltip />
              <el-table-column prop="createTime" :label="t('profile.logs.operationTime')" width="160" />
              <el-table-column :label="t('profile.logs.status')" width="80">
                <template #default="{ row }">
                  <el-tag :type="row.success ? 'success' : 'danger'" size="small">
                    {{ row.success ? t('profile.logs.success') : t('profile.logs.failed') }}
                  </el-tag>
                </template>
              </el-table-column>
            </el-table>

            <!-- 分页 -->
            <div class="pagination-wrapper mt-4 flex justify-center">
              <el-pagination
                v-model:current-page="logPagination.page"
                v-model:page-size="logPagination.size"
                :page-sizes="[10, 20, 50, 100]"
                :total="logPagination.total"
                layout="total, sizes, prev, pager, next, jumper"
                @size-change="loadOperationLogs"
                @current-change="loadOperationLogs"
              />
            </div>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>

    <!-- 编辑资料对话框 -->
    <el-dialog v-model="showEditProfile" :title="t('profile.editProfile')" width="600px">
      <el-form ref="profileFormRef" :model="editProfileForm" :rules="profileFormRules" label-width="100px">
        <el-form-item :label="t('profile.realName')" prop="realName">
          <el-input v-model="editProfileForm.realName" :placeholder="t('profile.enterRealName')" />
        </el-form-item>
        <el-form-item :label="t('profile.email')" prop="email">
          <el-input v-model="editProfileForm.email" :placeholder="t('profile.enterEmail')" />
        </el-form-item>
        <el-form-item :label="t('profile.phone')" prop="phone">
          <el-input v-model="editProfileForm.phone" :placeholder="t('profile.enterPhone')" />
        </el-form-item>
        <el-form-item :label="t('profile.bio')">
          <el-input
            v-model="editProfileForm.bio"
            type="textarea"
            :rows="3"
            :placeholder="t('profile.enterBio')"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="showEditProfile = false">{{ t('common.cancel') }}</el-button>
          <el-button type="primary" @click="saveProfile" :loading="savingProfile">
            {{ t('common.save') }}
          </el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 修改密码对话框 -->
    <el-dialog v-model="showChangePassword" :title="t('profile.security.changePassword')" width="500px">
      <el-form ref="passwordFormRef" :model="changePasswordForm" :rules="passwordFormRules" label-width="100px">
        <el-form-item :label="t('profile.security.currentPassword')" prop="currentPassword">
          <el-input
            v-model="changePasswordForm.currentPassword"
            type="password"
            :placeholder="t('profile.security.enterCurrentPassword')"
            show-password
          />
        </el-form-item>
        <el-form-item :label="t('profile.security.newPassword')" prop="newPassword">
          <el-input
            v-model="changePasswordForm.newPassword"
            type="password"
            :placeholder="t('profile.security.enterNewPassword')"
            show-password
          />
        </el-form-item>
        <el-form-item :label="t('profile.security.confirmPassword')" prop="confirmPassword">
          <el-input
            v-model="changePasswordForm.confirmPassword"
            type="password"
            :placeholder="t('profile.security.enterConfirmPassword')"
            show-password
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="showChangePassword = false">{{ t('common.cancel') }}</el-button>
          <el-button type="primary" @click="changePassword" :loading="changingPassword">
            {{ t('common.confirm') }}
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, computed } from 'vue'
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus'
import {
  User,
  Check,
  Warning,
  Lock
} from '@element-plus/icons-vue'
import { useI18n } from 'vue-i18n'

const { t } = useI18n()

// 响应式数据
const activeTab = ref('settings')
const showEditProfile = ref(false)
const showChangePassword = ref(false)
const showLoginDevices = ref(false)
const showLoginLogs = ref(false)
const savingSettings = ref(false)
const savingProfile = ref(false)
const changingPassword = ref(false)
const loadingLogs = ref(false)
const profileFormRef = ref<FormInstance>()
const passwordFormRef = ref<FormInstance>()

// 用户信息
const userInfo = reactive({
  id: 1,
  username: 'admin',
  realName: '系统管理员',
  email: 'admin@company.com',
  phone: '13800138000',
  department: '技术部',
  role: '超级管理员',
  avatar: '',
  bio: '负责系统管理和维护工作',
  lastLoginTime: '2024-01-15 14:30:25',
  twoFactorEnabled: false
})

// 个人设置
const profileSettings = reactive({
  theme: 'light',
  language: 'zh-CN',
  sidebarCollapsed: false,
  showBreadcrumb: true,
  emailNotifications: ['system', 'security'],
  desktopNotifications: true,
  soundNotifications: false,
  showOnlineStatus: true,
  profileVisibility: 'colleagues'
})

// 编辑资料表单
const editProfileForm = reactive({
  realName: '',
  email: '',
  phone: '',
  bio: ''
})

// 修改密码表单
const changePasswordForm = reactive({
  currentPassword: '',
  newPassword: '',
  confirmPassword: ''
})

// 日志筛选
const logFilter = reactive({
  type: '',
  dateRange: []
})

// 日志分页
const logPagination = reactive({
  page: 1,
  size: 20,
  total: 0
})

// 操作日志
const operationLogs = ref([
  {
    id: 1,
    type: 'login',
    description: '用户登录系统',
    ip: '192.168.1.100',
    userAgent: 'Chrome 120.0.0.0 / Windows 10',
    createTime: '2024-01-15 14:30:25',
    success: true
  },
  {
    id: 2,
    type: 'profile',
    description: '修改个人资料',
    ip: '192.168.1.100',
    userAgent: 'Chrome 120.0.0.0 / Windows 10',
    createTime: '2024-01-15 10:15:30',
    success: true
  },
  {
    id: 3,
    type: 'password',
    description: '修改登录密码',
    ip: '192.168.1.100',
    userAgent: 'Chrome 120.0.0.0 / Windows 10',
    createTime: '2024-01-14 16:45:12',
    success: true
  },
  {
    id: 4,
    type: 'login',
    description: '用户登录失败',
    ip: '192.168.1.101',
    userAgent: 'Firefox 121.0 / Windows 10',
    createTime: '2024-01-14 09:20:15',
    success: false
  }
])

// 表单验证规则
const profileFormRules = computed((): FormRules => ({
  realName: [
    { required: true, message: t('profile.validation.realNameRequired'), trigger: 'blur' }
  ],
  email: [
    { required: true, message: t('profile.validation.emailRequired'), trigger: 'blur' },
    { type: 'email', message: t('profile.validation.emailFormat'), trigger: 'blur' }
  ],
  phone: [
    { required: true, message: t('profile.validation.phoneRequired'), trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: t('profile.validation.phoneFormat'), trigger: 'blur' }
  ]
}))

const passwordFormRules = computed((): FormRules => ({
  currentPassword: [
    { required: true, message: t('profile.validation.currentPasswordRequired'), trigger: 'blur' }
  ],
  newPassword: [
    { required: true, message: t('profile.validation.newPasswordRequired'), trigger: 'blur' },
    { min: 8, message: t('profile.validation.passwordMinLength'), trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: t('profile.validation.confirmPasswordRequired'), trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (value !== changePasswordForm.newPassword) {
          callback(new Error(t('profile.validation.passwordMismatch')))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ]
}))

// 方法
const beforeAvatarUpload = (file: File) => {
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
    userInfo.avatar = e.target?.result as string
    ElMessage.success('头像上传成功')
  }
  reader.readAsDataURL(file)
  
  return false // 阻止自动上传
}

const saveProfileSettings = async () => {
  savingSettings.value = true
  try {
    // 模拟保存设置
    await new Promise(resolve => setTimeout(resolve, 1000))
    ElMessage.success('设置保存成功')
  } catch (error) {
    ElMessage.error('设置保存失败')
  } finally {
    savingSettings.value = false
  }
}

const resetProfileSettings = () => {
  Object.assign(profileSettings, {
    theme: 'light',
    language: 'zh-CN',
    sidebarCollapsed: false,
    showBreadcrumb: true,
    emailNotifications: ['system', 'security'],
    desktopNotifications: true,
    soundNotifications: false,
    showOnlineStatus: true,
    profileVisibility: 'colleagues'
  })
  ElMessage.success('设置已重置')
}

const saveProfile = async () => {
  if (!profileFormRef.value) return
  
  try {
    await profileFormRef.value.validate()
    savingProfile.value = true
    
    // 模拟保存
    await new Promise(resolve => setTimeout(resolve, 1000))
    
    // 更新用户信息
    Object.assign(userInfo, editProfileForm)
    
    showEditProfile.value = false
    ElMessage.success('个人资料更新成功')
  } catch (error) {
    console.error('表单验证失败:', error)
  } finally {
    savingProfile.value = false
  }
}

const changePassword = async () => {
  if (!passwordFormRef.value) return
  
  try {
    await passwordFormRef.value.validate()
    changingPassword.value = true
    
    // 模拟修改密码
    await new Promise(resolve => setTimeout(resolve, 1500))
    
    showChangePassword.value = false
    Object.assign(changePasswordForm, {
      currentPassword: '',
      newPassword: '',
      confirmPassword: ''
    })
    ElMessage.success('密码修改成功')
  } catch (error) {
    console.error('表单验证失败:', error)
  } finally {
    changingPassword.value = false
  }
}

const setupTwoFactor = () => {
  ElMessage.info('双因子认证功能开发中')
}

const loadOperationLogs = async () => {
  loadingLogs.value = true
  try {
    // 模拟加载日志
    await new Promise(resolve => setTimeout(resolve, 500))
    logPagination.total = 50
  } catch (error) {
    ElMessage.error('加载日志失败')
  } finally {
    loadingLogs.value = false
  }
}

const resetLogFilter = () => {
  Object.assign(logFilter, {
    type: '',
    dateRange: []
  })
  loadOperationLogs()
}

const getLogTypeTag = (type: string) => {
  const typeMap: Record<string, string> = {
    'login': 'success',
    'logout': 'info',
    'profile': 'warning',
    'password': 'danger',
    'system': 'primary'
  }
  return typeMap[type] || 'info'
}

const getLogTypeName = (type: string) => {
  const nameMap: Record<string, string> = {
    'login': t('profile.logs.login'),
    'logout': t('profile.logs.logout'),
    'profile': t('profile.logs.profileEdit'),
    'password': t('profile.logs.passwordChange'),
    'system': t('profile.logs.systemOperation')
  }
  return nameMap[type] || t('common.unknown')
}

// 组件挂载时初始化
onMounted(() => {
  // 初始化编辑表单
  Object.assign(editProfileForm, {
    realName: userInfo.realName,
    email: userInfo.email,
    phone: userInfo.phone,
    bio: userInfo.bio
  })
  
  // 加载操作日志
  loadOperationLogs()
})
</script>

<style scoped>
.profile-page {
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

.profile-card {
  transition: all 0.3s ease;
}

.avatar-uploader {
  border: 2px dashed #d9d9d9;
  border-radius: 50%;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: border-color 0.3s;
  width: 100px;
  height: 100px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.avatar-uploader:hover {
  border-color: #409eff;
}

.avatar {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  object-fit: cover;
  display: block;
}

.avatar-placeholder {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  background: #f5f7fa;
  display: flex;
  align-items: center;
  justify-content: center;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
}

.info-grid {
  max-width: 500px;
}

.info-item {
  display: flex;
  align-items: center;
}

.label {
  min-width: 80px;
  margin-right: 8px;
}

.profile-tabs {
  min-height: 500px;
}

.tab-content {
  max-height: 60vh;
  overflow-y: auto;
}

.settings-form {
  max-width: 600px;
}

.form-section {
  margin-bottom: 30px;
  padding-bottom: 20px;
  border-bottom: 1px solid #f0f0f0;
}

.form-section:last-child {
  border-bottom: none;
}

.section-title {
  font-size: 16px;
  font-weight: 600;
  color: #333;
  margin-bottom: 16px;
  padding-bottom: 8px;
  border-bottom: 2px solid #409eff;
  display: inline-block;
}

.security-status {
  margin-bottom: 30px;
}

.status-grid {
  gap: 16px;
}

.status-card {
  transition: transform 0.2s ease;
}

.status-card:hover {
  transform: translateY(-2px);
}

.security-actions {
  margin-bottom: 30px;
}

.action-list {
  max-width: 700px;
}

.action-item {
  transition: all 0.2s ease;
}

.action-item:hover {
  border-color: #409eff;
  box-shadow: 0 2px 8px rgba(64, 158, 255, 0.1);
}

.filter-section {
  background: #f8f9fa;
  padding: 16px;
  border-radius: 8px;
  border: 1px solid #e9ecef;
}

.pagination-wrapper {
  margin-top: 20px;
}

.dialog-footer {
  text-align: right;
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