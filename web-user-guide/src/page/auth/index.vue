<template>
  <wrapper :data="steps" :type="isIdentityVerification" class="new-auth"></wrapper>
</template>
<script>
import wrapper from './components/wrapper'
export default {
  components: {
    wrapper
  },
  data: () => ({
    isIdentityVerification: 0,
    list: {},
    steps: ['添加模板', '设置门户', '设置栏目', '个性化', '应用设置', '完善信息']
  }),
  created () {
    // 查询模板 步骤
    if (this.$step) {
      this.findSetp(this.$step.templateId)
    }
  },
  methods: {
    // 查询模板 步骤
    findSetp (val) {
      this.$api.post('/member-reversion/realStep/findStep', {
        account: this.$user.loginAccount,
        templateId: val
      }).then(response => {
        if (response.code === 200) {
          console.log(response)
          if (!response.data) {
            this.isIdentityVerification = 0
          } else {
            let list = response.data
            this.list = list
            this.isIdentityVerification = Number(response.data.step)
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
