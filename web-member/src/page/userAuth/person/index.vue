<template>
  <wrapper :data="steps" :type="isIdentityVerification === 6 ? 3 : 0"></wrapper>
</template>
<script>
import wrapper from '../components/wrapper'
export default {
  components: {
    wrapper
  },
  data: () => ({
    loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
    isIdentityVerification: 0,
    steps: ['个人认证', '设置栏目', '个性化', '应用设置', '完善信息', '网站设置']
  }),
  created () {
    this.$api.post('/member/login/findCurrentUser', {
        account: this.loginUser.loginAccount
    }).then(res => {
      this.isIdentityVerification = parseInt(res.data.isIdentityVerification)
    })
  }
}
</script>
<style lang="scss">
.layouts{
  width: 1200px;
  margin: 0 auto;
}
</style>
