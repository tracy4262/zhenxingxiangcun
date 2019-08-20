<template>
  <div class="new-good-detail">
    <p class="good-name"><span class="tag" v-if="info.isRetrospect === '是'">可追溯/可防伪</span>{{info.productName}}</p>
    <div class="mt15">
      <div class="clocker"  v-if="isDiscount">
        距离预售结束还剩：
        <vui-clocker :time="discountEndTime" @get-time="getTimes" format="%D天 %H小时 %M分 %S秒"/>
      </div>
      <div class="content pd10">
        <div class="vui-flex price">
          <div>
            <template>
             <span class="t-red h6" >预售价： ￥<b class="h1">{{pricing.orderPrice}}</b></span>
             <span class="t-red h6 pl5" >定金金额： ￥<b>{{pricing.allDepositPrice}}</b></span>
            </template>
            <p class="pb10 h5">
              支付定金方式: {{pricing.deposit}}
              <span v-if="pricing.deposit == '定额支付'">¥{{pricing.depositAmount}}</span>
              <span v-if="pricing.deposit == '按比例支付'">{{pricing.depositPercent}}%</span>
            </p>
          </div>
          <div class="vui-flex-item pl10 pt20">
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
              <p class="ell p" v-if="info.productionBase" >生产基地：<span class="a t-blue" @click="handleProductionBase">{{info.productionBaseName}}</span></p>
            </Col>
          </Row>
        </div>
      </div>
      <!-- 配送 -->
      <div class="delivery">
        <p class="pt10 pb10">
          <span class="lines pl10" v-for="(item, index) in delivery">
            <span class="line" v-if="index > 0 && index < delivery.length"></span>
            配送：{{item.deliveryMethods}} {{item.transportMethods}} {{item.paymentMethod}}
          </span>
        </p>
      </div>
      <div class="pt15">
        <Row type="flex" align="middle">
          <Col span="12" class="pl10">
            <div>
            数量
            <InputNumber @on-change="numChange" :step="1" v-model="buy.count" :min="info.productSalesVolume" :max="info.maximumSingleShipment"></InputNumber>
            <span class="t-grey">{{info.productAvailabilityUnits}}（{{info.productSalesVolume}}{{info.productAvailabilityUnits}}起售）</span>
            </div>
            <div class="pt20 pb20">
              <!-- 已超出 购买时间不在预售时间范围内 -->
              <Button class="mr15" size="large" :disabled="!isDiscount" @click="onBuy">支付定金</Button>
              <!-- <Button type="primary" size="large" :disabled="!isDiscount" @click="onAdd">加入购物车</Button> -->
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
import {numMulti} from '~utils/utils'
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
      this.numChange()
    }
  },
  watch: {
    pricing: {
      handler: function (newVal, oldVal) {
        this.getTime()
      }
    },
    'info.productSalesVolume': {
      handler(newName, oldName) {
        this.buy.count = newName
        this.numChange()
      },
      immediate: true
    },
    info: {
      handler: function (newVal, oldVal) {
        this.buy.count = this.info.productSalesVolume
      },
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
    numChange () {
      this.pricing.allDepositPrice = parseFloat((numMulti(this.pricing.depositPrice ? this.pricing.depositPrice : 0, this.buy.count ? this.buy.count : 1)).toFixed(2))
    },
    getTime () {
      // advancePaymentTime 预付款支付时间段 超出此时间 或者还没到时间，不能购买
      if (this.pricing.advancePaymentTime && this.pricing.advancePaymentTime.length) {
        let times = this.pricing.advancePaymentTime
        let discountStartTime = new Date(times[0])
        let discountEndTime = new Date(times[1])
        // depositPrice
        if (this.pricing.deposit === '免付定金') {
          this.pricing.depositPrice = 0
          this.pricing.allDepositPrice = 0
        } else if (this.pricing.deposit === '定额支付') {
          this.pricing.depositPrice = this.pricing.depositAmount
          this.pricing.allDepositPrice = this.pricing.depositAmount
          this.numChange()
        } else if (this.pricing.deposit === '按比例支付') {
          let depositPrice = parseFloat((numMulti(this.pricing.depositPercent, this.pricing.orderPrice)).toFixed(2))
          this.pricing.depositPrice = parseFloat((numMulti(depositPrice, 0.01)).toFixed(2))
          this.numChange()
        }
        
        this.discountEndTime = this.moment(new Date(times[1])).format('YYYY-MM-DD HH:MM:SS')
        let currentTime = new Date()
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