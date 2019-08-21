<template>
    <div class="pd20 sales">
        <Form  ref="data" :model="data" label-position="left" :label-width="150" >
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="salesTime" label="销售时间：">
                        {{data.salesTime}}
                    </Form-item>
                </Col>
                <Col span="12" v-if="data.salesTime != '常年供货'">
                    <Form-item prop="availability" label="现货供应时间：">
                        {{data.availability}}
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="pricingMethod" label="定价方式：">
                        {{data.pricingMethod}}
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="currentPrice" label="时价：">
                        <span>{{data.currentPrice}}</span>
                        <span v-if="data.currentPrice">元</span>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="discountPrice" label="折扣价：">
                        <span>{{data.discountPrice}}</span>
                        <span v-if="data.discountPrice">元</span>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="discountPeriod" label="折扣时限：">
                        {{data.discountPeriod}}
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="wholesaleVolume" label="起批量：">
                        <span>{{data.wholesaleVolume}}</span>
                        <span v-if="data.wholesaleVolume">{{data.wholesaleVolumeUnits}}</span>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="wholesalePrice" label="批发价：">  
                        <span>{{data.wholesalePrice}}</span>
                        <span v-if="data.wholesalePrice">元</span>
                    </Form-item>
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
                    salesTime: '常年供货', // 销售时间
                    availability: '', // 现货供应时间
                    pricingMethod: '', // 定价方式
                    currentPrice: '', // 时价
                    discountPrice: '', // 折扣价
                    discountPeriod: '', // 折扣时限
                    wholesaleVolume: '', // 起批量
                    wholesaleVolumeUnits: '公斤', // 起批量 单位
                    wholesalePrice: ''// 批发价
                }
            }
        },
        methods:{
            getData (val) {
                this.data = val
                if (this.data.currentPrice) {
                    this.data.currentPrice += ''
                }
                if (this.data.discountPrice) {
                    this.data.discountPrice += ''
                }
                if (this.data.wholesaleVolume) {
                    this.data.wholesaleVolume += ''
                }
                if (this.data.wholesalePrice) {
                    this.data.wholesalePrice += ''
                }
            }
        }
    }
</script>
<style>

</style>
