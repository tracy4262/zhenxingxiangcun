<template>
  <div>
    <Row>
      <Col span="2">
        套餐内容
      </Col>
      <Col span="22">
        <Table size="small" :columns="columns" :data="row.productList"></Table>
        <div class="tr mt10">合计：￥{{parseFloat(row.setMealPrice).toFixed(2)}}</div>
      </Col>
    </Row>
    <Row class="mt10">
      <Col span="2">
        服务费用
      </Col>
      <Col span="22">
        原价： ￥{{parseFloat(row.totalPrice).toFixed(2)}} <span class="ml15">团购价：￥{{parseFloat(row.setMealPrice).toFixed(2)}}</span>
      </Col>
    </Row>
    <Row class="mt10">
      <Col span="2">
        截止日期
      </Col>
      <Col span="22">
        {{row.endDate}}
      </Col>
    </Row>
  </div>
</template>

<script>
export default {
  props: {
    row: Object
  },
  data () {
    return {
      columns: [{
        title: '名称',
        key: 'name'
      },
      {
        title: '单价',
        key: 'price',
        render: (h, params) => {
            return h('span',`￥ ${parseFloat(params.row.price).toFixed(2)}`)
        }
      },
      {
        title: '数量/规格',
        key: 'num'
      },
      {
        title: '小计',
        key: 'total',
        render: (h, params) => {
            return h('span',`￥ ${parseFloat(params.row.total).toFixed(2)}`)
        }
      }]
    }
  }
}
</script>

<style lang="scss" scoped>
</style>
