<template>
    <div>
        <Modal
            v-model="show"
            title="取消订单"
            width="600"
            :mask-closable="false">
                <div class="pl40 pr20">
                    <Form ref="data" :model="data" :label-width="100" label-position="left" :rules="ruleInline">
                        <FormItem label="取消原因：" prop="reason">
                            <Select v-model="data.reason" style="width:100%">
                                <Option v-for="item in cancelForReturns" :value="item.label" :key="item.label">{{ item.label }}</Option>
                            </Select>
                        </FormItem>
                        <FormItem label="取消说明：" prop="describeInfo">
                            <Input v-model="data.describeInfo" type="textarea" :autosize="{minRows: 3,maxRows: 5}" :maxlength="50"></Input>
                        </FormItem>
                        <FormItem label="上传图片：" prop="picUrl">
                            <vui-upload
                                ref="upload"
                                @on-getPictureList="getPictureList"
                                :total="5"
                                :hint="'图片大小小于2M'"
                                ></vui-upload>
                        </FormItem>
                    </Form>
                </div>
                <div slot="footer" class="tc">
                    <Button type="default" @click="show = false">取消</Button>
                    <Button type="primary" @click.native="ok">确定</Button>
                </div>
        </Modal>
    </div>
</template>
<script>
    import {isPhone2} from '~utils/validate'
    import vuiUpload from '~components/vui-upload'
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
                    picUrl: [] // 上传凭证 5张
                },
                cancelForReturns: [
                    {label: '买错'},
                    {label: '不想要'},
                    {label: '不发货'},
                    {label: '其他原因'}
                ],
                ruleInline: {
                    reason: [
                        {required: true,message: '请选择退款原因', trigger: 'blur' }
                    ]
                },
                loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
                account: '',
                isType: 0,
                status: ''
            }
        },
        created() {
            this.account = this.loginUser.loginAccount
        },
        methods: {
            // 点击确认
            ok () {
                this.$refs['data'].validate((valid) => {
                    if (valid) {
                        let url = '/shop/shopOrderOperate/order/cancel'
                        let list = {
                            orderCode: this.data.orderCodeId,
                            fromAccount: this.isType, // 0 买家 1卖家
                            type: '1', // 1 取消 2 退款
                            account: this.$user.loginAccount,
                            reason: this.data.reason,
                            describeInfo: this.data.describeInfo,
                            picUrl: this.data.picUrl,
                            status: this.status, // 
                        }
                        console.log(list)
                        this.$api.post(url, list).then(response => {
                            if (response.code === 200) {
                                this.$Message.success('取消成功')
                                this.$emit('on-cancel')
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
                if (type === 1) { // 卖家操作
                    this.cancelForReturns = [
                        {label: '缺货'},
                        {label: '其他原因'}
                    ]
                    if (status == 3) {
                        this.status = 11
                    }
                } else { // 买家操作
                    this.cancelForReturns = [
                        {label: '买错'},
                        {label: '不想要'},
                        {label: '不发货'},
                        {label: '其他原因'}
                    ]
                    // 判断现在的状态 ，如果订单状态是1、或者2 1.提交状态，2.修改， 17 未支付订单，取消
                    if (status == 1 || status == 2 || status == 15) {
                        this.status = 17
                    }
                    if (status == 3) { // 已支付订单，买家申请取消 
                        this.status = 10
                    }
                }
                this.isType =  type
                this.data.orderCodeId = orderCodeId
                this.show = true
                this.$refs['data'].resetFields()
                this.$refs['upload'].handleGive('')
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