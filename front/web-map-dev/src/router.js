import Vue from 'vue'
import Router from 'vue-router'
import edit from './views/edit.vue'
import view from './views/view.vue'
import addMap from './views/addMap.vue'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: '/map',
  routes: [
    {
      path: '/',
      name: 'view',
      component: view,
      meta: {
        auth: true
      }
    },
    {
      path: '/edit',
      name: 'edit',
      component: edit,
      meta: {
        auth: true
      }
    },
    {
      path: '/addMap',
      name: 'addMap',
      component: addMap,
      meta: {
        auth: true
      }
    }
  ]
})
