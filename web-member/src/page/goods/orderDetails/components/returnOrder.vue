<template>
    <div>
        <Modal
            v-model="show"
            :title="title"
            width="800"
            :mask-closable="false">
            <div> 
                <div class="pl20 pr20">
                    <Form  ref="data" :model="data" :label-width="100" label-position="left" :rules="ruleInline" v-if="isType === 0">
                        <FormItem label="退货原因：" :prop="status == 4 ? 'reason' : 'reason'">
                            <Select v-if="status == 4" v-model="data.reason" style="width:100%">
                                <Option v-for="item in reasonsForReturns" :value="item.label" :key="item.label">{{ item.label }}</Option>
                            </Select>
                            <span v-else>{{data.reason}}</span>
                        </FormItem>
                        <FormItem label="退款说明：" prop="describeInfo">
                            <Input v-if="status == 4" v-model="data.describeInfo" type="textarea" :autosize="{minRows: 3,maxRows: 5}" :maxlength="50"></Input>
                            <span v-else>{{data.describeInfo}}</span>
                        </FormItem>
                        <FormItem label="上传图片：" prop="picUrl">
                            <div v-if="status == 4">
                                <vui-upload
                                    ref="upload"
                                    @on-getPictureList="getPictureList"
                                    :total="5"
                                    :hint="'图片大小小于2M'"
                                    ></vui-upload>
                            </div>
                            <div v-else>
                                <Row>
                                    <Col span="8" class="pd10" v-for="(item, index) in data.picUrl" :key="index">
                                        <img :src="item" alt="" width="116px" height="116px">
                                    </Col>
                                </Row>
                            </div>
                        </FormItem>
                    </Form>
                     <Form ref="data" :model="data" :label-width="100" label-position="left" :rules="ruleInline" v-if="isType === 1">
                        <FormItem label="退货原因：">
                            {{data.reason}}
                        </FormItem>
                        <FormItem label="退款说明：" prop="describeInfo">
                            {{data.describeInfo}}
                        </FormItem>
                        <FormItem label="上传图片：" prop="picUrl">
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
                <div v-if="isType === 1 || status == 18 || status == 14">
                    <p class="pt30 pb20 mt40 pl20" style="border-top: 1px solid #eee;">订单金额：{{addressInfo.total}}元</p>
                    <div class="pl20 pr20">
                        <Form ref="info" :label-width="110" label-position="left" :model="info" :rules="infoRuleInline">
                        <FormItem  label="确认退款金额：" prop="refund">
                            <Input :maxlength="20" style="width: 220px;" v-if="status == 13" v-model="info.refund" @on-change="refundChange" @on-blur="refundChange">
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
                            <Input type="textarea" v-if="status == 13" v-model="info.remark" :maxlength="200" :autosize="{minRows: 3, maxRows: 5}" placeholder="请输入"/>
                            <span v-else>{{info.remark}}</span>
                        </FormItem>
                        </Form>
                    </div>
                </div>
            </div>
                <div slot="footer" class="tc">
                    <div v-if="isType == 1 && status == 13">
                        <Button type="default" @click="show = false" >取消</Button>
                        <Button type="primary" @click.native="handleOk(14)">确认退货</Button>
                        <Button type="primary" @click.native="handleOk(18)">拒绝退货</Button>
                    </div>
                    <div v-if="isType == 0 && status == 4">
                        <Button type="default" @click="show = false" >取消</Button>
                        <Button type="primary" @click.native="ok">确定</Button>
                    </div>
                </div>
        </Modal>
    </div>
