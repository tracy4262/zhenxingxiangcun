<template>
  <div class="new-good-detail">
    <Row>
        <Col span=18>
            <p class="good-name"><span class="tag">{{ info.isRetrospect === '是' ? '可追溯' : '不可追溯' }}/{{ info.antiFake === '是' ? '可防伪' : '不可防伪' }}</span>{{ info.productName }}</p>
            <div class="mt15">
                <div class="clocker" v-if="!isEnd">
                    <div v-if="isDiscount">
                        距离竞拍结束还剩：
                        <vui-clocker :time="this.pricing.biddingEndTimeStr" format="%D天 %H小时 %M分 %S秒"/>
                    </div>
                    <div v-else>
                        竞拍未开始，开始时间：{{ pricing.biddingStartTimeStr }}
                    </div>
                </div>
                <div class="clocker" v-else>
                    竞拍已结束
                </div>
                <div class="content pd10">
                    <div class="vui-flex price">
                    <div>
                        <template>
                        <span class="t-red h6" >起拍价： ￥<b class="h1">{{ pricing.startPrice }}</b></span>
                        <!-- <span class="t-grey ml20 h6">竞拍最小加价：{{pricing.minimum}}</span> -->
                        <span class="t-grey ml20 h6">[出价{{ count }}次]</span>
                        </template>
                    </div>
                    
                    <div class="vui-flex-item pl10 pt20">
                        <div class="vui-flex">
                        <div class="vui-flex-item tr">
                            <p>{{ participantCount }}人参与</p>
                        </div>
                        <div class="evaluation">
                            <span class="line"></span>
                            <p class="pb5">库存：{{info.productAvailability}}{{info.productAvailabilityUnits}}</p>
                        </div>
                        </div>
                    </div>
                    </div>
                    <div class="location pt5">
                    <Row>
                        <Col span="14">
                        <p class="ell p" :title="info.productOrigin + '/' + info.addrDetail">产品产地：{{ info.productOrigin + '/' + info.addrDetail }}</p>
                        <p class="ell p" :title="info.productLocation + '/' + info.productAddrDetail">产品所在地：{{ info.productLocation + '/' + info.productAddrDetail }}</p>
                        </Col>
                        <Col span="10" class="pl10">
                        <p class="ell p" v-if="info.productionBase">生产基地：<span class="a t-blue" @click="goToProductionBase">{{ info.productionBaseName }}</span></p>
                        </Col>
                    </Row>
                    </div>
                </div>
                <div class="delivery pl20">
                    <!-- 竞价结束之前 -->
                    <div v-if="!isEnd">
                        <!-- 初始化页面，未提交保证金 -->
                        <div class="vui-flex vui-flex-middle" style="height: 60px;" v-if="info.biddingStatus === 0">
                            <div>
                                <span style="color: #ff9900;">保证金：￥</span><span style="color: #ff9900; font-size: 18px;">{{ pricing.bond }}</span>
                                <Button type="primary" class="ml20" @click="submit" :disabled="!protocol">提交保证金报名</Button>
                            </div>
                            <div class="ml20">
                                <Checkbox v-model="protocol">我已阅读并同意遵守</Checkbox>
                                <span class="t-green" style="cursor: pointer;" @click="detail"><<{{ pricing.biddingProtocol[0].response.data.name.split('.')[0] }}>></span>
                            </div>
                        </div>
                        <!-- 提交了保证金，但未付款 -->
                        <div class="vui-flex vui-flex-middle" style="height: 60px;" v-if="info.biddingStatus === 1">
                            已生成保证金订单<Button type="primary" class="ml20" @click="goToPay">去付款</Button>
                        </div>
                        <!-- 已提交保证金，已付款 -->
                        <div class="pt10 pb10" v-if="info.biddingStatus === 2 || info.biddingStatus === 3">
                            <!-- 报名成功，但未开始竞拍 -->
                            <Button type="primary" v-if="!isDiscount" disabled>报名成功，等待开拍</Button>
                            <!-- 报名成功，已开始竞拍 -->
                            <div v-else>
                                <Row type="flex" align="middle">
                                    <Col span="24">
                                        <div>出价：￥<InputNumber :min="parseFloat(pricing.startPrice)" v-model="price" :active-change="false"></InputNumber>&nbsp;&nbsp;/&nbsp;&nbsp;{{ info.productAvailabilityUnits }}（注：出价金额不得低于起拍价，且最小加价不得小于{{ pricing.minimum }}元）</div>
                                    </Col>
                                </Row>
                                <Row class="mt10" type="flex" align="middle">
                                    <Col span="16">
                                        <div>数量：&nbsp;&nbsp;&nbsp;&nbsp;<InputNumber :max="info.productAvailability" :min="info.productSalesVolume" v-model="number" :active-change="false"></InputNumber>&nbsp;&nbsp;{{ info.productAvailabilityUnits }}（注：{{ info.productSalesVolume + info.productAvailabilityUnits }}起售）</div>                                    
                                    </Col>
                                    <Col span="8">
                                        <Button type="primary" @click="bid">确认出价</Button>                                    
                                    </Col>
                                </Row>
                            </div>
                        </div>
                    </div>
                    <!-- 竞价结束之后 -->
                    <div v-if="isEnd">
                        <!-- 竞拍结束后的其他状态均显示竞拍结束 -->
                        <div class="pt10 pb10" v-if="info.biddingStatus === 0 || info.biddingStatus === 1 || info.biddingStatus === 2 || info.biddingStatus === 3">
                            <div class="vui-flex">
                                <img src="../../../../img/paimai.png" width="30" height="30">
                                <div class="vui-flex-item pl10">
                                    <h5 class="pb5">竞拍已结束！</h5>
                                </div>
                            </div>
                        </div>
                        <!-- 竞拍成功，未提交订单 --> <!-- 竞拍成功，已提交订单，未支付 -->
                        <div class="pt10 pb10" v-if="info.biddingStatus === 4 || info.biddingStatus === 7">
                            <div class="vui-flex">
                                <img src="../../../../img/paimai.png" width="30" height="30">
                                <div class="vui-flex-item pl10">
                                    <p class="pb5">恭喜！您的出价在发货范围内！</p>
                                    <p class="pb5">成交价：￥{{ biddingInfo.price }}/{{ info.productAvailabilityUnits }}</p>
                                    <p class="pb5">成交数量：{{ biddingInfo.number }}{{ info.productAvailabilityUnits }}</p>
                                    <p class="pb5">请在24小时内提交订单，若在24小时内未提交订单，系统将自动关闭交易</p>
                                    <Button type="primary" class="mt10" @click="checkOrder" v-if="info.biddingStatus === 4">订单核对</Button>
                                    <Button type="primary" class="mt10" @click="payReset" v-if="info.biddingStatus === 7">支付尾款</Button>
                                </div>
                            </div>
                        </div>
                        <!-- 竞拍成功，已提交订单，已支付 -->
                        <div class="pt10 pb10" v-if="info.biddingStatus === 8">
                            <div class="vui-flex">
                                <img src="../../../../img/paimai.png" width="30" height="30">
                                <div class="vui-flex-item pl10">
                                    <p class="pb5">竞拍已结束！</p>
                                    <p class="pb5">成交价：￥{{ biddingInfo.price }}/{{ info.productAvailabilityUnits }}</p>
                                    <p class="pb5">成交数量：{{ biddingInfo.number }}{{ info.productAvailabilityUnits }}</p>
                                </div>
                            </div>
                        </div>
                        <!-- 竞拍失败 -->
                        <div class="pt10 pb10" v-if="info.biddingStatus === 5 || info.biddingStatus === 6">
                            <div class="vui-flex">
                                <img src="../../../../img/paimai.png" width="30" height="30">
                                <div class="vui-flex-item pl10">
                                    <p class="pb5">很遗憾！您的出价不在发货范围内！</p>
                                    <p>请及时与商家联系退还保证金 <Icon class="t-green ml5" type="md-text" size="18"/></p>
                                </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="pt15">
                <Row type="flex" align="middle">
                    <Col span="12" class="pl10">
                        <div>
                            起拍价：￥{{pricing.startPrice}} / {{info.productAvailabilityUnits}}
                        </div>
                        <div class="mt20">
                            竞拍最小加价：￥{{pricing.minimum}} / {{info.productAvailabilityUnits}}
                        </div>
                        <!-- <p class="pt20"><img src="../../../../img/shouhou.png" width="30" height="30">售后保障</p> -->
                    </Col>
                    <Col span="12">
                        <Row>
                        <Col span="8">
                            <div class="pl5 pr5 tc">
                            <p>商品二维码</p>
                            <img src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554975641707&di=91381cbe03893448df82e8a4ede7fc12&imgtype=0&src=http%3A%2F%2Fimg.atobo.com%2FProductImg%2FEWM%2FUWeb%2F3%2F3%2F4%2F0%2F332%2F3340332%2F1.gif" alt="" width="100%">
                            </div>
                        </Col>
                        <Col span="8">
                            <div class="pl5 pr5 tc">
                            <p>追溯码</p>
                            <img src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554975641707&di=91381cbe03893448df82e8a4ede7fc12&imgtype=0&src=http%3A%2F%2Fimg.atobo.com%2FProductImg%2FEWM%2FUWeb%2F3%2F3%2F4%2F0%2F332%2F3340332%2F1.gif" alt="" width="100%">
                            </div>
                        </Col>
                        <Col span="8">
                            <div class="pl5 pr5 tc">
                            <p>防伪码</p>
                            <img src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554975641707&di=91381cbe03893448df82e8a4ede7fc12&imgtype=0&src=http%3A%2F%2Fimg.atobo.com%2FProductImg%2FEWM%2FUWeb%2F3%2F3%2F4%2F0%2F332%2F3340332%2F1.gif" alt="" width="100%">
                            </div>
                        </Col>
                        </Row>
                    </Col>
                </Row>
            </div>
            <div class="process">
                <p>竞拍流程： 
                竞拍报名 <Icon type="ios-arrow-forward" />
                填写报价及购买数量 <Icon type="ios-arrow-forward" /> 
                竞拍结束 <Icon type="ios-arrow-forward" />
                按价格高低的先后顺序进行发货</p>
            </div>
            </div>
        </Col>
        <Col span="6" class="pl20">
            <div style="border: 1px solid rgb(242, 242, 242);">
                <div class="h5 tc pt10 pb10" style="border-bottom: 1px solid rgb(242, 242, 242);">出价记录</div>
                <div class="pd5">截止至：{{ this.moment(new Date()).format('YYYY-MM-DD HH:mm:ss') }}</div>
                <div class="pd5">最低价：{{ minPrice }}元</div>
                <div class="pd5">平均价：{{ avgPrice }}元</div>
                <div class="pd5">竞拍数量合计：{{ sum }}{{ info.productAvailabilityUnits }}</div>
            </div>
        </Col>
    </Row>
    <Modal
        v-model="show"
        :width="820"
        :mask-closable="false"
        title="平台免责协议">
        <div class="protocol">
          <p>在农事无忧平台依据《用户注册协议》注册的用户（即“竞拍人”），在同意本协议以下全部条款后，方有资格享受农事无忧平台（以下简称“平台”）提供的竞拍服务（以下简称“服务”）。您使用本平台提供的服务即意味着同意与本平台签订本协议并同意受本协议约束，使用服务前请认真阅读本协议。</p>
          <p>第一条：立约背景</p>
          <p>为维护农事无忧平台的竞拍活动秩序，规范竞拍人参与竞拍活动的行为，保障用户的合法权益，基于相应的竞拍流程与规则（以下简称：竞拍规则）和农事无忧开放平台公示的规则，特制定本协议。</p>
          <p>第二条：参与竞拍的条件</p>
          <p>同时符合以下条件的用户方能参与平台提供的竞拍活动：</p>
          <p>2.1 用户为农事无忧平台会员，拥有独立的农事无忧平台用户名；</p>
          <p>2.2 同意本协议及竞拍规则中的条款；</p>
          <p>2.3 按照竞拍规则缴纳相应的竞拍保证金；</p>
          <p>第三条：服务说明</p>
          <p>3.1 本平台开展的竞拍活动要求参与竞拍的用户缴纳相应保证金，以取得竞拍资格。用户通过本平台参与竞拍活动前应仔细阅读竞拍规则，并应予以遵守。</p>
          <p>3.2 本平台不提供金额交易渠道，金额交易需竞拍方和卖方通过线下沟通达成共识，涉及到金额交易用户需慎重，若因此造成损失，本平台不承担相关责任。</p>
          <p>第四条：用户权利和义务</p>
          <p>4.1 用户应自行妥善保管用户名及密码，该竞拍行为被认为用户本人参与竞拍活动的行为，用户应自行承担竞拍行为产生的相应义务及责任。</p>
          <p>4.2参与竞拍活动的商品由商家提供，并由商家进行商品信息、商品介绍的上传，商品咨询、配送、及相应的售后服务。用户参与竞拍活动前应仔细查看竞拍活动页面对竞拍商品的信息、商品描述介绍。且知悉认可因摄影、显示等造成描述作品的色调等与原物有误差的，以原物为准。</p>
          <p>4.3用户知悉互联网竞拍活动数据处理的时间间隙，如遇系统提示出价金额与最终出价金额不同，用户认可以最终出价金额为准。</p>
          <p>4.5用户应按照竞拍规则及商家设置的保证金金额缴纳保证金，且在竞拍成功后应在竞拍规则规定的时间内完成付款。用户竞拍成功后未在竞拍规则规定的时间完成付款或在竞拍成功并完成货款支付后申请退款的，将直接扣除用户缴纳的保证金作为违约金用于赔付商家。商家应向用户出具相应凭据，但平台无需就代为扣除的保证金向用户开具发票及收据等相关凭证；</p>
          <p>4.6 保证金的返还：以下两种情况下，将原路退还用户保证金：</p>
          <p>4.6.1用户未成功竞拍商品；</p>
          <p>4.6.2用户竞拍活动成功后，商家主动关闭交易或未履约发货。</p>
          <p>4.7若竞拍成功后，商家具有成交不卖的违规行为，包括但不限于关闭交易或未履约发货，用户将获得由商家提供的金额为所缴纳保证金金额一倍的违约金作为赔偿</p>
          <p>第五条：平台服务及免责条款</p>
          <p>5.1本平台仅为技术服务平台，竞拍活动均由平台各商家举办开展，本平台尽力向用户提供稳定的技术服务，使用户参与的竞拍活动得以顺利进行；用户同意严格按照本协议约定及京东用户注册协议规定使用技术服务。如用户行为违反本协议及平台规则，本平台有权取消用户竞拍资格，由此产生的损失及不利后果由用户自行承担</p>
          <p>5.2 本协议项下的竞拍服务将按“现状”和按“可得到”的状态提供，本平台将在现有技术的基础上尽最大努力提供相应的安全措施以保障服务安全和正常运行。但由于可能存在的计算机病毒、网络通讯故障、浏览器兼容问题、系统维护等方面的因素以及可能发生的不可抗力事件，本平台在此明确声明对技术服务不作任何明示或暗示的保证，包括但不限于：对服务的可适用性、没有错误或疏漏、持续性、准确性、可靠性、适用于某一特定用途。对此用户予以理解并不应要求本平台承担责任。</p>
          <p>5.3 本平台仅向用户提供技术服务平台以便用户与平台商家之间达成竞拍活动相关的交易，本平台并非交易的参与方，不对商家的任何口头、书面陈述或者向上传的线上信息及竞拍活动商品的真实性、合法性做任何明示或暗示的担保，或对此承担任何责任。如因竞拍活动商品交易产生纠纷的，包括但不限于因商家未及时付款给委托方导致用户无法提取拍品的，均由商家以自己的名义独立承担所有的法律责任</p>
          <p>第六条：协议生效及适用</p>
          <p>6.1 用户通过本平台参与竞拍活动即时本协议生效，本协议生效后即表示您选择接受本协议，并同意接受本协议的全部约定内容。</p>
          <p>6.2本协议内容包括协议正文、竞拍规则以及所有本平台已经发布的或将来可能发布的各类规则、操作流程。所有规则为协议不可分割的一部分，与协议正文具有同等的法律效力。本平台有权根据需要不时地制定、修改本协议、附件或各类规则、操作流程，如有任何变更，将在本平台上以公示形式通知用户。任何修订和新规则及流程一经公布即自动生效，成为本协议的一部分。如您继续在本平台参与竞拍活动，则视为您对修改后的条款不持异议并同意遵守。</p>
          <p>第七条：法律适用与争议解决</p>
          <p>7.1 本协议适用中华人民共和国法律。</p>
          <p>7.2 因本协议产生的任何争议，由双方协商解决，协商不成的，任何一方有权向法院提起诉讼。</p>
        </div>
        <div class="tc" slot="footer">
          <Button @click="show=false">不同意</Button>
          <Button type="primary" @click="handleAgree">已阅同意</Button>
        </div>
    </Modal>
    <!-- 保证金支付 -->
    <Modal
        v-model="modal"
        :width="520"
        :mask-closable="false"
        title="保证金支付">
        <div class="">
        </div>
        保证金：{{ pricing.bond }}元。
        <div class="tc" slot="footer">
          <Button @click="modal=false">取消支付</Button>
          <Button type="primary" @click="ok">确认支付</Button>
        </div>
    </Modal>
    <!-- 竞拍成功 订单核对 -->
    <Modal
        v-model="orderModal"
        :width="520"
        :mask-closable="false"
        :closable="false"
        title="订单核对">
        <h5 style="font-size:16px" class="mb20">订单信息</h5>
        <p class="pb5">商品名称：{{ biddingInfo.productName }}</p>
        <p class="pb5">成交价：￥{{ biddingInfo.price }}/{{ info.productAvailabilityUnits }}</p>
        <p class="pb5">成交数量：{{ biddingInfo.number }}{{ info.productAvailabilityUnits }}</p>
        <p class="pb5">保证金：￥{{ biddingInfo.bond }}</p>
        <p class="pb5">剩余应付尾款：￥{{ biddingInfo.remainder }}</p>
        <p class="pb5">送货至：{{ addressInfo.addArea }}，{{ addressInfo.addDetail }}，{{ addressInfo.linkman }}，{{ addressInfo.mobile | filterPhone }}</p>
        <h5 style="font-size:16px" class="mt20 mb20">发票信息</h5>
        <Checkbox v-model="isInvoice" class="mb20" @on-change="handleChangeCheckbox">开具发票</Checkbox>
        <invoiceInfo v-if="isInvoice" ref="invoiceInfo"></invoiceInfo>
        <div class="tc" slot="footer">
          <Button type="primary" @click="submitOrder">提交订单</Button>
        </div>
    </Modal>
    <!-- 竞拍成功支付 -->
    <Modal
        v-model="biddingModal"
        :width="520"
        :mask-closable="false"
        :closable="false"
        title="尾款支付">
        <div class="">
        </div>
        尾款支付。。。
        <div class="tc" slot="footer">
          <Button @click="biddingModal=false">取消支付</Button>
          <Button type="primary" @click="ok3">确认支付</Button>
        </div>
    </Modal>
  </div>
