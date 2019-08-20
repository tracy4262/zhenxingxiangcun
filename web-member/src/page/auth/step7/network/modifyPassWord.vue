<template>
<div class="pd20">
    <Title :title="title" :id="id" :yearId="yearId" edit :templateId="templateId" @left-refresh="leftRefresh"></Title>
    <div class="pd20">
      <Form :label-width="80" label-position="left" ref="data" :model="data.networkInformation" :rules="dataLine">
        <!-- <Row  :gutter="38">
          <Col span="8">
            <FormItem label="权限">
              <Switch class="ml20"  size="large" v-model="data.status">
                <span slot="open">公开</span>
                <span slot="close">隐藏</span>
              </Switch>
            </FormItem>
          </Col>
        </Row> -->
        <Row :gutter="38">
          <!-- <Col span="12">
            <FormItem label="昵称">
              <Input v-model="data.networkInformation.realname.model"  :maxlength="20" @on-change="change"></Input>
            </FormItem>
          </Col> -->
          <Col span="12">
            <FormItem label="旧密码">
              <Input v-model="data.networkInformation.password"  type="password" :maxlength="20"></Input>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="38">
          <Col span="12">
            <FormItem label="新密码" prop="new_password">
              <Input v-model="data.networkInformation.new_password"  type="password" :maxlength="20"></Input>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="38">
          <Col span="12">
            <FormItem label="确认新密码" prop="new_password1">
              <Input v-model="data.networkInformation.new_password1"  type="password" :maxlength="20"></Input>
            </FormItem>
          </Col>
        </Row>
      </Form>
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
      title: '修改密码',
      templateId: '',
      isLoading: true
    }
  },
  created() {
    this.templateId = this.$route.query.templateId
    this.account = this.$user.loginAccount
    // this.handleInit()
  },
  methods: {
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
          this.data.networkInformation = response.data.networkInformation
          this.data.status = response.data.status
          this.data.sys_dict_id = this.id
          this.data.password = ''
          this.data.new_password = ''
          this.data.new_password1 = ''
          this.data.textPreview = response.data.textPreview
          console.log(this.data)
        }
      })
    },
    change () {
      let data = this.data.networkInformation
      let content = ''
      for(var key  in data){
          if(data[key] && data[key].model){
            content +=  `${data[key].name}：${data[key].model}，`
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
