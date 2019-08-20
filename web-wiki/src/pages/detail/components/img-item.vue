<template>
<div>
  <Row :gutter="16" v-if="data.length" :style="{transition: 'all .2s cubic-bezier(1.0, 0.5, 0.8, 1.0)', height: simple ? 'auto' : height, overflow: 'hidden'}">
    <Col  :span="`${24 / col}`" v-for="(item, index) in data" :key="index" >
        <router-link :to="{
          path: `/${url}`,
          query: {
            indexid: item.indexid,
            speciesName: speciesName,
            classId: classId,
            parentId: indexid,
            speciesid: speciesid
          }
        }">
          <template v-if="item.fimagesrc">
            <img v-if="item.fimagesrc instanceof Array" :src="item.fimagesrc[0] ? `${item.fimagesrc[0]}` : './static/imgs/default-img.png'" width="100%" height="100" :class="simple ? 'mt10' : 'mt20'">
            <img v-else :src="item.fimagesrc ? `${item.fimagesrc}` : './static/imgs/default-img.png'" width="100%" height="100" :class="simple ? 'mt10' : 'mt20'">
          </template>
          <template v-else-if="item.ficon">
            <img :src="item.ficon" width="100%" height="100" :class="simple ? 'mt10' : 'mt20'">
          </template>
          <template v-else>
            <img :src="src" width="100%" height="100" :class="simple ? 'mt10' : 'mt20'">
          </template>
        </router-link>
        <div class="tc">
          <p class="ell">{{item.fname}}</p>
          <p v-if="item.judge" class="t-orange h8">好评： {{item.judge}}%</p>
          <p v-if="item.type" class="t-grey h8">{{item.type}}</p>
        </div>
    </Col>
  </Row>
  <div v-else class="pd20 tc">
    <img src="../../../assets/imgs/no-result.png" height="100" alt="">
    <p class="t-grey">暂无数据</p>
  </div>
</div>
</template>
<script>
export default {
  props: {
    url: String,
    simple: {
      type: Boolean,
      default: false
    },
    col: {
      type: Number,
      default: 6
    },
    data: Array,
    height: {
      type: String,
      default: 'auto'
    },
    speciesName: String,
    classId: String,
    speciesid: String
  },
  data: () => ({
    indexid: ''
  }),
  created () {
    this.indexid = this.$route.query.indexid
  },
  computed: {
    src (item) {
      let src = item.ficon
      if (typeof src === 'string' && src !== '') {
        return src
      } else if (src === 'object' && src.length > 0) {
        return src[0]
      } else {
        return './static/imgs/default-img.png'
      }
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
