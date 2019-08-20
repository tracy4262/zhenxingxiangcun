<template>
    <div class="pt30 pl10 pr10 sales">
        <Form ref="data" :model="data" label-position="left" :label-width="120" :rules="ruleInline">
            <div>
                <Row :gutter="32">
                    <Col span="12">
                        <Form-item prop="salesWay" label="销售方式">
                            <Select v-model="data.salesWay" style="width: 100%" @on-change="salesWayChange">
                                <Option v-for="item in salesWays" :value="item.value" :key="item.value">{{ item.label }}</Option>
                            </Select>
                        </Form-item>
                    </Col>
                </Row>
                <div v-show="data.salesWay === '定价销售'">
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
                            <Form-item prop="discountPeriod" label="折扣时限">
                                <DatePicker placeholder="折扣时限" style="width:100%;" type="daterange" :options="options3"  @on-change="discountPeriodChange" :editable="false" v-model="data.discountPeriod" ></DatePicker>
                            </Form-item>
                        </Col>
                    </Row>
                </div>
                <div v-show="data.salesWay === '竞价销售'">
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
                    <Row :gutter="32">
                        <Col span="12">
                            <Form-item prop="biddingStartTime" label="竞价开始时间">
                                <DatePicker placeholder="竞价开始时间" style="width:100%;" type="datetime" :options="options3" @on-change="biddingStartTimeChange"
                                    :editable="false" v-model="data.biddingStartTime" format="yyyy-MM-dd HH:mm"></DatePicker>
                            </Form-item>
                        </Col>
                        <Col span="12">
                            <Form-item prop="biddingEndTime" label="竞价结束时间">
                                <DatePicker placeholder="竞价结束时间" style="width:100%;" type="datetime" :options="options3" @on-change="biddingEndTimeChange"
                                    :editable="false" v-model="data.biddingEndTime" format="yyyy-MM-dd HH:mm"></DatePicker>
                            </Form-item>
                        </Col>
                    </Row>
                    <Row :gutter="32">
                        <Col span="24">
                            <Form-item prop="biddingProtocol" label="竞价协议">
                                <vui-upload-file
                                    ref="biddingProtocol"
                                    @on-getFileList="getList"
                                    :hint="'协议大小小于2MB，支持doc/docx/xls/xlsx格式'"
                                    :total="1"
                                    :format="`doc/docx/xls/xlsx`"
                                    :size="[80,80]">
                                </vui-upload-file>
                            </Form-item>
                        </Col>
                    </Row>
                </div>
            </div>
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
    import vuiUploadFile from '~components/vui-upload-file'
    import {isNumber, isDecimal2} from '~utils/validate'
    export default {
        components: {
            addPanel,
            viewPanel,
            vuiUploadFile
        },
        data () {
            return {
                data: {
                    salesWay: '定价销售',
                    // 定价销售字段
                    currentPrice: '',
                    discountPrice: '',
                    discountPeriod: [],
                    discountPeriodStr: '',
                    // 竞价销售字段
                    startPrice: '',
                    minimum: '',
                    biddingStartTime: '',
                    biddingStartTimeStr: '',
                    biddingEndTime: '',
                    biddingEndTimeStr: '',
                    biddingProtocol: [],
                    formData: [] // 自定义表单
                },
                ruleInline: {
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
                    startPrice: [
                        { required: true, message: '请填写起拍价', trigger:'blur' },
                        { validator: isDecimal2, trigger:'blur' }
                    ],
                    minimum: [
                        { required: true, message: '请填写竞拍最小加价', trigger:'blur' },
                        { validator: isDecimal2, trigger:'blur' }
                    ],
                    biddingStartTime: [
                        { required: true, type: 'date', message: '请填选择竞价开始时间', trigger:'change' }
                    ],
                    biddingEndTime: [
                        { required: true, type: 'date', message: '请填选择竞价结束时间', trigger:'change' }
                    ],
                    biddingProtocol: [
                        { required: true, type: 'array', min: 1, message: '请上传竞价协议', trigger:'change' }
                    ]
                },
                salesWays: [
                    { label: '定价销售', value: '定价销售' },
                    { label: '竞价销售', value: '竞价销售' },
                    { label: '面议', value: '面议' }
                ],
                options3: {
					disabledDate(date) {
						return date && date.valueOf() < Date.now() - 86400000;
					}
                },
                flag: false, // 用于判断是否触发过salesWayChange事件
            }
        },
        created () {
        },
        methods: {
            salesWayChange (val) {
                this.flag = true
                // 定价销售字段清空
                this.data.currentPrice = ''
                this.data.discountPrice = ''
                this.data.discountPeriod = []
                this.data.discountPeriodStr = ''
                // 竞价销售字段清空
                this.data.startPrice = ''
                this.data.minimum = ''
                this.data.biddingStartTime = ''
                this.data.biddingStartTimeStr = ''
                this.data.biddingEndTime = ''
                this.data.biddingEndTimeStr = ''
                this.data.biddingProtocol = []
                if (val === '定价销售') {
                    this.ruleInline = {
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
                        ]
                    }
                } else if (val === '竞价销售') {
                    this.ruleInline = {
                        startPrice: [
                            { required: true, message: '请填写起拍价', trigger:'blur' },
                            { validator: isDecimal2, trigger:'blur' }
                        ],
                        minimum: [
                            { required: true, message: '请填写竞拍最小加价', trigger:'blur' },
                            { validator: isDecimal2, trigger:'blur' }
                        ],
                        biddingStartTime: [
                            { required: true, type: 'date', message: '请填选择竞价开始时间', trigger:'change' }
                        ],
                        biddingEndTime: [
                            { required: true, type: 'date', message: '请填选择竞价结束时间', trigger:'change' }
                        ],
                        biddingProtocol: [
                            { required: true, type: 'array', min: 1, message: '请上传竞价协议', trigger:'change' }
                        ]
                    }
                } else if (val === '面议') {
                    this.ruleInline = {}
                }
            },
            discountPeriodChange () {
                if (this.data.discountPeriod) {
                    this.data.discountPeriodStr = this.moment(this.data.discountPeriod[0]).format('YYYY-MM-DD') + ' - ' + this.moment(this.data.discountPeriod[1]).format('YYYY-MM-DD')
                } else {
                    this.data.discountPeriod = []
                    this.data.discountPeriodStr = ''
                }
            },
            biddingStartTimeChange () {
                if (this.data.biddingStartTime) {
                    this.data.biddingStartTimeStr = this.moment(this.data.biddingStartTime).format('YYYY-MM-DD HH:mm')
                } else {
                    this.data.biddingStartTime = ''
                    this.data.biddingStartTimeStr = ''
                }
            },
            biddingEndTimeChange () {
                if (this.data.biddingEndTime) {
                    this.data.biddingEndTimeStr = this.moment(this.data.biddingEndTime).format('YYYY-MM-DD HH:mm')
                } else {
                    this.data.biddingEndTime = ''
                    this.data.biddingEndTimeStr = ''
                }
            },
            groupBuyingStartTimeChange () {
                if (this.data.groupBuyingStartTime) {
                    this.data.groupBuyingStartTimeStr = this.moment(this.data.groupBuyingStartTime).format('YYYY-MM-DD HH:mm')
                } else {
                    this.data.groupBuyingStartTime = ''
                    this.data.groupBuyingStartTimeStr = ''
                }
            },
            groupBuyingEndTimeChange () {
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
                // 如果没有触发过flag事件则要修改ruleInline
                if (!this.flag) {
                    this.ruleInline = {
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
                        ]
                    }
                }
                this.$refs['data'].validate((valid) => {
                    console.log('data val', valid)
                    if(valid) {
                        this.$emit('on-submit',true)
                    }else{
                        this.$emit('on-submit',false)
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
