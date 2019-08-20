<template>
  <div class="company-auth-5 mt5">
    <vui-affix-tabs :data="tabsData">
       <div class="pd20">
          <section id="privateInformation" v-if="tabsList.privateInformation">
              <Title title="隐私信息"></Title>
              <private-information ref="privateInformation" @on-submit="handleGetSubmit"></private-information>
          </section>
          <section id="networkInformation" v-if="tabsList.networkInformation">
              <Title title="网络信息"></Title>
              <network-information ref="networkInformation" @on-submit="handleGetSubmit"></network-information>
          </section>
          <section id="education" v-if="tabsList.education">
              <Title title="教育经历"></Title>
              <education ref="education" @on-submit="handleGetSubmit"></education>
          </section>
          <section id="work" v-if="tabsList.work">
              <Title title="工作经历"></Title>
              <work ref="work" @on-submit="handleGetSubmit"></work>
          </section>
          <section id="policy" v-if="tabsList.policy">
              <Title title="政治面貌"></Title>
              <policyInfo ref="policy" @on-submit="handleGetSubmit"></policyInfo>
          </section>
          <section id="religion" v-if="tabsList.religion">
              <Title title="宗教信仰"></Title>
              <religionInfo ref="religion" @on-submit="handleGetSubmit"></religionInfo>
          </section>
          <section id="professionalInformation" v-if="tabsList.professionalInformation">
              <Title title="专业信息"></Title>
              <professional-information ref="professionalInformation" @on-submit="handleGetSubmit"></professional-information>
          </section>
          <section id="corpHonor" v-if="tabsList.corpHonor">
              <Title title="荣誉风采"></Title>
              <corp-honor ref="corpHonor" @on-submit="handleGetSubmit"></corp-honor>
          </section>
          <section id="member" v-if="tabsList.member">
              <Title title="家庭成员"></Title>
              <family-detail ref="member" @on-submit="handleGetSubmit" ></family-detail>  
          </section>
          <section id="product" v-if="tabsList.product">
              <Title title="产出产品"></Title>
              <product-detail ref="product" @on-submit="handleGetSubmit"></product-detail>
          </section>
          <section id="provide" v-if="tabsList.provide">
              <Title title="提供服务"></Title>
              <service-detail ref="provide" @on-submit="handleGetSubmit" ></service-detail>
          </section>
          <section id="purchase" v-if="tabsList.purchase">
              <Title title="求购信息"></Title>
              <want-to-buy ref="purchase" @on-submit="handleGetSubmit"></want-to-buy>
          </section>
          <section id="asset"  v-if="tabsList.asset">
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
          <section id="department" v-if="tabsList.department">
              <Title title="组织机构"></Title>
              <department ref="department"></department>
          </section>
          <section id="team" v-if="tabsList.team">
              <Title title="管理团队" class="mt20"></Title>
              <team ref="team" @on-submit="handleGetSubmit"></team>
          </section>
          <section id="survey" v-if="tabsList.survey">
              <Title title="经营概况"></Title>
              <survey ref="survey" @on-submit="handleGetSubmit"></survey>
          </section>
          <section id="assetFinance"  v-if="tabsList.assetFinance">
              <Title title="财务信息"></Title>
              <asset-finance ref="assetFinance" @on-submit="handleGetSubmit"></asset-finance>
          </section>
          <section id="productService" v-if="tabsList.productService">
              <Title title="产品&服务"></Title>
              <product-service ref="productService" @on-submit="handleGetSubmit"></product-service>
          </section>
          <section id="qualification" v-if="tabsList.qualification">
              <Title title="专业资质"></Title>
              <qualification ref="qualification" @on-submit="handleGetSubmit"></qualification>
          </section>
            <section id="commonAssets"  v-if="tabsList.commonAssets">
                <Title title="普通资产"></Title>
                <common-assets ref="commonAssets" @on-submit="handleGetSubmit"></common-assets>
            </section>
            <section id="overViewInfo"   v-if="tabsList.overViewInfo">
                <Title title="概览信息"></Title>
                <editor ref="overViewInfo" @on-submit="handleGetSubmit"></editor>
            </section>
       </div>
       <div class="tc pd20">
         <Button type="primary" id="markup-1" @click="handleClickBack">上一步</Button>
         <Button type="primary" id="markup-1" @click="handleClicNext">下一步</Button>
         <Button type="text" id="markup-1" @click="handleJump">跳过</Button>
       </div>
    </vui-affix-tabs>
      <Button type="warning" :style="style" @click="handlePreview">预<br>览</Button>
      <preview ref="preview" :tabsList="tabsList"></preview>
  </div>
