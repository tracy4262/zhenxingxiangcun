<template>
<div>
  <detail-title :name="sellerData.name" :avatar="sellerData.avatar" :account="sellerData.account" grey>
    <Button type="text" size="small" @click.stop="webimchat(sellerData.userId, sellerData.name, sellerData.avatar)"><Icon type="md-text" class="t-green"  size="16"></Icon> 发起聊天</Button>
  </detail-title> 
  <!-- <ul class="list">
    <li>
      <Icon type="ios-call" class="mr5" size="16"></Icon>
      联系电话： {{sellerData.phone}}
    </li>
    <li>
      <Icon type="ios-mail" class="mr5" size="16"/>
      邮箱： {{sellerData.email}}
    </li>
    <li v-if="sellerData.qq">
      <Icon type="md-text" class="mr5" size="16"/>
      QQ号码： {{sellerData.qq}}
    </li>
  </ul> -->
</div>
</template>

<script>
import detailTitle from './title'
export default {
  props: {
    sellerData: Object
  },
  components: {
    detailTitle
  },
  data () {
    return {
      loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
      account: ''
    }
  },
  created() {
    if (this.loginUser) {
      this.account = this.loginUser.loginAccount
    }
  },
  methods: {
    // 聊天
    webimchat (userId, name, avatar) {
      if (!this.account) {
        this.$Message.error('请登录后再发起聊天')
        this.$emit('on-login')
        return
      } 
      layui.layim.chat({
        id: this.sellerData.userId,
        name: this.sellerData.name,
        avatar: this.sellerData.avatar,
        type: 'friend'
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.list{
  padding: 10px 15px;
  border: 1px solid #EDEDED;
  border-top: none;
  li{
    color:#999;
    margin: 8px 0;
  }
}
</style>
