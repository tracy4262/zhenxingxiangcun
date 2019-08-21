<!--
    作者：chenqim
    时间：2019-02-26
    描述：发布咨询服务第三步
-->
<template>
    <div class="pd20" style="min-height: 500px; width: 720px; margin: 0 auto;">
        <Form ref="data" :model="data" :label-width="100" label-position="left" :rules="ruleInline" class="mt20">
            <FormItem label="服务方式" prop="serviceType">
                <RadioGroup v-model="data.serviceType" @on-change="serviceTypeChange">
                    <Radio label="提供免费咨询"></Radio>
                    <Radio label="提供付费咨询"></Radio>
                </RadioGroup>
                <span style="color: #d0d0d0; margin-left: 10px;">提供付费咨询时，对方需聘请您为专家后，才能享受相关咨询服务</span>
            </FormItem>
        </Form>
        <div v-if="data.serviceType === '提供付费咨询'">
            <Title class="mb20" title="收费方式"></Title>
            <div v-for="(item, index) in data.chargeType" :key="index">
                <Form :ref="`data${index}`" :model="item" :label-width="80" label-position="left" :rules="ruleInline">
                    <Row :gutter="36">
                        <Col span="9">
                            <FormItem label="聘请时长" prop="employTime">
                                <Select v-model="item.employTime">
                                    <Option v-for="item in employTimeList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                                </Select>
                            </FormItem>
                        </Col>
                        <Col span="9">
                            <FormItem label="聘请费用" prop="employMoney">
                                <InputNumber v-model="item.employMoney" :min="1" :max="1000000"></InputNumber>
                                <span v-if="item.employTime === '按小时'">元/小时</span>
                                <span v-if="item.employTime === '按天'">元/天</span>
                                <span v-if="item.employTime === '按月'">元/月</span>
                                <span v-if="item.employTime === '按年'">元/年</span>
                            </FormItem>
                        </Col>
                        <Col span="6">
                            <Button @click="del(index)" v-if="data.chargeType.length > 1">删除</Button>
                            <Button @click="add" v-if="index === 0">新增</Button>
                        </Col>
                    </Row>
                </Form>
            </div>
        </div>
        <Title class="mb20" title="可提供的服务方式"></Title>
        <!-- 上门服务 -->
        <Row class="mt20">
            <Checkbox v-model="data.doorService" @on-change="doorServiceChange">上门服务</Checkbox>
            <span style="color: #d0d0d0; margin-left: 10px;">到顾客指定地点提供服务，产生的额外费用需要另行收费</span>
        </Row>
        <card class="mt20" v-if="data.doorService">
            <Form ref="doorServiceData" :model="data.doorServiceData" :label-width="100" label-position="left" :rules="ruleInline">
                <Row :gutter="36" type="flex" align="middle">
                    <Col span="14">
                        <FormItem label="上门服务区域" prop="areaStatus">
                            <RadioGroup v-model="data.doorServiceData.areaStatus" @on-change="data.doorServiceData.area=''">
                                <Radio label="不限"></Radio>
                                <Radio label="设定服务区域"></Radio>
                            </RadioGroup>
                        </FormItem>
                    </Col>
                    <Col span="10" v-if="data.doorServiceData.areaStatus === '设定服务区域'">
                        <FormItem prop="area">
                            <Input v-model="data.doorServiceData.area" placeholder="请填写服务区域，如武汉" style="width: 200px;  margin-left: -100px;" />
                        </FormItem>
                    </Col>
                </Row>
                <Row :gutter="36" type="flex" align="middle">
                    <Col span="14">
                        <FormItem label="服务时段" prop="timeStatus">
                            <RadioGroup v-model="data.doorServiceData.timeStatus" @on-change="data.doorServiceData.time=''">
                                <Radio label="不限"></Radio>
                                <Radio label="双方约定"></Radio>
                                <Radio label="设定服务时间"></Radio>
                            </RadioGroup>
                        </FormItem>
                    </Col>
                    <Col span="10" v-if="data.doorServiceData.timeStatus === '设定服务时间'">
                        <FormItem prop="time">
                            <Input v-model="data.doorServiceData.time" placeholder="例如：每周一 9:00 — 12:00" style="width: 200px; margin-left: -100px;" />
                        </FormItem>
                    </Col>
                </Row>
                <Row :gutter="36" type="flex" align="middle">
                    <Col span="24">
                        <FormItem label="备注" prop="mark">
                            <Input v-model="data.doorServiceData.mark" type="textarea" />
                        </FormItem>
                    </Col>
                </Row>
            </Form>
        </card>
        <!-- 定点服务 -->
        <Row class="mt20">
            <Checkbox v-model="data.locationService" @on-change="locationServiceChange">定点服务</Checkbox>
            <span style="color: #d0d0d0; margin-left: 10px;">顾客到商家指定地点接受服务</span>
        </Row>
        <card class="mt20" v-if="data.locationService">
            <Form ref="locationServiceData" :model="data.locationServiceData" :label-width="100" label-position="left" :rules="ruleInline">
                <Row>
                    <Col span="14">
                        <FormItem label="服务时段" prop="timeStatus">
                            <RadioGroup v-model="data.locationServiceData.timeStatus" @on-change="data.locationServiceData.time=''">
                                <Radio label="不限"></Radio>
                                <Radio label="双方约定"></Radio>
                                <Radio label="设定服务时间"></Radio>
                            </RadioGroup>
                        </FormItem>
                    </Col>
                    <Col span="10" v-if="data.locationServiceData.timeStatus === '设定服务时间'">
                        <FormItem prop="time">
                            <Input v-model="data.locationServiceData.time" placeholder="例如：每周一 9:00 — 12:00" style="width: 200px; margin-left: -100px;" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <FormItem label="服务网点信息" prop="networkStationInfo">
                        <Button type="success" ghost @click="chooseInfo" class="btn-light-primary">从基础设置中选择</Button>
                        <Button type="success" ghost @click="addInfo" class="btn-light-primary">添加</Button>
                    </FormItem>
                </Row>
            </Form>
            <!-- 服务网点控件 -->
            <network-station ref="networkStation"></network-station>
        </card>
        <!-- 电话服务 -->
        <Row class="mt20">
            <Checkbox v-model="data.telephoneService" @on-change="telephoneServiceChange">电话服务</Checkbox>
            <span style="color: #d0d0d0; margin-left: 10px;">商家在指定的时间内向顾客提供咨询服务</span>
        </Row>
        <card class="mt20" v-if="data.telephoneService">
            <Form ref="telephoneServiceData" :model="data.telephoneServiceData" :label-width="100" label-position="left" :rules="ruleInline">
                <Row :gutter="36" type="flex" align="middle">
                    <Col span="14">
                        <FormItem label="电话服务区域" prop="telephoneAreaStatus">
                            <RadioGroup v-model="data.telephoneServiceData.telephoneAreaStatus" @on-change="data.telephoneServiceData.telephoneArea=''">
                                <Radio label="不限"></Radio>
                                <Radio label="设定服务区域"></Radio>
                            </RadioGroup>
                        </FormItem>
                    </Col>
                    <Col span="10" v-if="data.telephoneServiceData.telephoneAreaStatus === '设定服务区域'">
                        <FormItem prop="telephoneArea">
                            <Input v-model="data.telephoneServiceData.telephoneArea" placeholder="请填写服务区域，如武汉" style="width: 200px;  margin-left: -100px;" />
                        </FormItem>
                    </Col>
                </Row>
                <Row :gutter="36" type="flex" align="middle">
                    <Col span="14">
                        <FormItem label="服务时段" prop="timeStatus">
                            <RadioGroup v-model="data.telephoneServiceData.timeStatus" @on-change="data.telephoneServiceData.time=''">
                                <Radio label="不限"></Radio>
                                <Radio label="双方约定"></Radio>
                                <Radio label="设定服务时间"></Radio>
                            </RadioGroup>
                        </FormItem>
                    </Col>
                    <Col span="10" v-if="data.telephoneServiceData.timeStatus === '设定服务时间'">
                        <FormItem prop="time">
                            <Input v-model="data.telephoneServiceData.time" placeholder="例如：每周一 9:00 — 12:00" style="width: 200px; margin-left: -100px;" />
                        </FormItem>
                    </Col>
                </Row>
            </Form>
        </card>
        <!-- 网络服务 -->
        <Row class="mt20">
            <Checkbox v-model="data.networkService" @on-change="networkServiceChange">网络服务</Checkbox>
            <span style="color: #d0d0d0; margin-left: 10px;">商家在指定的时间内向顾客提供网上服务</span>
        </Row>
        <card class="mt20" v-if="data.networkService">
            <Form ref="networkServiceData" :model="data.networkServiceData" :label-width="100" label-position="left" :rules="ruleInline">
                <Row :gutter="36" type="flex" align="middle">
                    <Col span="14">
                        <FormItem label="网络服务区域" prop="networkAreaStatus">
                            <RadioGroup v-model="data.networkServiceData.networkAreaStatus" @on-change="data.networkServiceData.networkArea=''">
                                <Radio label="不限"></Radio>
                                <Radio label="设定服务区域"></Radio>
                            </RadioGroup>
                        </FormItem>
                    </Col>
                    <Col span="10" v-if="data.networkServiceData.networkAreaStatus === '设定服务区域'">
                        <FormItem prop="networkArea">
                            <Input v-model="data.networkServiceData.networkArea" placeholder="请填写服务区域，如武汉" style="width: 200px;  margin-left: -100px;" />
                        </FormItem>
                    </Col>
                </Row>
                <Row :gutter="36" type="flex" align="middle">
                    <Col span="14">
                        <FormItem label="服务时段" prop="timeStatus">
                            <RadioGroup v-model="data.networkServiceData.timeStatus" @on-change="data.networkServiceData.time=''">
                                <Radio label="不限"></Radio>
                                <Radio label="双方约定"></Radio>
                                <Radio label="设定服务时间"></Radio>
                            </RadioGroup>
                        </FormItem>
                    </Col>
                    <Col span="10" v-if="data.networkServiceData.timeStatus === '设定服务时间'">
                        <FormItem prop="time">
                            <Input v-model="data.networkServiceData.time" placeholder="例如：每周一 9:00 — 12:00" style="width: 200px; margin-left: -100px;" />
                        </FormItem>
                    </Col>
                </Row>
            </Form>
        </card>
        <div class="tc pt20">
            <Button type="primary" @click="handleBack">上一步</Button>
            <Button type="primary" @click="handleSave">下一步</Button>
            <Button type="text" @click="handleNext">以后再完善</Button>
        </div>
    </div>
