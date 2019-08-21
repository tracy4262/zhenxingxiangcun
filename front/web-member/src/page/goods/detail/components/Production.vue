<template>
    <div class="pd20">
        <Form  ref="data" :model="data" label-position="left" :label-width="150" >
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="whether" label="是否投入品或原料：">{{data.whether}}</Form-item>
                </Col>
                <Col span="12" v-if="data.whether == '是'">
                    <Form-item prop="rawMaterial" label="投入品或原料：">{{data.rawMaterial}}</Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="productionMethods" label="生产方式：">{{data.productionMethods}}</Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="roughProcessing" label="粗加工方式：">{{data.roughProcessing}}</Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="transgenic" label="是否转基因：">{{data.transgenic}}</Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="irradiation" label="是否辐照：">{{data.irradiation}}</Form-item>
                </Col>
            </Row>        
        </Form>
    </div>
</template>
<script>
    export default {
        data () {
            return {
                data: {
                    whether: '', // 是否投入品或原料
                    rawMaterial: '', // 投入品或原料
                    productionMethods: '', // 生产方式
                    roughProcessing: '', // 粗加工方式
                    transgenic: '', // 是否转基因
                    irradiation: '' //是否辐照
                }          
            }
        },
        methods:{
            getData (val) {
                this.data = val 
            }
        }
    }
</script>
<style>

</style>
