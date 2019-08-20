<template>
    <div class="add-service" style="width:1100px;">
        <Form :label-width="120">
            <FormItem label="收费方式" prop="chargingMethod">
                <Row>
                    <Col span="4"><Checkbox v-model="data.timeCharging">按钓鱼时间收费</Checkbox></Col>
                </Row>
                <Row>
                    <Col span="4"><Checkbox v-model="data.timeVariety">按钓鱼品种收费</Checkbox></Col>
                    <!-- <Col v-if="data.timeVariety">
                        <FormItem label="预约金额" style="">
                            <Input  v-model="data.money" style="width:200px;" :maxlength="20"><span slot="append">元</span></Input>
                        </FormItem>
                    </Col> -->
                </Row>
                <div v-if="data.timeCharging" class="pt20">
                    <Row type="flex" align="middle" :gutter="16">
                        <Col span="8" v-for="(item, index) in data.fishTimeCharge" >
                            <Card class="fishing-form-card">
                                <p class="tr fishing-form-p" v-if="data.fishTimeCharge.length > 1" style="z-index: 99999;">
                                    <Icon type="md-close" @click.native="delTimeChargings(index)"/>
                                </p>
                                <Form class="fishing-time-form" :ref="`timeChargings${index}`" :model="item" :rules="timeChargingsInline">
                                    <FormItem label="请选择垂钓时长" prop="fishDuration" class="pb15">
                                        <Select style="width:100%" v-model="item.fishDuration">
                                            <Option v-for="item in fishingTime" :value="item.label" :key="item.label">{{ item.label }}</Option>
                                        </Select>
                                    </FormItem>
                                    <FormItem label="请填写对应垂钓时长的价格" prop="durationPrice" class="pb15">
                                       <Input  v-model="item.durationPrice" :maxlength="20"></Input>
                                    </FormItem>
                                    <FormItem label="优惠价" prop="discount" class="pb10">
                                       <Input  v-model="item.discount" :maxlength="20"></Input>
                                    </FormItem>
                                </Form>
                            </Card>
                        </Col>
                        <Col span="4" class="tc">
                            <div style="height:270px; line-height: 270px;">
                                <Icon type="md-add-circle" size="32" style="cursor: pointer;" @click="addTimeChargings"/>
                            </div>
                            <!-- <Icon type="md-add" size="28" class=""/> -->
                        </Col>
                    </Row>
                    <p class="pt20">垂钓品种</p>
                    <Table width="550" :columns="columns1" :data="variety" class="mt20"></Table>
                </div>
            </FormItem>
        </Form>
        <div v-if="data.timeVariety">
            <p>产品列表</p>
            <Table :columns="columns2" :data="data.fishVarietyCharge" class="mt20"></Table>
            <!-- <Table :columns="columns2" :data="product" class="mt20"></Table> -->
        </div>

        <div class="tc pt20">
            <Button type="primary" @click="handleBack">上一步</Button>
            <Button type="primary" @click="handleSave">下一步</Button>
            <Button type="text" @click="handleNext">以后再完善</Button>
        </div>
         <Modal v-model="show" width="860" title="编辑产品" :mask-closable="false" :styles="{top: '20px'}">
            <Form ref="productInfo" :model="productInfo" label-position="right" :label-width="100" :rules="productInfoInline" class="mt20">
                <Form-item prop="productPicture" label="产品图片">
                    <vui-upload
                        ref="pic"
                        :disabled="true"
                        :hint="'图片大小小于2MB'"
                        :total="1"
                        :size="[80,80]">
                    </vui-upload>
                </Form-item>
                <Row>
                    <Col span="12">
                        <Form-item prop="speciesName" label="物种名称">
                             <Input v-model="productInfo.speciesName" readonly/>
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="productName" label="产品名称">
                            <Input v-model="productInfo.productName" readonly/>
                        </Form-item>
                    </Col>
                </Row>
                <Row>
                    <Col span="12">
                        <Form-item prop="productPrice" label="产品价格">
                            <Input v-model="productInfo.productPrice" @on-change="calProportion" :maxlength="10" placeholder="请输入产品价格" />
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="unit" label="计量单位">
                          <vuiUnit :value="productInfo.unit" @on-get-data="onGetUnit($event)"></vuiUnit>
                            <!-- <Select v-model="productInfo.unit"  filterable style="width: 100%">
                                <Option v-for="(item, index) in units" :value="item.unit_name" :key="index">{{item.unit_name}}</Option>
                            </Select>  -->
                            <!-- <Input v-model="productInfo.unit" :maxlength="10" placeholder="请输入计量单位" /> -->
                        </Form-item>
                    </Col>
                </Row>
                <Row>
                    <Col span="12">
                        <Form-item prop="durationPrice" label="折扣价格">
                            <Input v-model="productInfo.durationPrice" @on-change="calProportion" :maxlength="10" placeholder="请输入打折价" />
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="durationScale" label="打折比例">
                            <Input v-model="productInfo.durationScale" placeholder="自动生成打折比例" readonly />
                        </Form-item>
                    </Col>
                </Row>
                <Row>
                    <Col span="12">
                    </Col>
                    <Col span="12">
                        <Form-item prop="fishType" label="垂钓状态">
                            <RadioGroup v-model="productInfo.fishType">
                                <Radio label="1">上架</Radio>
                                <Radio label="0">下架</Radio>
                            </RadioGroup>
                        </Form-item>
                    </Col>
                </Row>
            </Form>
            <div slot="footer">
                <Button type="text" @click="show = false">取消</Button>
                <Button type="primary" @click="ok">确定</Button>
            </div>
        </Modal>
    </div>
