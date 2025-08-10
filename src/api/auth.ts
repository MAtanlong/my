import { request } from './index'

// 登录请求参数
export interface LoginRequest {
  username?: string
  password?: string
  captcha?: string
  phone?: string
  smsCode?: string
  loginType: 'username' | 'phone'
}

// 登录响应数据
export interface LoginResponse {
  code: number
  message: string
  data: {
    token: string
    refreshToken: string
    userInfo: {
      id: string
      username: string
      realName: string
      email: string
      avatar: string
      roles: string[]
      permissions: string[]
    }
  }
}

// 用户信息响应
export interface UserInfoResponse {
  code: number
  message: string
  data: {
    id: string
    username: string
    realName: string
    email: string
    avatar: string
    roles: string[]
    permissions: string[]
  }
}

// 刷新token响应
export interface RefreshTokenResponse {
  code: number
  message: string
  data: {
    token: string
    refreshToken: string
  }
}

// 认证相关API
export const authApi = {
  // 用户登录
  login(data: LoginRequest): Promise<LoginResponse> {
    return request.post('/api/auth/login', data)
  },
  
  // 用户登出
  logout(): Promise<any> {
    return request.post('/api/auth/logout')
  },
  
  // 获取用户信息
  getUserInfo(): Promise<UserInfoResponse> {
    return request.get('/api/auth/userinfo')
  },
  
  // 刷新token
  refreshToken(refreshToken: string): Promise<RefreshTokenResponse> {
    return request.post('/api/auth/refresh', { refreshToken })
  },
  
  // 发送短信验证码
  sendSmsCode(phone: string): Promise<any> {
    return request.post('/api/auth/sms/send', { phone })
  },
  
  // 获取图片验证码
  getCaptcha(): Promise<any> {
    return request.get('/api/auth/captcha')
  }
}