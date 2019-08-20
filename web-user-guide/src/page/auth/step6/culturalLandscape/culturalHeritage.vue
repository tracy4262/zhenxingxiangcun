<template>
  <div class="pd20">
    <Title :title="title" edit :id="modeId" :yearId="yearId"/>
    <Card v-for="(item,index) in data" :key="index" class="mt40">
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
                        <Button type="text" @click="del(item)"><Icon type="trash-a" size="16" class="pr5"></Icon> 删除</Button>
                    </div>
                </Col> -->
            </Row>
            <Row :gutter="32">
                <Col span="8">
                    <Form-item label="通用名称">
                        <Select v-model="item.commonName" :disabled="!item.isAdd">
                            <Option value="文化遗产">文化遗产</Option>
                            <Option value="自定义（其他景区）">自定义（其他景区）</Option>
                        </Select>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="编号">
                        <Input v-model="item.no" :disabled="!item.isAdd" readonly />
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="文化遗产名称">
                        <Input v-model="item.name" :disabled="!item.isAdd" />
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="16">
                    <Form-item label="接待能力">
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
                            <Select v-model="item.unit" filterable slot="append" style="width: 200px" class="units"  placeholder="单位" :disabled="!item.isAdd">
                                <Option v-for="(item, index) in units" :value="item.unit_name" :key="index">{{item.unit_name}}</Option>
                            </Select>
                        </Input> -->
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="投资额（万元）">
                        <Input v-model="item.investment" :disabled="!item.isAdd" />
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="8">
                    <Form-item label="是否免费">
                        <Select v-model="item.isFree" :disabled="!item.isAdd">
                            <Option value="是">是</Option>
                            <Option value="否">否</Option>
                        </Select>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="票价（元）">
                        <Input v-model="item.price" :disabled="!item.isAdd" />
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="联系人">
                        <Input v-model="item.contact" :disabled="!item.isAdd" />
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="12" style="z-index: 1;">
                  <Form-item label="所处位置">
                    <vui-cascander :values="item.location" @handle-get-result="handleGetData" @click.native="getIndex(index)" :disabled="!item.isAdd">
                    </vui-cascander>
                  </Form-item>
                </Col>
                <Col span="8">
                  <Form-item style="margin-left: -100px;">
                    <Input v-model="item.locationDetail" :maxlength="5" :disabled="!item.isAdd" style="width: 100px; margin-right: 10px;" />号
                  </Form-item>
                </Col>
            </Row>
            <Row>
                <Col span="24">
                  <Form-item label="介绍">
                      <Input v-model="item.description" type="textarea" :autosize="{minRows: 3,maxRows: 5}" :maxlength="200" :disabled="!item.isAdd" />
                  </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="8">
                <FormItem label="东经">
                    <Input v-model="item.longitude" :disabled="!item.isAdd" readonly />
                </FormItem>
                </Col>
                <Col span="8">
                <FormItem label="北纬">
                    <Input v-model="item.latitude" :disabled="!item.isAdd" readonly />
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
                            :disabled="!item.isAdd"
                            :ref="`picture${index}`"
                            @on-getPictureList="getList($event, index)"
                            :hint="'图片大小小于2MB，支持后缀名png jpg'"
                            :total="10"
                            :size="[80,80]"
                        ></vui-upload>
                        <!-- <div v-show="!item.isAdd" v-for="(pic, index) in item.pictureList" :key="index">
                            <img :src="pic" style="width: 80px; height: 80px; float: left; margin-right: 10px;">
                        </div> -->
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
                title: '文化遗产设施信息',
                formItemInline: {},
                data: [{
                    commonName: '',
                    no: 1,
                    name: '',
                    capacity: '',
                    unit: '',
                    investment: '',
                    isFree: '',
                    price: '',
                    contact: '',
                    location: '',
                    locationDetail: '',
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
                isLoading: true
            }
        },
        created () {
            // 切换下方子模块时调用的是这个方法
            if (this.modeId !== '' && this.modeId !== undefined) {
                this.init()
            }
        },
        // 切换上方父模块时调用的是这个方法
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
                this.$api.post('/member-reversion/cultureSight/findCulturalHeritage', {
                    account: this.$user.loginAccount,
                    templateId: this.$template.id,
                    yearId: this.yearId,
                    dictId: this.modeId
                }).then(response => {
                    if (response.code === 200) {
                        this.isLoading = false
                        if (response.data.propertyName && response.data.propertyName !== '') {
                            this.title = response.data.propertyName
                        }
                        if (response.data.preview && response.data.preview !== '') {
                            this.preview = response.data.preview
                        }
                        if (response.data.defaultData.length !== 0) {
                            this.data = []
                            response.data.defaultData.forEach((element, index) => {
                                this.data.push({
                                    id: element.id,
                                    commonName: element.currencyName,
                                    no: element.number,
                                    name: element.sightName,
                                    capacity: element.recCapacity,
                                    unit: element.unitName,
                                    investment: element.investment,
                                    isFree: element.isFree,
                                    price: element.ticketPrice,
                                    contact: element.contacts,
                                    location: element.location,
                                    locationDetail: element.locationNumber,
                                    description: element.introduce,
                                    longitude: element.longitude,
                                    latitude: element.latitude,
                                    pictureList: element.uploadImg,
                                    status: element.status === 1 ? true : false,
                                    isAdd: false
                                })
                                this.$nextTick(() => {
                                    this.$refs['picture' + index][0].handleGive(element.uploadImg)
                                })
                            })
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
            del (item) {
                this.$Modal.confirm({
                    title: '操作提示',
                    content: '是否确认删除？',
                    onOk:()=>{
                        if (item.id !== undefined && item.id !== '') {
                            this.$api.post('/member-reversion/cultureSight/deleteCultural', {
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
                    dictId: this.modeId,
                    isComplete: '1',
                    currencyName: item.commonName,
                    number: item.no,
                    sightName: item.name,
                    recCapacity: item.capacity,
                    unitName: item.unit,
                    investment: item.investment,
                    isFree: item.isFree,
                    ticketPrice: item.price,
                    contacts: item.contact,
                    location: item.location,
                    locationNumber: item.locationDetail,
                    introduce: item.description,
                    longitude: item.longitude,
                    latitude: item.latitude,
                    uploadImg: item.pictureList,
                    status: item.status,
                    id: item.id === '' || item.id === undefined ? '' : item.id
                }
                this.$api.post('/member-reversion/cultureSight/saveCultural', data).then(response => {
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
                    account: this.$user.loginAccount,
                    templateId: this.$template.id,
                    yearId: this.yearId,
                    dictId: this.modeId,
                    isComplete: '1',
                    textPreview: this.preview
                }
                this.isLoading = true
                this.$api.post('/member-reversion/cultureSight/saveTextPreview', data).then(response => {
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
            }
        },
        mounted() {
            this.preview = `通用名称（），编号（），文化遗产名称（），接待能力（），投资额（）万元，是否免费（），票价（）元，联系人（），所处位置（），门牌号（）号，介绍（），东经（），北纬（）。`
        }
    }
</script>
