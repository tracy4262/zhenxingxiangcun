<template>
  <div class>
    <top :address="false" ref="top"></top>
    <head-nav :active="4"></head-nav>
    <div class="layouts">
      <Row>
        <Col span="24">
          <Breadcrumb class="mt20">
            <BreadcrumbItem to="/goods/index">产品首页</BreadcrumbItem>
            <!-- 1 团购 2 竞价 3 预售 4 定价 5 面议 -->
            <BreadcrumbItem
              to="/goods/retrospect?type=1"
              v-if="pricing.salesWay == '团购销售' && goodInfo.productStatus !== '预定产品'"
            >{{pricing.salesWay}}</BreadcrumbItem>
            <BreadcrumbItem
              to="/goods/retrospect?type=2"
              v-if="pricing.salesWay == '竞价销售' && goodInfo.productStatus !== '预定产品'"
            >{{pricing.salesWay}}</BreadcrumbItem>
            <BreadcrumbItem
              to="/goods/retrospect?type=3"
              v-if="goodInfo.productStatus == '预定产品'"
            >预售产品</BreadcrumbItem>
            <BreadcrumbItem
              to="/goods/retrospect?type=4"
              v-if="pricing.salesWay == '定价销售' && goodInfo.productStatus !== '预定产品'"
            >{{pricing.salesWay}}</BreadcrumbItem>
            <BreadcrumbItem
              to="/goods/retrospect?type=5"
              v-if="pricing.salesWay == '面议' && goodInfo.productStatus !== '预定产品'"
            >{{pricing.salesWay}}</BreadcrumbItem>
            <BreadcrumbItem>{{goodInfo.productName}}</BreadcrumbItem>
            <!-- <BreadcrumbItem>{{info.commodityTypeInfos.productName}}</BreadcrumbItem> -->
          </Breadcrumb>
          <Row class="mt20 mb20">
            <Col span="9">
              <product-view ref="view"></product-view>
            </Col>
            <Col span="15" class="pl20">
              <div v-if="goodInfo.productStatus !== '预定产品'">
                <!-- 定价商品 团购-->
                <pricing-goods
                  v-if="pricing.salesWay === '定价销售' || pricing.salesWay === '团购销售'"
                  :info="goodInfo"
                  :pricing="pricing"
                  :delivery="delivery"
                  :gradeNum="gradeNum"
                  @on-login="handleLogin"
                  @on-buy="handleBuyNow"
                  @on-add="handleAddCat"
                  @get-base="getBase"
                ></pricing-goods>
                <!-- 面议-->
                <negotiableGoods
                  v-if="pricing.salesWay === '面议'"
                  :info="goodInfo"
                  :gradeNum="gradeNum"
                  :pricing="pricing"
                  :delivery="delivery"
                  :sellerData="sellerData"
                  @on-login="handleLogin"
                  @get-base="getBase"
                ></negotiableGoods>
                <!-- 竞价销售 -->
                <auctionProduct
                  v-if="pricing.salesWay === '竞价销售'"
                  :info="goodInfo"
                  :gradeNum="gradeNum"
                  :pricing="pricing"
                  :delivery="delivery"
                  @on-login="handleLogin"
                  @get-base="getBase"
                ></auctionProduct>
              </div>
              <!-- 预售商品 -->
              <presaleGoods
                v-if="goodInfo.productStatus == '预定产品'"
                :info="goodInfo"
                :pricing="pricing"
                :gradeNum="gradeNum"
                :delivery="delivery"
                @get-base="getBase"
                @on-login="handleLogin"
                @on-buy="handleBuyNow"
                @on-add="handleAddCat"
              ></presaleGoods>
            </Col>
          </Row>
        </Col>
        <!-- <Col span="4" class="pl20">
          <relatedProduct ref="relatedProduct"></relatedProduct>
        </Col>-->
      </Row>
      <div>
        <Row>
          <Col span="5">
            <store-info :sellerData="sellerData" class="mb20" @on-login="handleLogin"></store-info>
            <relatedProduct ref="relatedProduct" :id="id"></relatedProduct>
          </Col>
          <Col span="19">
            <div style="border: 1px solid #F4F4F4;" class="ml20">
              <Affix :offset-top="98">
                <Menu
                  mode="horizontal"
                  theme="light"
                  active-name="detail"
                  @on-select="handleMenuChange"
                >
                  <MenuItem name="detail">产品信息</MenuItem>
                  <MenuItem name="goodsInfo">通用商品信息</MenuItem>
                  <MenuItem name="trace">追溯与防伪</MenuItem>
                  <MenuItem name="sellInfo">售后保障</MenuItem>
                  <!-- <MenuItem name="record">月成交记录</MenuItem> -->
                  <MenuItem name="promise" v-if="integrity != '否'">诚信承诺</MenuItem>
                  <MenuItem name="grade">
                    商品评价
                    <span class="t-orange">{{gradeNum}}</span>
                  </MenuItem>
                </Menu>
              </Affix>
              <div class="pd20">
                <goods-detail v-if="name === 'detail'" ref="detail"></goods-detail>
                <goods-info v-if="name === 'goodsInfo'"></goods-info>
                <sell-info v-if="name === 'sellInfo'"></sell-info>
                <grade v-if="name === 'grade'"></grade>
                <record v-if="name === 'record'" unit></record>
                <trace v-if="name === 'trace'"></trace>
                <promise v-if="name === 'promise' && integrity != '否'"></promise>
              </div>
            </div>
          </Col>
        </Row>
      </div>
    </div>
    <productionBaseDetail ref="base"></productionBaseDetail>
    <cart-btn></cart-btn>
  </div>
