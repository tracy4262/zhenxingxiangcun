<template>
    <div class="bg-white pt30">
    <Row type="flex" justify="center">
      <Col span="16">
        <Form ref="info" :model="info" label-position="left" :label-width="180" :rules="ruleInline">
          <FormItem prop="isRetrospect" label="是否可追溯">
            <RadioGroup v-model="info.isRetrospect" @on-change="clear7">
              <Radio label="是"></Radio>
              <Radio label="否"></Radio>
            </RadioGroup>
          </FormItem>
          <FormItem label="产品生产单位" v-if="info.isRetrospect === '是'">
            <Select v-model="info.productUnit" clearable @on-change="clear3">
              <Option v-for="item in productUnitList" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
          </FormItem>
          <FormItem label="购入产品单位" v-if="info.isRetrospect === '是' && info.productUnit === '购入产品'">
              <Input v-model="info.unitName" :maxlength="50" />
          </FormItem>
          <FormItem label="是否关联生产计划" v-if="info.isRetrospect === '是'">
            <RadioGroup v-model="info.isRelatedProductionPlan" @on-change="clear5">
              <Radio label="是"></Radio>
              <Radio label="否"></Radio>
            </RadioGroup>
          </FormItem>
          <FormItem prop="productionPlan" label="生产计划" v-if="info.isRetrospect === '是' && info.isRelatedProductionPlan === '是'">
              <Input v-model="info.productionPlan" :maxlength="50" />
              <!-- <Select v-model="info.productionPlan" clearable @on-change="clear8">
                <Option v-for="item in productionBaseList" :value="item.value" :key="item.value">{{ item.label }}</Option>
              </Select> -->
          </FormItem>
          <FormItem label="是否关联生产基地" v-if="info.isRetrospect === '是'">
            <RadioGroup v-model="info.isRelatedProductionBase" @on-change="clear78">
              <Radio label="是"></Radio>
              <Radio label="否"></Radio>
            </RadioGroup>
          </FormItem>
          <FormItem label="生产基地" v-if="info.isRetrospect === '是' && info.isRelatedProductionBase === '是'">
            <Select v-model="info.productionBase" clearable @on-change="clear8">
              <Option v-for="item in productionBaseList" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
          </FormItem>
          <FormItem label="追溯码" class="mt20" v-if="info.isRetrospect === '是'">
              <Input v-model="info.securityInformation" :maxlength="20" />
          </FormItem>
          <FormItem label="是否可防伪">
            <RadioGroup v-model="info.antiFake" @on-change="clear9">
              <Radio label="是"></Radio>
              <Radio label="否"></Radio>
            </RadioGroup>
          </FormItem>
          <FormItem label="防伪码" v-if="info.antiFake === '是'">
            <Input v-model="info.antiFakeCode" :maxlength="20" />
          </FormItem>
        </Form>
        <view-panel
            title="自定义表单"
            :data="info.formData"
            @on-data="fhandleGetForm"
            @on-add="handleAddBtnAddPanel">
        </view-panel>
        <!-- 添加面板 -->
        <add-panel ref="addPanel" @on-save="handleControlBtn"></add-panel>
        <div class="tc mt20 mb20">
          <Button type="primary" @click="last">上一步</Button>
          <Button type="primary" @click="next">下一步</Button>
        </div>
      </Col>
    </Row>
  </div>
