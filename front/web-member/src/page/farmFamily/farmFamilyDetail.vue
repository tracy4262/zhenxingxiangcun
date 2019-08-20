<template>
<div>
    <app-banner
        src="../../../static/img/app-banner-farm-family.png"
        title="种养户管理">
    </app-banner>
    <Breadcrumb class="pd20">
        <BreadcrumbItem to="/family/list">种养户管理</BreadcrumbItem>
        <BreadcrumbItem>详情</BreadcrumbItem>
    </Breadcrumb>
  <div class="mt20">
    <vui-affix-tabs :data="tabsData">
        <p class="t-orange pl20">完善种养户档案能更好的管理你的种养户</p>
        <div class="pd20">
            <section id="family" >
                <Title title="家庭成员"></Title>
                <family-detail ref="family" @on-submit="handleGetSubmit" ></family-detail>  
            </section>
            <section id="product" >
                <Title title="产出产品"></Title>
                <product-detail ref="product" @on-submit="handleGetSubmit"></product-detail>
            </section>
            <section id="service" >
                <Title title="提供服务"></Title>
                <service-detail ref="service" @on-submit="handleGetSubmit" ></service-detail>
            </section>
            <section id="wantToBuy" >
                <Title title="求购信息"></Title>
                <want-to-buy ref="wantToBuy" @on-submit="handleGetSubmit"></want-to-buy>
            </section>
            <section id="asset" >
                <Title title="资产管理"></Title>
                <Tag style="margin-left: 10px; margin-top: 10px;" checked color="default">房产信息</Tag>
                <house ref="house" @on-submit="handleGetSubmit" />
                <place-of-business ref="placeOfBusiness" @on-submit="handleGetSubmit" />
                <Tag style="margin-left: 10px;" checked color="default">主要设备</Tag>
                <modern ref="modern" :isAdd="false" @on-submit="handleGetSubmit" />
                <Tag style="margin-left: 10px;" checked color="default">生产设施</Tag>
                <facility-assets ref="facilityAssets" @on-submit="handleGetSubmit" />
                <Tag style="margin-left: 10px;" checked color="default">无形资产</Tag>
                <intangible-assets ref="intangibleAssets" @on-submit="handleGetSubmit" />
            </section>
            <!-- <section id="house" >
                <Title title="房屋生活情况"></Title>
                <house ref="house" @on-submit="handleGetSubmit" ></house>
            </section>
            <section id="modern" >
                <Title title="家庭现代化情况"></Title>
                <modern ref="modern" @on-submit="handleGetSubmit"></modern>
            </section> -->
        </div>
        <div class="tc pd30">
            <Button type="default" @click="handleCancel">取消</Button>
            <Button type="primary" @click="handleNext">确定</Button>
        </div>
    </vui-affix-tabs>
  </div>
