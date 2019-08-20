import Vue from 'vue'
import App from './App.vue'
import router from './router'
import iView from 'iview'
import "ol/ol.css";
import './css/them.less'
import './css/index.less'
import config from '@/config'
import api from '@/api'
import moment from 'moment' //日期格式化
import { toPortals } from './utils/toPortals';

/**
 * @description 生产环境关掉提示
 */
Vue.config.productionTip = false
Vue.use(iView)

/**
 * @description 全局注册应用配置
 */
Vue.prototype.$config = config
Vue.prototype.$api = api
Vue.prototype.$toPortals = toPortals
// Vue.prototype.$user = JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
Vue.prototype.$user = { loginAccount: '20181010' }
Vue.prototype.moment = moment

router.beforeEach((to, from, next) => {
  if(to.meta.auth){
    // const user = sessionStorage.getItem(sessionStorage.getItem('key'))
    const user = { loginAccount: '20181010' }
    if(user === null){
      iView.Message.error('请先登录')
      // next({ path: '/index' });
      window.location.href = `${window.location.origin}/index`
    } else {
      next();
    }
  }else{
    next();
  }
});

router.afterEach((to, from, next) => {
  iView.LoadingBar.finish();
  if(from.meta !== {}) {
      if(from.meta.isScroll) {
          window.scrollTo(0, 0);
      }
  }
});

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