</template>
<script>
import vuiUpload from '~components/vui-upload'
import { isPhone2,isMoney,isMoney2 } from '~utils/validate'
import vuiUnit from '~components/vui-unit'
    export default {
        components: {
            vuiUpload,
            vuiUnit
        },
        data() {
            return{
                show: false,
                fishingTime: [
                    {label: '1小时'},
                    {label: '3小时'},
                    {label: '6小时'},
                    {label: '一天'},
                ],
                timeChargingsInline: {
                    fishDuration: [
                        {required: true, message:'请选择垂钓时长', trigger: 'change'}
                    ],
                    durationPrice: [
                        {required: true, message:'请填写对应垂钓时长的价格', trigger: 'blur'}
                    ],
                    // discount: [
                    //     {required: true, message:'请填写优惠价', trigger: 'blur'}
                    // ]
                },
                columns1: [
                    {
                        title: '产品图片',
                        key: 'image',
                        render: (h, params) => {
                            return h('div',{
                                style: {
                                    padding: '20px 5px'
                                }
                                },[
                                h('img', {
                                    domProps: {
                                        src: params.row.image && params.row.image[0] ? params.row.image[0] : '../../../../../static/img/goods-list-no-picture1.png'
                                    },
                                    style: {
                                        width: '120px',
                                        height: '80px'
                                    }
                                }),
                            ])
                        }
                    },{
                        title: '产品名称',
                        key: 'productName'
                    },
                ],
                product: [
                    {

                    }
                ],
                productInfo: {},
                productInfoIndex: 0,
                durationScale: 1,
                productInfoInline: {
                    productPrice: [
                        { required: true, validator: isMoney, trigger: 'blur' }
                    ],
                    durationPrice: [
                        { validator: isMoney2, trigger: 'blur' }
                    ],
                    unit: [
                        { required: true, message: '请输入计量单位', trigger: 'blur' }
                    ],
                    fishType: [
                        { required: true, message: '请选择垂钓状态', trigger: 'change' }
                    ]
                },
                columns2: [
                    {
                        title: '产品图片',
                        key: 'image',
                        render: (h, params) => {
                            return h('div',{
                                style: {
                                    padding: '20px 5px'
                                }
                                },[
                                h('img', {
                                    domProps: {
                                        src: params.row.image && params.row.image[0] ? params.row.image[0] : '../../../../../static/img/goods-list-no-picture1.png'
                                    },
                                    style: {
                                        width: '120px',
                                        height: '80px'
                                    }
                                }),
                            ])
                        }
                    },{
                        title: '产品名称',
                        key: 'productName'
                    },{
                        title: '产品价格',
                        key: 'productPrice',
                        render: (h, params) => {
                            if (params.row.productPrice) {
                                return h('span',`￥ ${params.row.productPrice ? parseFloat(params.row.productPrice).toFixed(2) : ''}`)
                            } else {
                                return h('span',``)
                            }
                        }
                    },{
                        title: '计量单位',
                        key: 'unit'
                    },{
                        title: '产品状态',
                        key: 'fishType',
                        render: (h, params) => {
                            if (params.row.fishType) {
                                // "1">上架 0 下架
                                return h('span',`${params.row.fishType == '1' ? '营业中' : '休息中'}`)
                            } else {
                                return h('span',``)
                            }
                        }
                    },{
                        title: '操作',
                        key: 'image',
                        render: (h, params) => {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'text',
                                        size: 'small'
                                    },
                                    style: {
                                        color: '#57A97B'
                                    },
                                    on: {
                                        click: () => {
                                            this.productInfo = Object.assign({},params.row)
                                            this.productInfoIndex = params.index
                                            this.show = true
                                            this.$refs['pic'].handleGive(this.productInfo.image)
                                            // this.edit(params.row.id)
                                        }
                                    }
                                }, '编辑'),
                                // h('Button', {
                                //     props: {
                                //         type: 'text',
                                //         size: 'small'
                                //     },
                                //     style: {
                                //         color: '#8C8C8C'
                                //     },
                                //     on: {
                                //         click: () => {
                                //             this.$Modal.confirm({
                                //                 title: '是否确定删除',
                                //                 content: '是否确认删除？',
                                //                 onOk:()=>{
                                //                     this.$Message.success('删除成功！')
                                //                     this.fishVarietyCharge.splice(params.index,1)
                                //                 },
                                //                 okText:'确定',
                                //                 cancelText:'取消'
                                //             })
                                //             // this.delete(params.row.id)
                                //         }
                                //     }
                                // }, '删除')
                            ]);
                        }
                    }
                ],
                data:{
                    timeCharging: false, // 按钓鱼时间收费
                    timeVariety: false, // 按钓鱼品种收费
                    money: '', // 预约金额
                    fishTimeCharge: [], // 按时间收费
                    fishVarietyCharge: [], // 品种列表
                    id:''
                },
                variety: [],
            }
        },
        created () {
            this.data.id = this.$route.query.id
            if(this.data.id){
                this.handleInit()
            }
        },
        methods:{
            // 取下拉数据
            onGetUnit($event){
              this.productInfo.unit = $event
            },
            // 按时间收费，点击添加
            addTimeChargings () {
                this.data.fishTimeCharge.push({
                        fishDuration: '',
                        durationPrice: '',
                        discount: '',
                    })
            },
            // 按时间收费，删除
            delTimeChargings (index) {
                this.$Modal.confirm({
                    title: '是否确定删除',
                    content: '是否确认删除？',
                    onOk:()=>{
                        this.$Message.success('删除成功！')
                        this.data.fishTimeCharge.splice(index,1)
                    },
                    okText:'确定',
                    cancelText:'取消'
                })
            },
            ok () {
                if (this.durationScale == 2) {
                    this.$Message.error('折扣价格必须小于产品价格')
                    return
                }
                this.$refs[`productInfo`].validate(v => {
                    if (v) {
                        this.data.fishVarietyCharge.splice(this.productInfoIndex, 1, this.productInfo)
                        this.show = false
                    } else {
                        this.$Message.error('请核对表单信息！')
                    }
                })
            },
            calProportion () {
                let reg = /^[0-9]+([.]{1}[0-9]{1,2})?$/
                if (this.productInfo.productPrice !== '' &&
                    this.productInfo.durationPrice !== '' &&
                    reg.test(this.productInfo.productPrice) &&
                    reg.test(this.productInfo.durationPrice)) {
                    let durationScale = parseFloat(this.productInfo.durationPrice / this.productInfo.productPrice * 100).toFixed(2)
                    if (durationScale > 100) {
                        this.$Message.error('折扣价格必须小于产品价格')
                        this.productInfo.durationScale = ''
                        this.durationScale = 2
                    } else {
                        this.productInfo.durationScale = durationScale + '%'
                        this.durationScale = 3
                    }
                } else if (this.productInfo.durationPrice === '' || this.productInfo.productPrice === '') {
                    this.durationScale = 1
                    this.productInfo.durationScale = ''
                }
            },
            //初始化获取数据
            handleInit(){
                this.$api.post('/member/fishing/findFishingService',{id: this.data.id,pageNum:1}).then(response => {
                    if(response.code == 200){
                        if(response.data.list[0]){
                            let data = response.data.list[0]
                            this.data.timeCharging = data.timeCharging
                            this.data.timeVariety = data.timeVariety
                            this.data.money = data.money
                            this.data.fishTimeCharge = data.fishTimeCharge
                            this.data.type = data.type
                            data.variety.forEach(e => {
                                e.serviceVarietyId = e.id
                            })
                            this.data.id = data.id
                            this.variety = data.variety
                            this.data.fishVarietyCharge = data.variety
                            if (!this.data.fishTimeCharge.length) {
                                this.data.fishTimeCharge = [{
                                                fishDuration: '',
                                                durationPrice: '',
                                                discount: '',
                                            }]
                            }
                        }
                    }
                })
            },
            // 保存并继续
            handleSave () {
                if (!this.data.timeCharging && !this.data.timeVariety) {
                    this.$Message.warning('请至少选择一种收费方式')
                } else {
                    let flag = true
                    if (this.data.timeCharging) {
                        this.data.fishTimeCharge.forEach((element, index) => {
                            this.$refs[`timeChargings${index}`][0].validate(v => {
                                if (!v) {
                                    flag = false
                                    this.$Message.error('请核对表单信息！')
                                }
                            })
                        })
                    }
                    if (flag) {
                        this.$api.post('/member/fishing/updateFishingService',this.data).then(response=>{
                            if(response.code == 200){
                                this.$Message.success('保存成功')
                                this.$router.push(`/addService/step4?id=${this.data.id}`)
                            }
                        })
                    }
                }
            },
            // 以后在完善
            handleNext () {
                this.$router.push('/fishing/service')
            },
            // 上一步
            handleBack () {
                this.$router.push('/addService/step2?id='+this.data.id)
            },
        }
    }
</script>
<style scoped>
.fishing-form-card{
    position: relative;
}
.fishing-form-card .fishing-form-p{
    position: absolute;
    width: 90%;
    top: 5px;
    display: none
}
.fishing-form-card:hover .fishing-form-p{
    display: inline-block
}
</style>
