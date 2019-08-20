<template>
  <Card class="pt30">
    <div v-if="step1" style="width: 800px; margin: 0 auto;">
        <Form ref="info" :model="info" :label-width="200" label-position="right" :rules="ruleInline">
            <FormItem prop="commodityName" label="通用商品名称">
                <Input v-model="info.commodityName" placeholder="请输入内容" :disabled="edit" :readonly="id ? true : false" @on-change="getPinyin" @on-blur="checkName" :maxlength="20" />
            </FormItem>
            <FormItem label="通用商品名拼音">
                <Input v-model="info.commodityPinyin" :disabled="edit" placeholder="由通用商品名称自动生成拼音" readonly />
            </FormItem>
            <FormItem label="俗名别名">
                <Input v-model="info.commodityAlias" :disabled="edit" placeholder="请输入内容" />
            </FormItem>
            <FormItem label="行业分类">
               <vuiTrade :values="info.industryType" :disabled="edit" ref="vuiTrade" @on-save="onSaveTrade" @on-save-id="onSaveTradeId" :num="1"></vuiTrade>
                <!-- <Input v-model="info.industryType" readonly @on-focus="handleFilterModal('tradeFilter')" /> -->
            </FormItem>
            <FormItem prop="productType" label="产品分类">
              <vuiProduct :values="info.productType" :disabled="edit" ref="vuiSpecies" @on-save="onSaveProduct" @on-save-id="onSaveProductId" :num="1"></vuiProduct>
                <!-- <Input v-model="info.productType" readonly @on-focus="handleFilterModal('productTypeFilter')" /> -->
            </FormItem>
            <FormItem label="关联物种">
              <vuiSpecies :values="info.relatedSpecies" :disabled="edit" ref="vuiSpecies" @on-save="onSaveSpecies" @on-save-id="onSaveSpeciesId" :num="1"></vuiSpecies>
                <!-- <Input v-model="info.relatedSpecies" readonly @on-focus="handleFilterModal('speciFilter')" /> -->
            </FormItem>
            <FormItem label="是否主要农产品">
                <RadioGroup v-model="info.isMainProduct" >
                    <Radio label="是" :disabled="edit"></Radio>
                    <Radio label="否" :disabled="edit"></Radio>
                </RadioGroup>
            </FormItem>
            <FormItem label="追溯类型">
                <Select v-model="info.traceaType" clearable :disabled="edit">
                <Option v-for="item in traceaTypeList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                </Select>
            </FormItem>
            <FormItem label="释义">
                <Input v-model="info.remark" :disabled="edit" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..." />
            </FormItem>
            <FormItem label="上传商品预览图片">
                <vui-upload
                    :disabled="edit"
                    ref="pic"
                    @on-getPictureList="getList"
                    :pictureLists="info.image"
                    :hint="'支持jpg/png格式，最多可上传10张'"
                    :total="10"
                ></vui-upload>
            </FormItem>
        </Form>
        <view-panel
            title="质量信息表单"
            :edit="!edit"
            :disabled="edit"
            :data="qualityFormData"
            @on-data="handleGetQualityForm"
            @on-add="handleAddBtnAddPanel('quality')" class="mt20" style="margin-left: 100px;"></view-panel>
        <view-panel
            :edit="!edit"
            :disabled="edit"
            title="安全标准表单"
            :data="safeFormData"
            @on-data="handleGetSafeForm"
            @on-add="handleAddBtnSecurity('safe')" class="mt20" style="margin-left: 100px;"></view-panel>
        <div class="tc mt20 mb40">
            <Button type="primary" @click="next" v-if="!edit">提交</Button>
            <Button type="default" class="ml20" @click="complete">退出</Button>
        </div>
    </div>
    <div v-else class="tc mb40">
        <h2>您已提交新的通用商品信息，审核工作将在<strong>三个工作日</strong>内完成，请耐心等待</h2>
        <Button type="primary" class="mt20 mb40" @click="complete">完成</Button>
    </div>
        <add-panel ref="addPanel" @on-save="handleControlBtn"></add-panel>
        <add-panel-security ref="security" @on-save="handleControlBtn"></add-panel-security>
  </Card>
</template>

