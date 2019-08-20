<template>
  <div>
    <top :address="false" ref="top" active="1"></top>
    <head-nav :active="4"></head-nav>
    <div class="layouts">
      <Row type="flex" justify="center" class="goods-search mt30 mb30">
        <Col span="10">
          <Input
            v-model="keyword"
            placeholder="搜索商品"
            size="large"
        
          ></Input>
        </Col>
        <Col span="2">
          <Button type="primary" icon="ios-search" size="large" long @click="handleSearch"></Button>
        </Col>
      </Row>
    </div>
    <ul class="nav-top">
      <li @click="showAllClick(4)">定价</li>
      <li @click="showAllClick(1)">团购</li>
      <li @click="showAllClick(2)">竞价</li>
      <li @click="showAllClick(5)">面议</li>
      <li @click="showAllClick(3)">预售</li>
      <li @click="showAllClick(6)">可追溯</li>
    </ul>
    <div class="slider">
      <div class="layouts">
        <!-- <ul class="nav">
          <li v-for="(item, index) in navData" :key="index">
            {{item.name}}
          </li>
        </ul>-->
        <min-nav></min-nav>
      </div>
      <Carousel arrow="never" autoplay v-model="silder" loop>
        <CarouselItem v-for="(item, index) in imgList" :key="index">
          <img :src="item.picture_url" alt>
        </CarouselItem>
      </Carousel>

      <!-- <div class="layouts">
      <div class="tc">
        <div class="tc pt30">
          <span style="width:14px;height:3px;background:#00c587;display:inline-block;"></span>
        </div>
        <span style="font-size:30px;color:#4a4a4a;">定价好货</span>
      </div>
      <list ref="list" @on-login="handleLogin"></list>
      </div>-->
      <Row type="flex" justify="center" align="middle" class="mt30 container">
        <Col class="banner_line"></Col>
        <Col>
          <p style="font-size:28px;color:#4a4a4a;margin:0 20px">团购好货</p>
        </Col>
        <Col class="banner_line"></Col>
        <p class="showAll" @click="showAllClick(1)">
          查看全部
          <Icon type="ios-arrow-forward" size="16"/>
        </p>
      </Row>
      <groupList></groupList>

      <Row type="flex" justify="center" align="middle" class="mt30 container">
        <Col class="banner_line"></Col>
        <Col>
          <p style="font-size:28px;color:#4a4a4a;margin:0 20px">竞价商品</p>
        </Col>
        <Col class="banner_line"></Col>
        <p class="showAll" @click="showAllClick(2)">
          查看全部
          <Icon type="ios-arrow-forward" size="16"/>
        </p>
      </Row>
      <biddingList></biddingList>

      <Row type="flex" justify="center" align="middle" class="mt30 container">
        <Col class="banner_line"></Col>
        <Col>
          <p style="font-size:28px;color:#4a4a4a;margin:0 20px">预售商品</p>
        </Col>
        <Col class="banner_line"></Col>
        <p class="showAll" @click="showAllClick(3)">
          查看全部
          <Icon type="ios-arrow-forward" size="16"/>
        </p>
      </Row>
      <preSaleList></preSaleList>

      <Row type="flex" justify="center" align="middle" class="mt30 container">
        <Col class="banner_line"></Col>
        <Col>
          <p style="font-size:28px;color:#4a4a4a;margin:0 20px">定价商品</p>
        </Col>
        <Col class="banner_line"></Col>
        <p class="showAll" @click="showAllClick(4)">
          查看全部
          <Icon type="ios-arrow-forward" size="16"/>
        </p>
      </Row>
      <saleList></saleList>

      <Row type="flex" justify="center" align="middle" class="mt30 container">
        <Col class="banner_line"></Col>
        <Col>
          <p style="font-size:28px;color:#4a4a4a;margin:0 20px">价格面议类商品</p>
        </Col>
        <Col class="banner_line"></Col>
        <p class="showAll" @click="showAllClick(5)">
          查看全部
          <Icon type="ios-arrow-forward" size="16"/>
        </p>
      </Row>
      <faceSaleList></faceSaleList>
    </div>
    <cart-btn></cart-btn>
  </div>
</template>

<script>
import top from "~src/top";
import headNav from "../../51index/components/nav";
import minNav from "../components/min-nav";
import cartBtn from "../components/cart-btn";

import list from "./components/list";
import newList from "./components/newList";
import groupList from "./components/groupList";
import biddingList from "./components/biddingList";
import saleList from "./components/saleList";
import preSaleList from "./components/preSaleList";
import faceSaleList from "./components/faceSaleList";

export default {
  components: {
    top,
    headNav,
    list,
    minNav,
    cartBtn,
    newList,
    groupList,
    biddingList,
    saleList,
    preSaleList,
    faceSaleList
  },
  data() {
    return {
      keyword: "",
      silder: 0,
      imgList: [],
      list: [],
      groupList: [],
      biddingList: [],
      preSaleList: [],
      saleList: [],
      faceSaleList: [],
      account: "",
      loginInfo: JSON.parse(
        sessionStorage.getItem(sessionStorage.getItem("key"))
      )
    };
  },
  created() {
    this.keyword = this.$route.query.title;
    // 获取banner图
    this.handleGetImgList();
    // this.getData();
  },
  methods: {
    // 搜索
    handleSearch() {
      this.$router.push({path:"/goods/showGoods?type=7",query:{type:7,keyword:this.keyword}})
    },
    // 登录
    handleLogin() {
      this.$refs["top"].loginuser();
    },
    // 获取banner图
    handleGetImgList() {
      this.$api
        .post("/portal/shopCommdoity/findCommodityImage", { account: "" })
        .then(response => {
          if (response.code === 200) {
            this.imgList = response.data;
          }
        });
    },
    showAllClick(num) {
      this.$router.push({ path: "/goods/showGoods", query: { type: num } });
    }
  }
};
</script>

<style lang="scss" scoped>
.slider {
  position: relative;
  height: 385px;
  .layouts {
    position: relative;
  }
  img {
    height: 385px;
    width: 100%;
  }
}
</style>
<style lang="scss">
.nav-top{
  width: 1200px;
  text-align: center;
  margin: 20px auto;
  li{
    display: inline-block;
    margin: 0 20px;
    font-size: 16px;
    &:hover{
      cursor: pointer;
    }
  }
}
.goods-search {
  .ivu-input,
  .ivu-btn {
    border-radius: 0;
  }
}
.container {
  width: 1200px;
  position: relative;
}
.banner_line {
  height: 2px;
  width: 80px;
  background: #4a4a4a;
}
.showAll {
  position: absolute;
  right: 2px;
  &:hover {
    cursor: pointer;
  }
}
</style>

