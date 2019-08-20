<template>
    <div class="pd20">
        <Form  ref="data" :model="data" label-position="left" :label-width="150">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="deliveryMethods" label="送货方式：">
                        {{data.deliveryMethods}}
                    </Form-item>
                </Col>
                <Col span="12" v-if="data.deliveryMethods === '送货上门'">
                    <Form-item prop="transportMethods" label="发货方式：">
                        {{data.transportMethods}}
                    </Form-item>
                </Col>
                <Col span="12" v-if="data.deliveryMethods === '送货上门'">
                    <Form-item prop="deliveryArea" label="选择配送范围：">
                        {{data.deliveryArea}}
                    </Form-item>
                </Col>
                <Col span="12" v-if="data.deliveryMethods === '送货上门'">
                    <Form-item prop="paymentMethod" label="运费支付方：">
                        {{data.paymentMethod}}
                    </Form-item>
                </Col>
                <Col span="12" v-if="data.deliveryMethods === '送货上门'">
                    <Form-item prop="negotiationFreight" label="是否双方协定运费：">
                        {{data.negotiationFreight}}
                    </Form-item>
                </Col>
                 <Col span="12" v-if="data.deliveryMethods === '送货上门'">
                    <Form-item prop="freight" label="运费：">
                        <span>{{data.freight}}</span>
                        <span v-if="data.freight">元</span>
                    </Form-item>
                </Col>
                <Col span="24" v-if="data.deliveryMethods === '上门取货'">
                    <Form-item prop="pickupLocation" label="取货地点：">
                        {{data.pickupLocation}}
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
                    deliveryMethods: '送货上门', // 送货方式
                    transportMethods: '', // 发货方式
                    deliveryArea: '', // 选择配送范围
                    paymentMethod: '', // 运费支付方
                    negotiationFreight: '', // 是否双方协定运费
                    freight: '', // 运费
                    pickupLocation: '' // 取货地点
                }
            }
        },
        methods:{
            getData (val) {
                this.data = val
                if (this.data.freight) {
                    this.data.freight = this.data.freight + ''
                }
            }
        }
    }
</script>
<style>

</style>
