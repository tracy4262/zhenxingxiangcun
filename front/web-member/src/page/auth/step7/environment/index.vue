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
    <component slot="content" v-bind:is="mode" :modeId="modeId" :yearId="yearId" @on-save="onSave" @left-refresh="leftRefresh"></component>
  </vui-wrapper>
</template>

<script>
import vuiWrapper from '../components/wrapper'
import vuiTab from '../components/tab'
import air from './air'
import water from './water'
import ecology from './ecology' 
export default {
  components: {
    vuiWrapper,
    vuiTab,
    air,
    water,
    ecology
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
      tabTitle: '环境状况',
      tabData: [],
      mode: 'air',
      tabId: '',
      modeId: '',
      activeInidex: 0,
      templateId: ''
    }
  },
  created () {
    this.templateId = this.$route.query.templateId
    // 初始化获取左侧模块信息
    this.handleInit()
  },
  methods: {
    handleInit () {
       this.$api.post('/member-reversion/user/perfect/initData', {
          account: this.$user.loginAccount,
          yearId: this.yearId,
          appId: this.appId,
          templateId: this.templateId
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
      }).catch(error => {
          this.$Message.error('服务器异常！')
      })
    },
    // 选中的标签
    onTabClick (name, data, index) {
      this.mode = data.name
      this.modeId = data.id
      this.activeInidex = index
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
    handleEdit () {
      this.$emit('handleRefresh')
      this.handleInit()
    },
    leftRefresh () {
      this.handleInit()
    }
  }
}
</script>

<style lang="scss">

</style>
