<template>
<div class="evaluation">
  <top :address="false" ref="top"></top>
  <head-nav :active="4"></head-nav>
  <div class="layouts">
    <Breadcrumb class="mt20">
      <BreadcrumbItem to="/goods/index">产品</BreadcrumbItem>
      <BreadcrumbItem>{{info.commodityTypeInfos.productName}}</BreadcrumbItem>
    </Breadcrumb>
    <Row class="mt20 mb20">
      <Col span="9">
        <product-view ref="view"></product-view>
      </Col>
      <Col span="15" class="pl20">
        <Row>
          <Col span="18">
            <p class="b" style="font-size:24px;color:#666;">{{info.commodityTypeInfos.productName}}</p>
            <!-- <p class="t-grey">{{data.describe}}</p> -->
          </Col>
          <Col span="6" class="tr">
            <Rate disabled v-model="info.commodityTypeInfos.star" allow-half></Rate>
            <p class="t-grey">已有{{info.commodityTypeInfos.commentSum}}人评价</p>
          </Col>
        </Row>
        <div class="mt10 mb20 pd15" style="background: #F6F3F9;">
          <Row type="flex" align="middle">
            <Col span="16">
              <div v-if="discountTime > currentTime">
                <span class="t-orange h6" >折扣价： ￥<b class="h1">{{info.commodityTypeInfos.discountPrice}}</b></span>
                <span class="t-grey ml20">时价： ￥<b style="text-decoration: line-through;">{{info.commodityTypeInfos.timePrice}}</b></span>
              </div>
              <div v-else>
                <span class="t-orange h6">时价： ￥<b class="h1">{{info.commodityTypeInfos.timePrice}}</b></span>
              </div>
              <div v-if="discountTime > currentTime" style="color:#666">折扣时限: <vui-clocker :time="time" format="%D天 %H小时 %M分 %S秒"/></div>
            </Col>
            <Col span="8" class="tr">
              <p style="color:#666">库存：{{info.commodityTypeInfos.productVbep}}{{info.commodityTypeInfos.unit}}</p>
              <p class="mt10" style="color:#666"><span class="t-green">{{info.commodityTypeInfos.number}} </span>{{info.commodityTypeInfos.unit}}已售</p>
            </Col>
          </Row>
        </div>
        <Form :label-width="100">
          <Row>
            <Col span="7">
              <FormItem label="配送方式：">
                {{info.commodityTypeInfos.deliveryMode}}
                <!-- <Select v-model="info.commodityTypeInfos.deliveryMode">
                  <Option value="送货上门">送货上门</Option>
                  <Option value="上门取货">上门取货</Option>
                </Select> -->
              </FormItem>
            </Col>
            <template v-if="info.commodityTypeInfos.deliveryMode === '送货上门'">
              <Col span="7" offset="1">
                <FormItem label="发货方式：">
                  <span style="color:#666">{{info.commodityTypeInfos.deliverMode}}</span>
                </FormItem>
              </Col>
              <Col span="7" offset="1">
                <FormItem label="运费：" v-if="info.commodityTypeInfos.freight">
                  <span style="color:#666">￥{{info.commodityTypeInfos.freight}}</span>
                </FormItem>
              </Col>
            </template>
          </Row>
          <FormItem label="购买须知：">
            <div style="color:#999; font-size:12px;line-height:22px;" class="pt5">
              <p>1. 预付款下单后，请在30分钟内付款，超时将自动关闭订单。</p>
              <p>2. 尾款的支付请至“我的订单”页面。</p>
              <p>3. 请在要求的尾款支付时间内支付尾款，因买家原因未如期支付尾款，超时将自动关闭订单，预付款不予退还，请务必注意尾款  支付时间并及时支付尾款。</p>
              <div v-if="showMore">
                <p>4. 发货时间请以预售商品详情页面中的“发货时间”描述为准。</p>
                <p>5. 预付款支付后、尾款支付前，因买家自身原因要求撤销订单并返还预付款的，双方可协商处置，但卖家有权拒绝。非因买家责任，导致订单丧失继续履行条件的，则卖家应返还预付款。</p>
                <p>6. 买家全款支付后，若因卖家违约，造成交易无法履行或延迟发货且订单状态为“交易关闭”，则买家可投诉卖家，冻结交易款。</p>
                <div class="tc">
                   <Button type="text"   @click="handleMore"><Icon type="ios-arrow-up" size="16" style="vertical-align: top;"></Icon>收起</Button>
                  <!-- <Icon type="chevron-up"></Icon> -->
                </div>
              </div>
              <div v-else class="tc">
                <Button type="text" @click="handleMore"><Icon type="ios-arrow-down" size="16" style="vertical-align: top;"></Icon>展开</Button>
                <!-- <Icon type="chevron-down" @click="handleMore"></Icon> -->
              </div>
            </div>
          </FormItem>
          <FormItem label="数量：">
            <InputNumber :max="info.commodityTypeInfos.maxSupplybecomes" :min="startNum" :step="1" v-model="info.commodityTypeInfos.startNum"></InputNumber>
            <span class="t-grey">{{startNum}}{{info.commodityTypeInfos.unit}}起售</span>
          </FormItem>

          <Row>
            <Col span="5" class="pr10">
              <Button type="default" size="large" long @click="handleBuyNow">立即购买</Button>
              <!-- <Button type="default" size="large" long>立即购买</Button> -->
            </Col>
            <Col span="5" class="pl10">
              <Button type="primary" size="large" long @click="handleAddCat"><Icon type="ios-cart"></Icon> 加入购物车</Button>
            </Col>
          </Row>
        </Form>
      </Col>
    </Row>

    <Row class="mt30 mb30">
      <Col span="5">
        <store-info :sellerData="sellerData" class="mb20" @on-login="handleLogin"></store-info>
        <!-- <recommend></recommend> -->
      </Col>
      <Col span="19">
        <div style="border: 1px solid #F4F4F4;" class="ml20">
          <Affix :offset-top="74">
            <Menu mode="horizontal" theme="light" active-name="detail" @on-select="handleMenuChange">
              <MenuItem name="detail">商品详情</MenuItem>
              <MenuItem name="goodsInfo">产品信息</MenuItem>
              <MenuItem name="sellInfo">销售信息</MenuItem>
              <MenuItem name="grade">累计评价 <span class="t-orange">{{gradeNum}}</span></MenuItem>
              <MenuItem name="record">月成交记录</MenuItem>
              <MenuItem name="trace">追溯与防伪</MenuItem>
              <MenuItem name="promise">诚信承诺</MenuItem>
            </Menu>
          </Affix>
          <div class="pd20">
            <goods-detail v-if="name === 'detail'" ref="detail"></goods-detail>
            <goods-info v-if="name === 'goodsInfo'"></goods-info>
            <sell-info v-if="name === 'sellInfo'"></sell-info>
            <grade v-if="name === 'grade'"></grade>
            <record v-if="name === 'record'" :unit="info.commodityTypeInfos.unit"></record>
            <trace v-if="name === 'trace'"></trace>
            <promise v-if="name === 'promise'"></promise>
          </div>
        </div>
      </Col>
    </Row>

  </div>
  <cart-btn></cart-btn>
