<template>
<div class="pd20">
    <Form ref="data" :model="data" label-position="left" :label-width="150" >
        <Row :gutter="32">
            <Col span="24">
            <Form-item prop="servicePolicy" label="售后服务政策：">
                {{data.servicePolicy}}
            </Form-item>
            </Col>
            <Col span="24">
            <Form-item prop="aftermarketOutlets" label="售后网点：">
                {{data.aftermarketOutlets}}
            </Form-item>
            </Col>
            <Col span="24">
            <Form-item prop="returnAndRepair" label="退换货政策：">
                {{data.returnAndRepair}}
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
            }
        }
    },
    methods: {
        getData(val) {
            this.data = val
        }
    }
}
</script>
<style>

</style>
