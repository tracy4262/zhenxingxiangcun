<!--
    作者：chenqim
    时间：2019-01-07
    描述：生产基地基础信息编辑页
-->
<template>
    <div class="pd20" style="min-height: 500px;">
        <Title title="基础信息"></Title>
        <Form ref="form" :model="model" :rules="rule" :label-width="100" class="mt40">
            <Row>
                <Col span="12">
                    <FormItem label="基地名称" prop="productionBaseName">
                        <Input v-model="model.productionBaseName" :maxlength="30" style="width: 300px;" />
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="选择地块" prop="land">
                        <Select v-model="model.land" placeholder="请选择地块" style="width: 300px;" disabled>
                            <Option v-for="(it, index) in landList" :key="index" :value="it.land">{{ it.land }}</Option>
                        </Select>
                    </FormItem>
                </Col>
            </Row>
            <Row>
                <Col span="12">
                    <FormItem label="所处位置">
                        <Input v-model="model.location" style="width: 300px;" />
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="基地中心点坐标">
                        <Input v-model="model.coordinate" style="width: 300px;" disabled />
                    </FormItem>
                </Col>
            </Row>
            <Row>
                <Col span="12">
                    <FormItem label="基地简介">
                        <Input type="textarea" v-model="model.introduction" :maxlength="30" style="width: 300px;" />
                    </FormItem>
                </Col>
            </Row>
            <Row>
                <Col span="12">
                    <FormItem label="联系人信息" prop="contactInfo">
                        <Button type="success" ghost @click="chooseInfo" class="btn-light-primary">从基础设置中选择</Button>
                        <Button type="success" ghost @click="addInfo" class="btn-light-primary">添加</Button>
                    </FormItem>
                </Col>
            </Row>
        </Form>
        <!-- 选择联系人弹框 -->
        <Modal v-model="modal" title="选择联系人" width="1000" :closable="false">
            <Table border :columns="tableColumns" :data="tableData" @on-selection-change="tableChange"></Table>
            <div slot="footer">
                <Button type="text" @click="cancel">取消</Button>
                <Button type="primary" @click="ok">确定</Button>
            </div>
        </Modal>
        <!-- 联系人信息表单 -->
        <div class="certification pd20 mb20 pt30" v-for="(item, index) in data">
            <Form :label-width="80" label-position="left" :ref="`data${index}`" :model="item" :rules="dataLine">
                <Row :gutter="38">
                    <Col span="8" offset="16" class="tr mb10">
                        <span class="auth-btn-toolbar"  @click="delInfo(item, index)">删除</span>
                    </Col>
                </Row>
                <Row :gutter="38" type="flex" align="top">
                    <Col span="8">
                        <FormItem label="会员全称" prop="member_name">
                            <Input v-model="item.member_name"  :maxlength="20"></Input>
                        </FormItem>
                    </Col>
                    <Col span="4">
                        <Switch class="ml20 mt5" size="large" v-model="item.member_name_status" >
                            <span slot="open">公开</span>
                            <span slot="close">隐藏</span>
                        </Switch>
                    </Col>
                    <Col span="8">
                        <FormItem label="名称简写" prop="member_abbreviation">
                            <Input v-model="item.member_abbreviation"  :maxlength="20"></Input>
                        </FormItem>
                    </Col>
                    <Col span="4">
                        <Switch class="ml20 mt5" size="large" v-model="item.member_abbreviation_status" >
                            <span slot="open">公开</span>
                            <span slot="close">隐藏</span>
                        </Switch>
                    </Col>
                </Row>
                <Row :gutter="38">
                    <Col span="8">
                        <FormItem label="联系人姓名" prop="contact_name">
                            <Input v-model="item.contact_name"  :maxlength="20"></Input>
                        </FormItem>
                    </Col>
                    <Col span="4">
                        <Switch class="ml20 mt5" size="large" v-model="item.contact_name_status" >
                            <span slot="open">公开</span>
                            <span slot="close">隐藏</span>
                        </Switch>
                    </Col>
                    <Col span="8">
                        <FormItem label="身份证号" prop="card">
                            <Input v-model="item.card" :maxlength="18" ></Input>
                        </FormItem>
                    </Col>
                    <Col span="4">
                        <Switch class="ml20 mt5" size="large" v-model="item.card_status" >
                            <span slot="open">公开</span>
                            <span slot="close">隐藏</span>
                        </Switch>
                    </Col>
                </Row>
                <Row :gutter="38">
                    <Col span="8">
                        <FormItem label="座机电话" prop="seat_phone">
                            <Input v-model="item.seat_phone" :maxlength="13" ></Input>
                        </FormItem>
                    </Col>
                    <Col span="4">
                        <Switch class="ml20 mt5" size="large" v-model="item.seat_phone_status" >
                            <span slot="open">公开</span>
                            <span slot="close">隐藏</span>
                        </Switch>
                    </Col>
                    <Col span="8">
                        <FormItem label="手机号码" prop="phone">
                            <Input v-model="item.phone" :maxlength="11" ></Input>
                        </FormItem>
                    </Col>
                    <Col span="4">
                        <Switch class="ml20 mt5" size="large" v-model="item.phone_status" >
                            <span slot="open">公开</span>
                            <span slot="close">隐藏</span>
                        </Switch>
                    </Col>
                </Row>
                <Row :gutter="38">
                    <Col span="8">
                        <FormItem label="QQ号码" prop="qq_number">
                            <Input v-model="item.qq_number" :maxlength="11" ></Input>
                        </FormItem>
                    </Col>
                    <Col span="4">
                        <Switch class="ml20 mt5" size="large" v-model="item.qq_number_status" >
                            <span slot="open">公开</span>
                            <span slot="close">隐藏</span>
                        </Switch>
                    </Col>
                    <Col span="8">
                        <FormItem label="微信账号" prop="wechat_number">
                            <Input v-model="item.wechat_number"  :maxlength="20"></Input>
                        </FormItem>
                    </Col>
                    <Col span="4">
                        <Switch class="ml20 mt5" size="large" v-model="item.wechat_number_status" >
                            <span slot="open">公开</span>
                            <span slot="close">隐藏</span>
                        </Switch>
                    </Col>
                </Row>
                <Row :gutter="38">
                    <Col span="8">
                        <FormItem label="邮箱" prop="email">
                            <Input v-model="item.email" ></Input>
                        </FormItem>
                    </Col>
                    <Col span="4">
                        <Switch class="ml20 mt5" size="large" v-model="item.email_status" >
                            <span slot="open">公开</span>
                            <span slot="close">隐藏</span>
                        </Switch>
                    </Col>
                    <Col span="8">
                        <FormItem label="网站地址" prop="website_url">
                            <Input v-model="item.website_url"  :maxlength="200"></Input>
                        </FormItem>
                    </Col>
                    <Col span="4">
                        <Switch class="ml20 mt5" size="large" v-model="item.website_url_status" >
                            <span slot="open">公开</span>
                            <span slot="close">隐藏</span>
                        </Switch>
                    </Col>
                </Row>
                <Row :gutter="38">
                    <Col span="8">
                        <FormItem label="邮政编码" prop="postal_code">
                            <Input v-model="item.postal_code" :maxlength="6" ></Input>
                        </FormItem>
                    </Col>
                    <Col span="4">
                        <Switch class="ml20 mt5" size="large" v-model="item.postal_code_status" >
                            <span slot="open">公开</span>
                            <span slot="close">隐藏</span>
                        </Switch>
                    </Col>
                </Row>
                <FormItem label="个人照片" prop="image">
                    <Row>
                        <Col span="20">
                            <vui-upload
                                :ref="`upload${index}`"
                                @on-getPictureList="getPictureList($event, index)"
                                :total="1"
                                :multiple="false"
                                :hint="'支持拓展名称：png jpg'"
                                :size="[80,80]"
                                ></vui-upload>
                        </Col>
                        <Col span="4">
                            <Switch class="ml20"  size="large" v-model="item.image_status" >
                                <span slot="open">公开</span>
                                <span slot="close">隐藏</span>
                            </Switch>
                        </Col>
                    </Row>
                </FormItem>
                <FormItem label="所在位置" prop="location">
                    <Row>
                        <Col span="20"  @click.native="locationClick(index)">
                            <vui-cascander :values="item.location" @handle-get-result="handleGetData" ></vui-cascander>
                        </Col>
                        <Col span="4">
                            <Switch class="ml20"  size="large" v-model="item.location_status" >
                                <span slot="open">公开</span>
                                <span slot="close">隐藏</span>
                            </Switch>
                        </Col>
                    </Row>
                </FormItem>
                <Row :gutter="38">
                    <Col span="13" >
                        <FormItem label="详细地址" prop="address">
                            <Input v-model="item.address"  placeholder="请输入所在街道/湾组/楼栋"></Input>
                        </FormItem>
                    </Col>
                    <Col span="11" style="overflow: hidden;">
                        <FormItem  prop="house_number" style="margin-left: -70px;">
                            <Row :gutter="0">
                                <Col span="16" class="pl10" style="margin-left:-10px;">
                                    <Input v-model="item.house_number"  placeholder="请输入详细门牌号"></Input>
                                </Col>
                                <Col span="5">
                                    <Switch class="ml20" size="large" v-model="item.address_status" >
                                        <span slot="open">公开</span>
                                        <span slot="close">隐藏</span>
                                    </Switch>
                                </Col>
                            </Row>
                        </FormItem>
                    </Col>
                </Row>
                <Row :gutter="38">
                    <Col span="8">
                        <FormItem label="东经" prop="longitude">
                            <Input v-model="item.longitude" readonly >></Input>
                        </FormItem>
                    </Col>
                    <Col span="8">
                        <FormItem label="北纬" prop="latitude">
                            <Input v-model="item.latitude" readonly ></Input>
                        </FormItem>
                    </Col>
                    <Col span="8" style="text-decoration: underline; color: #6C6C6C; cursor: pointer;" class="pt5">
                        <span @click="onSelectPoint(index)">定位获取</span>
                        <Switch class="ml20" size="large" v-model="item.lng_lat_status" >
                            <span slot="open">公开</span>
                            <span slot="close">隐藏</span>
                        </Switch>
                    </Col>
                </Row>
                <div class="pb30 pt10" v-if="item.latitude">
                    <a target="_blank" :href="`http://api.map.baidu.com/marker?location=${item.latitude},${item.longitude}&title=我的位置&content=${item.latitude},${item.longitude}&output=html`">
                        <img :src="`//api.map.baidu.com/staticimage?height=200&center=${item.longitude},${item.latitude}&zoom=15&scale=2&markers=${item.longitude},${item.latitude}`" alt="" width="100%" height="500px">
                    </a>
                </div>
            </Form>
        </div>
        <vui-map ref="experMap" @on-get-point="onGetPoint"></vui-map>
        <div class="tc mt40">
            <Button type="default" @click="quit" style="width: 105px;">退出</Button>
            <Button type="primary" @click="next" style="width: 105px;" class="ml10">保存并下一步</Button>
        </div>
    </div>
