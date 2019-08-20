<template>
  <div class="layouts">
    <Card>
      <p class="pd20 template-name">{{templateName}}</p>
      <div style="width: 800px; margin: 0 auto;" class="pt30 pb20">
        <Form :model="info" label-position="left" ref="info" :label-width="180" :rules="ruleInline">
          <FormItem label="从库存中选择产品">
            <Row>
              <Col span="20">
                <Select v-model="info.productNameId" filterable @on-change="productNameChange" ref="search">
                  <Option v-for="(item, index) in productNames" :disabled="item.disabled" :value="item.id" :key="index">{{ item.productName }}</Option>
                </Select>
              </Col>
              <Col span="4" class="tr">
                <Button type="primary" @click="addProduct">添加产品</Button>
              </Col>
            </Row>
          </FormItem>
          <!-- <FormItem label="从库存中选择产品" prop="productName"v-if="show" >
            <Input v-model="info.productName" readonly @on-focus="productNameFocus"/>
          </FormItem>
          <FormItem label="从库存中选择产品" v-if="!show" >
            <Select 
                v-model="info.productNameId" 
                filterable 
                remote 
                :remote-method="remoteMethod1"
                :loading="loading1"
                style="width:100%;" 
                @on-open-change="shwo = true"
                @on-change="productNameChange">
                <Option v-for="(item, index) in productNames" :disabled="item.disabled" :value="item.id" :key="index">{{ item.productName }}</Option>
            </Select> -->
          </FormItem>
          <FormItem label="通用商品名称" prop="commodityName">
            <Input v-model="info.commodityName" readonly/>
          </FormItem>
          <FormItem label="产品分类" prop="productType">
             <Input v-model="info.productType" readonly/>
          </FormItem>
          <FormItem label="行业分类">
            <Input v-model="info.industryType" readonly/>
             <!-- <vui-trade
              :values="info.industryType" 
              @on-save="handleResult"
              @on-save-id="handleResult"></vui-trade> -->
          </FormItem>
          <FormItem label="是否关联物种">
            <RadioGroup v-model="info.isRelatedToSpecies">
              <Radio label="是"></Radio>
              <Radio label="否"></Radio>
            </RadioGroup>
          </FormItem>
          <FormItem label="相关物种" :prop="info.isRelatedToSpecies == '是' ? 'species' : ''" v-if="info.isRelatedToSpecies == '是'">
             <vui-species
              :values="info.species" 
              :num="1"
              @on-save="onSaveSpecies"
              @on-save-id="onSaveSpeciesId"></vui-species>
          </FormItem>
        <!-- </Form>
        <Form :model="info" label-position="left" ref="info" :label-width="180" :rules="ruleInline"> -->
          <FormItem label="销售市场">
             <Input v-model="info.salesMarket" :maxlength="200"/>
          </FormItem>
          <FormItem label="品种名称">
            <!-- type 0 品种 病害1 虫害2 -->
            <vuiVariety ref="vuiVariety" :values="info.variety" @on-save="onSaveVariety" @on-save-id="onSaveVarietyId" :num="1" type="0"></vuiVariety>
          </FormItem>
          <div v-if="categoryId == 'CP05' || categoryId == 'CP06'">
            <FormItem label="是否主要农产品">
              <RadioGroup v-model="info.isMainProducts">
                <Radio label="是"></Radio>
                <Radio label="否"></Radio>
              </RadioGroup>
            </FormItem>
          </div>
          <div v-if="categoryId == 'CP01' || categoryId == 'CP05' || categoryId == 'CP06'">
            <FormItem label="产出产品的物种生长环境">
              <Select v-model="info.liveEnvironment">
                <Option v-for="item in liveEnvironmentList" :value="item.value" :key="item.value">{{ item.label }}</Option>
              </Select>
            </FormItem>
            <FormItem label="物种保护级别">
              <Select v-model="info.protectionLevel" @on-change="protectionLevelChange">
                <Option v-for="item in protectionLevelList" :value="item.value" :key="item.value">{{ item.label }}</Option>
              </Select>
            </FormItem>
            <FormItem label="保护证书" v-if="info.protectionLevel && info.protectionLevel != '非保护'">
              <vuiUpload
                ref="protection"
                @on-getPictureList="getProtectionList"
                :total="10"
              ></vuiUpload>
            </FormItem>
          </div>
          <div v-if="categoryId == 'CP05' || categoryId == 'CP06'">
            <FormItem label="是否食用产品或原料">
              <RadioGroup v-model="info.isFood">
                <Radio label="是"></Radio>
                <Radio label="否"></Radio>
              </RadioGroup>
            </FormItem>
            <FormItem label="是否填写热量或营养">
              <RadioGroup v-model="info.isWriteHeat">
                <Radio label="是"></Radio>
                <Radio label="否"></Radio>
              </RadioGroup>
            </FormItem>
            <FormItem label="热量或者营养素" v-if="info.isWriteHeat === '是'">
                <Input v-model="info.heat" :maxlength="50" />
            </FormItem>
            <FormItem label="是否适宜家庭个人消费食用">
              <RadioGroup v-model="info.isSuitFamily">
                <Radio label="是"></Radio>
                <Radio label="否"></Radio>
              </RadioGroup>
            </FormItem>
            <FormItem label="适宜人群">
              <Select v-model="info.suitPeople" clearable multiple>
                <Option v-for="item in suitPeopleList" :value="item.value" :key="item.value">{{ item.label }}</Option>
              </Select>
            </FormItem>
          </div>
          <FormItem label="追溯类型" v-if="categoryId == 'CP01' || categoryId == 'CP05' || categoryId == 'CP06' || categoryId == 'CP07' || categoryId == 'CP08' || categoryId == 'CP09'">
            <Select v-model="info.retrospectType" clearable>
              <Option v-for="item in retrospectTypeList" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
          </FormItem>
          <FormItem prop="productIntroduction" label="商品简介">
            <vuequilleditor ref="detail" :content="info.productIntroduction" @quilCon="getCon" uploadId="detail" myQuillEditor="detail">
            </vuequilleditor>
          </FormItem>
          <FormItem label="上传商品预览图片" prop="notarizationCertificate">
            <vuiUpload
                ref="notarizationCertificate"
                @on-getPictureList="getList"
                :pictureLists="info.notarizationCertificate"
                :hint="'支持jpg/png格式，单张<300K，第一张图片标注为“商品封面图”，最多可上传10张'"
                :total="10"
                :pictureSize="0.3"
            ></vuiUpload>
          </FormItem>
            <view-panel v-if="info.safeFormData.length"
                :edit="false"
                title="添加自定义字段"
                :data="info.safeFormData"
                @on-data="handleGetSafeForm"
                @on-add="handleAddBtn"></view-panel>
        </Form>
        <div class="tc pd20">
          <!-- <Button @click="prve" class="mr30">上一步</Button> -->
          <Button type="primary" @click="next">下一步</Button>
        </div>
      </div>
    </Card>
        <!-- 添加面板 -->
        <add-panel ref="addPanel" @on-save="handleControlBtn"></add-panel>
  </div>
