import Vue from 'vue'
import VueRouter from 'vue-router'
import * as  cookies from '@/util/cookies'
import Layout from '@/layout'

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import( '@/views/Login.vue')
  },
  {
    
    path: '/',
    component: Layout,
    children: [
      {
        path: '',
        redirect:'/home'
      },
      {
        path: 'home',
        name: 'Home',
        component:  () => import('@/views/Home.vue'),
        meta: {
          requiresAuth:true
        }
      },
      {
        path: 'bugs',
        name: 'Bugs',
        component:  () => import('@/views/Bugs.vue'),
        meta: {
          requiresAuth:true
        }
      },
      {
        path: 'users',
        name: 'Users',
        component:  () => import('@/views/Users.vue'),
        meta: {
          requiresAuth:true
        }
      },
      {
        path: 'versions',
        name: 'Versions',
        component: () => import( '../views/Versions.vue'),
        meta: {
          requiresAuth:true
        }
      }
    ]
  }
]

const router = new VueRouter({
  routes
})

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
      let currentUser = cookies.getCurrentUser()
      if (!currentUser) {
        next({
          path: '/login',
          query: { redirect: to.fullPath }
        })
      } else {
        next()
      }
    } else {
      next() 
    }
})

export default router
