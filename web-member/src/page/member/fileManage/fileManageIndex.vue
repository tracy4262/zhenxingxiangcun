<template>
  <div class="layout">
    <div ref="top">
      <top :address="false"/>
    </div>
    <div class="main" :style="{'min-height':height}">
      <div class="container">
        <Row :gutter="20">
          <Col span="24">
            <Breadcrumb class="breadcrumb">
              <BreadcrumbItem to="/">首页</BreadcrumbItem>
              <BreadcrumbItem to="/pro/member">会员中心</BreadcrumbItem>
              <BreadcrumbItem>文件管理</BreadcrumbItem>
            </Breadcrumb>
            <div>
              <h1 class="wjgl">文件管理</h1>
              <p class="wjsm">文件管理应用为用户在农事无忧平台使用的网盘，用户上传图片、视频、课件、图书等文件，方便用户在发布信息或者门户展示时可以快速的调用</p>
            </div>

            <div class="wrapper">
              <my-style></my-style>
            </div>
          </Col>
        </Row>
      </div>
    </div>
    <div ref="foot">
      <foot></foot>
    </div>
  </div>
</template>

<script>
import top from "../../../top";
import foot from "../../../foot";
import myUpload from "vue-image-crop-upload";
import myStyle from "./myStyle";
import appBanner from "~components/app-banner";

export default {
  components: {
    top,
    foot,
    myUpload,
    myStyle,
    appBanner
  },
  data() {
    return {
      height: ""
    };
  },
  created() {},
  mounted() {
    this.handleGetHeight();
  },
  methods: {
    // 获取页面高度
    handleGetHeight() {
      let clientHeight = document.documentElement.clientHeight;
      let topHeight = this.$refs.top.offsetHeight;
      let footHeight = this.$refs.foot.offsetHeight;
      this.height = `${clientHeight - topHeight - footHeight}px`;
    }
  }
};
</script>
<style lang="scss" scoped>
.breadcrumb {
  padding-top: 29px;
  padding-bottom: 16px
}
.wjgl {
    font-family: PingFangSC-Medium;
    font-size: 20px;
    color: #000;
    opacity: 0.85;
    line-height: 28px;
    width: 80px;
    height: 28px;
}
.wjsm {
   font-family: PingFangSC-Regular; 
   padding-top: 16px;
   font-size: 14px;
   color: #000;
   opacity: 0.65;
   line-height: 22px;
   width: 748px;
   padding-bottom: 35px
}
</style>