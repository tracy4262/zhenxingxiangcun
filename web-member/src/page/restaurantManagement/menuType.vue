<template>
    <div class="pd20">       
        <Row class="pt20 pb20" type="flex" justify="center" align="middle">
            <Col span="12" class="tl pl20">
                <span>
                    菜品分类列表
                </span>
            </Col>
            <Col span="12" class="tr pr20">
                <Button 
                    type="default" 
                    icon="android-add"
                    @click="addMenuType" >
                    添加分类
                </Button>
            </Col>
        </Row>
        <div class="pb30">            
            <Table :columns="columns1" :data="data"></Table>
        </div>
        <Modal v-model="modal" width="660" title="添加菜品分类" :mask-closable="false">
            <Form ref="info" :model="info" label-position="right" :label-width="100" :rules="ruleInline" class="mt20">
                <Form-item prop="name" label="菜品分类名称">
                    <Input v-model="info.name" :maxlength="20" placeholder="请输入菜品分类名" />
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
    export default {
        name: 'menuType',
        components: {
        },
        data () {
            return {
                modal: false,
                info: {
                    name: ''
                },
                ruleInline: {
                    name: [
                        { required: true, message: '请填写菜品分类名称', tiger: 'blur' }
                    ]
                },
                height: 0,
                columns1: [
                    {
                        title: '菜品分类名称',
                        key: 'name'
                    },
                    {
                        title: '操作',
                        key: 'action',
                        width: 150,
                        align: 'center',
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
                            ])
                        }
                    }
                ],
                data: [],
                loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
                pageNum: 1,
                pageSize: 1000,
                isAdd: true,
                updateId: -1
            }
        },
        created () {
            this.init()
        },
        methods: {
            addMenuType () {
                this.isAdd = true
                this.info = {
                    name: ''
                }
                this.$refs['info'].resetFields()
                this.modal = true
            },
            init () {
                this.$api.post('/member/restaurant/findRestaurant', {
                    account: this.loginuserinfo.loginAccount,
                    pageNum: this.pageNum,
                    pageSize: this.pageSize
                }).then(response => {
                    console.log('resp', response)
                    if (response.code === 200) {
                        this.data = []
                        response.data.list.forEach(element => {
                            this.data.push({
                                id: element.id,
                                name: element.foodClassName
                            })
                        })
                    }
                }).catch(error => {
                    this.$Message.error('查询菜品分类失败！')
                })
            },
            ok () {
                this.$refs['info'].validate((valid) => {
                    if (valid) {
                        if (this.isAdd) {
                            // 新增
                            this.$api.post('/member/restaurant/insertRestaurant', {
                                account: this.loginuserinfo.loginAccount,
                                foodClassName: this.info.name
                            }).then(response => {
                                console.log('resp', response)
                                if (response.code === 200) {
                                    this.$Message.success('添加菜品分类成功！')
                                    this.init()
                                    this.modal = false
                                } else if (response.code === 300) {
                                    this.$Message.error('菜品分类名称不允许重复！')
                                }
                            }).catch(error => {
                                this.$Message.error('添加菜品分类失败！')
                            })
                        } else {
                            // 编辑
                            this.$api.post('/member/restaurant/updateRestaurant', {
                                account: this.loginuserinfo.loginAccount,
                                foodClassName: this.info.name,
                                id: this.updateId
                            }).then(response => {
                                console.log('resp', response)
                                if (response.code === 200) {
                                    this.$Message.success('修改菜品分类成功！')
                                    this.init()
                                    this.modal = false
                                } else if (response.code === 300) {
                                    this.$Message.error('菜品分类名称不允许重复！')
                                }
                            }).catch(error => {
                                this.$Message.error('修改菜品分类失败！')
                            })
                        }
                    }
                })
            },
            cancel () {
                this.modal = false
            },
            edit (id) {
                this.$api.post('/member/restaurant/findFood', 
                {account: this.loginuserinfo.loginAccount, pageNum: 1, pageSize: 999999, foodName: '', status: '', foodClassId: id})
                .then(response => {
                    if (response.code === 200) {
                        if (response.data.total) {
                            // 说明当前分类下有菜品
                            this.$Message.error({
                                content: '此菜品分类下已有菜品，请先删除菜品后再编辑菜品分类。',
                                duration: 5
                            });
                        } else {
                            // 说明当前分类下没有菜品
                            this.isAdd = false
                            this.updateId = id
                            this.$api.post('/member/restaurant/findRestaurant', {
                                id: id,
                                pageNum: this.pageNum,
                                pageSize: this.pageSize
                            }).then(response => {
                                console.log('resp', response)
                                if (response.code === 200) {
                                    this.info.name = response.data.list[0].foodClassName
                                    this.modal = true
                                }
                            }).catch(error => {
                                this.$Message.error('查询单个菜品分类失败！')
                            })
                        }
                    }
                })
            },
            delete (id) {
                this.$api.post('/member/restaurant/findFood', 
                {account: this.loginuserinfo.loginAccount, pageNum: 1, pageSize: 999999, foodName: '', status: '', foodClassId: id})
                .then(response => {
                    if (response.code === 200) {
                        if (response.data.total) {
                            // 说明当前分类下有菜品
                            this.$Message.error({
                                content: '此菜品分类下已有菜品，请先删除菜品后再删除菜品分类。',
                                duration: 5
                            });
                        } else {
                            // 说明当前分类下没有菜品
                            this.$Modal.confirm({
                                title: '操作提示',
                                content: '确定删除该菜品分类？',
                                onOk: () => {
                                    this.$api.post('/member/restaurant/deleteRestaurant', {
                                        id: id
                                    }).then(response => {
                                        console.log('resp', response)
                                        if (response.code === 200) {
                                            this.$Message.success('删除菜品分类成功！')
                                            this.init()
                                        }
                                    }).catch(error => {
                                        this.$Message.error('删除菜品分类失败！')
                                    })
                                }
                            })
                        }
                    }
                })
                
            }
        }
    }
</script>