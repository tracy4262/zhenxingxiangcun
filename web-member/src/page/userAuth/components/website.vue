<template>
  <Card>
    <div class="pd20">
      <p class="t-orange pt20 pb20">申请店铺完全免费，一个身份只能开一家店，开店后店铺无法注销，申请店铺到正式开通需要1-3个工作日。</p>
      <Title title="网站基本信息"></Title>
      <Form ref="websiteInfo" :model="websiteInfo" label-position="left" :label-width="150" :rules="ruleInline" class="mt20">
        <Row :gutter="32">
          <Col span="12">
            <Form-item prop="websiteName" class="input" label="网站名称">
              <Input v-model="websiteInfo.websiteName" :maxlength="100" />
            </Form-item>
          </Col>
          <Col span="12">
            <Form-item prop="isShowWebsiteName" class="input" label="网站名称是否显示">
              <Select v-model="websiteInfo.isShowWebsiteName" style="width:120px;">
                <Option value="是">是</Option>
                <Option value="否">否</Option>
              </Select>
            </Form-item>
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
              :size="[800,80]"
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
        <Row :gutter="32">
          <Col span="24">
            <Form-item prop="websiteIntroduction" label="网站介绍">
              <Input v-model="websiteInfo.websiteIntroduction" type="textarea" :autosize="{minRows: 3,maxRows: 5}" :maxlength="500" />
            </Form-item>
          </Col>
        </Row>
      </Form>
      <Title title="选择模版" class="mt20"></Title>
      <vui-min-card ref="template" :data="templateData" class="mt15" @on-click="handleGetTemplateChoosed"></vui-min-card>
      <Title title="选择模块" class="mt30"></Title>
      <vui-min-card ref="module" :data="moduleData" class="mt15" @on-click="handleGetModuleChoosed"></vui-min-card>
      <div class="tc pd20">
        <Button type="primary" @click="handleClickBack">上一步</Button>
        <Button type="primary" @click="handleClickNext">完成</Button>
      </div>
    </div>
  </Card>
</template>
<script>
import Title from './title'
import VuiMinCard from './vui-min-card'
import vuiUpload from '~components/vui-upload'
export default {
  components: {
    Title,
    VuiMinCard,
    vuiUpload
  },
  /* 1企业 2专家 3个人 4乡村 5机关 */
  props: {
    userType: Number,
    required: true
  },
  data: () => ({
    websiteInfo: {
      websiteName: '',
      isShowWebsiteName: '',
      websiteLOGO: '',
      websiteBanner: '',
      websiteProfile: '',
      websiteIntroduction: ''
    },
    ruleInline: {
      websiteName: [
        { required: true, message: '请填写网站名称', trigger: 'blur' }
      ],
      isShowWebsiteName: [
        { required: true, message: '请选择网站名称是否显示', trigger: 'change' }
      ],
      /* websiteLOGO: [
        { required: true, message: '请上传网站LOGO', trigger: 'change' }
      ],
      websiteBanner: [
        { required: true, message: '请上传网站横幅', trigger: 'change' }
      ], */
      websiteProfile: [
        { required: true, message: '请填写网站简介', trigger: 'blur' }
      ],
      websiteIntroduction: [
        { required: true, message: '请填写网站介绍', trigger: 'blur' }
      ]
    },
    templateData: [],
    moduleData: [],
    loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
    userType2: 3
  }),
  created () {
    /* 1企业 2专家 3个人 4乡村 5机关 */
    this.$api.post('/member/login/findCurrentUser', {
        account: this.loginUser.loginAccount
    }).then(res => {
      if (res.data.userType !== undefined) {
        if (1 === res.data.userType) {
          this.userType2 = 1
        } else if (3 === res.data.userType) {
          this.userType2 = 5
        } else if (4 === res.data.userType) {
          this.userType2 = 2
        } else if (5 === res.data.userType) {
          this.userType2 = 4
        } else {
          this.userType2 = 3
        }
      }
      // 如果第一次进来则初始化模版与模块信息 否则回显上次填的数据
      this.$api.post('/member/websiteSettings/findWebsiteSettingsInfo', {
        account: this.loginUser.loginAccount,
        userType: this.userType2
      }).then(response => {
        console.log('response123', response)
        if (response.code === 200) {
          this.templateData = response.data.templateData
          this.moduleData = response.data.moduleData
          if (response.data.websiteInfo) {
            this.websiteInfo = response.data.websiteInfo
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
      }).catch(error => {
        this.$Message.error('服务器异常！')
      })
    })
  },
  methods: {
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
    handleGetTemplateChoosed (checked) {
      this.templateData = checked
    },
    handleGetModuleChoosed (checked) {
      this.moduleData = checked
    },
    // 上一步
    handleClickBack () {
      this.$emit('on-back')
    },
    // 下一步
    handleClickNext () {
      this.$refs['websiteInfo'].validate((valid) => {
        if (valid) {
            this.$api.post('/member/websiteSettings/saveOrUpdateWebsiteSettingsInfo', {
              account: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount,
              websiteInfo: this.websiteInfo,
              templateData: this.templateData,
              moduleData: this.moduleData,
              userType: this.userType2
            }).then(response => {
              console.log('response222', response)
              if (response.code === 200) {
                this.$emit('on-next')
              }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        }
      })
    }
  }
}
</script>
<style lang="scss">
</style>
