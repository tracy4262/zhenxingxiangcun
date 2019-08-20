<template>
  <div>
    <detail-title
      :title="title"
      :icon="flag ? 'chevron-down' : 'chevron-up'"
      @on-click="handleEdit">
    </detail-title>
    <img-item :url="url" :data="data.data" :speciesName="speciesName" :classId="classId" :speciesid="speciesid"></img-item>
    <div class="tr pd20" v-if="data.total > 12">
      <Page :current="data.current" :total="data.total" simple :page-size="12" @on-change="handleChange"></Page>
    </div>
    <!-- <div class="mt20 tc t-green" style="cursor:pointer" @click="handleFold" v-if="data.length > 12">
      <Icon :type="flag ? 'ios-arrow-down' : 'ios-arrow-up'" :size="36" style="line-height:1"></Icon>
      <p>{{flag ? '展开' : '收起'}}</p>
    </div> -->
  </div>
</template>
<script>
import detailTitle from '~components/title'
import imgItem from './img-item'
export default {
  props: {
    url: String,
    title: String,
    data: {
      type: Object,
      default: {}
    },
    speciesName: String,
    classId: String,
    speciesid: String
  },
  components: {
    detailTitle,
    imgItem
  },
  data: () => ({
    height: 'auto',
    flag: false,
    row: ''
  }),
  created () {
  },
  methods: {
    handleChange (e) {
      this.$emit('on-change', e)
    },
    // 折叠
    // handleFold () {
    //   this.flag = !this.flag
    //   this.height = this.flag ? '146px' : this.row
    // },
    // 编辑
    handleEdit () {
      this.$emit('on-edit')
    }
  },
  watch: {
    // data (newVal, oldVal) {
    //   this.row = `${146 * (this.data.length % 6 >= 0 ? Math.round(this.data.length / 6) + 1 : Math.round(this.data.length / 6))}px`
    //   this.height = this.row
    // }
  }
}
</script>
<style lang="scss" scoped>
</style>
