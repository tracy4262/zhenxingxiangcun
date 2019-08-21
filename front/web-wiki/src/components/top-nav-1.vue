<template>
  <div>
    <Affix>
      <div class="ns-header">
        <Row type="flex" align="middle" class="top-nav">
            <Col span="2">
              <a :href="`${$url.serverUrl}index`" class="logo">
                <img src="../assets/imgs/banner-logo.png" alt="" width="100%" height="100%">
              </a>
            </Col>
            <Col span="8" :offset="0" class="ns-nav pl10">
              <Col span="6">
                <a :href="`${$url.serverUrl}index`" class="vui-top-nav-item">
                  <Icon type="monitor"></Icon> 无忧导航
                </a>
              </Col>
              <Col span="6">
                <a :href="`${$url.serverUrl}mall/index`" class="vui-top-nav-item">
                  <Icon type="ios-home"></Icon> 无忧首页
                </a>
              </Col>
              <Col span="6">
                <a :href="`${$url.serverUrl}mapNav`" class="vui-top-nav-item">
                  <Icon type="map"></Icon> 地图导航
                </a>
              </Col>
            </Col>
            <Col span="2" :offset="9" class="tc">
                <Dropdown class="mr15">
                    <a @click="handleGate('member')" class="vui-dropdown-sub">
                    会员中心
                    <Icon type="chevron-down"></Icon>
                  </a>
                    <DropdownMenu slot="list">
                        <DropdownItem>
                        <a @click="handleGate('personal')">我的资料</a>
                        </DropdownItem>
                        <DropdownItem >
                          <div  @click="handleGate('gate')">我的门户</div>
                        </DropdownItem>
                        <DropdownItem >
                          <a @click="handleGate('center')" class="vui-dropdown-sub">应用中心</a>
                        </DropdownItem>
                    </DropdownMenu>
                </Dropdown>
            </Col>
            <Col span="3" >
              <template v-if="loginuserinfo === null">
                <span class="vui-dropdown-sub">
                  <Button type="ghost" @click.native="registerBtn('注册')" class="ml10 mr10">注册</Button>
                  <Button type="primary" @click.native="loginBtn('登录')">登录</Button>
                </span>
              </template>
              <template v-else>
                <span style="margin-left: 5px;margin-right: 5px;" v-if="loginuserinfo.loginAccount.length <= 10">{{ loginuserinfo.loginAccount }}</span>
                <span style="margin-left: 5px;" v-else :title="loginuserinfo.loginAccount">{{ substr(loginuserinfo.loginAccount) }}...</span>
                  <Dropdown style="vertical-align: middle;">
                    <a >
                      <Avatar :src="loginuserinfo.avatar" v-if="loginuserinfo.avatar"/>
                      <Avatar src="./static/imgs/user-icon-big.png" v-else/>
                    </a>
                    <DropdownMenu slot="list">
                      <DropdownItem v-if="loginuserinfo.displayName !== undefined && loginuserinfo.displayName !== '' && loginuserinfo.displayName !== null">
                          <a>{{loginuserinfo.displayName}}</a>
                      </DropdownItem>
                      <DropdownItem divided v-if="loginuserinfo.displayName !== undefined && loginuserinfo.displayName !== '' && loginuserinfo.displayName !== null">
                          <a>账户安全</a>
                      </DropdownItem>
                      <DropdownItem v-else>
                          <a>账户安全</a>
                      </DropdownItem>
                      <DropdownItem>
                          <a :href="[this.$url.shop + '/center/address.htm?uid='+loginuserinfo.uniqueId]" target="_blank">收货地址</a>
                      </DropdownItem>
                      <DropdownItem>
                          <a :href="[this.$url.shop +'/center/order/list.htm?uid='+loginuserinfo.uniqueId]" target="_blank">消费记录</a>
                      </DropdownItem>
                      <DropdownItem divided>
                          <a @click="logout">
                              <Icon type="log-out"></Icon> 退出
                          </a>
                      </DropdownItem>
                  </DropdownMenu>
                </Dropdown>
              </template>
            </Col>
        </Row>

        <!-- 登录弹窗 -->
        <login-modal  ref="loginModal"/>

      </div>
    </Affix>
  </div>
</template>

