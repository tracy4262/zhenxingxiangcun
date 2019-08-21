<template>
    <div class="pd20" style="min-height: 500px; width: 720px; margin: 0 auto;">
        <!-- 基础信息表单 -->
        <Form ref="data" :model="data" :label-width="120" label-position="left" :rules="ruleInline" class="mt20">
            <FormItem label="服务名称" prop="serviceName">
                <Input v-model="data.serviceName" :maxlength="20" placeholder="水稻种植技术咨询服务"/>
            </FormItem>
            <FormItem label="服务介绍" prop="serviceDescribe">
                 <quil-editor @quilCon="getServiceIntroduce" :content="data.serviceDescribe"></quil-editor>
            </FormItem>
            <FormItem label="咨询专家" prop="expert">
                <Input v-model="data.expert" readonly />
            </FormItem>
            <FormItem label="个人照片" prop="personalPicture">
                <img :src="data.personalPicture" style="width: 80px; height: 80px;" />
            </FormItem>
            <FormItem label="专家分类" prop="expertType">
                <Input v-model="data.expertType" readonly />
            </FormItem>
            <FormItem label="擅长领域">
                <Input v-model="data.adeptField" readonly />
            </FormItem>
            <FormItem label="相关物种">
                <Input v-model="data.relatedSpecies" readonly />
            </FormItem>
            <FormItem label="相关经验">
                <Input v-model="data.relatedExperience" type="textarea" />
            </FormItem>
            <FormItem label="咨询时间段">
                <Input v-model="data.time" type="textarea" placeholder="例如：周一 9:00 — 12:00" />
            </FormItem>
            <FormItem label="联系人信息" prop="contactInfo">
                <Button type="success" ghost @click="chooseInfo" class="btn-light-primary">从基础设置中选择</Button>
                <Button type="success" ghost @click="addInfo" class="btn-light-primary">添加</Button>
            </FormItem>
        </Form>
        <!-- 联系人信息表单 -->
        <contact ref="contact"></contact>
        <!-- 交通信息表单 -->
        <Form ref="data3" :model="data" :label-width="120"  label-position="left" :rules="ruleInline" >
            <FormItem label="公交路线指引">
                <Row v-for="(item, index) in data.busLead" class="pb15">
                    <Col span="18"><Input v-model="item.name"  :maxlength="200"/></Col>
                    <Col span="6" class="pl15">
                        <Button @click="delBusLead(index)" v-if="data.busLead.length > 1" class="mr5">删除</Button>
                        <Button @click="addBusLead" v-if="index === 0">添加</Button>
                    </Col>
                </Row>
            </FormItem>
             <FormItem label="自驾路线指引">
                 <Row v-for="(item, index) in data.carLead"  class="pb15">
                    <Col span="18"><Input v-model="item.name" :maxlength="200"/></Col>
                    <Col span="6" class="pl15">
                        <Button @click="delTrafficLead(index)" v-if="data.carLead.length > 1" class="mr5">删除</Button>
                        <Button @click="addTrafficLead" v-if="index === 0" >添加</Button>
                    </Col>
                </Row>
            </FormItem>
        </Form>
        <div class="tc pt20">
            <Button type="primary" @click="handleBack">上一步</Button>
            <Button type="primary" @click="handleSave">下一步</Button>
            <Button type="text" @click="handleNext">以后再完善</Button>
        </div>
    </div>
