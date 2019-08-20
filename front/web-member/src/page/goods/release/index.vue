<template>
  <section>
    <top :address="false"></top>
    <section style="background: #F9F9F9">
      <div class="bg-white">
        <div class="layouts pt30 pb20">
          <Breadcrumb>
            <BreadcrumbItem to="/index">首页</BreadcrumbItem>
            <BreadcrumbItem to="/pro/member">会员中心</BreadcrumbItem>
            <BreadcrumbItem>发布商品</BreadcrumbItem>
          </Breadcrumb>
          <p class="mt20 mb40 b" style="font-size: 20px">发布商品</p>
          <Steps :current="current" class="mb20" :class="step >= 5 ? 'cursor' : ''">
            <Step @click.native="handleStepClick(1)" title="通用商品基本信息"></Step>
            <Step @click.native="handleStepClick(2)" title="商品基本信息"></Step>
            <Step @click.native="handleStepClick(3)" title="商品营销基础信息"></Step>
            <Step @click.native="handleStepClick(4)" title="商品追溯与防伪信息"></Step>
            <Step @click.native="handleStepClick(5)" title="商品承诺信息"></Step>
          </Steps>
        </div>
      </div>
      <div class="layouts">
        <section class="pt30 pb30">
          <router-view></router-view>
        </section>
      </div>
    </section>
  </section>
</template>
<script>
import top from '~src/top'
// import goodsHead from '../components/head'
export default {
  components: {
    top,
    // goodsHead
  },
  data () {
    return {
      current: 0,
      step: 0,
      goodsId: '',
      item: {}
    }
  },
  created () {
    let path = this.$route.path
    this.current = parseInt(path.slice(path.length - 1, path.length)) - 1 || 0
    if (this.$route.query.templateId) {
      this.item.templateId = this.$route.query.templateId
    }
    if (this.$route.query.goodsId) {
      this.item.goodsId = this.$route.query.goodsId
      this.findSetp()
    }
    if (this.$route.query.categoryId) {
      this.item.productCategoryId = this.$route.query.categoryId
    }
    if (this.$route.query.templateType) {
      this.item.templateType = this.$route.query.templateType
    }
  },
  watch: {
    '$route' (to, path) {
      this.current = parseInt(to.path.slice(to.path.length - 1, to.path.length)) - 1 || 0
    }
  },
  methods: {
    handleStepClick (index) {
      if (this.step >= 5) { // 说明可以点击切换
        console.log(index)
        this.goRouter(index)
      }
    },
    goRouter (index) {
      this.$router.push(`/release-goods/step${index}?goodsId=${this.item.goodsId}&templateId=${this.item.templateId}&templateType=${this.item.templateType}&categoryId=${this.item.productCategoryId}`)
    },
    // 查询模板 步骤
    findSetp (item) {
      if (item) {
        this.item = item
      }
      this.$api.post('/shop/pushShopInfo/pushIsComplete', {
        account: this.$user.loginAccount,
        goodsId: this.item.goodsId,
      }).then(response => {
          // isComplete 判断是否有发布完成,  0、1，1 表示完成 0 表示未完成
        if (response.code === 200 && response.data.isComplete == '1') {
          this.step = 5
        } else {
          this.step = 0
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>

.cursor{
  .ivu-steps-item{
    cursor:pointer;
  }
}
</style>
