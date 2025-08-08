<template>
  <div class="department-list-page">
    <!-- 页面标题和操作栏 -->
    <div class="page-header flex items-center justify-between mb-6">
      <div>
        <h1 class="text-2xl font-bold text-gray-800 mb-2">{{ t('department.title') }}</h1>
        <p class="text-gray-600">{{ t('department.subtitle') }}</p>
      </div>
      <div class="flex items-center space-x-3">
        <el-button type="success" :icon="Download" @click="exportDepartments">
          {{ t('department.actions.export') }}
        </el-button>
        <el-button type="info" :icon="View" @click="previewOrganization">
          {{ t('department.actions.orgChart') }}
        </el-button>
        <el-button type="primary" :icon="Plus" @click="handleAdd">
          {{ t('department.actions.add') }}
        </el-button>
      </div>
    </div>

    <!-- 搜索和筛选区域 -->
    <div class="search-section bg-white rounded-lg shadow-sm border border-gray-200 p-6 mb-6">
      <el-form :model="searchForm" :inline="true" class="search-form">
        <el-form-item :label="t('department.form.name')">
          <el-input
            v-model="searchForm.name"
            :placeholder="t('department.form.namePlaceholder')"
            clearable
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item :label="t('department.form.code')">
          <el-input
            v-model="searchForm.code"
            :placeholder="t('department.form.codePlaceholder')"
            clearable
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item :label="t('department.form.leader')">
          <el-input
            v-model="searchForm.leader"
            :placeholder="t('department.form.leaderPlaceholder')"
            clearable
            style="width: 150px"
          />
        </el-form-item>
        <el-form-item :label="t('common.status')">
          <el-select v-model="searchForm.status" :placeholder="t('department.form.statusPlaceholder')" clearable style="width: 120px">
            <el-option :label="t('common.normal')" value="1" />
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

    <!-- 部门树表格 -->
    <div class="table-section bg-white rounded-lg shadow-sm border border-gray-200">
      <div class="table-header flex items-center justify-between p-4 border-b border-gray-200">
        <div class="flex items-center space-x-4">
          <span class="text-sm text-gray-600">{{ t('department.table.totalCount', { count: totalCount }) }}</span>
          <el-button size="small" @click="expandAll">
            <el-icon><Plus /></el-icon>
            {{ t('department.actions.expandAll') }}
          </el-button>
          <el-button size="small" @click="collapseAll">
            <el-icon><Minus /></el-icon>
            {{ t('department.actions.collapseAll') }}
          </el-button>
        </div>
        <div class="flex items-center space-x-2">
          <el-tooltip :content="t('department.actions.refresh')">
            <el-button :icon="Refresh" size="small" @click="loadDepartmentList" />
          </el-tooltip>
          <el-tooltip :content="t('department.actions.sync')">
            <el-button :icon="Connection" size="small" type="warning" @click="syncOrganization" />
          </el-tooltip>
        </div>
      </div>

      <el-table
        v-loading="loading"
        :data="departmentList"
        row-key="id"
        :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
        :expand-row-keys="expandedKeys"
        stripe
        style="width: 100%"
      >
        <el-table-column prop="name" :label="t('department.table.name')" min-width="200">
          <template #default="{ row }">
            <div class="flex items-center">
              <el-icon class="mr-2 text-blue-500" :size="16">
                <OfficeBuilding />
              </el-icon>
              <span class="font-medium">{{ row.name }}</span>
              <el-tag v-if="row.isHeadquarters" size="small" type="danger" class="ml-2">
                {{ t('department.table.headquarters') }}
              </el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="code" :label="t('department.table.code')" width="120">
          <template #default="{ row }">
            <code class="text-xs bg-gray-100 px-2 py-1 rounded">
              {{ row.code }}
            </code>
          </template>
        </el-table-column>
        <el-table-column :label="t('department.table.leader')" width="120">
          <template #default="{ row }">
            <div v-if="row.leader" class="flex items-center">
              <el-avatar :size="24" class="mr-2">
                <el-icon><User /></el-icon>
              </el-avatar>
              <span>{{ row.leader }}</span>
            </div>
            <span v-else class="text-gray-400">{{ t('department.table.notSet') }}</span>
          </template>
        </el-table-column>
        <el-table-column :label="t('department.table.contact')" width="140">
          <template #default="{ row }">
            <div v-if="row.phone" class="text-sm">
              <div>{{ row.phone }}</div>
              <div v-if="row.email" class="text-gray-500">{{ row.email }}</div>
            </div>
            <span v-else class="text-gray-400">-</span>
          </template>
        </el-table-column>
        <el-table-column :label="t('department.table.memberCount')" width="100">
          <template #default="{ row }">
            <el-tag type="info" size="small">
              {{ t('department.table.memberCountValue', { count: row.memberCount || 0 }) }}
            </el-tag>
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
        <el-table-column prop="sort" :label="t('common.sort')" width="80" />
        <el-table-column prop="createTime" :label="t('common.createTime')" min-width="160" />
        <el-table-column :label="t('common.actions')" width="200" fixed="right">
          <template #default="{ row }">
            <div class="flex items-center space-x-2">
              <el-tooltip :content="t('department.actions.addChild')">
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

    <!-- 部门表单对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="700px"
      :close-on-click-modal="false"
    >
      <el-form
        ref="departmentFormRef"
        :model="departmentForm"
        :rules="departmentFormRules"
        label-width="100px"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item :label="t('department.form.name')" prop="name">
              <el-input v-model="departmentForm.name" :placeholder="t('department.form.namePlaceholder')" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="t('department.form.code')" prop="code">
              <el-input v-model="departmentForm.code" :placeholder="t('department.form.codePlaceholder')" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item :label="t('department.form.parentDepartment')">
              <el-tree-select
                v-model="departmentForm.parentId"
                :data="departmentTreeOptions"
                :props="{ label: 'name', value: 'id', children: 'children' }"
                :placeholder="t('department.form.parentDepartmentPlaceholder')"
                style="width: 100%"
                clearable
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="t('department.form.leader')">
              <el-select
                v-model="departmentForm.leaderId"
                :placeholder="t('department.form.leaderSelectPlaceholder')"
                clearable
                filterable
                style="width: 100%"
              >
                <el-option
                  v-for="user in userOptions"
                  :key="user.id"
                  :label="user.name"
                  :value="user.id"
                >
                  <div class="flex items-center">
                    <el-avatar :size="20" class="mr-2">
                      <el-icon><User /></el-icon>
                    </el-avatar>
                    <span>{{ user.name }}</span>
                    <span class="text-gray-500 ml-2">({{ user.username }})</span>
                  </div>
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item :label="t('department.form.phone')">
              <el-input v-model="departmentForm.phone" :placeholder="t('department.form.phonePlaceholder')" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="t('department.form.email')">
              <el-input v-model="departmentForm.email" :placeholder="t('department.form.emailPlaceholder')" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item :label="t('common.sort')">
              <el-input-number v-model="departmentForm.sort" :min="0" :max="999" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="t('common.status')">
              <el-switch
                v-model="departmentForm.status"
                :active-value="1"
                :inactive-value="0"
                :active-text="t('common.normal')"
                :inactive-text="t('common.disabled')"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item :label="t('department.form.address')">
          <el-input v-model="departmentForm.address" :placeholder="t('department.form.addressPlaceholder')" />
        </el-form-item>
        <el-form-item :label="t('department.form.description')">
          <el-input
            v-model="departmentForm.description"
            type="textarea"
            :rows="3"
            :placeholder="t('department.form.descriptionPlaceholder')"
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

    <!-- 组织架构图对话框 -->
    <el-dialog
      v-model="orgChartVisible"
      :title="t('department.orgChart.title')"
      width="90%"
      :close-on-click-modal="false"
    >
      <div class="org-chart-container">
        <div class="mb-4">
          <el-alert
            :title="t('department.orgChart.title')"
            :description="t('department.orgChart.description')"
            type="info"
            show-icon
            :closable="false"
          />
        </div>
        <div class="org-chart" ref="orgChartRef">
          <div class="org-node root-node">
            <div class="node-content">
              <div class="node-title">{{ rootDepartment?.name || t('department.orgChart.company') }}</div>
              <div class="node-subtitle">{{ rootDepartment?.leader || t('department.orgChart.ceo') }}</div>
            </div>
            <div v-if="rootDepartment?.children" class="children-container">
              <div
                v-for="child in rootDepartment.children"
                :key="child.id"
                class="org-node child-node"
              >
                <div class="node-content">
                  <div class="node-title">{{ child.name }}</div>
                  <div class="node-subtitle">{{ child.leader || t('department.table.notSet') }}</div>
                  <div class="node-info">{{ t('department.table.memberCountValue', { count: child.memberCount || 0 }) }}</div>
                </div>
                <div v-if="child.children" class="children-container">
                  <div
                    v-for="grandChild in child.children"
                    :key="grandChild.id"
                    class="org-node grandchild-node"
                  >
                    <div class="node-content">
                      <div class="node-title">{{ grandChild.name }}</div>
                      <div class="node-subtitle">{{ grandChild.leader || t('department.table.notSet') }}</div>
                      <div class="node-info">{{ t('department.table.memberCountValue', { count: grandChild.memberCount || 0 }) }}</div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="exportOrgChart">{{ t('department.actions.exportImage') }}</el-button>
          <el-button type="primary" @click="orgChartVisible = false">{{ t('common.close') }}</el-button>
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
  User,
  OfficeBuilding,
  Minus,
  Connection
} from '@element-plus/icons-vue'

