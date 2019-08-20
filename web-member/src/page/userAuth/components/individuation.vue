<template>
  <Card>
    <p class="t-orange pt20 pb20">个性化将帮助您设置常用的关键词这些关键词有助于过滤出最感兴趣的信息，还能协助您建立交易关系。</p>
    <Title title="设置关注"/>
    <vui-follow ref="follow"></vui-follow>
    <Title title="设置收藏"/>
    <collection></collection>
    <Title title="好友分组"></Title>
    <buddy-group ref="buddyGroup"></buddy-group>
    <Title title="设置账户"></Title>
    <bank-account-setting ref="bankSetting"></bank-account-setting>
    <div class="tc pd20">
      <Button type="primary" @click="handleClickBack">上一步</Button>
      <Button type="primary" @click="handleClickNext">下一步</Button>
      <Button type="text" @click="handleClickOver">跳过</Button>
    </div>
  </Card>
</template>
<script>
import vuiFollow from './vui-follow'
import buddyGroup from './buddy-group'
import bankAccountSetting from './bank-account-setting'
import collection from './collection'
import Title from './title'
export default {
  components: {
    vuiFollow,
    buddyGroup,
    bankAccountSetting,
    collection,
    Title
  },
  data () {
    return {
      loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
    }
  },
  created () {
    this.initData()
  },
  methods:{
    initData () {
      this.$api.post('/member/indivi/findIndividInfo',{
        account: this.loginUser.loginAccount,
        follow_type: 'knowledge'
      }).then(response => {
        console.log('response999', response)
        if (response.code === 200) {
          // 回显关注数据
          if (response.data.FollowData[0].follow[0].length === 0 &&
              response.data.FollowData[0].follow[1].length === 0 &&
              response.data.FollowData[0].follow[2].length === 0 &&
              response.data.FollowData[0].releva[0].length === 0 &&
              response.data.FollowData[0].releva[1].length === 0 &&
              response.data.FollowData[0].releva[2].length === 0) {
            this.$refs.follow.listData = []
          } else {
            this.$refs.follow.listData = response.data.FollowData
          }
          // 回显好友分组数据
          this.$refs.buddyGroup.buddyGroupList = response.data.FriendGroupData === '' ? [] : response.data.FriendGroupData
          // 回显银行账户数据
          this.$refs.bankSetting.bankInfo.name = response.data.BankSettingData.name
          this.$refs.bankSetting.bankInfo.id = response.data.BankSettingData.id
          this.$refs.bankSetting.bankInfo.bankCard = response.data.BankSettingData.bankCard
          this.$refs.bankSetting.bankInfo.mobile = response.data.BankSettingData.mobile
        }
      }).catch(error => {
        console.log(error)
      })
    },
    handleClickBack () {
      this.$emit('on-back')
    },
    handleClickNext () {
      let flag = true
      let obj = this.$refs.bankSetting.bankInfo
      // 调用子组件的表单验证 账户设置这整一块可以不填 只要有填了一个值就触发表单验证
      for (let key in obj) {
        if (obj[key]) {
          flag = this.$refs.bankSetting.handleSubmit()
          break
        }
      }
      if (flag) {
        let data = {
          LoginAccount: this.loginUser.loginAccount,
          FollowData: this.$refs.follow.listData,
          FriendGroupData: this.$refs.buddyGroup.buddyGroupList,
          BankSettingData: this.$refs.bankSetting.bankInfo
        }
        console.log('data', data)
        this.$api.post('/member/indivi/saveIndividInfo', data).then(response => {
          console.log('res', response)
          if (response.code === 200) {
            this.$emit('on-next')
            this.$Message.success('保存成功！')
          } else if (response.code === 400) {
            this.$Message.error('验证码错误！')
          } else if (response.code === 500) {
            this.$Message.error('保存失败！')
          }
        }).catch(error => {
          this.$Message.error('服务器异常！')
        })
      }
    },
    handleClickOver () {
      this.$emit('on-over')
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
