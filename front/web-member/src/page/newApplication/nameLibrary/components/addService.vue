<template>
  <Card class="pt30">
    <div v-if="step1" style="width: 800px; margin: 0 auto;">
        <Form ref="info" :model="info" :label-width="200" label-position="right" :rules="ruleInline">
            <FormItem prop="commodityName" label="通用服务名称">
                <Input v-model="info.commodityName" placeholder="请输入内容" :disabled="edit" :readonly="id ? true : false" @on-change="getPinyin" @on-blur="checkName" :maxlength="20" />
            </FormItem>
            <FormItem label="通用服务名拼音">
                <Input v-model="info.commodityPinyin" :disabled="edit" placeholder="由通用服务名称自动生成拼音" readonly />
            </FormItem>
            <FormItem label="俗名别名">
                <Input v-model="info.commodityAlias" :disabled="edit" placeholder="请输入内容" />
            </FormItem>
            <FormItem label="行业分类">
               <vuiTrade 
                  :values="info.industryType" 
                  :disabled="edit" 
                  ref="vuiTrade" 
                  @on-save="onSaveTrade" 
                  @on-save-id="onSaveTradeId" :num="1"></vuiTrade>
            </FormItem>
            <FormItem prop="serviceClass" label="服务分类">
              <vuiProduct 
                  :values="info.serviceClass" 
                  :disabled="edit" 
                  ref="vuiSpecies" 
                  @on-save="onSaveserviceClass" 
                  @on-save-id="onSaveserviceClassId" :num="1"></vuiProduct>
            </FormItem>
            <FormItem label="关联物种">
              <vuiSpecies 
                  :values="info.relatedSpecies" 
                  :disabled="edit" 
                  ref="vuiSpecies" 
                  @on-save="onSaveSpecies" 
                  @on-save-id="onSaveSpeciesId" :num="1"></vuiSpecies>
            </FormItem>
            <FormItem prop="productType" label="关联产品">
              <vuiProduct 
                  :values="info.productType" 
                  :disabled="edit" 
                  ref="vuiSpecies" 
                  @on-save="onSaveProduct" 
                  @on-save-id="onSaveProductId" :num="1"></vuiProduct>
            </FormItem>
            <FormItem label="关联服务">
              <vuiCommodity 
                  ref="vuiCommodity" 
                  :values="info.service" 
                  :disabled="edit" 
                  @on-save="onSaveService" :num="1" type="2"></vuiCommodity>
            </FormItem>
            <FormItem label="服务对象">
              <Input v-model="info.servicePeopleName" :disabled="edit" :maxlength="20"/>
            </FormItem>
            <FormItem label="描述">
                <Input v-model="info.remark" :disabled="edit" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..." />
            </FormItem>
        </Form>
        <div class="tc mt20 mb40">
            <Button type="primary" @click="next" v-if="!edit">提交</Button>
            <Button type="default" class="ml20" @click="complete">退出</Button>
        </div>
    </div>
    <div v-else class="tc mb40">
        <h2>您已提交新的通用商品信息，审核工作将在<strong>三个工作日</strong>内完成，请耐心等待</h2>
        <Button type="primary" class="mt20 mb40" @click="complete">完成</Button>
    </div>
  </Card>
</template>

