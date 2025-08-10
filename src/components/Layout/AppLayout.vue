<template>
  <div class="app-layout h-screen flex">
    <!-- 侧边栏 -->
    <aside class="sidebar w-64 bg-white shadow-sm border-r border-gray-200 flex flex-col">
      <!-- Logo区域 -->
      <div class="logo-area h-16 flex items-center justify-center border-b border-gray-200">
        <div class="flex items-center space-x-2">
          <div class="w-8 h-8 bg-blue-500 rounded-lg flex items-center justify-center">
            <span class="text-white font-bold text-sm">权</span>
          </div>
          <span class="text-lg font-semibold text-gray-800">权限管理系统</span>
        </div>
      </div>
      
      <!-- 导航菜单 -->
      <nav class="flex-1 overflow-y-auto py-4">
        <el-menu
          :default-active="activeMenu"
          class="border-none"
          router
          @select="handleMenuSelect"
        >
          <el-menu-item index="/dashboard">
            <el-icon><House /></el-icon>
            <span>{{ t('menu.dashboard') }}</span>
          </el-menu-item>
          
          <el-sub-menu index="user">
            <template #title>
              <el-icon><User /></el-icon>
              <span>{{ t('menu.userManagement') }}</span>
            </template>
            <el-menu-item index="/user/list">{{ t('menu.userList') }}</el-menu-item>
          </el-sub-menu>
          
          <el-sub-menu index="role">
            <template #title>
              <el-icon><UserFilled /></el-icon>
              <span>{{ t('menu.roleManagement') }}</span>
            </template>
            <el-menu-item index="/role/list">{{ t('menu.roleList') }}</el-menu-item>
          </el-sub-menu>
          
          <el-menu-item index="/permission/list">
            <el-icon><Lock /></el-icon>
            <span>{{ t('menu.permissionManagement') }}</span>
          </el-menu-item>
          
          <el-menu-item index="/menu/list">
            <el-icon><Menu /></el-icon>
            <span>{{ t('menu.menuManagement') }}</span>
          </el-menu-item>
          
          <el-menu-item index="/department/list">
            <el-icon><OfficeBuilding /></el-icon>
            <span>{{ t('menu.departmentManagement') }}</span>
          </el-menu-item>
          
          <el-menu-item index="/system/settings">
            <el-icon><Setting /></el-icon>
            <span>{{ t('menu.systemSettings') }}</span>
          </el-menu-item>
        </el-menu>
      </nav>
    </aside>
    
    <!-- 主内容区域 -->
    <div class="main-content flex-1 flex flex-col">
      <!-- 顶部导航栏 -->
      <header class="header h-16 bg-white shadow-sm border-b border-gray-200 flex items-center justify-between px-6">
        <!-- 面包屑导航 -->
        <div class="breadcrumb-area">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/dashboard' }">{{ t('menu.dashboard') }}</el-breadcrumb-item>
            <el-breadcrumb-item v-for="item in breadcrumbItems" :key="item.path" :to="{ path: item.path }">
              {{ item.title }}
            </el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        
        <!-- 用户操作区域 -->
        <div class="user-area flex items-center space-x-4">
          <!-- 语言切换 -->
          <el-dropdown @command="handleLanguageChange">
            <el-button :icon="Operation" circle />
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="zh-CN" :class="{ 'is-active': currentLocale === 'zh-CN' }">
                  <span class="flex items-center">
                    <span class="mr-2">🇨🇳</span>
                    简体中文
                  </span>
                </el-dropdown-item>
                <el-dropdown-item command="en-US" :class="{ 'is-active': currentLocale === 'en-US' }">
                  <span class="flex items-center">
                    <span class="mr-2">🇺🇸</span>
                    English
                  </span>
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
          
          <!-- 通知 -->
          <el-badge :value="3" class="item">
            <el-button :icon="Bell" circle />
          </el-badge>
          
          <!-- 用户下拉菜单 -->
          <el-dropdown @command="handleUserCommand">
            <div class="flex items-center space-x-2 cursor-pointer hover:bg-gray-50 px-3 py-2 rounded-lg">
              <el-avatar :size="32" :src="userInfo.avatar" />
              <span class="text-sm text-gray-700">{{ userInfo.realName }}</span>
              <el-icon class="text-gray-400"><ArrowDown /></el-icon>
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">
                  <el-icon><User /></el-icon>
                  {{ t('menu.personalCenter') }}
                </el-dropdown-item>
                <el-dropdown-item command="settings">
                  <el-icon><Setting /></el-icon>
                  {{ t('profile.title') }}
                </el-dropdown-item>
                <el-dropdown-item divided command="logout">
                  <el-icon><SwitchButton /></el-icon>
                  {{ t('common.logout') }}
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </header>
      
      <!-- 页面内容区域 -->
      <main class="content flex-1 overflow-auto bg-gray-50 p-6">
        <router-view />
      </main>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'
