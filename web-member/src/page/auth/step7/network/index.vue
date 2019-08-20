<template>
  <vui-wrapper>
    <vui-tab
    slot="tab"
    :title="tabTitle"
    :data="tabData"
    @on-click="onTabClick"
    @on-edit-name="onEditTabName"
    :appId="appId"
    @handleEdit="handleEdit"
    class="mr15"
    style="width:200px;"></vui-tab>
    <component slot="content" v-bind:is="mode" :id="modeId" :appId="appId" :yearId="yearId" @on-save="onSave" :ref="mode" @left-refresh="leftRefresh"></component>
  </vui-wrapper>
</template>

<script>
import vuiWrapper from '../components/wrapper'
import vuiTab from '../components/tab'
import network from './network-info'
import modifyPassWord from './modifyPassWord'
export default {
  components: {
    vuiWrapper,
    vuiTab,
    network,
    modifyPassWord
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
      tabTitle: '网络信息',
      tabData: [],
      mode: 'network',
      modeId: '',
      activeInidex: 0,
      templateId: ''
    }
  },
  created() {
    this.templateId = this.$route.query.templateId
    this.account = this.$user.loginAccount
    this.handleInit ()
  },
  methods: {
     handleInit () {
        this.$api.post('/member-reversion/user/perfect/initData',{
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
        })
    },
    // 选中的标签
    onTabClick (name, data, index) {
      this.mode = name
      this.modeId = data.id
      this.activeInidex = index
      this.$nextTick(e => {
        this.$refs[this.mode].handleInit()
        this.$refs[this.mode].initTitle()
      })
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
    // 修改model
    handleEdit () {
      this.$emit('handleRefresh')
      // 重新请求应用名称进行显示
      this.$api.post('/member-reversion/user/perfect/initData', {
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
    },
    leftRefresh () {
      this.handleInit()
    }
  }
}
</script>

<style lang="scss">

</style>
