<template>
  <div>
    <Affix class="new-top-affix">
        <Row class="top-back">
        <div class="vui-layout">
            <span class="top-title">感谢湖北省人民政府丨亚洲开发银行 支持惠民项目</span>
            </div>
        </Row>
        <div style="background:#ffffff; border-bottom: 1px solid #ededed;">
            <div class="vui-layout">
                <Row type="flex" align="middle" class="top-nav">
                    <Col span="3">
                      <a :href="`${$url.toNswy}index`">
                          <img src="img/huiyuan-logo.png" alt="" width="98px" height="40px">
                      </a>
                    </Col>
                    <Col span="10" offset="7" class="ns-nav">
                        <Col span="4">
                            <a :href="`${$url.toNswy}index`">
                                无忧导航
                            </a>
                        </Col>
                        <Col span="4" offset="1">
                            <a :href="`${$url.toNswy}51index`" >
                                无忧首页
                            </a>
                        </Col>
                        <Col span="4" offset="1">
                            <a>
                                掌上无忧
                            </a>
                        </Col>
                        <Col span="4" offset="1">
                            <a :href="`${$url.toNswy}mapNav`">
                                地图导航
                            </a>
                        </Col>
                        <Col span="4" offset="1">
                            <a>
                              农业大数据
                            </a>
                        </Col>
                    </Col>
                    <Col span="3" offset="1" v-if="!loginuserinfo" class="tr">
                        <Button type="text" size="large" @click="loginuser">登录</Button>
                        <Button type="primary" size="large" class="ml10 mr10" @click="regist">注册</Button>
                    </Col>
                    <Col span="4" v-if="loginuserinfo" class="tr">
                        <a>
                            <Avatar :src="`http:${avatar}`" v-if="avatar" class="cus" />
                            <Avatar :src="`http://${$url.img}/static/img/user-icon-big.png`" class="cus" v-else />
                        </a>
                        <Dropdown style="vertical-align: middle; margin-left: 10px;">
                            <a href="javascript:void(0)">
                                <span v-if="displayName">
                                    <span v-if="displayName.length <= 6">{{displayName}}</span>
                                    <span v-else :title="displayName">{{displayName.substring(0,6)}}...</span>
                                </span>
                                <span v-else>
                                    <span v-if="loginuserinfo.loginAccount.length <= 10">{{loginuserinfo.loginAccount}}</span>
                                    <span v-else :title="loginuserinfo.loginAccount">{{loginuserinfo.loginAccount.substring(0,6)}}...</span>
                                </span>
                                <Icon type="ios-arrow-down" />
                            </a>
                            <DropdownMenu slot="list" style="margin-top: 10px;">
                                <DropdownItem>
                                    <a @click="handleGate('member')">会员中心</a>
                                </DropdownItem>
                                <DropdownItem>
                                    <a @click="handleGate('center')">应用中心</a>
                                </DropdownItem>
                                <DropdownItem>
                                    <a  @click="handleGate('gate')">我的门户</a>
                                </DropdownItem>
                                <DropdownItem>
                                    <a @click="editPassWord">修改密码</a>
                                </DropdownItem>
                                <DropdownItem divided>
                                    <a @click="logout">
                                        &nbsp;&nbsp;&nbsp;&nbsp;退出
                                    </a>
                                </DropdownItem>
                            </DropdownMenu>
                        </Dropdown>
                    </Col>
                </Row>
            </div>
        </div>
    </Affix>
    <login-register ref="loginRegister" @on-success="handleSuccess"></login-register>
  </div>
</template>
<script>
  import loginRegister from './components/loginRegister'

  export default {
    components: {
      loginRegister
    },
    data () {
      return {
        loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
        account: '',
        loginAccount: false,
        displayName: '',
        avatar: '',
        templateId: 0
      }
    },
    created () {
        if(this.$route.query.account){
            //说明是从会员中心跳转过来的，直接执行登录方法
            this.$api.get('/member/login/findbyname/'+this.$route.query.account).then(res=>{
                if(res.code == 200){
                    this.loginAccount = res.data.loginAccount
                    this.$refs['loginRegister'].$refs['login'].toLogin({username: res.data.loginAccount , pwd: res.data.loginPasswd})
                }
            })
        }else{
            //说明是退出的
        }
        if (this.loginuserinfo) {
            this.account = this.$user.loginAccount
            this.init()
            this.$api.post('/member-reversion/user/userTemplateManage/find', {
                    account: this.$user.loginAccount
                }).then(response => {
                    if (response.code === 200) {
                        if (response.data.userTemplate) {
                            this.templateId = response.data.userTemplate.templateId
                        }
                    }
                })
        }
    },
    methods: {
        // 点击修改密码
        editPassWord () {
            if (this.$user) {
                window.open(`${this.$url.toNswy}/auth/step7?templateId=${this.templateId}&active=networkInfo`)
            } else {
                this.loginuser()
                this.$Message.error('请先登录！')
            }
        },
        init () {
            this.$api.post('/member/login/findCurrentUser', {
                account: this.$user.loginAccount
            }).then(response => {
                if (response.data.displayName) {
                    this.displayName = response.data.displayName
                }
                if (response.data.avatar) {
                    this.avatar = response.data.avatar
                }
            })
        },
        // 登录成功的回调
        handleSuccess (response) {
            sessionStorage.setItem('key', response.data.key)
            response.data.proxy.forEach(element => {
                sessionStorage.setItem(element.account, JSON.stringify(element.session))
            })
            this.loginuserinfo = JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
            // window.location.reload()
            window.location.href = `${window.location.origin}${window.location.pathname}`
        },
      handleGate (type) {
        let login = this.loginuserinfo
        if (login) {
          // 我的门户
          if (type === 'gate') {
            // 查询用户是否实名
            this.$toPortals(this.$user.loginAccount)
            // window.location.href = `${this.$url.toNswy}portals/index?uid=${this.$user.loginAccount}`
          } else if (type === 'personal') {
            // 我的资料
            window.location.href = `${this.$url.toNswy}personalIndex/detail?uid=${this.loginAccount}`
          } else if (type === 'member') {
            // 会员中心
            window.location.href = `${this.$url.toNswy}pro/member?uid=${this.loginAccount}`
          } else if (type === 'center') {
            // 应用中心
            window.location.href = `${this.$url.toNswy}center`
          }
        }
      },
      // 点击登录显示登录界面
      loginuser () {
        this.$refs['loginRegister'].loginuser()
      },
      // 点击注册显示注册界面
      regist () {
        this.$refs['loginRegister'].regist()
      },
      // 退出登录
      logout () {
        sessionStorage.removeItem(sessionStorage.getItem('key'))
        this.$Message.success('退出成功！')
        this.loginuserinfo = null
        this.userName = ''
        window.location.href = `${window.location.origin}/live`
      }
    }
  }
</script>
<style lang="scss">
.vui-layout{
  width:1200px;
  margin:0 auto;
}
.top-back {
    background-color: #00c587;
    padding:3px;
    .top-title{
        color: #ffffff;
        font-size: 12px;
    }
}

.top-nav{
    height: 74px;
    .ns-nav a {
        display: block;
        height: 62px;
        line-height: 58px;
        font-size: 15px;
        text-align: center;
        color: #666;
        border-top: 4px solid #fff;
        margin-top:-4px;
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
    .cus.ivu-avatar {
        width: 32px;
        height: 32px;
        line-height: 31px;
        border-radius: 16px;
    }
}
</style>
