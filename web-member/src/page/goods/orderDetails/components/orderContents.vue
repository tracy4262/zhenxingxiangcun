<template>
    <div class="order-contents-list">
        <!-- tableTitle 我买到的-->
        <Row style="background:#f7f7f7" :gutter="32"  type="flex" justify="center" align="middle">
            <Col span="8"> <div class="pd10">商品</div> </Col>
            <Col span="2"> <div class="pd10 tc">单价</div></Col>
            <Col span="2"> <div class="pd10 tc">数量</div>  </Col>
            <Col span="3"> <div class="pd10 tc">总价</div>  </Col>
            <Col span="3"> <div class="pd10 tc">订单状态</div>  </Col>
            <Col span="3"> <div class="pd10 tc">订单生成时间</div>   </Col>
            <Col span="3"> <div class="pd10 tc">订单操作</div>   </Col>
        </Row>
        <div  v-if="datas.length">
            <div v-for="(data, index) in datas" :key="index" class="mt20" style="background:#FCFDFE">
                <p class="pd10" style="border:1px solid #f1f1f1">
                    <span class="pl10">{{moment(data.createTime).format('YYYY-MM-DD HH:mm:ss')}}</span>
                    <span  class="pl30 pr30">订单号：{{data.orderCode}}</span>
                    <!-- 0 定价，1预售，2面议，3团购，4竞拍 -->
                    <Tag color="primary"  v-if="data.shopType == '1'">预售</Tag>
                    <Tag  color="#2d8cf0" v-if="data.shopType == '0'">定价</Tag>
                    <Tag  color="error" v-if="data.shopType == '2'">面议</Tag>
                    <Tag  color="warning" v-if="data.shopType == '3'">团购</Tag>
                    <Tag  color="error" v-if="data.shopType == '4'">竞拍</Tag>
                    <span class="pl30">卖家名称: {{data.seller}}</span>
                    <Button type="text" class="pl30" @click="handleWebimchat(data.sellerAccount)"><Icon type="md-text" style="font-size:18px;" class="t-green mr5"></Icon> 和卖家联系</Button>
                </p>
                <div style="border:1px solid #f1f1f1;border-top:none;">
                    <Row type="flex" justify="center" align="middle" >
                        <Col span="18" style="border-right:1px solid #f1f1f1">
                            <Row type="flex" align="middle" v-for="(item, index) in data.shopProducts" :key="index" :class="index != data.shopProducts.length - 1 ? 'bd-b-1': ''">
                                <Col span="10">
                                    <Row type="flex" align="middle">
                                        <Col span="7" class="tc pd10">
                                            <img :src="item.productPic" alt="" width="80px" height="80px">
                                        </Col>
                                        <Col span="17">
                                            <div class="pd10">{{item.productName}}</div>
                                        </Col>
                                    </Row>
                                </Col>
                                <Col span="3" class="tc"> <div class="pd10">￥{{item.amount}}</div></Col>
                                <Col span="3" class="tc"> <div class="pd10">{{item.number}} {{item.productOutputUnit}}</div>  </Col>
                                <Col span="5" class="tc">
                                    <div class="pd10">
                                        <div v-if="data.shopType == '1'">
                                            <span class="t-orange pb5"><b>总价￥{{item.total}}</b></span> <br/>
                                            <span class="pb5">(含运费：￥{{item.logisticAmount}})</span> <br/>
                                            <span class="t-orange pb5">定金￥{{item.pennyTotal}}</span> <br/>
                                            <span class="t-orange pb5">尾款￥{{item.restTotal}}</span> <br/>
                                        </div>
                                        <div v-else-if="data.shopType == '4'">
                                            <span class="t-orange pb5"><b>总价￥{{item.total}}</b></span> <br/>
                                            <span class="pb5">(含运费：￥{{item.logisticAmount}})</span><br/>
                                            <span class="t-orange pb5">保证金￥{{item.margin}}</span> <br/>
                                            <span class="t-orange pb5">尾款￥{{item.restTotal}}</span>
                                        </div>
                                        <div v-else>
                                        <span class="t-orange pb5">￥{{item.total}}</span> <br/>
                                            <span>(含运费：￥{{item.logisticAmount}})</span>
                                        </div>
                                    </div>
                                </Col>
                                <!--
                                    待支付 1（订单提交，刚生成订单）、2（订单修改） 15（待支付尾款）
                                    待发货 3 （支付，卖家还未发货）
                                    待收货 4 （卖家已发货，还未确认收货）
                                    待评价 5  （买家已确认收货）
                                    已完成  对卖家来说 7 （卖家 已评价）对买家来说 6 （买家已评价 ）
                                    已关闭 16 （超时未支付） 17（未支付订单取消） 14 （买家申请退货 卖家已同意） 11 （买家申请取消 卖家已同意） 12 （卖家取消订单）
                                    退货/退款 10 （已支付 买家申请取消订单） 13 （买家申请退货） 18 （买家申请退货，卖家拒绝） 19 （买家申请取消，卖家拒绝）
                                 -->
                                <!-- 状态，1.提交状态，2.修改，3.支付，4.发货，5.收货，6 买家已评价 7 卖家已评价，10 买取消 11 卖家取消，12 取消同意，13，退货状态 14 卖家同意退货 15待支付尾款 16 超时未支付订单 （系统自动取消的） 17 未支付订单取消之后 18 退货拒绝 19 取消订单拒绝-->
                                <Col span="3" class="tc">
                                    <div v-if="data.status === 1">待付款</div>
                                    <div v-if="data.status === 1 && !data.outTime">
                                        {{data.times}}后自动关闭
                                    </div>
                                    <div v-if="data.status === 3">已支付待发货</div>
                                    <div v-if="data.status === 4">等待收货</div>
                                    <div v-if="data.status === 5">交易成功</div>
                                    <div v-if="data.status === 6 || data.status === 7">交易成功</div>
                                    <div v-if="data.status === 10">申请取消</div>
                                    <div v-if="data.status === 12 || data.status === 16 || data.status === 17 || data.status === 11">交易关闭</div>
                                    <div v-if="data.status === 13">申请退货</div>
                                    <div v-if="data.status === 14">交易关闭</div>
                                    <div v-if="data.status === 15">
                                        <div v-if="new Date(item.endPaymentTime[1]) > new Date()">
                                            待支付尾款
                                            <p>支付时间：{{item.endPaymentTime[0]}}——{{item.endPaymentTime[1]}}</p>
                                        </div>
                                        <div else>
                                            交易关闭
                                        </div>
                                    </div>

                                    <div v-if="data.status === 18 || data.status === 19">已拒绝</div>
                                </Col>
                            </Row>
                        </Col>
                        <Col span="6">
                            <Row type="flex" align="middle">
                                <Col span="12" class="tc"> <div class="pd10">距离订单创建时间已过去<span class="t-orange">{{data.createTimes}}</span></div></Col>
                                <Col span="12" class="tc">
                                    <div class="pd10">
                                        <div v-if="data.status === 4">
                                            <Poptip transfer confirm title="是否确定收货？" @on-ok="handleConfirm(data)">
                                                <Button type="primary" size="small" >确认收货</Button>
                                            </Poptip>
                                        </div>
                                        <div v-if="data.status === 1">
                                            <Button type="text" @click="handleCancel(data.orderCode, index, data.status)">取消订单</Button>
                                            <Button type="text" @click="handleBuy(data.orderCode, data.shopType)">立即支付</Button>
                                        </div>
                                        <div v-if="data.status === 2 || data.status === 3">
                                            <Button type="text" @click="handleCancel(data.orderCode, index, data.status)">取消订单</Button>
                                        </div>
                                        <div v-if="data.status === 10 || data.status === 11 || data.status === 12 || data.status === 19 || data.status === 17">
                                            <Button type="text"  @click="handleCancelView(data.orderCode, index, data.status)">取消订单预览</Button>
                                        </div>
                                        <div v-if="data.status === 13 || data.status === 18 || data.status === 14">
                                            <Button type="text"  @click="handleReturn(data.orderCode, index, data.status)">退货订单预览</Button>
                                        </div>
                                        <div v-if="data.status === 4">
                                            <Button type="text" @click="handleReturn(data.orderCode, index, data.status)">退货</Button>
                                        </div>
                                        <div v-if="data.status === 5">
                                            <Button type="text"  @click="handleEvaluation(data.shopProducts, data.orderCode, index)">评价</Button>
                                        </div>
                                        <div v-if="data.status === 5 || data.status === 6">
                                            <!-- hasComplaint 是否投诉，默认是0，未投诉 -->
                                            <Button type="text" @click="handleComplaintsView(data)" v-if="data.hasComplaint">查看投诉</Button>
                                            <Button type="text" @click="handleComplaints(data)" v-else>投诉卖家</Button>
                                        </div>
                                        <div v-if="data.status === 15">
                                            <Button type="text" @click="handleBuy(data.orderCode, data.shopType, data)">立即支付</Button>
                                        </div>
                                        <div>
                                            <Button type="text" style="color:#00c587" @click="handleOrderDetail(data.orderCode, data.shopType)">订单详情</Button>
                                        </div>
                                    </div>
                                </Col>
                            </Row>
                        </Col>
                    </Row>
                </div>
            </div>
            <div class="mt30 mb50 tc">
                <Page :total="pages.total" :page-size="pages.pageSize" :current="pages.pageNum" @on-change="handleChangePage"></Page>
            </div>
        </div>
        <div v-else class="tc pd20">
            <p>暂无数据</p>
        </div>
        <order-detail ref="detail" @on-init="handleInit"></order-detail>
        <complaints ref="complaints" @on-ok="handleReturnOrder"></complaints>
        <complaints-view ref="complaintsView"></complaints-view>
        <return-order ref="returnOrder" @on-return="handleReturnOrder"></return-order>
        <cancel-order ref="cancelOrder" @on-cancel="handleCancelOrder"></cancel-order>
        <cancel-order-view ref="cancelOrderView"></cancel-order-view>
        <evaluation ref="evaluation" @on-success="handleEvaluationSuccess"></evaluation>
        <inStore ref="inStore"></inStore>
    </div>