</template>

<script>
import vuiClocker from '~components/clocker/clocker'
import {isPhone2, isEmail2} from '~utils/validate'
import invoiceInfo from '../../components/invoiceInfo'
export default {
  components: {
    vuiClocker,
    invoiceInfo
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
      item: '',
      show: false,
      account: '',
      isDiscount: false,
      isEnd: false,
      discountEndTime: '',
      protocol: false,
      modal: false,
      lastPrice: null,
      price: null,
      number: null,
      commodityId: '',
      participantCount: 0,
      count: 0,
      biddingInfo: {},
      addressInfo: {},
      orderModal: false,
      biddingModal: false,
      avgPrice: 0,
      minPrice: 0,
      sum: 0,
      isInvoice: false,
      showTitle: false
    }
  },
  created () {
    console.log('info', this.info)
    console.log('pricing', this.pricing)
    console.log('delivery', this.delivery)
    console.log('this.router', this.$route)
    this.account = this.$route.query.account
    this.commodityId = this.$route.query.id
    this.getTime()
    // if (this.info.productSalesVolume) {
    //   this.number = this.info.productSalesVolume
    // }
    if (this.$user !== null) {
      this.initRecord()
      // 竞拍成功，还未提交订单，则查询生成订单的相关数据
      if (this.info.biddingStatus === 4 || this.info.biddingStatus === 7 || this.info.biddingStatus === 8) {
        this.initBiddingInfo()
      }
    }
  },
  filters: {
    filterPhone (val) {
      if (val) {
        return `${val.substr(0, 3)}*****${val.substr(8)}`
      }
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
        this.getTime()
        // this.number = this.info.productSalesVolume
        // this.item = this.info.biddingEndTimeStr
      }
    }
  },
  methods: {
    // 计算是否还在活动
    getTime () {
      if (this.pricing.salesWay === '竞价销售') {
        let discountStartTime = new Date(this.pricing.biddingStartTime)
        let discountEndTime = new Date(this.pricing.biddingEndTime)
        this.discountEndTime = this.moment(discountStartTime).format('YYYY-MM-DD HH:MM:SS')
        let currentTime = new Date()
        if (discountStartTime < currentTime && discountEndTime > currentTime) {
          this.isDiscount = true
        } else {
          this.isDiscount = false
        }
        // 说明活动已结束
        if (currentTime > discountEndTime) {
            this.isEnd = true
        }
      } else {
        this.isDiscount = false
      }
    },
    submit () {
      if (this.$user !== null) {
        if (this.account === this.$user.loginAccount) {
          this.$Message.info('不能竞拍自己的产品！')
        } else {
          this.show = true
        }
      } else {
        this.$Message.info('请先登录！')
      }
    },
    // 点击提交订单
    checkOrder () {
        this.orderModal = true
    },
    payReset () {
        // this.biddingModal = true
        this.$router.push('/orderDetails/purchasedGoods')
    },
    handleAgree () {
      this.$router.push(`/goods/auction-order-check?id=${this.commodityId}&account=${this.account}`)
    },
    goToProductionBase () {
        this.$emit('get-base')
        // window.open(`/member/productionBaseDetail?id=${this.info.productionBase}&account=${this.account}`, '_blank')
    },
    detail () {
      console.log('11')
      window.open(this.pricing.biddingProtocol[0].response.data.src, '_blank')
    },
    goToPay () {
      this.modal = true
    },
    ok () {
      // 买家支付保证金
      this.$api.post('/shop/shopBidding/payMargin', {
        buyerAccount: this.$user.loginAccount,   //买家账号
        commodityId: this.commodityId,  //商品ID
      }).then(response => {
        if (response.code === 200) {
          this.modal = false
          this.$Message.success('支付成功！')
        }
      })
    },
    bid () {
        if (this.price - this.lastPrice >= this.pricing.minimum) {
            this.$api.post('/shop/shopBidding/snatch', {
                buyerAccount: this.$user.loginAccount,
                commodityId: this.commodityId,
                price: this.price,
                number: this.number
            }).then(response => {
                if (response.code === 200) {
                    this.$Message.success('出价成功！')
                    this.initRecord()
                }
            })
        } else {
            this.$Message.info(`最小加价不得小于${this.pricing.minimum}元！`)
        }
    },
    // 买家：在商品详情处，查询出价记录
    initRecord () {
        this.$api.post('/shop/shopBidding/bidRecord', {
            buyerAccount: this.$user.loginAccount,
            commodityId: this.commodityId
        }).then(response => {
            if (response.code === 200) {
                if (parseFloat(response.data.price) === 0) {
                    // 说明还没出过价 回写起拍价
                    this.price = parseFloat(this.pricing.startPrice)
                    this.lastPrice = parseFloat(this.pricing.startPrice) // 用于判断加价是否大于最小加价
                } else {
                    // 已经出过价 回写上次的出价
                    this.price = parseFloat(response.data.price)
                    this.lastPrice = parseFloat(response.data.price) // 用于判断加价是否大于最小加价
                }
                if (parseFloat(response.data.number) === 0) {
                    // 说明还没出过价 回写最小起拍量
                    this.number = parseFloat(this.info.productSalesVolume)
                } else {
                    // 已经出过价 回写上次的数量
                    this.number = parseFloat(response.data.number)
                }
                this.participantCount = response.data.participantCount // 参与人数
                this.count = response.data.count // 总出价次数
                this.avgPrice = response.data.avgPrice.toFixed(2) // 平均价（返回的是数字）
                this.minPrice = parseFloat(response.data.minPrice).toFixed(2) // 最低价（返回的是字符串）
                this.sum = response.data.sum // 竞拍数量合计
            }
        })
    },
    // 竞拍成功
    initBiddingInfo () {
        this.$api.post('/shop/shopBidding/marginInfo', {
            commodityId: this.commodityId,
            buyerAccount: this.$user.loginAccount
        }).then(response => {
            console.log('respo', response)
            if (response.code === 200) {
                this.biddingInfo = response.data
                this.addressInfo = response.data.addressInfo
            }
        }).catch(error => {
            this.$Message.error('服务器异常！')
        })
    },
    submitOrder () {
        let money = parseFloat(this.biddingInfo.number) * parseFloat(this.biddingInfo.price) - parseFloat(this.biddingInfo.bond)
        let list = {
            orderCode: this.biddingInfo.orderCode,
            money: money, // 金额+运费
            logisticAmount: 0, // 运费
            addressInfo: JSON.stringify(this.addressInfo), // 地址
            amount: money // 金额
        }
        // 送货上门有运费
        if (this.delivery[0].deliveryMethods === '送货上门' && this.delivery[0].freight !== '') {
            list.logisticAmount += parseFloat(this.delivery[0].freight)
            list.money += parseFloat(this.delivery[0].freight)
        }
        let arr = [{
            account: this.$user.loginAccount,
            freight: list.logisticAmount,
            seller: [{
                commodityId: this.commodityId,
                freight: list.logisticAmount,
                num: this.biddingInfo.number,
                orderCode: this.biddingInfo.orderCode,
                productAvailabilityUnits: this.biddingInfo.unit,
                notarizationCertificate: this.biddingInfo.image ? this.biddingInfo.image : [],
                productPrice: this.biddingInfo.price,
                sellerAccount: this.biddingInfo.sellerAccount,
                productName: this.biddingInfo.productName,
                subtotal: list.money
            }],
            sellerAccount: this.biddingInfo.sellerAccount
        }]
        list.shopProducts = arr
        list.remark = ''
        list.account = this.$user.loginAccount
        list.type = ''
        list.shopType = 4
        // 将组件中的发票信息取出来
        this.invoiceInfo = this.$refs['invoiceInfo'].invoiceInfo
        if (this.invoiceInfo.invoiceMode == '0') { // 0电子发票还是 1纸质发票
            list.invoiceMode = 0
        } else {
            list.invoiceMode = 1
        }
        console.log('list', list)
        // this.invoicePersonal
        // console.log(this.invoiceInfo)
        if (this.isInvoice) { // 勾选了发票
            this.$refs['invoiceInfo'].$refs['invoiceInfo'].validate((valid) => {
                if (valid) {
                    if (this.invoiceInfo.invoiceType == '1') { // 普通发票
                        list.invoiceType = 1
                        list.invoiceInfo = JSON.stringify({
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
                    this.$router.push('/orderDetails/purchasedGoods')
                }
            })
        }
    },
    ok3 () {
        this.biddingModal = false
    },
    // 勾选开具发票
    handleChangeCheckbox (e) {
      if (e) {
        // 说明选择了
        this.$nextTick(() => {
            this.$refs.invoiceInfo.invoiceInfo.invoiceType = '1'
        })
      } else {
        // 说明没有选择需要发票
        this.$nextTick(() => {
            this.$refs.invoiceInfo.invoiceInfo.invoiceType = '0'
        })
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.protocol{
  p{
    padding: 8px 4px;
  }
}
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
          height: 17px;
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
      padding-left: 40px;
      .line{
        position: absolute;
        left: 20px;
        width: 2px;
        height: 20px;
        background: #999;
      }
    }
  }
  .process{
    margin-top: 15px;
    padding: 15px;
    border: 1px solid #f2f2f2;
  }
}
</style>