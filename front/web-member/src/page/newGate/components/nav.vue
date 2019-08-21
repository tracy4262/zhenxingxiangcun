<template>
    <div>
        <top/>
        <nav class="new-gate-nav">
           <Row type="flex" align="middle" style="min-height:81px;">
                <Col span="6">
                    <div class="vui-flex pl20">
                        <img v-if="websiteInfo.websiteLOGO" :src="websiteInfo.websiteLOGO" alt="" width="51px" height="51px">
                        <div class="vui-flex-item ell pl10" style="line-height: 51px;" :style="{'font-size': websiteNameZizelogo}" :title="websiteName">
                            {{websiteName}}
                        </div>
                    </div>
                </Col>
                <Col span="12" offset="6" class="tr pr15">
                    <canvas id="canvas" ref="canvas" ></canvas>
                </Col>
            </Row>
        </nav>
        <div v-if="active == '0/index'" class="banner-gate">
          <img v-if="websiteInfo.websiteBanner" :src="websiteInfo.websiteBanner" alt="" height="340px" width="100%">
          <img v-else src="../../../img/new-gate-banner1.png" alt="" height="340px" width="100%">
          <div class="content">
            <Row type="flex" align="middle" class="layouts">
              <Col span="12">
                <p class="ell gate-name pt100" :style="{'font-size': websiteNameZize}" :title="websiteName">{{websiteName}}</p>
              </Col>
              <!-- <Col span="11" offset="1">
                <div class="gate-text" @click="toIntroduction" v-if="introduction.length > 286">
                  {{introduction.substring(0, 286)}}...
                </div>
                <div class="gate-text" @click="toIntroduction" v-else>
                  {{introduction}}
                </div>
              </Col> -->
            </Row>
          </div>
        </div>
        <div class="nav-gate" :class="active == '0/index' ? 'nav-gate-position' : ''">
          <div class="layouts">
                <Tabs :value="active" @on-click="clickTab">
                    <TabPane :class="{'on': item.checked}" :label="item.columnName" :name="item.attributionId" v-for="(item,index) in data" :key="index"></TabPane>
                </Tabs>
          </div>
        </div>
        <div style="min-height: 500px;">
            <router-view></router-view>
        </div>
        <foot/>
    </div>
