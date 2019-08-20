<template>
  <div>
    <Modal
      width="50%"
      :mask-closable="false"
      v-model="showAddPanel"
      title="添加自定义控件"
      @on-visible-change="handleModalChange">
        <Title title="组件库" desc="注： 点击选择组件，并设置组件参数"></Title>
        <div >
          <Button
            :type="item.checked ? 'primary' : 'default'"
            class="mt5 mr5"
            :key="item.name"
            v-for="item in eleData"
            @click="handleAddBtn(item)">
            {{item.name}}
          </Button>
        </div>

        <div class="mt30" style="border-top: 1px dotted #eee"></div>
        <Title :title="`编辑 - ${title}组件`"></Title>       

        <ele-pesticide-pick-edit
        ref="pesticidePick"
        :data="data"
        v-if="data.type === 'pesticidePick'"
        @on-save="handleSave"
        @on-cancel="handleCancel"></ele-pesticide-pick-edit>

        <ele-pollute-pick-edit
        :data="data"
        v-if="data.type === 'pollutePick'"
        @on-save="handleSave"
        @on-cancel="handleCancel"></ele-pollute-pick-edit>

         <div slot="footer"></div>
    </Modal>
  </div>
</template>
<script>
import {eleAttr} from './components/mixins'
import Title from './components/title'
import elePesticidePickEdit from './components/pesticide-pick-edit'
import elePollutePickEdit from './components/pollute-pick-edit'
export default {
  mixins: [eleAttr],
  components: {
    Title,
    elePesticidePickEdit,
    elePollutePickEdit
  },
  data: () => ({
    showAddPanel: false,
    showEditPanel: true,
    type: 'pesticidePick',
    eleData: [{
      name: '农药残留指标', 
      type: 'pesticidePick', 
      checked: false
    }, {
      name: '污染物残留指标', 
      type: 'pollutePick', 
      checked: false
    }],
    data: {},
    title: '农药残留指标'
  }),
  created () {
    this.init()
  },
  methods: {
    // 添加组件
    handleAddBtn (item) {
      let type = item.type
      this.type = type
      this.showEditPanel = true
      this.eleData.forEach(child => { child.checked = false })
      item.checked = true
      if (type === 'pesticidePick') {
        this.title = '农药残留指标'
        this.init()
        this.data = this.pesticidePick
      } else if (type === 'pollutePick') {
        this.title = '污染物残留指标'
        this.init()
        this.data = this.pollutePick
      }
      this.$nextTick(() => {
        if (this.type == 'pesticidePick') {
          this.$refs['pesticidePick'].handleInit('')
          this.data.list = []
        }
      })
    },
    // 保存组件
    handleSave () {
      let type = this.type
      if (type === 'pesticidePick') {
        this.$emit('on-save', this.pesticidePick)
      } else if (type === 'pollutePick') {
        this.$emit('on-save', this.pollutePick)
      }
      this.showAddPanel = false
    },
    // 取消编辑
    handleCancel () {
      this.showAddPanel = false
      this.type = 'pesticidePick'
    },
    // 初始化组件状态
    handleModalChange (flag) {
      this.init('security')
      this.eleData.forEach(child => { child.checked = false })
      this.eleData[0].checked = true
      this.type = this.eleData[0].type
      this.title = this.eleData[0].name
      this.$nextTick(() => {
        if (this.type == 'pesticidePick') {
          this.$refs['pesticidePick'].handleInit('')
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
