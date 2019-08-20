<template>
    <div class="pt30 pl10 pr10 sales">
        <Form  ref="data" :model="data" label-position="left" :label-width="150" :rules="ruleInline">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="salesTime" label="销售时间">
                        <Select v-model="data.salesTime" style="width: 100%" @on-change="handleChange">
                            <Option v-for="item in salesTimes" :value="item.value" :key="item.value">{{ item.label }}</Option>
                        </Select>
                    </Form-item>
                </Col>
                <Col span="12" v-if="data.salesTime !== '常年供货'">
                    <Form-item prop="availability" label="现货供应时间">
                        <DatePicker type="date" style="width:100%;" :editable="false" v-model="data.availability" @on-change="changeTime"></DatePicker>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="pricingMethod" label="定价方式">
                        <Select v-model="data.pricingMethod" style="width: 100%">
                            <Option v-for="item in pricingMethods" :value="item.value" :key="item.value">{{ item.label }}</Option>
                        </Select>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="currentPrice" label="时价">
                        <Input v-model="data.currentPrice">
                            <span slot="append">元</span>
                        </Input>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="discountPrice" label="折扣价">
                        <Input v-model="data.discountPrice">
                            <span slot="append">元</span>
                        </Input>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="discountPeriod" label="折扣时限">
                        <DatePicker placeholder="生产日期" style="width:100%;" type="datetime" :options="options3"  @on-change="changeTime" :editable="false" v-model="data.discountPeriod" ></DatePicker>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="wholesaleVolume" label="起批量">
                        <Input v-model="data.wholesaleVolume">
                            <Select v-model="data.wholesaleVolumeUnits" filterable slot="append" style="width: 200px" class="units"  placeholder="单位" @on-change="changeUNits">
                                <Option v-for="(item, index) in units" :value="item.unit_name" :key="index">{{item.unit_name}}</Option>
                            </Select>
                        </Input>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="wholesalePrice" label="批发价">
                        <Input v-model="data.wholesalePrice">
                            <span slot="append">元</span>
                        </Input>
                    </Form-item>
                </Col>
            </Row>
        </Form>
    </div>
</template>
<script>
    import vuiUpload from '~components/vui-upload'
    import {isNumber, isDecimal2} from '~utils/validate'
    export default {
        components: {
            vuiUpload
        },
        data () {
            return {
                data: {
                    salesTime: '常年供货', // 销售时间
                    availability: '', // 现货供应时间
                    pricingMethod: '', // 定价方式
                    currentPrice: '', // 时价
                    discountPrice: '', // 折扣价
                    discountPeriod: '', // 折扣时限
                    wholesaleVolume: '', // 起批量
                    wholesaleVolumeUnits: '公斤', // 起批量 单位
                    wholesalePrice: ''// 批发价
                },
                ruleInline: {
                    currentPrice:[{ required: true, message: '请填写时价', trigger:'blur'},{validator:isDecimal2,trigger:'blur'}],
                    discountPrice:[{validator:isDecimal2,trigger:'blur'}],
                    wholesaleVolume:[{validator:isNumber,trigger:'blur'}],
                    wholesalePrice:[{validator:isDecimal2,trigger:'blur'}]
                },
                salesTimes: [
                    {label: '常年供货', value: '常年供货'},
                    {label: '定期供货', value: '定期供货'}
                ],
                pricingMethods: [
                    {label: '定价', value: '定价'}
                ],
                options3: {
					disabledDate(date) {
						return date && date.valueOf() < Date.now() - 86400000;
					}
                },
                units: []
            }
        },
        created () {
            this.handleGetInit()
        },
        methods:{
            handleChange () {
                if (this.data.salesTime === '常年供货') {
                    this.data.availability = ''
                    this.ruleInline.availability = []
                } else {
                    this.ruleInline.availability = [{ required: true, message: '请选择现货供应时间', trigger:'blur'}]
                }
            },
            // 时间改变
            changeTime (){
                if(this.data.availability){
                    this.data.availability = this.moment(this.data.availability).format('YYYY/MM/DD')
                }
                if(this.data.discountPeriod){
                    this.data.discountPeriod = this.moment(this.data.discountPeriod).format('YYYY/MM/DD HH:mm:ss')
                }
            },
            // 取下拉数据
            handleGetInit(){
                this.$api.post('/portal/shopCommdoity/findUnitStandard',{name: ''}).then(response => {
                    if (response.code == 200) {
                        this.units = response.data
                    }
                })
            },
            getData (val) {
                this.data = val
                if (this.data.currentPrice) {
                    this.data.currentPrice += ''
                }
                if (this.data.discountPrice) {
                    this.data.discountPrice += ''
                }
                if (this.data.wholesaleVolume) {
                    this.data.wholesaleVolume += ''
                }
                if (this.data.wholesalePrice) {
                    this.data.wholesalePrice += ''
                }
            },
            handleSubmit () {
                this.changeTime()
                this.$refs['data'].validate((valid) => {
                    if(valid) {
                        this.$emit('on-submit',true)
                    }else{
                        this.$emit('on-submit',false)
                    }
                })
            },
            // 单位发生变化
            changeUNits () {
                this.$emit('on-change', this.data.wholesaleVolumeUnits)
            },
            handleUnits (e) {
                this.data.wholesaleVolumeUnits = e
            }
        }
    }
</script>
<style>

</style>
