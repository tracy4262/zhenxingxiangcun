// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import iView from 'iview'
import '../theme/dist/iview.css'
import App from './App'
import router from './router'
import url from '../src/api/config.js'
import api from './api'
import {toPortals} from './utils/toPortals';

Vue.use(iView)
Vue.config.productionTip = false
Vue.prototype.$api = api
Vue.prototype.$url=url
Vue.prototype.$toPortals=toPortals
Vue.prototype.$user = JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
// axios.defaults.withCredentials = true

router.beforeEach((to, from, next) => {
  window.scrollTo(0, 0)
  next()
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  render: h => h(App)
})