</template>
<script>
import Title from './title2'
import {isTelephone, isPhone2, isQQ, isWeChat2, isEmail2, isUrl, isPostCode, isIdCard2} from '~utils/validate'
import vuiCascander from '~components/vuiCascader/index'
import vuiMap from '../../../member/components/productionMap'
import vuiUpload from '~components/vui-upload'
export default {
    name: 'baseInfo',
    components: {
        Title,
        vuiCascander,
        vuiMap,
        vuiUpload
    },
    data () {
        return {
            activeIndex: 0,
            modal: false,
            tableColumns: [
                {
                    type: 'selection',
                    width: 60
                },
                {
                    title: '联系人姓名',
                    key: 'contact_name'
                },
                {
                    title: '身份证号码',
                    key: 'card'
                },
                {
                    title: '座机电话',
                    key: 'seat_phone'
                },
                {
                    title: '手机',
                    key: 'phone'
                },
                {
                    title: '邮箱',
                    key: 'email'
                }
            ],
            tableData: [],
            model: {
                productionBaseName: '',
                land: '',
                location: '',
                coordinate: '',
                introduction: '',
                contactInfo: []
            },
            rule: {
                productionBaseName: [
                    { required: true, message: '请填写基地名称', trigger: 'blur' }
                ],
                land: [
                    { required: true, message: '请选择地块', trigger: 'change' }
                ],
                contactInfo: [
                    { required: true, message: '请选择联系人信息', type: 'array', min: 1, trigger: 'change' }
                ]
            },
            data: [],
            choosedData: [],
            dataLine: {
                member_name: [
                    { required: true, message: '请填选择会员全称', trigger: 'change' }
                ],
                member_abbreviation: [
                    { required: true, message: '请填选择名称简写', trigger: 'change' }
                ],
                contact_name: [
                    { required: true, message: '请填写联系人姓名', trigger: 'blur' }
                ],
                card: [
                    {required: true, message: '请填写身份证号', trigger: 'blur'},
                    {validator: isIdCard2}
                ],
                seat_phone: [
                    {required: true, message: '请填写座机电话', trigger: 'blur'},
                    {validator: isTelephone},
                ],
                phone: [
                    {required: true, message: '请填写手机号码', trigger: 'blur'},
                    {validator: isPhone2},
                ],
                qq_number: [
                    {required: true, message: '请填写QQ号码', trigger: 'blur'},
                    {validator: isQQ},
                ],
                wechat_number: [
                    {required: true, message: '请填写微信账号', trigger: 'blur'},
                    {validator: isWeChat2},
                ],
                email:[
                    {required: true, message: '请填写邮箱', trigger: 'blur'},
                    {validator: isEmail2},
                ],
                website_url:[
                    /* {required: true, message: '请填写网站地址', trigger: 'blur'}, */
                    {validator: isUrl},
                ],
                postal_code:[
                    {required: true, message: '请填写邮政编码', trigger: 'blur'},
                    {validator: isPostCode},
                ],
                location:[
                    {required: true, message: '请选择所在位置', trigger: 'change'}
                ],
                address:[
                    {required: true, message: '请填写详细地址', trigger: 'blur'}
                ],
                house_number:[
                    {required: true, message: '请填写详细门牌号', trigger: 'blur'}
                ],
                image: [
                    { required: true, type: 'array', min: 1, message: '请上传个人照片', trigger: 'change' }
                ],
                longitude:[
                    {required: true, message: '请选获取定位'}
                ],
                latitude:[
                    {required: true, message: '请选获取定位'}
                ]
            },
            landList: [],
            baseId: ''
        }
    },
    created () {
        this.baseId = this.$route.query.id
        this.initLandList()
        this.initBaseInfo()
        this.initContactList()
    },
    methods: {
        // 初始化联系人表格
        initContactList () {
            this.$api.post('/member-reversion/productionBase/contactList', {
                account: this.$user.loginAccount,
                baseId: this.baseId
            }).then(response => {
                if (response.code === 200) {
                    let arr = response.data
                    arr.forEach(element => {
                        element._checked = element.is_choose === 1 ? true : false
                        element._disabled = element.is_choose === 1 ? true : false
                    })
                    this.tableData = arr
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        // 初始化地块信息下拉框列表
        initLandList () {
            this.$api.post('/member-reversion/productionBase/landInfo', {
                account: this.$user.loginAccount
            }).then(response => {
                if (response.code === 200) {
                    this.landList = response.data
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        initBaseInfo () {
            this.$api.post('/member-reversion/productionBase/findBaseInfo', {
                account: this.$user.loginAccount,
                id: this.$route.query.id
            }).then(response => {
                if (response.code === 200) {
                    this.model = response.data.baseInfo
                    if (response.data.contactInfo.length > 0) {
                        let arr = response.data.contactInfo
                        arr.forEach((element, i) => {
                            this.$nextTick(() => {
                                this.$refs[`upload${i}`][0].handleGive(element.image)
                            })
                        })
                        this.data = arr
                    }
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
        next () {
            if (this.data.length === 0) {
                this.$Message.info('请先选择联系人！')
                return
            }
            this.model.contactInfo = this.data
            this.$refs['form'].validate((valid) => {
                console.log('valid', valid)
                if (valid) {
                    let flag = false
                    for (let index = 0; index < this.data.length; index++) {
                        console.log('refs', this.$refs[`data${index}`])
                        this.$refs[`data${index}`][0].validate((val) => {
                            if (!val) {
                                flag = true
                            }
                        })
                    }
                    if (!flag) {
                        this.$api.post('/member-reversion/productionBase/saveBaseInfo', {
                            account: this.$user.loginAccount,
                            baseInfo: this.model,
                            contactInfo: this.data
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
                        this.$Message.error('请核对表单字段！')
                    }
                } else {
                    this.$Message.error('请核对表单字段！')
                }
            })
        },
        // 点击定位获取
        onSelectPoint (index) {
            this.activeIndex = index
			this.$refs.experMap.showMap = true
		},
        // 取坐标
        onGetPoint(point) {
            if (point.lng !== '' && point.lng !== undefined && point.lat !== '' && point.lat !== undefined) {
                this.data[this.activeIndex].longitude = point.lng
                this.data[this.activeIndex].latitude = point.lat
            } else {
                this.data[this.activeIndex].longitude = ''
                this.data[this.activeIndex].latitude = ''
            }
        },
        locationClick (index) {
            this.activeIndex = index
        },
        // 所在位置
        handleGetData ($event, selectedData) {
            console.log('data', selectedData)
            let labelArr = []
            let idArr = []
            selectedData.forEach(element => {
                labelArr.push(element.label)
                idArr.push(element.value)
            })
            this.data[this.activeIndex].location = labelArr.join('/')
            this.data[this.activeIndex].locationId = idArr.join('/')
        },
        // 上传资质照片
        getPictureList ($event, e) {
            var arr = []
            $event.forEach(element => {
                if(element.response){
                    arr.push(element.response.data.picName)
                }
            })
            console.log(arr)
            this.data[e].image = arr
            this.$refs[`data${e}`][0].validate()
        },
        chooseInfo () {
            let idArr = []
            this.data.forEach(ele => {
                idArr.push(ele.member_contact_info_id)
            })
            // 将已选择过的联系人置灰不可选
            let data = this.tableData.concat()
            this.tableData = []
            for (let i = 0; i < data.length; i++) {
                // 如果存在则置灰不可选择
                if (idArr.indexOf(data[i].member_contact_info_id) !== -1) {
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
            let list = {
                member_contact_info_id: '', // 自己新增的为空
                account: this.$user.loginAccount,
                member_name: '',
                member_name_status: true,
                member_abbreviation: '',
                member_abbreviation_status: true,
                contact_name: '',
                contact_name_status: true,
                card: '',
                card_status: false,
                seat_phone: '',
                seat_phone_status: true,
                phone: '',
                phone_status: true,
                qq_number: '',
                qq_number_status: true,
                wechat_number: '',
                wechat_number_status: true,
                email: '',
                email_status: true,
                website_url: '',
                website_url_status: true,
                postal_code: '',
                postal_code_status: true,
                image: [],
                image_status: true,
                location: '',
                location_status: true,
                address: '',
                house_number: '',
                address_status: true,
                longitude: '',
                latitude: '',
                lng_lat_status: true
            }
            this.data.push(list)
        },
        cancel () {
            this.modal = false
        },
        ok () {
            if (this.choosedData.length === 0) {
                this.$Message.info('请先选择联系人！')
                return
            }
            // 选择联系人
            if (this.data.length !== 0) {
                // 利用 member_contact_info_id 去重
                let idArr = []
                this.data.forEach(ele => {
                    idArr.push(ele.member_contact_info_id)
                })
                for (let i = 0; i < this.choosedData.length; i++) {
                    if (idArr.indexOf(this.choosedData[i].member_contact_info_id) === -1) {
                        let element = this.choosedData[i]
                        this.data.push({
                            member_contact_info_id: element.member_contact_info_id,
                            account: this.$user.loginAccount,
                            member_name: element.member_name,
                            member_name_status: true,
                            member_abbreviation: element.member_abbreviation,
                            member_abbreviation_status: true,
                            contact_name: element.contact_name,
                            contact_name_status: true,
                            card: element.card,
                            card_status: false,
                            seat_phone: element.seat_phone,
                            seat_phone_status: true,
                            phone: element.phone,
                            phone_status: true,
                            qq_number: element.qq_number,
                            qq_number_status: true,
                            wechat_number: element.wechat_number,
                            wechat_number_status: true,
                            email: element.email,
                            email_status: true,
                            website_url: element.website_url,
                            website_url_status: true,
                            postal_code: element.postal_code,
                            postal_code_status: true,
                            image: element.image.split(','),
                            image_status: true,
                            location: element.location,
                            location_status: true,
                            address: element.address,
                            house_number: element.house_number,
                            address_status: true,
                            longitude: element.longitude,
                            latitude: element.latitude,
                            lng_lat_status: true
                        })
                        this.$nextTick(() => {
                            this.$refs[`upload${i}`][0].handleGive(element.image)
                        })
                    }
                }
            } else {
                this.choosedData.forEach((element, i) => {
                    this.data.push({
                        member_contact_info_id: element.member_contact_info_id,
                        account: this.$user.loginAccount,
                        member_name: element.member_name,
                        member_name_status: true,
                        member_abbreviation: element.member_abbreviation,
                        member_abbreviation_status: true,
                        contact_name: element.contact_name,
                        contact_name_status: true,
                        card: element.card,
                        card_status: false,
                        seat_phone: element.seat_phone,
                        seat_phone_status: true,
                        phone: element.phone,
                        phone_status: true,
                        qq_number: element.qq_number,
                        qq_number_status: true,
                        wechat_number: element.wechat_number,
                        wechat_number_status: true,
                        email: element.email,
                        email_status: true,
                        website_url: element.website_url,
                        website_url_status: true,
                        postal_code: element.postal_code,
                        postal_code_status: true,
                        image: element.image.split(','),
                        image_status: true,
                        location: element.location,
                        location_status: true,
                        address: element.address,
                        house_number: element.house_number,
                        address_status: true,
                        longitude: element.longitude,
                        latitude: element.latitude,
                        lng_lat_status: true
                    })
                    this.$nextTick(() => {
                        this.$refs[`upload${i}`][0].handleGive(element.image)
                    })
                })
            }
            this.modal = false
        },
        // 点击删除联系人信息
        delInfo (item, index) {
            this.$Modal.confirm({
                title: '操作提示',
                content: '是否确认删除该联系人信息？',
                onOk:()=>{
                    this.data.splice(index, 1)
                    this.$Message.success('删除成功')
                },
                okText:'确定',
                cancelText:'取消'
            })
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
