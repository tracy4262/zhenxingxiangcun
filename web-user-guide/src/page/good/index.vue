<template>
  <div>
  <wrapper :data="steps" :type="step" class="new-auth" @on-router="goRouter"></wrapper>
  </div>
</template>
<script>
import wrapper from './components/wrapper'
export default {
  components: {
    wrapper
  },
  data: () => ({
    step: 5, // 管理员默认可以切换
    list: {},
    item: {},
    steps: ['选择模板', '通用商品基本信息', '商品基本信息', '商品营销基础信息', '商品追溯防伪信息', '商品承诺信息']
  }),
  created () {
    let item = {}
    if (this.$route.query.templateId) {
      item.id = this.$route.query.templateId
    }
    if (this.$route.query.categoryId) {
      item.productCategoryId = this.$route.query.categoryId
    }
    if (this.$route.query.templateType) {
      item.templateType = this.$route.query.templateType
      this.findSetp(item)
    }
    if (this.$route.query.templateName) {
      item.templateName = this.$route.query.templateName
    }
    this.item = item
  },
  methods: {
    goRouter (index) {
      this.$router.push(`/good/step${index}?templateName=${this.item.templateName}&templateId=${this.item.id}&templateType=${this.item.templateType}&categoryId=${this.item.productCategoryId}`)
    },
    // 查询模板 步骤
    findSetp (item) {
      this.item = item
    },
  }
}
</script>
<style lang="scss">
.layouts{
  width: 1200px;
  margin: 0 auto;
}
</style>
