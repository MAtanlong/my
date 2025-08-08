<template>
  <div class="role-list-page">
    <!-- 页面标题和操作栏 -->
    <div class="page-header flex items-center justify-between mb-6">
      <div>
        <h1 class="text-2xl font-bold text-gray-800 mb-2">{{ t('role.title') }}</h1>
        <p class="text-gray-600">{{ t('role.description') }}</p>
      </div>
      <div class="flex items-center space-x-3">
        <el-button type="success" :icon="Download" @click="exportRoles">
          {{ t('role.export') }}
        </el-button>
        <el-button type="primary" :icon="Plus" @click="handleAdd">
          {{ t('role.add') }}
        </el-button>
      </div>
    </div>

    <!-- 搜索和筛选区域 -->
    <div class="search-section bg-white rounded-lg shadow-sm border border-gray-200 p-6 mb-6">
      <el-form :model="searchForm" :inline="true" class="search-form">
        <el-form-item :label="t('role.name')">
          <el-input
            v-model="searchForm.name"
            :placeholder="t('role.namePlaceholder')"
            clearable
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item :label="t('role.code')">
          <el-input
            v-model="searchForm.code"
            :placeholder="t('role.codePlaceholder')"
            clearable
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item :label="t('common.status')">
          <el-select v-model="searchForm.status" :placeholder="t('common.selectStatus')" clearable style="width: 120px">
            <el-option :label="t('common.enabled')" value="1" />
            <el-option :label="t('common.disabled')" value="0" />
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

    <!-- 角色列表 -->
    <div class="table-section bg-white rounded-lg shadow-sm border border-gray-200">
      <div class="table-header flex items-center justify-between p-4 border-b border-gray-200">
        <div class="flex items-center space-x-4">
          <span class="text-sm text-gray-600">{{ t('common.totalRecords', { total: pagination.total }) }}</span>
          <el-button
            v-if="selectedRoles.length > 0"
            type="danger"
            size="small"
            :icon="Delete"
            @click="handleBatchDelete"
          >
            {{ t('common.batchDelete') }} ({{ selectedRoles.length }})
          </el-button>
        </div>
        <div class="flex items-center space-x-2">
          <el-tooltip :content="t('common.refresh')">
            <el-button :icon="Refresh" size="small" @click="loadRoleList" />
          </el-tooltip>
        </div>
      </div>

      <el-table
        v-loading="loading"
        :data="roleList"
        @selection-change="handleSelectionChange"
        stripe
        style="width: 100%"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="ID" width="80" sortable />
        <el-table-column prop="name" :label="t('role.name')" min-width="150" sortable />
        <el-table-column prop="code" :label="t('role.code')" min-width="150" sortable>
          <template #default="{ row }">
            <el-tag :type="getRoleTagType(row.code)" size="small">
              {{ row.code }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column :label="t('role.permissionCount')" width="120">
          <template #default="{ row }">
            <div class="flex items-center justify-center">
              <el-tag type="info" size="small">
                {{ t('role.permissionCountText', { count: row.permissions?.length || 0 }) }}
              </el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column :label="t('role.userCount')" width="120">
          <template #default="{ row }">
            <div class="flex items-center justify-center">
              <el-tag type="success" size="small">
                {{ t('role.userCountText', { count: row.userCount || 0 }) }}
              </el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column :label="t('common.status')" width="100">
          <template #default="{ row }">
            <el-switch
              v-model="row.status"
              :active-value="1"
              :inactive-value="0"
              @change="handleStatusChange(row)"
            />
          </template>
        </el-table-column>
        <el-table-column prop="sort" :label="t('common.sort')" width="100" sortable />
        <el-table-column prop="createTime" :label="t('common.createTime')" min-width="160" sortable />
        <el-table-column prop="remark" :label="t('common.remark')" min-width="200" show-overflow-tooltip />
        <el-table-column :label="t('common.actions')" width="250" fixed="right">
          <template #default="{ row }">
            <div class="flex items-center space-x-2">
              <el-tooltip :content="t('common.view')">
                <el-button type="primary" :icon="View" size="small" @click="handleView(row)" />
              </el-tooltip>
              <el-tooltip :content="t('common.edit')">
                <el-button type="warning" :icon="Edit" size="small" @click="handleEdit(row)" />
              </el-tooltip>
              <el-tooltip :content="t('role.configPermission')">
                <el-button type="success" :icon="Key" size="small" @click="handlePermission(row)" />
              </el-tooltip>
              <el-tooltip :content="t('role.copy')">
                <el-button type="info" :icon="CopyDocument" size="small" @click="handleCopy(row)" />
              </el-tooltip>
              <el-tooltip :content="t('common.delete')">
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

    <!-- 角色表单对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="600px"
      :close-on-click-modal="false"
    >
      <el-form
        ref="roleFormRef"
        :model="roleForm"
        :rules="roleFormRules"
        label-width="100px"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item :label="t('role.name')" prop="name">
              <el-input v-model="roleForm.name" :placeholder="t('role.namePlaceholder')" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="t('role.code')" prop="code">
              <el-input v-model="roleForm.code" :placeholder="t('role.codePlaceholder')" :disabled="isEdit" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item :label="t('common.sort')" prop="sort">
              <el-input-number v-model="roleForm.sort" :min="0" :max="999" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="t('common.status')" prop="status">
              <el-switch
                v-model="roleForm.status"
                :active-value="1"
                :inactive-value="0"
                :active-text="t('common.enabled')"
                :inactive-text="t('common.disabled')"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item :label="t('common.remark')">
          <el-input
            v-model="roleForm.remark"
            type="textarea"
            :rows="3"
            :placeholder="t('role.remarkPlaceholder')"
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

    <!-- 权限配置对话框 -->
    <el-dialog
      v-model="permissionDialogVisible"
      :title="t('role.configPermission')"
      width="800px"
      :close-on-click-modal="false"
    >
      <div class="mb-4">
        <p class="text-sm text-gray-600 mb-2">{{ t('role.roleInfo', { name: currentRole?.name, code: currentRole?.code }) }}</p>
        <p class="text-sm text-gray-600">{{ t('role.selectPermissions') }}</p>
      </div>
      
      <div class="permission-tree-container">
        <div class="tree-header flex items-center justify-between mb-4">
          <div class="flex items-center space-x-4">
            <el-button size="small" @click="expandAll">{{ t('common.expandAll') }}</el-button>
            <el-button size="small" @click="collapseAll">{{ t('common.collapseAll') }}</el-button>
            <el-button size="small" type="primary" @click="checkAll">{{ t('common.selectAll') }}</el-button>
            <el-button size="small" @click="uncheckAll">{{ t('common.unselectAll') }}</el-button>
          </div>
          <div class="text-sm text-gray-600">
            {{ t('role.selectedPermissions', { count: checkedPermissions.length }) }}
          </div>
        </div>
        
        <el-tree
          ref="permissionTreeRef"
          :data="permissionTree"
          :props="treeProps"
          show-checkbox
          node-key="id"
          :default-checked-keys="checkedPermissions"
          @check="handlePermissionCheck"
          class="permission-tree"
        >
          <template #default="{ node, data }">
            <div class="tree-node flex items-center justify-between w-full">
              <div class="flex items-center">
                <el-icon class="mr-2" :class="getPermissionIcon(data.type)">
                  <component :is="getPermissionIconComponent(data.type)" />
                </el-icon>
                <span>{{ data.name }}</span>
                <el-tag v-if="data.code" size="small" class="ml-2" type="info">
                  {{ data.code }}
                </el-tag>
              </div>
              <div class="flex items-center space-x-2">
                <el-tag :type="getPermissionTypeTag(data.type)" size="small">
                  {{ getPermissionTypeName(data.type) }}
                </el-tag>
              </div>
            </div>
          </template>
        </el-tree>
      </div>
      
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="permissionDialogVisible = false">{{ t('common.cancel') }}</el-button>
          <el-button type="primary" @click="handlePermissionSubmit" :loading="permissionSubmitLoading">
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
  Key,
  CopyDocument,
  Menu,
  Document,
  Operation,
  Files
} from '@element-plus/icons-vue'

