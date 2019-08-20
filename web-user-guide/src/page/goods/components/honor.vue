<template>
    <div class="pt30 pl10 pr10">
        <Form  ref="data" :model="data" label-position="left" :label-width="150" :rules="ruleInline">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="qualification" label="“三品一标”证书">
                        <Select v-model="data.qualification" style="width: 100%" multiple>
                            <Option v-for="item in credentialss" :value="item.value" :key="item.value">{{ item.label }}</Option>
                        </Select>
                    </Form-item>
                </Col>
                 <Col span="24">
                    <Form-item prop="certificate" label="无公害证书">
                        <vui-upload
                            ref="license"
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
                    qualification: [], // “三品一标”证书
                    certificate: [] // 无公害证书
                },
                ruleInline: {
                },
                // 无认证/无公害/绿色食品/有机食品/地方标志
                credentialss: [
                    {label:'无认证', value: '无认证'},
                    {label:'无公害', value: '无公害'},
                    {label:'绿色食品', value: '绿色食品'},
                    {label:'有机食品', value: '有机食品'},
                    {label:'地方标志', value: '地方标志'}
                ]
            }
        },
        methods:{
            getData (val) {
                this.data = val
                this.$refs['license'].handleGive(this.data.certificate)
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
            // 证书
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
