<template>
    <div style="width: 1200px; margin: 40px auto;">
        <Row>
            <!-- 轮播图 -->
            <Col span="7">
                <Carousel v-model="value" loop dots="outside">
                    <CarouselItem v-for="(item, index) in rotationPictureList" class="carouse-height bc">
                        <div class="tc" @click="detail(item)" style="cursor: pointer;">
                            <img :src="item.image" style="width: 100%; height: 300px;" />
                            <div class="mt10 mb10 ell" :title="item.title">{{ item.title }}</div>
                        </div>
                    </CarouselItem>
                </Carousel>
            </Col>
            <!-- 走进企业 -->
            <Col span="12" class="pl20">
                <div style="background-color: #fafafa; padding-top: 1px; padding-bottom: 1px;">
                    <Title :title="introducationTitle" class="ml10"></Title>
                </div>
                <div class="mt10">
                    <p style="color: #4A4A4A; font-size: 14px; line-height: 24px;">{{ introduction }}<a @click="goIntroduction">查看详情 >></a></p>
                </div>
            </Col>
            </Col>
            <!-- 联系我们 -->
            <Col span="5" class="pl20">
                <!-- 标题 -->
                <div style="background-color: #fafafa; padding-top: 1px; padding-bottom: 1px;">
                    <Row type="flex" align="middle">
                        <Col span="16"><Title :title="contactUs.name" class="ml10"></Title></Col>
                        <Col span="8" class="tr">
                            <a @click="goContactUs" class="new-title-16 mr10">查看更多</a>
                        </Col>
                    </Row>
                </div>
                <!-- 详情 -->
                <Row class="mt10" v-if="contactUsDetail.longitudeStatus">
                    <img :src="`//api.map.baidu.com/staticimage?width=286&height=180&center=${contactUsDetail.longitude},${contactUsDetail.latitude}&zoom=14&markers=${contactUsDetail.longitude},${contactUsDetail.latitude}`" style="width: 100%; height: 150px;">
                </Row>
                <Row class="mt10" style="font-size: 12px; line-height: 23px; color: #4A4A4A;">
                    <Col span="24" v-if="contactUsDetail.memberMameStatus">姓名：{{ contactUsDetail.member_name }}</Col>
                    <Col span="24" v-if="contactUsDetail.seatPhoneStatus">座机电话：{{ contactUsDetail.seat_phone }}</Col>
                    <Col span="24" v-if="contactUsDetail.phoneStatus">手机号：{{ contactUsDetail.phone }}</Col>
                    <Col span="24" v-if="contactUsDetail.qqNumberStatus">QQ号：{{ contactUsDetail.qq_number }}</Col>
                    <Col span="24" v-if="contactUsDetail.wechatNumberStatus">微信号：{{ contactUsDetail.wechat_number }}</Col>
                    <Col span="24" v-if="contactUsDetail.emailStatus">邮箱：{{ contactUsDetail.email }}</Col>
                    <Col span="24" v-if="contactUsDetail.detailAddressStatus" class="ell" :title="contactUsDetail.detailAddress">详细地址：{{ contactUsDetail.detailAddress }}</Col>
                </Row>
            </Col>
        </Row>
        <!-- 产品服务 -->
        <Row>
            <indexProductList ref="product" :tabList="productTabList" :path="path"></indexProductList>
        </Row>
        <!-- 基地 -->
        <Row class="mt40">
            <index-production-base :dataList="productionBaseList" :productionBaseTitle="productionBaseTitle"></index-production-base>
        </Row>
        <!-- 资讯 -->
        <Row class="mt40">
            <indexKnowledgeList ref="knowledge" :tabList="knowledgeTabList" :path="path"></indexKnowledgeList>
        </Row>
    </div>
