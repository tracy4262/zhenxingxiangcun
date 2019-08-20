<template>
    <div class="evaluation">
        <Modal
            v-model="show"
            title="评价订单"
            width="600"
            :mask-closable="false">
                <div class="">
                    <div class="pl40 pr40 pb30 pt30 evaluation" v-for="(item, index) in data" :key="index">
                        <Form :ref="`data${index}`" :model="item" :label-width="100" label-position="left" :rules="ruleInline">
                            <FormItem label="商品名称：" >
                                <Row>
                                    <Col span="7" class="tc" v-if="item.productPic">
                                        <img :src="item.productPic" alt="" width="80px" height="80px">
                                    </Col>
                                    <Col span="17">
                                        <div class="pd10">{{item.productName}}</div> 
                                    </Col>
                                </Row>
                            </FormItem>
                            <FormItem label="商品质量：" prop="reputation" v-if="!isType">
                                <RadioGroup v-model="item.reputation">
                                    <Radio :label="3">
                                        <Icon type="ios-star" style="font-size:18px;"></Icon>
                                        <span>好评</span>
                                    </Radio>
                                    <Radio :label="2">
                                        <Icon type="ios-star-half"  style="font-size:18px;"></Icon>
                                        <span>中评</span>
                                    </Radio>
                                    <Radio :label="1">
                                        <Icon type="ios-star-outline"  style="font-size:18px;"></Icon>
                                        <span>差评</span>
                                    </Radio>
                                </RadioGroup>
                            </FormItem>
                            <FormItem label="评分：" prop="star">
                                <Rate allow-half v-model="item.star"></Rate>
                            </FormItem>
                            <FormItem label="评语：" prop="describeInfo">
                                <Input v-model="item.describeInfo" type="textarea" :autosize="{minRows: 3,maxRows: 5}" :maxlength="200"></Input>
                            </FormItem>
                        </Form>
                    </div>
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
                data:[],
                cancelForReturns: [
                    {label: '质量问题'},
                    {label: '与承诺不符'},
                    {label: '诚信问题'},
                    {label: '其他问题'}
                ],
                ruleInline: {
                    reputation: [
                        {required: true, message: '请评价商品质量', trigger: 'blur', type: 'number'}
                    ],
                    star: [
                        {required: true,message: '请评分', type: 'number' ,trigger: 'blur' }
                    ]
                },
                loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
                account: '',
                submit: true,
                isType: '',
                orderCodeId: ''
            }
        },
        created() {
            this.account = this.loginUser.loginAccount
        },
        methods: {
            // 点击确认
            ok () {
                var data = this.data
                this.submit = true
                for(var i = 0 ;i < data.length ; i++){
                    this.$refs[`data${i}`][0].validate((valid)=>{
                        if(!valid){
                            this.submit = false
                        }
                    })
                }
                if (this.submit) {
                    let url = ''
                    if (this.isType === 0) { // 买家
                        url = '/nswy-portal-service/shop/order/comment/buyer'
                    } else { // 卖家
                        url = '/nswy-portal-service/shop/order/comment/seller'
                    }
                    this.data.forEach(e => {
                        e.star += e.star
                        e.orderCodeId = e.orderCode
                        e.commodityTypeInfoId = e.commodityId
                    })
                    this.$api.post(url, {account: this.account, entity: this.data}).then(response => {
                        if (response.code === 200) {
                            this.$Message.success('评价成功')
                            this.show = false
                            this.$emit('on-success')
                        }
                    })
                } else {
                    this.$Message.error('请核对表单信息')
                }
            },
            // 对话框显示
            showModal (list, orderCode, type) {
                this.orderCodeId = orderCode
                this.show = true
                // type 0 买家 1卖家
                this.isType = type
                if (this.isType) {
                    this.ruleInline.reputation = []
                }
                this.data = list
                // this.$api.post('/nswy-portal-service/shop/order/detail/code', {account: this.account, code: this.orderCodeId}).then(response => {
                //     if (response.code === 200) {
                //         this.data = response.data.shopProducts
                //         this.show = true
                //     }
                // })
            },
        }
    }
</script>
<style lang="scss">
.evaluation{
    border-bottom:1px dashed #EFEFEF;
}
.evaluation .ivu-rate-star-full:before, .evaluation .ivu-rate-star-half .ivu-rate-star-content:before{
    color: #f5a623 !important;
}
</style>
