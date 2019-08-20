<template>
    <div class="pd20" style="min-height: 500px; width: 720px; margin: 0 auto;">
        <Form ref="data" :model="data" :label-width="120" label-position="left" :rules="ruleInline" class="mt20">
            <FormItem label="通用服务名称" prop="currencyServiceName">
                <Input v-model="data.currencyServiceName" readonly></Input>
            </FormItem>
            <FormItem label="行业分类">
                <Input v-model="data.tradeClassId" readonly></Input>
            </FormItem>
            <FormItem label="服务分类">
                <Input v-model="data.serviceClassId" readonly></Input>
            </FormItem>
        </Form>
        <div class="tc pt20">
            <Button type="primary" @click="handleSave">下一步</Button>
            <Button type="text" @click="handleNext">以后再完善</Button>
        </div>
    </div>
</template>
<script>
export default {
    components:{
    },
    data () {
        return {
            data: {
                currencyServiceName: '咨询服务',
                tradeClassId: '咨询行业',
                serviceClassId: '咨询'
            },
            ruleInline: {
                currencyServiceName:[{required: true,message:'请输入通用服务名称', trigger: 'blur'}]
            },
            id: '',
            account: this.$user.loginAccount
        }
    },
    created () {
        // 如果是编辑进来的话就去查询
        if (this.$route.query.id && this.$route.query.id !== '') {
            this.id = this.$route.query.id
            this.handleInit()
        }
    },
    methods: {
        handleInit () {
            this.$api.get('/member-reversion/consult/findStepOne?id=' + this.id).then(response => {
                if (response.code === 200) {
                    this.data = response.data
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        handleSave () {
            this.$refs['data'].validate((valid) => {
                if (valid) {
                    this.$api.post('/member-reversion/consult/publishStepOne', {
                        id: this.id,
                        currencyServiceName: this.data.currencyServiceName,
                        tradeClassId: this.data.tradeClassId,
                        serviceClassId: this.data.serviceClassId,
                        account: this.account,
                        type: 5 // 5为咨询服务
                    }).then(response => {
                        if (response.code === 200) {
                            this.$Message.success('保存成功！')
                            this.$router.push({
                                path: '/addConsultationService/step2',
                                query: {
                                    id: response.data
                                }
                            })
                            this.$emit('next')
                        }
                    }).catch(error => {
                        this.$Message.error('服务器异常！')
                    })
                } else {
                    this.$Message.error('请核对表单字段！')
                }
            })
        },
        handleNext () {
            this.$router.push('/service/consultationService')
        }
    }
}
</script>

