<template>
  <div id="app">
    <div class="nav vui-flex vui-flex-middle">
      <img src="../assets/logo.png" class="logo">
      <div class="vui-flex-item tr t-primary title">专注农村农业的服务平台</div>
    </div>

    <div class="header">
      <div class="card">
        <div class="user">
          <img :src="registrationMessage.image ? registrationMessage.image : './img/default-user-head.png'" width="100%" alt="">
        </div>
        <ul class="pl10">
          <li v-if="registrationMessage.accountFlag">用户名：{{registrationMessage.account}}</li>
          <li v-if="registrationMessage.nswyIdFlag">农事无忧账号：{{registrationMessage.nswyId}}</li>
          <li v-if="registrationMessage.realNameFlag">昵称：{{registrationMessage.realName}}</li>
          <li v-if="registrationMessage.locationFlag">所在区域：{{registrationMessage.location}}</li>
        </ul>
      </div>

     <template v-if="certificationData.length" >
        <p class="pd5 mt20 mb10 pl15 card-list-title"><b>会员信息</b></p>
        <ul class="list mb20">
          <li v-for="(item, index) in certificationData" :key="index">
            <div class="vui-flex vui-flex-middle" @click="onClick(item)">
              <div class="vui-flex-item pl10">
                <p class="member-name">{{item.memberName}}</p>
                <p class="t-grey mt10 member-class">{{item.memberClass}}</p>
              </div>
            </div>
          </li>
        </ul>
      </template>
    </div>
  </div>
</template>
<script lang="js">
export default {
  data () {
    return {
      registrationMessage: {},
      certificationData: [],
      user_id: '',
      templateId: ''
    }
  },
  created () {
    this.user_id = this.getUrl('account')
    this.templateId = this.getUrl('templateId')
    this.$api.post('/member-reversion/uesr/visitingCard/findVisitingCard', {
      account: this.user_id,
      templateId: this.templateId
    }).then(response => {
      if (response.code === 200) {
        this.registrationMessage = response.data.registerInfo
        this.certificationData = response.data.memberAptitude
      }
    })
  },
  methods: {
    // 取用户ID
    onClick (item) {
      this.$emit('on-detail', item)
    },
    // 解析URL
    getUrl (name) {
      var reg = new RegExp(`(^|\\?|&)${name}=([^&]*)(\\s|&|$)`, 'i')
      if (reg.test(location.href)) {
        return unescape(RegExp.$2.replace(/\+/g, ' '))
      } else {
        return ''
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.nav{
  padding: 15px 10px;
  background-color: #fff;
  .logo{
    width: 100px;
  }
  .title{
    font-size: 16px
  }
}
.header{
  background: #00C587;
  height: 160px;
  position: relative;
  padding: 15px;
  .card {
    margin-top: 80px;
    border-radius: 6px;
    padding: 60px 15px 15px;
    background: #FFFFFF;
    box-shadow: 0px 2px 12px 0px rgba(0,0,0,0.10);
    .user{
      width: 100px;
      height: 100px;
      margin: -100px auto 20px;
      border-radius: 100px;
      overflow: hidden;
    }
    li{
      padding: 8px 0;
      font-size: 15px;
    }
  }
  .card-list-title{
    font-size: 16px;
  }
  .list{
    background: #fff;
    li{
      padding: 15px;
      &:not(:last-child){
        border-bottom: 1px solid rgba(244,244,244,1);
      }
      .member-name{
        font-size: 16px;
      }
      .member-class{
        font-size: 14px;
      }
    }
    .user{
      width: 60px;
      height: 60px;
      border-radius: 100px;
    }
  }
}
</style>
