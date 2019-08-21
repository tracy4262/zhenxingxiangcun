<template>
  <div class="bg-white">
    <div class="layouts pt30 pb20">
      <Breadcrumb>
        <BreadcrumbItem to="/stay/roomType">住宿管理</BreadcrumbItem>
        <BreadcrumbItem to="/stay/service">发布项目</BreadcrumbItem>
        <BreadcrumbItem :to="toLinks">上传服务营销信息</BreadcrumbItem>
        <BreadcrumbItem>{{title}}</BreadcrumbItem>
        <slot></slot>
      </Breadcrumb>
      <p class="mt20 mb20 b" style="font-size: 20px">{{title}}</p>
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
    },
    toLinks: {
      type: String,
      default: ''
    },
  },
  data () {
    return {

    }
  }
}
</script>
<style lang="scss" scoped>
</style>
