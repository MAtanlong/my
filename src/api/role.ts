import { request } from './index'

// 角色信息
export interface Role {
  id: string
  roleName: string
  roleCode: string
  description: string
  status: number
  sort: number
  permissions: string[]
  createTime: string
  updateTime: string
}

// 角色列表查询参数
export interface RoleListParams {
  page: number
  size: number
  roleName?: string
  roleCode?: string
  status?: number
}

// 角色列表响应
export interface RoleListResponse {
  code: number
  message: string
  data: {
    records: Role[]
    total: number
    current: number
    size: number
  }
}

// 创建/更新角色请求
export interface RoleRequest {
  id?: string
  roleName: string
  roleCode: string
  description: string
  status: number
  sort: number
  permissionIds: string[]
}

// 角色相关API
export const roleApi = {
  // 获取角色列表
  getRoleList(params: RoleListParams): Promise<RoleListResponse> {
    return request.get('/api/role/list', { params })
  },
  
  // 获取所有角色（不分页）
  getAllRoles(): Promise<{ code: number; message: string; data: Role[] }> {
    return request.get('/api/role/all')
  },
  
  // 获取角色详情
  getRoleById(id: string): Promise<{ code: number; message: string; data: Role }> {
    return request.get(`/api/role/${id}`)
  },
  
  // 创建角色
  createRole(data: RoleRequest): Promise<any> {
    return request.post('/api/role', data)
  },
  
  // 更新角色
  updateRole(id: string, data: RoleRequest): Promise<any> {
    return request.put(`/api/role/${id}`, data)
  },
  
  // 删除角色
  deleteRole(id: string): Promise<any> {
    return request.delete(`/api/role/${id}`)
  },
  
  // 批量删除角色
  batchDeleteRoles(ids: string[]): Promise<any> {
    return request.delete('/api/role/batch', { data: { ids } })
  },
  
  // 更新角色状态
  updateRoleStatus(id: string, status: number): Promise<any> {
    return request.patch(`/api/role/${id}/status`, { status })
  },
  
  // 分配权限给角色
  assignPermissions(roleId: string, permissionIds: string[]): Promise<any> {
    return request.post(`/api/role/${roleId}/permissions`, { permissionIds })
  },
  
  // 获取角色的权限列表
  getRolePermissions(roleId: string): Promise<{ code: number; message: string; data: string[] }> {
    return request.get(`/api/role/${roleId}/permissions`)
  }
}