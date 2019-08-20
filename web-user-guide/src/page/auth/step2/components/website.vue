<template>
  <div class="new-auth-website">
    <Card class="layout">
      <div class="pd20">
        <p class="pb20 template-name">{{$template.templateName}}</p>
        <Title title="网站基本信息"></Title>
        <Form ref="websiteInfo" :model="websiteInfo" label-position="left" :label-width="150" :rules="ruleInline" class="mt40 ml20">
          <Row :gutter="32">
            <Col span="12">
              <Form-item prop="nameSuffixs" class="input" label="网站名称">
                <Input class="website-name" v-model="websiteInfo.websiteName" :maxlength="100" style="width:140px;" />
                <Input v-model="websiteInfo.nameSuffix" :maxlength="20" style="width:105px;" placeholder="请输入名称后缀"/>
              </Form-item>
            </Col>
            <Col span="10">
              <Switch size="large" v-model="websiteInfo.isShowWebsiteName" class="mt5" >
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
                <Input v-model="websiteInfo.websiteProfile" type="textarea"  :autosize="{minRows: 3,maxRows: 5}" :maxlength="500" />
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
      isShowWebsiteName: true,
      websiteLOGO: '',
      websiteBanner: '',
      websiteProfile: '',
      nameSuffix: '', // 名称后缀
      nameSuffixs: '1' // 用于 显示表单验证，* 无其他作用
    },
    ruleInline: {
      // 用于 显示表单验证，* 无其他作用
      nameSuffixs: [
        {required: true, message: '请输入名称后缀', trigger: 'blur' }
      ]
    },
  }),
  created () {
    this.$api.post('/member-reversion/websiteSettings/findWebsiteSettingsInfo', {
      account: this.$user.loginAccount,
      templateId: this.$template.id
    }).then(response => {
      if (response.code === 200) {
        if (response.data.websiteInfo) {
          this.websiteInfo = response.data.websiteInfo
          // 用于 显示表单验证，* 无其他作用
          this.websiteInfo.nameSuffixs = '1'
          this.$refs.banner.handleGive(response.data.websiteInfo.websiteBanner)
          this.$refs.logo.handleGive(response.data.websiteInfo.websiteLOGO)
        }
      }
    }).catch(error => {
      this.$Message.error('服务器异常！')
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
    // 上一步
    handleClickBack () {
      this.$emit('on-back')
    },
    // 下一步
    handleClickNext () {
      // this.$refs['websiteInfo'].validate((valid) => {
      //   if (valid) {
            this.$api.post('/member-reversion/websiteSettings/saveOrUpdateWebsiteSettingsInfo', {
              account: this.$user.loginAccount,
              templateId: this.$template.id,
              websiteInfo: this.websiteInfo,
              loginStep: {
                id: this.$step.id,
                account: this.$user.loginAccount,
                templateId: this.$template.id,
                step: 2
              }
            }).then(response => {
              console.log('response222', response)
              if (response.code === 200) {
                this.$emit('on-next')
              }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
      //   }
      // })
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
<style lang="scss">
.new-auth-website {
  .website-name{
    .ivu-input{
      border: 1px solid #dcdee2;
      background-color: #fff;
      &:hover {
        border-color: #dcdee2;
        outline: 0;
        box-shadow: 0 0 0 2px rgba(0,197,135,.2);
      }
      &:focus{
        border-color: #dcdee2;
        outline: 0;
        box-shadow: 0 0 0 2px rgba(0,197,135,.2);
      }
    }
  }
}
.new-auth-website .ivu-form-item-error-tip{
  margin-left: 150px;
}
</style>

