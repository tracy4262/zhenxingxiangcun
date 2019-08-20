<template>
  <div>
     <Modal v-model="show"
     width="750"
     heigth="700px"
     title="我要问"
     :mask-closable="false"
     :styles="{top: '20px'}">
     <div>
      <Row :gutter="16" class="mt10">
        <Col span="6" v-for="(item, index) in expertTeam" class="tc pb15" @click.native="expertDetail(item)" style="cursor: pointer;">
            <img :src="item.personalPhoto" style="width: 100%; height: 125px;">
            <p style="font-size: 14px; color: #4A4A4A; line-height: 20px;" class="mt5 ell" :title="item.expertName">{{ item.expertName }}</p>
            <p style="color: #9B9B9B; line-height: 17px;" class="ell mt5" :title="item.title">{{ item.title }}</p>
        </Col>
      </Row>
      <Page v-if="expertTeam.length" class="tc" :total="total" :page-size="pageSize" :current="pageNum" @on-change="pageChange" />
      <h2 class="ml20" v-if="expertTeam.length === 0"> 暂无相关内容</h2>
      </div>
     <div slot="footer"></div>
    </Modal>
  </div>
</template>
<script>
export default {
  data () {
    return {
      show: false,
      expertTeam: [],
      keyWord: '',
      pageSize: 10,
      pageNum: 1,
      total: 0,
      loginAccount: ''
    }
  },
  created() {
    this.loginAccount = this.$route.query.uid
    this.init()
  },
  methods: {
    pageChange (e) {
      this.pageNum = e
      this,init()
    },
    init () {
      this.$api.post('/member-reversion/employ/manage', {
          type: '1',
          account: this.loginAccount,
          expertName: '',
          location: '',
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          relatedIndustry: '',
          relatedSpecies: '',
      }).then(response => {
          if (response.code === 200) {
              this.expertTeam = response.data.list
              this.total = response.data.total
          }
      }).catch(error => {
          this.$Message.error('服务器异常！')
      })
    },
    expertDetail (item) {
      this.$toPortals(item.account)
    }
  },
}
</script>
<style>

</style>
