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
                                <h3 style="margin-top: 20px;">企业认证引导</h3>
                                <div class="step-container">
                                    <Steps :current="current">
                                        <Step title="企业基本信息"></Step>
                                        <Step title="法人基本信息"></Step>
                                        <Step title="提交认证"></Step>
                                    </Steps>
                                </div>
                                <vui-map ref="vuiMap" @on-get-point="onGetPoint"></vui-map>
                                <Form ref="corpInfo" :model="corpInfo" label-position="right" :label-width="280" :rules="ruleInline">
                                    <div style="width: 1000px;" v-if="step1">
                                        <Form-item prop="corp_name" class="input" label="企业名称：">
                                            <Input v-model="corpInfo.corp_name" :maxlength="100" />
                                        </Form-item>
                                        <Form-item prop="companyType" class="input" label="企业类型：">
                                                <Input v-model="corpInfo.companyType" :maxlength="50" />
                                        </Form-item>
                                        <Form-item prop="corp_domicile" class="input" label="企业住所：">
                                            <Input v-model="corpInfo.corp_domicile" :maxlength="50" />
                                        </Form-item>
                                        <Form-item prop="registered_capital" class="input" label="注册资本：">
                                            <Input v-model="corpInfo.registered_capital" :maxlength="10" >
                                                <span slot="append">万元</span>
                                            </Input>
                                        </Form-item>
                                        <Form-item prop="establish_date" class="input" label="成立日期：">
                                            <Date-picker format="yyyy年MM月dd日" type="date"
                                                            style="width: 100%;" v-model="corpInfo.esDate"
                                                            :options="options3"
                                                            @on-change="corpInfo.establish_date=$event"></Date-picker>
                                        </Form-item>
                                        <Form-item prop="business_scope" class="input" label="经营范围：">
                                            <Input v-model="corpInfo.business_scope" :maxlength="500" />
                                        </Form-item>
                                        <Form-item prop="busniss_term" class="input" label="营业期限：">
                                            <Date-picker format="yyyy年MM月dd日" type="daterange" placement="bottom-end"
                                                            style="width: 100%;" v-model="corpInfo.busDate"
                                                            @on-change="corpInfo.busniss_term=$event"></Date-picker>
                                        </Form-item>
                                        <Form-item prop="credit_code" class="input" label="统一社会信用代码：">
                                            <Input v-model="corpInfo.credit_code" placeholder="请填写18位统一社会信用代码" :maxlength="18" />
                                        </Form-item>
                                        <Row>
                                            <Col span="14"  style="z-index: 1;">
                                                <Form-item prop="location" class="input" label="行政区划：">
                                                    <Cascader v-model="corpInfo.locationIdList" :render-format="format" :data="locationList" :load-data="loadPositionDatas" change-on-select>
                                                    </Cascader>
                                                </Form-item>
                                            </Col>
                                            <Col span="10">
                                                <Form-item prop="locationDetail" label="" style="margin-left: -270px;">
                                                    <Input v-model="corpInfo.locationDetail" placeholder="请填写详细地址" :maxlength="50" />
                                                </Form-item>
                                            </Col>
                                        </Row>
                                        <Form-item prop="preview">
                                            <Input v-model="corpInfo.preview" readonly/>
                                        </Form-item>
                                        <Form-item prop="coordinate" class="input" label="地理位置坐标：">
                                            <Input v-model="corpInfo.coordinate" readonly @on-focus="onSelectPoint" />
                                        </Form-item>
                                        <Form-item prop="telephone" class="input" label="联系电话：">
                                            <Input v-model="corpInfo.telephone" placeholder="请输入座机号码，例：027-88888888"/>
                                        </Form-item>
                                        <Form-item class="input" label="企业简介：">
                                            <Input v-model="corpInfo.company_profile" type="textarea"
                                                    :maxlength="500" :autosize="{minRows: 2,maxRows: 5}" />
                                        </Form-item>
                                        <Form-item>
                                            <Checkbox v-model="single" :checked.sync="single" >同意<a>《农事无忧企业服务协议》</a></Checkbox>
                                        </Form-item>
                                        <Form-item prop="logo_picture_list" class="logo-pic" label="上传企业LOGO：">
                                            <vui-upload
                                                ref="logo"
                                                @on-getPictureList="getList1"
                                                :hint="'图片大小小于2MB'"
                                                :total="1"
                                                :size="[80,80]">
                                            </vui-upload>
                                        </Form-item>
                                    </div>
                                </Form>
                                <Form ref="corpInfo4" :model="corpInfo4" label-position="right" :label-width="280" :rules="ruleInline4">
                                    <div style="width: 1000px;" v-if="step1">
                                        <Form-item prop="business_license_picture_list" class="business_license" label="上传企业工商营业执照（正本及副本）：">
                                            <vui-upload
                                                ref="bussiness"
                                                @on-getPictureList="getList2"
                                                :hint="'图片大小小于2MB'"
                                                :total="2"
                                                :size="[80,80]">
                                            </vui-upload>
                                        </Form-item>
                                        <div style="margin-left: 250px; margin-top: 40px; margin-bottom: 40px; text-align: center;">
                                            <Button type="primary" shape="circle" style="width:110px;height:30px;" :disabled="!single" @click="next">下一步</Button>&nbsp;&nbsp;
                                            <Button type="primary" shape="circle" style="width:110px;height:30px;"  @click="back">退出</Button>
                                        </div>
                                    </div>
                                </Form>
                                <Form ref="corpInfo2" :model="corpInfo2" label-position="right" :label-width="280" :rules="ruleInline2">
                                    <div style="width: 1000px;" v-if="step2">
                                        <Form-item prop="legal_person" class="input" label="法定代表人：">
                                            <Input v-model="corpInfo2.legal_person" :maxlength="10" />
                                        </Form-item>
                                        <Form-item prop="identification_card" class="input" label="法人身份证号码：">
                                            <Input v-model="corpInfo2.identification_card" :maxlength="18" />
                                        </Form-item>
                                        <Form-item prop="mobile" class="input" label="法人手机号码：">
                                            <Input v-model="corpInfo2.mobile" :maxlength="11" />
                                        </Form-item>
                                        <Form-item prop="email" class="input" label="法人邮箱：">
                                            <Input v-model="corpInfo2.email" />
                                        </Form-item>
                                        <Form-item class="input" label="法人介绍：">
                                            <Input v-model="corpInfo2.legal_person_introduce" type="textarea"
                                                    :maxlength="500" :autosize="{minRows: 2,maxRows: 5}" />
                                        </Form-item>
                                    </div>
                                </Form>
                                <Form ref="corpInfo3" :model="corpInfo3" label-position="right" :label-width="280" :rules="ruleInline3">
                                    <div style="width: 1000px;" v-if="step2">
                                        <Form-item prop="identification_card_picture_list" class="identification_card" label="上传身份证（正面及反面）：">
                                            <vui-upload
                                                ref="identification"
                                                @on-getPictureList="getList3"
                                                :hint="'图片大小小于2MB'"
                                                :total="2"
                                                :size="[80,80]">
                                            </vui-upload>
                                        </Form-item>
                                        <div style="margin-left: 250px; margin-top: 40px; margin-bottom: 40px; text-align: center;">
                                            <Button type="primary" shape="circle" style="width:110px;height:30px;" @click="breaks">上一步</Button>&nbsp;&nbsp;
                                            <Button type="primary" shape="circle" @click="submit" style="width:110px;height:30px;">提交</Button>&nbsp;&nbsp;
                                            <Button type="primary" shape="circle" @click="back" style="width:110px;height:30px;">退出</Button>
                                        </div>
                                    </div>
                                </Form>
                                <div style="display: flex; justify-content: center; align-items: center; margin-bottom: 20px;" v-if="step3">
                                    <div align="center" style="margin-left: 15%;margin-right: 15%">
                                        <img src="../../../img/chenggong.png" alt="">
                                        <h4 style="color: #666666; margin-top: 20px;">
                                            <span>您已提交企业认证信息，审核工作将在</span><br>
                                            <span style="line-height: 3"><strong style="color: red">三个工作日</strong>内完成，请耐心等待</span>
                                        </h4>
                                        <Button type="primary" shape="circle" style="width:110px;height:30px;margin-top:50px;"  @click="again">继续提交</Button>
                                        <Button type="primary" shape="circle" style="width:110px;height:30px;margin-left: 50px;margin-top:50px;"  @click="complete">完成</Button>
                                    </div>
                                </div>
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
    import vuiUpload from '~components/vui-upload'
    import highApp from '~components/memberHighApp'
    import BaseApp from '~components/memberBaseApp'
    import UserApp from '~components/memberUserApp'
    import vuiMap from '../components/productionMap'
    import vuiFilter from '~components/vuiFilter/filter'
    import memberHeader from '../components/memberHeader'
    import vuequilleditor from '~components/vuequilEditor'
    import photoSelector from '~components/photoSelector'
    import appBanner from '~components/app-banner'
    import { isCreditCode,isTelephone,isIntegerNumber,isIdCard,isPhone2,isEmail2 } from '~utils/validate'

    export default {
        components:{
            top,
            foot,
            highApp,
            BaseApp,
            UserApp,
            divider,
            memberHeader,
            vuiFilter,
            vuiMap,
            vuequilleditor,
            photoSelector,
            vuiUpload,
            appBanner
        },
        data() {
            return {
                step1: true,
                step2: false,
                step3: false,
                current: 0,
                action: `${this.$url.upload}/upload/up`,
                single: true,
                corpInfo: {
                    corp_name: '',
                    corp_domicile: '',
                    credit_code: '',
                    companyType: '',
                    company_type: '',
                    location: '',
                    locationDetail: '',
                    preview: '',
                    locationIdList: [],
                    company_profile: '',
                    telephone: '',
                    coordinate: '',
                    logo_picture_list: [],
                    busniss_term: [],
                    esDate: '',
                    busDate: []
                },
                corpInfo4: {
                    business_license_picture_list: []
                },
                corpInfo2: {
                    legal_person: '',
                    identification_card: '',
                    mobile: '',
                    email: '',
                    legal_person_introduce: '',
                },
                corpInfo3: {
                    identification_card_picture_list: []
                },
                ruleInline: {
                    corp_name: [
                        { required: true, message: '请填写企业名称', trigger: 'blur' }
                    ],
                    corp_domicile: [
                        { required: true, message: '请填写企业住所', trigger: 'blur' }
                    ],
                    telephone: [
                        { validator: isTelephone, trigger: 'blur' }
                    ],
                    credit_code: [
                        { validator: isCreditCode, required: true, trigger: 'blur' }
                    ],
                    coordinate: [
                        { required: true, message: '请选择地理位置坐标', trigger: 'change' }
                    ],
                    registered_capital: [
                        { validator: isIntegerNumber, required: true, trigger: 'blur' }
                    ],
                    establish_date: [
                        { message: '请选择成立日期', required: true, trigger: 'change' }
                    ],
                    business_scope: [
                        { required: true, message: '请填写经营范围', trigger: 'change' }
                    ],
                    busniss_term: [
                        { required: true, type: 'array', min: 1, message: '请选择营业期限', trigger: 'change' }
                    ],
                    location: [
                        { message: '请选择行政区划', required: true, trigger: 'change' }
                    ],
                    locationDetail: [
                        { required: true, message: '请填写详细地址', trigger: 'blur' }
                    ],
                    companyType: [
                        { message: '请填写企业类型', required: true, trigger: 'change' }
                    ],
                    logo_picture_list: [
                        { required: true, message: '请上传企业LOGO', type: 'array', min: 1, trigger: 'change' }
                    ]
                },
                ruleInline3: {
                    identification_card_picture_list: [
                        { required: true, message: '请上传身份证（正面及反面）', type: 'array', min: 2, trigger: 'change'}
                    ]
                },
                ruleInline2: {
                    legal_person: [
                        { required: true, message: '请填写法定代表人', trigger: 'blur' }
                    ],
                    identification_card: [
                        { validator: isIdCard, required: true, trigger: 'blur' }
                    ],
                    mobile: [
                        { validator: isPhone2, trigger: 'blur' }
                    ],
                    email: [
                        { validator: isEmail2, trigger: 'blur' }
                    ]
                },
                ruleInline4: {
                    business_license_picture_list: [
                        {required: true, message: '请上传企业工商营业执照（正本及副本）', type: 'array', min: 2, trigger: 'change'}
                    ]
                },
                locationList: [],
                options3: {
                    disabledDate (date) {
                        return date && date.valueOf() > Date.now()
                    }
                }
            }
        },
        created () {
            /*
             * TODO 表单具体的验证（有粗略的验证）
             * TODO 请求后台接口获取下拉框的数据
             * */
            // 取地址
            this.$api.post('/member/town/next/4cc0ce9b1b8d1e8ab8c005056bc3816').then(res => {
                this.locationList = res.data
            })
        },
        computed: {
            pre () {
                return this.corpInfo.location
            },
            pre2 () {
                return this.corpInfo.locationDetail
            }
        },
        watch: {
            pre: {
                handler(curVal, oldVal) {
                    this.corpInfo.preview = curVal + this.corpInfo.locationDetail
                },
                deep: true
            },
            pre2: {
                handler(curVal, oldVal) {
                    this.corpInfo.preview = this.corpInfo.location + curVal
                },
                deep: true
            }
        },
        methods: {
            getList1 (list) {
                this.corpInfo.logo_picture_list = list
                this.handleSubmit('corpInfo')
            },
            getList2 (list) {
                this.corpInfo4.business_license_picture_list = list
                this.handleSubmit('corpInfo4')
            },
            getList3 (list) {
                this.corpInfo3.identification_card_picture_list = list
                this.handleSubmit('corpInfo3')
            },
            loadPositionDatas (item, callback) {
                item.loading = true
                this.$api.post(`/member/town/next/${item.value}`).then(res => {
                    item.loading = false
                    item.children = res.data
                    callback()
                })
            },
            handleSubmit (name) {
                let flag = false
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        flag = true
                    } else {
                    }
                })
                return flag
            },
            corpRegister () {
                this.$router.push('/member/corpRegister')
            },
            next () {
                let flag = this.handleSubmit('corpInfo')
                let flag4 = this.handleSubmit('corpInfo4')
                if (flag && flag4) {
                    this.step1 = false
                    this.step2 = true
                    this.step3 = false
                    this.current += 1
                    this.$nextTick(() => {
                        let identification_card_picture_list = []
                        for (let i = 0; i < this.corpInfo3.identification_card_picture_list.length; i++) {
                            identification_card_picture_list.push(this.corpInfo3.identification_card_picture_list[i].response.data.picName)
                        }
                        this.$refs.identification.handleGive(identification_card_picture_list)
                    })
                }
            },
            back () {
                this.$router.push({
                    path: '/member/proxy'
                })
            },
            again () {
                window.location.reload()
            },
            complete () {
                // 跳转到代理管理主页面中的 进度 tab页
                this.$router.push({
                    path: '/member/proxy',
                    query: {
                        tag: '2',
                        type: '企业'
                    }
                })
            },
            breaks () {
                this.step1 = true
                this.step2 = false
                this.step3 = false
                this.current -= 1
                this.$nextTick(() => {
                    // 上一步回显图片
                    let logo_picture_list = []
                    for (let i = 0; i < this.corpInfo.logo_picture_list.length; i++) {
                        logo_picture_list.push(this.corpInfo.logo_picture_list[i].response.data.picName)
                    }
                    this.$refs.logo.handleGive(logo_picture_list)
                    let business_license_picture_list = []
                    for (let i = 0; i < this.corpInfo4.business_license_picture_list.length; i++) {
                        business_license_picture_list.push(this.corpInfo4.business_license_picture_list[i].response.data.picName)
                    }
                    this.$refs.bussiness.handleGive(business_license_picture_list)
                })
            },
            submit () {
                let flag2 = this.handleSubmit('corpInfo2')
                let flag3 = this.handleSubmit('corpInfo3')
                if (flag2 && flag3) {
                    let identification_card_picture_list = []
                    for (let i = 0; i < this.corpInfo3.identification_card_picture_list.length; i++) {
                        identification_card_picture_list.push(this.corpInfo3.identification_card_picture_list[i].response.data.picName)
                    }
                    let logo_picture_list = []
                    for (let i = 0; i < this.corpInfo.logo_picture_list.length; i++) {
                        logo_picture_list.push(this.corpInfo.logo_picture_list[i].response.data.picName)
                    }
                    let business_license_picture_list = []
                    for (let i = 0; i < this.corpInfo4.business_license_picture_list.length; i++) {
                        business_license_picture_list.push(this.corpInfo4.business_license_picture_list[i].response.data.picName)
                    }
                    let data = {
                        corp_name: this.corpInfo.corp_name,
                        company_type: this.corpInfo.companyType,
                        company_address: this.corpInfo.corp_domicile,
                        registered_capital: this.corpInfo.registered_capital,
                        establish_date: this.corpInfo.establish_date,
                        business_scope: this.corpInfo.business_scope,
                        busniss_term: this.corpInfo.busniss_term,
                        credit_code: this.corpInfo.credit_code,
                        location: this.corpInfo.location,
                        addrDetail: this.corpInfo.locationDetail,
                        coordinate: this.corpInfo.coordinate,
                        phone: this.corpInfo.telephone,
                        company_profile: this.corpInfo.company_profile,
                        logo_url: logo_picture_list,
                        business_license_url: business_license_picture_list,

                        legal_person: this.corpInfo2.legal_person,
                        identification_card: this.corpInfo2.identification_card,
                        mobile: this.corpInfo2.mobile,
                        email: this.corpInfo2.email,
                        legal_person_introduce: this.corpInfo2.legal_person_introduce,
                        identification_card_url: identification_card_picture_list,

                        agencyer: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
                    }
                    console.log('data', data)
                    // 请求接口增加数据
                    this.$api.post('/member/corpInfo/saveCorp', data).then(response => {
                        console.log('corp response', response)
                        if (response.code === 200) {
                            this.$Message.success('注册成功!')
                            this.step1 = false
                            this.step2 = false
                            this.step3 = true
                            this.current += 1
                        }
                    }).catch(error => {
                        this.$Message.error('注册失败!')
                    })
                }
            },
            format (labels, selectedData) {
                let locationStr = ''
                labels.forEach(item => {
                    locationStr += item + '/'
                })
                this.corpInfo.location = locationStr
                return locationStr
            },
            onSelectPoint () {
                this.$refs.vuiMap.showMap = true
            },
            // 取坐标
            onGetPoint (point) {
                if (point.lng !== '' && point.lng !== undefined && point.lat !== '' && point.lat !== undefined) {
                    this.corpInfo.coordinate = `${point.lng},${point.lat}`
                } else {
                    this.corpInfo.coordinate = ''
                }
            },
            getCon (msg) {
				this.corpInfo.company_profile = msg
			}
        }
    }
</script>
<style lang="scss" scoped>
    .content {
        width: 1000px;
        margin-left: 40px;
    }
    .step-container {
        margin-left: 150px;
        margin-top: 30px;
        height: 95px;
    }
    .step {
        margin-left: 150px;
    }

    .new-upload-list {
        display: inline-block;
        width: 142px;
        height: 142px;
        text-align: center;
        line-height: 60px;
        border: 1px solid transparent;
        border-radius: 4px;
        overflow: hidden;
        background: #fff;
        position: relative;
        box-shadow: 0 1px 1px rgba(0,0,0,.2);
        margin-right: 4px;
    }
    .new-upload-list:hover .demo-upload-list-cover{
        display: block;
    }
    .upload-add-div {
        border: 1px;
        height: 140px;
        width: 140px;
        background: #F6F6F6;
    }
    .photo-selector {
        display: inline-block;
        position: relative;
        top: -11px;
        text-align: center;
        border: 1px #dddee1 dashed;
        cursor: pointer;
    }
    .photo-selector:hover {
        border: 1px #00c587 dashed;
    }
</style>
