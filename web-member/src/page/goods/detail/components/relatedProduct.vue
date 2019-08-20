<template>
  <!-- <div class="related-services pt10" v-if="data.length">
    <Divider>相关推荐</Divider>
    <div class="related-services-lists" v-if="data.length">
      <div class="related-services-list" ref="list">
        <div v-for="(item, index) in data" class="pt15" style="height: 225px;">
          <img v-if="item.notarizationCertificate && item.notarizationCertificate[0]" :src="item.notarizationCertificate[0]" alt="" height="160px" width="100%">
          <img v-else src="../../../../../static/img/goods-list-no-picture1.png" alt="" height="160px" width="100%">
          <p class="t-grey ell tc mt10" :title="item.productName">{{item.productName}}</p>
          <p v-if="item.contact" class="ell tc mt5" :title="item.contact[0] ? item.contact[0].detailAddress : ''">
            {{item.contact[0] ? item.contact[0].detailAddress : ''}}
          </p>
        </div>
      </div>
    </div>
    <div class="tc pt5" v-if="data.length">
      <Button type="text" @click="hadleUp"><Icon type="ios-arrow-up" size="32"/></Button>
      <Button type="text" @click="hadleDown"> <Icon type="ios-arrow-down" size="32"/></Button>
    </div>
    <div v-if="!data.length" class="tc pt50">
      <p>暂无相关服务</p>
    </div>
  </div>-->
  <div
    style="border-left: 1px solid #f2f2f2; border-right: 1px solid #f2f2f2; border-top: 1px solid #f2f2f2;"
  >
    <div class="h5 tc pt10 pb10" style="border-bottom: 1px solid #f2f2f2;">商品推荐</div>
    <div v-for="(item, index) in data" class="pt15 pb15" style="border-bottom: 1px solid #f2f2f2; cursor: pointer;" @click="goDetail(item)">
      <img
        v-if="item.notarizationCertificate && item.notarizationCertificate[0]"
        :src="item.notarizationCertificate[0]"
        alt
        height="180px"
        width="100%"
      >
      <img
        v-else
        src="../../../../../static/img/goods-list-no-picture1.png"
        alt
        height="180px"
        width="100%"
      >
      <p class="t-grey ell tc mt10" :title="item.productName">{{item.productName}}</p>
      <div class="tc" v-if="item.pricing.salesWay === '定价销售'">
        <template v-if="item.pricing.discountPrice && item.isDiscount">
          <span class="t-red h6">
            ￥
            <b class="h5">{{item.pricing.discountPrice}}</b>
          </span>
          <span class="t-grey ml10">
            <span style="text-decoration: line-through;">￥{{item.pricing.currentPrice}}</span>
          </span>
        </template>
        <template v-else>
          <span class="t-red h6">
            ￥
            <b class="h5">{{item.pricing.currentPrice}}</b>
          </span>
        </template>
      </div>
      <div class="tc" v-if="item.pricing.salesWay === '团购销售'">
        <template v-if="item.pricing.groupBuyingPrice && item.isDiscount">
          <span class="t-red h6">
            ￥
            <b class="h5">{{item.pricing.groupBuyingPrice}}</b>
          </span>
          <span class="t-grey ml10">
            <span style="text-decoration: line-through;">￥{{item.pricing.originalPrice}}</span>
          </span>
        </template>
        <template v-else>
          <span class="t-red h6">
            ￥
            <b class="h5">{{item.pricing.groupBuyingPrice}}</b>
          </span>
        </template>
      </div>
      <div class="tc" v-if="item.productStatus == '预定产品'">
        <span class="t-red h6">
          ￥
          <b class="h5">{{item.pricing.orderPrice}}</b>
        </span>
      </div>
      <div class="tc" v-if="item.pricing.salesWay === '面议'">
        <span class="t-red h6">
          <b class="h5">面议</b>
        </span>
      </div>
      <div class="tc" v-if="item.pricing.salesWay === '竞价销售'">
        <span class="t-red h6">
          ￥
          <b class="h5">{{item.pricing.startPrice}}</b>
        </span>
      </div>
    </div>
    <div v-if="!data.length" class="tc pd20" style="border-bottom: 1px solid #f2f2f2;">
      <p>暂无相关商品</p>
    </div>
  </div>
