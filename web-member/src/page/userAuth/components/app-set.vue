<template>
  <Card>
    <p class="t-orange pt20 pb20">您还没有添加应用，应用中心作为平台的一种功能扩展将为您提供众多管理工具和模块，您可以根据自己的需求，选择添加相应的模块。</p>
    <Title title="基本应用"></Title>
    <vui-min-card ref="basicApp" :data="basicAppData" class="mt15" @on-click="handleGetBasicAppChoosed"></vui-min-card>
    <Title title="高级应用" class="mt20"></Title>
    <vui-min-card ref="advancedApp" :data="advancedAppData" class="mt15" @on-click="handleGetAdvancedAppChoosed"></vui-min-card>
    <Title title="第三方应用" class="mt20"></Title>
    <vui-min-card ref="thirdApp" :data="thirdAppData" class="mt15" @on-click="handleGetThirdAppChoosed"></vui-min-card>
    <div class="tc pd20">
      <Button type="primary" @click="handleClickBack">上一步</Button>
      <Button type="primary" @click="handleClickNext">下一步</Button>
      <Button type="text" @click="handleClickOver">跳过</Button>
    </div>
  </Card>
</template>
<script>
import Title from './title'
import VuiMinCard from './vui-min-card'
export default {
  components: {
    Title,
    VuiMinCard
  },
  data: () => ({
    advancedAppData: [],
    basicAppData: [],
    thirdAppData: []
  }),
  created () {
    this.$api.post('/member/appSettings/findAppSettingsInfo', {
      account: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
    }).then(response => {
      console.log('response123', response)
      if (response.code === 200) {
        this.advancedAppData = response.data.advancedAppData
        this.basicAppData = response.data.basicAppData
        this.thirdAppData = response.data.thirdAppData
      }
    }).catch(error => {
        this.$Message.error('服务器异常！')
    })
  },
  methods: {
    handleGetAdvancedAppChoosed (checked) {
      this.advancedAppData = checked
    },
    handleGetBasicAppChoosed (checked) {
      this.basicAppData = checked
    },
    handleGetThirdAppChoosed (checked) {
      this.thirdAppData = checked
    },
    // 上一步
    handleClickBack () {
      this.$emit('on-back')
    },
    // 下一步
    handleClickNext () {
      this.$api.post('/member/appSettings/saveOrUpdateAppSettingsInfo', {
        account: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount,
        data: {
          advancedAppData: this.advancedAppData,
          basicAppData: this.basicAppData,
          thirdAppData: this.thirdAppData
        }
      }).then(response => {
        if (response.code === 200) {
          this.$emit('on-next')
        } else {
          this.$Message.error('服务器异常！')
        }
      }).catch(error => {
        this.$Message.error('服务器异常！')
      })
    },
    // 跳过
    handleClickOver () {
      this.$emit('on-over')
    }
  }
}
</script>
<style lang="scss">
</style>