const { t } = useI18n()

// 响应式数据
const loading = ref(false)
const dialogVisible = ref(false)
const permissionDialogVisible = ref(false)
const submitLoading = ref(false)
const permissionSubmitLoading = ref(false)
const isEdit = ref(false)
const roleFormRef = ref<FormInstance>()
const permissionTreeRef = ref()
const currentRole = ref<any>(null)
const selectedRoles = ref<any[]>([])
const checkedPermissions = ref<number[]>([])

// 搜索表单
const searchForm = reactive({
  name: '',
  code: '',
  status: ''
})

// 分页信息
const pagination = reactive({
  current: 1,
  size: 20,
  total: 0
})

// 角色表单
const roleForm = reactive({
  id: null,
  name: '',
  code: '',
  sort: 0,
  status: 1,
  remark: ''
})

// 表单验证规则
const roleFormRules: FormRules = {
  name: [
    { required: true, message: () => t('role.validation.nameRequired'), trigger: 'blur' },
    { min: 2, max: 50, message: () => t('role.validation.nameLength'), trigger: 'blur' }
  ],
  code: [
    { required: true, message: () => t('role.validation.codeRequired'), trigger: 'blur' },
    { pattern: /^[A-Z_]+$/, message: () => t('role.validation.codeFormat'), trigger: 'blur' }
  ]
}

