<template>
    <div>
        <Modal
            v-model="show"
            :title="title"
            width="1000"
            :mask-closable="false">
            <div>
                <p class="pd20" style="font-size: 16px">收货地址</p>
                <div class="pl40 pr20">
                    <Form :label-width="100" label-position="left" class="vui-form-sm">
                        <FormItem label="姓名：">
                        {{addressInfo.linkman}}
                        </FormItem>
                        <FormItem label="联系电话：">
                        {{addressInfo.mobile}}
                        </FormItem>
                        <FormItem label="收货地址：">
                        {{addressInfo.addArea}} , {{addressInfo.addDetail}}
                        </FormItem>
                    </Form>
                </div>
                <p class="pd20 mt30" style="font-size: 16px">订单信息</p>
                <div class="pl20 pr20">
                    <Row type="flex" justify="center" align="middle">
                        <Col span="10"> <div class="pd10">商品信息</div> </Col>
                        <Col span="4"> <div class="pd10 tc">单价</div></Col>
                        <Col span="5"> <div class="pd10 tc">数量</div>  </Col>
                        <Col span="5"> <div class="pd10 tc">小计</div>  </Col>
                    </Row>
                    <div style="border:1px solid #f1f1f1;border-top:none;background:#FCFDFE;">
                        <Row type="flex" align="middle" v-for="(item, index) in data.shopProducts" :key="index">
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
                            <Col span="4" class="tc"> <div class="pd10">￥{{item.amount}}</div></Col>
                            <Col span="5" class="tc"> <div class="pd10">{{item.number}}件</div>  </Col>
                            <Col span="5" class="tc"> 
                                <div class="pd10" v-if="buy == '1'">
                                    <p class="t-orange pb5"><b>总价￥{{item.subTotal}}</b></p>
                                    <p class="pb5">(含运费：￥{{item.logisticAmount}})</p>
                                    <p class="t-orange pb5">定金￥{{item.pennyTotal}}</p>
                                    <p class="t-orange pb5">尾款￥{{item.restTotal}}</p>
                                </div>  
                                <div class="pd10" v-else-if="buy == '4'">
                                    <p class="t-orange pb5"><b>总价￥{{item.subTotal}}</b></p>
                                    <p class="pb5">(含运费：￥{{item.logisticAmount}})</p>
                                    <p class="t-orange pb5" >保证金￥{{item.margin}}</p>
                                    <p class="t-orange pb5">尾款￥{{item.restTotal}}</p>
                                </div>  
                                <div class="pd10" v-else>
                                    <p class="t-orange pb5">￥{{item.subTotal}}</p>
                                    <p>(含运费：￥{{item.logisticAmount}})</p>
                                </div>  
                            </Col>
                        </Row>
                    </div>
                </div>
                <p class="pd20 mt30" style="font-size: 16px">发票信息</p>
                <div class="pl40 pr20">
                    <Form :label-width="130" label-position="left" class="vui-form-sm" v-if="data.invoiceType !== 0">
                        <FormItem label="开票方式：">
                            <span v-if="data.invoiceMode === 0">电子发票</span>
                            <span v-if="data.invoiceMode === 1">纸质发票</span>
                        </FormItem>
                        <FormItem label="开票类型：">
                            <span v-if="data.invoiceType === 1">普通发票</span>
                            <span v-if="data.invoiceType === 2">增值税专用发票</span>
                        </FormItem>
                        <div v-if="data.invoiceType === 2">
                            <FormItem label="单位名称：">
                                {{invoiceInfo.unitName}}
                            </FormItem>
                            <FormItem label="纳税人识别码：">
                                {{invoiceInfo.identificationCode}}
                            </FormItem>
                            <FormItem label="注册地址：">
                                {{invoiceInfo.registerAddress}}
                            </FormItem>
                            <FormItem label="注册电话：">
                                {{invoiceInfo.registerTelephone}}
                            </FormItem>
                            <FormItem label="开户银行：">
                                {{invoiceInfo.accountBank}}
                            </FormItem>
                            <FormItem label="银行账户：">
                                {{invoiceInfo.bankAccount}}
                            </FormItem>
                        </div>
                        <div v-else>  
                            <FormItem label="单位名称" v-if="invoiceInfo.unitName">
                                {{invoiceInfo.unitName}}
                            </FormItem>
                            <FormItem label="纳税人识别码" v-if="invoiceInfo.identificationCode">
                                {{invoiceInfo.identificationCode}}
                            </FormItem>
                             <FormItem label="收票人手机号：">
                                {{invoiceInfo.mobile}}
                            </FormItem>
                            <FormItem label="收票人邮箱：" v-if="invoiceInfo.email">
                                {{invoiceInfo.email}}
                            </FormItem>
                        </div>
                    </Form>
                    <p v-else>没有开发票</p>
                </div>
            </div>
            <div slot="footer" class="tc">
                <Button @click="show = false" v-if="showBuy">取消</Button>
                <Button @click="handleBuy" type="primary" v-if="showBuy">立即支付</Button>
                <!-- <Button type="primary" v-else>确定</Button> -->
            </div>
        </Modal>
    </div>