</template>
<script>
import orderDetail from './orderDetail'
import complaints from './complaints'
import complaintsView from './complaintsView'
import returnOrder from './returnOrder'
import cancelOrder from './cancelOrder'
import evaluation from './evaluation'
import cancelOrderView from './cancelOrderView'
import inStore from './inStore'
import vuiClocker from '~components/clocker/clocker'
export default {
    props: {
        datas: {
            type:Array,
            default: ()=>{
                return []
            }
        },
        pages: {
            type: Object,
            default: {}
        }
    },
    components:{
        orderDetail,
        complaints,
        returnOrder,
        cancelOrder,
        evaluation,
        complaintsView,
        cancelOrderView,
        inStore,
        vuiClocker
    },
    data () {
        return {
            loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
            account: '',
            index: '',
            currentPage: 1
        }
    },
    created() {
        this.account = this.loginUser.loginAccount
    },
    methods: {
        getStartTime ($event, item, index) {
            console.log($event)
            if ($event === '00天 00小时 00分 00秒'){
                // this.isDiscount = false
            }
        },
        // 预售商品 立即支付
        handleBuy (e, type, data) {
            console.log(type)
            if (type === '1') { // 预售
                let element = data.shopProducts[0]
                if (element.endPaymentTime[0]) {
                    let startTime = new Date(element.endPaymentTime[0])
                    let endTime = new Date(element.endPaymentTime[1])
                    let currentTime = new Date()
                    if (startTime > currentTime) {
                        this.$Message.error('支付尾款时间还没到')
                    } else if (endTime < currentTime) {
                        this.$Message.error('支付尾款时间已过期')
                    } else {
                        this.$refs['detail'].showModal(e, type, true)
                    }
                }

            } else {
                this.$refs['detail'].showModal(e, type, true)
            }
        },
        handleInit () {
            if (this.datas.length || this.currentPage === 1) {
                this.handleChangePage(this.currentPage)
            } else {
                this.currentPage -=1
                this.handleChangePage(this.currentPage)
            }
        },
        // 翻页
        handleChangePage (e) {
            this.currentPage = e
            this.$emit('on-change', e)
        },
        handleWebimchat (account) {
            this.$api.post('/member/fishing/findAvatar',{account: account}).then(response => {
                if (response.code == 200) {
                    let data = response.data
                    this.webimchat(data.userId, data.name, data.avatar)
                }
            })
        },
        webimchat (userId, name, avatar) {
            layui.layim.chat({
                id: userId,
                name: name,
                avatar: avatar,
                type: 'friend'
            })
        },
        // 买家取消 ，查询预览
        handleCancelView (orderCode, index, status) {
            this.$refs['cancelOrderView'].showModal(orderCode, 0, status)
        },
        // 订单详情
        handleOrderDetail (e, type) {
            console.log(type)
            this.$refs['detail'].showModal(e, type)
        },
        // 投诉
        handleComplaints (data) {
            this.$refs['complaints'].showModal(data.sellerAccount,data.orderCode)
        },
        // 查看投诉
        handleComplaintsView (data) {
            this.$refs['complaintsView'].showModal(data.sellerAccount,data.orderCode)
        },
        // 退货
        handleReturn (orderCode, index, status) {
            this.index = index
            this.$refs['returnOrder'].showModal(orderCode, 0, status)
        },
        // 退货回调
        handleReturnOrder () {
            this.handleInit()
        },
        // handleCancel取消订单
        handleCancel (orderCode, index, status) {
            this.index = index
            this.$refs['cancelOrder'].showModal(orderCode, 0, status)
            // if (status == 1) {
            //     this.cancel(orderCode)
            // } else if (status == 2) {
            //     this.cancel(orderCode)
            // } else if (status == 3) {
            //     this.$refs['cancelOrder'].showModal(orderCode, 0, status)
            // }
        },
        cancel (orderCode) {
            // this.$api.post('/shop/shopOrder/updateState', {account: this.$user.loginAccount, status: 16, orderCode: orderCode}).then(response => {
            //     if (response.code === 200) {
            //         this.handleInit()
            //     }
            // })
        },
        // 买家取消订单回调
        handleCancelOrder () {
            // this.datas[this.index].status = 10
            this.handleInit()
        },
        // 评价
        handleEvaluation (list, orderCode, index) {
            this.index = index
            console.log(list)
            this.$refs['evaluation'].showModal(list, orderCode, 0)
            // this.$refs['evaluation'].showModal(orderCode, 0)
        },
        // 评价成功的回调
        handleEvaluationSuccess () {
            // this.datas[this.index].status = 6
            this.handleInit()
        },
        // 确认收货
        handleConfirm (data) {
            let arr = []
            data.shopProducts.forEach(element => {
                arr.push(element.productNameId)
            });
            this.$api.post('/shop/shopOrder/updateState', {account: this.$user.loginAccount, status: 5, orderCode: data.orderCode}).then(response => {
                if (response.code === 200) {
                    this.$Message.success('操作成功')
                    this.$refs['inStore'].initAdd(arr)
                    this.handleInit()
                }
            })
        }
    }
}
</script>

<style lang="scss">
.order-contents-list {
    .bd-b-1{
        border-bottom:1px solid #f1f1f1;
    }
}
</style>
