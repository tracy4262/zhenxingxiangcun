<template>
    <div>
        <Modal
            v-model="show"
            title="投诉商家"
            width="600"
            :mask-closable="false">
                <div class="pl40 pr20">
                    <Form ref="data" :model="data" :label-width="100" label-position="left" :rules="ruleInline">
                        <FormItem label="退货原因：" prop="reason">
                            <Select v-model="data.reason" style="width:100%">
                                <Option v-for="item in reasonsForReturns" :value="item.label" :key="item.label">{{ item.label }}</Option>
                            </Select>
                        </FormItem>
                        <FormItem label="退款说明：" prop="describeInfo">
                            <Input v-model="data.describeInfo" type="textarea" :autosize="{minRows: 3,maxRows: 5}" ></Input>
                        </FormItem>
                        <FormItem label="联系电话：" prop="mobile">
                            <Input v-model="data.mobile" :maxlength="11" ></Input>
                        </FormItem>
                        <FormItem label="上传凭证：" prop="picUrl">
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
                    reason: '' ,// 退货原因
                    describeInfo:'', // 退货说明
                    mobile:'', // 联系电话
                    picUrl: '' // 上传凭证 5张
                },
                reasonsForReturns: [
                    {label: '质量问题'},
                    {label: '与承诺不符'},
                    {label: '诚信问题'},
                    {label: '其他问题'}
                ],
                ruleInline: {
                    mobile:[
                        {validator:isPhone2, trigger:'blur'}
                    ],
                    reason: [
                        {required: true,message: '请选择退款原因', trigger: 'blur' }
                    ]
                },
                account: '',
                loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
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
                        this.$api.post('/nswy-portal-service/shop/complaint/add', {account: this.account, entity: this.data}).then(response => {
                            if (response.code == 200) {
                                this.$Message.success('已提交投诉')
                                this.show = false
                                this.$emit('on-ok')
                            }
                        })
                    } else {
                        this.$Message.error('请核对表单信息')
                    }
                })
            },
            // 对话框显示
            showModal (sellerAccount, orderCode) {
                this.data.sellerAccount = sellerAccount
                this.data.orderCodeId = orderCode
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
                this.data.picUrl = arr.join(',')
            }
        }
    }
</script>