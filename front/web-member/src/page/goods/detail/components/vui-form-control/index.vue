<template>
  <div>
    <view-panel
    :data="viewData"
    ></view-panel>
  </div>
</template>
<script>
import viewPanel from './view-panel'
export default {
  components: {
    viewPanel
  },
  data: () => ({
    viewData: []
  }),
  methods: {
  }
}
</script>
<style lang="scss" scoped>
</style>
