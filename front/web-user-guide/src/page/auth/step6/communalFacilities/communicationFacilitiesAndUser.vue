<template>
  <div class="pd20">
    <Title :title="title" edit :id="modeId" :yearId="yearId" />
    <Card v-for="(item,index) in data" class="mt40" :key="index">
        <Form :ref="`formItem${index}`" :model="item" label-position="left" :label-width="100" :rules="formItemInline">
            <Row>
                <Col span="12">
                    <Form-item label="权限">
                        <i-switch  v-model="item.status" size="large" :disabled="!item.isAdd">
                            <span slot="open">公开</span>
                            <span slot="close">隐藏</span>
                        </i-switch>
                    </Form-item>
                </Col>
                <!-- <Col span="12" class="tr">
                    <div class="btn-toolbar" style="margin-top: -10px;">
                        <Button type="text" @click="edit(index)" v-if="!item.isAdd"><Icon type="trash-a" size="16" class="pr5"></Icon> 编辑</Button>
                        <Button type="text" @click="del(item, index)"><Icon type="trash-a" size="16" class="pr5"></Icon> 删除</Button>
                    </div>
                </Col> -->
            </Row>
            <Row :gutter="32">
                <Col span="8">
                    <Form-item label="通用名称">
                        <Select v-model="item.commonName" :disabled="!item.isAdd" @on-change="change">
                            <Option value="电话设施">电话设施</Option>
                            <Option value="电视设施">电视设施</Option>
                            <Option value="网络设施">网络设施</Option>
                            <Option value="自定义（其他设施）">自定义（其他设施）</Option>
                        </Select>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="编号">
                        <Input v-model="item.no" :disabled="!item.isAdd" readonly />
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="设施名称">
                        <Input v-model="item.name" :disabled="!item.isAdd" @on-change="change" />
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="16">
                    <Form-item label="能力参数">
                      <Row>
                        <Col span="14">
                          <Input v-model="item.capacity" :disabled="!item.isAdd">
                          </Input>
                        </Col>
                        <Col span="10" class="pl10">
                          <vuiUnit :value="item.unit" @on-get-data="onGetUnit($event, index)" :disabled="!item.isAdd"></vuiUnit>
                        </Col>
                      </Row>
                        <!-- <Input v-model="item.capacity" :disabled="!item.isAdd">
                            <Select v-model="item.unit" filterable slot="append" style="width: 200px" class="units"  placeholder="单位" @on-change="change" :disabled="!item.isAdd">
                                <Option v-for="(item, index) in units" :value="item.unit_name" :key="index">{{item.unit_name}}</Option>
                            </Select>
                        </Input> -->
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="投资额（万元）">
                        <Input v-model="item.investment" :disabled="!item.isAdd" @on-change="change" />
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="8">
                    <Form-item label="责任人">
                        <Input v-model="item.contact" :disabled="!item.isAdd" @on-change="change" />
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="用户数">
                        <Input v-model="item.userNumber" :disabled="!item.isAdd" @on-change="change" />
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="16" style="z-index: 1;">
                  <Form-item label="所处位置">
                    <vui-cascander :values="item.location" @handle-get-result="handleGetData" @click.native="getIndex(index)" :disabled="!item.isAdd">
                    </vui-cascander>
                  </Form-item>
                </Col>
                <Col span="8">
                  <Form-item style="margin-left: -100px;">
                    <Input v-model="item.group" :maxlength="5" :disabled="!item.isAdd" style="width: 60px; margin-right: 10px;" @on-change="change" />组
                    <Input v-model="item.number" :maxlength="5" :disabled="!item.isAdd" style="width: 60px; margin-right: 10px; margin-left: 10px;" @on-change="change" />号
                  </Form-item>
                </Col>
            </Row>
            <Row>
                <Col span="24">
                  <Form-item label="说明">
                      <Input v-model="item.description" type="textarea" :autosize="{minRows: 3,maxRows: 5}" :maxlength="200" :disabled="!item.isAdd" @on-change="change" />
                  </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="8">
                <FormItem label="东经">
                    <Input v-model="item.longitude" :disabled="!item.isAdd" readonly @on-change="change" />
                </FormItem>
                </Col>
                <Col span="8">
                <FormItem label="北纬">
                    <Input v-model="item.latitude" :disabled="!item.isAdd" readonly @on-change="change" />
                </FormItem>
                </Col>
                <Col span="8" style="text-decoration: underline; color: #6C6C6C; cursor: pointer;" class="pt5">
                    <span @click="onSelectPoint(index)" v-show="item.isAdd">定位获取</span>
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
                            v-show="item.isAdd"
                            :ref="`picture${index}`"
                            @on-getPictureList="getList($event, index)"
                            :hint="'图片大小小于2MB，支持后缀名png jpg'"
                            :total="10"
                            :size="[80,80]"
                        ></vui-upload>
                        <div v-show="!item.isAdd" v-for="(pic, index) in item.pictureList" :key="index">
                            <img :src="pic" style="width: 80px; height: 80px; float: left; margin-right: 10px;">
                        </div>
                    </Form-item>
                </Col>
            </Row>
        </Form>
        <!-- <div class="tc">
            <Button type="primary" v-if="item.isAdd" @click="save(item, index)">保存</Button>
        </div> -->
    </Card>
    <!-- <Form label-position="left" :label-width="150" class="pb20 mt40">
        <Row :gutter="32">
            <Col span="12">
                <Button type="success" ghost @click="handleAdd" icon="md-add" class="btn-light-primary">添加</Button>
            </Col>
        </Row>
    </Form> -->
    <Title title="文字预览" class="mt50"/>
    <div class="pd20 tc pt30">
        <Input v-model="preview" type="textarea" :autosize="{minRows: 3,maxRows: 5}" />
        <Button type="primary" v-if="isLoading" class="mt40">保存</Button>
        <Button type="primary" v-else @click="handleSave()" class="mt40">保存</Button>
    </div>
    <vui-map ref="experMap" @on-get-point="onGetPoint"></vui-map>
  </div>
