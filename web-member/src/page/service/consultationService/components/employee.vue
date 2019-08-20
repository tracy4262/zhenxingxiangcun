<!--
    作者：chenqim
    时间：2019-02-22
    描述：发布咨询服务
-->
<template>
    <div class="pd20" style="min-height: 500px;">
        <Row>
            <Col span="12" offset="12" class=tr>
                <Button type="default" icon="ios-add" @click="addService" :disabled="disabled">添加服务</Button>
            </Col>
        </Row>
        <Table class="mt20" border :columns="columns" :data="data"></Table>
    </div>
</template>
<script>
export default {
    name: 'employee',
    components: {

    },
    data () {
        return {
            columns: [
                {
                    title: '服务名称',
                    key: 'serviceName'
                },
                {
                    title: '服务分类',
                    key: 'serviceClassId'
                },
                {
                    title: '创建时间',
                    key: 'createTime'
                },
                {
                    title: '操作',
                    key: 'action',
                    width: 200,
                    render: (h, params) => {
                        return h('div', [
                            h('a', {
                                style: {
                                    marginRight: '10px',
                                    color: '#2c92ff'
                                },
                                on: {
                                    click: () => {
                                        this.edit(params.row)
                                    }
                                }
                            }, '编辑'),
                            h('a', {
                                style: {
                                    color: '#ff5c76'
                                },
                                on: {
                                    click: () => {
                                        this.del(params.row)
                                    }
                                }
                            }, '删除')
                        ])
                    }
                }
            ],
            data: [],
            disabled: true
        }
    },
    created () {
        this.init()
    },
    methods: {
        init () {
            this.$api.post('/member-reversion/consult/list', {
                account: this.$user.loginAccount
            }).then(response => {
                console.log('init res', response)
                if (response.code === 200) {
                    this.data = []
                    if (response.data) {
                        this.data.push(response.data)
                    }
                    // 控制只能发布一条咨询服务
                    if (this.data.length === 0) {
                        this.disabled = false
                    }
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        edit (item) {
            this.$router.push({
                path: '/addConsultationService/step1',
                query: {
                    id: item.id
                }
            })
        },
        del (item) {
            this.$Modal.confirm({
                title: '操作提示',
                content: '是否确认删除该咨询服务？',
                okText:'确定',
                cancelText:'取消',
                onOk: () => {
                    this.$api.get('/member-reversion/consult/delete?id=' + item.id).then(response => {
                        if (response.code === 200) {
                            this.$Message.success('删除成功！')
                            this.init()
                        }
                    }).catch(error => {
                        this.$Message.error('服务器异常！')
                    })
                }
            })
        },
        addService () {
            this.$router.push('/addConsultationService/step1')
        }
    }
}
</script>
<style lang="scss" scoped>

</style>
