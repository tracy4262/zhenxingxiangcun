  <template>
  <div class="layouts service-detail">
     <div>
       <Row>
        <Col :span="relatedServicesData.length ? '20' : '24'">
          <div  class="pt10">
            <Breadcrumb class="pd20" v-if="isIndex">
              <BreadcrumbItem :to="`${links[0]}`">服务</BreadcrumbItem>
              <BreadcrumbItem :to="`${links[1]}${serviceLink}`">{{serviceType}}</BreadcrumbItem>
              <BreadcrumbItem>{{data.service_name}}</BreadcrumbItem>
            </Breadcrumb>
            <Breadcrumb class="pd20" v-else>
              <BreadcrumbItem :to="`${links[0]}?uid=${account}`">服务</BreadcrumbItem>
              <BreadcrumbItem :to="`${links[1]}${serviceLink}?uid=${account}`">{{serviceType}}</BreadcrumbItem>
              <BreadcrumbItem>{{data.service_name}}</BreadcrumbItem>
            </Breadcrumb>
          </div>
          <Row class="mt20 mb20">
            <Col span="9">
              <product-view ref="view"></product-view>
            </Col>
            <Col span="15" class="pl20">
              <Row>
                <Col span="17">
                  <p  style="color:#666;">
                    <b style="font-size:24px;">{{data.service_name}} </b>
                    <span style="font-size:14px" v-if="data.contact && data.contact.length && data.contact[0].detailAddress"><Icon type="md-pin" />{{data.contact[0].detailAddress}}</span>
                    <a @click="openWindow(data.online_preview)" style="font-size:14px" target="_blank">查看实况</a>
                    <!-- <b style="font-size:14px;cursor: pointer;">{{data.online_preview}}</b> -->
                  </p>
                  <p class="pt10" v-if="type > 1">服务时间：{{data.coordinate}}</p>
                </Col>
                <Col span="7" class="tr">
                  <Rate disabled v-model="star" allow-half></Rate>
                  <p class="t-grey pt10">已有{{stars}}人评价</p>
                </Col>
              </Row>
              <div class="mt10 pd15" style="background: #F6F3F9;" v-if="type > 1">
                <Row type="flex" align="middle">
                  <Col span="18">
                    <div>
                      <span class="t-orange h6" >优惠价： ￥
                        <b class="h1">
                          <span v-if="checkedData[0] && checkedData[0].setMealPrice">{{checkedData[0].setMealPrice}}</span>
                          <span v-else>0.00</span>
                        </b>
                      </span>
                      <span class="t-grey ml20">原价： ￥
                        <b style="text-decoration: line-through;">
                          <span v-if="checkedData[0] && checkedData[0].totalPrice">{{checkedData[0].totalPrice}}</span>
                          <span v-else>0.00</span>
                        </b>
                      </span>
                    </div>
                  </Col>
                  <Col span="6" class="tr">
                    <p class="mt10" style="color:#666">{{data.orderCount}}人已团购</p>
                  </Col>
                </Row>
                <p><span class="t-red">* </span> 选择套餐后显示服务价格</p>
              </div>
              <div class="mt20">
                <p style="color:#666;">{{data.simple_describe}}</p>
              </div>
              <div class="pt20 vui-form-sm pb10" v-if="type > 1">
                <Form :label-width="80">
                  <FormItem label="套餐：">
                    <Button class="mr20 mb20" v-for="(item, index) in allPackageData" @click="handleCheckPackages(item)">
                      {{item.setMealName}} ￥ {{item.setMealPrice}} (<span style="text-decoration: line-through;">￥{{item.totalPrice}}</span>)
                    </Button>
                    <!-- <Button type="default" class="mr20 mb20" @click="handleCheckPackage">套餐选择</Button> -->
                    <Button type="default" class="mb20" @click="handleCheckPackageOnline">在线选择</Button>
                  </FormItem>
                  <FormItem label="已选择：">
                    <Button type="default" class="mr10 active-btn" style="margin-bottom: 10px;" v-for="(item, index) in checkedData" >
                      {{item.setMealName}} ￥ {{item.setMealPrice}} (<span style="text-decoration: line-through;">￥{{item.totalPrice}}</span>)
                    </Button>
                  </FormItem>
                </Form>
              </div>
              <div v-else class="pt20 vui-form-sm pb10" :class="{'step3-info': step3Show}" style="overflow: hidden;">
                <Form :label-width="90">
                  <FormItem label="垂钓季节：" v-if="type == 0">
                    {{data.season}}
                  </FormItem>
                  <FormItem :label="type == 0 ? '开放时间段：' : '服务时间：'">
                    {{data.coordinate}}
                  </FormItem>
                  <FormItem label="服务方式：">
                    <div>
                      <div v-if="type == 0 && step3Info.timeCharging">
                        <p>按钓鱼时间</p>
                        <p class="t-grey">收费标准：
                          <span v-for="(item, index) in step3Info.fishTimeCharge">
                            <span v-if="item.discount">
                              {{item.fishDuration}} ￥ {{item.discount}}
                              <span style="text-decoration: line-through;">￥ {{item.durationPrice}}</span> ；
                            </span>
                            <span v-else>
                              {{item.fishDuration}} ￥ {{item.durationPrice}}；
                            </span>
                          </span>
                        </p>
                        <p class="t-grey">可垂钓品种：
                          <span v-for="(item, index) in step3Info.fishVarietyCharge">
                            {{item.productName}}、
                          </span>
                        </p>
                      </div>
                      <div v-if="step3Info.timeVariety">
                        <p v-if="type == 0">按钓鱼品种</p>
                        <p v-if="type == 1">按采摘品种</p>
                        <p v-for="(item, index) in step3Info.fishVarietyCharge" class="t-grey">
                          <span v-if="item.durationPrice">{{item.productName}}：{{item.durationPrice}}/{{item.unit}} <span style="text-decoration: line-through;">{{item.productPrice}}/{{item.unit}} </span> </span>
                          <span else>{{item.productName}}：{{item.productPrice}}/{{item.unit}} </span>
                        </p>
                      </div>

                    </div>
                    <!-- <Button type="default" class="mr10" style="margin-bottom: 10px;" :class="acctiveProduct == index ? 'active-btn': ''" v-for="(item, index) in fishingListData" @click="checkProduct(item, index)">{{item.product_name}}</Button> -->
                  </FormItem>
                </Form>
              </div>
              <div class="tc" v-if="type < 2">
                <Button v-if="step3Show" type="text" @click="step3Show = false">
                  <Icon type="ios-arrow-down" size="20"/>
                </Button>
                <Button v-else type="text" @click="step3Show = true">
                  <Icon type="ios-arrow-up" size="20"/>
                </Button>
              </div>
              <Row>
                <Col span="7" class="pr10">
                  <Button v-if="type > 1" type="primary" size="large" long @click="handleBuy">立即购买</Button>
                  <Button v-if="type == 1" type="primary" size="large" long @click="handleMap">我要采摘</Button>
                  <Button v-if="type == 0"type="primary" size="large" long @click="handleMap">我要垂钓</Button>
                </Col>
              </Row>
              <div>
                <Row type="flex" align="middle" :class="[type > 1 ? 'pt40' : 'pt15']">
                  <Col span="12">
                    <span class="mr10">分享到：</span>
                    <div style="display: inline-block; vertical-align: middle;">
                      <vue-share></vue-share>
                    </div>
                  </Col>
                  <Col span="12" class="tr pr20">
                    <Button type="text" @click="handleCollect" v-if="!isCollect">
                      <Icon type="ios-star-outline" size="24" class="t-green"></Icon>
                      收藏
                    </Button>
                    <Button type="text" @click="handleCollect" v-else>
                      <Icon type="ios-star" size="24" class="t-green"></Icon>
                      已收藏
                    </Button>
                  </Col>

                </Row>
              </div>
            </Col>
          </Row>
         </Col>
         <Col span="4" class="pl20">
          <relatedServices ref="relatedServices"></relatedServices>
         </Col>
       </Row>

    </div>
    <div>
      <Row>
        <Col span="5">
          <div style="border: 1px solid #F4F4F4;" class="mt30 mb30">
            <detail-title :name="sellerData.name" :avatar="sellerData.avatar" grey>
              <Button type="text" size="small" @click.stop="webimchat"><Icon type="md-text" class="t-green"  size="16"></Icon> 发起聊天</Button>
            </detail-title>
            <div style="border-top: 1px solid #F4F4F4;" v-if="data.contact && data.contact.length" v-for="(item, index) in data.contact">
              <div class="pd10">
                <p style="line-height: 24px;"><span class="t-grey"><Icon type="ios-call" size="20"/> 座机电话：</span> {{item.seat_phone}}</p>
                <p style="line-height: 24px;"><span class="t-grey"><Icon type="ios-phone-portrait" size="20"/> 手机号码：</span>{{item.phone}}</p>
                <p style="line-height: 24px;"><span class="t-grey"><Icon type="ios-mail" size="20"/> 邮箱：</span>{{item.email}}</p>
              </div>
            </div>
          </div>
        </Col>
        <Col span="19" class="pl15">
          <Row class="mt30 mb30">
            <Col span="24">
              <div style="border: 1px solid #F4F4F4;">
              <Affix :offset-top="98">
                <Menu mode="horizontal" theme="light" :active-name="activeName" @on-select="handleMenuChange">
                  <MenuItem name="serviceIntroduction">服务介绍</MenuItem>
                  <MenuItem name="generalInfo">通用信息</MenuItem>
                  <MenuItem name="sellerInfo">网点服务</MenuItem>
                  <MenuItem name="productInfo">产品展示</MenuItem>
                  <MenuItem name="serviceInfo">服务信息</MenuItem>
                  <MenuItem name="mapInfo">地图信息</MenuItem>
                  <MenuItem name="gradeInfo">评论信息({{stars ? stars : '0'}})</MenuItem>
                </Menu>
              </Affix>
              <div class="dp20">
                <div class="pt30 pb20 vui-form-ssm" v-if="activeName == 'generalInfo'">
                  <Form :label-width="150">
                    <FormItem label="通用服务名：">{{data.currency_service_name}}</FormItem>
                    <FormItem label="行业分类：">{{data.trade_class_id}}</FormItem>
                    <FormItem label="服务分类：">{{data.service_class_id}}</FormItem>
                  </Form>
                </div>
                <div class="pt30 pb20 vui-form-ssm" v-if="activeName == 'sellerInfo'">
                  <selectBusinessOutletCard :datas="serviceOutlet"></selectBusinessOutletCard>
                  <!-- <Form :label-width="150">
                      <FormItem label="联系人：">{{data.contact_name}}</FormItem>
                      <FormItem label="联系QQ：">{{data.qq_number}}</FormItem>
                      <FormItem label="联系电话：">{{data.phone}}</FormItem>
                      <FormItem label="联系邮箱：">{{data.email}}</FormItem>
                      <FormItem label="联系地址：">
                        <span  v-if="data.address">{{data.address}} <span v-if="data.addrs">/</span>{{data.addrs}}</span>
                        <span v-else>{{data.address}}</span>
                      </FormItem>
                  </Form> -->
                </div>
                <div v-if="activeName == 'serviceIntroduction'" class="pt30 pd20 vui-form-ssm">
                  <div v-html="data.service_describe">服务介绍</div>
                </div>
                <div class="pt30 pd20 vui-form-ssm" v-if="activeName == 'productInfo'">
                  <!-- 垂钓  -->
                  <fishingList v-if="type == 0" :typeId="type" :data="step3Info.fishVarietyCharge" :type="type"></fishingList>
                  <!-- 采摘  -->
                  <fishingList v-if="type == 1" :typeId="type" :data="step3Info.fishVarietyCharge" :type="type"></fishingList>
                  <!-- 景区 -->
                  <Table  :columns="scenicSpotColumns" :data="scenicSpotDatas" v-if="type == 2"></Table>
                  <!-- 农家乐  -->
                  <dishList v-if="type == 3"></dishList>
                  <!-- 民宿 -->
                  <Table  :columns="stayColumns" :data="stayDatas" v-if="type == 4"></Table>
                </div>
                <div class="pt30 pb20 vui-form-ssm" v-if="activeName == 'serviceInfo'">
                  <Form :label-width="150">
                      <FormItem label="注意事项：">{{data.mattres_need_attention}}</FormItem>
                      <!-- <FormItem label="服务时间：">{{data.service_time}}</FormItem> -->
                      <FormItem label="诚信承诺：">{{data.promise_content}}</FormItem>
                  </Form>
                </div>
                <div class="pt30 pb20 vui-form-ssm" v-if="activeName == 'mapInfo'">
                  <mapInfo :datas="data"></mapInfo>
                </div>
                <div  class="pt30 pd20" v-if="activeName == 'gradeInfo'">
                  <comments @on-login="handleLogin" ref="comments" @on-stars="getStars" ></comments>
                </div>
              </div>
              </div>
            </Col>
          </Row>
        </Col>
      </Row>
    </div>
     <restaurantList ref="restaurantList" :data="packageData" @on-buy="selectionChange"  @on-save="selectionSave"></restaurantList>
     <restaurantOnlineList ref="restaurantOnlineList" :packageData="data" @on-buy="selectionChange" @on-save="selectionSaves"></restaurantOnlineList>

     <scenicSpotList ref="scenicSpotList" :data="packageData" @on-buy="selectionChange" @on-save="selectionSave"></scenicSpotList>
     <scenicSpotOnlineList ref="scenicSpotOnlineList" :packageData="data" @on-buy="selectionChange"  @on-save="selectionSaves"></scenicSpotOnlineList>

     <stayList ref="stayList" :data="packageData" @on-buy="selectionChange" @on-save="selectionSave"></stayList>
     <stayOnlineList ref="stayOnlineList" :packageData="data" @on-buy="selectionChange" @on-save="selectionSaves"></stayOnlineList>
     <collectModal ref="collectModal" :type="collectType" :link="link" :collectTitle="data.service_name" @on-init="checkCollect"></collectModal>
    <Modal
      v-model="showMap"
      width="780"
      title="路线指引">
      <mapInfo :datas="data"></mapInfo>
      <div slot="footer"></div>
    </Modal>
  </div>
