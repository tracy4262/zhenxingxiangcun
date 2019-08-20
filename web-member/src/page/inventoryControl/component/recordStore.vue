<template>
<div>
  <div>
    <Tabs v-model="tabView" @on-click="tabChange">
      <TabPane label="入库记录及单据查询" name="inStoreRecord"></TabPane>
      <TabPane label="出库记录及单据查询" name="outStoreRecord"></TabPane>
    </Tabs>
    <components :is="tabView"></components>
  </div>
</div>
</template>

<script>
import inStoreRecord from './recordStore/inStoreRecord'
import outStoreRecord from './recordStore/outStoreRecord'

export default {
  components: {
    inStoreRecord,
    outStoreRecord
  },
  data () {
    return {
      tabView: 'inStoreRecord'
    }
  },
  methods: {
    tabChange () {}
  }
}
</script>

<style lang="scss" scoped>

</style>
