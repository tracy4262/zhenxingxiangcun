<template>
    <div class="pt30 vui-form-ssm">
      <section>
        <Title title="追溯信息"></Title>
        <div class="pt30 pl10 pr10 pb30">
          <Form ref="info" label-position="left" :label-width="180">
            <FormItem label="产品生产单位" v-if="info.isRetrospect === '是'">
              {{info.productUnit}}
            </FormItem>
            <FormItem label="购入产品单位" v-if="info.productUnit === '购入产品' && info.isRetrospect === '是'">
                {{info.unitName}}
            </FormItem>
            <FormItem label="是否关联生产计划">
              {{info.isRelatedProductionPlan}}
            </FormItem>
            <FormItem prop="productionPlan" label="生产计划" v-if="info.isRelatedProductionPlan === '是'">
                {{info.productionPlan}}
            </FormItem>
            <FormItem label="是否与生产环境相关">
              {{info.isRelatedProductionBase}}
            </FormItem>
            <FormItem label="生产基地" v-if="info.isRelatedProductionBase === '是'">
              {{info.productionBase}}
            </FormItem>
            <FormItem prop="isRetrospect" label="是否可追溯">
              {{info.isRetrospect}}
            </FormItem>
            <FormItem label="追溯码">
              {{info.securityInformation}}
            </FormItem>
            <FormItem label="是否可防伪">
              {{info.antiFake}}
            </FormItem>
            <FormItem label="防伪码" v-if="info.antiFake === '是'">
                {{info.antiFakeCode}}
            </FormItem>
          </Form>
          <view-panel
              title="自定义表单"
              :edit="false"
              :data="info.formData">
          </view-panel>
        </div>
      </section>
  </div>
</template>
<script>
import Title from '~auth/components/title'
// import viewPanel from '../../../goods/components/vui-form-control/view-panel'
import viewPanel from './vui-form-control/view-panel'

export default {
  components: {
    Title,
    viewPanel
  },
  data () {
    return {
      info: {},
      id:''
    }
  },
  created () {
    this.id = this.$route.query.id
    this.account = this.$route.query.account
    // 数据回显
    this.handleGetInit()
  },
  methods: {
    // 初始化查询数据
    handleGetInit () {
      //  商品详情 第一步 
      this.$api.post('/shop/commodityDetail/findCommodityDetailFour', {
        pushShopCommodityId: this.id
      }).then(response => {
        if (response.code === 200) {
          this.info = response.data.info
        }
      })
    }
  }
}
</script>
<style lang="scss">
.vui-form-sm {
  .ivu-table:after{
    display: none
  }
  .ivu-table:before{
    display: none
  }
}
</style>
