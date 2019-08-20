<template>
<div>  
    <top :address="false"></top>
    <div style="background: #F9F9F9;">
        <goods-head title="订单核对">
          <BreadcrumbItem to="/shop-cart">我的购物车</BreadcrumbItem>
          <BreadcrumbItem>订单核对</BreadcrumbItem>
        </goods-head>
      <div class="layouts">
        <div style="background: #fff;" class="mt20 pd20">
          <Row class="pl15 pb10">
            <Col span="20">
              <h5 style="font-size:16px">请选择收货地址</h5>
            </Col>
            <Col span="4">
              <router-link to="/address" class="t-green">
                管理收货地址
              </router-link>
            </Col>
          </Row>
          <vui-address :data="addressData" :edit="false" @on-select="handleSelect"></vui-address>          
          <div class="pl15">
            <h5 style="font-size:16px;color:#737373;" class="mt20"> <b>确认订单信息</b></h5>
            <div class="goods-table pt20">
              <Row type="flex" align="middle" justify="center" class="table-head" style="color:#666;">
                <!-- <Col span="3"></Col> -->
                <Col span="15" class="tl">商品信息</Col>
                <Col span="3">单价</Col>
                <Col span="3">数量</Col>
                <Col span="3">小计</Col>
              </Row>
              <!-- <div v-for="(oredrData, i) in oredrDatas" :key="i"> -->
              <div class="table-body" v-for="(list, index) in oredrDatas" :key="index">
                <h5 style="font-size:14px;color:#666;" class="mt20 mb10 tl">{{list.sellerName}}
                <Icon type="md-text" class="t-green"  size="16" @click="webimchat(list.sellerAccount)"></Icon>
                </h5>
                <!-- <Row class="item" type="flex" align="middle" justify="center" v-for="child in item.data">
                  <Col span="3"><img :src="child.src" width="70" height="70"></Col>
                  <Col span="12"><div class="tl">{{child.goodsName}}</div></Col>
                  <Col span="3">￥{{child.price}}</Col>
                  <Col span="3">{{child.number}}件</Col>
                  <Col span="3"><span class="t-orange">￥{{child.subtotal}}</span></Col>
                </Row> -->
                <Row class="item" type="flex" align="middle" justify="center"  v-for="(item, i) in list.seller" :key="i">
                  <Col span="3"><img v-if="item.notarizationCertificate[0]" :src="item.notarizationCertificate[0]"  width="70" height="70"></Col>
                  <Col span="12"><div class="tl">{{item.productName}}</div></Col>
                  <Col span="3">￥{{item.productPrice}}</Col>
                  <Col span="3"><span>{{item.num}} </span>{{item.productAvailabilityUnits}}</Col>
                  <Col span="3"><span class="t-orange">￥{{item.subtotal}}</span></Col>
                </Row>
              </div>

              <!-- </div> -->
            </div>
            <h5 style="font-size:16px" class="mt20 mb20">发票信息</h5>
            <Checkbox v-model="isInvoice" class="mb20">开具发票</Checkbox>
            <invoiceInfo v-if="isInvoice" ref="invoiceInfo"></invoiceInfo>
            
            <div class="info tr">
              <div class="pd20 h6" style="color:#737373 ">
                <p class="mb5"><span class="t-grey">商品金额：</span>￥{{orderForm.amount}}</p>
                <!-- 预售不显示运费 -->
                <p class="mb5" v-if="shopType != 1"><span class="t-grey">运费：</span>￥{{orderForm.logisticAmount}}</p>
                <!-- <p class="mb5" v-else><span class="t-grey">运费：</span>￥{{orderForm.logisticAmount}}</p> -->
                <p v-if="addressInfo.addArea">送货至：
                {{addressInfo.addArea}}，{{addressInfo.addDetail}}，{{addressInfo.linkman}}，{{addressInfo.mobile | filterPhone}}
                </p>
                <p v-else>请添加收货地址</p>
              </div>
              <div class="bar">
                合计：￥<span class="t-orange h6 b">{{orderForm.money}}</span>
                <Button type="primary" @click="handleSubmit">提交</Button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
</div>
</template>

