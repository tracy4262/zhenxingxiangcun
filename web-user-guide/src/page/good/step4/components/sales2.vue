<template>
    <div class="pt30 pl10 pr10 sales">
        <Form ref="data" :model="data" label-position="left" :label-width="120" :rules="ruleInline">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="assetAvailability" label="资产可售量">
                        <Input v-model.trim="data.assetAvailability">
                            <span slot="append">{{data.maximumUnits}}</span>
                            <!-- <Select v-model="data.maximumUnits" filterable slot="append" style="width: 100px" class="units"  placeholder="单位">
                                <Option v-for="(item, index) in units" :value="item.unit_name" :key="index">{{item.unit_name}}</Option>
                            </Select> -->
                        </Input>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="assetProvideWay" label="资产提供方式">
                        <Select v-model="data.assetProvideWay" style="width: 100%">
                            <Option v-for="item in assetProvideWays" :value="item.value" :key="item.value">{{ item.label}}</Option>
                        </Select>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="startTime" label="有效期开始时间">
                        <DatePicker placeholder="有效期开始时间" style="width:100%;" type="datetime" :options="options3"  @on-change="startTimeChange"
                            :editable="false" v-model="data.startTime" format="yyyy-MM-dd HH:mm"></DatePicker>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="endTime" label="有效期截止时间">
                        <DatePicker placeholder="有效期截止时间" style="width:100%;" type="datetime" :options="options3"  @on-change="endTimeChange"
                            :editable="false" v-model="data.endTime" format="yyyy-MM-dd HH:mm"></DatePicker>
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
                    assetAvailability: '',
                    maximumUnits: '套',
                    assetProvideWay: '租赁',
                    startTime: '',
                    startTimeStr: '',
                    endTime: '',
                    endTimeStr: '',
                    formData: [] // 自定义表单
                },
                ruleInline: {
                    assetAvailability: [
                        {required: true, message: '请输入资产可售量', trigger: 'blur' },
                        {validator: isNumber, trigger:'blur'}
                    ],
                    startTime: [
                        { required: true, type: 'date', message: '请填选择团购开始时间', trigger:'change' }
                    ],
                    endTime: [
                        { required: true, type: 'date', message: '请填选择团购结束时间', trigger:'change' }
                    ]
                },
                options3: {
					disabledDate(date) {
						return date && date.valueOf() < Date.now() - 86400000;
					}
                },
                assetProvideWays: [
                    { label:'租赁', value: '租赁' },
                    { label:'买断', value: '买断' }
                ]
            }
        },
        created () {
        },
        methods: {
            startTimeChange () {
                if (this.data.startTime) {
                    this.data.startTimeStr = this.moment(this.data.startTime).format('YYYY-MM-DD HH:mm')
                } else {
                    this.data.startTime = ''
                    this.data.startTimeStr = ''
                }
            },
            endTimeChange () {
                if (this.data.endTime) {
                    this.data.endTimeStr = this.moment(this.data.endTime).format('YYYY-MM-DD HH:mm')
                } else {
                    this.data.endTime = ''
                    this.data.endTimeStr = ''
                }
            },
            getData (val) {
                this.data = val
                this.data.productAvailability = this.data.productAvailability + ''
                this.data.output = this.data.output + ''
                this.data.productSalesVolume = this.data.productSalesVolume + ''
                // this.changeAddr()
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
