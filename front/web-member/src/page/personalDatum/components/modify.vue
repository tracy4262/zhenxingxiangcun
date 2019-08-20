<template>
  <div class="company-auth-5 mt5" >
    <vui-affix-tabs class="pt30" :data="tabsData">
      <Card >
          <div class="pd20">
              <div id="privateInformation" v-if="tabsList.privateInformation">
                  <Title title="隐私信息"></Title>
                  <private-information ref="privateInformation" @on-submit="handleGetSubmit"></private-information>
              </div>
              <div id="networkInformation" v-if="tabsList.networkInformation">
                  <Title title="网络信息"></Title>
                  <network-information ref="networkInformation" @on-submit="handleGetSubmit"></network-information>
              </div>
              <div id="education" v-if="tabsList.education">
                  <Title title="教育经历"></Title>
                  <education ref="education" @on-submit="handleGetSubmit"></education>
              </div>
              <div id="work" v-if="tabsList.work">
                  <Title title="工作经历"></Title>
                  <work ref="work" @on-submit="handleGetSubmit"></work>
              </div> 
              <div id="policy" v-if="tabsList.policy">
                  <Title title="政治面貌"></Title>
                  <policyInfo ref="policy" @on-submit="handleGetSubmit"></policyInfo>
              </div>
              <div id="religion" v-if="tabsList.religion">
                  <Title title="宗教信仰"></Title>
                  <religionInfo ref="religion" @on-submit="handleGetSubmit"></religionInfo>
              </div>
              <div id="professionalInformation" v-if="tabsList.professionalInformation">
                  <Title title="专业信息"></Title>
                  <professional-information ref="professionalInformation" @on-submit="handleGetSubmit"></professional-information>
              </div>
              <div id="corpHonor" v-if="tabsList.corpHonor">
                  <Title title="荣誉风采"></Title>
                  <corp-honor ref="corpHonor" @on-submit="handleGetSubmit"></corp-honor>
              </div>   
              <div id="member"  v-if="tabsList.member">
                  <Title title="家庭成员"></Title>
                  <family-detail ref="member" @on-submit="handleGetSubmit" ></family-detail>  
              </div>
              <div id="product"  v-if="tabsList.product">
                  <Title title="产出产品"></Title>
                  <product-detail ref="product" @on-submit="handleGetSubmit"></product-detail>
              </div>
              <div id="provide"  v-if="tabsList.provide">
                  <Title title="提供服务"></Title>
                  <service-detail ref="provide" @on-submit="handleGetSubmit" ></service-detail>
              </div>
              <div id="purchase"  v-if="tabsList.purchase">
                  <Title title="求购信息"></Title>
                  <want-to-buy ref="purchase" @on-submit="handleGetSubmit"></want-to-buy>
              </div>
              <div id="asset"  v-if="tabsList.asset">
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
              </div>
              <!-- <div id="house"  v-if="tabsList.house">
                  <Title title="房屋生活情况"></Title>
                  <house ref="house" @on-submit="handleGetSubmit"></house>
              </div>
              <div id="modern" v-if="tabsList.modern">
                  <Title title="家庭现代化情况"></Title>
                  <modern ref="modern" :isAdd="false" @on-submit="handleGetSubmit"></modern>
              </div> -->
              <div id="department" v-if="tabsList.department">
                  <Title title="组织机构"></Title>
                  <department ref="department"></department>
              </div>
              <div id="team" v-if="tabsList.team">
                  <Title title="管理团队"></Title>
                  <team ref="team" @on-submit="handleGetSubmit"></team>
              </div>
              <div id="survey" v-if="tabsList.survey">
                  <Title title="经营概况"></Title>
                  <survey ref="survey" @on-submit="handleGetSubmit"></survey>
              </div>  
              <div id="assetFinance"  v-if="tabsList.assetFinance">
                  <Title title="财务信息"></Title>
                  <asset-finance ref="assetFinance" @on-submit="handleGetSubmit"></asset-finance>
              </div>  
              <div id="productService" v-if="tabsList.productService">
                  <Title title="产品&服务"></Title>
                  <product-service ref="productService" @on-submit="handleGetSubmit"></product-service>
              </div>
              <div id="qualification" v-if="tabsList.qualification">
                  <Title title="专业资质"></Title>
                  <qualification ref="qualification" @on-submit="handleGetSubmit"></qualification>
              </div>        
            <div id="commonAssets"  v-if="tabsList.commonAssets">
                <Title title="普通资产"></Title>
                <common-assets ref="commonAssets" @on-submit="handleGetSubmit"></common-assets>
            </div>
            <!-- <div id="intangibleAssets"  v-if="tabsList.intangibleAssets">
                <Title title="无形资产"></Title>
                <intangible-assets ref="intangibleAssets" @on-submit="handleGetSubmit"></intangible-assets>
            </div>
            <div id="facilityAssets"  v-if="tabsList.facilityAssets">
                <Title title="设施资产"></Title>
                <facility-assets ref="facilityAssets" @on-submit="handleGetSubmit"></facility-assets>
            </div>
            <div id="placeOfBusiness"  v-if="tabsList.placeOfBusiness">
                <Title title="经营场所"></Title>
                <place-of-business ref="placeOfBusiness" @on-submit="handleGetSubmit"></place-of-business>
            </div> -->
          </div>
          <div class="tc pb30">
              <Button type="primary" id="markup-1" @click="handleClicNext">确定</Button>
              <Button type="default" id="markup-1" @click="handleClickBack">取消</Button>
          </div>
      </Card>
    </vui-affix-tabs>
  </div>
