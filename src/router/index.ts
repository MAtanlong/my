import { createRouter, createWebHistory } from 'vue-router'
import AppLayout from '../components/Layout/AppLayout.vue'
import LoginPage from '../pages/LoginPage.vue'
import DashboardPage from '../pages/DashboardPage.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: LoginPage,
      meta: {
        title: '登录',
        requiresAuth: false
      }
    },
    {
      path: '/',
      component: AppLayout,
      redirect: '/dashboard',
      meta: {
        requiresAuth: true
      },
      children: [
        {
          path: 'dashboard',
          name: 'dashboard',
          component: DashboardPage,
          meta: {
            title: '首页仪表板',
            icon: 'Dashboard'
          }
        },
        {
          path: 'user',
          name: 'user',
          meta: {
            title: '用户管理',
            icon: 'User'
          },
          children: [
            {
              path: 'list',
              name: 'userList',
              component: () => import('../pages/User/UserListPage.vue'),
              meta: {
                title: '用户列表'
              }
            }
          ]
        },
        {
          path: 'role',
          name: 'role',
          meta: {
            title: '角色管理',
            icon: 'Lock'
          },
          children: [
            {
              path: 'list',
              name: 'roleList',
              component: () => import('../pages/Role/RoleListPage.vue'),
              meta: {
                title: '角色列表'
              }
            }
          ]
        },
        {
          path: 'permission',
          name: 'permission',
          meta: {
            title: '权限管理',
            icon: 'Key'
          },
          children: [
            {
              path: 'list',
              name: 'permissionList',
              component: () => import('../pages/Permission/PermissionListPage.vue'),
              meta: {
                title: '权限列表'
              }
            }
          ]
        },
        {
          path: 'menu',
          name: 'menu',
          meta: {
            title: '菜单管理',
            icon: 'Menu'
          },
          children: [
            {
              path: 'list',
              name: 'menuList',
              component: () => import('../pages/Menu/MenuListPage.vue'),
              meta: {
                title: '菜单列表'
              }
            }
          ]
        },
        {
          path: 'department',
          name: 'department',
          meta: {
            title: '部门管理',
            icon: 'OfficeBuilding'
          },
          children: [
            {
              path: 'list',
              name: 'departmentList',
              component: () => import('../pages/Department/DepartmentListPage.vue'),
              meta: {
                title: '部门列表'
              }
            }
          ]
        },
        {
          path: 'system',
          name: 'system',
          meta: {
            title: '系统设置',
            icon: 'Setting'
          },
          children: [
            {
              path: 'settings',
              name: 'systemSettings',
              component: () => import('../pages/Settings/SystemSettingsPage.vue'),
              meta: {
                title: '系统设置'
              }
            }
          ]
        },
        {
          path: 'profile',
          name: 'profile',
          component: () => import('../pages/Profile/ProfilePage.vue'),
          meta: {
            title: '个人中心',
            icon: 'UserFilled'
          }
        }
      ]
    }
  ],
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  
  if (to.meta.requiresAuth && !token) {
    // 需要登录但没有token，跳转到登录页
    next('/login')
  } else if (to.path === '/login' && token) {
    // 已登录用户访问登录页，跳转到首页
    next('/dashboard')
  } else {
    next()
  }
})

export default router
