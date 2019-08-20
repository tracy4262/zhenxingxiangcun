<!--
    作者：chenqim
    时间：2019-02-26
    描述：营业网点组件
-->
<template>
    <div>
        <!-- 营业网点信息表单 -->
        <div v-for="(item, index) in data" :key="index">
            <Form :ref="`form${index}`" :model="item" :label-width="90" label-position="left" style="background:#f9f9f9;" class="pd20 mb30" :rules="ruleInline">
                <Row :gutter="16">
                    <Col span="8" offset="16" class="tr">
                        <span class="auth-btn-toolbar" @click="handleDel(item, index)" style="cursor: pointer;">删除</span>
                    </Col>
                </Row>
                <Row :gutter="16">
                    <Col span="12">
                        <FormItem prop="networkName" label="网点名称">
                            <Input v-model="item.networkName" :maxlength="20" ></Input>       
                        </FormItem>
                    </Col>
                    <Col span="12">
                        <Form-item prop="networkType" label="网点类型">
                            <CheckboxGroup v-model="item.networkType" >
                                <Checkbox label="销售门店" ></Checkbox>
                                <Checkbox label="售后网点" ></Checkbox>
                            </CheckboxGroup>
                        </Form-item>
                    </Col>
                </Row>
                <Row :gutter="16">
                    <Col span="24">
                        <Form-item prop="location" label="网点所在地" @click.native="activeMode = index">
                            <vui-cascander :values="item.location" @handle-get-result="handleGetData" ></vui-cascander>
                        </Form-item>
                    </Col>
                </Row>
                <Row :gutter="16">
                    <Col span="24">
                        <Form-item label="详细地址">
                            <Row>
                                <Col span="16" class="pr10">
                                    <Input v-model="item.address" :maxlength="50"  placeholder="请输入所在街道/湾组/楼栋" @on-change="changePerfectAddress(item, index)"></Input>
                                </Col>
                                <Col span="8" class="pl10">
                                    <Input v-model="item.houseNumber" :maxlength="20"  placeholder="请输入详细门牌号"  @on-change="changePerfectAddress(item, index)"></Input>
                                </Col>
                            </Row>
                        </Form-item>
                    </Col>
                </Row>
                <Row :gutter="16">
                    <Col span="24">
                        <Form-item label="网点完整地址">
                            <Input type="textarea" :value="item.perfectAddress" :autosize="{minRows: 3,maxRows: 5}" readonly></Input>
                        </Form-item>
                    </Col>
                </Row>
                <Row :gutter="16">
                    <Col span="8">
                        <Form-item prop="contact" label="联系人">
                            <Input v-model="item.contact" :maxlength="20" ></Input>
                        </Form-item>
                    </Col>
                    <Col span="8">
                        <Form-item prop="officePhone" label="办公电话">
                            <Input v-model="item.officePhone" :maxlength="20" ></Input>
                        </Form-item>
                    </Col>
                    <Col span="8">
                        <Form-item prop="phone" label="手机号码">
                            <Input v-model="item.phone" :maxlength="20" ></Input>
                        </Form-item>
                    </Col>
                </Row>
                <Row :gutter="16">
                    <Col span="8">
                        <FormItem label="东经">
                            <Input v-model="item.longitude" readonly ></Input>
                        </FormItem>
                    </Col>
                    <Col span="8">
                        <FormItem label="北纬">
                            <Input v-model="item.latitude" readonly ></Input>
                        </FormItem>
                    </Col>
                    <Col span="8" style="text-decoration: underline; color: #6C6C6C; cursor: pointer;" class="pt5">
                        <span @click="onSelectPoint(item, index)">定位获取</span>
                    </Col>
                </Row>
                <div class="pb30 pt10" v-if="item.latitude">
                    <a target="_blank" :href="`http://api.map.baidu.com/marker?location=${item.latitude},${item.longitude}&title=我的位置&content=${item.longitude,item.latitude}&output=html`">
                    <img :src="`//api.map.baidu.com/staticimage/v2?ak=7syPirZ2AWxacMfHeAfuujdDgFmxCB5g&center=${item.longitude},${item.latitude}&zoom=15&markers=${item.longitude},${item.latitude}&width=800`" width="100%" />
                    </a>
                </div>
            </Form>
        </div>
        <!-- 选择营业网点弹窗 -->
        <Modal v-model="show" width="1000" title="选择服务网点" :mask-closable="false">
            <div>
                <Form :label-width="100">
                    <Row>
                        <Col span="14" class="tc">
                            <FormItem label="网点名称">
                                <Input v-model="keyWord"/>
                            </FormItem>
                        </Col>
                        <Col span="3" class="tc">
                            <Button @click="onSearch">查询</Button>
                        </Col>
                    </Row>
                </Form>
                <Table :columns="columns1" :data="tableData" class="mt20" @on-selection-change="onChane"></Table>
            </div>
            <div slot="footer">
                <Button @click="show = false">取消</Button>
                <Button type="primary" @click="onOk">确定</Button>
            </div>
        </Modal>
        <vui-map ref="experMap" @on-get-point="onGetPoint"></vui-map>
    </div>
