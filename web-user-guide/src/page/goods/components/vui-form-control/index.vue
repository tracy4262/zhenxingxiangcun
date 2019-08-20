<template>
  <div>
    <view-panel
    :data="viewData"
    edit
    @on-add="handleAddBtn"></view-panel>
    <!-- 添加面板 -->
    <add-panel ref="addPanel" @on-save="handleControlBtn"></add-panel>
  </div>
</template>
<script>
import addPanel from './add-panel'
import viewPanel from './view-panel'
export default {
  props: {
    edit: {
      type: Boolean,
      default: true
    }
  },
  components: {
    addPanel,
    viewPanel
  },
  data: () => ({
    viewData: []
  }),
  methods: {
    handleAddBtn () {
      this.$refs.addPanel.showAddPanel = true
    },
    // 添加组件
    handleControlBtn (data) {
      this.viewData.push(data)
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
