<template>
  <div>
     <Modal
        v-model="isShow"
        :mask-closable="false"
        width="1000px"
        title="好友请求">
          <div>
            <Table border :columns="columns7" :data="data"></Table>
            <div class="tc pt20" v-if="data.length">
                  <Page :total="total" @on-change="getNextPage" :page-size="pageSize" :current="pageNum"></Page>
            </div>
          </div>
        <div slot="footer" class="tc">
        </div>
    </Modal>
    <groupList ref="groupList" @on-save="onSave"></groupList>
  </div>
</template>
<script>
import groupList from './groupList'
export default {
    components:{
        groupList
    },
  data () {
    return {
      isShow: false,
      data: [],
      columns7: [
          {
            title: '请求好友名称',
            key: 'groupFriendAccountName',
            align: 'center',
          },
          {
            title: '请求时间',
            key: 'createTime',
            align: 'center',
            render: (h, params) => {
                return h('span',`${this.moment(params.row.createTime).format('YYYY-MM-DD HH:mm')}`)
            }
          },
          {
            title: '操作',
            key: 'action',
            width: 350,
            align: 'center',
            render: (h, params) => {
                return h('div', [
                    h('Button', {
                        props: {
                            type: 'text',
                            size: 'small'
                        },
                        style: {
                            marginRight: '5px'
                        },
                        on: {
                            click: () => {
                                this.$router.push(`/portals/index?uid=${params.row.account}`)
                            }
                        }
                    }, '查看详情'),
                    h('Button', {
                        props: {
                            type: 'text',
                            size: 'small'
                        },
                        style: {
                            marginRight: '5px'
                        },
                        on: {
                            click: () => {
                                this.addFriend(params.row)
                            }
                        }
                    }, '接受'),
                     h('Button', {
                        props: {
                            type: 'text',
                            size: 'small'
                        },
                        style: {
                            marginRight: '5px'
                        },
                        on: {
                            click: () => {
                                this.del(params.row)
                            }
                        }
                    }, '拒绝')
                ]);
              }
          }
      ],
      pageSize: 10,
      pageNum: 1,
      total: 0,
      focusData: []
    }
  },
  created() {
      this.getList()
  },
  methods: {
    getList () {
        let data = {
            pageSize: this.pageSize,
            pageNum: this.pageNum,
            account: this.$user.loginAccount,
            type: '1',
            invite: '0'
        }
        // 查询好友邀请列表
        this.$api.post('/member/relationshipCircle/findGroupFriendList', data).then(response => {
            if (response.code === 200) {
                this.data = response.data.dataList
                this.total = response.data.total
                this.$emit('get-total', this.total)
            }
        })
    },
    onSave (data) {
        let groupId = data[0].id
      // invite 0邀请他  1 接受他  
        this.$api.post('/member/relationshipCircle/insertGroupFriendInfo', 
        {account: this.$user.loginAccount, invite: '1', groupId: groupId, dataList: this.focusData}).then(response => {
            if (response.code === 200) {
                this.$Message.success('你们已经是好友了！')
                this.$parent.$refs['tree'].init()
                this.$parent.$refs['tree'].reload = true
                this.$refs['groupList'].isShow = false
                if (this.total == 1) {
                    this.getNextPage(1)
                } else if(this.data.length == 1) {
                    this.pageNum--
                    this.getList()
                } else {
                    this.getNextPage(1)
                }
            } else {
                this.$Message.error('操作失败！')
            }
        })
    },
    // 接受邀请
    addFriend (item) {
        this.$refs['groupList'].init()
        this.focusData = [item]
    },
    // 拒绝邀请
    del (item) {
        this.$Modal.confirm({
            title: '操作提示',
            content: '是否确认拒绝邀请？',
            onOk:()=>{
                let data = {
                    account: this.$user.loginAccount,
                    id: item.id,
                }
                // 拒绝好友邀请
                this.$api.post('/member/relationshipCircle/deleteFriendInfo', data).then(response => {
                    if (response.code === 200) {
                        this.$Message.success('操作成功！')
                        if (this.total == 1) {
                            this.getNextPage(1)
                        } else if(this.data.length == 1) {
                            this.pageNum--
                            this.getList()
                        } else {
                            this.getNextPage(1)
                        }
                    } else {
                        this.$Message.error('操作失败！')
                    }
                })
            },
            okText:'确定',
            cancelText:'取消'
        })
    },
    getNextPage (e) {
        this.pageNum = e
        this.getList()
    },
    init () {
      this.isShow = true
    }
  }
}
</script>
<style>
</style>


