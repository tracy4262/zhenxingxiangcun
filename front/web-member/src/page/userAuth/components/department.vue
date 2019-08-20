<template>
    <div class="pt30 pl10 pr10 ">
        <Form label-position="left" :label-width="150">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item label="部门">
                        <Button type="primary" @click="handleAdd">
                            <Icon type="plus"></Icon> 添加
                        </Button>
                    </Form-item>
                </Col>
            </Row>
        </Form>
        <Form label-position="left" :label-width="150">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item label="权限">
                        <i-switch  v-model="dept_status" size="large">
                            <span slot="open">公开</span>
                            <span slot="close">隐藏</span>
                        </i-switch>
                    </Form-item>
                </Col>
            </Row>
        </Form>
        <!-- 添加部门弹出框 -->
        <Modal v-model="modal" width="660" title="添加部门" :mask-closable="false">
            <Form ref="deptInfo" :model="deptInfo" label-position="left" :label-width="100" :rules="ruleInline" class="mt20">
                <Row :gutter="32">
                    <Col span="12">
                        <Form-item prop="departmentName" label="部门名称">
                            <Input v-model="deptInfo.departmentName" :maxlength="15" />
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item label="负责人">
                            <Input v-model="deptInfo.leader" :maxlength="15" />
                        </Form-item>
                    </Col>
                </Row>
                <Row :gutter="32">
                    <Col span="12">
                        <Form-item prop="phone" label="联系电话">
                            <Input v-model="deptInfo.phone" :maxlength="11" />
                        </Form-item>
                    </Col>
                </Row>
                <Row :gutter="32">
                    <Col span="24">
                        <Form-item label="职能介绍">
                            <Input type="textarea" v-model="deptInfo.introduce" :maxlength="500" />
                        </Form-item>
                    </Col>
                </Row>
            </Form>
            <div slot="footer">
                <Button type="text" @click="cancel">取消</Button>
                <Button type="primary" @click="ok">确定</Button>
            </div>
        </Modal>
        <Row>
            <Col span="12" class="pr20">
                <Tree :data="data5" :render="renderContent" class="collection-tree mb20"></Tree>
            </Col>
            <Col span="12" v-if="data5.length !== 0">
                <Card>
                    <Row>
                        <Col span="16">部门：{{ showInfo.departmentName }}</Col>
                        <Col span="8" class="tr">
                            <div class="btn-toolbar">
                                <Button type="text" @click="edit"><Icon type="edit" size="16" class="pr5"></Icon> 修改</Button>
                            </div>
                        </Col>
                    </Row>
                    <Row class="mt20">
                        <Col span="12">
                            负责人：{{ showInfo.leader }}
                        </Col>
                        <Col span="12">
                            联系电话：{{ showInfo.phone }}
                        </Col>
                    </Row>
                    <Row class="mt20">
                        职能介绍：{{ showInfo.introduce }}
                    </Row>
                </Card>
            </Col>
        </Row>
    </div>
