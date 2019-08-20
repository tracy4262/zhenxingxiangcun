<!--
    作者：chenqim
    时间：2019-02-18
    描述：生产基地物联设施编辑页
-->
<template>
    <div class="pd20" style="min-height: 500px;">
        <Title title="物联设施信息"></Title>
        <Form :label-width="100" class="mt40">
            <Row>
                <Col span="12">
                    <FormItem label="物联设施信息" prop="contactInfo">
                        <Button type="success" ghost @click="chooseInfo" class="btn-light-primary">从公共设施——物联设施中导入</Button>
                        <Button type="success" ghost @click="addInfo" class="btn-light-primary">新增</Button>
                    </FormItem>
                </Col>
            </Row>
        </Form>
        <!-- 导入物联设施信息弹框 -->
        <Modal v-model="modal" title="导入物联设施信息" width="1000" :closable="false">
            <Table border :columns="tableColumns" :data="tableData" @on-selection-change="tableChange"></Table>
            <div slot="footer">
                <Button type="text" @click="cancel">取消</Button>
                <Button type="primary" @click="ok">确定</Button>
            </div>
        </Modal>
        <div v-if="this.data.length === 0" style="height: 300px;"></div>
        <!-- 物联设施信息card -->
        <div class="certification pd20 mb20 pt30" v-for="(item, index) in data">
            <span>注：设置公开的物联设施可被其他用户查看；这里做了新增或修改操作会同步新增或修改基地公共设施中的物联设施信息。</span>
            <Form :ref="`formItem${index}`" :model="item" label-position="left" :label-width="100" :rules="formItemInline" class="mt20">
                <Row>
                    <Col span="12">
                        <Form-item label="权限">
                            <i-switch  v-model="item.status" size="large" >
                                <span slot="open">公开</span>
                                <span slot="close">隐藏</span>
                            </i-switch>
                        </Form-item>
                    </Col>
                    <Col span="12" class="tr">
                        <div class="btn-toolbar">
                            <Poptip trigger="hover" content="点击撤销，该物联设施信息在基地公共设施中保留。">
                                <Button type="text" @click="revoke(item, index)"><Icon size="16" class="pr5"></Icon>撤销</Button>
                            </Poptip>
                            <Poptip trigger="hover" content="点击删除，则同步删除基地公共设施中的该物联设施信息。">
                                <Button type="text" @click="del(item, index)"><Icon size="16" class="pr5"></Icon>删除</Button>
                            </Poptip>
                        </div>
                    </Col>
                </Row>
                <Row :gutter="32">
                    <Col span="8">
                        <Form-item label="设施类别">
                            <Select v-model="item.commonName" >
                                <Option value="监控设施">监控设施</Option>
                                <Option value="天气监测设施">天气监测设施</Option>
                                <Option value="土壤检测设施">土壤检测设施</Option>
                                <Option value="自定义（其他设施）">自定义（其他设施）</Option>
                            </Select>
                        </Form-item>
                    </Col>
                    <Col span="8">
                        <Form-item label="设施名称">
                            <Input v-model="item.name"  />
                        </Form-item>
                    </Col>
                    <Col span="8">
                        <Form-item label="设施编号">
                            <Input v-model="item.no"  readonly />
                        </Form-item>
                    </Col>
                </Row>
                <Row :gutter="32">
                    <Col span="8">
                        <Form-item label="设施规格">
                            <Input v-model="item.capacity"  />
                        </Form-item>
                    </Col>
                    <Col span="8">
                        <Form-item label="投资额（万元）">
                            <Input v-model="item.investment" />
                        </Form-item>
                    </Col>
                    <Col span="8">
                        <Form-item label="责任人">
                            <Input v-model="item.contact"  />
                        </Form-item>
                    </Col>
                </Row>
                <Row>
                    <Col span="24">
                    <Form-item label="设施地址">
                        <Input v-model="item.address" :maxlength="100"  />
                    </Form-item>
                    </Col>
                </Row>
                <Row :gutter="32">
                    <Col span="16" style="z-index: 1;">
                    <Form-item label="所处位置">
                        <vui-cascander :values="item.location" @handle-get-result="handleGetData" @click.native="getIndex(index)" >
                        </vui-cascander>
                    </Form-item>
                    </Col>
                    <Col span="8">
                    <Form-item style="margin-left: -100px;">
                        <Input v-model="item.group" :maxlength="5"  style="width: 60px; margin-right: 10px;" />组
                        <Input v-model="item.number" :maxlength="5"  style="width: 60px; margin-right: 10px; margin-left: 10px;" />号
                    </Form-item>
                    </Col>
                </Row>
                <Row>
                    <Col span="24">
                    <Form-item label="说明">
                        <Input v-model="item.description" type="textarea" :autosize="{minRows: 3,maxRows: 5}" :maxlength="200"  />
                    </Form-item>
                    </Col>
                </Row>
                <Row :gutter="32">
                    <Col span="8">
                    <FormItem label="东经">
                        <Input v-model="item.longitude"  readonly />
                    </FormItem>
                    </Col>
                    <Col span="8">
                    <FormItem label="北纬">
                        <Input v-model="item.latitude"  readonly />
                    </FormItem>
                    </Col>
                    <Col span="8" style="text-decoration: underline; color: #6C6C6C; cursor: pointer;" class="pt5">
                        <span @click="onSelectPoint(index)">定位获取</span>
                    </Col>
                </Row>
                <div class="pb30 pt10" v-if="item.latitude">
                    <a target="_blank" :href="`http://api.map.baidu.com/marker?location=${item.latitude},${item.longitude}&title=我的位置&content=${item.locationAddr}&output=html`">
                        <img :src="`//api.map.baidu.com/staticimage/v2?ak=7syPirZ2AWxacMfHeAfuujdDgFmxCB5g&center=${item.longitude},${item.latitude}&zoom=15&markers=${item.longitude},${item.latitude}&width=800`" width="100%" />
                    </a>
                </div>
                <Row>
                    <Col span="24">
                        <Form-item label="上传图片">
                            <vui-upload
                                :ref="`picture${index}`"
                                @on-getPictureList="getList($event, index)"
                                :hint="'图片大小小于2MB，支持后缀名png jpg'"
                                :total="9999999"
                                :size="[80,80]"
                            ></vui-upload>
                        </Form-item>
                    </Col>
                </Row>
            </Form>
        </div>
        <!-- 地图控件 -->
        <vui-map ref="experMap" @on-get-point="onGetPoint"></vui-map>
        <div class="tc mt40">
            <Button type="default" @click="quit" style="width: 105px;">退出</Button>
            <Button type="primary" @click="last" style="width: 105px;" class="ml10">上一步</Button>
            <Button type="primary" @click="next" style="width: 105px;" class="ml10">保存并下一步</Button>
        </div>
    </div>
