<template>
  <div class="layouts pt100">
    <Row :gutter="16">
      <Col span="6" v-for="(item, index) in list">
        <Card @click.native="handleClick(item)" style="cursor: pointer;">
          <p class="tc ptb10">{{item.label}}</p>
        </Card>
      </Col>
    </Row>
  </div>
</template>
<script>
export default {
  data () {
    return {
      list: [
        {value: 'auth', label: '配置用户向导模板'},
        {value: 'good', label: '配置商品发布模板'},
      ],
      checkData: ''
    }
  },
  methods: {
    handleClick (item) {
      this.$router.push(`/${item.value}/step1`)
    }
  },
}
</script>
<style>
</style>
