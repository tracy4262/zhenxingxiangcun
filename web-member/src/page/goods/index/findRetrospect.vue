<template>
    <div class="layouts">
        <div class="wrapper">
            <productSearch @on-search="onSearch" ></productSearch>
        </div>
        <productSort :activeName="activeName" @on-search="onSort"></productSort>
        <div class="perService" >
            <list :listData="data" :type="5" @on-login="handleLogin"></list>
            <Page v-if="data.length" class="mt30 tc pb50" :page-size="pageSize" :total="total" :current="current" @on-change="handleChangePage"></Page>
        </div>
        <!-- <div class="ma-polic-img tc" v-if="data.length === 0">
            <img src="../../../img/ma-img-002.png"> 
            <p style="margin-top: 10px;">暂无数据</p>
        </div> -->
    </div>
</template>
<script>
import list from './components/new-list'
import productSort from './components/productSort'
import productSearch from './components/productSearch'
export default {
    name: 'person-service-picking',
    components: {
        list,
        productSort,
        productSearch
    },
    data () {
        return {
            data: [],
            total: 0,
            pageSize: 10,
            current: 1,
            keyword: '',
            activeName: '',
            url: '',
            productCode: '',
            salesType: '',
            productStatus: '',
            retrospectType: '',
            type: '',
        }
    },
    created () {
        this.type = this.$route.query.type ? this.$route.query.type : '';

        // <!-- 1 团购 2 竞价 3 预售 4 定价 5 面议 -->
        if (this.type == '1') {
            this.url = '/shop/pushShopCommodity/findGroupBuying'
            this.activeName = '团购好货'
            this.salesType = '团购销售'
            this.productStatus = '现货产品'
            this.retrospectType = ''
        } else if (this.type == '2') {
            this.url = '/shop/pushShopCommodity/findBidding'
            this.activeName = '竞价商品'
            this.salesType = '竞价销售'
            this.productStatus = '现货产品'
            this.retrospectType = ''
        } else if (this.type == '3') {
            this.url = '/shop/pushShopCommodity/findPresale'
            this.activeName = '预售商品'
            this.salesType = ''
            this.productStatus = '预定产品'
            this.retrospectType = ''
        } else if (this.type == '4') {
            this.url = '/shop/pushShopCommodity/findPricing'
            this.activeName = '定价商品'
            this.salesType = '定价销售'
            this.productStatus = '现货产品'
            this.retrospectType = ''
        } else if (this.type == '5') {
            this.url = '/shop/pushShopCommodity/findFace'
            this.activeName = '面议类商品'
            this.salesType = '面议'
            this.productStatus = '现货产品'
            this.retrospectType = ''
        } else {
            this.url = '/shop/pushShopCommodity/findRetrospect'
            this.activeName = '可追溯商品'
            this.salesType = ''
            this.retrospectType = '是'
            this.productStatus = ''
        }
        this.handleInit(1)
    },
    methods: {
        // 登录
        handleLogin() {
            this.$parent.$refs["top"].loginuser();
        },
        handleInit (e) {
            this.$api.post('/shop/pushShopCommodity/findProduct', {
                    keyword: this.keyword,
                    code: this.productCode,
                    salesWay: this.salesType, // 其他产品类型
                    num: this.current,
                    size: this.pageSize,
                    retrospectType: this.retrospectType, // 可追溯产品
                    productStatus: this.productStatus // 预定产品
                }).then(res => {
                if (res.code === 200) {
                    let data = res.data.list ? res.data.list : []
                    data.forEach(element => {
                        if (element.salesWay == '团购销售') { // 团购
                            if (element.groupBuyingEndTimeStr) {
                                let time = [element.groupBuyingStartTimeStr, element.groupBuyingEndTimeStr]

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
                        }
                        if (element.salesWay == '竞价销售') {
                            if (element.biddingEndTimeStr) {
                                let time = [element.biddingStartTimeStr, element.biddingEndTimeStr]
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

                        }
                        if (element.salesWay == '定价销售') {
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
                            
                        }
                    });
                    this.data = data
                    this.total = res.data.total
                }
            });
        },
        onSort (e, dataName) {
            console.log(e)
        },
        onSearch (info) {
            console.log(info)
            this.keyword = info.name
            if (info.type.length) {
                this.productCode = info.type[info.type.length - 1]
            } else {
                this.productCode = ''
            }
            this.handleChangePage(1)
        },
        handleChangePage (page) {
            this.current = page
            this.handleInit()
        }
    }
}
</script>
<style lang="scss">

</style>
