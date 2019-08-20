<template>
<div class="pd20 mt20">
    <Title :title="data.netWorkInfo_name"  :id="id" :yearId="yearId" edit></Title>
    <div class="pd20">
      <Form :label-width="80" label-position="left" ref="data" :model="data.networkInformation" :rules="dataLine">
        <Row  :gutter="38">
          <Col span="8">
            <FormItem label="权限">
              <Switch class="ml20"  size="large" v-model="data.status" :disabled="true">
                <span slot="open">公开</span>
                <span slot="close">隐藏</span>
              </Switch>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="38">
          <Col span="8">
            <FormItem label="农事无忧ID">
              <Input v-model="data.networkInformation.ID.model" readonly :disabled="true"></Input>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="QQ号码" prop="QQ">
              <Input v-model="data.networkInformation.QQ.model" :disabled="true" :maxlength="20"></Input>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="邮箱" prop="Email">
              <Input v-model="data.networkInformation.Email.model" :disabled="true" :maxlength="20"></Input>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="申请域名" prop="domainName">
              <Input v-model="data.networkInformation.domainName.model" :disabled="true"></Input>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="38">
          <Col span="12">
            <FormItem label="昵称">
              <Input v-model="data.networkInformation.realname.model" :disabled="true" :maxlength="20"></Input>
            </FormItem>
          </Col>
        <!-- </Row>
        <Row :gutter="38"> -->
          <Col span="12">
            <FormItem label="旧密码">
              <Input v-model="data.networkInformation.password" :disabled="true" type="password" :maxlength="20"></Input>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="38">
          <Col span="12">
            <FormItem label="新密码" prop="new_password">
              <Input v-model="data.networkInformation.new_password" :disabled="true" type="password" :maxlength="20"></Input>
            </FormItem>
          </Col>
        <!-- </Row>
        <Row :gutter="38"> -->
          <Col span="12">
            <FormItem label="确认新密码" prop="new_password1">
              <Input v-model="data.networkInformation.new_password1" :disabled="true" type="password" :maxlength="20"></Input>
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
      <Button type="primary" @click="handleSave">保存</Button>
    </div>
</div>
</template>

<script>
import Title from '../../components/title'
import {isQQDeep, isEmailDeep, isDomainNameDeep} from '~utils/validate'
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
     const password = (rule, value, callback) => {
          var uPattern = /^[a-zA-Z0-9]{6,16}$/
          if (!uPattern.test(value)) {
              return callback(new Error('登录密码为6-16个字符组成，可使用字母、数字'));
          }else {
              callback()
          }
      }
     const checkedPassword = (rule, value, callback) => {
          if(this.data.networkInformation.new_password1) {
            if (this.data.networkInformation.new_password !== this.data.networkInformation.new_password1) {
              return callback(new Error('两次输入的密码不一致'))
            }else {
                callback()
            }
          }else {
              callback()
          }
      }
    return {
      data: {
        true: true,
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
          password: '',
          new_password: '',
          new_password1: '',
        }
      },
      dataLine: {
        QQ:[{validator: isQQDeep, trigger: 'blur'}],
        Email:[{validator: isEmailDeep, trigger: 'blur'}],
        domainName:[{validator: isDomainNameDeep, trigger: 'blur'}],
        new_password:[
          {
              type: 'string',
              min: 6,
              message: '登录密码为6-16个字符组成，区分大小写',
              trigger: 'blur'
          },{
              validator: password,
              message:'登录密码为6-16个字符组成，区分大小写'
          },{
              validator: checkedPassword,
              trigger: 'blur'
          }
        ],
        new_password1:[
          {
            type: 'string',
            min: 6,
            message: '登录密码为6-16个字符组成，区分大小写',
            trigger: 'blur'
          },{
              validator: password,
              message:'登录密码为6-16个字符组成，区分大小写'
          },{
            validator: checkedPassword,
            trigger: 'blur'
          }
        ]
      },
      account: '',
      title: ''
    }
  },
  created() {
    this.account = this.$user.loginAccount
    // this.handleInit()
  },
  methods: {
    handleInit () {
      this.$api.post('/member-reversion/netWorkInfo/getNetworkInfo', {templateId: this.$template.id, user_id: this.account, year_id: this.yearId, parent_id: this.id}).then(response => {
        if (response.code === 200) {
          if (response.data.networkInformation) {
            this.data.networkInformation = response.data.networkInformation
          }
          this.data.status = response.data.status
          this.data.sys_dict_id = this.id
          this.data.netWorkInfo_name = response.data.netWorkInfo_name
          this.data.password = ''
          this.data.new_password = ''
          this.data.new_password1 = ''
          if (!response.data.textPreview.text_preview) {
            response.data.textPreview.text_preview = `农事无忧ID（），QQ号码（），邮箱（），申请域名（），昵称（）。`
          }
          this.data.textPreview = response.data.textPreview
        }
      })
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
          this.data.user_id = this.account
          this.data.yearId = this.yearId
          this.data.textPreview.is_complete = true
          this.data.templateId = this.$template.id,
          this.data.networkInformation.status = this.data.status
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
  },
  mounted () {
    
  }
}
</script>

<style lang="scss" scoped>

</style>
