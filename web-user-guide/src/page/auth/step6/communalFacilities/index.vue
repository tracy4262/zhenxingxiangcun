<template>
  <vui-wrapper>
    <vui-tab
    :id="tabId"
    slot="tab"
    :title="tabTitle"
    :data="tabData"
    @on-click="onTabClick"
    @on-edit-name="onEditTabName"
    @handleEdit="handleEdit"
    :appId="appId"
    class="mr15"
    style="width:200px;"></vui-tab>
    <component slot="content" v-bind:is="mode" :modeId="modeId" :yearId="yearId" @on-save="onSave"></component>
  </vui-wrapper>
</template>

<script>
import vuiWrapper from '../components/wrapper'
import vuiTab from '../components/tab'
import trafficAndUser from './trafficAndUser'
import communicationFacilitiesAndUser from './communicationFacilitiesAndUser'
import runningWaterAndUser from './runningWaterAndUser'
import waterConservancyFacilities from './waterConservancyFacilities'
import garbageDisposalFacilities from './garbageDisposalFacilities'
import securityFacilities from './securityFacilities'
import educationalFacilities from './educationalFacilities'
import culturalFacility from './culturalFacility'
import sportsFacilities from './sportsFacilities'
import pensionFacilities from './pensionFacilities'
import otherFacilities from './otherFacilities'
export default {
  components: {
    vuiWrapper,
    vuiTab,
    trafficAndUser,
    communicationFacilitiesAndUser,
    runningWaterAndUser,
    waterConservancyFacilities,
    garbageDisposalFacilities,
    securityFacilities,
    educationalFacilities,
    culturalFacility,
    sportsFacilities,
    pensionFacilities,
    otherFacilities
  },
  props: {
    yearId: {
      type: String
    },
    appId: {
      type: String
    }
  },
  data() {
    return {
      tabTitle: '公共设施',
      tabData: [],
      mode: 'trafficAndUser',
      tabId: '',
      modeId: ''
    }
  },
  created () {
    // 初始化获取左侧模块信息
    this.$api.post('/member-reversion/perfect/initData', {
        account: this.$user.loginAccount,
        templateId: this.$template.id,
        yearId: this.yearId,
        appId: this.appId
    }).then(response => {
        if (response.code === 200) {
          response.data.subModule.forEach((element, index) => {
            this.tabData.push({
              title: element.name,
              name: element.url,
              id: element.dictId,
              checked: index === 0 ? true : false,
              status: element.isComplete
            })
          })
          this.modeId = this.tabData[0].id
          this.tabTitle = response.data.moduleName
          console.log('tandata', this.tabData)
        }
    }).catch(error => {
        this.$Message.error('服务器异常！')
    })
  },
  methods: {
    // 选中的标签
    onTabClick (name, data, index) {
      console.log('name', name)
      this.mode = name
      this.modeId = data.id
      console.log('dict id', this.modeId)
    },
    // 编辑tab名称
    onEditTabName (name) {
      this.tabTitle = name
    },
    // 保存切换状态需要判断表单是否完成
    onSave (){
      this.tabData.forEach(item => {
        if(item.name === this.mode) item.status = true
      })
    },
    // 修改左侧应用名称
    handleEdit (name) {
      // 回调父组件初始化接口刷新应用名称
      this.$emit('handleRefresh')
      // 重新请求应用名称进行显示
      this.$api.post('/member-reversion/perfect/initData', {
          account: this.$user.loginAccount,
          templateId: this.$template.id,
          yearId: this.yearId,
          appId: this.appId
      }).then(response => {
          if (response.code === 200) {
            this.tabTitle = response.data.moduleName
          }
      }).catch(error => {
          this.$Message.error('服务器异常！')
      })
    }
  }
}
</script>

<style lang="scss">

</style>