</template>
<script>
import quilEditor from '~components/vuequilEditor'
import contact from './contact'
export default {
    components:{
        quilEditor,
        contact
    },
    data () {
        return {
            data: {
                serviceName: '',
                serviceDescribe: '',
                expert: '',
                personalPicture: '',
                expertType: '',
                adeptField: '',
                relatedSpecies: '',
                relatedExperience: '',
                time: '',
                contactInfo: [],
                busLead: [
                    {
                        name: ''
                    }
                ],
                carLead: [
                    {
                        name: ''
                    }
                ]
            },
            ruleInline: {
                serviceName: [{ required: true, message:'请输入服务名称', trigger: 'blur' }],
                serviceDescribe: [{ required: true, message:'请输入服务介绍', trigger: 'blur' }],
                expert: [{ required: true, message:'请先完成专家认证', trigger: 'blur' }],
                personalPicture: [{ required: true, message:'请先完成专家认证', trigger: 'blur' }],
                contactInfo: [{ required: true, type: 'array', min: 1, message:'请选择联系人信息', trigger: 'change' }]
            },
            id: '',
            expertId: '',
            account: this.$user.loginAccount
        }
    },
    created () {
        this.id = this.$route.query.id
        this.handleInit()
    },
    methods: {
        handleInit () {
            this.$api.post('/member-reversion/consult/findStepTwo', {
                id: this.id,
                account: this.account
            }).then(response => {
                if (response.code === 200) {
                    if (response.data.serviceName) {
                        this.data.serviceName = response.data.serviceName
                    }
                    if (response.data.serviceDescribe) {
                        this.data.serviceDescribe = response.data.serviceDescribe
                    }
                    if (response.data.expert) {
                        this.data.expert = response.data.expert
                    }
                    if (response.data.personalPicture) {
                        this.data.personalPicture = response.data.personalPicture
                    }
                    if (response.data.expertType) {
                        this.data.expertType = response.data.expertType
                    }
                    if (response.data.adeptField) {
                        this.data.adeptField = response.data.adeptField
                    }
                    if (response.data.relatedSpecies) {
                        this.data.relatedSpecies = response.data.relatedSpecies
                    }
                    if (response.data.relatedExperience) {
                        this.data.relatedExperience = response.data.relatedExperience
                    }
                    if (response.data.time) {
                        this.data.time = response.data.time
                    }
                    if (response.data.contactInfo) {
                        this.data.contactInfo = response.data.contactInfo
                        this.$refs['contact'].data = response.data.contactInfo
                    }
                    if (response.data.busLead) {
                        this.data.busLead = response.data.busLead
                    }
                    if (response.data.carLead) {
                        this.data.carLead = response.data.carLead
                    }
                    if (response.data.expertId) {
                        this.expertId = response.data.expertId
                    }
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        // 获取服务介绍
        getServiceIntroduce (e) {
            this.data.serviceDescribe = e
        },
        // 公交路线指引
        addBusLead () {
            this.data.busLead.push({name: ''})
        },
        delBusLead (index) {
            this.$Modal.confirm({
                title: '操作提示',
                content: '是否确认删除？',
                onOk:()=>{
                    this.data.busLead.splice(index,1)
                    this.$Message.success('删除成功！')
                },
                okText:'确定',
                cancelText:'取消'
            })
        },
        // 自驾路线指引
        addTrafficLead () {
            this.data.carLead.push({name: ''})
        },
        delTrafficLead (index) {
            this.$Modal.confirm({
                title: '操作提示',
                content: '是否确认删除？',
                onOk:()=>{
                    this.data.carLead.splice(index,1)
                    this.$Message.success('删除成功！')
                },
                okText:'确定',
                cancelText:'取消'
            })
        },
        handleBack () {
            this.$router.push({
                path: '/addConsultationService/step1',
                query: {
                    id: this.id
                }
            })
            this.$emit('last')
        },
        handleSave () {
            this.data.contactInfo = this.$refs['contact'].data // 从组件中取得联系人的数据
            this.data.account = this.account
            this.data.id = this.id
            this.data.expertId = this.expertId
            this.$refs['data'].validate((valid) => {
                if (valid) {
                    // 联系人信息表单验证
                    let flag = true
                    for (let index = 0; index < this.data.contactInfo.length; index++) {
                        this.$refs['contact'].$refs[`data${index}`][0].validate((val) => {
                            if (!val) {
                                flag = false
                            }
                        })
                    }
                    if (flag) {
                        this.$api.post('/member-reversion/consult/publishStepTwo', this.data).then(response => {
                            if (response.code === 200) {
                                this.$Message.success('保存成功！')
                                this.$router.push({
                                    path: '/addConsultationService/step3',
                                    query: {
                                        id: this.id
                                    }
                                })
                                this.$emit('next')
                            }
                        }).catch(error => {
                            this.$Message.error('服务器异常！')
                        })
                    } else {
                        this.$Message.error('请核对联系人信息表单字段！')
                    }
                } else {
                    this.$Message.error('请核对表单字段！')
                }
            })
        },
        handleNext () {
            this.$router.push('/service/consultationService')
        },
        chooseInfo () {
            this.$refs['contact'].chooseInfo()
        },
        addInfo () {
            this.$refs['contact'].addInfo()
        }
    }
}
</script>