</template>

<script>
import top from "~src/top";
import headNav from "../../51index/components/nav";
import storeInfo from "./components/store-info";
import cartBtn from "../components/cart-btn";
import productView from "./components/view";
import pricingGoods from "./components/pricingGoods";
import negotiableGoods from "./components/negotiableGoods";
import auctionProduct from "./components/auctionProduct";
import presaleGoods from "./components/presaleGoods";
import productionBaseDetail from './components/productionBaseDetail'

import goodsDetail from "./components/goods-detail";
import goodsInfo from "./components/goods-info";
import sellInfo from "./components/sell-info";
import grade from "./components/grade";
import record from "./components/record";
import trace from "./components/trace";
import promise from "./components/promise";
import relatedProduct from "./components/relatedProduct";
export default {
  components: {
    top,
    headNav,
    storeInfo,
    cartBtn,
    productView,
    pricingGoods,
    negotiableGoods,
    auctionProduct,
    presaleGoods,
    goodsDetail,
    goodsInfo,
    sellInfo,
    grade,
    record,
    trace,
    promise,
    relatedProduct,
    productionBaseDetail
  },
  data() {
    return {
      id: "",
      account: "",
      info: {
        barcode: {},
        origin: {},
        productLocation: {},
        product: {},
        quality: {},
        safety: {}
      },
      name: "detail",
      sellerData: {
        account: "",
        qq: "",
        phone: "",
        email: "",
        name: ""
      },
      gradeNum: '0',
      notarizationCertificate: [], // 图片
      pricing: {}, // 商品售价 收获方式等信息
      delivery: [], // 商品配送方式
      goodInfo: {}, // 商品名称等信息
      integrity: '否', // 诚信承诺
    };
  },
  created() {
    this.id = this.$route.query.id;
    this.account = this.$route.query.account;
    this.handlInit();
    this.getgoodInfo();
    this.handleSellerData();
    this.handleGetInit()
    // 商品详情 第5步 诚信承诺 判断是否显示
    this.$api.post('/shop/commodityDetail/findCommodityDetailFive', {
      pushShopCommodityId: this.id
    }).then(response => {
      if (response.code === 200 && response.data.info) {
        this.integrity = response.data.info.integrity
        // let data1 = response.data.commodity[0]
        // 商品图片展示
      }
    })
  },
  methods: {
    getBase () {
      this.$refs['base'].init(this.account, this.goodInfo.productionBase)
    },
    // 加入购物车
    handleAddCat(count) {
      let startNum = Number(count);
      let productVbep = Number(this.goodInfo.productAvailability);
      if (!this.account) {
        this.$Message.error("请登录后再加入购物车");
        this.handleLogin();
        return;
      } else if (this.account == this.$user.loginAccount) {
        this.$Message.info("不能购买自己发布的商品");
        return;
      } else if (startNum > productVbep) {
        this.$Message.error("购买量超出库存");
        return;
      } else {
        let list = {
          num: startNum,
          commodityId: this.id,
          account: this.$user.loginAccount,
          sellerAccount: this.account
        };
        this.$api.post("/shop/shopCard/add", list).then(response => {
          if (response.code === 200) {
            this.$Message.success("添加成功");
          } else if (response.code === 300) {
            this.$Message.error(response.msg);
          } else {
            this.$Message.error("添加失败");
          }
        });
      }
    },
    // 立即购买
    handleBuyNow(count) {
      let startNum = Number(count);
      let productVbep = Number(this.goodInfo.productAvailability);
      if (!this.account) {
        this.$Message.error("请登录后再加入购物车");
        this.handleLogin();
        return;
      } else if (this.account == this.$user.loginAccount) {
        this.$Message.info("不能购买自己发布的商品");
        return;
      } else if (startNum > productVbep) {
        this.$Message.error("购买量超出库存");
        return;
      } else {
        let list = {
          num: startNum,
          commodityId: this.id,
          account: this.$user.loginAccount,
          sellerAccount: this.account
        };
        if (this.goodInfo.productStatus == "预定产品") {
          list.shopType = 1;
        }
        localStorage.setItem("goodsInfo", JSON.stringify(list));
        this.$router.push({ path: "/order-check" });
        // this.$api.post('/shop/shopCard/purchase', list).then(response => {
        //   if (response.code === 200) {
        //     this.$Message.success('添加成功')
        //   } else if (response.code === 300) {
        //     this.$Message.error(response.msg)
        //   } else {
        //     this.$Message.error('添加失败')
        //   }
        // })
      }
    },
    // 查询卖家信息
    handleSellerData() {
      this.$api
        .post("/member/fishing/findAvatar", { account: this.account })
        .then(response => {
          if (response.code == 200) {
            this.sellerData = response.data;
          }
        });
    },
    // 查询
    handlInit() {
      // 商品详情 第一步
      // this.$api.post('/shop/commodityDetail/findCommodityDetailFirst', {
      //   pushShopCommodityId: this.id
      // }).then(response => {
      //   if (response.code === 200) {
      //     let data1 = response.data.commodity[0]
      //     // 商品图片展示
      //   }
      // })
      // 商品详情 第一步
      this.$api
        .post("/shop/commodityDetail/findCommodityDetailTwo", {
          pushShopCommodityId: this.id
        })
        .then(response => {
          if (response.code === 200) {
            this.info.barcode = response.data.barcode[0];
            this.info.origin = response.data.origin[0];
            this.info.productLocation = response.data.productLocation[0];
            this.info.product = response.data.product[0];
            this.info.quality = response.data.quality[0];
            this.info.safety = response.data.safety[0];
            // let data2 = response.data.commodity[0]
            // 商品图片展示
            // this.$refs['view'].getData(data1.notarizationCertificate)
          }
        });
    },
    // 查询 上部分商品名称 售价等信息
    getgoodInfo() {
      this.$api
        .post("/shop/commodityDetail/findCommodityDetailInfo", {
          pushShopCommodityId: this.id,
          account: this.$user ? this.$user.loginAccount : ''
        })
        .then(res => {
          if (res.code === 200) {
            this.notarizationCertificate = res.data.notarizationCertificate;
            let pricing = res.data.pricing;
            this.pricing = pricing;
            let goodInfo = res.data;
            goodInfo.productSalesVolume = parseFloat(
              goodInfo.productSalesVolume
            );
            goodInfo.maximumSingleShipment = parseFloat(
              goodInfo.maximumSingleShipment
            );
            goodInfo.productAvailability = parseFloat(
              goodInfo.productAvailability
            );
            if (goodInfo.maximumSingleShipment > goodInfo.productAvailability) {
              // 最大可售量大于 库存 最大可售量 = 剩余库存
              goodInfo.maximumSingleShipment = goodInfo.productAvailability;
            }
            this.goodInfo = goodInfo;
            console.log("this.pricing", this.pricing);
            this.delivery = res.data.delivery;
            let images = [];
            this.notarizationCertificate.map(element => {
              images.push({ url: element });
            });
            this.$refs["view"].getData(images);
            // this.$refs["relatedProduct"].init([goodInfo, goodInfo, goodInfo]);
          }
        });
    },
    // tab 点击切换
    handleMenuChange(name) {
      this.name = name;
    },
    // 登录
    handleLogin() {
      this.$refs["top"].loginuser();
    },
    // 获取评论总数
    handleGetInit() {
      this.$api
        .post("/portal/shopCommdoity/findCommentNum", {
          commodityId: this.id,
          account: this.account
        })
        .then(response => {
          if (response.code == 200) {
            this.gradeNum = response.data.commentNum;
          }
        });
    }
  }
};
</script>

<style lang="" scoped>
</style>