<template>
   <div>
      <top :address="false" />
      <div class="main" :style="{'min-height': height}">
        <app-banner
          src="../../../static/img/app-banner-follow.png"
          title="关注管理">
        </app-banner>
        <vui-follow ref="follow" @save="save"></vui-follow>
      </div>
      <foot></foot>
   </div>
</template>
<script>
import top from '../../top'
import foot from '../../foot'
import appBanner from '~components/app-banner'
import vuiFollow from '../userAuth/components/vui-follow'

export default{
  components: {
    top,
    foot,
    appBanner,
    vuiFollow
  },
  data () {
    return {
        height: 0,
        loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
    }
  },
  created () {
    this.initData()
  },
  methods: {
    save (listData) {

    },
    initData () {
      this.$api.post('/member/indivi/findIndividInfo',{
        account: this.loginUser.loginAccount,
        follow_type: 'knowledge'
      }).then(response => {
        console.log('response999', response)
        if (response.code === 200) {
          // 回显关注数据
          if (response.data.FollowData[0].follow.length === 0 &&
            response.data.FollowData[0].releva[0].length === 0 &&
            response.data.FollowData[0].releva[1].length === 0 &&
            response.data.FollowData[0].releva[2].length === 0) {
            this.$refs.follow.listData = []
          } else {
            this.$refs.follow.listData = response.data.FollowData
          }
        }
      }).catch(error => {
        console.log(error)
      })
    }
  },
  mounted () {
    this.height = `${window.innerHeight-226}px`
  },
}
</script>
<style>

</style>
