<template>
  <div>
    <top :address="false" ref="top" active="1"></top>
    <headNav></headNav>
    <groupList :isShow="isShow" ref="group" v-if="this.type == 1"></groupList>
    <biddingList :isShow="isShow" ref="bidding" v-if="this.type == 2"></biddingList>
    <preSaleList :isShow="isShow" ref="preSale" v-if="this.type == 3"></preSaleList>
    <saleList :isShow="isShow" ref="sale" v-if="this.type == 4"></saleList>
    <faceSaleList :isShow="isShow" ref="face" v-if="this.type == 5"></faceSaleList>
    <Traceability v-if="this.type == 6" ref="trace"></Traceability>
    <newList
      v-if="productCode != undefined"
      :productCode="productCode"
      :productName="productName"
      :parentCode="parentCode"
      :parentName="parentName"
    ></newList>
    <searchList v-if="this.type == 7" :keyword="keyword"></searchList>
  </div>
</template>
<script>
import top from "~src/top";
import headNav from "../../../51index/components/nav";
import newList from "./newList";
import groupList from "./groupList";
import biddingList from "./biddingList";
import saleList from "./saleList";
import preSaleList from "./preSaleList";
import faceSaleList from "./faceSaleList";
import Traceability from "./Traceability";
import searchList from "./searchList";
export default {
  components: {
    newList,
    headNav,
    top,
    groupList,
    biddingList,
    saleList,
    preSaleList,
    faceSaleList,
    Traceability,
    searchList
  },
  data() {
    return {
      isShow: true,
      type: 0,
      productCode: "",
      productName: "",
      parentName: "",
      parentCode: "",
      keyword:""
      // total: "",
      // pageSize: 1,
      // pageNum: 1,
      // list:""
    };
  },
  created() {
    this.type = this.$route.query.type;
    this.productCode = this.$route.query.code;
    this.productName = this.$route.query.name;
    this.parentName = this.$route.query.parentName;
    this.parentCode = this.$route.query.parentCode;
    this.keyword = this.$route.query.keyword;
  },
  methods: {
  }
};
</script>