// 树形组件属性
const treeProps = {
  children: 'children',
  label: 'name',
  disabled: 'disabled'
}

// 角色列表数据
const roleList = ref([
  {
    id: 1,
    name: '超级管理员',
    code: 'SUPER_ADMIN',
    sort: 1,
    status: 1,
    userCount: 1,
    permissions: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
    createTime: '2024-01-01 00:00:00',
    remark: '系统超级管理员，拥有所有权限'
  },
  {
    id: 2,
    name: '普通用户',
    code: 'USER',
    sort: 10,
    status: 1,
    userCount: 15,
    permissions: [1, 2, 3],
    createTime: '2024-01-02 10:00:00',
    remark: '普通用户角色，基础权限'
  },
  {
    id: 3,
    name: '部门管理员',
    code: 'DEPT_ADMIN',
    sort: 5,
    status: 1,
    userCount: 3,
    permissions: [1, 2, 3, 4, 5, 6],
    createTime: '2024-01-03 14:30:00',
    remark: '部门管理员，管理本部门用户'
  },
  {
    id: 4,
    name: '审核员',
    code: 'AUDITOR',
    sort: 8,
    status: 0,
    userCount: 2,
    permissions: [1, 2, 7, 8],
    createTime: '2024-01-04 09:15:00',
    remark: '审核员角色，负责内容审核'
  }
])

// 权限树数据
const permissionTree = ref([
  {
    id: 1,
    name: '系统管理',
    code: 'SYSTEM',
    type: 'module',
    children: [
      {
        id: 2,
        name: '用户管理',
        code: 'USER_MANAGE',
        type: 'menu',
        children: [
          { id: 3, name: '查看用户', code: 'USER_VIEW', type: 'button' },
          { id: 4, name: '新增用户', code: 'USER_ADD', type: 'button' },
          { id: 5, name: '编辑用户', code: 'USER_EDIT', type: 'button' },
          { id: 6, name: '删除用户', code: 'USER_DELETE', type: 'button' }
        ]
      },
      {
        id: 7,
        name: '角色管理',
        code: 'ROLE_MANAGE',
        type: 'menu',
        children: [
          { id: 8, name: '查看角色', code: 'ROLE_VIEW', type: 'button' },
          { id: 9, name: '新增角色', code: 'ROLE_ADD', type: 'button' },
          { id: 10, name: '编辑角色', code: 'ROLE_EDIT', type: 'button' },
          { id: 11, name: '删除角色', code: 'ROLE_DELETE', type: 'button' },
          { id: 12, name: '分配权限', code: 'ROLE_PERMISSION', type: 'button' }
        ]
      }
    ]
  },
  {
    id: 13,
    name: '内容管理',
    code: 'CONTENT',
    type: 'module',
    children: [
      {
        id: 14,
        name: '文章管理',
        code: 'ARTICLE_MANAGE',
        type: 'menu',
        children: [
          { id: 15, name: '查看文章', code: 'ARTICLE_VIEW', type: 'button' },
          { id: 16, name: '发布文章', code: 'ARTICLE_PUBLISH', type: 'button' },
          { id: 17, name: '编辑文章', code: 'ARTICLE_EDIT', type: 'button' },
          { id: 18, name: '删除文章', code: 'ARTICLE_DELETE', type: 'button' }
        ]
      }
    ]
  }
])