</template>
<script>
import Title from '../../../goFishing/components/title'
import networkStation from './networkStation'
export default {
    components: {
        Title,
        networkStation
    },
    data () {
        return {
            data: {
                serviceType: '提供免费咨询',
                chargeType: [
                    {
                        employTime: '按小时',
                        employMoney: 1
                    }
                ],
                doorService: false,
                locationService: false,
                telephoneService: false,
                networkService: false,
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
                    networkStationInfo: []
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
                }
            },
            ruleInline: {
                serviceType:[{required: true,message:'请选择服务方式', trigger: 'change'}]
            },
            employTimeList: [
                {
                    label: '按小时',
                    value: '按小时'
                },
                {
                    label: '按天',
                    value: '按天'
                },
                {
                    label: '按月',
                    value: '按月'
                },
                {
                    label: '按年',
                    value: '按年'
                }
            ],
            id: '',
            account: this.$user.loginAccount
        }
    },
    created () {
        this.id = this.$route.query.id
        this.handleInit()
    },
    methods: {
        handleInit () {
            this.$api.post('/member-reversion/consult/findStepThree', {
                id: this.id,
                account: this.account
            }).then(response => {
                if (response.code === 200) {
                    if (response.data.serviceType) {
                        this.data.serviceType = response.data.serviceType
                    }
                    if (response.data.chargeType) {
                        let arr = []
                        response.data.chargeType.forEach(element => {
                            arr.push({
                                employTime: element.employTime,
                                employMoney: parseInt(element.employMoney)
                            })
                        })
                        this.data.chargeType = arr
                    }
                    if (response.data.doorService) {
                        this.data.doorService = response.data.doorService
                    }
                    if (response.data.doorServiceData) {
                        if (response.data.doorServiceData.area) {
                            this.data.doorServiceData.area = response.data.doorServiceData.area
                        }
                        if (response.data.doorServiceData.areaStatus) {
                            this.data.doorServiceData.areaStatus = response.data.doorServiceData.areaStatus
                        }
                        if (response.data.doorServiceData.mark) {
                            this.data.doorServiceData.mark = response.data.doorServiceData.mark
                        }
                        if (response.data.doorServiceData.time) {
                            this.data.doorServiceData.time = response.data.doorServiceData.time
                        }
                        if (response.data.doorServiceData.timeStatus) {
                            this.data.doorServiceData.timeStatus = response.data.doorServiceData.timeStatus
                        }
                    }
                    if (response.data.locationService) {
                        this.data.locationService = response.data.locationService
                    }
                    if (response.data.locationServiceData) {
                        if (response.data.locationServiceData.networkStationInfo) {
                            this.data.locationServiceData.networkStationInfo = response.data.locationServiceData.networkStationInfo
                            if (response.data.locationService) {
                                this.$nextTick(() => {
                                    this.$refs['networkStation'].data = response.data.locationServiceData.networkStationInfo
                                })
                            }
                        }
                        if (response.data.locationServiceData.timeStatus) {
                            this.data.locationServiceData.timeStatus = response.data.locationServiceData.timeStatus
                        }
                        if (response.data.locationServiceData.time) {
                            this.data.locationServiceData.time = response.data.locationServiceData.time
                        }
                    }
                    if (response.data.networkService) {
                        this.data.networkService = response.data.networkService
                    }
                    if (response.data.networkServiceData) {
                        if (response.data.networkServiceData.networkArea) {
                            this.data.networkServiceData.networkArea = response.data.networkServiceData.networkArea
                        }
                        if (response.data.networkServiceData.networkAreaStatus) {
                            this.data.networkServiceData.networkAreaStatus = response.data.networkServiceData.networkAreaStatus
                        }
                        if (response.data.networkServiceData.time) {
                            this.data.networkServiceData.time = response.data.networkServiceData.time
                        }
                        if (response.data.networkServiceData.timeStatus) {
                            this.data.networkServiceData.timeStatus = response.data.networkServiceData.timeStatus
                        }
                    }
                    if (response.data.telephoneService) {
                        this.data.telephoneService = response.data.telephoneService
                    }
                    if (response.data.telephoneServiceData) {
                        if (response.data.telephoneServiceData.telephoneArea) {
                            this.data.telephoneServiceData.telephoneArea = response.data.telephoneServiceData.telephoneArea
                        }
                        if (response.data.telephoneServiceData.telephoneAreaStatus) {
                            this.data.telephoneServiceData.telephoneAreaStatus = response.data.telephoneServiceData.telephoneAreaStatus
                        }
                        if (response.data.telephoneServiceData.time) {
                            this.data.telephoneServiceData.time = response.data.telephoneServiceData.time
                        }
                        if (response.data.telephoneServiceData.timeStatus) {
                            this.data.telephoneServiceData.timeStatus = response.data.telephoneServiceData.timeStatus
                        }
                    }
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        serviceTypeChange (e) {
            this.data = {
                serviceType: e,
                chargeType: [
                    {
                        employTime: '按小时',
                        employMoney: ''
                    }
                ],
                doorService: false,
                locationService: false,
                telephoneService: false,
                networkService: false,
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
                    networkStationInfo: []
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
                }
            }
        },
        doorServiceChange () {
            this.data.doorServiceData = {
                areaStatus: '不限',
                area: '',
                timeStatus: '不限',
                time: '',
                mark: ''
            }
        },
        locationServiceChange () {
            this.data.locationServiceData = {
                timeStatus: '不限',
                time: '',
                networkStationInfo: []
            }
        },
        telephoneServiceChange () {
            this.data.telephoneServiceData = {
                telephoneAreaStatus: '不限',
                telephoneArea: '',
                timeStatus: '不限',
                time: '',
            }
        },
        networkServiceChange () {
            this.data.networkServiceData = {
                networkAreaStatus: '不限',
                networkArea: '',
                timeStatus: '不限',
                time: ''
            }
        },
        add () {
            this.data.chargeType.push({
                employTime: '按小时',
                employMoney: ''
            })
        },
        del (index) {
            this.$Modal.confirm({
                title: '操作提示',
                content: '是否确认删除？',
                onOk:()=>{
                    this.data.chargeType.splice(index, 1)
                    this.$Message.success('删除成功！')
                },
                okText:'确定',
                cancelText:'取消'
            })
        },
        chooseInfo () {
            this.$refs['networkStation'].chooseInfo()
        },
        addInfo () {
            this.$refs['networkStation'].addInfo()
        },
        handleBack () {
            this.$router.push({
                path: '/addConsultationService/step2',
                query: {
                    id: this.id
                }
            })
            this.$emit('last')
        },
        handleSave () {
            if (this.data.serviceType === '提供付费咨询') {
                for (let index = 0; index < this.data.chargeType.length; index++) {
                    let element = this.data.chargeType[index]
                    if (element.employMoney === '') {
                        this.$Message.error('请填写聘请费用！')
                        return
                    }
                }
                for (let i = 0; i < this.data.chargeType.length; i++) {
                    let flag = this.data.chargeType[i]
                    for (let j = i + 1; j < this.data.chargeType.length; j++) {
                        if (flag.employTime === this.data.chargeType[j].employTime) {
                            this.$Message.error('聘请时长不能相同！')
                            return
                        }
                    }
                }
            }
            if (!(this.data.doorService || this.data.locationService || this.data.telephoneService || this.data.networkService)) {
                this.$Message.error('请选择至少一种服务方式！')
                return
            }
            if (this.data.doorService) {
                if (this.data.doorServiceData.areaStatus === '设定服务区域') {
                    if (this.data.doorServiceData.area === '') {
                        this.$Message.error('请填写上门服务的服务区域！')
                        return
                    }
                }
                if (this.data.doorServiceData.timeStatus === '设定服务时间') {
                    if (this.data.doorServiceData.time === '') {
                        this.$Message.error('请填写上门服务的服务时段！')
                        return
                    }
                }
            }
            if (this.data.locationService) {
                this.data.locationServiceData.networkStationInfo = this.$refs['networkStation'].data // 从组件中取得营业网点的数据
                if (this.data.locationServiceData.timeStatus === '设定服务时间') {
                    if (this.data.locationServiceData.time === '') {
                        this.$Message.error('请填写定点服务的服务时段！')
                        return
                    }
                }
                if (this.data.locationServiceData.networkStationInfo.length === 0) {
                    this.$Message.error('请选择或添加至少一个服务网点信息！')
                    return
                }
            }
            if (this.data.telephoneService) {
                if (this.data.telephoneServiceData.telephoneAreaStatus === '设定服务区域') {
                    if (this.data.telephoneServiceData.telephoneArea === '') {
                        this.$Message.error('请填写电话服务的服务区域！')
                        return
                    }
                }
                if (this.data.telephoneServiceData.timeStatus === '设定服务时间') {
                    if (this.data.telephoneServiceData.time === '') {
                        this.$Message.error('请填写电话服务的服务时段！')
                        return
                    }
                }
            }
            if (this.data.networkService) {
                if (this.data.networkServiceData.networkAreaStatus === '设定服务区域') {
                    if (this.data.networkServiceData.networkArea === '') {
                        this.$Message.error('请填写网络服务的服务区域！')
                        return
                    }
                }
                if (this.data.networkServiceData.timeStatus === '设定服务时间') {
                    if (this.data.networkServiceData.time === '') {
                        this.$Message.error('请填写网络服务的服务时段！')
                        return
                    }
                }
            }
            this.data.account = this.account
            this.data.id = this.id
            this.$api.post('/member-reversion/consult/publishStepThree', this.data).then(response => {
                if (response.code === 200) {
                    this.$Message.success('保存成功！')
                    this.$router.push({
                        path: '/addConsultationService/step4',
                        query: {
                            id: this.id
                        }
                    })
                    this.$emit('next')
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        handleNext () {
            this.$router.push('/service/consultationService')
        }
    }
}
</script>
