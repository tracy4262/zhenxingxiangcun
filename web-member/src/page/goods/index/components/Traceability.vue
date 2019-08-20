<template>
  <div>
    <!-- 筛选 -->

    <Row type="flex" justify="center" class="mt20">
      <div class="layouts">
        <Row type="flex" justify="space-around" class="mt30 mb30">
          <Col :span="8">
            <Input search enter-button placeholder="请选择产品分类" size="large"/>
          </Col>
          <Col :span="8">
            <Input
              search
              enter-button
              placeholder="请输入商品名称进行搜索"
              size="large"
              v-model="keyword"
              @on-search="searchBtn"
            />
          </Col>
        </Row>
      </div>
      <div style="width:1200px">
        <Breadcrumb>
          <BreadcrumbItem to="/goods/index">产品首页</BreadcrumbItem>
          <BreadcrumbItem to="/goods/showGoods">可追溯</BreadcrumbItem>
        </Breadcrumb>
        <filter-btn ref="btn" @on-search="handleOnchange"></filter-btn>
        <ul class="goods-list" v-if="list!=null">
          <!-- 团购 -->
          <li v-for="(item, index) in list" :key="index" @click="handleDetail(item)">
            <div v-if="item.salesWay == '团购销售'">
              <div
                v-if="item.groupBuyingEndTimeStr.length != 0 && new Date(item.groupBuyingEndTimeStr).getTime() > new Date().getTime()"
                class="clocker"
              >
                距离结束还剩：
                <vui-clocker :time="item.groupBuyingEndTimeStr" format="%D天 %H小时 %M分 %S秒"/>
              </div>
              <img :src="item.notarizationCertificate[0]" class="imgSet">
              <div class="goodsName">
                <p class="ml20" style="font-size:16px">{{item.commodityName}}</p>
                <p
                  style="background:#f5f5f5;padding:2px;font-size:14px"
                  v-if="item.isRetrospect == '是'"
                >可追溯</p>
              </div>
              <Row type="flex" class="priceArea">
                <Col :span="16">
                  <p class="priceText">
                    预售价：
                    <span style="text-decoration:line-through;">￥{{item.originalPrice}}</span>
                  </p>
                  <p class="price">￥{{item.groupBuyingPrice}}</p>
                  <span class="buyCount">购买人数{{item.salesNumber}}</span>
                </Col>
                <Col :span="8">
                  <div class="buyButton">立即抢购</div>
                </Col>
              </Row>
            </div>
            <div v-if="item.salesWay == '定价销售'">
              <div
                v-if="item.discountPeriodStr.length != 0 && new Date(item.discountPeriodStr.slice(13)).getTime() > new Date().getTime()"
                class="clocker"
              >
                距离结束还剩：
                <vui-clocker :time="item.discountPeriodStr.slice(13)" format="%D天 %H小时 %M分 %S秒"/>
              </div>
              <img :src="item.notarizationCertificate[0]" class="imgSet">
              <Row type="flex" justify="space-between" class="mt10 ml15">
                <Col>
                  <span style="font-size:16px;color:red">￥{{item.discountPrice}}</span>
                  <span class="baoyou">包邮</span>
                </Col>
                <Col>
                  <span class="buyCount mr15">{{item.salesNumber}}人已购买</span>
                </Col>
              </Row>
              <Row type="flex" justify="space-between" class="mt10 ml15">
                <Col>
                  <span style="font-size:16px">{{item.commodityName}}</span>
                </Col>
                <Col>
                  <span style="background:#f5f5f5" v-if="item.isRetrospect == '是'">可追溯</span>
                </Col>
              </Row>
              <p style="color:#b1b1b1" class="mt10 ml15 location">{{item.productLocation}}</p>
              <p style="text-decoration:underline;color:#b1b1b1" class="mt10 ml15">{{item.name}}</p>
            </div>
            <div v-if="item.salesWay == '竞价销售'">
              <div
                v-if="item.biddingEndTimeStr.length != 0 && new Date(item.biddingEndTimeStr).getTime() > new Date().getTime()"
                class="clocker"
              >
                距离结束还剩：
                <vui-clocker :time="item.biddingEndTimeStr" format="%D天 %H小时 %M分 %S秒"/>
              </div>
              <img :src="item.notarizationCertificate[0]" class="imgSet">
              <div class="goodsName">
                <p class="ml20" style="font-size:16px">{{item.commodityName}}</p>
                <p
                  style="background:#f5f5f5;padding:2px;font-size:14px"
                  v-if="item.isRetrospect == '是'"
                >可追溯</p>
              </div>
              <Row type="flex" class="priceArea">
                <Col :span="8">
                  <p class="priceText">当前价：</p>
                </Col>
                <Col :span="8">
                  <p style="font-size:18px;margin-top:5px;color:red">￥{{item.startPrice}}</p>
                  <p style="background:#f5f5f5;text-align:center">{{item.salesNumber}}人出价</p>
                </Col>
                <Col :span="8">
                  <div class="buyButton">立即抢拍</div>
                </Col>
              </Row>
            </div>
            <div v-if="item.salesWay == '预售'">
              <div class="clocker">预售</div>
              <img :src="item.notarizationCertificate[0]" class="imgSet">
              <div class="goodsName">
                <p class="ml20" style="font-size:16px">{{item.commodityName}}</p>
                <p
                  style="background:#f5f5f5;padding:2px;font-size:14px"
                  v-if="item.isRetrospect == '是'"
                >可追溯</p>
              </div>
            <Row type="flex" class="priceArea">
              <Col :span="16">
                <p class="priceText">
                  预售价：
                  <span class="price">￥{{item.orderPrice}}</span>
                </p>
                <p class="ml10">
                  定金
                  <span>￥{{item.depositAmount == ""?0:item.depositAmount}}</span>
                  <span class="buyCount">{{item.salesNumber}}人已预购</span>
                </p>
              </Col>
              <Col :span="8">
                <div class="buyButton">立即抢购</div>
              </Col>
            </Row>
            </div>
            <div v-if="item.salesWay == '面议'">
              <img :src="item.notarizationCertificate[0]" class="imgSet">
              <Row type="flex" justify="space-between" class="mt10 ml15">
                <Col>
                  价格
                  <span style="font-size:16px;color:red">面议</span>
                  <span class="baoyou">包邮</span>
                </Col>
                <Col>
                  <span class="buyCount mr15">{{item.salesNumber}}人已购买</span>
                </Col>
              </Row>
              <Row type="flex" justify="space-between" class="mt10 ml15">
                <Col>
                  <span style="font-size:16px">{{item.commodityName}}</span>
                </Col>
                <Col>
                  <span style="background:#f5f5f5" v-if="item.isRetrospect == '是'">可追溯</span>
                </Col>
              </Row>
              <p style="color:#b1b1b1" class="mt10 ml15 location">{{item.productLocation}}</p>
              <p style="text-decoration:underline;color:#b1b1b1" class="mt10 ml15">{{item.name}}</p>
            </div>
          </li>
        </ul>
      </div>
    </Row>
    <div class="mt30 mb50 tc" v-if="list!=null">
      <Page :total="total" :page-size="pageSize" :current="pageNum" @on-change="pageChange"></Page>
    </div>
  </div>
