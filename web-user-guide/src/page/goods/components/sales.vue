<template>
    <div class="pt30 pl10 pr10 sales">
        <Form  ref="data" :model="data" label-position="left" :label-width="150" :rules="ruleInline">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="productStatus" label="产品状态">
                        <Select v-model="data.productStatus" style="width: 100%">
                            <Option v-for="item in productStatuss" :value="item.value" :key="item.value">{{ item.label}}</Option>
                        </Select>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="productPackaging" label="产品包装">
                        <Select v-model="data.productPackaging" style="width: 100%">
                            <Option v-for="item in productPackagings" :value="item.value" :key="item.value">{{ item.label}}</Option>
                        </Select>
                    </Form-item>
                </Col>
                <Col span="12" v-if="data.productPackaging != '否'">
                    <Form-item prop="Packing" label="包装方式">
                        <Select v-model="data.Packing" style="width: 100%">
                            <Option v-for="item in Packings" :value="item.value" :key="item.value">{{ item.label}}</Option>
                        </Select>
                    </Form-item>
                </Col>
            </Row>      
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="netWeight" label="以销售单元为计量单位每单元产品净含量">
                        <Input v-model.trim="data.netWeight">
                            <Select v-model="data.maximumUnits" filterable slot="append" style="width: 200px" class="units"  placeholder="单位"  @on-change="changeUNits">
                               <Option v-for="(item, index) in units" :value="item.unit_name" :key="index">{{item.unit_name}}</Option>
                            </Select>
                        </Input>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="packageWeight" label="以销售单元为计量单位所用包装重量">
                        <Input v-model.trim="data.packageWeight">
                            <Select v-model="data.maximumUnits" filterable slot="append" style="width: 200px" class="units"  placeholder="单位"  @on-change="changeUNits">
                               <Option v-for="(item, index) in units" :value="item.unit_name" :key="index">{{item.unit_name}}</Option>
                            </Select>
                        </Input>
                    </Form-item>
                </Col>
            </Row>      
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="output" label="产品产量">
                        <Input v-model.trim="data.output">
                            <Select v-model="data.maximumUnits" filterable slot="append" style="width: 200px" class="units"  placeholder="单位"  @on-change="changeUNits">
                               <Option v-for="(item, index) in units" :value="item.unit_name" :key="index">{{item.unit_name}}</Option>
                            </Select>
                        </Input>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="productAvailability" label="产品可售量">
                        <Input v-model.trim="data.productAvailability">
                            <Select v-model="data.maximumUnits" filterable slot="append" style="width: 200px" class="units"  placeholder="单位"  @on-change="changeUNits">
                                <Option v-for="(item, index) in units" :value="item.unit_name" :key="index">{{item.unit_name}}</Option>
                            </Select>
                        </Input>
                    </Form-item>
                </Col>
            </Row>      
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="productSalesVolume" label="产品起售量">
                        <Input v-model.trim="data.productSalesVolume">
                            <Select v-model="data.maximumUnits" filterable slot="append" style="width: 200px" class="units"  placeholder="单位"  @on-change="changeUNits">
                               <Option v-for="(item, index) in units" :value="item.unit_name" :key="index">{{item.unit_name}}</Option>
                            </Select>
                        </Input>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="maximumSingleShipment" label="单次最大供货量">
                        <Input v-model.trim="data.maximumSingleShipment">
                            <Select v-model="data.maximumUnits"  filterable slot="append" style="width: 200px" class="units"  placeholder="单位" @on-change="changeUNits">
                                <Option v-for="(item, index) in units" :value="item.unit_name" :key="index">{{item.unit_name}}</Option>
                            </Select>   
                        </Input>
                    </Form-item>
                </Col>
            </Row>      
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="productLocation" label="产品所在地">
                        <vui-cascander :values="data.productLocation" @handle-get-result="handleGetData"></vui-cascander>
                    </Form-item>
                </Col>
                <!-- <Col span="12">
                    <Form-item prop="addrDetail" label="" style="margin-left:-150px;">
                        <Input v-model="data.addrDetail" @on-change="changeAddr" :maxlength="50" placeholder="详细地址..."/>
                    </Form-item>
                </Col>
                    <Col span="24">
                    <Form-item prop="" label="" style="margin-top:-10px;">
                        <Input v-model="data.addrView" type="textarea" :autosize="{minRows: 2,maxRows: 5}" readonly/>
                    </Form-item>
                </Col> -->
                <Col span="12">
                    <Form-item prop="location" label="产品所在地地理位置">
                        <Input v-model="data.location" readonly @on-focus="onSelectPoint" />
                    </Form-item>
                </Col>
                <Col span="24">
                    <Form-item prop="productOriginAddress" label="产品所在地地址">
                        <Input v-model="data.productOriginAddress" type="textarea"  :autosize="{minRows: 3,maxRows: 4}" :maxlength="50"></Input>
                    </Form-item>
                </Col>
            </Row>        
        </Form>
		<vui-map ref="experMap" @on-get-point="onGetPoint"></vui-map>
    </div>
