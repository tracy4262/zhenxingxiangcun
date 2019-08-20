<template>
    <div class="wrapper">
        <top :address="false" goShop />
        <mall-search :datas="search" />
        <!-- 导航 -->
        <nav class="mall-nav">
            <div class="layouts">
                <a href="/pro/productList" class="link">首页</a>
                <a href="/mall/hotGroupBuy" class="link">热门团购</a>
                <a href="/mall/fixPriceProduct" class="link">定价好货</a>
                <a href="/mall/ypAuction" class="link on">优品竞拍</a>
                <a href="/mall/newPresell" class="link">新品预售</a>
                <a href="/mall/stock" class="link">抢现货</a>
                <a href="/mall/ascend" class="link">可追溯商品</a>
                <a href="javascript:;" class="link">特卖商品</a>
                <a href="javascript:;" class="link">超实惠</a>
            </div>
        </nav>
        <!-- banner -->
        <section class="layouts">
            <Row class="auction-bannner">
                <Col span="18">
                    <Carousel
                        v-model="carousel.value"
                        :autoplay="carousel.autoplay"
                        :arrow="carousel.arrow">
                        <Carousel-item v-for="(item,index) in carousel.list" :key="index">
                            <img :src="item.src">
                            <div class="tit">{{item.title}}
                                <clocker :time="item.time" slot="value">
                                    <span class="item">%D</span>天
                                    <span class="item">%H</span>小时
                                    <span class="item">%M</span>分
                                    <span class="item">%S</span>秒
                                </clocker>
                            </div>
                        </Carousel-item>
                    </Carousel>
                </Col>
                <Col span="6">
                    <paper :level="2">
                        <contentBlock :padding="['20px']">
                            <p class="h5 tc">张家港村0543地块10公顷</p>
                            <contentBlock :padding="['5px']" border class="clear mt5">
                                威龙实业有限公司 <span class="fr">广东湛江</span>
                            </contentBlock>
                            <contentBlock :padding="['4px','5px']" class="mt10 mb5">
                                拍卖湛江市威龙实业有限公司位于湛江市赤坎区大埠工业区即市威立制钉厂北侧三块土地使用权Ώ、面积4000平方米，证号为湛府国用（1997）字第特243号；2、面积3460.6平方米，证号为湛府国用（1997）字第特244号;3、面积4000平方米，证号为湛府国用（1997）字第特078号]
                            </contentBlock>
                            <Row>
                                <Col span="6" class="tc">
                                    <i class="icon-gift t-green h2"></i>
                                    <p class="t-grey">拍品数量</p>
                                    <p class="t-orange h4">1</p>
                                </Col>
                                <Col span="6" class="tc">
                                    <i class="icon-holl-team t-green h2"></i>
                                    <p class="t-grey">围观人数</p>
                                    <p class="t-orange h4">56</p>
                                </Col>
                                <Col span="6" class="tc">
                                    <i class="icon-holl-hammer t-green h2"></i>
                                    <p class="t-grey">出价次数</p>
                                    <p class="t-orange h4">70</p>
                                </Col>
                                <Col span="6" class="tc">
                                    <i class="icon-holl-gold t-green h2"></i>
                                    <p class="t-grey">当前价格</p>
                                    <p class="t-orange h4">99</p>
                                </Col>
                            </Row>
                        </contentBlock>
                    </paper>
                </Col>
            </Row>
            <!-- 火热竞拍 -->
            <div class="goods-list hot-auction">
                <div class="hd mb20">
                    <p class="tit">火热竞拍</p>
                </div>
                <Row>
                    <Col span="6" v-for="(item,index) in hotAuctionList" :key="index" class="mb20">
                        <a href="/mall/ypAuctionDetail" class="goods ma-goodb">
                            <div class="hd">
                                距离结束：
                                <clocker :time="item.time" slot="value">
                                    <span class="item">%D</span>天
                                    <span class="item">%H</span>小时
                                    <span class="item">%M</span>分
                                    <span class="item">%S</span>秒
                                </clocker>
                            </div>
                            <div class="bd">
                                <img :src="item.src">
                            </div>
                            <Row class="pd10">
                                <Col span="13">
                                    <span class="t-grey">当前价格：</span>
                                    <span class="h4 t-orange">￥{{item.price}}</span>
                                </Col>
                                <Col span="11">
                                    <span class="t-grey">出价：</span>
                                    <span class="h4 t-green">{{item.rate}}</span>
                                    <span class="t-grey">次</span>
                                </Col>
                                <Col span="24" class="ell h6">
                                    {{item.title}}
                                </Col>
                                <Row>
                                    <Col span="24" class="ma-font t-grey">{{item.shop}}</Col>
                                </Row>
                                <Row>
                                    <Col span="24" class="ma-font t-grey">{{item.address}}</Col>
                                </Row>
                            </Row>
                            <div class="fd">
                                <Button type="primary" long><i class="icon-holl-hammer h4"></i> 我要出价</Button>
                            </div>
                        </a>
                    </Col>
                </Row>
            </div>

            <!-- 拍品 -->
            <div class="goods-list hot-auction">
                <div class="hd mb20">
                    <p class="tit">拍品</p>
                    <div class="fliter pd10">
                        <Row>
                            <Col span="8">
                                <template v-for="(item,index) in filterBtn">
                                    <Button :type="item.type" size="small" class="mr5" @click="handleSort(item,index)">
                                        {{item.text}}
                                        <template v-if="item.icon">
                                            <i :class="item.icon"></i>
                                        </template>
                                    </Button>
                                </template>
                            </Col>
                            <Col span="16" class="tr">
                                共 <span class="t-green"> 99999 </span>件商品  
                                <span class="ml10 mr10">{{page.current}}/100</span>
                                <Button-group>
                                    <Button type="default" size="small" @click="handleBack">
                                        <Icon type="chevron-left"></Icon>
                                        前进
                                    </Button>
                                    <Button type="default" size="small" @click="handleForward">
                                        后退
                                        <Icon type="chevron-right"></Icon>
                                    </Button>
                                </Button-group>
                            </Col>
                        </Row>
                    </div>
                </div>
                <Row>
                    <Col span="6" v-for="(item,index) in auctionList" :key="index" class="mb20">
                        <a href="/mall/ypAuctionDetail" class="goods ma-goodb">
                            <div class="hd">
                                距离结束：
                                <clocker :time="item.last_time" slot="value">
                                    <span class="item">%D</span>天
                                    <span class="item">%H</span>小时
                                    <span class="item">%M</span>分
                                    <span class="item">%S</span>秒
                                </clocker>
                            </div>
                            <div class="bd">
                                <img :src="item.image">
                            </div>
                            <Row class="pd10">
                                <Col span="16">
                                    <span class="t-grey">当前价格：</span>
                                    <span class="h4 t-orange">￥{{item.price}}</span>
                                </Col>
                                <Col span="8">
                                    <span class="t-grey">出价：</span>
                                    <span class="h4 t-green">{{item.rate}}</span>
                                    <span class="t-grey">次</span>
                                </Col>
                                <Col span="24" class="ell h6">
                                    {{item.name}}
                                </Col>
                                <Row>
                                    <Col span="24" class="ma-font t-grey">{{item.gateway}}</Col>
                                </Row>
                                <Row>
                                    <Col span="24" class="ma-font t-grey">{{item.addr}}</Col>
                                </Row>
                            </Row>
                            <div class="fd">
                                <Button type="primary" long><i class="icon-holl-hammer h4"></i> 我要出价</Button>
                            </div>
                        </a>
                    </Col>
                </Row>
            </div>
            <div class="tc">                
                <Page :total="page.total" :current="page.current" :page-size="page.size" @on-change="getYpAuctionProduct" show-elevator></Page>
            </div>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
        </section>
    </div>
