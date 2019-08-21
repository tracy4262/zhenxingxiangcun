<template>
  <Modal
  v-model="show"
  :mask-closable="false"
  width="360"
  @on-cancel="handleCancel"
  class="vui-login">
    <Tabs :value="tabsActive" :animated="false">
      <tab-pane label="注册" name="reg">
        <Form ref="regFormInline" :model="regFormInline" :rules="regRuleInline">
          <Form-item prop="user" class="mb30">
              <i-input type="text" size="large" v-model.trim="regFormInline.user" :readonly="regFormInline.readonly" @on-focus="regFormInline.readonly = false"  placeholder="请输入用户名" :maxlength='numn'>
                  <icon slot="prepend" type="ios-person-outline" size="20">
                  </Icon>
              </i-input>
          </Form-item>
          <Form-item prop="password" class="mb30">
              <i-input :type="inputType" id="pwd" size="large" icon="ios-eye-outline" @on-click="showpwd('inputType')" v-model.trim="regFormInline.password" class="password" placeholder="请输入密码" :maxlength='nump'>
                  <Icon type="ios-unlocked-outline" slot="prepend" size="20"></Icon>
              </i-input>
          </Form-item>
          <Form-item prop="password1" class="mb30">
              <i-input :type="type" size="large" icon="ios-eye-outline" @on-click="showpwd('type')" v-model.trim="regFormInline.password1" class="password" placeholder="请确认密码" :maxlength='nump' @keyup.enter.native="register">
                  <Icon type="ios-locked-outline" slot="prepend" size="20"></Icon>
              </i-input>
          </Form-item>
          <Form-item>
              <Button type="primary" shape="circle" @click="register" style="width:100%">注册无忧账号</Button>
          </Form-item>
        </Form>
      </tab-pane>
      <tab-pane label="登录" name="login">
        <Form ref="formInline" :model="formInline" :rules="ruleInline">
          <Form-item prop="user">
              <i-input type="text" size="large" v-model.trim="formInline.user" placeholder="用户名">
                  <icon slot="prepend" type="ios-person-outline" size="20">
                  </Icon>
              </i-input>
          </Form-item>
          <Form-item prop="password">
              <i-input type="password" size="large" v-model.trim="formInline.password" placeholder="密码" @keyup.enter.native="login('formInline')">
                  <Icon type="ios-locked-outline" slot="prepend" size="20"></Icon>
              </i-input>
          </Form-item>
          <Form-item class="pt30">

          </Form-item>
          <Form-item>
              <Button  type="primary" shape="circle" @click="login" style="width:100%">登录</Button>
          </Form-item>
        </Form>
      </tab-pane>
    </Tabs>
    <Row slot="footer" class="tc">
    </Row>
  </Modal>
</template>

