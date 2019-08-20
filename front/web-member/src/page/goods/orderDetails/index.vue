<template>
  <div>
    <div ref="top">
      <top :address="false" />
    </div>
    <div class="pt30" :style="{'min-height': height}">
      <div  class="bg-white layouts">
        <goods-head title="订单管理">
          <BreadcrumbItem>订单管理</BreadcrumbItem>
        </goods-head>
      </div>
      <div class="layouts goods-order-detail">
        <Tabs :value="tabActive"  :animated="false" @on-click="handleTabsClick">
          <TabPane label="我买到的商品" name="purchasedGoods"></TabPane>
          <TabPane label="我卖出的商品" name="soldGoods"></TabPane>
          <TabPane label="我参与的竞拍" name="purchasedBidding"></TabPane>
          <TabPane label="我发起的竞拍" name="soldBidding"></TabPane>
        </Tabs>
      </div>      
      <div class="pt30 pb20 goods-order-details" style="background:#F9F9F9;">
        <div class="layouts bg-white pd20">
          <router-view></router-view>
        </div>
      </div>
    </div>    
    <div ref="foot">
      <foot></foot>
    </div>
  </div>
</template>
<script>
import top from '~src/top'
import foot from '~src/foot'
import goodsHead from '../components/head'
export default {
  components: {
    top,
    foot,
    goodsHead
  },
  data () {
    return {
      height:'',
      tabActive: 'purchasedGoods'
    }
  },
  created(){
    this.tabActive = this.$router.history.current.name
  },
  watch:{
    '$route' (to, from){
      this.tabActive = to.name
    }
  },
  methods: {
    handleTabsClick (name) {
      if (name === 'purchasedGoods') {
        this.$router.push({
          path: '/orderDetails/purchasedGoods'
        })
      } else if (name === 'soldGoods') {
        this.$router.push('/orderDetails/soldGoods')
      } else if (name === 'purchasedBidding') {
        this.$router.push('/orderDetails/purchasedBidding')
      } else if (name === 'soldBidding') {
        this.$router.push('/orderDetails/soldBidding')
      }
    }, 
    // 获取页面高度
    handleGetHeight () {
      let clientHeight = document.documentElement.clientHeight
      let topHeight = this.$refs.top.offsetHeight
      let footHeight = this.$refs.foot.offsetHeight
      this.height = `${clientHeight-topHeight-footHeight}px`
    }
  },
  mounted () {
    this.handleGetHeight()
  },
}
</script>
<style lang="scss">
.goods-order-detail{
  .ivu-tabs-bar{
    margin-bottom: 0px;
  }
}
.goods-order-details{
  .ivu-tabs{
    overflow: inherit;
  }
}
  
</style>
