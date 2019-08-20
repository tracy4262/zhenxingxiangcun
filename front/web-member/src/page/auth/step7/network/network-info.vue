<template>
<div class="pd20">
    <Title :title="title" :id="id" :yearId="yearId" edit :templateId="templateId" @left-refresh="leftRefresh"></Title>
    <div class="pd20">
      <Form :label-width="80" label-position="left" ref="data" :model="data.networkInformation" :rules="dataLine">
        <Row  :gutter="38">
          <Col span="8">
            <FormItem label="权限">
              <Switch class="ml20"  size="large" v-model="data.status">
                <span slot="open">公开</span>
                <span slot="close">隐藏</span>
              </Switch>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="38">
          <Col span="12">
            <FormItem label="农事无忧ID">
              <Input v-model="data.networkInformation.ID.model" readonly disabled></Input>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="用户名">
              <Input v-model="$user.loginAccount"  readonly disabled></Input>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="38">
          <Col span="12">
            <FormItem label="昵称">
              <Input v-model="data.networkInformation.realname.model"></Input>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="邮箱" prop="Email">
              <Input v-model="data.networkInformation.Email.model"  :maxlength="20" @on-change="change"></Input>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="38">
          <Col span="12">
            <FormItem label="QQ号" prop="QQ">
              <Input v-model="data.networkInformation.QQ.model"  :maxlength="20" @on-change="change"></Input>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="微信号" prop="weChat">
              <Input v-model="data.networkInformation.weChat.model"  :maxlength="20" @on-change="change"></Input>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="38">
          <Col span="12">
            <FormItem label="门户网站" prop="domainName">
              <Input v-model="domainName" @on-change="change" readonly disabled></Input>
            </FormItem>
          </Col>
        </Row>
      </Form>
    </div>
    <Title title="文字预览"></Title>
      <div class="pd20">
        <Input v-model="data.textPreview.text_preview" type="textarea"  :autosize="{minRows: 4,maxRows: 4}"></Input>
      </div>
    <div class="tc pt40">
      <Button type="primary" v-if="isLoading">保存</Button>
      <Button type="primary" @click="handleSave" v-else>保存</Button>
    </div>
</div>
</template>