<script>
import sha1 from 'js-sha1'
import {loginuserinfo} from '~components/mixins'
export default {
  mixins: [loginuserinfo],
  data () {
    // 自定义用户名验证
    const userName = (rule, value, callback) => {
      var uPattern = /^[a-zA-Z0-9_-]{4,16}$/
      if (!uPattern.test(value)) {
        return callback(new Error('用户名为4-16个字符组成，可使用字母、数字'))
      } else {
        callback()
      }
    }
    const password = (rule, value, callback) => {
      var uPattern = /^[a-zA-Z0-9]{6,16}$/
      if (!uPattern.test(value)) {
        return callback(new Error('登录密码为6-16个字符组成，可使用字母、数字'))
      } else {
        callback()
      }
    }
    const checkName = (rule, value, callback) => {
      if (value) {
        this.$api.post('/member/login/is_exists', {username: this.regFormInline.user}).then(response => {
          if (response.code === 500) {
            return callback(new Error('用户名已存在'))
          } else {
            return callback()
          }
        })
      }
    }
    return {
      show: false,
      tabsActive: 'login',
      inputType: 'password',
      type: 'password',
      nump: 16, // 密码长度
      numn: 16, // 名字长度
      formInline: {
        user: '',
        password: '',
        password1: ''
      },
      ruleInline: {
        user: [{
          required: true,
          message: '请填写用户名',
          trigger: 'blur'
        }, {
          min: 4,
          message: '用户名为4-16个字符组成，包含字母、数字'
        }],
        password: [{
          required: true,
          message: '请输入密码',
          trigger: 'blur'
        }, {
          type: 'string',
          min: 6,
          message: '登录密码为6-16个字符组成，区分大小写',
          trigger: 'blur'
        }, {
          validator: password,
          message: '登录密码为6-16个字符组成，区分大小写'
        }]
      },
      regRuleInline: {
        user: [{
          required: true,
          message: '请填写用户名',
          trigger: 'blur'
        }, {
          min: 4,
          message: '用户名为4-16个字符组成，可使用字母、数字'
        }, {
          validator: userName
        }, {
          validator: checkName,
          trigger: 'blur'
        }],
        password: [{
          required: true,
          message: '请输入密码',
          trigger: 'blur'
        }, {
          type: 'string',
          min: 6,
          message: '登录密码为6-16个字符组成，区分大小写',
          trigger: 'blur'
        }, {
          validator: password,
          message: '登录密码为6-16个字符组成，区分大小写'
        }],
        password1: [{
          required: true,
          message: '请再次输入密码',
          trigger: 'blur'
        }, {
          type: 'string',
          min: 6,
          message: '登录密码为6-16个字符组成，区分大小写',
          trigger: 'blur'
        }, {
          validator: password,
          message: '登录密码为6-16个字符组成，区分大小写'
        }]
      },
      regFormInline: {
        user: '',
        readonly: true,
        password: '',
        password1: ''
      }
    }
  },
  methods: {
    // 密码可见
    showpwd (e) {
      if (e === 'inputType') {
        if (this.inputType === 'text') {
          this.inputType = 'password'
        } else {
          this.inputType = 'text'
        }
      } else if (e === 'type') {
        if (this.type === 'text') {
          this.type = 'password'
        } else {
          this.type = 'text'
        }
      }
    },
    // 注册
    register () {
      this.$refs['regFormInline'].validate((valid) => {
        if (valid) {
          if (this.regFormInline.password !== this.regFormInline.password1) {
            this.$Message.error('密码不一致!')
          } else {
            this.$api.post('member/login/insert', {
              username: this.regFormInline.user,
              pwd: this.regFormInline.password
            }).then(res => {
              if (res.code === 500) {
                this.$Message.error('用户名已存在！')
              } else {
                sessionStorage.setItem('user', JSON.stringify(res.data))
                this.$Message.success('注册成功!')
                this.loginuserinfo.userName = this.loginuserinfo.displayName === null ? '未认证' : this.loginuserinfo.displayName
                this.show = false
                window.location.reload()
              }
            }).catch(function (error) {
              console.log(error)
            })
          }
        }
      })
    },
    // 登录
    login () {
      this.$refs['formInline'].validate((valid) => {
        if (valid) {
          this.$api.post('/member/login/login', {
            username: this.formInline.user,
            pwd: sha1(this.formInline.password + 'sessionSALT')
          }).then(res => {
            if (res.data) {
              sessionStorage.setItem('user', JSON.stringify(res.data))
              this.$Message.success('登录成功!')
              this.show = false
              window.location.reload()
            } else {
              this.$Message.error('用户名或密码错误!')
            }
          }).catch(function (error) {
            console.log(error)
          })
        }
      })
    },
    handleCancel () {
    }
  }
}
</script>

<style lang="scss">
.vui-login{
  .ivu-modal-close{
    z-index: 9
  }
  // 登录模块显示密码
  .password{
    .ivu-icon-ios-eye-outline{
        display: none;
    }
    &:hover{
        .ivu-icon-ios-eye-outline{
            display: inline-block;
        }
    }
  }
}
</style>
