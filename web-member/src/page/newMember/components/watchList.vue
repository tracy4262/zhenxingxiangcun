<template>
  <div class="bg-white">
    <Card :bordered="false" class="member-watch-list">  
        <!-- 消息通知 -->
        <p class="pt10"><img src="../../../img/chat.png"  alt="" class="mr10" width="20px" height="20px">消息通知</p>
        <div class="pt20 pb20">
          <router-link to="" class="my-watch">查看我的消息通知>></router-link>
        </div>
        <!-- 关注列表 -->
        <p class="pt10"><img src="../../../img/follow.png"  alt="" class="mr10" width="20px" height="20px">我的关注</p>
        <div class="pt20 pb20">
          <router-link to="/pro/myFollow" class="my-watch">查看我关注的内容>></router-link>
        </div>

        <p> <img src="../../../img/tv-icon.png" alt="" class="mr10" width="20px" height="20px">直播间</p>
        <div class="pt20 pb10">
          <a :href="this.$url.liveVideo+'/liveVideoSetting?account='+$user.loginAccount">
            我要直播>>
          </a>
          <a :href="this.$url.liveVideo+'?account='+$user.loginAccount">
            逛直播>>
          </a>
        </div>
    </Card>
  </div>
</template>
<script>
  export default {
    data () {
      return {

      }
    },
    methods: {

    }
  }
</script>
<style lang="scss">
.bg-white{
  .member-watch-list{
    color: #4A4A4A;
    p{
      font-weight: 700;
      font-family: PingFangSC-Semibold;
    }
    a{
      display: inline-block;
      font-family: PingFangSC-Regular;
        padding:5px 15px;
        font-size: 12px;
        color: #4A4A4A;
        &:hover{
          color:#00C587;
        }
    }
  }
}
</style>
