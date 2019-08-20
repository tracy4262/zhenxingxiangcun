<template>
  <div class="new-good-detail">
    <p class="good-name"><span class="tag" v-if="info.isRetrospect === '是'">可追溯/可防伪</span>{{info.productName}}</p>
    <div class="mt15">
      <div class="clocker">
        价格面议类商品
      </div>
      <div class="content pd10">
        <Row>
          <Col span="14">
            <div class="pt10">
              <div class="vui-flex">
                <div>
                  <p class="pb5">库存：{{info.productAvailability}}{{info.productAvailabilityUnits}}</p>
                  <p>已售：{{info.salesNumber}}{{info.productAvailabilityUnits}}</p>
                </div>
                <div class="vui-flex-item evaluation">
                  <span class="line"></span>
                  <p>累计评价：{{gradeNum}}</p>
                  <Rate disabled allow-half v-model="info.rate"></Rate>
                </div>
              </div>
            </div>
          </Col>
          <Col  span="10" class="pl10 location">
            <p class="ell p" :title="info.productOrigin + '/' + info.addrDetail">产品产地：{{ info.productOrigin + '/' + info.addrDetail }}</p>
            <p class="ell p" :title="info.productLocation + '/' + info.productAddrDetail">产品所在地：{{ info.productLocation + '/' + info.productAddrDetail }}</p>
                        
            <p class="ell p" v-if="info.productionBase" >生产基地：<span class="a t-blue" @click="handleProductionBase">{{info.productionBaseName}}</span></p>
          </Col>
        </Row>
      </div>
      <!-- 配送 -->
      <div class="delivery pl20">
        <p class="pt10 pb10">
          <span class="lines" v-for="(item, index) in delivery">
            <span class="line" v-if="index > 0 && index < delivery.length"></span>
            配送：{{item.deliveryMethods}} {{item.transportMethods}} {{item.paymentMethod}}
          </span>
        </p>
      </div>
      <div class="pt15">
        <Row type="flex" align="middle">
          <Col span="12" class="pl10">
            <!-- <div>
            数量
            <InputNumber :step="1" v-model="buy.count" :min="info.productSalesVolume" :max="info.maximumSingleShipment"></InputNumber>
            <span class="t-grey">{{info.productAvailabilityUnits}}（{{info.productSalesVolume}}{{info.productAvailabilityUnits}}起售）</span>
            </div> -->
            <div class="pt20 pb20">
              <Button class="mr15" size="large" @click="webimchat">联系卖家</Button>
              <!-- <Button type="primary" size="large">加入购物车</Button> -->
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
    sellerData:{ // 
      type: Object
    },
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
    if (this.info.productSalesVolume) {
      this.buy.count = this.info.productSalesVolume
    }
  },
  watch: {
    info: {
      handler: function (newVal, oldVal) {
        this.buy.count = this.info.productSalesVolume
      }
    }
  },
  methods: {
    handleProductionBase () {
      this.$emit('get-base')
    },
    // 聊天
    webimchat (userId, name, avatar) {
      if (!this.$user) {
        this.$Message.error('请登录后再发起聊天')
        this.$emit('on-login')
        return
      } 
      layui.layim.chat({
        id: this.sellerData.userId,
        name: this.sellerData.name,
        avatar: this.sellerData.avatar,
        type: 'friend'
      })
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
      padding-left: 10px;
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