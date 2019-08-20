<template>
  <div class="pd15">
    <Row type="flex" align="middle">
      <Col span="18">相关产品</Col>
      <Col span="6">
        <Button size="small" type="text"><span class="t-grey h8">查看全部</span></Button>
      </Col>
    </Row>
    <!-- <img-item :data="data" :col="3" simple></img-item> -->
  </div>
</template>
<script>
// import imgItem from './img-item'
export default {
  props: {
    data: Array
  },
  components: {
    // imgItem
  },
  data: () => ({
  })
}
</script>
<style lang="scss" scoped>
</style>
