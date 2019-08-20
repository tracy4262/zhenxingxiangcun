<template>
  <div>
    <detail-title
      :title="title"
      @on-click="handleEdit">
    </detail-title>
      <p class="content pt20" v-if="data">
        {{data}}
      </p>
     <div v-else class="pd20 tc">
      <img src="../../../assets/imgs/no-result.png" height="100" alt="">
      <p class="t-grey">暂无数据</p>
    </div>
  </div>
</template>
<script>
import detailTitle from '~components/title'
export default {
  props: {
    title: {
      type: String,
      default: ''
    },
    data: {
      type: String,
      default: ''
    }
  },
  components: {
    detailTitle
  },
  data: () => ({

  }),
  methods: {
    handleEdit () {
      this.$emit('on-edit')
    }
  }
}
</script>
<style lang="scss" scoped>
.content{
  text-indent: 2em;
  line-height: 24px;
  font-size: 14px;
  margin: 10px 0;
  color: #4A4A4A;
  text-align: justify;
}
</style>
