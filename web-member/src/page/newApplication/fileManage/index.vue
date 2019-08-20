<!--
    作者：wh
    时间：2019-1-2
    描述：文件管理首页
-->
<template>
  <div>
    <top></top>
    <div class="back" :style="{'min-height': height}">
      <!-- 上半部分 -->
      <div class="back-inner">
        <div class="back-center">
          <Row type="flex" align="middle" class="mt20">
            <Col span="24">
              <Breadcrumb>
                <BreadcrumbItem to="/index">首页</BreadcrumbItem>
                <BreadcrumbItem :to="'/pro/member?uid=' + $user.loginAccount">会员中心</BreadcrumbItem>
                <BreadcrumbItem>文件管理</BreadcrumbItem>
              </Breadcrumb>
            </Col>
          </Row>
          <div class="top-app-title mt20">文件管理</div>
          <application-brief appId="e85ebb0d558240179eb7cbbf063bc4e4"></application-brief>
          <div class="mt20">
            <div :class="activeIndex === 0 ? 'tab-cus-active' : 'tab-cus'" @click="tabClick(0)">相册</div>
            <div :class="activeIndex === 1 ? 'tab-cus-active' : 'tab-cus'" @click="tabClick(1)">视频</div>
            <div :class="activeIndex === 2 ? 'tab-cus-active' : 'tab-cus'" @click="tabClick(2)">课件</div>
            <div :class="activeIndex === 3 ? 'tab-cus-active' : 'tab-cus'" @click="tabClick(3)">图书</div>
          </div>
        </div>
      </div>
      <!-- 下半部分 -->
      <div class="back-inner back-center">
        <Picture v-if="activeIndex === 0" @showPic="showPic22" @index="index22"></Picture>
        <Video v-if="activeIndex === 1"></Video>
        <File v-if="activeIndex === 2"></File>
        <Books v-if="activeIndex === 3"></Books>
      </div>
      <!-- 照片预览 -->
      <Preview :list="previewList" ref="preview" :options="options"/>
    </div>
    <div style="height: 40px;" class="back"></div>
    <foot></foot>
  </div>
</template>
<script>
import top from "../../../top";
import foot from "../../../foot";
import File from "./components/file";
import Picture from "./components/picture";
import Video from "./components/video";
import Books from "./components/books";
import Preview from "~components/preview";
import applicationBrief from '~components/application-brief'
export default {
  name: "fileIndex",
  components: {
    top,
    foot,
    File,
    Picture,
    Video,
    Books,
    Preview,
    applicationBrief
  },
  data() {
    return {
      previewList: [],
      options: {
        //  动画显示
        showHideOpacity: true,
        // 背景透明
        bgOpacity: 0.8
        // 显示第几张
        // index: 2
      },
      height: 0,
      activeIndex: 0
    };
  },
  created() {},
  methods: {
    tabClick(index) {
      this.activeIndex = index;
    },
    showPic22(previewList) {
      this.previewList = previewList;
    },
    index22(index) {
      this.$refs.preview.open(index, ".preview-img");
    }
  },
  mounted() {
    this.height = `${window.innerHeight}px`;
  }
};
</script>
<style scoped>
.back {
  background-color: #f5f5f5;
}
.back-inner {
  background-color: #ffffff;
}
.back-center {
  width: 1000px;
  margin: 0 auto;
  margin-top: 10px;
}
.top-app-title {
  font-size: 20px;
  color: #000 85%;
}
.tab-cus {
  padding: 8px 16px;
  font-size: 14px;
  display: inline-block;
  cursor: pointer;
}
.tab-cus-active {
  padding: 8px 16px;
  font-size: 14px;
  display: inline-block;
  cursor: pointer;
  color: #00c587;
  border-bottom: 2px solid #00c587;
}
</style>
