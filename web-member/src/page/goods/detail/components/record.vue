<template>
  <div>
    <Table :columns="columns" :data="data"></Table>
    <div class="mt20 tc">
      <Page :total="total" :page-size="pageSize" :current="pageNum" @on-change="handleChange"></Page>    
    </div>
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {
    unit: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      columns: [{
        title: '买家',
        key: 'name'
      }, {
        title: '数量',
        key: 'number',
        render: (h, params) => {
          return h('span', `${params.row.number} ${params.row.product_output_unit}`)
        }
      }, {
        title: '成交时间',
        key: 'update_time',
        render: (h, params) => {
          return h('span', {
          },this.moment(params.row.update_time).format('YYYY-MM-DD H:mm'))
        }
      }, {
        title: '评价打分',
        key: 'star',
        align: 'center',
        render: (h, params) => {
          if (params.row.rate) {
            return h('Rate', {
              attrs: {
                disabled: true,
                value: params.row.rate
              },
              props: {
                allowHalf: true
              }
            })
          } else {
            return h('span', {
              domProps: {
                innerHTML: '暂未评价'
              }
            })
          }
        }
      }],
      data: [],      
      commodityId: '',
      sellerAccount: '',
      pageSize: 10,
      pageNum: 1,
      total: 0
    }
  },
  created () {
    this.commodityId = this.$route.query.id
    this.sellerAccount = this.$route.query.account
    this.handleGetInit()
  },
  methods: {
    handleGetInit () {
      this.$api.post('/portal/shopCommdoity/findMonthList', {
        commodityId: this.commodityId,
        pageSize: this.pageSize,
        pageNum: this.pageNum
      }).then(response => {
        if(response.code == 200) {
          this.data = response.data.list
          this.total = response.data.total
        }
      })
    },
    // 分页
    handleChange (e) {
      this.pageNum = e
      this.handleGetInit()
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