</template>
<script>
    import { isPhone2 } from '../../../utils/validate'
    export default {
        name: 'department',
        data () {
            return {
                data5: [],
                buttonProps: {
                    type: 'ghost',
                    size: 'small',
                },
                modal: false,
                deptInfo: {
                    departmentName: '',
                    leader: '',
                    phone: '',
                    introduce: ''
                },
                ruleInline: {
                    departmentName: [
                        { required: true, message: '请填写部门名称', tiger: onblur }
                    ],
                    phone: [
                        { validator: isPhone2, tiger: onblur }
                    ]
                },
                temp: [],
                tempNode: [],
                type: 0,
                loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
                showInfo: {},
                isAdd: true,
                dept_status: true
            }
        },
        created () {
            this.initDept()
        },
        methods: {
            renderContent (h, { root, node, data }) {
                return h('span', {
                    style: {
                        display: 'inline-block',
                        width: '100%'
                    }
                }, [
                    h('span', [
                        h('Icon', {
                            props: {
                                type: 'ios-paper-outline'
                            }
                        }),
                        h('span', {
                            style: {
                                marginLeft: '8px',
                                cursor: 'pointer'
                            },
                            on: {
                                click: () => { this.show(node) }
                            }
                        }, data.title)
                    ]),
                    h('span', {
                        style: {
                            display: 'inline-block',
                            float: 'right',
                            marginRight: '32px'
                        }
                    }, [
                        h('Button', {
                            props: Object.assign({}, this.buttonProps, {
                                icon: 'ios-plus-empty'
                            }),
                            style: {
                                marginRight: '8px'
                            },
                            on: {
                                click: () => { this.append(data) }
                            }
                        }),
                        h('Button', {
                            props: Object.assign({}, this.buttonProps, {
                                icon: 'ios-minus-empty'
                            }),
                            on: {
                                click: () => { this.remove(root, node, data) }
                            }
                        })
                    ])
                ]);
            },
            append (data) {
                this.temp = data
                this.deptInfo.departmentName = ''
                this.deptInfo.leader = ''
                this.deptInfo.phone = ''
                this.deptInfo.introduce = ''
                this.$refs['deptInfo'].resetFields()
                this.isAdd = true
                this.modal = true
                this.type = 1
            },
            remove (root, node, data) {
                // 说明下级还有子文件夹
                if (node.node.children) {
                    this.$Message.warning('请先删除子级部门！')
                } else {
                    this.$Modal.confirm({
                        title: '操作提示',
                        content: '确定删除该部门？',
                        onOk: () => { this.removeDept(node.node.id) }
                    })
                }
            },
            initDept () {
                this.$api.post('/member/perfectInfo/findDepartment', {
                    account: this.loginUser.loginAccount
                }).then(response => {
                    console.log('dept', response)
                    if (response.code === 200) {
                        if (response.data.length !== 0 && response.data[0].depart_status) {
                            this.dept_status = response.data[0].depart_status === '1' ? true : false
                        }
                        this.data5 = response.data
                        // 初始化部门右侧数据
                        if (response.data.length !== 0) {
                            // 用于回显第一个部门信息
                            this.showInfo.departmentName = response.data[0].title
                            this.showInfo.leader = response.data[0].leader
                            this.showInfo.phone = response.data[0].phone
                            this.showInfo.introduce = response.data[0].introduce
                            // 用于一进来就编辑第一个部门信息
                            let node = {
                                node: {
                                    title: response.data[0].title,
                                    leader: response.data[0].leader,
                                    phone: response.data[0].phone,
                                    introduce: response.data[0].introduce,
                                    id: response.data[0].id
                                }
                            }
                            this.tempNode = node
                        } else {
                            this.showInfo.departmentName = ''
                            this.showInfo.leader = ''
                            this.showInfo.phone = ''
                            this.showInfo.introduce = ''
                        }
                    }
                }).catch(error => {
                    this.$Message.error('初始化部门数据错误！')
                })
            },
            handleAdd () {
                this.deptInfo.departmentName = ''
                this.deptInfo.leader = ''
                this.deptInfo.phone = ''
                this.deptInfo.introduce = ''
                this.$refs['deptInfo'].resetFields()
                this.isAdd = true
                this.modal = true
                this.type = 0
            },
            cancel () {
                this.modal = false
            },
            ok () {
                this.$refs['deptInfo'].validate((valid) => {
                    console.log('valid', valid)
                    if (valid) {
                        // 新增
                        if (this.isAdd) {
                            // 如果是根节点 pid则为0 否则传入pid
                            if (this.type === 0) {
                                this.addDept(0)
                            } else if (this.type === 1) {
                                this.addDept(this.temp.id)
                            }
                        } else {
                            // 修改
                            this.editDept()
                        }
                    }
                })
            },
            addDept (pid) {
                this.$api.post('/member/perfectInfo/insertOrganization', {
                    pid: pid,
                    departmentName: this.deptInfo.departmentName,
                    leader: this.deptInfo.leader,
                    phone: this.deptInfo.phone,
                    introduce: this.deptInfo.introduce,
                    account: this.loginUser.loginAccount
                }).then(response => {
                    if (response.code === 200) {
                        this.initDept()
                        this.modal = false
                    }
                }).catch(error => {
                    this.$Message.error('新增部门失败！')
                })
            },
            removeDept (id) {
                this.$api.post('/member/perfectInfo/deleteDepartment', {
                    id: id
                }).then(response => {
                    if (response.code === 200) {
                        this.initDept()
                    }
                }).catch(error => {
                    this.$Message.error('删除部门失败！')
                })
            },
            edit () {
                console.log('this.tempNode', this.tempNode)
                // 数据回显
                this.modal = true
                this.isAdd = false
                this.deptInfo.departmentName = this.tempNode.node.title
                this.deptInfo.leader = this.tempNode.node.leader
                this.deptInfo.phone = this.tempNode.node.phone
                this.deptInfo.introduce = this.tempNode.node.introduce
            },
            editDept () {
                // 调用接口修改
                this.$api.post('/member/perfectInfo/updateOrganization', {
                    departmentName: this.deptInfo.departmentName,
                    leader: this.deptInfo.leader,
                    phone: this.deptInfo.phone,
                    introduce: this.deptInfo.introduce,
                    id: this.tempNode.node.id
                }).then(response => {
                    if (response.code === 200) {
                        this.initDept()
                        this.modal = false
                        this.isAdd = true
                    }
                }).catch(error => {
                    this.$Message.error('修改部门失败！')
                })
            },
            show (node) {
                // 用于编辑
                this.tempNode = node
                console.log('this.tempNode', this.tempNode)
                this.$api.post('/member/perfectInfo/findDepartById', {
                    id: node.node.id
                }).then(response => {
                    if (response.code === 200) {
                        this.showInfo = response.data[0]
                    }
                }).catch(error => {
                    this.$Message.error('查询部门信息有误！')
                })
            }
        }
    }
</script>
<style scoped>
    .collection-tree{
        border-bottom: 1px solid #e7e7e7;
    }
    .collection-tree>.ivu-tree-children>li{
        padding:10px;
        border: 1px solid #E7E7E7;
        border-bottom: none;
        margin:0px;
    }
</style>