</template>
<script>
    import vuiUpload from '~components/vui-upload'
    import Title from '../../components/title'
    import vuiMap from '../../../member/components/productionMap'
    import vuiCascander from '~components/vuiCascader/index'
    import vuiUnit from '~components/vui-unit'
    export default {
        components: {
            vuiUpload,
            Title,
            vuiMap,
            vuiCascander,
            vuiUnit
        },
        props: {
            modeId: {
                type: String
            },
            yearId: {
                type: String
            }
        },
        data () {
            return {
                title: '交通设施及用户信息',
                formItemInline: {},
                data: [{
                    commonName: '',
                    no: 1,
                    name: '',
                    capacity: '',
                    unit: '',
                    investment: '',
                    contact: '',
                    userNumber: '',
                    location: '',
                    group: '',
                    number: '',
                    description: '',
                    longitude: '',
                    latitude: '',
                    locationAddr: '',
                    pictureList: [],
                    status: true,
                    isAdd: false
                }],
                preview: '',
                index: 0,
                locationList: [],
                id: '',
                isLoading: true
            }
        },
        created () {
            if (this.modeId !== '' && this.modeId !== undefined) {
                this.init()
            }
        },
        watch: {
            modeId: {
                handler (newValue, oldValue) {
                    this.init()
                },
                deep: true
            }
        },
        methods: {
            // 初始化加载数据
            init (type = 0) {
                this.$api.post('/member-reversion/communalFacilities/findCommunicationFacilitiesInfo', {
                    account: this.$user.loginAccount,
                    templateId: this.$template.id,
                    yearId: this.yearId,
                    parentId: this.modeId
                }).then(response => {
                    if (response.code === 200) {
                        this.isLoading = false
                        if (response.data.communicationFacilitiesInfoName && response.data.communicationFacilitiesInfoName !== '') {
                            this.title = response.data.communicationFacilitiesInfoName
                        }
                        if (response.data.textPreview && response.data.textPreview.textPreview !== '') {
                            this.preview = response.data.textPreview.textPreview
                            this.id = response.data.textPreview.id
                        }
                        if (response.data.communicationFacilitiesInfo.length !== 0) {
                            this.data = []
                            response.data.communicationFacilitiesInfo.forEach((element, index) => {
                                this.data.push({
                                    id: element.id,
                                    commonName: element.commonName,
                                    no: element.no,
                                    name: element.name,
                                    capacity: element.capacity,
                                    unit: element.unit,
                                    investment: element.investment,
                                    contact: element.contact,
                                    userNumber: element.userNumber,
                                    location: element.location,
                                    group: element.group,
                                    number: element.number,
                                    description: element.description,
                                    longitude: element.longitude,
                                    latitude: element.latitude,
                                    pictureList: element.pictureList,
                                    status: element.status,
                                    isAdd: false
                                })
                                this.$nextTick(() => {
                                    this.$refs['picture' + index][0].handleGive(element.pictureList)
                                })
                            })
                        }
                        if (type === 1) {
                            this.change()
                        }
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            },
            // 增加
            handleAdd () {
                this.data.push({
                    commonName: '',
                    no: this.data.length === 0 ? 1 : parseInt(this.data[this.data.length - 1].no) + 1,
                    name: '',
                    capacity: '',
                    unit: '',
                    investment: '',
                    isFree: '',
                    price: '',
                    contact: '',
                    userNumber: '',
                    location: '',
                    locationDetail: '',
                    description: '',
                    longitude: '',
                    latitude: '',
                    locationAddr: '',
                    pictureList: [],
                    status: true,
                    isAdd: true
                })
                this.index = this.data.length - 1
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
            edit (index) {
                this.data[index].isAdd = true
                this.index = index
            },
            del (item, index) {
                this.$Modal.confirm({
                    title: '操作提示',
                    content: '是否确认删除？',
                    onOk:()=>{
                        if (item.id !== undefined && item.id !== '') {
                            this.$api.post('/member-reversion/communalFacilities/deleteCommunicationFacilitiesInfo', {
                                id: item.id
                            }).then(response => {
                                if (response.code === 200) {
                                    this.$Message.success('删除成功！')
                                    this.data = []
                                    this.init(1)
                                }
                            }).catch(error => {
                                this.$Message.error('服务器异常！')
                            })
                        } else {
                            this.data.splice(index, 1)
                            this.change()
                        }
                    },
                    okText:'确定',
                    cancelText:'取消'
                })
            },
            // 保存单个风景名胜设施信息
            save (item, index) {
                let data = {
                    account: this.$user.loginAccount,
                    yearId: this.yearId,
                    parentId: this.modeId,
                    communicationFacilitiesInfoName: this.title,
                    communicationFacilitiesInfo: {
                        commonName: item.commonName,
                        no: item.no,
                        name: item.name,
                        capacity: item.capacity,
                        unit: item.unit,
                        investment: item.investment,
                        contact: item.contact,
                        userNumber: item.userNumber,
                        location: item.location,
                        group: item.group,
                        number: item.number,
                        description: item.description,
                        longitude: item.longitude,
                        latitude: item.latitude,
                        pictureList: item.pictureList,
                        status: item.status,
                        id: item.id === undefined || item.id === '' ? 0 : item.id
                    }
                }
                this.$api.post('/member-reversion/communalFacilities/saveCommunicationFacilitiesInfo', data).then(response => {
                    if (response.code === 200) {
                        this.$Message.success('保存成功！')
                        this.init(1)
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            },
            // 保存预览信息
            handleSave () {
                let data = {
                    textPreview: {
                        account: this.$user.loginAccount,
                        templateId: this.$template.id,
                        yearId: this.yearId,
                        parentId: this.modeId,
                        id: this.id === '' || this.id === undefined ? 0 : this.id,
                        textPreview: this.preview,
                        isComplete: this.data.length === 0 ? false : true
                    }

                }
                this.isLoading = true
                this.$api.post('/member-reversion/communalFacilities/saveTextPreview', data).then(response => {
                    if (response.code === 200) {
                        this.$Message.success('保存成功！')
                        this.init()
                        this.$emit('on-save')
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            },
            // 地区控件选择后的返回值
            handleGetData (value, selectedData) {
				let labelArr = []
				selectedData.forEach(element => {
					labelArr.push(element.label)
				})
                this.data[this.index].location = labelArr.join('/')
                this.change()
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
            // 取单位数据
            onGetUnit ($event, index) {
                this.data[index].unit = $event
            },
            // 拼接文字预览
            change () {
                this.preview = ''
                this.preview += `通信设施有`
                let phoneNum = 0
                let tvNum = 0
                let networkNum = 0
                let otherNum = 0
                this.data.forEach(element => {
                    if (element.name && element.name !== '') {
                        this.preview += `${element.name}、`
                    }
                    if (element.commonName === '电话设施' && element.userNumber !== '') {
                        phoneNum += parseInt(element.userNumber)
                    } else if (element.commonName === '电视设施' && element.userNumber !== '') {
                        tvNum += parseInt(element.userNumber)
                    } else if (element.commonName === '网络设施' && element.userNumber !== '') {
                        networkNum += parseInt(element.userNumber)
                    } else if (element.commonName === '自定义（其他设施）' && element.userNumber !== '') {
                        otherNum += parseInt(element.userNumber)
                    }
                })
                this.preview = this.preview.substring(0, this.preview.length - 1) + '，'
                this.preview += `其中电话用户${phoneNum}户，电视用户${tvNum}户，网络用户${networkNum}户，其他用户${otherNum}户。`
            }
        },
        mounted () {
            this.preview = `通用名称（），编号（），设施名称（），能力参数（），投资额（）万元，责任人（），用户数（），所处位置（），（）组，（）号，说明（），东经（），北纬（）。`
        }
    }
</script>
<style lang="scss" scoped>
</style>
