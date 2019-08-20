<template>
    <div class="pt30 pl10 pr10 ">
        <!-- <Form label-position="left" :label-width="150">
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
        </Form> -->
        <Row v-if="dept_status">
            <Col span="12" class="pr20">
                <Tree :data="data5" :render="renderContent" class="collection-tree"></Tree>
            </Col>
            <Col span="12">
                <Card>
                    <Row>
                        <Col span="16">部门：{{ showInfo.departmentName }}</Col>
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
                account: '',
                showInfo: {},
                isAdd: true,
                dept_status: true
            }
        },
        created () {
            this.account = this.$route.query.uid
            if(!this.account){
                this.account = this.loginUser.loginAccount
            }
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
                    ])
                ]);
            },
            initDept () {
                this.$api.post('/member/perfectInfo/findDepartment', {
                    account: this.account
                }).then(response => {
                    console.log('response dept', response)
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
            show (node) {
                // 用于编辑
                this.tempNode = node
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
