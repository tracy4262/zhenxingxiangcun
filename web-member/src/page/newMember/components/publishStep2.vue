<template>
  <div>
    <Form :label-width="100" :rules="ruleInline2" :model="mydynamic" ref="mydynamic">
      <FormItem label="关联物种">
        <vuiSpecies :values="mydynamic.species" @on-save="saveSpecies"  @on-save-id="saveSpeciesId"></vuiSpecies>
      </FormItem>
      <FormItem label="通用商品名">
          <vuiCommodity ref="products" @on-save="getProducts" type="1" :values="mydynamic.goodsname"></vuiCommodity>
      </FormItem>
      <FormItem label="通用服务名">
          <vuiCommodity ref="service" @on-save="getService" type="2" :values="mydynamic.servicename"></vuiCommodity>
      </FormItem>
      <FormItem label="行业分类">
        <vuiTrade :values="mydynamic.industryName" @on-save="saveTrade"  @on-save-id="saveTradeId"></vuiTrade>
      </FormItem>
      <FormItem label="适用区域" prop="district">
        <vui-cascander :values="mydynamic.district" @handle-get-result="handleGetData">
        </vui-cascander>
      </FormItem>
    </Form>
  </div>
</template>
<script>
import vuiSpecies from "~components/vui-species";
import vuiCommodity from "~components/vui-commodity";
import vuiTrade from "~components/vui-trade";
import vuiCascander from '~components/vuiCascader/index'
export default {
  components: {
    vuiSpecies,
    vuiCommodity,
    vuiTrade,
    vuiCascander,
  },
  props: {
    data: {
      type: Object,
      default: () => {
        return {}
      }
    },
    type: {
      type: String,
      default: '文章'
    }
  },
  data () {
    return {
      mydynamic: {
        speciesId: '',
        species: '',
        goodsname: '',
        goods: '',
        service: '',
        servicename: '',
        industry: '',
        industryName: '',
        district: '',
      },
      ruleInline2: {
        district: [
          {required: true, message: "请选择适用区域", trigger: "change"}
        ]
      },
    }
  },
  created() {
    this.mydynamic = this.data
    console.log(this.mydynamic)
  },
  watch: {
    data: {
      handler(curVal, oldVal) {
        this.mydynamic = curVal
      },
      deep: true
    }
  },
  methods: {
    next () {
      let flag = true
      this.$refs[`mydynamic`].validate(v => {
        if (!v) {
          flag = false
          this.$Message.error('请核对表单信息')
          this.$emit('on-next', flag)
        } else {
          this.$emit('on-next', flag)
        }
      })
    },
    // 获取物种
    saveSpeciesId (id) {
      this.mydynamic.speciesId = id
    },
    saveSpecies (value) {
      this.mydynamic.species = value
    },
    // 获取 行业分类
    saveTradeId (id) {
      this.mydynamic.industryId = id
    },
    saveTrade (value) {
      this.mydynamic.industryName = value
    },
    // 通用服务名
    getService (result) {
      var arr = [];
      var service = [];
      result.forEach(item => {
        arr.push(item.value)
        service.push(item.label)
      });
      this.mydynamic.servicename = service.join(" ");
      // this.$refs.bookData.service = service.join(" ");
      this.mydynamic.serviceId = arr.join(" ");
    },
    // 通用商品名
    getProducts (result) {
      var arr = [];
      var products = [];
      result.forEach(item => {
        arr.push(item.value)
        products.push(item.label)
      })
      this.mydynamic.goodsname = products.join(" ");
      // this.$refs.bookData.products = products.join(" ");
      this.mydynamic.goodsId = arr.join(" ");
    },
    // 适用 地区
    handleGetData (value, selectedData) {
        let labelArr = []
        selectedData.forEach(element => {
            labelArr.push(element.label)
        })
        this.mydynamic.district = labelArr.join('/')
    }
  },
}
</script>