const { t } = useI18n()

// 部门数据接口
interface Department {
  id: number
  name: string
  code: string
  parentId: number | null
  leaderId: number | null
  leader?: string
  phone?: string
  email?: string
  address?: string
  sort: number
  status: number
  memberCount?: number
  isHeadquarters?: boolean
  createTime: string
  description?: string
  children?: Department[]
}

// 响应式数据
const loading = ref(false)
const dialogVisible = ref(false)
const orgChartVisible = ref(false)
const submitLoading = ref(false)
const isEdit = ref(false)
const isAddChild = ref(false)
const departmentFormRef = ref<FormInstance>()
const orgChartRef = ref<HTMLElement>()
const expandedKeys = ref<number[]>([])
const parentDepartment = ref<any>(null)

// 搜索表单
const searchForm = reactive({
  name: '',
  code: '',
  leader: '',
  status: ''
})

// 部门表单
const departmentForm = reactive({
  id: null,
  name: '',
  code: '',
  parentId: null,
  leaderId: null,
  phone: '',
  email: '',
  address: '',
  sort: 0,
  status: 1,
  description: ''
})

// 表单验证规则
const departmentFormRules: FormRules = {
  name: [
    { required: true, message: t('department.validation.nameRequired'), trigger: 'blur' },
    { min: 2, max: 50, message: t('department.validation.nameLength'), trigger: 'blur' }
  ],
  code: [
    { required: true, message: t('department.validation.codeRequired'), trigger: 'blur' },
    { pattern: /^[A-Z0-9]{2,10}$/, message: t('department.validation.codeFormat'), trigger: 'blur' }
  ]
}

