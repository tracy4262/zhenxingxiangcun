<template>
  <div class="pd15">
    <Row type="flex" align="middle">
      <Col span="18">相关词条</Col>
      <Col span="6">
        <Button size="small" type="text" @click="handleAll"><span class="t-grey h8">查看全部</span></Button>
      </Col>
    </Row>
    <div v-if="data.length">
      <Row :gutter="16" >
        <Col :span="8" v-for="(item, index) in data" :key="index">
          <router-link :to="{path: `/detail?indexid=${item.indexid}&speciesid=${item.fid}&classId=${item.fclassid}`}">
            <img :src="item.ficon.length ? `${item.ficon[0]}` : './static/imgs/default-img.png'" width="100%" height="80px" class="mt20">
          </router-link>
          <div class="tc">
            <p class="ell" :title="item.fname">{{item.fname}}</p>
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
    data: Array
  },
  data: () => ({
  }),
  created () {
  },
  methods: {
    handleAll () {
      this.$router.push('/')
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
