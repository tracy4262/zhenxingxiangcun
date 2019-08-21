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
              <BreadcrumbItem>名称库管理</BreadcrumbItem>
          </Breadcrumb>
          <b style="font-size:20px">名称库管理</b>
          <application-brief appId="2259165989144d499fb6d003619ae3e7"></application-brief>
          <Tabs :value="tabActive" :animated="false" @on-click="handleTabsClick" class="mt20 page-ivu-tabs-bar">
            <TabPane :label="item.label" :name="item.name" v-for="(item, index) in data"></TabPane>
          </Tabs>
          <!-- <Row>
            <Col span="3" class="mt15" v-for="(item, index) in data" :key="index">
              <Button style="max-width: 100%;" :type="item.name == tabActive ? 'primary' : 'text'" @click="handleTabsClick(item.name)">
                  <Tooltip placement="top" class="ell" :delay="1000">
                    {{item.label}}
                    <div slot="content"> {{item.label}} </div>
                  </Tooltip>
              </Button>
            </Col>
          </Row> -->
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
import top from '../../../top'
import foot from '../../../foot'
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
        {name: 'species', label: '物种管理'},
        {name: 'variety', label: '品种管理'},
        {name: 'disease', label: '病害管理'},
        {name: 'pest', label: '虫害管理'},
        {name: 'commodity', label: '通用商品名'},
        {name: 'service', label: '通用服务名'},
        // {name: 'unit', label: '计量单位'},
        // {name: 'position', label: '职务'},
        // {name: 'job', label: '职称'},
        // {name: 'nationalEconomy', label: '国民经济行业分类'},
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
          path: `/nameLibrary/${name}`
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
    .ivu-tabs-ink-bar{
      height: 3px;
    }
  }
}
</style>
