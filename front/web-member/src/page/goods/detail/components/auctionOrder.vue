<template>
  <div class="">
    <top :address="false" ref="top"></top>
    <head-nav :active="4"></head-nav>
    <div class="bg-white">
      <div class="layouts">
        <Breadcrumb class="mt20">
          <BreadcrumbItem to="/goods/index">产品首页</BreadcrumbItem>
          <BreadcrumbItem to="/goods/index">竞价商品</BreadcrumbItem>
          <BreadcrumbItem to="/goods/index">{{ info.productName }}</BreadcrumbItem>
          <BreadcrumbItem>保证金订单</BreadcrumbItem>
          <!-- <BreadcrumbItem>{{info.commodityTypeInfos.productName}}</BreadcrumbItem> -->
        </Breadcrumb>
        <h3 class="pb30 pt50">保证金订单</h3>
      </div>
    </div>
    <div style="background: #f2f2f2;" class="pt20 pb30">
      <div class="bg-white layouts pd20">
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
        <h5 style="font-size:16px;color:#737373;" class="mt20"> <b>确认订单信息</b></h5>
        <p class="pt10"><div class="t-grey" style="display: inline-block;">拍品名称：</div>{{ info.productName }}</p>
        <p class="pt10"><div class="t-grey" style="display: inline-block;">保证金：</div>￥{{ info.bond }}</p>
        <p class="pt10"><Icon type="ios-information-circle" size="25" color="#d8d8d8" class="mr10" />若竞拍成功，保证金直接作为货款的一部分扣款至商家账户；若竞拍失败，保证金讲在拍卖结束后1-15个工作日自动退回。</p>
        <!-- <p class="pt10"><span class="t-grey">支付方式：</span>与商家协商<Icon class="t-green ml5" type="md-text" size="18"/></p> -->
        <div class="info mt30 tr">
          <div class="pd20 h6" style="color:#737373 ">
            <!-- <p class="mb5"><span class="t-grey">保证金金额：</span>￥55</p> -->
            <!-- <p class="mb5"><span class="t-grey">运费：</span>￥55</p> -->
            <p v-if="addressInfo.addArea">送货至：
              {{addressInfo.addArea}}，{{addressInfo.addDetail}}，{{addressInfo.linkman}}，{{addressInfo.mobile | filterPhone}}
            </p>
            <p v-else>请添加收货地址</p>
          </div>
          <div class="bar">
            合计：￥<span class="t-orange h6 b">{{ info.bond }}</span>
            <Button type="primary" @click="handleSubmit">提交保证金订单</Button>
          </div>
        </div>
      </div>
    </div>
    <Modal
        v-model="show"
        :width="520"
        :mask-closable="false"
        title="保证金支付">
        <div class="">
        </div>
        保证金：{{ info.bond }}元。
        <div class="tc" slot="footer">
          <Button @click="show=false">取消支付</Button>
          <Button type="primary" @click="ok">确认支付</Button>
        </div>
    </Modal>
  </div>
</template>

<script>
import top from '~src/top'
import headNav from '../../../51index/components/nav'
import vuiAddress from '../../components/vui-address'
export default {
  components: {
    top,
    headNav,
    vuiAddress
  },
  data () {
    return {
      info: {},
      addressData: [],
      addressInfo: {},
      account: '', // 卖家账号
      show: false,
      commodityId: ''
    }
  },
  created () {
    this.commodityId = this.$route.query.id
    console.log('this.router', this.$route)
    this.account = this.$route.query.account
    this.handleGetAddress()
    this.initBondInfo()
  },
  filters: {
    filterPhone (val) {
      if (val) {
        return `${val.substr(0, 3)}*****${val.substr(8)}`
      }
    }
  },
  methods: {
    // 提交保证金页面信息查询
    initBondInfo () {
      this.$api.post('/shop/shopBidding/marginInfo', {
        commodityId: this.commodityId
      }).then(response => {
        console.log('respo', response)
        if (response.code === 200) {
          this.info = response.data
        }
      }).catch(error => {
        this.$Message.error('服务器异常！')
      })
    },
    // 取收货地址
    handleGetAddress () {
      // order 0 按照时间默认排序， 1 按照时间+选装状态排序 订单管理需要的
      this.$api.post('/nswy-portal-service/shop/address/list',{account: this.$user.loginAccount, order: 0}).then(response => {
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
    handleSelect (e) {
      console.log('选择地址后的回调', e)
      this.addressInfo = e
    },
    // 提交
    handleSubmit () {
      let flag = false
      // 如果没有设置默认收货地址 则不会自动勾选
      for (let i = 0; i < this.addressData.length; i++) {
        if (this.addressData[i].isDefault) {
          flag = true
        }
      }
      if (!flag) {
        this.$Message.info('请选择收货地址！')
        return
      } 
      // 买家提交保证金
      this.$api.post('/shop/shopBidding/submitMargin', {
        buyerAccount: this.$user.loginAccount,   //买家账号
        sellerAccount: this.account,  //卖家账号
        commodityId: this.commodityId,  //商品ID
        productName: this.info.productName,   //商品名称
        addressInfo: this.addressInfo,   //地址信息
        margin: this.info.bond,  //保证金
        startTime: this.info.startTime,
        endTime: this.info.endTime,
        image: this.info.image,
        unit: this.info.unit
      }).then(response => {
        if (response.code === 200) {
          this.show = true
        }
      })
      // this.$router.push(`/goods/newDetail?id=${162}&account=${this.account}`)
    },
    ok () {
      // 买家支付保证金
      this.$api.post('/shop/shopBidding/payMargin', {
        buyerAccount: this.$user.loginAccount,   //买家账号
        commodityId: this.commodityId,  //商品ID
      }).then(response => {
        if (response.code === 200) {
          this.show = false
          this.$Message.success('支付成功！')
          this.$router.push(`/goods/newDetail?id=${this.commodityId}&account=${this.account}`)
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>

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