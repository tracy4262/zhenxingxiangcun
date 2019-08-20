<template>
    <div>
        <top ref="top"/>
        <nav class="new-gate-nav">
           <Row type="flex" align="middle" style="min-height:40px;">
                <Col span="6">
                    <div class="vui-flex pl20">
                        <img v-if="websiteInfo.websiteLOGO" :src="websiteInfo.websiteLOGO" alt="" width="22px" height="22px">
                        <div class="vui-flex-item ell pl10" style="line-height: 22px;" :style="{'font-size': websiteNameZizelogo}" :title="websiteName">
                            {{websiteName}}
                        </div>
                    </div>
                </Col>
                <!-- <Col span="12" offset="6" class="tr pr15">
                    <canvas id="canvas" ref="canvas" ></canvas>
                </Col> -->
            </Row>
        </nav>
        <div style="background: #F7F7F7;">
            <div class="banner-gate">
                <img v-if="websiteInfo.websiteBanner" :src="websiteInfo.websiteBanner" alt="" height="238px" width="100%">
                <img v-else src="../../../../img/new-gate-banner1.png" alt="" height="238px" width="100%">
                <div class="banner-content">
                    <Row>
                        <Col span="5">
                            <div class="user-img">
                                <img v-if="expertData.personalPhoto" :src="expertData.personalPhoto" alt="" height="160px" width="160px">
                                <img v-else src="../../../../img/default_header.png" alt="" height="160px" width="160px">
                            </div>
                        </Col>
                        <Col span="19" offset="5">
                        <div class="pd10 pl20">
                            <Row>
                                <Col span="17">
                                    <p class="user-name pt10">{{expertData.expertName}}</p>
                                    <p v-if="expertData.title"><b>职称：</b>{{expertData.title}}</p>
                                    <!-- adeptField 擅长领域 adeptSpecies 擅长物种 -->
                                    <p v-if="expertData.adeptField || expertData.adeptSpecies"><b>擅长：</b>{{expertData.adeptField}} {{expertData.adeptSpecies}}</p>
                                    <div v-if="more">
                                        <p v-if="introduction.abstracts"><b>简介：</b>{{introduction.abstracts}}</p>
                                        <Row>
                                            <Col span="12" v-if="concatData.seatPhoneStatus">
                                                <p><b>座机电话：</b>{{concatData.seat_phone}}</p>
                                            </Col>
                                            <Col span="12" v-if="concatData.phoneStatus">
                                                <p><b>手机号：</b>{{concatData.phone}}</p>
                                            </Col>
                                        <!-- </Row>
                                        <Row> -->
                                            <Col span="12" v-if="concatData.qqNumberStatus">
                                                <p><b>QQ号：</b>{{concatData.qq_number}}</p>
                                            </Col>
                                            <Col span="12" v-if="concatData.wechatNumberStatus">
                                                <p><b>微信号：</b>{{concatData.wechat_number}}</p>
                                            </Col>
                                        <!-- </Row>
                                        <Row> -->
                                            <Col span="12" v-if="concatData.emailStatus">
                                                <p><b>邮箱：</b>{{concatData.email}}</p>
                                            </Col>
                                            <Col span="24"  v-if="concatData.detailAddressStatus">
                                                <p><b>详细地址：</b>{{concatData.detailAddress}}</p>
                                            </Col>
                                        </Row>
                                        <div @click="more = false" type="text" class="t-grey more">收起详细资料<Icon type="ios-arrow-up" size="18"/></div>
                                    </div>
                                    <div v-else>
                                        <div @click="more = true" type="text" class="t-grey more">查看详细资料<Icon type="ios-arrow-down" size="18"/></div>
                                    </div>
                                </Col>
                                <Col span="7">
                                    <div class="tr pr10">
                                        <span class="name-card">我的名片</span>
                                        <canvas id="canvas" ref="canvas" ></canvas>
                                    </div>
                                    <div class="pr10 tr pt15">
                                        <Button type="primary" v-if="account!= loginAccount && !isFocus" class="mr10" @click="handleFocus"><Icon type="md-add" size="18" class="mr5"/>关注他</Button>
                                        <Button type="primary" v-if="account != loginAccount && isFocus" class="mr10" @click="handleFocus"><Icon type="md-checkmark" size="18" class="mr5"/>已关注</Button>
                                        <Button @click="handleWebimchat"><Icon type="ios-chatbubbles-outline"  size="18" class="mr5"/>写留言</Button>
                                    </div>
                                </Col>
                            </Row>
                        </div>
                        </Col>
                    </Row>
                </div>
            </div>
            <div class="nav-gate-expert" :class="active == '0/index' ? 'nav-gate-position' : ''">
                <div class="nav-gate-layouts">
                    <Tabs :value="active" @on-click="clickTab">
                        <TabPane :class="{'on': item.checked}" :label="item.columnName" :name="item.attributionId" v-for="(item,index) in data"></TabPane>
                    </Tabs>
                </div>
            </div>
            <div>
                <!-- <div style="width:1000px;margin: 0 auto;"> -->
        <div style="min-height: 500px;">
            <router-view></router-view>
        </div>
                <!-- </div> -->
            </div>
        </div>
        <foot/>
    </div>
