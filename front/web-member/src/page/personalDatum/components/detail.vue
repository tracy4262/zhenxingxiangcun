<template>
<div>
    <vui-affix-tabs class="pt30" :data="tabsData">
    <Card class="company-auth-5" >
        <div class="tc pd20" v-if="!isGate">
            <img :src="avatar" v-if="avatar" class="user-avatar">
            <img src="../../../img/default_header.png" v-else class="user-avatar">
            <p class="pt20">{{displayName}}</p>
        </div>
        <div id="privateInformation" v-if="tabsList.privateInformation">
            <Title title="隐私信息">
                <Button type="primary" size="small" @click="handleModify('privateInformation')" v-if="isedit && !isGate" >修改</Button>
            </Title>
            <private-information ref="privateInformation" ></private-information>
        </div>
        <div id="networkInformation" v-if="tabsList.networkInformation">
            <Title title="网络信息">
                <Button type="primary" size="small" @click="handleModify('networkInformation')" v-if="isedit && !isGate" >修改</Button>
            </Title>
            <network-information ref="networkInformation" ></network-information>
        </div>
        <div id="education" v-if="tabsList.education">
            <Title title="教育经历">
                <Button type="primary" size="small" @click="handleModify('education')" v-if="isedit && !isGate" >修改</Button>
            </Title>
            <education ref="education" ></education>
        </div>
        <div id="work" v-if="tabsList.work">
            <Title title="工作经历">
                <Button type="primary" size="small" @click="handleModify('work')" v-if="isedit && !isGate" >修改</Button>
            </Title>
            <work ref="work" ></work>
        </div>
        <div id="policy" v-if="tabsList.policy">
            <Title title="政治面貌">
                <Button type="primary" size="small" @click="handleModify('policy')" v-if="isedit && !isGate" >修改</Button>
            </Title>
            <policyInfo ref="policy" ></policyInfo>
        </div>
        <div id="religion" v-if="tabsList.religion">
            <Title title="宗教信仰">
                <Button type="primary" size="small" @click="handleModify('religion')" v-if="isedit && !isGate" >修改</Button>
            </Title>
            <religionInfo ref="religion" ></religionInfo>
        </div>
        <div id="professionalInformation" v-if="tabsList.professionalInformation">
            <Title title="专业信息">
                <Button type="primary" size="small" @click="handleModify('professionalInformation')" v-if="isedit && !isGate" >修改</Button>
            </Title>
            <professional-information ref="professionalInformation" ></professional-information>
        </div>
        <div id="corpHonor" v-if="tabsList.corpHonor">
            <Title title="荣誉风采">
                <Button type="primary" size="small" @click="handleModify('corpHonor')" v-if="isedit && !isGate" >修改</Button>
            </Title>
            <corp-honor ref="corpHonor" ></corp-honor>
        </div>
        <div id="member"  v-if="tabsList.member">
            <Title title="家庭成员">
                <Button type="primary" size="small" @click="handleModify('member')" v-if="isedit && !isGate" >修改</Button>
            </Title>
            <family-detail ref="member" ></family-detail>  
        </div>
        <div id="product"  v-if="tabsList.product">
            <Title title="产出产品">
                <Button type="primary" size="small" @click="handleModify('product')" v-if="isedit && !isGate" >修改</Button>
            </Title>
            <product-detail ref="product"></product-detail>
        </div>
        <div id="provide"  v-if="tabsList.provide">
            <Title title="提供服务">
                <Button type="primary" size="small" @click="handleModify('provide')" v-if="isedit && !isGate" >修改</Button>
            </Title>
            <service-detail ref="provide"></service-detail>
        </div>
        <div id="purchase"  v-if="tabsList.purchase">
            <Title title="求购信息">
                <Button type="primary" size="small" @click="handleModify('purchase')" v-if="isedit && !isGate" >修改</Button>
            </Title>
            <want-to-buy ref="purchase"></want-to-buy>
        </div>
        <div id="asset" v-if="tabsList.asset">
            <Title title="资产管理">
                <Button type="primary" size="small" @click="handleModify('asset')" v-if="isedit && !isGate" >修改</Button>
            </Title>
            <Tag style="margin-left: 10px; margin-top: 10px;" checked color="default">房产信息</Tag>
            <house ref="house" ></house>
            <place-of-business ref="placeOfBusiness"></place-of-business>
            <Tag style="margin-left: 10px;" checked color="default">主要设备</Tag>
            <modern ref="modern" :isAdd="false" ></modern>
            <Tag style="margin-left: 10px;" checked color="default">生产设施</Tag>
            <facility-assets ref="facilityAssets"></facility-assets>
            <Tag style="margin-left: 10px;" checked color="default">无形资产</Tag>
            <intangible-assets ref="intangibleAssets" ></intangible-assets>
        </div>
        <!-- <div id="house"  v-if="tabsList.house">
            <Title title="房屋生活情况">
                <Button type="primary" size="small" @click="handleModify('house')" v-if="isedit && !isGate" >修改</Button>
            </Title>
            <house ref="house" ></house>
        </div>
        <div id="modern" v-if="tabsList.modern">
            <Title title="家庭现代化情况">
                <Button type="primary" size="small" @click="handleModify('modern')" v-if="isedit && !isGate" >修改</Button>
            </Title>
            <modern ref="modern" :isAdd="false" ></modern>
        </div> -->
        <div id="department" v-if="tabsList.department">
            <Title title="组织机构">
                <Button type="primary" size="small" @click="handleModify('department')" v-if="isedit && !isGate" >修改</Button>
            </Title>
            <department ref="department"></department>
        </div>
        <div id="team" v-if="tabsList.team">
            <Title title="管理团队">
                <Button type="primary" size="small" @click="handleModify('team')" v-if="isedit && !isGate" >修改</Button>
            </Title>
            <team ref="team" ></team>
        </div>
        <div id="survey" v-if="tabsList.survey">
            <Title title="经营概况">
                <Button type="primary" size="small" @click="handleModify('survey')" v-if="isedit && !isGate" >修改</Button>
            </Title>
            <survey ref="survey" ></survey>
        </div>
        <div id="assetFinance"  v-if="tabsList.assetFinance">
            <Title title="财务信息">
                <Button type="primary" size="small" @click="handleModify('assetFinance')" v-if="isedit && !isGate" >修改</Button>
            </Title>
            <asset-finance ref="assetFinance" ></asset-finance>
        </div>
        <div id="productService" v-if="tabsList.productService">
            <Title title="产品&服务">
                <Button type="primary" size="small" @click="handleModify('productService')" v-if="isedit && !isGate" >修改</Button>
            </Title>
            <product-service ref="productService" ></product-service>
        </div>
        <div id="qualification" v-if="tabsList.qualification">
            <Title title="专业资质">
                <Button type="primary" size="small" @click="handleModify('qualification')" v-if="isedit && !isGate" >修改</Button>
            </Title>
            <qualification ref="qualification" ></qualification>
        </div>
        <div id="commonAssets"  v-if="tabsList.commonAssets">
            <Title title="普通资产">
                <Button type="primary" size="small" @click="handleModify('commonAssets')" v-if="isedit && !isGate" >修改</Button>
            </Title>
            <common-assets ref="commonAssets"></common-assets>
        </div>
        <!-- <div id="intangibleAssets"  v-if="tabsList.intangibleAssets">
            <Title title="无形资产">
                <Button type="primary" size="small" @click="handleModify('intangibleAssets')" v-if="isedit && !isGate" >修改</Button>
            </Title>
            <intangible-assets ref="intangibleAssets" ></intangible-assets>
        </div>
        <div id="facilityAssets"  v-if="tabsList.facilityAssets">
            <Title title="设施资产">
                <Button type="primary" size="small" @click="handleModify('facilityAssets')" v-if="isedit && !isGate" >修改</Button>
            </Title>
            <facility-assets ref="facilityAssets"></facility-assets>
        </div>
        <div id="placeOfBusiness"  v-if="tabsList.placeOfBusiness">
            <Title title="经营场所">
                <Button type="primary" size="small" @click="handleModify('placeOfBusiness')" v-if="isedit && !isGate" >修改</Button>
            </Title>
            <place-of-business ref="placeOfBusiness"></place-of-business>
        </div> -->
    </Card>
  </vui-affix-tabs>
