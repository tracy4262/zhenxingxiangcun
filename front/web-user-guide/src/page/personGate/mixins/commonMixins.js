
// 导航状态
export const navStatus = {
  data () {
      return {
        index: 0
      }
  },
  created(){
      this.data.forEach(item => {
          item.checked = false
      })
      this.data[this.index].checked = true
  }
}

// 用户信息
export const userInfo = {
  data () {
    return {
      userInfo: {},
    }
  },
  created () {
    this.userInfo=JSON.parse(sessionStorage.getItem('user'))
  }
}