<!--
    作者：chenqim
    时间：2018-12-18
    描述：待审页面
-->
<template>
    <div class="pd20" style="min-height: 500px;">
        <Button :type="activeIndex === 0 ? 'primary' : 'text'" @click="apply">申请代理审核</Button>
        <Button :type="activeIndex === 1 ? 'primary' : 'text'" @click="cancel">取消代理审核</Button>
        <Form ref="form" :model="form" :label-width="80" label-position="left" class="mt20">
          <Row :gutter="36">
            <Col span="12">
              <FormItem label="会员名称">
                <Input v-model="form.memberName"></Input>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem label="用户名">
                <Input v-model="form.account"></Input>
              </FormItem>
            </Col>
          </Row>
          <Row :gutter="36">
            <Col span="12">
              <FormItem label="申请时间">
                <DatePicker v-model="form.time" type="daterange" placement="bottom-end" style="width: 100%" @on-change="dateChange"></DatePicker>
              </FormItem>
            </Col>
            <Col span="12">
              <Button type="primary" @click="query">查询</Button>
            </Col>
          </Row>
        </Form>
        <Table class="mt20" border :columns="columns" :data="data"></Table>
        <div class="mt20 tr" v-if="data.length !== 0">
            <Page :total="total" :page-size="pageSize" :current="pageNum" @on-change="pageChange" />
        </div>
    </div>
</template>
<script>
export default {
    name: 'pendingApproval',
    components: {

    },
    data () {
        return {
            form: {
              memberName: '',
              account: '',
              time: [],
              lowTime: '',
              upperTime: ''
            },
            activeIndex: 0,
            columns: [
                {
                    type: 'index',
                    title: '序号',
                    width: 60
                },
                {
                    title: '会员名称',
                    key: 'memberName',
                    width: 120
                },
                {
                    title: '用户名',
                    key: 'account',
                    width: 140
                },
                {
                    title: '农事无忧账号',
                    key: 'nswyId',
                    width: 200
                },
                {
                    title: '申请时间',
                    key: 'time',
                    width: 190
                },
                {
                    title: '状态',
                    key: 'status',
                    width: 100,
                    render: (h, params) => {
                        return h('div', [
                            h('div', {
                                style: {
                                    backgroundColor: params.row.status === '审核中' ? '#f5a622' : params.row.status === '拒绝' ? '#f24d61' : '#00c687',
                                    width: '8px',
                                    height: '8px',
                                    borderRadius: '50%',
                                    display: 'inline-block'
                                }
                            }),
                            h('div', {
                                style: {
                                    display: 'inline-block',
                                    marginLeft: '5px'
                                }
                            }, params.row.status)
                        ]);
                    }
                },
                {
                    title: '审核意见',
                    key: 'auditOpinion'
                }
            ],
            data: [],
            total: 0,
            pageSize: 10,
            pageNum: 1
        }
    },
    created () {
        this.init(1)
    },
    methods: {
        dateChange (arr) {
          this.form.lowTime = arr[0]
          this.form.upperTime = arr[1]
        },
        query () {
          this.pageNum = 1
          this.init(this.activeIndex === 0 ? 1 : 0)
        },
        init (type) {
            this.$api.post('/member/reversionProxy/waitProxy', {
                pageNum : this.pageNum,
                pageSize : this.pageSize,
                proxyAccount : this.$user.loginAccount,  //代理人账号
                type : type,  //0:取消代理，1:代理
                lowTime: this.form.lowTime, // 时间下限
                upperTime: this.form.upperTime, // 时间上限
                memberName: this.form.memberName, // 会员名称
                userName: this.form.account // 用户名
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
        apply () {
            this.activeIndex = 0
            this.pageNum = 1
            this.form = {
              memberName: '',
              account: '',
              time: [],
              lowTime: '',
              upperTime: ''
            }
            this.init(1)
        },
        cancel () {
            this.activeIndex = 1
            this.pageNum = 1
            this.form = {
              memberName: '',
              account: '',
              time: [],
              lowTime: '',
              upperTime: ''
            }
            this.init(0)
        },
        pageChange (page) {
            this.pageNum = page
            this.init(this.activeIndex === 0 ? 1 : 0)
        }
    }
}
</script>
<style lang="scss" scoped>

</style>
