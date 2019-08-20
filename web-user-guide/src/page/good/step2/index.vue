<template>
  <div class="layouts">
    <Card>
      <p class="pd20 template-name">{{templateName}}</p>
      <div style="width: 800px; margin: 0 auto;" class="pt30 pb20">
        <Form :model="info" label-position="left" ref="info" :label-width="180" :rules="ruleInline">
          <FormItem label="从库存中选择产品" prop="productName"v-if="show" >
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
                <Option v-for="(item, index) in productNames" :value="item.id" :key="index">{{ item.productName }}</Option>
            </Select>
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
          <FormItem label="销售市场">
             <Input v-model="info.salesMarket" :maxlength="200"/>
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
              <Select v-model="info.protectionLevel">
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
            <view-panel
                title="添加自定义字段"
                :data="info.safeFormData"
                @on-data="handleGetSafeForm"
                @on-add="handleAddBtn"></view-panel>
        </Form>
        <div class="tc pd20">
          <Button @click="prve" class="mr30">上一步</Button>
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
import addPanel from '../../goods/components/vui-form-control/add-panel'
import viewPanel from '../../goods/components/vui-form-control/view-panel'
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
        variety: '', // 品种名称
        varietyId: '', // 品种名称id
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
          {required: true, message: '请输入上传商品图片', trigger: 'change', validator: validateUpload}
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
      suitPeopleList: [ // 不限/婴幼儿/少儿/青年/中年/老年/男性/女性
        {
          label: '不限',
          value: '不限'
        },{
          label: '婴幼儿',
          value: '婴幼儿'
        },{
          label: '少儿',
          value: '少儿'
        },{
          label: '青年',
          value: '青年'
        },{
          label: '中年',
          value: '中年'
        },{
          label: '老年',
          value: '老年'
        },{
          label: '男性',
          value: '男性'
        },{
          label: '女性',
          value: '女性'
        }
      ],
      categoryId: '',
      templateId: '',
      templateType: '',
      templateName: '',
      loading1: false,
      productNames: [],
      show: true
    }
  },
  created() {
    this.categoryId = this.$route.query.categoryId
    this.templateId = this.$route.query.templateId
    this.templateType = this.$route.query.templateType
    this.templateName = this.$route.query.templateName
    console.log('this.categoryId', this.categoryId)
    this.getInit()
  },
  methods: {
    productNameFocus () {
      this.show = false
      this.remoteMethod1(this.info.productName)
    },
    remoteMethod1 (query) {
      if (query) {
        this.loading1 = true;
        this.initGoods(query)
      } else {
        this.productNames = []
      }
    },
    // 初始化商品信息
    initGoods (key) {
      this.$api.post('/shop/inventory/basicSetting/productList', {
        account: this.$user.loginAccount,
        productName: key,
      }).then(response => {
        if (response.code === 200) {
          this.loading1 = false
          this.productNames = response.data
        } else {
          this.$Message.error('服务器异常！')
        }
      }).catch(error => {
        this.$Message.error('服务器异常！')
      })
    },
    productNameChange (data) {
      this.productNames.forEach((e, index) => {
        if (this.info.productNameId == e.id) {
          // commodityId
          this.info.productName = e.productName
          this.info.productNameId = e.id
          this.info.commodityName = e.commodityName // 通用商品名称
          this.info.commodityId = e.commodityId
          this.info.productType = e.classifyName // 产品分类
          this.info.productTypeId = e.productClassify
          this.info.industryType = e.industryType // 行业分类
          this.info.industryTypeId = e.industryTypeId
          this.info.productCategory = e.productCategory
          this.info.productCategoryId = e.productCategoryId
          this.info.isRelatedToSpecies = e.relatedSpecies ? '是' : '否' // 是否关联物种
          this.info.species = e.relatedSpecies // 相关物种
          this.info.speciesId = e.relatedSpeciesId // 相关物种 id
        }
      })
    },
    // 查询第二步的数据
    getInit () {
      this.$api.post('/shop/pushTemplateTwo/findTemplateList',{
        productCategoryId: this.categoryId, 
        templateType: this.templateType, 
        shopPushTemplateId: this.templateId, 
        account: this.$user.loginAccount
      }).then(response => {
        if (response.code == 200) {
          console.log(response)
          if (response.data.length) {
            this.info = response.data[0]
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
    prve () {
      this.$router.push(`/good/step1?templateName=${this.templateName}&templateId=${this.templateId}&templateType=${this.templateType}&categoryId=${this.categoryId}`)
    },
    // 下一步
    next () {
      // this.$refs['info'].validate(e => {
      //   if (e) {
          this.info.shopPushTemplateId = this.templateId
          this.info.account = this.$user.loginAccount
          this.info.templateType = this.templateType
          this.info.productCategoryId = this.categoryId
          this.$api.post('/shop/pushTemplateTwo/savePush',this.info).then(response => {
            if (response.code == 200) {
              this.$Message.success('保存成功！')
              this.$router.push(`/good/step3?templateName=${this.templateName}&templateId=${this.templateId}&templateType=${this.templateType}&categoryId=${this.categoryId}`)
              // this.$router.push('/good/step3')
            } else {
              this.$Message.error('保存失败！')
            }
          })
      //   } else {
      //     this.$Message.error('请核对表单信息！')
      //   }
      // })
    },
    // 通用商品名称
    onSaveCommodity (list) {
      console.log(list)
      let id = ''
      if (list.length) {
        this.info.commodityName = list[0].label
        id = list[0].commodityId
        this.$api.post('/portal/currencyCommodity/findCommodityById',{id: id}).then(response => {
          if (response.code === 200) {
            this.info.industryType = response.data.industryType
            this.info.productType = response.data.productType
          } else {
            this.$Message.error('查询失败')
          }
        })
      } else {
        this.info.commodityName = ''
        this.info.industryType = ''
        this.info.productType = ''
      }
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