<template>
  <vui-wrapper class="new-auth">
    <vui-tab
    :id="tabId"
    slot="tab"
    :title="tabTitle"
    :data="tabData"
    @on-click="onTabClick"
    @handleEdit="handleEdit"
    :appId="appId"
    class="mr15"
    style="width:200px"></vui-tab>
    <component slot="content" v-bind:is="mode" :id="modeId" :ref="mode"></component>
  </vui-wrapper>
</template>

<script>
import vuiWrapper from '../wrapper'
import vuiTab from '../tab'
import property from './property'
import industry from './industry'
import breeding from './breeding'
import agriculture from './agriculture'
import service from './service'
export default {
  components: {
    vuiWrapper,
    vuiTab,
    property,
    industry,
    breeding,
    agriculture,
    service
  },
  props: {
    appId: {
      type: String
    }
  },
  data() {
    return {
      activeInidex: 0,
      tabTitle: '经济社会发展',
      tabData: [],
      mode: 'industry',
      modeId: '',
      tabId: '123',
      baseId: ''
    }
  },
  created() {
    this.baseId = this.$route.query.id
    this.account = this.$user.loginAccount
    this.handleInit()
  },
  methods: {
    handleInit () {
      this.$api.post('/member-reversion/productionBase/initData',{
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
          this.onTabClick(this.tabData[this.activeInidex].name, this.tabData[this.activeInidex], this.activeInidex)
          this.tabTitle = response.data.moduleName
        }
      })
    },
    // 修改model
    handleEdit () {
      this.$emit('handleRefresh')
      this.handleInit()
    },
     // 选中的标签
    onTabClick (name, data, index) {
      this.mode = data.name
      this.modeId = data.id
      this.activeInidex = index
      this.$nextTick(e => {
        this.$refs[this.mode].handleInit()
        this.$refs[this.mode].initTitle()
      })
    }
  }
}
</script>

<style lang="scss">

</style>
