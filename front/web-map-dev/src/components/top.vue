<template>
  <div class="nav-top m-w1200">
    <div class="top-back">
      <div class="top-title">感谢湖北省人民政府丨亚洲开发银行 支持惠民项目</div>
    </div>
    <!-- <Row>
      <Col></Col>
      <Col></Col>
    </Row> -->
    <!-- <div class="w1200 vui-flex vui-flex-middle nav">
      <div class="vui-flex logo vui-flex-middle">
        <img class="vui-flex-item vui-flex" src="../assets/img/logo.png" alt="" style="width:90px; height:45px"  width="90px" height="45px">
        <b class="title pl15">我的地图</b>
      </div>
      <div class="vui-flex-item"> -->
        <Row  class="w1200 nav" type="flex" align="middle">
          <Col span="11">
            <div class="vui-flex logo vui-flex-middle">
              <img class="" src="../assets/img/logo.png" alt="" style="width:90px; height:45px"  width="90px" height="45px" @click="handlHome">
              <b class="title pl15 vui-flex-item">我的地图</b>
            </div>
          </Col>
          <Col span="2" class="tc tab" :class="[$route.name == 'view' ? 'active' : '']">
            <router-link to="/">
                数据展示
            </router-link>
          </Col>
          <Col span="2" offset="1" class="tc tab" :class="[$route.name == 'edit' ? 'active' : '']">
            <router-link to="/edit">
                数据标注
            </router-link>
          </Col>
          <Col span="2" offset="1" class="tc tab" :class="[$route.name == 'addMap' ? 'active' : '']">
            <router-link to="/addMap">
                地图导入
            </router-link>
          </Col>
          <Col span="4" offset="1" class="tr" @click.native="handlMember">
            <div>
              <a>
                <Avatar :src="avatar" v-if="avatar" class="cus" />
                <Avatar :src="defaultAvatar" class="cus" v-else />
              </a>
              <Dropdown style="vertical-align: middle; margin-left: 10px; cursor: pointer;">
                  <div>
                    <span v-if="displayName">
                      <span v-if="displayName.length <= 6">{{displayName}}</span>
                      <span v-else :title="displayName">{{displayName.substring(0,6)}}...</span>
                    </span>
                    <span v-else>
                        <span v-if="$user.loginAccount.length <= 10">{{$user.loginAccount}}</span>
                        <span v-else :title="$user.loginAccount">{{$user.loginAccount.substring(0,6)}}...</span>
                    </span>
                    <Icon type="ios-arrow-down" />
                  </div>
                <!-- <DropdownMenu slot="list" style="margin-top: 10px;">
                    <DropdownItem>
                        <a @click="isLogin('/pro/member')">会员中心</a>
                    </DropdownItem>
                    <DropdownItem>
                        <a @click="isLogin('/center')">应用中心</a>
                    </DropdownItem>
                    <DropdownItem>
                        <a  @click="isLogin('iswho')">我的门户</a>
                    </DropdownItem>
                    <DropdownItem>
                        <a href="javascript:void(0)" target="_blank">修改密码</a>
                    </DropdownItem>
                    <DropdownItem divided style="text-align: center;">
                        <a @click="logout">
                            退出
                        </a>
                    </DropdownItem>
                </DropdownMenu> -->
            </Dropdown>
            </div>
          </Col>
        </Row>
      <!-- </div> -->
    </div>
  </div>
</template>

<script>
import defaultAvatar from '@/assets/img/user-icon-big.png';
export default {
  name: 'nav-top',
  data () {
    return {
      loginuserinfo: {},
      displayName: '',
      avatar: '',
      defaultAvatar: defaultAvatar,
      activeIndex: 1
    }
  },
  created () {
    // 根据用户名查询 头像跟 displayName
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
  methods: {
    handlHome () {
      window.location.href = `${window.location.origin}/index`
    },
    handlMember () {
      window.location.href = `${window.location.origin}/pro/member?uid=${this.$user.loginAccount}`
    }
  }
}
</script>

<style lang="less" scoped>
@import '../css/colors.less';
  .nav-top{
    border-bottom: 1px solid #ededed;
    background: #fff;
    // background-image: linear-gradient(to bottom, #383838 0%, #6e6e6e 100%);
    .nav{
      height: 69px;
      .logo{
        line-height: 65px;
        .title{
          color: #666;
          font-size: 18px;
        }
      }
      .tab{
        font-size: 15px;
        height: 65px;
        color: #666;
        height: 56px;
        line-height: 65px;
        cursor: pointer;
        border-top: 4px solid  #fff;
        &:hover{
          color: @link-color;
        }
      }
      .active{
        border-top: 4px solid  @link-color;
        color: @link-color!important;
      }
    }
  }
  .top-title {
    /* margin-left: 120px; */
    color: #ffffff;
    font-size: 12px;
    width: 1200px;
    margin: auto;
  }
  .top-back {
    background-color: @link-color;
    padding: 3px;
  }
</style>
