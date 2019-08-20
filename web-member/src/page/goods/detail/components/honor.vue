<template>
    <div class="pd20">
        <Form  ref="data" :model="data" label-position="left" :label-width="150" :rules="ruleInline">
            <Row :gutter="32">
                <Col span="12" :class="data.qualification.length ? 'pb20' : ''">
                    <Form-item prop="qualification" label="“三品一标”证书：">
                        <span v-for="(item, index) in data.qualification">
                            {{item }}{{index+1 === data.qualification.length ? '。' : '、'}}
                        </span>
                    </Form-item>
                </Col>
                 <Col span="24" :class="data.certificate.length ? 'pb20' : ''">
                    <Form-item prop="certificate" label="无公害证书：">
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
    import vuiUpload from '~components/vui-upload'
    export default {
        components: {
            vuiUpload
        },
        data () {
            return {
                data: {
                    qualification: '', // “三品一标”证书
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
