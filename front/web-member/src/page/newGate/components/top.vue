<template>
  <div class="new-gate-top">
    <div class="new-gate-top-layout">
      <Row>
        <Col span="8">
        <div class="pl20">
          <Row>
            <Col span="4">
              <img src="../../../img/huiyuan-logo.png" alt="" width="49px" height="20px">
            </Col>
            <Col span="4">
             <router-link to="/index">无忧导航</router-link>
            </Col>
             <Col span="4">
             <router-link to="/51index">无忧首页</router-link>
            </Col>
             <Col span="4">
             <router-link to="/mapNav">地图导航</router-link>
            </Col>
             <Col span="4">
             <a href="/pocket">掌上无忧</a>
            </Col>
             <Col span="4">
             <a href="javascript:void(0)">农业大数据</a>
            </Col>
          </Row>

        </div>
        </Col>
        <Col span="8" offset="8" class="tc" v-if="$user">
        <div class="tr">
           <Row>
              <Col span="11" :offset="13">
                <Dropdown style="vertical-align: middle;" class="tc">
                    <a style="color: #666;">
                        <span v-if="displayName">
                            <span v-if="displayName.length <= 6">{{displayName}}</span>
                            <span v-else :title="displayName">{{displayName.substring(0,6)}}...</span>
                        </span>
                        <span v-else>
                            <span v-if="account.length <= 10">{{account}}</span>
                            <span v-else :title="account">{{account.substring(0,6)}}...</span>
                        </span>
                        <Icon type="md-arrow-dropdown" size="18"/>
                    </a>
                    <DropdownMenu slot="list" style="margin-top: 10px;">
                        <DropdownItem>
                            <router-link :to="`/pro/member?uid=${this.$user.loginAccount}`">会员中心</router-link>
                        </DropdownItem>
                        <DropdownItem>
                            <router-link :to="`/center?uid=${this.$user.loginAccount}`">应用中心</router-link>
                        </DropdownItem>
                        <DropdownItem>
                            <a @click="toPortals">我的门户</a>
                        </DropdownItem>
                        <DropdownItem>
                            <a href="javascript:void(0)" target="_blank">修改密码</a>
                        </DropdownItem>
                        <!-- <DropdownItem>
                            <a href="javascript:void(0)" target="_blank">收货地址</a>
                        </DropdownItem>
                        <DropdownItem>
                            <a href="javascript:void(0)" target="_blank">消费记录</a>
                        </DropdownItem> -->
                        <DropdownItem divided class="tc">
                            <a @click="logout">
                                退出
                            </a>
                        </DropdownItem>
                    </DropdownMenu>
                </Dropdown>
              </Col>
            </Row>
          
        </div>
        </Col>
        <Col span="8" offset="8" class="tr" v-else>
          <Button size="small"  @click="loginuser()">登录</Button>
          <Button type="primary" size="small" class="ml10 mr10" @click="regist()">注册</Button>
        </Col>
      </Row>
    </div>
       <login-register ref="loginRegister" @on-success="handleSuccess"></login-register>
  </div>
</template>
<script>
// import VueQrCode from '@xkeshi/vue-qrcode'
import QRCode from 'qrcode'
import loginRegister from '~components/loginRegister/index'
  export default {
    components: {
      QRCode,
      loginRegister
    },
    data () {
      return {
        account: '',
        displayName: '',
        qrCodeUrl: '',
        templateId: ''
      }
    },
    created() {
      if (this.$user) {
        this.account = this.$user.loginAccount
        this.init()
      }
      this.$api.post('/member-reversion/realStep/findEnableStep', {
            account: this.$route.query.uid
        }).then(response => {
            if (response.code === 200) {
                if (response.data) {
                    this.templateId = response.data.templateId
                    // this.qrCodeUrl = `${window.location.origin}/nswy-member-info?account=${this.$route.query.uid}&templateId=${this.templateId}`
                    // this.useqrcode()
                }
            }
        })
    },
    // mounted() {
    //   this.useqrcode()
    // },
    methods: {
      toPortals () {
        this.$toPortals(this.$user.loginAccount)
      },
      useqrcode () {
        var canvas = this.$refs['canvas']
        console.log('8888888888888888')
        QRCode.toCanvas(canvas, this.qrCodeUrl, function (error) {
          if (error) console.error(error)
          console.log('QRCode success!')
        })
      },
      init () {
          this.$api.post('/member/login/findCurrentUser', {
              account: this.$user.loginAccount
          }).then(response => {
              if (response.data.displayName) {
                  this.displayName = response.data.displayName
              }
          })
      },
      //点击登录显示登录界面
      loginuser() {
          this.$refs['loginRegister'].loginuser()
      },
      //点击注册显示注册界面
      regist() {
          this.$refs['loginRegister'].regist()
      },
      // 登录成功的回调
      handleSuccess (response) {
          sessionStorage.setItem('key', response.data.key)
          response.data.proxy.forEach(element => {
              sessionStorage.setItem(element.account, JSON.stringify(element.session))
          })
          this.loginuserinfo = JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
          window.location.reload()
      },
      logout () {
        var path=this.$route.path
        this.$api.get('/member/login/logout')
        sessionStorage.removeItem(sessionStorage.getItem('key'))
        if('/index'==path){
            window.location.reload()
        }else{
            this.$router.push('/index')
            window.location.reload()
        }
      }
    }
  }
</script>
<style lang="scss">
.new-gate-top {
  background: #F0F2F5;
  min-width: 1200px;
  .new-gate-top-layout{
    // min-width: 1366px;
    width: 1200px;
    margin: 0 auto;
    padding: 8px 0px;
    a{
      color: #4A4A4A;
      font-family: PingFangSC-Regular;
      font-size: 12px;
      &:hover{
        color:#00c587;
      }
    }
    .carte{
      position: relative;
      .qr-code-url{
        position: absolute;
        // display: none;
        top: 30px;
        width: 100%;
        #canvas{
          width:70px !important;
          height: 70px !important;
        }
        .qr-code-border{
          position: absolute;
          top: 5px;
          left: 5px;
          background: #fff;
          width: 74px;
          height: 74px;
          z-index: 999;
          border: 2px solid #00c587;
        }
        &::before{
          content: " ";
          display: inline-block;
          width: 10px;
          height: 10px;
          background: #00c587;
          transform:rotate(45deg);
          -ms-transform:rotate(45deg); 	/* IE 9 */
          -moz-transform:rotate(45deg); 	/* Firefox */
          -webkit-transform:rotate(45deg); /* Safari 和 Chrome */
          -o-transform:rotate(45deg); 	/* Opera */
          position: absolute;
          left: 35px;
        }
      }
      &:hover{
        .qr-code-url{
          display: block;
        }
      }
    }
  }
}
</style>
