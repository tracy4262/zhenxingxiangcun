<template>
    <div class="order-contents-list">
        <Row style="background:#f7f7f7" :gutter="32"  type="flex" justify="center" align="middle">
            <Col span="8"><div class="pd10">竞拍商品</div></Col>
            <Col span="3"><div class="pd10 tc">竞拍出价</div></Col>
            <Col span="3"><div class="pd10 tc">竞拍数量</div></Col>
            <Col span="3"><div class="pd10 tc">出价时间</div></Col>
            <Col span="7"><div class="pd10 tc">操作</div></Col>
        </Row>
        <div v-if="data.length">
            <div v-for="(item, index) in data" :key="index" class="mt20"  style="background:#FCFDFE">
                <p class="pd10" style="border:1px solid #f1f1f1"> 
                    <span class="pl10">商家：{{ item.memberName }}</span>
                    <Icon type="md-text" style="font-size:18px;" class="t-green mr5" @click="handleWebimchat(item.sellAccount)"></Icon>
                    <span class="pl30">竞拍编号：{{ item.order }}</span>
                    <span class="pl30">开拍时间：{{ item.startTime }}</span>
                </p>
                <div style="border:1px solid #f1f1f1;border-top:none;">
                    <Row type="flex" justify="center" align="middle">
                        <Col span="8">
                            <Row class="pd10" type="flex" justify="center" align="middle">
                                <Col span="6">
                                    <img v-if="item.image" :src="item.image[0]" width="80" height="80" />
                                    <img v-else src="../../../../../static/img/goods-list-no-picture.png" />
                                </Col>
                                <Col span="18">
                                    <p class="pd10">{{ item.productName }}</p>
                                </Col>
                            </Row>
                        </Col>
                        <Col span="3"><div class="pd10 tc">{{ item.price === '' ? '——' : '￥'+item.price }}</div></Col>
                        <Col span="3">
                            <div class="pd10 tc">{{ item.number === '' ? '——' : item.number + item.unit }}</div>
                        </Col>
                        <Col span="3"><div class="pd10 tc">{{ item.payTime === '' ? '——' : item.payTime }}</div></Col>
                        <Col span="7">
                            <div class="pd10 tc">
                                <Button type="primary" @click="detail(item)">查看商品详情</Button>
                                <Button type="primary" class="ml10" v-if="item.status === 4" @click="submit(item)">订单核对</Button>
                                <Button type="warning" class="ml10" v-if="item.status === 7 || item.status === 8">已转订单</Button>
                            </div>
                        </Col>
                    </Row>
                </div>
            </div>
            <div class="mt30 mb50 tc">
                <Page :total="total" :page-size="pageSize" :current="pageNum" @on-change="handleChangePage"></Page>
            </div>
        </div>
        <div v-else class="tc pd20">
            <p>暂无数据</p>
        </div>
        <!-- 竞拍成功 订单核对 -->
        <Modal
            v-model="orderModal"
            :width="520"
            :mask-closable="false"
            :closable="false"
            title="订单核对">
            <h5 style="font-size:16px" class="mb20">订单信息</h5>
            <p class="pb5">商品名称：{{ biddingInfo.productName }}</p>
            <p class="pb5">成交价：￥{{ biddingInfo.price }}/{{ this.biddingInfo.unit }}</p>
            <p class="pb5">成交数量：{{ biddingInfo.number }}{{ this.biddingInfo.unit }}</p>
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
    data () {
        return {
            data: [],
            pageNum: 1,
            pageSize: 10,
            orderModal: false,
            biddingInfo: {},
            addressInfo: {},
            isInvoice: false,
            showTitle: false,
            delivery: [],
            commodityId: ''
        }
    },
    filters: {
        filterPhone (val) {
            if (val) {
                return `${val.substr(0, 3)}*****${val.substr(8)}`
            }
        }
    },
    created () {
    },
    methods: {
        init () {
            this.$api.post('/shop/shopBidding/hasBid', {
                buyerAccount: this.$user.loginAccount,
                pageNum: this.pageNum,
                pageSize: this.pageSize
            }).then(response => {
                console.log('my isBidden list response', response)
                if (response.code === 200) {
                    this.data = response.data.list
                    this.total = response.data.total
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        handleWebimchat (account) {
            console.log('111')
            /* this.$api.post('/portal/shopCommdoity/findLoginUser',{account: account}).then(response => {
                if (response.code == 200) {
                    let data = response.data
                    this.webimchat(data.userId, data.name, data.avatar)
                }
            }) */
        },
        // 翻页
        handleChangePage (e) {
            this.pageNum = e
            this.init()
        },
        detail (item) {
            this.$router.push(`/goods/newDetail?id=${item.commodityId}&account=${item.sellerAccount}`)
        },
        submit (item) {
            this.commodityId = item.commodityId
            this.$api.post('/shop/shopBidding/marginInfo', {
                commodityId: item.commodityId,
                buyerAccount: this.$user.loginAccount
            }).then(response => {
                console.log('respo', response)
                if (response.code === 200) {
                    this.biddingInfo = response.data
                    this.addressInfo = response.data.addressInfo
                    this.$api.post('/shop/commodityDetail/findCommodityDetailInfo', {
                        pushShopCommodityId: item.commodityId,
                        account: this.$user.loginAccount
                    }).then(res =>{
                        if (res.code === 200) {
                            this.delivery = res.data.delivery
                            this.orderModal = true
                        }
                    })
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
                                window.location.href = '/orderDetails/purchasedGoods'
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
                        window.location.href = '/orderDetails/purchasedGoods'
                    }
                })
            }
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
