import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

export default new Router({
  mode: 'history',
  base: '/wiki',
  scrollBehavior (to, from, savedPosition) {
    if (to.hash) {
      return {
        selector: to.hash
      }
    }
  },
  routes: [
    {
      path: '/',
      component: resolve => require(['~pages/index/index'], resolve)
    }, {
      path: '/detail',
      name: 'detail',
      component: resolve => require(['~pages/detail/detail'], resolve)
    }, {
      path: '/disease-detail',
      name: 'disease',
      component: resolve => require(['~pages/disease-detail/index'], resolve)
    }, {
      path: '/disease-animal-detail',
      name: 'animal',
      component: resolve => require(['~pages/disease-animal-detail/index'], resolve)
    }, {
      path: '/pests-detail',
      name: 'pests',
      component: resolve => require(['~pages/pests-detail/index'], resolve)
    }, {
      path: '/variety-detail',
      name: 'variety',
      component: resolve => require(['~pages/variety-detail/index'], resolve)
    }
  ]
})
