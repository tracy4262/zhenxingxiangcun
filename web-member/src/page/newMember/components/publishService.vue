<template>
  <div>
     <Modal v-model="show"
     width="750"
     heigth="700px"
     title="请选择发布服务类型"
     :mask-closable="false"
     :styles="{top: '20px'}">
     <!-- <div slot="header"></div> -->
     <div>
       <Row>
          <Col span="12">
          <Input search v-model="keyWord" @on-search="onSearch" />
          </Col>
       </Row>
       <Row :gutter="16" class="pt20">
          <Col span="6" v-for="(item, index) in data" class="pb10 tc" @click.native="handlePublishService(item)">
            <Card >
              <div><img :src="item.logo"  width="64px" height="50px"></div>
              <Tooltip placement="top" :content="item.appName" :delay="1000" class="mt20">
                  <div class="app-name ell">{{ item.appName }}</div>
              </Tooltip>
            </Card>
            <!-- <img v-if="item.logo" :src="item.logo" alt="" width="100%" height="140px">
            <img v-else src="../../../../static/img/goods-list-no-picture1.png" alt="" width="100%" height="140px">
            <p class="pt10 tc pb5" style="font-size:14px;">{{item.appName}}</p> -->
          </Col>
           <Col span="24" v-if="!data.length">
            <p class="pd20 tc" style="font-size:14px;">暂无相关数据</p>
          </Col>
       </Row>
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
      datas: [],
      data: [],
      keyWord: ''
    }
  },
  created() {
    // 服务应用 查询
    this.init('3', '', '')
  },
  methods: {
    init (level, recommend, flag) {
      this.$api.post('/member/applicationCentrality/findList',
        {
          level: level, // level 0 基础 1 通用 2 高级
          recommend: recommend, // recommend 1 推荐 0 不推荐
          account: this.$user.loginAccount,
          templateId: this.templateId,
          appName: this.keyword,
          flag: flag // 1 最新，0 最热
        }
      ).then(response => {
        if (response.code === 200) {
          // level 0 基础 1 通用 2 高级 3 服务
          if (level === '3') {
            this.datas = response.data.filter( (item) => {
              if (item.url === '/fishing/service' || item.url === '/picking/service') {
                return item
              }
            })
            this.data = response.data.filter( (item) => {
              if (item.url === '/fishing/service' || item.url === '/picking/service') {
                return item
              }
            })
          }
        }
      })
    },
    handlePublishService (item) {
      console.log(item)
      let url = ``
      if (item.url === '/fishing/service') {
        url = '/addService'
        this.$router.push(`${url}/step1`)
      }
      if (item.url === '/picking/service') {
        url = '/pickingAddService'
        this.$router.push(`${url}/step1`)
      }
      if (item.url === '/scenicSpot/ticket') {
        url = '/scenicSpotAddService'
        this.$router.push(`${url}/step1`)
      }
      if (item.url === '/stay/roomType') {
        url = '/stayAddService'
        this.$router.push(`${url}/step1`)
      }
      if (item.url === '/restaurant/menuType') {
        url = '/restaurantAddService'
        this.$router.push(`${url}/step1`)
      }
      if (item.url === '/service/consultationService') {
        url = '/addConsultationService'
        // 查询是否已经发布过咨询服务
        this.$api.post('/member-reversion/consult/list', {
          account: this.$user.loginAccount
        }).then(response => {
          if (response.code === 200) {
            // 控制只能发布一条咨询服务
            if (response.data) {
              this.$Message.info('已经发布过咨询服务！请前往咨询服务进行编辑！')
            } else {
              this.$router.push(`${url}/step1`)
            }
          }
        }).catch(error => {
          this.$Message.error('服务器异常！')
        })
      }
    },
    onSearch () {
      console.log(this.keyWord)
      this.data = []
      this.datas.forEach(e => {
        let flag = e.appName.indexOf(this.keyWord)
        if (flag !== -1) {
          this.data.push(e)
        }
      })
    }
  },
}
</script>
<style>

</style>
