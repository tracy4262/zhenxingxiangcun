<template>
  <div>
    <top :address="false"></top>
    <div :style="{'background': '#F9F9F9', 'min-height': height}">
      <div class="layouts pt20">
        <Card class="mb10">
          <Steps :current="current" :class="type > 0 ? 'cursor' : ''">
            <Step
            v-for="(item, index) in data"
            :key="index"
            :title="item"
            @click.native="handleStepClick(index + 1)"
            ></Step>
          </Steps>
        </Card>
        <div ref="authWrap">
          <router-view></router-view>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import top from '~src/top'
export default {
  props: {
    data: Array,
    type: {
      type: Number,
      default: 0
    }
  },
  components: {
    top
  },
  data: () => ({
    current: 0,
    height: 0
  }),
  created () {
    let path = this.$route.path
    this.current = parseInt(path.slice(path.length - 1, path.length)) - 1
  },
  watch: {
    '$route' (to, path) {
      this.current = parseInt(to.path.slice(to.path.length - 1, to.path.length)) - 1
    }
  },
  methods: {
    // 跳转
    handleStepClick (index) {
      console.log('type', this.type)
      if (this.type > 0) {
        if (this.type === 1) this.$router.push(`/auth/comAuth/step${index}`)
        if (this.type === 3) this.$router.push(`/auth/personAuth/step${index}`)
        if (this.type === 4) this.$router.push(`/auth/ruralAuth/step${index}`)
        if (this.type === 5) this.$router.push(`/auth/govtAuth/step${index}`)
        this.current = index - 1
      }
    }
  },
  mounted () {
    this.height = `${window.innerHeight}px`
  }
}
</script>
<style lang="scss" scoped>
.layouts{
  width: 1200px;
  margin: 0 auto;
}
.cursor{
  .ivu-steps-item{
    cursor:pointer;
  }
}
</style>
<style lang="scss">
  #app{
    .ivu-form-item-label:before{
      margin: 0 2px 0 -10px;
    }
  }
  .v-transfer-dom{
    .ivu-form-item-label:before{
      margin: 0 2px 0 -10px;
    }
  }
</style>
