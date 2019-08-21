<template>
    <div class="pt30 pl10 pr10 sales">
        <Form ref="data" :model="data" label-position="left" :label-width="120" :rules="ruleInline">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="productStatus" label="产品状态">
                        <Select v-model="data.productStatus" style="width: 100%" @on-change="productStatusChange">
                            <Option v-for="item in productStatuss" :value="item.value" :key="item.value">{{ item.label}}</Option>
                        </Select>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="productPackaging" label="产品包装">
                        <Select v-model="data.productPackaging" style="width: 100%" @on-change="productPackagingChange">
                            <Option v-for="item in productPackagings" :value="item.value" :key="item.value">{{ item.label}}</Option>
                        </Select>
                    </Form-item>
                </Col>
                <Col span="12" v-if="data.productPackaging === '是'">
                    <Form-item prop="Packing" label="包装方式">
                        <Select v-model="data.Packing" style="width: 100%">
                            <Option v-for="item in Packings" :value="item.value" :key="item.value">{{ item.label}}</Option>
                        </Select>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="12"  v-if="data.productPackaging === '是'">
                    <Form-item prop="netWeight" label="以销售单元为计量单位每单元产品净含量">
                        <Input v-model.trim="data.netWeight" class="mt5">
                            <span slot="append">{{data.maximumUnits}}</span>
                            <!-- <Select v-model="data.maximumUnits" filterable slot="append" style="width: 100px" class="units"  placeholder="单位"  @on-change="changeUNits">
                               <Option v-for="(item, index) in units" :value="item.unit_name" :key="index">{{item.unit_name}}</Option>
                            </Select> -->
                        </Input>
                    </Form-item>
                </Col>
                <Col span="12" v-if="data.productPackaging === '是'">
                    <Form-item prop="packageWeight" label="以销售单元为计量单位所用包装重量">
                        <Input v-model.trim="data.packageWeight" class="mt5">
                            <span slot="append">{{data.maximumUnits}}</span>
                            <!-- <Select v-model="data.maximumUnits" filterable slot="append" style="width: 100px" class="units"  placeholder="单位"  @on-change="changeUNits">
                               <Option v-for="(item, index) in units" :value="item.unit_name" :key="index">{{item.unit_name}}</Option>
                            </Select> -->
                        </Input>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="output" label="产品产量">
                        <Input v-model.trim="data.output">
                            <span slot="append">{{data.maximumUnits}}</span>
                            <!-- <Select v-model="data.maximumUnits" filterable slot="append" style="width: 100px" class="units"  placeholder="单位"  @on-change="changeUNits">
                               <Option v-for="(item, index) in units" :value="item.unit_name" :key="index">{{item.unit_name}}</Option>
                            </Select> -->
                        </Input>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="productAvailability" :label="data.productStatus === '现货产品' ? '产品可售量' : '产品可定量'">
                        <Input v-model.trim="data.productAvailability">
                            <span slot="append">{{data.maximumUnits}}</span>
                            <!-- <Select v-model="data.maximumUnits" filterable slot="append" style="width: 100px" class="units"  placeholder="单位"  @on-change="changeUNits">
                                <Option v-for="(item, index) in units" :value="item.unit_name" :key="index">{{item.unit_name}}</Option>
                            </Select> -->
                        </Input>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="productSalesVolume" :label="data.productStatus === '现货产品' ? '产品起售量' : '产品起定量'">
                        <Input v-model.trim="data.productSalesVolume">
                            <span slot="append">{{data.maximumUnits}}</span>
                            <!-- <Select v-model="data.maximumUnits" filterable slot="append" style="width: 100px" class="units"  placeholder="单位"  @on-change="changeUNits">
                               <Option v-for="(item, index) in units" :value="item.unit_name" :key="index">{{item.unit_name}}</Option>
                            </Select> -->
                        </Input>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="maximumSingleShipment" :label="data.productStatus === '现货产品' ? '单次最大供货量' : '单次最大定货量'">
                        <Input v-model.trim="data.maximumSingleShipment">
                            <span slot="append">{{data.maximumUnits}}</span>
                            <!-- <Select v-model="data.maximumUnits"  filterable slot="append" style="width: 100px" class="units"  placeholder="单位" @on-change="changeUNits">
                                <Option v-for="(item, index) in units" :value="item.unit_name" :key="index">{{item.unit_name}}</Option>
                            </Select>    -->
                        </Input>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="12" v-if="data.productStatus === '现货产品'">
                    <Form-item prop="salesTime" label="销售时间">
                        <Select v-model="data.salesTime" style="width: 100%" @on-change="salesTimeChange">
                            <Option v-for="item in salesTimes" :value="item.value" :key="item.value">{{ item.label }}</Option>
                        </Select>
                    </Form-item>
                </Col>
                <Col span="12" v-if="data.salesTime === '定期供货' && data.productStatus === '现货产品'">
                    <Form-item prop="availability" label="现货供应时间">
                        <DatePicker type="daterange" style="width:100%;" :editable="false" v-model="data.availability" @on-change="changeTime"></DatePicker>
                    </Form-item>
                </Col>
            </Row>
        </Form>
        <view-panel
            title="自定义表单"
            :data="data.formData"
            @on-data="fhandleGetForm"
            @on-add="handleAddBtnAddPanel">
        </view-panel>
        <!-- 添加面板 -->
        <add-panel ref="addPanel" @on-save="handleControlBtn"></add-panel>
    </div>
