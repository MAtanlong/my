<template>
  <div class="dashboard-page">
    <!-- 页面标题 -->
    <div class="page-header mb-6">
      <h1 class="text-2xl font-bold text-gray-800 mb-2">首页仪表板</h1>
      <p class="text-gray-600">欢迎回来，{{ userInfo.realName }}！今天是 {{ currentDate }}</p>
    </div>
    
    <!-- 统计卡片 -->
    <div class="stats-cards grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
      <div 
        v-for="stat in statsData" 
        :key="stat.key"
        class="stat-card bg-white rounded-lg shadow-sm border border-gray-200 p-6 hover:shadow-md transition-shadow"
      >
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm text-gray-600 mb-1">{{ stat.title }}</p>
            <p class="text-2xl font-bold" :class="stat.color">{{ stat.value }}</p>
            <p class="text-xs text-gray-500 mt-1">
              <span :class="stat.trend === 'up' ? 'text-green-500' : 'text-red-500'">
                {{ stat.trend === 'up' ? '↗' : '↘' }} {{ stat.change }}
              </span>
              较昨日
            </p>
          </div>
          <div class="stat-icon w-12 h-12 rounded-lg flex items-center justify-center" :class="stat.bgColor">
            <el-icon :size="24" :class="stat.iconColor">
              <component :is="stat.icon" />
            </el-icon>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 图表和快捷操作区域 -->
    <div class="grid grid-cols-1 lg:grid-cols-3 gap-6 mb-8">
      <!-- 用户增长趋势图 -->
      <div class="lg:col-span-2">
        <div class="chart-card bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <div class="flex items-center justify-between mb-4">
            <h3 class="text-lg font-semibold text-gray-800">用户增长趋势</h3>
            <el-select v-model="chartPeriod" size="small" style="width: 120px">
              <el-option label="最近7天" value="7d" />
              <el-option label="最近30天" value="30d" />
              <el-option label="最近90天" value="90d" />
            </el-select>
          </div>
          <div class="chart-container h-64 flex items-center justify-center bg-gray-50 rounded-lg">
            <div class="text-center text-gray-500">
              <el-icon :size="48" class="mb-2"><TrendCharts /></el-icon>
              <p>图表组件开发中</p>
              <p class="text-sm">将集成 ECharts 或 Chart.js</p>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 快捷操作 -->
      <div class="quick-actions">
        <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <h3 class="text-lg font-semibold text-gray-800 mb-4">快捷操作</h3>
          <div class="space-y-3">
            <el-button 
              v-for="action in quickActions" 
              :key="action.key"
              :type="action.type"
              :icon="action.icon"
              class="w-full justify-start"
              @click="handleQuickAction(action.key)"
            >
              {{ action.title }}
            </el-button>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 最新动态和系统公告 -->
    <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
      <!-- 最新动态 -->
      <div class="activity-card bg-white rounded-lg shadow-sm border border-gray-200 p-6">
        <div class="flex items-center justify-between mb-4">
          <h3 class="text-lg font-semibold text-gray-800">最新动态</h3>
          <el-link type="primary" :underline="false" @click="viewAllActivities">
            查看全部
          </el-link>
        </div>
        <div class="activity-list space-y-4">
          <div 
            v-for="activity in recentActivities" 
            :key="activity.id"
            class="activity-item flex items-start space-x-3 p-3 hover:bg-gray-50 rounded-lg transition-colors"
          >
            <div class="activity-avatar w-8 h-8 rounded-full flex items-center justify-center text-white text-xs" :class="activity.avatarBg">
              {{ activity.user.charAt(0) }}
            </div>
            <div class="flex-1">
              <p class="text-sm text-gray-800">
                <span class="font-medium">{{ activity.user }}</span>
                {{ activity.action }}
              </p>
              <p class="text-xs text-gray-500 mt-1">{{ activity.time }}</p>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 系统公告 -->
      <div class="notice-card bg-white rounded-lg shadow-sm border border-gray-200 p-6">
        <div class="flex items-center justify-between mb-4">
          <h3 class="text-lg font-semibold text-gray-800">系统公告</h3>
          <el-link type="primary" :underline="false" @click="viewAllNotices">
            查看全部
          </el-link>
        </div>
        <div class="notice-list space-y-3">
          <div 
            v-for="notice in systemNotices" 
            :key="notice.id"
            class="notice-item p-3 hover:bg-gray-50 rounded-lg transition-colors cursor-pointer"
            @click="viewNotice(notice)"
          >
            <div class="flex items-center justify-between mb-1">
              <h4 class="text-sm font-medium text-gray-800 truncate">{{ notice.title }}</h4>
              <el-tag :type="notice.type" size="small">{{ notice.level }}</el-tag>
            </div>
            <p class="text-xs text-gray-500">{{ notice.date }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import {
  User,
  UserFilled,
  Lock,
  Menu,
  Plus,
  Setting,
  TrendCharts,
  Document,
  Bell
} from '@element-plus/icons-vue'

const router = useRouter()

// 当前日期
const currentDate = computed(() => {
  return new Date().toLocaleDateString('zh-CN', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    weekday: 'long'
  })
})