</template>
<script>
import productView from '../../goods/detail/components/view'
import storeInfo from '../../goods/detail/components/store-info'
import restaurantList from './serviceComponents/restaurantList'
import restaurantOnlineList from './serviceComponents/restaurantOnlineList'

import scenicSpotList from './serviceComponents/scenicSpotList'
import scenicSpotOnlineList from './serviceComponents/scenicSpotOnlineList'

import stayList from './serviceComponents/stayList'
import stayOnlineList from './serviceComponents/stayOnlineList'

import dishList from './serviceComponents/dishList'
import fishingList from './serviceComponents/fishingList'
import comments from './serviceComponents/comments'
import relatedServices from './serviceComponents/relatedServices'
import $ from 'jquery'
import collectModal from '~components/collectModal'
import selectBusinessOutletCard from './serviceComponents/selectBusinessOutletCard'
import vueShare from './serviceComponents/vue-share'
import detailTitle from './serviceComponents/webimchatTitle'
import mapInfo from './serviceComponents/mapInfo'
import top from '~src/top'
  export default {
    components: {
      productView,
      storeInfo,
      restaurantList,
      restaurantOnlineList,
      scenicSpotList,
      scenicSpotOnlineList,
      stayList,
      stayOnlineList,
      dishList,
      fishingList,
      comments,
      relatedServices,
      collectModal,
      selectBusinessOutletCard,
      top,
      vueShare,
      detailTitle,
      mapInfo
    },
    props: {
      links: {
        type: Array,
        default: () => {
          return []
        }
      },
      isIndex: {
        type: Boolean,
        default: false
      }
  },
    data () {
      return {
        showMap: false,
        stars:'',
        // 面包屑用到的
        serviceLink: '',
        serviceType: '',
        id: '',
        type: '',
        account: '',
        star: 0,
        // tab切换的当前值 generalInfo
        activeName: 'serviceIntroduction',
        // 服务介绍
        serviceIntroduction: '',
        // 产品展示的数据
        stayColumns: [
            {
                title: '房间图片',
                align: 'center',
                key: 'roomImage',
                width: 160,
                render: (h, params) => {
                    return h('div',{
                      style: {
                          padding: '20px 5px'
                      }
                    },[
                       h('img', {
                          domProps: {
                            src: params.row.roomImage ? params.row.roomImage[0] : '../../../../../static/img/goods-list-no-picture1.png'
                          },
                          style: {
                              width: '120px',
                              height: '80px'
                          }
                      }),
                    ])
                }
            },
            {
                title: '房间名称',
                align: 'center',
                key: 'roomName',
                width: 300,
            },
            {
                title: '房间价格',
                align: 'center',
                key: 'discountPrice',
                width: 200,
                render: (h, params) => {
                    return h('span',`￥ ${params.row.discountPrice ? params.row.discountPrice : params.row.roomPrice}`)
                }
            },
            {
                title: '房间描述',
                align: 'center',
                key: 'roomDescribe',
            },
        ],
        stayDatas: [],
        scenicSpotDatas: [],
        scenicSpotColumns: [
            {
                title: '门票名称',
                align: 'center',
                key: 'ticketName',
                width: 300,
            },
            {
                title: '门票价格',
                align: 'center',
                key: 'ticketPrice',
                width: 200,
                render: (h, params) => {
                    return h('span',`￥ ${params.row.discountPrice ? params.row.discountPrice : params.row.ticketPrice}`)
                }
            },
            {
                title: '描述',
                align: 'center',
                key: 'scenicDescribe',
            },
        ],
        packageData: [],
        allPackageData: [],
        // 卖家信息
        sellerData: {
          account: '',
          qq: '',
          phone: '',
          email: '',
          name: ''
        },
        data: {coordinates:{}, commentList: {}},
        location: '',
        loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
        loginAccount: '',
        fishingListData: [],
        acctiveProduct: -1,
        checkedData: [],
        buyData:{},
        contactData: [],
        serviceOutlet: [],
        link: this.$route.fullPath,
        collectType: '',
        selectionSaveData: [],
        isCollect: false,
        selectionType: 0,
        sellerData:{},
        step3Info: {
          timeCharging: false,
          fishTimeCharge: [],
          timeVariety: false,
          fishVarietyCharge: [],
        },
        step3Show: true,
        relatedServicesData: []
      }
    },
    created() {
      this.type = this.$route.query.type
      this.id = this.$route.query.id
      this.account = this.$route.query.uid
      this.loginAccount = this.loginUser ? this.loginUser.loginAccount : ''
      // 查询卖家信息
      // this.handleSellerData()
      // 初始化数据查询
      this.handleInit()
      // 判断服务类型
      this.handleJudgment()
      if (this.type < 2) {
        // 初始化查询垂钓采摘产品
        // this.handleInitfishingList()
      }
      if (this.type == 4) {
        this.getStayDatas()
      }
      if (this.type == 2) {
        this.getScenicSpotDatas()
      }
      //0 垂钓 1采摘 2景区 3餐饮 4住宿。
      // fishService 垂钓， pickService 采摘  farmStayService 农家乐  roomService 住宿 scenicSpotService 景区
      if (this.type == 0) {
        this.collectType = 'fishService'
        this.getStep3Info()
      } else if (this.type == 1) {
        this.collectType = 'pickService'
        this.getStep3Info()
      } else if (this.type == 2) {
        this.collectType = 'scenicSpotService'
      } else if (this.type == 3) {
        this.collectType = 'farmStayService'
      } else if (this.type == 4) {
        this.collectType = 'roomService'
      }
      this.getIsRelationData()
      this.checkCollect()
      // 查询 用户信息
      this.$api.post('/member/fishing/findAvatar',{account: this.account}).then(response => {
          if (response.code == 200) {
            this.sellerData = response.data
          }
        })
    },
    methods: {
      handleMap () {
        this.showMap = true
      },
      getStep3Info () {
        // 根据用户id查询 第三步信息
        this.$api.post('/member/fishing/findFishingService',{id: this.id,pageNum:1}).then(response => {
            if(response.code == 200){
                if(response.data.list[0]){
                    let data = response.data.list[0]
                    this.step3Info.timeCharging = data.timeCharging
                    this.step3Info.fishTimeCharge = data.fishTimeCharge && data.fishTimeCharge.length ? data.fishTimeCharge : []
                    this.step3Info.money = data.money
                    this.step3Info.timeVariety = data.timeVariety
                    this.step3Info.fishVarietyCharge = data.fishVarietyCharge && data.fishVarietyCharge.length ? data.fishVarietyCharge : data.variety
                }
            }
        })
      },
      // 查询是否收藏过
      checkCollect () {
        this.$api.post('/member/report/isExist', {
          link: this.$route.fullPath,
          account: this.$user.loginAccount
        }).then(resp => {
          if (200 === resp.code) {
            if (resp.data === 1) { // 已收藏
              this.isCollect = true
            } else {
              this.isCollect = false
            }
          }
        })
      },
      getIsRelationData () {
          this.$api.post('/member/fishing/findJoinServiceList', {
              // account: this.$user.loginAccount,
              service_name: '',
              joinService: 1, //  0 未关联。 1已关联
              pageNum: 1,
              pageSize: 100,
              id: this.id,
              type: '' // 0垂钓 1采摘 2景区 3餐饮 4住宿 空为全部
          }).then(response => {
              if (response.code === 200) {
                this.relatedServicesData = response.data.dataList
                this.$refs['relatedServices'].init(response.data.dataList)
              }
          })
      },
      // 登录
      handleLogin (){
        this.$refs['top'].loginuser()
      },
      // 聊天
      webimchat () {
        if (!this.$user.loginAccount) {
          this.$Message.error('请登录后再发起聊天')
          this.handleLogin()
          return
        }
        layui.layim.chat({
          id: this.sellerData.userId,
          name: this.sellerData.name,
          avatar: this.sellerData.avatar,
          type: 'friend'
        })
      },
      // 点击收藏
      handleCollect () {
      if (this.$user) {
        console.log(this.isCollect)
        if (this.isCollect) {
          this.$Message.error('请勿重复收藏')
        } else {
          this.$refs['collectModal'].show = true
        }
      } else {
        this.$Message.error('请先登录!')
        this.handleLogin()
      }
      },
      openWindow (url) {
        // window.open('http://www.baidu.com')
        window.open(url)
      },
      // 获取评论人数
      getStars (e) {
        console.log('获取评论数据')
        this.stars = e.stars
        this.star = e.star
      },
      // 初始化查询垂钓采摘产品
      handleInitfishingList () {
        this.$api.post('/member/fishing/findFishingProduct', {
              species_id: '',
              status: '',
              account: this.account,
              pageNum: 1,
              pageSize: 100000,
              type: this.type // 0垂钓 1采摘
            }).then(response => {
                console.log('res table', response)
                if (response.code === 200) {
                  this.fishingListData = response.data.list
                }
            }).catch(error => {
                console.log(error)
            })
      },
      // 选择产品 垂钓采摘
      checkProduct (item, index) {
        let value = item.fishing_time
        if (value) {
          value = value.replace(/[\u4e00-\u9fa5]/g, '/')
          let arr = value.split('-')
          arr[0] = arr[0].substring(0, arr[0].length-2)
          arr[1] = arr[1].substring(0, arr[1].length-1)
          let currentTime = new Date()
          let starTime = new Date(arr[0])
          let endTime = new Date(arr[1])
          if (currentTime > endTime) {
            // 当前时间大于结束时间 ， 已经结束了
            this.$Message.error('服务时间已结束')
            return
          }
          if (currentTime < starTime) {
            // 当前时间小于开始时间 ， 还没到时间
            this.$Message.error('服务时间未开始')
            return
          }
        }
        this.acctiveProduct = index
        let data = {
          contactName: this.data.contact_name,
          phone: this.data.phone,
          account: this.data.account,
          sellAccount: this.loginAccount,
          setMealId: item.id,
          // scenicSpotData: scenicSpotData,
          type: this.type,
          serviceId: this.id,
          discountPrice: item.discount_price,
          price: item.product_price,
        }
        this.buyData = data
      },
      // 判断是哪一种服务类型
      handleJudgment () {
        let type = this.type
        if (type == 0) {
          this.serviceType = '垂钓'
          this.serviceLink = '/fishing'
        } else if (type == 1) {
          this.serviceType = '采摘'
          this.serviceLink = '/picking'
        } else if (type == 2) {
          this.serviceType = '景区'
          this.serviceLink = '/scenicSpot'
        } else if (type == 3) {
          this.serviceType = '农家乐'
          this.serviceLink = '/restaurant'
        } else if (type == 4) {
          this.serviceType = '民宿'
          this.serviceLink = '/stay'
        }
      },
      // 选择套餐
      selectionChange (o) {
        this.checkedData = o.checkedData
        this.buyData = o.data
        // this.buyData.setMealName = this.checkedData[0].setMealName
        // this.buyData.setMeal = this.checkedData[0]
        console.log('this.buyData')
        console.log(this.buyData)
        this.$api.post('/member/fishing/saveOrder', this.buyData).then(response => {
          if (response.code == 200) {
            this.$Message.success('购买成功')
            this.handleInit()
          }
        })
      },
      selectionSave (o) {
        this.selectionType = 1
        this.checkedData = o.checkedData
        this.buyData = o.data
      },
      selectionSaves (o) {
        this.selectionType = 2
        this.checkedData = o.checkedData
        this.buyData = o.data
      },
      // 点击立即购买
      handleBuy () {
        if (this.loginUser) {
          if (this.checkedData.length) {
            if (this.type == 2) {
              if (this.selectionType == 1) {
                this.$refs['scenicSpotList'].checkOrder(this.checkedData)
              } else {
                this.$refs['scenicSpotOnlineList'].checkOrder(this.buyData)
              }
            } else if (this.type == 3){
              if (this.selectionType == 1) {
                this.$refs['restaurantList'].checkOrder(this.checkedData)
              } else {
                this.$refs['restaurantOnlineList'].checkOrder(this.buyData)
              }
            } else if (this.type == 4){
              if (this.selectionType == 1) {
                this.$refs['stayList'].checkOrder(this.checkedData)
              } else {
                this.$refs['stayOnlineList'].checkOrder(this.buyData)
              }
            }
          } else {
            this.$Message.info('请选择套餐')
          }
          // if (this.buyData.contactName) {
          //   this.$api.post('/member/fishing/saveOrder', this.buyData).then(response => {
          //     console.log(response)
          //     if (response.code == 200) {
          //       this.$Message.success('购买成功')
          //       this.handleInit()
          //     }
          //   })
          // } else {
          //   this.$Message.info('请选择套餐')
          // }
        } else {
          this.handleLogin()
        }
      },
      // 初始化信息查询
      handleInit () {
        this.$api.post('/member/fishing/findProductServiceById', {id: this.id, type: this.type}).then(response => {
          if (response.code === 200) {
            this.checkedData = []
            // 判断是此服务在创建时是否有套餐
            if (response.data[0].productList) {
              this.packageData = response.data
              this.allPackageData = response.data
              this.packageData.forEach(e => {
                e.checked = true
                e.date = ''
                e.userTime = ''
                e.checkDate = false
                e._expanded = true
              })
            }
            this.data = response.data[0]
            this.serviceOutlet = this.data.serviceOutlet
            this.contactData = this.data.contact
            console.log('serviceOutletserviceOutletserviceOutletserviceOutletserviceOutlet' ,this.serviceOutlet)
            // 联系方式
            // this.$refs['contactData'].init(this.data.contact)
            // 取评论星
            this.stars = this.data.commentList ? this.data.commentList.length : 0
            if (this.data.commentProbabilityNum) {
              this.star = this.data.commentProbabilityNum.star
            } else {
              this.star = 0
            }
            // 取图片
            let images = []
            this.data.image_url.map(element => {
              images.push({url: element})
            })
            this.$refs['view'].getData(images)
          }
        })
      },
      // 获取住宿产品数据
      getStayDatas () {
        this.$api.post('/member/accommodation/findRoomList',
        // 0 空闲中 1 使用中
            {account: this.account, pageNum: 1, pageSize: 999999, status: '0', roomClassName: ''})
            .then(response => {
                if (response.code === 200) {
                  // 转换产品展示列表
                  let stayDatas = []
                  this.stayDatas = response.data.list
                }
            })
      },
       // 获取景区产品数据
      getScenicSpotDatas () {
        this.$api.post('/member/scenicSpot/findScenicSpot',
        // 0 空闲中 1 使用中
            {account: this.account, pageNum: 1, pageSize: 999999})
            .then(response => {
                if (response.code === 200) {
                  // 转换产品展示列表
                  let scenicSpotDatas = []
                  this.scenicSpotDatas = response.data.list
                }
            })
      },
      // 点击套餐选择
      handleCheckPackages (item) {
        let obj = Object.assign({}, item)
        // 根据服务类型不同判断 弹出那个服务套餐的弹框
        if (this.type == 2) {
          this.packageData = [obj]
          this.$refs['scenicSpotList'].isCheckPackage = true
          this.$refs['scenicSpotList'].showOrder = true
        } else if (this.type == 3){
          this.packageData = [obj]
          this.$refs['restaurantList'].isCheckPackage = true
          this.$refs['restaurantList'].showOrder = true
        } else if (this.type == 4){
          this.packageData = [obj]
          this.$refs['stayList'].isCheckPackage = true
          this.$refs['stayList'].showOrder = true
        }
      },
      handleCheckPackage () {
        // 根据服务类型不同判断 弹出那个服务套餐的弹框
        if (this.type == 2) {
          this.$refs['scenicSpotList'].isCheckPackage = true
          this.$refs['scenicSpotList'].showOrder = true
        } else if (this.type == 3){
          this.$refs['restaurantList'].isCheckPackage = true
          this.$refs['restaurantList'].showOrder = true
        } else if (this.type == 4){
          this.$refs['stayList'].isCheckPackage = true
          this.$refs['stayList'].showOrder = true
        }
      },
      // 点击在线选择
      handleCheckPackageOnline () {
        console.log('点击在线选择')
        // 根据服务类型不同判断 弹出那个服务套餐的弹框
        if (this.type == 2) {
          this.$refs['scenicSpotOnlineList'].show()
        } else if (this.type == 3) {
          this.$refs['restaurantOnlineList'].show()
        } else if (this.type == 4) {
          this.$refs['stayOnlineList'].show()
        }
      },
      // 切换
      handleMenuChange (name) {
        this.activeName = name
      },
      handleLogin () {
        this.$Message.error('请登录')
        this.$parent.$refs['top'].loginuser()
      }
    }
  }
</script>
<style lang="scss" scoped>
.service-detail{
  .step3-info{
    max-height: 265px;
  }
  .ivu-menu-horizontal .ivu-menu-item, .ivu-menu-horizontal .ivu-menu-submenu{
    padding: 0 30px
  }
  .vui-form-sm .ivu-form .ivu-form-item{
    margin-bottom: 10px;
  }
  .active-btn{
    color: #00c587;
    background-color: transparent;
    border-color: #00c587;
  }
  .active-btn-warning{
    color: #f90;
    background-color: transparent;
    border-color: #f90;
  }
}
</style>
