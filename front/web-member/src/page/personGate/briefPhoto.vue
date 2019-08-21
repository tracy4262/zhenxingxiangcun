<template>
    <div class="pb50 briefHonor">
        <Row :gutter="16">
            <Col span="12" offset="6">
                <div v-if="items.length > 0">
                    <Row class="card-content" type="flex" align="middle">
                        <Col span="24">
                            <!-- <swiper :options="swiperOption"  ref="mySwiper" style="margin-top: 30px; margin-bottom: 30px;">
                                <div class="swiper-button-prev" slot="button-prev"></div>
                                <swiper-slide v-for="(item,index) in items" :key="index">
                                    <img :src="item" style="width: 193px; height: 135px;">
                                </swiper-slide>
                                <div class="swiper-button-next" slot="button-next"></div>
                            </swiper> -->
                        </Col>
                    </Row>
                </div>
                <div class="ma-polic-img" v-if="items.length === 0">
                    <img src="../../img/ma-img-002.png"> 
                    <p style="margin-top: 10px;">暂无数据</p>
                </div>  
            </Col>
        </Row>
    </div>
</template>
<script>
import { swiper, swiperSlide } from 'vue-awesome-swiper'
export default {
	components:{
        swiper,
        swiperSlide
    },
    data () {
        return {
        	supplyData: [],
        	// swiperOption: {
         //        slidesPerView: 3,
         //        spaceBetween: 0,
         //        slidesPerGroup: 1,
         //        // centeredSlides: true,
         //        autoplay: {
         //            delay: 3000,
         //            disableOnInteraction: false
         //        },
         //        // loop: true,
         //        loopFillGroupWithBlank: true,
         //        pagination: {
         //            el: '.swiper-pagination',
         //            clickable: true
         //        },
         //        navigation: {
         //            nextEl: '.swiper-button-next',
         //            prevEl: '.swiper-button-prev'
         //        }
         //    },
            items:[]
        }
    },
    created(){
    },
    computed: {
        // swiper() {  
        //   return this.$refs.mySwiper.swiper
        // }
    },
    methods: {
    },
    mounted() {
    	// this.swiper.slideTo(0, 0, false)
    }
}
</script>
<style lang="scss">
@import '../../../node_modules/swiper/dist/css/swiper';
.ma-polic-img{margin-top: 60px;}
</style>
