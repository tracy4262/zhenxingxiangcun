<template>
   <div>
      <div ref="top">
        <top :address="false" />
      </div>
      <div :style="{'min-height': height}">
        <!-- <app-banner
          src="../../../static/img/app-banner-picking.png"
          title="住宿管理">
        </app-banner> -->
        <div class="services-layouts">
          <Breadcrumb class="pt30 pb20">
              <BreadcrumbItem to="/index">首页</BreadcrumbItem>
              <BreadcrumbItem to="/pro/member">会员中心</BreadcrumbItem>
              <BreadcrumbItem>民宿服务</BreadcrumbItem>
          </Breadcrumb>
          <b style="font-size:20px">民宿服务</b>
          <application-brief appId="9420131312c94d8ab1e0f28c624cf134"></application-brief>
          <!-- <p class="pt20 pb40" style="font-size: 14px;font-family: PingFangSC-Regular;">
            民宿管理为用户提供民宿服务信息的维护、服务的发布以及订单管理。</p> -->
          <Tabs :value="tabActive" :animated="false" @on-click="handleTabsClick" class="mt20 page-ivu-tabs-bar">
            <TabPane label="房间类型" name="roomType"></TabPane>
            <TabPane label="房间列表" name="roomList"></TabPane>
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
// import appBanner from '~components/app-banner'
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
      tabActive:'roomType'
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
      this.$router.push('/stay/' + name)
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
