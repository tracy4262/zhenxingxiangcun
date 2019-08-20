<template>
  <div>
      <Modal
            v-model="leaderShow"
            :title="title"
            :mask-closable="false"
            width="50%">
            <div class="pd20">
                <Form ref="leaderForm" :model="leaderForm" label-position="left" :label-width="100" :rules="leaderRules">
                    <Row :gutter="32">
                        <Col :span="12">
                            <Form-item prop="job" label="职务">
                                <Input v-model="leaderForm.job" :maxlength="20"></Input>
                            </Form-item>
                        </Col>
                        <Col :span="12">
                            <Form-item prop="name" label="姓名">
                                <Input v-model="leaderForm.name" :maxlength="20"></Input>
                            </Form-item>
                        </Col>
                    </Row>
                    <Row :gutter="32">
                        <Col :span="12">
                            <Form-item prop="degree" label="学历">
                                <Select v-model="leaderForm.degree">
                                    <Option v-for="item in degrees" :value="item.value" :key="item.value">{{ item.label }}</Option>
                                </Select>
                            </Form-item>
                        </Col>
                        <Col :span="12">
                            <Form-item prop="idcard" label="身份证号码">
                                <Input v-model="leaderForm.idcard" :maxlength="18"></Input>
                            </Form-item>
                        </Col>
                    </Row>
                    <Row :gutter="32">
                        <Col :span="12">
                            <Form-item prop="phone" label="手机号码">
                                <Input v-model="leaderForm.phone" :maxlength="11"></Input>
                            </Form-item>
                        </Col>
                    </Row>
                    <Row :gutter="32">
                        <Col :span="24">
                            <Form-item prop="avatar" label="照片">
                                <vui-upload
                                    ref="upload"
                                    @on-getPictureList="getPictureList"
                                    :pictureLists="leaderForm.avatar"
                                    :total="1"
                                    :multiple="false"
                                    :hint="'图片大小小于2M'">
                                </vui-upload>
                            </Form-item>
                        </Col>
                    </Row>
                    <Row :gutter="32">
                        <Col :span="24">
                            <Form-item prop="introduction" label="简介">
                                <Input v-model="leaderForm.introduction" type="textarea" :rows="4" :maxlength="500"></Input>
                            </Form-item>
                        </Col>
                    </Row>
                </Form>
            </div>
            <div slot="footer" class="tc">
                <Button type="default" @click="leaderShow = false">取消</Button>
                <Button type="primary" @click.native="handleSubmit">确定</Button>
            </div>
        </Modal>
  </div>
</template>
<script>
import {isIdCard2,isPhone2} from '~utils/validate'
import vuiUpload from '~components/vui-upload'
    export default{
        components :{
            vuiUpload
        },
        props :{
            leaderForm : {
                type : Object,
                default : ()=>{
                    return {
                        job:'',
                        name:'',
                        degree:'',
                        idcard:'',
                        phone:'',
                        avatar:'',
                        introduction:'',
                    }
                }
            },
            title : {
                type : String,
                default : '添加成员'
            },
            type : {
                type : String,
                default : 'add'
            }
        },
        data () {
            return {
                leaderShow:false,
                degrees:[{
                        value: '小学',
                        label: '小学'
                    },
                    {
                        value: '初中',
                        label: '初中'
                    },
                    {
                        value: '高中',
                        label: '高中'
                    },
                    {
                        value: '高职高专',
                        label: '高职高专'
                    },
                    {
                        value: '大专',
                        label: '大专'
                    },
                    {
                        value: '本科',
                        label: '本科'
                    },
                    {
                        value: '研究生',
                        label: '研究生'
                    },
                    {
                        value: '博士',
                        label: '博士'
                    }],
                leaderRules:{
                    job:[
                        {required:true,message:'请输入职务',trigger:'blur'}
                    ],
                    name:[
                        {required:true,message:'请输入姓名',trigger:'blur'}
                    ],
                    idcard:[
                        {validator:isIdCard2,trigger:'blur'}
                    ],
                    phone:[
                        {validator:isPhone2,trigger:'blur'}
                    ]
                },
            }
        },
        methods:{
            //添加时绑定数据置空
            reset () {
                this.$refs['upload'].pictureList = ''
                this.$refs['upload'].$refs.upload.fileList=[]
            },
            //确定保存
            handleSubmit () {
                this.$refs.leaderForm.validate((valid) => {
                    if(valid) {    
                        console.log('on-save')     
                        this.$emit('on-save', this.leaderForm)
                        console.log(this.leaderForm)
                    }
                })
            
            },            
            getPictureList(e){
                this.leaderForm.avatar = e[0].response.data.picName
            },
        }
    }

</script>
<style>


</style>

