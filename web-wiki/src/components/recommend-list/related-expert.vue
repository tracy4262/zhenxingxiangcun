<template>
  <div class="pd15">
    <Row type="flex" align="middle">
      <Col span="18">相关专家</Col>
      <Col span="6">
        <Button size="small" type="text" @click="handleAll"><span class="t-grey h8">查看全部</span></Button>
      </Col>
    </Row>
    <div v-if="data.length">
      <Row :gutter="16" type="flex" align="middle" :key="index" v-for="(item, index) in data" class="mt10" >
        <Col span="8">
          <a :href="`${$url.serverUrl}expertGate/index?uid=${item.account}`">
            <img :src="item.personal_photo" width="100%" alt="">
          </a>
        </Col>
        <Col span="16">
          <p class="b mb10 ell" :title="item.display_name">{{item.display_name}}</p>
          <div class="t-grey h8">
            <p class="ell mb5">擅长领域：{{item.expert_type}}</p>
            <p class="ell">擅长物种：{{item.adept_species}}</p>
          </div>
        </Col>
      </Row>
    </div>
    <div class="mt20 tc" v-else>
      <img src="../../assets/imgs/no-result.png" height="60" alt="">
      <p class="t-grey h8">暂无数据</p>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    data: {
      type: Array,
      default: []
    }
  },
  data: () => ({
  }),
  methods: {
    handleAll () {
      window.location.href = `${this.$url.serverUrl}pro/expertList`
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
