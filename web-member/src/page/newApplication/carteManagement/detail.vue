<template>
  <div  class="ivu-carte-management">
    <!-- <CheckboxGroup v-model="selectData"> -->
      <div class="tc pd20">
        <Button type="primary" @click="selectAll" class="mr20">全选</Button>
        <Button  @click="reset">重置</Button>
      </div>
      <Title title="注册信息"></Title>
      <div class="pt20 pb20">
        <div class="pt20 pl20 pr20 mb20" style="background: #F9F9F9;">
        <Row :gutter="16" type="flex" class="pb20">
          <Col span="8">
            <Checkbox v-model="registrationMessage.accountFlag" @on-change="change">
              用户名：{{registrationMessage.account}}
            </Checkbox>
          </Col>
           <Col span="8">
            <Checkbox v-model="registrationMessage.realNameFlag" @on-change="change">
              昵称：{{registrationMessage.realName}}
            </Checkbox>
          </Col>
           <Col span="8">
            <Checkbox v-model="registrationMessage.nswyIdFlag" @on-change="change">
              农事无忧账号：{{registrationMessage.nswyId}}
            </Checkbox>
          </Col>
        </Row>
        <Row :gutter="16" type="flex" class="pb20">
           <Col span="16">
            <Checkbox v-model="registrationMessage.locationFlag" @on-change="change">
              所在区域：{{registrationMessage.location}}
            </Checkbox>
          </Col>
        </Row>
        </div>
      </div>
      <Title title="资质认证"></Title>
      <div class="pt20 pb20">
        <div class="pt20 pl20 pr20 mb20" style="background: #F9F9F9;" v-for="(item, index) in certificationData">
            <Row :gutter="16" class="pb20">
              <Col span="8">
                <Checkbox v-model="item.memberClassFlag" @on-change="change">
                  会员类别：{{item.memberClass}}
                </Checkbox>
              </Col>
              <Col span="8">
                <Checkbox v-model="item.memberNameFlag" @on-change="change">
                  会员名称全称：{{item.memberName}}
                </Checkbox>
              </Col>
              <Col span="8">
                <Checkbox v-model="item.memberNamePinyinFlag" @on-change="change">
                  全称拼音：{{item.memberNamePinyin}}
                </Checkbox>
              </Col>
            </Row>
            <Row :gutter="16" class="pb20">
                <Col span="8">
                  <Checkbox v-model="item.memberAbbreviationFlag" @on-change="change">
                    会员名称简写：{{item.memberAbbreviation}}
                  </Checkbox>
                </Col>
                <Col span="8">
                  <Checkbox v-model="item.abbreviationPinyinFlag" @on-change="change">
                    会员名称简称拼音：{{item.abbreviationPinyin}}
                  </Checkbox>
                </Col>
            </Row>
            <Row :gutter="16" class="pb20">
                <Col span="8">
                  <Checkbox v-model="item.aptitudeNameFlag" @on-change="change">
                    资质名称：{{item.aptitudeName}}
                  </Checkbox>
                </Col>
                <Col span="8">
                  <Checkbox v-model="item.aptitudeNumberFlag" @on-change="change">
                    资质编号：{{item.aptitudeNumber}}
                  </Checkbox>
                </Col>
                <Col span="8">
                  <Checkbox v-model="item.aptitudeImageFlag" @on-change="change">
                    资质照片：
                  </Checkbox>
                  <span class="t-green" @click="previewImages(item.aptitudeImage, '资质认证——资质照片')">查看</span>
                  <!-- <Button type="text" ghost @click="previewImages(item.aptitudeImage, '资质认证——资质照片')"><span class="t-green" >查看</span></Button> -->
                  
                </Col>
            </Row>
            <Row :gutter="16"  class="pb20">
                <Col span="24">
                  <Checkbox v-model="item.remarkFlag" @on-change="change">
                    说明：{{item.remark}}
                  </Checkbox>
                </Col>
            </Row>
        </div>
      </div>
      <Title title="联系方式"></Title>
      <div class="pt20 pb20">
        <div class="pt20 pl20 pr20 mb20" style="background: #F9F9F9;" v-for="(item, index) in concatData">
          <Row :gutter="16" class="pb20">
            <Col span="8">
              <Checkbox v-model="item.memberNameFlag" @on-change="change">
                会员全称：{{item.memberName}}
              </Checkbox>
            </Col>
            <Col span="8">
              <Checkbox v-model="item.memberAbbreviationFlag" @on-change="change">
                名称简写：{{item.memberAbbreviation}}
              </Checkbox>
            </Col>
            <Col span="8">
              <Checkbox v-model="item.contactNameFlag" @on-change="change">
                联系人姓名：{{item.contactName}}
              </Checkbox>
            </Col>
          </Row>
          <Row :gutter="16" class="pb20">
            <Col span="8">
              <Checkbox v-model="item.cardFlag" @on-change="change">
                身份证号：{{item.card}}
              </Checkbox>
            </Col>
            <Col span="8">
              <Checkbox v-model="item.setPhoneFlag" @on-change="change">
                座机电话：{{item.setPhone}}
              </Checkbox>
            </Col>
            <Col span="8">
              <Checkbox v-model="item.phoneFlag" @on-change="change">
                手机号码：{{item.phone}}
              </Checkbox>
            </Col>
          </Row>
          <Row :gutter="16" class="pb20">
            <Col span="8">
              <Checkbox v-model="item.qqNumberFlag" @on-change="change">
                QQ号码：{{item.qqNumber}}
              </Checkbox>
            </Col>
            <Col span="8">
              <Checkbox v-model="item.wechatNumberFlag" @on-change="change">
                微信账号：{{item.wechatNumber}}
              </Checkbox>
            </Col>
            <Col span="8">
              <Checkbox v-model="item.emailFlag" @on-change="change">
                邮箱：{{item.email}}
              </Checkbox>
            </Col>
          </Row>
          <Row :gutter="16" class="pb20">
            <Col span="8">
              <Checkbox v-model="item.postalCodeFlag" @on-change="change">
                邮政编码：{{item.postalCode}}
              </Checkbox>
            </Col>
            <Col span="8">
              <Checkbox v-model="item.websiteUrlFlag" @on-change="change">
                网站地址：{{item.websiteUrl}}
              </Checkbox>
            </Col>
            <Col span="8">
              <Checkbox v-model="item.imageFlag" @on-change="change">
                个人照片：
              </Checkbox>
              <span class="t-green" @click="previewImages(item.image, '联系方式——个人照片')">查看</span>
              <!-- <Button type="text" ghost @click="previewImages(item.image, '联系方式——个人照片')"><span class="t-green" >查看</span></Button> -->
            </Col>
          </Row>
          <Row :gutter="16" class="pb20">
            <Col span="24">
              <Checkbox v-model="item.locationFlag" @on-change="change">
                会员详细地址：{{item.location}}/{{item.address}}/{{item.house_number}}号
              </Checkbox>
            </Col>
          </Row>
          <Row :gutter="16" class="pb20">
            <Col span="24">
              <Checkbox v-model="item.longitudeFlag" @on-change="change">
                东经：{{item.longitude}} 北纬：{{item.latitude}}
              </Checkbox>
            </Col>
          </Row>
        </div>
      </div>
      <Title title="法人或个人身份"></Title>
      <div class="pt20 pb20">
        <div class="pt20 pl20 pr20 mb20" style="background: #F9F9F9;" v-for="(item, index) in identityData">
          <Row :gutter="16" class="pb20">
            <Col span="8">
              <Checkbox v-model="item.memberNameFlag" @on-change="change">
                会员名称全称：{{item.memberName}}
              </Checkbox>
            </Col>
            <Col span="8">
              <Checkbox v-model="item.memberAbbreviationFlag" @on-change="change">
                会员名称简写：{{item.memberAbbreviation}}
              </Checkbox>
            </Col>
            <Col span="8">
              <Checkbox v-model="item.nameFlag" @on-change="change">
                姓名：{{item.name}}
              </Checkbox>
            </Col>
          </Row>
          <Row :gutter="16" class="pb20">
            <Col span="8">
              <Checkbox v-model="item.sexFlag" @on-change="change">
                性别：{{item.sex}}
              </Checkbox>
            </Col>
            <Col span="8">
              <Checkbox v-model="item.cardFlag" @on-change="change">
                身份证号：{{item.card}}
              </Checkbox>
            </Col>
            <Col span="8">
              <Checkbox v-model="item.jobFlag" @on-change="change">
                职务：{{item.job}}
              </Checkbox>
            </Col>
          </Row>
          <Row :gutter="16" class="pb20">
            <Col span="8">
              <Checkbox v-model="item.jobTitleFlag" @on-change="change">
                职称：{{item.jobTitle}}
              </Checkbox>
            </Col>
            <Col span="8">
              <Checkbox v-model="item.phoneFlag" @on-change="change">
                手机：{{item.phone}}
              </Checkbox>
            </Col>
            <Col span="8">
              <Checkbox v-model="item.emailFlag" @on-change="change">
                邮箱：{{item.email}}
              </Checkbox>
            </Col>
          </Row>
          <Row :gutter="16" class="pb20">
            <Col span="24">
              <Checkbox v-model="item.dutyFlag" @on-change="change">
                职责：{{item.duty}}
              </Checkbox>
            </Col>
          </Row>
          <Row :gutter="16" class="pb20">
            <Col span="24">
              <Checkbox v-model="item.cardImageFlag" @on-change="change">
                身份证照片：
              </Checkbox>
              <span class="t-green" @click="previewImages(item.cardImage, '法人或个人身份——身份证照片')">查看</span>
              <!-- <Button type="text" ghost @click="previewImages(item.cardImage, '联系方式——个人照片')"><span class="t-green" >查看</span></Button> -->
            </Col>
          </Row>
        </div>
      </div>
      <Title title="法人或个人身份（管理员）"></Title>
      <div class="pt20 pb20">
        <div class="pt20 pl20 pr20 mb20" style="background: #F9F9F9;" v-for="(item, index) in administratorData"> 
          <Row :gutter="16" class="pb20" >
            <Col span="8">
              <Checkbox v-model="item.memberNameFlag" @on-change="change">
                会员名称全称：{{item.memberName}}
              </Checkbox>
            </Col>
            <Col span="8">
              <Checkbox v-model="item.memberAbbreviationFlag" @on-change="change">
                会员名称简写：{{item.memberAbbreviation}}
              </Checkbox>
            </Col>
            <Col span="8">
              <Checkbox v-model="item.nameFlag" @on-change="change">
                网站管理员姓名：{{item.name}}
              </Checkbox>
            </Col>
          </Row>
          <Row :gutter="16" class="pb20">
            <Col span="8">
              <Checkbox v-model="item.sexFlag" @on-change="change">
                性别：{{item.sex}}
              </Checkbox>
            </Col>
            <Col span="8">
              <Checkbox v-model="item.cardFlag" @on-change="change">
                身份证号：{{item.card}}
              </Checkbox>
            </Col>
            <Col span="8">
              <Checkbox v-model="item.jobFlag" @on-change="change">
                职务：{{item.job}}
              </Checkbox>
            </Col>
          </Row>
          <Row :gutter="16" class="pb20">
            <Col span="8">
              <Checkbox v-model="item.jobTitleFlag" @on-change="change">
                职称：{{item.jobTitle}}
              </Checkbox>
            </Col>
            <Col span="8">
              <Checkbox v-model="item.phoneFlag" @on-change="change">
                手机：{{item.phone}}
              </Checkbox>
            </Col>
            <Col span="8">
              <Checkbox v-model="item.emailFlag" @on-change="change">
                邮箱：{{item.email}}
              </Checkbox>
            </Col>
          </Row>
          <Row :gutter="16" class="pb20">
            <Col span="24">
              <Checkbox v-model="item.dutyFlag" @on-change="change">
                职责：{{item.duty}}
              </Checkbox>
            </Col>
          </Row>
          <Row :gutter="16" class="pb20">
            <Col span="8">
              <Checkbox v-model="item.cardImageFlag" @on-change="change">
                身份证照片：
              </Checkbox>
              <span class="t-green" @click="previewImages(item.cardImage, '法人或个人身份（管理员）——身份证照片')">查看</span>
              <!-- <Button type="text" ghost @click="previewImages(item.cardImage, '联系方式——身份证照片')"><span class="t-green" >查看</span></Button> -->
            </Col>
            <Col span="8">
              <Checkbox v-model="item.empowerImageFlag" @on-change="change">
                授权书照片：
              </Checkbox>
              <span class="t-green" @click="previewImages(item.empowerImage, '法人或个人身份（管理员）——授权书照片')">查看</span>
            </Col>
          </Row>
        </div>
      </div>
      <Title title="名片二维码"></Title>
      <Row :gutter="16" class="pb20">
          <Col span="5">
            <div class="pt30 tc">
                <canvas id="canvas" ref="canvas" ></canvas>
                <!-- <vue-qr-code :value="qrCodeUrl" :options="{size: 120}" ref="VueQrCode" id="VueQrCode"></vue-qr-code> -->
                <div class="tc">
                  <Button type="primary" class="mt10" style="width:120px" @click="handlePreview">预览</Button> <br/>
                  <Button class="mt10" style="width:120px" @click="toGate">链接</Button><br/>
                  <Button class="mt10" style="width:120px" @click="handleExport">导出</Button>
                </div>
            </div>
          </Col>
          <Col span="19">
            <real-name ref="realName"
              :registrationMessage="registrationMessage"
              :certificationData="certificationData"
              :concatData="concatData"
              :identityData="identityData"
              :administratorData="administratorData"
            ></real-name>
          </Col>
      </Row>
      <image-preview ref="imagePreview"></image-preview>
  </div>
