<template>
<Modal
    v-model="show"
    title="基地"
    width="870"
    >
    <div class="pd20" style="min-height: 500px;">
        <Row type="flex" align="middle">
            <Col span="20">
                <h1>{{item.productionBaseName}}</h1>
            </Col>
            <!-- <Col span="4" class="tr">
                <Button type="primary" @click="back">返回</Button>
            </Col> -->
        </Row>
        <Title title="基础相册" class="mt20"></Title>
        <Row type="flex" align="middle">
            <Col span="24">
                <swiper :options="swiperOption" ref="mySwiper" style="margin-top: 30px; margin-bottom: 30px;">
                    <div class="swiper-button-prev" slot="button-prev"></div>
                    <swiper-slide v-for="(item,index) in photoList" :key="index">
                        <Card :padding="0" @click.native="detail(index)">
                            <Row><img :src="item.imageUrl" style="width: 100%; height: 135px;"></Row>
                            <Row><div class="tc mt10 mb10" style="font-size: 16px; min-height: 24px;">{{ item.mediaName }}</div></Row>
                        </Card>
                    </swiper-slide>
                    <div class="swiper-button-next" slot="button-next"></div>
                </swiper>
            </Col>
        </Row>
        <!-- 照片详细信息弹框 -->
        <Modal v-model="detailModal" title="照片详情" width="1000" :closable="false" @ok="detailModal=false" @cancel="detailModal=false">
            <Row>
                <Col span="18"><img :src="detailPicture.imageUrl" style="width: 100%; height: 450px;"></Col>
                <Col span="6">
                    <h1 class="ml20">{{detailPicture.mediaName}}</h1>
                    <div class="mt10 ml20 fz14">{{detailPicture.mediaDescribe}}</div>
                    <div class="mt10 ml20 fz14">拍摄人：{{detailPicture.author}}</div>
                    <div class="mt10 ml20 fz14">拍摄地点：{{detailPicture.photoAddress}}</div>
                    <div class="mt10 ml20 fz14">拍摄时间：{{detailPicture.photoTime}}</div>
                </Col>
            </Row>
        </Modal>
        <Title title="基础简介" class="mt20"></Title>
        <Row class="pd20">
            <!-- 设施信息 -->
            <Col span="15">
            	<Row class="mb20" style="font-size: 16px;">
                    生产基地位于{{ item.location }},
                    <span v-if="item.factArea !== ''">土地总面积{{ item.factArea }}平方米。</span>
                    {{ item.introduction }}
                </Row>
                <Row>
                    <Row type="flex" align="middle" class="pt5">
                        <Col span="1">
                            <img src="../../../../../static/img/video-icon.png" style="width: 20px;">
                        </Col>
                        <Col span="4" class="equipment">
                            <span class="ml10">实况直播</span>
                        </Col>
                        <Col span="19">
                            <span v-if="this.videoDevice.length > 0">
                                <a>查看实况 >></a>
                                <a class="ml20">查看实时数据 >></a>
                                <a class="ml20" @click="modal=true;detailData=videoDevice">查看详情 >></a>
                            </span>
                            <span v-else>暂无相关设施</span>
                        </Col>
                    </Row>
                    <Row type="flex" align="middle" class="mt10">
                        <Col span="1">
                            <img src="../../../../../static/img/weather-icon.png" style="width: 20px;">
                        </Col>
                        <Col span="4" class="equipment">
                            <span class="ml10">天气监测</span>
                        </Col>
                        <Col span="19">
                            <span v-if="this.weatherDevice.length > 0">
                                <a>查看实况 >></a>
                                <a class="ml20">查看实时数据 >></a>
                                <a class="ml20" @click="modal=true;detailData=weatherDevice">查看详情 >></a>
                            </span>
                            <span v-else>暂无相关设施</span>
                        </Col>
                    </Row>
                    <Row type="flex" align="middle" class="mt10">
                        <Col span="1">
                            <img src="../../../../../static/img/soil-icon.png" style="width: 20px;">
                        </Col>
                        <Col span="4" class="equipment">
                            <span class="ml10">土壤检测</span>
                        </Col>
                        <Col span="19">
                            <span v-if="this.soilDevice.length > 0">
                                <a>查看实况 >></a>
                                <a class="ml20">查看实时数据 >></a>
                                <a class="ml20" @click="modal=true;detailData=soilDevice">查看详情 >></a>
                            </span>
                            <span v-else>暂无相关设施</span>
                        </Col>
                    </Row>
                    <Row type="flex" align="middle" class="mt10">
                        <Col span="1">
                            <img src="../../../../../static/img/other-icon.png" style="width: 20px;">
                        </Col>
                        <Col span="4" class="equipment">
                            <span class="ml10">其他设施</span>
                        </Col>
                        <Col span="19">
                            <span v-if="this.customDevice.length > 0">
                                <a>查看实况 >></a>
                                <a class="ml20">查看实时数据 >></a>
                                <a class="ml20" @click="modal=true;detailData=customDevice">查看详情 >></a>
                            </span>
                            <span v-else>暂无相关设施</span>
                        </Col>
                    </Row>
                </Row>
            </Col>
            <!-- 地图 -->
            <Col span="8" offset="1">
                <Row>
                    <img :src="`//api.map.baidu.com/staticimage?width=286&height=180&center=${item.coordinate.split(',')[0]},${item.coordinate.split(',')[1]}&zoom=11&markers=${item.coordinate.split(',')[0]},${item.coordinate.split(',')[1]}`" style="width: 100%;">
                </Row>
            </Col>
        </Row>
        <!-- 设施信息详情弹框 -->
        <Modal v-model="modal" title="查看详情" width="1000" :closable="false" @on-ok="ok" @on-cancel="cancel">
            <Row class="pd20">
                <Card v-for="(item, index) in detailData" :key="index" :style="index !== 0 ? 'margin-top: 20px' : ''">
                    <Row>
                        <Col span="8">设施类别：{{ item.commonName }}</Col>
                        <Col span="8">设施名称：{{ item.name }}</Col>
                        <Col span="8">设施编号：{{ item.no }}</Col>
                    </Row>
                    <Row class="mt10">
                        <Col span="8">设施规格：{{ item.capacity }}</Col>
                        <Col span="8">投资金额：{{ item.investment + '万元' }}</Col>
                        <Col span="8">责任人：{{ item.contact }}</Col>
                    </Row>
                    <Row class="mt10">
                        <Col span="8">设施地址：{{ item.address }}</Col>
                        <Col span="16">所处位置：{{ item.location + ' ' + item.group + '组' + item.number + '号' }}</Col>
                    </Row>
                    <Row class="mt10">
                        <Col span="16">说明：{{ item.description }}</Col>
                    </Row>
                    <Row class="mt10">
                        <div style="float: left;">上传图片：</div>
                        <img v-for="it in item.pictureList" :key="index" :src="it" style="width: 60px; height: 60px;" class="mr10" />
                    </Row>
                    <Row class="mt10">
                        坐标：{{ item.longitude + ', ' + item.latitude }}
                        <img class="mt10" :src="`//api.map.baidu.com/staticimage/v2?ak=7syPirZ2AWxacMfHeAfuujdDgFmxCB5g&center=${item.longitude},${item.latitude}&zoom=15&markers=${item.longitude},${item.latitude}&width=800`" width="100%" />
                    </Row>
                </Card>
            </Row>
        </Modal>
        <Title title="联系方式" class="mt20"></Title>
        <Row class="pd20">
            <Card v-for="(item, index) in contactInfo" :key="index" class="mt20">
                <Row>
                    <Col span="8">会员名称全称：{{ item.member_name_status ? item.member_name : '暂未公开' }}</Col>
                    <Col span="8">会员名称简称：{{ item.member_abbreviation_status ? item.member_abbreviation : '暂未公开' }}</Col>
                    <Col span="8">联系人姓名：{{ item.contact_name_status ? item.contact_name : '暂未公开' }}</Col>
                </Row>
                <Row class="mt10">
                    <Col span="8">身份证号码：{{ item.card_status ? item.card : '暂未公开' }}</Col>
                    <Col span="8">座机电话：{{ item.seat_phone_status ? item.seat_phone : '暂未公开' }}</Col>
                    <Col span="8">手机：{{ item.phone_status ? item.phone : '暂未公开' }}</Col>
                </Row>
                <Row class="mt10">
                    <Col span="8">QQ号：{{ item.qq_number_status ? item.qq_number : '暂未公开' }}</Col>
                    <Col span="8">微信：{{ item.wechat_number_status ? item.wechat_number : '暂未公开' }}</Col>
                    <Col span="8">邮箱：{{ item.email_status ? item.email : '暂未公开' }}</Col>
                </Row>
                <Row class="mt10">
                    <Col span="8">网站地址：{{ item.website_url_status ? item.website_url : '暂未公开' }}</Col>
                    <Col span="8">邮政编码：{{ item.postal_code_status ? item.postal_code : '暂未公开' }}</Col>
                </Row>
                <Row class="mt10">
                    <div style="float: left;">个人照片：</div>
                    <img v-if="item.image_status" :src="item.image[0]" style="width: 60px; height: 60px;" />
                    <span v-else>暂未公开</span>
                </Row>
                <Row class="mt10">
                    <Col span="12">所在位置：{{ item.location_status ? item.location : '暂未公开' }}</Col>
                    <Col span="12">会员详细地址：{{ item.address_status ? item.address + item.house_number : '暂未公开' }}</Col>
                </Row>
                <Row class="mt10">
                    坐标：{{ item.lng_lat_status ? item.longitude + ', ' + item.latitude : '暂未公开' }}
                    <img class="mt10" v-if="item.lng_lat_status" :src="`//api.map.baidu.com/staticimage/v2?ak=7syPirZ2AWxacMfHeAfuujdDgFmxCB5g&center=${item.longitude},${item.latitude}&zoom=15&markers=${item.longitude},${item.latitude}&width=800`" width="100%" />
                </Row>
            </Card>
        </Row>
        <Title title="详细信息" class="mt20"></Title>
        <Row class="pd20">
            <div v-for="(item, index) in detailInfo" :key="index">
                <div class="detail-title">{{ item.appName }}</div>
                <div class="mt10 mb20 fz14" v-if="item.textPreview.length > 0">
                    <span v-for="(item2, index2) in item.textPreview" :key="index2">
                        {{ item2.textPreview }}
                    </span>
                </div>
                <div class="mt10 mb20 fz14" v-else>暂无详细信息</div>
            </div>
        </Row>
    </div>
