<template>
    <div class="pd20" style="min-height: 500px; width: 720px; margin: 0 auto;">
        <Form ref="data" :model="data" :label-width="120" label-position="left" :rules="ruleInline" class="mt20">
            <FormItem label="注意事项" prop="attention">
                <Input type="textarea" v-model="data.attention"></Input>
            </FormItem>
            <FormItem label="承诺内容" prop="promise">
                <Input type="textarea" v-model="data.promise"></Input>
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
    data () {
        return {
            data: {
                attention: '',
                promise: ''
            },
            ruleInline: {
                attention:[{required: true,message:'请填写注意事项', trigger: 'change'}],
                promise: [{required: true,message:'请填写承诺内容', trigger: 'change'}]
            },
            account: this.$user.loginAccount,
            id: ''
        }
    },
    created () {
        this.id = this.$route.query.id
        this.handleInit()
    },
    methods: {
        handleInit () {
            this.$api.post('/member-reversion/consult/findStepFour', {
                id: this.id,
                account: this.account
            }).then(response => {
                if (response.code === 200) {
                    if (response.data.attention) {
                        this.data.attention = response.data.attention
                    }
                    if (response.data.promise) {
                        this.data.promise = response.data.promise
                    }
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        handleBack () {
            this.$router.push({
                path: '/addConsultationService/step3',
                query: {
                    id: this.id
                }
            })
            this.$emit('last')
        },
        handleSave () {
            this.data.account = this.account
            this.data.id = this.id
            this.$refs['data'].validate((valid) => {
                if (valid) {
                    this.$api.post('/member-reversion/consult/publishStepFour', this.data).then(response => {
                        if (response.code === 200) {
                            this.$Message.success('保存成功！')
                            this.$router.push({
                                path: '/addConsultationService/step5',
                                query: {
                                    id: this.id
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
