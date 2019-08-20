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
                    biddingStartTimeStr: [
                        { required: true, message: '请填选择竞价开始时间', trigger:'change' }
                    ],
                    biddingEndTimeStr: [
                        { required: true, message: '请填选择竞价结束时间', trigger:'change' }
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
                }
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
            getList (list) {
                console.log(list, '123')
                this.data.biddingProtocol = list
            },
            getData (val) {
                this.data = val
                let a = val.biddingProtocol[0].response.data
                this.$nextTick(() => {
                    this.$refs['biddingProtocol'].handleGive(a.origin, a.name, a.src)
                })
            },
            handleSubmit () {
                if (this.data.discountPeriod) {
                    this.data.discountPeriod[0] = this.moment(this.data.discountPeriod[0]).format('YYYY-MM-DD')
                    this.data.discountPeriod[1] = this.moment(this.data.discountPeriod[1]).format('YYYY-MM-DD')
                }
                if (this.data.biddingStartTime) {
                    this.data.biddingStartTime = this.moment(this.data.biddingStartTime).format('YYYY-MM-DD HH:mm')
                }
                if (this.data.biddingEndTime) {
                    this.data.biddingEndTime = this.moment(this.data.biddingEndTime).format('YYYY-MM-DD HH:mm')
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
