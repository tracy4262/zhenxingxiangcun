<template>
  <div>
    <top :address="false"></top>
    <div :style="{'background': '#F9F9F9', 'min-height': height}">
      <div class="layouts">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>
<script>
import top from '~src/top'
import Title from '../../userAuth/components/title'
import VuiAffixTabs from './vui-affix-tabs'
import detail from './detail'
export default {
  props: {
    data: Array,
    type: {
      type: Number,
      default: 0
    }
  },
  components: {
    top,
    VuiAffixTabs,
    Title,
    detail
  },
  data: () => ({
    current: 0,
    height: 0
  }),
  created () {
  },
  methods: {
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