</template>
<script>
import addPanel from '../../../goods/components/vui-form-control/add-panel'
import viewPanel from '../../../goods/components/vui-form-control/view-panel'
export default {
  components: {
    addPanel,
    viewPanel
  },
  data () {
    return {
      info: {
        isRetrospect: '是', // 是否可追溯
        productUnit: '', // 产品生产单位
        unitName: '', // 购入产品单位
        isRelatedProductionPlan: '是', // 是否关联生产计划
        productionPlan: '', // 生产计划
        isRelatedProductionBase: '是', // 是否与生产环境相关
        productionBase: '', // 生产基地
        securityInformation: '', // 追溯码
        antiFake: '是', // 是否可防伪
        antiFakeCode: '', // 防伪码
        formData: []
      },
      ruleInline: {
        isRetrospect: [
          { required: true }
        ],
        productionPlan: [
          { required: true, message: '请选择生产计划', trigger: 'change' }
        ]
      },
      airQuality: false,
      irrigationWaterQuality: false,
      fisheryWaterQuality: false,
      livestockWaterQuality: false,
      processWater: false,
      soilQuality: false,
      soilFertility: false,
      airQualityPage: false,
      irrigationWaterQualityPage: false,
      fisheryWaterQualityPage: false,
      livestockWaterQualityPage: false,
      processWaterPage: false,
      soilQualityPage: false,
      soilFertilityPage: false,
      productUnitList: [
        {
          label: '企业自产',
          value: '企业自产'
        },
        {
          label: '购入产品',
          value: '购入产品'
        }
      ],
      productionBaseList: [],
      isShowInfo: false,
      categoryId: '',
      templateId: '',
      templateType: '',
      templateName: ''
    }
  },
  created () {
    this.categoryId = this.$route.query.categoryId
    this.templateId = this.$route.query.templateId
    this.templateType = this.$route.query.templateType
    this.templateName = this.$route.query.templateName
    // 查询生产基地
    this.getProductionBase()
    // 数据回显
    this.init()
  },
  methods: {
    getProductionBase () {
      this.$api.post('/member/product-base/product-is-exist', {
        loginAccount: this.$user.loginAccount
      }).then(res => {
        console.log('isShow', res)
        if (res.code === 200) {
          if (res.data && res.data.length > 0) {
            res.data.forEach(element => {
              this.productionBaseList.push({
                label: element.base_name,
                value: element.product_id
              })
            })
          }
        }
      })
    },
    init () {
      this.$api.post('/shop/traceSecurity/find', {
        account: this.$user.loginAccount,
        shopPushTemplateId: this.templateId,
        productCategoryId: this.categoryId,
        templateType: this.templateType
      }).then(res => {
        console.log('info', res)
        if (res.code === 200 && res.data.info) {
          this.info = res.data.info
          // 标识为数据回显 不清空
          this.isShowInfo = true
        }
      })
    },
    // 取商品质量数据
    fhandleGetForm (data) {
    },
    // 质量信息添加
    handleAddBtnAddPanel () {
      this.$refs.addPanel.showAddPanel = true
    },
    // 添加组件
    handleControlBtn (data) {
      this.info.formData.push(data)
    },
    clear23 () {
      if (!this.isShowInfo) {
        this.info.productUnit = ''
        this.info.unitName = ''
      }
    },
    clear3 () {
      if (!this.isShowInfo) {
        this.info.unitName = ''
      }
    },
    clear5 () {
      this.info.productionPlan = ''
    },
    clear7 () {
      this.info.productUnit = ''
      this.info.unitName = ''
      this.info.isRelatedProductionPlan = '是'
      this.info.productionPlan = ''
      this.info.isRelatedProductionBase = '是'
      this.info.productionBase = ''
      this.info.securityInformation = ''
    },
    clear78 () {
      this.info.productionBase = ''
      this.airQuality = false
      this.irrigationWaterQuality = false
      this.fisheryWaterQuality = false
      this.livestockWaterQuality = false
      this.processWater = false
      this.soilQuality = false
      this.soilFertility = false
    },
    clear8 () {
      this.airQuality = false
      this.irrigationWaterQuality = false
      this.fisheryWaterQuality = false
      this.livestockWaterQuality = false
      this.processWater = false
      this.soilQuality = false
      this.soilFertility = false
    },
    clear9 () {
        this.info.antiFakeCode = ''
    },
    next () {
      /* this.$refs['info'].validate(valid => {
          if (valid) {
              console.log('valid', valid)
          } else {
              this.$Message.error('请核对表单字段！')
          }
      }) */
      let list = {
        account: this.$user.loginAccount,
        shopPushTemplateId: this.templateId,
        templateType: this.templateType,
        productCategoryId: this.categoryId,
        info: this.info
      }
      console.log('list', list)
      this.$api.post('/shop/traceSecurity/save', list).then(response => {
        console.log('response', response)
        if (response.code === 200) {
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
        }
      }).catch(error => {
        this.$Message.error('保存失败！')
      })
    },
    last () {
      this.$router.push({
        path: '/good/step4',
        query: {
          categoryId: this.categoryId,
          templateId: this.templateId,
          templateType: this.templateType,
          templateName: this.templateName
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
