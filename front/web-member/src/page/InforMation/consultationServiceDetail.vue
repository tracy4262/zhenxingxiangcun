<template>
  <div>
      <top :address="false" active="1"  ref="top"/>
      <head-nav :active="5"></head-nav>
      <detail :links="links" isIndex></detail>
      <foot></foot>
  </div>
</template>

<script>
import detail from '~page/service/consultationService/detail'
import top from '../../top'
import headNav from '~page/51index/components/nav'
import foot from '../../foot'
export default {
  components: {
    detail,
    top,
    headNav,
    foot
  },
  data () {
    return {
      index: 8,
      links:['/51index/serviceList/all','/51index/serviceList']
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