</template>
<script>
import vuiProductName from '~components/vui-productName'
import vuiTrade from '~components/vui-trade'
import vuiCommodity from '~components/vui-commodity'
import vuiSpecies from '~components/vui-species'
import vuiUpload from '~components/vui-upload'
import vuequilleditor from '~components/vuequilEditor'
import vuiVariety from '~components/vui-variety'
import addPanel from '../components/vui-form-control/add-panel'
import viewPanel from '../components/vui-form-control/view-panel'
export default {
  components: {
    vuiProductName,
    vuiTrade,
    vuiCommodity,
    vuiSpecies,
    vuiUpload,
    vuequilleditor,
    vuiVariety,
    addPanel,
    viewPanel
  },
  data () {
    const validateUpload = (rule, value, callback) => {
      if(this.notarizationCertificate && this.notarizationCertificate.length === 0) {
        callback(new Error('请上传商品图片'))
      } else {
        callback()
      }
    }
    return {
      info1: {
        productName: '', // 库存中的 产品名称
        productNameId: '', // 库存中的 产品  id
        commodityName: '', // 通用商品名称
        commodityId: '', // 通用商品名称  id
        productType: '', // 产品分类
        productTypeId: '', // 产品分类  id
        industryType: '', // 行业分类
        industryTypeId: '', // 行业分类  id
        isRelatedToSpecies: '是', // 是否关联物种
        species: '', // 相关物种
        speciesId: '', // 相关物种 id
      },
      info: {
        productName: '', // 库存中的 产品名称
        productNameId: '', // 库存中的 产品  id
        commodityName: '', // 通用商品名称
        commodityId: '', // 通用商品名称  id
        productType: '', // 产品分类
        productTypeId: '', // 产品分类  id
        industryType: '', // 行业分类
        industryTypeId: '', // 行业分类  id
        isRelatedToSpecies: '是', // 是否关联物种
        species: '', // 相关物种
        speciesId: '', // 相关物种 id

        salesMarket: '', // 销售市场
        variety: '', // 品种名称
        varietyId: '', // 品种名称 id
        isMainProducts: '是', // 是否主要农产品
        liveEnvironment: '', // 产出产品的物种生长环境
        protectionLevel: '', // 物种保护级别
        protection: [], // 保护证书
        isFood: '是', // 是否食用产品或原料
        isWriteHeat: '是', // 是否填写热量或营养
        heat: '', // 热量或者营养素
        isSuitFamily: '是', // 是否适宜家庭个人消费食用
        suitPeople: '是', // 适宜人群
        productIntroduction: '', // 商品简介
        notarizationCertificate: [], // 上传商品预览图片
        safeFormData: [], // 自定义表单
      },
      ruleInline: {
        productName: [
          {required: true, message: '请选择产品', trigger: 'change'}
        ],
        commodityName: [
          {required: true, message: '请选择通用商品名称', trigger: 'change'}
        ],
        productType: [
          {required: true, message: '请选择产品分类', trigger: 'change'}
        ],
        species: [
          {required: true, message: '请选择相关物种', trigger: 'change'}
        ],
        productIntroduction: [
          {required: true, message: '请输入商品简介', trigger: 'change'}
        ],
        notarizationCertificate :[
          {required: true, message: '请上传商品图片', trigger: 'change', validator: validateUpload}
        ]
      },
      liveEnvironmentList: [
        {
          label: '陆生',
          value: '陆生'
        },
        {
          label: '水生',
          value: '水生'
        },
        {
          label: '两栖',
          value: '两栖'
        },
        {
          label: '海产',
          value: '海产'
        },
        {
          label: '淡水产',
          value: '淡水产'
        }
      ],
      protectionLevelList: [
        {
          label: '非保护',
          value: '非保护'
        },
        {
          label: '珍稀',
          value: '珍稀'
        },
        {
          label: '国家一级',
          value: '国家一级'
        },
        {
          label: '国家二级',
          value: '国家二级'
        },
        {
          label: '国家三级',
          value: '国家三级'
        }
      ],
      retrospectTypeList: [
        {
          label: '应溯',
          value: '应溯'
        },
        {
          label: '被溯',
          value: '被溯'
        }
      ],
      suitPeopleList: [ // 不限 婴幼儿 少儿 青年 中年 老年 男性 女性
        {
          label: '不限',
          value: '不限'
        }, {
          label: '婴幼儿',
          value: '婴幼儿'
        }, {
          label: '少儿',
          value: '少儿'
        }, {
          label: '青年',
          value: '青年'
        }, {
          label: '中年',
          value: '中年'
        }, {
          label: '老年',
          value: '老年'
        }, {
          label: '男性',
          value: '男性'
        }, {
          label: '女性',
          value: '女性'
        }
      ],
      categoryId: '',
      templateId: '',
      templateType: '',
      templateName: '',
      goodsId: '',
      loading1: false,
      productNames: [],
      productCode: '',
      show: true,
    }
  },
  created() {
    this.goodsId = this.$route.query.goodsId
    this.templateId = this.$route.query.templateId
    this.categoryId = this.$route.query.categoryId
    this.templateType = this.$route.query.templateType
    if (this.$route.query.productName && !this.goodsId) {
      this.info.productName = this.$route.query.productName
      this.productCode = this.$route.query.productCode
      this.loading1 = true
      this.$api.post('/shop/inventory/basicSetting/productList', {
        account: this.$user.loginAccount,
        productName: '',
      }).then(response => {
        if (response.code === 200) {
          this.loading1 = false
          // isComplete 判断是否有发布完成,  0、1，1 表示完成 0 表示未完成
          response.data.forEach(e => {
            if (e.status === '0') {
              e.disabled = true
            } else {
              e.disabled = false
            }
          })
          this.productNames = response.data
          if (this.productNames.length) {
            this.productNames.forEach(e => {
              if (e.productCode == this.productCode) {
                this.info.productNameId = e.id
                this.productNameChange(e.id)
              }
            })
          }
        } else {
          this.$Message.error('服务器异常！')
        }
      })
    } else {
      this.initGoods('')
    }
    if (this.goodsId) {
      this.getInit()
    }
  },
  methods: {
    addProduct () {
      this.$router.push('/inventoryControl/config')

    },
    // remoteMethod1 (query) {
    //   if (query) {
    //     this.loading1 = true;
    //     this.initGoods(query)
    //   } else {
    //     this.productNames = []
    //   }
    // },
    productNameChange (data) {
      this.productNames.forEach((e, index) => {
        if (this.info.productNameId == e.id) {
          this.goodsId = e.goodsId ? e.goodsId : ''
          if (this.goodsId) {
            console.log('000')
            this.$api.post('/shop/pushShopInfo/findPushShopList', {
              account: this.$user.loginAccount,
              commodityName: e.commodityName,
              productCategoryId: e.productCategoryId,
            }).then(response => {
              if (response.code === 200) {
                let list = response.data[0]
                this.templateId = list.id
                this.templateType = list.templateType
                this.categoryId = list.productCategoryId
              } else {
                this.$Message.error('服务器异常！')
              }
            })
            this.getInit()
            return
          }
          // commodityId
          this.info1.productName = e.productName
          this.info1.productNameId = e.id
          this.info1.commodityName = e.commodityName // 通用商品名称
          this.info1.commodityId = e.commodityId
          this.info1.productType = e.classifyName // 产品分类
          this.info1.productTypeId = e.productClassify
          this.info1.industryType = e.industryType // 行业分类
          this.info1.industryTypeId = e.industryTypeId
          this.info1.productCategory = e.productCategory
          this.info1.productCategoryId = e.productCategoryId
          this.info1.isRelatedToSpecies = e.relatedSpecies ? '是' : '否' // 是否关联物种
          this.info1.species = e.relatedSpecies // 相关物种
          this.info1.speciesId = e.relatedSpeciesId // 相关物种 id
          this.getTemplate()
        }
      })
    },
    getTemplate () {
      this.$api.post('/shop/pushShopInfo/findPushShopList', {
        account: this.$user.loginAccount,
        commodityName: this.info1.commodityName,
        productCategoryId: this.info1.productCategoryId,
      }).then(response => {
        if (response.code === 200) {
          let list = response.data[0]
          this.templateId = list.id
          this.templateType = list.templateType
          this.categoryId = list.productCategoryId
          this.$api.post('/shop/pushTemplateTwo/findTemplateList',{
            productCategoryId: list.productCategoryId, 
            templateType: list.templateType, 
            shopPushTemplateId: list.id, 
            account: list.account
          }).then(response => {
            if (response.code == 200) {
              if (response.data.length) {
                if (this.goodsId) {
                  let item = {
                    templateId: this.templateId,
                    goodsId: this.goodsId,
                    productCategoryId: this.categoryId,
                    templateType: this.templateType,
                  }
                  this.$parent.findSetp(item)
                }
                this.info = Object.assign(response.data[0], this.info1)
                this.$nextTick(() => {
                  this.$refs['notarizationCertificate'].handleGive(this.info.notarizationCertificate)
                })
              }
            }
          })
        } else {
          this.$Message.error('服务器异常！')
        }
      })
    },
    // 初始化商品信息
    initGoods (key) {
      this.$api.post('/shop/inventory/basicSetting/productList', {
        account: this.$user.loginAccount,
        productName: key,
      }).then(response => {
        if (response.code === 200) {
          this.loading1 = false
          response.data.forEach(e => {
            if (e.status === '0') {
              e.disabled = true
            } else {
              e.disabled = false
            }
          })
          this.productNames = response.data
        } else {
          this.$Message.error('服务器异常！')
        }
      })
    },
    // 根据 id 回显数据
    getInit () {
      this.$api.post('/shop/pushShopInfo/findPushShopInfo',{
        id: this.goodsId, 
        account: this.$user.loginAccount
      }).then(response => {
        if (response.code == 200) {
          if (response.data.length) {
            this.info = response.data[0]
            // this.remoteMethod1(this.info.productName)
            this.protectionLevelChange()
            this.$nextTick(() => {
              this.$refs['notarizationCertificate'].handleGive(this.info.notarizationCertificate)
            })
          }
        }
      })
    },
    // 添加组件
    handleControlBtn (data) {
        this.info.safeFormData.push(data)
    },
    handleAddBtn () {
      this.$refs.addPanel.showAddPanel = true
    },
    // 取商品安全数据
    handleGetSafeForm (data) {
      console.log(data)
    },
    // 下一步
    next () {
      this.$refs['info'].validate(e => {
        if (e) {
            let list = this.info
            list.shopPushTemplateId = this.templateId
            list.account = this.$user.loginAccount
            list.templateType = this.templateType
            list.productCategoryId = this.categoryId
            this.$api.post('/shop/pushShopInfo/saveShop', list).then(response => {
              if (response.code == 200) {
                this.$Message.success('保存成功！')
                this.$router.push(`/release-goods/step2?goodsId=${response.data.id}&templateId=${this.templateId}&templateType=${this.templateType}&categoryId=${this.categoryId}`)
                // this.$router.push('/good/step3')
              } else {
                this.$Message.error('保存失败！')
              }
            })
        } else {
          this.$Message.error('请核对表单信息！')
        }
      })
    },
    // 相关物种
    onSaveSpecies (e) {
      console.log(e)
      this.info.species = e
    },
    // 相关物种
    onSaveSpeciesId (id) {
      console.log(id)
      this.info.speciesId = id
    },
    // 品种
    onSaveVariety (e) {
      console.log(e)
      this.info.variety = e
    },
    // 品种id
    onSaveVarietyId (id) {
      console.log(id)
      this.info.varietyId = id
    },
    // 是否保护级别 改变
    protectionLevelChange () {
      this.$nextTick(() => {
        if (this.info.protectionLevel && this.info.protectionLevel != '非保护') {
          this.$refs['protection'].handleGive(this.info.protection)
        }
      })
    },
    // 保护证书
    getProtectionList (e) {
      var arr = []
      e.forEach(element => {
            if(element.response){
              arr.push(element.response.data.picName)
          }
      })
      this.info.protection = arr
    },
    // 商品预览图片
    getList (e) {
      var arr = []
      e.forEach(element => {
            if(element.response){
              arr.push(element.response.data.picName)
          }
      })
      this.info.notarizationCertificate = arr
    },
    // 商品简介
    getCon (msg) {
      this.info.productIntroduction = msg
    }
  },
}
</script>