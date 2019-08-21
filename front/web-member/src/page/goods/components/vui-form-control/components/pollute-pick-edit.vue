<template>
  <div>
    <Row>
      <Col span="22"><p class="b">指标名</p></Col>
      <Col span="2"><p class="b">参考值</p></Col>
    </Row>
    
    <Row class="scroll-y mt20" style="max-height: 200px">
      <template v-for="(item, index) in list">
        <Col span="22">
          <Checkbox :disabled="item.disabled" v-model="item.checked" @on-change="handleChange($event, item)">{{item.name}}</Checkbox>
        </Col>
        <Col span="2">
          {{item.consult}}
        </Col>
      </template>
    </Row>
    <Row type="flex" class="mt20 mb20">
      <Tag v-for="(item, index) in data.list" :key="index" v-if="item.checked" closable @on-close="handleClose(item)">{{item.name}}</Tag>
    </Row>
    <div class="tc">
      <Button type="primary" class="mr20" @click="handleSave">保存</Button>
      <Button type="default" @click="handleCancel">取消</Button>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    data: {
      type: Object
    }
  },
  data () {
    return {
      list: [
        {name:'铅（以Pb计）', consult:'0.1', value:'', checked: false, disabled: false},
        {name:'汞（以Hg计）',consult:'0.01', value:'', checked: false, disabled: false},
        {name:'氟（以F计）', consult:'0.5', value:'', checked: false, disabled: false},
        {name:'砷（以As计）', consult:'0.5', value:'', checked: false, disabled: false},
        {name:'铜（以Cu计）', consult:'10', value:'', checked: false, disabled: false},
        {name:'氟（以F计）', consult:'0.5', value:'', checked: false, disabled: false},
        {name:'镉（以Cd计）', consult:'0.03', value:'', checked: false, disabled: false}
      ]
    }
  },
  created () {
  },
  methods: {
    // 切换选中
    handleChange ($event, item) {
      console.log(this.data.list)
      if (item.checked) {
        this.data.list.push(item)
      } else {
        this.filterFn(this.data.list, item)
      }
    },
    // 关闭
    handleClose (item) {
      this.list.forEach(child => {
        if (child.name === item.name) {
          child.checked = false
        }
      })
      this.filterFn(this.data.list, item)
    },
    filterFn (obj, item) {
      obj.forEach((child, index) => {
        if (child.name === item.name) {
          obj.splice(index, 1)
        }
      })
    },
    handleSave () {
      if (this.data.list.length) {
        this.$emit('on-save')
      } else {
        this.$Message.warning('请选择！')
      }
    },
    handleCancel () {
      this.$emit('on-cancel')
    }
  }
}
</script>
<style lang="scss">
  .pesticide-search{
    margin-bottom: 15px;
    .ivu-btn,
    .ivu-input{
      border-radius: 0
    }
  }
</style>
