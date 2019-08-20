<template>
    <div class="pt30 pl10 pr10 sales">
        <Form ref="data" :model="data" label-position="left" :label-width="120" :rules="ruleInline">
            <!-- 判断必须一个一个写不然表单验证渲染不出来 -->
            <Row :gutter="32" v-if="productStatus === '预定产品'">
                <Col span="12">
                    <Form-item prop="advancePaymentTimeStr" label="预付款支付时间段">
                        <DatePicker type="datetimerange" style="width:100%;" :editable="false" v-model="data.advancePaymentTime" @on-change="advancePaymentTimeChange"></DatePicker>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="endPaymentTimeStr" label="尾款支付时间段">
                        <DatePicker type="datetimerange" style="width:100%;" :editable="false" v-model="data.endPaymentTime" @on-change="endPaymentTimeChange"></DatePicker>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32" v-if="productStatus === '预定产品'">
                <Col span="12">
                    <Form-item prop="deliverTimeStr" label="发货时间">
                        <DatePicker type="date" style="width:100%;" :editable="false" v-model="data.deliverTime" @on-change="deliverTimeChange"></DatePicker>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="orderPrice" label="定货价格">
                        <Input v-model="data.orderPrice"><span slot="append">元</span></Input>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32" v-if="productStatus === '预定产品'">
                <Col span="12">
                    <Form-item prop="deposit" label="支付定金方式">
                        <Select v-model="data.deposit" style="width: 100%" @on-change="depositChange">
                            <Option v-for="item in deposits" :value="item.value" :key="item.value">{{ item.label }}</Option>
                        </Select>
                    </Form-item>
                </Col>
                <Col span="12" v-if="data.deposit === '定额支付'">
                    <Form-item prop="depositAmount" label="定金金额">
                        <Input v-model="data.depositAmount"><span slot="append">元</span></Input>
                    </Form-item>
                </Col>
                <Col span="12" v-if="data.deposit === '按比例支付'">
                    <Form-item prop="depositPercent" label="定金比例">
                        <Input v-model="data.depositPercent"><span slot="append">%</span></Input>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32" v-if="productStatus === '预定产品'">
                <Col span="24">
                    <Form-item label="定货注意事宜">
                        <Input type="textarea" :maxlength="300" v-model="data.attention"></Input>
                    </Form-item>
                </Col>
            </Row>
            <div v-if="productStatus === '现货产品'">
                <Row :gutter="32">
                    <Col span="12">
                        <Form-item prop="salesWay" label="销售方式">
                            <Select v-model="data.salesWay" style="width: 100%" @on-change="salesWayChange">
                                <Option v-for="item in salesWays" :value="item.value" :key="item.value">{{ item.label }}</Option>
                            </Select>
                        </Form-item>
                    </Col>
                </Row>
                <div v-if="data.salesWay === '定价销售'">
                    <Row :gutter="32">
                        <Col span="12">
                            <Form-item prop="currentPrice" label="时价">
                                <Input v-model="data.currentPrice"><span slot="append">元</span></Input>
                            </Form-item>
                        </Col>
                    </Row>
                    <Row :gutter="32">
                        <Col span="12">
                            <Form-item prop="discountPrice" label="折扣价">
                                <Input v-model="data.discountPrice"><span slot="append">元</span></Input>
                            </Form-item>
                        </Col>
                        <Col span="12">
                            <Form-item label="折扣时限">
                                <DatePicker placeholder="折扣时限" style="width:100%;" type="datetimerange" :options="options3"  @on-change="discountPeriodChange" :editable="false" v-model="data.discountPeriod" ></DatePicker>
                            </Form-item>
                        </Col>
                    </Row>
                    <!-- <Button type="primary" class="mb10" @click="add">新增批发价格</Button> -->
                    <Row :gutter="32" v-for="(item, index) in data.wholesale" :key="index">
                        <Col span="12">
                            <Form-item label="起批量">
                                <Input v-model="item.wholesaleVolume"></Input>
                            </Form-item>
                        </Col>
                        <Col span="11">
                            <Form-item prop="wholesalePrice" label="批发价">
                                <Input v-model="item.wholesalePrice"><span slot="append">元</span></Input>
                            </Form-item>
                        </Col>
                        <Col span="1">
                            <Icon class="mt5" type="ios-trash" size="20" @click="del(index)" />
                        </Col>
                    </Row>
                </div>
                <div v-if="data.salesWay === '团购销售'">
                    <Row :gutter="32">
                        <Col span="12">
                            <Form-item prop="groupBuyingPrice" label="团购价">
                                <Input v-model="data.groupBuyingPrice"><span slot="append">元</span></Input>
                            </Form-item>
                        </Col>
                        <Col span="12">
                            <Form-item prop="originalPrice" label="原价">
                                <Input v-model="data.originalPrice"><span slot="append">元</span></Input>
                            </Form-item>
                        </Col>
                    </Row>
                </div>
                <div v-if="data.salesWay === '团购销售'">
                    <Row :gutter="32">
                        <Col span="12">
                            <Form-item prop="groupBuyingStartTimeStr" label="团购开始时间">
                                <DatePicker style="width:100%;" type="datetime" :options="options3"  @on-change="groupBuyingStartTimeChange"
                                    :editable="false" v-model="data.groupBuyingStartTime" format="yyyy-MM-dd HH:mm"></DatePicker>
                            </Form-item>
                        </Col>
                        <Col span="12">
                            <Form-item prop="groupBuyingEndTimeStr" label="团购结束时间">
                                <DatePicker style="width:100%;" type="datetime" :options="options3"  @on-change="groupBuyingEndTimeChange"
                                    :editable="false" v-model="data.groupBuyingEndTime" format="yyyy-MM-dd HH:mm"></DatePicker>
                            </Form-item>
                        </Col>
                    </Row>
                </div>
                <div v-if="data.salesWay === '竞价销售'">
                    <Row :gutter="32">
                        <Col span="12">
                            <Form-item prop="minimum" label="竞拍最小加价">
                                <Input v-model="data.minimum"><span slot="append">元</span></Input>
                            </Form-item>
                        </Col>
                        <Col span="12">
                            <Form-item prop="startPrice" label="起拍价">
                                <Input v-model="data.startPrice"><span slot="append">元</span></Input>
                            </Form-item>
                        </Col>
                    </Row>
                </div>
                <div v-if="data.salesWay === '竞价销售'">
                    <Row :gutter="32">
                        <Col span="12">
                            <Form-item prop="biddingStartTimeStr" label="竞价开始时间">
                                <DatePicker placeholder="竞价开始时间" style="width:100%;" type="datetime" :options="options3" @on-change="biddingStartTimeChange"
                                    :editable="false" v-model="data.biddingStartTime" format="yyyy-MM-dd HH:mm"></DatePicker>
                            </Form-item>
                        </Col>
                        <Col span="12">
                            <Form-item prop="biddingEndTimeStr" label="竞价结束时间">
                                <DatePicker placeholder="竞价结束时间" style="width:100%;" type="datetime" :options="options3" @on-change="biddingEndTimeChange"
                                    :editable="false" v-model="data.biddingEndTime" format="yyyy-MM-dd HH:mm"></DatePicker>
                            </Form-item>
                        </Col>
                    </Row>
                </div>
                <div v-show="data.salesWay === '竞价销售'">
                    <Row :gutter="32">
                        <Col span="12">
                            <Form-item prop="bond" label="保证金">
                                <Input v-model="data.bond"><span slot="append">元</span></Input>
                            </Form-item>
                        </Col>
                    </Row>
                </div>
                <div v-if="data.salesWay === '竞价销售'">
                    <Row :gutter="32">
                        <Col span="24">
                            <Form-item prop="biddingProtocol" label="竞价协议">
                                <vui-upload-file
                                    ref="biddingProtocol"
                                    @on-getFileList="getList"
                                    :hint="'协议大小小于2MB，支持doc/docx/xls/xlsx格式'"
                                    :total="1"
                                    :format="['doc', 'docx', 'xls', 'xlsx']"
                                    :size="[80,80]">
                                </vui-upload-file>
                            </Form-item>
                        </Col>
                    </Row>
                </div>
            </div>
        </Form>
        <view-panel
            v-if="data.formData.length !== 0"
            title="自定义表单"
            :edit="false"
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
    import vuiUploadFile from '~components/vui-upload-file'
    import {isNumber, isDecimal2} from '~utils/validate'
    export default {
        components: {
            addPanel,
            viewPanel,
            vuiUploadFile
        },
        props: {
            productStatus: {
                type: String,
                default: '现货产品' // 现货产品、预定产品
            }
        },
        watch: {
            productStatus: {
                handler (newVal, oldVal) {
                    console.log('watch', newVal)
                    // 清空数据源
                    this.data = {
                        advancePaymentTime: [], // 预付款支付时间段
                        advancePaymentTimeStr: '',
                        endPaymentTime: [], // 尾款支付时间段
                        endPaymentTimeStr: '',
                        deliverTime: '', // 发货时间
                        deliverTimeStr: '',
                        orderPrice: '', // 订货价格
                        deposit: '', // 支付定金方式
                        attention: '', // 定货注意事宜
                        depositAmount: '', // 定金金额
                        depositPercent: '', // 定金比例
                        salesWay: '定价销售',
                        // 定价销售字段
                        currentPrice: '',
                        discountPrice: '',
                        discountPeriod: [],
                        discountPeriodStr: '',
                        wholesale: [],
                        // 团购销售字段
                        groupBuyingPrice: '',
                        originalPrice: '',
                        groupBuyingStartTime: '',
                        groupBuyingStartTimeStr: '',
                        groupBuyingEndTime: '',
                        groupBuyingEndTimeStr: '',
                        // 竞价销售字段
                        startPrice: '',
                        minimum: '',
                        biddingStartTime: '',
                        biddingStartTimeStr: '',
                        biddingEndTime: '',
                        biddingEndTimeStr: '',
                        bond: '', // 保证金
                        biddingProtocol: [],
                        formData: [] // 自定义表单
                    }
                },
                deep: true
            }
        },
        data () {
            return {
                data: {
                    advancePaymentTime: [], // 预付款支付时间段
                    advancePaymentTimeStr: '',
                    endPaymentTime: [], // 尾款支付时间段
                    endPaymentTimeStr: '',
                    deliverTime: '', // 发货时间
                    deliverTimeStr: '',
                    orderPrice: '', // 订货价格
                    deposit: '', // 支付定金方式
                    attention: '', // 定货注意事宜
                    depositAmount: '', // 定金金额
                    depositPercent: '', // 定金比例
                    salesWay: '定价销售',
                    // 定价销售字段
                    currentPrice: '',
                    discountPrice: '',
                    discountPeriod: [],
                    discountPeriodStr: '',
                    wholesale: [],
                    // 团购销售字段
                    groupBuyingPrice: '',
                    originalPrice: '',
                    groupBuyingStartTime: '',
                    groupBuyingStartTimeStr: '',
                    groupBuyingEndTime: '',
                    groupBuyingEndTimeStr: '',
                    // 竞价销售字段
                    startPrice: '',
                    minimum: '',
                    biddingStartTime: '',
                    biddingStartTimeStr: '',
                    biddingEndTime: '',
                    biddingEndTimeStr: '',
                    bond: '', // 保证金
                    biddingProtocol: [],
                    formData: [] // 自定义表单
                },
                ruleInline: {
                    advancePaymentTimeStr: [
                        { required: true, message: '请填选择预付款支付时间段', trigger:'change' }
                    ],
                    endPaymentTimeStr: [
                        { required: true, message: '请填选择尾款支付时间段', trigger:'change' }
                    ],
                    deliverTimeStr: [
                        { required: true, message: '请填选择发货时间', trigger:'change' }
                    ],
                    orderPrice: [
                        { required: true, message: '请填写定货价格', trigger:'blur' },
                        { validator: isDecimal2, trigger: 'blur' }
                    ],
                    deposit: [
                        { required: true, message: '请填选择支付定金方式', trigger:'change' }
                    ],
                    depositAmount: [
                        { required: true, message: '请填写定金金额', trigger:'blur' },
                        { validator: isDecimal2, trigger: 'blur' }
                    ],
                    depositPercent: [
                        { validator: isDecimal2, trigger: 'blur' }
                    ],
                    salesWay: [
                        { required: true, message: '请选择销售方式', trigger: 'change' }
                    ],
                    currentPrice: [
                        { required: true, message: '请填写时价', trigger:'blur' },
                        { validator: isDecimal2, trigger:'blur' }
                    ],
                    discountPrice: [
                        { validator: isDecimal2, trigger:'blur' }
                    ],
                    wholesalePrice: [
                        { validator: isDecimal2, trigger: 'blur' }
                    ],
                    groupBuyingPrice: [
                        { required: true, message: '请填写团购价', trigger:'blur' },
                        { validator: isDecimal2, trigger:'blur' }
                    ],
                    originalPrice: [
                        { required: true, message: '请填写原价', trigger:'blur' },
                        { validator: isDecimal2, trigger:'blur' }
                    ],
                    groupBuyingStartTimeStr: [
                        { required: true, message: '请填选择团购开始时间', trigger:'change' }
                    ],
                    groupBuyingEndTimeStr: [
                        { required: true, message: '请填选择团购结束时间', trigger:'change' }
                    ],
                    startPrice: [
                        { required: true, message: '请填写起拍价', trigger:'blur' },
                        { validator: isDecimal2, trigger:'blur' }
                    ],
                    minimum: [
                        { required: true, message: '请填写竞拍最小加价', trigger:'blur' },
                        { validator: isDecimal2, trigger:'blur' }
                    ],
                    biddingStartTimeStr: [
                        { required: true, message: '请填选择竞价开始时间', trigger:'change' }
                    ],
                    biddingEndTimeStr: [
                        { required: true, message: '请填选择竞价结束时间', trigger:'change' }
                    ],
                    bond: [
                        { required: true, message: '请填写保证金', trigger:'blur' },
                        { validator: isDecimal2, trigger:'blur' }
                    ],
                    biddingProtocol: [
                        { required: true, type: 'array', min: 1, message: '请上传竞价协议', trigger:'change' }
                    ]
                },
                salesWays: [
                    { label: '定价销售', value: '定价销售' },
                    { label: '团购销售', value: '团购销售' },
                    { label: '竞价销售', value: '竞价销售' },
                    { label: '面议', value: '面议' }
                ],
                deposits: [
                    { label: '免付定金', value: '免付定金' },
                    { label: '定额支付', value: '定额支付' },
                    { label: '按比例支付', value: '按比例支付' }
                ],
                options3: {
					disabledDate(date) {
						return date && date.valueOf() < Date.now() - 86400000;
					}
                }
            }
        },
        created () {
        },
        methods: {
            advancePaymentTimeChange (date) {
                if (this.data.advancePaymentTime) {
                    this.data.advancePaymentTimeStr = this.moment(this.data.advancePaymentTime[0]).format('YYYY-MM-DD HH:mm:ss') + ' - ' + this.moment(this.data.advancePaymentTime[1]).format('YYYY-MM-DD HH:mm:ss')
                } else {
                    this.data.advancePaymentTime = ''
                    this.data.advancePaymentTimeStr = ''
                }
            },
            endPaymentTimeChange (date) {
                if (this.data.endPaymentTime) {
                    this.data.endPaymentTimeStr = this.moment(this.data.endPaymentTime[0]).format('YYYY-MM-DD HH:mm:ss') + ' - ' + this.moment(this.data.endPaymentTime[1]).format('YYYY-MM-DD HH:mm:ss')
                } else {
                    this.data.endPaymentTime = ''
                    this.data.endPaymentTimeStr = ''
                }
            },
            deliverTimeChange (date) {
                if (this.data.deliverTime) {
                    this.data.deliverTimeStr = this.moment(this.data.deliverTime).format('YYYY-MM-DD')
                } else {
                    this.data.deliverTime = ''
                    this.data.deliverTimeStr = ''
                }
            },
            depositChange () {
                this.data.depositAmount = ''
                this.data.depositPercent = ''
            },
            salesWayChange (val) {
                this.flag = true
                // 定价销售字段清空
                this.data.currentPrice = ''
                this.data.discountPrice = ''
                this.data.discountPeriod = []
                this.data.discountPeriodStr = ''
                this.data.wholesale = []
                // 团购销售字段清空
                this.data.groupBuyingPrice = ''
                this.data.originalPrice = ''
                this.data.groupBuyingStartTime = ''
                this.data.groupBuyingStartTimeStr = ''
                this.data.groupBuyingEndTime = ''
                this.data.groupBuyingEndTimeStr = ''
                // 竞价销售字段清空
                this.data.startPrice = ''
                this.data.minimum = ''
                this.data.biddingStartTime = ''
                this.data.biddingStartTimeStr = ''
                this.data.biddingEndTime = ''
                this.data.biddingEndTimeStr = ''
                this.data.bond = ''
                this.data.biddingProtocol = []
                this.data = Object.assign({}, this.data)
            },
            discountPeriodChange (date) {
                if (this.data.discountPeriod) {
                    this.data.discountPeriodStr = this.moment(this.data.discountPeriod[0]).format('YYYY-MM-DD HH:mm') + ' - ' + this.moment(this.data.discountPeriod[1]).format('YYYY-MM-DD HH:mm')
                } else {
                    this.data.discountPeriod = []
                    this.data.discountPeriodStr = ''
                }
            },
            biddingStartTimeChange (date) {
                if (this.data.biddingStartTime) {
                    this.data.biddingStartTimeStr = this.moment(this.data.biddingStartTime).format('YYYY-MM-DD HH:mm')
                } else {
                    this.data.biddingStartTime = ''
                    this.data.biddingStartTimeStr = ''
                }
            },
            biddingEndTimeChange (date) {
                if (this.data.biddingEndTime) {
                    this.data.biddingEndTimeStr = this.moment(this.data.biddingEndTime).format('YYYY-MM-DD HH:mm')
                } else {
                    this.data.biddingEndTime = ''
                    this.data.biddingEndTimeStr = ''
                }
            },
            groupBuyingStartTimeChange (date) {
                if (this.data.groupBuyingStartTime) {
                    this.data.groupBuyingStartTimeStr = this.moment(this.data.groupBuyingStartTime).format('YYYY-MM-DD HH:mm')
                } else {
                    this.data.groupBuyingStartTime = ''
                    this.data.groupBuyingStartTimeStr = ''
                }
            },
            groupBuyingEndTimeChange (date) {
                if (this.data.groupBuyingEndTime) {
                    this.data.groupBuyingEndTimeStr = this.moment(this.data.groupBuyingEndTime).format('YYYY-MM-DD HH:mm')
                } else {
                    this.data.groupBuyingEndTime = ''
                    this.data.groupBuyingEndTimeStr = ''
                }
            },
            getList (list) {
                console.log(list, '123')
                this.data.biddingProtocol = list
                this.$refs['data'].validate()
            },
            add () {
                this.data.wholesale.push({
                    wholesaleVolume: '',
                    wholesalePrice: ''
                })
            },
            del (index) {
                this.data.wholesale.splice(index, 1)
            },
            getData (val) {
                console.log('get-data')
                this.data = val
                if (val.biddingProtocol.length > 0) {
                    let a = val.biddingProtocol[0].response.data
                    this.$nextTick(() => {
                        this.$refs['biddingProtocol'].handleGive(a.origin, a.name, a.src)
                    })
                }
            },
            handleSubmit () {
                // 判断竞价开始时间不能大于等于结束时间
                if (this.data.salesWay === '竞价销售') {
                    let start = new Date(this.data.biddingStartTimeStr).getTime()
                    let end = new Date(this.data.biddingEndTimeStr).getTime()
                    if (start >= end) {
                        // this.$Message.info('竞价开始时间不能大于结束时间！')
                        this.$emit('on-submit', false, '竞价开始时间不能大于等于结束时间！')
                    }
                }
                // 判断团购开始时间不能大于等于结束时间
                if (this.data.salesWay === '团购销售') {
                    let start = new Date(this.data.groupBuyingStartTimeStr).getTime()
                    let end = new Date(this.data.groupBuyingEndTimeStr).getTime()
                    if (start >= end) {
                        // this.$Message.info('竞价开始时间不能大于结束时间！')
                        this.$emit('on-submit', false, '团购开始时间不能大于等于结束时间！')
                    }
                }
                // 用于回显时格式化时间控件
                if (this.data.advancePaymentTime) {
                    this.data.advancePaymentTime[0] = this.moment(this.data.advancePaymentTime[0]).format('YYYY-MM-DD HH:mm:ss')
                    this.data.advancePaymentTime[1] = this.moment(this.data.advancePaymentTime[1]).format('YYYY-MM-DD HH:mm:ss')
                }
                if (this.data.endPaymentTime) {
                    this.data.endPaymentTime[0] = this.moment(this.data.endPaymentTime[0]).format('YYYY-MM-DD HH:mm:ss')
                    this.data.endPaymentTime[1] = this.moment(this.data.endPaymentTime[1]).format('YYYY-MM-DD HH:mm:ss')
                }
                if (this.data.deliverTime) {
                    this.data.deliverTime = this.moment(this.data.deliverTime).format('YYYY-MM-DD')
                }
                if (this.data.discountPeriod) {
                    this.data.discountPeriod[0] = this.moment(this.data.discountPeriod[0]).format('YYYY-MM-DD HH:mm')
                    this.data.discountPeriod[1] = this.moment(this.data.discountPeriod[1]).format('YYYY-MM-DD HH:mm')
                }
                if (this.data.biddingStartTime) {
                    this.data.biddingStartTime = this.moment(this.data.biddingStartTime).format('YYYY-MM-DD HH:mm')
                }
                if (this.data.biddingEndTime) {
                    this.data.biddingEndTime = this.moment(this.data.biddingEndTime).format('YYYY-MM-DD HH:mm')
                }
                if (this.data.groupBuyingStartTime) {
                    this.data.groupBuyingStartTime = this.moment(this.data.groupBuyingStartTime).format('YYYY-MM-DD HH:mm')
                }
                if (this.data.groupBuyingEndTime) {
                    this.data.groupBuyingEndTime = this.moment(this.data.groupBuyingEndTime).format('YYYY-MM-DD HH:mm')
                }
                this.$refs['data'].validate((valid) => {
                    console.log('pricing valid', valid)
                    if (valid) {
                        this.$emit('on-submit', true)
                    } else {
                        this.$emit('on-submit', false)
                    }
                })
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
<style>

</style>
