<template>
    <div>
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
                        定点服务&nbsp;&nbsp;&nbsp;&nbsp;服务网点：<span v-for="(item, index) in data.locationServiceData.networkStationInfo" :key="index">{{ item.name }}；</span>
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
                                            <Radio disabled v-for="(item, index) in data.chargeType" :label="item.employTime" :key="index"></Radio>
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
    </div>
</template>
<script>
import {isPhone2} from '~utils/validate'
export default {
    data () {
        return {
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
    methods: {
        init (id) {
            this.$api.get('/member-reversion/employ/orderDetail?id=' + id).then(response => {
                if (response.code === 200) {
                    this.data = response.data.data
                    this.order = response.data.order
                    this.modal = true
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        cancel () {
            this.modal = false
        }
    }
}
</script>
<style scoped>

</style>
