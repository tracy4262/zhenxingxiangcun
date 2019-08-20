<template>  
  <div  class="pt30 pl10 pr10 family-deatil">
       <Form label-position="left" :label-width="150">
        <Row :gutter="32">
           <Col span="12">
                <Form-item label="主要设备">
                    <Button type="primary" @click="handleAdd" v-if="isAdd"> <Icon type="plus"></Icon> 添加</Button>
                </Form-item>
           </Col>
        </Row>
        <Card v-for="(item , index) in data" :key="index" class="mb20" :bordered="false">
            <div class="btn-toolbar-card" v-if="isAdd">
                <div class="btn-toolbar">
                    <Button type="text"  @click="handleDel(index)" size="small"><Icon type="trash-a"  size="16"  class="pr5"></Icon> 删除</Button> 
                </div>
            </div>
            <!-- <Row>
                <Col span="22"> -->
                <Form  :ref="`corpInfo${index}`" :model="item" label-position="left" :label-width="130" :rules="ruleInline">
                    <Row :gutter="16">
                        <Col span="24">
                            <FormItem label="权限">
                                <i-switch  v-model="item.family_modern_status" size="large">
                                    <span slot="open">公开</span>
                                    <span slot="close">隐藏</span>
                                </i-switch>
                            </FormItem>
                        </Col>
                    </Row>
                    <Row :gutter="16">
                        <Col span="8">
                            <Form-item  prop="tv" label="电视机数量">                                
                                <Input v-model="item.tv" :maxlength="11"><span slot="append">台</span></Input>
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="computer" label="电脑数量">
                                <Input v-model="item.computer" :maxlength="11"><span slot="append">台</span></Input>
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="icebox" label="冰箱数量">
                                <Input v-model="item.icebox" :maxlength="11"><span slot="append">台</span></Input>
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="ari" label="空调数量">
                                <Input v-model="item.ari" :maxlength="11"><span slot="append">台</span></Input>
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="car" label="汽车数量">
                                <Input v-model="item.car" :maxlength="11"><span slot="append">辆</span></Input>
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="motorcycle" label="摩托车数量">
                                <Input v-model="item.motorcycle" :maxlength="11"><span slot="append">辆</span></Input>
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="heater" label="太阳能热水器数量">
                                <Input v-model="item.heater" :maxlength="11"><span slot="append">台</span></Input>
                            </Form-item>
                        </Col>
                    </Row>
                </Form>
                <!-- </Col>
                <Col span="2" class="btn-toolbar-card">
                    &nbsp;
                    <div class="btn-toolbar">
                        <Button type="text"  @click="handleDel(index)" size="small"><Icon type="trash-a"  size="16"  class="pr5"></Icon> 删除</Button> 
                    </div>
                </Col>
            </Row> -->
        </Card>
      </Form>
  </div>
</template>
<script>
    import vuiFilter from '~components/vuiFilter/filter'
    import vuiUpload from '~components/vui-upload'
    import {isNumber} from '~utils/validate'
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
                serviceResultDatas: [],
                data: [{tv: '',
                        computer: '',
                        icebox: '',
                        ari: '',
                        car: '',
                        motorcycle: '',
                        heater: ''}],
                ruleInline:{
                    tv:[{validator:isNumber,trigger:'blur'}],
                    computer:[{validator:isNumber,trigger:'blur'}],
                    icebox:[{validator:isNumber,trigger:'blur'}],
                    ari:[{validator:isNumber,trigger:'blur'}],
                    car:[{validator:isNumber,trigger:'blur'}],
                    motorcycle:[{validator:isNumber,trigger:'blur'}],
                    heater:[{validator:isNumber,trigger:'blur'}],
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
            //增加
            handleAdd () {
                this.data.unshift( 
                    {
                        tv: '',
                        computer: '',
                        icebox: '',
                        ari: '',
                        car: '',
                        motorcycle: '',
                        heater: ''
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
        }
    }
</script>
