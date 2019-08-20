<template>
  <div>
    <Card class="layout">
      <div class="pd20">
        <Title title="网站基本信息"></Title>
        <Form ref="websiteInfo" :model="websiteInfo" label-position="left" :label-width="150" :rules="ruleInline" class="mt40 ml20">
          <Row :gutter="32">
            <Col span="12">
              <Form-item prop="websiteName" class="input" label="网站名称">
                <Input v-model="websiteInfo.websiteName" :maxlength="100" />
              </Form-item>
            </Col>
            <Col span="6">
                <Input v-model="websiteInfo.nameSuffix" :maxlength="20" :readonly="templateId !== '0'" :disabled="templateId !== '0'" />
            </Col>
            <Col span="6">
              <Switch size="large" v-model="websiteInfo.isShowWebsiteName" class="mt5">
                  <span slot="open">显示</span>
                  <span slot="close">隐藏</span>
              </Switch>
            </Col>
          </Row>
          <Row :gutter="32">
            <Col span="24">
              <Form-item class="input" label="网站LOGO">
                <vui-upload
                ref="logo"
                @on-getPictureList="getLOGOList"
                :hint="'图片大小小于2MB'"
                :total="1"
                :size="[80,80]"
                ></vui-upload>
              </Form-item>
            </Col>
          </Row>
          <Row :gutter="32">
            <Col span="24">
              <Form-item class="input" label="网站横幅">
                <vui-upload
                ref="banner"
                @on-getPictureList="getBannerList"
                :hint="'图片大小小于2MB，最佳显示效果800px * 80px'"
                :total="1"
                :size="[756,80]"
                ></vui-upload>
              </Form-item>
            </Col>
          </Row>
          <Row :gutter="32">
            <Col span="24">
              <Form-item prop="websiteProfile" label="网站简介">
                <Input v-model="websiteInfo.websiteProfile" type="textarea" :autosize="{minRows: 3,maxRows: 5}" :maxlength="500" />
              </Form-item>
            </Col>
          </Row>
        </Form>
        <div class="tc pd20">
          <Button type="primary" @click="handleClickBack" class="back-btn mr20">返回上一步</Button>
          <Button type="primary" @click="handleClickNext">保存并下一步</Button>
        </div>
      </div>
    </Card>
  </div>
</template>
<script>
import Title from '../../components/title'
import vuiUpload from '~components/vui-upload'
export default {
  components: {
    Title,
    vuiUpload
  },
  data: () => ({
    websiteInfo: {
      websiteName: '',
      nameSuffix: '',
      isShowWebsiteName: true,
      websiteLOGO: '',
      websiteBanner: '',
      websiteProfile: ''
    },
    templateId: '',
    ruleInline: {
      websiteName: [
        { required: true, message: '请填写网站名称', trigger: 'blur' }
      ],
      websiteProfile: [
        { required: true, message: '请填写网站简介', trigger: 'blur' }
      ]
    },
    stepId:''
  }),
  created () {
    this.templateId = this.$route.query.templateId
    // 查询模板 步骤
    this.$api.post('/member-reversion/realStep/findStep', {
          account: this.$user.loginAccount,
          templateId: this.templateId
      }).then(response => {
          if (response.code === 200) {
              // reload 存在， 说明是点击模板后查询的 如果 response.data 存在 那么则需要修改url
              if (response.data) {
                  this.stepId = response.data.id
              }
          }
      })
    this.init()
  },
  methods: {
    init () {
      // url若为0则调用管理员侧的接口，不为0则调用用户侧的接口
      let url = this.templateId === '0' ? '/member-reversion/websiteSettings/findWebsiteSettingsInfo' : '/member-reversion/user/websiteSettings/findWebsiteSettingsInfo'
      this.$api.post(url, {
        account: this.$user.loginAccount,
        templateId: this.templateId
      }).then(response => {
        console.log('website init response', response)
        if (response.code === 200) {
          if (response.data.websiteInfo) {
            this.websiteInfo = response.data.websiteInfo
            // this.templateId = response.data.templateId
            if (response.data.websiteInfo.websiteLOGO) {
              let logoArr = [{
                  response: {
                      data: {
                          picName: response.data.websiteInfo.websiteLOGO
                      }
                  },
                  status: 'finished'
              }]
              this.$refs.logo.pictureList = logoArr
              this.$refs.logo.$refs.upload.fileList = logoArr
            }
            if (response.data.websiteInfo.websiteBanner) {
              let bannerArr = [{
                  response: {
                      data: {
                          picName: response.data.websiteInfo.websiteBanner
                      }
                  },
                  status: 'finished'
              }]
              this.$refs.banner.pictureList = bannerArr
              this.$refs.banner.$refs.upload.fileList = bannerArr
            }
          }
        }
      })
    },
    getLOGOList (list) {
      if (list.length > 0) {
        this.websiteInfo.websiteLOGO = list[0].response.data.picName
      } else {
        this.websiteInfo.websiteLOGO = ''
      }
    },
    getBannerList (list) {
      if (list.length > 0) {
        this.websiteInfo.websiteBanner = list[0].response.data.picName
      } else {
        this.websiteInfo.websiteBanner = ''
      }
    },
    // 上一步
    handleClickBack () {
      this.$emit('on-back', this.templateId)
    },
    // 下一步
    handleClickNext () {
      this.$refs['websiteInfo'].validate((valid) => {
        if (valid) {
            // url若为0则调用管理员侧的接口，不为0则调用用户侧的接口
            let url = this.templateId === '0' ? '/member-reversion/websiteSettings/saveOrUpdateWebsiteSettingsInfo' : '/member-reversion/user/websiteSettings/saveOrUpdateWebsiteSettingsInfo'
            this.$api.post(url, {
              account: this.$user.loginAccount,
              templateId: this.templateId,
              websiteInfo: this.websiteInfo,
              loginStep:{
                id: this.stepId ? this.stepId : 0,
                account: this.$user.loginAccount,
                templateId: this.templateId,
                step: 2
              }
            }).then(response => {
              console.log('save response', response)
              if (response.code === 200) {
                this.$Message.success('保存成功！')
                this.$emit('on-next', this.templateId)
              }
            })
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.back-btn {
  background-color: #9B9B9B;
  border-color: #9B9B9B;
  &:hover {
    background-color: #9B9B9B;
    border-color: #9B9B9B;
  }
}
.layout {
  width: 1000px;
  margin: auto;
  margin-top: 20px;
}
</style>
