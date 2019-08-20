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
              <BreadcrumbItem>景区服务</BreadcrumbItem>
          </Breadcrumb>
          <b style="font-size:20px">景区服务</b>
          <application-brief appId="f21f18517ba64175b7741ffc89248d43"></application-brief>
          <Tabs :value="tabActive" :animated="false" @on-click="handleTabsClick" class="mt20 page-ivu-tabs-bar">
            <TabPane label="门票管理" name="ticket"></TabPane>
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
      tabActive:'ticket'
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
      this.$router.push('/scenicSpot/' + name)
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
<style>

</style>