</template>
<script>
import Title from './title'
import VuiAffixTabs from './vui-affix-tabs'
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
import house from '../../farmFamily/house'
import modern from '../../farmFamily/modern'
import wantToBuy from '../../farmFamily/wantToBuy'
import serviceDetail from '../../farmFamily/serviceDetail'
import productDetail from '../../farmFamily/productDetail'
import familyDetail from '../../farmFamily/familyDetail'
import department from './department'
import commonAssets from './commonAssets'
import intangibleAssets from './intangibleAssets'
import facilityAssets from './facilityAssets'
import placeOfBusiness from './placeOfBusiness'
import preview from './preview'
import editor from './editor'

export default {
  components: {
    VuiAffixTabs,
    Title,
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
    preview,
    editor,
    wantToBuy,
    familyDetail,
    productDetail,
    serviceDetail
  },
  data() {
    return {
      account: '',
      isNext: true,
      showTab: [],
      tabsData: [],
      tabsList: {},
      style: {},
      loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
      dept: ''
    }
  },
  created() {
    this.account = this.loginUser.loginAccount
    this.getModel()
    this.queryConfig()
  },
  methods: {
    // 取按钮位置
    getPosition () {
      return {
        position: 'fixed',
        top: `${(window.innerHeight - 170) / 2}px`,
        left: `${(window.innerWidth - 1200) / 2 + 1200 - 8}px`,
        padding: '10px'
      }
    },
     //点击预览
    handlePreview(){
      // 隐私信息预览
      if (this.$refs['privateInformation']) {
        let data = this.$refs['privateInformation'].data
        if (data.birthday.model) {                    
          data.birthday.model = this.moment(data.birthday.model).format("YYYY-MM-DD")
        } else {
          data.birthday.model = ''
        }
        let privateInformation = ''
        for (let key in data) {
          if (data[key] && data[key].model) {
            privateInformation += `${data[key].name}：${data[key].model}，`
          }
        }
        if (privateInformation.length !== 0) {
          privateInformation = privateInformation.substring(0,privateInformation.length-1)+'。'
        }
        this.$refs['preview'].privateInformation = privateInformation
      }
      // 网络信息预览
      if (this.$refs['networkInformation']) {
        let data2 = this.$refs['networkInformation'].data
        let networkInformation = ''
        for(let key in data2){
          if(data2[key] && data2[key].model){                        
            networkInformation +=  `${data2[key].name}：${data2[key].model}，`
          }
        }
        if (networkInformation.length !== 0) {
          networkInformation = networkInformation.substring(0, networkInformation.length - 1) + '。'
        }
        this.$refs['preview'].networkInformation = networkInformation
      }
      // 教育信息预览
      if (this.$refs['education']) {
        let education = ''
        this.$refs['education'].data.forEach(element => {
          let formItem = element
          let content = ''
          for(let key in formItem){
              if(formItem[key] && formItem[key].model){
                  if(formItem[key].name == '入学/毕业时间'){
                      var starTime = formItem[key].model[0]
                      var endTime = formItem[key].model[1]
                      if(starTime && endTime){
                          content += `${formItem[key].name}：${this.moment(starTime).format('YYYY/MM/DD')}-${this.moment(endTime).format('YYYY/MM/DD')}，`
                      }
                  }else{
                      content += `${formItem[key].name}：${formItem[key].model}，`
                  }
              }
          }
          content = content.substring(0,content.length-1)+'。'
          education += content + '<br/>'
        })
        this.$refs['preview'].education = education
      }
      // 工作经历预览
      if (this.$refs['work']) {
        let work = ''
        this.$refs['work'].data.forEach(element => {
          let formItem = element
          let content = ''
          for(let key in formItem){
              if(formItem[key] && formItem[key].model){
                  if(formItem[key].name == '工作时间'){
                      var starTime = formItem[key].model[0]
                      var endTime = formItem[key].model[1]
                      if(starTime && endTime){
                          content += `${formItem[key].name}：${this.moment(starTime).format('YYYY/MM/DD')}-${this.moment(endTime).format('YYYY/MM/DD')}，`
                      }
                  }else{
                      content += `${formItem[key].name}：${formItem[key].model}，`
                  }
              }
          }
          content = content.substring(0,content.length-1)+'。'
          work += content + '<br/>'
        })
        this.$refs['preview'].work = work
      }
      // 政治面貌预览
      if (this.$refs['policy']) {
        let policy = ''
        let newPolicy = this.$refs['policy'].data
        if (newPolicy.joinTime.model !== '') {
          policy += this.moment(newPolicy.joinTime.model).format('YYYY年MM月')
        }
        if (newPolicy.policy.model && newPolicy.joinTime.model) {
          policy += '加入'
        }
        policy += newPolicy.policy.model
        if (policy.length !== 0) {
          policy += '。'
        }
        this.$refs['preview'].policy = policy
      }
      // 宗教信仰预览
      if (this.$refs['religion']) {
        let religion = ''
        let newReligion = this.$refs['religion'].data
        if (newReligion.religion.model) {
            religion += '信仰'
        }
        religion += newReligion.religion.model
        if (religion.length !== 0) {
          religion += '。'
        }
        this.$refs['preview'].religion = religion
      }
      // 专业信息预览
      if (this.$refs['professionalInformation']) {
        let data3 = this.$refs['professionalInformation'].data
        let professionalInformation = ''
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
        this.$refs['preview'].professionalInformation = professionalInformation
      }
      // 荣誉风采预览
      if (this.$refs['corpHonor']) {
        let corpHonor = ''
        this.$refs['corpHonor'].data.forEach(element => {
          if (element.name !== '') {
            corpHonor += `标题：${element.name}，`
          }
          if (element.content !== '') {
            corpHonor += `简介：${element.content}，`
          }
          corpHonor = corpHonor.substring(0,corpHonor.length-1) + '。<br/>'
        })
        this.$refs['preview'].corpHonor = corpHonor
      }
      // 家庭成员
      if (this.$refs['member']) {
        let member = ''
        this.$refs['member'].data.forEach(element => {
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
          this.$refs['preview'].member = member
        })
      }
      // 产出产品
      if (this.$refs['product']) {
        let product = ''
        this.$refs['product'].data.forEach(element => {
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
          this.$refs['preview'].product = product
        })
      }
      // 提供服务
      if (this.$refs['provide']) {
        let provide = ''
        this.$refs['provide'].data.forEach(element => {
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
          this.$refs['preview'].provide = provide
        })
      }
      // 求购信息
      if (this.$refs['purchase']) {
        let purchase = ''
        this.$refs['purchase'].data.forEach(element => {
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
            purchase += `产品单价：${element.price}，`
          }
          if (element.totalAmount !== '') {
            purchase += `金额：${element.totalAmount}元，`
          }
          if (purchase.length !== 0) {
            purchase = purchase.substring(0,purchase.length-1) + '。<br/>'
          }
          this.$refs['preview'].purchase = purchase
        })
      }

      // 资产信息
        // 房产信息
          // 房屋生活情况预览
          console.log('111')
          if (this.$refs['house']) {
            console.log('222')
            let house = ''
            this.$refs['house'].data.forEach(element => {
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
            })
            console.log('house', house)
            this.$refs['preview'].house = house
          }
          // 经营场所
          if (this.$refs['placeOfBusiness']) {
            let placeOfBusiness = ''
            this.$refs['placeOfBusiness'].data.forEach(element => {
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
                placeOfBusiness += `资产估值：${element.valuation}，`
              }
              if (element.assetsExplain !== '') {
                placeOfBusiness += `资产说明：${element.assetsExplain}，`
              }
              placeOfBusiness = placeOfBusiness.substring(0,placeOfBusiness.length-1) + '。<br/>'
            })
            this.$refs['preview'].placeOfBusiness = placeOfBusiness
          }
        // 主要设备
        if (this.$refs['modern']) {
          let modern = ''
          this.$refs['modern'].data.forEach(element => {
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
          })
          this.$refs['preview'].modern = modern
        }
        // 生产设施
        if (this.$refs['facilityAssets']) {
          let facilityAssets = ''
          this.$refs['facilityAssets'].data.forEach(element => {
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
              facilityAssets += `资产原值：${element.originalValue}，`
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
          })
          console.log('facilityAssets', facilityAssets)
          this.$refs['preview'].facilityAssets = facilityAssets
        }
        // 无形资产
        if (this.$refs['intangibleAssets']) {
          let intangibleAssets = ''
          this.$refs['intangibleAssets'].data.forEach(element => {
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
          })
          this.$refs['preview'].intangibleAssets = intangibleAssets
        }

      // 组织机构预览
      if (this.$refs['department']) {
        // this.dept = ''
        // this.getDept(this.$refs['department'].data5)
        this.$refs['preview'].dept = this.$refs['department'].data5
      }
      // 经营概况预览
      if (this.$refs['survey']) {
        let element = this.$refs['survey'].data
        let survey = ''
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
        this.$refs['preview'].survey = survey
      }
      // 财务信息预览
      if (this.$refs['assetFinance']) {
        let element = this.$refs['assetFinance'].data
        let assetFinance = ''
        if (element.interests !== '') {
          assetFinance += `所有者权益：${element.interests}万元，`
        }
        if (element.assets !== '') {
          assetFinance += `固定资产规模：${element.assets}万元，`
        }
        if (assetFinance.length !== 0) {
          assetFinance = assetFinance.substring(0,assetFinance.length-1) + '。'
        }
        this.$refs['preview'].assetFinance = assetFinance
      }
      // 管理团队预览
      if (this.$refs['team']) {
        let team = ''
        this.$refs['team'].data.forEach(element => {
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
        })
        this.$refs['preview'].team = team
      }
      // 产品服务预览
      if (this.$refs['productService']) {
        let productService = ''
        this.$refs['productService'].data.forEach(element => {
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
        })
        this.$refs['preview'].productService = productService
      }
      // 专业资质
      if (this.$refs['qualification']) {
        let qualification = ''
        this.$refs['qualification'].data.forEach(element => {
          if (element.name !== '') {
            qualification += `资质名称：${element.name}，`
          }
          if (element.content !== '') {
            qualification += `简介：${element.content}，`
          }
          qualification = qualification.substring(0,qualification.length-1) + '。<br/>'
        })
        this.$refs['preview'].qualification = qualification
      }
      // 普通资产
      if (this.$refs['commonAssets']) {
        let commonAssets = ''
        this.$refs['commonAssets'].data.forEach(element => {
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
        })
        this.$refs['preview'].commonAssets = commonAssets
      }
      // 概览信息
      if (this.$refs['overViewInfo']) {
        let overViewInfo = ''
        this.$refs['overViewInfo'].data.forEach(element => {
          if (element.editor !== '') {
            overViewInfo += `${element.editor}`
          }
        })
        this.$refs['preview'].overViewInfo = overViewInfo
      }
      this.$refs['preview'].more = true
    },
    // 递归取部门数据
    getDept (arr) {
      arr.forEach(element => {
        if (element.title) {
          this.dept += `部门：${element.title}，`
        }
        if (element.leader) {
          this.dept += `负责人：${element.leader}，`
        }
        if (element.phone) {
          this.dept += `联系电话：${element.phone}，`
        }
        if (element.title) {
          this.dept += `职能简介：${element.introduce}，`
        }
        if (this.dept.length !== 0) {
          this.dept = this.dept.substring(0,this.dept.length-1) + '。<br/>'
        }
        if (element.children) {
          return this.getDept(element.children)
        }
      })
    },
    // 初始化获取数据
    getModel() {
      this.$api
        .post('/member/perfectInfo/findPerfectInfo ', { account: this.account })
        .then(response => {
          console.log('response asset', response)
          if (response.code == 200) {
            // 这个判断里面写了所有取数据的，但是当此用户类型没有这个模块的时候就不会返回这个key的数据，
            // 所以要先判断key是否存在，再根据各个模块数据类型的不同判断，里面是否有值，如果有值，就取值了之后，赋值给当前模块
            var data = response.data;
            //隐私信息
            if (
              data.privateInformation &&
              Object.keys(data.privateInformation).length &&
              this.$refs['privateInformation']
            ) {
              // 如果有数据说明之前已经保存过，不管是保存的是否是空数据，都已经有数据格式了
              this.$refs['privateInformation'].getData(data.privateInformation);
            }
            //网络信息networkInformation
            if (
              data.networkInformation &&
              Object.keys(data.networkInformation).length &&
              this.$refs['networkInformation']
            ) {
              this.$refs['networkInformation'].getData(data.networkInformation);
            }
            //教育经历 education
            if (
              data.education &&
              data.education.length &&
              this.$refs['education']
            ) {
              this.$refs['education'].getData(data.education);
            }
            //工作经历 work
            if (data.work && data.work.length && this.$refs['work']) {
              this.$refs['work'].getData(data.work);
            }
            //政治面貌 policyInfo
            if (
              data.policy &&
              Object.keys(data.policy).length &&
              this.$refs['policy']
            ) {
              this.$refs['policy'].getData(data.policy);
            }
            //宗教信仰
            if (
              data.religion &&
              Object.keys(data.religion).length &&
              this.$refs['religion']
            ) {
              this.$refs['religion'].getData(data.religion);
            }
            //专业信息 professionalInformation
            if (
              data.professionalInformation &&
              Object.keys(data.professionalInformation).length &&
              this.$refs['professionalInformation']
            ) {
              this.$refs['professionalInformation'].getData(
                data.professionalInformation
              );
            }
            //荣誉风采 corpHonor
            if (
              data.corpHonor &&
              data.corpHonor.length &&
              this.$refs['corpHonor']
            ) {
              this.$refs['corpHonor'].getData(data.corpHonor);
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
              this.$refs['purchase'].getData(data.purchase);
            }
            //团队管理  team
            if (data.team && data.team.length && this.$refs['team']) {
              this.$refs['team'].getData(data.team);
            }
            //survey 经营概况
            if (
              data.survey &&
              Object.keys(data.survey).length &&
              this.$refs['survey']
            ) {
              this.$refs['survey'].getData(data.survey);
            }
            //finance 财务管理
            if (
              data.assetFinance &&
              Object.keys(data.assetFinance).length &&
              this.$refs['assetFinance']
            ) {
              this.$refs['assetFinance'].getData(data.assetFinance);
            }
            // 普通资产
            if (
              data.commonAssets &&
              data.commonAssets.length &&
              this.$refs["commonAssets"]
            ) {
              this.$refs["commonAssets"].getData(data.commonAssets);
            }
            // 资产管理
            if (this.tabsList.asset) {
              // 房产信息
              if (data.house && data.house.length) {
                this.$refs['house'].getData(data.house);
              }
              if (data.placeOfBusiness && data.placeOfBusiness.length) {
                this.$refs['placeOfBusiness'].getData(data.placeOfBusiness);
              }
              // 主要设备
              if (data.modern && data.modern.length) {
                this.$refs['modern'].getData(data.modern);
              }
              // 生产设施
              if (data.facilityAssets && data.facilityAssets.length) {
                this.$refs['facilityAssets'].getData(data.facilityAssets);
              }
              // 无形资产
              if (data.intangibleAssets && data.intangibleAssets.length) {
                this.$refs['intangibleAssets'].getData(data.intangibleAssets);
              }
            }
            //产品服务 productService
            if (
              data.productService &&
              data.productService.length &&
              this.$refs['productService']
            ) {
              this.$refs['productService'].getData(data.productService);
            }
            //专业资质 qualification
            if (
              data.qualification &&
              data.qualification.length &&
              this.$refs['qualification']
            ) {
              this.$refs['qualification'].getData(data.qualification);
            }
             //概览信息 overviewInfo
            if (
              data.overViewInfo &&
              data.overViewInfo.length &&
              this.$refs['overViewInfo']
            ) {
              this.$refs['overViewInfo'].getData(data.overViewInfo);
            }
          }
        });
    },
    // 查询用户角色配置的表单信息
    queryConfig() {
      this.$api
        .post('/member/perfectInfo/findSysUserInfo', {
          account: this.loginUser.loginAccount
        })
        .then(response => {
          if (response.code === 200) {
            this.tabsData = response.data.tabsData;
            // 处理表单是否显示
            let arr = [];
            this.tabsData.forEach(item => arr.push(item.name));
            arr.forEach(item => (this.tabsList[item] = true));
          }
        })
        .catch(error => {
          this.$Message.error('查询用户配置信息出错！')
        });
    },
    //上一步
    handleClickBack() {
      this.$emit('on-back');
    },
    // 下一步
    handleClicNext() {
      var list = {
        account: this.account,
        depart_status: this.$refs['department'] === undefined ? '' : this.$refs['department'].dept_status
      }
      this.tabsData.forEach(element => {
        var corpInfo = element.name;
        // department 单独保存不取数据
        if (corpInfo !== 'department') {
          if (corpInfo !== 'asset') {
            // 物种必填，测试时物种取不到数据，不做验证
            this.$refs[corpInfo].handleSubmit();
            list[corpInfo] = this.$refs[corpInfo].data;
          } else {
            this.$refs['house'].handleSubmit();
            list['house'] = this.$refs['house'].data;
            this.$refs['placeOfBusiness'].handleSubmit();
            list['placeOfBusiness'] = this.$refs['placeOfBusiness'].data;
            this.$refs['modern'].handleSubmit();
            list['modern'] = this.$refs['modern'].data;
            this.$refs['facilityAssets'].handleSubmit();
            list['facilityAssets'] = this.$refs['facilityAssets'].data;
            this.$refs['intangibleAssets'].handleSubmit();
            list['intangibleAssets'] = this.$refs['intangibleAssets'].data;
          }
        }
      });
      if (this.isNext) {
        console.log('list', list)
        this.handleSave(list);
      } else {
        this.isNext = true;
        this.$Message.error('请核对输入信息');
      }
    },
    handleSave(data) {
      this.$api
        .post('/member/perfectInfo/savePerfectInfo', data)
        .then(response => {
          if (response.code == 200) {
            var user = JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')));
            if (data.privateInformation) {
              user.avatar = data.privateInformation.avatar;
              if (user.avatar) {
                sessionStorage.setItem('user', JSON.stringify(user));
              }
            }
            this.$emit('on-next');
          } else {
            this.$Message.error('保存失败！');
          }
        });
    },
    //判断表单验证是否通过，通过会返回true，不通过会返回false，不通过才给isNext赋值，然后根据isNext是否为真判断调
    handleGetSubmit(e) {
      if (!e) {
        this.isNext = e;
      }
    },
    //跳过
    handleJump() {
      this.$emit('on-jump');
    }
  },
  mounted() {
    this.style = this.getPosition()
    window.onresize = () => {
      this.style = this.getPosition()
    }
  }
};
</script>
