<template>
<div>
    <!-- 我买到的 -->
    <Tabs :value="tabActive"  :animated="false" @on-click="handleTabsClick">
        <TabPane label="全部订单" name="allDetail">
            <order-list ref="allDetail" :data="allDetailData" :pages="pages" @on-search="handleSearch" :searchStatus="true" @on-change="handleChangePage"></order-list>
        </TabPane>
        <TabPane label="待付款" name="pendingPayment">
            <order-list ref="pendingPayment" :data="allDetailData" :pages="pages" @on-search="handlePendingPayment" :evaluation="false" @on-change="handleChangePage"></order-list>
        </TabPane>
         <TabPane label="待发货" name="toBeDelivered">
            <order-list ref="toBeDelivered" :data="allDetailData" :pages="pages" @on-search="handleToBeDelivered" :evaluation="false" @on-change="handleChangePage"></order-list>
        </TabPane>
        <TabPane label="待收货" name="toReceiveGoods">
            <order-list ref="toReceiveGoods" :data="allDetailData" :pages="pages" @on-search="handleToReceiveGoods" :evaluation="false" @on-change="handleChangePage"></order-list>
        </TabPane>
        <TabPane label="待评价" name="beEvaluated">
            <order-list ref="beEvaluated" :data="allDetailData" :pages="pages" @on-search="handleBeEvaluated" :evaluation="false" @on-change="handleChangePage"></order-list>
        </TabPane>
        <TabPane label="已完成" name="completed">
            <order-list ref="completed" :data="allDetailData" :pages="pages" @on-search="handleCompleted" :evaluation="false" @on-change="handleChangePage"></order-list>
        </TabPane>
        <TabPane label="已关闭" name="closedOrders">
            <order-list ref="closedOrders" :data="allDetailData" @on-search="handleClosedOrders" :evaluation="false" :pages="pages" @on-change="handleChangePage"></order-list>
        </TabPane>
        <TabPane label="退货/退款" name="cancelled">
            <order-list ref="cancelled" :data="allDetailData" @on-search="handlehandleCancelled" :evaluation="false" :pages="pages" @on-change="handleChangePage"></order-list>
        </TabPane>
    </Tabs>
