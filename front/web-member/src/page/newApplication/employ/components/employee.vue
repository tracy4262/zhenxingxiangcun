<!--
    作者：chenqim
    时间：2018-12-20
    描述：受聘管理
-->
<template>
    <div class="pd20" style="min-height: 500px;">
        <Table class="mt20" border :columns="columns" :data="data"></Table>
        <div class="mt20 tr" v-if="data.length !== 0">
            <Page :total="total" :page-size="pageSize" :current="pageNum" @on-change="pageChange" />
        </div>
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
                    title: '聘请方',
                    key: 'inviteName'
                },
                {
                    title: '关系成立时间',
                    key: 'establishTime'
                },
                {
                    title: '状态',
                    key: 'status'
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
                                        this.detail(params.row)
                                    }
                                }
                            }, '查看详情'),
                            h('a', {
                                style: {
                                    color: '#ff5c76'
                                },
                                on: {
                                    click: () => {
                                        this.relive(params.row)
                                    }
                                }
                            }, '解除关系')
                        ])
                    }
                }
            ],
            data: [],
            total: 0,
            pageSize: 10,
            pageNum: 1
        }
    },
    created () {
        this.init()
    },
    methods: {
        init () {
            this.$api.post('/member-reversion/employ/beEmployed', {
                account: this.$user.loginAccount,
                pageNum: this.pageNum,
                pageSize: this.pageSize
            }).then(response => {
                console.log('init res', response)
                if (response.code === 200) {
                    this.data = response.data.list
                    this.total = response.data.total
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        detail (item) {
            window.open('/portals/index?uid='+item.sendAccount)
        },
        relive (item) {
            this.$Modal.confirm({
                title: '操作提示',
                content: '是否确认解除关系？',
                okText:'确定',
                cancelText:'取消',
                onOk: () => {
                    this.$api.post('/member-reversion/employ/relieve', {
                        type: 1,  //0:聘请管理里的解除关系，1:受聘管理里的解除关系
                        activeAccount: item.beinvitedAccount,  //主动解除方
                        passiveAccount: item.sendAccount //被动解除方
                    }).then(response => {
                        if (response.code === 200) {
                            this.$Message.success('解除关系成功！')
                            this.init()
                        }
                    }).catch(error => {
                        this.$Message.error('服务器异常！')
                    })
                }
            })
        },
        pageChange (page) {
            this.pageNum = page
            this.init()
        }
    }
}
</script>
<style lang="scss" scoped>

</style>
