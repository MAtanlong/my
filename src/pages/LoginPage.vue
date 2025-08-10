<template>
  <div class="login-page min-h-screen flex items-center justify-center bg-gradient-to-br from-blue-50 to-indigo-100">
    <div class="login-container w-full max-w-md">
      <!-- 登录卡片 -->
      <div class="bg-white rounded-lg shadow-lg p-8">
        <!-- Logo和标题 -->
        <div class="text-center mb-8">
          <div class="w-16 h-16 bg-blue-500 rounded-full flex items-center justify-center mx-auto mb-4">
            <span class="text-white font-bold text-2xl">权</span>
          </div>
          <h1 class="text-2xl font-bold text-gray-800 mb-2">{{ t('login.title') }}</h1>
          <p class="text-gray-500 text-sm">{{ t('login.subtitle') }}</p>
        </div>
        
        <!-- 登录方式切换 -->
        <div class="mb-6">
          <el-tabs v-model="loginType" class="login-tabs">
            <el-tab-pane :label="t('login.usernameLogin')" name="username">
              <el-form
                ref="usernameFormRef"
                :model="usernameForm"
                :rules="usernameRules"
                size="large"
                @submit.prevent="handleLogin"
              >
                <el-form-item prop="username">
                  <el-input
                    v-model="usernameForm.username"
                    :placeholder="t('login.usernamePlaceholder')"
                    :prefix-icon="User"
                    clearable
                  />
                </el-form-item>
                
                <el-form-item prop="password">
                  <el-input
                    v-model="usernameForm.password"
                    type="password"
                    :placeholder="t('login.passwordPlaceholder')"
                    :prefix-icon="Lock"
                    show-password
                    clearable
                  />
                </el-form-item>
                
                <el-form-item prop="captcha">
                  <div class="flex space-x-2">
                    <el-input
                      v-model="usernameForm.captcha"
                      :placeholder="t('login.captchaPlaceholder')"
                      :prefix-icon="Key"
                      clearable
                      class="flex-1"
                    />
                    <div 
                      class="captcha-image w-24 h-10 bg-gray-100 border border-gray-300 rounded cursor-pointer flex items-center justify-center text-sm font-mono"
                      @click="refreshCaptcha"
                    >
                      {{ captchaText }}
                    </div>
                  </div>
                </el-form-item>
              </el-form>
            </el-tab-pane>
            
            <el-tab-pane :label="t('login.phoneLogin')" name="phone">
              <el-form
                ref="phoneFormRef"
                :model="phoneForm"
                :rules="phoneRules"
                size="large"
                @submit.prevent="handleLogin"
              >
                <el-form-item prop="phone">
                  <el-input
                    v-model="phoneForm.phone"
                    :placeholder="t('login.phonePlaceholder')"
                    :prefix-icon="Iphone"
                    clearable
                  />
                </el-form-item>
                
                <el-form-item prop="smsCode">
                  <div class="flex space-x-2">
                    <el-input
                      v-model="phoneForm.smsCode"
                      :placeholder="t('login.smsCodePlaceholder')"
                      :prefix-icon="Message"
                      clearable
                      class="flex-1"
                    />
                    <el-button
                      :disabled="smsCountdown > 0"
                      @click="sendSmsCode"
                      class="w-24"
                    >
                      {{ smsCountdown > 0 ? `${smsCountdown}s` : t('login.getSmsCode') }}
                    </el-button>
                  </div>
                </el-form-item>
              </el-form>
            </el-tab-pane>
          </el-tabs>
        </div>
        
        <!-- 记住登录状态和忘记密码 -->
        <div class="flex items-center justify-between mb-6">
          <el-checkbox v-model="rememberMe">{{ t('login.rememberMe') }}</el-checkbox>
          <el-link type="primary" :underline="false" @click="handleForgotPassword">
            {{ t('login.forgotPassword') }}
          </el-link>
        </div>
        
        <!-- 登录按钮 -->
        <el-button
          type="primary"
          size="large"
          class="w-full mb-4"
          :loading="loginLoading"
          @click="handleLogin"
        >
          {{ loginLoading ? t('login.loggingIn') : t('login.login') }}
        </el-button>
        
        <!-- 其他登录方式 -->
        <div class="text-center">
          <div class="text-gray-500 text-sm mb-4">{{ t('login.otherLoginMethods') }}</div>
          <div class="flex justify-center space-x-4">
            <el-button circle :icon="ChatDotRound" @click="handleQrLogin" />
            <el-button circle :icon="ChatDotRound" @click="handleWechatLogin" />
          </div>
        </div>
      </div>
      
      <!-- 版权信息 -->
      <div class="text-center mt-8 text-gray-400 text-sm">
        © 2024 {{ t('login.title') }}. {{ t('common.allRightsReserved') }}.
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus'
import {
  User,
  Lock,
  Key,
  Iphone,
  Message,
  ChatDotRound
} from '@element-plus/icons-vue'
import { authApi } from '../api/auth'

