<template>
  <wrapper :data="steps" class="new-auth" :type="isIdentityVerification" :id="id"></wrapper>
</template>
<script>
import wrapper from './components/wrapper'
export default {
  components: {
    wrapper
  },
  data: () => ({
    isIdentityVerification: 0,
    id: '',
    steps: ['选择模板', '设置门户', '设置栏目', '个性化', '应用设置', '实名认证', '完善信息']
  }),
  created () {
    if (this.$route.query.templateId) {
      this.$api.post('/member-reversion/realStep/findStep', {
          account: this.$user.loginAccount,
          templateId: this.$route.query.templateId
      }).then(response => {
          if (response.code === 200) {
              // reload 存在， 说明是点击模板后查询的 如果 response.data 存在 那么则需要修改url
              if (response.data) {
                  this.isIdentityVerification = Number(response.data.step)
                  this.id = response.data.templateId
              } else {
                this.id = ''
                this.isIdentityVerification = 0
              }
          }
      })
    }
  }
}
</script>
<style lang="scss">
.layouts{
  width: 1200px;
  margin: 0 auto;
}
</style>