</template>
<script>
import vuiCascander from '~components/vuiCascader/index'
import vuiMap from '../../../member/components/productionMap'
import {isPhone2, isTelephoneAndphone} from '~utils/validate'
export default {
    components: {
        vuiCascander,
        vuiMap
    },
    data () {
        return {
            data: [],
            ruleInline:{
                phone: [{validator: isPhone2, trigger: 'blur'}],
                officePhone: [{validator: isTelephoneAndphone, trigger: 'blur'}],
            },
            activeMode: 0,
            show: false,
            keyWord: '',
            tableData: [],
            datas: [],
            columns1: [
                {
                    type: 'selection',
                    width: 60,
                    align: 'center'
                },
                {
                    title: '网点名称',
                    key: 'networkName'
                },
                {
                    title: '网点地址',
                    key: 'perfectAddress'
                }
            ],
            selectData: [],
            id: ''
        }
    },
    created () {
        this.id = this.$route.query.id
        this.handleInit()
    },
    methods: {
        // 营业网点数据初始化
        handleInit () {
            this.$api.post('/member-reversion/consult/networkStationList', {
                account: this.$user.loginAccount,
                id: this.id
            }).then(response => {
                if (response.code === 200) {
                    let arr = response.data
                    arr.forEach(element => {
                        element._checked = element.is_choose === 1 ? true : false
                        element._disabled = element.is_choose === 1 ? true : false
                    })
                    this.tableData = arr.concat()
                    this.datas = arr.concat()
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        handleValidate () {
            let flag = true
            this.data.forEach((e, index) => {
                this.$refs[`form${index}`][0].validate(v => {
                    if (!v) {
                        flag = false
                    }
                })
            })
            return flag
        },
        handleDel (index) {
            this.$Modal.confirm({
                title: '操作提示',
                content: '是否确认删除？',
                onOk:()=>{
                    this.$Message.success('删除成功！')
                    this.data.splice(index, 1)
                },
                okText:'确定',
                cancelText:'取消'
            })
        },
        // 获取地址
        handleGetData (value, selectedData) {
            let labelArr = []
            selectedData.forEach(element => {
                labelArr.push(element.label)
            })
            this.data[this.activeMode].location = labelArr.join('/')
        },
        // 经纬度
        onSelectPoint (item, index) {
            this.activeMode = index
            this.$refs.experMap.showMap = true
        },
        // 取坐标
        onGetPoint (point) {
            if(point.lng !== '' && point.lng !== undefined && point.lat !== '' && point.lat !== undefined) {
                this.data[this.activeMode].longitude = point.lng
                this.data[this.activeMode].latitude = point.lat
            } else {
                this.data[this.activeMode].longitude = ''
                this.data[this.activeMode].latitude = ''
            }
        },
        // 查询
        onSearch () {
            this.tableData = this.datas.filter(e => {
                return e.networkName.indexOf(this.keyWord) > -1
            })
        },
        onChane (e) {
            this.selectData = e
        },
        // 点击确定
        onOk () {
            if (this.selectData.length === 0) {
                this.$Message.info('请先选择营业网点！')
                return
            }
            // 选择营业网点
            if (this.data.length !== 0) {
                // 利用 member_contact_info_id 去重
                let idArr = []
                this.data.forEach(ele => {
                    idArr.push(ele.outletsId)
                })
                for (let i = 0; i < this.selectData.length; i++) {
                    if (idArr.indexOf(this.selectData[i].outletsId) === -1) {
                        let element = this.selectData[i]
                        this.data.push({
                            outletsId: element.outletsId,
                            networkName: element.networkName, // 网点名字
                            networkType: element.networkType, // 网点类型
                            location: element.location, // 网点所在地
                            address: element.address, // 详细地址
                            houseNumber: element.houseNumber, // 门牌号
                            perfectAddress: element.perfectAddress, //网点完整地址
                            longitude: element.longitude, // 东经
                            latitude: element.latitude, // 北纬
                            contact: element.contact, // 联系人
                            officePhone: element.officePhone, // 办公电话
                            phone: element.phone, // 手机号码
                        })
                    }
                }
            } else {
                this.selectData.forEach((element, i) => {
                    this.data.push({
                        outletsId: element.outletsId,
                        networkName: element.networkName, // 网点名字
                        networkType: element.networkType, // 网点类型
                        location: element.location, // 网点所在地
                        address: element.address, // 详细地址
                        houseNumber: element.houseNumber, // 门牌号
                        perfectAddress: element.perfectAddress, //网点完整地址
                        longitude: element.longitude, // 东经
                        latitude: element.latitude, // 北纬
                        contact: element.contact, // 联系人
                        officePhone: element.officePhone, // 办公电话
                        phone: element.phone, // 手机号码
                    })
                })
            }
            this.show = false
        },
        changePerfectAddress (item, index) {
            let str = ''
            if (item.location) {
                str += `${item.location}，`
            }
            if (item.address) {
                str += `${item.address}，`
            }
            if (item.houseNumber) {
                str += `${item.houseNumber}，`
            }
            if (str) {
                str = `${str.substring(0,str.length-1)}。`
            }
            item.perfectAddress = str
        },
        chooseInfo () {
            let idArr = []
            this.data.forEach(ele => {
                idArr.push(ele.outletsId)
            })
            // 将已选择过的联系人置灰不可选
            let data = this.tableData.concat()
            this.tableData = []
            for (let i = 0; i < data.length; i++) {
                // 如果存在则置灰不可选择
                if (idArr.indexOf(data[i].outletsId) !== -1) {
                    data[i]._checked = true
                    data[i]._disabled = true
                } else {
                    data[i]._checked = false
                    data[i]._disabled = false
                }
                this.tableData.push(data[i])
            }
            console.log('dataaaa', this.tableData)
            this.show = true
        },
        addInfo () {
            this.data.push({
                outletsId: '',
                networkName: '', // 网点名字
                networkType: [], // 网点类型
                location: '', // 网点所在地
                address: '', // 详细地址
                houseNumber: '', // 门牌号
                perfectAddress: '', //网点完整地址
                longitude: '', // 东经
                latitude: '', // 北纬
                contact: '', // 联系人
                officePhone: '', // 办公电话
                phone: '' // 手机号码
            })
        }
    }
}
</script>
