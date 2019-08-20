<template>
    <div class="pt30 pl10 pr10">
        <Form  ref="data" :model="data" label-position="left" :label-width="150" :rules="ruleInline">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="qualification" label="资质">
                        <Select v-model="data.qualification" style="width: 100%">
                            <Option v-for="item in qualifications" :value="item.value" :key="item.value">{{ item.label }}</Option>
                        </Select>
                    </Form-item>
                </Col>
                <!-- <Col span="24">
                    <Form-item label="生产或销售许可证说明">
                        <Input v-model="data.licenceExplain" :maxlength="50"></Input>
                    </Form-item>
                </Col> -->
                <Col span="24">
                    <Form-item prop="license" label="生产或销售许可证">
                        <div class="pb20">
                            <Input v-model="data.licenceExplain" :maxlength="50"></Input>
                        </div>
                        <vui-upload
                            ref="license"
                            @on-getPictureList="getLicense"
                            :total="50"
                            :hint="'图片大小小于2M'"
                            :size="[100, 100]"
                            ></vui-upload>
                    </Form-item>
                </Col>
                <!-- <Col span="24">
                    <Form-item label="品种审定编号说明">
                        <Input v-model="data.validationNumberExplain" :maxlength="50"></Input>
                    </Form-item>
                </Col> -->
                <Col span="24">
                    <Form-item prop="validationNumber" label="品种审定编号">
                        <div class="pb20">
                            <Input v-model="data.validationNumberExplain" :maxlength="50"></Input>
                        </div>
                        <vui-upload
                            ref="validationNumber"
                            @on-getPictureList="getValidationNumber"
                            :total="50"
                            :hint="'图片大小小于2M'"
                            :size="[100, 100]"
                            ></vui-upload>
                    </Form-item>
                </Col>
                <!-- <Col span="24">
                    <Form-item label="产地检疫合格证说明">
                        <Input v-model="data.quarantineQualifieExplain" :maxlength="50"></Input>
                    </Form-item>
                </Col> -->
                <Col span="24">
                    <Form-item prop="certification" label="产地检疫合格证">
                        <div class="pb20">
                            <Input v-model="data.quarantineQualifieExplain" :maxlength="50"></Input>
                        </div>
                        <vui-upload
                            ref="certification"
                            @on-getPictureList="getCertification"
                            :total="50"
                            :hint="'图片大小小于2M'"
                            :size="[100, 100]"
                            ></vui-upload>
                    </Form-item>
                </Col>
                <!-- <Col span="24">
                    <Form-item label="检疫证书说明">
                        <Input v-model="data.certificateExplain" :maxlength="50"></Input>
                    </Form-item>
                </Col> -->
                 <Col span="24">
                    <Form-item prop="certificate" label="检疫证书">
                        <div class="pb20">
                            <Input v-model="data.certificateExplain" :maxlength="50"></Input>
                        </div>
                        <vui-upload
                            ref="certificate"
                            @on-getPictureList="getCertificate"
                            :total="50"
                            :hint="'图片大小小于2M'"
                            :size="[100, 100]"
                            ></vui-upload>
                    </Form-item>
                </Col>
            </Row>        
        </Form>
    </div>
</template>
<script>
    import vuiUpload from '~components/vui-upload'
    export default {
        components: {
            vuiUpload
        },
        data () {
            return {
                data: {
                    qualification: '', // 资质
                    license: [], // 生产或销售许可证
                    licenceExplain: '',
                    validationNumber: [], // 品种审定编号
                    validationNumberExplain: '',
                    certification: [], // 产地检疫合格证
                    quarantineQualifieExplain: '',
                    certificate: [], // 检疫证书
                    certificateExplain: ''
                },
                ruleInline: {
                },
                qualifications: [
                    {label:'进口', value: '进口'},
                    {label:'国产', value: '国产'}
                ]
            }
        },
        methods:{
            getData (val) {
                this.data = val 
                this.$refs['license'].handleGive(this.data.license)
                this.$refs['validationNumber'].handleGive(this.data.validationNumber)
                this.$refs['certification'].handleGive(this.data.certification)
                this.$refs['certificate'].handleGive(this.data.certificate)
            },
            handleSubmit () {
                this.$refs['data'].validate((valid) => {
                    if(valid) {
                        this.$emit('on-submit',true)
                    }else{
                        this.$emit('on-submit',false)
                    }
                })
            },
            // 获取图片
            getLicense (e) {
                var arr = []
                e.forEach(element => {
                        if(element.response){
                        arr.push(element.response.data.picName)
                    }
                })
                this.data.license = arr
            },
            // 品种审定编号
            getValidationNumber (e) {
                var arr = []
                e.forEach(element => {
                        if(element.response){
                        arr.push(element.response.data.picName)
                    }
                })
                this.data.validationNumber = arr
            },
            // 合格证
            getCertification (e) {
              var arr = []
                e.forEach(element => {
                        if(element.response){
                        arr.push(element.response.data.picName)
                    }
                })
                this.data.certification = arr
            },
            // 检疫证书
            getCertificate (e) {
              var arr = []
                e.forEach(element => {
                        if(element.response){
                        arr.push(element.response.data.picName)
                    }
                })
                this.data.certificate = arr
            },
        }
    }
</script>
<style>

</style>