</div>
</template>
<script>
    import Title from '../../userAuth/components/title'
    import vuiAffixTabs from './vui-affix-tabs'
    import education from './education'
    import privateInformation from './privateInformation'
    import networkInformation from './networkInformation'
    import work from './work'
    import policyInfo from './policy'
    import religionInfo from './religion'
    import survey from './survey'
    import team from './team'
    import professionalInformation from './professionalInformation'
    import corpHonor from './corpHonor'
    import qualification from './proQualification'
    import productService from './productService'
    import assetFinance from './assetFinance'
    import house from './house'
    import modern from './modern'
    import familyDetail from './familyDetail'
    import productDetail from './productDetail'
    import serviceDetail from './serviceDetail'
    import wantToBuy from './wantToBuy'
    import department from './department'
    import commonAssets from './commonAssets'
    import intangibleAssets from './intangibleAssets'
    import facilityAssets from './facilityAssets'
    import placeOfBusiness from './placeOfBusiness'
    export default{
        components: {
            Title,
            vuiAffixTabs,
            education,
            privateInformation,
            networkInformation,
            work,
            policyInfo,
            religionInfo,
            house,
            modern,
            team,
            assetFinance,
            productService,
            corpHonor,
            qualification,
            professionalInformation,
            survey,
            department,
            commonAssets,
            intangibleAssets,
            facilityAssets,
            placeOfBusiness,
            familyDetail,
            productDetail,
            serviceDetail,
            wantToBuy
        },
        props: {
            isGate: {
                type: Boolean,
                default: false
            }
        },
        data(){
            return{
                account:'',
                isNext:false,
                showTab:[],
                tabsData:[],
                tabsList: {
                    assetFinance: false,
                    corpHonor:false,
                    department:false,
                    education:false,
                    house:false,
                    member:false,
                    product:false,
                    provide:false,
                    purchase:false,
                    modern:false,
                    networkInformation:false,
                    policy:false,
                    privateInformation:false,
                    productService:false,
                    professionalInformation:false,
                    qualification:false,
                    religion:false,
                    survey:false,
                    team:false,
                    work:false
                },
                loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
                avatar:'',
                displayName:'',
                isedit: false
            }
        },
		created() {
            this.tabsList.networkInformation = false
            this.account = this.$route.query.uid
            if(this.loginUser && this.loginUser.loginAccount == this.account){
                // 自己登陆自己的查看
                this.isedit = true
            }else if(!this.account){
                // 没有传uid 则取登陆人的信息
                this.isedit = true
                this.account = this.loginUser.loginAccount
                this.avatar = this.loginUser.avatar
                this.displayName = this.loginUser.displayName
            }
            this.getModel()
            this.queryConfig()
            this.getUser()
		},
        methods:{
            //查询用户头像以及用户名
            getUser () {
                this.$api.post('/member/login/findCurrentUser', {
                    account: this.account
                }).then(res => {
                    if (res.data.displayName) {
                        this.displayName = res.data.displayName
                    }
                    if(res.data.avatar){
                        this.avatar = res.data.avatar
                    }
                })
            },
            // 初始化获取数据
            getModel () {
                this.$api.post('/member/perfectInfo/findPerfectInfo',{account:this.account}).then(response =>{
                    console.log(response)
                    if (response.code == 200) {
                        // 这个判断里面写了所有取数据的，但是当此用户类型没有这个模块的时候就不会返回这个key的数据，
                        // 所以要先判断key是否存在，再根据各个模块数据类型的不同判断，里面是否有值，如果有值，就取值了之后，赋值给当前模块
                        var data = response.data
                        //隐私信息
                        if (data.privateInformation && Object.keys(data.privateInformation).length && this.$refs['privateInformation']) {
                            // 如果有数据说明之前已经保存过，不管是保存的是否是空数据，都已经有数据格式了
                            this.$refs['privateInformation'].getData(data.privateInformation)
                        }
                        //网络信息networkInformation
                        if (data.networkInformation && Object.keys(data.networkInformation).length && this.$refs['networkInformation']) {
                            this.$refs['networkInformation'].getData(data.networkInformation)
                        }
                        //教育经历 education
                        if (data.education && data.education.length && this.$refs['education']) {
                            this.$refs['education'].getData(data.education)
                        }
                        //工作经历 work
                        if (data.work && data.work.length && this.$refs['work']) {
                            this.$refs['work'].getData(data.work)
                        }
                        //政治面貌 policyInfo
                        if (data.policy && Object.keys(data.policy).length && this.$refs['policy']) {
                            this.$refs['policy'].getData(data.policy)
                        }
                        //宗教信仰
                        if (data.religion && Object.keys(data.religion).length && this.$refs['religion']) {
                            this.$refs['religion'].getData(data.religion)
                        }
                        //专业信息 professionalInformation
                        if (data.professionalInformation && Object.keys(data.professionalInformation).length && this.$refs['professionalInformation']) {
                            this.$refs['professionalInformation'].getData(data.professionalInformation)
                        }
                        //荣誉风采 corpHonor
                        if (data.corpHonor && data.corpHonor.length && this.$refs['corpHonor']) {
                            this.$refs['corpHonor'].getData(data.corpHonor)
                        }
                        //家庭成员 member
                        if (data.member && data.member.length && this.$refs['member']) {
                            this.$refs['member'].getData(data.member);
                        }
                        //产出产品 product
                        if (data.product && data.product.length && this.$refs['product']) {
                            this.$refs['product'].getData(data.product);
                        }
                        //提供服务 provide
                        if (data.provide && data.provide.length && this.$refs['provide']) {
                            this.$refs['provide'].getData(data.provide);
                        }
                        //求购信息 purchase
                        if (data.purchase && data.purchase.length && this.$refs['purchase']) {
                            this.$refs['purchase'].getData(data.purchase)
                        }
                        //房屋生活情况 house
                        if (data.house && data.house.length && this.$refs['house']) {
                            this.$refs['house'].getData(data.house)
                        }
                        //家庭现代化情况  modern
                        if (data.modern && data.modern.length && this.$refs['modern']) {
                            this.$refs['modern'].getData(data.modern)
                        }
                        //团队管理  team
                        if (data.team && data.team.length && this.$refs['team']) {
                            this.$refs['team'].getData(data.team)
                        }
                        //survey 经营概况
                        if (data.survey && Object.keys(data.survey).length && this.$refs['survey']) {
                            this.$refs['survey'].getData(data.survey)
                        }
                         //finance 财务管理
                        if (data.assetFinance  && Object.keys(data.assetFinance).length && this.$refs['assetFinance']) {
                            this.$refs['assetFinance'].getData(data.assetFinance)
                        }                        
                        // 普通资产
                        if (data.commonAssets && data.commonAssets.length && this.$refs['commonAssets']) {
                            this.$refs['commonAssets'].getData(data.commonAssets)
                        }
                        // 无形资产
                        if (data.intangibleAssets && data.intangibleAssets.length && this.$refs['intangibleAssets']) {
                            this.$refs['intangibleAssets'].getData(data.intangibleAssets);
                        }
                        // 设施资产
                        if ( data.facilityAssets && data.facilityAssets.length && this.$refs['facilityAssets'] ) {
                            this.$refs['facilityAssets'].getData(data.facilityAssets);
                        }
                        // 经营场所
                        if ( data.placeOfBusiness &&  data.placeOfBusiness.length && this.$refs['placeOfBusiness'] ) {
                            this.$refs['placeOfBusiness'].getData(data.placeOfBusiness);
                        }
                        //产品服务 productService
                        if (data.productService && data.productService.length && this.$refs['productService']) {
                            this.$refs['productService'].getData(data.productService)
                        }
                        //专业资质 qualification
                        if (data.qualification && data.qualification.length && this.$refs['qualification']) {
                            this.$refs['qualification'].getData(data.qualification)
                        }
                    }
                })
            },
            // 查询用户角色配置的表单信息
            queryConfig () {
                this.$api.post('/member/perfectInfo/findSysUserInfo', {
                    account: this.account
                }).then(response => {
                    if (response.code === 200) {
                        var data = response.data.tabsData
                        this.tabsData = data.splice(0,data.length-1)
                        // 处理表单是否显示
                        let arr = []
                        this.tabsData.forEach(item => {
                            this.tabsList[item.name] = true
                        })
                    }
                }).catch(error => {
                    console.log(error)
                })
            },
            //点击修改
            handleModify(detil){
                this.$router.push({path:`/personalIndex/modify#${detil}`})
            }
        }
    }

</script>
<style scoped>
.user-avatar{
    width:120px;
    height:120px;
    border-radius: 50%;
    border:2px solid #EEEEEE;
}
</style>
