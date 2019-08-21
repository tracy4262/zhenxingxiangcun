<template>
    <div class="pd20 pr10">
        <Form  ref="data" :model="data" label-position="left" :label-width="150" >
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="date" label="日期：">
                        {{data.date}}
                    </Form-item>
                </Col>
                <Col span="12" v-if="data.date === '采收日期'">
                    <Form-item prop="harvestDate" label="时间段：">
                        {{data.harvestDate}}
                    </Form-item>
                </Col>
                <Col span="12" v-else>
                    <Form-item prop="productionDate" label="时间：">
                        {{data.productionDate}}
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="shelfLife" label="保质期：">
                        <span>{{data.shelfLife}}</span>
                        <span v-if="data.shelfLife">月</span>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="shelfLifeTo" label="保质期至：">
                        {{data.shelfLifeTo}}
                    </Form-item>
                </Col>
            </Row>        
        </Form>
    </div>
</template>
<script>
    import vuiUpload from '~components/vui-upload'
    import {isNumber} from '~utils/validate'
    export default {
        components: {
            vuiUpload
        },
        data () {
            return {
                data: {
                    date: '', // 日期
                    harvestDate: '', // 时间段 采收日期
                    productionDate: '', // 时间 生产日期
                    shelfLife: '', // 保质期
                    shelfLifeTo: '', // 保质期至
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
