<template>
    <div class="pt30 pl10 pr10">
        <Form  ref="data" :model="data" label-position="left" :label-width="150" :rules="ruleInline">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="whether" label="是否投入品或原料">
                        <Select v-model="data.whether" style="width: 100%" @on-change="whetherChange">
                            <Option v-for="item in whethers" :value="item.value" :key="item.value">{{ item.label }}</Option>
                        </Select>
                    </Form-item>
                </Col>
                <Col span="12" v-if="data.whether == '是'">
                    <Form-item prop="rawMaterial" label="投入品或原料">
                        <Input v-model="data.rawMaterial" :maxlength="50"></Input>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="productionMethods" label="生产方式">
                        <Select v-model="data.productionMethods" style="width: 100%">
                            <Option v-for="item in productionMethodss" :value="item.value" :key="item.value">{{ item.label }}</Option>
                        </Select>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="roughProcessing" label="粗加工方式">
                        <Select v-model="data.roughProcessing" style="width: 100%">
                            <Option v-for="item in roughProcessings" :value="item.value" :key="item.value">{{ item.label }}</Option>
                        </Select>
                    </Form-item>
                </Col>
            </Row>
            <view-panel
                title="添加自定义字段"
                :data="data.customData"
                @on-data="handleGetSafeForm"
                @on-add="handleAddBtn"></view-panel>
        </Form>
        <!-- 添加面板 -->
        <add-panel ref="addPanel" @on-save="handleControlBtn"></add-panel>
    </div>
</template>
<script>
import addPanel from '../../../goods/components/vui-form-control/add-panel'
import viewPanel from '../../../goods/components/vui-form-control/view-panel'
    export default {
        components: {
            addPanel,
            viewPanel
        },
        data () {
            return {
                data: {
                    whether: '', // 是否投入品或原料
                    rawMaterial: '', // 投入品或原料
                    productionMethods: '', // 生产方式
                    roughProcessing: '', // 粗加工方式
                    customData: [], // 自定义 字段
                },
                ruleInline: {
                    rawMaterial:[]
                },
                productionMethodss:[ // 野生/人工种植/人工放养/人工精养/手工生产/机械生产
                    {label: '野生', value: '野生'},
                    {label: '人工种植', value: '人工种植'},
                    {label: '人工放养', value: '人工放养'},
                    {label: '人工精养', value: '人工精养'},
                    {label: '手工生产', value: '手工生产'},
                    {label: '机械生产', value: '机械生产'},
                ],
                whethers:[
                    {label: '是', value: '是'},
                    {label: '否', value: '否'}
                ],
                roughProcessings: [ // 干制/冷冻/腌制/熏制/发酵/无加工
                    {label: '干制', value: '干制'},
                    {label: '冷冻', value: '冷冻'},
                    {label: '腌制', value: '腌制'},
                    {label: '熏制', value: '熏制'},
                    {label: '发酵', value: '发酵'},
                    {label: '无加工', value: '无加工'},
                ],
                categoryId: '',
                templateId: '',
                templateType: '',
            }
        },
        created() {
            this.categoryId = this.$route.query.categoryId
            this.templateId = this.$route.query.templateId
            this.templateType = this.$route.query.templateType
        },
        methods:{
            whetherChange () {
                if (data.whether == '是') {
                    this.ruleInline.rawMaterial = [{required: true, message: '请输入投入品或原料', trigger: 'blur'}]
                } else {
                    this.ruleInline.rawMaterial = []
                }
            },
            // 添加组件
            handleControlBtn (data) {
                this.data.customData.push(data)
            },
            handleAddBtn () {
            this.$refs.addPanel.showAddPanel = true
            },
            // 取商品安全数据
            handleGetSafeForm (data) {
            console.log(data)
            },
            getData (val) {
                this.data = Object.assign(this.data, val)
            },
            handleSubmit () {
                if (this.data.whether == '是') {
                    this.$refs['data'].validate((valid) => {
                        if(valid) {
                            this.$emit('on-submit',true)
                        }else{
                            this.$emit('on-submit',false)
                        }
                    })
                } else { 
                    this.$emit('on-submit',true)
                }
            }
        }
    }
</script>
<style>

</style>