</template>
<script>
import Title from '../../auth/components/title'
import realName from './components/realName'
import imagePreview from './components/imagePreview'
import QRCode from 'qrcode'
export default {
  components: {
    Title,
    realName,
    imagePreview,
    QRCode
  },
  data: () => ({
    qrCodeUrl: '',
    // 注册信息
    registrationMessage: {},
    certificationData: [],
    concatData: [],
    identityData: [],
    administratorData: [],
    targetAction: '',
    showVueQrCode: false
  }),
  created () {
    this.$api.post('/member-reversion/user/userTemplateManage/find', {
            account: this.$user.loginAccount
        }).then(response => {
            if (response.code === 200) {
                if (response.data.userTemplate) {
                    this.templateId = response.data.userTemplate.templateId
                    this.getData()
                    this.qrCodeUrl = `${window.location.origin}/nswy-member-info?account=${this.$user.loginAccount}&templateId=${this.templateId}`
                    this.useqrcode()
                }
            }
        }).catch(error => {
            this.$Message.error('服务器异常！')
        })
  },
  methods: {
    useqrcode () {
      var canvas = this.$refs['canvas']
      QRCode.toCanvas(canvas, this.qrCodeUrl, function (error) {
        if (error) console.error(error)
        console.log('QRCode success!')
      })
    },
    handleExport () {
      let myCanvas = document.getElementsByTagName('canvas')
      let a = document.createElement("a")
      a.href = myCanvas[0].toDataURL('image/png')
      a.download = "名片"
      a.click()
    },
    change (e) {
      this.onSave()
      this.qrCodeUrl = `${window.location.origin}/nswy-member-info?account=${this.$user.loginAccount}&templateId=${this.templateId}`
    },
    // 全选
    selectAll () {
      // 注册信息
      for (var i in this.registrationMessage) {
        if (typeof(this.registrationMessage[i]) === 'boolean') {
          this.registrationMessage[i] = true
        }
      }
      this.certificationData.forEach(element => {
        for (var i in element) {
          if (typeof(element[i]) === 'boolean') {
            element[i] = true
          }
        }
      })
      
      this.concatData.forEach(element => {
        for (var i in element) {
          if (typeof(element[i]) === 'boolean') {
            element[i] = true
          }
        }
      })
      
      this.identityData.forEach(element => {
        for (var i in element) {
          if (typeof(element[i]) === 'boolean') {
            element[i] = true
          }
        }
      })

      this.administratorData.forEach(element => {
        for (var i in element) {
          if (typeof(element[i]) === 'boolean') {
            element[i] = true
          }
        }
      })
      this.onSave()
    },
    // 重置
    reset () {
      
      // 注册信息
      for (var i in this.registrationMessage) {
        if (typeof(this.registrationMessage[i]) === 'boolean') {
          this.registrationMessage[i] = false
        }
      }
      this.certificationData.forEach(element => {
        for (var i in element) {
          if (typeof(element[i]) === 'boolean') {
            element[i] = false
          }
        }
      })
      
      this.concatData.forEach(element => {
        for (var i in element) {
          if (typeof(element[i]) === 'boolean') {
            element[i] = false
          }
        }
      })
      
      this.identityData.forEach(element => {
        for (var i in element) {
          if (typeof(element[i]) === 'boolean') {
            element[i] = false
          }
        }
      })

      this.administratorData.forEach(element => {
        for (var i in element) {
          if (typeof(element[i]) === 'boolean') {
            element[i] = false
          }
        }
      })
      this.onSave()
    },
    // 保存
    onSave () {
      this.$api.post('/member-reversion/uesr/visitingCard/updateVisitingCard',{
        registerInfo: this.registrationMessage,
        memberAptitude: this.certificationData,
        memberContact: this.concatData,
        memberIdentity: this.identityData,
        memberAdmin: this.administratorData,
        account: this.$user.loginAccount,
      }).then(response => {
        if (response.code === 200) {
          // this.$Message.success('保存成功！')
        }
      })
    },
    // 查看照片
    previewImages (img, title) {
      this.$refs['imagePreview'].previewImages(img, title)
    },
    getData () {
      this.$api.post('/member-reversion/uesr/visitingCard/findVisitingCard', {
          account: this.$user.loginAccount,
          templateId: this.templateId
        }).then(response => {
          if (response.code === 200) {
            this.registrationMessage = response.data.registerInfo
            for (var i in this.registrationMessage) {
              if (typeof(this.registrationMessage[i]) === 'number') {
                this.registrationMessage[i] === 0 ? this.registrationMessage[i] = false : this.registrationMessage[i] = true;
              }
            }
            this.certificationData = response.data.memberAptitude
            this.certificationData.forEach(element => {
              for (var i in element) {
                if (typeof(element[i]) === 'number') {
                  element[i] === 0 ? element[i] = false : element[i] = true;
                }
              }
            })
            this.concatData = response.data.memberContact
            this.concatData.forEach(element => {
              for (var i in element) {
                if (typeof(element[i]) === 'number') {
                  element[i] === 0 ? element[i] = false : element[i] = true;
                }
              }
            })
            this.identityData = response.data.memberIdentity
            this.identityData.forEach(element => {
              for (var i in element) {
                if (typeof(element[i]) === 'number') {
                  element[i] === 0 ? element[i] = false : element[i] = true;
                }
              }
            })
            this.administratorData = response.data.memberAdmin
            this.administratorData.forEach(element => {
              for (var i in element) {
                if (typeof(element[i]) === 'number') {
                  element[i] === 0 ? element[i] = false : element[i] = true;
                }
              }
            })
          }
        })
    },
    // 点击预览
    handlePreview () {
      this.$refs['realName'].preview()
    },
    toGate () {
      // 查询用户是否实名
      this.$toPortals(this.$user.loginAccount)
      // this.$router.push(`/portals/index?uid=${this.$user.loginAccount}`)
    }
  }
}
</script>
<style>
.ivu-carte-management .t-green{
  text-decoration: underline;
  cursor: pointer;
}
.ivu-carte-management #canvas{
  width: 180px !important;
  height: 180px !important;
}
</style>
