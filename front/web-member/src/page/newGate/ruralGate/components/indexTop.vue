<!--
    作者：chenqim
    时间：2019-03-11
    描述：乡村门户首页上半部
-->
<template>
    <div style="width: 1200px; margin: 40px auto;">
      <Row>
        <Col span="19">
          <Row>
            <!-- 轮播图 -->
            <Col span="9">
                <Carousel v-model="value" loop dots="outside">
                    <CarouselItem v-for="(item, index) in rotationPictureList" :key="index" class="carouse-height bc">
                        <div class="tc" @click="detail(item)" style="cursor: pointer;">
                            <img :src="item.image" style="width: 100%; height: 300px;" />
                            <div class="mt10 mb10 ell" :title="item.title">{{ item.title }}</div>
                        </div>
                    </CarouselItem>
                </Carousel>
            </Col>
            <!-- 所有除推荐产品，推荐服务，推荐基地，联系方式以外的标签页 -->
            <Col span="15" class="pl20">
                <Tabs :value="allActiveIndex" @on-click="allTabClick">
                    <TabPane :label="item.name" :name="`${index}`" v-for="(item,index) in allTabList" :key="index"></TabPane>
                </Tabs>
                <div v-if="attribution === 'introduction'" class="mt10">
                    <p style="color: #4A4A4A; font-size: 14px; line-height: 24px;">{{ introduction }}<a v-if="introduction !== '暂无相关内容！' " @click="goIntroduction">查看详情 >></a></p>
                </div>
                <ul v-else class="mt10">
                    <li v-for="(item, index) in allList" :key="index">
                        <Row type="flex" align="middle">
                            <Col span="1"><div class="circle ml5"></div></Col>
                            <Col span="22"><div :class="index === 0 || index === 4? 'dynamic-title-big ell' : 'dynamic-title ell'" :title="item.title" @click="detail(item)">{{ item.title }}</div></Col>
                        </Row>
                        <div v-if="index === 3" style="height: 20px;"></div>
                    </li>
                    <h2 class="ml20" v-if="allList.length === 0">{{ text2 }}</h2>
                </ul>
            </Col>
          </Row>
          <Row class="mt20">
            <Col span="9">
                <!-- 标题 -->
                <div style="background-color: #fafafa; padding-top: 1px; padding-bottom: 1px;">
                    <Row type="flex" align="middle">
                        <Col span="16"><Title :title="contactUs.name" class="ml10"></Title></Col>
                        <Col span="8" class="tr">
                            <a @click="goContactUs" class="new-title-16 mr10">查看更多</a>
                        </Col>
                    </Row>
                </div>
                <!-- 详情 -->
                <Row class="mt20" v-if="contactUsDetail.longitudeStatus">
                    <img :src="`//api.map.baidu.com/staticimage?width=286&height=180&center=${contactUsDetail.longitude},${contactUsDetail.latitude}&zoom=14&markers=${contactUsDetail.longitude},${contactUsDetail.latitude}`" style="width: 100%; height: 150px;">
                </Row>
                <Row class="mt20" style="font-size: 14px; line-height: 27px; color: #4A4A4A;">
                    <Col span="24" v-if="contactUsDetail.memberMameStatus">姓名：{{ contactUsDetail.member_name }}</Col>
                    <Col span="24" v-if="contactUsDetail.seatPhoneStatus">座机电话：{{ contactUsDetail.seat_phone }}</Col>
                    <Col span="24" v-if="contactUsDetail.phoneStatus">手机号：{{ contactUsDetail.phone }}</Col>
                    <Col span="24" v-if="contactUsDetail.qqNumberStatus">QQ号：{{ contactUsDetail.qq_number }}</Col>
                    <Col span="24" v-if="contactUsDetail.wechatNumberStatus">微信号：{{ contactUsDetail.wechat_number }}</Col>
                    <Col span="24" v-if="contactUsDetail.emailStatus">邮箱：{{ contactUsDetail.email }}</Col>
                    <Col span="24" v-if="contactUsDetail.location_status" class="ell" :title="contactUsDetail.location">详细地址：{{ contactUsDetail.location }}</Col>
                </Row>
            </Col>
            <!-- 推荐基地，服务，产品 -->
            <Col span="15" class="pl20">
                <Tabs :value="recommendationActiveIndex" @on-click="recommendationTabClick">
                    <TabPane :label="item.name" :name="`${index}`" v-for="(item,index) in recommendationTabList" :key="index"></TabPane>
                </Tabs>
                <a class="new-title-16 mr10" style="position: absolute; top: 10px; right: 0;" @click="goRecommendation">查看更多</a>
                <Row :gutter="16">
                    <Col span="8" v-for="(item, index) in recommendationList" :key="index">
                        <product-item v-if="attribution2 === 'product'" :item="item"></product-item>
                        <service-item v-if="attribution2 === 'service'" :item="item"></service-item>
                        <production-base-item v-if="attribution2 === 'productionBase'" :item="item"></production-base-item>
                    </Col>
                </Row>
                <h2 class="ml20" v-if="recommendationList.length === 0">{{ text }}</h2>
            </Col>
          </Row>
        </Col>
        <Col span="5" class="pl20">
            <div class="pb5">
              <div style="background-color: #fafafa; padding-top: 1px; padding-bottom: 1px;">
                <Title title="惠民服务" class="ml10"></Title>
              </div>
              <Row class="pt5">
                <Col span="8" v-for="(item, index) in huiminService" :key="index" @click.native="handleHuiminService(item)">
                  <span style="display: block; padding: 6px; cursor: pointer;" class="tc ell">{{item.name}}</span>
                </Col>
              </Row>
            </div>
            <!-- 专家团队 -->
            <div>
              <div style="background-color: #fafafa; padding-top: 1px; padding-bottom: 1px;">
                  <Title title="专家团队" class="ml10"></Title>
              </div>
              <Row :gutter="8" class="mt10" style="height: 120px; overflow-y:auto;">
                  <Col span="8" v-for="(item, index) in expertTeam" :key="index" class="tc" @click.native="expertDetail(item)" style="cursor: pointer;">
                      <img :src="item.personalPhoto" style="width: 100%; height: 70px;">
                      <p style="font-size: 14px; color: #4A4A4A; line-height: 20px;" class="mt5 ell" :title="item.expertName">{{ item.expertName }}</p>
                      <p style="color: #9B9B9B; line-height: 17px;" class="ell mt5" :title="item.title">{{ item.title }}</p>
                  </Col>
                  <h2 class="ml20" v-if="expertTeam.length === 0">{{ text3 }}</h2>
              </Row>
              <!-- 相关产品 -->
              <aboutProductItem></aboutProductItem>
            </div>
            <div class="mt20">
              <!-- 相关服务 -->
              <aboutServiceItem></aboutServiceItem>
                <!-- 邻村风采 -->
                <div style="background-color: #fafafa; padding-top: 1px; padding-bottom: 1px; margin-top: 20px;">
                    <Title title="邻村风采" class="ml10"></Title>
                </div>
                <Row class="mt10 mb10">
                    <Col span="12" v-for="(item, index) in neighborList" :key="index" style="font-size: 16px; line-height: 36px; padding-left: 20px; cursor: pointer;" class="ell" :title="item.location" @click.native="goNeighbor(item)">{{ item.location }}</Col>
                    <h2 class="ml20" v-if="neighborList.length === 0">{{ text4 }}</h2>
                </Row>
            </div>
        </Col>
      </Row>
      <expertTeam ref="expertTeam"></expertTeam>
      <publishService ref="publishService"></publishService>
    </div>