import {
  House,
  User,
  UserFilled,
  Lock,
  Menu,
  OfficeBuilding,
  Setting,
  Bell,
  ArrowDown,
  SwitchButton,
  Operation
} from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { switchLanguage, getCurrentLocale } from '@/i18n'
import { authApi } from '@/api/auth'

const route = useRoute()
const router = useRouter()
const { t } = useI18n()

// 当前语言
const currentLocale = ref(getCurrentLocale())

// 当前激活的菜单
const activeMenu = computed(() => route.path)

// 面包屑导航数据
const breadcrumbItems = ref<Array<{ path: string; title: string }>>([])

// 用户信息
const userInfo = ref({
  realName: '管理员',
  avatar: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'
})

// 菜单路径映射
const menuTitleMap: Record<string, string> = {
  '/dashboard': '首页仪表板',
  '/user/list': '用户管理',
  '/user/detail': '用户详情',
  '/role/list': '角色管理',
  '/role/detail': '角色详情',
  '/permission/list': '权限管理',
  '/menu/list': '菜单管理',
  '/department/list': '部门管理',
  '/system/settings': '系统设置',
  '/profile': '个人中心'
}

// 更新面包屑导航
const updateBreadcrumb = () => {
  const pathSegments = route.path.split('/').filter(Boolean)
  const items: Array<{ path: string; title: string }> = []
  
  let currentPath = ''
  pathSegments.forEach(segment => {
    currentPath += `/${segment}`
    const title = menuTitleMap[currentPath]
    if (title && currentPath !== '/dashboard') {
      items.push({ path: currentPath, title })
    }
  })
  
  breadcrumbItems.value = items
}

// 监听路由变化更新面包屑
watch(() => route.path, updateBreadcrumb, { immediate: true })

// 处理菜单选择
const handleMenuSelect = (index: string) => {
  console.log('选择菜单:', index)
}

// 处理语言切换
const handleLanguageChange = (locale: string) => {
  switchLanguage(locale)
  currentLocale.value = locale as 'zh-CN' | 'en-US'
  ElMessage.success(locale === 'zh-CN' ? '已切换到中文' : 'Switched to English')
}

// 处理用户下拉菜单命令
const handleUserCommand = (command: string) => {
  switch (command) {
    case 'profile':
      router.push('/profile')
      break
    case 'settings':
      ElMessage.info('个人设置功能开发中')
      break
    case 'logout':
      handleLogout()
      break
  }
}

// 处理退出登录
const handleLogout = async () => {
  try {
    await ElMessageBox.confirm(
      '确定要退出登录吗？',
      '退出确认',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    
    // 调用退出登录API
    try {
      await authApi.logout()
    } catch (error) {
      console.warn('调用退出登录API失败:', error)
    }
    
    // 清除本地存储
    localStorage.removeItem('token')
    localStorage.removeItem('refreshToken')
    localStorage.removeItem('userInfo')
    localStorage.removeItem('rememberMe')
    
    ElMessage.success('退出登录成功')
    router.push('/login')
  } catch {
    // 用户取消退出
  }
}
</script>

<style scoped>
.app-layout {
  font-family: 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', '微软雅黑', Arial, sans-serif;
}

.sidebar {
  transition: width 0.3s ease;
}

.el-menu {
  border-right: none;
}

.el-menu-item {
  height: 48px;
  line-height: 48px;
}

.el-sub-menu .el-menu-item {
  height: 40px;
  line-height: 40px;
  padding-left: 60px !important;
}

.breadcrumb-area .el-breadcrumb {
  font-size: 14px;
}

.user-area .el-button {
  border: none;
  background: transparent;
}

.user-area .el-button:hover {
  background: #f5f7fa;
}
</style>