<template>
  <div id="app">
    <div class="nav vui-flex vui-flex-middle">
      <img src="../assets/logo.png" class="logo">
      <div class="vui-flex-item tr t-primary title">专注农村农业的服务平台</div>
    </div>

    <div class="header">
      <div class="card">
        <div class="user">
          <img :src="data.image ? data.image : '../../img/default-user-head.png'" width="100%" alt="">
        </div>
        <ul>
          <li>会员帐号：{{data.user_nswy_id}}</li>
          <li>用户名：{{data.user_id}}</li>
          <li>备注名称：{{data.user_name_remark}}</li>
          <li>会员简称：{{data.user_abbreviation}}</li>
          <li>手机号：{{data.phone}}</li>
          <li>座机号：{{data.seat_phone}}</li>
          <li class="vui-flex vui-flex-middle">
            <div class="vui-flex-item">QQ：{{data.qq_number}}</div>
            <div class="vui-flex-item">微信：{{data.wechat_number}}</div>
          </li>
          <li>邮箱：{{data.email}}</li>
          <li>网站地址：{{data.website_url}}</li>
          <li>所在位置：{{data.location}}</li>
        </ul>
      </div>

      <template v-if="list.length">
        <p class="pd5 mt20 mb10">他的联系人</p>
        <ul class="list">
          <li v-for="item in list" :key="item.user_nswy_id">
            <div class="vui-flex vui-flex-middle" @click="onClick(item)">
              <img :src="item.src" class="user" alt="">
              <div class="vui-flex-item pl15">
                <p>{{item.name}}</p>
                <p class="t-grey mt10">{{item.address}}</p>
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
      data: [],
      list: []
    }
  },
  created () {
    this.user_id = this.getUrl('user_id')
    this.loadData(this.user_id)
  },
  methods: {
    // 取数据
    loadData (id) {
      this.$api.post('member-reversion/realCertification/findList', { user_id: id }).then(res => {
        this.data = res.data
      })
    },
    // 取用户ID
    onClick (d) {
      this.loadData(d.user_nswy_id)
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
      padding: 15px 0;
    }
  }
  .list{
    background: #fff;
    li{
      padding: 15px;
      &:not(:last-child){
        border-bottom: 1px solid rgba(244,244,244,1);
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
