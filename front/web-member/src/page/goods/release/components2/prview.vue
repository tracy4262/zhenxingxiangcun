<template>
  <div class="company-auth-5 mt5">
    <vui-affix-tabs :data="tabsData">
      <section id="product">
        <Title :title="tabsData[0].title"></Title>
        <product ref="product" @on-submit="handleGetSubmit"></product>
      </section>
      <section id="production" v-show="categoryId == 'CP05' || categoryId == 'CP06'">
        <Title :title="tabsData[1].title"></Title>
        <production ref="production" @on-submit="handleGetSubmit"></production>
      </section> 
      <section id="origin">
        <Title :title="tabsData[2].title"></Title>
        <origin ref="origin" @on-submit="handleGetSubmit"></origin>
      </section>
      <section id="productLocation">
        <Title :title="tabsData[3].title"></Title>
        <productLocation ref="productLocation"></productLocation>
      </section> 
      <!-- <section id="qualification">
        <Title :title="tabsData[4].title"></Title>
        <qualification ref="qualification" @on-submit="handleGetSubmit"></qualification>
      </section> 
      <section id="honor">
        <Title :title="tabsData[5].title"></Title>
        <honor ref="honor" @on-submit="handleGetSubmit"></honor>
      </section>  -->
      <section id="quality">
        <Title :title="tabsData[4].title"></Title>
        <qualityInformation ref="quality" @on-submit="handleGetSubmit"></qualityInformation>
      </section> 
      <section id="safety">
        <Title :title="tabsData[5].title"></Title>
        <securityInformation ref="safety" @on-submit="handleGetSubmit"></securityInformation>
      </section> 
      <section id="barcode">
        <Title :title="tabsData[6].title"></Title>
        <barcode ref="barcode" @on-submit="handleGetSubmit"></barcode>
      </section> 
      <div class="tc pt30">
        <Button  class="mr30" @click="handleBack">上一步</Button>
        <Button type="primary" @click="handleNext">下一步</Button>
      </div>
    </vui-affix-tabs>
  </div>
</template>
<script>
import Title from '../../../userAuth/components/title'
import VuiAffixTabs from '../../../userAuth/components/vui-affix-tabs'
// import VuiAffixTabs from '../components/vui-affix-tabs'
import product from './product'
import production from './production'
import origin from './origin'
import productLocation from './productLocation'
import qualification from './qualification'
import honor from './honor'
import qualityInformation from './qualityInformation'
import securityInformation from './securityInformation'
import barcode from './barcode'

