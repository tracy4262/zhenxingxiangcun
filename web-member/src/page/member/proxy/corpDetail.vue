<template>
    <div class="layout">
        <top :address="false" />

        <div class="main">
            <div class="container">
                <Row :gutter="20">
                    <Col span="24">
                        <div>
                            <app-banner
                              src="../../../../static/img/app-banner-proxy.png"
                              title="代理管理">
                            </app-banner>
                            <div>
                                <h3 class="mt20 mb20" v-if="isRegister">企业认证详情</h3>
                                <h3 class="mt20 mb20" v-else>企业代理详情</h3>
                                <Form ref="corpInfo" :model="corpInfo" label-position="right" :label-width="280">
                                    <div style="width: 1000px;" v-if="step1">
                                        <Form-item prop="corp_name" class="input" label="企业名称：">
                                            <Input v-model="corpInfo.corp_name" readonly />
                                        </Form-item>
                                        <Form-item prop="companyType" class="input" label="企业类型：">
                                                <Input v-model="corpInfo.company_type" readonly />
                                        </Form-item>
                                        <Form-item prop="corp_domicile" class="input" label="企业住所：">
                                            <Input v-model="corpInfo.company_address" readonly />
                                        </Form-item>
                                        <Form-item prop="registered_capital" class="input" label="注册资本：">
                                            <Input v-model="corpInfo.registered_capital" readonly>
                                                <span slot="append">万元</span>
                                            </Input>
                                        </Form-item>
                                        <Form-item prop="establish_date" class="input" label="成立日期：">
                                            <Input v-model="corpInfo.establish_date" readonly />
                                        </Form-item>
                                        <Form-item prop="business_scope" class="input" label="经营范围：">
                                            <Input v-model="corpInfo.business_scope" readonly />
                                        </Form-item>
                                        <Form-item prop="busniss_term" class="input" label="营业期限：">
                                            <Input v-model="corpInfo.busniss_term.replace(',', ' - ')" readonly />
                                        </Form-item>
                                        <Form-item prop="credit_code" class="input" label="统一社会信用代码：">
                                            <Input v-model="corpInfo.credit_code" readonly />
                                        </Form-item>
                                        <Row>
                                            <Col span="14"  style="z-index: 1;">
                                                <Form-item prop="location" class="input" label="行政区划：">
                                                    <Input v-model="corpInfo.location" readonly />
                                                </Form-item>
                                            </Col>
                                            <Col span="10">
                                                <Form-item label="" style="margin-left: -270px;">
                                                    <Input v-model="corpInfo.addrDetail" readonly />
                                                </Form-item>
                                            </Col>
                                        </Row>
                                        <Form-item prop="preview">
                                            <Input v-model="corpInfo.preview" readonly/>
                                        </Form-item>
                                        <Form-item prop="coordinate" class="input" label="地理位置坐标：" v-if="isRegister">
                                            <Input v-model="corpInfo.coordinate" readonly />
                                        </Form-item>
                                        <Form-item prop="telephone" class="input" label="联系电话：" v-if="isRegister">
                                            <Input v-model="corpInfo.phone" readonly />
                                        </Form-item>
                                        <Form-item class="input" label="企业简介：" v-if="isRegister">
                                            <Input type="textarea" v-model="corpInfo.company_profile" readonly />
                                        </Form-item>
                                        <Form-item>
                                            <Checkbox v-model="single" :checked.sync="single" disabled >同意<a>《农事无忧企业服务协议》</a></Checkbox>
                                        </Form-item>
                                        <Form-item prop="logo_picture_list" class="logo-pic" label="上传企业LOGO：" v-if="isRegister">
                                            <img style="width:140px; height: 140px;" :src="corpInfo.logo_url">
                                        </Form-item>
                                    </div>
                                </Form>
                                <Form ref="corpInfo4" :model="corpInfo4" label-position="right" :label-width="280">
                                    <div style="width: 1000px;" v-if="step1">
                                        <Form-item prop="business_license_picture_list" class="business_license" label="上传企业工商营业执照（正本及副本）：">
                                            <img style="width:140px; height: 140px;" :src="corpInfo4.business_license_picture_list.split(',')[0]">
                                            <img style="width:140px; height: 140px;" :src="corpInfo4.business_license_picture_list.split(',')[1]">
                                        </Form-item>
                                        <div style="margin-left: 250px; margin-top: 40px; margin-bottom: 40px; text-align: center;">
                                            <Button type="primary" shape="circle" style="width:110px;height:30px;" :disabled="!single" @click="next">下一步</Button>&nbsp;&nbsp;
                                            <Button type="primary" shape="circle" style="width:110px;height:30px;"  @click="back">退出</Button>
                                        </div>
                                    </div>
                                </Form>
                                <Form ref="corpInfo2" :model="corpInfo2" label-position="right" :label-width="280">
                                    <div style="width: 1000px;" v-if="step2">
                                        <Form-item prop="legal_person" class="input" label="法定代表人：">
                                            <Input v-model="corpInfo2.legal_person" readonly />
                                        </Form-item>
                                        <Form-item prop="identification_card" class="input" label="法人身份证号码：">
                                            <Input v-model="corpInfo2.identification_card" readonly />
                                        </Form-item>
                                        <Form-item prop="mobile" class="input" label="法人手机号码：" v-if="isRegister">
                                            <Input v-model="corpInfo2.mobile" readonly />
                                        </Form-item>
                                        <Form-item prop="email" class="input" label="法人邮箱：" v-if="isRegister">
                                            <Input v-model="corpInfo2.email" readonly />
                                        </Form-item>
                                        <Form-item class="input" label="法人介绍：" v-if="isRegister">
                                            <Input v-model="corpInfo2.legal_person_introduce" type="textarea" readonly
                                                    :maxlength="500" :autosize="{minRows: 2,maxRows: 5}" />
                                        </Form-item>
                                    </div>
                                </Form>
                                <Form ref="corpInfo3" :model="corpInfo3" label-position="right" :label-width="280">
                                    <div style="width: 1000px;" v-if="step2">
                                        <Form-item prop="identification_card_picture_list" class="identification_card" label="上传身份证（正面及反面）：">
                                            <img style="width:140px; height: 140px;" :src="corpInfo3.identification_card_picture_list.split(',')[0]">
                                            <img style="width:140px; height: 140px;" :src="corpInfo3.identification_card_picture_list.split(',')[1]">
                                        </Form-item>
                                        <div style="margin-left: 250px; margin-top: 40px; margin-bottom: 40px; text-align: center;">
                                            <Button type="primary" shape="circle" style="width:110px;height:30px;" @click="breaks">上一步</Button>&nbsp;&nbsp;
                                            <Button type="primary" shape="circle" @click="back" style="width:110px;height:30px;">退出</Button>
                                        </div>
                                    </div>
                                </Form>
                            </div>
                        </div>
                    </Col>
                </Row>
            </div>
        </div>

        <foot></foot>
    </div>
