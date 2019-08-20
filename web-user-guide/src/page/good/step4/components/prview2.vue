<template>
  <div class="company-auth-5 mt5">
    <vui-affix-tabs :data="tabsData">
      <section id="sales">
        <Title :title="tabsData[0].title" edit @on-change="titleChange($event, 0)"></Title>
        <sales2 ref="sales" @on-submit="handleGetSubmit" @on-change="changeUnits"></sales2>
      </section>
      <section id="pricing">
        <Title :title="tabsData[1].title" edit @on-change="titleChange($event, 1)"></Title>
        <pricing2 ref="pricing" @on-submit="handleGetSubmit" @on-change="changePricingUnits"></pricing2>
      </section>
      <section id="afterSales">
        <Title :title="tabsData[2].title" edit @on-change="titleChange($event, 2)"></Title>
        <after-sales ref="afterSales" @on-submit="handleGetSubmit"></after-sales>
      </section>
      <div class="tc">
        <Button type="primary" @click="handleBack">上一步</Button>
        <Button type="primary" @click="handleNext">下一步</Button>
      </div>
    </vui-affix-tabs>
  </div>
</template>
<script>
import Title from '../../components/title'
import VuiAffixTabs from '../../components/vui-affix-tabs'
import sales2 from './sales2'
import pricing2 from './pricing2'
import afterSales from './afterSales'
export default {
  components: {
    VuiAffixTabs,
    Title,
    sales2,
    pricing2,
    afterSales
  },
  data () {
    return {
      tabsData: [
        { name:'sales', title:'商品销售信息' },
        { name:'pricing', title:'商品定价信息' },
        { name:'afterSales', title:'产品售后信息' }
      ],
      isNext: true,
      id: '',
      categoryId: '',
      templateId: '',
      templateType: '',
      templateName: ''
    }
  },
  created() {
    this.categoryId = this.$route.query.categoryId
    this.templateId = this.$route.query.templateId
    this.templateType = this.$route.query.templateType
    this.templateName = this.$route.query.templateName
    this.handleInit()
  },
  methods: {
     // 初始化查询
    handleInit  () {
      this.$api.post('/shop/commodityMarketing/find', {
        account: this.$user.loginAccount,
        shopPushTemplateId: this.templateId,
        productCategoryId: this.categoryId,
        templateType: this.templateType
      }).then(response => {
        if (response.code === 200) {
          console.log('data', response)
          if (response.data.tabsData) {
            this.tabsData = response.data.tabsData
          }
          if (response.data.sales) {
            this.$refs['sales'].getData(response.data.sales)
          }
          if (response.data.warranty) {
            this.$refs['warranty'].getData(response.data.warranty)
          }
          if (response.data.pricing) {
            this.$refs['pricing'].getData(response.data.pricing)
          }
          if (response.data.delivery) {
            this.$refs['delivery'].getData(response.data.delivery)
          }
          if (response.data.afterSales) {
            this.$refs['afterSales'].getData(response.data.afterSales)
          }
        }
      })
    },
    // 下一步
    handleNext(){
      let list = {
        account: this.$user.loginAccount, 
        shopPushTemplateId: this.templateId,
        templateType: this.templateType,
        productCategoryId: this.categoryId,
        tabsData: this.tabsData
      }
      this.tabsData.forEach(element => {
        var corpInfo = element.name;
        // this.$refs[corpInfo].handleSubmit();
        list[corpInfo] = this.$refs[corpInfo].data;
        console.log('data', this.$refs[corpInfo].data)
      })
      if (this.isNext) {
        // 调保存方法
        this.handleSave(list)
      } else {
        this.isNext = true;
        this.$Message.error('请核对输入信息')
      }
    },
     //判断表单验证是否通过，通过会返回true，不通过会返回false，不通过才给isNext赋值，然后根据isNext是否为真判断调
    handleGetSubmit (e) {
      if (!e) {
        this.isNext = e;
      }
    },
    // 保存
    handleSave (list) {
      this.$api.post('/shop/commodityMarketing/save', list).then(response => {
        if(response.code == 200) {
          this.$Message.success('保存成功！')
          this.$router.push({
            path: '/good/step6',
            query: {
              categoryId: this.categoryId,
              templateId: this.templateId,
              templateType: this.templateType,
              templateName: this.templateName
            }
          })
        } else {
          this.$Message.error('保存失败！')
        }
      })
    },
    // 上一步
    handleBack(){
      this.$router.push({
        path: '/good/step3',
        query: {
          categoryId: this.categoryId,
          templateId: this.templateId,
          templateType: this.templateType,
          templateName: this.templateName
        }
      })
    },
    changeUnits (e) {
      this.$refs['pricing'].handleUnits(e)
    },
    changePricingUnits (e) {
      this.$refs['sales'].handleUnits(e)
    },
    titleChange ($event, index) {
      this.tabsData[index].title = $event
    },
  },
  mounted() {
  }
};
</script>
