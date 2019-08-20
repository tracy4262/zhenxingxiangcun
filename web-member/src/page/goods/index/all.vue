<template>
    <div>
        <div class="wrapper">
            <serviceSeach @on-search="onSearch" :keyWord="keyWord" placeholder="请输入产品名称进行搜索"></serviceSeach>
        </div>
        <div class="tc pb20 pt20">

            <!-- 1 团购 2 竞价 3 预售 4 定价 5 面议 -->
            <Button type="text" size="large" :class="[tabActive == 'price' ? 't-green' : '']" @click.native="handleTabsClick('price', 4)">定价</Button>
            <Button type="text" size="large" :class="[tabActive == 'groupBuying' ? 't-green' : '']" @click.native="handleTabsClick('groupBuying', 1)">团购</Button>
            <Button type="text" size="large" :class="[tabActive == 'biddingBrice' ? 't-green' : '']" @click.native="handleTabsClick('biddingBrice', 2)">竞价</Button>
            <Button type="text" size="large" :class="[tabActive == 'face' ? 't-green' : '']" @click.native="handleTabsClick('face', 5)">面议</Button>
            <Button type="text" size="large" :class="[tabActive == 'advanceSale' ? 't-green' : '']" @click.native="handleTabsClick('advanceSale', 3)">预售</Button>
            <Button type="text" size="large" :class="[tabActive == 'findRetrospect' ? 't-green' : '']" @click.native="handleTabsClick('findRetrospect')">可追溯</Button>
        </div>
        <div  class="slider">
            <div class="layouts">
            <min-nav></min-nav>
            </div>
            <Carousel arrow="never" autoplay v-model="silder" loop>
            <CarouselItem v-for="(item, index) in imgList" :key="index">
                <img :src="item.picture_url" alt="">
            </CarouselItem>
            </Carousel>
        </div>
        <div class="layouts">
            <div class="vui-flex vui-flex-middle">
                <div class="tc vui-flex-item pl100">
                    <span class="pl20 pr20 mt50 divider mb30">团购好货</span>
                </div>
                <div>
                    <Button type="text" @click="handleTabsClick('groupBuying', 1)">查看全部 <Icon type="ios-arrow-forward" /></Button>
                </div>
            </div>
            <div class="perService">
                <list :listData="groupBuyingData" @on-login="handleLogin"></list>
            </div>
            <div class="vui-flex vui-flex-middle">
                <div class="tc vui-flex-item pl100">
                    <span class="pl20 pr20 mt50 divider mb30">竞价商品</span>
                </div>
                <div>
                    <Button type="text" @click="handleTabsClick('biddingBrice', 2)">查看全部 <Icon type="ios-arrow-forward" /></Button>
                </div>
            </div>
            <div class="perService">
                <list :listData="biddingBriceData" :type="2" @on-login="handleLogin"></list>
            </div>
            <div class="vui-flex vui-flex-middle">
                <div class="tc vui-flex-item pl100">
                    <span class="pl20 pr20 mt50 divider mb30">预售商品</span>
                </div>
                <div>
                    <Button type="text" @click="handleTabsClick('advanceSale', 3)">查看全部 <Icon type="ios-arrow-forward" /></Button>
                </div>
            </div>
            <div class="perService">
                <list :listData="advanceSaleData" :type="3" @on-login="handleLogin"></list>
            </div>
            <div class="vui-flex vui-flex-middle">
                <div class="tc vui-flex-item pl100">
                    <span class="pl20 pr20 mt50 divider mb30">定价商品</span>
                </div>
                <div>
                    <Button type="text" @click="handleTabsClick('price', 4)">查看全部 <Icon type="ios-arrow-forward" /></Button>
                </div>
            </div>
            <div class="perService">
                <list :listData="priceData" :type="4" @on-login="handleLogin"></list>
            </div>
            <div class="vui-flex vui-flex-middle">
                <div class="tc vui-flex-item pl100">
                    <span class="pl20 pr20 mt50 divider mb30">价格面议类商品</span>
                </div>
                <div>
                    <Button type="text" @click="handleTabsClick('face', 5)">查看全部 <Icon type="ios-arrow-forward" /></Button>
                </div>
            </div>
            <div class="perService">
                <list :listData="faceData" :type="5" @on-login="handleLogin"></list>
            </div>
        </div>
    </div>