</template>
<script>
export default {
  props: ["id"],
  data() {
    return {
      data: [],
      end: 0,
      active: 0
    };
  },
  created() {
    this.getData()
    // this.end = this.data.length ? this.data.length - 2 : this.data.length;
  },
  methods: {
    goDetail (item) {
      console.log('888888')
      window.open(`${window.location.origin}/goods/newDetail?id=${item.id}&account=${item.account}`)
      // this.$router.push(`/goods/newDetail?id=${item.id}&account=${item.account}`)
    },
    getData() {
      this.$api
        .post("/shop/commodityDetail/findRelatedCommodity", {
          pushShopCommodityId: this.id
        })
        .then(res => {
          console.log("111")
          this.data = res.data
        });
    },
    init(data) {
      data.forEach(element => {
        if (element.pricing.salesWay === "团购销售") {
          let discountStartTime = new Date(
            element.pricing.groupBuyingStartTime
          );
          let discountEndTime = new Date(element.pricing.groupBuyingEndTime);
          let currentTime = new Date();
          if (
            discountStartTime < currentTime &&
            discountEndTime > currentTime
          ) {
            element.isDiscount = true;
          } else {
            element.isDiscount = false;
          }
        } else if (
          element.pricing.salesWay === "定价销售" &&
          element.pricing.discountPeriod &&
          element.pricing.discountPeriod.length
        ) {
          let times = element.pricing.discountPeriod;
          let discountStartTime = new Date(times[0]);
          let discountEndTime = new Date(times[1]);
          let currentTime = new Date();
          currentTime;
          if (
            discountStartTime < currentTime &&
            discountEndTime > currentTime
          ) {
            element.isDiscount = true;
          } else {
            element.isDiscount = false;
          }
        } else if (
          element.productStatus == "预定产品" &&
          element.pricing.advancePaymentTime &&
          element.pricing.advancePaymentTime.length
        ) {
          let times = element.pricing.advancePaymentTime;
          let discountStartTime = new Date(times[0]);
          let discountEndTime = new Date(times[1]);
          let currentTime = new Date();
          if (
            discountStartTime < currentTime &&
            discountEndTime > currentTime
          ) {
            element.isDiscount = true;
          } else {
            element.isDiscount = false;
          }
        } else if (element.pricing.salesWay === "面议") {
          element.isDiscount = true;
        } else if (element.pricing.salesWay === "竞价销售") {
          let discountStartTime = new Date(element.pricing.biddingStartTime);
          let discountEndTime = new Date(element.pricing.biddingEndTime);
          let currentTime = new Date();
          if (
            discountStartTime < currentTime &&
            discountEndTime > currentTime
          ) {
            element.isDiscount = true;
          } else {
            element.isDiscount = false;
          }
        }
      });
      this.data = data;
      // this.end = this.data.length ? this.data.length - 2 : this.data.length;
    },
    // hadleUp() {
    //   if (this.active > 0) {
    //     this.active--;
    //     this.active--;
    //     if (this.active < 0) {
    //       this.active = 0;
    //       this.$refs.list.style.top = `-${this.active * 225}px`;
    //     } else {
    //       this.$refs.list.style.top = `-${this.active * 225}px`;
    //     }
    //   }
    // },
    // hadleDown() {
    //   if (this.end > 0) {
    //     this.active++;
    //     this.active++;
    //     if (this.active < this.end) {
    //       this.$refs.list.style.top = `-${this.active * 225}px`;
    //     } else {
    //       this.active = this.end;
    //       this.$refs.list.style.top = `-${this.active * 225}px`;
    //     }
    //   }
    //   // 225px
    //   console.log(this.$refs.list.style.top);
    // }
  }
};
</script>
<style scoped>
.related-services .related-services-lists {
  height: 460px;
  overflow: hidden;
  position: relative;
}
.related-services .related-services-list {
  position: absolute;
  top: 0px;
  width: 100%;
  left: 0px;
}
/* .related-services p{
    line-height: 24px;
  } */
</style>