const router = useRouter()
const { t } = useI18n()

// 登录类型
const loginType = ref('username')

// 登录加载状态
const loginLoading = ref(false)

// 记住登录状态
const rememberMe = ref(false)

// 验证码文本
const captchaText = ref('')

// 短信验证码倒计时
const smsCountdown = ref(0)

// 表单引用
const usernameFormRef = ref<FormInstance>()
const phoneFormRef = ref<FormInstance>()

// 用户名登录表单
const usernameForm = reactive({
  username: 'admin',
  password: '123456',
  captcha: ''
})

// 手机号登录表单
const phoneForm = reactive({
  phone: '',
  smsCode: ''
})

// 用户名登录验证规则
const usernameRules: FormRules = {
  username: [
    { required: true, message: () => t('login.validation.usernameRequired'), trigger: 'blur' },
    { min: 3, max: 20, message: () => t('login.validation.usernameLength'), trigger: 'blur' }
  ],
  password: [
    { required: true, message: () => t('login.validation.passwordRequired'), trigger: 'blur' },
    { min: 6, max: 20, message: () => t('login.validation.passwordLength'), trigger: 'blur' }
  ],
  captcha: [
    { required: true, message: () => t('login.validation.captchaRequired'), trigger: 'blur' },
    { len: 4, message: () => t('login.validation.captchaLength'), trigger: 'blur' }
  ]
}

// 手机号登录验证规则
const phoneRules: FormRules = {
  phone: [
    { required: true, message: () => t('login.validation.phoneRequired'), trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: () => t('login.validation.phoneFormat'), trigger: 'blur' }
  ],
  smsCode: [
    { required: true, message: () => t('login.validation.smsCodeRequired'), trigger: 'blur' },
    { len: 6, message: () => t('login.validation.smsCodeLength'), trigger: 'blur' }
  ]
}

// 生成随机验证码
const generateCaptcha = () => {
  const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789'
  let result = ''
  for (let i = 0; i < 4; i++) {
    result += chars.charAt(Math.floor(Math.random() * chars.length))
  }
  return result
}

// 刷新验证码
const refreshCaptcha = () => {
  captchaText.value = generateCaptcha()
  usernameForm.captcha = ''
}

// 发送短信验证码
const sendSmsCode = async () => {
  if (!phoneForm.phone) {
    ElMessage.warning(t('login.messages.enterPhoneFirst'))
    return
  }
  
  if (!/^1[3-9]\d{9}$/.test(phoneForm.phone)) {
    ElMessage.warning(t('login.validation.phoneFormat'))
    return
  }
  
  try {
    // 调用发送短信验证码API
    const response = await authApi.sendSmsCode(phoneForm.phone)
    
    if (response.code === 200) {
      ElMessage.success(t('login.messages.smsCodeSent'))
      
      // 开始倒计时
      smsCountdown.value = 60
      const timer = setInterval(() => {
        smsCountdown.value--
        if (smsCountdown.value <= 0) {
          clearInterval(timer)
        }
      }, 1000)
    } else {
      ElMessage.error(response.message || t('login.messages.smsCodeSendFailed'))
    }
  } catch (error: any) {
    console.error('发送短信验证码失败:', error)
    ElMessage.error(error.message || t('login.messages.smsCodeSendFailed'))
  }
}

