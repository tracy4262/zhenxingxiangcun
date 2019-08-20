<template>
    <div class="order-contents-list">
        <!-- tableTitle 33-->
        <Row style="background:#f7f7f7;color:#666;" :gutter="32"  type="flex" justify="center" align="middle">
            <Col span="8"> <div class="pd10">商品</div> </Col>
            <Col span="2"> <div class="pd10 tc">单价</div></Col>
            <Col span="2"> <div class="pd10 tc">数量</div>  </Col>
            <Col span="3"> <div class="pd10 tc">总价</div>  </Col>
            <Col span="3"> <div class="pd10 tc">订单状态</div>  </Col>
            <Col span="3"> <div class="pd10 tc">订单生成时间</div>   </Col>
            <Col span="3"> <div class="pd10 tc">订单操作</div>   </Col>
        </Row>
        <div v-if="datas.length">
            <div style="background:#FCFDFE" class="mt20" v-for="(data, index) in datas" :key="index" >                    
                <p class="pd10" style="border:1px solid #f1f1f1"> 
                    <span class="pl10">{{moment(data.createTime).format('YYYY-MM-DD HH:mm:ss')}}</span>
                    <span  class="pl30 pr30">订单号：{{data.orderCode}}</span>
                    <!-- 0 定价，1预售，2面议，3团购，4竞拍 -->
                    <Tag color="primary" v-if="data.shopType == '1'">预售</Tag>
                    <Tag color="#2d8cf0" v-if="data.shopType == '0'">定价</Tag>
                    <Tag color="error" v-if="data.shopType == '2'">面议</Tag>
                    <Tag color="warning"v-if="data.shopType == '3'">团购</Tag>
                    <Tag  color="error" v-if="data.shopType == '4'">竞拍</Tag>
                    <span class="pl30">买家名称: {{data.buyer}}</span>
                    <Button type="text" class="pl30"  @click="handleWebimchat(data.account)"><Icon type="md-text" style="font-size:18px;" class="t-green mr5"></Icon>和买家联系</Button>
                </p>
                <div style="border:1px solid #f1f1f1;border-top:none;">
                    <Row :gutter="32"  type="flex" justify="center" align="middle" >
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
                                            <span class="t-orange pb5"><b>￥{{item.total}}</b></span> <br/>
                                            <span class="pb5">(含运费：￥{{item.logisticAmount}})</span> <br/>
                                            <span class="t-orange pb5">定金￥{{item.pennyTotal}}</span> <br/>
                                            <span class="t-orange pb5">尾款￥{{item.restTotal}}</span> <br/>
                                        </div>
                                        <div v-else-if="data.shopType == '4'">
                                            <span class="t-orange pb5"><b>总价￥{{item.total}}</b></span> <br/>
                                            <span class="pb5">(含运费：￥{{item.logisticAmount}})</span> <br/>
                                            <span class="t-orange pb5">保证金￥{{item.margin}}</span> <br/>
                                            <span class="t-orange pb5">尾款￥{{item.restTotal}}</span>
                                        </div>
                                        <div v-else>
                                            <span class="t-orange pb5">￥{{item.total}}</span> <br/>
                                            <span>(含运费：￥{{item.logisticAmount}})</span>
                                        </div>
                                    </div>  
                                </Col>
                                <Col span="3" class="tc"> 
                                    <div v-if="data.status === 1">待付款</div>  
                                    <div v-if="data.status === 1 && !data.outTime">
                                        {{data.times}}后自动关闭
                                    </div>
                                    <div v-if="data.status === 3">待发货</div>  
                                    <div v-if="data.status === 4">已发货</div>  
                                    <div v-if="data.status === 5 || data.status === 6 || data.status === 7">交易成功</div>
                                    <div v-if="data.status === 10">申请取消</div>
                                    <div v-if="data.status === 12|| data.status === 16 || data.status === 17 || data.status === 11">交易关闭</div>  
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
                                <Col span="12" class="tc"> <div>距离订单创建时间已过去<span class="t-orange">{{data.createTimes}}</span></div></Col>
                                <Col span="12" class="tc"> 
                                    <div class="pd10">
                                        <div  v-if="data.status === 3">
                                            <Poptip transfer confirm title="是否确定已发货？" @on-ok="handleShip(data, data.orderCode, data.shopType)">
                                                <Button type="text" >发货</Button>
                                            </Poptip>
                                        </div>
                                        <div v-if="data.status === 13 || data.status === 14 || data.status === 18">
                                            <Button type="text"  @click="handleReturn(data.orderCode, index, data.status)">退货订单预览</Button>
                                        </div>
                                        <Button type="text" v-if="data.status === 3" @click="handleCancel(data.orderCode, index, data.status)">取消订单</Button>
                                        <div v-if="data.status === 10 || data.status === 11 || data.status === 12 || data.status === 17|| data.status === 19">
                                            <Button type="text"  @click="handleCancelView(data.orderCode, index, data.status)">取消订单预览</Button>
                                        </div>
                                        <Button type="text" @click="handleComplaintsView(data)" v-if="data.hasComplaint">查看投诉</Button>
                                        <div v-if="data.status === 5 || data.status == 6">
                                             <Button type="text" v-if="data.sellStatus  === 1" @click="handleEvaluation(data.shopProducts, data.orderCode, index)">评价</Button>
                                        </div>
                                        <Button type="text" style="color:#00c587" @click="handleOrderDetail(data.orderCode, data.shopType)">订单详情</Button>
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
        <order-detail ref="detail"></order-detail>
        <return-order ref="returnOrder" @on-return="handleReturnOrder"></return-order>
        <complaints-view ref="complaintsView"></complaints-view>
        <cancel-order ref="cancelOrder" @on-cancel="handleCancelOrder"></cancel-order>
        <cancel-order-view ref="cancelOrderView" @on-return="handleReturnOrder"></cancel-order-view>
        <sold-evaluation ref="evaluation" @on-success="handleEvaluationSuccess"></sold-evaluation>
        <outStore ref="outStore"></outStore>
    </div>
