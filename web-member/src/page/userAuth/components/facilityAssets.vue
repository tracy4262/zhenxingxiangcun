<template>  
  <div  class="pt30 pl10 pr10 family-deatil">
       <Form label-position="left" :label-width="150">
        <Row :gutter="32">
           <Col span="12">
                <Form-item label="生产设施">
                    <Button type="primary" @click="handleAdd"> <Icon type="plus"></Icon> 添加</Button>
                </Form-item>
           </Col>
        </Row>
        <Card v-for="(item , index) in data" :key="index" class="mb20  card" :bordered="false" @click.native="handleIndex(index)">
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
                        <Form-item  prop="name" label="资产名称">                                
                            <Input v-model="item.name" :maxlength="20"></Input>
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="assetsType" label="资产类型">
                            <Select v-model="item.assetsType">
                                <Option v-for="item in assetsTypes" :value="item.value" :key="item.value">{{ item.label }}</Option>
                            </Select>
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="model" label="品牌型号">
                            <Input v-model="item.model" :maxlength="50">
                            </Input>
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="purchaseTime" label="采购时间" >
                            <DatePicker type="date" :options="options3"  @on-change="handleTimeChange"  style="width:100%;" v-model="item.purchaseTime" ></DatePicker>
                        </Form-item>
                    </Col>
                    <Col span="24">
                        <Form-item prop="assetPicture" label="资产图片">
                                  <vui-upload
                                    :ref="`upload${index}`"
                                    @on-getPictureList="getAssetPicture"
                                    :hint="'图片大小小于2MB，最多上传 9 张'"
                                    :total="9"
                                    :size="[80,80]"
                                ></vui-upload>
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="originalValue" label="资产原值">
                            <!-- <Input v-model="item.originalValue" @on-change="handleTotalAmount(index)"> -->
                            <Input v-model="item.originalValue">
                            <span slot="append">万元</span>
                            </Input>
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="depreciation" label="年折旧率">
                            <!-- <Input v-model="item.depreciation" @on-change="handleTotalAmount(index)"> -->
                            <Input v-model="item.depreciation" >
                            <span slot="append">%</span>
                            </Input>
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="netAssetValue" label="资产净值">
                            <!-- <Input v-model="item.netAssetValue" readonly> -->
                            <Input v-model="item.netAssetValue">
                            <span slot="append">万元</span>
                            </Input>
                        </Form-item>
                    </Col>
                    <Col span="24">
                        <Form-item prop="assetsExplain" label="资产说明">
                            <Input type="textarea" v-model="item.assetsExplain" :autosize="{minRows: 3,maxRows: 5}" :maxlength="200" />
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
                options3: {
					disabledDate(date) {
						return date && date.valueOf() > Date.now() - 86400000;
					}
                },      
                //资产类型
                assetsTypes:[
                    {label:'办公设施', value:'办公设施'},
                    {label:'生产设施', value:'生产设施'},
                    {label:'仓储设施', value:'仓储设施'},
                    {label:'包装设施', value:'包装设施'},
                    {label:'运输设施', value:'运输设施'},
                    {label:'仪器设施', value:'仪器设施'}
                ],
                ruleInline:{
                    name:[{ required: true, message: '请填写资产名称',trigger:'blur'}],
                    assetsType:[{ required: true, message: '请选择资产类型', trigger:'change'}],
                    originalValue:[{validator:isDecimal2,trigger:'blur'}],
                    depreciation:[{validator:isDecimal2,trigger:'blur'}],
                    netAssetValue:[{validator:isDecimal2,trigger:'blur'}]
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
            //日期
            handleTimeChange (formartval) {
               this.data[this.index].purchaseTime = formartval
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
             //计算资产净值
            handleTotalAmount(index){
                var corpInfo = `corpInfo${index}`
                //取值
                if(this.data[index].originalValue){                    
                    var originalValue = this.data[index].originalValue
                }else{
                    this.data[index].netAssetValue = ''
                    //转字符串
                    this.data[index].originalValue = this.data[index].originalValue +''
                    this.data[index].depreciation = this.data[index].depreciation +'' 
                    return         
                }
                if(this.data[index].depreciation){                    
                    var originalValue = this.data[index].depreciation
                }else{
                    depreciation = 1
                }
                if(originalValue && depreciation){
                    this.data[index].netAssetValue = originalValue*depreciation + ''
                } else {
                    this.data[index].netAssetValue = ''
                }    
                //转字符串
                this.data[index].originalValue = this.data[index].originalValue +''
                this.data[index].depreciation = this.data[index].depreciation +''                 
            },
            //给各个图片组件赋值
            handlePicture () {
                var data = this.data
                for(var i = 0 ;i < data.length ; i++){
                    console.log(this.$refs[`upload${i}`][0])
                    var list = this.data[i].assetPicture
                    this.$refs[`upload${i}`][0].handleGive(list)
                }
            },
            //获取图片
            getAssetPicture (e) {
                var arr = []
                e.forEach(element => {
                     if(element.response){
                        arr.push(element.response.data.picName)
                    }
                })
                this.data[this.index].assetPicture = arr
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
                        name: '',
                        assetsType:'', //资产类型
                        model:'', //型号
                        purchaseTime:'', //采购时间
                        assetPicture:[],    //资产图片       
                        originalValue: '',//资产原值  
                        depreciation:'',     //折旧    
                        netAssetValue: '',//资产净值  
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
