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
    <component slot="content" v-bind:is="mode" :modeId="modeId" ></component>
  </vui-wrapper>
</template>

<script>
import vuiWrapper from '../wrapper'
import vuiTab from '../tab'
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
import IOTDevice from './IOTDevice'
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
    otherFacilities,
    IOTDevice
  },
  props: {
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
      modeId: '',
      baseId: ''
    }
  },
  created () {
    this.baseId = this.$route.query.id
    // 初始化获取左侧模块信息
    this.$api.post('/member-reversion/productionBase/initData', {
        account: this.$user.loginAccount,
        appId: this.appId,
        baseId: this.baseId
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
        }
    }).catch(error => {
        this.$Message.error('服务器异常！')
    })
  },
  methods: {
    // 选中的标签
    onTabClick (name, data, index) {
      this.mode = name
      this.modeId = data.id
      console.log('dict id', this.modeId)
    },
    // 编辑tab名称
    onEditTabName (name) {
      this.tabTitle = name
    },
    // 修改左侧应用名称
    handleEdit (name) {
      // 回调父组件初始化接口刷新应用名称
      this.$emit('handleRefresh')
      // 重新请求应用名称进行显示
      this.$api.post('/member-reversion/productionBase/initData', {
          account: this.$user.loginAccount,
          yearId: this.yearId,
          appId: this.appId,
          templateId: this.templateId
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