</template>
<script>
import Title from '../../components/title'
import { goToPath } from '../../mixins/commonMixins'
import { O_RDONLY } from 'constants'
import productItem from './product-item'
import aboutProductItem from './about-product-item'
import serviceItem from './service-item'
import aboutServiceItem from './about-service-item'
import productionBaseItem from './production-base-item'
import expertTeam from './expertTeam'
import publishService from '~page/newMember/components/publishService.vue'
import serverItem from '~page/51index/components/server-item'
import consultationServerItem from '~page/51index/components/consultation-server-item'
export default {
    name: '',
    mixins: [goToPath],
    components: {
        Title,
        productItem,
        aboutProductItem,
        serviceItem,
        aboutServiceItem,
        productionBaseItem,
        publishService,
        expertTeam,
        serverItem,
        consultationServerItem
    },
    props: {
        allTabList: {
            type: Array,
            default: () => {
                return []
            }
        },
        recommendationTabList: {
            type: Array,
            default: () => {
                return []
            }
        },
        columnSetting: {
            type: Array,
            default: () => {
                return []
            }
        },
        contactUs: {
            type: Object,
            default: () => {
                return {
                    name: ''
                }
            }
        }
    },
    data () {
        return {
            huiminService: [
              { name: '我要查', url: '/51index' }, // 无忧首页 - 首页
              { name: '我要问', refs: 'expertTeam' }, // 弹框 显示所有专家
              { name: '我要学', url: '/wiki/' }, // 物种百科。
              { name: '我要买', url: '/goods/index' }, // 无忧首页-产品。
              { name: '我要卖', url: '/release-goods/step1', isLogin: true }, // 游客点击，弹出注册/登录界面； 已登录 跳转至产品发布。
              { name: '我要帮', isLogin: true, refs: 'publishService'}, // 游客点击，弹出注册/登录界面； 已登录 跳转至服务发布。
              { name: '我要玩', url: '/51index/serviceList/all'}, // 服务首页
              { name: '我要办'}, // 跳转地址待定
            ],
            value: 0,
            allActiveIndex: '0',
            allList: [],
            allDocType: '',
            recommendationActiveIndex: '0',
            neighborList: [],
            recommendationList: [],
            recommendationDocType: '',
            contactUsDetail: {},
            expertTeam: [],
            introduction: '',
            account: '',
            text2: '',
            text: '',
            text3: '',
            text4: '',
            rotationPictureList: [],
            toProductionBase: '',
            attribution: '',
            attribution2: ''
        }
    },
    watch: {
        allTabList: {
            handler (newValue, oldValue) {
                console.log('allTabList', newValue)
                // 等到tab页签请求到之后 模拟点击第一个tab
                this.allTabClick('0')
            },
            deep: true
        },
        recommendationTabList: {
            handler (newValue, oldValue) {
                console.log('recommendationTabList', newValue)
                // 等到tab页签请求到之后 模拟点击第一个tab
                this.recommendationTabClick('0')
            },
            deep: true
        }
    },
    created () {
        this.account = this.$route.query.uid
        this.getRotationPicture()
        this.getNeighborList()
        this.getContactUs()
        this.getExpertTeam()
    },
    methods: {
        // 点击 惠民服务中的按钮
        handleHuiminService (item) {
          if (item.isLogin) { // 说明要登陆
            if (this.$user && this.$user.loginAccount) {
              if (item.url) { // 说明是跳转页面
                // this.$router.push(item.url)
                window.open(item.url, "_blank")
              } else { // 否则是 弹窗
                this.$refs[item.refs].show = true
              }
            } else {
              this.$Message.error('请登录')
              this.$parent.$parent.$refs.top.$refs['loginRegister'].loginuser()
            }
          } else {
            if (item.url) { // 说明是跳转页面
              window.open(item.url, "_blank")
            } else if (item.refs) { // 否则是 弹窗
              this.$refs[item.refs].show = true
            }
          }
        },
        allTabClick (e) {
            this.allActiveIndex = e
            let index = parseInt(e)
            this.allDocType = this.allTabList[index].docType
            this.attribution = this.allTabList[index].attributionId.split('/')[0]
            this.text2 = ''
            this.allList = []
            if (this.attribution === 'dynamic') {
                this.getList('动态')
            } else if (this.attribution === 'knowledge') {
                this.getList('知识')
            } else if (this.attribution === 'policy') {
                this.getList('政策')
            } else if (this.attribution === 'standard') {
                this.getStandardList()
            } else if (this.attribution === 'introduction') {
                this.getIntroduction()
            }
        },
        recommendationTabClick (e) {
            this.recommendationActiveIndex = e
            let index = parseInt(e)
            this.recommendationDocType = this.recommendationTabList[index].docType
            this.attribution2 = this.recommendationTabList[index].attributionId.split('/')[0]
            this.text = ''
            this.recommendationList = []
            console.log('attribution2', this.attribution2)
            if (this.attribution2 === 'product') {
                this.getProduct()
            } else if (this.attribution2 === 'service') {
                this.getService()
            } else if (this.attribution2 === 'productionBase') {
                this.getProductionBase()
            }
        },
        getExpertTeam () {
            this.$api.post('/member-reversion/employ/manage', {
                type: '1',
                account: this.account,
                expertName: '',
                location: '',
                pageNum: 1,
                pageSize: 9999,
                relatedIndustry: '',
                relatedSpecies: '',
            }).then(response => {
                if (response.code === 200) {
                    this.expertTeam = response.data.list
                    if (this.expertTeam.length === 0) {
                        this.text3 = '暂无相关内容！'
                    }
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        getContactUs () {
            this.$api.post('/member/columnSettings/findContact', {
                account: this.account
            }).then(response => {
                if (response.code === 200) {
                    if (response.data.length > 0) {
                        this.contactUsDetail = response.data[0].safeFormData[0]
                    }
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        getProduct () {
            this.$api.post('/member-reversion/myRecommend/productList', {
                account: this.account,
                flag: '1', //0:查询所有服务, 1:查询已推荐服务
                productLocation: '',
                keyword: '',
                memberName: '',
                pageNum: 1,
                pageSize: 3
            }).then(response => {
                if (response.code === 200) {
                    console.log('res', response)
                    this.recommendationList = response.data.list
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        getService () {
            this.$api.post('/member-reversion/myRecommend/serviceList', {
                account: this.account,
                flag: '1', //0:查询所有服务, 1:查询已推荐服务
                address: '',
                serviceName: '',
                memberName: '',
                pageNum: 1,
                pageSize: 3
            }).then(response => {
                if (response.code === 200) {
                    console.log('res', response)
                    this.recommendationList = response.data.list
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        getProductionBase () {
            this.$api.post('/member-reversion/myRecommend/baseList', {
                account: this.account,
                flag: '1', // 0:查询所有基地, 1:查询已推荐基地
                address: '',
                baseName: '',
                memberName: '',
                pageNum: 1,
                pageSize: 3
            }).then(response => {
                if (response.code === 200) {
                    console.log('res', response)
                    this.recommendationList = response.data.list
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        // 获取轮播图
        getRotationPicture () {
            this.$api.post('/member/inforMation/dynamicCoverList', {
                account: this.account
            }).then(response => {
                console.log('res', response)
                if (response.code === 200) {
                    this.rotationPictureList = response.data
                    this.rotationPictureList.forEach(element => {
                        element.dataType = '动态'
                    })
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        // 获取邻村
        getNeighborList () {
            this.$api.post('/member-reversion/realCertification/neighborVillageStyle', {
                account: this.account
            }).then(response => {
                console.log('res', response)
                if (response.code === 200) {
                    this.neighborList = response.data
                    this.neighborList.forEach(element => {
                        element.dataType = '政策'
                    })
                    if (response.data.length === 0) {
                        this.text4 = '暂无相关内容！'
                    }
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        // 查询资讯（动态）、政策、知识的列表
        getList (type) {
            this.$api.get('/member/columnSettings/findColumnList?label=全部&columnId=' + type + '&currentPage=1&pageSize=8&account=' + this.account + '&docType=' + this.allDocType).then(response => {
                if (response.code === 200) {
                    this.allList = response.data.dataList
                    if (response.data.dataList.length === 0) {
                        this.text2 = '暂无相关内容！'
                    }
                }
            })
        },
        // 查询标准
        getStandardList () {
            this.$api.post('/portal/standard/standard-list', {
                account: this.account,
                label: '全部',
                pageNum: 1,
                pageSize: 8
            }).then(response => {
                if (response.code === 200) {
                    this.allList = response.data.list
                    this.allList.forEach(e => {
                        e.dataType = '标准'
                    })
                    if (response.data.list.length === 0) {
                        this.text2 = '暂无相关内容！'
                    }
                }
            })
        },
        // 查询会员介绍
        getIntroduction () {
            this.$api.post('/member/memberIntroduce/findMemberIntroduceInfo', {
                account: this.account
            }).then(response => {
                if (response.code === 200) {
                    if (response.data) {
                        this.introduction = response.data.introduceDetail.abstracts
                    } else {
                        this.introduction = '暂无相关内容！'
                    }
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        goIntroduction () {
            // 计算会员介绍是属于第几个tab
            let id = 0
            this.columnSetting.forEach((element, index) => {
                if (element.attributionId === 'introduction') {
                    id = index + 1
                }
            })
            // 会员介绍是一级栏目，跳转时需要带两个参数uid，id
            let url = `/ruralPortal/introduction?uid=${this.account}&id=${id}`
            this.$router.push(url)
        },
        goProductionBase () {
            this.$router.push(this.toProductionBase)
        },
        goContactUs () {
            // 计算联系我们是属于第几个tab
            let id = 0
            this.columnSetting.forEach((element, index) => {
                if (element.attributionId === 'contcat') {
                    id = index + 1
                }
            })
            // 联系我们是一级栏目，跳转时需要带两个参数uid，id
            let url = `/ruralPortal/contcat?uid=${this.account}&id=${id}`
            this.$router.push(url)
        },
        goNeighbor (item) {
            let url = `/ruralPortal/index?uid=${item.account}&id=0`
            window.open(url, "_blank")
        },
        goRecommendation () {
            if (this.attribution2 === 'product') {
                // 跳推荐产品更多页
                // 计算推荐产品是属于第几个tab
                let id = 0
                this.columnSetting.forEach((element, index) => {
                    if (element.attributionId === 'product') {
                        id = index + 1
                    }
                })
                // 推荐产品是一级栏目，跳转时需要带两个参数uid，id
                let url = `/ruralPortal/product?uid=${this.account}&id=${id}`
                this.$router.push(url)
            } else if (this.attribution2 === 'service') {
                // 跳推荐服务更多页
                // 计算推荐服务是属于第几个tab
                let id = 0
                this.columnSetting.forEach((element, index) => {
                    if (element.attributionId === 'service') {
                        id = index + 1
                    }
                })
                // 推荐服务是一级栏目，跳转时需要带两个参数uid，id
                let url = `/ruralPortal/service?uid=${this.account}&id=${id}`
                this.$router.push(url)
            } else if (this.attribution2 === 'productionBase') {
                // 跳推荐生产基地更多页
                // 计算推荐生产基地是属于第几个tab
                let id = 0
                this.columnSetting.forEach((element, index) => {
                    if (element.attributionId === 'productionBase') {
                        id = index + 1
                    }
                })
                // 推荐生产基地是一级栏目，跳转时需要带两个参数uid，id
                let url = `/ruralPortal/productionBase?uid=${this.account}&id=${id}`
                this.$router.push(url)
            }
        },
        detail (item) {
            this.goDetail(item)
        },
        expertDetail (item) {
            this.$toPortals(item.account)
        }
    }
}
</script>
<style lang="scss" scoped>
.bc {
    background-color: #f9f9f9;
}
.carouse-height {
    height: 440px;
}
.tab-cus {
    padding: 8px 16px;
    font-size: 18px;
    display: inline-block;
    cursor: pointer;
    border-top: 2px solid #eeeeee;
}
.tab-cus-active {
    padding: 8px 16px;
    font-size: 18px;
    display: inline-block;
    cursor: pointer;
    color: #00C587;
    border-top: 2px solid #00C587;
    transition: 1s;
}
.tab-hr {
    height: 2px;
    position: absolute;
    background: #eeeeee;
    z-index: -999;
    width: 97%;
}
.dynamic-title {
    font-size: 18px;
    line-height: 36px;
    cursor: pointer;
    &:hover{
        color: #00C587
    }
}
.dynamic-title-big {
    font-size: 20px;
    line-height: 36px;
    font-weight: bold;
    cursor: pointer;
    &:hover{
        color: #00C587
    }
}
.circle {
    width: 6px;
    height: 6px;
    background-color: #cccccc;
    border-radius: 3px;
}
.investment-title {
    font-size: 16px;
    line-height: 36px;
    cursor: pointer;
    &:hover{
        color: #00C587
    }
}
.investment-title-big {
    font-size: 16px;
    line-height: 36px;
    font-weight: bold;
    cursor: pointer;
    &:hover{
        color: #00C587
    }
}
.new-title-16{
    color: #4A4A4A;
    font-size: 12px;
    &:hover{
        color: #00c587;
    }
}
</style>

<style lang="scss">
// tab 滚动样式 重写
.dynamic-tab-list{
    .ivu-tabs-bar{
        border-bottom: none;
        font-size:18px;
    }
    .ivu-tabs-ink-bar{
        top: 0px;
    }
    .ivu-tabs-nav-prev{
        font-size: 18px;
        line-height: 45px;
    }
    .ivu-tabs-nav-next{
        font-size: 18px;
        line-height: 45px;
    }
    .ivu-tabs-nav .ivu-tabs-tab{
        // border-top: 2px  solid #dcdee2;
        margin-right: 0px;
        font-size:18px;
        &:hover{
            border-top: 2px  solid #00c587;
        }
    }
    .ivu-tabs-nav .ivu-tabs-tab-active{
        border-top: 2px solid #00c587
    }
    .ivu-tabs-ink-bar{
        display: none;
    }
}
</style>