</div>
</template>
<script>
import orderList from './components/orderList'
import {numMulti, numAdd, Subtr} from '~utils/utils'
import {timeFormat} from './components/mixins'
export default {
    name: 'purchasedGoods',
    components: {
        orderList
    },
    mixins: [timeFormat],
    data() {
        return {
            tabActive: 'allDetail',
            allDetailData:[],
            account: '',
            loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
            pages: {
                pageNum: 1,
                pageSize: 5,
                total: 0,
            },
            list: {
                from: 0, // 0 我买到的 1 我卖出的
                state: 0, // tab栏状态 0 全部 1等待发货 2等到收货 3等待评价
                dealState: 0, // 交易状态 0 全部状态 1等待发货 2等到收货 3等待评价 4已取消
                judgeState: 0, // 评价状态 0 全部状态 1未评价 2已评价 3双方已评价
                productName: '', // 商品名称
                buyer: '', // 买家名称 不支持模糊搜索
                seller: '', // 卖家名称 不支持模糊搜索
                startDate: '', // 成交时间 开始时间
                endDate: '' // 成交时间 结束时间
            }
        }
    },
    created() {
        this.account = this.loginUser.loginAccount
        this.handleGetInit(this.list)
    },
    methods: {
        // 翻页
        handleChangePage (e) {
            this.pages.pageNum = e
            this.handleGetInit(this.list)
        },
        // 初始化获取数据
        handleGetInit (e) {
            let list = e
            this.$api.post('/shop/shopOrder/list',{account: this.account, page: this.pages, query: list}).then(response => {
                if (response.code === 200) {
                    this.allDetailData = response.data.data
                    this.pages.total = response.data.total
                    this.allDetailData.forEach(e => {
                        let now_time = Date.parse(new Date());//当前时间的时间戳
                        let end_time = Date.parse(new Date(e.createTime));//指定时间的时间戳
                        let current_time = now_time - end_time
                        let days = Math.floor(current_time/(24*3600*1000))
                        if (days >= 1) {
                            e.outTime = true
                        } else {
                            e.outTime = false
                            let time_dis = Date.parse(new Date(e.createTime)) + 24*3600*1000 - Date.parse(new Date())
                            //计算出小时数
                            var leave1=time_dis%(24*3600*1000);//计算天数后剩余的毫秒数
                            var hours=Math.floor(leave1/(3600*1000));
                            //计算相差分钟数
                            var leave2=leave1%(3600*1000);//计算小时数后剩余的毫秒数
                            var minutes=Math.floor(leave2/(60*1000));
                            //计算相差秒数
                            var leave3=leave2%(60*1000);//计算小时数后剩余的毫秒数
                            var second = leave3/1000;
                            e.times = `${hours}小时${minutes}分`
                        }
                        e.createTimes = this.timeFormat(e.createTime)
                        e.shopProducts.forEach(element => {
                            // 预售商品
                            if (e.shopType == '1') {
                                // pennyTotal 定金 restTotal 尾款
                                element.pennyTotal = parseFloat((numMulti(element.amount, element.number)).toFixed(2))
                                let total = parseFloat((numMulti(element.orderPrice, element.number)).toFixed(2))
                                let restTotal = parseFloat((numAdd(total, element.logisticAmount)).toFixed(2))
                                element.restTotal = Subtr(restTotal, element.pennyTotal)
                                element.amount = element.orderPrice
                                // element.startTime = '2019-04-25 15:44:00'
                                // element.endTime = '2019-04-25 15:47:00'
                                // if (element.endPaymentTime[0]) {
                                //     let startTime = new Date(element.endPaymentTime[0])
                                //     let endTime = new Date(element.endPaymentTime[1])
                                //     let currentTime = new Date()
                                //     if (startTime < currentTime && endTime > currentTime) {
                                //         e.isDiscount = true
                                //     } else {
                                //         e.isDiscount = false
                                //     }
                                // }
                            }
                            element.total = parseFloat((numMulti(element.amount, element.number)).toFixed(2))
                            element.total = parseFloat((numAdd(element.total, element.logisticAmount)).toFixed(2))
                            // 竞价商品
                            if (e.shopType == '4') {
                                // margin 保证金 restTotal 剩余需要支付的金额
                                element.restTotal = Subtr(element.total, element.margin)
                            }
                        })
                    })
                }
            })
        },
        
        // 搜全部
        handleSearch (e) {
            this.pages.pageNum = 1
            e.state = e.dealState
            this.list = e
            this.handleGetInit(e)
        },
        // 搜索 待付款
        handlePendingPayment (e) {
            this.pages.pageNum = 1
            e.state = 6
            this.list = e
            this.handleGetInit(e)
        },
         // 搜索等待发货
        handleToBeDelivered (e) {
            this.pages.pageNum = 1
            e.state = 1
            this.list = e
            this.handleGetInit(e)
        },
        // 搜等待收货
        handleToReceiveGoods (e) {
            this.pages.pageNum = 1
            e.state = 2
            this.list = e
            this.handleGetInit(e)
        },
        // 搜 等待评价
        handleBeEvaluated (e) {
            this.pages.pageNum = 1
            e.state = 3
            this.list = e
            this.handleGetInit(e)
        },
        // 搜 已完成
        handleCompleted (e) {
            this.pages.pageNum = 1
            e.state = 4
            this.list = e
            this.handleGetInit(e)
        },
        // 关闭的订单
        handleClosedOrders (e) {
            this.pages.pageNum = 1
            e.state = 5
            this.list = e
            this.handleGetInit(e)
        },
        // 退货/退款
        handlehandleCancelled (e) {
            this.pages.pageNum = 1
            e.state = 7
            this.list = e
            this.handleGetInit(e)
        },
        handleTabsClick (e) {
            this.$refs[e].hendleInitSearch()
        }
    }
}
</script>