</template>
<script>
import vuiClocker from "~components/clocker/clocker";
import filterBtn from "./btn-bar";
export default {
  props: {
    isShow: {
      type: Boolean,
      default: false
    }
  },
  components: {
    vuiClocker,
    filterBtn
  },
  created() {
    this.searchBtn();
  },
  data() {
    return {
      list: [],
      total: 0,
      pageSize: 10,
      pageNum: 1,
      keyword: "",
      retrospectType: "",
      productStatus: "",
      salesType: ""
      // btnData: [
      //   {
      //     name: "定价",
      //     checked: false,
      //     dataName: "定价销售"
      //   },
      //   {
      //     name: "团购",
      //     checked: false,
      //     dataName: "团购销售"
      //   },
      //   {
      //     name: "竞拍",
      //     checked: false,
      //     dataName: "竞价销售"
      //   },
      //   {
      //     name: "预售",
      //     checked: false,
      //     dataName: "预定产品"
      //   },
      //   {
      //     name: "面议",
      //     checked: false,
      //     dataName: "面议"
      //   },
      //   {
      //     name: "可追溯",
      //     checked: false,
      //     dataName: "应溯"
      //   }
      // ]
    };
  },
  methods: {
    // 到详情页
    handleDetail(item) {
      this.$router.push(
        `/goods/newDetail?id=${item.id}&account=${item.account}`
      );
    },
    reload() {
      window.location.reload();
    },
    searchBtn() {
      this.$api
        .post("/shop/pushShopCommodity/findRetrospect", {
          keyword: this.keyword,
          // code: this.productCode,
          // salesWay: this.salesType,
          num: this.pageNum,
          size: this.pageSize
          // retrospectType: this.retrospectType,
          // productStatus: this.productStatus
        })
        .then(res => {
          if (res.code === 200) {
            this.list = res.data.list;
            this.total = res.data.total;
          }
        });
    },
    handleOnchange(e, dataName) {
      // console.log(e);
      // this.retrospectType = "";
      // this.productStatus = "";
      // if (e.name === "预售") {
      //   this.productStatus = e.dataName;
      //   this.searchBtn();
      // } else if (e.name === "可追溯") {
      //   this.retrospectType = e.dataName;
      //   this.searchBtn();
      // } else if (e.name === "默认") {
      //   this.salesType = "";
      //   this.searchBtn();
      // } else if (e.name != "价格") {
      //   this.salesType = e.dataName;
      //   this.searchBtn();
      // }
    },
    pageChange(e) {
      this.pageNum = e;
      this.searchBtn();
    }
  }
};
</script>
<style lang="scss" scoped>
.goods-list {
  .clocker {
    background: rgba(254, 121, 34, 1);
    color: #fff;
    position: absolute;
    left: 0;
    top: 0;
    right: 0;
    padding: 6px 2px;
    text-align: center;
  }
  li {
    height: 280px;
    position: relative;
    background: #fff;
    margin: 15px 15px 0 0;
    display: inline-block;
    width: calc(100% / 5 - 12px);
    list-style: none;
    // padding: 2px;
    vertical-align: middle;
    border: 1px solid rgba(58, 58, 58, 0.62);
    transition: box-shadow 0.2s cubic-bezier(0.47, 0, 0.745, 0.715);
    &:nth-child(5n) {
      margin-right: 0;
    }
    &:hover {
      box-shadow: 0 0 0 2px #00c587;
    }
    a {
      display: block;
    }
    .goodsName {
      height: 66px;
      display: flex;
      justify-content: space-between;
      line-height: 66px;
      color: #4a4a4a;
      font-size: 18px;
    }
    .imgSet {
      width: 100%;
      height: 150px;
      background: #66ccff;
    }
    .priceArea {
      height: 64px;
      border-top: 1px solid #4a4a4a;
      .buyButton {
        background: #bebebe;
        color: #fff;
        font-size: 14px;
        text-align: center;
        line-height: 60px;
        height: 60px;
      }
      .priceText {
        font-size: 16px;
        margin: 5px 0 0 10px;
      }
      .price {
        font-size: 20px;
        color: red;
        margin: 0 0 0 10px;
        display: inline-block;
      }
    }
  }
}
.buyCount {
  background: #f5f5f5;
  padding: 1px;
}
.location {
  overflow: hidden;
  width: 200px;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
