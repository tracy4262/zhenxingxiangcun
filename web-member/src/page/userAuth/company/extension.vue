<template>
  <div class="pt30 pl10 pr10">
    <Form  ref="formItem" :model="formItem" label-position="left" :label-width="150" :rules="formItemInline">
        <Row :gutter="32">
            <Col span="12">
                <Form-item prop="logoList" label="企业LOGO">
                    <vui-upload
                        @on-getPictureList="getlogoList"
                        :pictureLists="formItem.logoList"
                        :hint="'支持拓格式jpg/png'"
                        :size="[80,80]"
                    ></vui-upload>
                </Form-item>
            </Col>
        </Row>
        <Row :gutter="32">
            <Col span="12">
                <Form-item prop="website" label="官方网站">
                    <Input v-model="formItem.website"></Input>
                </Form-item>
            </Col>
            <Col span="12" class="">
                <Form-item prop="serviceTelephone" label="客服电话">
                    <Input v-model="formItem.serviceTelephone" :maxlength="20"></Input>
                </Form-item>
            </Col>
        </Row>
        <Row :gutter="32">
            <Col span="12">
                <Form-item prop="blogList" label="官方微博">
                    <vui-upload
                        @on-getPictureList="getBlogList"
                        :pictureLists="formItem.blogList"
                        :hint="'支持拓格式jpg/png'"
                    ></vui-upload>
                </Form-item>
            </Col>
            <Col span="12">
                <Form-item prop="weChatList" label="官方微信公众号">
                    <vui-upload
                        @on-getPictureList="getWeChatList"
                        :pictureLists="formItem.weChatList"
                        :hint="'支持拓格式jpg/png'"
                    ></vui-upload>
                </Form-item>
            </Col>
        </Row>
    </Form>
  </div>
</template>
<script>
    import vuiUpload from '~components/vui-upload'
    import {isUrl,isisNumber} from '~utils/validate'
    export default{
        components:{
            vuiUpload
        },
        data(){
            return{
                formItem:{
                    logoList:[],
                    website:'',
                    serviceTelephone:'',
                    blogList:[],
                    weChatList:[]
                },
                formItemInline:{
                    logoList:[
                        {required: true, message: '请上传企业LOGO', type: 'array', max: 1, trigger: 'change'}
                    ],
                    website:[
                        {validator: isUrl,message: '请输入正确的网站地址',trigger: 'blur'}
                    ],
                    serviceTelephone:[
                        {validator: isisNumber,message: '请输入正确电话',trigger: 'blur'}
                    ]
                }
            }
        },
        mounted () {
          this.$refs.formItem.resetFields()
        },
        methods:{            
            // 回显图片 this.$refs['upload'].handleGive( this.leaderForm.avatar)
            handleSubmit () {
                this.$refs['formItem'].validate((valid) => {
                    if (valid) {
                        return true
                    } else {
                        return false
                    }
                })
            },
            // 获取企业logo图片
            getlogoList(e){
                this.formItem.logoList = e
            },
            //获取官方微博图片
            getBlogList(e){
                this.formItem.blogList =e
            },
            // 获取企业微信图片
            getWeChatList(e){
                this.formItem.weChatList =e
            }
        }

    }
</script>
<style>

</style>
