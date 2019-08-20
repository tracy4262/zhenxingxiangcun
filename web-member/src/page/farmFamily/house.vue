<template>  
  <div  class="pt30 pl10 pr10 family-deatil">
       <Form label-position="left" :label-width="150">
        <Row :gutter="32">
           <Col span="12">
                <Form-item label="房屋生活情况">
                    <Button type="primary" @click="handleAdd"> <Icon type="plus"></Icon> 添加</Button>
                </Form-item>
           </Col>
        </Row>
        <Card v-for="(item , index) in data" :key="index" class="mb20 card" :bordered="false" @click.native="getIndex(index)">
            <div class="btn-toolbar-card">                
                <div class="btn-toolbar">
                    <Button type="text"  @click="handleDel(index)" size="small"><Icon type="trash-a"  size="16"  class="pr5"></Icon> 删除</Button> 
                </div>
            </div>
            <Form  :ref="`corpInfo${index}`" :model="item" label-position="left" :label-width="155" :rules="ruleInline">
                <Row :gutter="32">
                    <Col span="24">
                        <Form-item  label="权限">
                            <i-switch  v-model="item.status" size="large">
                                <span slot="open">公开</span>
                                <span slot="close">隐藏</span>
                            </i-switch>
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item  prop="name" label="房主姓名">
                            <Input v-model="item.name"></Input>
                        </Form-item>
                    </Col>
                </Row>
                <Row :gutter="32">
                    <Col span="12">
                        <Form-item prop="addr" label="房屋地址">
                            <vui-cascander :values="item.addr" @handle-get-result="format"></vui-cascander>
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="addrDetail" label="" style="margin-left:-155px;">
                            <Input v-model="item.addrDetail" @on-change="changeAddr" :maxlength="50"  placeholder="详细地址..."/>
                        </Form-item>
                    </Col>
                     <Col span="24">
                        <Form-item prop="" label="">
                            <Input v-model="item.addrView" type="textarea" :autosize="{minRows: 2,maxRows: 5}" readonly/>
                        </Form-item>
                    </Col>
                </Row>
                <Row :gutter="32">
                    <Col span="12">
                        <Form-item prop="buildingArea" label="建筑面积">
                            <Input v-model="item.buildingArea" :maxlength="20"><span slot="append">平方米</span></Input>
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="useArea" label="土地使用面积">  
                            <Input v-model="item.useArea" :maxlength="20"><span slot="append">平方米</span></Input>   
                        </Form-item>
                    </Col>
                </Row>
                <Row :gutter="32">
                    <Col span="12">
                        <Form-item prop="certificate" label="是否办证">  
                            <Select v-model="item.certificate">
                                <Option v-for="item in waterHards" :value="item.value" :key="item.value">{{ item.label }}</Option>
                            </Select>
                        </Form-item>
                    </Col>
                    <Col span="12" v-if="item.certificate == '否'">
                        <Form-item prop="reason" label="未办证原因">  
                            <Select v-model="item.reason">
                                <Option v-for="item in reasons" :value="item.value" :key="item.value">{{ item.label }}</Option>
                            </Select>
                        </Form-item>
                    </Col>
                </Row>
                <Row :gutter="32"  v-if="item.certificate == '是'">
                    <Col span="12">
                        <Form-item prop="houseNumber" label="房屋所有权证编号">  
                            <Input v-model="item.houseNumber"  :maxlength="50"></Input>
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="landNumber" label="土地使用证编号">  
                            <Input v-model="item.landNumber" :maxlength="50"></Input>   
                        </Form-item>
                    </Col>
                     <Col span="12">
                        <Form-item prop="estate" label="不动产权证编号">  
                            <Input v-model="item.estate" :maxlength="50"></Input>
                        </Form-item>
                    </Col>
                </Row>
                <Row :gutter="32">
                    <Col span="12">
                        <Form-item prop="structure" label="房屋结构">  
                            <Select v-model="item.structure">
                                <Option v-for="item in structures" :value="item.value" :key="item.value">{{ item.label }}</Option>
                            </Select>
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="distance" label="房屋与乡村公路的距离">  
                            <Input v-model="item.distance" :maxlength="10"><span slot="append">米</span></Input>   
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="land" label="所在地理位置">  
                            <Input v-model="item.land" readonly @on-focus="onSelectPoint(index)" />
                        </Form-item>
                    </Col>
                     <Col span="12">
                        <Form-item prop="purpose" label="房屋用途">  
                            <Select v-model="item.purpose">
                                <Option v-for="item in purposes" :value="item.value" :key="item.value">{{ item.label }}</Option>
                            </Select>
                        </Form-item>
                    </Col>
                </Row>
                <Row :gutter="32">
                    <Col span="24">
                        <Form-item prop="development" label="房屋建设情况描述">  
                            <Input v-model="item.development"  type="textarea" :autosize="{minRows: 3,maxRows: 5}" :maxlength="500"></Input>   
                        </Form-item>
                    </Col>
                </Row>
                <Row :gutter="32">
                    <Col span="12">
                        <Form-item prop="waterSource" label="饮水来源">
                            <Select v-model="item.waterSource">
                                <Option v-for="item in waterSources" :value="item.value" :key="item.value">{{ item.label }}</Option>
                            </Select>
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="waterHard" label="饮水是否困难">  
                            <Select v-model="item.waterHard">
                                <Option v-for="item in waterHards" :value="item.value" :key="item.value">{{ item.label }}</Option>
                            </Select>
                        </Form-item>
                    </Col>
                        <Col span="12">
                        <Form-item prop="biogasPool" label="沼气池">  
                            <Select v-model="item.biogasPool">
                                <Option v-for="item in biogasPools" :value="item.value" :key="item.value">{{ item.label }}</Option>
                            </Select>
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="pool" label="一池三改">  
                            <Select v-model="item.pool">
                                <Option v-for="item in pools" :value="item.value" :key="item.value">{{ item.label }}</Option>
                            </Select>
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="gas" label="天然气">  
                            <Select v-model="item.gas">
                                <Option v-for="item in gass" :value="item.value" :key="item.value">{{ item.label }}</Option>
                            </Select>
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="communicationQuality" label="通电质量">  
                            <Select v-model="item.communicationQuality">
                                <Option v-for="item in communicationQualitys" :value="item.value" :key="item.value">{{ item.label }}</Option>
                            </Select>
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="broadband" label="宽带网">  
                             <Select v-model="item.broadband">
                                <Option v-for="item in gass" :value="item.value" :key="item.value">{{ item.label }}</Option>
                            </Select>
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="tcSignal" label="电视信号">  
                            <Select v-model="item.tcSignal">
                                <Option v-for="item in tcSignals" :value="item.value" :key="item.value">{{ item.label }}</Option>
                            </Select>
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="network" label="电信网络">  
                            <Select v-model="item.network">
                                <Option v-for="item in networks" :value="item.value" :key="item.value">{{ item.label }}</Option>
                            </Select>
                        </Form-item>
                    </Col>
                </Row>
            </Form>      
        </Card>        
      </Form>
      <vui-map ref="vuiMap" @on-get-point="onGetPoint"></vui-map>
  </div>
