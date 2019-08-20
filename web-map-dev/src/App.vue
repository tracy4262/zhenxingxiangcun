
<style lang="less" scoped>
#app{
  width: 100%;
  height: 100%;
  position: relative;
}
.min-height{
  // min-height: calc(100vh - 125px);
  // height: calc(100vh - 125px);
  width: 100%;
  height: 100%;
  position: relative;
}
</style>
<template>
  <div id="app">
    <Affix>
      <top></top>
    </Affix>
    <div class="min-height">
      <router-view/>
    </div>
  </div>
</template>
<script>
import top from '@/components/top'
export default {
  components: {
    top
  },
  data () {
    return {

    }
  }
}
</script>
