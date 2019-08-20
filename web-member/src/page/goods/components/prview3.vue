<template>
  <div class="company-auth-5 mt5">
    <vui-affix-tabs :data="tabsData">
      <section id="sales">
        <Title title="商品销售信息"></Title>
        <sales ref="sales" @on-submit="handleGetSubmit" @on-change="changeUnits"></sales>
      </section>
      <section id="warranty">
        <Title title="商品质保信息"></Title>
        <warranty ref="warranty" @on-submit="handleGetSubmit"></warranty>
      </section>
      <section id="pricing">
        <Title title="商品定价信息"></Title>
        <pricing ref="pricing" @on-submit="handleGetSubmit" @on-change="changePricingUnits"></pricing>
      </section>
      <section id="delivery">
        <Title title="产品发货信息"></Title>
        <delivery ref="delivery" @on-submit="handleGetSubmit"></delivery>
      </section>
      <section id="afterSales">
        <Title title="产品售后信息"></Title>
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
import Title from '../../userAuth/components/title'
import VuiAffixTabs from '../../userAuth/components/vui-affix-tabs'
import sales from './sales'
import warranty from './warranty'
import pricing from './pricing'
import delivery from './delivery'
import afterSales from './afterSales'
export default {
  components: {
    VuiAffixTabs,
    Title,
    sales,
    warranty,
    pricing,
    delivery,
    afterSales
  },
  data() {
    return {
      account: '',
      showTab: [],
      tabsData: [
        {name:'sales', title:'商品销售信息'},
        {name:'warranty', title:'商品质保信息'},
        {name:'pricing', title:'商品定价信息'},
        {name:'delivery', title:'产品发货信息'},
        {name:'afterSales', title:'产品售后信息'}
      ],
      tabsList: {},
      loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
      isNext: true,
      id: ''
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
      this.$api.post('/portal/shopCommdoity/getMarketingBasicInfo', {account: this.account,commodityId: this.id}).then(response =>{
        if(response.code == 200) {
          let data = response.data
          if (data.sales){
            this.$refs['sales'].getData(response.data.sales)
          }
          if (data.warranty){
            this.$refs['warranty'].getData(response.data.warranty)
          }
          if (data.pricing){
            this.$refs['pricing'].getData(response.data.pricing)
          }
          if (data.delivery){
            this.$refs['delivery'].getData(response.data.delivery)
          }
          if (data.afterSales){
            this.$refs['afterSales'].getData(response.data.afterSales)
          }
        }
      })
    },
    // 下一步
    handleNext(){
      let list = {account: this.account,commodityId: this.id}
      this.tabsData.forEach(element => {
        var corpInfo = element.name;
        this.$refs[corpInfo].handleSubmit();
        list[corpInfo] = this.$refs[corpInfo].data;
      })
      if (this.isNext) {
        // 调保存方法
        this.handleSave(list);
      } else {
        this.isNext = true;
        this.$Message.error('请核对输入信息');
      }
    },
     //判断表单验证是否通过，通过会返回true，不通过会返回false，不通过才给isNext赋值，然后根据isNext是否为真判断调
    handleGetSubmit(e) {
      if (!e) {
        this.isNext = e;
      }
    },
    // 保存
    handleSave (list) {
      this.$api.post('/portal/shopCommdoity/upMarketingBasicInfo',list).then(response =>{
        if(response.code == 200) {
          this.$Message.success('保存成功')
          this.$router.push(`/release-goods/step4?id=${this.id}&productType=${this.$route.query.productType}&speciesid=${this.$route.query.speciesid}`)
        } else {
          this.$Message.error('保存失败')
        }
      })
      
    },
    // 上一步
    handleBack(){
        this.$router.push(`/release-goods/step2?id=${this.id}&productType=${this.$route.query.productType}&speciesid=${this.$route.query.speciesid}`)
    },
    changeUnits (e) {
      this.$refs['pricing'].handleUnits(e)
    },
    changePricingUnits (e) {
      this.$refs['sales'].handleUnits(e)
    }
  },
  mounted() {
  }
};
</script>
