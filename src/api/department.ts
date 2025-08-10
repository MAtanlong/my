import { request } from './index'

// 部门信息
export interface Department {
  id: string
  departmentName: string
  departmentCode: string
  parentId: string
  description: string
  sort: number
  status: number
  leader: string
  phone: string
  email: string
  address: string
  children?: Department[]
  createTime: string
  updateTime: string
}

// 部门列表查询参数
export interface DepartmentListParams {
  page?: number
  size?: number
  departmentName?: string
  departmentCode?: string
  status?: number
  parentId?: string
}

// 部门列表响应
export interface DepartmentListResponse {
  code: number
  message: string
  data: {
    records: Department[]
    total: number
    current: number
    size: number
  }
}

// 部门树响应
export interface DepartmentTreeResponse {
  code: number
  message: string
  data: Department[]
}

// 创建/更新部门请求
export interface DepartmentRequest {
  id?: string
  departmentName: string
  departmentCode: string
  parentId: string
  description: string
  sort: number
  status: number
  leader: string
  phone: string
  email: string
  address: string
}

// 部门相关API
export const departmentApi = {
  // 获取部门列表
  getDepartmentList(params: DepartmentListParams): Promise<DepartmentListResponse> {
    return request.get('/api/department/list', { params })
  },
  
  // 获取部门树
  getDepartmentTree(): Promise<DepartmentTreeResponse> {
    return request.get('/api/department/tree')
  },
  
  // 获取部门详情
  getDepartmentById(id: string): Promise<{ code: number; message: string; data: Department }> {
    return request.get(`/api/department/${id}`)
  },
  
  // 创建部门
  createDepartment(data: DepartmentRequest): Promise<any> {
    return request.post('/api/department', data)
  },
  
  // 更新部门
  updateDepartment(id: string, data: DepartmentRequest): Promise<any> {
    return request.put(`/api/department/${id}`, data)
  },
  
  // 删除部门
  deleteDepartment(id: string): Promise<any> {
    return request.delete(`/api/department/${id}`)
  },
  
  // 批量删除部门
  batchDeleteDepartments(ids: string[]): Promise<any> {
    return request.delete('/api/department/batch', { data: { ids } })
  },
  
  // 更新部门状态
  updateDepartmentStatus(id: string, status: number): Promise<any> {
    return request.patch(`/api/department/${id}/status`, { status })
  },
  
  // 获取部门用户列表
  getDepartmentUsers(departmentId: string): Promise<any> {
    return request.get(`/api/department/${departmentId}/users`)
  },
  
  // 同步组织架构
  syncOrganization(): Promise<any> {
    return request.post('/api/department/sync')
  }
}