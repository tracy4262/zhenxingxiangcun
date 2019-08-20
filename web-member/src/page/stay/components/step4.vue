<template>
    <div class="add-service">
        <Form ref="data" :model="data" :label-width="120"   label-position="left" :rules="ruleInline">
            <!-- <FormItem label="服务时间" prop="service_times">
                <DatePicker type="daterange" v-model="data.service_times" :editable="false" style="width: 100%;" @on-change="handleChange"></DatePicker>
            </FormItem>            -->
            <FormItem label="注意事项" prop="mattres_need_attention">
                <Input type="textarea" v-model="data.mattres_need_attention" :autosize="{minRows: 3,maxRows: 5}" :maxlength="200"/>
                <!-- <Input  v-model="data.mattres_need_attention" :maxlength="200"/> -->
            </FormItem>
            <FormItem label="承诺内容" prop="promise_content">
                <Input type="textarea" v-model="data.promise_content" :autosize="{minRows: 3,maxRows: 5}" :maxlength="200"/>
            </FormItem>
            </Form>
        <div class="tc pt20">
            <Button type="primary" @click="handleBack">上一步</Button>
            <Button type="primary" @click="handleSave">下一步</Button>
            <Button type="text" @click="handleNext">以后再完善</Button>
        </div>
    </div>
</template>
<script>
    export default {
        data() {
            return{
                data:{
                    service_times: [],//服务时间
                    mattres_need_attention: '',//注意事项
                    promise_content: '',//承诺内容
                    id:''
                },
                ruleInline:{
                    service_times:[{required: true, type: 'array', max: 2, message:'请选择服务时间', trigger: 'blur'}],
                    mattres_need_attention:[{required: true, message:'请输入注意事项', trigger: 'blur'}],
                    promise_content:[{required: true, message:'请输入承诺内容', trigger: 'blur'}],
                }
            }
        },
        created () {
            this.data.id = this.$route.query.id
            if(this.data.id){
                this.handleInit()
            }
        },
        methods:{
            // 服务时间改变
            timeChange () {
                let data = this.data
                let start = data.service_times[0]
                let end = data.service_times[1]
                if (start && end){
                    this.data.service_time = `${this.moment(start).format('YYYY/MM/DD')} 至 ${this.moment(end).format('YYYY/MM/DD')}`
                } else {
                    this.data.service_time = ``
                }
            },
            //初始化获取数据
            handleInit(){
                this.$api.post('/member/fishing/findFishingService',{id: this.data.id,pageNum:1}).then(response => {
                    if(response.code == 200){
                        if(response.data.list[0]){
                            let data = response.data.list[0]
                            let time = []
                            if (data.service_time) {
                                time = data.service_time.split('至')
                            }
                            data.service_times = time
                            this.data = data
                        }
                    }
                })
            },
            // 保存并继续
            handleSave () {
                if(this.data)
                this.$refs['data'].validate((valid) => {
                    if (valid) {
                        this.$api.post('/member/fishing/updateFishingService',this.data).then(response=>{
                            if(response.code == 200){
                                this.$Message.success('保存成功')
                                // this.$router.push('/stayAddService/step5')
                                // this.$router.push('/stay/service')
                                this.$router.push('/stayAddService/step5?id=' + this.data.id)
                            }
                        })  
                    } else {
                        this.$Message.error('请核对输入信息!');
                    }
                }) 
            },
            //日期格式化
            handleChange(e){
                this.data.service_times = e
                this.timeChange()
            },
            // 以后在完善            
            handleNext () {
                this.$router.push('/stay/service')
            },            
            // 上一步
            handleBack () {
                this.$router.push('/stayAddService/step2?id=' + this.data.id)
            },
        }
    }
</script>