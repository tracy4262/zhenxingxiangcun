// 导入Vue和VueRouter
import Vue from 'vue'
import VueRouter from 'vue-router'
// 选择
import index from './page/index'

// 认证
import auth from '~auth/index'
import authStep1 from '~auth/step1/index'
import authStep2 from '~auth/step2/index'
import authStep3 from '~auth/step3/index'
import authStep4 from '~auth/step4/index'
import authStep5 from '~auth/step5/index'
import authStep6 from '~auth/step6/index'

// 商品 模板配置
import good from './page/good/index'
import goodStep1 from './page/good/step1/index'
import goodStep2 from './page/good/step2/index'
import goodStep3 from './page/good/step3/index'
import goodStep4 from './page/good/step4/index'
import goodStep5 from './page/good/step5/index'
import goodStep6 from './page/good/step6/index'

Vue.use(VueRouter)

const routers = new VueRouter({
  mode: 'history',
  base: '/user-auth-admin',
  routes: [
    {
      path: '/index',
      component: index,
    },
    {
      path: '/good',
      component: good,
      meta: {
        auth: true
      },
      children: [
        {
          path: 'step1',
          component: goodStep1,
          meta: {
            auth: true
          },
        },{
          path: 'step2',
          component: goodStep2,
          meta: {
            auth: true
          },
        },{
          path: 'step3',
          component: goodStep3,
          meta: {
            auth: true
          },
        },
        {
          path: 'step4',
          component: goodStep4,
          meta: {
            auth: true
          },
        },
        {
          path: 'step5',
          component: goodStep5,
          meta: {
            auth: true
          },
        },
        {
          path: 'step6',
          component: goodStep6,
          meta: {
            auth: true
          },
        }
      ]
    },
    {
      // 认证
      path: '/auth',
      component: auth,
      redirect: '/auth/step1',
      meta: {
        auth: true
      },
      children: [
        {
          path: 'step1',
          component: authStep1,
          meta: {
            auth: true
          }
        },
        {
          path: 'step2',
          component: authStep2,
          meta: {
            auth: true
          }
        },
        {
          path: 'step3',
          component: authStep3,
          meta: {
            auth: true
          }
        },
        {
          path: 'step4',
          component: authStep4,
          meta: {
            auth: true
          }
        },
        {
          path: 'step5',
          component: authStep5,
          meta: {
            auth: true
          }
        },
        {
          path: 'step6',
          component: authStep6,
          meta: {
            auth: true
          }
        }
      ]
    }
  ]
})

export default routers
