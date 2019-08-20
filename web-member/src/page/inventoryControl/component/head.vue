<template>
  <div class="bg-white">
    <div class="layouts pt30 pb20">
      <Breadcrumb>
        <BreadcrumbItem to="/pro/member">会员中心</BreadcrumbItem>
        <slot></slot>
      </Breadcrumb>
      <p class="mt20 mb40 b" style="font-size: 20px">{{title}}</p>
      <p style="font-size: 14px" v-html="content"></p>
      <Steps :current="current" class="mb20" v-if="steps">
        <Step title="通用商品基本信息"></Step>
        <Step title="商品基本信息"></Step>
        <Step title="商品营销基础信息"></Step>
        <Step title="商品追溯与防伪信息"></Step>
        <Step title="商品承诺信息"></Step>
      </Steps>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    steps: {
      type: Boolean,
      default: false
    },
    title: String,
    current: {
      type: Number,
      default: 0
    },
    content: {
      type: String,
      default: ''
    }
  },
  data () {
    return {

    }
  }
}
</script>
<style lang="scss" scoped>
</style>
