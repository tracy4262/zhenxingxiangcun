<template>
  <section class="evaluation">
    <div>
      <!-- 3 好评。 2中评。1差评 -->
      <Button :type="active == '' ? 'primary' : 'text'" @click="handleFilterClick('')" size="small" class="mr10">全部评价({{all}})</Button>
      <Button :type="active == 3 ? 'primary' : 'text'" @click="handleFilterClick(3)" size="small"  class="mr10">好评({{good}})</Button>
      <Button :type="active == 2 ? 'primary' : 'text'" @click="handleFilterClick(2)" size="small" class="mr10">中评({{medium}})</Button>
      <Button :type="active == 1 ? 'primary' : 'text'" @click="handleFilterClick(1)" size="small" class="mr10">差评({{bad}})</Button>
    </div>
    <div v-if="data.length && isShow">
      <ul class="list">
        <li v-for="(item, index) in data" :key="index">
          <Row>
            <Col span="2" class="tc">
              <img :src="item.avatar" width="48" height="48" class="mt10" v-if="item.avatar"/>
              <img src="../../../../img/default_header.png" width="48" height="48" class="mt10" v-else/>
              <p class="mt5 ell" style="font-size:12px; color:#666;cursor: pointer;" :title="item.name">{{item.name}}</p>
            </Col>
            <Col span="22" class="pl20">
              <Rate disabled :value="item.rate" allow-half></Rate>
              <p class="mt10 mb10" style="color:#515151;">{{item.content}}</p>
              <!-- <viewer :images="item.list" :options="{title: false}">
                <img class="pd5" v-for="src in item.list" :src="src" width="80" height="80" :key="src">
              </viewer> -->
              <div class="t-grey mt15" v-if="item.list.length" style="font-size:12px; color:#666;">
                <span class="mr5" v-if="item.list[0].name">{{item.list[0].name}}:</span>
                <span>{{item.list[0].content}}</span>
                <div>
                <!-- <Rate disabled :value="item.list.rate"></Rate> -->
                </div>
              </div>
            </Col>
          </Row>
        </li>
      </ul>
      <div class="mt20 tc">
        <Page :total="total" :page-size="pageSize" @on-change="handleChange"></Page>    
      </div>
    </div>
    <div v-if="data.length == 0 && isShow">
      <div  class="tc pt30 pb50">
          <img src="../../../../img/no-content.png"> 
          <p style="margin-top: 10px;">暂无相关评价</p>
      </div>
    </div>
  </section>
</template>

<script>
import 'viewerjs/dist/viewer.css'
import viewer from 'v-viewer/src/component.vue'
export default {
  components: {
    viewer
  },
  data () {
    return {
      pageSize: 10,
      total: 0,
      pageNum: 1,
      all: 0,
      good: 0,
      medium: 0,
      bad: 0,
      active: '',
      data: [],
      commodityId: '',
      sellerAccount: '',
      isShow: false
    }
  },
  created () {
    this.commodityId = this.$route.query.id
    this.sellerAccount = this.$route.query.account
    this.handleGetInit()
    this.handleGetDetail('')
  },
  methods: {
    // 获取评论数量
    handleGetInit () {
      this.$api.post('/portal/shopCommdoity/findCommentNum', {commodityId: this.commodityId, account: this.sellerAccount}).then(response => {
        if (response.code == 200) {
          this.all = response.data.commentNum
          this.good = response.data.praise
          this.medium = response.data.review
          this.bad = response.data.negative
        }
      })
    },
    // 获取评论内容
    // reputation 不传。 全部。   3 好评。 2中评。1差评
    handleGetDetail () {
      this.$api.post('/portal/shopCommdoity/findComment', 
      {commodityId: this.commodityId, account: this.sellerAccount, reputation: this.active, pageSize: this.pageSize, pageNum: this.pageNum,formAccount:0}).then(response  => {
        if (response.code == 200) {
          this.isShow = true
          this.data = response.data.list
          this.total = response.data.total
        }
      })
    },
    handleFilterClick (n) {
      this.active = n
      // 3 好评。 2中评。1差评
      // this.handleGetDetail(n)
      this.handleChange(1)
    },
    inited ($event, index) {
      this.$refs[`viewer${index}`].$viewer = $event
    },
    handleViewerClick (index) {
      this.$nextTick(() => {
        this.$refs[`viewer${index}`].$viewer.show()
      })
    },
    // 分页
    handleChange (e) {
      this.pageNum = e
      this.handleGetDetail()
    }
  }
}
</script>

<style lang="scss" scoped>
.list{
  li{
    padding: 30px 0;
    &:not(:last-child) {
      border-bottom: 1px solid #F4F4F4;
    }
  }
}
// .active{
//   color: #00c587;
// }
</style>