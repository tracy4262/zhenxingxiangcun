<template>
  <div>
    <Card>
      <!-- <p class="t-orange pt20 pb20">您的企业帐号未进行实名注册，为营造健康的信息服务环境，提高服务精准度，邀请您 使用有效身份证进行实名注册。</p> -->
      <Title title="企业基本信息"></Title>
      <combasic ref="combasic"></combasic>
      <Title title="法人基本信息"></Title>
      <perbasic ref="perbasic"></perbasic>
      <div class="tc pd20">
        <Button type="primary" @click="handleClickNext">下一步</Button>
      </div>
    </Card>
    <br>
  </div>
</template>
<script>
import Title from '../components/title'
import combasic from './enterpriseBasicInformation'
import perbasic from './legalPersonBasic'
export default {
  components: {
    Title,
    combasic,
    perbasic
  },
  data: () => ({
  }),
  created () {
    this.$api.post('/member/proxy/queryInfoDetail', {
      login_account: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount,
      flag: 0
    }).then(response => {
      console.log('response123', response)
      if (response.code === 200 && response.data) {
        this.$refs.combasic.corpInfo = response.data
        this.$refs.perbasic.corpInfo2 = response.data
      }
    }).catch(error => {
        this.$Message.error('服务器异常！')
    })
  },
  methods: {
    // 下一步
    handleClickNext () {
      this.$router.push('/auth/comAuth/step2')
    }
  }
}
</script>
<style lang="scss">
</style>
