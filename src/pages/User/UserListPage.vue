<template>
  <div class="user-list-page">
    <!-- 页面标题和操作栏 -->
    <div class="page-header flex items-center justify-between mb-6">
      <div>
        <h1 class="text-2xl font-bold text-gray-800 mb-2">{{ t('user.title') }}</h1>
        <p class="text-gray-600">{{ t('user.description') }}</p>
      </div>
      <div class="flex items-center space-x-3">
        <el-button type="success" :icon="Download" @click="exportUsers">
          {{ t('user.export') }}
        </el-button>
        <el-button type="primary" :icon="Plus" @click="handleAdd">
          {{ t('user.add') }}
        </el-button>
      </div>
    </div>

    <!-- 搜索和筛选区域 -->
    <div class="search-section bg-white rounded-lg shadow-sm border border-gray-200 p-6 mb-6">
      <el-form :model="searchForm" :inline="true" class="search-form">
        <el-form-item :label="t('user.username')">
          <el-input
            v-model="searchForm.username"
            :placeholder="t('user.usernamePlaceholder')"
            clearable
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item :label="t('user.realName')">
          <el-input
            v-model="searchForm.realName"
            :placeholder="t('user.realNamePlaceholder')"
            clearable
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item :label="t('user.phone')">
          <el-input
            v-model="searchForm.phone"
            :placeholder="t('user.phonePlaceholder')"
            clearable
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item :label="t('user.status')">
          <el-select v-model="searchForm.status" :placeholder="t('user.statusPlaceholder')" clearable style="width: 120px">
            <el-option :label="t('common.enabled')" value="1" />
            <el-option :label="t('common.disabled')" value="0" />
          </el-select>
        </el-form-item>
        <el-form-item :label="t('user.role')">
          <el-select v-model="searchForm.roleId" :placeholder="t('user.rolePlaceholder')" clearable style="width: 150px">
            <el-option
              v-for="role in roleOptions"
              :key="role.id"
              :label="role.name"
              :value="role.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :icon="Search" @click="handleSearch">
            {{ t('common.search') }}
          </el-button>
          <el-button :icon="Refresh" @click="handleReset">
            {{ t('common.reset') }}
          </el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 用户列表 -->
    <div class="table-section bg-white rounded-lg shadow-sm border border-gray-200">
      <div class="table-header flex items-center justify-between p-4 border-b border-gray-200">
        <div class="flex items-center space-x-4">
          <span class="text-sm text-gray-600">{{ t('common.totalRecords', { total: pagination.total }) }}</span>
          <el-button
            v-if="selectedUsers.length > 0"
            type="danger"
            size="small"
            :icon="Delete"
            @click="handleBatchDelete"
          >
            {{ t('common.batchDelete', { count: selectedUsers.length }) }}
          </el-button>
        </div>
        <div class="flex items-center space-x-2">
          <el-tooltip :content="t('common.refreshList')">
            <el-button :icon="Refresh" size="small" @click="loadUserList" />
          </el-tooltip>
          <el-tooltip :content="t('common.columnSettings')">
            <el-button :icon="Setting" size="small" @click="showColumnSettings = true" />
          </el-tooltip>
        </div>
      </div>

      <el-table
        v-loading="loading"
        :data="userList"
        @selection-change="handleSelectionChange"
        stripe
        style="width: 100%"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" :label="t('common.id')" width="80" sortable />
        <el-table-column :label="t('user.avatar')" width="80">
          <template #default="{ row }">
            <el-avatar :size="40" :src="row.avatar">
              {{ row.realName?.charAt(0) || t('user.form.unknown').charAt(0) }}
            </el-avatar>
          </template>
        </el-table-column>
        <el-table-column prop="username" :label="t('user.username')" min-width="120" sortable />
        <el-table-column prop="realName" :label="t('user.realName')" min-width="120" />
        <el-table-column prop="phone" :label="t('user.phone')" min-width="130" />
        <el-table-column prop="email" :label="t('user.email')" min-width="180" />
        <el-table-column :label="t('user.role')" min-width="150">
          <template #default="{ row }">
            <div class="flex flex-wrap gap-1">
              <el-tag
                v-for="role in row.roles"
                :key="role.id"
                :type="getRoleTagType(role.code)"
                size="small"
              >
                {{ role.name }}
              </el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column :label="t('user.status')" width="100">
          <template #default="{ row }">
            <el-switch
              v-model="row.status"
              :active-value="1"
              :inactive-value="0"
              @change="handleStatusChange(row)"
            />
          </template>
        </el-table-column>
        <el-table-column prop="lastLoginTime" :label="t('user.lastLoginTime')" min-width="160" sortable />
        <el-table-column prop="createTime" :label="t('user.createTime')" min-width="160" sortable />
        <el-table-column :label="t('user.actions')" width="200" fixed="right">
          <template #default="{ row }">
            <div class="flex items-center space-x-2">
              <el-tooltip :content="t('user.view')">
                <el-button type="primary" :icon="View" size="small" @click="handleView(row)" />
              </el-tooltip>
              <el-tooltip :content="t('user.edit')">
                <el-button type="warning" :icon="Edit" size="small" @click="handleEdit(row)" />
              </el-tooltip>
              <el-tooltip :content="t('user.assignRole')">
                <el-button type="success" :icon="Key" size="small" @click="handleAssignRole(row)" />
              </el-tooltip>
              <el-tooltip :content="t('user.delete')">
                <el-button type="danger" :icon="Delete" size="small" @click="handleDelete(row)" />
              </el-tooltip>
            </div>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination-section p-4 border-t border-gray-200">
        <el-pagination
          v-model:current-page="pagination.current"
          v-model:page-size="pagination.size"
          :page-sizes="[10, 20, 50, 100]"
          :total="pagination.total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </div>

    <!-- 用户表单对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="600px"
      :close-on-click-modal="false"
    >
      <el-form
        ref="userFormRef"
        :model="userForm"
        :rules="userFormRules"
        label-width="100px"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item :label="t('user.username')" prop="username">
              <el-input v-model="userForm.username" :placeholder="t('user.usernamePlaceholder')" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="t('user.realName')" prop="realName">
              <el-input v-model="userForm.realName" :placeholder="t('user.realNamePlaceholder')" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item :label="t('user.phone')" prop="phone">
              <el-input v-model="userForm.phone" :placeholder="t('user.phonePlaceholder')" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="t('user.email')" prop="email">
              <el-input v-model="userForm.email" :placeholder="t('user.emailPlaceholder')" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" v-if="!isEdit">
          <el-col :span="12">
            <el-form-item :label="t('user.password')" prop="password">
              <el-input
                v-model="userForm.password"
                type="password"
                :placeholder="t('user.passwordPlaceholder')"
                show-password
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="t('user.confirmPassword')" prop="confirmPassword">
              <el-input
                v-model="userForm.confirmPassword"
                type="password"
                :placeholder="t('user.confirmPasswordPlaceholder')"
                show-password
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item :label="t('user.gender')" prop="gender">
              <el-radio-group v-model="userForm.gender">
                <el-radio :label="1">{{ t('user.male') }}</el-radio>
                <el-radio :label="2">{{ t('user.female') }}</el-radio>
                <el-radio :label="0">{{ t('user.unknown') }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="t('user.status')" prop="status">
              <el-switch
                v-model="userForm.status"
                :active-value="1"
                :inactive-value="0"
                :active-text="t('common.enabled')"
                :inactive-text="t('common.disabled')"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item :label="t('user.department')" prop="departmentId">
          <el-tree-select
            v-model="userForm.departmentId"
            :data="departmentOptions"
            :props="{ label: 'name', value: 'id', children: 'children' }"
            :placeholder="t('user.departmentPlaceholder')"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item :label="t('user.remark')">
          <el-input
            v-model="userForm.remark"
            type="textarea"
            :rows="3"
            :placeholder="t('user.remarkPlaceholder')"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialogVisible = false">{{ t('common.cancel') }}</el-button>
          <el-button type="primary" @click="handleSubmit" :loading="submitLoading">
            {{ t('common.confirm') }}
          </el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 角色分配对话框 -->
    <el-dialog
      v-model="roleDialogVisible"
      :title="t('user.assignRole')"
      width="500px"
      :close-on-click-modal="false"
    >
      <div class="mb-4">
        <p class="text-sm text-gray-600 mb-2">{{ t('user.userInfo', { realName: currentUser?.realName, username: currentUser?.username }) }}</p>
        <p class="text-sm text-gray-600">{{ t('user.selectRoleTip') }}</p>
      </div>
      <el-checkbox-group v-model="selectedRoleIds">
        <div class="space-y-2">
          <el-checkbox
            v-for="role in roleOptions"
            :key="role.id"
            :label="role.id"
            class="w-full"
          >
            <div class="flex items-center justify-between w-full">
              <span>{{ role.name }}</span>
              <el-tag :type="getRoleTagType(role.code)" size="small">
                {{ role.code }}
              </el-tag>
            </div>
          </el-checkbox>
        </div>
      </el-checkbox-group>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="roleDialogVisible = false">{{ t('common.cancel') }}</el-button>
          <el-button type="primary" @click="handleRoleSubmit" :loading="roleSubmitLoading">
            {{ t('common.confirm') }}
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, computed } from 'vue'
import { useI18n } from 'vue-i18n'
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus'
import {
  Plus,
  Search,
  Refresh,
  Edit,
  Delete,
  View,
  Download,
  Setting,
  Key
} from '@element-plus/icons-vue'
import * as userApi from '@/api/user'
import * as roleApi from '@/api/role'
import * as departmentApi from '@/api/department'