</Modal>
</template>
<script>
import Title from '../../../newApplication/productionBase/components/title2'
import { swiper, swiperSlide } from 'vue-awesome-swiper'
export default {
    name: 'productionBaseDetail',
    components: {
        Title,
        swiper,
        swiperSlide
    },
    data () {
        return {
            show: false,
            item: {
                id: 1,
                productionBaseName: '',
                contactName: '',
                phoneNumber: '',
                coordinate: '',
                introduction: ''
            },
            detailData: [],
            videoDevice: [],
            weatherDevice: [],
            soilDevice: [],
            customDevice: [],
            contactInfo: [],
            photoList: [],
            detailInfo: [],
            modal: false,
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
            detailModal: false,
            detailPicture: {}
        }
    },
    created () {
        // this.init()
    },
    methods: {
        init (account, id) {
            this.show = true
            this.$api.post('/member-reversion/productionBase/baseIntroduction', {
                account: account,
                baseId: id
            }).then(response => {
                console.log('response', response)
                if (response.code === 200) {
                    this.item = response.data.baseIntroduction.baseInfo
                    this.photoList = response.data.baseIntroduction.photoList
                    response.data.baseIntroduction.iotDeviceInfo.forEach(element => {
                        if (element.commonName === '监控设施') {
                            this.videoDevice.push(element)
                        } else if (element.commonName === '天气监测设施') {
                            this.weatherDevice.push(element)
                        } else if (element.commonName === '土壤检测设施') {
                            this.soilDevice.push(element)
                        } else if (element.commonName === '自定义（其他设施）') {
                            this.customDevice.push(element)
                        }
                    })
                    this.contactInfo = response.data.baseIntroduction.contactInfo
                    this.detailInfo = response.data.detailInformation
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        back () {
            this.$router.push('/member/productionBaseList')
        },
        ok () {
            this.modal = false
        },
        cancel () {
            this.modal = false
        },
        detail (index) {
            this.detailPicture = this.photoList[index]
            this.detailModal = true
        }
    }
}
</script>
<style lang="scss" scoped>
    .info {
        color: #4a4a4a;
        font-size: 14px;
    }
    .equipment {
        color: #4a4a4a;
        font-size: 16px;
    }
    .equipment-name {
        color: #3889FF;
        cursor: pointer;
    }
    .detail-title {
        color: #00d280;
        font-size: 16px;
    }
    .fz14 {
        font-size: 14px;
    }
</style>
<style lang="scss">
    @import '../../../../../node_modules/swiper/dist/css/swiper';
    .content-pd .ivu-tooltip-popper{
        top: 0 !important;
    }
</style>
