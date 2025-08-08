<template>
  <div class="menu-list-page">
    <!-- 页面标题和操作栏 -->
    <div class="page-header flex items-center justify-between mb-6">
      <div>
        <h1 class="text-2xl font-bold text-gray-800 mb-2">{{ t('menu.title') }}</h1>
        <p class="text-gray-600">{{ t('menu.subtitle') }}</p>
      </div>
      <div class="flex items-center space-x-3">
        <el-button type="success" :icon="Download" @click="exportMenus">
            {{ t('menu.actions.export') }}
          </el-button>
          <el-button type="info" :icon="View" @click="previewRoutes">
            {{ t('menu.actions.preview') }}
          </el-button>
          <el-button type="primary" :icon="Plus" @click="handleAdd">
            {{ t('menu.actions.add') }}
          </el-button>
      </div>
    </div>

    <!-- 搜索和筛选区域 -->
    <div class="search-section bg-white rounded-lg shadow-sm border border-gray-200 p-6 mb-6">
      <el-form :model="searchForm" :inline="true" class="search-form">
        <el-form-item :label="t('menu.form.name')">
          <el-input
            v-model="searchForm.name"
            :placeholder="t('menu.form.namePlaceholder')"
            clearable
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item :label="t('menu.form.path')">
          <el-input
            v-model="searchForm.path"
            :placeholder="t('menu.form.pathPlaceholder')"
            clearable
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item :label="t('menu.form.type')">
          <el-select v-model="searchForm.type" :placeholder="t('menu.form.typePlaceholder')" clearable style="width: 120px">
            <el-option :label="t('menu.types.catalog')" value="catalog" />
            <el-option :label="t('menu.types.menu')" value="menu" />
            <el-option :label="t('menu.types.button')" value="button" />
          </el-select>
        </el-form-item>
        <el-form-item :label="t('menu.form.status')">
          <el-select v-model="searchForm.status" :placeholder="t('menu.form.statusPlaceholder')" clearable style="width: 120px">
            <el-option :label="t('menu.status.visible')" value="1" />
            <el-option :label="t('menu.status.hidden')" value="0" />
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

    <!-- 菜单树表格 -->
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
            <el-button :icon="Refresh" size="small" @click="loadMenuList" />
          </el-tooltip>
          <el-tooltip content="同步路由">
            <el-button :icon="Connection" size="small" type="warning" @click="syncRoutes" />
          </el-tooltip>
        </div>
      </div>

      <el-table
        v-loading="loading"
        :data="menuList"
        row-key="id"
        :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
        :expand-row-keys="expandedKeys"
        stripe
        style="width: 100%"
      >
        <el-table-column prop="name" :label="t('menu.table.name')" min-width="200">
          <template #default="{ row }">
            <div class="flex items-center">
              <el-icon v-if="row.icon" class="mr-2" :size="16">
                <component :is="row.icon" />
              </el-icon>
              <span>{{ row.name }}</span>
              <el-tag v-if="row.isFrame" size="small" type="warning" class="ml-2">
                {{ t('menu.table.external') }}
              </el-tag>
              <el-tag v-if="row.isCache" size="small" type="success" class="ml-2">
                {{ t('menu.table.cached') }}
              </el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column :label="t('menu.table.type')" width="100">
          <template #default="{ row }">
            <el-tag :type="getMenuTypeTag(row.type)" size="small">
              {{ getMenuTypeName(row.type) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="path" :label="t('menu.table.path')" min-width="150">
          <template #default="{ row }">
            <code v-if="row.path" class="text-xs bg-gray-100 px-2 py-1 rounded">
              {{ row.path }}
            </code>
            <span v-else class="text-gray-400">-</span>
          </template>
        </el-table-column>
        <el-table-column prop="component" :label="t('menu.table.component')" min-width="180" show-overflow-tooltip>
          <template #default="{ row }">
            <code v-if="row.component" class="text-xs bg-gray-100 px-2 py-1 rounded">
              {{ row.component }}
            </code>
            <span v-else class="text-gray-400">-</span>
          </template>
        </el-table-column>
        <el-table-column :label="t('menu.table.permission')" min-width="150">
          <template #default="{ row }">
            <el-tag v-if="row.permission" size="small" type="info">
              {{ row.permission }}
            </el-tag>
            <span v-else class="text-gray-400">-</span>
          </template>
        </el-table-column>
        <el-table-column :label="t('menu.table.status')" width="100">
          <template #default="{ row }">
            <el-switch
              v-model="row.visible"
              :active-value="1"
              :inactive-value="0"
              @change="handleVisibleChange(row)"
            />
          </template>
        </el-table-column>
        <el-table-column prop="sort" :label="t('menu.table.sort')" width="80" />
        <el-table-column prop="createTime" :label="t('menu.table.createTime')" min-width="160" />
        <el-table-column :label="t('menu.table.actions')" width="200" fixed="right">
          <template #default="{ row }">
            <div class="flex items-center space-x-2">
              <el-tooltip :content="t('menu.actions.addChild')">
                <el-button type="success" :icon="Plus" size="small" @click="handleAddChild(row)" />
              </el-tooltip>
              <el-tooltip :content="t('common.edit')">
                <el-button type="warning" :icon="Edit" size="small" @click="handleEdit(row)" />
              </el-tooltip>
              <el-tooltip :content="t('common.delete')">
                <el-button type="danger" :icon="Delete" size="small" @click="handleDelete(row)" />
              </el-tooltip>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 菜单表单对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="800px"
      :close-on-click-modal="false"
    >
      <el-form
        ref="menuFormRef"
        :model="menuForm"
        :rules="menuFormRules"
        label-width="100px"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item :label="t('menu.form.name')" prop="name">
              <el-input v-model="menuForm.name" :placeholder="t('menu.form.namePlaceholder')" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="t('menu.form.type')" prop="type">
              <el-select v-model="menuForm.type" :placeholder="t('menu.form.typePlaceholder')" style="width: 100%">
                <el-option :label="t('menu.types.catalog')" value="catalog" />
                <el-option :label="t('menu.types.menu')" value="menu" />
                <el-option :label="t('menu.types.button')" value="button" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item :label="t('menu.form.parentMenu')">
              <el-tree-select
                v-model="menuForm.parentId"
                :data="menuTreeOptions"
                :props="{ label: 'name', value: 'id', children: 'children' }"
                :placeholder="t('menu.form.parentMenuPlaceholder')"
                style="width: 100%"
                clearable
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="t('menu.form.icon')">
              <el-select
                v-model="menuForm.icon"
                :placeholder="t('menu.form.iconPlaceholder')"
                clearable
                filterable
                style="width: 100%"
              >
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
        </el-row>
        <el-row :gutter="20" v-if="menuForm.type !== 'button'">
          <el-col :span="12">
            <el-form-item :label="t('menu.form.path')" prop="path">
              <el-input v-model="menuForm.path" :placeholder="t('menu.form.pathPlaceholder')" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="t('menu.form.component')" v-if="menuForm.type === 'menu'">
              <el-input v-model="menuForm.component" :placeholder="t('menu.form.componentPlaceholder')" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item :label="t('menu.form.permission')">
              <el-input v-model="menuForm.permission" :placeholder="t('menu.form.permissionPlaceholder')" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="t('menu.form.sort')">
              <el-input-number v-model="menuForm.sort" :min="0" :max="999" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item :label="t('menu.form.visible')">
              <el-switch
                v-model="menuForm.visible"
                :active-value="1"
                :inactive-value="0"
                :active-text="t('menu.form.show')"
                :inactive-text="t('menu.form.hide')"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item :label="t('menu.form.cache')" v-if="menuForm.type === 'menu'">
              <el-switch
                v-model="menuForm.isCache"
                :active-value="1"
                :inactive-value="0"
                :active-text="t('menu.form.cached')"
                :inactive-text="t('menu.form.notCached')"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item :label="t('menu.form.external')" v-if="menuForm.type === 'menu'">
              <el-switch
                v-model="menuForm.isFrame"
                :active-value="1"
                :inactive-value="0"
                :active-text="t('common.yes')"
                :inactive-text="t('common.no')"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item :label="t('menu.form.remark')">
          <el-input
            v-model="menuForm.remark"
            type="textarea"
            :rows="3"
            :placeholder="t('menu.form.remarkPlaceholder')"
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

    <!-- 路由预览对话框 -->
    <el-dialog
      v-model="routePreviewVisible"
      :title="t('menu.routePreview.title')"
      width="900px"
      :close-on-click-modal="false"
    >
      <div class="route-preview">
        <div class="mb-4">
          <el-alert
            :title="t('menu.routePreview.alertTitle')"
            :description="t('menu.routePreview.alertDescription')"
            type="info"
            show-icon
            :closable="false"
          />
        </div>
        <el-input
          v-model="routeConfigText"
          type="textarea"
          :rows="20"
          readonly
          class="route-config-text"
        />
      </div>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="copyRouteConfig">{{ t('menu.routePreview.copy') }}</el-button>
          <el-button type="primary" @click="routePreviewVisible = false">{{ t('common.close') }}</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, computed } from 'vue'
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus'
import { useI18n } from 'vue-i18n'
import {
  Plus,
  Search,
  Refresh,
  Edit,
  Delete,
  Download,
  View,
  Menu,
  Document,
  Operation,
  Files,
  User,
  Setting,
  Lock,
  Key,
  Minus,
  Connection,
  Odometer,
  OfficeBuilding,
  Monitor,
  DataAnalysis,
  Notification
} from '@element-plus/icons-vue'

// 国际化
const { t } = useI18n()

// 响应式数据
const loading = ref(false)
const dialogVisible = ref(false)
const routePreviewVisible = ref(false)
const submitLoading = ref(false)
const isEdit = ref(false)
const isAddChild = ref(false)
const menuFormRef = ref<FormInstance>()
const expandedKeys = ref<number[]>([])
const parentMenu = ref<any>(null)
const routeConfigText = ref('')

// 搜索表单
const searchForm = reactive({
  name: '',
  path: '',
  type: '',
  status: ''
})

// 菜单表单
const menuForm = reactive({
  id: null,
  name: '',
  type: 'menu',
  parentId: null,
  path: '',
  component: '',
  icon: '',
  permission: '',
  sort: 0,
  visible: 1,
  isCache: 1,
  isFrame: 0,
  remark: ''
})

// 表单验证规则
const menuFormRules: FormRules = {
  name: [
    { required: true, message: t('menu.validation.nameRequired'), trigger: 'blur' },
    { min: 2, max: 50, message: t('menu.validation.nameLength'), trigger: 'blur' }
  ],
  type: [
    { required: true, message: t('menu.validation.typeRequired'), trigger: 'change' }
  ],
  path: [
    { required: true, message: t('menu.validation.pathRequired'), trigger: 'blur' }
  ]
}

// 图标选项
const iconOptions = computed(() => [
  { label: t('menu.icons.dashboard'), value: 'Odometer' },
  { label: t('menu.icons.user'), value: 'User' },
  { label: t('menu.icons.lock'), value: 'Lock' },
  { label: t('menu.icons.key'), value: 'Key' },
  { label: t('menu.icons.menu'), value: 'Menu' },
  { label: t('menu.icons.document'), value: 'Document' },
  { label: t('menu.icons.operation'), value: 'Operation' },
  { label: t('menu.icons.files'), value: 'Files' },
  { label: t('menu.icons.setting'), value: 'Setting' },
  { label: t('menu.icons.building'), value: 'OfficeBuilding' },
  { label: t('menu.icons.monitor'), value: 'Monitor' },
  { label: t('menu.icons.analysis'), value: 'DataAnalysis' },
  { label: t('menu.icons.notification'), value: 'Notification' }
])

// 菜单列表数据
const menuList = ref([
  {
    id: 1,
    name: '首页',
    type: 'menu',
    parentId: null,
    path: '/dashboard',
    component: 'DashboardPage',
    icon: 'Odometer',
    permission: 'dashboard:view',
    sort: 1,
    visible: 1,
    isCache: 1,
    isFrame: 0,
    createTime: '2024-01-01 00:00:00',
    remark: '系统首页'
  },
  {
    id: 2,
    name: '系统管理',
    type: 'catalog',
    parentId: null,
    path: '/system',
    component: '',
    icon: 'Setting',
    permission: '',
    sort: 2,
    visible: 1,
    isCache: 0,
    isFrame: 0,
    createTime: '2024-01-01 00:00:00',
    remark: '系统管理目录',
    children: [
      {
        id: 3,
        name: '用户管理',
        type: 'menu',
        parentId: 2,
        path: '/user/list',
        component: 'User/UserListPage',
        icon: 'User',
        permission: 'user:list',
        sort: 1,
        visible: 1,
        isCache: 1,
        isFrame: 0,
        createTime: '2024-01-01 00:00:00',
        remark: '用户管理页面',
        children: [
          {
            id: 4,
            name: '新增用户',
            type: 'button',
            parentId: 3,
            path: '',
            component: '',
            icon: '',
            permission: 'user:add',
            sort: 1,
            visible: 1,
            isCache: 0,
            isFrame: 0,
            createTime: '2024-01-01 00:00:00',
            remark: '新增用户按钮'
          },
          {
            id: 5,
            name: '编辑用户',
            type: 'button',
            parentId: 3,
            path: '',
            component: '',
            icon: '',
            permission: 'user:edit',
            sort: 2,
            visible: 1,
            isCache: 0,
            isFrame: 0,
            createTime: '2024-01-01 00:00:00',
            remark: '编辑用户按钮'
          },
          {
            id: 6,
            name: '删除用户',
            type: 'button',
            parentId: 3,
            path: '',
            component: '',
            icon: '',
            permission: 'user:delete',
            sort: 3,
            visible: 1,
            isCache: 0,
            isFrame: 0,
            createTime: '2024-01-01 00:00:00',
            remark: '删除用户按钮'
          }
        ]
      },
      {
        id: 7,
        name: '角色管理',
        type: 'menu',
        parentId: 2,
        path: '/role/list',
        component: 'Role/RoleListPage',
        icon: 'Lock',
        permission: 'role:list',
        sort: 2,
        visible: 1,
        isCache: 1,
        isFrame: 0,
        createTime: '2024-01-01 00:00:00',
        remark: '角色管理页面'
      },
      {
        id: 8,
        name: '权限管理',
        type: 'menu',
        parentId: 2,
        path: '/permission/list',
        component: 'Permission/PermissionListPage',
        icon: 'Key',
        permission: 'permission:list',
        sort: 3,
        visible: 1,
        isCache: 1,
        isFrame: 0,
        createTime: '2024-01-01 00:00:00',
        remark: '权限管理页面'
      },
      {
        id: 9,
        name: '菜单管理',
        type: 'menu',
        parentId: 2,
        path: '/menu/list',
        component: 'Menu/MenuListPage',
        icon: 'Menu',
        permission: 'menu:list',
        sort: 4,
        visible: 1,
        isCache: 1,
        isFrame: 0,
        createTime: '2024-01-01 00:00:00',
        remark: '菜单管理页面'
      }
    ]
  },
  {
    id: 10,
    name: '部门管理',
    type: 'menu',
    parentId: null,
    path: '/department/list',
    component: 'Department/DepartmentListPage',
    icon: 'OfficeBuilding',
    permission: 'department:list',
    sort: 3,
    visible: 1,
    isCache: 1,
    isFrame: 0,
    createTime: '2024-01-01 00:00:00',
    remark: '部门管理页面'
  }
])

// 计算属性
const dialogTitle = computed(() => {
  if (isAddChild.value) {
    return `${t('menu.dialog.addChildTitle')} - ${parentMenu.value?.name}`
  }
  return isEdit.value ? t('menu.dialog.editTitle') : t('menu.dialog.addTitle')
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
  return countNodes(menuList.value)
})

const menuTreeOptions = computed(() => {
  const filterOptions = (nodes: any[], excludeId?: number): any[] => {
    return nodes.filter(node => node.id !== excludeId && node.type !== 'button').map(node => ({
      id: node.id,
      name: node.name,
      children: node.children ? filterOptions(node.children, excludeId) : []
    }))
  }
  return filterOptions(menuList.value, menuForm.id)
})

// 获取菜单类型标签
const getMenuTypeTag = (type: string) => {
  const typeMap: Record<string, string> = {
    'catalog': 'info',
    'menu': 'success',
    'button': 'warning'
  }
  return typeMap[type] || 'info'
}

// 获取菜单类型名称
const getMenuTypeName = (type: string) => {
  const nameMap: Record<string, string> = {
    'catalog': '目录',
    'menu': '菜单',
    'button': '按钮'
  }
  return nameMap[type] || '未知'
}

// 方法
const loadMenuList = async () => {
  loading.value = true
  try {
    // 模拟API调用
    await new Promise(resolve => setTimeout(resolve, 500))
    ElMessage.success('菜单列表加载成功')
  } catch (error) {
    ElMessage.error('加载菜单列表失败')
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  loadMenuList()
}

const handleReset = () => {
  Object.assign(searchForm, {
    name: '',
    path: '',
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
  expandedKeys.value = getAllKeys(menuList.value)
}

const collapseAll = () => {
  expandedKeys.value = []
}

const handleAdd = () => {
  isEdit.value = false
  isAddChild.value = false
  parentMenu.value = null
  resetMenuForm()
  dialogVisible.value = true
}

const handleAddChild = (row: any) => {
  isEdit.value = false
  isAddChild.value = true
  parentMenu.value = row
  resetMenuForm()
  menuForm.parentId = row.id
  // 根据父级类型设置子级类型
  if (row.type === 'catalog') {
    menuForm.type = 'menu'
  } else if (row.type === 'menu') {
    menuForm.type = 'button'
  }
  dialogVisible.value = true
}

const handleEdit = (row: any) => {
  isEdit.value = true
  isAddChild.value = false
  parentMenu.value = null
  Object.assign(menuForm, { ...row })
  dialogVisible.value = true
}

const handleDelete = async (row: any) => {
  if (row.children && row.children.length > 0) {
    ElMessage.warning(t('menu.messages.hasChildren'))
    return
  }
  
  try {
    await ElMessageBox.confirm(
      t('menu.messages.deleteConfirm', { name: row.name }),
      t('menu.messages.deleteTitle'),
      {
        confirmButtonText: t('common.confirm'),
        cancelButtonText: t('common.cancel'),
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
    
    deleteNode(menuList.value, row.id)
    ElMessage.success(t('menu.messages.deleteSuccess'))
  } catch {
    ElMessage.info(t('menu.messages.deleteCancelled'))
  }
}

const handleVisibleChange = async (row: any) => {
  try {
    // 模拟状态更新
    await new Promise(resolve => setTimeout(resolve, 300))
    ElMessage.success(t('menu.messages.statusUpdateSuccess', { status: row.visible ? t('menu.status.visible') : t('menu.status.hidden') }))
  } catch (error) {
    // 恢复原状态
    row.visible = row.visible ? 0 : 1
    ElMessage.error(t('menu.messages.statusUpdateFailed'))
  }
}

const resetMenuForm = () => {
  Object.assign(menuForm, {
    id: null,
    name: '',
    type: 'menu',
    parentId: null,
    path: '',
    component: '',
    icon: '',
    permission: '',
    sort: 0,
    visible: 1,
    isCache: 1,
    isFrame: 0,
    remark: ''
  })
  menuFormRef.value?.clearValidate()
}

const handleSubmit = async () => {
  if (!menuFormRef.value) return
  
  try {
    await menuFormRef.value.validate()
    submitLoading.value = true
    
    // 模拟提交
    await new Promise(resolve => setTimeout(resolve, 1000))
    
    if (isEdit.value) {
      // 更新菜单
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
      updateNode(menuList.value, menuForm)
      ElMessage.success(t('menu.messages.updateSuccess'))
    } else {
      // 新增菜单
      const newMenu = {
        ...menuForm,
        id: Date.now(),
        children: [],
        createTime: new Date().toLocaleString()
      }
      
      if (menuForm.parentId) {
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
        addToParent(menuList.value, menuForm.parentId, newMenu)
      } else {
        // 添加到根节点
        menuList.value.push(newMenu)
      }
      
      ElMessage.success(t('menu.messages.createSuccess'))
    }
    
    dialogVisible.value = false
    resetMenuForm()
  } catch (error) {
    console.error('表单验证失败:', error)
  } finally {
    submitLoading.value = false
  }
}

const generateRouteConfig = (nodes: any[], level = 0): string => {
  let config = ''
  const indent = '  '.repeat(level)
  
  nodes.forEach((node, index) => {
    if (node.type === 'button') return // 跳过按钮类型
    
    config += `${indent}{\n`
    config += `${indent}  path: '${node.path}',\n`
    config += `${indent}  name: '${node.name}',\n`
    
    if (node.type === 'menu' && node.component) {
      config += `${indent}  component: () => import('@/pages/${node.component}.vue'),\n`
    }
    
    config += `${indent}  meta: {\n`
    config += `${indent}    title: '${node.name}',\n`
    if (node.icon) {
      config += `${indent}    icon: '${node.icon}',\n`
    }
    if (node.permission) {
      config += `${indent}    permission: '${node.permission}',\n`
    }
    config += `${indent}    visible: ${node.visible === 1},\n`
    if (node.type === 'menu') {
      config += `${indent}    keepAlive: ${node.isCache === 1},\n`
    }
    config += `${indent}  }\n`
    
    if (node.children && node.children.length > 0) {
      const childMenus = node.children.filter((child: any) => child.type !== 'button')
      if (childMenus.length > 0) {
        config += `${indent}  children: [\n`
        config += generateRouteConfig(childMenus, level + 2)
        config += `${indent}  ]\n`
      }
    }
    
    config += `${indent}}${index < nodes.length - 1 ? ',' : ''}\n`
  })
  
  return config
}

const previewRoutes = () => {
  const visibleMenus = menuList.value.filter(menu => menu.visible === 1)
  routeConfigText.value = `// 路由配置\nconst routes = [\n${generateRouteConfig(visibleMenus, 1)}]\n\nexport default routes`
  routePreviewVisible.value = true
}

const copyRouteConfig = async () => {
  try {
    await navigator.clipboard.writeText(routeConfigText.value)
    ElMessage.success(t('menu.messages.copySuccess'))
  } catch (error) {
    ElMessage.error(t('menu.messages.copyFailed'))
  }
}

const syncRoutes = () => {
  ElMessage.success(t('menu.messages.syncSuccess'))
}

const exportMenus = () => {
  ElMessage.info(t('common.featureInDevelopment'))
}

// 组件挂载时加载数据
onMounted(() => {
  loadMenuList()
  // 默认展开第一级
  expandedKeys.value = menuList.value.map(item => item.id)
})
</script>

<style scoped>
.menu-list-page {
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

.route-config-text {
  font-family: 'Courier New', monospace;
  font-size: 12px;
}

.route-preview {
  max-height: 600px;
  overflow-y: auto;
}

.el-table :deep(.el-table__expand-icon) {
  color: #409eff;
}

.el-table :deep(.el-table__indent) {
  padding-left: 20px;
}
</style>