// 用户信息
const userInfo = ref({
  realName: '管理员'
})

// 图表时间周期
const chartPeriod = ref('7d')

// 统计数据
const statsData = ref([
  {
    key: 'totalUsers',
    title: '用户总数',
    value: '1,234',
    change: '+12%',
    trend: 'up',
    color: 'text-blue-600',
    bgColor: 'bg-blue-50',
    iconColor: 'text-blue-600',
    icon: User
  },
  {
    key: 'onlineUsers',
    title: '在线用户',
    value: '89',
    change: '+5%',
    trend: 'up',
    color: 'text-green-600',
    bgColor: 'bg-green-50',
    iconColor: 'text-green-600',
    icon: UserFilled
  },
  {
    key: 'totalRoles',
    title: '角色数量',
    value: '24',
    change: '+2',
    trend: 'up',
    color: 'text-purple-600',
    bgColor: 'bg-purple-50',
    iconColor: 'text-purple-600',
    icon: Lock
  },
  {
    key: 'totalMenus',
    title: '菜单数量',
    value: '156',
    change: '+8',
    trend: 'up',
    color: 'text-orange-600',
    bgColor: 'bg-orange-50',
    iconColor: 'text-orange-600',
    icon: Menu
  }
])

// 快捷操作
const quickActions = ref([
  {
    key: 'addUser',
    title: '新增用户',
    type: 'primary',
    icon: Plus
  },
  {
    key: 'addRole',
    title: '新增角色',
    type: 'success',
    icon: Plus
  },
  {
    key: 'systemSettings',
    title: '系统设置',
    type: 'warning',
    icon: Setting
  },
  {
    key: 'viewLogs',
    title: '查看日志',
    type: 'info',
    icon: Document
  }
])

// 最新动态
const recentActivities = ref([
  {
    id: 1,
    user: '张三',
    action: '创建了新用户 "李四"',
    time: '2分钟前',
    avatarBg: 'bg-blue-500'
  },
  {
    id: 2,
    user: '王五',
    action: '修改了角色 "系统管理员" 的权限',
    time: '5分钟前',
    avatarBg: 'bg-green-500'
  },
  {
    id: 3,
    user: '赵六',
    action: '登录了系统',
    time: '10分钟前',
    avatarBg: 'bg-purple-500'
  },
  {
    id: 4,
    user: '孙七',
    action: '更新了个人信息',
    time: '15分钟前',
    avatarBg: 'bg-orange-500'
  }
])

// 系统公告
const systemNotices = ref([
  {
    id: 1,
    title: '系统维护通知：本周六凌晨2:00-4:00进行系统升级',
    level: '重要',
    type: 'danger',
    date: '2024-01-15'
  },
  {
    id: 2,
    title: '新功能上线：支持批量导入用户功能',
    level: '通知',
    type: 'success',
    date: '2024-01-14'
  },
  {
    id: 3,
    title: '安全提醒：请定期修改登录密码',
    level: '提醒',
    type: 'warning',
    date: '2024-01-13'
  },
  {
    id: 4,
    title: '系统优化：页面加载速度提升30%',
    level: '更新',
    type: 'info',
    date: '2024-01-12'
  }
])

// 处理快捷操作
const handleQuickAction = (actionKey: string) => {
  switch (actionKey) {
    case 'addUser':
      router.push('/user/list')
      ElMessage.success('跳转到用户管理页面')
      break
    case 'addRole':
      router.push('/role/list')
      ElMessage.success('跳转到角色管理页面')
      break
    case 'systemSettings':
      router.push('/system/settings')
      ElMessage.success('跳转到系统设置页面')
      break
    case 'viewLogs':
      ElMessage.info('日志查看功能开发中')
      break
    default:
      ElMessage.info('功能开发中')
  }
}

// 查看所有动态
const viewAllActivities = () => {
  ElMessage.info('查看全部动态功能开发中')
}

// 查看所有公告
const viewAllNotices = () => {
  ElMessage.info('查看全部公告功能开发中')
}

// 查看公告详情
const viewNotice = (notice: any) => {
  ElMessage.info(`查看公告：${notice.title}`)
}

// 组件挂载时加载数据
onMounted(() => {
  // 这里可以调用API加载真实数据
  console.log('仪表板页面已加载')
})
</script>

<style scoped>
.dashboard-page {
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

.stat-card {
  transition: all 0.3s ease;
}

.stat-card:hover {
  transform: translateY(-2px);
}

.stat-icon {
  transition: all 0.3s ease;
}

.chart-container {
  border: 2px dashed #e5e7eb;
}

.activity-item:hover {
  transform: translateX(4px);
}

.notice-item:hover {
  transform: translateX(4px);
}

.quick-actions .el-button {
  margin-bottom: 0;
}

.quick-actions .el-button + .el-button {
  margin-left: 0;
}
</style>