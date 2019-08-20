<template>
  <div>
    <Row type="flex" align="middle"  class="mb20">
      <Col span="16"><p class="b">指标名</p></Col>
      <Col span="3"><p class="b">参考值</p></Col>
      <Col span="4" offset="1"><p class="b">实际值</p></Col>
    </Row>
    <Row class="mb5" type="flex" align="middle" :key="index" v-for="(item, index) in data">
      <Col span="16"><p class="ell">{{item.name}}</p></Col>
      <Col span="3">{{item.consult}}</Col>
      <Col span="4" offset="1">
        {{item.value}}
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