</template>
<script>
    import {isNumber,isDecimal2} from '~utils/validate'
    import vuiMap from '../member/components/productionMap'
    import vuiCascander from '~components/vuiCascader/index'
    export default {
        components: {
            vuiMap,
            vuiCascander
        },
        data () {
            return {
                locationList: [],
                //房屋结构 砖木结构/混合结构/钢结构/钢筋混凝土结构/其它结构
                structures:[
                    {label:'砖木结构',value:'砖木结构'},
                    {label:'混合结构',value:'混合结构'},
                    {label:'钢结构',value:'钢结构'},
                    {label:'钢筋混凝土结构',value:'钢筋混凝土结构'},
                    {label:'其它结构',value:'其它结构'}
                ],
                //未办证原因
                reasons:[
                    {label:'个人原因',value:'个人原因'},
                    {label:'其他原因',value:'其他原因'}
                ],
                //通电质量
                communicationQualitys:[
                    {label:'好',value:'好'},
                    {label:'中',value:'中'},
                    {label:'差',value:'差'},
                ],
                //waterHards饮用水是否困难
                waterHards:[
                    {label:'是',value:'是'},
                    {label:'否',value:'否'},
                ],
                //一池三改
                pools:[
                    {label:'未实施',value:'未实施'},
                    {label:'已实施',value:'已实施'},
                ],
                //沼气池
                biogasPools:[
                    {label:'未建',value:'未建'},
                    {label:'已建',value:'已建'},
                ],
                //天然气
                gass:[
                    {label:'开通',value:'开通'},
                    {label:'未开通',value:'未开通'},
                ],
                networks:[
                    {label:'移动电信网络',value:'移动电信网络'},
                    {label:'固定电信网络',value:'固定电信网络'},
                ],
                purposes:[
                    {label:'自住',value:'自住'},
                    {label:'出租',value:'出租'},
                    {label:'闲置',value:'闲置'}
                ],
                waterSources:[
                    {label:'管道自来水',value:'管道自来水'},
                    {label:'地下水',value:'地下水'},
                    {label:'河水',value:'河水'}
                ],
                tcSignals:[
                    {label:'普通天线',value:'普通天线'},
                    {label:'卫星天线',value:'卫星天线'},
                    {label:'有线模拟信号',value:'有线模拟信号'},
                    {label:'有线数字信号',value:'有线数字信号'},
                ],
                data: [],
                ruleInline:{
                    //必填字段 
                    name:[{required: true, message: '请填写房主姓名',trigger:'blur'}],
                    addr:[{required: true, message: '请选择房屋地址',trigger:'blur'}],
                    addrDetail:[{required: true, message: '请填写房屋详细地址',trigger:'blur'}],
                    // buildingArea:[{required: true, message:'请填写建筑面积',trigger:'blur'},{validator:isDecimal2,trigger:'blur'}],
                    // useArea:[{required: true, message: '请输入土地使用面积',trigger:'blur'},{validator:isDecimal2,trigger:'blur'}],
                    //非必填字段，验证格式
                    distance:[{validator:isDecimal2,trigger:'blur'}],
                },
                index:0,
                submit:true
            }
        },
        created() {
            // 取地址
            this.$api.post("/member/town/next/4cc0ce9b1b8d1e8ab8c005056bc3816").then(res => {
                this.locationList = res.data;
            });
        },
        methods: {
            getData(val){
                this.data = val
                this.data.forEach(e => {
                    if (e.addrDetail && e.addr) {
                        e.addrView = `${e.addr} / ${e.addrDetail}`
                    } else if (!e.addrDetail) {
                        e.addrView = `${e.addr}`
                    }  else if (!e.addr) {
                        e.addrView = `${e.addrDetail}`
                    }
                })
            },
            // 表单验证
            handleSubmit () {
                var data = this.data
                this.submit = true
                for(var i = 0 ;i < data.length ; i++){
                    this.$refs[`corpInfo${i}`][0].validate((valid)=>{
                        if(!valid){
                            this.submit = false
                        }
                    })
                }
                console.log('3333', this.submit)
                this.$emit('on-submit',this.submit)
            },
            //增加
            handleAdd () {
                this.data.unshift(
                     {
                        status: true,
                        name: '', //房主姓名
                        addr: '', //地址
                        buildingArea: '', //建筑面积
                        houseNumber: '', //房屋所有权证  编号
                        useArea: '', //土地使用面积
                        landNumber: '', //土地使用证  编号
                        distance: '', //房屋与乡村公路的距离
                        structure: '',//房屋结构
                        land: '',//地理位置
                        development: '',//房屋建设情况描述
                        reason: '',//未办证原因
                        purpose: '',//房屋用途
                        waterSource: '',//饮水来源
                        tcSignal: '',//电视信号
                        communicationQuality: '',//通电质量
                        waterHard: '',//饮水是否困难
                        biogasPool: '',//沼气池
                        pool: '',//一池三改
                        gas: '',//天然气
                        broadband: '',//宽带网
                        network: '',//电信网络
                        certificate:'',//是否办证
                        estate:'',//不动产权编号
                        addrDetail: '' // 详细地址
                    }
                )
            },
            //删除
            handleDel (index) {
                this.$Modal.confirm({
                    title: '是否确定删除',
                    content: '是否确认删除？',
                    onOk:()=>{
                        this.data.splice(index,1)
                    },
                    okText:'确定',
                    cancelText:'取消'
                });
            },
            getIndex (index) {
                this.index = index
            },
            //地址
            format (labels, selectedData) {
                let labelArr = []
                selectedData.forEach(element => {
                    labelArr.push(element.label)
                })
                this.data[this.index].addr = labelArr.join('/')
                this.changeAddr()
            },
            changeAddr () {
                if (this.data[this.index].addrDetail && this.data[this.index].addr) {
                    this.data[this.index].addrView = `${this.data[this.index].addr} / ${this.data[this.index].addrDetail}`
                } else if (!this.data[this.index].addrDetail) {
                    this.data[this.index].addrView = `${this.data[this.index].addr}`
                }  else if (!this.data[this.index].addr) {
                    this.data[this.index].addrView = `${this.data[this.index].addrDetail}`
                }
            },
            loadPositionDatas(item, callback) {
                item.loading = true;
                this.$api.post(`/member/town/next/${item.value}`).then(res => {
                    item.loading = false;
                    item.children = res.data;
                    callback();
                });
            },
            //地理位置
            onSelectPoint (index) {
                this.$refs.vuiMap.showMap = true
                this.getIndex(index)
            },
            // 取坐标
            onGetPoint (point) {
                if (point.lng !== '' && point.lng !== undefined && point.lat !== '' && point.lat !== undefined) {
                    this.data[this.index].land = `${point.lng},${point.lat}`
                } else {
                    this.data[this.index].land = ''
                }
            },
        }
    }
</script>
<style lang="scss">
.family-deatil {
    .card.ivu-card{
        overflow: visible;
        &:hover{
            .btn-toolbar{
            top: 0px;
            }
        }
    }
}
</style>