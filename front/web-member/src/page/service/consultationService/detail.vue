<template>
    <div class="layouts" style="min-height: 600px;">
        <Row class="pt10">
            <Breadcrumb class="pd20">
                <BreadcrumbItem to="/51index/serviceList/all">服务</BreadcrumbItem>
                <BreadcrumbItem to="/51Index/serviceList/consultation">咨询服务</BreadcrumbItem>
                <BreadcrumbItem>{{ data.baseData.serviceName }}</BreadcrumbItem>
            </Breadcrumb>
        </Row>
        <Row>
            <!-- 咨询服务基本信息 -->
            <Col :span="relatedServicesData.length > 0 ? 20 : 24">
                <Row>
                    <!-- 照片 -->
                    <Col span="8">
                        <img :src="data.baseData.personalPicture" style="width: 100%;">
                        <div style="font-size: 16px;" class="mt20 tc"><b>专家：{{ data.baseData.expertName }}</b></div>
                    </Col>
                    <!-- 文字 -->
                    <Col span="16" class="pl20">
                        <Row>
                            <Col span="12"><b style="font-size:24px;">{{ data.baseData.serviceName }}</b></Col>
                            <Col span="12" class="tr">
                                <Rate disabled v-model="star" allow-half></Rate>
                                <p class="t-grey mr10">已有{{ stars }}人评价</p>
                            </Col>
                        </Row>
                        <Row class="mt10">
                            <Col span="3"><b>擅长领域：</b></Col>
                            <Col span="21">{{ data.baseData.adeptField }}</Col>
                        </Row>
                        <Row class="mt10">
                            <Col span="3"><b>相关物种：</b></Col>
                            <Col span="21">{{ data.baseData.relatedSpecies }}</Col>
                        </Row>
                        <Row class="mt10">
                            <Col span="3"><b>相关经历：</b></Col>
                            <Col span="21">{{ data.baseData.relatedExperience }}</Col>
                        </Row>
                        <Row class="mt20" type="flex" align="middle">
                            <Col span="12">
                                <span class="mr10"><b>分享到：</b>&nbsp;&nbsp;&nbsp;&nbsp;</span>
                                <div style="display: inline-block; vertical-align: middle;">
                                    <!-- 分享组件 -->
                                    <vue-share></vue-share>
                                </div>
                            </Col>
                            <Col span="12" class="tr pr20">
                                <Button type="text" @click="handleCollect" v-if="!isCollect">
                                <Icon type="ios-star-outline" size="24" class="t-green"></Icon>
                                收藏
                                </Button>
                                <Button type="text" @click="handleCollect" v-else>
                                <Icon type="ios-star" size="24" class="t-green"></Icon>
                                已收藏
                                </Button>
                            </Col>
                        </Row>
                    </Col>
                </Row>
                <Row class="mt20">
                    <Title title="服务方式"></Title>
                    <Card class="mt20">
                        <Row>
                            <Col span="18">
                                <Row>聘请专家后，在聘请期内可享受{{ data.baseData.expertName }}专家提供的以下方式的咨询服务：</Row>
                                <Row class="mt20" type="flex" align="middle" v-if="data.doorService">
                                    <img src="../../../../static/img/door-service.png" style="width: 20px;" class="mr10">
                                    上门服务&nbsp;&nbsp;&nbsp;&nbsp;服务区域：{{ data.doorServiceData.areaStatus === '设定服务区域' ? data.doorServiceData.area : '不限' }}；
                                    服务时间段：{{ data.doorServiceData.timeStatus === '设定服务时间' ? data.doorServiceData.time : data.doorServiceData.timeStatus === '双方约定' ? '双方约定' : '不限'}}；
                                    {{ data.doorServiceData.mark === '' ? '' : data.doorServiceData.mark + '；'}}
                                </Row>
                                <Row class="mt20" type="flex" align="middle"  v-if="data.locationService">
                                    <img src="../../../../static/img/location-service.png" style="width: 20px;" class="mr10">
                                    定点服务&nbsp;&nbsp;&nbsp;&nbsp;服务网点：<span v-for="(item, index) in data.locationServiceData.networkStationInfo"  :key="index">{{ item.name }}；</span>
                                    服务时间段：{{ data.locationServiceData.timeStatus === '设定服务时间' ? data.locationServiceData.time : data.locationServiceData.timeStatus === '双方约定' ? '双方约定' : '不限'}}；
                                </Row>
                                <Row class="mt20" type="flex" align="middle"  v-if="data.telephoneService">
                                    <img src="../../../../static/img/telephone-service.png" style="width: 20px;" class="mr10">
                                    电话服务&nbsp;&nbsp;&nbsp;&nbsp;服务区域：{{ data.telephoneServiceData.telephoneAreaStatus === '设定服务区域' ? data.telephoneServiceData.telephoneArea : '不限' }}；
                                    服务时间段：{{ data.telephoneServiceData.timeStatus === '设定服务时间' ? data.telephoneServiceData.time : data.telephoneServiceData.timeStatus === '双方约定' ? '双方约定' : '不限'}}；
                                </Row>
                                <Row class="mt20" type="flex" align="middle"  v-if="data.networkService">
                                    <img src="../../../../static/img/network-service.png" style="width: 20px;" class="mr10">
                                    网络服务&nbsp;&nbsp;&nbsp;&nbsp;服务区域：{{ data.networkServiceData.networkAreaStatus === '设定服务区域' ? data.networkServiceData.networkArea : '不限' }}；
                                    服务时间段：{{ data.networkServiceData.timeStatus === '设定服务时间' ? data.networkServiceData.time : data.networkServiceData.timeStatus === '双方约定' ? '双方约定' : '不限'}}；
                                </Row>
                            </Col>
                            <Col span="6">
                                <Row>聘请费用：</Row>
                                <div v-if="data.serviceType === '提供付费咨询'">
                                    <Row class="mt20" v-for="(item, index) in data.chargeType" :key="index">{{ item.employTime }}收费：{{ item.employMoney }}元/{{ item.employTime.substring(1) }}</Row>
                                </div>
                                <div class="mt20"  v-else>
                                    提供免费咨询
                                </div>
                                <Row class="mt20">
                                    <Button type="primary" size="large" long @click="handleBuy" :disabled="disabled">立即聘请</Button>
                                </Row>
                            </Col>
                        </Row>
                    </Card>
                </Row>
            </Col>
            <!-- 相关服务 -->
            <Col span="4" class="pl20">
                <relatedServices ref="relatedServices" style="margin-top: -20px;"></relatedServices>
            </Col>
        </Row>
        <!-- 详细信息 -->
        <Row class="mt30 mb30">
            <Col span="24">
                <div style="border: 1px solid #F4F4F4;">
                    <Affix :offset-top="74">
                        <Menu mode="horizontal" theme="light" :active-name="activeName" @on-select="handleMenuChange">
                            <MenuItem name="generalInfo">通用信息</MenuItem>
                            <MenuItem name="serviceIntroduction">服务介绍</MenuItem>
                            <MenuItem name="serviceInfo">服务信息</MenuItem>
                            <MenuItem name="mapInfo">地图导航</MenuItem>
                            <MenuItem name="gradeInfo">评论信息({{ stars ? stars : '0' }})</MenuItem>
                        </Menu>
                    </Affix>
                    <div v-if="activeName === 'generalInfo'" class="pd20">
                        <Form :label-width="100">
                            <FormItem label="通用服务名：">{{ data.generalInfo.currencyServiceName }}</FormItem>
                            <FormItem label="行业分类：">{{ data.generalInfo.tradeClassId }}</FormItem>
                            <FormItem label="服务分类：">{{ data.generalInfo.serviceClassId }}</FormItem>
                        </Form>
                    </div>
                    <div v-if="activeName === 'serviceIntroduction'" class="pd20">
                        <div v-html="data.serviceIntroduction"></div>
                    </div>
                    <div v-if="activeName === 'serviceInfo'" class="pd20">
                        <Form :label-width="100">
                            <FormItem label="注意事项：">{{ data.serviceInfo.attention }}</FormItem>
                            <FormItem label="承诺内容：">{{ data.serviceInfo.promise }}</FormItem>
                        </Form>
                    </div>
                    <div v-if="activeName === 'mapInfo'" class="pd20">
                        <Form :label-width="100">
                            <FormItem label="公交线路指引：">
                                <span v-for="(item, index) in data.mapInfo.busLead"  :key="index">
                                    {{ item.name === '' ? '暂无公交线路指引' : item.name }};
                                </span>
                            </FormItem>
                        </Form>
                        <Form :label-width="100">
                            <FormItem label="自驾线路指引：">
                                <span v-for="(item, index) in data.mapInfo.carLead"  :key="index">
                                    {{ item.name === '' ? '暂无自驾线路指引' : item.name }};
                                </span>
                            </FormItem>
                        </Form>
                        <Form :label-width="100">
                            <FormItem label="联系人地址：">
                                <div v-for="(item, index) in data.mapInfo.map" class="mb10 mt10"  :key="index">
                                    <a target="_blank" :href="`http://api.map.baidu.com/marker?location=${item.latitude},${item.longitude}&title=我的位置&content=${item.latitude},${item.longitude}&output=html`">
                                        <img :src="`//api.map.baidu.com/staticimage?height=200&center=${item.longitude},${item.latitude}&zoom=15&scale=2&markers=${item.longitude},${item.latitude}`" alt="" width="100%" height="500px">
                                    </a>
                                </div>
                            </FormItem>
                        </Form>
                    </div>
                    <div v-if="activeName === 'gradeInfo'" class="pd20">
                        <comments @on-login="handleLogin" ref="comments" @on-stars="getStars"></comments>
                    </div>
                </div>
            </Col>
        </Row>
        <!-- 订单详情 -->
        <Modal v-model="modal" title="订单详情" width="1000" :closable="false">
            <Row>
                <Col span="4">
                    <img :src="data.baseData.personalPicture" style="width: 100%;">
                    <div style="font-size: 16px;" class="mt20 tc"><b>专家：{{ data.baseData.expertName }}</b></div>
                </Col>
                <Col span="20"  class="pl20">
                    <Row><b>可享受的服务内容：</b></Row>
                    <Row class="mt20" type="flex" align="middle" v-if="data.doorService">
                        <img src="../../../../static/img/door-service.png" style="width: 20px;" class="mr10">
                        上门服务&nbsp;&nbsp;&nbsp;&nbsp;服务区域：{{ data.doorServiceData.areaStatus === '设定服务区域' ? data.doorServiceData.area : '不限' }}；
                        服务时间段：{{ data.doorServiceData.timeStatus === '设定服务时间' ? data.doorServiceData.time : data.doorServiceData.timeStatus === '双方约定' ? '双方约定' : '不限'}}；
                        {{ data.doorServiceData.mark === '' ? '' : data.doorServiceData.mark + '；'}}
                    </Row>
                    <Row class="mt20" type="flex" align="middle"  v-if="data.locationService">
                        <img src="../../../../static/img/location-service.png" style="width: 20px;" class="mr10">
                        定点服务&nbsp;&nbsp;&nbsp;&nbsp;服务网点：<span v-for="(item, index) in data.locationServiceData.networkStationInfo"  :key="index">{{ item.name }}；</span>
                        服务时间段：{{ data.locationServiceData.timeStatus === '设定服务时间' ? data.locationServiceData.time : data.locationServiceData.timeStatus === '双方约定' ? '双方约定' : '不限'}}；
                    </Row>
                    <Row class="mt20" type="flex" align="middle"  v-if="data.telephoneService">
                        <img src="../../../../static/img/telephone-service.png" style="width: 20px;" class="mr10">
                        电话服务&nbsp;&nbsp;&nbsp;&nbsp;服务区域：{{ data.telephoneServiceData.telephoneAreaStatus === '设定服务区域' ? data.telephoneServiceData.telephoneArea : '不限' }}；
                        服务时间段：{{ data.telephoneServiceData.timeStatus === '设定服务时间' ? data.telephoneServiceData.time : data.telephoneServiceData.timeStatus === '双方约定' ? '双方约定' : '不限'}}；
                    </Row>
                    <Row class="mt20" type="flex" align="middle"  v-if="data.networkService">
                        <img src="../../../../static/img/network-service.png" style="width: 20px;" class="mr10">
                        网络服务&nbsp;&nbsp;&nbsp;&nbsp;服务区域：{{ data.networkServiceData.networkAreaStatus === '设定服务区域' ? data.networkServiceData.networkArea : '不限' }}；
                        服务时间段：{{ data.networkServiceData.timeStatus === '设定服务时间' ? data.networkServiceData.time : data.networkServiceData.timeStatus === '双方约定' ? '双方约定' : '不限'}}；
                    </Row>
                    <Row>
                        <Col span="8">
                            <Row class="mt20"><b>聘请费用：</b></Row>
                            <div v-if="data.serviceType === '提供付费咨询'">
                                <Row class="mt20" v-for="(item, index) in data.chargeType"  :key="index">{{ item.employTime }}收费：{{ item.employMoney }}元/{{ item.employTime.substring(1) }}</Row>
                            </div>
                            <div class="mt20"  v-else>
                                提供免费咨询
                            </div>
                        </Col>
                        <Col span="16">
                            <div v-if="data.serviceType === '提供付费咨询'">
                                <Row class="mt20">
                                    <Col span="24"><b>请选择聘请周期：</b></Col>
                                </Row>
                                <Row type="flex" align="middle" class="mt20">
                                    <Col span="12">
                                        <RadioGroup v-model="order.employTime" @on-change="change1">
                                            <Radio v-for="(item, index) in data.chargeType"  :key="index" :label="item.employTime"></Radio>
                                        </RadioGroup>
                                    </Col>
                                    <Col span="6">
                                        <InputNumber v-model="order.count" :min="1" :max="1000" @on-change="change2"></InputNumber>
                                        <span v-if="order.employTime === '按小时'">小时</span>
                                        <span v-if="order.employTime === '按天'">天</span>
                                        <span v-if="order.employTime === '按月'">月</span>
                                        <span v-if="order.employTime === '按年'">年</span>
                                    </Col>
                                </Row>
                            </div>
                            <Row class="mt20">
                                <Col span="24"><b>个人信息：</b></Col>
                            </Row>
                            <Form ref="order" :model="order" :label-width="80" label-position="left" :rules="ruleInline" class="mt20">
                                <Row :gutter="32">
                                    <Col span="12">
                                        <FormItem label="姓名" prop="name">
                                            <Input v-model="order.name" />
                                        </FormItem>
                                    </Col>
                                    <Col span="12">
                                        <FormItem label="联系方式" prop="phone">
                                            <Input v-model="order.phone" />
                                        </FormItem>
                                    </Col>
                                </Row>
                                <Row :gutter="32">
                                    <Col span="12">
                                        <FormItem label="联系地址" prop="location">
                                            <Input v-model="order.location" />
                                        </FormItem>
                                    </Col>
                                </Row>
                            </Form>
                        </Col>
                    </Row>
                </Col>
            </Row>
            <div slot="footer">
                <span class="mr20" style="font-size: 16px;">费用：<span style="color: #FF7921;">{{ order.money }}</span> 元</span>
                <Button type="text" @click="cancel">取消</Button>
                <Button type="primary" @click="ok">立即支付</Button>
            </div>
        </Modal>
        <!-- 收藏组件 -->
        <collectModal ref="collectModal" :type="collectType" :link="link" :collectTitle="data.baseData.serviceName" @on-init="checkCollect"></collectModal>
    </div>
