import { request } from './index'

// 用户信息
export interface User {
  id: string
  username: string
  realName: string
  email: string
  phone: string
  avatar: string
  status: number
  roles: string[]
  departmentId: string
  departmentName: string
  createTime: string
  updateTime: string
}

// 用户列表查询参数
export interface UserListParams {
  page: number
  size: number
  username?: string
  realName?: string
  email?: string
  phone?: string
  status?: number
  departmentId?: string
  roleId?: string
}

// 用户列表响应
export interface UserListResponse {
  code: number
  message: string
  data: {
    records: User[]
    total: number
    current: number
    size: number
  }
}

// 创建/更新用户请求
export interface UserRequest {
  id?: string
  username: string
  realName: string
  email: string
  phone: string
  password?: string
  avatar?: string
  status: number
  roleIds: string[]
  departmentId: string
}

// 用户相关API
export const userApi = {
  // 获取用户列表
  getUserList(params: UserListParams): Promise<UserListResponse> {
    return request.get('/api/user/list', { params })
  },
  
  // 获取用户详情
  getUserById(id: string): Promise<{ code: number; message: string; data: User }> {
    return request.get(`/api/user/${id}`)
  },
  
  // 创建用户
  createUser(data: UserRequest): Promise<any> {
    return request.post('/api/user', data)
  },
  
  // 更新用户
  updateUser(id: string, data: UserRequest): Promise<any> {
    return request.put(`/api/user/${id}`, data)
  },
  
  // 删除用户
  deleteUser(id: string): Promise<any> {
    return request.delete(`/api/user/${id}`)
  },
  
  // 批量删除用户
  batchDeleteUsers(ids: string[]): Promise<any> {
    return request.delete('/api/user/batch', { data: { ids } })
  },
  
  // 更新用户状态
  updateUserStatus(id: string, status: number): Promise<any> {
    return request.patch(`/api/user/${id}/status`, { status })
  },
  
  // 重置用户密码
  resetPassword(id: string, newPassword: string): Promise<any> {
    return request.patch(`/api/user/${id}/password`, { password: newPassword })
  },
  
  // 更新用户头像
  updateAvatar(id: string, avatar: string): Promise<any> {
    return request.patch(`/api/user/${id}/avatar`, { avatar })
  },
  
  // 导出用户数据
  exportUsers(params: UserListParams): Promise<Blob> {
    return request.get('/api/user/export', { 
      params, 
      responseType: 'blob' 
    })
  },
  
  // 导入用户数据
  importUsers(file: File): Promise<any> {
    const formData = new FormData()
    formData.append('file', file)
    return request.post('/api/user/import', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
  }
}