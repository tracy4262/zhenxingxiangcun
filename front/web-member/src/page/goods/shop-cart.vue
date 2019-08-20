<template>
<section>
    <top :address="false"></top>
    <section style="background: #F9F9F9;">
      <goods-head title="我的购物车">
        <BreadcrumbItem>我的购物车</BreadcrumbItem>
      </goods-head>
      <div class="layouts pb20">
        <div class="goods-table" style="min-height: 600px">
          <template v-if="data.length && isShow">
            <Row type="flex" align="middle" justify="center" class="table-head">
              <Col span="1"><Checkbox v-model="checkall" @on-change="handleCheckAll"></Checkbox></Col>
              <Col span="3"></Col>
              <Col span="7">商品名称</Col>
              <Col span="2" offset="1">单价</Col>
              <Col span="3" offset="1">数量</Col>
              <Col span="3" offset="1">小计</Col>
              <Col span="2">操作</Col>
            </Row>
            <div class="table-body">
              <div v-for="(e, i) in data" :key="i">
              <div class="tl pd20 h6">
                <Checkbox v-model="e.checked" @on-change="handleCheckedAll($event, e, i)"></Checkbox>
                {{e.sellerName}} <Icon type="md-text" class="t-green"  size="16" @click="webimchat(e.sellerAccount)"></Icon>
                </div>
              <Row class="item" type="flex" align="middle" justify="center" v-for="(item, index) in e.seller" :key="index">
                <Col span="1">
                  <span v-if="item.productNum && item.storageStatus == 3 && item.isBuy">
                    <Checkbox  v-if="item.maxNum" v-model="item.checked" :disabled="item.maxNum < item.num || item.productNum < item.num" @on-change="handleChecked($event, item, index, i)"></Checkbox>
                    <Checkbox  v-else v-model="item.checked" :disabled="item.productNum < item.num" @on-change="handleChecked($event, item, index, i)"></Checkbox>
                  </span>
                  <Checkbox v-else v-model="item.checked" :disabled="true"></Checkbox>
                </Col>
                <Col span="3">
                  <img v-if="item.notarizationCertificate && item.notarizationCertificate[0]" :src="item.notarizationCertificate[0]" width="70" height="70">
                </Col>
                <Col span="7">
                <router-link :to="`/goods/newDetail?id=${item.commodityId}&account=${item.sellerAccount}`" style="color:#666;">{{item.productName}}</router-link>
                <!-- <div class="tc">{{item.productName}}</div> -->
                </Col>
                <Col span="2" offset="1" >
                <!-- <span v-if="item.productPriceInfo && item.productPriceInfo > item.currentTime">￥{{item.productPrice}}</span>
                <span v-else>￥{{item.productOldPrice}}</span> -->
                <div v-if="item.disProductPrice">
                  <p style="text-decoration: line-through;">￥{{item.currentPrice}}</p>
                  <b>¥ {{item.disProductPrice}}</b>
                </div>
                <p v-else><b>￥{{item.currentPrice}}</b></p>
                </Col>
                <Col span="3" offset="1">
                  <InputNumber :max="item.maxNum ? item.maxNum : item.productNum" :min="item.minNum" v-model="item.num" :disabled="!item.productNum || !item.isBuy" @on-change="handleNumber(item, index, i)"></InputNumber>
                  <!-- 没卖完？ 剩余数量大于购买数量即可以购买，否则提示超出库存 不能购买，选择框变灰-->
                  <p class="t-orange mt5" v-if="item.productNum > 0">
                    <span v-if="item.maxNum">{{`限购${item.maxNum}件`}}</span>
                  </p>
                  <p class="t-orange mt5" v-else>{{'已售空'}}</p>
                  </Col>
                <Col span="3" offset="1" class="t-orange">￥{{item.subtotal}}</Col>
                <Col span="2">
                  <Poptip transfer confirm title="你确定要删除此商品吗？" @on-ok="handleDel(item, index,i)" >
                    <Button type="text" size="small">删除</Button>
                  </Poptip>
                </Col>
              </Row>
              </div>
            </div>
          </template>
          <template v-if="!data.length && isShow">
            <Row type="flex" align="middle" justify="center" class="mt50">
              <Col span="4">
                <img src="../../img/no-goods.png" />
              </Col>
              <Col span="6">
                <P>购物车空空如也，<br>快去抢购心仪商品吧~</P>
                <Button type="primary" class="mt10" @click="handleGoingShop">继续购物</Button>
              </Col>
            </Row>
          </template>

          <!-- 固定栏 -->
          <div class="table-foot">
            <div class="layouts">
              <Row type="flex" align="middle">
                <Col span="1"><Checkbox v-model="checkall" :disabled="!data.length" @on-change="handleCheckAll"></Checkbox></Col>
                <Col span="2">
                  <Poptip transfer confirm title="你确定要删除选中的商品吗？" @on-ok="handleDelSel" >
                    <Button type="text"  :disabled="isChecked <= 0">删除</Button>
                  </Poptip>
                </Col>
                <Col span="2">
                  <Poptip transfer confirm title="你确定要清空购物车吗？" @on-ok="handleDelAll" >
                    <Button type="text" :disabled="!data.length" >清空购物车</Button>
                  </Poptip>
                </Col>
                <Col span="4" offset="7" class="tc">已选商品 <span class="t-orange">{{isChecked}}</span> 件</Col>
                <Col span="5" class="tc">合计（不含运费）：<span class="t-orange">{{total}}</span></Col>
                <Col span="3" class="tr">
                  <Button type="primary" :disabled="isChecked <= 0" class="total-btn" @click="handleCount">结算</Button>
                </Col>
              </Row>
            </div>
          </div>
        </div>
      </div>
    </section>
