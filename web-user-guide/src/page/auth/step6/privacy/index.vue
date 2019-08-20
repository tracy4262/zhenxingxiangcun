<template>
  <vui-wrapper>
    <vui-tab
    slot="tab"
    :title="tabTitle"
    :data="tabData"
    @on-click="onTabClick"
    @on-edit-name="onEditTabName"
    @handleEdit="handleEdit"
    :appId="appId"
    class="mr15"
    style="width:200px;"></vui-tab>
    <component slot="content" v-bind:is="mode" :id="modeId" :appId="appId" :yearId="yearId" @on-save="onSave" :ref="mode"></component>
  </vui-wrapper>
</template>

<script>
import vuiWrapper from '../components/wrapper'
import vuiTab from '../components/tab'
import privacy from './privacy'
export default {
  components: {
    vuiWrapper,
    vuiTab,
    privacy
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
      tabTitle: '隐私信息',
      tabData: [
      //   {
      //   title: '隐私信息',
      //   name: 'privacy',
      //   id: '1',
      //   checked: true,
      //   status: false
      // }
      ],
      mode: 'privacy',
      modeId: '',
      account: '',
      activeInidex: 0
    }
  },
  created() {
    this.account = this.$user.loginAccount
    console.log('隐私信息')
    this.handleInit()
  },
  methods: {
    handleInit () {
      this.$api.post('/member-reversion/perfect/initData',{
        account: this.$user.loginAccount,
        templateId: this.$template.id,
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
    // 编辑tab名称
    onEditTabName (name) {
      this.tabTitle = name
    },
    // 保存切换状态需要判断表单是否完成
    onSave (){
      this.tabData.forEach(item => {
        if(item.name === this.mode) item.status = true
        
      })
    }
  }
}
</script>

<style lang="scss">

</style>
