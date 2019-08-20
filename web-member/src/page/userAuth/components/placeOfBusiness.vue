<template>  
  <div  class="pt30 pl10 pr10 family-deatil">
       <Form label-position="left" :label-width="150">
        <Row :gutter="32">
           <Col span="12">
                <Form-item label="经营场所">
                    <Button type="primary" @click="handleAdd"> <Icon type="plus"></Icon> 添加</Button>
                </Form-item>
           </Col>
        </Row>
        <Card v-for="(item , index) in data" :key="index" class="mb20 card" :bordered="false" @click.native="handleIndex(index)">
            <div class="btn-toolbar-card">
                <div class="btn-toolbar">
                    <Button type="text"  @click="handleDel(index)" size="small"><Icon type="trash-a"  size="16"  class="pr5"></Icon> 删除</Button> 
                </div>
            </div>
            <Form  :ref="`corpInfo${index}`" :model="item" label-position="left" :label-width="150" :rules="ruleInline">
                <Row :gutter="32">
                    <Col span="24">
                        <FormItem label="权限">
                            <i-switch  v-model="item.assets_status" size="large">
                                <span slot="open">公开</span>
                                <span slot="close">隐藏</span>
                            </i-switch>
                        </FormItem>
                    </Col>
                </Row>
                <Row :gutter="32">
                    <Col span="12">
                        <Form-item  prop="place" label="处所">                                
                            <Input v-model="item.place" :maxlength="50"></Input>
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="rightType" label="权利类型">
                            <Select v-model="item.rightType">
                                <Option v-for="item in rightTypes" :value="item.value" :key="item.value">{{ item.label }}</Option>
                            </Select>
                        </Form-item>
                    </Col>
                     <Col span="12">
                        <Form-item prop="area" label="场所面积">
                            <Input v-model="item.area"><span slot="append">平方米</span>
                            </Input>
                        </Form-item>
                    </Col>
                     <Col span="12">
                        <Form-item prop="purpose" label="用途" >
                             <Select v-model="item.purpose">
                                <Option v-for="item in purposes" :value="item.value" :key="item.value">{{ item.label }}</Option>
                            </Select>
                        </Form-item>
                    </Col>
                     <Col span="12">
                        <Form-item prop="certificateNumber" label="不动产证号" v-if="item.rightType == '自购'">
                            <Input v-model="item.certificateNumber" :maxlength="20">
                            </Input>
                        </Form-item>
                    </Col>
                    <Col span="24">
                        <Form-item prop="certificate" label="不动产证扫描件" v-if="item.rightType == '自购'">
                                  <vui-upload
                                    :ref="`certificate${index}`"
                                    @on-getPictureList="getCertificate"
                                    :hint="'图片大小小于2MB，最多上传 9 张'"
                                    :total="9"
                                    :size="[80,80]"
                                ></vui-upload>
                        </Form-item>
                    </Col>
                     <Col span="24">
                        <Form-item prop="contract" label="租赁合同扫描件">
                                  <vui-upload
                                    :ref="`contract${index}`"
                                    @on-getPictureList="getContract"
                                    :hint="'图片大小小于2MB，最多上传 9 张'"
                                    :total="9"
                                    :size="[80,80]"
                                ></vui-upload>
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="valuation" label="资产估值">
                            <Input v-model="item.valuation" @on-change="changeValuation(index)">
                            <span slot="append">万元</span>
                            </Input>
                        </Form-item>
                    </Col>
                     <Col span="24">
                        <Form-item prop="assetsExplain" label="资产说明">
                            <Input type="textarea" v-model="item.assetsExplain" :autosize="{minRows: 3,maxRows: 5}" :maxlength="500"></Input>
                        </Form-item>
                    </Col>
                </Row>
            </Form>
        </Card>

      </Form>
  </div>
</template>
<script>
    import vuiFilter from '~components/vuiFilter/filter'
    import vuiUpload from '~components/vui-upload'
    import {isDecimal2} from '~utils/validate'
    export default{
        components: {
            vuiFilter,
            vuiUpload
        },
        props:{
            isAdd:{
                type: Boolean,
                default:false
            }
        },
        data () {
            return {
                data: [],
                //权利类型
                rightTypes:[
                    {label:'自购', value:'自购'},
                    {label:'租赁', value:'租赁'}
                ],
                // 用途
                purposes:[
                    {label:'办公', value:'办公'},
                    {label:'宿舍', value:'宿舍'},
                    {label:'经营', value:'经营'},
                    {label:'仓储', value:'仓储'},
                    {label:'公益', value:'公益'},
                    {label:'其他', value:'其他'}
                ],
                ruleInline:{
                    place:[{ required: true, message: '请填写资产名称',trigger:'blur'}],
                    rightType:[{ required: true, message: '请选择权利类型', trigger:'change'}],
                    area:[{validator:isDecimal2,trigger:'blur'}],
                    valuation:[{validator:isDecimal2,trigger:'blur'}]
                },
                index:0,
                submit:true
            }
        },
        created() {
        },
        methods: {   
            getData(val){
                this.data = val                
                setTimeout(()=>{                    
                    this.handlePicture()
                },1000)
            },
            //表单验证
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
                this.$emit('on-submit',this.submit)
            },
             //资产转字符串
            changeValuation(index){
                console.log(index)
                this.data[index].valuation = this.data[index].valuation + ''
            },
            //给各个图片组件赋值
            handlePicture () {
                var data = this.data
                for(var i = 0 ;i < data.length ; i++){
                    var contractlist = this.data[i].contract
                    this.$refs[`contract${i}`][0].handleGive(contractlist)
                    if(this.data[i].rightType == '自购'){
                        var certificateList = this.data[i].certificate
                        this.$refs[`certificate${i}`][0].handleGive(certificateList)
                    }
                }
            },
            //获取图片 不动产证扫描件
            getCertificate (e) {
                var arr = []
                e.forEach(element => {
                     if(element.response){
                        arr.push(element.response.data.picName)
                    }
                })
                this.data[this.index].certificate = arr
            },
            //获取图片 租赁合同扫描件
            getContract (e) {
                var arr = []
                e.forEach(element => {
                     if(element.response){
                        arr.push(element.response.data.picName)
                    }
                })
                this.data[this.index].contract = arr
            },
            //获取索引
            handleIndex (index) {
                this.index = index
            },
            //增加
            handleAdd () {
                this.data.unshift( 
                    {
                        assets_status: true,
                        place: '',
                        rightType:'', //权利类型
                        area:'', //场所面积
                        purpose:'', //用途
                        certificateNumber:'', //不动产证编号
                        certificate:[],    //不动产证扫描件   
                        contract:[],     //租赁合同扫描件   
                        valuation: '',//资产估值
                        assetsExplain: ''//资产说明
                    }
                )
                setTimeout(()=>{                    
                    this.handlePicture()
                },500)
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
        }
    }
</script>
