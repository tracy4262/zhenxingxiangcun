<template>
    <div class="layout">
        <top :address="false" />

        <div class="main">
            <div class="container">
                <Row :gutter="20">
                    <Col span="4" class="main-l">
                    <high-app name="高级应用" />
                    <Divider />
                    <base-app name="基础应用" />
                    <Divider />
                    <base-app name="通用应用"  />
                    </Col>
                    <Col span="20">
                    <member-header />
                    <div class="wrapper-container">
                        <div style="width: 1000px; margin-left: 40px;">
                            <h1 style="margin-top: 20px;">新增代理机构</h1>
                            <div style="margin-top: 30px;height: 95px">
                                <Steps :current="current" style="margin-left: 80px;">
                                    <Step title="新增组别"></Step>
                                    <Step title="新增种养户"></Step>
                                    <Step title="完成"></Step>
                                </Steps>
                            </div>
                            <p style="text-align:center">小提示：新增分组后，可以按照实际需要对人员分组查看和管理。</p>
                            <div class="tc pt10">
                                <Button type="primary" @click="next">下一步</Button>
                                <Button type="primary">退出</Button>
                            </div>
                            <Collapse v-model="value1">
                                <Panel name="1">
                                    一组农户
                                    <p slot="content">aaa</p>
                                </Panel>
                                <Panel name="2">
                                    二组农户
                                    <p slot="content">bbb</p>
                                </Panel>
                                <Panel name="3">
                                    三组农户
                                    <p slot="content">ccc</p>
                                </Panel>
                            </Collapse>
                        </div>
                    </div>
                    </Col>
                </Row>
            </div>
        </div>
    </div>
</template>

