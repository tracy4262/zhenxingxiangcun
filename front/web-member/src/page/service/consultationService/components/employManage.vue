<!--
    作者：chenqim
    时间：2018-12-20
    描述：聘请管理
-->
<template>
    <div class="pd20" style="min-height: 500px;">
        <div class="mt20">
            <Form ref="queryInfo" :model="queryInfo" label-position="right" :label-width="90" :rules="ruleInline">
                <Row :gutter="32">
                    <Col span="8">
                        <Form-item prop="trade" class="input" label="相关行业：">
                            <Input v-model="queryInfo.trade" class="pinput-clear" icon="ios-close-circle" readonly @on-focus="handleFilterModal('tradeFilter')" @on-click="inputClear('trade')" />
                        </Form-item>
                    </Col>
                    <Col span="8">
                        <Form-item prop="speci" class="input" label="相关物种：">
                            <Input v-model="queryInfo.speci" class="pinput-clear" icon="ios-close-circle" readonly @on-focus="handleFilterModal('speciFilter')" @on-click="inputClear('speci')" />
                        </Form-item>
                    </Col>
                    <Col span="8">
                        <Form-item prop="name" class="input" label="专家姓名：">
                            <Input v-model="queryInfo.name" clearable />
                        </Form-item>
                    </Col>
                </Row>
                <Row :gutter="32">
                    <Col span="16">
                        <Form-item prop="trade" class="input" label="所处位置：">
                            <Cascader :data="locationList" v-model="queryInfo.locationList" :load-data="loadPositionDatas" :render-format="format" change-on-select>
                            </Cascader>
                        </Form-item>
                    </Col>
                    <Col span="8">
                        <Button type="primary" @click="query">查询</Button>
                        <Button type="text" @click="clear">重置</Button>
                    </Col>
                </Row>
            </Form>
        </div>
        <div class="mt20">
            <Table border :columns="columns" :data="tableData"></Table>
        </div>
        <div class="mt20 tr" v-if="data.length !== 0">
            <Page :total="total" :page-size="pageSize" :current="pageNum" @on-change="pageChange" />
        </div>

        <!-- 订单详情 -->
        <Modal v-model="modal" title="订单详情" width="1000">
            <Row>
                <Col span="4">
                    <img :src="data.baseData.personalPicture" style="width: 100%;">
                    <div style="font-size: 16px;" class="mt20 tc"><b>专家：{{ data.baseData.expertName }}</b></div>
                </Col>
                <Col span="20"  class="pl20">
                    <Row><b>可享受的服务内容：</b></Row>
                    <Row class="mt20" type="flex" align="middle" v-if="data.doorService">
                        <img src="../../../../../static/img/door-service.png" style="width: 20px;" class="mr10">
                        上门服务&nbsp;&nbsp;&nbsp;&nbsp;服务区域：{{ data.doorServiceData.areaStatus === '设定服务区域' ? data.doorServiceData.area : '不限' }}；
                        服务时间段：{{ data.doorServiceData.timeStatus === '设定服务时间' ? data.doorServiceData.time : data.doorServiceData.timeStatus === '双方约定' ? '双方约定' : '不限'}}；
                        {{ data.doorServiceData.mark === '' ? '' : data.doorServiceData.mark + '；'}}
                    </Row>
                    <Row class="mt20" type="flex" align="middle"  v-if="data.locationService">
                        <img src="../../../../../static/img/location-service.png" style="width: 20px;" class="mr10">
                        定点服务&nbsp;&nbsp;&nbsp;&nbsp;服务网点：<span v-for="(item, index) in data.locationServiceData.networkStationInfo" :key="index">{{ item.name }}；</span>
                        服务时间段：{{ data.locationServiceData.timeStatus === '设定服务时间' ? data.locationServiceData.time : data.locationServiceData.timeStatus === '双方约定' ? '双方约定' : '不限'}}；
                    </Row>
                    <Row class="mt20" type="flex" align="middle"  v-if="data.telephoneService">
                        <img src="../../../../../static/img/telephone-service.png" style="width: 20px;" class="mr10">
                        电话服务&nbsp;&nbsp;&nbsp;&nbsp;服务区域：{{ data.telephoneServiceData.telephoneAreaStatus === '设定服务区域' ? data.telephoneServiceData.telephoneArea : '不限' }}；
                        服务时间段：{{ data.telephoneServiceData.timeStatus === '设定服务时间' ? data.telephoneServiceData.time : data.telephoneServiceData.timeStatus === '双方约定' ? '双方约定' : '不限'}}；
                    </Row>
                    <Row class="mt20" type="flex" align="middle"  v-if="data.networkService">
                        <img src="../../../../../static/img/network-service.png" style="width: 20px;" class="mr10">
                        网络服务&nbsp;&nbsp;&nbsp;&nbsp;服务区域：{{ data.networkServiceData.networkAreaStatus === '设定服务区域' ? data.networkServiceData.networkArea : '不限' }}；
                        服务时间段：{{ data.networkServiceData.timeStatus === '设定服务时间' ? data.networkServiceData.time : data.networkServiceData.timeStatus === '双方约定' ? '双方约定' : '不限'}}；
                    </Row>
                    <Row>
                        <Col span="8">
                            <Row class="mt20"><b>聘请费用：</b></Row>
                            <div v-if="data.serviceType === '提供付费咨询'">
                                <Row class="mt20" v-for="(item, index) in data.chargeType" :key="index">{{ item.employTime }}收费：{{ item.employMoney }}元/{{ item.employTime.substring(1) }}</Row>
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
                                        <RadioGroup v-model="order.employTime">
                                            <Radio disabled v-for="(item, index) in data.chargeType" :key="index" :label="item.employTime"></Radio>
                                        </RadioGroup>
                                    </Col>
                                    <Col span="6">
                                        <InputNumber v-model="order.count" :min="1" :max="1000" readonly></InputNumber>
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
                            <Form ref="order" :model="order" :label-width="80" label-position="left" :rules="ruleInline2" class="mt20">
                                <Row :gutter="32">
                                    <Col span="12">
                                        <FormItem label="姓名" prop="name">
                                            <Input v-model="order.name" readonly />
                                        </FormItem>
                                    </Col>
                                    <Col span="12">
                                        <FormItem label="联系方式" prop="phone">
                                            <Input v-model="order.phone" readonly />
                                        </FormItem>
                                    </Col>
                                </Row>
                                <Row :gutter="32">
                                    <Col span="12">
                                        <FormItem label="联系地址" prop="location">
                                            <Input v-model="order.location" readonly />
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
            </div>
        </Modal>

        <!-- 相关行业 -->
        <vui-filter
        ref="tradeFilter"
        :simple="true"
        :cols="2"
        :pageShow="true"
        :total="total6"
        :pageCur="pageCur6"
        :resultDatas="tradeResultDatas"
        @on-search="handleTradeSearch"
        @on-page-change="handleTradePageChange"
        @on-get-result="handleGetTradeResult" />

        <!-- 相关物种 -->
        <vui-filter
        ref="speciFilter"
        :cols="2"
        :pageShow="true"
        :total="total5"
        :pageCur="pageCur5"
        :classifyDatas="speciClassifyDatas"
        :resultDatas="speciResultDatas"
        :load-data="loadSpeciDatas"
        @on-search="handleSpeciSearch"
        @on-get-classify="handleGetSpeciClassify"
        @on-get-result="handleGetSpeciResult"
        @on-page-change="handleSpeciPageChange" />
    </div>
