<!--
    作者：chenqim
    时间：2019-4-03
    描述：门户首页生产基地组件
-->
<template>
    <div class="new-gate-product-content">
        <Row class="pt20" type="flex" align="middle">
            <Col span="12" class="pl10">
                <img src="../../../img/production-base-icon.png" alt="" class="mr10" width="28px" height="26px"> 
                <span class="product-title mr30">{{ productionBaseTitle }}</span>
            </Col>
        </Row>
        <Row class="mt20">
            <Col span="24">
                <swiper :options="swiperOption" ref="mySwiper" class="pb10">
                    <div class="swiper-button-prev" slot="button-prev"></div>
                    <swiper-slide :key="index" v-for="(item, index) in dataList" @click.native="detail(item)">
                        <Row style="background-color: #F7F7F7; height: 300px;" type="flex" justify="center" align="middle">
                            <Col span="10">
                                <img v-if="item.imageUrl" :src="item.imageUrl" style="width: 100%; height: 260px;" />
                                <img v-else src="../../../../static/img/goods-list-no-picture1.png" style="width: 100%; height: 260px;" />
                            </Col>
                            <Col span="12" class="ml20">
                                <Row>
                                    <p style="font-size: 22px; color: #8bd839; line-height: 30px;" class="mt20 ell" :title="item.productionBaseName">{{ item.productionBaseName }}</p>
                                </Row>
                                <Row style="min-height: 170px;">
                                    <p style="font-size: 16px; color: #4A4A4A; line-height: 30px;" class="mt20 ell-2" :title="item.introduction">{{ item.introduction === '' ? '暂无简介' : item.introduction }}</p>
                                </Row>
                                <Row class="mt20" style="font-size: 16px; color: #4A4A4A; line-height: 22px;">
                                    <Col span="8">联系人：{{ item.name }}</Col>
                                    <Col span="16">联系电话：027-88888888</Col>
                                </Row>
                            </Col>
                        </Row>
                    </swiper-slide>
                    <div class="swiper-button-next" slot="button-next"></div>
                </swiper>
            </Col>
        </Row>
    <baseDetail ref="detail"></baseDetail>
    </div>
</template>
<script>
import { swiper, swiperSlide } from 'vue-awesome-swiper'
import baseDetail from '../../goods/detail/components/productionBaseDetail'
export default {
    name: 'indexProductionBase',
    components: {
        swiper,
        swiperSlide,
        baseDetail
    },
    props: {
        dataList: {
            type: Array,
            default: () => {
                return []
            }
        },
        productionBaseTitle: {
            type: String,
            default: '生产基地'
        }
    },
    data () {
        return {
            swiperOption: {
                slidesPerView: 1,
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
        }
    },
    methods: {
        detail (item) {
            this.$refs['detail'].init(item.account, item.id)
        }
    }
}
</script>
<style lang="scss" scoped>
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
.li{
    height: 360px;
    position: relative;
    background: #fff;
    margin: 25px 10px 0 10px;
    display: inline-block;
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
