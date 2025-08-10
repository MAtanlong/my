import { request } from './index'

// 菜单信息
export interface Menu {
  id: string
  menuName: string
  menuCode: string
  parentId: string
  path: string
  component: string
  icon: string
  sort: number
  status: number
  menuType: 'DIRECTORY' | 'MENU' | 'BUTTON'
  visible: boolean
  keepAlive: boolean
  redirect: string
  description: string
  children?: Menu[]
  createTime: string
  updateTime: string
}

// 菜单列表查询参数
export interface MenuListParams {
  page?: number
  size?: number
  menuName?: string
  menuCode?: string
  status?: number
  parentId?: string
  menuType?: string
}

// 菜单列表响应
export interface MenuListResponse {
  code: number
  message: string
  data: {
    records: Menu[]
    total: number
    current: number
    size: number
  }
}

// 菜单树响应
export interface MenuTreeResponse {
  code: number
  message: string
  data: Menu[]
}

// 创建/更新菜单请求
export interface MenuRequest {
  id?: string
  menuName: string
  menuCode: string
  parentId: string
  path: string
  component: string
  icon: string
  sort: number
  status: number
  menuType: 'DIRECTORY' | 'MENU' | 'BUTTON'
  visible: boolean
  keepAlive: boolean
  redirect: string
  description: string
}

// 菜单相关API
export const menuApi = {
  // 获取菜单列表
  getMenuList(params: MenuListParams): Promise<MenuListResponse> {
    return request.get('/api/menu/list', { params })
  },
  
  // 获取菜单树
  getMenuTree(): Promise<MenuTreeResponse> {
    return request.get('/api/menu/tree')
  },
  
  // 获取用户菜单树
  getUserMenuTree(): Promise<MenuTreeResponse> {
    return request.get('/api/menu/user/tree')
  },
  
  // 获取菜单详情
  getMenuById(id: string): Promise<{ code: number; message: string; data: Menu }> {
    return request.get(`/api/menu/${id}`)
  },
  
  // 创建菜单
  createMenu(data: MenuRequest): Promise<any> {
    return request.post('/api/menu', data)
  },
  
  // 更新菜单
  updateMenu(id: string, data: MenuRequest): Promise<any> {
    return request.put(`/api/menu/${id}`, data)
  },
  
  // 删除菜单
  deleteMenu(id: string): Promise<any> {
    return request.delete(`/api/menu/${id}`)
  },
  
  // 批量删除菜单
  batchDeleteMenus(ids: string[]): Promise<any> {
    return request.delete('/api/menu/batch', { data: { ids } })
  },
  
  // 更新菜单状态
  updateMenuStatus(id: string, status: number): Promise<any> {
    return request.patch(`/api/menu/${id}/status`, { status })
  },
  
  // 获取角色菜单
  getRoleMenus(roleId: string): Promise<{ code: number; message: string; data: Menu[] }> {
    return request.get(`/api/menu/role/${roleId}`)
  },
  
  // 分配菜单给角色
  assignMenusToRole(roleId: string, menuIds: string[]): Promise<any> {
    return request.post(`/api/menu/role/${roleId}/assign`, { menuIds })
  }
}