<template>
  <vui-wrapper>
    <vui-tab
    slot="tab"
    :title="tabTitle"
    :data="tabData"
    @on-click="onTabClick"
    @handleEdit="handleEdit"
    :appId="appId"
    class="mr15"
    style="width:200px;"></vui-tab>
    <component slot="content" v-bind:is="mode" :id="modeId" :appId="appId" @on-show-land="handleShowLandInfo" @on-map="handleShowMap" :ref="mode"></component>
  </vui-wrapper>
</template>

<script>
import vuiWrapper from '../wrapper'
import vuiTab from '../tab'
import landInfo from './landInfo'
import landContent from './landContent'
import soilQuality from './soilQuality'
import waterQuality from './waterQuality'
import status from './status'
import search from './search'
import { setTimeout } from 'timers';
export default {
  components: {
    vuiWrapper,
    vuiTab,
    landInfo,
    landContent,
    soilQuality,
    waterQuality,
    status,
    search
  },
  props: {
    appId: {
      type: String
    }
  },
  data() {
    return {
      tabTitle: '地块信息',
      tabData: [],
      mode: 'landInfo',
      tabId: '',
      modeId: '',
      activeInidex: 0,
      baseId: ''
    }
  },
  created () {
    this.baseId = this.$route.query.id
    this.handleInit()
  },
  methods: {
    // 初始化数据
    handleInit () {
      // 初始化获取左侧模块信息
      this.$api.post('/member-reversion/productionBase/initData', {
          account: this.$user.loginAccount,
          appId: this.appId,
          baseId: this.baseId
      }).then(response => {
          if (response.code === 200) {
            this.tabData = []
            response.data.subModule.forEach((element, index) => {
              this.tabData.push({
                title: element.name,
                name: element.url,
                id: element.dictId,
                checked: index === this.activeInidex ? true : false,
                status: element.isComplete
              })
            })
            if (this.tabData[0].status) {
              this.tabData[1].status = this.tabData[0].status
              this.tabData[2].status = this.tabData[0].status
              this.tabData[3].status = this.tabData[0].status
              this.tabData[5].status = this.tabData[0].status
            }
            this.onTabClick(this.tabData[this.activeInidex].name, this.tabData[this.activeInidex], this.activeInidex, true)
            this.tabTitle = response.data.moduleName
          }
      }).catch(error => {
          this.$Message.error('服务器异常！')
      })
    },
    // 地块信息页面，点击查看地图
    handleShowMap (item) {
      console.log('itemitem', item)
      this.activeInidex = 5
      this.onTabClick(this.tabData[this.activeInidex].name, this.tabData[this.activeInidex], this.activeInidex, item)
    },
    // 地块地图查询中点击查看详情
    handleShowLandInfo () {
      this.activeInidex = 0
      this.onTabClick(this.tabData[this.activeInidex].name, this.tabData[this.activeInidex], this.activeInidex)
    },
    // 修改model
    handleEdit () {
      this.$emit('handleRefresh')
      this.handleInit()
    },
    // 选中的标签
    onTabClick (name, data, index, item) {
      console.log('data', data)
      this.mode = data.name
      this.modeId = data.id
      this.activeInidex = index
      this.tabData.forEach((item, tabIndex) => {
        item.checked = index === tabIndex ? true : false
      })
      console.log('item', item)
      this.$nextTick(e => {
        console.log('4refs', this.$refs)
        if (item) { // 用于地块信息页面判断是否直接显示 地块信息， 地块地图信息页面 是否直接定位当前点
          this.$refs[this.mode].init(item)
        } else {
          this.$refs[this.mode].init()
        }
        this.$refs[this.mode].initTitle()
      })
    }
  }
}
</script>

<style lang="scss">

</style>
