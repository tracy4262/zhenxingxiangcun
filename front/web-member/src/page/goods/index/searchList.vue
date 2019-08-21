<template>
    <div class="layouts">
        <div class="wrapper8">
            
            <Row class="pt30 pb30">
                <Col span="10" offset="7">
                    <Input search enter-button="查询" v-model="keyword" @on-search="handleChangePage(1)" @on-enter="handleChangePage(1)" placeholder="请输入商品名称"/>
                </Col>
            </Row>
        </div>
        <Breadcrumb class="mt30 pl5">
            <BreadcrumbItem to="/goods/index">产品首页</BreadcrumbItem>
            <BreadcrumbItem @click.native="reload" :to="`/goods/search?name=${parentName}&code=${parentCode}`" v-if="parentName">{{parentName}}</BreadcrumbItem>
            <BreadcrumbItem>{{productName}}</BreadcrumbItem>
        </Breadcrumb>

        <div class="filter-bar">
            <template v-for="(item, index) in btnData">
            <Button
                :key="index"
                :type="item.checked ? 'primary' : 'text'"
                size="small"
                @click="handleClick(item, index)"
                >
                {{item.name}}
            </Button>
            </template>
        </div>
        <div class="perService" >
            <list :listData="data" :type="5" @on-login="handleLogin"></list>
            <Page v-if="data.length" class="mt30 tc pb50" :page-size="pageSize" :total="total" :current="current" @on-change="handleChangePage"></Page>
        </div>
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
            
            btnData: [
                {
                name: "综合排序",
                checked: true,
                dataName: ""
                },{
                name: "定价",
                checked: false,
                dataName: "定价销售"
                },
                {
                name: "团购",
                checked: false,
                dataName: "团购销售"
                },
                {
                name: "竞拍",
                checked: false,
                dataName: "竞价销售"
                },
                {
                name: "预售",
                checked: false,
                dataName: ""
                },
                {
                name: "面议",
                checked: false,
                dataName: "面议"
                },
                {
                name: "可追溯",
                checked: false,
                dataName: ""
                }
            ],
            data: [],
            total: 0,
            pageSize: 10,
            current: 1,
            keyword: '',
            retrospectType: '',
            salesType: '',
            productStatus: '',
        }
    },
    created () {
        this.init()
    },
    watch: {
        $route(){
        this.reload()
        },
    },
    methods: {
        init () {
            this.type = this.$route.query.type ? this.$route.query.type : '';
            this.productCode = this.$route.query.code ? this.$route.query.code : '';
            this.productName = this.$route.query.name ? this.$route.query.name : '';
            this.parentName = this.$route.query.parentName ? this.$route.query.parentName : '';
            this.parentCode = this.$route.query.parentCode ? this.$route.query.parentCode : '';
            this.keyword = this.$route.query.keyword ? this.$route.query.keyword : '';
            this.handleInit(1)
        },
        reload () {
            console.log('dianjile')
            this.handleClick(this.btnData[0], 0)
            this.current = 1
            this.init()
        },
        handleClick (item, index) {
            console.log(item)
            this.btnData.map((item, i) => {
                if (index === i) {
                    item.checked = true
                    this.salesType = item.dataName
                    if (item.name === '预售') {
                        this.productStatus = '预定产品'
                    } else if (item.name === '综合排序') {
                        this.productStatus = ''
                    }else{
                        this.productStatus = '现货产品'
                    }
                    if (item.name === '可追溯') {
                        this.retrospectType = '是'
                        this.productStatus = ''
                    } else {
                        this.retrospectType = ''
                    }
                } else {
                    item.checked = false
                }
            })
            this.handleChangePage(1)
        },
        // 登录
        handleLogin() {
            this.$parent.$refs["top"].loginuser();
        },
        handleInit (e) {
            // this.list.pageSize = this.pageSize
            // this.list.pageNum = this.current
            // // this.list.service_name = this.$parent.info.service_name
            // // this.list.address = this.$parent.info.address
            // // this.list.species = this.$parent.info.species
            // // this.list.industry = this.$parent.info.industry
            // this.$api.post('/member/fishing/findProductServiceList', this.list).then(response => {
            //     if (response.code === 200) {
            //         this.serverData = response.data.dataList
            //         this.total = response.data.total
            //     }
            // })
            // 定价
            this.$api.post("/shop/pushShopCommodity/findProduct", {
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
            console.log('dataName', dataName)
            this.current = 1
            // this.list = {
            //     account: '',
            //     type: '1', // 0垂钓 1采摘 2景区 3餐饮 4住宿
            //     address: this.list.address,
            //     species: this.list.species,
            //     speciesId: this.list.speciesId,
            //     industry: this.list.industry,
            //     industryId: this.list.industryId,
            //     service_name: this.list.service_name,
            // }
            // if (e.name == '价格') {
            //     this.list.timePriceFlag = e.asc
            // } else if (e.name == '好评率') {
            //     this.list.gradeFlag = e.asc
            // } else if (e.name == '综合排序') {
            //     this.list.default = '1'
            // }
            // this.handleInit()
        },
        onSearch (info) {
            console.log(info)
            // this.list = Object.assign(this.list, info)
            // this.keyword = info.name
            // this.handleChangePage(1)
        },
        handleChangePage (page) {
            this.current = page
            this.handleInit()
        }
    }
}
</script>
<style lang="scss" scoped>
.filter-bar{
  margin-top: 20px;
  padding: 10px;
  background: #F9F9F9;
  .ivu-btn{
    margin-right: 10px;
    min-width: 50px;
    padding: 2px 5px;
  }
  .ivu-icon{
    display: block;
    line-height: .5;
    padding-left: 3px;
  }
}
</style>

