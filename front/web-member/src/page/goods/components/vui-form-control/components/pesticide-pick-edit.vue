<template>
  <div>
    <Row type="flex" algin="middle" justify="center" class="pesticide-search">
      <Col span="10">
        <Input v-model.trim="keyword" placeholder="指标名称搜索" @on-enter="handleSearch" @on-change="handleSearch"></Input>
      </Col>
      <Col span="4">
        <Button type="primary" icon="ios-search" @click="handleSearch"></Button>
      </Col>
    </Row>
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
    <Row class="mt20 mb20">
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
      keyword: '',
      list: []
    }
  },
  created () {
    this.handleInit('')
  },
  methods: {
    // 初始化查询 
    handleInit (key) {
      this.$api.post('/portal/shopCommdoity/findResidueStandard',{name: key}).then(response => {
        if (response.code == 200) {
          if (response.data) {
            this.list = response.data
            // this.list.forEach(item => {
            //   this.data.list.forEach(child => {
            //     if (item.name === child.name) {
            //       item.checked = true
            //     }
            //   })
            // })
          } else {
            this.list = []
          }          
        }
      })
    },
    // 查询 
    handleSearch () {
      this.handleInit(this.keyword)
    },
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