</div>
</template>

<script>
import top from '~src/top'
import headNav from '../../pro/components/headNav'
import vuiClocker from '~components/clocker/clocker'
import storeInfo from './components/store-info'
import recommend from './components/recommend'
import productView from './components/view'
import goodsDetail from './components/goods-detail'
import goodsInfo from './components/goods-info'
import sellInfo from './components/sell-info'
import grade from './components/grade'
import record from './components/record'
import trace from './components/trace'
import promise from './components/promise'
import cartBtn from '../components/cart-btn'
export default {
  components: {
    top,
    headNav,
    vuiClocker,
    storeInfo,
    recommend,
    productView,
    goodsDetail,
    goodsInfo,
    sellInfo,
    grade,
    record,
    trace,
    promise,
    cartBtn
  },
  data () {
    return {
      name: 'detail',
      commodityId: '',
      sellerAccount: '',
      info: {
        commoditySupplyInfo: [], // 产地信息
        commodityTypeInfo: [], // 商品分类 
        commodityTypeInfos: {}, // 商品内容
        commodityAbstract: [] // 商品简介
      },
      startNum: 0,
      sellerData: {
        account: '',
        qq: '',
        phone: '',
        email: '',
        name: ''
      },
      gradeNum: 0,
      time: '2018-06-21',
      discountTime: '',
      currentTime: '',
      loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
      account: '',
      // 查看更多
      showMore: false
    }
  },
  created () {
    this.commodityId = this.$route.query.id
    this.sellerAccount = this.$route.query.account
    if (this.loginUser) {
      this.account = this.loginUser.loginAccount
    }    
    this.handlegetDetail()
    this.handleSellerData()
    this.handleGetInit()
  },
  methods: {
    handleMore () {
      this.showMore = !this.showMore
    },
    handleLogin (){
      this.$refs['top'].loginuser()
    },
    // 加入购物车
    handleAddCat () {
      let startNum = Number(this.info.commodityTypeInfos.startNum)
      let productVbep = Number(this.info.commodityTypeInfos.productVbep)
      if (!this.account) {
        this.$Message.error('请登录后再加入购物车')
        this.handleLogin()
        return
      } else if (this.account == this.sellerAccount) {
        this.$Message.info('不能购买自己发布的商品')
        return
      }else if (startNum > productVbep) {
        this.$Message.error('购买量超出库存')
        return
      } else {
        let list = {num: this.info.commodityTypeInfos.startNum, commodityTypeInfoId: this.commodityId}
        this.$api.post('/nswy-portal-service/shop/card/add', {account: this.account, entity: list}).then(response => {
          if (response.code === 200) {
            this.$Message.success('添加成功')
          } else {
            this.$Message.error('添加失败')
          }
        })
      }
    },
    // 立即购买
    handleBuyNow () {      
      let startNum = Number(this.info.commodityTypeInfos.startNum)
      let productVbep = Number(this.info.commodityTypeInfos.productVbep)
       if (!this.account) {
        this.$Message.error('请登录后再购买')
        this.handleLogin()
        return
      } else if (this.account == this.sellerAccount) {
        this.$Message.info('不能购买自己发布的商品')
        return
      }else if (startNum > productVbep) {
        this.$Message.error('购买量超出库存')
        return
      } else {
        this.$api.post('/nswy-portal-service/shop/card/product', {account: this.account, productId: this.commodityId, productNum: this.info.commodityTypeInfos.startNum}).then(response => {
          if (response.code === 200) {
            let account = this.account
            let list = {} 
            list[account] = [response.data]
            localStorage.setItem('goodsList', JSON.stringify(list))
            // this.$store.commit('SaveGoodsList', list)
            this.$router.push('/order-check')
          } else {
            this.$Message.error('添加失败')
          }
        })
      }
    },
    // 获取评论数量
    handleGetInit () {
      this.$api.post('/portal/shopCommdoity/findCommentNum', {commodityId: this.commodityId, account: this.sellerAccount}).then(response => {
        if (response.code == 200) {
          this.gradeNum = response.data.commentNum
        }
      })
    },
    // 查询卖家信息
    handleSellerData () {
      this.$api.post('/portal/shopCommdoity/findLoginUser',{account: this.sellerAccount}).then(response => {
        if (response.code == 200) {
          this.sellerData = response.data
        }
      })
    },
    // 初始化查询
    handlegetDetail () {
      this.$api.post('/portal/shopCommdoity/findCommodityDetail', {commodityId: this.commodityId, account: this.sellerAccount}).then(response => {
        if (response.code == 200) {
          this.info = response.data
          this.time = this.info.commodityTypeInfos.discountDate
          this.discountTime = new Date(this.info.commodityTypeInfos.discountDate)
          this.currentTime = new Date()
          let images = []
          response.data.commodityTypeInfos.commodityImage.map(element => {
            images.push({url: element})
          })
          this.startNum = response.data.commodityTypeInfos.startNum
          // 商品图片展示
          this.$refs['view'].getData(images)
        }
      })
    },
    handleMenuChange (name) {
      this.name = name
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
