<template>
  <div class="pt30 pl10 pr10">
      <div >
            <Form ref="data" label-position="left"  :rules="ruleInline"  :label-width="100" :model="data">
                <Row :gutter="32">
                    <Col span="12">
                        <FormItem label="权限" >
                            <i-switch  v-model="data.status" size="large">
                                <span slot="open">公开</span>
                                <span slot="close">隐藏</span>
                            </i-switch>
                        </FormItem>
                    </Col>
                </Row>                 
                <Row  :gutter="32">
                    <Col span="12">
                        <FormItem label="农事无忧ID" prop="ID">
                            <Input v-model="data.ID.model" readonly  />
                        </FormItem>
                    </Col>
                    <Col span="12">
                        <FormItem label="QQ号码" prop="QQ">
                            <Input v-model="data.QQ.model" />
                        </FormItem>
                    </Col>
                </Row>
                 <Row :gutter="32">
                    <Col span="12">
                        <FormItem label="邮箱" prop="Email">
                            <Input v-model="data.Email.model" />
                        </FormItem>
                    </Col>
                    <Col span="12">
                        <FormItem label="申请域名" prop="domainName">
                            <Input v-model="data.domainName.model"  />
                        </FormItem>
                    </Col>
                </Row>     
                <Row :gutter="32">
                    <Col span="24">
                        <FormItem label="实时预览">
                            <Input v-model="content" readonly type="textarea"  :autosize="{minRows: 3,maxRows: 3}"  style="width: 100%;" />
                        </FormItem>
                    </Col>
                </Row>                
            </Form>
      </div>
      <div>

      </div>
  </div>
</template>
<script>
    import {isQQDeep,isEmailDeep,isDomainNameDeep} from '~utils/validate'
    export default {
        data(){
            return{
                content:'',
                status:true,
                data:{
                    ID:{model:'',name:'农事无忧ID'},
                    QQ:{model:'',name:'QQ号码'},
                    Email:{model:'',name:'邮箱'},
                    domainName:{model:'',name:'申请域名'},
                    status:true
                },
                ruleInline:{
                    QQ:[{validator:isQQDeep,trigger: 'blur'}],
                    Email:[{validator:isEmailDeep,trigger: 'blur'}],
                    domainName:[{validator:isDomainNameDeep,trigger: 'blur'}]
                }
            }
        },
        watch: {
            data: {
				handler(curVal, oldVal) {
					this.onChange()
				},
				deep: true
			},
		},
        methods:{
            //接收数据
			getData(val){
                this.data = val
			},
			//下一步表单验证
            handleSubmit(){
                this.$refs.data.validate ( (valid) => {
                    if(valid) {                        
                        this.$emit('on-submit',true)
                    }else{
                        this.$emit('on-submit',false)
                    }
                })
            },
            //当数据发生变化或者操作隐藏
            onChange(){
                let data = this.data
                let content = ''
                if (data.status) {
                    for(var key  in data){
                        if(data[key] && data[key].model){                        
                            content +=  `${data[key].name}：${data[key].model}，`
                        }
                    }
                    content = content.substring(0,content.length-1)+'。'
                }
                this.content = content
            },
        }
    }
</script>
<style>

</style>

