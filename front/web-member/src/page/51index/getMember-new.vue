<template>
<div>
  <search @on-search="onSearch" :count="total"></search>
  <div class="pt30 pb20">
    <Row :gutter="32" type="flex" algin="middle">
      <Col span="6" v-for="(item, index) in data" :key="index" class="pb20" style="cursor: pointer;">
          <Card @click.native="toLink(item)" :padding="0">
            <img v-if="item.avatar" :src="item.avatar" style="width:100%" height="230px">
            <img v-else src="../../../static/img/user-icon-big.png" style="width:100%" height="230px">
            <p class="ell pd10">{{item.memberName}} </p>
          </Card>
      </Col>
    </Row>
  </div>
  <div v-if="data.length == 0 && isShow" class="tc pt30 pb50">
    <img src="../../img/no-content.png">
    <p style="margin-top: 10px;">暂无相关会员</p>
  </div>
  <div class="mt30 mb50 tc" v-if="data.length">
      <Page :total="total" :page-size="pageSize" :current="pageNum" @on-change="handleChangePage"></Page>
  </div>
</div>
</template>

<script>
import search from "./components/memberSearch";
export default {
  components: {
    search
  },
  data() {
    return {
      isShow: false,
      data: [{}],
      pageSize: 12,
      pageNum: 1,
      total: 0,
      info: {
        memberName: '', // 名字
        address: '', // 行政区划
        memberType: '', // 会员类别
        product: '', // 产品
        industry: '', // 行业
        species: '', // 物种
        service: '', // 服务
        adeptField: '', //擅长领域
        expertType: '', // 专家类型
        status: '' // 非专家0 专家1 全部空
      }
    };
  },
  created() {
    this.init(1)
  },
  methods: {
    toLink (item) {
      console.log('account', item.account)
      this.$toPortals(item.account)
    },
    init (page) {
      this.$api.post(`/member/member/find/${page}`, this.info).then(response => {
        console.log('respo', response)
        if (response.code === 200) {
          this.isShow = true
          this.data = response.data.list
          this.total = response.data.total
        } else {
          this.$Message.error('服务器异常！')
        }
      }).catch(error => {
        this.$Message.error('服务器异常！')
      })
    },
    onSearch (e) {
      this.info.memberName = e.keyword
      this.info.address = e.district
      this.info.memberType = e.member_class
      this.info.product = e.product
      this.info.industry = e.trade
      this.info.species = e.species
      this.info.service = e.service
      this.info.status = e.member_class === '个人' ? (e.isExpert === '是' ? 1 : 0) : ''
      this.info.adeptField = e.expertise
      this.info.expertType = e.expertType
      this.init(1)
    },
    handleChangePage (e) {
      this.pageNum = e
      this.init(e)
    }
  }
}
</script>

<style lang='scss' scoped>

</style>
