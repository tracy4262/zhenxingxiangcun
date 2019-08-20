<template>
  <div :style="{'background': '#F9F9F9'}">
    <top :address="false"></top>
    <div :style="{'min-height': height}">
      <div class="bg-white">
        <div class="layouts pd30">
          <Breadcrumb class="mb40">
            <BreadcrumbItem to="/index">首页</BreadcrumbItem>
            <BreadcrumbItem to="/pro/member">会员中心</BreadcrumbItem>
            <BreadcrumbItem>名片管理</BreadcrumbItem>
          </Breadcrumb>
          <h4 style="font-size: 22px;">名片管理</h4>
          <application-brief appId="35d66355d9264a3a98ccaee2404a0af8"></application-brief>
        </div>
      </div>
      <div class="layout-detail bg-white" >
        <detail class="pd30 mt20"></detail>
      </div>
    </div>
    <foot class="mt50"></foot>
  </div>
</template>
<script>
import top from '~src/top'
import foot from '~src/foot'
import detail from './detail'
import applicationBrief from '~components/application-brief'

export default {
  props: {
    data: Array,
    type: {
      type: Number,
      default: 0
    },
    id: {
      type: String,
      default: ''
    }
  },
  components: {
    top,
    foot,
    detail,
    applicationBrief
  },
  data: () => ({
    height: 0
  }),
  created () {
  },
  methods: {
  },
  mounted () {
    this.height = `${window.innerHeight}px`
  }
}
</script>
<style lang="scss" scoped>
.layouts{
  width: 1200px;
  margin: 0 auto;
}
.layout-detail{
  width: 1100px;
  margin: 0 auto;
}
</style>
