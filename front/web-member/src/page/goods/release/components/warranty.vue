<template>
    <div class="pt30 pl10 pr10">
        <Form  ref="data" :model="data" label-position="left" :label-width="120" :rules="ruleInline">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="date" label="日期">
                        <Select v-model="data.date" style="width: 100%" @on-change="handleChangeDate">
                            <Option v-for="item in credentialss" :value="item.value" :key="item.value">{{ item.value }}</Option>
                        </Select>
                    </Form-item>
                </Col>
                <Col span="12" v-if="data.date === '采收日期'">
                    <Form-item prop="harvestDateStr" label="时间段">
                        <DatePicker placeholder="采收日期" type="daterange" style="width:100%;" @on-change="harvestDateChange" :editable="false" v-model="data.harvestDate" ></DatePicker>
                    </Form-item>
                </Col>
                <Col span="12" v-else>
                    <Form-item prop="productionDateStr" label="时间">
                        <DatePicker placeholder="生产日期" style="width:100%;" type="date" @on-change="productionDateChange" :options="options3" :editable="false" v-model="data.productionDate" ></DatePicker>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="shelfLife" label="保质期">
                        <Input v-model="data.shelfLife">
                        <span slot="append"> 月</span>
                        </Input>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="shelfLifeToStr" label="保质期至">
                        <DatePicker style="width:100%;" type="date" :options="options4" :editable="false" v-model="data.shelfLifeTo" @on-change="shelfLifeToChange"></DatePicker>
                    </Form-item>
                </Col>
            </Row>        
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
    import {isNumber} from '~utils/validate'
    export default {
        components: {
            addPanel,
            viewPanel
        },
        data () {
            return {
                data: {
                    date: '采收日期', // 日期
                    harvestDate: [], // 时间段 采收日期
                    harvestDateStr: '',
                    productionDate: '', // 时间 生产日期
                    productionDateStr: '',
                    shelfLife: '', // 保质期
                    shelfLifeTo: '', // 保质期至
                    shelfLifeToStr: '',
                    formData: [] // 自定义表单
                },
                ruleInline: {
                    date: [
                        { required: true, message: '请选择日期', trigger: 'change'}
                    ],
                    harvestDateStr: [
                        { required: true, message: '请选择时间段', trigger: 'change' }
                    ],
                    productionDateStr: [
                        { required: true, message: '请选择时间', trigger: 'change' }
                    ],
                    shelfLife: [
                        { required: true, message: '请填写保质期', trigger: 'blur' },
                        { validator: isNumber, trigger:'blur' }
                    ],
                    shelfLifeToStr: [
                        { required: true, message: '请选择保质期至', trigger: 'change' }
                    ]
                },
                options3: {
					disabledDate(date) {
						return date && date.valueOf() > Date.now();
					}
                },  
                options4: {
					disabledDate(date) {
						return date && date.valueOf() < Date.now();
					}
                },
                credentialss: [
                    {value: '采收日期'},
                    {value: '生产日期'},
                ]
            }
        },
        methods:{
            // 日期改变
            handleChangeDate () {
                console.log('data', this.data)
                if (this.data.date === '采收日期') {
                    this.data.harvestDate = []
                    this.data.harvestDateStr = ''
                } else {
                    this.data.productionDate = ''
                    this.data.productionDateStr = ''
                }
            },
            harvestDateChange (date) {
                if (this.data.harvestDate) {
                    let start = `${this.moment(this.data.harvestDate[0]).format('YYYY-MM-DD')}`
                    let end = `${this.moment(this.data.harvestDate[1]).format('YYYY-MM-DD')}`
                    this.data.harvestDateStr = start + ' - ' + end
                } else {
                    this.data.harvestDate = []
                    this.data.harvestDateStr = ''
                }
            },
            productionDateChange (date) {
                if (this.data.productionDate) {
                    this.data.productionDateStr = `${this.moment(this.data.productionDate).format('YYYY-MM-DD')}`
                } else {
                    this.data.productionDate = []
                    this.data.productionDateStr = ''
                }
            },
            shelfLifeToChange (date) {
                if (this.data.shelfLifeTo) {
                    this.data.shelfLifeToStr = this.moment(this.data.shelfLifeTo).format('YYYY-MM-DD')
                } else {
                    this.data.shelfLifeTo = ''
                    this.data.shelfLifeToStr = ''
                }
            },
            getData (val) {
                this.data = val
                console.log('dataaa', this.data)
            },
            handleSubmit () {
                // 提交前格式化时间控件的格式
                if (this.data.harvestDate) {
                    this.data.harvestDate[0] = this.moment(this.data.harvestDate[0]).format('YYYY-MM-DD')
                    this.data.harvestDate[1] = this.moment(this.data.harvestDate[1]).format('YYYY-MM-DD')
                }
                if (this.data.productionDate) {
                    this.data.productionDate = this.moment(this.data.productionDate).format('YYYY-MM-DD')
                }
                if (this.data.shelfLifeTo) {
                    this.data.shelfLifeTo = this.moment(this.data.shelfLifeTo).format('YYYY-MM-DD')
                }
                // 表单验证
                this.$refs['data'].validate((valid) => {
                    console.log('warranty valid', valid)
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