</template>
<script>
import foot from './foot'
import top from './top'
import QRCode from 'qrcode'
export default {
    components:{
        top,
        foot
    },
    data () {
        return {
            isNav: true,
            data:[{
                title: '首页',
                url: '/portals/index',
                checked: true,
                isShow: true
            },{
                title: '乡村介绍',
                url: '/portals/Introduction',
                checked: false,
                isShow: true
            }, {
                title: '乡村动态',
                url: '/portals/dynamic',
                checked: false,
                isShow: true
            }, {
                title: '乡村政策',
                url: '/portals/policy',
                checked: false,
                isShow: true
            }, {
                title: '乡村知识',
                url: '/portals/knowledge',
                checked: false,
                isShow: true
            }, {
                title: '标准',
                url: '/portals/standard',
                checked: false,
                isShow: true
            }, {
                title: '会员产品',
                url: '/portals/product',
                checked: false,
                isShow: true
            }, {
                title: '会员服务',
                url: '/portals/service',
                checked: false,
                isShow: true
            }, {
                title: '基地',
                url: '/portals/productionBase',
                checked: false,
                isShow: true
            }, 
            {
                title: '联系我们',
                url: '/portals/contact',
                checked: false,
                isShow: true
            }],
            loginAccount:'',
            headData:{},
            templateId: '',
            websiteInfo: {},
            websiteName: '',
            websiteNameZize: '60px',
            websiteNameZizelogo: '60px',
            qrCodeUrl: '',
            active: '0/index',
            introduction: `先锋村位于武汉市江夏区山坡街西南端，濒临湖北省第三大湖泊——斧头湖。与咸安、嘉鱼隔湖相望，座落在一面连陆、三面环水的两个半岛上。该村气候温和，四季分明，年平均气温约16.8℃，日照长，霜期短，雨量充沛，年均降雨量1350毫升左右。空气清新，植被覆盖率占全村面积的66 %，
先锋村原名三门徐乡，元代成宗年间，江西饶州秀才徐廷琏举家迁移在此定居，该村气候温和，四季分明，年平均气温约16.8℃，日照长，霜期短，雨量充沛，年均降雨量1350毫升左右。空气清新，植被覆盖率占全村面积的66 %，水域面积占30%，全年优良天气360天以上。这里田地湖泊交叉、生态环境优美，是典型的江南水乡地形地貌。
先锋村原名三门徐乡，经过几十代繁衍，至今已有700多年历史,先锋村位于武汉市江夏区山坡街西南端，`
        }
    },
    created(){
        this.init()
        this.checkNav()
    },
    methods:{
        init () {
            this.loginAccount = this.$route.query.uid
            this.$api.post('/member-reversion/realStep/findEnableStep', {
                    account: this.loginAccount
                }).then(response => {
                if (response.code === 200) {
                    if (response.data) {
                        this.templateId = response.data.templateId
                        this.qrCodeUrl = `${window.location.origin}/nswy-member-info?account=${this.$route.query.uid}&templateId=${this.templateId}`
                        this.useqrcode()
                        this.getData()
                        this.getIntroduction()
                    }
                }
            })
        },
        checkNav () {
            if (this.$route.query.tabType) {
                this.active = `${this.$route.query.id}/${window.location.pathname.split('/')[2]}/${this.$route.query.tabType}`
            } else {
                this.active = `${this.$route.query.id}/${window.location.pathname.split('/')[2]}`
            }
            if (window.location.pathname.split('/')[2] == 'index') {
                this.init()
            }
        },
        clickTab (attributionId) {
            this.active = attributionId
            if (attributionId.split('/').length > 2) {
                this.$router.push(`/portals/${attributionId.split('/')[1]}?uid=${this.$route.query.uid}&tabType=${attributionId.split('/')[2]}&id=${attributionId.split('/')[0]}`)
            } else {
                this.$router.push(`/portals/${attributionId.split('/')[1]}?uid=${this.$route.query.uid}&id=${attributionId.split('/')[0]}`)
            }
        },
        toIntroduction () {
            let active = 'introduction'
            this.data.forEach((e, index) => {
                if (e.attributionId == `${index}/${this.active}`) {
                    this.active = e.attributionId
                    this.$router.push(`/portals/introduction?uid=${this.loginAccount}&id=${index}`)
                }
            })
        },
        useqrcode () {
            var canvas = this.$refs['canvas']
            QRCode.toCanvas(canvas, this.qrCodeUrl, function (error) {
                if (error) console.error(error)
            })
        },
        getIndex(){
            let path = window.location.pathname.split('/')[2]
            let checkedIndex = 0
            this.data.forEach((item, index) => {
                item.checked = false
                if (this.$route.query.tabType) {
                    if (`${index}/${path}/${this.$route.query.tabType}` === item.attributionId) {
                        this.active = item.attributionId
                        item.checked = true
                        checkedIndex = index
                    } else {
                        item.checked = false
                    }
                } else {
                    if (`${index}/${path}` === item.attributionId) {
                        this.active = item.attributionId
                        item.checked = true
                        checkedIndex = index
                    } else {
                        item.checked = false
                    }
                }
            })
            this.data.splice(checkedIndex, 1, this.data[checkedIndex])
        },
        getIntroduction () {
            // url若为0则调用管理员侧的接口，不为0则调用用户侧的接口
            let url = this.templateId === '0' ? '/member-reversion/websiteSettings/findWebsiteSettingsInfo' : '/member-reversion/user/websiteSettings/findWebsiteSettingsInfo'
            this.$api.post(url, {
                    account: this.loginAccount,
                    templateId: this.templateId
                }).then(response => {
                    if (response.code === 200) {
                        if (response.data.websiteInfo) {
                        this.websiteInfo = response.data.websiteInfo
                          if (this.websiteInfo.isShowWebsiteName) { // 显示网站名称
                            this.websiteName = `${this.websiteInfo.websiteName}${this.websiteInfo.nameSuffix}`
                            if (this.websiteName.length <= 9) {
                              this.websiteNameZize = '60px'
                              this.websiteNameZizelogo = '26px'
                            } else if (this.websiteName.length > 9 &&  this.websiteName.length <= 10) {
                              this.websiteNameZize = '54px'
                              this.websiteNameZizelogo = '24px'
                            } else if (this.websiteName.length > 10 &&  this.websiteName.length <= 11) {
                              this.websiteNameZize = '48px'
                              this.websiteNameZizelogo = '22px'
                            } else if (this.websiteName.length > 11 &&  this.websiteName.length <= 12) {
                              this.websiteNameZize = '44px'
                              this.websiteNameZizelogo = '20px'
                            } else if (this.websiteName.length > 12 &&  this.websiteName.length <= 13) {
                              this.websiteNameZize = '42px'
                              this.websiteNameZizelogo = '18px'
                            } else if (this.websiteName.length > 13 &&  this.websiteName.length <= 15) {
                              this.websiteNameZize = '36px'
                              this.websiteNameZizelogo = '16px'
                            } else if (this.websiteName.length > 15 &&  this.websiteName.length <= 18) {
                              this.websiteNameZize = '30px'
                              this.websiteNameZizelogo = '14px'
                            } else {
                              this.websiteNameZize = '26px'
                              this.websiteNameZizelogo = '12px'
                            }
                          }
                        }
                    }
            })
        },
        // 查询数据
        getData(){
            // url若为0则调用管理员侧的接口，不为0则调用用户侧的接口
            let url = this.templateId === '0' ? '/member-reversion/columnSetting/findColumnSettingInfo' : '/member-reversion/user/columnSetting/findColumnSettingInfo'
            this.$api.post(url, {
                account: this.loginAccount,
                templateId: this.templateId
            }).then(response => {
                if (response.code === 200) {
                    let arr = [{columnName: '首页', attribution: '全部', attributionId: 'index'}]
                    let data = arr.concat(response.data.columnSetting)
                    data.forEach((e, index) => {
                        e.attributionId = `${index}/${e.attributionId}`
                    })
                    this.data = data
                    this.getIndex()
                }
            })
        }
    }
}
</script>
<style lang="scss">
.nav-gate{
    .ivu-tabs-bar{
        border-bottom: none;
        margin-bottom: 0px
    }
    .ivu-tabs-nav .ivu-tabs-tab{
        height: 52px;
        line-height: 52px;
        padding: 0px 12px;
        margin-right:0px;
        color: #fff;
        &:hover{
            color: #fff;
            background: #00c587;
        }
    }
    .ivu-tabs-tab-active.ivu-tabs-tab-focused{
        color: #fff;
        background: #00c587;
    }
    .ivu-tabs-nav-prev{
        height: 52px;
        line-height: 52px;
        color: #fff;
    }
    .ivu-tabs-nav-next{
        height: 52px;
        line-height: 52px;
        color: #fff;
    }
    .ivu-tabs-ink-bar{
        display: none;
    }
}
</style>

