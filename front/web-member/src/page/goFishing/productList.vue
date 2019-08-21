<template>
    <div class="pl15 pr15" style="min-height: 500px">
        <Row class="mt20">
            <Col span="24">
                <span>分类</span>
                <span v-for="(item,index) in typeList" :key="index" class="pl10 pr10">
                    <span @click="chooseType(item, index)" :class="{'farm-group-btn-active': index === activeType, 'farm-group-btn': true}">
                        {{ item.species_name }}
                    </span>
                </span>
            </Col>
        </Row>
        <Row class="mt20">
            <Col span="24">
                <span>状态</span>
                <span v-for="(item,index) in statusList" :key="index" class="pl10 pr10">
                    <span @click="chooseStatus(item, index)" :class="{'farm-group-btn-active': index === activeStatus, 'farm-group-btn': true}">
                        {{ item.statusName }}
                    </span>
                </span>
            </Col>
        </Row>
        <Row class="mt40">
            <Col span="12" class="tl"><span>产品列表</span></Col>
            <Col span="12" class="tr"><Button type="default" @click="addProduct" class="add-btn"><Icon type="plus"></Icon> 添加产品</Button></Col>
        </Row>
        <Row class="mt20">
            <Col span="24">
                <Table :columns="columns" :data="data"></Table>
            </Col>
        </Row>
        <Row class="mt20 mb20">
            <Col span="24">
                <Page :total="total" :current="currentPage" @on-change="handleGetNextPage" class="tr"></Page>
            </Col>
        </Row>
        <Modal v-model="modal" width="860" title="添加产品" :mask-closable="false" :styles="{top: '20px'}">
            <Form ref="productInfo" :model="productInfo" label-position="right" :label-width="100" :rules="ruleInline" class="mt20">
                <Form-item prop="productPicture" label="产品图片">
                    <vui-upload
                        ref="pic"
                        @on-getPictureList="getList"
                        :hint="'图片大小小于2MB'"
                        :total="1"
                        :size="[80,80]">
                    </vui-upload>
                </Form-item>
                <Row>
                    <Col span="12">
                        <Form-item prop="speciesName" label="物种名称">
                            <Select v-model="productInfo.speciesName" placeholder="请选择物种名称">
                                <Option v-for="(item, index) in speciesNameList" :key="index" :value="item.species_id">{{ item.species_name }}</Option>
                            </Select>
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="productName" label="产品名称">
                            <Input v-model="productInfo.productName" :maxlength="50" placeholder="请输入产品名称" />
                        </Form-item>
                    </Col>
                </Row>
                <Row>
                    <Col span="12">
                        <Form-item prop="productPrice" label="产品价格">
                            <Input v-model="productInfo.productPrice" @on-change="calProportion" :maxlength="10" placeholder="请输入产品价格" />
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="measurementUnit" label="计量单位">
                            <Input v-model="productInfo.measurementUnit" :maxlength="10" placeholder="请输入计量单位" />
                        </Form-item>
                    </Col>
                </Row>
                <Row>
                    <Col span="12">
                        <Form-item prop="discountPrice" label="折扣价格">
                            <Input v-model="productInfo.discountPrice" @on-change="calProportion" :maxlength="10" placeholder="请输入打折价" />
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="proportion" label="打折比例">
                            <Input v-model="productInfo.proportion" placeholder="自动生成打折比例" readonly />
                        </Form-item>
                    </Col>
                </Row>
                <Form-item prop="time" label="垂钓时间">
                    <Date-picker format="yyyy年MM月dd日" type="daterange" placement="bottom-end"
                        style="width: 100%;" v-model="productInfo.timeArr" placeholder="请输入垂钓时间"
                        @on-change="productInfo.time=$event"></Date-picker>
                </Form-item>
                <Row>
                    <Col span="12">
                        <Form-item prop="contact" label="联系姓名">
                            <Input v-model="productInfo.contact" :maxlength="10" placeholder="请输入联系人姓名" />
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="mobile" label="联系电话">
                            <Input v-model="productInfo.mobile" :maxlength="11" placeholder="请输入联系人电话" />
                        </Form-item>
                    </Col>
                </Row>
                <Form-item prop="address" label="垂钓地址">
                    <Input v-model="productInfo.address" :maxlength="50" placeholder="请输入详细地址" />
                </Form-item>
                <Row>
                    <Col span="12">
                        <Form-item prop="coordinate" label="经纬度">
                            <Input v-model="productInfo.coordinate" readonly @on-focus="onSelectPoint" placeholder="点击获取经纬度" />
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="fishingStatus" label="垂钓状态">
                            <RadioGroup v-model="productInfo.fishingStatus">
                                <Radio label="1">上架</Radio>
                                <Radio label="0">下架</Radio>
                            </RadioGroup>
                        </Form-item>
                    </Col>
                </Row>
                <Form-item prop="detail" label="图文详情">
                    <vuequilleditor ref="detail" :content="productInfo.detail" @quilCon="getCon" uploadId="detail" myQuillEditor="detail">
					</vuequilleditor>
                </Form-item>
            </Form>
            <div slot="footer">
                <Button type="text" @click="cancel">取消</Button>
                <Button type="primary" @click="ok">确定</Button>
            </div>
        </Modal>
        <vui-map ref="vuiMap" :transfer="true" @on-get-point="onGetPoint"></vui-map>
    </div>
