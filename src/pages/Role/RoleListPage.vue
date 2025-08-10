<template>
  <div class="role-list-page">
    <!-- 页面标题 -->
    <div class="page-header">
      <h2>{{ t('role.title') }}</h2>
      <p class="page-description">{{ t('role.description') }}</p>
    </div>

    <!-- 搜索和操作区域 -->
    <div class="search-section">
      <el-form :model="searchForm" inline class="search-form">
        <el-form-item :label="t('role.name')">
          <el-input
            v-model="searchForm.name"
            :placeholder="t('role.searchPlaceholder')"
            clearable
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item :label="t('role.status')">
          <el-select
            v-model="searchForm.status"
            :placeholder="t('common.pleaseSelect')"
            clearable
            style="width: 120px"
          >
            <el-option :label="t('common.enabled')" :value="1" />
            <el-option :label="t('common.disabled')" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">
            <el-icon><Search /></el-icon>
            {{ t('common.search') }}
          </el-button>
          <el-button @click="handleReset">
            <el-icon><Refresh /></el-icon>
            {{ t('common.reset') }}
          </el-button>
        </el-form-item>
      </el-form>

      <div class="action-buttons">
        <el-button type="primary" @click="handleAdd">
          <el-icon><Plus /></el-icon>
          {{ t('role.add') }}
        </el-button>
        <el-button
          type="danger"
          :disabled="selectedRoles.length === 0"
          @click="handleBatchDelete"
        >
          <el-icon><Delete /></el-icon>
          {{ t('common.batchDelete') }}
        </el-button>
        <el-button @click="handleExport">
          <el-icon><Download /></el-icon>
          {{ t('common.export') }}
        </el-button>
      </div>
    </div>

    <!-- 角色列表表格 -->
    <div class="table-section">
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
            <el-tag type="info" size="small">
              {{ row.permissions?.length || 0 }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column :label="t('role.userCount')" width="100">
          <template #default="{ row }">
            <span>{{ row.userCount || 0 }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="sort" :label="t('common.sort')" width="80" sortable />
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
        <el-table-column prop="createTime" :label="t('common.createTime')" width="180" sortable />
        <el-table-column :label="t('common.actions')" width="280" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleView(row)">
              <el-icon><View /></el-icon>
              {{ t('common.view') }}
            </el-button>
            <el-button type="success" size="small" @click="handleEdit(row)">
              <el-icon><Edit /></el-icon>
              {{ t('common.edit') }}
            </el-button>
            <el-button type="warning" size="small" @click="handleAssignPermissions(row)">
              <el-icon><Key /></el-icon>
              {{ t('role.assignPermissions') }}
            </el-button>
            <el-button type="info" size="small" @click="handleCopy(row)">
              <el-icon><CopyDocument /></el-icon>
              {{ t('common.copy') }}
            </el-button>
            <el-button type="danger" size="small" @click="handleDelete(row)">
              <el-icon><Delete /></el-icon>
              {{ t('common.delete') }}
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination-section">
        <el-pagination
          v-model:current-page="pagination.page"
          v-model:page-size="pagination.size"
          :total="pagination.total"
          :page-sizes="[10, 20, 50, 100]"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="loadRoleList"
          @current-change="loadRoleList"
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
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="100px"
      >
        <el-form-item :label="t('role.name')" prop="name">
          <el-input v-model="formData.name" :placeholder="t('role.namePlaceholder')" />
        </el-form-item>
        <el-form-item :label="t('role.code')" prop="code">
          <el-input
            v-model="formData.code"
            :placeholder="t('role.codePlaceholder')"
            :disabled="isEdit"
          />
        </el-form-item>
        <el-form-item :label="t('common.sort')" prop="sort">
          <el-input-number
            v-model="formData.sort"
            :min="0"
            :max="999"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item :label="t('common.status')" prop="status">
          <el-radio-group v-model="formData.status">
            <el-radio :label="1">{{ t('common.enabled') }}</el-radio>
            <el-radio :label="0">{{ t('common.disabled') }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item :label="t('common.remark')" prop="remark">
          <el-input
            v-model="formData.remark"
            type="textarea"
            :rows="3"
            :placeholder="t('common.remarkPlaceholder')"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">{{ t('common.cancel') }}</el-button>
        <el-button type="primary" @click="handleSubmit" :loading="submitting">
          {{ t('common.confirm') }}
        </el-button>
      </template>
    </el-dialog>

    <!-- 权限分配对话框 -->
    <el-dialog
      v-model="permissionDialogVisible"
      :title="t('role.assignPermissions')"
      width="800px"
      :close-on-click-modal="false"
    >
      <div class="permission-section">
        <div class="permission-header">
          <span>{{ t('role.selectPermissions') }}</span>
          <div class="permission-actions">
            <el-button size="small" @click="expandAll">
              {{ t('role.expandAll') }}
            </el-button>
            <el-button size="small" @click="collapseAll">
              {{ t('role.collapseAll') }}
            </el-button>
            <el-button size="small" @click="selectAll">
              {{ t('role.selectAll') }}
            </el-button>
            <el-button size="small" @click="unselectAll">
              {{ t('role.unselectAll') }}
            </el-button>
          </div>
        </div>
        <el-tree
          ref="permissionTreeRef"
          :data="permissionTree"
          :props="treeProps"
          show-checkbox
          node-key="id"
          :default-checked-keys="selectedPermissions"
          class="permission-tree"
        />
      </div>
      <template #footer>
        <el-button @click="permissionDialogVisible = false">{{ t('common.cancel') }}</el-button>
        <el-button type="primary" @click="handlePermissionSubmit" :loading="submitting">
          {{ t('common.confirm') }}
        </el-button>
      </template>
    </el-dialog>

    <!-- 角色详情对话框 -->
    <el-dialog
      v-model="viewDialogVisible"
      :title="t('role.details')"
      width="600px"
    >
      <el-descriptions :column="2" border>
        <el-descriptions-item :label="t('role.name')">
          {{ viewData.name }}
        </el-descriptions-item>
        <el-descriptions-item :label="t('role.code')">
          <el-tag :type="getRoleTagType(viewData.code)" size="small">
            {{ viewData.code }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item :label="t('common.sort')">
          {{ viewData.sort }}
        </el-descriptions-item>
        <el-descriptions-item :label="t('common.status')">
          <el-tag :type="viewData.status === 1 ? 'success' : 'danger'" size="small">
            {{ viewData.status === 1 ? t('common.enabled') : t('common.disabled') }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item :label="t('role.permissionCount')">
          {{ viewData.permissions?.length || 0 }}
        </el-descriptions-item>
        <el-descriptions-item :label="t('role.userCount')">
          {{ viewData.userCount || 0 }}
        </el-descriptions-item>
        <el-descriptions-item :label="t('common.createTime')" :span="2">
          {{ viewData.createTime }}
        </el-descriptions-item>
        <el-descriptions-item :label="t('common.remark')" :span="2">
          {{ viewData.remark || t('common.none') }}
        </el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, computed } from 'vue'
import { ElMessage, ElMessageBox, ElTree } from 'element-plus'
import {
  Search,
  Refresh,
  Plus,
  Delete,
  Download,
  View,
  Edit,
  Key,
  CopyDocument
} from '@element-plus/icons-vue'
import { useI18n } from 'vue-i18n'
import { roleApi } from '../../api/role'
import { permissionApi } from '../../api/permission'

const { t } = useI18n()

// 响应式数据
const loading = ref(false)
const submitting = ref(false)
const dialogVisible = ref(false)
const permissionDialogVisible = ref(false)
const viewDialogVisible = ref(false)
const isEdit = ref(false)
const selectedRoles = ref([])
const selectedPermissions = ref([])
const currentRole = ref(null)

// 表单引用
const formRef = ref()
const permissionTreeRef = ref<InstanceType<typeof ElTree>>()

// 搜索表单
const searchForm = reactive({
  name: '',
  status: ''
})

// 分页数据
const pagination = reactive({
  page: 1,
  size: 10,
  total: 0
})

// 表单数据
const formData = reactive({
  id: null,
  name: '',
  code: '',
  sort: 0,
  status: 1,
  remark: ''
})

// 查看数据
const viewData = reactive({
  id: null,
  name: '',
  code: '',
  sort: 0,
  status: 1,
  permissions: [],
  userCount: 0,
  createTime: '',
  remark: ''
})

// 表单验证规则
const formRules = {
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
const roleList = ref([])

// 权限树数据
const permissionTree = ref([])

// 计算属性
const dialogTitle = computed(() => {
  return isEdit.value ? t('role.edit') : t('role.add')
})

// 获取角色标签类型
const getRoleTagType = (code: string) => {
  const typeMap: Record<string, string> = {
    SUPER_ADMIN: 'danger',
    ADMIN: 'warning',
    MANAGER: 'success',
    USER: 'info'
  }
  return typeMap[code] || 'info'
}

// 加载角色列表
const loadRoleList = async () => {
  try {
    loading.value = true
    const params = {
      page: pagination.page,
      size: pagination.size,
      name: searchForm.name,
      status: searchForm.status
    }
    const response = await roleApi.getRoleList(params)
    roleList.value = response.data.records
    pagination.total = response.data.total
  } catch (error) {
    console.error('加载角色列表失败:', error)
    ElMessage.error('加载角色列表失败')
  } finally {
    loading.value = false
  }
}

// 加载权限树
const loadPermissionTree = async () => {
  try {
    const response = await permissionApi.getPermissionTree()
    permissionTree.value = response.data
  } catch (error) {
    console.error('加载权限树失败:', error)
    ElMessage.error('加载权限树失败')
  }
}

// 搜索
const handleSearch = () => {
  pagination.page = 1
  loadRoleList()
}

// 重置
const handleReset = () => {
  searchForm.name = ''
  searchForm.status = ''
  pagination.page = 1
  loadRoleList()
}

// 添加角色
const handleAdd = () => {
  isEdit.value = false
  Object.assign(formData, {
    id: null,
    name: '',
    code: '',
    sort: 0,
    status: 1,
    remark: ''
  })
  dialogVisible.value = true
}

// 编辑角色
const handleEdit = (row: any) => {
  isEdit.value = true
  Object.assign(formData, { ...row })
  dialogVisible.value = true
}

// 查看角色
const handleView = (row: any) => {
  Object.assign(viewData, { ...row })
  viewDialogVisible.value = true
}

// 复制角色
const handleCopy = (row: any) => {
  isEdit.value = false
  Object.assign(formData, {
    ...row,
    id: null,
    name: `${row.name}_copy`,
    code: `${row.code}_COPY`
  })
  dialogVisible.value = true
}

// 删除角色
const handleDelete = async (row: any) => {
  try {
    await ElMessageBox.confirm(
      t('role.deleteConfirm', { name: row.name }),
      t('common.warning'),
      {
        confirmButtonText: t('common.confirm'),
        cancelButtonText: t('common.cancel'),
        type: 'warning'
      }
    )
    
    await roleApi.deleteRole(row.id)
    ElMessage.success(t('common.deleteSuccess'))
    await loadRoleList()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除角色失败:', error)
      ElMessage.error(t('common.deleteFailed'))
    }
  }
}

// 批量删除
const handleBatchDelete = async () => {
  if (selectedRoles.value.length === 0) {
    ElMessage.warning(t('common.pleaseSelectData'))
    return
  }

  try {
    await ElMessageBox.confirm(
      t('role.batchDeleteConfirm', { count: selectedRoles.value.length }),
      t('common.warning'),
      {
        confirmButtonText: t('common.confirm'),
        cancelButtonText: t('common.cancel'),
        type: 'warning'
      }
    )

    const ids = selectedRoles.value.map((role: any) => role.id)
    await roleApi.batchDeleteRoles(ids)
    ElMessage.success(t('common.deleteSuccess'))
    selectedRoles.value = []
    await loadRoleList()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('批量删除角色失败:', error)
      ElMessage.error(t('common.deleteFailed'))
    }
  }
}

// 状态变更
const handleStatusChange = async (row: any) => {
  try {
    await roleApi.updateRoleStatus(row.id, row.status)
    ElMessage.success(t('common.updateSuccess'))
  } catch (error) {
    console.error('更新角色状态失败:', error)
    ElMessage.error(t('common.updateFailed'))
    // 恢复原状态
    row.status = row.status === 1 ? 0 : 1
  }
}

// 表格选择变更
const handleSelectionChange = (selection: any[]) => {
  selectedRoles.value = selection
}

// 分配权限
const handleAssignPermissions = (row: any) => {
  currentRole.value = row
  selectedPermissions.value = row.permissions || []
  permissionDialogVisible.value = true
}

// 提交表单
const handleSubmit = async () => {
  if (!formRef.value) return

  try {
    await formRef.value.validate()
    submitting.value = true

    if (isEdit.value) {
      await roleApi.updateRole(formData.id, formData)
      ElMessage.success(t('common.updateSuccess'))
    } else {
      await roleApi.createRole(formData)
      ElMessage.success(t('common.createSuccess'))
    }

    dialogVisible.value = false
    await loadRoleList()
  } catch (error) {
    console.error('提交角色失败:', error)
    ElMessage.error(isEdit.value ? t('common.updateFailed') : t('common.createFailed'))
  } finally {
    submitting.value = false
  }
}

// 权限树操作
const expandAll = () => {
  const tree = permissionTreeRef.value
  if (tree) {
    const allKeys = getAllNodeKeys(permissionTree.value)
    allKeys.forEach(key => tree.setExpanded(key, true))
  }
}

const collapseAll = () => {
  const tree = permissionTreeRef.value
  if (tree) {
    const allKeys = getAllNodeKeys(permissionTree.value)
    allKeys.forEach(key => tree.setExpanded(key, false))
  }
}

const selectAll = () => {
  const tree = permissionTreeRef.value
  if (tree) {
    const allKeys = getAllNodeKeys(permissionTree.value)
    tree.setCheckedKeys(allKeys)
  }
}

const unselectAll = () => {
  const tree = permissionTreeRef.value
  if (tree) {
    tree.setCheckedKeys([])
  }
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

// 提交权限分配
const handlePermissionSubmit = async () => {
  if (!currentRole.value) return

  try {
    submitting.value = true
    const tree = permissionTreeRef.value
    const checkedKeys = tree?.getCheckedKeys() || []
    
    await roleApi.assignPermissions(currentRole.value.id, checkedKeys)
    ElMessage.success(t('role.assignSuccess'))
    permissionDialogVisible.value = false
    await loadRoleList()
  } catch (error) {
    console.error('分配权限失败:', error)
    ElMessage.error(t('role.assignFailed'))
  } finally {
    submitting.value = false
  }
}

// 导出
const handleExport = () => {
  ElMessage.info(t('common.exportTip'))
}

// 组件挂载时加载数据
onMounted(() => {
  loadRoleList()
  loadPermissionTree()
})
</script>

<style scoped>
.role-list-page {
  padding: 20px;
  background-color: #f5f5f5;
  min-height: 100vh;
}

.page-header {
  margin-bottom: 20px;
  padding: 20px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.page-header h2 {
  margin: 0 0 8px 0;
  color: #303133;
  font-size: 24px;
  font-weight: 600;
}

.page-description {
  margin: 0;
  color: #606266;
  font-size: 14px;
}

.search-section {
  margin-bottom: 20px;
  padding: 20px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  flex-wrap: wrap;
  gap: 16px;
}

.search-form {
  flex: 1;
  min-width: 300px;
}

.action-buttons {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.table-section {
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.pagination-section {
  padding: 20px;
  display: flex;
  justify-content: center;
  border-top: 1px solid #ebeef5;
}

.permission-section {
  max-height: 400px;
  overflow-y: auto;
}

.permission-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
  padding-bottom: 12px;
  border-bottom: 1px solid #ebeef5;
}

.permission-actions {
  display: flex;
  gap: 8px;
}

.permission-tree {
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  padding: 12px;
  background-color: #fafafa;
}

@media (max-width: 768px) {
  .role-list-page {
    padding: 10px;
  }
  
  .search-section {
    flex-direction: column;
    align-items: stretch;
  }
  
  .action-buttons {
    justify-content: flex-start;
  }
  
  .permission-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
  }
  
  .permission-actions {
    flex-wrap: wrap;
  }
}
</style>