</template>
<script>
    import addPanel from '../../../goods/components/vui-form-control/add-panel'
    import viewPanel from '../../../goods/components/vui-form-control/view-panel'
    import vuiCascander from '~components/vuiCascader/index'
    import {isNumber} from '~utils/validate'
    export default {
        components: {
            vuiCascander,
            addPanel,
            viewPanel
        },
        data () {
            return {
                data: {
                    productStatus: '现货产品', // 产品状态
                    productPackaging: '否', // 产品包装
                    Packing: '', // 包装方式
                    netWeight: '', // 以销售单元为计量单位每单元产品净含量
                    netWeightUnits: '公斤', // 以销售单元为计量单位每单元产品净含量 单位
                    packageWeight: '', // 以销售单元为计量单位所用包装重量
                    packageWeightUnits: '公斤', // 以销售单元为计量单位所用包装重量 单位
                    output: '', // 产品产量
                    outputUnits: '公斤', // 产品产量单位
                    productAvailability: '', // 产品可售量
                    productAvailabilityUnits: '公斤', // 产品可售量单位
                    productSalesVolume: '', // 产品起售量
                    productSalesVolumeUnits: '公斤', // 产品起售量单位
                    maximumSingleShipment: '', // 单次最大供货量
                    maximumUnits: '公斤', // 单次最大供货量单位
                    salesTime: '常年供货', // 销售时间
                    availability: [], // 现货供应时间
                    availabilityStr: '',
                    formData: [] // 自定义表单
                },
                ruleInline: {
                    netWeight: [
                        {validator:isNumber,trigger:'blur'}
                    ],
                    packageWeight: [
                        {validator:isNumber,trigger:'blur'}
                    ],
                    maximumSingleShipment: [
                        {validator:isNumber,trigger:'blur'}
                    ],
                    productStatus: [
                        {required: true,message: '请选择产品状态', trigger: 'change' }
                    ],
                    output: [
                        {required: true, message: '请输入产品产量', trigger: 'blur' },
                        {validator: isNumber, trigger:'blur'}
                    ],
                    productAvailability: [
                        {required: true, message: '请输入产品可售（定）量', trigger: 'blur' },
                        {validator: isNumber, trigger:'blur'}
                    ],
                    productSalesVolume: [
                        {required: true, message: '请输入产品起（定）量', trigger: 'blur' },
                        {validator: isNumber, trigger:'blur'}
                    ],
                    addrDetail: [
                        {required: true,message: '请输入产品所在地详细地址', trigger: 'change' }
                    ]
                },
                productStatuss: [
                    {label:'现货产品', value: '现货产品'},
                    {label:'预定产品', value: '预定产品'}
                ],
                productPackagings: [
                    {label:'是', value: '是'},
                    {label:'否', value: '否'}
                ],
                Packings: [
                    {label:'散装', value: '散装'},
                    {label:'简装', value: '简装'},
                    {label:'精装', value: '精装'}
                ],
                salesTimes: [
                    {label: '常年供货', value: '常年供货'},
                    {label: '定期供货', value: '定期供货'}
                ]
            }
        },
        created () {
        },
        methods: {
            // 单位发生变化
            changeUNits () {
                this.data.netWeightUnits = this.data.maximumUnits
                this.data.packageWeightUnits = this.data.maximumUnits
                this.data.outputUnits = this.data.maximumUnits
                this.data.productAvailabilityUnits = this.data.maximumUnits
                this.data.productSalesVolumeUnits = this.data.maximumUnits
                this.$emit('on-change', this.data.maximumUnits)
            },
            getData (val) {
                console.log('val', val)
                this.data = val
            },
            handleSubmit () {
                this.$refs['data'].validate((valid) => {
                    if(valid) {
                        this.$emit('on-submit',true)
                    }else{
                        this.$emit('on-submit',false)
                    }
                })
            },
            productStatusChange (value) {
                // 返回产品状态
                this.$emit('getProductStatus', value)
                console.log('value', value)
            },
            productPackagingChange () {
                this.data.Packing = ''
                this.data.netWeight = ''
                this.data.packageWeight = ''
            },
            handleChange () {
                if (this.data.salesTime === '常年供货') {
                    this.data.availability = ''
                    // this.ruleInline.availability = []
                } else {
                    // this.ruleInline.availability = [{ required: true, message: '请选择现货供应时间', trigger:'blur'}]
                }
            },
            // 时间改变
            changeTime () {
                if (this.data.availability) {
                    this.data.availabilityStr = this.moment(this.data.availability[0]).format('YYYY-MM-DD') + ' - ' + this.moment(this.data.availability[1]).format('YYYY-MM-DD')
                }
            },
            salesTimeChange () {
                this.data.availability = ''
                this.data.availabilityStr = ''
            },
            // 取商品质量数据
            fhandleGetForm (data) {
            },
            // 质量信息添加
            handleAddBtnAddPanel () {
                this.$refs.addPanel.showAddPanel = true
            },
            // 添加组件
            handleControlBtn (data) {
                this.data.formData.push(data)
            }
        }
    }
</script>
<style lang="scss">
    /* .sales .ivu-form .ivu-form-item-label{
        line-height: 22px;
    }
    .units .ivu-select-item{
        padding: 7px 8px;
    }
    .ivu-select-group-title{
        text-align: left;
    } */
    // .sales .ivu-input-group-append, .sales .ivu-input-group-prepend{
    //     padding:0px;
    // }
</style>
