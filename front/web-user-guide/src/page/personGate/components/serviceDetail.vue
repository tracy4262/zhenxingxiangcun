<template>
  <div class="layouts service-detail">
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
     <div>
      <Row class="mt20 mb20">
        <Col span="13">
          <product-view ref="view"></product-view>
        </Col>
        <Col span="11" class="pl20">
          <Row>
            <Col span="17">
              <p class="b" style="font-size:24px;color:#666;">{{data.service_name}} <b style="font-size:14px;cursor: pointer;">在线预览</b></p>
              <!-- <p class="t-grey">{{data.describe}}</p> -->
            </Col>
            <Col span="7" class="tr">
              <Rate disabled v-model="star" allow-half></Rate>
              <p class="t-grey">已有{{stars}}人评价</p>
            </Col>
          </Row>
          <div class="mt10 mb20 pd15" style="background: #F6F3F9;">
            <Row type="flex" align="middle">
              <Col span="18">
                <div>
                  <span class="t-orange h6" >团购价： ￥
                    <b class="h1"> 
                      <span v-if="buyData.discountPrice">{{buyData.discountPrice}}</span> 
                      <span v-else>0.00</span> 
                    </b>
                  </span>
                  <span class="t-grey ml20">价值： ￥
                    <b style="text-decoration: line-through;">
                      <span v-if="buyData.price">{{buyData.price}}</span> 
                      <span v-else>0.00</span> 
                    </b>
                  </span>
                </div>
              </Col>
              <Col span="6" class="tr">
                <p class="mt10" style="color:#666">{{data.orderCount}}人已团购</p>
              </Col>
            </Row>
          </div>
          <div>
            <p style="color:#666;">{{data.simple_describe}}</p>
          </div>
          <div class="pt20 vui-form-sm pb10" v-if="type > 1">
            <Form :label-width="80">
              <FormItem label="套餐：">
                <Button type="default" class="mr20" @click="handleCheckPackage">套餐选择</Button>
                <Button type="default" @click="handleCheckPackageOnline">在线选择</Button>
              </FormItem>
              <FormItem label="已选择：">
                <Button type="default" class="mr10 active-btn" style="margin-bottom: 10px;" v-for="(item, index) in checkedData" >{{item.setMealName}}</Button>
              </FormItem>
            </Form>
          </div>
          <div v-else class="pt20 vui-form-sm pb10">
            <Form :label-width="90">
              <FormItem :label="type == 0 ? '当季垂钓：' : '当季采摘：'">
              </FormItem>
              <FormItem label="其它季节：">
                <Button type="default" class="mr10" style="margin-bottom: 10px;" :class="acctiveProduct == index ? 'active-btn': ''" v-for="(item, index) in fishingListData" @click="checkProduct(item, index)">{{item.product_name}}</Button>
              </FormItem>
            </Form>
          </div>
          <div v-if="type <= 1">
            <Row>
              <Col span="7" class="pr10">
                <Button type="primary" size="large" long @click="handleBuy">
                  {{type == 0 ? '我要垂钓' : '我要采摘'}}
                </Button>
              </Col>
            </Row>
          </div>
          <div v-if="type > 1">
            <Row>
              <Col span="7" class="pr10">
                <Button type="primary" size="large" long @click="handleBuy">立即购买</Button>
              </Col>
              <Col span="7" class="pl10">
                <Button type="default" size="large" long class="active-btn"><Icon type="ios-cart"></Icon> 加入购物车</Button>
              </Col>
            </Row>
          </div>
          <div>
            <Row type="flex" align="middle" class="pt40">
              <Col span="5" >
              分享到：
              </Col>
              <Col span="13">
                <img src="../../../img/weixin-icon.png" alt="" width="28px" height="28px"  class="mr10">
                <img src="../../../img/QQ.png" alt="" width="28px" height="28px"  class="mr10">
                <img src="../../../img/weibo.png" alt="" width="28px" height="28px"  class="mr10">
              </Col>
              <Col span="6" class="tr pr20">
                <Icon type="ios-star-outline" size="24" class="t-green"></Icon>
                收藏
              </Col>

            </Row>
          </div>
        </Col>
        </Row>
    </div>
    <div>
      <Row class="mt30 mb30">
        <Col span="24">
          <div style="border: 1px solid #F4F4F4;">
          <Affix :offset-top="74">
            <Menu mode="horizontal" theme="light" active-name="generalInfo" @on-select="handleMenuChange">
              <MenuItem name="generalInfo">通用信息</MenuItem>
              <MenuItem name="sellerInfo">商家信息</MenuItem>
              <MenuItem name="serviceIntroduction">服务介绍</MenuItem>
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
              <Form :label-width="150">
                  <FormItem label="联系人：">{{data.contact_name}}</FormItem>
                  <FormItem label="联系QQ：">{{data.qq_number}}</FormItem>
                  <FormItem label="联系电话：">{{data.phone}}</FormItem>
                  <FormItem label="联系邮箱：">{{data.email}}</FormItem>
                  <FormItem label="联系地址：">
                    <span  v-if="data.address">{{data.address}} <span v-if="data.addrs">/</span>{{data.addrs}}</span>
                    <span v-else>{{data.address}}</span>
                  </FormItem>
              </Form>
            </div>
            <div v-if="activeName == 'serviceIntroduction'" class="pt30 pd20 vui-form-ssm">
              <div v-html="data.service_describe">服务介绍</div>
            </div>
            <div class="pt30 pd20 vui-form-ssm" v-if="activeName == 'productInfo'">
               <!-- 垂钓  -->
              <fishingList v-if="type == 0" :typeId="type" :data="fishingListData" :type="type"></fishingList>
               <!-- 采摘  -->
              <fishingList v-if="type == 1" :typeId="type" :data="fishingListData" :type="type"></fishingList>
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
                  <FormItem label="服务时间：">{{data.service_time}}</FormItem>
                  <FormItem label="诚信承诺：">{{data.promise_content}}</FormItem>
              </Form>
            </div>
            <div class="pt30 pb20 vui-form-ssm" v-if="activeName == 'mapInfo'">
              <Row class="traffic-table-bus pd20">
                <Col span="4" class="tc"> <Icon type="android-bus" class="t-green pl5" size="22" style="vertical-align: top;"></Icon> 交通引导 </Col>
                <Col span="20" class="tl">
                  <p style="word-wrap:break-word;">{{data.traffic_lead}}</p>
                </Col>
                 <Col span="24" v-if="data.coordinate" class="pt30">
                    <a target="_blank" :href="`http://api.map.baidu.com/marker?location=${data.coordinates.lat},${data.coordinates.lng}&title=我的位置&content=${location}&output=html`">
                        <img :src="`//api.map.baidu.com/staticimage?height=200&center=${data.coordinates.lng},${data.coordinates.lat}&zoom=15&scale=2&markers=${data.coordinates.lng},${data.coordinates.lat}`" alt="" width="100%">
                    </a>
                </Col>
              </Row>
              
            </div>
            <div  class="pt30 pd20" v-if="activeName == 'gradeInfo'">
              <comments @on-login="handleLogin" ref="comments" @on-stars="getStars" ></comments>
            </div>
          </div>
          </div>
        </Col>
      </Row>
    </div>
     <restaurantList ref="restaurantList" :data="packageData" @on-buy="selectionChange" ></restaurantList>
     <restaurantOnlineList ref="restaurantOnlineList" :packageData="data" @on-buy="selectionChange"></restaurantOnlineList>
     
     <scenicSpotList ref="scenicSpotList" :data="packageData" @on-buy="selectionChange" ></scenicSpotList>
     <scenicSpotOnlineList ref="scenicSpotOnlineList" :packageData="data" @on-buy="selectionChange"></scenicSpotOnlineList>
     
     <stayList ref="stayList" :data="packageData" @on-buy="selectionChange" ></stayList>
     <stayOnlineList ref="stayOnlineList" :packageData="data" @on-buy="selectionChange"></stayOnlineList>
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
import $ from 'jquery'
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
      comments
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
        stars:'',
        // 面包屑用到的
        serviceLink: '',
        serviceType: '',
        id: '',
        type: '',
        account: '',
        star: 0,
        // tab切换的当前值
        activeName: 'generalInfo',
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
        loginUser: JSON.parse(sessionStorage.getItem('user')),
        loginAccount: '',
        fishingListData: [],
        acctiveProduct: -1,
        checkedData: [],
        buyData:{},
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
        this.handleInitfishingList()
      }
      if (this.type == 4) {
        this.getStayDatas()
      }
      if (this.type == 2) {
        this.getScenicSpotDatas()
      }
    },
    methods: {
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
      },
      // 点击立即购买
      handleBuy () {
        console.log(this.buyData)
        if (this.loginUser) {
          if (this.buyData.contactName) {
            this.$api.post('/member/fishing/saveOrder', this.buyData).then(response => {
              console.log(response)
              if (response.code == 200) {
                this.$Message.success('购买成功')
                this.handleInit()
              }
            })
          } else {
            this.$Message.info('请选择套餐')
          }
        } else {
          this.handleLogin()
        }
      },
      // 初始化信息查询
      handleInit () {
        this.$api.post('/member/fishing/findProductServiceById', {id: this.id, type: this.type}).then(response => {
          if (response.code === 200) {
            // 判断是此服务在创建时是否有套餐
            if (response.data[0].productList) {
              this.packageData = response.data
              this.packageData.forEach(e => {
                e.checked = false
                e.date = ''
                e.userTime = ''
                e.checkDate = false
                e._expanded = false
              })
            }
            this.data = response.data[0]
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
            if(this.data.coordinate){
              var center = {}
              center.lng = this.data.coordinate.split(',')[0]
              center.lat = this.data.coordinate.split(',')[1]
              this.data.coordinates = center
            }
            var v = this
            // 转换经纬度
            if(this.data.coordinate){
                $.ajax({
                    async : 'false',
                    url : 'http://api.map.baidu.com/geocoder/v2/',
                    type : 'GET',
                    dataType : 'jsonp',
                    jsonpCallback : 'showLocation',
                    timeout : '5000',
                    contentType : 'application/json;utf-8',
                    data : {
                        location: v.data.coordinates.lat + ',' + v.data.coordinates.lng,
                        output:'json',
                        ak:'7syPirZ2AWxacMfHeAfuujdDgFmxCB5g',
                        callback:'showLocation'
                    },
                    success : function(data) {
                        console.log('data',data)
                        v.location = data.result.formatted_address
                    }
                });
            }
            
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
  .ivu-menu-horizontal .ivu-menu-item, .ivu-menu-horizontal .ivu-menu-submenu{
    padding: 0 40px
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
