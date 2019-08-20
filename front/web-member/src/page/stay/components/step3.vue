<template>
<div>
  <Row class="pb10">
    <Col span="12">
    <p class="h5">套餐列表</p>
    </Col>
    <Col span="12" class="tr">
    <a :href="`/stay/add-set-meal?id=${id}`">
      <Button type="primary">添加套餐</Button>
    </a>
    </Col>
  </Row>
  <Table :columns="columns" :data="data"></Table>
    <div class="pt30 tc pb30" v-if="data.length">
      <Page :total="total" :page-size="pageSize"  @on-change="hanhdleChangePage"></Page>
    </div>
  <div class="tc pt20">
    <Button type="primary" @click="handleBack">上一步</Button>
    <Button type="primary" @click="handleSave">下一步</Button>
    <Button type="text" @click="handleNext">以后再完善</Button>
  </div>
</div>
</template>
<script>
import expandRow from './table-expand.vue'
export default {
  components: {
    expandRow
  },
  data() {
    return {
      id: '',
      data: [],
      columns: [{
          type: 'expand',
          width: 50,
          render: (h, params) => {
            return h(expandRow, {
              props: {
                row: params.row
              }
            })
          }
        },

        {
          title: '套餐名称',
          key: 'setMealName',
          align: 'center'
        },
        {
          title: '原价',
          key: 'totalPrice',
          align: 'center',
          render: (h, params) => {
              return h('span',`￥ ${parseFloat(params.row.totalPrice).toFixed(2)}`)
          }
        },
        {
          title: '现价',
          key: 'setMealPrice',
          align: 'center',
          render: (h, params) => {
              return h('span',`￥ ${parseFloat(params.row.setMealPrice).toFixed(2)}`)
          }
        },
        {
          title: '操作',
          key: 'handle',
          width: 150,
          align: 'center',
          render: (h, params) => {
            return h('div', [
              h(
                'Button', {
                  props: {
                    type: 'text',
                    size: 'small'
                  },
                  style: {
                    color: '#57A97B'
                  },
                  on: {
                    click: () => {
                      this.$router.push(`/stay/add-set-meal?id=${this.id}&activeId=${params.row.setMealId}`)
                    }
                  }
                },
                '编辑'
              ),
              h(
                'Button', {
                  props: {
                    type: 'text',
                    size: 'small'
                  },
                  style: {
                    color: '#8C8C8C'
                  },
                  on: {
                    click: () => {
                      this.handleDel(params.row)
                    }
                  }
                },
                '删除'
              )
            ])
          }
        }
      ],
      loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
      pageSize: 10,
      pageNum: 1,
      total: 0
    }
  },
  created() {
    this.id = this.$route.query.id
    this.account = this.loginUser.loginAccount
    this.handleInit()
  },
  methods: {
    // 翻译
    hanhdleChangePage (e) {
      this.pageNum = e
      this.handleInit()
    },
    // 初始化查询
    handleInit () {
      this.$api.post('/member/fishing/findFishingService', {pageNum: this.pageNum, type: '4', pageSize: this.pageSize,account: this.account,id: this.id}).then(response => {
        if (response.code == 200) {
          this.data = []
          this.total = 0
          if (response.data.list[0] && response.data.list[0].productList) {
            this.data = response.data.list
            this.total = response.data.total
          }
        }
      })
    },
    // 编辑套餐
    handleEdit () {
      this.$router.push({
        path: '/stay/add-set-meal',
        query: {
          id: this.id,
          activeId: this.activeId
        }
      })
    },
    // 删除套餐
    handleDel (row) {
      this.$Modal.confirm({
					title: '操作提示',
					content: '确定删除该套餐？',
          onOk: () => {
            this.$api.post('/member/fishing/deleteProductManagementService', {fishServiceId: this.id, setMealId: row.setMealId}).then(response => {
            if (response.code === 200) {
              this.$Message.success('删除成功')
              if (this.pageNum !== 1 && this.data.length == 1) {
                  this.pageNum -=1
                }
                this.handleInit()
            } else {
              this.$Message.error('删除失败')
            }
          })
        }
      })
    },
    // 上一步
    handleBack() {
      this.$router.push('/stayAddService/step2?id=' + this.id)
    },
    // 下一步
    handleSave() {
      this.$router.push('/stayAddService/step4?id=' + this.id)
    },
    // 以后在完善
    handleNext() {
      this.$router.push('/stay/service')
    },
  }
}
</script>