</template>
<script>
import minNav from '../components/min-nav'
import list from './components/new-list'
import serviceSeach from '../../51index/components/serviceSeach'
export default {
    components: {
        minNav,
        serviceSeach,
        list
    },
    data () {
        return {
            serverData: [],
            total: 0,
            pageSize: 10,
            current: 1,
            imgList: [],
            silder: 0,
            tabActive: 'all',
            list: {
                account: '',
                type: '', // 0垂钓 1采摘 2景区 3餐饮 4住宿 5咨询 ''全部
                default: '1',
                address: '',
                species: '',
                speciesId: '',
                industry: '',
                industryId: '',
                service_name:''
            },
            productLocation: '', //     行政区划
            industryType:'',   //行业分类
            species:'',  //物种
            keyWord: '',
            groupBuyingData: [], // 团购商品
            biddingBriceData: [], // 竞价商品
            advanceSaleData: [], // 预售商品
            priceData: [], // 定价商品
            faceData: [], // 价格面议类商品
        }
    },
    created () {
        // 无忧导航搜索跳转过来时应带查询条件
        if (this.$route.query.title && this.$route.query.title !== '') {
            this.list.service_name = this.$route.query.title
            this.keyWord = this.$route.query.title
            console.log('this.list.service_name', this.list.service_name)
        }
        this.handleInit(1)
        this.handleGetImgList()
    },
    methods: {
        // 登录
        handleLogin() {
            this.$parent.$refs["top"].loginuser();
        },
        handleTabsClick (name, type) {
            console.log(name)
            this.tabActive = name
            if (type) {
                this.$router.push(`/goods/retrospect?type=${type}`)
            } else {
                this.$router.push(`/goods/retrospect`)
            }
        },
        // 获取banner图
        handleGetImgList () {
            this.$api.post('/portal/shopCommdoity/findCommodityImage',{account: ''}).then(response => {
                if (response.code === 200) {
                    this.imgList = response.data
                }
            })
        },
        handleInit () {
            // this.list.pageSize = this.pageSize
            // this.list.pageNum = this.current
            // this.$api.post('/member/fishing/findProductServiceList', this.list).then(response => {
            //     if (response.code === 200) {
            //         this.serverData = response.data.dataList
            //         this.total = response.data.total
            //         console.log('serviceData', this.serverData)
            //     }
            // })
            // 团购
            this.$api.post("/shop/pushShopCommodity/findGroupBuying", {
                    keyword: this.keyword,
                    productLocation: this.productLocation,
                    industryType: this.industryType,
                    species: this.species,
                    num: 1,
                    size: 5
                }).then(res => {
                if (res.code === 200) {
                    let data = res.data.list ? res.data.list : []
                    data.forEach(element => {
                        if (element.groupBuyingEndTimeStr) {
                            let time = ['2019-04-07', element.groupBuyingEndTimeStr]

                            let discountStartTime = new Date(time[0])
                            let discountEndTime = new Date(time[1])
                            element.discountEndTime = this.moment(new Date(time[1])).format('YYYY-MM-DD HH:MM:SS')
                            let currentTime = new Date()
                            console.log(discountStartTime)
                            if (discountStartTime < currentTime && discountEndTime > currentTime) {
                                element.isDiscount = true
                            } else {
                                element.isDiscount = false
                            }
                        }
                    })
                    this.groupBuyingData = data
                }
            });
            // 竞价
            this.$api.post("/shop/pushShopCommodity/findBidding", {
                    keyword: this.keyword,
                    productLocation: this.productLocation,
                    industryType: this.industryType,
                    species: this.species,
                    num: 1,
                    size: 5
                }).then(res => {
                if (res.code === 200) {
                    let data = res.data.list ? res.data.list : []
                    data.forEach(element => {
                        if (element.biddingEndTimeStr) {
                            let time = ['2019-04-07', element.biddingEndTimeStr]
                            let discountStartTime = new Date(time[0])
                            let discountEndTime = new Date(time[1])
                            element.discountEndTime = this.moment(new Date(time[1])).format('YYYY-MM-DD HH:MM:SS')
                            let currentTime = new Date()
                            console.log(discountStartTime)
                            if (discountStartTime < currentTime && discountEndTime > currentTime) {
                                element.isDiscount = true
                            } else {
                                element.isDiscount = false
                            }
                        }
                    })
                    this.biddingBriceData = data
                }
            });
            // 预售
            this.$api.post("/shop/pushShopCommodity/findPresale", {
                    keyword: this.keyword,
                    productLocation: this.productLocation,
                    industryType: this.industryType,
                    species: this.species,
                    num: 1,
                    size: 5
                }).then(res => {
                if (res.code === 200) {
                    let data = res.data.list ? res.data.list : []
                    this.advanceSaleData = data
                }
            });
            // 定价
            this.$api.post("/shop/pushShopCommodity/findPricing", {
                    keyword: this.keyword,
                    productLocation: this.productLocation,
                    industryType: this.industryType,
                    species: this.species,
                    num: 1,
                    size: 5
                }).then(res => {
                if (res.code === 200) {
                    let data = res.data.list ? res.data.list : []
                    data.forEach(element => {
                        if (element.discountPeriodStr) {
                            let times = element.discountPeriodStr.split('-')
                            let time = [`${times[0]}-${times[1]}-${times[2]}`, `${times[3]}-${times[4]}-${times[5]}`]

                            let discountStartTime = new Date(time[0])
                            let discountEndTime = new Date(time[1])
                            element.discountEndTime = this.moment(new Date(time[1])).format('YYYY-MM-DD HH:MM:SS')
                            let currentTime = new Date()
                            if (discountStartTime < currentTime && discountEndTime > currentTime) {
                                element.isDiscount = true
                            } else {
                                element.isDiscount = false
                            }
                        }
                    })
                    this.priceData = data
                }
            });
            // 面议
            this.$api.post("/shop/pushShopCommodity/findFace", {
                    keyword: this.keyword,
                    productLocation: this.productLocation,
                    industryType: this.industryType,
                    species: this.species,
                    num: 1,
                    size: 5
                }).then(res => {
                if (res.code === 200) {
                    let data = res.data.list ? res.data.list : []
                    this.faceData = data
                }
            });
        },
        onSearch (info) {
            // this.list = Object.assign(this.list, info)
            console.log(info)
            this.keyword = info.service_name
            this.productLocation = info.address
            this.industryType = info.industry
            this.species = info.species
            this.handleChangePage(1)
        },
        handleChangePage (page) {
            this.current = page
            this.handleInit(page)
        }
    }
}
</script>
<style lang="scss" scoped>
.divider {
    font-size:24px;
    color:#4a4a4a;
    display: inline-block; 
    height: 4px; 
    line-height: 6px; 
    border-left: 60px solid #797979; 
    border-right: 60px solid #797979;
}
.slider {
  position: relative;
  height: 385px;
  .layouts {
    position: relative;
  }
  img {
    height: 385px;
    width: 100%;
  }
}
</style>