<script>
import loginRegister from '~components/loginRegister/index'
import {loginuserinfo} from '~components/mixins'
export default {
  mixins: [loginuserinfo],
  props: {
    searchBtn: {
      type: Boolean,
      default: false
    },
    goShop: {
      type: Boolean,
      default: false
    }
  },
  components: {
    loginModal
  },
  data () {
    return {
      address: '',
      cityData: [],
      comData: [],
      loginModalShow: false,
      userName: '',
      loginAccount: ''
    }
  },
  created () {
    if (this.loginuserinfo) this.userName = this.loginuserinfo.displayName === null ? '未认证' : this.loginuserinfo.displayName
  },
  methods: {
    handleGate (type) {
      let login = this.checkLogin()
      if (login) {
        // 我的门户
        if (type === 'gate') {
          this.$api.post('/member/login/findCurrentUser', {
            account: this.loginAccount
          })
            .then(response => {
              if (response.code === 200) {
                if (response.data.userType !== undefined) {
                  let userType = response.data.userType
                  // 用户类型 0 个人用户  1 企业 2政府 3机关4专家 5乡村
                  if (userType === 1) {
                    window.location.href = `${this.$url.serverUrl}companyGate/index?uid=${this.loginAccount}`
                  } else if (userType === 2) {
                  } else if (userType === 3) {
                    window.location.href = `${this.$url.serverUrl}govGate/index?uid=${this.loginAccount}`
                  } else if (userType === 4) {
                    window.location.href = `${this.$url.serverUrl}expertGate/index?uid=${this.loginAccount}`
                  } else if (userType === 5) {
                    window.location.href = `${this.$url.serverUrl}ruralGate/index?uid=${this.loginAccount}`
                  } else {
                    window.location.href = `${this.$url.serverUrl}personGate/index?uid=${this.loginAccount}`
                  }
                }
              }
            })
        } else if (type === 'personal') {
          // 我的资料
          window.location.href = `${this.$url.serverUrl}personalIndex/detail?uid=${this.loginAccount}`
        } else if (type === 'member') {
          // 会员中心
          window.location.href = `${this.$url.serverUrl}pro/member?uid=${this.loginAccount}`
        } else if (type === 'center') {
          // 应用中心
          window.location.href = `${this.$url.serverUrl}center`
        }
      }
    },
    // 判断是否登录
    checkLogin () {
      if (!this.loginuserinfo) {
        this.$Message.error('请先登录')
        this.loginBtn('登录')
        return false
      } else {
        this.loginAccount = this.loginuserinfo.loginAccount
        return true
      }
    },
    // 选择地区回调
    CascaderChange (value, selectedData) {
      this.address = selectedData[selectedData.length - 1].label
    },
    loadData (item, callback) {
      item.loading = true
      this.$.post(`wiki/town/next/${item.value}`).then(res => {
        item.loading = false
        item.children = res.data
        callback()
      })
    },
    // 注册
    registerBtn (val) {
      let login = this.$refs.loginModal
      login.tabsActive = 'reg'
      login.show = true
    },
    loginBtn (val) {
      let login = this.$refs.loginModal
      login.tabsActive = 'login'
      login.show = true
    },
    // 退出登录
    logout () {
      sessionStorage.removeItem('user')
      this.$Message.success('退出成功！')
      this.loginuserinfo = null
      this.userName = ''
      window.location.reload()
    }
  }
}
</script>

<style lang="scss" scoped>
.ns-header {
    border-bottom: 1px solid #ededed;
    min-width:1200px;
    background-color: #fff;
}
.top-nav{
    .logo{
        display: block;
        padding: 10px;
    }
    .ns-nav a {
        display: block;
        height: 62px;
        line-height: 58px;
        font-size: 15px;
        text-align: center;
        color: #666;
        border-top: 4px solid #fff;
        &:hover {
            color: #00c587 !important;
        }
        &.ns-active {
            border-top-color:#00c587;
            color: #00c587 !important;
        }
    }
    .ivu-dropdown-rel,
    .ivu-dropdown-menu{
        a{
            font-size: 15px;
            color:#666;
        }
    }
    .ivu-dropdown-menu{
        min-width: auto
    }
}
  // .vui-top-nav{
  //   background-color: #fff;
  //   min-width: 1200px;
  //   font-size: 14px;
  //   border-bottom: 1px solid $border-color;
  //   .logo{
  //     display: block;
  //     padding: 15px;
  //   }
  //   .sel{
  //     padding: 24px 0 25px;
  //     width: 80px;
  //   }
  //   &-item,
  //   .sel{
  //     display: inline-block;
  //     vertical-align: middle;
  //   }
  //   &-item{
  //     color:#333;
  //     padding: 28px 10px 29px;
  //     font-size: 16px;
  //     transition: color .3s;
  //     &:hover{
  //       color:$green
  //     }
  //   }
  //   .vui-dropdown-sub{
  //     color:#333;
  //     font-size: 16px;
  //     display: block;
  //     padding: 24px 0 25px;
  //   }
  //   .vui-user-bar{
  //     .vui-dropdown-sub{
  //       padding:30px 0;
  //     }
  //   }
  // }
</style>
