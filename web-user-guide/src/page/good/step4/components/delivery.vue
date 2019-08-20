<template>
    <div class="pt30 pl10 pr10">
        <Card v-for="(item, index) in data" class="mb20">
            <Row v-if="data.length > 1" class="tr mb10">
                <Col span="24"><Button type="text" @click="delDelivery(index)">删除</Button></Col>
            </Row>
            <Form :ref="`item${index}`" :model="item" label-position="left" :label-width="120" :rules="ruleInline">
                <Row :gutter="32">
                    <Col span="12">
                        <Form-item prop="deliveryMethods" label="送货方式">
                            <Select v-model="item.deliveryMethods" style="width: 100%" @on-change="deliveryMethodsChange($event,index)">
                                <Option v-for="item in deliveryMethodss" :value="item.value" :key="item.value">{{ item.label }}</Option>
                            </Select>
                        </Form-item>
                    </Col>
                    <Col span="12" v-show="item.deliveryMethods === '送货上门'">
                        <Form-item prop="transportMethods" label="发货方式">
                            <Select v-model="item.transportMethods" style="width: 100%">
                                <Option v-for="item in transportMethodss" :value="item.value" :key="item.value">{{ item.label }}</Option>
                            </Select>
                        </Form-item>
                    </Col>
                    <Col span="12" v-show="item.deliveryMethods === '送货上门'">
                        <Form-item prop="deliveryArea" label="配送范围">
                            <Input v-model="item.deliveryArea"></Input>
                        </Form-item>
                    </Col>
                    <Col span="12" v-show="item.deliveryMethods === '送货上门'">
                        <Form-item prop="paymentMethod" label="运费支付方">
                            <Select v-model="item.paymentMethod" style="width: 100%">
                                <Option v-for="item in paymentMethods" :value="item.value" :key="item.value">{{ item.label }}</Option>
                            </Select>
                        </Form-item>
                    </Col>
                    <Col span="12" v-show="item.deliveryMethods === '送货上门' && item.paymentMethod === '买方承担'">
                        <Form-item prop="negotiationFreight" label="是否双方协定运费">
                            <Select v-model="item.negotiationFreight" style="width: 100%">
                                <Option v-for="item in negotiationFreights" :value="item.value" :key="item.value">{{ item.label }}</Option>
                            </Select>
                        </Form-item>
                    </Col>
                    <Col span="12" v-show="item.deliveryMethods === '送货上门' && item.paymentMethod === '买方承担' && item.negotiationFreight === '否'">
                        <Form-item prop="freight" label="运费" >
                            <Input v-model="item.freight"><span slot="append">元</span></Input>
                        </Form-item>
                    </Col>
                    <Col span="12" v-show="item.deliveryMethods === '上门取货'">
                        <Form-item label="取货地点">
                            <Select v-model="item.pickupLocation" style="width: 100%">
                                <Option v-for="item in pickupLocations" :value="item.value" :key="item.value">{{ item.label }}</Option>
                            </Select>
                        </Form-item>
                    </Col>
                    <Col span="24" v-show="item.pickupLocation === '定点取货'">
                        <FormItem label="选择取货地点">
                            <Button type="success" ghost @click="chooseInfo(index)" class="btn-light-primary">从基础设置中选择</Button>
                            <Button type="success" ghost @click="addInfo(index)" class="btn-light-primary">添加</Button>
                        </FormItem>
                    </Col>
                </Row>
            </Form>
            <!-- 服务网点控件 -->
            <network-station :ref="`networkStation${index}`" :item="item" :type="1"></network-station>
            <view-panel
                title="自定义表单"
                :item="item.formData"
                @on-item="fhandleGetForm"
                @on-add="handleAddBtnAddPanel(index)">
            </view-panel>
            <!-- 添加面板 -->
            <add-panel :ref="`addPanel${index}`" @on-save="handleControlBtn($event, index)"></add-panel>
        </Card>
        <Button type="success" ghost @click="add" class="btn-light-primary mb20">增加</Button>
    </div>
</template>
<script>
    import addPanel from '../../../goods/components/vui-form-control/add-panel'
    import viewPanel from '../../../goods/components/vui-form-control/view-panel'
    import networkStation from './networkStation'
    import vuiCascander from '~components/vuiCascader/index'
    import {isNumber} from '~utils/validate'
    export default {
        components: {
            addPanel,
            viewPanel,
            vuiCascander,
            networkStation
        },
        data () {
            return {
                data: [{
                    id: '',
                    deliveryMethods: '送货上门', // 送货方式
                    transportMethods: '快递', // 发货方式
                    deliveryArea: '', // 选择配送范围
                    paymentMethod: '买方承担', // 运费支付方
                    negotiationFreight: '否', // 是否双方协定运费
                    freight: '', // 运费
                    pickupLocation: '', // 取货地点
                    networkStation: [], // 网点
                    formData: [] // 自定义表单
                }],
                ruleInline: {
                    deliveryArea: [
                        { required: true, message: '请输入配送范围', trigger: 'blur' }
                    ],
                    freight: [
                        { required: true, message: '请输入运费', trigger: 'blur' },
                        { validator: isNumber, trigger:'blur' }
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
                pickupLocations: [
                    {label: '不限', value: '不限'},
                    {label: '定点取货', value: '定点取货'},
                ],
                options3: {
					disabledDate(date) {
						return date && date.valueOf() > Date.now();
					}
                }
            }
        },
        methods: {
            deliveryMethodsChange (val, index) {
                console.log('val', val, 'index', index)
                this.data.splice(index, 1, {
                    id: this.data[index].id,
                    deliveryMethods: val, // 送货方式
                    transportMethods: '快递', // 发货方式
                    deliveryArea: '', // 选择配送范围
                    paymentMethod: '买方承担', // 运费支付方
                    negotiationFreight: '否', // 是否双方协定运费
                    freight: '', // 运费
                    pickupLocation: '', // 取货地点
                    networkStation: [], // 网点
                    formData: [] // 自定义表单
                })
                this.$refs[`networkStation${index}`][0].data = []
                // 改变ruleInline
            },
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
            getData (val) {
                this.data = val
            },
            handleSubmit () {
                /* this.$refs['item'].validate((valid) => {
                    if(valid) {
                        this.$emit('on-submit',true)
                    }else{
                        this.$emit('on-submit',false) 
                    }
                }) */
            },
            chooseInfo (index) {
                this.$refs[`networkStation${index}`][0].chooseInfo()
            },
            addInfo (index) {
                this.$refs[`networkStation${index}`][0].addInfo()
            },
            add () {
                this.data.push({
                    id: '',
                    deliveryMethods: '送货上门', // 送货方式
                    transportMethods: '快递', // 发货方式
                    deliveryArea: '', // 选择配送范围
                    paymentMethod: '买方承担', // 运费支付方
                    negotiationFreight: '否', // 是否双方协定运费
                    freight: '', // 运费
                    pickupLocation: '', // 取货地点
                    networkStation: [], // 网点
                    formData: [] // 自定义表单
                })
            },
            delDelivery (index) {
                this.$Modal.confirm({
                    title: '操作提示',
                    content: '<p>您确定删除该发货信息？</p>',
                    cancelText: '取消',
                    onOk: () => {
                        this.data.splice(index, 1)
                    }
                })
            },
            // 取商品质量数据
            fhandleGetForm (data) {
            },
            // 质量信息添加
            handleAddBtnAddPanel (index) {
                this.$refs[`addPanel${index}`][0].showAddPanel = true
            },
            // 添加组件
            handleControlBtn (data, index) {
                this.data[index].formData.push(data)
            }
        }
    }
</script>
<style>

</style>