</template>
<script>
import relatedServices from '../../personGate/components/serviceComponents/relatedServices'
import vueShare from '../../personGate/components/serviceComponents/vue-share'
import comments from '../../personGate/components/serviceComponents/comments'
import collectModal from '~components/collectModal'
import Title from '../../newApplication/productionBase/components/title2'
import {isPhone2} from '~utils/validate'
export default {
    components: {
        relatedServices,
        vueShare,
        collectModal,
        Title,
        comments
    },
    data () {
        return {
            relatedServicesData: [],
            star: 0,
            stars: 0,
            isCollect: false,
            collectType: 'consultationService',
            link: this.$route.fullPath,
            data: {
                baseData: {
                    expertName: '',
                    serviceName: '',
                    adeptField: '',
                    relatedSpecies: '',
                    relatedExperience: '',
                    personalPicture: ''
                },
                chargeType: [],
                doorServiceData: {
                    areaStatus: '不限',
                    area: '',
                    timeStatus: '不限',
                    time: '',
                    mark: ''
                },
                locationServiceData: {
                    timeStatus: '不限',
                    time: '',
                    networkStationInfo: [
                        {
                            name: ''
                        },
                        {
                            name: ''
                        }
                    ]
                },
                telephoneServiceData: {
                    telephoneAreaStatus: '不限',
                    telephoneArea: '',
                    timeStatus: '不限',
                    time: '',
                },
                networkServiceData: {
                    networkAreaStatus: '不限',
                    networkArea: '',
                    timeStatus: '不限',
                    time: ''
                },
                generalInfo: {
                    currencyServiceName: '',
                    tradeClassId: '',
                    serviceClassId: ''
                },
                serviceIntroduction: '',
                serviceInfo: {
                    attention: '',
                    promise: ''
                },
                mapInfo: {
                    busLead: [
                        {
                            name: ''
                        },
                        {
                            name: ''
                        }
                    ],
                    carLead: [
                        {
                            name: ''
                        },
                        {
                            name: ''
                        }
                    ],
                    map: [
                        {
                            longitude: '',
                            latitude: ''
                        },
                        {
                            longitude: '',
                            latitude: ''
                        }
                    ]
                }
            },
            order: {
                employTime: '',
                employMoney: '',
                count: 1,
                name: '',
                phone: '',
                location: '',
                money: 0
            },
            ruleInline: {
                name: [
                    { required: true, message: '请填写联系人姓名', trigger: 'blur' }
                ],
                phone: [
                    { required: true, message: '请填写手机号码', trigger: 'blur' },
                    { validator: isPhone2 }
                ]
            },
            activeName: 'generalInfo',
            disabled: true,
            modal: false,
            id: '',
            account: this.$user.loginAccount
        }
    },
    created () {
        this.id = this.$route.query.id
        this.getIsRelationData()
        this.init()
    },
    methods: {
        init () {
            this.$api.post('/member-reversion/consult/detail', {
                id: this.id,
                account: this.account
            }).then(response => {
                if (response.code === 200) {
                    console.log('detail', response)
                    // 如果是自己不能聘请自己 如果已经聘请过还没有到底也不能聘请 只有聘请到期了才能继续聘请
                    this.disabled = response.data.account === this.account ? true : response.data.status === 0 ? true : false
                    this.data = response.data
                    // 默认为第一个的价格
                    if (this.data.serviceType === '提供付费咨询') {
                        this.order.employTime = this.data.chargeType[0].employTime
                        this.order.employMoney = this.data.chargeType[0].employMoney
                        this.order.money = parseInt(this.order.employMoney) * this.order.count
                    }
                }
            })
        },
        getIsRelationData () {
            this.$api.post('/member/fishing/findJoinServiceList', {
                // account: this.$user.loginAccount,
                service_name: '',
                joinService: 1, //  0 未关联。 1已关联
                pageNum: 1,
                pageSize: 100,
                id: this.id,
                type: '' // 0垂钓 1采摘 2景区 3餐饮 4住宿 空为全部
            }).then(response => {
                if (response.code === 200) {
                    this.relatedServicesData = response.data.dataList
                    this.$refs['relatedServices'].init(response.data.dataList)
                }
            })
        },
        // 查询是否收藏过
        checkCollect () {
            this.$api.post('/member/report/isExist', {
                link: this.$route.fullPath,
                account: this.$user.loginAccount
            }).then(resp => {
                if (200 === resp.code) {
                    if (resp.data === 1) { // 已收藏
                        this.isCollect = true
                    } else {
                        this.isCollect = false
                    }
                }
            })
        },
        // 点击收藏
        handleCollect () {
            if (this.$user) {
                console.log(this.isCollect)
                if (this.isCollect) {
                    this.$Message.error('请勿重复收藏')
                } else {
                    this.$refs['collectModal'].show = true
                }
            } else {
                this.$Message.error('请先登录!')
                this.handleLogin()
            }
        },
        handleLogin () {
            this.$Message.error('请登录')
            this.$parent.$refs['top'].loginuser()
        },
        handleBuy () {
            this.modal = true
        },
        // 切换
        handleMenuChange (name) {
            this.activeName = name
        },
        // 获取评论人数
        getStars (e) {
            console.log('获取评论数据')
            this.stars = e.stars
            this.star = e.star
        },
        cancel () {
            this.modal = false
        },
        ok () {
            if (this.data.serviceType === '提供付费咨询') {
                if (this.order.employTime === '') {
                    this.$Message.info('请选择聘请周期！')
                    return
                }
                if (this.order.count === null) {
                    this.$Message.info('请填写聘请时长！')
                    return
                }
            }
            this.$refs['order'].validate((valid) => {
                if (valid) {
                    this.order.account = this.account
                    this.order.id = this.id
                    this.order.type = 5
                    this.order.data = this.data
                    this.$api.post('/member/fishing/saveOrder', this.order).then(response => {
                        if (response.code === 200) {
                            this.$Message.success('下单成功！')
                            this.init()
                            this.modal = false
                        } else {
                            this.$Message.error('服务器异常！')
                        }
                    }).catch(error => {
                        this.$Message.error('服务器异常！')
                    })
                }
            })
        },
        change1 (value) {
            console.log('value', value)
            let arr = this.data.chargeType.filter(element => {
                return element.employTime === value
            })
            console.log('arr[0].employMoney', arr[0].employMoney)
            this.order.employMoney = arr[0].employMoney
            if (this.order.employMoney !== '' && this.order.count !== null) {
                this.order.money = parseInt(this.order.employMoney) * this.order.count
            }
        },
        change2 (value) {
            if (this.order.employMoney !== '' && this.order.count !== null) {
                this.order.money = parseInt(this.order.employMoney) * this.order.count
            }
        }
    }
}
</script>
