<template>
    <div class="pl15 pr15" style="min-height: 500px">
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
                <span>餐桌列表</span>
            </Col>
            <Col span="12" class="tr pr20">
                <Input v-model="queryInfo.keyword" icon="ios-search" placeholder="请输入关键字" style="width: 200px" @on-enter="handleSearch" @on-click="handleSearch" />
                <Button type="default" @click="addProduct" class="add-btn"><Icon type="plus"></Icon> 添加餐桌</Button>
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
        <Modal v-model="modal" width="660" title="添加餐桌" :mask-closable="false" :styles="{top: '20px'}">
            <Form ref="info" :model="info" label-position="right" :label-width="100" :rules="ruleInline" class="mt20">
                <Form-item prop="area" label="所在区域">
                    <Select v-model="info.area" placeholder="请选择所在区域">
                        <Option v-for="(item, index) in areaList" :key="index" :value="item.id">{{ item.name }}</Option>
                    </Select>
                </Form-item>
                <Form-item prop="count" label="用餐人数">
                    <Input v-model="info.count" :maxlength="10" placeholder="请输入用餐人数" />
                </Form-item>
                <Form-item prop="no" label="餐桌编号">
                    <Input v-model="info.no" :maxlength="10" placeholder="请输入餐桌编号" />
                </Form-item>
                <Form-item prop="status" label="餐桌状态">
                    <RadioGroup v-model="info.status">
                        <Radio label="0">空闲中</Radio>
                        <Radio label="1">使用中</Radio>
                    </RadioGroup>
                </Form-item>
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
            activeStatus: 0,
            total: 0,
            currentPage: 1,
            pageSize: 10,
            modal: false,
            isAdd: true,
            updateId: -1,
            loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
            queryInfo: {
                status: '',
                keyword: ''
            },
            info: {
                area: '',
                count: '',
                no: '',
                status: '0'
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
                    { required: true, type: 'array', min: 1, message: '请选择采摘时间', trigger: 'change' }
                ],
                coordinate: [
                    { required: true, message: '请选择地理位置坐标', trigger: 'change' }
                ],
                address: [
                    { required: true, message: '请输入采摘地址', trigger: 'blur' }
                ],
                mobile: [
                    { validator: isPhone2, trigger: 'blur' }
                ],
                fishingStatus: [
                    { required: true, message: '请选择采摘状态', trigger: 'change' }
                ]
            },
            statusList: [
                {
                    id: '',
                    statusName: '全部状态'
                },
                {
                    id: '0',
                    statusName: '空闲中'
                },
                {
                    id: '1',
                    statusName: '使用中'
                }
            ],
            areaList: [
                {
                    id: 0,
                    name: '大厅'
                }
            ],
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
                    title: '餐桌号',
                    key: 'no',
                    align: 'center'
                },
                {
                    title: '所在区域',
                    key: 'area',
                    align: 'center'
                },
                {
                    title: '可用餐人数',
                    key: 'count',
                    align: 'center'
                },
                {
                    title: '餐桌状态',
                    key: 'status',
                    align: 'center'
                },
                {
                    title: '操作',
                    key: 'action',
                    align: 'center',
                    width: 250,
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
                            }, '删除'),
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
                                        this.generateQRcode(params.row.id)
                                    }
                                }
                            }, '生成二维码')
                        ]);
                    }
                }
            ],
            data: []
        }
    },
    created () {
        this.initRoomList()
        this.initTable({
            account: this.loginUser.loginAccount,
            pageNum: this.currentPage,
            pageSize: this.pageSize,
            number: '',
            status: ''
        })
    },
    methods: {
        // 初始包房
        initRoomList () {
            this.$api.post('/member/restaurant/findRoom', {
                number: '',
                status: '',
                pageNum: 1,
                pageSize: 1000,
                account: this.loginUser.loginAccount
            }).then(response => {
                console.log('res typelist', response)
                if (response.code === 200) {
                    response.data.list.forEach(element => {
                        this.areaList.push({
                            id: element.id,
                            name: element.roomName
                        })
                    })
                }
            }).catch(error => {
                console.log(error)
            })
        },
        // 初始化表格
        initTable (data) {
            this.$api.post('/member/restaurant/findTable', data).then(response => {
                console.log('res table', response)
                if (response.code === 200) {
                    response.data.list.forEach(element => {
                        this.data.push({
                            id: element.id,
                            no: element.number,
                            area: element.roomName,
                            count: element.haveMealsNum,
                            status: element.status
                        })
                    })
                    this.total = response.data.total
                }
            }).catch(error => {
                console.log(error)
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
                number: this.queryInfo.keyword,
                status: this.queryInfo.status
            })
        },
        addProduct () {
            this.$refs['info'].resetFields()
            this.info = {
                area: '',
                count: '',
                no: '',
                status: '0'
            }
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
                number: this.queryInfo.keyword,
                status: this.queryInfo.status
            })
        },
        edit (id) {
            // 修改状态 说明是修改
            this.isAdd = false
            this.updateId = id
            this.$api.post('/member/restaurant/findTable', {
                id: id,
                pageNum: 1,
                pageSize: this.pageSize
            }).then(response => {
                console.log('edit', response)
                if (response.code === 200) {
                    // 回显要修改的产品数据
                    this.info.area = parseInt(response.data.list[0].location),
                    this.info.count = response.data.list[0].haveMealsNum,
                    this.info.no = response.data.list[0].number,
                    this.info.status = response.data.list[0].status === '使用中' ? '1' : '0',
                    this.modal = true
                }
            }).catch(error => {
                this.$Message.error('查询到单个餐桌失败！')
            })
        },
        delete (id) {
            this.$Modal.confirm({
                title: '操作提示',
                content: '确定删除该餐桌？',
                onOk: () => {
                    this.$api.post('/member/restaurant/deleteTable', {
                        id: id
                    }).then(response => {
                        if (response.code === 200) {
                            this.$Message.success('删除餐桌成功!')
                            this.data = []
                            this.currentPage = 1
                            this.initTable({
                                account: this.loginUser.loginAccount,
                                pageNum: this.currentPage,
                                pageSize: this.pageSize,
                                number: this.queryInfo.keyword,
                                status: this.queryInfo.status
                            })
                        } else {
                            this.$Message.error('删除餐桌失败!')
                        }
                    })
                }
            })
        },
        cancel () {
            this.modal = false
        },
        ok () {
            this.$refs['info'].validate((valid) => {
                if (valid) {
                    // 新增
                    if (this.isAdd) {
                        this.$api.post('/member/restaurant/insertTable', {
                            account: this.loginUser.loginAccount,
                            location: this.info.area,
                            haveMealsNum: this.info.count,
                            number: this.info.no,
                            status: this.info.status
                        }).then(response => {
                            console.log('res55555', response)
                            if (response.code === 200) {
                                this.$Message.success('添加餐桌成功！')
                                this.data = []
                                this.currentPage = 1
                                this.initTable({
                                    account: this.loginUser.loginAccount,
                                    pageNum: 1,
                                    pageSize: this.pageSize,
                                    number: '',
                                    status: ''
                                })
                                this.modal = false
                            } else if (response.code === 300) {
                                this.$Message.error('该餐桌编号已存在！')
                            }
                        }).catch(error => {
                            this.$Message.error('添加餐桌失败！')
                        })
                    } else {
                        this.$api.post('/member/restaurant/updateTable', {
                            account: this.loginUser.loginAccount,
                            location: this.info.area,
                            haveMealsNum: this.info.count,
                            number: this.info.no,
                            status: this.info.status,
                            id: this.updateId
                        }).then(response => {
                            console.log('xiugai', response)
                            if (response.code === 200) {
                                this.$Message.success('修改餐桌成功！')
                                this.data = []
                                this.currentPage = 1
                                this.initTable({
                                    account: this.loginUser.loginAccount,
                                    pageNum: this.currentPage,
                                    pageSize: this.pageSize,
                                    number: this.queryInfo.keyword,
                                    status: this.queryInfo.status
                                })
                                this.modal = false
                            } else if (response.code === 300) {
                                this.$Message.error('该餐桌编号已存在！')
                            }
                        }).catch(error => {
                            this.$Message.error('修改餐桌失败！')
                        })
                    }
                }
            })
        },
        // 查询
        handleSearch () {
            this.currentPage = 1
            this.data = []
            this.initTable({
                account: this.loginUser.loginAccount,
                pageNum: this.currentPage,
                pageSize: this.pageSize,
                number: this.queryInfo.keyword,
                status: this.queryInfo.status
            })
        },
        generateQRcode () {}
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