</section>
</template>

<script>
import top from '~src/top'
import goodsHead from './components/head'
import {numMulti} from '~utils/utils'
export default {
  components: {
    top,
    goodsHead
  },
  data () {
    return {
      checkall: false,
      checkedNum: [],
      isChecked: 0,
      total: 0,
      totals: [],
      data: [],
      account: '',
      isShow: false
    }
  },
  created() {
    //货架状态 storageStatus  0:未上架,1:我下架的,2:售完下架,3:上架到正在出售页面',4:货架查全部
    this.account = this.$user.loginAccount
    this.handleInit()
  },
  methods: {
    // 初始化获取购物车列表
    handleInit () {
      this.$api.post('/shop/shopCard/list', {account: this.account}).then(response => {
        if (response.code === 200) {
          this.isShow = true
          let data = response.data
          data.forEach((e, i) => {
            e.checked = false
            e.checkedNum = []
            e.total = 0
            e.freight = 0
            e.seller.forEach(element =>{
              // 选中状态
              let freight = element.freight ? parseFloat(element.freight) : 0
              e.freight += freight
              element.checked = false
              element.isBuy = true
              element.maxNum = parseFloat(element.maximumSingleShipment)
              element.minNum = parseFloat(element.productSalesVolume)
              element.productNum = parseFloat(element.productAvailability)
              if (element.maxNum > element.productNum) { // 当最大购买量大于库存，最大购买量等于库存
                element.maxNum = element.productNum 
              }
              // // 调用数量改变方法，计算总价格
              if (element.pricing.salesWay === '团购销售') {
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
                  element.disProductPrice = element.pricing.groupBuyingPrice
                  element.productPrice = element.pricing.groupBuyingPrice
                  element.currentPrice = element.pricing.originalPrice
                } else {
                  element.productPrice = element.pricing.originalPrice
                  element.currentPrice = element.pricing.originalPrice
                }
              }
              if (element.pricing.salesWay === '定价销售') {
                let times = element.pricing.discountPeriod
                let discountStartTime = new Date(times[0])
                let discountEndTime = new Date(times[1])
                let currentTime = new Date()
                currentTime
                if (discountStartTime < currentTime && discountEndTime > currentTime) {
                  element.isDiscount = true
                } else {
                  element.isDiscount = false
                }
                // 是否在折扣时间内 折扣价格是否存在
                if (element.isDiscount && element.pricing.discountPrice) {
                  element.disProductPrice = element.pricing.discountPrice
                  element.productPrice = element.pricing.discountPrice
                  element.currentPrice = element.pricing.currentPrice
                } else {
                  element.productPrice = element.pricing.currentPrice
                  element.currentPrice = element.pricing.currentPrice
                }
              }
              if (element.productStatus == '预定产品') {
                element.productPrice = element.pricing.orderPrice
                element.currentPrice = element.pricing.orderPrice
                let times = element.pricing.advancePaymentTime
                let discountStartTime = new Date(times[0])
                let discountEndTime = new Date(times[1])
                let currentTime = new Date()
                if (discountStartTime < currentTime && discountEndTime > currentTime) {
                  element.isDiscount = true
                } else {
                  element.isDiscount = false
                }
              } else if (element.pricing.salesWay === '面议') {
                element.isBuy = false
                element.isDiscount = true
              } else if (element.pricing.salesWay === '竞价销售') {
                element.isBuy = false
                element.productPrice = element.pricing.startPrice
                element.currentPrice = element.pricing.startPrice
                let discountStartTime = new Date(element.pricing.biddingStartTime)
                let discountEndTime = new Date(element.pricing.biddingEndTime)
                let currentTime = new Date()
                if (discountStartTime < currentTime && discountEndTime > currentTime) {
                  element.isDiscount = true
                } else {
                  element.isDiscount = false
                }
              }
              this.handleChangeNumber(element)
            })
          })
          this.data = data
          console.log('data', this.data)
          this.getChecked()
        }
      })
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
    // 计算 是否有选中
    getChecked () {
      let isChecked = 0
      this.data.forEach(element => {
        element.seller.forEach(e => {
          if (e.checked) {
            isChecked++
          }
        })
      })
      this.isChecked = isChecked
    },
    // 根据商家 全选 
    handleCheckedAll ($event, item, index) {
      let total = 0
      this.data[index].checkedNum = []
      this.data[index].total = 0
      this.data[index].seller.forEach(e => {
        // 遍历当前操作商家的商品 ，如果有查库，并且商品状态等于上架 ，数量小于商品库存 并且最大销售量存在 数量小于最大销售量 操作当前的选中 或者不选
        if (e.productNum && e.storageStatus == '3'){
          console.log(e)
          // if (e.maxNum >= e.num && e.minNum <= e.num) {
          if (item.productNum < item.num || item.maxNum && item.maxNum < item.num) {
          } else {
            e.checked = $event
          }
        }
        if (e.checked) { // 如果选中 则计算金额，并push到选中数组中
          this.data[index].checkedNum.push(e)
          // 计算金额
          total += e.subtotal
          this.data[index].total = total
        }
      })
      // 遍历全部数据 商家下的商品长度 与选中数据长度相同 商家选中
      let flag = true
      this.data.forEach(e => {
        if (!e.checked) {
          flag = false
        }
      })
      this.checkall = flag
      this.countFn()
      this.getChecked()
    },
    // 全选
    handleCheckAll (flag) {
      this.data.forEach((item, index) => {
        item.checked = flag
        this.handleCheckedAll(flag, item, index)
      })
    },
    // 选择单条
    handleChecked ($event, item, index, i) {
      // 如果选中 则push
      item.checked = $event
      if (item.checked) {
        this.data[i].checkedNum.push(item)
      } else {
        // 不选 删除
        this.data[i].checkedNum.forEach((e, curentIndex) => {
          if (e.id == item.id) {
            this.data[i].checkedNum.splice(curentIndex, 1)
          }
        })
      }
      // 遍历全部数据 查看 当前操作的 商家的商品长度 与对应选中数组 中的 选中数据长度相同  商家选中 不同 商家不选中
      let flag = true
      this.data.forEach((e, index) => {
        if (!e.checked) {
          flag = false
        }
        if (index === i) {
          if (e.seller.length === this.data[i].checkedNum.length) {
            this.data[i].checked = true
          } else {
            this.data[i].checked = false
          }
        }
      })
      this.checkall = flag
      // 重新计算价格 
      let total = 0
      this.data[i].total = 0
      this.data[i].checkedNum.forEach(e => {
        if (e.checked) {
          console.log('cxongxinjisuan')
          // this.data[i].checkedNum.push(e)
          // 计算金额
          total += e.subtotal
          this.data[i].total = total
        }
      })
      this.countFn()
      this.getChecked()
    },
    // 初始化 数量改变
    handleChangeNumber (item) {
      item.subtotal = parseFloat((numMulti(item.productPrice ? item.productPrice : 0, item.num ? item.num : 1)).toFixed(2))
      console.log('item.subtotal', item.subtotal)
      this.countFn()
    },
    // 单击 改变数量
    handleNumber (item, index, i) {
      let entity = {id: item.id, num: item.num, account: this.account}
      this.$api.post('/shop/shopCard/update', entity).then(response => {
        if (response.code === 200) {
          this.handleChangeNumber(item)
          if (item.checked) {
            // 重新计算价格 
            let total = 0
            this.data[i].total = 0
            this.data[i].checkedNum.forEach(e => {
              if (e.checked) {
                console.log('cxongxinjisuan')
                // this.data[i].checkedNum.push(e)
                // 计算金额
                total += e.subtotal
                this.data[i].total = total
              }
            })
            this.countFn()
            this.getChecked()
          }
          this.countFn()
        }
      })
    },
    // 删除单条
    handleDel (item, index, i) {
     let arr = [{id: item.id}]
     this.handleDelete(arr, item, 2, index, i)
    },
    // 删除调用的方法，传数组，数组中是要删除商品的id
    handleDelete (array, item, type, index, i) {
      this.$api.post('/shop/shopCard/delete', {account: this.account, delIds: array}).then(response => {
        if (response.code === 200) {
          this.$Message.success('删除成功')
          if (type === 1) {
            // 表示是点击最下方的删除按钮，那么所有选中的都会删除，要讲选中数组归0，总价格归0            
            this.handleInit()
          } else if (type === 2) {
            // 表示是点击单个删除，那么需要将删除的数据从选中checkbox数组中以及data中删除，并且重新计算总金额
            // this.handleChecked(false, item, index, i)
            if (!this.data[i].seller.length) {
              this.data.splice(i, 1)
            }
            // 重新计算价格 
            let total = 0
            this.data[i].seller.forEach(e => {
              if (e.checked) {
                // 计算金额
                total += e.subtotal
                this.data[i].total = total
              }
            })
            this.data[i].seller.splice(index, 1)
            if (!this.data[i].seller.length) {
              this.data.splice(i, 1)
            }
            this.countFn()
            this.getChecked()
          }
        } else {
          this.$Message.success('删除失败')
        }
      })
    },
    // 删除选择的多条
    handleDelSel () {
      let arr = []
      this.data.forEach((item, index) => {
        item.checkedNum.forEach((e, i) => {
          if (e.checked) {
            arr.push({id: e.id})
          }
        })
      })
      console.log(arr)
      this.handleDelete(arr, [], 1)
    },
    // 计算金额
    countFn () {
      let total = 0
      if (this.data.length) {
        this.data.forEach(item => {
          total += item.total
          this.total = total.toFixed(2)
        })
      } else {
        this.checkall = false
        this.total = 0
      }
    },
    // 删除所有 清空购物车
    handleDelAll () {
      this.$api.post('/shop/shopCard/clear', {account: this.account}).then(response => {
        if (response.code === 200) {
          this.$Message.success('清除购物车成功')
          this.data = []
          this.total = 0
        } else {
          this.$Message.success('清除购物车失败')
        }
      })
    },
    // 结算页
    handleCount () {
      let account = this.account
      let data = []
      let array = []
      console.log('data', this.data)
      this.data.forEach(e => {
        if (e.checkedNum.length) { // 有被选中的商品
          data.push(e)
        }
        // let j = {id: []}
        e.checkedNum.forEach(element => {
          array.push({id: element.id})
          // j.id.push(element.id)
        })
        // array.push(j)
      })
      let list = {} 
      list[account] = array
      console.log('lists', list)
      localStorage.setItem('goodsList', JSON.stringify(list))
      this.$router.push({path: '/order-check', query: {type: 1}})
    },
    // 点击继续购物
    handleGoingShop () {
      this.$router.push('/goods/index')
    }
  }
}
</script>

<style lang="scss" scoped>
.goods-table{
  padding: 20px;
  margin-top: 20px;
  background: #fff;
  .table-head{
    font-size: 14px;
    font-weight: 700;
    padding: 5px 10px;
    margin-bottom: 10px;
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
    .ivu-btn-text{
      font-size: 14px;
      color:#262626;
    }
    .ivu-btn-text[disabled]{
      color: #bbbec4;
    }
  }
}
</style>