</div>
</template>
<script>
import VuiAffixTabs from '../userAuth/components/vui-affix-tabs'
import Title from '../userAuth/components/title'
import familyDetail from './familyDetail'
import productDetail from './productDetail'
import serviceDetail from './serviceDetail'
import wantToBuy from './wantToBuy'
import house from './house'
import modern from './modern'
import placeOfBusiness from '../userAuth/components/placeOfBusiness'
import facilityAssets from '../userAuth/components/facilityAssets'
import intangibleAssets from '../userAuth/components/intangibleAssets'
import appBanner from '~components/app-banner'
    export default{
        components: {
            VuiAffixTabs,
            Title,
            familyDetail,
            productDetail,
            serviceDetail,
            wantToBuy,
            house,
            modern,
            appBanner,
            placeOfBusiness,
            facilityAssets,
            intangibleAssets
        },
        data () {
            return {
                isNext:true,
                tabsData: [
                    {name: 'family',title:'家庭成员'},
                    {name: 'product',title:'产出产品'},
                    {name: 'service',title:'提供服务'},
                    {name: 'wantToBuy',title:'求购信息'},
                    {name: 'asset', title:'资产管理'}
                    /* {name: 'house',title:'房屋生活情况'},
                    {name: 'modern',title:'家庭现代化情况'}, */
                ],
                id:'',
                familyDatas: [],
                productDatas: [],
                serviceDatas: [],
                buyDatas:[],
                houseDatas: [],
                placeOfBusinessDatas: [],
                facilityAssetsDatas: [],
                intangibleAssetsDatas: [],
                modernDatas: [
                    {
                        tv: '',
                        computer: '',
                        icebox: '',
                        ari: '',
                        car: '',
                        motorcycle: '',
                        heater: ''
                    }
                ]
            }
        },
        created () {
            //初始化获取数据
            this.id = this.$route.query.id
            this.$api.post('/member/farmFamilyDetail/findFarmFamilyDetail',{farm_family_id: this.id}).then(response => {
                console.log('init', response)
                if (response.code == 200) {
                    var data = response.data
                    console.log(response.data.purchase_intention.length)
                    
                    if (response.data.purchase_intention.length) {
                        var buyData = response.data.purchase_intention
                        this.$refs['wantToBuy'].getData(buyData)
                    }else{
                        this.$refs['wantToBuy'].getData(this.buyDatas)
                    }
                    
                    if (response.data.house_situation.length) {
                        var houseData = response.data.house_situation
                        this.$refs['house'].getData(houseData)
                    }else{
                        this.$refs['house'].getData(this.houseDatas)
                    }
                    if (response.data.family_situation.length) {
                        var modernData = response.data.family_situation
                        this.$refs['modern'].getData(modernData)
                    }else{
                        this.$refs['modern'].getData(this.modernDatas)
                    }
                    if (response.data.family_Member.length) {
                        var familyData = response.data.family_Member
                        this.$refs['family'].getData(familyData)
                    }else{
                        this.$refs['family'].getData(this.familyDatas)
                    }
                    if (response.data.output_product.length) {
                        var productData = response.data.output_product
                        this.$refs['product'].getData(productData)
                    }else{
                        this.$refs['product'].getData(this.productDatas)
                    }
                    if (response.data.provide_services.length) {
                        var serviceData = response.data.provide_services
                        this.$refs['service'].getData(serviceData)
                    }else{
                        this.$refs['service'].getData(this.serviceDatas)
                    }

                    if (response.data.placeOfBusiness.length) {
                        var placeOfBusinessData = response.data.placeOfBusiness
                        this.$refs['placeOfBusiness'].getData(placeOfBusinessData)
                    }else{
                        this.$refs['placeOfBusiness'].getData(this.placeOfBusinessDatas)
                    }

                    if (response.data.facilityAssets.length) {
                        var facilityAssetsData = response.data.facilityAssets
                        this.$refs['facilityAssets'].getData(facilityAssetsData)
                    }else{
                        this.$refs['facilityAssets'].getData(this.facilityAssetsDatas)
                    }

                    if (response.data.intangibleAssets.length) {
                        var intangibleAssetsData = response.data.intangibleAssets
                        this.$refs['intangibleAssets'].getData(intangibleAssetsData)
                    }else{
                        this.$refs['intangibleAssets'].getData(this.intangibleAssetsDatas)
                    }
                }
            })
        },
        methods: {
            //取消
            handleCancel () {
                this.$router.push({ path: '/family/list'})  
            },
            //确定
            handleNext () {
                console.log('点击了确认')
                this.tabsData.forEach(element => {
                    var corpInfo = element.name
                    if (element.name === 'asset') {
                        this.$refs['house'].handleSubmit()
                        this.$refs['placeOfBusiness'].handleSubmit()
                        this.$refs['modern'].handleSubmit()
                        this.$refs['facilityAssets'].handleSubmit()
                        this.$refs['intangibleAssets'].handleSubmit()
                    } else {
                        this.$refs[corpInfo].handleSubmit()
                    }
                })
                if(this.isNext){
                    var list = {}
                    list.farm_family_id = this.id
                    list.family_Member = this.$refs['family'].data
                    list.output_product = this.$refs['product'].data
                    list.provide_services = this.$refs['service'].data
                    list.purchase_intention = this.$refs['wantToBuy'].data
                    list.house_situation = this.$refs['house'].data
                    list.family_situation = this.$refs['modern'].data
                    list.placeOfBusiness = this.$refs['placeOfBusiness'].data
                    list.facilityAssets = this.$refs['facilityAssets'].data
                    list.intangibleAssets = this.$refs['intangibleAssets'].data
                    //表单验证成功的操作
                    console.log('表单验证成功', list)
                    this.$api.post('/member/farmFamilyDetail/updateFarmFamilyDetail',list).then(response => {
                        console.log(response)
                        if (response.code === 200) {
                            this.$router.push({ path: '/family/list'})
                        } else {
                            this.$Message.error('保存失败！')
                        }
                    })
                }else{
                    this.isNext = true
                    this.$Message.error('请核对输入信息')
                }
            },
            handleGetSubmit(e){
                if(!e){
                    this.isNext = e
                }
            }
        }
    }

</script>
<style>


</style>
