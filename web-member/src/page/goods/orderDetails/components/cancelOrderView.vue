<template>
    <div>
        <Modal
            v-model="show"
            title="取消订单预览"
            width="800"
            :mask-closable="false">
            <div>
                <div class="pl40 pr20">
                    <Form ref="data" :model="data" :label-width="100" :rules="ruleInline" label-position="left" >
                        <FormItem label="取消原因：" prop="reason">
                            {{data.reason}}
                        </FormItem>
                        <FormItem label="取消说明：" prop="describeInfo">
                            {{data.describeInfo}}
                        </FormItem>
                        <FormItem label="上传图片：" prop="picList">
                             <div>
                                <Row>
                                    <Col span="8" class="pd10" v-for="(item, index) in data.picUrl" :key="index">
                                        <img :src="item" alt="" width="116px" height="116px">
                                    </Col>
                                </Row>
                            </div>
                        </FormItem>
                    </Form>
                </div>
                <!-- 卖家 或者 已经拒绝 或者已经同意 -->
                <div v-if="(isType === 1 || status == 19 || status == 12) && status != 11 && status != 17">
                    <p class="pt30 pb20 mt40 pl20" style="border-top: 1px solid #eee;">订单金额：{{addressInfo.total}}元</p>
                    <div class="pl20 pr20">
                        <Form ref="info" :label-width="110" label-position="left" :model="info" :rules="infoRuleInline">
                        <FormItem  label="确认退款金额：" :prop="status == 10 ? 'refund' : 'refund'">
                            <Input :maxlength="20" style="width: 220px;" v-if="status == 10" v-model="info.refund" @on-change="refundChange" @on-blur="refundChange">
                                 <span slot="append" >元</span>
                            </Input>
                            <span v-else>{{info.refund}} 元</span>
                        </FormItem>
                        <FormItem label="收货人姓名：">
                            {{addressInfo.linkman}}
                        </FormItem>
                        <FormItem label="联系电话：">
                            {{addressInfo.mobile}}
                        </FormItem>
                        <FormItem label="收货地址：">
                            {{addressInfo.addArea}},{{addressInfo.addDetail}}
                        </FormItem>
                        <FormItem label="处理备注：">
                            <Input type="textarea" v-if="status == 10" v-model="info.remark" :maxlength="200" :autosize="{minRows: 3, maxRows: 5}" placeholder="请输入"/>
                            <span v-else>{{info.remark}}</span>
                        </FormItem>
                        </Form>
                    </div>
                </div>
            </div>
                <div slot="footer" class="tc">
                    <Button type="default" v-if="status == 10 && isType === 1" @click="show = false">取消</Button>
                    <Button type="primary" v-if="status == 10 && isType === 1" @click.native="handleOk(12)">确认取消</Button>
                    <Button type="primary" v-if="status == 10 && isType === 1" @click.native="handleOk(19)">拒绝取消</Button>
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
                data: {
                    orderCodeId: '', // 订单号
                    reason: '' ,// 退货原因
                    describeInfo:'', // 退货说明
                    picList: '' // 上传凭证 5张
                },
                ruleInline: {
                    reason: [
                        {required: true,message: '请选择退款原因', trigger: 'blur' }
                    ]
                },
                infoRuleInline: {
                    refund: [
                        {required: true, message:'请填写退款金额', trigger: 'blur'}
                    ]
                },
                info: {
                    refund: '',
                    remark: ''
                },
                addressInfo: {},
                account: '',
                isType: 0,
                status: ''
            }
        },
        created() {
            this.account = this.$user.loginAccount
        },
        methods: {
            refundChange () {
                // console.log(this.info.refund)
                // if (parseFloat(this.info.refund ).toFixed(2) > this.addressInfo.total) {
                //     console.log('000')
                //     console.log(this.addressInfo.total)
                //     this.info.refund = `${this.addressInfo.total}`
                //     console.log(this.info.refund)
                // }
            },
            handleOk (status) { // 卖家操作 同意退货 或者拒绝退货
                this.$refs['info'].validate((valid) => {
                    if (valid) {
                        let url = '/shop/shopOrderOperate/order/cancel'
                        let list = {
                            orderCode: this.data.orderCodeId,
                            fromAccount: 1, // 0 买家 1卖家
                            type: '1', // 1 取消 2 退款
                            // account: this.addressInfo.account,
                            account: this.$user.loginAccount,
                            reason: this.data.reason,
                            describeInfo: this.data.describeInfo,
                            picUrl: this.data.picUrl,
                            status: status, // 
                            refund: this.info.refund,
                            remark: this.info.remark,
                        }
                        console.log(list)
                        this.$api.post(url, list).then(response => {
                            if (response.code === 200) {
                                this.$Message.success('操作成功')
                                this.$emit('on-return')
                                this.show = false
                            }
                        })
                    } else {
                        this.$Message.error('请核对表单信息')
                    }
                })
            },
            // 对话框显示
            showModal (orderCodeId, type, status) {
                this.isType =  type
                this.status = status
                this.show = true
                let list = {
                  orderCode: orderCodeId
                }
                this.$api.post('/shop/shopOrder/detail/code', {orderCode : orderCodeId}).then(response => {
                    if (response.code === 200) {
                        let info = response.data
                        let total = 0
                        info.shopProducts.forEach(element => {
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
                            total = parseFloat((numAdd(total, element.subTotal)).toFixed(2))
                        })
                        this.addressInfo = response.data.addressInfo
                        this.addressInfo.total = total
                        this.addressInfo.account = info.account
                    }
                })
                this.$api.post('/shop/shopOrderOperate/list/findById', list).then(response => {
                    if (response.code === 200) {
                        this.data = response.data
                        this.data.orderCodeId = orderCodeId
                            console.log(this.data)
                        if (this.data.remark) {
                            this.info.remark = this.data.remark
                        } else {
                            this.info.remark = ''
                        }
                        if (this.data.refund) {
                            this.info.refund = this.data.refund + ''
                        } else {
                            this.info.refund = ''
                        }
                    }
                })
            }
        }
    }
</script>