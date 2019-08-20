<template>
  <div class="production">
    <div ref="top">
      <top :address="false" />
    </div>
    <div class="pro_main" :style="{'min-height': height}">
      <div class="main_top">
        <div class="main_top_wrap">
          <Breadcrumb  v-if="tabActive === 'yearList'">
            <BreadcrumbItem to="/index">首页</BreadcrumbItem>
            <BreadcrumbItem to="/pro/member">会员中心</BreadcrumbItem>
            <BreadcrumbItem>种植业生产管理</BreadcrumbItem>
          </Breadcrumb>

          <Breadcrumb  v-if="tabActive === 'plantList'">
            <BreadcrumbItem to="/index">首页</BreadcrumbItem>
            <BreadcrumbItem to="/pro/member">会员中心</BreadcrumbItem>
            <BreadcrumbItem to="/productionControl/yearList">种植业生产管理</BreadcrumbItem>
            <BreadcrumbItem v-if="year && tabActive === 'plantList'">{{year}}</BreadcrumbItem>
          </Breadcrumb>

          <Breadcrumb  v-if="tabActive != 'yearList' && tabActive != 'plantList'">
            <BreadcrumbItem to="/index">首页</BreadcrumbItem>
            <BreadcrumbItem to="/pro/member">会员中心</BreadcrumbItem>
            <BreadcrumbItem to="/productionControl/yearList">种植业生产管理</BreadcrumbItem>
            <BreadcrumbItem :to="`/productionControl/plantList?yearId=${this.yearId}&year=${year}`" v-if="year">{{year}}</BreadcrumbItem>
            <BreadcrumbItem>{{name}}</BreadcrumbItem>
          </Breadcrumb>
          <div class="main_top_title">种植业生产管理</div>
          <application-brief appId="aa1f18517ba64175b7123ffc89248d88"></application-brief>
          <Tabs :animated="false" @on-click="handleTabsClick" class="pro_tab" :value="tabActive" v-if="tabActive != 'yearList' && tabActive != 'plantList'">
            <TabPane label="生产计划" name="productionPlans"></TabPane>
            <TabPane label="产量测算" name="outputGuess"></TabPane>
            <TabPane label="生产记录" name="productionRecords"></TabPane>
          </Tabs>
        </div>
      </div>
      <router-view></router-view>
    </div>
    <div ref="foot">
      <foot ></foot>
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
      height: '',
      tabShow: false,
      tabActive: 'productionPlans',
      year: '',
      yearId: '',
      name: '',
      id: '',
      displayName: ''
    }
  },
  created () {
    this.tabActive = this.$router.history.current.name
     this.$api.post('/member/login/findCurrentUser', {
        account: this.$user.loginAccount
    }).then(response => {
        if (response.data.displayName) {
            this.displayName = response.data.displayName
        }
    })
  },
  mounted () {
    this.handleGetHeight()
  },
  watch: {
    '$route' (to, from){
      this.tabActive = to.name
    }
  },
  methods: {
    handleGetHeight () {
      let clientHeight = document.documentElement.clientHeight
      let topHeight = this.$refs.top.offsetHeight
      let footHeight = this.$refs.foot.offsetHeight
      this.height = `${clientHeight-topHeight-footHeight}px`
    },
    handleTabsClick (name) {
      this.$router.push({name: name, query: {
        year: this.year,
        yearId: this.yearId,
        name: this.name,
        id: this.id,
      }})
    }
  }
}
</script>

<style lang="scss" scoped>
.production{
  .pro_main{
    width: 100%;
    background: rgb(249, 249, 249);
    padding-bottom: 40px;
    .main_top{
      background: #fff;
      margin-bottom: 20px;
      .main_top_wrap{
        width: 1000px;
        margin: 0 auto;
        padding-top: 28px;
      }
      .main_top_title{
        font-size: 20px;
        color: #000 85%;
        font-weight: bold;
        margin: 16px 0;
      }
      .main_top_desc{
        width: 760px;
        line-height: 22px;
        font-size: 14px;
        color: rgba(0, 0, 0, .6);
        padding-bottom: 20px;
      }
    }
  }
}
</style>
<style>
.pro_tab .ivu-tabs-bar{
  border-bottom: 0;
  margin-bottom: 0;
}
</style>