// 用户选项
const userOptions = ref([
  { id: 1, name: '张三', username: 'zhangsan' },
  { id: 2, name: '李四', username: 'lisi' },
  { id: 3, name: '王五', username: 'wangwu' },
  { id: 4, name: '赵六', username: 'zhaoliu' },
  { id: 5, name: '钱七', username: 'qianqi' }
])

// 部门列表数据
const departmentList = ref<Department[]>([
  {
    id: 1,
    name: '总公司',
    code: 'HQ',
    parentId: null,
    leaderId: 1,
    leader: '张三',
    phone: '010-12345678',
    email: 'hq@company.com',
    address: '北京市朝阳区xxx大厦',
    sort: 1,
    status: 1,
    memberCount: 150,
    isHeadquarters: true,
    createTime: '2024-01-01 00:00:00',
    description: '公司总部',
    children: [
      {
        id: 2,
        name: '技术部',
        code: 'TECH',
        parentId: 1,
        leaderId: 2,
        leader: '李四',
        phone: '010-12345679',
        email: 'tech@company.com',
        address: '北京市朝阳区xxx大厦5楼',
        sort: 1,
        status: 1,
        memberCount: 45,
        isHeadquarters: false,
        createTime: '2024-01-01 00:00:00',
        description: '负责技术研发',
        children: [
          {
            id: 3,
            name: '前端开发组',
            code: 'FE',
            parentId: 2,
            leaderId: 3,
            leader: '王五',
            phone: '010-12345680',
            email: 'fe@company.com',
            address: '北京市朝阳区xxx大厦5楼A区',
            sort: 1,
            status: 1,
            memberCount: 15,
            isHeadquarters: false,
            createTime: '2024-01-01 00:00:00',
            description: '前端技术开发'
          },
          {
            id: 4,
            name: '后端开发组',
            code: 'BE',
            parentId: 2,
            leaderId: 4,
            leader: '赵六',
            phone: '010-12345681',
            email: 'be@company.com',
            address: '北京市朝阳区xxx大厦5楼B区',
            sort: 2,
            status: 1,
            memberCount: 20,
            isHeadquarters: false,
            createTime: '2024-01-01 00:00:00',
            description: '后端技术开发'
          },
          {
            id: 5,
            name: '测试组',
            code: 'QA',
            parentId: 2,
            leaderId: 5,
            leader: '钱七',
            phone: '010-12345682',
            email: 'qa@company.com',
            address: '北京市朝阳区xxx大厦5楼C区',
            sort: 3,
            status: 1,
            memberCount: 10,
            isHeadquarters: false,
            createTime: '2024-01-01 00:00:00',
            description: '软件测试'
          }
        ]
      },
      {
        id: 6,
        name: '市场部',
        code: 'MKT',
        parentId: 1,
        leaderId: null,
        leader: null,
        phone: '010-12345683',
        email: 'market@company.com',
        address: '北京市朝阳区xxx大厦3楼',
        sort: 2,
        status: 1,
        memberCount: 25,
        isHeadquarters: false,
        createTime: '2024-01-01 00:00:00',
        description: '市场营销推广'
      },
      {
        id: 7,
        name: '人事部',
        code: 'HR',
        parentId: 1,
        leaderId: null,
        leader: null,
        phone: '010-12345684',
        email: 'hr@company.com',
        address: '北京市朝阳区xxx大厦2楼',
        sort: 3,
        status: 1,
        memberCount: 15,
        isHeadquarters: false,
        createTime: '2024-01-01 00:00:00',
        description: '人力资源管理'
      },
      {
        id: 8,
        name: '财务部',
        code: 'FIN',
        parentId: 1,
        leaderId: null,
        leader: null,
        phone: '010-12345685',
        email: 'finance@company.com',
        address: '北京市朝阳区xxx大厦4楼',
        sort: 4,
        status: 1,
        memberCount: 12,
        isHeadquarters: false,
        createTime: '2024-01-01 00:00:00',
        description: '财务管理'
      }
    ]
  }
])

