<template>
    <div class="pl15 pr15" style="min-height: 500px">
        <Row class="mt20 pl20">
            <Col span="24">
                <span>分类</span>
                <span v-for="(item,index) in typeList" :key="index" class="pl10 pr10">
                    <span @click="chooseType(item, index)" :class="{'farm-group-btn-active': index === activeType, 'farm-group-btn': true}">
                        {{ item.name }}
                    </span>
                </span>
            </Col>
        </Row>
        <Row class="mt20 pl20">
            <Col span="24">
                <span>状态</span>
                <span v-for="(item,index) in statusList" :key="index" class="pl10 pr10">
                    <span @click="chooseStatus(item, index)" :class="{'farm-group-btn-active': index === activeStatus, 'farm-group-btn': true}">
                        {{ item.statusName }}
                    </span>
                </span>
            </Col>
        </Row>
        <Row class="mt40" type="flex" justify="center" align="middle">
            <Col span="12" class="tl pl20">
                <span>菜品列表</span>
            </Col>
            <Col span="12" class="tr pr20">
                <Input v-model="queryInfo.keyword" icon="ios-search" placeholder="请输入关键字" style="width: 200px" @on-enter="handleSearch" @on-click="handleSearch" />
                <Button type="default" @click="add" class="add-btn"><Icon type="plus"></Icon> 添加菜品</Button>
            </Col>
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
        <Modal v-model="modal" width="860" title="添加菜品" :mask-closable="false" :styles="{top: '20px'}">
            <Form ref="info" :model="info" label-position="right" :label-width="100" :rules="ruleInline" class="mt20">
                <Form-item prop="picture" label="菜品图片">
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
                        <Form-item prop="type" label="菜品分类">
                            <Select v-model="info.type" placeholder="请选择菜品分类">
                                <Option v-for="(item, index) in dishTypeList" :key="index" :value="item.id">{{ item.foodClassName }}</Option>
                            </Select>
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="name" label="菜品名称">
                            <Input v-model="info.name" :maxlength="50" placeholder="请输入菜品名称" />
                        </Form-item>
                    </Col>
                </Row>
                <Row>
                    <Col span="12">
                        <Form-item prop="price" label="菜品价格">
                            <Input v-model="info.price" @on-change="calProportion" :maxlength="10" placeholder="请输入菜品价格">
                                <span slot="append">元</span>
                            </Input>
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="status" label="菜品状态">
                            <RadioGroup v-model="info.status">
                                <Radio label="1">上架</Radio>
                                <Radio label="0">下架</Radio>
                            </RadioGroup>
                        </Form-item>
                    </Col>
                </Row>
                <Row>
                    <Col span="12">
                        <Form-item prop="discountPrice" label="折扣价格">
                            <Input v-model="info.discountPrice" @on-change="calProportion" :maxlength="10" placeholder="请输入打折价">
                                <span slot="append">元</span>
                            </Input>
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="proportion" label="折扣比例">
                            <Input v-model="info.proportion" readonly placeholder="自动生成折扣比例" />
                        </Form-item>
                    </Col>
                </Row>
            </Form>
            <div slot="footer">
                <Button type="text" @click="cancel">取消</Button>
                <Button type="primary" @click="ok">确定</Button>
            </div>
        </Modal>
    </div>