</template>
<script>
import Title from './title2'
import vuiUpload from '~components/vui-upload'
import vuiMap from '../../../member/components/productionMap'
import vuiCascander from '~components/vuiCascader/index'
export default {
    components: {
        Title,
        vuiUpload,
        vuiMap,
        vuiCascander
    },
    data () {
        return {
            index: 0,
            modal: false,
            tableColumns: [
                {
                    type: 'selection',
                    width: 60
                },
                {
                    title: '设施类别',
                    key: 'commonName'
                },
                {
                    title: '设施名称',
                    key: 'name'
                },
                {
                    title: '设施编号',
                    key: 'no'
                }
            ],
            tableData: [],
            data: [],
            formItemInline: {},
            data: [],
            choosedData: [],
            baseId: ''
        }
    },
    created () {
        this.baseId = this.$route.query.id
        this.initDeviceInfo()
        this.initDeviceInfoTable()
    },
    methods: {
        // 初始化物联设施信息表格
        initDeviceInfoTable () {
            this.$api.post('/member-reversion/productionBase/iotList', {
                account: this.$user.loginAccount,
                baseId: this.baseId
            }).then(response => {
                if (response.code === 200) {
                    let arr = response.data
                    arr.forEach(element => {
                        element._checked = element.isChoose === 1 ? true : false
                        element._disabled = element.isChoose === 1 ? true : false
                    })
                    this.tableData = arr
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        // 初始化第二步物联设施信息
        initDeviceInfo () {
            this.$api.post('/member-reversion/productionBase/findBaseIotDeviceInfo', {
                account: this.$user.loginAccount,
                baseId: this.baseId
            }).then(response => {
                if (response.code === 200) {
                    console.log('resss', response)
                    this.data = response.data
                    this.data.forEach((element, index) => {
                        this.$nextTick(() => {
                            this.$refs[`picture${index}`][0].handleGive(element.pictureList)
                        })
                    })
                } else {
                    this.$Message.error('服务器异常！')
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        quit () {
            this.$router.push('/member/productionBaseList')
        },
        last () {
            this.$emit('last')
        },
        next () {
            if (this.data.length > 0) {
                this.$api.post('/member-reversion/productionBase/saveBaseIotDeviceInfo', {
                    account: this.$user.loginAccount,
                    baseId: this.baseId,
                    list: this.data,
                }).then(response => {
                    if (response.code === 200) {
                        this.$Message.success('保存成功！')
                        this.$emit('next')
                    } else {
                        this.$Message.error('服务器异常！')
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            } else {
                // 如果为0则直接跳转下一步
                this.$emit('next')
            }
        },
        getList (e, index) {
            var arr = []
            e.forEach(element => {
                if(element.response){
                    arr.push(element.response.data.picName)
                }
            })
            this.data[index].pictureList = arr
        },
        revoke (item, index) {
            this.$Modal.confirm({
                title: '操作提示',
                content: '是否确认撤销该物联设备？',
                onOk:()=>{
                    console.log('item', item)
                    this.$api.get(`/member-reversion/productionBase/revokeBaseIotDeviceInfo?id=${item.id}`).then(response => {
                        if (response.code === 200) {
                            this.$Message.success('撤销成功！')
                            this.initDeviceInfo()
                        } else {
                            this.$Message.error('服务器异常！')
                        }
                    }).catch(error => {
                        this.$Message.error('服务器异常！')
                    })
                },
                okText:'确定',
                cancelText:'取消'
            })
        },
        del (item, index) {
            this.$Modal.confirm({
                title: '操作提示',
                content: '是否确认删除该物联设备？',
                onOk:()=>{
                    console.log('item', item)
                    this.$api.post('/member-reversion/productionBase/deleteBaseIotDeviceInfo', {
                        id: item.id,
                        iotId: item.iotId
                    }).then(response => {
                        if (response.code === 200) {
                            this.$Message.success('删除成功！')
                            this.initDeviceInfo()
                            this.initDeviceInfoTable()
                        } else {
                            this.$Message.error('服务器异常！')
                        }
                    }).catch(error => {
                        this.$Message.error('服务器异常！')
                    })
                },
                okText:'确定',
                cancelText:'取消'
            })
        },
        // 地区控件选择后的返回值
        handleGetData (value, selectedData) {
            let labelArr = []
            selectedData.forEach(element => {
                labelArr.push(element.label)
            })
            this.data[this.index].location = labelArr.join('/')
        },
        // 点击定位获取
        onSelectPoint (index) {
            this.index = index
            this.$refs.experMap.showMap = true
        },
        // 取坐标
        onGetPoint (point) {
            if(point.lng !== '' && point.lng !== undefined && point.lat !== '' && point.lat !== undefined) {
                this.data[this.index].longitude = point.lng
                this.data[this.index].latitude = point.lat
                /* this.$nextTick(() => {
                    this.changePreview()
                }) */
                this.data[this.index].locationAddr = `${point.lat},${point.lng}`
            } else {
                this.data[this.index].locationAddr = ''
                this.data[this.index].longitude = ''
                this.data[this.index].latitude = ''
            }
        },
        // 点击地区控件时保存index值
        getIndex (index) {
            this.index = index
        },
        tableChange () {},
        chooseInfo () {
            let idArr = []
            this.data.forEach(ele => {
                idArr.push(ele.iotId)
            })
            // 将已选择过的物联设施置灰不可选
            let data = this.tableData.concat()
            this.tableData = []
            for (let i = 0; i < data.length; i++) {
                // 如果存在则置灰不可选择
                if (idArr.indexOf(data[i].iotId) !== -1) {
                    data[i]._checked = true
                    data[i]._disabled = true
                } else {
                    data[i]._checked = false
                    data[i]._disabled = false
                }
                this.tableData.push(data[i])
            }
            console.log('dataaaa', this.tableData)
            this.modal = true
        },
        addInfo () {
            // 增加
            let no = ''
            if (this.data.length === 0) {
                no = '01'
            } else {
                if ((parseInt(this.data[this.data.length - 1].no) + 1) > 9) {
                    no = parseInt(this.data[this.data.length - 1].no) + 1
                } else {
                    no = `0${(parseInt(this.data[this.data.length - 1].no) + 1)}`
                }
            }
            this.data.push({
                id: '',
                iotId: '',
                commonName: '',
                name: '',
                no: no,
                capacity: '',
                investment: '',
                contact: '',
                address: '',
                location: '',
                group: '',
                number: '',
                description: '',
                longitude: '',
                latitude: '',
                locationAddr: '',
                pictureList: [],
                status: true
            })
            this.index = this.data.length - 1
        },
        cancel () {
            this.modal = false
        },
        ok () {
            if (this.choosedData.length === 0) {
                this.$Message.info('请先选择联系人！')
                return
            }
            if (this.data.length !== 0) {
                // 利用 iotId 去重
                let idArr = []
                this.data.forEach(ele => {
                    idArr.push(ele.iotId)
                })
                for (let i = 0; i < this.choosedData.length; i++) {
                    if (idArr.indexOf(this.choosedData[i].iotId) === -1) {
                        let element = this.choosedData[i]
                        this.data.push({
                            iotId: element.iotId,
                            commonName: element.commonName,
                            name: element.name,
                            no: element.no,
                            capacity: element.capacity,
                            investment: element.investment,
                            contact: element.contact,
                            address: element.address,
                            location: element.location,
                            group: element.group,
                            number: element.number,
                            description: element.description,
                            longitude: element.longitude,
                            latitude: element.latitude,
                            locationAddr: element.locationAddr,
                            pictureList: element.pictureList.split(';'),
                            status: true
                        })
                        this.$nextTick(() => {
                            this.$refs[`picture${i}`][0].handleGive(element.pictureList.split(';'))
                        })
                    }
                }
            } else {
                this.choosedData.forEach((element, i) => {
                    this.data.push({
                        iotId: element.iotId,
                        commonName: element.commonName,
                        name: element.name,
                        no: element.no,
                        capacity: element.capacity,
                        investment: element.investment,
                        contact: element.contact,
                        address: element.address,
                        location: element.location,
                        group: element.group,
                        number: element.number,
                        description: element.description,
                        longitude: element.longitude,
                        latitude: element.latitude,
                        locationAddr: element.locationAddr,
                        pictureList: element.pictureList.split(';'),
                        status: true
                    })
                    this.$nextTick(() => {
                        this.$refs[`picture${i}`][0].handleGive(element.pictureList.split(';'))
                    })
                })
            }
            this.modal = false
        },
        tableChange (value) {
            this.choosedData = value
            console.log('choosedData', this.choosedData)
        },
    }
}
</script>
<style lang="scss" scoped>

</style>
