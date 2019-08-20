<template>
  <div>
    <Modal v-model="showLoginRegister" :width="width"  :mask-closable="false"  class="login-modle new-login">
        <div class="tc pt30">
          <img src="../../../src/img/huiyuan-logo.png" alt="" height="50px" width="120px">
          <div class="pt15">
            <img src="../../../src/img/loginTip.png" alt="" >
          </div>
        </div>
        <div class="pt30 pl30 pr30">
          <login v-if="active == '登录'" @on-success="handleSuccess" :active="active" ref="login"></login>
          <regrister v-if="active == '注册'" @on-read="handleRead" :active="active" @on-success="handleRegristerSuccess" ref="regrister"></regrister>
          <read-item v-if="active == '阅读'"></read-item>
          <register-success  v-if="active == '注册成功'" ref="registerSuccess"></register-success> 
        </div>
        <div v-if="active == '阅读'" class="tc pd10" slot="footer">
            <Button type="default" size="large" @click="agreeOrRefuse(false)">拒绝</Button>
            <Button type="primary"  size="large" @click="agreeOrRefuse(true)">同意</Button>
        </div>
        <div v-else-if="active == '注册成功'"  slot="footer">&nbsp;</div>
        <div slot="footer" class="tc new-login-footer" v-else>
            <p v-if="active == '登录' && type === '普通登录注册'"><span class="t-grey">没有账号？</span><span class="t-green" @click="active = '注册'" style="cursor: pointer;">注册</span></p>
            <p v-if="active == '注册' && type === '普通登录注册'"><span class="t-grey">已有账号？</span><span class="t-green" @click="active = '登录'" style="cursor: pointer;">登录</span></p>
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
    props: {
      type: {
        type: String,
        default: '普通登录注册'
      }
    },
    data () {
      return {
        showLoginRegister: false,
        active: '登录',
        width: '420'
      }
    },
    created() {
      
    },
    methods: {
      // 登录成功
      handleSuccess (response) {
        this.showLoginRegister = false
        this.$emit('on-success', response)
      },
      // 注册成功
      handleRegristerSuccess (response) {
        if (this.type === '普通登录注册') {
          this.active = '注册成功'
          this.width = '570'
          this.$nextTick(() => {
            this.$refs['registerSuccess'].id = response.data.proxy[0].session.nswyIdModel
            sessionStorage.setItem('key', response.data.key)
            response.data.proxy.forEach(element => {
              sessionStorage.setItem(element.account, JSON.stringify(element.session))
            })
          })
        } else if (this.type === '代理登录注册') {
          console.log('代理登录注册', response)
          this.showLoginRegister = false
          this.$emit('on-success', response)
        }
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
      //点击注册
      regist() {
        this.active = '注册'
        this.width = '420'
        this.showLoginRegister = true
        this.$nextTick(() => {
          this.$refs['regrister'].typep = 'password'
          this.$refs['regrister'].typep2 = 'password'
          this.$refs['regrister'].isSuccess = false
        })
      },
    }
  }
</script>
<style lang="scss">
// 登录部分
.new-login{
    .ivu-input-group-append, .ivu-input-group-prepend{
        background-color: #fff;
    }
    .ivu-modal-footer{
      padding:0px;
      .new-login-footer{
        background: #F7F7F7;
        padding: 12px 18px 12px 18px;
      }
    }
}
</style>

