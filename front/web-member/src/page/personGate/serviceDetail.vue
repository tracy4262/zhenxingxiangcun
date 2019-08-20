<template>
  <div>
      <detail :links="links"></detail>
  </div>
</template>

<script>
import detail from './components/serviceDetail'
import { navStatus } from '~page/companyGate/mixins/commonMixins'
export default {
  mixins: [navStatus],
  components: {
    detail
  },
  data () {
    return {
      index: 8,
      links:['/personGate/service/all','/personGate/service']
    }
  },
  created() {
    // 0 垂钓 1采摘 2景区 3餐饮 4住宿
  },
  methods: {
  }
}
</script>

<style lang="scss" scoped>

</style>
