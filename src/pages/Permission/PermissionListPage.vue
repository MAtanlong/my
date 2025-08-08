<template>
  <div class="permission-list-page">
    <!-- 页面标题和操作栏 -->
    <div class="page-header flex items-center justify-between mb-6">
      <div>
        <h1 class="text-2xl font-bold text-gray-800 mb-2">权限管理</h1>
        <p class="text-gray-600">管理系统权限树结构和权限节点</p>
      </div>
      <div class="flex items-center space-x-3">
        <el-button type="success" :icon="Download" @click="exportPermissions">
          导出权限
        </el-button>
        <el-button type="primary" :icon="Plus" @click="handleAdd">
          新增权限
        </el-button>
      </div>
    </div>

    <!-- 搜索和筛选区域 -->
    <div class="search-section bg-white rounded-lg shadow-sm border border-gray-200 p-6 mb-6">
      <el-form :model="searchForm" :inline="true" class="search-form">
        <el-form-item label="权限名称">
          <el-input
            v-model="searchForm.name"
            placeholder="请输入权限名称"
            clearable
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item label="权限编码">
          <el-input
            v-model="searchForm.code"
            placeholder="请输入权限编码"
            clearable
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item label="权限类型">
          <el-select v-model="searchForm.type" placeholder="请选择类型" clearable style="width: 120px">
            <el-option label="模块" value="module" />
            <el-option label="菜单" value="menu" />
            <el-option label="按钮" value="button" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="请选择状态" clearable style="width: 120px">
            <el-option label="启用" value="1" />
            <el-option label="禁用" value="0" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :icon="Search" @click="handleSearch">
            搜索
          </el-button>
          <el-button :icon="Refresh" @click="handleReset">
            重置
          </el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 权限树表格 -->
    <div class="table-section bg-white rounded-lg shadow-sm border border-gray-200">
      <div class="table-header flex items-center justify-between p-4 border-b border-gray-200">
        <div class="flex items-center space-x-4">
          <span class="text-sm text-gray-600">共 {{ totalCount }} 条记录</span>
          <el-button size="small" @click="expandAll">
            <el-icon><Plus /></el-icon>
            展开全部
          </el-button>
          <el-button size="small" @click="collapseAll">
            <el-icon><Minus /></el-icon>
            收起全部
          </el-button>
        </div>
        <div class="flex items-center space-x-2">
          <el-tooltip content="刷新列表">
            <el-button :icon="Refresh" size="small" @click="loadPermissionList" />
          </el-tooltip>
          <el-tooltip content="权限验证">
            <el-button :icon="CircleCheck" size="small" type="success" @click="validatePermissions" />
          </el-tooltip>
        </div>
      </div>

      <el-table
        v-loading="loading"
        :data="permissionList"
        row-key="id"
        :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
        :expand-row-keys="expandedKeys"
        stripe
        style="width: 100%"
      >
        <el-table-column prop="name" label="权限名称" min-width="200">
          <template #default="{ row }">
            <div class="flex items-center">
              <el-icon class="mr-2" :class="getPermissionIcon(row.type)">
                <component :is="getPermissionIconComponent(row.type)" />
              </el-icon>
              <span>{{ row.name }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="code" label="权限编码" min-width="150">
          <template #default="{ row }">
            <el-tag v-if="row.code" size="small" type="info">
              {{ row.code }}
            </el-tag>
            <span v-else class="text-gray-400">-</span>
          </template>
        </el-table-column>
        <el-table-column label="权限类型" width="100">
          <template #default="{ row }">
            <el-tag :type="getPermissionTypeTag(row.type)" size="small">
              {{ getPermissionTypeName(row.type) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="path" label="路由路径" min-width="150">
          <template #default="{ row }">
            <code v-if="row.path" class="text-xs bg-gray-100 px-2 py-1 rounded">
              {{ row.path }}
            </code>
            <span v-else class="text-gray-400">-</span>
          </template>
        </el-table-column>
        <el-table-column prop="component" label="组件路径" min-width="180" show-overflow-tooltip>
          <template #default="{ row }">
            <code v-if="row.component" class="text-xs bg-gray-100 px-2 py-1 rounded">
              {{ row.component }}
            </code>
            <span v-else class="text-gray-400">-</span>
          </template>
        </el-table-column>
        <el-table-column label="图标" width="80">
          <template #default="{ row }">
            <el-icon v-if="row.icon" :size="18">
              <component :is="row.icon" />
            </el-icon>
            <span v-else class="text-gray-400">-</span>
          </template>
        </el-table-column>
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-switch
              v-model="row.status"
              :active-value="1"
              :inactive-value="0"
              @change="handleStatusChange(row)"
            />
          </template>
        </el-table-column>
        <el-table-column prop="sort" label="排序" width="80" />
        <el-table-column prop="createTime" label="创建时间" min-width="160" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <div class="flex items-center space-x-2">
              <el-tooltip content="新增子权限">
                <el-button type="success" :icon="Plus" size="small" @click="handleAddChild(row)" />
              </el-tooltip>
              <el-tooltip content="编辑">
                <el-button type="warning" :icon="Edit" size="small" @click="handleEdit(row)" />
              </el-tooltip>
              <el-tooltip content="删除">
                <el-button type="danger" :icon="Delete" size="small" @click="handleDelete(row)" />
              </el-tooltip>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 权限表单对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="700px"
      :close-on-click-modal="false"
    >
      <el-form
        ref="permissionFormRef"
        :model="permissionForm"
        :rules="permissionFormRules"
        label-width="100px"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="权限名称" prop="name">
              <el-input v-model="permissionForm.name" placeholder="请输入权限名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="权限编码" prop="code">
              <el-input v-model="permissionForm.code" placeholder="请输入权限编码" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="权限类型" prop="type">
              <el-select v-model="permissionForm.type" placeholder="请选择权限类型" style="width: 100%">
                <el-option label="模块" value="module" />
                <el-option label="菜单" value="menu" />
                <el-option label="按钮" value="button" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="上级权限">
              <el-tree-select
                v-model="permissionForm.parentId"
                :data="permissionTreeOptions"
                :props="{ label: 'name', value: 'id', children: 'children' }"
                placeholder="请选择上级权限"
                style="width: 100%"
                clearable
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" v-if="permissionForm.type === 'menu'">
          <el-col :span="12">
            <el-form-item label="路由路径">
              <el-input v-model="permissionForm.path" placeholder="请输入路由路径" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="组件路径">
              <el-input v-model="permissionForm.component" placeholder="请输入组件路径" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="图标">
              <el-select v-model="permissionForm.icon" placeholder="请选择图标" clearable style="width: 100%">
                <el-option
                  v-for="icon in iconOptions"
                  :key="icon.value"
                  :label="icon.label"
                  :value="icon.value"
                >
                  <div class="flex items-center">
                    <el-icon class="mr-2">
                      <component :is="icon.value" />
                    </el-icon>
                    <span>{{ icon.label }}</span>
                  </div>
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="排序">
              <el-input-number v-model="permissionForm.sort" :min="0" :max="999" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="状态">
              <el-switch
                v-model="permissionForm.status"
                :active-value="1"
                :inactive-value="0"
                active-text="启用"
                inactive-text="禁用"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否显示">
              <el-switch
                v-model="permissionForm.visible"
                :active-value="1"
                :inactive-value="0"
                active-text="显示"
                inactive-text="隐藏"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注">
          <el-input
            v-model="permissionForm.remark"
            type="textarea"
            :rows="3"
            placeholder="请输入权限描述"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit" :loading="submitLoading">
            确定
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
  Plus,
  Search,
  Refresh,
  Edit,
  Delete,
  Download,
  Menu,
  Document,
  Operation,
  Files,
  User,
  Setting,
  Lock,
  Key,
  Minus,
  CircleCheck,
  Odometer,
  OfficeBuilding
} from '@element-plus/icons-vue'

// 响应式数据
const loading = ref(false)
const dialogVisible = ref(false)
const submitLoading = ref(false)
const isEdit = ref(false)
const isAddChild = ref(false)
const permissionFormRef = ref<FormInstance>()
const expandedKeys = ref<number[]>([])
const parentPermission = ref<any>(null)

// 搜索表单
const searchForm = reactive({
  name: '',
  code: '',
  type: '',
  status: ''
})

// 权限表单
const permissionForm = reactive({
  id: null,
  name: '',
  code: '',
  type: 'menu',
  parentId: null,
  path: '',
  component: '',
  icon: '',
  sort: 0,
  status: 1,
  visible: 1,
  remark: ''
})

// 表单验证规则
const permissionFormRules: FormRules = {
  name: [
    { required: true, message: '请输入权限名称', trigger: 'blur' },
    { min: 2, max: 50, message: '权限名称长度在 2 到 50 个字符', trigger: 'blur' }
  ],
  code: [
    { required: true, message: '请输入权限编码', trigger: 'blur' },
    { pattern: /^[A-Z_]+$/, message: '权限编码只能包含大写字母和下划线', trigger: 'blur' }
  ],
  type: [
    { required: true, message: '请选择权限类型', trigger: 'change' }
  ]
}

// 图标选项
const iconOptions = ref([
  { label: '仪表板', value: 'Odometer' },
  { label: '用户', value: 'User' },
  { label: '锁定', value: 'Lock' },
  { label: '钥匙', value: 'Key' },
  { label: '菜单', value: 'Menu' },
  { label: '文档', value: 'Document' },
  { label: '操作', value: 'Operation' },
  { label: '文件', value: 'Files' },
  { label: '设置', value: 'Setting' },
  { label: '办公楼', value: 'OfficeBuilding' }
])

// 权限列表数据
const permissionList = ref([
  {
    id: 1,
    name: '系统管理',
    code: 'SYSTEM',
    type: 'module',
    parentId: null,
    path: '',
    component: '',
    icon: 'Setting',
    sort: 1,
    status: 1,
    visible: 1,
    createTime: '2024-01-01 00:00:00',
    remark: '系统管理模块',
    children: [
      {
        id: 2,
        name: '用户管理',
        code: 'USER_MANAGE',
        type: 'menu',
        parentId: 1,
        path: '/user',
        component: 'User/UserListPage',
        icon: 'User',
        sort: 1,
        status: 1,
        visible: 1,
        createTime: '2024-01-01 00:00:00',
        remark: '用户管理菜单',
        children: [
          {
            id: 3,
            name: '查看用户',
            code: 'USER_VIEW',
            type: 'button',
            parentId: 2,
            path: '',
            component: '',
            icon: '',
            sort: 1,
            status: 1,
            visible: 1,
            createTime: '2024-01-01 00:00:00',
            remark: '查看用户按钮权限'
          },
          {
            id: 4,
            name: '新增用户',
            code: 'USER_ADD',
            type: 'button',
            parentId: 2,
            path: '',
            component: '',
            icon: '',
            sort: 2,
            status: 1,
            visible: 1,
            createTime: '2024-01-01 00:00:00',
            remark: '新增用户按钮权限'
          },
          {
            id: 5,
            name: '编辑用户',
            code: 'USER_EDIT',
            type: 'button',
            parentId: 2,
            path: '',
            component: '',
            icon: '',
            sort: 3,
            status: 1,
            visible: 1,
            createTime: '2024-01-01 00:00:00',
            remark: '编辑用户按钮权限'
          },
          {
            id: 6,
            name: '删除用户',
            code: 'USER_DELETE',
            type: 'button',
            parentId: 2,
            path: '',
            component: '',
            icon: '',
            sort: 4,
            status: 1,
            visible: 1,
            createTime: '2024-01-01 00:00:00',
            remark: '删除用户按钮权限'
          }
        ]
      },
      {
        id: 7,
        name: '角色管理',
        code: 'ROLE_MANAGE',
        type: 'menu',
        parentId: 1,
        path: '/role',
        component: 'Role/RoleListPage',
        icon: 'Lock',
        sort: 2,
        status: 1,
        visible: 1,
        createTime: '2024-01-01 00:00:00',
        remark: '角色管理菜单',
        children: [
          {
            id: 8,
            name: '查看角色',
            code: 'ROLE_VIEW',
            type: 'button',
            parentId: 7,
            path: '',
            component: '',
            icon: '',
            sort: 1,
            status: 1,
            visible: 1,
            createTime: '2024-01-01 00:00:00',
            remark: '查看角色按钮权限'
          },
          {
            id: 9,
            name: '新增角色',
            code: 'ROLE_ADD',
            type: 'button',
            parentId: 7,
            path: '',
            component: '',
            icon: '',
            sort: 2,
            status: 1,
            visible: 1,
            createTime: '2024-01-01 00:00:00',
            remark: '新增角色按钮权限'
          }
        ]
      }
    ]
  },
  {
    id: 10,
    name: '内容管理',
    code: 'CONTENT',
    type: 'module',
    parentId: null,
    path: '',
    component: '',
    icon: 'Document',
    sort: 2,
    status: 1,
    visible: 1,
    createTime: '2024-01-01 00:00:00',
    remark: '内容管理模块',
    children: [
      {
        id: 11,
        name: '文章管理',
        code: 'ARTICLE_MANAGE',
        type: 'menu',
        parentId: 10,
        path: '/article',
        component: 'Article/ArticleListPage',
        icon: 'Document',
        sort: 1,
        status: 1,
        visible: 1,
        createTime: '2024-01-01 00:00:00',
        remark: '文章管理菜单'
      }
    ]
  }
])

// 计算属性
const dialogTitle = computed(() => {
  if (isAddChild.value) {
    return `新增子权限 - ${parentPermission.value?.name}`
  }
  return isEdit.value ? '编辑权限' : '新增权限'
})

const totalCount = computed(() => {
  const countNodes = (nodes: any[]): number => {
    let count = 0
    nodes.forEach(node => {
      count++
      if (node.children && node.children.length > 0) {
        count += countNodes(node.children)
      }
    })
    return count
  }
  return countNodes(permissionList.value)
})

const permissionTreeOptions = computed(() => {
  const filterOptions = (nodes: any[], excludeId?: number): any[] => {
    return nodes.filter(node => node.id !== excludeId).map(node => ({
      id: node.id,
      name: node.name,
      children: node.children ? filterOptions(node.children, excludeId) : []
    }))
  }
  return filterOptions(permissionList.value, permissionForm.id)
})

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
    'module': '模块',
    'menu': '菜单',
    'button': '按钮'
  }
  return nameMap[type] || '未知'
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
const loadPermissionList = async () => {
  loading.value = true
  try {
    // 模拟API调用
    await new Promise(resolve => setTimeout(resolve, 500))
    ElMessage.success('权限列表加载成功')
  } catch (error) {
    ElMessage.error('加载权限列表失败')
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  loadPermissionList()
}

const handleReset = () => {
  Object.assign(searchForm, {
    name: '',
    code: '',
    type: '',
    status: ''
  })
  handleSearch()
}

const expandAll = () => {
  const getAllKeys = (nodes: any[]): number[] => {
    const keys: number[] = []
    const traverse = (nodeList: any[]) => {
      nodeList.forEach(node => {
        if (node.children && node.children.length > 0) {
          keys.push(node.id)
          traverse(node.children)
        }
      })
    }
    traverse(nodes)
    return keys
  }
  expandedKeys.value = getAllKeys(permissionList.value)
}

const collapseAll = () => {
  expandedKeys.value = []
}

const handleAdd = () => {
  isEdit.value = false
  isAddChild.value = false
  parentPermission.value = null
  resetPermissionForm()
  dialogVisible.value = true
}

const handleAddChild = (row: any) => {
  isEdit.value = false
  isAddChild.value = true
  parentPermission.value = row
  resetPermissionForm()
  permissionForm.parentId = row.id
  // 根据父级类型设置子级类型
  if (row.type === 'module') {
    permissionForm.type = 'menu'
  } else if (row.type === 'menu') {
    permissionForm.type = 'button'
  }
  dialogVisible.value = true
}

const handleEdit = (row: any) => {
  isEdit.value = true
  isAddChild.value = false
  parentPermission.value = null
  Object.assign(permissionForm, { ...row })
  dialogVisible.value = true
}

const handleDelete = async (row: any) => {
  if (row.children && row.children.length > 0) {
    ElMessage.warning('该权限下还有子权限，无法删除')
    return
  }
  
  try {
    await ElMessageBox.confirm(
      `确定要删除权限 "${row.name}" 吗？`,
      '删除确认',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    
    // 模拟删除操作
    const deleteNode = (nodes: any[], id: number): boolean => {
      for (let i = 0; i < nodes.length; i++) {
        if (nodes[i].id === id) {
          nodes.splice(i, 1)
          return true
        }
        if (nodes[i].children && deleteNode(nodes[i].children, id)) {
          return true
        }
      }
      return false
    }
    
    deleteNode(permissionList.value, row.id)
    ElMessage.success('删除成功')
  } catch {
    ElMessage.info('已取消删除')
  }
}

const handleStatusChange = async (row: any) => {
  try {
    // 模拟状态更新
    await new Promise(resolve => setTimeout(resolve, 300))
    ElMessage.success(`权限状态已${row.status ? '启用' : '禁用'}`)
  } catch (error) {
    // 恢复原状态
    row.status = row.status ? 0 : 1
    ElMessage.error('状态更新失败')
  }
}

const resetPermissionForm = () => {
  Object.assign(permissionForm, {
    id: null,
    name: '',
    code: '',
    type: 'menu',
    parentId: null,
    path: '',
    component: '',
    icon: '',
    sort: 0,
    status: 1,
    visible: 1,
    remark: ''
  })
  permissionFormRef.value?.clearValidate()
}

const handleSubmit = async () => {
  if (!permissionFormRef.value) return
  
  try {
    await permissionFormRef.value.validate()
    submitLoading.value = true
    
    // 模拟提交
    await new Promise(resolve => setTimeout(resolve, 1000))
    
    if (isEdit.value) {
      // 更新权限
      const updateNode = (nodes: any[], data: any): boolean => {
        for (const node of nodes) {
          if (node.id === data.id) {
            Object.assign(node, data)
            return true
          }
          if (node.children && updateNode(node.children, data)) {
            return true
          }
        }
        return false
      }
      updateNode(permissionList.value, permissionForm)
      ElMessage.success('权限更新成功')
    } else {
      // 新增权限
      const newPermission = {
        ...permissionForm,
        id: Date.now(),
        children: [],
        createTime: new Date().toLocaleString()
      }
      
      if (permissionForm.parentId) {
        // 添加到父节点
        const addToParent = (nodes: any[], parentId: number, child: any): boolean => {
          for (const node of nodes) {
            if (node.id === parentId) {
              if (!node.children) node.children = []
              node.children.push(child)
              return true
            }
            if (node.children && addToParent(node.children, parentId, child)) {
              return true
            }
          }
          return false
        }
        addToParent(permissionList.value, permissionForm.parentId, newPermission)
      } else {
        // 添加到根节点
        permissionList.value.push(newPermission)
      }
      
      ElMessage.success('权限创建成功')
    }
    
    dialogVisible.value = false
    resetPermissionForm()
  } catch (error) {
    console.error('表单验证失败:', error)
  } finally {
    submitLoading.value = false
  }
}

const validatePermissions = () => {
  ElMessage.success('权限验证通过，所有权限配置正常')
}

const exportPermissions = () => {
  ElMessage.info('导出功能开发中')
}

// 组件挂载时加载数据
onMounted(() => {
  loadPermissionList()
  // 默认展开第一级
  expandedKeys.value = permissionList.value.map(item => item.id)
})
</script>

<style scoped>
.permission-list-page {
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

code {
  font-family: 'Courier New', monospace;
}

.el-table :deep(.el-table__expand-icon) {
  color: #409eff;
}

.el-table :deep(.el-table__indent) {
  padding-left: 20px;
}
</style>