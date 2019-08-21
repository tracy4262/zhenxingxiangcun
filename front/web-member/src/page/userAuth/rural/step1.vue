<template>
    <div>
        <Card>
          <!-- <p class="t-orange pt20 pb20">您的乡村帐号未进行实名注册，为营造健康的信息服务环境，提高服务精准度，邀请您 使用有效身份证进行实名注册。</p> -->
          <Title title="乡村基本信息"></Title>
          <basic-info ref="basicInfo"></basic-info>
          <div class="tc pd20">
            <Button type="primary" @click="handleClickNext">下一步</Button>
            </div>
        </Card>
    </div>
</template>
<script>
import Title from '../components/title'
import basicInfo from './basicInfo'

export default {
  components:{
    Title,
    basicInfo
  },
  data: () => ({
  }),
  created () {
    this.$api.post('/member/proxy/queryInfoDetail', {
      login_account: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount,
      flag: 1
    }).then(response => {
      console.log('response123', response)
      if (response.code === 200 && response.data) {
        this.$refs.basicInfo.basicInfoForm = response.data
      }
    }).catch(error => {
        this.$Message.error('服务器异常！')
    })
  },
  methods:{
    handleClickNext () {
        this.$router.push({path:'/auth/ruralAuth/step2'})
    }
  }
}
</script>
<style lang="scss">
</style>