// 计算属性
const dialogTitle = computed(() => {
  if (isAddChild.value) {
    return `新增子部门 - ${parentDepartment.value?.name}`
  }
  return isEdit.value ? '编辑部门' : '新增部门'
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
  return countNodes(departmentList.value)
})

const departmentTreeOptions = computed(() => {
  const filterOptions = (nodes: any[], excludeId?: number): any[] => {
    return nodes.filter(node => node.id !== excludeId).map(node => ({
      id: node.id,
      name: node.name,
      children: node.children ? filterOptions(node.children, excludeId) : []
    }))
  }
  return filterOptions(departmentList.value, departmentForm.id)
})

const rootDepartment = computed(() => {
  return departmentList.value[0] || null
})

// 方法
const loadDepartmentList = async () => {
  loading.value = true
  try {
    // 模拟API调用
    await new Promise(resolve => setTimeout(resolve, 500))
    ElMessage.success(t('department.messages.loadSuccess'))
  } catch (error) {
    ElMessage.error(t('department.messages.loadError'))
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  loadDepartmentList()
}

const handleReset = () => {
  Object.assign(searchForm, {
    name: '',
    code: '',
    leader: '',
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
  expandedKeys.value = getAllKeys(departmentList.value)
}

const collapseAll = () => {
  expandedKeys.value = []
}

const handleAdd = () => {
  isEdit.value = false
  isAddChild.value = false
  parentDepartment.value = null
  resetDepartmentForm()
  dialogVisible.value = true
}

const handleAddChild = (row: any) => {
  isEdit.value = false
  isAddChild.value = true
  parentDepartment.value = row
  resetDepartmentForm()
  departmentForm.parentId = row.id
  dialogVisible.value = true
}

const handleEdit = (row: any) => {
  isEdit.value = true
  isAddChild.value = false
  parentDepartment.value = null
  Object.assign(departmentForm, { ...row })
  dialogVisible.value = true
}

const handleDelete = async (row: any) => {
  if (row.children && row.children.length > 0) {
    ElMessage.warning(t('department.messages.hasChildDepartments'))
    return
  }
  
  if (row.memberCount > 0) {
    ElMessage.warning(t('department.messages.hasEmployees'))
    return
  }
  
  try {
    await ElMessageBox.confirm(
      t('department.messages.deleteConfirm', { name: row.name }),
      t('department.messages.deleteTitle'),
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
    
    deleteNode(departmentList.value, row.id)
    ElMessage.success(t('department.messages.deleteSuccess'))
  } catch {
    ElMessage.info(t('common.cancelled'))
  }
}

const handleStatusChange = async (row: any) => {
  try {
    // 模拟状态更新
    await new Promise(resolve => setTimeout(resolve, 300))
    ElMessage.success(t('department.messages.statusUpdateSuccess', { status: row.status ? t('common.enabled') : t('common.disabled') }))
  } catch (error) {
    // 恢复原状态
    row.status = row.status ? 0 : 1
    ElMessage.error(t('department.messages.statusUpdateError'))
  }
}

const resetDepartmentForm = () => {
  Object.assign(departmentForm, {
    id: null,
    name: '',
    code: '',
    parentId: null,
    leaderId: null,
    phone: '',
    email: '',
    address: '',
    sort: 0,
    status: 1,
    description: ''
  })
  departmentFormRef.value?.clearValidate()
}

const handleSubmit = async () => {
  if (!departmentFormRef.value) return
  
  try {
    await departmentFormRef.value.validate()
    submitLoading.value = true
    
    // 模拟提交
    await new Promise(resolve => setTimeout(resolve, 1000))
    
    if (isEdit.value) {
      // 更新部门
      const updateNode = (nodes: any[], data: any): boolean => {
        for (const node of nodes) {
          if (node.id === data.id) {
            Object.assign(node, data)
            // 更新负责人名称
            const leader = userOptions.value.find(user => user.id === data.leaderId)
            node.leader = leader?.name || null
            return true
          }
          if (node.children && updateNode(node.children, data)) {
            return true
          }
        }
        return false
      }
      updateNode(departmentList.value, departmentForm)
      ElMessage.success(t('department.messages.updateSuccess'))
    } else {
      // 新增部门
      const leader = userOptions.value.find(user => user.id === departmentForm.leaderId)
      const newDepartment = {
        ...departmentForm,
        id: Date.now(),
        leader: leader?.name || null,
        memberCount: 0,
        isHeadquarters: false,
        children: [],
        createTime: new Date().toLocaleString()
      }
      
      if (departmentForm.parentId) {
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
        addToParent(departmentList.value, departmentForm.parentId, newDepartment)
      } else {
        // 添加到根节点
        departmentList.value.push(newDepartment)
      }
      
      ElMessage.success(t('department.messages.createSuccess'))
    }
    
    dialogVisible.value = false
    resetDepartmentForm()
  } catch (error) {
    console.error('表单验证失败:', error)
  } finally {
    submitLoading.value = false
  }
}

const previewOrganization = () => {
  orgChartVisible.value = true
}

const exportOrgChart = () => {
  ElMessage.info(t('common.featureInDevelopment'))
}

const syncOrganization = () => {
  ElMessage.success(t('department.messages.syncSuccess'))
}

const exportDepartments = () => {
  ElMessage.info(t('common.featureInDevelopment'))
}

// 组件挂载时加载数据
onMounted(() => {
  loadDepartmentList()
  // 默认展开第一级
  expandedKeys.value = departmentList.value.map(item => item.id)
})
</script>

<style scoped>
.department-list-page {
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

.org-chart-container {
  max-height: 600px;
  overflow: auto;
}

.org-chart {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}

.org-node {
  margin: 10px;
  position: relative;
}

.node-content {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 15px 20px;
  border-radius: 8px;
  text-align: center;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  min-width: 150px;
  transition: transform 0.3s ease;
}

.node-content:hover {
  transform: translateY(-2px);
}

.root-node .node-content {
  background: linear-gradient(135deg, #ff6b6b 0%, #ee5a24 100%);
  font-size: 16px;
  font-weight: bold;
}

.child-node .node-content {
  background: linear-gradient(135deg, #4ecdc4 0%, #44a08d 100%);
}

.grandchild-node .node-content {
  background: linear-gradient(135deg, #a8edea 0%, #fed6e3 100%);
  color: #333;
}

.node-title {
  font-weight: bold;
  margin-bottom: 5px;
}

.node-subtitle {
  font-size: 12px;
  opacity: 0.9;
}

.node-info {
  font-size: 11px;
  margin-top: 5px;
  opacity: 0.8;
}

.children-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  margin-top: 20px;
  position: relative;
}

.children-container::before {
  content: '';
  position: absolute;
  top: -20px;
  left: 50%;
  transform: translateX(-50%);
  width: 2px;
  height: 20px;
  background: #ddd;
}

.el-table :deep(.el-table__expand-icon) {
  color: #409eff;
}

.el-table :deep(.el-table__indent) {
  padding-left: 20px;
}
</style>