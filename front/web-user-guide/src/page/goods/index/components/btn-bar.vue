<template>
  <div class="filter-bar">
    <template v-for="(item, index) in btnData">
      <Button
      :key="index"
      :type="item.checked ? 'primary' : 'text'"
      size="small"
      @click="handleClick(item, index)"
      v-if="item.name === '默认'">
        {{item.name}}
      </Button>
      <Button
      :key="index"
      :type="item.checked ? 'primary' : 'text'"
      size="small"
      @click="handleClick(item, index)"
      v-else>
        <Row type="flex" align="middle">
          <Col span="18">{{item.name}}</Col>
          <Col span="6">
            <template v-if="item.asc === 0">
              <!-- 0 从低到高 -->
              <Icon type="md-arrow-dropup" size="16"/>
              <!-- <Icon type="arrow-up-b"></Icon> -->
            </template>
            <template v-if="item.asc === 1">
              <!-- 1 从高到底 -->
              <Icon type="md-arrow-dropdown" size="16"/>
              <!-- <Icon type="ios-arrow-down"></Icon> -->
            </template>
          </Col>
        </Row>
      </Button>
    </template>
  </div>
</template>

<script>
export default {
  components: {

  },
  data () {
    return {
      btnData: [{
        name: '默认',
        checked: true,
        dataName: 'default'
      }, {
        // 0 价格正序 从低到高 1价格倒序 从高到底
        name: '价格',
        checked: false,
        asc: 1,
        dataName: 'timePriceFlag'
      }, {
        // 0 好评率正序 从低到高 1好评率倒序 从高到底
        name: '好评率',
        checked: false,
        asc: 1,
        dataName: 'gradeFlag'
      }]
    }
  },
  methods: {
    // 重置
    handleReset () {
      this.btnData = [{
        name: '默认',
        checked: true,
        dataName: 'default'
      }, {
        name: '价格',
        checked: false,
        asc: 1,
        dataName: 'timePriceFlag'
      }, {
        name: '好评率',
        checked: false,
        asc: 1,
        dataName: 'gradeFlag'
      }]
    },
    // 切换筛选状态
    handleClick (item, index) {
      this.btnData.map((item, i) => {
        item.checked = false
        if (index !== i) {
          item.asc = 0
        }
      })
      if (item.asc < 1) {
        item.asc++
      } else {
        item.asc = 0
      }
      item.checked = true
      console.log(item)
      this.$emit('on-search', item ,item.dataName)
    }
  }
}
</script>

<style lang="scss" scoped>
.filter-bar{
  margin-top: 20px;
  padding: 10px;
  background: #F9F9F9;
  .ivu-btn{
    margin-right: 10px;
    min-width: 50px;
    padding: 2px 5px;
  }
  .ivu-icon{
    display: block;
    line-height: .5;
    padding-left: 3px;
  }
}
</style>
