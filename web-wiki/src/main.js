import Vue from 'vue'
import router from '~src/router'
import Meta from 'vue-meta'
import iView from 'iview'
import vueScrollActive from 'vue-scrollactive'
import '../theme/dist/iview'
import '~static/scss/WMlib'
import api from '~api'
import url from './api/config'
import App from './App'
import fecha from 'fecha' // 日期格式化
import moment from 'moment'
import {toPortals} from './utils/toPortals'

Vue.use(Meta)
Vue.use(iView)
Vue.use(vueScrollActive)
Vue.prototype.$api = api
Vue.prototype.$url = url
Vue.prototype.$fecha = fecha
Vue.prototype.moment = moment
Vue.prototype.$toPortals = toPortals
Vue.prototype.$user = JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
// 阻止 vue 在启动时生成生产提示
// Vue.config.productionTip = false

// 使用 router.beforeEach 注册一个全局的 before 钩子：
router.beforeEach((to, from, next) => {
  iView.LoadingBar.finish()
  window.scrollTo(0, 0)
  next()
})

// 创建和挂载根实例 记得要通过 router 配置参数注入路由，从而让整个应用都有路由功能
new Vue({
  router,
  ...App
}).$mount('#app')