</template>
<script>
import foot from '../../components/foot'
import top from '../../components/top'
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
                url: '/expertPortal/index',
                checked: true,
                isShow: true
            },{
                title: '乡村介绍',
                url: '/expertPortal/Introduction',
                checked: false,
                isShow: true
            }, {
                title: '乡村动态',
                url: '/expertPortal/dynamic',
                checked: false,
                isShow: true
            }, {
                title: '乡村政策',
                url: '/expertPortal/policy',
                checked: false,
                isShow: true
            }, {
                title: '乡村知识',
                url: '/expertPortal/knowledge',
                checked: false,
                isShow: true
            }, {
                title: '标准',
                url: '/expertPortal/standard',
                checked: false,
                isShow: true
            }, {
                title: '会员产品',
                url: '/expertPortal/product',
                checked: false,
                isShow: true
            }, {
                title: '会员服务',
                url: '/expertPortal/service',
                checked: false,
                isShow: true
            }, {
                title: '基地',
                url: '/expertPortal/productionBase',
                checked: false,
                isShow: true
            }, 
            {
                title: '联系我们',
                url: '/expertPortal/contact',
                checked: false,
                isShow: true
            }],
            loginAccount:'',
            headData:{},
            templateId: '',
            websiteInfo: {},
            websiteName: '',
            websiteNameZize: '60px',
            websiteNameZizelogo: '18px',
            qrCodeUrl: '',
            more: false,
            active: '0/index',
            isFocus: false,
            isFocusData: {},
            expertData: {},
            introduction: {},
            concatData:{},
            account: ''
        }
    },
    created(){
        this.init()
        this.checkNav()
    },
    methods:{
        handleLogin () {
            this.$refs['top'].loginuser()
        },
        // 查询用户 发布的联系方式
        getConcat () {
            this.$api.post('/member/columnSettings/findContact', {account: this.loginAccount}).then(response => {
                if (response.code === 200) {
                    if (response.data.length) {
                        this.concatData = response.data[0].safeFormData[0]
                    } else {
                        this.concatData = []
                    }
                }
            })
        },
        // 查询会员介绍 获取用户简介
        getIntroduction () {
            this.$api.post('/member/memberIntroduce/findMemberIntroduceInfo', {account: this.loginAccount}).then(response => {
                if (response.code === 200 && response.data) {
                    this.introduction = response.data
                } else {
                    this.introduction = {}
                }
            })
        },
        // 获取专家认证的数据
        getexpert () {
            this.$api.post('/member-reversion/expertAuth/findExpertByAccount', {account: this.loginAccount}).then(response => {
                if (response.code === 200) {
                    if (response.data) {
                        this.expertData = response.data
                    } else {
                        this.expertData = {}
                    }
                }
            })
        },
        // 点击聊天
        handleWebimchat () {
            if (!this.$user) {
                this.$Message.error('请登录后再发起聊天')
                this.handleLogin()
                return
            } else {
                this.$api.post('/member/fishing/findAvatar',{account: this.loginAccount}).then(response => {
                    if (response.code == 200) {
                        let data = response.data
                        this.webimchat(data.userId, data.name, data.avatar)
                    }
                })
            }
        },
        webimchat (userId, name, avatar) {
            layui.layim.chat({
                id: userId,
                name: name,
                avatar: avatar,
                type: 'friend'
            })
        },
        // 查询 有没有关注这个人
        searchFocus () {
            this.$api.post('/member/followManage/findLoginByMemberList',{
                type: '0',
                account: this.account,
                pageSize:24,
                pageNum:1,
                keyword: this.loginAccount
            }).then(response => {
                if (response.code === 200) {
                    if (response.data.list.length) {
                        this.isFocus = true
                        this.isFocusData = response.data.list[0]
                    } else {
                        this.isFocusData = {
                            account: this.loginAccount
                        }
                        this.isFocus = false
                    }
                }
            })
        },
        // 点击关注或者取消关注
        handleFocus () {
            if (!this.account) {
                this.$Message.error('请登录后再关注')
                console.log(this.$parent)
                this.handleLogin()
                return
            } else {
                if (!this.isFocus) { // 未关注 调用关注的接口
                    this.$api.post('/member/followManage/insertFollowMemberInfo', {account: this.account, dataList: [this.isFocusData]}).then(response => {
                        if (response.code === 200) {
                            this.$Message.success('关注成功')
                            this.searchFocus()
                        } else {
                            this.$Message.error('关注失败')
                        }
                    })
                } else { // 已关注 调用取消关注的接口
                    this.$api.post('/member/followManage/deleteFollowMemberInfo', {dataList: [this.isFocusData]}).then(response => {
                        if (response.code === 200) {
                            this.$Message.success('取消关注成功')
                            this.searchFocus()
                        } else {
                            this.$Message.error('取消关注失败')
                        }
                    })
                }
            }
        },
        init () {
            this.loginAccount = this.$route.query.uid
            // 查询是否有关注此人
            if (this.$user && this.$user.loginAccount) {
                this.searchFocus()
                this.account = this.$user.loginAccount
            }
            this.getexpert()
            // 获取简介
            this.getIntroduction()
            this.getConcat()
            this.$api.post('/member-reversion/realStep/findEnableStep', {
                    account: this.loginAccount
                }).then(response => {
                if (response.code === 200) {
                    if (response.data) {
                        this.templateId = response.data.templateId
                        this.qrCodeUrl = `${window.location.origin}/nswy-member-info?account=${this.$route.query.uid}&templateId=${this.templateId}`
                        this.useqrcode()
                        this.getData()
                        // 获取 网站设置
                        this.getWebsite()
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
                console.log('99999')
            }
        },
        clickTab (attributionId) {
            this.active = attributionId
            if (attributionId.split('/').length > 2) {
                this.$router.push(`/expertPortal/${attributionId.split('/')[1]}?uid=${this.$route.query.uid}&tabType=${attributionId.split('/')[2]}&id=${attributionId.split('/')[0]}`)
            } else {
                this.$router.push(`/expertPortal/${attributionId.split('/')[1]}?uid=${this.$route.query.uid}&id=${attributionId.split('/')[0]}`)
            }
        },
        toIntroduction () {
            let active = 'introduction'
            this.data.forEach((e, index) => {
                if (e.attributionId == `${index}/${this.active}`) {
                    this.active = e.attributionId
                    this.$router.push(`/expertPortal/introduction?uid=${this.loginAccount}&id=${index}`)
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
        getWebsite () {
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
                            // if (this.websiteName.length <= 9) {
                            //   this.websiteNameZize = '60px'
                            //   this.websiteNameZizelogo = '26px'
                            // } else if (this.websiteName.length > 9 &&  this.websiteName.length <= 10) {
                            //   this.websiteNameZize = '54px'
                            //   this.websiteNameZizelogo = '24px'
                            // } else if (this.websiteName.length > 10 &&  this.websiteName.length <= 11) {
                            //   this.websiteNameZize = '48px'
                            //   this.websiteNameZizelogo = '22px'
                            // } else if (this.websiteName.length > 11 &&  this.websiteName.length <= 12) {
                            //   this.websiteNameZize = '44px'
                            //   this.websiteNameZizelogo = '20px'
                            // } else if (this.websiteName.length > 12 &&  this.websiteName.length <= 13) {
                            //   this.websiteNameZize = '42px'
                            //   this.websiteNameZizelogo = '18px'
                            // } else if (this.websiteName.length > 13 &&  this.websiteName.length <= 15) {
                            //   this.websiteNameZize = '36px'
                            //   this.websiteNameZizelogo = '16px'
                            // } else if (this.websiteName.length > 15 &&  this.websiteName.length <= 18) {
                            //   this.websiteNameZize = '30px'
                            //   this.websiteNameZizelogo = '14px'
                            // } else {
                            //   this.websiteNameZize = '26px'
                            //   this.websiteNameZizelogo = '12px'
                            // }
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
$activeColor: #00C587;
$color: #4A4A4A;
$height: 68px;
.nav-gate-expert{
    .ivu-tabs-bar{
        border-bottom: none;
        margin-bottom: 0px
    }
    .ivu-tabs-nav .ivu-tabs-tab{
        height:$height;
        line-height: $height;
        padding: 0px 12px;
        margin-right:0px;
        color: $color;
        &:hover{
            color: $activeColor;
            background: #fff;
        }
    }
    .ivu-tabs-tab-active.ivu-tabs-tab-focused{
        color: $activeColor;
        background: #fff;
    }
    .ivu-tabs-nav-prev{
        height:$height;
        line-height:$height;
        color: $color;
    }
    .ivu-tabs-nav-next{
        height: $height;
        line-height: $height;
        color: $color;
    }
    .ivu-tabs-ink-bar{
        display: none;
    }
}
</style>

<style lang="scss" scoped>
$background: #fff;
$color: #4A4A4A;
$activeColor: #4E8411;
.new-gate-nav {
    // min-width: 1366px;
    // width: 1200px;
    width: 1000px;
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
.nav-gate-expert {
    background: $background;
    border-radius: 0px 0px 4px 4px;
    // width: 100%;
    height: 68px;
    // min-width: 1200px;
    width: 1000px;
    margin: 0 auto;
    .nav-gate-layouts{
        // min-width:1200px;
        width: 1000px;
        margin: 0 auto;
        border-top: 2px solid rgb(247, 247, 247);
    }
  li{
      float: left;
  }
  .item{
      font-family: PingFangSC-Regular;
      color: $color;
      font-size: 14px;
      padding: 0px 12px;
      display: block;
      height: 50px;
      line-height: 50px;
      &:hover,
      &.on{
        color: $color;
        background: #00c587;
      }
  }
}
.nav-gate-expert.nav-gate-position{
    // margin-top: -50px;
    background: $background;
    opacity: 0.9;
}
</style>
<style lang="scss" scoped>
// banner 图片部分
.banner-gate {
  width: 1000px;
  margin:0 auto;
//   min-width: 1200px;
  .banner-content{
    position: relative;
    width: 100%;
    min-height: 163px;
    background: #fff;
    .name-card{
        display: inline-block;
        height: 28px;
        vertical-align: bottom;
    }
    #canvas{
      width: 75px !important;
      height: 75px !important;
    }
    .user-img{
        position: relative;
        img{
            position: absolute;
            border-radius: 4px;
            box-shadow: 0px 0px 0px 5px #fff;
            top: -35px;
            left: 40px;
        }
    }
    p{
        padding-top: 10px;
    }
    .user-name{
        color: #4a4a4a;
        font-size: 26px;
    }
    .more{
        cursor: pointer;
        display: inline-block;
        padding: 10px 0px 10px 0px;
        margin-top: 10px;
    }
    // .gate-name{
    //   font-family: FZXKJW--GB1-0;
    //   color: #FFFFFF;
    //   padding-left:40px;
    // }
    // .gate-text{
    //   background: rgba(0,0,0,0.52);
    //   opacity: 0.66;
    //   width: 100%;
    //   height: 250px;
    //   color: #fff;
    //   margin-top: 20px;
    //   padding: 25px 30px;
    //   line-height: 24px;
    //   font-size: 14px;
    //   cursor: pointer;
    // }
  }
}
</style>