</template>
<script>
import vuiFilter from '~components/vuiFilter/filter'
import {isPhone2} from '~utils/validate'
export default {
    name: 'employManage',
    components: {
        vuiFilter
    },
    data () {
        return {
            columns: [
                {
                    title: '专家姓名',
                    key: 'expertName',
                    width: 100
                },
                {
                    title: '擅长领域',
                    key: 'adeptField'
                },
                {
                    title: '擅长物种',
                    key: 'adeptSpecies'
                },
                {
                    title: '相关行业',
                    key: 'relatedIndustry'
                },
                {
                    title: '相关物种',
                    key: 'relatedSpecies'
                },
                {
                    title: '付费金额（元）',
                    key: 'money',
                    width: 130
                },
                {
                    title: '聘请到期时间',
                    key: 'endTime'
                },
                {
                    title: '操作',
                    key: 'action',
                    width: 180,
                    render: (h, params) => {
                        return h('div', [
                            h('a', {
                                style: {
                                    marginRight: '10px',
                                    color: '#2c92ff'
                                },
                                on: {
                                    click: () => {
                                        this.portal(params.row)
                                    }
                                }
                            }, '查看门户'),
                            h('a', {
                                style: {
                                    marginRight: '10px',
                                    color: '#00c587'
                                },
                                on: {
                                    click: () => {
                                        this.detail(params.row)
                                    }
                                }
                            }, '订单详情'),
                            h('a', {
                                style: {
                                    color: params.row.employStatus === 1 ? '#ff5c76' : '#999',
                                    cursor: params.row.employStatus === 1 ? 'pointer' : 'not-allowed',
                                },
                                on: {
                                    click: () => {
                                      if (params.row.employStatus === 1) {
                                        this.renew(params.row)
                                      }
                                    }
                                }
                            }, '续费')
                        ])
                    }
                }
            ],
            tableData: [],
            total: 0,
            pageSize: 10,
            pageNum: 1,
            queryInfo: {
                trade: '',
                tradeId: '',
                speci: '',
                speciId: '',
                name: '',
                location: '',
                locationList: []
            },
            locationList: [],
            ruleInline: {},
            tradeResultDatas: [],
            speciClassifyDatas: [
                {
                    label: '动物',
                    value: '0',
                    classId: '',
                    loading: false,
                    checked: false,
                    children: []
                },
                {
                    label: '植物',
                    value: '1',
                    classId: '',
                    loading: false,
                    checked: false,
                    children: []
                }
            ],
            speciResultDatas: [],
            total5: 0,
            pageCur5: 1,
            total6: 0,
            pageCur6: 1,
            modal: false,
            data: {
                baseData: {
                    expertName: '',
                    serviceName: '',
                    adeptField: '',
                    relatedSpecies: '',
                    relatedExperience: ''
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
            ruleInline2: {
                name: [
                    { required: true, message: '请填写联系人姓名', trigger: 'blur' }
                ],
                phone: [
                    { required: true, message: '请填写手机号码', trigger: 'blur' },
                    { validator: isPhone2 }
                ]
            }
        }
    },
    created () {
        this.init()
        // 取相关行业数据
        this.$api.post('/member/system-dict/getSystemDict', {
            typeName:'行业分类',
			pageNum: 1
        }).then(res => {
            console.log('初始化相关行业', res)
            var data = res.data.list
            this.total6 = res.data.total
            this.tradeResultDatas = data
        })
        // 取相关物种结果
        this.loadSpeciResult('', '', [], this.pageCur5, [])
        // 取地址
        this.$api.post('/member/town/next/4cc0ce9b1b8d1e8ab8c005056bc3816').then(res => {
            this.locationList = res.data
        })
    },
    methods: {
        init () {
            this.$api.post('/member-reversion/employ/manage', {
                pageNum: this.pageNum,
                pageSize: this.pageSize,
                account: this.$user.loginAccount,
                relatedIndustry: this.queryInfo.trade, //相关行业
                relatedSpecies: this.queryInfo.speci, //相关物种
                expertName: this.queryInfo.name, //专家名称
                location: this.queryInfo.location //所在位置
            }).then(response => {
                console.log('res', response)
                if (response.code === 200) {
                    this.tableData = response.data.list
                    this.total = response.data.total
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        query () {
            this.pageNum = 1
            this.init()
        },
        clear () {
            this.inputClear('trade')
            this.inputClear('speci')
            this.queryInfo.name = ''
            this.queryInfo.location = ''
            this.queryInfo.locationList = []
        },
        portal (item) {
            console.log('item', item)
            this.$toPortals(item.account)
        },
        detail (item) {
            this.$api.get('/member-reversion/employ/orderDetail?id=' + item.id).then(response => {
                if (response.code === 200) {
                    this.data = response.data.data
                    this.order = response.data.order
                    this.modal = true
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        renew (item) {
            this.$router.push({
                path: '/consultationService/detail',
                query: {
                    id: item.consultId
                }
            })
        },
        cancel () {
            this.modal = false
        },
        pageChange (page) {
            this.pageNum = page
            this.init()
        },
        // 清空文本框
        inputClear(name) {
            if (name === 'speci') {
                this.queryInfo.speci = ''
                this.queryInfo.speciId = ''
                this.$refs.speciFilter.handleReset()
            } else if (name === 'trade') {
                this.queryInfo.trade = ''
                this.queryInfo.tradeId =''
                this.$refs.tradeFilter.handleReset()
            }
        },
        // 地区
        loadPositionDatas (item, callback) {
            item.loading = true
            this.$api.post(`/member/town/next/${item.value}`).then(res => {
                item.loading = false
                item.children = res.data
                callback()
            })
        },
        format (labels, selectedData) {
            this.queryInfo.location = labels.join('/')
            return labels.join('/')
        },
        // 高级搜索弹窗
        handleFilterModal (name) {
            this.$refs[name].highFilterShow = true
        },
        // 相关行业搜索
        handleTradeSearch (letter, keyword, result) {
            this.$api.post(`/member/system-dict/getSystemDict`, {
                dictName : keyword,
                typeName : '行业分类',
                character: letter === '全部' ? '' : letter,
                pageNum: 1
            }).then(res => {
                console.log('行业分类按条件查询', res)
                var d = res.data.list
                this.total6 = res.data.total
                this.pageCur6 = 1
                this.tradeResultDatas = []
                if (!d.length) return
                if (result.length) {
                    result.forEach(item => {
                        d.forEach(child => {
                            if (child.label === item.label) {
                                child.checked = true
                                child.label = child.label
                                child.value = child.value
                            }
                        })
                    })
                } else {
                    d.forEach(child => {
                        child.checked = false
                        child.label = child.label
                        child.value = child.value
                    })
                }
                this.tradeResultDatas = d
            })
        },
        handleGetTradeResult (classify, result) {
            var arr = []
            var arr2 = []
            result.forEach(item => {
                arr.push(item.label)
                arr2.push(item.value)
            })
            this.queryInfo.trade = arr.join(' ')
            this.queryInfo.tradeId = arr2.join(' ')
        },
        handleTradePageChange (letter, keyword, classify, num, result) {
            this.pageCur6 = num
            this.$api.post('/member/system-dict/getSystemDict', {
                typeName:'行业分类',
                character: letter === '全部' ? '' : letter,
                dictName: keyword,
                pageNum: num
            }).then(res => {
                console.log('行业分类分页', res)
                var data = res.data.list
                this.total6 = res.data.total
                this.tradeResultDatas = data
            })
            this.$Message.success('筛选完成！')
        },
        loadSpeciDatas (item, callback) {
            item.loading = true
            api.post(`/member/specicesClass/findByParentId/${item.value}`).then(res => {
                item.loading = false
                var d = res.data
                d.forEach(child => {
                    child.checked = false
                    child.label = child.className
                })
                item.children = d
                callback()
            })
        },
        // 相关物种
        handleSpeciSearch (letter, keyword, classify, result) {
            this.loadSpeciResult(letter, keyword, classify, this.pageCur5, result)
            this.$Message.success('筛选完成！')
        },
        handleGetSpeciClassify (letter, keyword, classify, result) {
            this.loadSpeciResult(letter, keyword, classify, this.pageCur5, result)
            this.$Message.success('筛选完成！')
        },
        handleGetSpeciResult (classify, result) {
            var arr = []
            var arr2 = []
            result.forEach(item => {
                arr.push(item.label)
                arr2.push(item.value)
            })
            this.queryInfo.speci = arr.join(' ')
            this.queryInfo.speciId = arr2.join(' ')
        },
        handleSpeciPageChange (letter, keyword, classify, num, result) {
            this.pageCur5 = num
            this.loadSpeciResult(letter, keyword, classify, this.pageCur5, result)
            this.$Message.success('筛选完成！')
        },
        loadSpeciResult (letter, keyword, classify, num, result) {
            if(classify.length) {
                var arr = []
                classify.forEach(item => {
                    arr.push(item.classId)
                })
            } else {
                arr = null
            }
            let a = {
                keywords: keyword,
                fpinyin: letter === '全部' ? '' : letter,
                fclassifiedid: arr,
                pageNum: num,
                pageSize: 32
            }
            this.$api.post('/member/specicesClass/findSpecies', a).then(res => {
                var data = res.data
                this.total5 = data.total
                if (result) {
                    result.forEach(item => {
                        data.list.forEach((child,index)=>{
                            if (child.label === item.label) {
                                child.checked = true
                            }
                        })
                    })
                } else {
                    data.list.forEach(child => {
                        child.checked = false
                    })
                }
                this.speciResultDatas = data.list
            })
        }
    }
}
</script>
<style lang="scss" scoped>

</style>
