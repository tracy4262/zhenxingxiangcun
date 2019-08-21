<template>
  <Card>
    <p class="pd20 template-name">{{templateName}}</p>
    <div style="width: 800px; margin: 0 auto;" class="pt30 pl10 pr10">
      <Form ref="data" :model="data" label-position="left" :label-width="150" :rules="ruleInline">
        <Row :gutter="32">
          <Col span="24">
            <Form-item prop="assetsName" label="资产名称">
              <Input v-model="data.assetsName" :maxlength="30" />
            </Form-item>
          </Col>
          <Col span="24">
            <Form-item prop="assetOwner" label="资产所属方">
              <Input v-model="data.assetOwner" :maxlength="200" />
            </Form-item>
          </Col>
          <Col span="24">
            <Form-item prop="businessLocation" label="商家所在地">
                <vui-cascander :values="data.businessLocation" @handle-get-result="handleGetData"></vui-cascander>
            </Form-item>
          </Col>
          <Col span="24">
            <Form-item prop="assetLocation" label="资产适用地区">
                <vui-cascander :values="data.assetLocation" @handle-get-result="handleGetData"></vui-cascander>
            </Form-item>
          </Col>
          <Col span="24">
          <Form-item prop="assetIntroduction" label="资产简介">
              <Input v-model="data.assetIntroduction" type="textarea" :autosize="{minRows: 3,maxRows: 4}" :maxlength="300"></Input>
          </Form-item>
          </Col>
          <Col span="24">
          <Form-item prop="assetFeature" label="资产特点">
              <Input v-model="data.assetFeature" type="textarea" :autosize="{minRows: 3,maxRows: 4}" :maxlength="300"></Input>
          </Form-item>
          </Col>
          <Col span="24">
          <Form-item prop="assetCondition" label="资产所需条件">
              <Input v-model="data.assetCondition" type="textarea" :autosize="{minRows: 3,maxRows: 4}" :maxlength="300"></Input>
          </Form-item>
          </Col>
          <Col span="24">
          <Form-item prop="remind" label="重要提醒">
              <Input v-model="data.remind" type="textarea" :autosize="{minRows: 3,maxRows: 4}" :maxlength="300"></Input>
          </Form-item>
          </Col>
        </Row>
        <view-panel title="添加自定义字段" :data="data.customData" @on-data="handleGetSafeForm" @on-add="handleAddBtn"></view-panel>
      </Form> 
      <div class="tc pt30">
        <Button @click="handleBack" class="mr30">上一步</Button>
        <Button type="primary" @click="handleNext">下一步</Button>
      </div>
      <!-- 添加面板 -->
      <add-panel ref="addPanel" @on-save="handleControlBtn"></add-panel>
    </div>
  </Card>
</template>
<script>
import vuiCascander from '~components/vuiCascader/index'
import addPanel from '../../../goods/components/vui-form-control/add-panel'
import viewPanel from '../../../goods/components/vui-form-control/view-panel'
export default {
  components: {
    addPanel,
    viewPanel,
    vuiCascander
  },
  data() {
    return {
      data: {
        assetsName: '', // 资产名称
        assetOwner: '', // 资产所属方
        businessLocation: '', // 商家所在地
        assetLocation: '', // 资产适用地区
        assetIntroduction: '', // 资产简介
        assetFeature: '', // 资产特点
        assetCondition: '', // 资产所需条件
        remind: '', // 重要提醒
        customData: [], // 自定义 字段
      },
      ruleInline: {
        assetsName: [
          { required: true, message: '请输入', trigger: 'blur' }
        ],
        businessLocation: [
          { required: true, message: '请选择商家所在地', trigger: 'blur' }
        ],
        assetLocation: [
          { required: true, message: '请选择资产适用地区', trigger: 'blur' }
        ],
        assetIntroduction: [
          { required: true, message: '请输入资产简介', trigger: 'blur' }
        ]
      },
      categoryId: '',
      templateId: '',
      templateType: '',
      templateName: '',
    }
},
created() {
    this.categoryId = this.$route.query.categoryId
    this.templateId = this.$route.query.templateId
    this.templateType = this.$route.query.templateType
    this.templateName = this.$route.query.templateName
    this.account = this.$user.loginAccount
    this.handleInit()
  },
  methods: {
    // 初始化查询
    handleInit  () {
      this.$api.post('/shop/pushTemplateThree/findTemplateList',{
        productCategoryId: this.categoryId, 
        templateType: this.templateType, 
        shopPushTemplateId: this.templateId, 
        account: this.$user.loginAccount
      }).then(response => {
        if (response.code == 200) {
          if (response.data.intangibleAssets.length) {
            this.data = response.data.intangibleAssets[0]
          }
        }
      })
    },
    // 添加组件
    handleControlBtn(data) {
      this.data.customData.push(data)
    },
    handleAddBtn() {
      this.$refs.addPanel.showAddPanel = true
    },
    // 取商品安全数据
    handleGetSafeForm(data) {
      console.log(data)
    },
    // 保存
    handleNext () {
      let list = {
        account: this.$user.loginAccount, 
        shopPushTemplateId: this.templateId,
        templateType: this.templateType,
        productCategoryId: this.categoryId,
        intangibleAssets: this.data
      }
      this.$api.post('/shop/pushTemplateThree/savePush',list).then(response =>{
        if(response.code == 200) {
          this.$Message.success('保存成功')
            this.$router.push(`/good/step4?templateId=${this.templateId}&templateType=${this.templateType}&categoryId=${this.categoryId}&templateName=${this.templateName}`)
        } else {
          this.$Message.error('保存失败')
        }
      })
    },
    // 上一步
    handleBack(){
      this.$router.push(`/good/step2?templateId=${this.templateId}&templateType=${this.templateType}&categoryId=${this.categoryId}&templateName=${this.templateName}`)
    },
    //地区
    handleGetData(value, selectedData) {
      let labelArr = []
      selectedData.forEach(element => {
        labelArr.push(element.label)
      })
      this.data.productOrigin = labelArr.join('/')
    }
  }
}
</script>
