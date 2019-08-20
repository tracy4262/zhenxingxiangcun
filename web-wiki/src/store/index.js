import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    // 定义了应用状态的数据结构，可以在这里设置默认的初始状态
    loginState: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))) || null,
    loginShow: false
  },
  actions: {
    // 定义提交触发更改信息的描述
  },
  mutations: {
    // 唯一允许更新应用状态的地方
    login (state) {
      if (JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))) {
        state.loginShow = false
        state.loginState = true
      } else {
        state.loginShow = true
      }
    },
    logout (state) {
      state.loginState = false
      state.loginShow = false
    }
  },
  getters: {
    loginState: function (state) {
      if (JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))) {
        return true
      } else {
        return false
      }
    }
    // 允许组件从 Store 中获取数据
  }
  // modules: {
  //     // 允许将单一的 Store 拆分为多个 Store 的同时保存在单一的状态树中
  //     login,
  //     index
  // }

})
