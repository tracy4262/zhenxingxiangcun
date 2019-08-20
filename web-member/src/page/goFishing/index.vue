<template>
   <div>
      <div ref="top">
        <top :address="false" />
      </div>
      <div :style="{'min-height': height}">
        <div class="focus-management-layouts">
          <Breadcrumb class="pt30 pb20">
              <BreadcrumbItem to="/index">首页</BreadcrumbItem>
              <BreadcrumbItem to="/pro/member">会员中心</BreadcrumbItem>
              <BreadcrumbItem>垂钓服务</BreadcrumbItem>
          </Breadcrumb>
          <b style="font-size:20px">垂钓服务</b>
          <application-brief appId="c96d3729a9fc4a37a6eeb9d29bb776f0"></application-brief>
          <Tabs :value="tabActive" :animated="false" @on-click="handleTabsClick" class="mt20 page-ivu-tabs-bar">
            <!-- <TabPane label="物种列表" name="species"></TabPane>
            <TabPane label="产品列表" name="productList"></TabPane> -->
            <TabPane label="服务列表" name="service"></TabPane>
          </Tabs>
        </div>
        <div style="background: #F5F5F5;" class="pt30 pb30">
          <Card class="focus-management-layouts">
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
      tabActive:'service'
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
      if (name === 'species') {
        this.$router.push({
          path: '/fishing/species'
        })
      } else if (name === 'productList') {
        this.$router.push('/fishing/productList')
      } else if (name === 'service'){
        this.$router.push('/fishing/service')
      } else if (name === 'order'){
        this.$router.push('/fishing/order')
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
<style>

</style>
