<template>
  <div class="bg-white pt30">
    <Row type="flex" justify="center" class="mb30">
      <Col span="16">
        <Form ref="info" :model="info" label-position="left" :label-width="180" :rules="ruleInline">
          <FormItem prop="integrity" label="诚信承诺">
            <RadioGroup v-model="info.integrity" @on-change="info.money = 1">
              <Radio label="是"></Radio>
              <Radio label="否"></Radio>
            </RadioGroup>
          </FormItem>
        </Form>
        <Card v-if="info.integrity === '是'">
            <p slot="title" class="tc">产品质量标准承诺书</p>
            <p> 
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;为向消费者提供安全放心产品，完善监督服务体系，本公司(人)郑重承诺： 本公司(人)在本网站上所发布的产品质量标准严格依据国家标准进行公布，并对标准以外可能影响产品品质的所有信息进行公开。本公司(人)确保所发布信息的真实性和完整性，并愿为之承担相应法律责任，接受各方监督。 消费者若认为本公司(人)所公布信息不完整，请及时与本公司(人)联系，我们会慎重对待您的意见并予以回复。若发现所购产品与公布的产品标准不符或有重大纰漏，也请及时与本公司(人)联系或向有关部门举报，一经查实，本公司(人)除按国家赔偿标准进行赔偿外，另给予因此产生的<br/>
              交通费（当地的士费标准）、<br/>
              误工费（当地最低工资标准）、<br/>
              检测费（法定检测机构检测费用标准）、<br/>
              所购产品价格 <InputNumber :max="1000000000" :min="1" v-model="info.money" size="small" style="width: 150px;"/> 倍之和的额外补偿。<br/>
              提供放心的产品是我们永恒的追求，欢迎社会各界进行监督。
            </p>
        </Card>
        <div class="tc mt20 mb20">
          <Button type="primary" @click="last">上一步</Button>
          <Button type="primary" @click="next">完成</Button>
        </div>
      </Col>
    </Row>
  </div>
</template>
<script>
export default {
  data () {
    return {
      info: {
        integrity: '是',
        money: 1
      },
      ruleInline: {
        integrity: [
          { required: true }
        ]
      },
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
    this.init()
  },
  methods: {
    init () {
      this.$api.post('/shop/commodityCommitment/find', {
        account: this.$user.loginAccount,
        shopPushTemplateId: this.templateId,
        productCategoryId: this.categoryId,
        templateType: this.templateType
      }).then(response => {
        console.log('init response', response)
        if (response.code === 200 && response.data.info) {
          this.info = response.data.info
        }
      }).catch(error => {
        this.$Message.error('服务器异常失败！')
      })
    },
    last () {
      if (this.categoryId === 'CP09') {
        this.$router.push({
          path: '/good/step4',
          query: {
            categoryId: this.categoryId,
            templateId: this.templateId,
            templateType: this.templateType,
            templateName: this.templateName
          }
        })
      } else {
        this.$router.push({
          path: '/good/step5',
          query: {
            categoryId: this.categoryId,
            templateId: this.templateId,
            templateType: this.templateType,
            templateName: this.templateName
          }
        })
      }
    },
    next () {
      let list = {
        account: this.$user.loginAccount,
        shopPushTemplateId: this.templateId,
        templateType: this.templateType,
        productCategoryId: this.categoryId,
        info: this.info
      }
      this.$api.post('/shop/commodityCommitment/save', list).then(response => {
        console.log('response', response)
        if (response.code === 200) {
          this.$Message.success('保存成功！')
          this.$router.push('/good/step1')
        }
      }).catch(error => {
        this.$Message.error('保存失败！')
      })
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
