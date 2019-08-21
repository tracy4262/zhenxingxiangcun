<template>
  <div class="tc new-register">
    <h3 class="pt30">
      <Icon type="ios-checkmark" class="t-green pr10" size="44" style="vertical-align: top;"></Icon>
      <span class="success">注册成功</span>
    </h3>
    <p class="pt40" style="font-size:18px;">恭喜您申请成功</p>
    <p class="pt10" style="font-size:16px;">您获得农事无忧号：
      <span class="t-green" style="text-decoration: underline;">{{id}}</span>
    </p>
    <div class="pt30 pb20">
      <Button type="primary" size="large" class="mr20" @click="handleMember">进入会员中心</Button>
      <Button type="ghost" size="large" @click="handleReload">返回当前页面</Button>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      id: ''
    }
  },
  methods: {
    // 进入会员中心
    handleMember () {
      window.location.href = `${window.location.origin}/pro/member`
    },
    // 返回当前页面
    handleReload () {
      window.location.reload()
    }
  }
}
</script>
<style  lang="scss" scoped>
.new-register {
  .success {
    font-size: 26px;
  }
}
</style>

