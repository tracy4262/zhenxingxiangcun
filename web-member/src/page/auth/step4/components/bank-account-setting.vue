<template>
    <div class="pt30 pl10 pr10">
        <Form ref="bankInfo" :model="bankInfo" label-position="left" :label-width="150" :rules="ruleValidate" class="pb20 pl5">
            <div class="mt20">
                <Row :gutter="32">
                    <Col span="12">
                        <Form-item prop="name" label="真实姓名">
                            <Input v-model="bankInfo.name" :maxlength="20" />
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="id" label="身份证号码">
                            <Input v-model="bankInfo.id" />
                        </Form-item>
                    </Col>
                </Row>
                <Row :gutter="32">
                    <Col span="12">
                        <Form-item prop="bankCard" label="银行卡号（仅限储蓄卡）">
                            <Input v-model="bankInfo.bankCard" :maxlength="19"/>
                        </Form-item>
                    </Col>
                </Row>
                <Row :gutter="32">
                    <Col span="12">
                        <Form-item prop="mobile" label="银行预留手机号">
                            <Input v-model="bankInfo.mobile" />
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="code" label="验证码">
                            <Input v-model="bankInfo.code" :maxlength="6">
                                <Button type="primary"  :disabled="sendDisabled" slot="append" @click.native="code">
                                    <vui-countdown
                                    title="秒重新发送"
                                    v-model="sendTime"
                                    :start="sendStrat"
                                    v-show="sendShow"
                                    @finish="handleSendFinish"
                                    />
                                <template v-show="!sendShow">{{sendLabel}}</template>
                                </Button>
                            </Input>
                        </Form-item>
                    </Col>
                </Row>
                <Row :gutter="32">
                    <Col span="12">
                        <Form-item prop="password" label="支付密码">
                            <Input v-model="bankInfo.password" type="password" />
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="passwordAgain" label="再次输入支付密码">
                            <Input v-model="bankInfo.passwordAgain" type="password" />
                        </Form-item>
                    </Col>
                </Row>
            </div>
        </Form>
    </div>
</template>
<script>
    import { isIdCard,isBankCard2,isPhone } from '../../../../utils/validate'
    import vuiCountdown from '~components/countdown'
    export default {
        components: {
            vuiCountdown
        },
        data () {
            return {
                bankInfo: {
                    name: '',
                    id: '',
                    bankCard: '',
                    mobile: '',
                    code: '',
                    password: '',
                    passwordAgain: ''
                },
                ruleValidate: {
                    /* name: [
                        { required: true, message: '请填写真实姓名', trigger: 'change' }
                    ],   
                    id: [
                        { required: true, validator: isIdCard, trigger: 'change' }
                    ],
                    bankCard: [
                        { required: true, validator: isBankCard2, trigger: 'change' }
                    ],
                    mobile: [
                        { required: true, validator: isPhone, trigger: 'change' }
                    ],
                    code: [
                        { required: true, message: '请填写验证码', trigger: 'change' }
                    ],
                    password: [
                        { required: true, message: '请填写支付密码', trigger: 'change' }
                    ],
                    passwordAgain: [
                        { required: true, message: '请填写再次输入支付密码', trigger: 'change' }
                    ] */
                },
                sendDisabled: false,
                sendTime: 60,
                sendStrat: false,
                sendShow: false,
                sendLabel: '发送验证码'
            }
        },
        methods: {
            // 验证码发送 发送验证码之前对手机号进行验证
            code () {
                var modle = /^1[3|4|5|7|8][0-9]{9}$/
                if (this.bankInfo.mobile === '') {
                    this.$Message.error('请填写银行预留手机号!')
                } else {
                    if (!modle.test(this.bankInfo.mobile)) {
                        this.$Message.error('银行预留手机号填写错误!')
                    } else {
                        this.sendStrat = true
                        this.sendShow = true
                        this.sendLabel = ''
                        this.sendDisabled = true
                        this.$api.post('/member/Certification/code', {
                            phone: this.bankInfo.mobile
                        }).then(response => {
                            console.log(response.data)
                            if (response.data === 0) {
                                this.$Message.error('银行预留手机号填写错误!')
                            } else {
                                // 设定一个60秒的计时器
                                this.last_time = 60
                                this.isDisable = true
                                clearInterval(this.clock)
                                this.clock = setInterval(this.refresh, 1000)
                                this.$Message.success('验证码发送成功!')
                            }
                        })
                    }
                }
            },
            handleSendFinish () {
                this.sendStrat = false
                this.sendShow = false
                this.sendLabel = '重新发送'
                this.sendDisabled = false
                this.sendTime = 60
            },
            handleSubmit () {
                let flag = false
                // 先判断两次密码填写是否一致
                if (this.bankInfo.password === this.bankInfo.passwordAgain) {
                    this.$refs['bankInfo'].validate((valid) => {
                        if (valid) {
                            flag = true
                        } else {
                            flag = false
                        }
                    })
                } else {
                    this.$Message.error('支付密码不一致！')
                    flag = false
                }
                return flag
            }
        }
    }
</script>