</template>
<script>
import Title from "../../userAuth/components/title";
import vuiAffixTabs from './vui-affix-tabs'
import education from "../../userAuth/components/education";
import privateInformation from "../../userAuth/components/privateInformation";
import networkInformation from "../../userAuth/components/networkInformation";
import work from "../../userAuth/components/work";
import policyInfo from "../../userAuth/components/policy";
import religionInfo from "../../userAuth/components/religion";
import survey from "../../userAuth/components/survey";
import team from "../../userAuth/components/team";
import professionalInformation from "../../userAuth/components/professionalInformation";
import corpHonor from "../../userAuth/components/corpHonor";
import qualification from "../../userAuth/components/proQualification";
import productService from "../../userAuth/components/productService";
import assetFinance from "../../userAuth/components/assetFinance";
import house from "../../farmFamily/house";
import familyDetail from "../../farmFamily/familyDetail";
import wantToBuy from "../../farmFamily/wantToBuy";
import serviceDetail from "../../farmFamily/serviceDetail";
import productDetail from "../../farmFamily/productDetail";
import modern from "../../farmFamily/modern";
import department from "../../userAuth/components/department";
import commonAssets from '../../userAuth/components/commonAssets'
import intangibleAssets from '../../userAuth/components/intangibleAssets'
import facilityAssets from '../../userAuth/components/facilityAssets'
import placeOfBusiness from '../../userAuth/components/placeOfBusiness'
export default {
  components: {
    vuiAffixTabs,
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
    familyDetail,
    wantToBuy,
    serviceDetail,
    productDetail
  },
  data() {
    return {
      account: "",
      isNext: true,
      showTab: [],
      tabsData: [],
      tabsList: {},
      loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
      tabsList: {
        assetFinance: false,
        corpHonor: false,
        department: false,
        education: false,
        house: false,
        modern: false,
        networkInformation: false,
        policy: false,
        privateInformation: false,
        productService: false,
        professionalInformation: false,
        qualification: false,
        religion: false,
        survey: false,
        team: false,
        work: false,
        asset: false
      },
      overViewInfo:[{editor:''}] //概览信息
    };
  },
  created() {
    this.account = this.loginUser.loginAccount;
    this.getModel();
    this.queryConfig();
  },
  methods: {
    // 初始化获取数据
    getModel() {
      this.$api
        .post("/member/perfectInfo/findPerfectInfo ", { account: this.account })
        .then(response => {
          console.log(response);
          if (response.code == 200) {
            // 这个判断里面写了所有取数据的，但是当此用户类型没有这个模块的时候就不会返回这个key的数据，
            // 所以要先判断key是否存在，再根据各个模块数据类型的不同判断，里面是否有值，如果有值，就取值了之后，赋值给当前模块
            var data = response.data;
            //隐私信息
            if (
              data.privateInformation &&
              Object.keys(data.privateInformation).length &&
              this.$refs["privateInformation"]
            ) {
              // 如果有数据说明之前已经保存过，不管是保存的是否是空数据，都已经有数据格式了
              this.$refs["privateInformation"].getData(data.privateInformation);
            }
            //网络信息networkInformation
            if (
              data.networkInformation &&
              Object.keys(data.networkInformation).length &&
              this.$refs["networkInformation"]
            ) {
              this.$refs["networkInformation"].getData(data.networkInformation);
            }
            //教育经历 education
            if (
              data.education &&
              data.education.length &&
              this.$refs["education"]
            ) {
              this.$refs["education"].getData(data.education);
            }
            //工作经历 work
            if (data.work && data.work.length && this.$refs["work"]) {
              this.$refs["work"].getData(data.work);
            }
            //政治面貌 policyInfo
            if (
              data.policy &&
              Object.keys(data.policy).length &&
              this.$refs["policy"]
            ) {
              this.$refs["policy"].getData(data.policy);
            }
            //宗教信仰
            if (
              data.religion &&
              Object.keys(data.religion).length &&
              this.$refs["religion"]
            ) {
              this.$refs["religion"].getData(data.religion);
            }
            //专业信息 professionalInformation
            if (
              data.professionalInformation &&
              Object.keys(data.professionalInformation).length &&
              this.$refs["professionalInformation"]
            ) {
              this.$refs["professionalInformation"].getData(
                data.professionalInformation
              );
            }
            //荣誉风采 corpHonor
            if (
              data.corpHonor &&
              data.corpHonor.length &&
              this.$refs["corpHonor"]
            ) {
              this.$refs["corpHonor"].getData(data.corpHonor);
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
            //房屋生活情况 house
            /* if (data.house && data.house.length && this.$refs["house"]) {
              this.$refs["house"].getData(data.house);
            } */
            //家庭现代化情况  modern
            /* if (data.modern && data.modern.length && this.$refs["modern"]) {
              this.$refs["modern"].getData(data.modern);
            } */
            //团队管理  team
            if (data.team && data.team.length && this.$refs["team"]) {
              this.$refs["team"].getData(data.team);
            }
            //survey 经营概况
            if (
              data.survey &&
              Object.keys(data.survey).length &&
              this.$refs["survey"]
            ) {
              this.$refs["survey"].getData(data.survey);
            }
            //finance 财务管理
            if (
              data.assetFinance &&
              Object.keys(data.assetFinance).length &&
              this.$refs["assetFinance"]
            ) {
              this.$refs["assetFinance"].getData(data.assetFinance);
            }
            // 普通资产
            if (
              data.commonAssets &&
              data.commonAssets.length &&
              this.$refs["commonAssets"]
            ) {
              this.$refs["commonAssets"].getData(data.commonAssets);
            }
             // 无形资产
            /* if (
              data.intangibleAssets &&
              data.intangibleAssets.length &&
              this.$refs["intangibleAssets"]
            ) {
              this.$refs["intangibleAssets"].getData(data.intangibleAssets);
            } */
            // 设施资产
            /* if (
              data.facilityAssets &&
              data.facilityAssets.length &&
              this.$refs["facilityAssets"]
            ) {
                console.log('data.facilityAssets',data.facilityAssets)
              this.$refs["facilityAssets"].getData(data.facilityAssets);
            } */
            // 经营场所
            /* if (
              data.placeOfBusiness &&
              data.placeOfBusiness.length &&
              this.$refs["placeOfBusiness"]
            ) {
              this.$refs["placeOfBusiness"].getData(data.placeOfBusiness);
            } */
            //产品服务 productService
            if (
              data.productService &&
              data.productService.length &&
              this.$refs["productService"]
            ) {
              this.$refs["productService"].getData(data.productService);
            }
            //专业资质 qualification
            if (
              data.qualification &&
              data.qualification.length &&
              this.$refs["qualification"]
            ) {
              this.$refs["qualification"].getData(data.qualification);
            }
             //概览信息 overviewInfo
            if (
              data.overViewInfo &&
              data.overViewInfo.length
            ) {
              this.overViewInfo = data.overViewInfo
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
          }
        });
    },
    // 查询用户角色配置的表单信息
    queryConfig() {
      this.$api
        .post("/member/perfectInfo/findSysUserInfo", {
          account: this.account
        })
        .then(response => {
          if (response.code === 200) {
            var data = response.data.tabsData;
            this.tabsData = data.splice(0,data.length-1)
            // 处理表单是否显示
            let arr = [];
            this.tabsData.forEach(item => arr.push(item.name));
            arr.forEach(item => (this.tabsList[item] = true));
            console.log(this.tabsList);
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    //取消
    handleClickBack() {
      this.$router.push({ path: `/personalIndex/detail` });
    },
    // 下一步
    handleClicNext() {
      var list = { 
        account: this.account,
        depart_status: this.$refs['department'] === undefined ? '' : this.$refs['department'].dept_status
      };
      this.tabsData.forEach(element => {
        var corpInfo = element.name;
        console.log('corpInfo', corpInfo)
        // department 单独保存不取数据
        if (corpInfo != "department") {
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
        list.overViewInfo = this.overViewInfo
        this.handleSave(list);
      } else {
        this.isNext = true;
        this.$Message.error("请核对输入信息");
      }
    },
    handleSave(data) {
      this.$api
        .post("/member/perfectInfo/savePerfectInfo", data)
        .then(response => {
          if (response.code == 200) {
            var user = JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')));
            if(data.privateInformation){                            
              user.avatar = data.privateInformation.avatar
              if(user.avatar) {
                sessionStorage.setItem("user", JSON.stringify(user))
              }
            }
            window.location.href=`${window.location.origin}/personalIndex/detail?uid=${this.account}`
            this.$router.push({ path: `/personalIndex/detail` });
          } else {
            this.$Message.error('保存失败！')
          }
        });
    },
    //判断表单验证是否通过，通过会返回true，不通过会返回false，不通过才给isNext赋值，然后根据isNext是否为真判断调
    handleGetSubmit(e) {
      if (!e) {
        this.isNext = e;
      }
    }
  }
};
</script>