</template>
<script>
    import {isPhone2} from '~utils/validate'
    import vuiUpload from '~components/vui-upload'
    import {numMulti, numAdd, Subtr} from '~utils/utils'
    export default {
        components: {
            vuiUpload
        },
        data () {
            return {
                show: false,
                data: {
                    orderCodeId: '', // 订单号
                    reason: '' ,// 退货原因
                    describeInfo:'', // 退货说明
                    picUrl: '' // 上传凭证 5张
                },
                reasonsForReturns: [
                    {label: '质量问题'},
                    {label: '与承诺不符'},
                    {label: '诚信问题'},
                    {label: '其他问题'}
                ],
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
                loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
                account: '',
                isType: 0,
                title: '退货订单',
                status: '',
                addressInfo: {}
            }
        },
        created() {
            this.account = this.loginUser.loginAccount
        },
        methods: {
            refundChange () {
                // if (parseFloat(this.info.refund ).toFixed(2) > this.addressInfo.total) {
                //     console.log('000')
                //     console.log(this.addressInfo.total)
                //     this.info.refund = `${this.addressInfo.total}`
                //     console.log(this.info.refund)
                // }
            },
            // 点击确认
            ok () { // 申请退货
                this.$refs['data'].validate((valid) => {
                    if (valid) {
                        // this.$api.post('/nswy-portal-service/shop/order/operate/buyer/retreat', {account: this.account, entity: this.data}).then(response => {
                        //     if (response.code === 200) {
                        //         this.$Message.success('操作成功')
                        //         this.$emit('on-return')
                        //         this.show = false
                        //     }
                        // })
                        let url = '/shop/shopOrderOperate/order/cancel'
                        let list = {
                            orderCode: this.data.orderCodeId,
                            fromAccount: this.isType, // 0 买家 1卖家
                            type: '2', // 1 取消 2 退款
                            account: this.$user.loginAccount,
                            reason: this.data.reason,
                            describeInfo: this.data.describeInfo,
                            picUrl: this.data.picUrl,
                            status: 13, // 
                        }
                        console.log(list)
                        this.$api.post(url, list).then(response => {
                            if (response.code === 200) {
                                this.$Message.success('取消成功')
                                this.$emit('on-return')
                                this.show = false
                            }
                        })
                    } else {
                        this.$Message.error('请核对表单信息')
                    }
                })
            },
            handleOk (status) { // 卖家操作 同意退货 或者拒绝退货
                this.$refs['info'].validate((valid) => {
                    if (valid) {
                        let url = '/shop/shopOrderOperate/order/cancel'
                        let list = {
                            orderCode: this.data.orderCodeId,
                            fromAccount: 0, // 0 买家 1卖家
                            type: '2', // 1 取消 2 退款
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
                console.log('staus', status)
                this.status = status
                this.isType =  type
                this.data.orderCodeId = orderCodeId
                this.show = true

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
                if (type == 0) { // 买家退货
                    if (status == 4) {
                        this.title = '退货订单'
                        this.$refs['data'].resetFields()
                        this.$nextTick(() => {
                            this.$refs['upload'].handleGive('')
                        })
                    }
                    
                    if (status == 18 || status == 14 || status == 13) { //预览
                        this.title = '退货订单预览'
                        let list = {
                            orderCode: orderCodeId
                        }
                        this.$api.post('/shop/shopOrderOperate/list/findById', list).then(response => {
                            if (response.code === 200) {
                                this.data = response.data
                                this.data.orderCodeId = orderCodeId
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
                } else { 
                    // 卖家确认退货，预览买家退货信息
                    this.title = '退货订单预览'
                    // from 0是买家，1是卖家，可以不传
                    this.isType =  type
                    this.show = true
                    let list = {
                        orderCode: orderCodeId
                    }
                    this.$api.post('/shop/shopOrderOperate/list/findById', list).then(response => {
                        if (response.code === 200) {
                            this.data = response.data
                            this.data.orderCodeId = orderCodeId
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
            },
            // 获取图片
             getPictureList(e){
                var arr = []
                e.forEach(element => {
                        if(element.response){
                        arr.push(element.response.data.picName)
                    }
                })
                this.data.picUrl = arr
            }
        }
    }
</script>