<script>
    import top from '../../top'
    import highApp from '~components/memberHighApp'
    import BaseApp from '~components/memberBaseApp'
    import axios from '~src/api/api'
    import $ from 'jquery'
    import api from '~api'
    import memberHeader from './components/memberHeader'

    export default {
        components: {
            top,
            highApp,
            BaseApp,
            memberHeader
        },

        data() {
            let checkCredit = (rules, value, cb) => {　//转换函数，主要目的是传给store内方法的参数。
                this.$store.dispatch('checkCredit', { rules, value, cb })//这儿的checkAge是写在store中的，vuex规定参数必须传对象。
            }
            let checkPhone = (rules, value, cb) => {　//转换函数，主要目的是传给store内方法的参数。
                this.$store.dispatch('checkPhone', { rules, value, cb })//这儿的checkAge是写在store中的，vuex规定参数必须传对象。
            }
            return {
                displayName: '',
                loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
                signature: '还没有签名！',
                favorite:0,
                num:0,
                value: '',
                customVal: '',
                isNull: true,
                subClassModalShow: false,
                subClassForm: {
                    subClass: '',
                    name: ''
                },
                action: `${this.$url.upload}/upload/up`,
                modal1: false,
                step1: true,
                step2: false,
                step3: false,
                current: 0,
                agentInfo: {
                    type: '',
                    full_name: '',
                    credit_code: '',
                    agent_account: '',
                    legal_name: '',
                    phone: '',
                    register_address: ''
                },
                ruleInline: {
                    type: [
                        { required: true, message: '请选择代理机构类型', trigger: 'change' }
                    ],
                    full_name: [
                        { required: true, message: '被代理机构全称不能为空', trigger: 'blur' }
                    ],
                    agent_account: [
                        { required: true, message: '被代理机构帐号不能为空', trigger: 'blur' }
                    ],
                    credit_code: [
                        { required: true, validator: checkCredit, trigger: 'blur' }
                    ],
                    legal_name: [
                        { required: true, message: '法定代表人姓名不能为空', trigger: 'blur' }
                    ],
                    phone: [
                        { required: true, validator: checkPhone, trigger: 'blur' }
                    ],
                    register_address: [
                        { required: true, message: '机构注册地址不能为空', trigger: 'blur' }
                    ]
                },
                agentInfo2: {
                    agent_agreement_picture_list: []
                },
                ruleInline2: {
                    agent_agreement_picture_list: [
                        { required: true, message: '请上传代理协议图片', type: 'array', min: 1, trigger: 'change' }
                    ]
                },
                agentInfo3: {
                    business_license_picture_list: []
                },
                ruleInline3: {
                    business_license_picture_list: [
                        { required: true, message: '请上传营业执照图片', type: 'array', min: 2, trigger: 'change' }
                    ]
                },
                agentInfo4: {
                    agent_identity_card_picture_list: []
                },
                ruleInline4: {
                    agent_identity_card_picture_list: [
                        { required: true, message: '请上传代理人身份证', type: 'array', min: 2, trigger: 'change' }
                    ]
                },
                agentInfo5: {
                    legal_identity_card_picture_list: []
                },
                ruleInline5: {
                    legal_identity_card_picture_list: [
                        { required: true, message: '请上传法人代表身份证', type: 'array', min: 2, trigger: 'change' }
                    ]
                },
                agentTypeList: [
                    {
                        value: '企业',
                        label: '企业'
                    },
                    {
                        value: '乡村服务站',
                        label: '乡村服务站'
                    },
                    {
                        value: '机关服务大厅',
                        label: '机关服务大厅'
                    }
                ]
            }
        },
        created: function() {
            this.showTop()
        },

        methods: {
            handleSubmit (name) {
                let flag = false;
                this.$refs[name].validate((valid) => {
                    console.log(valid)
                    if (valid) {
                        flag = true;
                        // this.$Message.success('提交成功!');
                    } else {
                        // this.$Message.error('表单验证失败!');
                    }
                })
                return flag;
            },
            searchOrganization () {
                // TODO 调用接口查询对应的机构
                this.$Message.success('查询机构成功!');
            },
            addOrganization () {
                this.$router.push("/pro/addOrganization")
            },
            next() {
                let flag = this.handleSubmit('agentInfo')
                if (flag) {
                    this.step1 = false
                    this.step2 = true
                    this.step3 = false
                    this.current += 1
                }
            },
            back() {
                this.$router.push({
                    path: '/pro/daili',
                    query: {
                        tag: 1
                    }
                })
            },
            breaks() {
                this.step1 = true
                this.step2 = false
                this.step3 = false
                this.current -= 1
            },
            complete () {
                this.$router.push({
                    path: '/pro/daili',
                    query: {
                        tag: 1
                    }
                })
            },
            show() {
                this.modal2 = true;
                this.$router.push("/pro/member/step1")
            },
            submit() {
                let flag2 = this.handleSubmit('agentInfo2')
                let flag3 = this.handleSubmit('agentInfo3')
                let flag4 = this.handleSubmit('agentInfo4')
                let flag5 = this.handleSubmit('agentInfo5')
                // TODO 请求接口增加代理机构信息
                if (flag2 && flag3 && flag4 && flag5) {
                    this.step1 = false
                    this.step2 = false
                    this.step3 = true
                    this.current += 1
                }
                /*var _this=this
                var imgAddr=[]
                this.uploadList.forEach(e=>{
                    imgAddr.push('http://'+e.response.data.picName)
                })
                console.log(imgAddr)
                api.post('/agency/insert',{
                    name:this.daili.name,
                    type:this.daili.type,
                    account:this.daili.account,
                    imgAddr:imgAddr
                }).then(response => {
                    console.log("img="+response.data)
                })*/
            },
            /**
             *  头部数据显示
             */
            showTop(){
                api.get('/member/memberCenter/index')
                    .then(response => {
                        this.favorite = response.data.favorite
                        this.num = response.data.number
                        this.signature = response.data.signature
                        this.displayName = response.data.displayName
                    })
                    .catch(function(error) {
                        console.log(error)
                    })
            },
            /*
            *   图片的上传
            * */
            handleBeforeUpload () {
                const check = this.$refs.upload.fileList.length < 5;
                if (!check) {
                    this.$Notice.warning({
                        title: '最多只能上传 5 张图片。'
                    });
                }
                return check;
            },
            handleSuccess (response, file, fileList) {
                if (response.code === 500) {
                    this.$Message.error('上传失败!')
                } else {
                    this.$Message.success('上传成功!')
                    this.agentInfo2.agent_agreement_picture_list = this.$refs.upload.fileList
                    this.handleSubmit('agentInfo2')
                }
            },
            handleRemove (file) {
                // 先从服务器上把图片删除
                const fileList = this.agentInfo2.agent_agreement_picture_list
                this.agentInfo2.agent_agreement_picture_list.splice(fileList.indexOf(file), 1)
                this.handleSubmit('agentInfo2')
            },
            handleBeforeUpload2 () {
                const check = this.$refs.upload2.fileList.length < 2;
                if (!check) {
                    this.$Notice.warning({
                        title: '最多只能上传 2 张图片。'
                    });
                }
                return check;
            },
            handleSuccess2 (response, file, fileList) {
                if (response.code === 500) {
                    this.$Message.error('上传失败!')
                } else {
                    this.$Message.success('上传成功!')
                    this.agentInfo3.business_license_picture_list = this.$refs.upload2.fileList
                    this.handleSubmit('agentInfo3')
                }
            },
            handleRemove2 (file) {
                // 先从服务器上把图片删除
                const fileList = this.agentInfo3.business_license_picture_list
                this.agentInfo3.business_license_picture_list.splice(fileList.indexOf(file), 1)
                this.handleSubmit('agentInfo3')
            },
            handleBeforeUpload3 () {
                const check = this.$refs.upload3.fileList.length < 2;
                if (!check) {
                    this.$Notice.warning({
                        title: '最多只能上传 2 张图片。'
                    });
                }
                return check;
            },
            handleSuccess3 (response, file, fileList) {
                if (response.code === 500) {
                    this.$Message.error('上传失败!')
                } else {
                    this.$Message.success('上传成功!')
                    this.agentInfo4.agent_identity_card_picture_list = this.$refs.upload3.fileList
                    this.handleSubmit('agentInfo4')
                }
            },
            handleRemove3 (file) {
                // 先从服务器上把图片删除
                const fileList = this.agentInfo4.agent_identity_card_picture_list
                this.agentInfo4.agent_identity_card_picture_list.splice(fileList.indexOf(file), 1)
                this.handleSubmit('agentInfo4')
            },
            handleBeforeUpload4 () {
                const check = this.$refs.upload4.fileList.length < 2;
                if (!check) {
                    this.$Notice.warning({
                        title: '最多只能上传 2 张图片。'
                    });
                }
                return check;
            },
            handleSuccess4 (response, file, fileList) {
                if (response.code === 500) {
                    this.$Message.error('上传失败!')
                } else {
                    this.$Message.success('上传成功!')
                    this.agentInfo5.legal_identity_card_picture_list = this.$refs.upload4.fileList
                    this.handleSubmit('agentInfo5')
                }
            },
            handleRemove4 (file) {
                // 先从服务器上把图片删除
                const fileList = this.agentInfo5.legal_identity_card_picture_list
                this.agentInfo5.legal_identity_card_picture_list.splice(fileList.indexOf(file), 1)
                this.handleSubmit('agentInfo5')
            }
        }
    }
</script>
