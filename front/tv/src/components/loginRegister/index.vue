<template>
  <div>
    <Modal v-model="showLoginRegister" :width="width" :mask-closable="false" class="login-modle new-login">
      <div class="tc pt30">
        <img src="../../img/huiyuan-logo.png" alt="" height="50px" width="120px">
        <div class="pt15">
          <img src="../../img/loginTip.png" alt="">
        </div>
      </div>
      <div class="pt30 pl30 pr30">
        <login v-if="active == '登录'" @on-success="handleSuccess" :active="active" ref="login"></login>
        <regrister v-if="active == '注册'" @on-read="handleRead" :active="active" @on-success="handleRegristerSuccess" ref="regrister"></regrister>
        <read-item v-if="active == '阅读'"></read-item>
        <register-success v-if="active == '注册成功'" ref="registerSuccess"></register-success>
      </div>
      <div v-if="active == '阅读'" class="tc pd10" slot="footer">
        <Button type="ghost" size="large" @click="agreeOrRefuse(false)">拒绝</Button>
        <Button type="primary" size="large" @click="agreeOrRefuse(true)">同意</Button>
      </div>
      <div v-else-if="active == '注册成功'" slot="footer">&nbsp;</div>
      <div slot="footer" class="tc new-login-footer" v-else>
        <p v-if="active == '登录'">
          <span class="t-grey">没有账号？</span>
          <span class="t-green" @click="active = '注册'" style="cursor: pointer;">注册</span>
        </p>
        <p v-if="active == '注册'">
          <span class="t-grey">已有账号？</span>
          <span class="t-green" @click="active = '登录'" style="cursor: pointer;">登录</span>
        </p>
      </div>
    </Modal>
  </div>
</template>
<script>
import login from './login'
import regrister from './register'
import readItem from './readItem'
import registerSuccess from './registerSuccess'
export default {
  components: {
    login,
    regrister,
    readItem,
    registerSuccess
  },
  data () {
    return {
      showLoginRegister: false,
      active: '登录',
      width: '420'
    }
  },
  created () {

  },
  methods: {
    // 登录成功
    handleSuccess (response) {
      this.showLoginRegister = false
      this.$emit('on-success', response)
    },
    // 注册成功
    handleRegristerSuccess (response) {
      this.active = '注册成功'
      this.width = '570'
      this.$nextTick(() => {
        this.$refs['registerSuccess'].id = response.data.nswyIdModel
        sessionStorage.setItem('user', JSON.stringify(response.data))
      })
    },
    // 点击阅读条款
    handleRead () {
      this.width = '800'
      this.active = '阅读'
    },
    // 点击同意条款 or 点击拒绝条款
    agreeOrRefuse (e) {
      this.width = '420'
      this.active = '注册'
      this.$nextTick(() => {
        this.$refs['regrister'].isAgree = e
      })
    },
    // 点击登录
    loginuser () {
      this.active = '登录'
      this.width = '420'
      this.showLoginRegister = true
      this.$nextTick(() => {
        this.$refs['login'].isSuccess = false
      })
    },
    // 点击注册
    regist () {
      this.active = '注册'
      this.width = '420'
      this.showLoginRegister = true
      this.$nextTick(() => {
        this.$refs['regrister'].typep = 'password'
        this.$refs['regrister'].typep2 = 'password'
        this.$refs['regrister'].isSuccess = false
      })
    }
  }
}
</script>
<style lang="scss">
.pt10{
  padding-top:10px;
}
.pt30{
  padding-top:30px;
}
.pb20{
  padding-bottom: 20px;
}
.pt40{
  padding-top:40px;
}
.pt15{
  padding-top:15px;
}
.pr10{
  padding-right:10px
}
.pr30{
  padding-right:30px
}
.pl30{
  padding-left:30px
}
.mr20{
  margin-right:20px;
}
.tr{
  text-align: right;
}
.t-green{
  color: #00C587 !important;
}
.t-grey{
  color: #9B9B9B !important;
}
// 登录部分
.new-login {
  .ivu-input-group-append,
  .ivu-input-group-prepend {
    background-color: #fff;
  }
  .ivu-modal-footer {
    padding: 0px;
    .new-login-footer {
      background: #f7f7f7;
      padding: 12px 18px 12px 18px;
    }
  }
}
</style>

