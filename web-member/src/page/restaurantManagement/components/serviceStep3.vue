<template>
    <div>
      <Row class="pb10">
        <Col span="12">
          <p class="h5">套餐列表</p>
        </Col>
        <Col span="12" class="tr">
            <Button type="primary" @click="add">添加套餐</Button>
        </Col>
      </Row>
      <Table :columns="columns" :data="data"></Table>
      <div class="pt30 tr pb30" v-if="data.length">
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
  components: { expandRow },
  data() {
    return {
      data: [],
      columns: [
        {
          type: 'expand',
          width: 50,
          render: (h, params) => {
            console.log(params)
            return h(expandRow, {
              props: {
                row: params.row
              }
            })
          }
        },
        {
          title: '套餐名称',
          key: 'name',
          align: 'center'
        },
        {
          title: '原价',
          key: 'total',
          align: 'center'
        },
        {
          title: '现价',
          key: 'price',
          align: 'center'
        },
        {
          title: '操作',
          key: 'handle',
          width: 150,
          align: 'center',
          render: (h, params) => {
            return h('div', [
              h(
                'Button',
                {
                  props: {
                    type: 'text',
                    size: 'small'
                  },
                  style: {
                    color: '#57A97B'
                  },
                  on: {
                    click: () => {
                      console.log('params', params)
                      this.$router.push({
                        path: '/restaurant/add-set-meal',
                        query: {
                          id: this.$route.query.id, // 服务id
                          mealId: params.row.setMealId // 套餐id
                        }
                      })
                    }
                  }
                },
                '编辑'
              ),
              h(
                'Button',
                {
                  props: {
                    type: 'text',
                    size: 'small'
                  },
                  style: {
                    color: '#8C8C8C'
                  },
                  on: {
                    click: () => {
                      this.del(params.row.setMealId)
                    }
                  }
                },
                '删除'
              )
            ])
          }
        }
      ],
      loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
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
      this.$api.post('/member/fishing/findFishingService', {
        pageNum: this.pageNum,
        type: '3',
        account: this.loginuserinfo.loginAccount,
        id: this.$route.query.id
      }).then(response => {
        console.log('response', response)
        if (response.code == 200) {
          response.data.list.forEach(element => {
            if (element.productList !== undefined) {
              let list = []
              // 添加菜品信息
              element.productList.forEach(item => {
                list.push({
                  id: item.id,
                  name: item.name,
                  total: '￥ ' + parseFloat(item.total).toFixed(2),
                  num: item.num,
                  price: '￥ ' + parseFloat(item.price).toFixed(2),
                })
              })
              // 把包房的信息也添加进去
              /* list.push({
                id: element.selectedRoom[0].id,
                name: element.selectedRoom[0].name,
                total: '￥ ' + element.selectedRoom[0].price,
                num: 1,
                price: '￥ ' + element.selectedRoom[0].price,
              }) */
              // 添加表格信息
              this.data.push({
                setMealId: element.setMealId,
                name: element.setMealName,
                total: '￥ ' + parseFloat(element.totalPrice).toFixed(2),
                price: '￥ ' + parseFloat(element.setMealPrice).toFixed(2),
                endDate: element.endDate,
                list: list
              })
            }
          })
        }
      })
    },
    hanhdleChangePage (page) {
      this.pageNum = page
      this.init()
    },
    // 以后在完善
    handleNext() {
      this.$router.push('/restaurant/service')
    },
    handleSave () {
      this.$router.push('/restaurantAddService/step4?id=' + this.$route.query.id)
    },
    // 上一步
    handleBack() {
      this.$router.push('/restaurantAddService/step2?id=' + this.$route.query.id)
    },
    add () {
      this.$router.push('/restaurant/add-set-meal?id=' + this.$route.query.id)
    },
    del (mealId) {
      this.$Modal.confirm({
					title: '操作提示',
					content: '确定删除该套餐？',
          onOk: () => {
            this.$api.post('/member/fishing/deleteProductManagementService', {
              setMealId: mealId,
              fishServiceId: this.$route.query.id
            }).then(response => {
              this.$Message.success('套餐删除成功！')
              this.data = []
              this.init()
            }).catch(error => {
              this.$Messag.success('套餐删除失败！')
            })
          }
      })
    }
  }
}
</script>