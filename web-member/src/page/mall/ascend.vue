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
                <a href="/mall/ypAuction" class="link">优品竞拍</a>
                <a href="/mall/newPresell" class="link">新品预售</a>
                <a href="/mall/stock" class="link">抢现货</a>
                <a href="/mall/ascend" class="link on">可追溯商品</a>
                <a href="javascript:;" class="link">特卖商品</a>
                <a href="javascript:;" class="link">超实惠</a>
                <ul class="min-nav">
                    <li class="item" v-for="item in minNav" :key="item.id">
                        <!-- <i :class="item.icon"></i> -->
                        {{item.name}}
                        <!-- <i class="icon-arrow-right"></i> -->
                        <div class="sub">
                            <Row>
                                <Col span="18">
                                    <Row v-for="sub in item.sub" class="mt10 mb10 pr10" :key="sub.id">
                                        <Col span="3">
                                            <a :href="$url.shop +sub.url" class="sub-class">{{sub.text}} <i class="icon-arrow-right"></i></a>
                                        </Col>
                                        <Col span="21" class="pb5 mb5 tl" style="border-bottom: 1px dotted #ddd;">
                                            <a v-for="link in sub.sublink" :href="$url.shop +link.url" class="sub-link">{{link.text}}</a>
                                        </Col>
                                    </Row>
                                </Col>
                                <Col span="6">
                                    <Col span="12" v-for="(logo,index) in item.shopLogo" :key="index">
                                        <a :href="logo.url" class="shop-logo"><img :src="logo.src"></a>
                                    </Col>
                                </Col>
                            </Row>
                        </div>

                    </li>
                </ul>
            </div>
        </nav>
        <!-- banner -->
        <div class="hot-group-banner">
            <Carousel
                class="auction-bannner"
                v-model="carousel.value"
                :autoplay="carousel.autoplay"
                :arrow="carousel.arrow">
                <Carousel-item v-for="(item,index) in carousel.list" :key="index">
                    <img :src="item.src">
                </Carousel-item>
            </Carousel>
        </div>
        <section class="layouts">
            <div class="goods-list fix-price-product">
                <div class="hd mb20">
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
                <Row :gutter="10">
                    <Col span="4" v-for="(item,index) in stockList" :key="index" class="mb20">
                        <a href="/mall/ascendDetail" class="goods">
                            <div class="bd">
                                <img :src="item.image">
                            </div>
                            <Row class="pd5 info">
	                            <Col span="24" class="h4 t-orange">￥{{item.price}}</Col>
	                            <Col span="24" class="ell h6 mt5 mb10">{{item.name}}</Col>
                                <Col span="12" class="t-green">已售{{item.difference}}件</Col>
                                <Col span="12" class="t-green tr">好评率{{item.acclaim}}%</Col>
                                
                                <Row>
                                    <Col span="24" class="ma-font t-grey">{{item.gateway}}</Col>
                                </Row>
                                <Row>
                                    <Col span="24" class="ma-font t-grey">{{item.addr}}</Col>
                                </Row>
                            </Row>
                        </a>
                    </Col>
                </Row>
            </div>
            <div class="tc">
                <Page :total="page.total" :current="page.current" :page-size="page.size" @on-change="getAscendProduct" show-elevator></Page>
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
import axios from 'axios'
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
            minNav: [],
            carousel:{
                value: 0,
                autoplay: true,
                arrow:'never',
                list:[
                    {
                        src:'../static/datas/img/banner.jpg',
                        url:'javascript:;'
                    },{
                        src:'../static/datas/img/banner.jpg',
                        url:'javascript:;'
                    }
                ]
            },
            stockList:[],
            filterBtn:[
                {
                    type:'primary',
                    text:'默认',
                    icon:''
                },{
                    type:'ghost',
                    text:'好评率',
                    icon:'icon-desc-default'
                },{
                    type:'ghost',
                    text:'成交数',
                    icon:'icon-desc-default'
                }
            ],
            page:{
                total:50,
                current:1,
                size:6
            },
            pagedata: 0
        }
    },
     created(){
        this.getAscendProduct(1)
    },
    methods: {
        // 排序处理
        handleSort(item,index){
            // this.filterBtn.forEach(item => item.type='ghost')
            // item.type = 'primary'
            // if(index === 1){
            //     this.stockList = this.stockList.sort((a, b) => a.difference < b.difference ? -1 : 1)
            // }else if(index === 2){
            //     this.stockList = this.stockList.sort((a, b) => a.acclaim < b.acclaim ? 1 : -1)
            // }
        },
        // 后退
        handleForward(){
            if(this.page.current >= this.pagedata){
                return false
            }
            this.page.current++
            this.getAscendProduct(this.page.current);
        },
        // 前进
        handleBack(){
            if(this.page.current > 1){
                this.page.current--
                this.getAscendProduct(this.page.current)
            } 
        },
        getAscendProduct(cpage) {
            this.page.current = cpage;
            api.get('/member/shop/getAscendProduct?page='+cpage+'&pageSize='+this.page.size)
                .then(response => {
                    this.stockList = response.data.list
                    this.page.total = response.data.page.totalCount
                    this.page.current = response.data.page.pageNo
                    this.page.size = response.data.page.pageSize
                    this.page.current = cpage

                    this.pagedata = Math.ceil(this.page.total/this.page.size)
                    console.log(this.page.total)
                    console.log(this.page.size)
                    console.log(this.pagedata)
                })
        }
    }
}
</script>
