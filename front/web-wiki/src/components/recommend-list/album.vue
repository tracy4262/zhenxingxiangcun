<template>
  <div class="pd15">
    <template v-if="data.length">
      <viewer
      :images="data"
      @inited="inited"
      class="viewer"
      ref="viewer">
      <div scope="scope">
        <img
        style="cursor:pointer;display:block"
        v-for="(item, index) in data"
        :key="index"
        :src="item"
        height="140"
        width="100%"
        v-show="index === 0"
        @click="show">
      </div>
      <p class="tc pd10" style="background:#efefef">{{name}} 图册 ({{data.length}})</p>
    </viewer>
    </template>
    <div class="mt20 tc" v-else>
      <img src="../../assets/imgs/no-result.png" height="60" alt="">
      <p class="t-grey h8">{{name}} 暂无图片</p>
    </div>
  </div>
</template>
<script>
import 'viewerjs/dist/viewer.css'
import Viewer from 'v-viewer/src/component.vue'
export default {
  props: {
    name: String,
    data: Array
  },
  components: {
    Viewer
  },
  data: () => ({
  }),
  methods: {
    inited (viewer) {
      this.$viewer = viewer
    },
    show () {
      this.$viewer.show()
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
