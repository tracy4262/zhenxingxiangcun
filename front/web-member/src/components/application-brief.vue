<template>
  <div>
    <p class="pt20 pb40" style="font-size: 14px;font-family: PingFangSC-Regular;">
      {{str}}
    </p>
  </div>
</template>

<script>
  export default {
    name: '',
    props: {
      appId: String
    },
    data() {
      return {
        str: '',
        id: ''
      }
    },
    created() {
      this.id = this.$route.query.appId ? this.$route.query.appId : this.appId
      if (this.id) {
        this.init()
      }
    },
    methods: {
      init () {
        this.$api.post('/member/applicationCentrality/findAppAbstract', {appId: this.id}).then(res => {
          if (res.code === 200 && res.data && res.data.length) {
            this.str = res.data[0].abstract
          }
        })
      }
    }
  }
</script>

<style lang="less" scoped>

</style>