// 处理登录
const handleLogin = async () => {
  const formRef = loginType.value === 'username' ? usernameFormRef.value : phoneFormRef.value
  
  if (!formRef) return
  
  try {
    await formRef.validate()
    
    loginLoading.value = true
    
    // 验证码校验
    if (loginType.value === 'username') {
      if (usernameForm.captcha.toUpperCase() !== captchaText.value) {
        ElMessage.error(t('login.messages.captchaError'))
        refreshCaptcha()
        loginLoading.value = false
        return
      }
    }
    
    // 准备登录数据
    const loginData = {
      loginType: loginType.value,
      ...(loginType.value === 'username' ? {
        username: usernameForm.username,
        password: usernameForm.password,
        captcha: usernameForm.captcha
      } : {
        phone: phoneForm.phone,
        smsCode: phoneForm.smsCode
      })
    }
    
    // 调用登录API
    const response = await authApi.login(loginData)
    
    if (response.code === 200) {
      // 保存token和用户信息
      localStorage.setItem('token', response.data.token)
      localStorage.setItem('refreshToken', response.data.refreshToken)
      localStorage.setItem('userInfo', JSON.stringify(response.data.userInfo))
      
      // 保存登录状态
      if (rememberMe.value) {
        localStorage.setItem('rememberMe', 'true')
      }
      
      // 登录成功
      ElMessage.success(t('login.messages.loginSuccess'))
      
      // 跳转到首页
      router.push('/dashboard')
    } else {
      ElMessage.error(response.message || '登录失败')
      refreshCaptcha()
    }
    
  } catch (error: any) {
    console.error('登录失败:', error)
    ElMessage.error(error.message || '登录失败，请稍后重试')
    refreshCaptcha()
  } finally {
    loginLoading.value = false
  }
}

// 处理忘记密码
const handleForgotPassword = () => {
  ElMessageBox.alert(t('login.messages.contactAdmin'), t('login.forgotPassword'), {
    confirmButtonText: t('common.confirm')
  })
}

// 处理二维码登录
const handleQrLogin = () => {
  ElMessage.info(t('login.messages.qrLoginDeveloping'))
}

// 处理微信登录
const handleWechatLogin = () => {
  ElMessage.info(t('login.messages.wechatLoginDeveloping'))
}

// 组件挂载时初始化
onMounted(() => {
  refreshCaptcha()
  
  // 检查是否记住登录状态
  if (localStorage.getItem('rememberMe')) {
    rememberMe.value = true
  }
})
</script>

<style scoped>
.login-page {
  background-image: 
    radial-gradient(circle at 25% 25%, rgba(59, 130, 246, 0.1) 0%, transparent 50%),
    radial-gradient(circle at 75% 75%, rgba(99, 102, 241, 0.1) 0%, transparent 50%);
}

.login-container {
  animation: fadeInUp 0.6s ease-out;
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.login-tabs :deep(.el-tabs__header) {
  margin: 0 0 20px 0;
}

.login-tabs :deep(.el-tabs__nav-wrap::after) {
  display: none;
}

.captcha-image {
  user-select: none;
  transition: all 0.3s ease;
}

.captcha-image:hover {
  background-color: #f0f0f0;
  border-color: #409eff;
}

.el-button.is-circle {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  border: 1px solid #dcdfe6;
  background: white;
  color: #606266;
  transition: all 0.3s ease;
}

.el-button.is-circle:hover {
  border-color: #409eff;
  color: #409eff;
  background: #ecf5ff;
}
</style>