<script>
import vuiUpload from '~components/vui-upload'
import addPanel from '../../../goods/components/vui-form-control/add-panel'
import addPanelSecurity from '../../../goods/components/vui-form-control/add-panel-security'
import viewPanel from './vui-form-control/view-panel'
import vuiSpecies from '~components/vui-species'
import vuiTrade from '~components/vui-trade'
import vuiProduct from '~components/vui-product'
import vuiProductName from '~components/vui-productName'
export default {
    components: {
        vuiUpload,
        addPanel,
        viewPanel,
        addPanelSecurity,
        vuiSpecies,
        vuiTrade,
        vuiProduct,
        vuiProductName
    },
    data () {
        return {
            step1: true,
            info: {
                commodityName : '',  //通用商品名称
                commodityPinyin : '', //通用商品名拼音
                commodityAlias : '', //俗名别名
                industryType: '',
                industryTypeId : '', //行业分类id
                productType: '',
                productTypeId : '', //产品分类id
                relatedSpecies: '',
                relatedSpeciesId : '', //关联物种id
                remark : '',  //释义
                image: [],  //图册
                traceaType: '',  //追溯类型
                isMainProduct: '否' //是否主要农产品
            },
            ruleInline: {
                commodityName:[
                    { required: true, message: '请填写通用商品名称', trigger:'blur' }
                ],
                productType:[
                    { required: true, message: '请选择产品分类', trigger:'change' }
                ]
            },
            traceaTypeList: [
                {
                label: '应溯',
                value: '应溯'
                },
                {
                label: '被溯',
                value: '被溯'
                }
            ],
            safeFormData: [],
            qualityFormData: [],
            id:'',
            edit: true,
            type: '1'
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
                  id: this.id
              }).then(response => {
                  if (response.code === 200) {
                      this.info = response.data
                      this.account = this.$user.loginAccount
                      this.$refs.pic.handleGive(this.info.image)
                      this.safeFormData = response.data.safetyCustomContent
                      this.qualityFormData = response.data.qualityCustomContent
                  }
              }).catch(error => {
                  this.$Message('查询通用商品出错！')
              })
        },
        next () {
            this.$refs['info'].validate((valid) => {
                if (valid) {
                    if (this.qualityFormData.length === 0) {
                        this.$Message.error('请添加质量信息表单！')
                        return
                    }
                    if (this.safeFormData.length === 0) {
                        this.$Message.error('请添加安全标准表单！')
                        return
                    }
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
            this.$router.push('/nameLibrary/commodity')
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
                            this.$Message.error('该通用商品名已存在，请重新输入通用商品名！')
                            this.info.commodityName = ''
                            this.info.commodityPinyin = ''
                        }
                    }
                }).catch(error => {
                    this.$Message('查询通用商品名出错！')
                })
            }
        },
        getList (e) {
            var arr = []
            e.forEach(element => {
                    if(element.response){
                    arr.push(element.response.data.picName)
                }
            })
            this.info.image = arr
        },
        // 取商品质量数据
        handleGetQualityForm (data) {
            console.log(data)
        },
        // 取商品安全数据
        handleGetSafeForm (data) {
            console.log(data)
        },
        // 质量信息添加
        handleAddBtnAddPanel (name) {
            this.$refs.addPanel.showAddPanel = true
            this.name = name
        },
        // 安全标准添加
        handleAddBtnSecurity (name) {
            this.$refs.security.showAddPanel = true
            this.name = name
        },
        // 添加组件
        handleControlBtn (data) {
            if (this.name === 'safe') {
                this.safeFormData.push(data)
            } else {
                this.qualityFormData.push(data)
            }
        },
        // 行业分类
        onSaveTrade (e) {
          this.info.industryType = e
        },
        onSaveTradeId (e) {
          this.info.industryTypeId = e
        },
        // 产品 分类
        onSaveProduct (e) {
          this.info.productType = e
        },
        onSaveProductId (e) {
          this.info.productTypeId = e
          console.log(this.info)
        },
        // 物种
        onSaveSpecies (e) {
          this.info.relatedSpecies = e
          console.log(this.info)
        },
        onSaveSpeciesId (e) {
          this.info.relatedSpeciesId = e
          console.log(this.info)
        }
    }
}
</script>
