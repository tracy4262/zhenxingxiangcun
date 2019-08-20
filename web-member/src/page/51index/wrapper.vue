<template>
  <div class="wrapper">
    <top :address="false" active="1" />
    <!-- 导航 -->
    <head-nav></head-nav>
    <section class="layouts">
      <router-view></router-view>
    </section>
    <foot></foot>
  </div>
</template>
<script>
import top from '../../top'
import headNav from './components/nav'
import foot from '../../foot'
export default {
  components: {
    top,
    headNav,
    foot
  },
  data() {
    return {}
  },
  created() {
  },
  methods: {
  }
}
</script>
<style lang="scss">
</style>
