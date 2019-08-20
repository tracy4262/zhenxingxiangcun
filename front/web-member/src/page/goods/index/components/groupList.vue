<template>
  <div>
    <!-- 团购 -->

    <Row type="flex" justify="center" class="mt20">
      <div class="layouts" v-show="isShow">
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
        <Breadcrumb v-show="isShow">
          <BreadcrumbItem to="/goods/index">产品首页</BreadcrumbItem>
          <BreadcrumbItem to="/goods/showGoods">团购好货</BreadcrumbItem>
        </Breadcrumb>
        <filter-btn ref="btn" v-if="isShow"></filter-btn>
        <ul class="goods-list" v-if="list!=null">
          <!-- 展示在首页的部分 -->
          <li
            v-for="(item,index) in list"
            :key="index"
            @click="handleDetail(item)"
            v-if="index < 5 && isShow == false"
          >
            <div v-if="item.groupBuyingEndTimeStr.length != 0 && new Date(item.groupBuyingEndTimeStr).getTime() > new Date().getTime()" class="clocker">
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
                  团购价：
                  <span style="text-decoration:line-through;">￥{{item.originalPrice}}</span>
                </p>
                <p class="price">￥{{item.groupBuyingPrice}}</p>
                <span class="buyCount">购买人数{{item.salesNumber}}</span>
              </Col>
              <Col :span="8">
                <div class="buyButton">立即抢购</div>
              </Col>
            </Row>
          </li>
          <!-- 查看全部 -->
          <div v-if="isShow">
            <li v-for="item in list" @click="handleDetail(item)">
              <div
                v-if="item.groupBuyingEndTimeStr.length != 0 && new Date(item.groupBuyingEndTimeStr).getTime() > new Date().getTime()"
                class="clocker"
              >
                距离结束还剩：
                <vui-clocker :time="item.groupBuyingEndTimeStr" format="%D天 %H小时 %M分 %S秒"/>
              </div>
              <img :src="item.notarizationCertificate[0]" class="imgSet">
              <Row type="flex" justify="space-between" class="mt10 ml15">
                <Col>
                  <span style="font-size:16px;color:red">￥{{item.groupBuyingPrice}}</span>
                  <span class="baoyou" v-if="item.paymentMethod == '卖方承担'">包邮</span>
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
            </li>
          </div>
        </ul>
      </div>
    </Row>

    <div class="mt30 mb50 tc" v-if="isShow&&list!=null">
      <Page :total="total" :page-size="pageSize" :current="pageNum" @on-change="pageChange"></Page>
    </div>
  </div>
</template>
<script>
import vuiClocker from "~components/clocker/clocker";
import filterBtn from "./btn-bar";
export default {
  props: {
    // list: {},
    type: {},
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
      keyword: "",
      pageNum: 1,
      pageSize: 10,
      total: 0
    };
  },
  methods: {
    // 到详情页
    handleDetail(item) {
      this.$router.push(
        `/goods/newDetail?id=${item.id}&account=${item.account}`
      );
    },
    pageChange(e) {
      this.pageNum = e;
      this.searchBtn();
    },
    searchBtn() {
      this.$api
        .post("/shop/pushShopCommodity/findGroupBuying", {
          keyword: this.keyword,
          num: this.pageNum,
          size: this.pageSize
        })
        .then(res => {
          if (res.code === 200) {
            this.list = res.data.list;
            this.total = res.data.total;
          }
        });
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
    border: 1px solid rgba(58, 58, 58, 0.62);
    transition: box-shadow 0.2s cubic-bezier(0.47, 0, 0.745, 0.715);
    &:nth-child(5n) {
      margin-right: 0;
    }
    &:hover {
      box-shadow: 0 0 0 2px #00c587;
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
.baoyou {
  background: #b1b1b1;
  padding: 1px;
}
.location {
  overflow: hidden;
  width: 200px;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
