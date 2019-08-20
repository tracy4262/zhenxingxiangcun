<template>
<div class="new-gate-product-content">
  <div class="vui-flex pt40 index-gate-knowledge-list">
    <img src="../../../img/product-icon.png" class="mr10 mt10" height="32px">
    <div class="vui-flex-item tabs">
      <Tabs :value="activeIndex" @on-click="tabClick">
        <TabPane v-for="(item, index) in tabList" :label="item.name" :name="`${index}`"  :key="index"></TabPane>
      </Tabs>
    </div>
    <span class="more" @click="handleMore">查看更多</span>
  </div>
  <swiper :options="swiperOption" ref="mySwiper" class="pb10">
    <div class="swiper-button-prev" slot="button-prev"></div>
    <swiper-slide :key="index" v-for="(item, index) in columnList">
      <!-- 产品 -->
      <div class="pd10" v-if="activeName == '产品'">
        <productList :item="item"></productList>
        <!-- <li class="li li-product" >
          <img v-if="item.image" :src="item.image" alt="" height="200px" width="100%">
          <img v-else src="../../../../static/img/goods-list-no-picture1.png" alt="" height="100px" width="100%">
          <div class="pd10">
            <p class="t-orange" style="font-size: 20px">
              {{item.discount}}/{{item.unit}}
            </p>
            <p class="name ell" :title="item.name">{{item.name}}</p>
            <p class="ell t-grey" :title="item.address"><Icon type="md-pin" />{{item.address}}</p>
            <Row class="t-grey" style="font-size:12px;" type="flex" align="middle">
              <Col span="18" class="ell" :title="item.seller" style="cursor: pointer;">
                <span v-if="item.seller">{{item.seller}}</span>
                <span v-else>&nbsp;</span>
              </Col>
              <Col span="6" class="tr">
              <Button icon="ios-text-outline" type="text" @click.stop="webimchat(item.userId, item.account, item.avatar)"></Button> 
              </Col>
            </Row>
          </div>
        </li> -->
      </div>
      <!-- 服务 -->
      <div class="pd10" v-if="activeName == '服务'">
        <li class="li li-service">
          <service-item :item="item"></service-item>
        </li>
        <!-- <li class="li li-service" v-if="activeName == '服务'">
          <img v-if="item.image_url && item.image_url.length" :src="item.image_url[0]" alt="" height="159px" width="100%">
          <img v-else src="../../../../static/img/goods-list-no-picture1.png" alt="" height="159px" width="100%">
          <div class="pd10">
            <p class="name ell" :title="item.service_name">{{item.service_name}}</p>
            <p class="ell" v-if="item.contact && item.contact.length" :title="item.contact[0].detailAddress"><Icon type="md-pin" />{{item.contact[0].detailAddress}}</p>
          </div>
        </li> -->
      </div>
    </swiper-slide>
    <div class="swiper-button-next" slot="button-next"></div>
  </swiper>
  </div>
