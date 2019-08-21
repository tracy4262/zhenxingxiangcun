<template>
  <div :style="{'background': '#F9F9F9'}">
    <!-- <top :address="false"></top> -->
    <div :style="{'min-height': height}">
      <div class="bg-white">
        <div class="layouts pd30">
          <Breadcrumb class="mb40">
            <BreadcrumbItem><a :href="`${location}/user-auth-admin/index`">首页</a></BreadcrumbItem>
            <!-- <BreadcrumbItem><a :href="`${location}/pro/member`">会员中心</a></BreadcrumbItem> -->
            <BreadcrumbItem>商品发布</BreadcrumbItem>
          </Breadcrumb>
          <Steps :current="current" :class="type >= 5 ? 'cursor' : ''">
            <Step
            v-for="(item, index) in data"
            :key="index"
            :title="item"
            @click.native="handleStepClick(index + 1)"
            ></Step>
          </Steps>
        </div>
      </div>
      <div class="layouts" ref="authWrap">
        <router-view class="pd30 mt20"></router-view>
      </div>
    </div>
    <foot class="mt50"></foot>
  </div>
</template>
<script>
import top from '~src/top'
import foot from '~src/foot'

export default {
  props: {
    data: Array,
    type: {
      type: Number,
      default: 0
    }
  },
  components: {
    top,
    foot
  },
  data: () => ({
    current: 0,
    height: 0,
    location: window.location.origin
  }),
  created () {
    let path = this.$route.path
    this.current = parseInt(path.slice(path.length - 1, path.length)) - 1
  },
  watch: {
    '$route' (to, path) {
      this.current = parseInt(to.path.slice(to.path.length - 1, to.path.length)) - 1
    }
  },
  methods: {
    // 跳转
    handleStepClick (index) {
      if (this.type >= 5) {
        this.$emit('on-router', index)
        this.current = index - 1
      }
    }
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
.cursor{
  .ivu-steps-item{
    cursor:pointer;
  }
}
</style>
<style lang="scss">
  #app{
    .ivu-form-item-label:before{
      margin: 0 2px 0 -10px;
    }
  }
  .v-transfer-dom{
    .ivu-form-item-label:before{
      margin: 0 2px 0 -10px;
    }
  }
</style>