</template>
<script>
import { isPhone2,isMoney,isMoney2 } from '~utils/validate'
import vuiUpload from '~components/vui-upload'
export default {
    name: 'fishingProductList',
    components: {
        vuiUpload
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
                type: '',
                status: '',
                keyword: ''
            },
            info: {
                picture: [],
                type: '',
                name: '',
                price: '',
                discountPrice: '',
                proportion: '',
                status: '1'
            },
            ruleInline: {
                picture: [
                    { required: true, message: '请上传菜品图片', type: 'array', min: 1, trigger: 'change' }
                ],
                type: [
                    { required: true, message: '请选择菜品分类', type: 'number', trigger: 'change' }
                ],
                name: [
                    { required: true, message: '请填写菜品名称', trigger: 'blur' }
                ],
                price: [
                    { required: true, validator: isMoney, trigger: 'blur' }
                ],
                discountPrice: [
                    { validator: isMoney2, trigger: 'blur' }
                ],
                status: [
                    { required: true, message: '请选择菜品状态', trigger: 'change' }
                ]
            },
            typeList: [
                {
                    id: '',
                    name: '全部分类'
                }
            ],
            statusList: [
                {
                    id: '',
                    statusName: '全部状态'
                },
                {
                    id: '1',
                    statusName: '热卖中'
                },
                {
                    id: '0',
                    statusName: '已停售'
                }
            ],
            dishTypeList: [],
            columns: [
                /* {
                    title: '菜品图片',
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
                }, */
                {
                    title: '菜品名称',
                    key: 'name',
                    align: 'center'
                },
                {
                    title: '菜品分类',
                    key: 'type',
                    align: 'center'
                },
                {
                    title: '菜品价格',
                    key: 'price',
                    align: 'center'
                },
                {
                    title: '菜品状态',
                    key: 'status',
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
                                        this.edit(params.row)
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
                                        this.delete(params.row)
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
            account: this.loginUser.loginAccount,
            pageNum: this.currentPage,
            pageSize: this.pageSize,
            foodName: this.queryInfo.keyword,
            foodClassId: this.queryInfo.type,
            status: this.queryInfo.status
        })
    },
    methods: {
        // 初始化分类
        initTypeList () {
            this.$api.post('/member/restaurant/findRestaurant', {
                account: this.loginUser.loginAccount,
                pageNum: 1,
                pageSize: 1000
            }).then(response => {
                console.log('res typelist', response)
                if (response.code === 200) {
                    response.data.list.forEach(element => {
                        this.typeList.push({
                            id: element.id,
                            name: element.foodClassName
                        })
                    })
                    this.dishTypeList = response.data.list
                }
            }).catch(error => {
                this.$Message.error('初始化分类失败！')
            })
        },
        // 初始化表格
        initTable (data) {
            this.$api.post('/member/restaurant/findFood', data).then(response => {
                console.log('res table', response)
                if (response.code === 200) {
                    response.data.list.forEach(element => {
                        this.data.push({
                            id: element.id,
                            type: element.foodClassName,
                            name: element.foodName,
                            price: `￥ ${element.foodPrice}`,
                            status: element.status,
                            flag: element.flag
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
            this.queryInfo.type = item.id
            this.currentPage = 1
            this.data = []
            this.initTable({
                account: this.loginUser.loginAccount,
                pageNum: this.currentPage,
                pageSize: this.pageSize,
                foodName: this.queryInfo.keyword,
                foodClassId: this.queryInfo.type,
                status: this.queryInfo.status
            })
        },
        chooseStatus (item, index) {
            this.activeStatus = index
            this.queryInfo.status = item.id
            this.currentPage = 1
            this.data = []
            this.initTable({
                account: this.loginUser.loginAccount,
                pageNum: this.currentPage,
                pageSize: this.pageSize,
                foodName: this.queryInfo.keyword,
                foodClassId: this.queryInfo.type,
                status: this.queryInfo.status
            })
        },
        add () {
            this.$refs['info'].resetFields()
            this.info = {
                picture: [],
                type: '',
                name: '',
                price: '',
                discountPrice: '',
                proportion: '',
                status: '1'
            }
            this.$refs.pic.handleGive('')
            this.modal = true
            this.isAdd = true
        },
        handleGetNextPage (page) {
            this.currentPage = page
            this.data = []
            this.initTable({
                account: this.loginUser.loginAccount,
                pageNum: page,
                pageSize: this.pageSize,
                foodName: this.queryInfo.keyword,
                foodClassId: this.queryInfo.type,
                status: this.queryInfo.status
            })
        },
        edit (item) {
            console.log(item)
            // if (!item.flag) {
                // 修改状态 说明是修改
                this.isAdd = false
                this.updateId = item.id
                this.$api.post('/member/restaurant/findFood', {
                    id: item.id,
                    pageNum: 1,
                    pageSize: this.pageSize
                }).then(response => {
                    console.log('edit', response)
                    if (response.code === 200) {
                        // 回显要修改的产品数据
                        this.info.picture.push({
                            response: {
                                data: {
                                    picName: response.data.list[0].foodImage[0]
                                }
                            }
                        })
                        // 上传图片控件回显图片
                        this.$refs.pic.handleGive(response.data.list[0].foodImage[0])
                        this.info.type = response.data.list[0].foodClassId,
                        this.info.name = response.data.list[0].foodName,
                        this.info.price = response.data.list[0].foodPrice,
                        this.info.discountPrice = response.data.list[0].discountPrice,
                        this.info.proportion = response.data.list[0].discountProportion,
                        this.info.status = response.data.list[0].status === '热卖中' ? '1' : '0',
                        this.info.flag = response.data.list[0].flag,
                        this.modal = true
                    }
                }).catch(error => {
                    this.$Message.error('查询单个菜品出错！')
                })
            // } else {
            //     this.$Message.error({
            //         content: '此菜品已被关联，请先删除关联后再编辑菜品。',
            //         duration: 5
            //     });
            // }
        },
        delete (item) {
            if (!item.flag) {
                this.$Modal.confirm({
                    title: '操作提示',
                    content: '确定删除该菜品？',
                    onOk: () => {
                        this.$api.post('/member/restaurant/deleteFood', {
                            id: id
                        }).then(response => {
                            if (response.code === 200) {
                                this.$Message.success('删除菜品成功!')
                                this.data = []
                                this.currentPage = 1
                                this.initTable({
                                    account: this.loginUser.loginAccount,
                                    pageNum: this.currentPage,
                                    pageSize: this.pageSize,
                                    foodName: this.queryInfo.keyword,
                                    foodClassId: this.queryInfo.type,
                                    status: this.queryInfo.status
                                })
                            } else {
                                this.$Message.error('删除菜品失败!')
                            }
                        })
                    }
                })
            } else {
                this.$Message.error({
                    content: '此菜品已被关联，请先删除关联后再删除菜品。',
                    duration: 5
                });
            }
        },
        cancel () {
            this.modal = false
        },
        ok () {
            if (this.proportion == 2) {
                this.$Message.error('折扣价格必须小于菜品价格')
                return
            }
            this.$refs['info'].validate((valid) => {
                if (valid) {
                    if (this.isAdd) {
                        // 新增
                        this.$api.post('/member/restaurant/insertFood', {
                            account: this.loginUser.loginAccount,
                            foodClassId: this.info.type,
                            foodName: this.info.name,
                            foodPrice: this.info.price,
                            discountPrice: this.info.discountPrice,
                            discountProportion: this.info.proportion,
                            status: this.info.status,
                            foodImage: this.info.picture[0].response.data.picName
                        }).then(response => {
                            console.log('res55555', response)
                            if (response.code === 200) {
                                this.$Message.success('菜品添加成功！')
                                this.data = []
                                this.currentPage = 1
                                this.initTable({
                                    account: this.loginUser.loginAccount,
                                    pageNum: this.currentPage,
                                    pageSize: this.pageSize,
                                    foodName: this.queryInfo.keyword,
                                    foodClassId: this.queryInfo.type,
                                    status: this.queryInfo.status
                                })
                                this.modal = false
                            } else if (response.code === 300) {
                                this.$Message.error('该菜品名称已存在！')
                            }
                        }).catch(error => {
                            this.$Message.error('菜品添加失败！')
                        })
                    } else {
                        if (this.info.flag) {
                             this.$Message.error({
                                content: '此菜品已被关联，请先删除关联后再编辑菜品。',
                                duration: 5
                            });
                        } else {
                            // 编辑
                            let data = {
                                account: this.loginUser.loginAccount,
                                foodClassId: this.info.type,
                                foodName: this.info.name,
                                foodPrice: this.info.price,
                                discountPrice: this.info.discountPrice,
                                discountProportion: this.info.proportion,
                                status: this.info.status,
                                foodImage: this.info.picture[0].response.data.picName,
                                id: this.updateId
                            }
                            console.log('data', data)
                            this.$api.post('/member/restaurant/updateFood', data).then(response => {
                                console.log('res55555', response)
                                if (response.code === 200) {
                                    this.$Message.success('修改菜品成功！')
                                    this.data = []
                                    this.currentPage = 1
                                    this.initTable({
                                        account: this.loginUser.loginAccount,
                                        pageNum: this.currentPage,
                                        pageSize: this.pageSize,
                                        foodName: this.queryInfo.keyword,
                                        foodClassId: this.queryInfo.type,
                                        status: this.queryInfo.status
                                    })
                                    this.modal = false
                                } else if (response.code === 300) {
                                    this.$Message.error('该菜品名称已存在！')
                                }
                            }).catch(error => {
                                this.$Message.error('修改菜品失败！')
                            })

                        }
                    }
                }
            })
        },
        getList (list) {
            this.info.picture = list
        },
        calProportion () {
            let reg = /^[0-9]+([.]{1}[0-9]{1,2})?$/
            if (this.info.price !== '' && 
                this.info.discountPrice !== '' && 
                reg.test(this.info.price) && 
                reg.test(this.info.discountPrice)) {
                let proportion = parseFloat(this.info.discountPrice / this.info.price * 100).toFixed(2)
                if (proportion > 100) {
                    this.$Message.error('折扣价格必须小于房间价格')
                    this.info.proportion = ''
                    this.proportion = 2
                } else {
                    this.info.proportion = proportion + '%'
                    this.proportion = 3
                }
            } else if (this.info.discountPrice === '' || this.info.price === '') {
                this.proportion = 1
                this.info.proportion = ''
            }
        },
        // 查询
        handleSearch () {
            this.currentPage = 1
            this.data = []
            this.initTable({
                account: this.loginUser.loginAccount,
                pageNum: this.currentPage,
                pageSize: this.pageSize,
                foodName: this.queryInfo.keyword,
                foodClassId: this.queryInfo.type,
                status: this.queryInfo.status
            })
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
