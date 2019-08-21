<template>
<div class="pt30 pl10 pr10">
    <Form ref="data" :model="data" label-position="left" :label-width="150" :rules="ruleInline">
        <Row :gutter="32">
            <Col span="24">
            <Form-item prop="servicePolicy" label="售后服务政策">
                <Input v-model="data.servicePolicy" type="textarea" :autosize="{minRows: 3,maxRows: 4}" :maxlength="200"></Input>
            </Form-item>
            </Col>
            <Col span="24">
            <Form-item prop="aftermarketOutlets" label="售后网点">
                <Input v-model="data.aftermarketOutlets" type="textarea" :autosize="{minRows: 3,maxRows: 4}" :maxlength="200"></Input>
            </Form-item>
            </Col>
            <Col span="24">
            <Form-item prop="returnAndRepair" label="退换货政策">
                <Input v-model="data.returnAndRepair" type="textarea" :autosize="{minRows: 3,maxRows: 4}" :maxlength="200"></Input>
            </Form-item>
            </Col>
        </Row>
    </Form>
</div>
</template>
<script>
export default {
    data() {
        return {
            data: {
                servicePolicy: '', // 售后服务政策
                aftermarketOutlets: '', // 售后网点
                returnAndRepair: '' // 退换货政策
            },
            ruleInline: {
                servicePolicy: [{
                    required: true,
                    message: '请输入售后服务政策',
                    trigger: 'blur'
                }]
            }
        }
    },
    methods: {
        getData(val) {
            this.data = val
        },
        handleSubmit() {
            this.$refs['data'].validate((valid) => {
                if (valid) {
                    this.$emit('on-submit', true)
                } else {
                    this.$emit('on-submit', false)
                }
            })
        }
    }
}
</script>
<style>

</style>
