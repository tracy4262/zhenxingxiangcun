<template>
  <div class="new-good-detail">
    <p class="good-name"><span class="tag" v-if="info.isRetrospect === '是'">可追溯/可防伪</span>{{info.productName}}</p>
    <div class="mt15">
      <div class="clocker" v-if="isDiscount">
        <span v-if="pricing.salesWay === '定价销售'">距离折扣结束还剩：</span>
        <span v-if="pricing.salesWay === '团购销售'">距离团购结束还剩：</span>
        <vui-clocker v-if="discountEndTime" :time="discountEndTime" @get-time="getTimes" format="%D天 %H小时 %M分 %S秒">
        </vui-clocker>
      </div>
      <div class="content pd10">
        <div class="vui-flex price">
          <div v-if="pricing.salesWay === '定价销售'">
            <template v-if="pricing.discountPrice && isDiscount">
             <span class="t-red h6" >折扣价： ￥<b class="h1">{{pricing.discountPrice}}</b></span>
             <span class="t-grey ml20">[<span style="text-decoration: line-through;">时价： ￥{{pricing.currentPrice}}</span>]</span>
            </template>
            <template v-else>
              <span class="t-red h6" >时价： ￥<b class="h1">{{pricing.currentPrice}}</b></span>
            </template>
          </div>
          <div v-if="pricing.salesWay === '团购销售'">
            <template v-if="pricing.groupBuyingPrice && isDiscount">
             <span class="t-red h6" >折扣价： ￥<b class="h1">{{pricing.groupBuyingPrice}}</b></span>
             <span class="t-grey ml20">[<span style="text-decoration: line-through;">时价： ￥{{pricing.originalPrice}}</span>]</span>
            </template>
            <template v-else>
              <span class="t-red h6" >时价： ￥<b class="h1">{{pricing.originalPrice}}</b></span>
            </template>
          </div>
          <div class="vui-flex-item pl10 pt10">
            <div class="vui-flex">
              <div class="vui-flex-item tr">
                <p class="pb5">库存：{{info.productAvailability}}{{info.productAvailabilityUnits}}</p>
                <p>已售：{{info.salesNumber}}{{info.productAvailabilityUnits}}</p>

              </div>
              <div class="evaluation">
                <span class="line"></span>
                <p>累计评价：{{gradeNum}}</p>
                <Rate disabled allow-half v-model="info.rate"></Rate>
              </div>
            </div>
          </div>
        </div>
        <div class="location pt5">
          <Row>
            <Col span="12">
              <p class="ell p" :title="info.productOrigin + '/' + info.addrDetail">产品产地：{{ info.productOrigin + '/' + info.addrDetail }}</p>
              <p class="ell p" :title="info.productLocation + '/' + info.productAddrDetail">产品所在地：{{ info.productLocation + '/' + info.productAddrDetail }}</p>
            </Col>
            <Col span="12" class="pl10">
              <p class="ell p" v-if="info.productionBase">生产基地：<span class="a t-blue" @click="handleProductionBase">{{info.productionBaseName}}</span></p>
            </Col>
          </Row>
        </div>
      </div>
      <!-- 配送 -->
      <div class="delivery">
        <p class="pt10 pb10">
          <span class="lines" v-for="(item, index) in delivery">
            <span class="line" v-if="index > 0 && index < delivery.length"></span>
            配送：{{item.deliveryMethods}} {{item.transportMethods}} {{item.paymentMethod}}
          </span>
        </p>
        <!-- <div class="vui-flex">
          <div class="pt10 pb10">
            配送：送货上门
          </div>
          <div class="vui-flex-item lines pt10 pb10">
            <span class="line"></span>
            快递：免运费
          </div>
        </div> -->
      </div>
      <div class="pt15">
        <Row type="flex" align="middle">
          <Col span="12" class="pl10">
            <div>
            数量
            <!-- <InputNumber :max="info.commodityTypeInfos.maxSupplybecomes" :min="startNum" :step="1" v-model="info.commodityTypeInfos.startNum"></InputNumber> -->
            <InputNumber :step="1" v-model="buy.count" :min="info.productSalesVolume" :max="info.maximumSingleShipment"></InputNumber>
            <span class="t-grey">{{info.productAvailabilityUnits}}（{{info.productSalesVolume}}{{info.productAvailabilityUnits}}起售）</span>
            </div>
            <div class="pt20 pb20">
              <Button class="mr15" size="large" @click="onBuy">立即购买</Button>
              <Button type="primary" size="large" @click="onAdd">加入购物车</Button>
            </div>
            <!-- <p class="pt10"><Icon type="ios-construct-outline" size="18" class="mr5"/>售后保障</p> -->
          </Col>
          <!-- <Col span="12">
            <Row>
              <Col span="8">
                <div class="pl5 pr5">
                  <p>商品二维码</p>
                  <img src="../../../../img/baicai.png" alt="" width="100%">
                </div>
              </Col>
              <Col span="8">
                <div class="pl5 pr5">
                  <p>追溯码</p>
                  <img src="../../../../img/baicai.png" alt="" width="100%">
                </div>
              </Col>
              <Col span="8">
                <div class="pl5 pr5">
                  <p>防伪码</p>
                  <img src="../../../../img/baicai.png" alt="" width="100%">
                </div>
              </Col>
            </Row>
          </Col> -->
        </Row>
      </div>
    </div>
  </div>