const { t } = useI18n()

// 响应式数据
const loading = ref(false)
const dialogVisible = ref(false)
const roleDialogVisible = ref(false)
const showColumnSettings = ref(false)
const submitLoading = ref(false)
const roleSubmitLoading = ref(false)
const isEdit = ref(false)
const userFormRef = ref<FormInstance>()
const currentUser = ref<any>(null)
const selectedUsers = ref<any[]>([])
const selectedRoleIds = ref<number[]>([])

// 搜索表单
const searchForm = reactive({
  username: '',
  realName: '',
  phone: '',
  status: '',
  roleId: ''
})

// 分页信息
const pagination = reactive({
  current: 1,
  size: 20,
  total: 0
})

// 用户表单
const userForm = reactive({
  id: null,
  username: '',
  realName: '',
  phone: '',
  email: '',
  password: '',
  confirmPassword: '',
  gender: 1,
  status: 1,
  departmentId: null,
  remark: ''
})

// 表单验证规则
const userFormRules: FormRules = {
  username: [
    { required: true, message: () => t('user.validation.usernameRequired'), trigger: 'blur' },
    { min: 3, max: 20, message: () => t('user.validation.usernameLength'), trigger: 'blur' }
  ],
  realName: [
    { required: true, message: () => t('user.validation.realNameRequired'), trigger: 'blur' }
  ],
  phone: [
    { required: true, message: () => t('user.validation.phoneRequired'), trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: () => t('user.validation.phoneFormat'), trigger: 'blur' }
  ],
  email: [
    { required: true, message: () => t('user.validation.emailRequired'), trigger: 'blur' },
    { type: 'email', message: () => t('user.validation.emailFormat'), trigger: 'blur' }
  ],
  password: [
    { required: true, message: () => t('user.validation.passwordRequired'), trigger: 'blur' },
    { min: 6, max: 20, message: () => t('user.validation.passwordLength'), trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: () => t('user.validation.confirmPasswordRequired'), trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (value !== userForm.password) {
          callback(new Error(t('user.validation.passwordMismatch')))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ]
}

// 用户列表数据
const userList = ref([])

// 角色选项
const roleOptions = ref([])

// 部门选项
const departmentOptions = ref([])

// 加载角色选项
const loadRoleOptions = async () => {
  try {
    const response = await roleApi.getAllRoles()
    roleOptions.value = response.data
  } catch (error) {
    console.error('加载角色选项失败:', error)
  }
}

// 加载部门选项
const loadDepartmentOptions = async () => {
  try {
    const response = await departmentApi.getDepartmentTree()
    departmentOptions.value = response.data
  } catch (error) {
    console.error('加载部门选项失败:', error)
  }
}

// 计算属性
const dialogTitle = computed(() => {
  return isEdit.value ? t('user.edit') : t('user.add')
})

// 获取角色标签类型
const getRoleTagType = (code: string) => {
  const typeMap: Record<string, string> = {
    'SUPER_ADMIN': 'danger',
    'DEPT_ADMIN': 'warning',
    'AUDITOR': 'success',
    'USER': 'info'
  }
  return typeMap[code] || 'info'
}

// 方法
const loadUserList = async () => {
  loading.value = true
  try {
    const params = {
      ...searchForm,
      page: pagination.current,
      size: pagination.size
    }
    
    const response = await userApi.getUserList(params)
    userList.value = response.data.records
    pagination.total = response.data.total
    
  } catch (error) {
    console.error('加载用户列表失败:', error)
    ElMessage.error(t('user.messages.loadFailed'))
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  pagination.current = 1
  loadUserList()
}

const handleReset = () => {
  Object.assign(searchForm, {
    username: '',
    realName: '',
    phone: '',
    status: '',
    roleId: ''
  })
  handleSearch()
}

const handleAdd = () => {
  isEdit.value = false
  resetUserForm()
  dialogVisible.value = true
}

const handleEdit = (row: any) => {
  isEdit.value = true
  Object.assign(userForm, { ...row })
  dialogVisible.value = true
}

const handleView = (row: any) => {
  ElMessage.info(t('user.messages.viewUser', { name: row.realName }))
}

const handleDelete = async (row: any) => {
  try {
    await ElMessageBox.confirm(
      t('user.messages.deleteConfirm', { name: row.realName }),
      t('common.deleteConfirm'),
      {
        confirmButtonText: t('common.confirm'),
        cancelButtonText: t('common.cancel'),
        type: 'warning'
      }
    )
    
    await userApi.deleteUser(row.id)
    ElMessage.success(t('common.deleteSuccess'))
    loadUserList() // 重新加载列表
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除用户失败:', error)
      ElMessage.error(t('user.messages.deleteFailed'))
    } else {
      ElMessage.info(t('common.deleteCanceled'))
    }
  }
}

const handleBatchDelete = async () => {
  try {
    await ElMessageBox.confirm(
      t('user.messages.batchDeleteConfirm', { count: selectedUsers.value.length }),
      t('common.batchDeleteConfirm'),
      {
        confirmButtonText: t('common.confirm'),
        cancelButtonText: t('common.cancel'),
        type: 'warning'
      }
    )
    
    const selectedIds = selectedUsers.value.map(user => user.id)
    await userApi.batchDeleteUsers(selectedIds)
    selectedUsers.value = []
    ElMessage.success(t('common.batchDeleteSuccess'))
    loadUserList() // 重新加载列表
  } catch (error) {
    if (error !== 'cancel') {
      console.error('批量删除用户失败:', error)
      ElMessage.error(t('user.messages.batchDeleteFailed'))
    } else {
      ElMessage.info(t('common.deleteCanceled'))
    }
  }
}

const handleStatusChange = async (row: any) => {
  try {
    await userApi.updateUserStatus(row.id, row.status)
    ElMessage.success(t('user.messages.statusChanged', { status: row.status ? t('common.enabled') : t('common.disabled') }))
  } catch (error) {
    // 恢复原状态
    row.status = row.status ? 0 : 1
    console.error('更新用户状态失败:', error)
    ElMessage.error(t('user.messages.statusChangeFailed'))
  }
}

const handleAssignRole = (row: any) => {
  currentUser.value = row
  selectedRoleIds.value = row.roles.map((role: any) => role.id)
  roleDialogVisible.value = true
}

const handleSelectionChange = (selection: any[]) => {
  selectedUsers.value = selection
}

const handleSizeChange = (size: number) => {
  pagination.size = size
  loadUserList()
}

const handleCurrentChange = (current: number) => {
  pagination.current = current
  loadUserList()
}

const resetUserForm = () => {
  Object.assign(userForm, {
    id: null,
    username: '',
    realName: '',
    phone: '',
    email: '',
    password: '',
    confirmPassword: '',
    gender: 1,
    status: 1,
    departmentId: null,
    remark: ''
  })
  userFormRef.value?.clearValidate()
}

const handleSubmit = async () => {
  if (!userFormRef.value) return
  
  try {
    await userFormRef.value.validate()
    submitLoading.value = true
    
    if (isEdit.value) {
      // 更新用户
      await userApi.updateUser(userForm.id, userForm)
      ElMessage.success(t('user.messages.updateSuccess'))
    } else {
      // 新增用户
      await userApi.createUser(userForm)
      ElMessage.success(t('user.messages.createSuccess'))
    }
    
    dialogVisible.value = false
    resetUserForm()
    loadUserList() // 重新加载列表
  } catch (error) {
    console.error('提交失败:', error)
    ElMessage.error(isEdit.value ? t('user.messages.updateFailed') : t('user.messages.createFailed'))
  } finally {
    submitLoading.value = false
  }
}

const handleRoleSubmit = async () => {
  if (!currentUser.value) return
  
  try {
    roleSubmitLoading.value = true
    
    // 调用API分配角色
    await roleApi.assignRoleToUser(currentUser.value.id, selectedRoleIds.value)
    
    ElMessage.success(t('user.messages.roleAssignSuccess'))
    roleDialogVisible.value = false
    loadUserList() // 重新加载列表以更新角色信息
  } catch (error) {
    console.error('分配角色失败:', error)
    ElMessage.error(t('user.messages.roleAssignFailed'))
  } finally {
    roleSubmitLoading.value = false
  }
}

const exportUsers = () => {
  ElMessage.info(t('user.messages.exportInDevelopment'))
}

// 组件挂载时加载数据
onMounted(() => {
  loadUserList()
  loadRoleOptions()
  loadDepartmentOptions()
})
</script>

<style scoped>
.user-list-page {
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

.search-form .el-form-item {
  margin-bottom: 16px;
}

.table-section {
  overflow: hidden;
}

.dialog-footer {
  text-align: right;
}

.el-checkbox {
  display: flex;
  align-items: center;
  width: 100%;
  margin-right: 0;
  padding: 8px 12px;
  border-radius: 6px;
  transition: background-color 0.2s;
}

.el-checkbox:hover {
  background-color: #f5f7fa;
}
</style>