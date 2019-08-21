<template>
  <div class="company-auth-5 mt5 vui-form-ssm">
    <Title :title="'通用商品信息'"></Title>
    <div class="pd20">
       <Form label-position="left" ref="info" :label-width="180" >
         <Row :gutter="32">
           <Col span="12">
            <FormItem label="从库存中选择产品" prop="productName">
              {{info.productName}}
            </FormItem>
           </Col>
           <Col span="12">
            <FormItem label="通用商品名称" prop="commodityName">
              {{info.commodityName}}
            </FormItem>
           </Col>
         </Row>
         <Row :gutter="32">
           <Col span="12">
            <FormItem label="产品分类" prop="productType">
              {{info.productType}}
            </FormItem>
           </Col>
           <Col span="12">
            <FormItem label="行业分类">
            {{info.industryType}}
            </FormItem>
           </Col>
         </Row>
         <Row :gutter="32">
           <Col span="12">
            <FormItem label="是否关联物种">
              {{info.isRelatedToSpecies}}
            </FormItem>
           </Col>
           <Col span="12">
            <FormItem label="相关物种" :prop="info.isRelatedToSpecies == '是' ? 'species' : ''" v-if="info.isRelatedToSpecies == '是'">
              {{info.species}}
            </FormItem>
           </Col>
         </Row>
         <Row :gutter="32">
           <Col span="12">
            <FormItem label="销售市场">
              {{info.salesMarket}}
            </FormItem>
           </Col>
           <Col span="12">
            <FormItem label="品种名称">
              <!-- type 0 品种 病害1 虫害2 -->
              {{info.variety}}
            </FormItem>
           </Col>
         </Row>
         <Row :gutter="32">
           <Col span="12">
            <div v-if="categoryId == 'CP05' || categoryId == 'CP06'">
              <FormItem label="是否主要农产品">
                {{info.isMainProducts}}
              </FormItem>
            </div>
           </Col>
         </Row>
        <Row :gutter="32"  v-if="categoryId == 'CP01' || categoryId == 'CP05' || categoryId == 'CP06'">
           <Col span="12">
             <FormItem label="产出产品的物种生长环境">
                {{info.liveEnvironment}}
              </FormItem>
            </Col>
            <Col span="12">
             <FormItem label="物种保护级别">
                {{info.protectionLevel}}
              </FormItem>
            </Col>
            <Col span="24">
              <FormItem label="保护证书" v-if="info.protectionLevel && info.protectionLevel != '非保护'">
                <Row>
                    <Col span="6" v-for="(e, i) in info.protection" :key="i">
                      <div class="pd10">
                          <img :src="e" alt="" width="100%" height="150px">
                      </div>
                    </Col>
                </Row>
              </FormItem>
            </Col>
         </Row>
         <Row :gutter="32" v-if="categoryId == 'CP05' || categoryId == 'CP06'">
           <Col span="12">
             <FormItem label="是否食用产品或原料">
                {{info.isFood}}
              </FormItem>
           </Col>
           <Col span="12">
             <FormItem label="是否填写热量或营养">
                {{info.isWriteHeat}}
              </FormItem>
           </Col>
           <Col span="12">
              <FormItem label="热量或者营养素" v-if="info.isWriteHeat === '是'">
                  {{info.heat}}
              </FormItem>
           </Col>
           <Col span="12">
              <FormItem label="是否适宜家庭个人消费食用">
                {{info.isSuitFamily}}
              </FormItem>
           </Col>
           <Col span="12">
            <FormItem label="适宜人群">
              <span v-for="(item, index) in info.suitPeople" :key="index"> {{item}}</span>
            </FormItem>
           </Col>
         </Row>
         <Row :gutter="32">
           <Col span="12">
            <FormItem label="追溯类型" v-if="categoryId == 'CP01' || categoryId == 'CP05' || categoryId == 'CP06' || categoryId == 'CP07' || categoryId == 'CP08' || categoryId == 'CP09'">
              {{info.retrospectType}}
            </FormItem>
           </Col>
         </Row>
         <Row :gutter="32">
           <Col span="24">
            <FormItem prop="productIntroduction" label="商品简介">
              <div v-html="info.productIntroduction"></div>
            </FormItem>
           </Col>
         </Row>
         <Row :gutter="32">
           <Col span="24">
             <FormItem label="上传商品预览图片">
                <Row>
                    <Col span="6" v-for="(e, i) in info.notarizationCertificate" :key="i">
                      <div class="pd10">
                          <img :src="e" alt="" width="100%" height="150px">
                      </div>
                    </Col>
                </Row>
              </FormItem>
           </Col>
         </Row>
            <view-panel
                :edit="false"
                title="添加自定义字段"
                :data="info.safeFormData"></view-panel>
        </Form>
    </div>
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
  data() {
    return {
      id: '',
      info: {},
      categoryId:''
    }
  },
  created() {
    this.id = this.$route.query.id
    this.handleInit()
  },
  methods: {
    // 初始化查询
    handleInit  () {
      this.$api.post('/shop/commodityDetail/findCommodityDetailFirst', {
        pushShopCommodityId: this.id
      }).then(response => {
        if (response.code === 200) {
          this.info = response.data.commodity[0]
          this.categoryId = this.info.productCategoryId
        }
      })
    }
  },
  mounted() {
  }
};
</script>

