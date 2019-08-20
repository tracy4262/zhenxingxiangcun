<template>
  <div>
      <div class="member-introduction" style="min-height: 300px;">
        <p v-if="!data.column_type && showContent" class="tc pd50">暂无相关信息</p>
        <bookBlurb :data="data" ref="book" v-if="data.column_type == '图书'"></bookBlurb>
        <div v-if="data.column_type == '短信'">
          <p class="titles">{{data.title}}</p>
          <p v-if="data.introduceDetail" class="content">{{data.introduceDetail.content}}</p>
        </div>
        <div v-if="data.column_type == '文章'">
          <p class="titles">{{data.title}}</p>
          <div v-if="data.introduceDetail" v-html="data.introduceDetail.content"></div>
        </div>
        <div v-if="data.column_type == '图册'">
          <p class="titles">{{data.title}}</p>
          <div v-if="data.introduceDetail" v-html="data.introduceDetail.content" class="img-content"></div>
        </div>
        <div v-if="data.column_type == '音频'">
          <p class="titles">{{data.title}}</p>
          <div v-if="data.introduceDetail" v-for="(item, index) in songs" class="pl50 pr50">
            <div class="mt20">
              <a-player :music="item" style="width: 100%;"></a-player>
            </div>
          </div>
        </div>
        <div v-if="data.column_type == '视频'">
          <p class="titles">{{data.title}}</p>
          <div style="width:750px; margin: 0 auto;">
            <swiper :options="swiperOptionTop" class="gallery-top" @slideChangeTransitionEnd="clickMe" ref="swiperTop">
              <swiper-slide v-for="(item,index) in videoList" :key="index" class="mb20">
                <div style="position:relative;text-align:center">
                  <video :src="item.addr" width="100%" controls="controls" />
                </div>
              </swiper-slide>
              <div class="swiper-button-next swiper-button-white" slot="button-next"></div>
              <div class="swiper-button-prev swiper-button-white" slot="button-prev"></div>
              <div class="swiper-pagination" slot="pagination" style="bottom: 0" v-if="videoList.length>1"></div>
            </swiper>
          </div>
          </div>
        </div>
        <div class="pt20 pd10 member-introduction-form" v-if="show">
          <Form>
            <Row>
              <Col span="11" offset="1">
                <FormItem label="农事无忧ID：">{{mydynamic.nswyId}}</FormItem>
              </Col>
              <Col span="12">
                <FormItem label="用户名：">{{mydynamic.account}}</FormItem>
              </Col>
            </Row>
            <Row>
              <Col span="11" offset="1">
                <FormItem label="昵称：">{{mydynamic.realname}}</FormItem>
              </Col>
              <Col span="12">
                <FormItem label="门户网站：">{{website}}</FormItem>
              </Col>
            </Row>
          </Form>
        </div>
      </div>
  </div>
</template>
<script>
import bookBlurb from './bookBlurb'
import "dplayer/dist/DPlayer.min.css";
import VueAplayer from 'vue-aplayer'
import { swiper, swiperSlide } from 'vue-awesome-swiper'
export default {
  components: {
    bookBlurb,
    "a-player": VueAplayer,
    swiper,
    swiperSlide
  },
  props: {
    gate: {
      type: Boolean,
      default: false
    },
  },
  data () {
    return {
      data:{},
      mydynamic: {},
      songs: [],
      videoList: [],
      swiperOptionTop: {
        spaceBetween: 10,
        navigation: {
          nextEl: '.swiper-button-next',
          prevEl: '.swiper-button-prev'
        },
        pagination: {
          el: '.swiper-pagination',
          clickable: true
        }
      },
      show: false,
      account: '',
      showContent: false,
      website: ''
    }
  },
  created() {
    if (this.gate) {
      this.account = this.$route.query.uid
    } else {
      this.account = this.$user.loginAccount
    }
    this.init()
    
  },
  methods: {
    getPortals (account) {
      // 查询用户是否实名
      this.$api.post('/member-reversion/realStep/findEnableStep', {
              account: account
          }).then(response => {
          if (response.code === 200) {
              if (response.data) {
                  let step = Number(response.data.step)
                  if (step >= 7) {
                      this.$api.post('/member-reversion/user/realCertification/findMemberClassByAccount', {
                          account: account
                      }).then(response => {
                          if (response.code === 200) {
                              console.log('userType', response.data.member_class)
                              if (response.data.member_class === '专家') {
                                  // 专家门户
                                  this.website = `${window.location.origin}/expertPortal/index?uid=${account}&id=0`
                              } else if (response.data.member_class === '法人/其他法人') {
                                  // 乡村门户
                                  this.website = `${window.location.origin}/ruralPortal/index?uid=${account}&id=0`
                                  // this.$router.push(`/ruralPortal/index?uid=${account}&id=0`)
                              } else if (response.data.member_class === '法人/企业法人/农业龙头企业') {
                                  // 农业龙头企业
                                  this.website = `${window.location.origin}/farmHeadPortal/index?uid=${account}&id=0`
                              } else if (response.data.member_class === '法人/企业法人/农民合作社') {
                                  // 农民合作社
                                  this.website = `${window.location.origin}/cooperativePortal/index?uid=${account}&id=0`
                              } else {
                                  // 其余都进通用门户
                                  this.website = `${window.location.origin}/portals/index?uid=${account}&id=0`
                              }
                          } else {
                              this.website = `${window.location.origin}/portals/index?uid=${account}&id=0`
                          }
                      })
                  } else {
                      // this.$Message.error('请完成实名认证！')
                      this.website = `${window.location.origin}/portals/index?uid=${account}&id=0`
                  }
              } else { // 说明没走过认证
                  // this.$Message.error('用户未实名')
                  this.website = `${window.location.origin}/portals/index?uid=${account}&id=0`
              }
          }
      }).catch(error => {
          this.$Message.error('服务器异常！')
      })
    },
    init () {
       // 查询昵称等信息
      this.$api.post('/member/memberIntroduce/findNswyInfo', {account: this.account}).then(response => {
        if (response.code === 200) {
          this.show = true
          this.mydynamic = response.data
          this.getPortals(this.account)
        } else {
          this.$Message.error('查询失败')
        }
      })
    },
    clickMe (e) {
      console.log(e)
    },
    getList () {
      this.showContent = false
      this.$api.post('/member/memberIntroduce/findMemberIntroduceInfo', {account: this.account}).then(response => {
        this.showContent = true
        if (response.code === 200 && response.data) {
          this.showContent = true
          this.show = true
          this.data = response.data
          this.$nextTick(() => {
            if (this.data.column_type == '图书') {
              this.$refs['book'].showBook = false
            }
          })
          if (this.data.column_type == '音频' || this.data.column_type == '视频') {
            this.data.introduceDetail.videoImgs.forEach(element => {
              if (element.type === 1) {
								this.songs.push({ url: element.addr, title: element.mediaName, author: this.data.introduceDetail.editer, describe: element.describe })
							} else {
                this.videoList.push(element)
							}
            })
          }
        }
      })
    }
  },
}
</script>
<style lang="scss">
.member-introduction{
  .titles{
    font-size: 20px;
    font-weight: 700;
    text-align: center;
    padding: 40px 20px;
  }
  .content{
    text-indent: 2em;
  }
  .img-content{
    img{
      display: inline-block;
      width: calc(100% / 3 - 16px);
      height: 200px;;
      margin: 8px;
    }
  }
}
.member-introduction-form{
  .ivu-form-item{
    margin-bottom: 5px;
  }
}
</style>
