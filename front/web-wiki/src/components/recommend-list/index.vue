<template>
  <div style="border: 1px solid #EBEBEB">
    <album :name="speciesName" :data="albumData"></album>
    <related-word class="mb15" :data="relevantLemma"></related-word>
    <!-- <related-product class="mb15" :data="relevantProductInfo"></related-product> -->
    <related-expert class="mb15" :data="relevantExpertInfo"></related-expert>
    <related-company :data="relevantCorpInfo"></related-company>
  </div>
</template>
<script>
import album from './album'
import relatedWord from './related-word'
// import relatedProduct from './related-product'
import relatedExpert from './related-expert'
import relatedCompany from './related-company'
export default {
  props: {
    name: String
  },
  components: {
    album,
    relatedWord,
    // relatedProduct,
    relatedExpert,
    relatedCompany
  },
  data: () => ({
    albumData: [],
    // 相关词条
    relevantLemma: [],
    // 相关专家
    relevantExpertInfo: [],
    // 相关产品
    relevantProductInfo: [],
    // 相关企业
    relevantCorpInfo: [],
    classId: '',
    speciesName: ''
  }),
  created () {
    this.indexid = this.$route.query.indexid
    this.speciesName = this.$route.query.speciesName
    this.classId = this.$route.query.classId
    if (this.speciesName) {
      this.getRelevantInfo(this.speciesName)
    }
  },
  watch: {
    name (newVal, oldVal) {
      this.getRelevantInfo(newVal)
    }
  },
  methods: {
    getRelevantInfo (newVal) {
      this.$api.post('wiki/api/species/getRelevantInfo', {
        speciesName: newVal,
        classId: this.classId
      }).then(response => {
        if (response.code === 200) {
          this.relevantLemma = response.data.relevantLemma
          this.relevantExpertInfo = response.data.relevantExpertInfo
          this.relevantProductInfo = response.data.relevantProductInfo
          this.relevantCorpInfo = response.data.relevantCorpInfo
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