</template>
<script>
	import vuiMap from '../../member/components/productionMap'
    import vuiCascander from '~components/vuiCascader/index'
    import {isNumber} from '~utils/validate'
    export default {
        components: {
            vuiMap,
            vuiCascander
        },
        data () {         
            return {
                data: {
                    productStatus: '', // 产品状态
                    productPackaging: '', // 产品包装
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
                    productLocation: '', // 产品所在地
                    location: '', // 产品产地地理位置
                    productOriginAddress: '', // 产品产地地址
                },
                ruleInline: {
                    netWeight: [
                        {validator:isNumber,trigger:'blur'}
                    ],
                    packageWeight: [
                        {validator:isNumber,trigger:'blur'}
                    ],
                    maximumSingleShipment: [
                        {validator:isNumber,trigger:'blur'},
                        // {validator:validateMaximum,trigger:'blur'},
                    ],
                    productStatus: [
                        {required: true,message: '请选择产品状态', trigger: 'change' }
                    ],
                    output: [
                        {required: true,message: '请输入产品产量', trigger: 'blur' },
                        {validator:isNumber,trigger:'blur'}
                    ],
                    productAvailability: [
                        {required: true,message: '请输入产品可售量', trigger: 'blur' },
                        {validator:isNumber,trigger:'blur'},
                        // {validator:validateAvailability,trigger:'blur'}
                    ],
                    productSalesVolume: [
                        {required: true,message: '请输入产品起售量', trigger: 'blur' },
                        {validator:isNumber,trigger:'blur'},
                        // {validator:validateSalesVolume,trigger:'blur'}
                    ],
                    productLocation: [
                        {required: true,message: '请选择产品所在地', trigger: 'change' }
                    ],
                    addrDetail: [
                        {required: true,message: '请输入产品所在地详细地址', trigger: 'change' }
                    ],
                },
                //现货产品/预定产品
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
                units:[]
            }
        },
        created () {
            this.handleGetInit()
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
            handleUnits (e) {
                this.data.maximumUnits = e
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
            //地区
            handleGetData (value, selectedData) {
				let labelArr = []
				selectedData.forEach(element => {
					labelArr.push(element.label)
				})
                this.data.productLocation = labelArr.join('/')
                // this.changeAddr()
            },
            changeAddr () {
                if (this.data.addrDetail && this.data.productLocation) {
                    this.data.addrView = `${this.data.productLocation} / ${this.data.addrDetail}`
                } else if (!this.data.addrDetail) {
                    this.data.addrView = `${this.data.productLocation}`
                }  else if (!this.data.productLocation) {
                    this.data.addrView = `${this.data.addrDetail}`
                }
            },
             //坐标
            onSelectPoint() {
				this.$refs.experMap.showMap = true
			},
			// 取坐标
			onGetPoint(point) {
				if(point.lng !== '' && point.lng !== undefined && point.lat !== '' && point.lat !== undefined) {
					this.data.location = `${point.lng},${point.lat}`
				} else {
					this.data.location = ''
				}
			}
        }
    }
</script>
<style lang="scss">
    .sales .ivu-form .ivu-form-item-label{
        line-height: 22px;
    }
    .units .ivu-select-item{
        padding: 7px 8px;
    }
    .ivu-select-group-title{
        text-align: left;
    }
    // .sales .ivu-input-group-append, .sales .ivu-input-group-prepend{
    //     padding:0px;
    // }
</style>
