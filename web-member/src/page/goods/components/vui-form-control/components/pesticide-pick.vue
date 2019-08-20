<template>
  <div class="pl20 mb20">
    <div class="tc b">农药残留指标</div>
    <Row type="flex" align="middle" justify="center" class="mb20">
      <Col span="17"><p class="b">指标名</p></Col>
      <Col span="3"><p class="b">参考值</p></Col>
      <Col span="3" offset="1"><p class="b">实际值</p></Col>
    </Row>
    <Row class="mb5" type="flex" align="middle" justify="center" :key="index" v-for="(item, index) in data">
      <Col span="17"><p class="ell">{{item.name}}</p></Col>
      <Col span="3">{{item.consult}}</Col>
      <Col span="3" offset="1">
        <Input v-model="item.value"></Input>
      </Col>
    </Row>
  </div>
</template>

<script>
export default {
  props: {
    data: Array
  },
  components: {

  },
  data () {
    return {

    }
  },
  methods: {

  }
}
</script>

<style lang="scss" scoped>

</style>
