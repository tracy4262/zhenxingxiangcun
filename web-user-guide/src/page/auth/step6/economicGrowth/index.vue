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
    <component slot="content" v-bind:is="mode" :id="modeId" :appId="appId" :yearId="yearId" @on-save="onSave" :ref="mode"></component>
  </vui-wrapper>
</template>

<script>
import vuiWrapper from '../components/wrapper'
import vuiTab from '../components/tab'
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
    yearId: {
      type: String
    },
    appId: {
      type: String
    }
  },
  data() {
    return {
      activeInidex: 0,
      tabTitle: '经济社会发展',
      tabData: [{
        title: '产业信息',
        id: '1',
        name: 'property',
        checked: true,
        status: false
      },{
        title: '种养信息',
        id: '1',
        name: 'breeding',
        checked: false,
        status: false
      },{
        title: '农产品信息',
        id: '1',
        name: 'agriculture',
        checked: false,
        status: false
      },{
        title: '工业产品信息',
        id: '1',
        name: 'industry',
        checked: false,
        status: false
      },{
        title: '服务产品信息',
        id: '1',
        name: 'service',
        checked: false,
        status: false
      }],
      mode: 'industry',
      modeId: '',
      tabId: '123'
    }
  },
  created() {
    this.account = this.$user.loginAccount
    this.handleInit()
  },
  methods: {
    handleInit () {
      this.$api.post('/member-reversion/perfect/initData',{
        account: this.$user.loginAccount,
        templateId: this.$template.id,
        yearId: this.yearId,
        appId: this.appId
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
          console.log('调用点击事件')
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
      })
    },
    // 保存切换状态需要判断表单是否完成
    onSave (){
      this.tabData.forEach(item => {
        if(item.name === this.mode) item.status = true
      })
    }
  },
  mounted() {
    this.modeId = this.tabData[0].id
  },
}
</script>

<style lang="scss">

</style>