<script>
import goodsHead from './components/head'
import invoiceInfo from './components/invoiceInfo'
import vuiAddress from './components/vui-address'
import {numAdd, numMulti} from '~utils/utils'
import {isPhone2, isEmail2} from '~utils/validate'
import top from '~src/top'
export default {
  components: {
    goodsHead,
    invoiceInfo,
    vuiAddress,
    top
  },
  data () {
    return {
      addressData: [],
      oredrDatas: [],
      oredrData: [],
      orderForm: {
        money: 0,
        logisticAmount: 0,
        addressInfo: {},
        amount: 0
      },
      addressInfo: {},
      ruleInline: {
        unitName: [
          {required: true, message: '请填写单位名称', trigger: 'blur' }
        ],
        identificationCode: [
          {required: true, message: '请填写纳税人识别码', trigger: 'blur' }
        ],
        registerAddress: [
          {required: true, message: '请填写注册地址', trigger: 'blur' }
        ],
        registerTelephone: [
          {required: true, message: '请填写注册电话', trigger: 'blur' }
        ],
        accountBank: [
          {required: true, message: '请填写开户银行', trigger: 'blur' }
        ],
        bankAccount: [
          {required: true, message: '请填写银行账户', trigger: 'blur' }
        ],
        mobile: [
          {required: true, message: '请填写收票人手机号', trigger: 'blur' },
          {validator: isPhone2, trigger: 'blur' }
        ],
        email: [
          {required: true, message: '请填写收票人邮箱', trigger: 'blur' },
          {validator: isEmail2, trigger: 'blur' }
        ],
      },
      account: '',
      isInvoice: false,
      invoiceInfo: {
        invoiceMode: '0', // 开票方式 0电子发票 
        invoiceType: '0', // 开票类型 0不要发票 1个人发票 2增值税发票
        title: '个人', // 开票抬头
        unitName: '', // 单位名称
        identificationCode: '', // 纳税人识别码
        registerAddress: '', // 注册地址
        registerTelephone: '', // 注册电话
        accountBank: '', // 开户银行
        bankAccount: '', // 银行账户
        mobile: '',
        email: ''
      },
      showTitle: false,
      disableType: false,
      isUpdate: false,
      invoiceId: '',
      type: '',
      goodsList: {},
      shopType: '', // 0 定价，1预售，2面议，3团购，4竞拍
      invoiceTaxId: '', // 增值税发票id
      invoicePersonalId: '', // 普通发票id
    }
  },
  created() {
    this.type = this.$route.query.type ?  this.$route.query.type : ''
    this.account = this.$user.loginAccount
    // 取地址
    this.handleGetAddress()
    // goodsList 存在 说明是点击的立即购买，不然就是从购物车进来的
    this.goodsList = JSON.parse(localStorage.getItem('goodsList'))
    // console.log('this.goodsList', this.goodsList)
    // 取商品数据 如果有type 说明是购物车进来的，调用购物车查询订单商品的接口， 没有说明是直接点击立即购买进来的
    if (this.type) {
      let data = JSON.parse(localStorage.getItem('goodsList'))
      let list = data[this.account] ? data[this.account] : []
      this.$api.post('/shop/shopCard/product', {data: data[this.account]}).then(response => {
        if (response.code === 200) {
          console.log(response)
          let data = response.data
          this.getData(data)
        }
      })
    } else {
      let list = JSON.parse(localStorage.getItem('goodsInfo'))
      console.log('list', list)
      this.$api.post('/shop/shopCard/purchase', list).then(response => {
        if (response.code === 200) {
          console.log(response)
          let data = response.data
          this.getData(data)
        }
      })
    }
  },
  filters: {
    filterPhone (val) {
      if (val) {
        return `${val.substr(0, 3)}*****${val.substr(8)}`
      }
    }
  },
  methods: {
    getData (data) {
          let total = 0
          let freight = 0
          data.forEach((e, i) => {
              e.total = 0
              e.freight = 0
              e.seller.forEach(element =>{
                // 选中状态
                let freight = element.freight ? parseFloat(element.freight) : 0
                console.log('99999', freight)
                element.isBuy = true
                element.maxNum = parseFloat(element.maximumSingleShipment)
                element.minNum = parseFloat(element.productSalesVolume)
                element.productNum = parseFloat(element.productAvailability)
                if (element.maxNum > element.productNum) { // 当最大购买量大于库存，最大购买量等于库存
                  element.maxNum = element.productNum 
                }
                // // 调用数量改变方法，计算总价格
                if (element.pricing.salesWay === '团购销售') {
                  this.shopType = 3
                  let discountStartTime = new Date(element.pricing.groupBuyingStartTime)
                  let discountEndTime = new Date(element.pricing.groupBuyingEndTime)
                  let currentTime = new Date()
                  if (discountStartTime < currentTime && discountEndTime > currentTime) {
                    element.isDiscount = true
                  } else {
                    element.isDiscount = false
                  }
                  // 是否在折扣时间内 折扣价格是否存在
                  if (element.isDiscount && element.pricing.groupBuyingPrice) {
                    element.productPrice = element.pricing.groupBuyingPrice
                  } else {
                    element.productPrice = element.pricing.originalPrice
                  }
                }
                if (element.pricing.salesWay === '定价销售') {
                  this.shopType = 0
                  let times = element.pricing.discountPeriod
                  let discountStartTime = new Date(times[0])
                  let discountEndTime = new Date(times[1])
                  let currentTime = new Date()
                  if (discountStartTime < currentTime && discountEndTime > currentTime) {
                    element.isDiscount = true
                  } else {
                    element.isDiscount = false
                  }
                  // 是否在折扣时间内 折扣价格是否存在
                  if (element.isDiscount && element.pricing.discountPrice) {
                    element.productPrice = element.pricing.discountPrice
                  } else {
                    element.productPrice = element.pricing.currentPrice
                  }
                }
                if (element.productStatus == '预定产品') {
                  this.shopType = 1
                  if (JSON.parse(localStorage.getItem('goodsInfo')).shopType == 1) { // 说明是 支付定金
                    // depositPrice
                    if (element.pricing.deposit === '免付定金') {
                      element.productPrice = 0
                    } else if (element.pricing.deposit === '定额支付') {
                      element.productPrice = element.pricing.depositAmount
                    } else if (element.pricing.deposit === '按比例支付') {
                      let depositPrice = parseFloat((numMulti(element.pricing.depositPercent ? element.pricing.depositPercent : 0, element.pricing.orderPrice ? element.pricing.orderPrice : 0)).toFixed(2))
                      element.productPrice = parseFloat((numMulti(depositPrice ? depositPrice : 0, 0.01)).toFixed(2))
                    }
                  } else { // 说明是支付尾款
                    element.productPrice = element.pricing.orderPrice
                  }
                  let times = element.pricing.advancePaymentTime
                  let discountStartTime = new Date(times[0])
                  let discountEndTime = new Date(times[1])
                  let currentTime = new Date()
                  if (discountStartTime < currentTime && discountEndTime > currentTime) {
                    element.isDiscount = true
                  } else {
                    element.isDiscount = false
                  }
                }
                // this.handleChangeNumber(element)
                element.subtotal = parseFloat((numMulti(element.productPrice ? element.productPrice : 0, element.num)).toFixed(2))
                // 计算价格
                e.freight += freight
                e.total += element.subtotal
              })
              total += e.total
              freight += e.freight
            })
              this.orderForm.amount = total.toFixed(2)
              console.log('freight', freight)
              this.orderForm.logisticAmount = freight.toFixed(2)
              if (this.shopType == 1) { // 说明是 预售商品 支付定金
                this.orderForm.money = (total).toFixed(2)
              } else { // 其他商品 要加 运费
                this.orderForm.money = (total + freight).toFixed(2)
              }
              this.oredrDatas = data
    },
    // 初始化 数量改变
    handleChangeNumber (item) {
      item.subtotal = parseFloat((numMulti(item.productPrice ? item.productPrice : 0, item.num)).toFixed(2))
      console.log('item.subtotal', item.subtotal)
      this.countFn()
    },
    // 聊天
    webimchat (account) {
      this.$api.post('/member/fishing/findAvatar',{account: account}).then(response => {
          if (response.code == 200) {
            let sellerData = response.data
            layui.layim.chat({
              id: sellerData.userId,
              name: sellerData.name,
              avatar: sellerData.avatar,
              type: 'friend'
            })
          }
        })
    },
    change () {
      if (this.invoiceInfo.invoiceMode == '0') { // 电子发票
        this.invoiceInfo.invoiceType = '1'
        this.disableType = true
      } else { // 纸质发票
        if (this.invoiceInfo.invoiceType == '2') {  // 增值税
          this.invoiceInfo.title = '公司'
          this.disableType = true
        } else {
          this.disableType = false
        }
      }
    },
    // 提交订单
    handleSubmit () {
      this.orderForm.addressInfo = JSON.stringify(this.addressInfo)
      if (this.orderForm.addressInfo == '{}') {
        this.$Message.error('请选择收货地址')
        return
      }
      if (!this.oredrDatas.length) {
        this.$Message.error('请选择商品')
        return
      }
      let list = Object.assign({}, this.orderForm)
      list.shopProducts = this.oredrDatas
      list.remark = ''
      list.account = this.account
      list.type = this.type
      list.shopType = this.shopType
      if (this.isInvoice) { // 勾选了发票
        // 取 发票组件中的发票数据 赋值给当前页面的 invoiceInfo
        this.invoiceInfo = this.$refs['invoiceInfo'].invoiceInfo
        if (this.invoiceInfo.invoiceMode == '0') { // 0电子发票还是 1纸质发票
          list.invoiceMode = 0
        } else {
          list.invoiceMode = 1
        }
        this.$refs['invoiceInfo'].$refs['invoiceInfo'].validate((valid) => {
          if (valid) {
            if (this.invoiceInfo.invoiceType == '1') { // 普通发票
              list.invoiceType = 1
              list.invoiceInfo = JSON.stringify({
                unitName: this.invoiceInfo.unitName,
                identificationCode: this.invoiceInfo.identificationCode,
                mobile: this.invoiceInfo.mobile,
                email: this.invoiceInfo.email
              })
            } else if (this.invoiceInfo.invoiceType == '2') { // 增值税发票
              list.invoiceType = 2
              list.invoiceInfo = JSON.stringify({
                unitName: this.invoiceInfo.unitName,
                identificationCode: this.invoiceInfo.identificationCode,
                registerAddress: this.invoiceInfo.registerAddress,
                registerTelephone: this.invoiceInfo.registerTelephone,
                accountBank: this.invoiceInfo.accountBank,
                bankAccount: this.invoiceInfo.bankAccount
              })
            }
            this.$api.post('/shop/shopOrder/add', list).then(response => {
              if (response.code === 200) {
                this.$Message.success('提交成功')
                localStorage.removeItem("goodsList")
                let goodsList = {} 
                localStorage.setItem("goodsList", JSON.stringify(goodsList))
                this.$router.push('/orderDetails/purchasedGoods')
              }
            })
          } else {
            this.$Message.error('请核对发票信息')
          }
        })
      } else {
        list.invoiceType = 0
        list.invoiceInfo = ''
        this.$api.post('/shop/shopOrder/add', list).then(response => {
            if (response.code === 200) {
                this.$Message.success('提交成功')
                localStorage.removeItem("goodsList")
                let goodsList = {} 
                localStorage.setItem("goodsList", JSON.stringify(goodsList))
                this.$router.push('/orderDetails/purchasedGoods')
            }
        })
      }
    },
    // 取收货地址
    handleGetAddress () {
      // order 0 按照时间默认排序， 1 按照时间+选装状态排序 订单管理需要的
      this.$api.post('/nswy-portal-service/shop/address/list',{account: this.account, order: 0}).then(response => {
        if (response.code === 200) {
          this.addressData = response.data
          this.addressData.forEach(e => {
            if (e.isDefault) {
              this.addressInfo = e
            }
          })
        }
      })
    },
    // 返回收货地址
    handleSelect (item) {
      this.addressInfo = item
    }
  }
}
</script>

<style lang="scss" scoped>
.goods-table{
  .table-head{
    font-size: 14px;
    font-weight: 700;
    // padding: 5px 10px;
    text-align: center;
  }
  .table-body{
    text-align: center;
    .item{
      padding: 10px;
      background: #FCFDFE;
      border:1px solid #eee;
      &:not(:last-child){
        border-bottom: none;
      }
    }
  }
  .table-foot{
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    background: #F3F3F3;
    .total-btn{
      border-radius: 0;
      font-size: 20px;
      padding: 10px 40px;
    }
  }
}
.info{
  border: 1px solid #F3F3F3;
  .bar{
    background: #F3F3F3;
    .ivu-btn{
      border-radius: 0;
      font-size: 20px;
      padding: 10px 40px;
      margin-left: 20px;
    }
  }
}
</style>