</template>
<script>
import vuiUpload from '~components/vui-upload'
import vuiMap from '../member/components/productionMap'
import vuequilleditor from '~components/vuequilEditor'
import { isPhone2,isMoney,isMoney2 } from '~utils/validate'

export default {
    name: 'fishingProductList',
    components: {
        vuiUpload,
        vuiMap,
        vuequilleditor
    },
    data () {
        return {
            activeType: 0,
            activeStatus: 0,
            total: 0,
            currentPage: 1,
            pageSize: 10,
            modal: false,
            isAdd: true,
            updateId: -1,
            loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
            queryInfo: {
                speciesName: '',
                fishingStatus: ''
            },
            productInfo: {
                productPicture: [],
                speciesName: '',
                productName: '',
                productPrice: '',
                discountPrice: '',
                proportion: '',
                measurementUnit: '',
                time: [], // 用于传值保存
                timeArr: [], // 用于回显
                contact: '',
                mobile: '',
                address: '',
                coordinate: '',
                fishingStatus: '1',
                detail: ''
            },
            ruleInline: {
                productPicture: [
                    { required: true, message: '请上传产品图片', type: 'array', min: 1, trigger: 'change' }
                ],
                speciesName: [
                    { required: true, message: '请选择物种名称', trigger: 'change' }
                ],
                productName: [
                    { required: true, message: '请输入产品名称', trigger: 'blur' }
                ],
                productPrice: [
                    { required: true, validator: isMoney, trigger: 'blur' }
                ],
                discountPrice: [
                    { validator: isMoney2, trigger: 'blur' }
                ],
                measurementUnit: [
                    { required: true, message: '请输入计量单位', trigger: 'blur' }
                ],
                time: [
                    { required: true, type: 'array', min: 1, message: '请选择垂钓时间', trigger: 'change' }
                ],
                coordinate: [
                    { required: true, message: '请选择地理位置坐标', trigger: 'change' }
                ],
                address: [
                    { required: true, message: '请输入垂钓地址', trigger: 'blur' }
                ],
                mobile: [
                    { validator: isPhone2, trigger: 'blur' }
                ],
                fishingStatus: [
                    { required: true, message: '请选择垂钓状态', trigger: 'change' }
                ]
            },
            typeList: [
                {
                    species_id: '',
                    species_name: '全部分类'
                }
            ],
            statusList: [
                {
                    id: '',
                    statusName: '全部状态'
                },
                {
                    id: '1',
                    statusName: '营业中'
                },
                {
                    id: '0',
                    statusName: '休息中'
                }
            ],
            speciesNameList: [],
            columns: [
                {
                    title: '产品图片',
                    key: 'productPicture',
                    align: 'center',
                    width: 100,
                    render: (h, params) => {
                        return h('img', {
                            attrs: {
                                width: '58px',
                                height: '58px',
                                src: params.row.productPicture,
                                class: 'pd5'
                            }
                        })
                    }
                },
                {
                    title: '产品名称',
                    key: 'productName',
                    align: 'center'
                },
                {
                    title: '产品价格',
                    key: 'productPrice',
                    align: 'center'
                },
                {
                    title: '计量单位',
                    key: 'measurementUnit',
                    align: 'center',
                    width: 100
                },
                {
                    title: '产品状态',
                    key: 'productStatus',
                    align: 'center'
                },
                {
                    title: '操作',
                    key: 'action',
                    align: 'center',
                    width: 140,
                    render: (h, params) => {
                        return h('div', [
                            h('Button', {
                                props: {
                                    type: 'text',
                                    size: 'small'
                                },
                                style: {
                                    color: '#57A97B'
                                },
                                on: {
                                    click: () => {
                                        this.edit(params.row.id)
                                    }
                                }
                            }, '编辑'),
                            h('Button', {
                                props: {
                                    type: 'text',
                                    size: 'small'
                                },
                                style: {
                                    color: '#8C8C8C'
                                },
                                on: {
                                    click: () => {
                                        this.delete(params.row.id)
                                    }
                                }
                            }, '删除')
                        ]);
                    }
                }
            ],
            data: [],
            proportion: 1
        }
    },
    created () {
        this.initTypeList()
        this.initTable({
            species_id: '',
            status: '',
            account: this.loginUser.loginAccount,
            pageNum: 1,
            pageSize: this.pageSize,
            type: '0' // 0垂钓 1采摘
        })
    },
    methods: {
        // 初始化物种分类
        initTypeList () {
            this.$api.post('/member/fishing/getSpeciesInfo', {
                account: this.loginUser.loginAccount,
                type: '0'
            }).then(response => {
                console.log('res typelist', response)
                if (response.code === 200) {
                    response.data.forEach(element => {
                        this.typeList.push({
                            species_id: element.species_id,
                            species_name: element.species_name
                        })
                    })
                    this.speciesNameList = response.data
                }
            }).catch(error => {
                console.log(error)
            })
        },
        // 初始化表格
        initTable (data) {
            this.$api.post('/member/fishing/findFishingProduct', data).then(response => {
                console.log('res table', response)
                if (response.code === 200) {
                    response.data.list.forEach(element => {
                        this.data.push({
                            id: element.id,
                            productPicture: element.image_url,
                            productName: element.product_name,
                            productPrice: `￥ ${element.product_price}`,
                            measurementUnit: element.unit,
                            productStatus: element.status
                        })
                    })
                    this.total = response.data.total
                }
            }).catch(error => {
                console.log(error)
            })
        },
        chooseType (item, index) {
            this.activeType = index
            this.queryInfo.speciesName = item.species_id
            this.currentPage = 1
            this.data = []
            this.initTable({
                species_id: this.queryInfo.speciesName,
                status: this.queryInfo.fishingStatus,
                account: this.loginUser.loginAccount,
                pageNum: 1,
                pageSize: this.pageSize,
                type: '0' // 0垂钓 1采摘
            })
        },
        chooseStatus (item, index) {
            this.activeStatus = index
            this.queryInfo.fishingStatus = item.id
            this.currentPage = 1
            this.data = []
            this.initTable({
                species_id: this.queryInfo.speciesName,
                status: this.queryInfo.fishingStatus,
                account: this.loginUser.loginAccount,
                pageNum: 1,
                pageSize: this.pageSize,
                type: '0' // 0垂钓 1采摘
            })
        },
        addProduct () {
            this.$refs['productInfo'].resetFields()
            this.productInfo = {
                productPicture: [],
                speciesName: '',
                productName: '',
                productPrice: '',
                discountPrice: '',
                proportion: '',
                measurementUnit: '',
                time: [],
                timeArr: [],
                contact: '',
                mobile: '',
                address: '',
                coordinate: '',
                fishingStatus: '1',
                detail: ''
            }
            this.$refs.pic.handleGive('')
            this.modal = true
            this.isAdd = true
        },
        handleGetNextPage (page) {
            this.currentPage = page
            this.data = []
            this.initTable({
                species_id: this.queryInfo.speciesName,
                status: this.queryInfo.fishingStatus,
                account: this.loginUser.loginAccount,
                pageNum: page,
                pageSize: this.pageSize,
                type: '0' // 0垂钓 1采摘
            })
        },
        edit (id) {
            // 修改状态 说明是修改
            this.isAdd = false
            this.updateId = id
            this.$api.post('/member/fishing/findFishingProduct', {
                id: id,
                pageNum: 1,
                pageSize: this.pageSize
            }).then(response => {
                console.log('edit', response)
                if (response.code === 200) {
                    // 回显要修改的产品数据
                    this.productInfo.productPicture.push({
                        response: {
                            data: {
                                picName: response.data.list[0].image_url
                            }
                        }
                    })
                    this.productInfo.speciesName = response.data.list[0].species_id,
                    this.productInfo.productName = response.data.list[0].product_name,
                    this.productInfo.productPrice = response.data.list[0].product_price,
                    this.productInfo.discountPrice = response.data.list[0].discount_price,
                    this.productInfo.proportion = response.data.list[0].discount_proportion,
                    this.productInfo.measurementUnit = response.data.list[0].unit,
                    this.productInfo.time = response.data.list[0].fishing_time.split(' - '),
                    this.productInfo.timeArr = response.data.list[0].fishing_time.split(' - ')
                    this.productInfo.contact = response.data.list[0].contact_name,
                    this.productInfo.mobile = response.data.list[0].contact_phone,
                    this.productInfo.address = response.data.list[0].address,
                    this.productInfo.coordinate = response.data.list[0].coordinate,
                    this.productInfo.fishingStatus = response.data.list[0].status === '营业中' ? '1' : '0',
                    this.productInfo.detail = response.data.list[0].images_detail
                    // 上传图片控件回显图片
                    this.$refs.pic.handleGive(response.data.list[0].image_url)
                    this.modal = true
                }
            }).catch(error => {
                console.log(error)
            })
        },
        delete (id) {
            this.$Modal.confirm({
                title: '操作提示',
                content: '确定删除该产品？',
                onOk: () => {
                    this.$api.post('/member/fishing/deleteFishingProduct', {
                        id: id
                    }).then(response => {
                        if (response.code === 200) {
                            this.$Message.success('删除产品成功!')
                            this.data = []
                            this.currentPage = 1
                            this.initTable({
                                species_id: this.queryInfo.speciesName,
                                status: this.queryInfo.fishingStatus,
                                account: this.loginUser.loginAccount,
                                pageNum: 1,
                                pageSize: this.pageSize,
                                type: '0' // 0垂钓 1采摘
                            })
                        } else {
                            this.$Message.error('删除产品失败!')
                        }
                    })
                }
            })
        },
        cancel () {
            this.modal = false
        },
        ok () {
            if (this.proportion == 2) {
                this.$Message.error('折扣价格必须小于产品价格')
                return
            }
            this.$refs['productInfo'].validate((valid) => {
                if (valid) {
                    // 新增
                    if (this.isAdd) {
                        this.$api.post('/member/fishing/insertFishingProduct', {
                            image_url: this.productInfo.productPicture[0].response.data.picName,
                            species_id: this.productInfo.speciesName,
                            product_name: this.productInfo.productName,
                            product_price: this.productInfo.productPrice,
                            discount_price: this.productInfo.discountPrice,
                            discount_proportion: this.productInfo.proportion,
                            unit: this.productInfo.measurementUnit,
                            fishing_time: this.productInfo.time.join(' - '),
                            contact_name: this.productInfo.contact,
                            contact_phone: this.productInfo.mobile,
                            address: this.productInfo.address,
                            coordinate: this.productInfo.coordinate,
                            status: this.productInfo.fishingStatus,
                            images_detail: this.productInfo.detail,
                            account: this.loginUser.loginAccount,
                            type: '0' // 0垂钓 1采摘
                        }).then(response => {
                            console.log('res55555', response)
                            if (response.code === 200) {
                                this.$Message.success('产品添加成功！')
                                this.data = []
                                this.currentPage = 1
                                this.initTable({
                                    species_id: this.queryInfo.speciesName,
                                    status: this.queryInfo.fishingStatus,
                                    account: this.loginUser.loginAccount,
                                    pageNum: 1,
                                    pageSize: this.pageSize,
                                    type: '0' // 0垂钓 1采摘
                                })
                                this.modal = false
                            }
                        }).catch(error => {
                            console.log(error)
                        })
                    } else {
                        this.$api.post('/member/fishing/updateFishingProduct', {
                            image_url: this.productInfo.productPicture[0].response.data.picName,
                            species_id: this.productInfo.speciesName,
                            product_name: this.productInfo.productName,
                            product_price: this.productInfo.productPrice,
                            discount_price: this.productInfo.discountPrice,
                            discount_proportion: this.productInfo.proportion,
                            unit: this.productInfo.measurementUnit,
                            fishing_time: this.productInfo.time.join(' - '),
                            contact_name: this.productInfo.contact,
                            contact_phone: this.productInfo.mobile,
                            address: this.productInfo.address,
                            coordinate: this.productInfo.coordinate,
                            status: this.productInfo.fishingStatus,
                            images_detail: this.productInfo.detail,
                            account: this.loginUser.loginAccount,
                            id: this.updateId
                        }).then(response => {
                            console.log('xiugai', response)
                            if (response.code === 200) {
                                this.$Message.success('修改产品成功！')
                                this.data = []
                                this.currentPage = 1
                                this.initTable({
                                    species_id: this.queryInfo.speciesName,
                                    status: this.queryInfo.fishingStatus,
                                    account: this.loginUser.loginAccount,
                                    pageNum: 1,
                                    pageSize: this.pageSize,
                                    type: '0' // 0垂钓 1采摘
                                })
                                this.modal = false
                            }
                        }).catch(error => {
                            console.log(error)
                        })
                    }
                }
            })
        },
        getList (list) {
            this.productInfo.productPicture = list
        },
        onSelectPoint () {
            this.$refs.vuiMap.showMap = true
        },
        onGetPoint (point) {
            if (point.lng !== '' && point.lng !== undefined && point.lat !== '' && point.lat !== undefined) {
                this.productInfo.coordinate = `${point.lng},${point.lat}`
            } else {
                this.productInfo.coordinate = ''
            }
        },
        getCon (msg) {
            this.productInfo.detail = msg
        },
        calProportion () {
            let reg = /^[0-9]+([.]{1}[0-9]{1,2})?$/
            if (this.productInfo.productPrice !== '' && 
                this.productInfo.discountPrice !== '' && 
                reg.test(this.productInfo.productPrice) && 
                reg.test(this.productInfo.discountPrice)) {
                let proportion = parseFloat(this.productInfo.discountPrice / this.productInfo.productPrice * 100).toFixed(2)
                if (proportion > 100) {
                    this.$Message.error('折扣价格必须小于产品价格')
                    this.productInfo.proportion = ''
                    this.proportion = 2
                } else {
                    this.productInfo.proportion = proportion + '%'
                    this.proportion = 3
                }
            } else if (this.productInfo.discountPrice === '' || this.productInfo.productPrice === '') {
                this.proportion = 1
                this.productInfo.proportion = ''
            }
        }
    }
}
</script>
<style scoped>
    .farm-group-btn {
        color: #9B9B9B;
        cursor: pointer;
        font-family: 'PingFangSC-Medium';
    }
    .farm-group-btn-active {
        color: #00c587;
        cursor: pointer;
        font-family: 'PingFangSC-Medium';
    }
</style>