<script>
import vuiSpecies from '~components/vui-species'
import vuiTrade from '~components/vui-trade'
import vuiProduct from '~components/vui-product'
import vuiProductName from '~components/vui-productName'
import vuiCommodity from '~components/vui-commodity'
export default {
    components: {
        vuiSpecies,
        vuiTrade,
        vuiProduct,
        vuiProductName,
        vuiCommodity
    },
    data () {
        return {
            step1: true,
            info: {
                commodityName : '',  // 通用服务名称
                commodityPinyin : '', // 通用服务名拼音
                commodityAlias : '', //俗名别名
                industryType: '', // 行业分类
                industryTypeId : '', // 行业分类id

                serviceClass: '', // 服务分类
                serviceClassId: '', // 服务分类 id

                relatedSpecies: '', // 关联物种 
                relatedSpeciesId : '', //关联物种id

                productType: '', // 关联产品
                productTypeId : '', // 关联产品 id

                service: '', // 关联服务
                serviceId : '', // 关联服务 id
                servicePeopleName: '', // 服务对象
                remark : '',  // 描述
                image: [], 
            },
            ruleInline: {
                commodityName:[
                    { required: true, message: '请填写通用服务名称', trigger:'blur' }
                ],
                serviceClass:[
                    { required: true, message: '请选择产品分类', trigger:'change' }
                ]
            },
            id:'',
            edit: true,
            type: '2'
        }
    },
    created () {
        if (this.$route.query.id) {
            console.log('9999')
            this.id = this.$route.query.id
            this.getData()
        }
        if (this.$route.query.edit) {
            this.edit = true
        } else{
            this.edit = false
        }
    },
    methods: {
        getData () {
          this.$api.post('/portal/currencyCommodity/findCommodityById', {
                  id: this.id,
                  type: this.type
              }).then(response => {
                  if (response.code === 200) {
                      this.info = response.data
                  }
              }).catch(error => {
                  this.$Message('查询通用商品出错！')
              })
        },
        next () {
            this.$refs['info'].validate((valid) => {
                if (valid) {
                    this.info.type = this.type
                    this.info.safetyCustomContent = this.safeFormData
                    this.info.qualityCustomContent = this.qualityFormData
                    this.info.account = this.$user.loginAccount
                    console.log('data', this.info)
                    this.$api.post('/portal/currencyCommodity/saveOrUpdate', this.info).then(response => {
                        console.log('res', response)
                        if (response.code === 200) {
                            this.$Message.success('保存成功！')
                            this.step1 = false
                        }
                    }).catch(error => {
                        this.$Message.error('新增通用商品出错！')
                    })
                }
            })
        },
        complete () {
            this.$router.push('/nameLibrary/service')
        },
        // 得到汉字的拼音
        getPinyin () {
            if (this.info.commodityName !== '') {
                this.$api.get('/wiki/api/species/getSpeciesPinYin/' + this.info.commodityName).then(response => {
                    this.info.commodityPinyin = ''
                    this.info.commodityPinyin = response.data
                }).catch(error => {
                    this.$Message.error('拼音转换出错！')
                })
            } else {
                this.info.commodityPinyin = ''
            }
        },
        checkName () {
            if (this.info.commodityName !== '' && !this.id) {
                this.$api.post('/portal/currencyCommodity/isExist', {
                    fullName: this.info.commodityName,
                    type: this.type
                }).then(response => {
                    console.log('res', response)
                    if (response.code === 200) {
                        if (response.data) {
                            this.$Message.error('该通用服务名已存在，请重新输入通用服务名！')
                            this.info.commodityName = ''
                            this.info.commodityPinyin = ''
                        }
                    }
                }).catch(error => {
                    this.$Message('查询通用服务名出错！')
                })
            }
        },
        // 行业分类
        onSaveTrade (e) {
          this.info.industryType = e
        },
        onSaveTradeId (e) {
          this.info.industryTypeId = e
        },
        // 服务分类
        onSaveserviceClass (e) {
          this.info.serviceClass = e
        },
        onSaveserviceClassId (e) {
          this.info.serviceClassId = e
        },
        // 关联物种
        onSaveSpecies (e) {
          this.info.relatedSpecies = e
          console.log(this.info)
        },
        onSaveSpeciesId (e) {
          this.info.relatedSpeciesId = e
          console.log(this.info)
        },
        // 关联产品 分类
        onSaveProduct (e) {
          this.info.productType = e
        },
        onSaveProductId (e) {
          this.info.productTypeId = e
          console.log(this.info)
        },
        // 关联服务
        onSaveService (e) {
          let arr = []
          let arrId = []
          if (e) {
            e.forEach(e => {
                arr.push(e.label)
                arrId.push(e.value)
            })
          }
          this.info.service = arr.join(' ')
          this.info.serviceId = arrId.join(' ')
        }
    }
}
</script>