</template>
<script>
import Title from '../components/title'
import $ from 'jquery'
import { navStatus, moments, goToPath} from '../mixins/commonMixins'
import indexProductList from '../components/indexProductList'
import indexProductionBase from '../components/indexProductionBase'
import indexKnowledgeList from '../components/indexKnowledgeList'
export default {
mixins: [navStatus, moments, goToPath],
    components: {
        Title,
        indexProductList,
        indexProductionBase,
        indexKnowledgeList
    },
    data () {
        return {
            value: 0,
            templateId: '',
            rotationPictureList: [],
            account: '',
            columnSetting: [],
            introducationTitle: '',
            contactUs: {},
            contactUsDetail: {},
            introduction: '',
            productList: [
                {discount: '9.9', unit: '斤', seller: '张宏', name: '绿鲜知 云南甜玉米 水果玉米', address: '先锋村', image: 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1547676993,2174015573&fm=15&gp=0.jpg'},
                {discount: '22', unit: '斤', seller: '王尚', name: '鲢鱼', address: '先锋村', image: 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2234846370,1385206490&fm=26&gp=0.jpg'},
                {discount: '23', unit: '斤', seller: '王丽', name: '草鱼', address: '先锋村', image: 'https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=4114146544,4064470043&fm=200&gp=0.jpg'},
                {discount: '20.5', unit: '斤', seller: '韩鑫', name: '鲈鱼', address: '先锋村', image: 'https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3326750850,4117637607&fm=26&gp=0.jpg'},
                {discount: '15.5', unit: '斤', seller: '李硕', name: '莲藕', address: '先锋村', image: 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1700645086,3627266877&fm=200&gp=0.jpg'},
                {discount: '18.5', unit: '斤', seller: '孙素', name: '水果藕', address: '先锋村', image: 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1010754269,1960785645&fm=26&gp=0.jpg'},
                {discount: '50', unit: '斤', seller: '孙素', name: '花旗莲', address: '先锋村', image: 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1014809633,34860117&fm=26&gp=0.jpg'},
                {discount: '5000-8000', unit: '棵', seller: '孙素', name: '桂花树', address: '先锋村', image: 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2329696859,4081472697&fm=200&gp=0.jpg'},
                {discount: '1800-2000', unit: '棵', seller: '孙素', name: '广玉兰树', address: '先锋村', image: 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2208217277,3429394855&fm=26&gp=0.jpg'},
                {discount: '2500-3000', unit: '棵', seller: '孙素', name: '紫薇树', address: '先锋村', image: 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=4168228648,1904385376&fm=26&gp=0.jpg'},
            ],
            productionBaseList: [],
            productionBaseTitle: '生产基地',
            knowledgeTabList: [],
            path: '/cooperativePortal',
            productTabList: [] // 包含 产品 服务
        }
    },
    created () {
        this.createdInit ()
    },
    methods: {
        createdInit () {
            this.account = this.$route.query.uid
            this.$api.post('/member-reversion/realStep/findEnableStep', {
                account: this.account
            }).then(response => {
                if (response.code === 200) {
                    if (response.data) {
                        this.templateId = response.data.templateId
                        this.getIntroduction()
                        // this.getList('知识')
                        this.getData()
                    }
                }
            })
            this.getRotationPicture()
            this.getIntroduction()
            this.getContactUs()
            this.getProductionBase()
            // 路由改变 (在门户页面，点击右上角的我的门户)
            if (this.$route.query.tabType) {
                this.$parent.active = `${this.$route.query.id}/${window.location.pathname.split('/')[2]}/${this.$route.query.tabType}`
            } else {
                this.$parent.active = `${this.$route.query.id}/${window.location.pathname.split('/')[2]}`
            }
        },
        // 获取轮播图
        getRotationPicture () {
            this.$api.post('/member/inforMation/dynamicCoverList', {
                account: this.account
            }).then(response => {
                console.log('res', response)
                if (response.code === 200 && response.data) {
                    this.rotationPictureList = response.data
                    this.rotationPictureList.forEach(element => {
                        element.dataType = '动态'
                    })
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        detail (item) {
            console.log(item)
            this.goDetail(item)
        },
        // 根据栏目设置的 栏目名称 显示 动态 跟知识的title
        getData () {
            // url若为0则调用管理员侧的接口，不为0则调用用户侧的接口
            let url = this.templateId === '0' ? '/member-reversion/columnSetting/findColumnSettingInfo' : '/member-reversion/user/columnSetting/findColumnSettingInfo'
            this.$api.post(url, {
                account: this.account,
                templateId: this.templateId
            }).then(response => {
                if (response.code === 200) {
                    let data = response.data.columnSetting
                    this.columnSetting = response.data.columnSetting
                    let knowledgeTabList = []
                    let productTabList = new Array
                    console.log('data', data)
                    data.forEach((e, index) => {
                        if (e.attributionId.split('/')[0] === 'introduction') {
                            this.introducationTitle = e.columnName
                        }
                        if (e.attributionId.split('/')[0] === 'contcat') {
                            this.contactUs = {
                                name: e.columnName,
                                docType: ''
                            }
                        }
                        if (e.attributionId.split('/')[0] === 'dynamic') {
                            knowledgeTabList.push({
                            name: e.columnName,
                                dataType: '动态',
                                index: index + 1,
                                type: e.attributionId.split('/')[0],
                                docType: e.attributionId.split('/')[1]
                            })
                        }
                        if (e.attributionId.split('/')[0] === 'policy') {
                            knowledgeTabList.push({
                            dataType: '政策',
                                name: e.columnName,
                                index: index + 1,
                                type: e.attributionId.split('/')[0],
                                docType: e.attributionId.split('/')[1]
                            })
                        }
                        if (e.attributionId.split('/')[0] === 'knowledge') {
                            knowledgeTabList.push({
                                name: e.columnName,
                                dataType: '知识',
                                index: index + 1,
                                type: e.attributionId.split('/')[0],
                                docType: e.attributionId.split('/')[1],
                                attribution: e.attribution.split('/')[1]
                            })
                        }
                        if (e.attributionId.split('/')[0] === 'product') {
                            productTabList.push({
                                name: e.columnName,
                                dataType: '产品',
                                index: index + 1,
                                type: e.attributionId.split('/')[0],
                            })
                        }
                        if (e.attributionId.split('/')[0] === 'service') {
                            productTabList.push({
                                name: e.columnName,
                                dataType: '服务',
                                index: index + 1,
                                type: e.attributionId.split('/')[0],
                            })
                        }
                    })
                    this.productTabList = productTabList
                    this.knowledgeTabList = knowledgeTabList
                    this.$nextTick(() => {
                        this.$refs['product'].tabClick(0)
                        this.$refs['knowledge'].tabClick(0)
                    })
                }
            })
        },
        // 获取网站设置数据
        getIntroduction () {
            // url若为0则调用管理员侧的接口，不为0则调用用户侧的接口
            let url = this.templateId === '0' ? '/member-reversion/websiteSettings/findWebsiteSettingsInfo' : '/member-reversion/user/websiteSettings/findWebsiteSettingsInfo'
            this.$api.post(url, {
                account: this.loginAccount,
                templateId: this.templateId
            }).then(response => {
                if (response.code === 200) {
                    if (response.data.websiteInfo) {
                        this.websiteInfo = response.data.websiteInfo
                    }
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        // 查询会员介绍
        getIntroduction () {
            this.$api.post('/member/memberIntroduce/findMemberIntroduceInfo', {
                account: this.account
            }).then(response => {
                if (response.code === 200 && response.data) {
                    this.introduction = response.data.introduceDetail.abstracts
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        getContactUs () {
            this.$api.post('/member/columnSettings/findContact', {
                account: this.account
            }).then(response => {
                if (response.code === 200) {
                    console.log('contact us', response)
                    if (response.data.length) {
                        this.contactUsDetail = response.data[0].safeFormData[0]
                    }
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        getProductionBase () {
            this.$api.post('/member-reversion/myRecommend/baseList', {
                account: this.account,
                flag: '1', // 0:查询所有基地, 1:查询已推荐基地
                address: '',
                baseName: '',
                memberName: '',
                pageNum: 1,
                pageSize: 3
            }).then(response => {
                if (response.code === 200) {
                    console.log('res', response)
                    this.productionBaseList = response.data.list
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        goContactUs () {
            // 计算联系我们是属于第几个tab
            let id = 0
            this.columnSetting.forEach((element, index) => {
                if (element.attributionId === 'contcat') {
                    id = index + 1
                }
            })
            // 联系我们是一级栏目，跳转时需要带两个参数uid，id
            let url = `/cooperativePortal/contcat?uid=${this.account}&id=${id}`
            this.$router.push(url)
        },
        goIntroduction () {
            // 计算会员介绍是属于第几个tab
            let id = 0
            this.columnSetting.forEach((element, index) => {
                if (element.attributionId === 'introduction') {
                    id = index + 1
                }
            })
            // 会员介绍是一级栏目，跳转时需要带两个参数uid，id
            let url = `/cooperativePortal/introduction?uid=${this.account}&id=${id}`
            this.$router.push(url)
        },
    }
}
</script>
<style lang="scss" scoped>
.bc {
    background-color: #f9f9f9;
}
.carouse-height {
    height: 440px;
}
.tab-cus {
    padding: 8px 16px;
    font-size: 18px;
    display: inline-block;
    cursor: pointer;
    border-top: 2px solid #eeeeee;
}
.tab-cus-active {
    padding: 8px 16px;
    font-size: 18px;
    display: inline-block;
    cursor: pointer;
    color: #00C587;
    border-top: 2px solid #00C587;
    transition: 1s;
}
.tab-hr {
    height: 2px;
    position: absolute;
    background: #eeeeee;
    z-index: -999;
    width: 97%;
}
.dynamic-title {
    font-size: 18px;
    line-height: 36px;
    cursor: pointer;
    &:hover{
        color: #00C587
    }
}
.dynamic-title-big {
    font-size: 20px;
    line-height: 36px;
    font-weight: bold;
    cursor: pointer;
    &:hover{
        color: #00C587
    }
}
.circle {
    width: 6px;
    height: 6px;
    background-color: #cccccc;
    border-radius: 3px;
}
.investment-title {
    font-size: 16px;
    line-height: 36px;
    cursor: pointer;
    &:hover{
        color: #00C587
    }
}
.investment-title-big {
    font-size: 16px;
    line-height: 36px;
    font-weight: bold;
    cursor: pointer;
    &:hover{
        color: #00C587
    }
}
.new-title-16{
    color: #4A4A4A;
    font-size: 12px;
    &:hover{
        color: #00c587;
    }
}
</style>
