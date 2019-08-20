<template>
    <div class="wrapper layout">
        <div ref="top">
            <top :address="false" />
        </div>
        <div class="main">
            <div class="container">
                <Row :gutter="20">
                    <Col span="24">
                        <app-banner
                            src="../../../../static/img/app-banner-product-base.png"
                            title="生产基地管理">
                        </app-banner>
                        <div style="margin-left: 10px;position: relative">
                            <!-- 面包屑导航栏 -->
                            <Breadcrumb>
                                <BreadcrumbItem to="/member/productionBaseList">生产基地</BreadcrumbItem>
                                <BreadcrumbItem>{{baseName}}</BreadcrumbItem>
                            </Breadcrumb>
                            <Button type="primary" style="position: absolute;right: 10px;top: 1px;" @click="preStep">返回</Button>
                            <!-- 地理位置信息 -->
                            <Row class="card-title" type="flex" align="middle">
                                <Col span="12"><span class="ml10">地理位置</span></Col>
                                <Col span="2" offset="10">
                                    <Button type="text" @click="updateProductionBase">修改</Button>
                                </Col>
                            </Row>
                            <Row class="card-content">
                                <Col span="11" class="content-pd">
                                    <p>基地简介：</p>
                                    <p>{{baseSynopsis}}</p>
                                </Col>
                                <Col span="5" class="content-pd">
                                    <Row>联系人：{{contactName}}</Row>
                                    <Row>联系电话：{{contactTel}}</Row>
                                    <Row>实况直播：</Row>
                                    <Row>
                                        <div v-for="(item,index) in camreaList" :key="index" class="camera-list">
                                            <Button v-if="item.cameraStatus === '工作'" type="primary">{{item.equipmentName}}</Button>
                                            <Button v-else type="default">{{item.equipmentName}}</Button>
                                        </div>
                                    </Row>
                                </Col>
                                <Col span="7" class="content-pd">
                                    <Tooltip always placement="top">
                                        <template slot="content">
                                            <p class="ell">地址：{{geographicalPosition}}</p>
                                            <p>坐标：{{coordinate}}</p>
                                        </template>
                                        <img :src="`//api.map.baidu.com/staticimage?width=250&height=140&center=${coordinate.split(',')[0]},${coordinate.split(',')[1]}&zoom=11&markers=${coordinate.split(',')[0]},${coordinate.split(',')[1]}`" alt="">
                                    </Tooltip>
                                </Col>
                            </Row>
                            <!-- 基地相册信息 -->
                            <Row class="card-title" type="flex" align="middle">
                                <Col span="24"><span class="ml10">基地相册</span></Col>
                            </Row>
                            <Row class="card-content" type="flex" align="middle">
                                <Col span="24">
                                    <swiper :options="swiperOption"  ref="mySwiper" style="margin-top: 30px; margin-bottom: 30px;">
                                        <div class="swiper-button-prev" slot="button-prev"></div>
                                        <swiper-slide v-for="(item,index) in items" :key="index">
                                            <img :src="item" style="width: 180px; height: 135px;">
                                        </swiper-slide>
                                        <div class="swiper-button-next" slot="button-next"></div>
                                    </swiper>
                                </Col>
                            </Row>
                            <!-- 详细信息 -->
                            <Row class="card-title" type="flex" align="middle">
                                <Col span="12"><span class="ml10">详细信息</span></Col>
                                <Col span="3" offset="9">
                                    <Button type="text" @click="getModelData">查看完整描述</Button>
                                </Col>
                            </Row>
                            <Row class="card-content">
                                <detail-tabs />
                            </Row>
                        </div>
                    </Col>
                </Row>
            </div>
        </div>
        <div ref="foot">
            <foot></foot>
        </div>

        <Modal
        v-model="completeModel"
        title="完整描述详情"
        cancel-text="返回"
        >
            <h5 class="ma-viewAll">{{listTatle}}</h5>
            <div class="ma-text-content">
                <template v-for="item in textList">
                    <p :data-clipboard-text="item">{{item}}</p>
                </template>
            </div>
            <div slot="footer">
                <Button type="primary" size="large" class="ma_onAdd" :data-clipboard-text="copyData" @click="copyed">复制</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
    import top from '../../../top'
    import foot from '../../../foot'
    import api from '~api'
    import detailTabs from './productionDetails/details'
    import appBanner from '~components/app-banner'
    import { swiper, swiperSlide } from 'vue-awesome-swiper'
    import Clipboard from 'clipboard'
    const clipboard = null
    export default {
        components:{
            top,
            foot,
            detailTabs,
            swiper,
            swiperSlide,
            Clipboard,
            appBanner
        },
        data() {
            return {
                completeModel: false,
                camreaList: [],
                baseName: '',
                baseSynopsis: '',
                contactName: '',
                coordinate: '',
                geographicalPosition: '',
                contactTel: '',
                swiperOption: {
                    slidesPerView: 5,
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
                items: [],
                textList: [],
                listTatle: '',
                copyData: '',
                height:''
            }
        },
        //定义这个sweiper对象  
        computed: {  
            swiper() {  
              return this.$refs.mySwiper.swiper
            }  
        },
        mounted () {  
            this.handleGetHeight()
            //这边就可以使用swiper这个对象去使用swiper官网中的那些方法
            this.swiper.slideTo(0, 0, false)

            let _that = this
            this.clipboard = new Clipboard('.ma_onAdd')
            this.clipboard.on('success', function(e) {
                _that.$Message.success({content: '文字已复制到剪切板', duration: 1.5})
                e.clearSelection()
            })
            this.clipboard.on('error', function(e) {
                _that.$Message.success({content: '该浏览器不支持复制到剪切板功能', duration: 1.5})
            })
        },
        destroyed(){
            this.clipboard.destroy()
        },
        created () {
            let _that = this
            this.$api.post('/member/product-base/select-detail', {
                productId: this.$route.query.id
            }).then(response => {
                console.log('response', response)
                if (response.code === 200) {
                    _that.camreaList = response.data.camereMap
                    _that.baseName = response.data.baseName
                    _that.baseSynopsis = response.data.baseSynopsis
                    _that.contactName = response.data.contactName
                    _that.contactTel = response.data.contactTel
                    _that.coordinate = response.data.coordinate
                    _that.geographicalPosition = response.data.geographicalPosition
                    _that.contactTel = response.data.contactTel
                    response.data.photoMap.forEach(element => {
                        _that.items.push(element.photoUrl)
                    })
                    console.log('items', _that.items)
                }
            }).catch(error => {
                console.log('error', error)
            })
        },
        methods: {            
            // 获取页面高度
            handleGetHeight () {
                let clientHeight = document.documentElement.clientHeight
                let topHeight = this.$refs.top.offsetHeight
                let footHeight = this.$refs.foot.offsetHeight
                this.height = `${clientHeight-topHeight-footHeight}px`
            },
            //返回生产基地
            preStep(){
                var current = this.$route.query.current
                if(current){                    
                    this.$parent.$parent.$router.push('/member/productionBaseList?id='+current)
                }else{
                    this.$parent.$parent.$router.push('/member/productionBaseList?id='+1)
                }
            },
            getModelData(){
                let that = this 
                this.completeModel = true
                api.post('/member/product-base/select-full-describe', {
                    productId: this.$route.query.id
                })
                .then(response => {
                    let resault = []
                    if(response.data.productionBaseMap !== undefined){
                        that.listTatle = response.data.productionBaseMap.baseName
                    }

                    if(response.data.productPositionMap !== undefined){
                        resault.push(response.data.productPositionMap.describe)
                    }

                    if(response.data.topographyPhysiognomyMap !== undefined){
                        resault.push(response.data.topographyPhysiognomyMap.describe)
                    }

                    if(response.data.weatherConditionsMap !== undefined){
                        resault.push(response.data.weatherConditionsMap.describe)
                    }

                    if(response.data.waterConditionMap !== undefined){
                        resault.push(response.data.waterConditionMap.describe)
                    }

                    if(response.data.electricPowerMap !== undefined){
                        resault.push(response.data.electricPowerMap.describe)
                    }

                    if(response.data.networkCommMap !== undefined){
                        resault.push(response.data.networkCommMap.describe)
                    }


                    that.textList = resault
                    that.copyData = resault
                    console.log('ModelData',resault)
                })
            },

            copyed(){

            },

            updateProductionBase () {
                this.$router.push({
                    path: '/member/addProductionBase',
                    query: {
                        id: this.$route.query.id
                    }
                })
            }
        }
    }
</script>
<style scoped>
    .guide-title {
        margin-top: 20px;
        margin-left: 40px;
    }
    .warp {
        text-align: center;
    }
    .card-title {
        border: 1px solid rgba(217, 217, 217, 1);
        border-bottom: none;
        background-color: rgba(244, 244, 244, 1);
        margin-top: 20px;
        height: 50px;
    }
    .card-content {
        border: 1px solid rgba(217, 217, 217, 1);
        border-top: none;
    }
    .content-pd {
        padding-top: 20px;
        padding-bottom: 20px;
        margin-left: 10px;
    }
    .camera-list {
        float: left;
        padding-left: 5px;
        padding-top: 5px;
    }
    .ma-viewAll{line-height: 30px;text-align: center;}
    .ma-text-content{text-indent: 25px;padding: 10px;line-height: 26px;}
</style>
<style lang="scss">
    @import '../../../../node_modules/swiper/dist/css/swiper';
    .content-pd .ivu-tooltip-popper{
        top: 0 !important;
    }
</style>