<template>
   <div>
      <div ref="top">
        <top :address="false" />
      </div>
      <div :style="{'min-height': height}">
        <div class="services-layouts">
          <Breadcrumb class="pt30 pb20">
              <BreadcrumbItem to="/index">首页</BreadcrumbItem>
              <BreadcrumbItem to="/pro/member">会员中心</BreadcrumbItem>
              <BreadcrumbItem>农家乐服务</BreadcrumbItem>
          </Breadcrumb>
          <b style="font-size:20px">农家乐服务</b>
          <application-brief appId="ac2652e2c1f54816b6ff26dfaf38b43b"></application-brief>
          <Tabs :value="tabActive" :animated="false" @on-click="handleTabsClick" class="mt20 page-ivu-tabs-bar">
            <TabPane label="菜品分类管理" name="menuType"></TabPane>
            <TabPane label="菜品管理" name="dish"></TabPane>
            <TabPane label="包房管理" name="privateRoom"></TabPane>
            <TabPane label="餐桌管理" name="diningTable"></TabPane>
            <TabPane label="服务列表" name="service"></TabPane>
            <TabPane label="订单管理" name="order"></TabPane>
          </Tabs>
        </div>
        <div style="background: #F5F5F5;" class="pt30 pb30">
          <Card class="services-layouts">
            <router-view></router-view>
          </Card>
        </div>
      </div>
      <div ref="foot">
        <foot></foot>
      </div>
   </div>
</template>
<script>
import top from '../../top'
import foot from '../../foot'
import applicationBrief from '~components/application-brief'
export default {
  components: {
    top,
    foot,
    applicationBrief
  },
  data () {
    return {
      height:'',
      tabActive:'menuType'
    }
  },
  created(){
    console.log('this.$router.history', this.$router.history)
    this.tabActive = this.$router.history.current.name
  },
  watch:{
    '$route' (to, from){
      this.tabActive = to.name
    }
  },
  methods: {
    handleTabsClick (name) {
      this.$router.push('/restaurant/' + name)
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
.services-layouts{
  width: 1200px;
  margin: 0 auto;
  .page-ivu-tabs-bar{
    .ivu-tabs-bar{
      border-bottom: 0px solid #dcdee2;
      margin-bottom: 0px;
    }
    .ivu-tabs-ink-bar{
      height: 3px;
    }
  }
}
</style>
