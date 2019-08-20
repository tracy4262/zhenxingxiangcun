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
        <ele-input
        :data="data"
        v-if="data.type === 'text'"
        @on-save="handleSave"
        @on-cancel="handleCancel"
        ></ele-input>

        <ele-textarea
        :data="data"
        v-if="data.type === 'textarea'"
        @on-save="handleSave"
        @on-cancel="handleCancel"
        ></ele-textarea>

        <ele-select
        :data="data"
        v-if="data.type === 'select'"
        @on-save="handleSave"
        @on-cancel="handleCancel"
        ></ele-select>

        <ele-radio
        :data="data"
        v-if="data.type === 'radio'"
        @on-save="handleSave"
        @on-cancel="handleCancel"
        ></ele-radio>

        <ele-checkbox
        :data="data"
        v-if="data.type === 'checkbox'"
        @on-save="handleSave"
        @on-cancel="handleCancel"
        ></ele-checkbox>

        <ele-switch
        :data="data"
        v-if="data.type === 'switch'"
        @on-save="handleSave"
        @on-cancel="handleCancel"
        ></ele-switch>

        <ele-pesticide-pick-edit
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
import eleInput from './components/input'
import eleTextarea from './components/textarea'
import eleSelect from './components/select'
import eleRadio from './components/radio'
import eleCheckbox from './components/checkbox'
import eleSwitch from './components/switch'
import elePesticidePickEdit from './components/pesticide-pick-edit'
import elePollutePickEdit from './components/pollute-pick-edit'
export default {
  mixins: [eleAttr],
  components: {
    Title,
    eleInput,
    eleTextarea,
    eleSelect,
    eleRadio,
    eleCheckbox,
    eleSwitch,
    elePesticidePickEdit,
    elePollutePickEdit
  },
  data: () => ({
    showAddPanel: false,
    showEditPanel: true,
    type: 'text',
    eleData: [{
      name: '文本框组件', 
      type: 'text', 
      checked: true
    }, {
      name: '下拉组件', 
      type: 'select', 
      checked: false
    }, {
      name: '单选框组件', 
      type: 'radio', 
      checked: false
    },{
      name: '复选框组件', 
      type: 'checkbox', 
      checked: false
    }],
    // eleData: [{
    //   name: '文本框组件', 
    //   type: 'text', 
    //   checked: true
    // }, {
    //   name: '文本域组件', 
    //   type: 'textarea', 
    //   checked: false
    // }, {
    //   name: '下拉组件', 
    //   type: 'select', 
    //   checked: false
    // }, {
    //   name: '单选框组件', 
    //   type: 'radio', 
    //   checked: false
    // }, {
    //   name: '复选框组件', 
    //   type: 'checkbox', 
    //   checked: false
    // }, {
    //   name: '开关组件', 
    //   type: 'switch', 
    //   checked: false
    // }, {
    //   name: '农药残留指标', 
    //   type: 'pesticidePick', 
    //   checked: false
    // }, {
    //   name: '污染物残留指标', 
    //   type: 'pollutePick', 
    //   checked: false
    // }],
    data: {},
    title: '文本框'
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
      if (type === 'text') {
        this.title = '文本框'
        this.data = this.input
      } else if (type === 'textarea') {
        this.title = '文本域'
        this.data = this.textarea
      } else if (type === 'select') {
        this.title = '下拉菜单'
        this.data = this.select
      } else if (type === 'radio') {
        this.title = '单选按钮'
        this.data = this.radio
      } else if (type === 'checkbox') {
        this.title = '多选按钮'
        this.data = this.checkbox
      } else if (type === 'switch') {
        this.title = '开关'
        this.data = this.switch
      } else if (type === 'pesticidePick') {
        this.title = '农药残留指标'
        this.data = this.pesticidePick
      } else if (type === 'pollutePick') {
        this.title = '污染物残留指标'
        this.data = this.pollutePick
      }
    },
    // 保存组件
    handleSave () {
      let type = this.type
      if (type === 'text') {
        let input = Object.assign({maxlength: 12}, this.input)
        this.$emit('on-save', input)
      } else if (type === 'textarea') {
        let textarea = Object.assign({maxlength: 500}, this.textarea)
        this.$emit('on-save', textarea)
      } else if (type === 'select') {
        this.$emit('on-save', this.select)
      } else if (type === 'radio') {
        this.$emit('on-save', this.radio)
      } else if (type === 'checkbox') {
        this.$emit('on-save', this.checkbox)
      } else if (type === 'switch') {
        this.$emit('on-save', this.switch)
      } else if (type === 'pesticidePick') {
        this.$emit('on-save', this.pesticidePick)
      } else if (type === 'pollutePick') {
        this.$emit('on-save', this.pollutePick)
      }
      this.showAddPanel = false
    },
    // 取消编辑
    handleCancel () {
      this.showAddPanel = false
      this.type = 'text'
    },
    // 初始化组件状态
    handleModalChange (flag) {
      this.init()
      this.eleData.forEach(child => { child.checked = false })
      this.eleData[0].checked = true
      this.type = this.eleData[0].type
      this.title = this.eleData[0].name
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