<script>
import Title from '../../components/title'
import sha1 from '../../../member/proxy/sha1.js'
import {isQQDeep, isEmailDeep, isDomainNameDeep, isWeChatDeep} from '~utils/validate'
export default {
  components: {
    Title
  },
  props: {
    yearId: {
      type: String
    },
    appId: {
      type: String
    },
    id: {
      type: String
    }
  },
  data () {
    return {
      data: {
        netWorkInfo_name: '',
        textPreview: {},
        sys_dict_id: '',
        status: true,
        networkInformation: {
          ID:{},
          QQ:{},
          Email:{},
          domainName:{},
          realname: {},
          weChat: {},
          password: '',
          new_password: '',
          new_password1: '',
        }
      },
      dataLine: {
        QQ:[{validator: isQQDeep, trigger: 'blur'}],
        Email:[{validator: isEmailDeep, trigger: 'blur'}],
        domainName:[{validator: isDomainNameDeep, trigger: 'blur'}],
        weChat:[{validator: isWeChatDeep, trigger: 'blur'}],
      },
      account: '',
      title: '网络信息',
      templateId: '',
      isLoading: true,
      domainName: ''
    }
  },
  created() {
    this.templateId = this.$route.query.templateId
    this.account = this.$user.loginAccount
    this.getPortals()
  },
  methods: {
    getPortals (account) {
      // 查询用户是否实名
      this.$api.post('/member-reversion/realStep/findEnableStep', {
              account: account
          }).then(response => {
          if (response.code === 200) {
              if (response.data) {
                  let step = Number(response.data.step)
                  if (step >= 7) {
                      this.$api.post('/member-reversion/user/realCertification/findMemberClassByAccount', {
                          account: account
                      }).then(response => {
                          if (response.code === 200) {
                              if (response.data.member_class === '专家') {
                                  // 专家门户
                                  this.domainName = `${window.location.origin}/expertPortal/index?uid=${account}&id=0`
                              } else if (response.data.member_class === '法人/其他法人') {
                                  // 乡村门户
                                  this.domainName = `${window.location.origin}/ruralPortal/index?uid=${account}&id=0`
                              } else if (response.data.member_class === '法人/企业法人/农业龙头企业') {
                                  // 农业龙头企业
                                  this.domainName = `${window.location.origin}/farmHeadPortal/index?uid=${account}&id=0`
                              } else if (response.data.member_class === '法人/企业法人/农民合作社') {
                                  // 农民合作社
                                  this.domainName = `${window.location.origin}/cooperativePortal/index?uid=${account}&id=0`
                              } else {
                                  // 其余都进通用门户
                                  this.domainName = `${window.location.origin}/portals/index?uid=${account}&id=0`
                              }
                          } else {
                              this.domainName = `${window.location.origin}/portals/index?uid=${account}&id=0`
                          }
                      })
                  } else {
                      // this.$Message.error('请完成实名认证！')
                      this.domainName = `${window.location.origin}/portals/index?uid=${account}&id=0`
                  }
              } else { // 说明没走过认证
                  // this.$Message.error('用户未实名')
                  this.domainName = `${window.location.origin}/portals/index?uid=${account}&id=0`
              }
          }
      }).catch(error => {
          this.$Message.error('服务器异常！')
      })
    },
    initTitle () {
        this.$api.post('/member-reversion/user/perfect/findTableHead', {
            account: this.$user.loginAccount,
            yearId: this.yearId,
            dictId: this.id,
            templateId: this.templateId
        }).then(response => {
            if (response.code === 200) {
                if (response.data.propertyName) {
                    this.title = response.data.propertyName
                }
            }
        }).catch(error => {
            this.$Message.error('服务器异常！')
        })
    },
    handleInit () {
      this.$api.post('/member-reversion/netWorkInfo/getNetworkInfo', {
        user_id: this.account,
        year_id: this.yearId,
        parent_id: this.id,
        templateId: this.templateId
      }).then(response => {
        if (response.code === 200) {
          this.isLoading = false
          let data = response.data.networkInformation
          if (!data.weChat) {
            data.weChat = {
              model: '',
              name: '微信号'
            }
          }
          this.data.networkInformation = data
          this.data.status = response.data.status
          this.data.sys_dict_id = this.id
          // this.data.netWorkInfo_name = response.data.netWorkInfo_name
          this.data.password = ''
          this.data.new_password = ''
          this.data.new_password1 = ''
          this.data.textPreview = response.data.textPreview
        }
      })
    },
    change () {
      let data = this.data.networkInformation
      let content = ''
      for(var key  in data){
        if (data[key].name == '申请域名') {
          content +=  `门户网站：${this.domainName}，`
        } else {
          if(data[key] && data[key].model){
            content +=  `${data[key].name}：${data[key].model}，`
          }
        }
      }
      if (content) {
        content = content.substring(0,content.length-1) + '。'
      }
      this.data.textPreview.text_preview = content
    },
    leftRefresh () {
      this.$emit('left-refresh')
    },
    handleSave () {
      // 密码判断
      if (this.data.networkInformation.password) {
        if (!this.data.networkInformation.new_password) {
          this.$Message.error('请输入新密码')
          return
        }
        if (!this.data.networkInformation.new_password1) {
          this.$Message.error('请确认新密码')
          return
        }
      } else {
        if (this.data.networkInformation.new_password || this.data.networkInformation.new_password) {
          this.$Message.error('请输入旧密码')
          return
        }
        if (this.data.networkInformation.new_password1 && !this.data.networkInformation.new_password) {
          this.$Message.error('请确认新密码')
          return
        }
        if (!this.data.networkInformation.new_password1 && this.data.networkInformation.new_password) {
          this.$Message.error('请输入新密码')
          return
        }
      }
      this.$refs['data'].validate((valid) => {
        if (valid) {
          this.isLoading = true
          this.data.user_id = this.account
          this.data.yearId = this.yearId
          this.data.textPreview.is_complete = true
          this.data.networkInformation.status = this.data.status
          this.data.templateId = this.templateId
          console.log(this.data)
          this.$api.post('/member-reversion/netWorkInfo/insertNetworkInfo', this.data).then(response => {
            if (response.code === 200) {
             this.$Message.success('保存成功')
             this.$emit('on-save')
             this.handleInit()
            } else if (response.code === 301) {
              this.$Message.error('旧密码错误')
            } else {
              this.$Message.error('保存失败')
            }
          })
        } else {
          this.$Message.error('请核对表单信息')
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
