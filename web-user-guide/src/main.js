import Vue from 'vue';
import iView from 'iview';
import router from './router';
import App from './app.vue';
import vueScrollActive from 'vue-scrollactive';
import '../theme/dist/iview.css';
import './scss/WMlib.scss'

import moment from 'moment'; //日期格式化

import url from '~src/api/config';
import api from '~api/index';

// 实际打包时应该不引入mock
import env from '~src/api/env'
/* eslint-disable */
env === 'development' ? require('~src/mock') : ''

Vue.prototype.moment = moment;
Vue.prototype.$api = api;
Vue.prototype.$url = url;
Vue.prototype.$map = null
Vue.prototype.$step = JSON.parse(sessionStorage.getItem('stepData'))
Vue.prototype.$user = JSON.parse(sessionStorage.getItem('user')) ? JSON.parse(sessionStorage.getItem('user')).proxy[0].session : null
Vue.prototype.$template = JSON.parse(sessionStorage.getItem('templateData'))
Vue.use(iView);
Vue.use(vueScrollActive);

router.beforeEach((to, from, next) => {
    if(to.path !== '/index') {
      window.scrollTo(0, 0);
    }
    if(to.meta.auth){
        const user = sessionStorage.getItem('user')
        if(user === null){
            iView.Message.error('请先登录')
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
    el: '#app',
    router: router,
    render: h => h(App)
});
