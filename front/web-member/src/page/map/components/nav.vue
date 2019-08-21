<template>
   <div>
     <Card shadow class="route-panel">
      <Row type="flex" align="middle">
        <Col span="24" v-for="item in btns" :key="item.icon">
          <div class="item" :class="{on: item.checked}" @click="handleBtnClick(item)"><Icon :type="item.icon" size="20"></Icon> {{item.name}}</div>
        </Col>
      </Row>
      <Button type="primary" size="large" @click="handleBack" class="search-btn" icon="md-close">
      </Button>
      <slot></slot>
    </Card>
    <!-- <Card shadow class="mt10" :padding="10">
      <a ><Icon type="chevron-left"></Icon> 返回 搜索列表</a>
    </Card> -->
    <!-- <Card shadow class="mt10" v-if="data">
      <p><span class="t-grey">驾车前往</span> <span class="b">{{end}}</span></p>
      <p class="mt5">全长{{data.distance}} 需要{{data.time}}</p>
    </Card>
    <slot></slot> -->
    <!-- <div id="r-result"></div> -->
   </div>
</template>

<script>
export default {
  props: {
  },
  components: {
  },
  data () {
    return {
      btns: [{
      //   icon: 'android-bus',
      //   name: '公交',
      //   checked: false
      // }, {
        // icon: 'android-car',
        // name: '驾车',
        // checked: true
      // }, {
        icon: 'android-walk',
        name: '步行',
        checked: true
      // }, {
      //   icon: 'android-bicycle',
      //   name: '骑行',
      //   checked: false
      }],
      name: '驾车'
    }
  },
  methods: {
    // 切换导航
    handleBtnClick (item) {
      this.name = item.name
      this.btns.forEach(d => d.checked = false)
      item.checked = true
      this.$emit('on-switch-nav', this.name)
    },
    handleBack () {
      this.$emit('on-back')
    }
  },
  mounted() {
  },
}
</script>

<style lang="scss" scoped>
.route-panel{
  border-radius: 0;
  .search-btn{
    position: absolute;
    right: -58px;
    top: 0;
    border-radius: 0;
    padding: 8px 22px;
    font-size: 16px;
  }
  .item{
    margin-bottom: 10px;
    cursor: pointer;
    color: #666;
    text-align: center;
    border-bottom: 1px solid #eee;
    padding: 0 0 10px;
    .ivu-icon{
      vertical-align: middle;
    }
    &.on{
      color: #00c587
    }
  }
}
</style>
