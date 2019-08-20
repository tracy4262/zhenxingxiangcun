<template>
  <div id="app" class="wrapper">
    <topNav/>
    <router-view :key="key" class="vui-container"></router-view>
    <BackTop></BackTop>
  </div>
</template>

<script>
import topNav from '~components/top-nav'
export default {
  name: 'app',
  components: {
    topNav
  },
  computed: {
    key () {
      return this.$route.name !== undefined ? this.$route.name + +new Date() : this.$route + +new Date()
    }
  }
}
</script>

<style>
  #vueg-background{
    display: none;
  }
  .wrapper{
    min-width: 1200px;
    overflow-x: auto;
  }
</style>
