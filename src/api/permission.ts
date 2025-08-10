import { request } from './index'

// 权限信息
export interface Permission {
  id: string
  permissionName: string
  permissionCode: string
  permissionType: 'MENU' | 'BUTTON' | 'API'
  parentId: string
  path: string
  component: string
  icon: string
  sort: number
  status: number
  description: string
  children?: Permission[]
  createTime: string
  updateTime: string
}

// 权限列表查询参数
export interface PermissionListParams {
  page?: number
  size?: number
  permissionName?: string
  permissionCode?: string
  permissionType?: string
  status?: number
  parentId?: string
}

// 权限列表响应
export interface PermissionListResponse {
  code: number
  message: string
  data: {
    records: Permission[]
    total: number
    current: number
    size: number
  }
}

// 权限树响应
export interface PermissionTreeResponse {
  code: number
  message: string
  data: Permission[]
}

// 创建/更新权限请求
export interface PermissionRequest {
  id?: string
  permissionName: string
  permissionCode: string
  permissionType: 'MENU' | 'BUTTON' | 'API'
  parentId: string
  path: string
  component: string
  icon: string
  sort: number
  status: number
  description: string
}

// 权限相关API
export const permissionApi = {
  // 获取权限列表
  getPermissionList(params: PermissionListParams): Promise<PermissionListResponse> {
    return request.get('/api/permission/list', { params })
  },
  
  // 获取权限树
  getPermissionTree(): Promise<PermissionTreeResponse> {
    return request.get('/api/permission/tree')
  },
  
  // 获取权限详情
  getPermissionById(id: string): Promise<{ code: number; message: string; data: Permission }> {
    return request.get(`/api/permission/${id}`)
  },
  
  // 创建权限
  createPermission(data: PermissionRequest): Promise<any> {
    return request.post('/api/permission', data)
  },
  
  // 更新权限
  updatePermission(id: string, data: PermissionRequest): Promise<any> {
    return request.put(`/api/permission/${id}`, data)
  },
  
  // 删除权限
  deletePermission(id: string): Promise<any> {
    return request.delete(`/api/permission/${id}`)
  },
  
  // 批量删除权限
  batchDeletePermissions(ids: string[]): Promise<any> {
    return request.delete('/api/permission/batch', { data: { ids } })
  },
  
  // 更新权限状态
  updatePermissionStatus(id: string, status: number): Promise<any> {
    return request.patch(`/api/permission/${id}/status`, { status })
  },
  
  // 获取用户权限
  getUserPermissions(userId: string): Promise<{ code: number; message: string; data: Permission[] }> {
    return request.get(`/api/permission/user/${userId}`)
  },
  
  // 获取角色权限
  getRolePermissions(roleId: string): Promise<{ code: number; message: string; data: Permission[] }> {
    return request.get(`/api/permission/role/${roleId}`)
  }
}