</template>
<script>
import {numMulti, numAdd, Subtr} from '~utils/utils'
    export default {
        data () {
            return {
                show: false,
                buy: 1,
                title: '订单详情',
                data: {
                    shopProducts: []
                },
                addressInfo: {},
                invoiceInfo: {},
                orderCode: '',
                showBuy: false
            }
        },
        methods: {
            // 点击支付
            handleBuy () {
               this.$api.post('/shop/shopOrder/updateState', {account: this.$user.loginAccount, status: 3, orderCode: this.orderCode}).then(response => {
                   if (response.code === 200) {
                       this.$emit('on-init')
                       this.show =false
                   }
               }) 
            },
            // 对话框显示
            showModal (e, buy, showBuy) {
                this.showBuy = showBuy
                this.orderCode = e
                this.buy = buy
                this.show = true
                this.showBuy ? this.title = '订单核对' : this.title = '订单详情'
                this.$api.post('/shop/shopOrder/detail/code', {orderCode : e}).then(response => {
                    if (response.code === 200) {
                        this.data = response.data
                        let margin = this.data.margin
                        this.data.shopProducts.forEach(element => {
                            // <!-- 0 定价，1预售，2面议，3团购，4竞拍 -->
                            if(this.buy == '1') { // 说明是 预售订单 '1' 
                                // pennyTotal 定金金额
                                element.pennyTotal = parseFloat((numMulti(element.amount, element.number)).toFixed(2))
                                let total = parseFloat((numMulti(element.orderPrice, element.number)).toFixed(2))
                                let restTotal = parseFloat((numAdd(total, element.logisticAmount)).toFixed(2))
                                // restTotal尾款
                                element.restTotal = Subtr(restTotal, element.pennyTotal)
                                element.amount = element.orderPrice
                                // 总金额
                                element.total = parseFloat((numMulti(element.orderPrice, element.number)).toFixed(2))
                                element.subTotal = parseFloat((numAdd(element.total, element.logisticAmount)).toFixed(2))
                            } else if (this.buy == '4') {
                                element.total = parseFloat((numMulti(element.amount, element.number)).toFixed(2))
                                element.subTotal = parseFloat((numAdd(element.total, element.logisticAmount)).toFixed(2))
                                // margin 保证金 restTotal 剩余需要支付的金额
                                element.margin = margin
                                element.restTotal = Subtr(element.subTotal, element.margin)

                            } else { // 其他订单查看详情 '0'
                                element.total = parseFloat((numMulti(element.amount, element.number)).toFixed(2))
                                element.subTotal = parseFloat((numAdd(element.total, element.logisticAmount)).toFixed(2))
                            }
                        })
                        this.addressInfo = response.data.addressInfo
                        if (response.data.invoiceInfo) {
                            this.invoiceInfo = response.data.invoiceInfo
                        }
                    }
                })
            }
        }
    }
</script>