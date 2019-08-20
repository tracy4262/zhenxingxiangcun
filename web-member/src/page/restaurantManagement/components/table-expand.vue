<template>
  <div>
    <Row>
      <Col span="2">
        套餐内容
      </Col>
      <Col span="22">
        <Table size="small" :columns="columns" :data="row.list"></Table>
        <div class="tr mt10">合计：{{row.total}} 元</div>
      </Col>
    </Row>
    <Row class="mt10">
      <Col span="2">
        服务费用
      </Col>
      <Col span="22">
        原价： {{row.total}} 元<span class="ml15">团购价：{{row.price}} 元</span>
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
        key: 'price'
      },
      {
        title: '数量/规格',
        key: 'num'
      },
      {
        title: '小计',
        key: 'total'
      }]
    }
  }
}
</script>

<style lang="scss" scoped>
</style>
