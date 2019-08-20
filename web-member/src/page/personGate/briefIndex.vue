<template>
    <div class="pb50">
        <div class="briefInex">
            <div id="overViewInfo" v-if="tabsList.overViewInfo">
                <Title title="概览信息"></Title>
                <p class="pl15" v-html="overViewInfo"></p>
            </div>
            <div id="privateInformation" v-if="tabsList.privateInformation" class="mt20">
                <Title title="隐私信息"></Title>
                <p class="pl15">{{ privateInformation }}</p>
            </div>
            <div id="networkInformation" v-if="tabsList.networkInformation" class="mt20">
                <Title title="网络信息"></Title>
                <p class="pl15">{{ networkInformation }}</p>
            </div>
            <div id="education" v-if="tabsList.education" class="mt20">
                <Title title="教育经历"></Title>
                <p class="pl15" v-html="education"></p>
            </div>
            <div id="work" v-if="tabsList.work" class="mt20">
                <Title title="工作经历"></Title>
                <p class="pl15" v-html="work"></p>
            </div>
            <div id="policy" v-if="tabsList.policy" class="mt20">
                <Title title="政治面貌"></Title>
                <p class="pl15">{{ policy }}</p>
            </div>
            <div id="religion" v-if="tabsList.religion" class="mt20">
                <Title title="宗教信仰"></Title>
                <p class="pl15">{{ religion }}</p>
            </div>
            <div id="professionalInformation" v-if="tabsList.professionalInformation" class="mt20">
                <Title title="专业信息"></Title>
                <p class="pl15">{{ professionalInformation }}</p>
            </div>
            <div id="corpHonor" v-if="tabsList.corpHonor" class="mt20">
                <Title title="荣誉风采"></Title>
                <p class="pl15" v-html="corpHonor"></p>
            </div>
            <div id="member" v-if="tabsList.member">
                <Title title="家庭成员"></Title>
                <p class="pl15" v-html="member"></p>
            </div>
            <div id="product" v-if="tabsList.product">
                <Title title="产出产品"></Title>
                <p class="pl15" v-html="product"></p>
            </div>
            <div id="provide" v-if="tabsList.provide">
                <Title title="提供服务"></Title>
                <p class="pl15" v-html="provide"></p>
            </div>
            <div id="purchase" v-if="tabsList.purchase">
                <Title title="求购信息"></Title>
                <p class="pl15" v-html="purchase"></p>
            </div>
            <div id="asset" v-if="tabsList.asset">
                <Title title="资产信息"></Title>
                <Tag style="margin-left: 15px; margin-top: 10px; margin-bottom: 10px;" checked color="default">房产信息</Tag>
                <p class="pl15" v-html="house"></p>
                <p class="pl15" v-html="placeOfBusiness"></p>
                <Tag style="margin-left: 15px; margin-top: 10px; margin-bottom: 10px;" checked color="default">主要设备</Tag>
                <p class="pl15">{{ modern }}</p>
                <Tag style="margin-left: 15px; margin-top: 10px; margin-bottom: 10px;" checked color="default">生产设施</Tag>
                <p class="pl15" v-html="facilityAssets"></p>
                <Tag style="margin-left: 15px; margin-top: 10px; margin-bottom: 10px;" checked color="default">无形资产</Tag>
                <p class="pl15" v-html="intangibleAssets"></p>
            </div>
            <div id="department" v-if="tabsList.department" class="mt20">
                <Title title="组织机构"></Title>
                <!-- <p class="pl15" v-html="dept"></p> -->
                <Tree class="pl15" :data="dept" :render="renderContent" v-if="dept_status"></Tree>
            </div>
            <div id="team" v-if="tabsList.team" class="mt20">
                <Title title="管理团队"></Title>
                <p class="pl15" v-html="team"></p>
            </div>
            <div id="survey" v-if="tabsList.survey" class="mt20">
                <Title title="经营概况"></Title>
                <p class="pl15">{{ survey }}</p>
            </div>
            <div id="assetFinance" v-if="tabsList.assetFinance" class="mt20">
                <Title title="财务信息"></Title>
                <p class="pl15">{{ assetFinance }}</p>
            </div>
            <div id="productService" v-if="tabsList.productService" class="mt20">
                <Title title="产品&服务"></Title>
                <p class="pl15" v-html="productService"></p>
            </div>
            <div id="qualification" v-if="tabsList.qualification" class="mt20">
                <Title title="专业资质"></Title>
                <p class="pl15" v-html="qualification"></p>
            </div>
            <div id="commonAssets" v-if="tabsList.commonAssets" class="mt20">
                <Title title="普通资产"></Title>
                <p class="pl15" v-html="commonAssets"></p>
            </div>
        </div>
    </div>
