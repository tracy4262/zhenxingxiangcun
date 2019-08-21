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
              <BreadcrumbItem>关注管理</BreadcrumbItem>
          </Breadcrumb>
          <b style="font-size:20px">关注管理</b>
          <application-brief appId="c20bcf3d976447a8bf8d0d0120c16ca7"></application-brief>
          <Tabs :value="tabActive" :animated="false" @on-click="handleTabsClick" class="mt20 page-ivu-tabs-bar">
            <TabPane :label="item.label" :name="item.name" v-for="(item, index) in data" :key="index"></TabPane>
          </Tabs>
        </div>
        <div style="background: #F5F5F5;" >
          <div class="pt20 pb20 focus-management-layouts">
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
      tabActive:'species',
      data: [
        {name: 'species', label: '物种'},
        {name: 'product', label: '产品'},
        {name: 'service', label: '服务'},
        {name: 'member', label: '会员'},
        {name: 'public', label: '公众号'},
        {name: 'news', label: '资讯'},
        {name: 'knowledge', label: '知识'},
        {name: 'policy', label: '政策'},
      ]
    }
  },
  created(){
    this.tabActive = this.$router.history.current.name
    console.log(this.$router.history)
    console.log(this.tabActive)
  },
  watch:{
    '$route' (to, from){
      this.tabActive = to.name
    }
  },
  methods: {
    handleTabsClick (name) {
       this.$router.push({
          path: `/focusManagement/${name}`
        })
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
.focus-management-layouts{
  width: 1044px;
  margin: 0 auto;
  .page-ivu-tabs-bar{
    .ivu-tabs-bar{
      border-bottom: 0px solid #dcdee2;
      margin-bottom: 0px;
    }
  }
}
</style>
