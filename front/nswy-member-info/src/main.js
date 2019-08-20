import Vue from 'vue'
import App from './App.vue'
import api from '@/api'
import 'normalize.css'
import '../public/WMlib/WMlib.scss'

// 实际打包时应该不引入mock
import env from '@/api/env'
/* eslint-disable */
env === 'development' ? require('@/mock') : ''

Vue.config.productionTip = false
Vue.prototype.$api = api

new Vue({
  render: h => h(App)
}).$mount('#app')
