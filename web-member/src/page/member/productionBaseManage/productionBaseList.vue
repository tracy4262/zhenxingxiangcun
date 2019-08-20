<template>
    <div class="layout">
        <div ref="top">
            <top :address="false" />
        </div>
        <div class="main" :style="{'min-height': height}">
            <div class="container">
                <Row :gutter="20">
                    <Col span="24">
                        <app-banner
                            src="../../../../static/img/app-banner-product-base.png"
                            title="生产基地管理">
                        </app-banner>
                        <div v-if="isStart && isShow">
                            <h3 class="guide-title">生产基地管理</h3>
                            <div class="warp">
                                <Icon type="android-alert" size="100" class="mt-5"></Icon>
                                <p class="mt-5">欢迎您使用生产基地管理，本应用将帮助建立生产基地信息库。</p>
                                <div class="mt-5">
                                    <Button type="primary" class="btn" @click="addProductionBase">开始</Button>
                                    <span>&nbsp;&nbsp;&nbsp;&nbsp;或者</span>
                                    <Button type="text" @click="goToProductionBaseList">以后再说</Button>
                                </div>
                            </div>
                        </div>
                        <div v-if="!isStart && !isShow">
                            <Form class="mt10" inline>
                                <FormItem>
                                    <Input v-model="baseName" placeholder="基地名称"></Input>
                                </FormItem>
                                <FormItem>
                                    <Input v-model="contactName" placeholder="联系人"></Input>
                                </FormItem>
                                <FormItem>
                                    <Input v-model="contactTel" placeholder="电话号码"></Input>
                                </FormItem>
                                <FormItem>
                                    <Button type="primary" @click.native="handleSearch">查询</Button>
                                </FormItem>
                                <FormItem>
                                    <Button type="primary" @click.native="addProductionBase">新增</Button>
                                </FormItem>
                            </Form>
                            <Row :gutter="16">
                                <Col span="8" v-for="(item,index) in mapItemData" :key="index">
                                    <map-item
                                    :to="`productionBaseDetail?id=${item.productId}&current=${current}`"
                                    :data="item"
                                    @del="del"
                                    class="mb10"></map-item>
                                </Col>
                            </Row>
                            <Page class="mt20 tc mb50" :page-size="9" :total="total" :current="current" @on-change="handleChangePage"></Page>
                        </div>
                    </Col>
                </Row>
            </div>
        </div>
        <div ref="foot">
            <foot></foot>
        </div>
    </div>
</template>

<script>
    import top from '../../../top'
    import foot from '../../../foot'
    import mapItem from '../components/productionMapList'
    import appBanner from '~components/app-banner'
    export default {
        components:{
            top,
            foot,
            mapItem,
            appBanner
        },
        data() {
            return {
                mapItemData: [],
                total: 0,
                baseName: '',
                contactName: '',
                contactTel: '',
                loginuserinfo:{},
                isShow:false,
                isStart:true,
                current:1,
                height: ''
            }
        },
        created () {
            this.loginuserinfo = JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
            this.getinitStart()
        },
        mounted () {
            this.handleGetHeight()
        },
        methods: {            
            // 获取页面高度
            handleGetHeight () {
                let clientHeight = document.documentElement.clientHeight
                let topHeight = this.$refs.top.offsetHeight
                let footHeight = this.$refs.foot.offsetHeight
                this.height = `${clientHeight-topHeight-footHeight}px`
            },
            del(){
                this.loadMapData()
            },
            getinitStart(){
                this.$api.post('/member/product-base/product-is-exist',{
                    loginAccount: this.loginuserinfo.loginAccount}).then(res=>{
                    console.log('isShow',res)
                    if(res.code==200){
                        if(res.data && res.data.length>0){
                            this.isStart = false
                            if(this.$route.query.id){
                                this.current= parseInt(this.$route.query.id)
                                this.loadMapData(this.baseName, this.contactName, this.contactTel, this.current)
                            }else{
                                this.loadMapData()
                            }
                        }else{
                            this.isShow = true
                        }
                     }
                })
            },
            addProductionBase () {
                //点击开始
                this.$router.push('/member/addProductionBase')
            },
            goToProductionBaseList () {
                //点击以后再说
                this.isStart = false
                this.isShow = false
                this.loadMapData()
            },
            // 搜索
            handleSearch () {
                this.loadMapData(this.baseName, this.contactName, this.contactTel)
            },
            // 分页
            handleChangePage (pageNum) {
                this.current = pageNum
                this.loadMapData(this.baseName, this.contactName, this.contactTel, pageNum)
            }, 
            loadMapData (baseName = '', contactName = '', contactTel = '', pageNum = 1) {
                this.$api.post('/member/product-base/select-all', {
                    loginAccount: this.loginuserinfo.loginAccount,
                    baseName: baseName,
                    contactName: contactName,
                    contactTel: contactTel,
                    pageNum: pageNum,
                    pageSize: 9
                }).then(res => {
                    this.total = res.data.total
                    this.mapItemData = res.data.list
                })
            },
            addProductionBase () {
                this.$router.push('/member/addProductionBase')
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
    .mt-5 {
        margin-top: 25px;
    }
    .btn {
        width: 100px;
    }

</style>