</template>

<script>
import vuiClocker from '~components/clocker/clocker'
export default {
  components: {
    vuiClocker,
  },
  props:{
    info:{ // 商品名称等信息
      type: Object
    },
    pricing:{ // 商品售价 收获方式等信息
      type: Object
    },
    delivery:{ // 商品配送方式
      type: Array
    },
    gradeNum:{
      type:String
    }
  },
  data () {
    return {
      item: '2019-4-18 14:26:53',
      isDiscount: false,
      discountEndTime: '',
      buy: {
        count: 1
      }
    }
  },
  created () {
    this.getTime()
    if (this.info.productSalesVolume) {
      this.buy.count = this.info.productSalesVolume
    }
  },
  watch: {
    pricing: {
      handler: function (newVal, oldVal) {
        this.getTime()
      }
    },
    info: {
      handler: function (newVal, oldVal) {
        this.buy.count = this.info.productSalesVolume
      }
    }
  },
  methods: {
    getTimes (e) {
      if (e === '00天 00小时 00分 00秒'){
        this.isDiscount = false
      }
    },
    handleProductionBase () {
      this.$emit('get-base')
    },
    onBuy () {
      this.$emit('on-buy', this.buy.count)
    },
    onAdd () {
      this.$emit('on-add', this.buy.count)
    },
    getTime () {
      if (this.pricing.salesWay === '团购销售') {
        let discountStartTime = new Date(this.pricing.groupBuyingStartTime)
        let discountEndTime = new Date(this.pricing.groupBuyingEndTime)
        this.discountEndTime = this.moment(discountEndTime).format('YYYY-MM-DD HH:MM:SS')
        let currentTime = new Date()
        if (discountStartTime < currentTime && discountEndTime > currentTime) {
          this.isDiscount = true
        } else {
          this.isDiscount = false
        }
      } else if (this.pricing.salesWay === '定价销售' && this.pricing.discountPeriod && this.pricing.discountPeriod.length) {
        let times = this.pricing.discountPeriod
        let discountStartTime = new Date(times[0])
        let discountEndTime = new Date(times[1])
        this.discountEndTime = this.moment(new Date(times[1])).format('YYYY-MM-DD HH:MM:SS')
        let currentTime = new Date()
        currentTime
        if (discountStartTime < currentTime && discountEndTime > currentTime) {
          this.isDiscount = true
        } else {
          this.isDiscount = false
        }
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.new-good-detail{
  .good-name{
    font-size:20px;
    color:#666;
    .tag{
      font-size: 14px;
      color: #fff;
      background: #FF9900;
      display: inline-block;
      padding: 4px 8px;
      border-radius: 4px;
      margin-right: 10px;
    }
  }
  .clocker{
    padding: 10px;
    color: #ffffff;
    background: #999999;
  }
  .content{
    background: #f2f2f2;
    .price{
      border-bottom: 1px dashed #cecece;
      .evaluation{
        position: relative;
        padding-left: 15px;
        .line{
          position: absolute;
          left: 8px;
          display: inline-block;
          width: 1px;
          height: 40px;
          background: #999;
        }
      }
    }
    .location{
      .p{
        line-height: 26px;
        .a{
          cursor: pointer;
          text-decoration: underline ;
        }
      }
    }
  }
  .delivery{
    border-bottom: 1px dashed #cecece;
    .lines{
      position: relative;
      // padding-left: 40px;
      line-height: 36px;
      padding-left: 10px;
      display: inline-block;
      .line{
        position: absolute;
        left: 5px;
        top: 6px;
        width: 2px;
        height: 20px;
        background: #999;
      }
    }
  }
}
</style>