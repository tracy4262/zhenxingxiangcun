<template>  
  <div  class="pt30 pl10 pr10 family-deatil">
       <Form label-position="left" :label-width="150">
        <Row :gutter="32">
           <Col span="12">
                <Form-item label="无形资产">
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
                        <Form-item prop="number" label="知识产权编号" v-if="item.assetsType == '知识产权'">
                            <Input v-model="item.number" :maxlength="20">
                            </Input>
                        </Form-item>
                    </Col>
                    <Col span="24">
                        <Form-item prop="certificate" label="证书扫描件" v-if="item.assetsType == '知识产权'">
                                  <vui-upload
                                    :ref="`upload${index}`"
                                    @on-getPictureList="getCertificate"
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
                //资产类型
                assetsTypes:[
                    {label:'知识产权', value:'知识产权'},
                    {label:'品牌/商标', value:'品牌/商标'},
                    {label:'专营权', value:'专营权'},
                    {label:'其他', value:'其他'}
                ],
                ruleInline:{
                    name:[{ required: true, message: '请填写资产名称',trigger:'blur'}],
                    assetsType:[{ required: true, message: '请选择资产类型', trigger:'change'}],
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
                this.data[index].valuation = this.data[index].valuation + ''
            },
            //给各个图片组件赋值
            handlePicture () {
                var data = this.data
                for(var i = 0 ;i < data.length ; i++){
                    if(this.data[i].assetsType == '知识产权'){
                        var list = this.data[i].certificate
                        console.log('list',list)
                        console.log( this.$refs[`upload${i}`][0])
                        this.$refs[`upload${i}`][0].handleGive(list)
                    }
                }
            },
            //获取图片
            getCertificate (e) {
                var arr = []
                e.forEach(element => {
                     if(element.response){
                        arr.push(element.response.data.picName)
                    }
                })
                this.data[this.index].certificate = arr
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
                        number:'', //编号
                        certificate:[],    //扫描件       
                        valuation: '',//估值             
                        assetsExplain: ''
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
