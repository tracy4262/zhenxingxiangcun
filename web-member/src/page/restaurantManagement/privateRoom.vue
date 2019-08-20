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
                <span>包房列表</span>
            </Col>
            <Col span="12" class="tr pr20">
                <Input v-model="queryInfo.keyword" icon="ios-search" placeholder="请输入关键字" style="width: 200px" @on-enter="handleSearch" @on-click="handleSearch" />
                <Button type="default" @click="addProduct" class="add-btn"><Icon type="plus"></Icon> 添加包房</Button>
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
        <Modal v-model="modal" width="660" title="添加包房" :mask-closable="false" :styles="{top: '20px'}">
            <Form ref="info" :model="info" label-position="right" :label-width="100" :rules="ruleInline" class="mt20">
                <Form-item prop="picture" label="包房图片">
                    <vui-upload
                        ref="pic"
                        @on-getPictureList="getList"
                        :hint="'图片大小小于2MB'"
                        :total="1"
                        :size="[80,80]">
                    </vui-upload>
                </Form-item>
                <Form-item prop="name" label="包房名称">
                    <Input v-model="info.name" :maxlength="50" placeholder="请输入包房名称" />
                </Form-item>
                <Form-item prop="minPrice" label="最低消费价格">
                    <Input v-model="info.minPrice" :maxlength="10" placeholder="请输入包房价格">
                        <span slot="append">元</span>
                    </Input>
                </Form-item>
                <Form-item label="包房描述">
                    <Input type="textarea" v-model="info.description" :maxlength="100" placeholder="请输入包房描述" />
                </Form-item>
                <Form-item prop="status" label="包房状态">
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
                picture: [],
                name: '',
                minPrice: '',
                status: '0',
                description: ''
            },
            ruleInline: {
                picture: [
                    { required: true, message: '请上传包房图片', type: 'array', min: 1, trigger: 'change' }
                ],
                name: [
                    { required: true, message: '请填写包房名称', trigger: 'blur' }
                ],
                minPrice: [
                    { required: true, validator: isMoney, trigger: 'blur' }
                ],
                status: [
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
            speciesNameList: [],
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
                    title: '包房名称',
                    key: 'name',
                    align: 'center'
                },
                {
                    title: '最低消费价格',
                    key: 'minPrice',
                    align: 'center'
                },
                {
                    title: '包房状态',
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
            data: []
        }
    },
    created () {
        this.initTable({
            roomName: '',
            status: '',
            pageNum: 1,
            pageSize: this.pageSize,
            account: this.loginUser.loginAccount
        })
    },
    methods: {
        // 初始化表格
        initTable (data) {
            this.$api.post('/member/restaurant/findRoom', data).then(response => {
                console.log('res table', response)
                if (response.code === 200) {
                    response.data.list.forEach(element => {
                        this.data.push({
                            id: element.id,
                            name: element.roomName,
                            minPrice: `￥ ${element.minPrice}`,
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
        chooseStatus (item, index) {
            this.activeStatus = index
            this.queryInfo.status = item.id
            this.currentPage = 1
            this.data = []
            this.initTable({
                roomName: this.queryInfo.keyword,
                status: this.queryInfo.status,
                pageNum: this.currentPage,
                pageSize: this.pageSize,
                account: this.loginUser.loginAccount
            })
        },
        addProduct () {
            this.$refs['info'].resetFields()
            this.info = {
                picture: [],
                name: '',
                minPrice: '',
                status: '0',
                description: ''
            }
            this.$refs.pic.handleGive('')
            this.modal = true
            this.isAdd = true
        },
        handleGetNextPage (page) {
            this.currentPage = page
            this.data = []
            this.initTable({
                roomName: this.queryInfo.keyword,
                status: this.queryInfo.status,
                pageNum: page,
                pageSize: this.pageSize,
                account: this.loginUser.loginAccount
            })
        },
        edit (item) {
            if (item.flag) {
                this.$Message.error({
                    content: '此包房已被关联，请先删除关联后再编辑包房。',
                    duration: 5
                });
            } else {
                // 修改状态 说明是修改
                this.isAdd = false
                this.updateId = item.id
                this.$api.post('/member/restaurant/findRoom', {
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
                                    picName: response.data.list[0].roomImage[0]
                                }
                            }
                        })
                        this.$refs.pic.handleGive(response.data.list[0].roomImage[0])
                        this.info.name = response.data.list[0].roomName,
                        this.info.minPrice = response.data.list[0].minPrice,
                        this.info.description = response.data.list[0].roomDescribe,
                        this.info.status = response.data.list[0].status === '使用中' ? '1' : '0',
                        // 上传图片控件回显图片
                        this.modal = true
                    }
                }).catch(error => {
                    this.$Message.error('查询单个包房失败！')
                })
            }
        },
        delete (item) {
            if (item.flag) {
                this.$Message.error({
                    content: '此包房已被关联，请先删除关联后再删除包房。',
                    duration: 5
                });
            } else {
                this.$Modal.confirm({
                    title: '操作提示',
                    content: '确定删除该包房？',
                    onOk: () => {
                        this.$api.post('/member/restaurant/deleteRoom', {
                            id: id
                        }).then(response => {
                            if (response.code === 200) {
                                this.$Message.success('删除包房成功!')
                                this.data = []
                                this.currentPage = 1
                                this.initTable({
                                    roomName: this.queryInfo.keyword,
                                    status: this.queryInfo.status,
                                    pageNum: this.currentPage,
                                    pageSize: this.pageSize,
                                    account: this.loginUser.loginAccount
                                })
                            } else {
                                this.$Message.error('删除包房失败!')
                            }
                        })
                    }
                })
            }
        },
        cancel () {
            this.modal = false
        },
        ok () {
            this.$refs['info'].validate((valid) => {
                if (valid) {
                    if (this.isAdd) {
                        // 新增
                        this.$api.post('/member/restaurant/insertRoom', {
                            account: this.loginUser.loginAccount,
                            roomImage: this.info.picture[0].response.data.picName,
                            roomName: this.info.name,
                            minPrice: this.info.minPrice,
                            status: this.info.status,
                            roomDescribe: this.info.description
                        }).then(response => {
                            console.log('res55555', response)
                            if (response.code === 200) {
                                this.$Message.success('添加包房成功！')
                                this.data = []
                                this.currentPage = 1
                                this.initTable({
                                    roomName: this.queryInfo.keyword,
                                    status: this.queryInfo.status,
                                    pageNum: this.currentPage,
                                    pageSize: this.pageSize,
                                    account: this.loginUser.loginAccount
                                })
                                this.modal = false
                            } else if (response.code === 300) {
                                this.$Message.error('该包房名称已存在！')
                            }
                        }).catch(error => {
                            this.$Messgae.error('添加包房失败！')
                        })
                    } else {
                        // 编辑
                        this.$api.post('/member/restaurant/updateRoom', {
                            account: this.loginUser.loginAccount,
                            roomImage: this.info.picture[0].response.data.picName,
                            roomName: this.info.name,
                            minPrice: this.info.minPrice,
                            status: this.info.status,
                            roomDescribe: this.info.description,
                            id: this.updateId
                        }).then(response => {
                            console.log('xiugai', response)
                            if (response.code === 200) {
                                this.$Message.success('修改包房成功！')
                                this.data = []
                                this.currentPage = 1
                                this.initTable({
                                    roomName: this.queryInfo.keyword,
                                    status: this.queryInfo.status,
                                    pageNum: this.currentPage,
                                    pageSize: this.pageSize,
                                    account: this.loginUser.loginAccount
                                })
                                this.modal = false
                            } else if (response.code === 300) {
                                this.$Message.error('该包房名称已存在！')
                            }
                        }).catch(error => {
                            this.$Messgae.error('修改包房失败！')
                        })
                    }
                }
            })
        },
        getList (list) {
            this.info.picture = list
        },
        // 查询
        handleSearch () {
            this.currentPage = 1
            this.data = []
            this.initTable({
                roomName: this.queryInfo.keyword,
                status: this.queryInfo.status,
                pageNum: this.currentPage,
                pageSize: this.pageSize,
                account: this.loginUser.loginAccount
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
