<!--
    作者：chenqim
    时间：2018-12-20
    描述：受邀管理
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
    name: 'invitee',
    components: {

    },
    data () {
        return {
            columns: [
                {
                    title: '邀请方',
                    key: 'inviteName'
                },
                {
                    title: '邀请时间',
                    key: 'invitedTime'
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
                                    marginRight: '10px',
                                },
                                on: {
                                    click: () => {
                                        this.accept(params.row)
                                    }
                                }
                            }, '接受'),
                            h('a', {
                                style: {
                                    color: '#ff5c76'
                                },
                                on: {
                                    click: () => {
                                        this.refuse(params.row)
                                    }
                                }
                            }, '拒绝')
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
            this.$api.post('/member-reversion/employ/beInvited', {
                account: this.$user.loginAccount,
                pageNum: this.pageNum,
                pageSize: this.pageSize
            }).then(response => {
                console.log('res', response)
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
        accept (item) {
            this.$Modal.confirm({
                title: '操作提示',
                content: '是否确认接受邀请？',
                okText:'确定',
                cancelText:'取消',
                onOk: () => {
                    this.dealInvite(item, 1)
                }
            })
        },
        refuse (item) {
            this.$Modal.confirm({
                title: '操作提示',
                content: '是否确认拒绝申请？',
                okText:'确定',
                cancelText:'取消',
                onOk: () => {
                    this.dealInvite(item, 2)
                }
            })
        },
        dealInvite (item, status) {
            this.$api.post('/member-reversion/employ/dealInvite', {
                sendAccount: item.sendAccount,
                beInvitedAccount: this.$user.loginAccount,
                status: status // 1接受，2拒绝
            }).then(response => {
                if (response.code === 200) {
                    this.$Message.success(status === 1 ? '接受邀请成功！' : '拒绝邀请成功！')
                    this.init()
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
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
