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
                                <h3 class="mt20 mb20" v-if="isRegister">机关认证详情</h3>
                                <h3 class="mt20 mb20" v-else>机关代理详情</h3>
                                <Form ref="govInfo" :model="govInfo" label-position="right" :label-width="280">
                                    <div style="width: 1000px;" v-if="step1">
                                        <Form-item class="input" label="机关名称：">
                                            <Input v-model="govInfo.gov_name" readonly />
                                        </Form-item>
                                        <Form-item class="input" label="机关住所：">
                                            <Input v-model="govInfo.address" readonly />
                                        </Form-item>
                                        <Form-item class="input" label="统一社会信用代码：">
                                            <Input v-model="govInfo.organization_code" readonly />
                                        </Form-item>
                                        <Form-item class="input" label="机关类型：">
                                            <Input v-model="govInfo.gov_type" readonly />
                                        </Form-item>
                                        <Form-item class="input" label="机关级别：">
                                            <Input v-model="govInfo.gov_level" readonly />
                                        </Form-item>
                                        <Row>
                                            <Col span="14"  style="z-index: 1;">
                                                <Form-item class="input" label="行政区划：">
                                                    <Input v-model="govInfo.location" readonly />
                                                </Form-item>
                                            </Col>
                                            <Col span="10">
                                                <Form-item label="" style="margin-left: -270px;">
                                                    <Input v-model="govInfo.addrDetail" readonly />
                                                </Form-item>
                                            </Col>
                                        </Row>
                                        <Form-item prop="preview">
                                            <Input v-model="govInfo.preview" readonly />
                                        </Form-item>
                                        <Form-item class="input" label="地理位置坐标：" v-if="isRegister">
                                            <Input v-model="govInfo.coordinate" readonly />
                                        </Form-item>
                                        <Form-item class="input" label="联系电话：" v-if="isRegister">
                                            <Input v-model="govInfo.phone" readonly/>
                                        </Form-item>
                                        <Form-item class="input" label="机关简介：" v-if="isRegister">
                                            <Input v-model="govInfo.gov_profile" type="textarea" readonly
                                                    :maxlength="500" :autosize="{minRows: 2,maxRows: 5}" />
                                        </Form-item>
                                        <Form-item>
                                            <Checkbox v-model="single" :checked.sync="single" disabled >同意<a>《农事无忧机关服务协议》</a></Checkbox>
                                        </Form-item>
                                        <Form-item class="logo-pic" label="上传机关LOGO：" v-if="isRegister">
                                            <img style="width:140px; height: 140px;" :src="govInfo.logo_picture_list">
                                        </Form-item>
                                    </div>
                                </Form>
                                <Form ref="govInfo2" :model="govInfo2" label-position="right" :label-width="280">
                                    <div style="width: 1000px;" v-if="step1">
                                        <Form-item class="unit-person" label="上传事业单位法人证明：">
                                            <img style="width:140px; height: 140px;" :src="govInfo2.unit_person_picture_list">
                                        </Form-item>
                                    </div>
                                </Form>
                                <Form ref="govInfo5" :model="govInfo5" label-position="right" :label-width="280">
                                    <div v-if="step1">
                                        <Form-item class="qualification_certificate" label="上传社会信用代码证：">
                                            <img style="width:140px; height: 140px;" :src="govInfo5.qualification_certificate_picture_list">
                                        </Form-item>
                                        <div style="margin-left: 250px; margin-top: 40px; margin-bottom: 40px; text-align: center;">
                                            <Button type="primary" shape="circle" style="width:110px;height:30px;"  @click="back()">退出</Button>
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
        data () {
            return {
                isRegister: true,
                step1: true,
                step2: false,
                current: 0,
                single: true,
                govInfo:{},
                govInfo2: {},
                govInfo5: {},
            }
        },
        created () {
            // 判断是机关认证的详情页还是机关代理的详情页
            if (this.$route.query.tag !== undefined && this.$route.query.tag === 'register') {
                this.isRegister = true
                this.init({
                    url: '/member/proxy/queryInfoDetail',
                    data: {id: this.$route.query.id, flag: 1}
                })
            } else if (this.$route.query.tag !== undefined && this.$route.query.tag === 'proxy') {
                this.isRegister = false
                this.init({
                    url: '/member/proxy/queryStatusDetail',
                    data: {id: this.$route.query.id, flag: 1}
                })
            }
        },
        methods:{
            // 数据回显
            init (params) {
                this.$api.post(params.url, params.data).then(response => {
                    console.log('response123', response)
                    if (response.code === 200) {
                        this.govInfo = response.data
                        this.govInfo.preview = response.data.location + response.data.addrDetail
                        this.govInfo2.unit_person_picture_list = response.data.unit_person_picture_list
                        this.govInfo5.qualification_certificate_picture_list = response.data.qualification_certificate_picture_list
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            },
            back () {
                this.$router.push({
                    path: '/member/proxy',
                    query: {
                        tag: '2',
                        type: this.isRegister ? '企业' : '机关'
                    }
                })
            }
        }
    }
</script>