<style lang="scss" scoped>
.new-gate-nav {
    // min-width: 1366px;
    #canvas{
      width:70px !important;
      height: 70px !important;
    }
    width: 1200px;
    margin: 0 auto;
    li{
        float: right;
    }
    .item{
        font-family: PingFangSC-Regular;
        color: #4A4A4A;
        font-size: 14px;
        padding: 10px 12px;
        display: block;
        &:hover,
        &.on{
            color: #00c587;
        }
    }
}
.nav-gate {
    background: rgba(30,6,9,0.82);
    width: 100%;
    height: 50px;
    min-width: 1200px;
    .nav-gate-layouts{
        min-width:1200px;
        margin: 0 auto;
    }
  li{
      float: left;
  }
  .item{
      font-family: PingFangSC-Regular;
      color: #fff;
      font-size: 14px;
      padding: 0px 12px;
      display: block;
      height: 50px;
      line-height: 50px;
      &:hover,
      &.on{
        color: #fff;
        background: #00c587;
      }
  }
}
.nav-gate.nav-gate-position{
    margin-top: -50px;
    background: #042319;
    opacity: 0.9;
}
.banner-gate {
  position: relative;
  min-width: 1200px;
  .content{
    position: absolute;
    top: 0px;
    left:0px;
    width: 100%;
    .gate-name{
      font-family: FZXKJW--GB1-0;
      color: #FFFFFF;
      padding-left:40px;
    }
    .gate-text{
      background: rgba(0,0,0,0.52);
      opacity: 0.66;
      width: 100%;
      height: 250px;
      color: #fff;
      margin-top: 20px;
      padding: 25px 30px;
      line-height: 24px;
      font-size: 14px;
      cursor: pointer;
    }
  }
}
</style>