</template>
<script>
import top from '../../top'
import clocker from '~components/clocker'
import contentBlock from '~components/contentBlock'
import mallSearch from '~components/mallSearch'
import paper from '~components/paper'
import api from '~api'
export default {
    components:{
        top,
        clocker,
        contentBlock,
        mallSearch,
        paper
    },
    data () {
        return {
            search:{
                value:'',
                loading:false,
                defOpt:[],
                hotTag:[{
                    text:'苹果',
                    url:'javascript:;'
                },{
                    text:'香蕉',
                    url:'javascript:;'
                },{
                    text:'梨子',
                    url:'javascript:;'
                },{
                    text:'西瓜',
                    url:'javascript:;'
                },{
                    text:'葡萄',
                    url:'javascript:;'
                },{
                    text:'哈密瓜',
                    url:'javascript:;'
                }],
                filterOpt:[
                    {label:'蔬菜', value:10},
                    {label:'苹果', value:20},
                    {label:'香蕉', value:30},
                    {label:'梨子', value:40},
                    {label:'西瓜', value:50}
                ]
            },
            carousel:{
                value: 0,
                autoplay: true,
                arrow:'never',
                list:[
                    {
                        src:'../src/img/pai_banner.png',
                        title:'距离结束时间仅剩：',
                        time:'2018-08-01'
                    },
                    {
                        src:'../src/img/pai_banner.png',
                        title:'距离结束时间仅剩：',
                        time:'2016-08-01'
                    }
                ]
            },
            hotAuctionList:[
                {
                    time:'2017-09-10',
                    src:'../static/datas/img/goods-corn.png',
                    price: 10,
                    rate:12,
                    title:'鄂甜玉四号',
                    shop:'普利家农资专营店',
                    address:'湖北襄阳'
                },
                {
                    time:'2017-10-01',
                    src:'../src/img/news-img.png',
                    price: 5,
                    rate:6,
                    title:'鄂甜玉四号',
                    shop:'普利家农资专营店',
                    address:'湖北襄阳'
                },{
                    time:'2017-11-01',
                    src:'../src/img/baicai.png',
                    price: 20,
                    rate:9,
                    title:'鄂甜玉四号',
                    shop:'普利家农资专营店',
                    address:'湖北襄阳'
                },{
                    time:'2017-12-01',
                    src:'../src/img/baicai.png',
                    price: 25,
                    rate:10,
                    title:'鄂甜玉四号',
                    shop:'普利家农资专营店',
                    address:'湖北襄阳'
                }
            ],
            auctionList:[],
            filterBtn:[
                {
                    type:'primary',
                    text:'默认',
                    icon:''
                },{
                    type:'ghost',
                    text:'价格',
                    icon:'icon-desc-default'
                },{
                    type:'ghost',
                    text:'人数',
                    icon:'icon-desc-default'
                }
            ],
            page:{
                total:50,
                current:1,
                size: 10
            },
            pagedata: 0
        }
    },
    created(){
        this.getYpAuctionProduct(1)
    },
    methods: {
        // 排序处理
        handleSort(item,index){
            // this.filterBtn.forEach(item => item.type='ghost')
            // item.type = 'primary'
            // if(index === 1){
            //     this.auctionList = this.auctionList.sort((a, b) => a.price < b.price ? -1 : 1)
            // }else if(index === 2){
            //     this.auctionList = this.auctionList.sort((a, b) => a.rate < b.rate ? 1 : -1)
            // }
        },
        // 后退
        handleForward(){
            if(this.page.current >= this.pagedata){
                return false
            }
            this.page.current++
            this.getYpAuctionProduct(this.page.current)
        },
        // 前进
        handleBack(){
            if(this.page.current > 1){
                this.page.current--
                this.getYpAuctionProduct(this.page.current)
            } 
        },
        getYpAuctionProduct(cpage) {
            this.page.current = cpage
            api.get('/member/shop/getFullProduct/2?page='+cpage+'&pageSize='+this.page.size)
                .then(response => {
                    this.auctionList = response.data.list
                    this.page.total = response.data.page.totalCount
                    this.page.current = response.data.page.pageNo
                    this.page.size = response.data.page.pageSize
                    this.page.current = cpage
                    this.pagedata = Math.ceil(this.page.total/this.page.size)
                })
        }
    }
}
</script>
<style lang="scss">

.layouts .hot-auction .ma-goodb{height: 428px;}
</style>