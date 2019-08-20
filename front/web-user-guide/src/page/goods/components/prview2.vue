<template>
  <div class="company-auth-5 mt5">
    <vui-affix-tabs :data="tabsData">
      <section id="product">
        <Title title="商品信息"></Title>
        <product ref="product" @on-submit="handleGetSubmit"></product>
      </section>
      <section id="Production">
        <Title title="商品生产信息"></Title>
        <Production ref="Production" @on-submit="handleGetSubmit"></Production>
      </section> 
      <section id="origin">
        <Title title="商品产地信息"></Title>
        <origin ref="origin" @on-submit="handleGetSubmit"></origin>
      </section> 
      <section id="qualification">
        <Title title="商品资质信息"></Title>
        <qualification ref="qualification" @on-submit="handleGetSubmit"></qualification>
      </section> 
      <section id="honor">
        <Title title="商品荣誉信息"></Title>
        <honor ref="honor" @on-submit="handleGetSubmit"></honor>
      </section> 
      <section id="quality">
        <Title title="商品质量信息"></Title>
        <view-panel
        :edit="false"
        title="质量信息表单"
        :data="qualityFormData"
        @on-data="handleGetQualityForm"
        @on-add="handleAddBtn('quality')" class="mt20"></view-panel>
        <qualityInformation ref="qualityInformation"></qualityInformation>
      </section> 
      <section id="safety">
        <Title title="商品安全标准"></Title>
        <view-panel
        title="安全标准表单"
        :edit="false"
        :data="safeFormData"
        @on-data="handleGetSafeForm"
        @on-add="handleAddBtn('safe')" class="mt20"></view-panel>
        <securityInformation ref="securityInformation"></securityInformation>
      </section> 
           
      <!-- 添加面板 -->
      <!-- <add-panel ref="addPanel" @on-save="handleControlBtn"></add-panel> -->
      <div class="tc pt30">
        <Button type="primary" @click="handleBack">上一步</Button>
        <Button type="primary" @click="handleNext">下一步</Button>
      </div>
    </vui-affix-tabs>
  </div>
</template>
<script>
import Title from '../../userAuth/components/title'
import VuiAffixTabs from '../../userAuth/components/vui-affix-tabs'
import product from './product'
import Production from './Production'
import origin from './origin'
import qualification from './qualification'
import honor from './honor'
/* import addPanel from './vui-form-control/add-panel' */
import qualityInformation from './qualityInformation'
import securityInformation from './securityInformation'
import viewPanel from './vui-form-control/view-panel'

export default {
  components: {
    VuiAffixTabs,
    Title,
    product,
    Production,
    origin,
    qualification,
    honor,
    /* addPanel, */
    viewPanel,
    qualityInformation,
    securityInformation
  },
  data() {
    return {
      account: '',
      showTab: [],
      tabsData: [
        {name:'product', title:'商品信息'},
        {name:'Production', title:'商品生产信息'},
        {name:'origin', title:'商品产地信息'},
        {name:'qualification', title:'商品资质信息'},
        {name:'honor', title:'商品荣誉信息'},
        {name:'quality', title:'商品质量信息'},
        {name:'safety', title:'商品安全标准'}
      ],
      tabsList: {},
      loginUser: JSON.parse(sessionStorage.getItem('user')),
      safeFormData: [],
      qualityFormData: [],
      id: '',
      isNext: true
    }
  },
  created() {
    this.account = this.loginUser.loginAccount
    this.id = this.$route.query.id
    this.handleInit()
  },
  methods: {
    // 初始化查询
    handleInit  () {
      this.$api.post('/portal/shopCommdoity/getCommodityBasicInfo', {account: this.account,commodityId: this.id}).then(response =>{
        if(response.code == 200) {
          let data = response.data
          if (data.product){            
            this.$refs['product'].getData(response.data.product)
          }
          if (data.Production){            
          this.$refs['Production'].getData(response.data.Production)
          }
          if (data.origin){            
          this.$refs['origin'].getData(response.data.origin)
          }
          if (data.qualification){            
          this.$refs['qualification'].getData(response.data.qualification)
          }
          if (data.honor){            
          this.$refs['honor'].getData(response.data.honor)
          }
          if (data.qualityInformation) {
            this.$refs['qualityInformation'].getData(response.data.qualityInformation)
            this.qualityFormData = response.data.qualityInformation.custom_content
          } else {
            this.qualityFormData = response.data.qualityFormData
          }
          if (data.securityInformation) {
            this.$refs['securityInformation'].getData(response.data.securityInformation)
            this.safeFormData = response.data.securityInformation.custom_content
          } else {
            this.safeFormData = response.data.safeFormData
          }
        }
      })
    },
    // 下一步
    handleNext(){
      let list = {account: this.account,commodityId: this.id}
      this.tabsData.forEach(element => {
        var corpInfo = element.name;
        if(corpInfo != 'quality' && corpInfo != 'safety'){
          this.$refs[corpInfo].handleSubmit()
          list[corpInfo] = this.$refs[corpInfo].data
        } else {
          if (corpInfo != 'quality') {
            list['qualityFormData'] = this.qualityFormData
            this.$refs['qualityInformation'].handleSubmit()
            list['qualityInformation'] = this.$refs['qualityInformation'].data
          }
          if (corpInfo != 'safety') {
            list['safeFormData'] = this.safeFormData
            this.$refs['securityInformation'].handleSubmit()
            list['securityInformation'] = this.$refs['securityInformation'].data
          }
        }
      })
      if (this.isNext) {
        // 调保存方法
        // console.log(list)
        this.handleSave(list)
      } else {
        this.isNext = true
        this.$Message.error('请核对输入信息')
      }
    },
     //判断表单验证是否通过，通过会返回true，不通过会返回false，不通过才给isNext赋值，然后根据isNext是否为真判断调
    handleGetSubmit(e) {
      if (!e) {
        this.isNext = e
      }
    },
    // 保存
    handleSave (list) {
      this.$api.post('/portal/shopCommdoity/upCommodityBasicInfo',list).then(response =>{
        if(response.code == 200) {
          this.$Message.success('保存成功')
          this.$router.push(`/release-goods/step3?id=${this.id}&productType=${this.$route.query.productType}&speciesid=${this.$route.query.speciesid}`)
        } else {
          this.$Message.error('保存失败')
        }
      })
    },
    // 上一步
    handleBack(){
      this.$router.push(`/release-goods/step1?id=${this.id}`)
    },
    // 取商品质量数据
    handleGetQualityForm (data) {
      console.log(data)
    },
    // 取商品安全数据
    handleGetSafeForm (data) {
      console.log(data)
    },
    handleAddBtn (name) {
      this.$refs.addPanel.showAddPanel = true
      this.name = name
    },
    // 添加组件
    handleControlBtn (data) {
      if (this.name === 'safe') {
        this.safeFormData.push(data)
      } else {
        this.qualityFormData.push(data)
      }
    }
  },
  mounted() {
  }
};
</script>