</template>
<script>
import Title from '../userAuth/components/title'
export default {
    components: {
        Title
    },
    data () {
        return {
            tabsList: {
                overViewInfo: false,
                privateInformation: false,
                networkInformation: false,
                education: false,
                work: false,
                policy: false,
                religion: false,
                professionalInformation: false,
                corpHonor: false,
                member: false,
                product: false,
                provide: false,
                purchase: false,
                house: false,
                modern: false,
                department: false,
                team: false,
                survey: false,
                assetFinance: false,
                productService: false,
                qualification: false,
                commonAssets: false,
                intangibleAssets: false,
                facilityAssets: false,
                placeOfBusiness: false
            },
            account: '',

            privateInformation: '',
            networkInformation: '',
            education: '',
            work: '',
            policy: '',
            religion: '',
            professionalInformation: '',
            corpHonor: '',
            member: '',
            product: '',
            provide: '',
            purchase: '',
            house: '',
            modern: '',
            dept: [],
            team: '',
            survey: '',
            assetFinance: '',
            productService: '',
            qualification: '',
            commonAssets: '',
            intangibleAssets: '',
            facilityAssets: '',
            placeOfBusiness: '',
            overViewInfo: '',

            dept_status: true
        }
    },
    created(){
        this.account = this.$route.query.uid
        this.queryConfig()
        this.getData()
        this.initDept()
    },
    methods: {
        // 初始化部门数据
        initDept () {
            this.$api.post('/member/perfectInfo/findDepartment', {
                account: this.account
            }).then(response => {
                if (response.code === 200) {
                    this.dept = response.data
                    if (response.data.length !== 0 && response.data[0].depart_status) {
                        this.dept_status = response.data[0].depart_status === '1' ? true : false
                    }
                }
            }).catch(error => {
                this.$Message.error('初始化部门数据错误！')
            })
        },
        renderContent (h, { root, node, data }) {
            return h('span', {
                style: {
                    display: 'inline-block',
                    width: '100%'
                }
            }, [
                h('span', [
                    h('Icon', {
                        props: {
                            type: 'ios-paper-outline'
                        }
                    }),
                    h('span', {
                        style: {
                            marginLeft: '8px',
                            cursor: 'pointer'
                        },
                        on: {
                            click: () => { /* this.show(node) */ }
                        }
                    }, data.title)
                ])
            ]);
        },
        // 查询用户角色配置的表单信息
        queryConfig() {
            this.$api.post('/member/perfectInfo/findSysUserInfo', {
                account: this.account
            }).then(response => {
                if (response.code === 200) {
                    // this.tabsData = response.data.tabsData;
                    // 处理表单是否显示
                    let arr = [];
                    response.data.tabsData.forEach(item => arr.push(item.name));
                    arr.forEach(item => (this.tabsList[item] = true));
                    console.log(this.tabsList);
                }
            }).catch(error => {
                console.log(error);
            });
        },
        // 获取数据
        getData () {
            this.$api.post('/member/perfectInfo/findPerfectInfo', {
                account: this.account
            }).then(response => {
                console.log('response', response)
                if (response.code === 200) {
                    // 这个判断里面写了所有取数据的，但是当此用户类型没有这个模块的时候就不会返回这个key的数据，
                    // 所以要先判断key是否存在，再根据各个模块数据类型的不同判断，里面是否有值，如果有值，就取值了之后，赋值给当前模块
                    var data = response.data;
                    //概览信息 overviewInfo
                    if (data.overViewInfo && data.overViewInfo.length) {
                        this.overViewInfo = data.overViewInfo[0].editor
                    }
                    //隐私信息
                    if (data.privateInformation && Object.keys(data.privateInformation).length) {
                        // 如果有数据说明之前已经保存过，不管是保存的是否是空数据，都已经有数据格式了
                        let datas = data.privateInformation
                        if (datas.birthday.model) {                    
                            datas.birthday.model = this.moment(datas.birthday.model).format("YYYY-MM-DD")
                        } else {
                            datas.birthday.model = ''
                        }
                        let privateInformation = ''
                        for (let key in datas) {
                            if (datas[key] && datas[key].model && datas[key].status) {
                                privateInformation += `${datas[key].name}：${datas[key].model}，`
                            }
                        }
                        if (privateInformation.length !== 0) {
                            privateInformation = privateInformation.substring(0,privateInformation.length-1)+'。'
                        }
                        this.privateInformation = privateInformation
                    }
                    //网络信息networkInformation
                    if (data.networkInformation && Object.keys(data.networkInformation).length) {
                        let data2 = data.networkInformation
                        let networkInformation = ''
                        if (data2.status) {
                            for (let key in data2) {
                                if (data2[key] && data2[key].model) {                        
                                    networkInformation += `${data2[key].name}：${data2[key].model}，`
                                }
                            }
                        }
                        if (networkInformation.length !== 0) {
                            networkInformation = networkInformation.substring(0, networkInformation.length - 1) + '。'
                        }
                        this.networkInformation = networkInformation
                    }
                    //教育经历 education
                    if (data.education && data.education.length) {
                        let education = ''
                        data.education.forEach(element => {
                            let formItem = element
                            let content = ''
                            for (let key in formItem) {
                                if (formItem[key] && formItem[key].model && formItem[key].status) {
                                    if (formItem[key].name == '入学/毕业时间') {
                                        var starTime = formItem[key].model[0]
                                        var endTime = formItem[key].model[1]
                                        if(starTime && endTime){
                                            content += `${formItem[key].name}：${this.moment(starTime).format('YYYY/MM/DD')}-${this.moment(endTime).format('YYYY/MM/DD')}，`
                                        }
                                    } else {
                                        content += `${formItem[key].name}：${formItem[key].model}，`
                                    }
                                }
                            }
                            content = content.substring(0,content.length-1)+'。'
                            education += content + '<br/>'
                        })
                        this.education = education
                    }
                    //工作经历 work
                    if (data.work && data.work.length) {
                        let work = ''
                        data.work.forEach(element => {
                            let formItem = element
                            let content = ''
                            for (let key in formItem) {
                                if (formItem[key] && formItem[key].model && formItem[key].status) {
                                    if (formItem[key].name === '工作时间') {
                                        var starTime = formItem[key].model[0]
                                        var endTime = formItem[key].model[1]
                                        if(starTime && endTime){
                                            content += `${formItem[key].name}：${this.moment(starTime).format('YYYY/MM/DD')}-${this.moment(endTime).format('YYYY/MM/DD')}，`
                                        }
                                    } else {
                                        content += `${formItem[key].name}：${formItem[key].model}，`
                                    }
                                }
                            }
                            content = content.substring(0,content.length-1)+'。'
                            work += content + '<br/>'
                        })
                        this.work = work
                    }
                    //政治面貌 policyInfo
                    if (data.policy && Object.keys(data.policy).length) {
                        let policy = ''
                        let newPolicy = data.policy
                        if (newPolicy.joinTime.status && newPolicy.joinTime.model !== '') {
                            policy += newPolicy.joinTime.model
                        }
                        if (newPolicy.policy.model && newPolicy.joinTime.model && newPolicy.joinTime.status && newPolicy.policy.status) {
                            policy += '加入'
                        }
                        if (newPolicy.policy.status) {
                            policy += newPolicy.policy.model
                        }
                        if (policy.length !== 0) {
                            policy += '。'
                        }
                        this.policy = policy
                    }
                    //宗教信仰
                    if (data.religion && Object.keys(data.religion).length) {
                        let religion = ''
                        let newReligion = data.religion
                        if (newReligion.religion.model && newReligion.religion.status) {
                            religion += '信仰'
                        }
                        if (newReligion.religion.status) {
                            religion += newReligion.religion.model
                        }
                        if (religion.length !== 0) {
                            religion += '。'
                        }
                        this.religion = religion
                    }
                    //专业信息 professionalInformation
                    if (data.professionalInformation && Object.keys(data.professionalInformation).length) {
                        let data3 = data.professionalInformation
                        let professionalInformation = ''
                        if (data3.expert_status) {
                            if (data3.expertType !== '') {
                                professionalInformation += `专家分类：${data3.expertType}，`
                            }
                            if (data3.excelField !== '') {
                                professionalInformation += `擅长领域：${data3.excelField}，`
                            }
                            if (data3.excelSpecies !== '') {
                                professionalInformation += `擅长物种：${data3.excelSpecies}，`
                            }
                            if (data3.relatedSpecies !== '') {
                                professionalInformation += `相关物种：${data3.relatedSpecies}，`
                            }
                            if (data3.relatedIndustries !== '') {
                                professionalInformation += `相关行业：${data3.relatedIndustries}，`
                            }
                            if (data3.relatedGoods !== '') {
                                professionalInformation += `相关商品：${data3.relatedGoods}，`
                            }
                            if (data3.relatedServices !== '') {
                                professionalInformation += `相关服务：${data3.relatedServices}，`
                            }
                            if (professionalInformation.length !== 0) {
                                professionalInformation = professionalInformation.substring(0, professionalInformation.length - 1) + '。'
                            }
                        }
                        this.professionalInformation = professionalInformation
                    }
                    //荣誉风采 corpHonor
                    if (data.corpHonor && data.corpHonor.length) {
                        let corpHonor = ''
                        data.corpHonor.forEach(element => {
                            if (element.honour_status) {
                                if (element.name !== '') {
                                    corpHonor += `标题：${element.name}，`
                                }
                                if (element.content !== '') {
                                    corpHonor += `简介：${element.content}，`
                                }
                                corpHonor = corpHonor.substring(0,corpHonor.length-1) + '。<br/>'
                            }
                        })
                        this.corpHonor = corpHonor
                    }
                    //家庭成员 member
                    if (data.member && data.member.length) {
                        let member = ''
                        data.member.forEach(element => {
                            if (element.family_status) {
                                if (element.name !== '') {
                                    member += `成员名称：${element.name}，`
                                }
                                if (element.relationship !== '') {
                                    member += `与户主关系：${element.relationship}，`
                                }
                                if (element.sex !== '') {
                                    member += `性别：${element.sex}，`
                                }
                                if (element.birthday !== '') {
                                    member += `出生日期：${this.moment(element.birthday).format("YYYY-MM-DD")}，`
                                }
                                if (element.phone !== '') {
                                    member += `手机号码：${element.phone}，`
                                }
                                if (element.skill !== '') {
                                    member += `劳动技能：${element.skill}，`
                                }
                                if (member.length !== 0) {
                                    member = member.substring(0,member.length-1) + '。<br/>'
                                }
                            }
                            this.member = member
                        })
                    }
                    //产出产品 product
                    if (data.product && data.product.length) {
                        let product = ''
                        data.product.forEach(element => {
                            if (element.product_status) {
                                if (element.species !== '') {
                                    product += `物种：${element.species}，`
                                }
                                if (element.scale !== '') {
                                    product += `生产规模：${element.scale}，`
                                }
                                if (element.commodityName !== '') {
                                    product += `通用商品名：${element.commodityName}，`
                                }
                                if (element.productName !== '') {
                                    product += `产品名称：${element.productName}，`
                                }
                                if (element.product !== '') {
                                    product += `产品产量：${element.product}，`
                                }
                                if (element.conversion !== '') {
                                    product += `折算产量：${element.conversion}，`
                                }
                                if (product.length !== 0) {
                                    product = product.substring(0,product.length-1) + '。<br/>'
                                }
                            }
                            this.product = product
                        })
                    }
                    //提供服务 provide
                    if (data.provide && data.provide.length) {
                        let provide = ''
                        data.provide.forEach(element => {
                            if (element.provide_status) {
                                if (element.serviceName !== '') {
                                    provide += `服务名称：${element.serviceName}，`
                                }
                                if (element.classification !== '') {
                                    provide += `服务分类：${element.classification}，`
                                }
                                if (element.createTimes !== '') {
                                    provide += `创建时间：${this.moment(element.createTimes).format("YYYY-MM-DD")}，`
                                }
                                if (provide.length !== 0) {
                                    provide = provide.substring(0,provide.length-1) + '。<br/>'
                                }
                            }
                            this.provide = provide
                        })
                    }
                    //求购信息 purchase
                    if (data.purchase && data.purchase.length) {
                        let purchase = ''
                        data.purchase.forEach(element => {
                            if (element.purchase_status) {
                                if (element.name !== '') {
                                    purchase += `通用商品名：${element.name}，`
                                }
                                if (element.productName !== '') {
                                    purchase += `产品名称：${element.productName}，`
                                }
                                if (element.total !== '') {
                                    purchase += `产品数量：${element.total}，`
                                }
                                if (element.price !== '') {
                                    purchase += `产品单价：${element.price}元，`
                                }
                                if (element.totalAmount !== '') {
                                    purchase += `金额：${element.totalAmount}元，`
                                }
                                if (purchase.length !== 0) {
                                    purchase = purchase.substring(0,purchase.length-1) + '。<br/>'
                                }
                            }
                            this.purchase = purchase
                        })
                    }
                    //房屋生活情况 house
                    if (data.house && data.house.length) {
                        let house = ''
                        data.house.forEach(element => {
                            if (element.status) {
                                if (element.name !== '') {
                                    house += `房主姓名：${element.name}，`
                                }
                                if (element.addr !== '') {
                                    house += `房屋地址：${element.addr}，`
                                }
                                if (element.addrDetail !== '') {
                                    house += `详细地址：${element.addrDetail}，`
                                }
                                if (element.buildingArea !== '') {
                                    house += `建筑面积：${element.buildingArea}平方米，`
                                }
                                if (element.useArea !== '') {
                                    house += `土地使用面积：${element.useArea}平方米，`
                                }
                                if (element.certificate !== '') {
                                    house += `是否办证：${element.certificate}，`
                                }
                                if (element.certificate === '是') {
                                    if (element.houseNumber !== '') {
                                    house += `房屋所有权证编号：${element.houseNumber}，`
                                    }
                                    if (element.landNumber !== '') {
                                    house += `土地使用证编号：${element.landNumber}，`
                                    }
                                    if (element.estate !== '') {
                                    house += `不动产权证编号：${element.estate}，`
                                    }
                                } else {
                                    if (element.reason !== '') {
                                    house += `未办证原因：${element.reason}，`
                                    }
                                }
                                if (element.structure !== '') {
                                    house += `房屋结构：${element.structure}，`
                                }
                                if (element.distance !== '') {
                                    house += `房屋与乡村公路的距离：${element.distance}米，`
                                }
                                if (element.land !== '') {
                                    house += `所在地理位置：${element.land}，`
                                }
                                if (element.purpose !== '') {
                                    house += `房屋用途：${element.purpose}，`
                                }
                                if (element.development !== '') {
                                    house += `房屋建设情况描述：${element.development}，`
                                }
                                if (element.waterSource !== '') {
                                    house += `饮水来源：${element.waterSource}，`
                                }
                                if (element.waterHard !== '') {
                                    house += `饮水是否困难：${element.waterHard}，`
                                }
                                if (element.biogasPool !== '') {
                                    house += `沼气池：${element.biogasPool}，`
                                }
                                if (element.pool !== '') {
                                    house += `一池三改：${element.pool}，`
                                }
                                if (element.gas !== '') {
                                    house += `天然气：${element.gas}，`
                                }
                                if (element.communicationQuality !== '') {
                                    house += `通电质量：${element.communicationQuality}，`
                                }
                                if (element.broadband !== '') {
                                    house += `宽带网：${element.broadband}，`
                                }
                                if (element.tcSignal !== '') {
                                    house += `电视信号：${element.tcSignal}，`
                                }
                                if (element.network !== '') {
                                    house += `电信网络：${element.network}，`
                                }
                                house = house.substring(0,house.length-1) + '。<br/>'
                            }
                        })
                        this.house = house
                    }
                    //家庭现代化情况  modern
                    if (data.modern && data.modern.length) {
                        let modern = ''
                        data.modern.forEach(element => {
                            if (element.family_modern_status) {
                                if (element.tv !== '') {
                                    modern += `电视机数量：${element.tv}台，`
                                }
                                if (element.computer !== '') {
                                    modern += `电脑数量：${element.computer}台，`
                                }
                                if (element.icebox !== '') {
                                    modern += `冰箱数量：${element.icebox}台，`
                                }
                                if (element.ari !== '') {
                                    modern += `空调数量：${element.ari}台，`
                                }
                                if (element.car !== '') {
                                    modern += `汽车数量：${element.car}辆，`
                                }
                                if (element.motorcycle !== '') {
                                    modern += `摩托车数量：${element.motorcycle}辆，`
                                }
                                if (element.heater !== '') {
                                    modern += `太阳能热水器数量：${element.heater}台，`
                                }
                                if (modern.length !== 0) {
                                    modern = modern.substring(0,modern.length-1) + '。'
                                }
                            }
                        })
                        this.modern = modern
                    }
                    //产品服务 productService
                    if (data.productService && data.productService.length) {
                        let productService = ''
                        data.productService.forEach(element => {
                            if (element.product_status) {
                                if (element.category !== '') {
                                    productService += `类型：${element.category}，`
                                }
                                if (element.product !== '') {
                                    productService += `三品一标：${element.product}，`
                                }
                                if (element.name !== '') {
                                    productService += `名称：${element.name}，`
                                }
                                if (element.relatedSpecies !== '') {
                                    productService += `关联物种：${element.relatedSpecies}，`
                                }
                                if (element.brand !== '') {
                                    productService += `品牌：${element.brand}，`
                                }
                                if (element.introduction !== '') {
                                    productService += `简介：${element.introduction}，`
                                }
                                productService = productService.substring(0,productService.length-1) + '。<br/>'
                            }
                        })
                        this.productService = productService
                    }
                    //专业资质 qualification
                    if (data.qualification && data.qualification.length) {
                        let qualification = ''
                        data.qualification.forEach(element => {
                            if (element.professional_status) {
                                if (element.name !== '') {
                                    qualification += `资质名称：${element.name}，`
                                }
                                if (element.content !== '') {
                                    qualification += `简介：${element.content}，`
                                }
                                qualification = qualification.substring(0,qualification.length-1) + '。<br/>'
                            }
                        })
                        this.qualification = qualification
                    }
                    // 普通资产
                    if (data.commonAssets && data.commonAssets.length) {
                        let commonAssets = ''
                        data.commonAssets.forEach(element => {
                            if (element.common_status) {
                                if (element.name !== '') {
                                    commonAssets += `资产名称：${element.name}，`
                                }
                                if (element.valuation !== '') {
                                    commonAssets += `资产估值：${element.valuation}万元，`
                                }
                                if (element.assetsExplain !== '') {
                                    commonAssets += `资产说明：${element.assetsExplain}，`
                                }
                                commonAssets = commonAssets.substring(0,commonAssets.length-1) + '。<br/>'
                            }
                        })
                        this.commonAssets = commonAssets
                    }
                    // 无形资产
                    if (data.intangibleAssets && data.intangibleAssets.length) {
                        let intangibleAssets = ''
                        data.intangibleAssets.forEach(element => {
                            if (element.assets_status) {
                                if (element.name !== '') {
                                    intangibleAssets += `资产名称：${element.name}，`
                                }
                                if (element.assetsType !== '') {
                                    intangibleAssets += `资产类型：${element.assetsType}，`
                                }
                                if (element.assetsType === '知识产权' && element.number !== '') {
                                    intangibleAssets += `知识产权编号：${element.number}，`
                                }
                                if (element.valuation !== '') {
                                    intangibleAssets += `资产估值：${element.valuation}万元，`
                                }
                                if (element.assetsExplain !== '') {
                                    intangibleAssets += `资产说明：${element.assetsExplain}，`
                                }
                                intangibleAssets = intangibleAssets.substring(0,intangibleAssets.length-1) + '。<br/>'
                            }
                        })
                        this.intangibleAssets = intangibleAssets
                    }
                    // 设施资产
                    if (data.facilityAssets && data.facilityAssets.length) {
                        let facilityAssets = ''
                        data.facilityAssets.forEach(element => {
                            if (element.assets_status) {
                                if (element.name !== '') {
                                    facilityAssets += `资产名称：${element.name}，`
                                }
                                if (element.model !== '') {
                                    facilityAssets += `资产类型：${element.model}，`
                                }
                                if (element.assetsType !== '') {
                                    facilityAssets += `品牌型号：${element.assetsType}，`
                                }
                                if (element.purchaseTime !== '') {
                                    facilityAssets += `采购时间：${this.moment(element.purchaseTime).format("YYYY-MM-DD")}，`
                                }
                                if (element.originalValue !== '') {
                                    facilityAssets += `资产原值：${element.originalValue}万元，`
                                }
                                if (element.depreciation !== '') {
                                    facilityAssets += `年折旧率：${element.depreciation}%，`
                                }
                                if (element.netAssetValue !== '') {
                                    facilityAssets += `资产净值：${element.netAssetValue}万元，`
                                }
                                if (element.assetsExplain !== '') {
                                    facilityAssets += `资产说明：${element.assetsExplain}，`
                                }
                                facilityAssets = facilityAssets.substring(0,facilityAssets.length-1) + '。<br/>'
                            }
                        })
                        this.facilityAssets = facilityAssets
                    }
                    // 经营场所
                    if (data.placeOfBusiness && data.placeOfBusiness.length) {
                        let placeOfBusiness = ''
                        data.placeOfBusiness.forEach(element => {
                            if (element.assets_status) {
                                if (element.place !== '') {
                                    placeOfBusiness += `处所：${element.place}，`
                                }
                                if (element.rightType !== '') {
                                    placeOfBusiness += `权利类型：${element.rightType}，`
                                }
                                if (element.area !== '') {
                                    placeOfBusiness += `场所面积：${element.area}平方米，`
                                }
                                if (element.purpose !== '') {
                                    placeOfBusiness += `用途：${element.purpose}，`
                                }
                                if (element.rightType === '自购' && element.certificateNumber !== '') {
                                    placeOfBusiness += `不动产证号：${element.certificateNumber}，`
                                }
                                if (element.valuation !== '') {
                                    placeOfBusiness += `资产估值：${element.valuation}万元，`
                                }
                                if (element.assetsExplain !== '') {
                                    placeOfBusiness += `资产说明：${element.assetsExplain}，`
                                }
                                placeOfBusiness = placeOfBusiness.substring(0,placeOfBusiness.length-1) + '。<br/>'
                            }
                        })
                        this.placeOfBusiness = placeOfBusiness
                    }
                    //团队管理  team
                    if (data.team && data.team.length) {
                        let team = ''
                        data.team.forEach(element => {
                            if (element.team_status) {
                                if (element.job !== '') {
                                    team += `职务：${element.job}，`
                                }
                                if (element.name !== '') {
                                    team += `姓名：${element.name}，`
                                }
                                if (element.educate !== '') {
                                    team += `学历：${element.educate}，`
                                }
                                if (element.idCard !== '') {
                                    team += `身份证：${element.idCard}，`
                                }
                                if (element.phone !== '') {
                                    team += `手机号：${element.phone}，`
                                }
                                if (element.intro !== '') {
                                    team += `简介：${element.intro}，`
                                }
                                if (team.length !== 0) {
                                    team = team.substring(0,team.length-1) + '。<br/>'
                                }
                            }
                        })
                        this.team = team
                    }
                    //survey 经营概况
                    if (data.survey && Object.keys(data.survey).length) {
                        let element = data.survey
                        let survey = ''
                        if (element.manage_status) {
                            if (element.scale !== '') {
                                survey += `企业规模：${element.scale}，`
                            }
                            if (element.industry !== '') {
                                survey += `所属行业：${element.industry}，`
                            }
                            if (element.turnover !== '') {
                                survey += `上年度营业收入：${element.turnover}万元，`
                            }
                            if (element.JointStockCode !== '') {
                                survey += `股份代码：${element.JointStockCode}，`
                            }
                            if (survey.length !== 0) {
                                survey = survey.substring(0,survey.length-1) + '。'
                            }
                        }
                        this.survey = survey
                    }
                    //finance 财务管理
                    if (data.assetFinance && Object.keys(data.assetFinance).length) {
                        let element = data.assetFinance
                        let assetFinance = ''
                        if (element.finance_status) {
                            if (element.interests !== '') {
                                assetFinance += `所有者权益：${element.interests}万元，`
                            }
                            if (element.assets !== '') {
                                assetFinance += `固定资产规模：${element.assets}万元，`
                            }
                            if (assetFinance.length !== 0) {
                                assetFinance = assetFinance.substring(0,assetFinance.length-1) + '。'
                            }
                        }
                        this.assetFinance = assetFinance
                    }
                }
            })
        }
    }
}
</script>
<style lang="scss">
.briefInex{margin: 60px 0;line-height: 30px;}
.brief-content {
    padding-left: 20px;
    padding-right: 20px;
    padding-bottom: 20px;
}
.ma-polic-img{margin-top: 0;}
</style>