</template>

<script>
    import top from '../../../top'
    import foot from '../../../foot'
    import divider from '~components/divider'
    import highApp from '~components/memberHighApp'
    import BaseApp from '~components/memberBaseApp'
    import UserApp from '~components/memberUserApp'
    import memberHeader from '../components/memberHeader'
    import appBanner from '~components/app-banner'
    export default {
        components:{
            top,
            foot,
            highApp,
            BaseApp,
            UserApp,
            divider,
            memberHeader,
            appBanner
        },
        data() {
            return {
                isRegister: true,
                step1: true,
                step2: false,
                current: 0,
                single: true,
                corpInfo: {
                    busniss_term: ''
                },
                corpInfo4: {
                    business_license_picture_list: ''
                },
                corpInfo2: {
                },
                corpInfo3: {
                    identification_card_picture_list: ''
                }
            }
        },
        created () {
            // 判断是企业认证的详情页还是企业代理的详情页
            if (this.$route.query.tag !== undefined && this.$route.query.tag === 'register') {
                this.isRegister = true
                this.init({
                    url: '/member/proxy/queryInfoDetail',
                    data: {id: this.$route.query.id, flag: 0}
                })
            } else if (this.$route.query.tag !== undefined && this.$route.query.tag === 'proxy') {
                this.isRegister = false
                this.init({
                    url: '/member/proxy/queryStatusDetail',
                    data: {id: this.$route.query.id, flag: 0}
                })
            }
        },
        methods:{
            // 数据回显
            init (params) {
                this.$api.post(params.url, params.data).then(response => {
                    console.log('response123', response)
                    if (response.code === 200) {
                        this.corpInfo = response.data
                        this.corpInfo.preview = response.data.location + response.data.addrDetail
                        this.corpInfo4.business_license_picture_list = response.data.business_license_url
                        this.corpInfo2 = response.data
                        this.corpInfo3.identification_card_picture_list = response.data.identification_card_url
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            },
            next () {
                this.step1 = false
                this.step2 = true
                this.current += 1
            },
            back () {
                this.$router.push({
                    path: '/member/proxy',
                    query: {
                        tag: '2',
                        type: this.isRegister ? '企业' : '机关'
                    }
                })
            },
            breaks () {
                this.step1 = true
                this.step2 = false
                this.current -= 1
            }
        }
    }
</script>
<style scoped>
    .content {
        width: 1000px;
        margin-left: 40px;
    }
    .step-container {
        margin-top: 30px;
        height: 95px;
    }
    .step {
        width: 800px;
        margin-left: 300px;
    }
</style>