</template>
<script>
import { swiper, swiperSlide } from 'vue-awesome-swiper'
import serviceItem from '../ruralGate/components/service-item'
import productList from '../ruralGate/components/product-item'
export default {
  props: {
    tabList: {
        type:Array
    },
    small:{
        type:Boolean,
        default:false
    },
    path: {
      type: String,
      default: '/farmHeadPortal'
    },
    dataList: {
      type: Array,
      default: () =>{
        return []
      }
    }
  },
  components: {
    swiper,
    swiperSlide,
    serviceItem,
    productList
  },
  data () {
    return {
      productTabList: [],
      swiperOption: {
          slidesPerView: 4,
          spaceBetween: 10,
          slidesPerGroup: 1,
          // centeredSlides: true,
          autoplay: {
              delay: 3000,
              disableOnInteraction: false
          },
          // loop: true,
          loopFillGroupWithBlank: true,
          pagination: {
              el: '.swiper-pagination',
              clickable: true
          },
          navigation: {
              nextEl: '.swiper-button-next',
              prevEl: '.swiper-button-prev'
          }
      },
      columnList: [],
      activeIndex: '0',
      active: 0,
      activeName: '',
      productList: [
        // {discount: '9.9', unit: '斤', seller: '张宏', name: '绿鲜知 云南甜玉米 水果玉米', address: '先锋村', image: 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1547676993,2174015573&fm=15&gp=0.jpg'},
        // {discount: '22', unit: '斤', seller: '王尚', name: '鲢鱼', address: '先锋村', image: 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2234846370,1385206490&fm=26&gp=0.jpg'},
        // {discount: '23', unit: '斤', seller: '王丽', name: '草鱼', address: '先锋村', image: 'https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=4114146544,4064470043&fm=200&gp=0.jpg'},
        // {discount: '20.5', unit: '斤', seller: '韩鑫', name: '鲈鱼', address: '先锋村', image: 'https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3326750850,4117637607&fm=26&gp=0.jpg'},
        // {discount: '15.5', unit: '斤', seller: '李硕', name: '莲藕', address: '先锋村', image: 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1700645086,3627266877&fm=200&gp=0.jpg'},
        // {discount: '18.5', unit: '斤', seller: '孙素', name: '水果藕', address: '先锋村', image: 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1010754269,1960785645&fm=26&gp=0.jpg'},
        // {discount: '50', unit: '斤', seller: '孙素', name: '花旗莲', address: '先锋村', image: 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1014809633,34860117&fm=26&gp=0.jpg'},
        // {discount: '5000-8000', unit: '棵', seller: '孙素', name: '桂花树', address: '先锋村', image: 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2329696859,4081472697&fm=200&gp=0.jpg'},
        // {discount: '1800-2000', unit: '棵', seller: '孙素', name: '广玉兰树', address: '先锋村', image: 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2208217277,3429394855&fm=26&gp=0.jpg'},
        // {discount: '2500-3000', unit: '棵', seller: '孙素', name: '紫薇树', address: '先锋村', image: 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=4168228648,1904385376&fm=26&gp=0.jpg'},
      ],
    }
  },
  created() {
    this.loginAccount = this.$route.query.uid
    // this.productTabList = this.tabList
  },
  warch: {
    tabList: {
      handler: function (newDate, oldDate) {
        this.productTabList = newDate
        this.tabClick(0)
      },
      deep: true
    }
  },
  methods: {
    handleMore () {
      console.log(this.tabList[this.active])
      let item = this.tabList[this.active]
      let url = `${this.path}/${item.type}?uid=${this.loginAccount}&id=${item.index}`
      this.$router.push(url)
    },
    tabClick (e) {
      this.activeIndex = `${e}`
      this.active = e
      this.columnList = []
      this.activeName = this.tabList[e].dataType
      this.getList(this.tabList[e].dataType)
    },
    // 查询服务的列表
    getList (dataType) {
      let url = ''
      if (dataType == '产品') {
        this.$api.post('/member-reversion/myRecommend/productList', {
            account: this.account,
            flag: '1', //0:查询所有服务, 1:查询已推荐服务
            productLocation: '',
            keyword: '',
            memberName: '',
            pageNum: 1,
            pageSize: 10
        }).then(response => {
            if (response.code === 200) {
                console.log('res', response)
                this.total = response.data.total
                this.columnList = response.data.list
                this.loading = false
            }
        })
      } else if (dataType == '服务') {
        url = '/member-reversion/myRecommend/serviceList'
        this.$api.post(url, {
            account: this.loginAccount,
            flag: '1', // 0:查询所有服务, 1:查询已推荐服务
            address: '',
            serviceName: '',
            memberName: '',
            pageNum: 1,
            pageSize: 20
        }).then(response => {
            if (response.code === 200) {
              this.columnList = response.data.list
            }
        }).catch(error => {
            this.$Message.error('服务器异常！')
        })
      }
    }
  },
}
</script>
<style lang="scss">
.new-gate-product-content{
  .product-title{
    font-size: 22px;
    color: #4A4A4A;
    vertical-align: middle;
  }
  .product-sub-title{
    font-size: 22px;
    color: #9B9B9B;
    vertical-align: middle;
  }
  .product-more{
    font-size: 16px;
    color: #4A4A4A;
    cursor: pointer;
  }
}
</style>
<style lang="scss" scoped>
.new-gate-product-content{
  .more{
    line-height: 50px;
    // border-bottom: 1px solid #dcdee2;
    height: 50px;
    cursor: pointer;
    font-size: 16px;
    color: #4A4A4A;
  }
}
.li-product{
  height: 360px;
}
.li-service{
  // height: 250px;
}
.li{
    // height: 360px;
    position: relative;
    background: #fff;
    margin: 25px 10px 0 10px;
    display: inline-block;
    width: 100%;
    // width: calc(100% / 5 - 20px);
    // list-style: none;
    box-shadow: 2px 5px 14px 0px rgba(0, 0, 0, 0.1);
    &:hover{
      box-shadow: 0px 0px 0px 2px rgba(0,197,135,1);
    }
    p{
      height: 34px;
      line-height: 34px;
    }
    .name{
      font-size: 16px;
    }
}
</style>