</template>
<script>
import orderDetail from './orderDetail'
import cancelOrder from './cancelOrder'
import complaintsView from './complaintsView'
import soldEvaluation from './evaluation'
import returnOrder from './returnOrder'
import cancelOrderView from './cancelOrderView'
import outStore from './outStores'
// import outStore from '../../../inventoryControl/component/outStore'
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
        cancelOrder,
        soldEvaluation,
        returnOrder,
        cancelOrderView,
        complaintsView,
        outStore
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
        // 聊天
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
        // 订单详情
        handleOrderDetail (e, type) {
            // this.$refs['detail'].showModal(e)
            this.$refs['detail'].showModal(e, type)
        },
         // 查看投诉
        handleComplaintsView (data) {
            this.$refs['complaintsView'].showModal(data.sellAccount,data.orderCode)
        },
         // 退货表单预览
        handleReturn (orderCode, index, status) {
            this.index = index
            this.$refs['returnOrder'].showModal(orderCode, 1, status)
        },
        // 同意退货 或者拒绝退货 同意 取消或者拒绝取消 的回调
        handleReturnOrder () {
            this.handleInit()
        },
        // 卖家取消订单
        handleCancel (orderCode, index, status) {
            this.index = index
            this.$refs['cancelOrder'].showModal(orderCode, 1, status)
        },
        // 卖家取消订单回调
        handleCancelOrder () {
            // this.datas[this.index].status = 11
            this.handleInit()
        },
        // 买家取消 ，查询预览
        handleCancelView (orderCode, index, status) {
            this.$refs['cancelOrderView'].showModal(orderCode, 1, status)
        },
        // 评价
         handleEvaluation (list, orderCode, index) {
            this.index = index
            this.$refs['evaluation'].showModal(list, orderCode, 1)
        },
        // 评价成功的回调
        handleEvaluationSuccess () {
            // this.datas[this.index].sellStatus = 7
            this.handleInit()
        },
        // 发货
        handleShip (data, e, type) {
            let arr = []
            data.shopProducts.forEach(element => {
                arr.push(element.productNameId)
            });
            this.$api.post('/shop/shopOrder/updateState', {account: this.$user.loginAccount, status: 4, orderCode: data.orderCode}).then(response => {
                if (response.code === 200) {
                    this.$Message.success('操作成功')
                    this.$refs['outStore'].outStoreInit(e, type)
                    this.$refs['outStore'].outStoreInit(arr)
                    this.handleInit()
                }
            })
        }
    }
}
</script>

