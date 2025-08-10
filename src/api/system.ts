import { request } from './index'

// 系统配置信息
export interface SystemConfig {
  id: string
  configKey: string
  configValue: string
  configName: string
  configType: string
  description: string
  status: number
  createTime: string
  updateTime: string
}

// 系统配置列表查询参数
export interface SystemConfigListParams {
  page: number
  size: number
  configKey?: string
  configName?: string
  configType?: string
  status?: number
}

// 系统配置列表响应
export interface SystemConfigListResponse {
  code: number
  message: string
  data: {
    records: SystemConfig[]
    total: number
    current: number
    size: number
  }
}

// 创建/更新系统配置请求
export interface SystemConfigRequest {
  id?: string
  configKey: string
  configValue: string
  configName: string
  configType: string
  description: string
  status: number
}

// 系统信息
export interface SystemInfo {
  serverInfo: {
    serverName: string
    serverIp: string
    osName: string
    osArch: string
    javaVersion: string
    javaVendor: string
    javaHome: string
    startTime: string
    runTime: string
  }
  memoryInfo: {
    total: string
    used: string
    free: string
    usage: string
  }
  diskInfo: {
    total: string
    used: string
    free: string
    usage: string
  }
}

// 系统相关API
export const systemApi = {
  // 获取系统配置列表
  getSystemConfigList(params: SystemConfigListParams): Promise<SystemConfigListResponse> {
    return request.get('/api/system/config/list', { params })
  },
  
  // 获取系统配置详情
  getSystemConfigById(id: string): Promise<{ code: number; message: string; data: SystemConfig }> {
    return request.get(`/api/system/config/${id}`)
  },
  
  // 根据配置键获取配置值
  getSystemConfigByKey(configKey: string): Promise<{ code: number; message: string; data: string }> {
    return request.get(`/api/system/config/key/${configKey}`)
  },
  
  // 创建系统配置
  createSystemConfig(data: SystemConfigRequest): Promise<any> {
    return request.post('/api/system/config', data)
  },
  
  // 更新系统配置
  updateSystemConfig(id: string, data: SystemConfigRequest): Promise<any> {
    return request.put(`/api/system/config/${id}`, data)
  },
  
  // 删除系统配置
  deleteSystemConfig(id: string): Promise<any> {
    return request.delete(`/api/system/config/${id}`)
  },
  
  // 批量删除系统配置
  batchDeleteSystemConfigs(ids: string[]): Promise<any> {
    return request.delete('/api/system/config/batch', { data: { ids } })
  },
  
  // 更新系统配置状态
  updateSystemConfigStatus(id: string, status: number): Promise<any> {
    return request.patch(`/api/system/config/${id}/status`, { status })
  },
  
  // 获取系统信息
  getSystemInfo(): Promise<{ code: number; message: string; data: SystemInfo }> {
    return request.get('/api/system/info')
  },
  
  // 清理系统缓存
  clearCache(): Promise<any> {
    return request.post('/api/system/cache/clear')
  },
  
  // 获取系统日志
  getSystemLogs(params: { page: number; size: number; level?: string; startTime?: string; endTime?: string }): Promise<any> {
    return request.get('/api/system/logs', { params })
  },
  
  // 导出系统配置
  exportSystemConfig(): Promise<Blob> {
    return request.get('/api/system/config/export', { responseType: 'blob' })
  },
  
  // 导入系统配置
  importSystemConfig(file: File): Promise<any> {
    const formData = new FormData()
    formData.append('file', file)
    return request.post('/api/system/config/import', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
  }
}