// 计算属性
const dialogTitle = computed(() => {
  return isEdit.value ? t('role.edit') : t('role.add')
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

// 获取权限类型标签
const getPermissionTypeTag = (type: string) => {
  const typeMap: Record<string, string> = {
    'module': 'danger',
    'menu': 'warning',
    'button': 'success'
  }
  return typeMap[type] || 'info'
}

// 获取权限类型名称
const getPermissionTypeName = (type: string) => {
  const nameMap: Record<string, string> = {
    'module': t('permission.module'),
    'menu': t('permission.menu'),
    'button': t('permission.button')
  }
  return nameMap[type] || t('common.unknown')
}

// 获取权限图标
const getPermissionIcon = (type: string) => {
  const iconMap: Record<string, string> = {
    'module': 'text-blue-500',
    'menu': 'text-orange-500',
    'button': 'text-green-500'
  }
  return iconMap[type] || 'text-gray-500'
}

// 获取权限图标组件
const getPermissionIconComponent = (type: string) => {
  const componentMap: Record<string, any> = {
    'module': Files,
    'menu': Menu,
    'button': Operation
  }
  return componentMap[type] || Document
}

// 方法
const loadRoleList = async () => {
  loading.value = true
  try {
    // 模拟API调用
    await new Promise(resolve => setTimeout(resolve, 500))
    pagination.total = roleList.value.length
    ElMessage.success(t('role.messages.loadSuccess'))
  } catch (error) {
    ElMessage.error(t('role.messages.loadFailed'))
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  pagination.current = 1
  loadRoleList()
}

const handleReset = () => {
  Object.assign(searchForm, {
    name: '',
    code: '',
    status: ''
  })
  handleSearch()
}

const handleAdd = () => {
  isEdit.value = false
  resetRoleForm()
  dialogVisible.value = true
}

const handleEdit = (row: any) => {
  isEdit.value = true
  Object.assign(roleForm, { ...row })
  dialogVisible.value = true
}

const handleView = (row: any) => {
  ElMessage.info(t('role.messages.viewRole', { name: row.name }))
}

const handleCopy = (row: any) => {
  isEdit.value = false
  Object.assign(roleForm, {
    ...row,
    id: null,
    name: `${row.name}_副本`,
    code: `${row.code}_COPY`
  })
  dialogVisible.value = true
}

const handleDelete = async (row: any) => {
  if (row.userCount > 0) {
    ElMessage.warning(t('role.messages.cannotDeleteWithUsers'))
    return
  }
  
  try {
    await ElMessageBox.confirm(
      t('role.messages.deleteConfirm', { name: row.name }),
      t('common.deleteConfirm'),
      {
        confirmButtonText: t('common.confirm'),
        cancelButtonText: t('common.cancel'),
        type: 'warning'
      }
    )
    
    // 模拟删除操作
    const index = roleList.value.findIndex(role => role.id === row.id)
    if (index > -1) {
      roleList.value.splice(index, 1)
      pagination.total--
      ElMessage.success(t('common.deleteSuccess'))
    }
  } catch {
    ElMessage.info(t('common.deleteCanceled'))
  }
}

const handleBatchDelete = async () => {
  const hasUsersRoles = selectedRoles.value.filter(role => role.userCount > 0)
  if (hasUsersRoles.length > 0) {
    ElMessage.warning(t('role.messages.batchDeleteWithUsers'))
    return
  }
  
  try {
    await ElMessageBox.confirm(
      t('role.messages.batchDeleteConfirm', { count: selectedRoles.value.length }),
      t('common.batchDeleteConfirm'),
      {
        confirmButtonText: t('common.confirm'),
        cancelButtonText: t('common.cancel'),
        type: 'warning'
      }
    )
    
    // 模拟批量删除
    const selectedIds = selectedRoles.value.map(role => role.id)
    roleList.value = roleList.value.filter(role => !selectedIds.includes(role.id))
    pagination.total -= selectedRoles.value.length
    selectedRoles.value = []
    ElMessage.success(t('common.batchDeleteSuccess'))
  } catch {
    ElMessage.info(t('common.deleteCanceled'))
  }
}

const handleStatusChange = async (row: any) => {
  try {
    // 模拟状态更新
    await new Promise(resolve => setTimeout(resolve, 300))
    ElMessage.success(t('role.messages.statusChanged', { status: row.status ? t('common.enabled') : t('common.disabled') }))
  } catch (error) {
    // 恢复原状态
    row.status = row.status ? 0 : 1
    ElMessage.error(t('role.messages.statusChangeFailed'))
  }
}

const handlePermission = (row: any) => {
  currentRole.value = row
  checkedPermissions.value = row.permissions || []
  permissionDialogVisible.value = true
  
  // 设置树的选中状态
  setTimeout(() => {
    if (permissionTreeRef.value) {
      permissionTreeRef.value.setCheckedKeys(checkedPermissions.value)
    }
  }, 100)
}

const handleSelectionChange = (selection: any[]) => {
  selectedRoles.value = selection
}

const handleSizeChange = (size: number) => {
  pagination.size = size
  loadRoleList()
}

const handleCurrentChange = (current: number) => {
  pagination.current = current
  loadRoleList()
}

const resetRoleForm = () => {
  Object.assign(roleForm, {
    id: null,
    name: '',
    code: '',
    sort: 0,
    status: 1,
    remark: ''
  })
  roleFormRef.value?.clearValidate()
}

const handleSubmit = async () => {
  if (!roleFormRef.value) return
  
  try {
    await roleFormRef.value.validate()
    submitLoading.value = true
    
    // 模拟提交
    await new Promise(resolve => setTimeout(resolve, 1000))
    
    if (isEdit.value) {
      // 更新角色
      const index = roleList.value.findIndex(role => role.id === roleForm.id)
      if (index > -1) {
        Object.assign(roleList.value[index], roleForm)
      }
      ElMessage.success(t('role.messages.updateSuccess'))
    } else {
      // 新增角色
      const newRole = {
        ...roleForm,
        id: Date.now(),
        userCount: 0,
        permissions: [],
        createTime: new Date().toLocaleString()
      }
      roleList.value.unshift(newRole)
      pagination.total++
      ElMessage.success(t('role.messages.createSuccess'))
    }
    
    dialogVisible.value = false
    resetRoleForm()
  } catch (error) {
    console.error('表单验证失败:', error)
  } finally {
    submitLoading.value = false
  }
}

// 权限树操作
const expandAll = () => {
  const allKeys = getAllNodeKeys(permissionTree.value)
  permissionTreeRef.value?.setExpandedKeys(allKeys)
}

const collapseAll = () => {
  permissionTreeRef.value?.setExpandedKeys([])
}

const checkAll = () => {
  const allKeys = getAllNodeKeys(permissionTree.value)
  permissionTreeRef.value?.setCheckedKeys(allKeys)
  checkedPermissions.value = allKeys
}

const uncheckAll = () => {
  permissionTreeRef.value?.setCheckedKeys([])
  checkedPermissions.value = []
}

const getAllNodeKeys = (nodes: any[]): number[] => {
  const keys: number[] = []
  const traverse = (nodeList: any[]) => {
    nodeList.forEach(node => {
      keys.push(node.id)
      if (node.children && node.children.length > 0) {
        traverse(node.children)
      }
    })
  }
  traverse(nodes)
  return keys
}

const handlePermissionCheck = (data: any, checked: any) => {
  checkedPermissions.value = checked.checkedKeys
}

const handlePermissionSubmit = async () => {
  if (!currentRole.value) return
  
  try {
    permissionSubmitLoading.value = true
    
    // 模拟权限配置
    await new Promise(resolve => setTimeout(resolve, 500))
    
    // 更新角色权限
    const role = roleList.value.find(r => r.id === currentRole.value.id)
    if (role) {
      role.permissions = [...checkedPermissions.value]
    }
    
    ElMessage.success(t('role.messages.permissionSaveSuccess'))
    permissionDialogVisible.value = false
  } catch (error) {
    ElMessage.error(t('role.messages.permissionSaveFailed'))
  } finally {
    permissionSubmitLoading.value = false
  }
}

const exportRoles = () => {
  ElMessage.info(t('role.messages.exportInDevelopment'))
}

// 组件挂载时加载数据
onMounted(() => {
  loadRoleList()
})
</script>

<style scoped>
.role-list-page {
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

.permission-tree-container {
  max-height: 400px;
  overflow-y: auto;
  border: 1px solid #e4e7ed;
  border-radius: 4px;
  padding: 12px;
}

.permission-tree {
  background: transparent;
}

.tree-node {
  padding: 4px 0;
}

.tree-header {
  position: sticky;
  top: 0;
  background: white;
  z-index: 10;
  padding-bottom: 8px;
  border-bottom: 1px solid #e4e7ed;
}
</style>