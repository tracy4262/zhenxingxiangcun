<template>
  <div class="new-login">
    <Form ref="loginData" :model="loginData" :rules="ruleInline">
      <Form-item prop="user">
        <i-input type="text" size="large" v-model.trim="loginData.user" placeholder="请输入用户名">
          <span slot="prepend" class="pr10 pl10">
            <Icon type="ios-person-outline" class="pr10" size="18"></Icon>账号
          </span>
        </i-input>
      </Form-item>
      <Form-item prop="password">
        <i-input type="password" size="large" v-model.trim="loginData.password" placeholder="请输入密码" @keyup.enter.native="login">
          <span slot="prepend" class="pr10 pl10">
            <Icon type="ios-locked-outline" class="pr10" size="18"></Icon>密码
          </span>
        </i-input>
      </Form-item>
      <Form-item prop="isSuccess">
        <div id="TencentCaptcha1" v-if="!loginData.isSuccess">
          <i-input v-model="pictureText" size="large" readonly @click.native="showCode"></i-input>
          <!-- <Button type="ghost"  long>点我进行图片验证</Button> -->
        </div>
        <div v-if="loginData.isSuccess">
          <Button type="ghost" size="large" long icon="ios-checkmark">验证成功</Button>
        </div>
      </Form-item>
      <Form-item>
        <Row>
          <Col span="12">
          <Checkbox v-model="loginData.remberPassWord" @on-change="handleChange">记住密码</Checkbox>
          </Col>
          <Col span="12" class="tr t-grey">
          <span style="cursor: pointer;">忘记密码?</span>
          </Col>
        </Row>
      </Form-item>
      <Form-item style="box-shadow: 0 5px 18px 0 rgba(86,176,125,0.46);">
        <Button type="primary" size="large" @click="login" long>登录</Button>
      </Form-item>
    </Form>
  </div>
</template>
<script>
/* eslint-disable */
import sha1 from './js-sha1.js'

export default {
  props: {
    active: {
      type: String,
      default: '登录'
    }
  },
  data () {
    const password = (rule, value, callback) => {
      var uPattern = /^[a-zA-Z0-9]{6,16}$/
      if (!uPattern.test(value)) {
        return callback(new Error('登录密码为6-16个字符组成，可使用字母、数字'))
      } else {
        callback()
      }
    }
    return {
      loginData: {
        user: '',
        password: '',
        loginData: '',
        remberPassWord: false,
        isSuccess: ''
      },
      isSuccess: false,
      pictureText: '点我进行图片验证',
      ruleInline: {
        user: [{
          required: true,
          message: '请填写用户名',
          trigger: 'blur'
        }, {
          min: 4,
          message: '用户名为4-16个字符组成，包含字母、数字'
        }],
        isSuccess: [{
          required: true,
          type: 'string',
          message: '请进行图片验证'
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
      }
    }
  },
  created () {
    let that = this
    let data = JSON.parse(localStorage.getItem('ns51pwd'))
    if (data) {
      this.loginData.user = data.user
      this.loginData.password = data.password
      this.loginData.remberPassWord = data.remberPassWord
      this.password = data.password
    }
  },
  methods: {
    showCode () {
      let that = this
      var captcha1 = new TencentCaptcha('2028910108', function (res) {
        // that.isSuccess = false
        // res（未通过验证）= {ret: 1, ticket: null}
        // res（验证成功） = {ret: 0, ticket: "String", randstr: "String"}
        if (res.ret === 0) {
          console.log('success')
          that.loginData.isSuccess = 'true'
          that.$refs['loginData'].validate()
        }
        if (res.ret === 1) {
          that.loginData.isSuccess = ''
        }
      });
      captcha1.show()
    },
    handleChange (e) {
      console.log(e)
    },
    // 获取结果
    handleResult (e) {
      this.isSuccess = e
    },
    // 登录
    login () {
      this.$refs['loginData'].validate((valid) => {
        if (valid) {
          let list = {
            username: this.loginData.user,
            pwd: sha1.SHA2(this.loginData.password + 'sessionSALT')
          }
          this.$api.post('/member/login/login', list).then(response => {
            if (response.data) {
              if (this.loginData.remberPassWord) {
                localStorage.removeItem('ns51pwd')
                localStorage.setItem('ns51pwd', JSON.stringify(this.loginData))
              }
              if (response.data.userType == 10) {
                  sessionStorage.setItem("user", JSON.stringify(response.data))
                  // /user-auth-admin/auth/step1 管理员模板配置 路径 
                  window.location.href = `${window.location.origin}/user-auth-admin/auth/step1`
              } else {
                  this.$Message.success('登录成功!')
                  this.$emit('on-success', response)
              }
            } else {
              this.loginData.isSuccess = ''
              this.$Message.error('用户名或密码错误!')
            }
          })
        }
      })
    }
  }
}
</script>
<style>
#TencentCaptcha1 .ivu-input {
  cursor: pointer;
  text-align: center;
}
#TencentCaptcha2 .ivu-input {
  cursor: pointer;
  text-align: center;
}
</style>