export default {
  components: {
    VuiAffixTabs,
    Title,
    product,
    production,
    origin,
    productLocation,
    qualification,
    honor,
    qualityInformation,
    securityInformation,
    barcode
  },
  data() {
    return {
      account: '',
      tabsData: [
        {name:'product', title:'商品信息'},
        {name:'production', title:'商品生产信息'},
        {name:'origin', title:'商品产地信息'},
        {name:'productLocation', title:'商品所在地信息'},
        // {name:'qualification', title:'商品资质信息'},
        // {name:'honor', title:'商品荣誉信息'},
        {name:'quality', title:'商品质量信息'},
        {name:'safety', title:'商品安全标准'},
        {name:'barcode', title:'国际商品条形码'}
      ],
      isNext: true,
      categoryId: '',
      templateId: '',
      templateType: '',
      templateName: '',
      goodsId: ''
    }
},
created() {
    this.goodsId = this.$route.query.goodsId
    this.categoryId = this.$route.query.categoryId
    // if (this.categoryId !== 'CP05' && this.categoryId !== 'CP06') {
    //   this.tabsData.none = true
    // }
    this.templateId = this.$route.query.templateId
    this.templateType = this.$route.query.templateType
    this.templateName = this.$route.query.templateName
    this.account = this.$user.loginAccount
    this.init()
  },
  methods: {
    init () {
      this.$api.post('/shop/pushShopInfo/findPushBasicCommodityList',{
        pushShopCommodityId: this.goodsId, 
        shopPushTemplateId: this.templateId, 
        account: this.$user.loginAccount
      }).then(response => {
        if (response.code == 200) {
          console.log(response)
          if (response.data.product) {
            let data = response.data
            let arr = []
            console.log(data)
            for (let key in response.data) {
              if (key == 'product') {
                let title = data[key][0] && data[key][0].title ? data[key][0].title : '商品信息'
                this.tabsData[0].title = title
              }
              if (key == 'production') {
                let title = data[key][0] && data[key][0].title ? data[key][0].title : '商品生产信息'
                if (this.categoryId !== 'CP05' && this.categoryId !== 'CP06') {
                  this.tabsData[1].title = ''
                  this.tabsData[1].none = true
                } else {
                  this.tabsData[1].title = title
                }
              }
              if (key == 'origin') {
                let title = data[key][0] && data[key][0].title ? data[key][0].title : '商品产地信息'
                this.tabsData[2].title = title
              }
              if (key == 'productLocation') {
                let title = data[key][0] && data[key][0].title ? data[key][0].title : '商品所在地信息'
                this.tabsData[3].title = title
              }
              // if (key == 'qualification') {
              //   let title = data[key][0].title ? data[key][0].title : '商品资质信息'
              //   this.tabsData[4].title = title
              //   console.log('qualificationtitle', title)
              //   console.log('data[key][0]', data[key][0])
              // }
              // if (key == 'honor') {
              //   let title = data[key][0].title ? data[key][0].title : '商品荣誉信息'
              //   this.tabsData[5].title = title
              // }
              if (key == 'quality') {
                let title = data[key][0] && data[key][0].title ? data[key][0].title : '商品质量信息'
                this.tabsData[4].title = title
              }
              if (key == 'safety') {
                let title = data[key][0] && data[key][0].title ? data[key][0].title : '商品安全标准'
                this.tabsData[5].title = title
              }
              if (key == 'barcode') {
                let title = data[key][0] && data[key][0].title ? data[key][0].title : '国际商品条形码'
                this.tabsData[6].title = title
              }
              this.$nextTick(() => {
                if (data[key].length) {
                  if (key != 'honor' && key != 'qualification') {
                    this.$refs[`${key}`].getData(data[key][0])
                  }
                }
              })
            }
          }
        }
      })
    },
    // 下一步
    handleNext(){
      let list = {
        account: this.account, 
        shopPushTemplateId: this.templateId,
        templateType: this.templateType,
        productCategoryId: this.categoryId,
        pushShopCommodityId: this.goodsId
      }
      this.tabsData.forEach(element => {
        var corpInfo = element.name;
        if (corpInfo == 'production') {
          if (this.categoryId === 'CP05' || this.categoryId === 'CP06') {
            console.log()
            this.$refs[corpInfo].handleSubmit()
            list[corpInfo] = this.$refs[corpInfo].data
            list[corpInfo].title = element.title
          }
        } else {
          this.$refs[corpInfo].handleSubmit()
          list[corpInfo] = this.$refs[corpInfo].data
          list[corpInfo].title = element.title
        }
      })
      if (this.isNext) {
        // 调保存方法
        console.log(list)
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
      this.$api.post('/shop/pushShopInfo/savePushBasicCommodity',list).then(response =>{
        if(response.code == 200) {
          this.$Message.success('保存成功')
            this.$router.push(`/release-goods/step3?templateId=${this.templateId}&templateType=${this.templateType}&categoryId=${this.categoryId}&goodsId=${this.goodsId}`)
        } else {
          this.$Message.error('保存失败')
        }
      })
    },
    // 上一步
    handleBack(){
      this.$router.push(`/release-goods/step1?templateId=${this.templateId}&templateType=${this.templateType}&categoryId=${this.categoryId}&goodsId=${this.goodsId}`)
    }
  }
};
</script>

