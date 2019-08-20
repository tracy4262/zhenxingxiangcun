<template>
    <div>
        <div class="wrapper">
            <serviceSeach @on-search="onSearch" :keyWord="keyWord"></serviceSeach>
        </div>
        <div class="tc pb20 pt20">
            <Button type="text" size="large" :class="[tabActive == 'fishing' ? 't-green' : '']" @click.native="handleTabsClick('fishing')">垂钓</Button>
            <Button type="text" size="large" :class="[tabActive == 'picking' ? 't-green' : '']" @click.native="handleTabsClick('picking')">采摘</Button>
            <Button type="text" size="large" :class="[tabActive == 'stay' ? 't-green' : '']" @click.native="handleTabsClick('stay')">民宿</Button>
            <Button type="text" size="large" :class="[tabActive == 'restaurant' ? 't-green' : '']" @click.native="handleTabsClick('restaurant')">农家乐</Button>
            <Button type="text" size="large" :class="[tabActive == 'scenicSpot' ? 't-green' : '']" @click.native="handleTabsClick('scenicSpot')">景区</Button>
            <Button type="text" size="large" :class="[tabActive == 'consultation' ? 't-green' : '']" @click.native="handleTabsClick('consultation')">咨询服务</Button>
        </div>
        <div  class="slider">
            <div class="layouts">
            <min-nav type="1"></min-nav>
            </div>
            <Carousel arrow="never" autoplay v-model="silder" loop>
            <CarouselItem v-for="(item, index) in imgList" :key="index">
                <img :src="item.picture_url" alt="">
            </CarouselItem>
            </Carousel>
        </div>
        <div class="layouts">
            <!-- <div class="tc">
                <div class="tc pt30">
                <span style="width:14px;height:3px;background:#00c587;display:inline-block;"></span>
                </div>
                <span style="font-size:32px;color:#4a4a4a;">推荐服务</span>
            </div> -->
            <div class="tc">
                <span class="pl20 pr20 mt50 divider mb30">推荐服务</span>
            </div>
            <div class="perService" v-if="serverData.length > 0">
                <Row>
                    <div class="mt20 mr20" style="width: 18%;float:left;" v-for="(item,index) in serverData" :key="index">
                        <consultation-server-item v-if="item.type == 5" :item="item"></consultation-server-item>
                        <server-item v-else :item="item" isIndex></server-item>
                    </div>
                </Row>
                <Page class="mt30 tc pb50" :page-size="pageSize" :total="total" :current="current" @on-change="handleChangePage"></Page>
            </div>
            <div class="ma-polic-img tc" v-if="serverData.length === 0">
                <img src="../../img/ma-img-002.png"> 
                <p style="margin-top: 10px;">暂无数据</p>
            </div>
        </div>
    </div>
</template>
<script>
import serverItem from './components/server-item'
import consultationServerItem from './components/consultation-server-item'
import minNav from '../goods/components/min-nav'
import serviceSeach from './components/serviceSeach'
export default {
    name: 'person-service-all',
    components: {
        serverItem,
        minNav,
        serviceSeach,
        consultationServerItem
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
            keyWord: ''
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
        handleTabsClick (name) {
            console.log(name)
            this.tabActive = name
            this.$router.push('/51Index/serviceList/' + name)
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
            this.list.pageSize = this.pageSize
            this.list.pageNum = this.current
            this.$api.post('/member/fishing/findProductServiceList', this.list).then(response => {
                if (response.code === 200) {
                    this.serverData = response.data.dataList
                    this.total = response.data.total
                    console.log('serviceData', this.serverData)
                }
            })
        },
        onSearch (info) {
            this.list = Object.assign(this.list, info)
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