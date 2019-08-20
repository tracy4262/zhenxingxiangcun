<template>
    <div class="pd20">
        <Form  ref="data" :model="data" label-position="left" :label-width="150">
            <Row :gutter="32">
                <Col span="12" :class="data.qualification ? 'pb20' : ''">
                    <Form-item prop="qualification" label="资质">{{data.qualification}}</Form-item>
                </Col>
                <Col span="24" :class="data.license.length || data.licenceExplain ? 'pb20' : ''">
                    <Form-item prop="license" label="生产或销售许可证：">
                        <p v-if="data.licenceExplain">{{data.licenceExplain}}</p>
                        <Row>
                            <Col span="6" v-for="(item, index) in data.license" :key="index">
                                <div style="width:100%;height:100%" class="pd10">
                                    <img :src="item" alt="" width="100%" height="130px;">
                                </div>
                            </Col>
                        </Row>
                    </Form-item>
                </Col>
                <Col span="24" :class="data.validationNumber.length || data.validationNumberExplain ? 'pb20' : ''">
                    <Form-item prop="validationNumber" label="品种审定编号：">
                        <p  v-if="data.validationNumberExplain">{{data.validationNumberExplain}}</p>
                        <Row>
                            <Col span="6" v-for="(item, index) in data.validationNumber" :key="index">
                                <div style="width:100%;height:100%" class="pd10">
                                    <img :src="item" alt="" width="100%" height="130px;">
                                </div>
                            </Col>
                        </Row>
                    </Form-item>
                </Col>
                <Col span="24" :class="data.certification.length || data.quarantineQualifieExplain ? 'pb20' : ''">
                    <Form-item prop="certification" label="产地检疫合格证：">
                        <p v-if="data.quarantineQualifieExplain">{{data.quarantineQualifieExplain}}</p>
                       <Row>
                            <Col span="6" v-for="(item, index) in data.certification" :key="index">
                                <div style="width:100%;height:100%" class="pd10">
                                    <img :src="item" alt="" width="100%" height="130px;">
                                </div>
                            </Col>
                        </Row>
                    </Form-item>
                </Col>
                 <Col span="24" :class="data.certificate.length || data.certificateExplain ? 'pb20' : ''">
                    <Form-item prop="certificate" label="检疫证书：">
                        <p v-if="data.certificateExplain">{{data.certificateExplain}}</p>
                        <Row>
                            <Col span="6" v-for="(item, index) in data.certificate" :key="index">
                                <div style="width:100%;height:100%" class="pd10">
                                    <img :src="item" alt="" width="100%" height="130px;">
                                </div>
                            </Col>
                        </Row>
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
                    qualification: '', // 资质
                    license: [], // 生产或销售许可证
                    validationNumber: [], // 品种审定编号
                    certification: [], // 产地检疫合格证
                    certificate: [] // 检疫证书
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
