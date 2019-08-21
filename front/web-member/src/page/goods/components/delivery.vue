<template>
    <div class="pt30 pl10 pr10">
        <Form  ref="data" :model="data" label-position="left" :label-width="150" :rules="ruleInline">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="deliveryMethods" label="送货方式">
                        <Select v-model="data.deliveryMethods" style="width: 100%" @on-change="handleChange">
                            <Option v-for="item in deliveryMethodss" :value="item.value" :key="item.value">{{ item.label }}</Option>
                        </Select>
                    </Form-item>
                </Col>
                <Col span="12" v-if="data.deliveryMethods === '送货上门'">
                    <Form-item prop="transportMethods" label="发货方式">
                        <Select v-model="data.transportMethods" style="width: 100%">
                            <Option v-for="item in transportMethodss" :value="item.value" :key="item.value">{{ item.label }}</Option>
                        </Select>
                    </Form-item>
                </Col>
            <!-- </Row> -->
            <!-- <Row :gutter="32"> -->
                <Col span="12" v-if="data.deliveryMethods === '送货上门'">
                    <Form-item prop="deliveryArea" label="选择配送范围">
                        <vui-cascander :values="data.deliveryArea" @handle-get-result="handleGetData"></vui-cascander>
                    </Form-item>
                </Col>
                <Col span="12" v-if="data.deliveryMethods === '送货上门'">
                    <Form-item prop="paymentMethod" label="运费支付方">
                        <Select v-model="data.paymentMethod" style="width: 100%">
                            <Option v-for="item in paymentMethods" :value="item.value" :key="item.value">{{ item.label }}</Option>
                        </Select>
                    </Form-item>
                </Col>
                <Col span="12" v-if="data.deliveryMethods === '送货上门'">
                    <Form-item prop="negotiationFreight" label="是否双方协定运费">
                        <Select v-model="data.negotiationFreight" style="width: 100%">
                            <Option v-for="item in negotiationFreights" :value="item.value" :key="item.value">{{ item.label }}</Option>
                        </Select>
                    </Form-item>
                </Col>
                 <Col span="12" v-show="data.deliveryMethods === '送货上门'">
                    <Form-item prop="freight" label="运费" >
                        <Input v-model="data.freight">
                            <span slot="append">元</span> 
                        </Input>
                    </Form-item>
                </Col>
                <Col span="24" v-show="data.deliveryMethods === '上门取货'">
                    <Form-item prop="pickupLocation" label="取货地点">
                        <Input v-model="data.pickupLocation" type="textarea"  :autosize="{minRows: 3,maxRows: 4}" :maxlength="50"></Input>
                    </Form-item>
                </Col>
            </Row>
        </Form>
    </div>
</template>
<script>
    import vuiCascander from '~components/vuiCascader/index'
    import {isNumber} from '~utils/validate'
    export default {
        components: {
            vuiCascander
        },
        data () {
            return {
                data: {
                    deliveryMethods: '送货上门', // 送货方式
                    transportMethods: '快递', // 发货方式
                    deliveryArea: '', // 选择配送范围
                    paymentMethod: '', // 运费支付方
                    negotiationFreight: '', // 是否双方协定运费
                    freight: '', // 运费
                    pickupLocation: '' // 取货地点
                },
                ruleInline: {
                    freight:[
                        {required: true, message: '请输入运费', trigger: 'blur' },
                        {validator:isNumber, trigger:'blur'}
                    ],
                    pickupLocation:[
                        {required: true, message: '请输入取货地点', trigger: 'blur' }
                    ]
                },
                deliveryMethodss: [
                    {label: '送货上门', value: '送货上门'},
                    {label: '上门取货', value: '上门取货'}
                ],
                // 平邮/快递/邮政EMS/其它
                transportMethodss: [
                    {label: '平邮', value: '平邮'},
                    {label: '快递', value: '快递'},
                    {label: '邮政EMS', value: '邮政EMS'},
                    {label: '其它', value: '其它'}
                ],
                paymentMethods: [
                    {label: '买方承担', value: '买方承担'},
                    {label: '卖方承担', value: '卖方承担'}
                ],
                negotiationFreights: [
                    {label: '是', value: '是'},
                    {label: '否', value: '否'}
                ],
                options3: {
					disabledDate(date) {
						return date && date.valueOf() > Date.now();
					}
                }
            }
        },
        methods:{
            // 发货方式改变
            handleChange () {
                if (this.data.deliveryMethods === '上门取货') {
                    this.data.freight = ''
                    this.ruleInline.pickupLocation = [
                        {required: true, message: '请输入取货地点', trigger: 'blur' }
                    ]
                    this.ruleInline.freight = []
                } else {
                    this.data.pickupLocation = ''
                    this.ruleInline.freight =[
                        {required: true, message: '请输入运费', trigger: 'blur' },
                        {validator:isNumber, trigger:'blur'}
                    ],
                    this.ruleInline.pickupLocation = []
                }
            },
            //地区
            handleGetData (value, selectedData) {
				let labelArr = []
				selectedData.forEach(element => {
					labelArr.push(element.label)
				})
				this.data.deliveryArea = labelArr.join('/')
            },
            getData (val) {
                this.data = val
                if (this.data.freight) {
                    this.data.freight = this.data.freight + ''
                }
                this.handleChange()
            },
            handleSubmit () {
                this.$refs['data'].validate((valid) => {
                    if(valid) {
                        this.$emit('on-submit',true)
                    }else{
                        this.$emit('on-submit',false) 
                    }
                })
            }
        },
        mounted() {
            this.handleChange()
        },
    }
</script>
<style>

</style>
