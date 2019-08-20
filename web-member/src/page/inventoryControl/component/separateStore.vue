<template>
<div>
  <Tabs v-model="tabView" @on-click="tabChange">
      <TabPane label="拆分产品" name="openProduct"></TabPane>
      <TabPane label="合并产品" name="mergeProduct"></TabPane>
    </Tabs>
    <components :is="tabView" @close="$emit('close')" ref="separateStore"></components>
</div>
</template>

<script>
import openProduct from './separateStore/openProduct'
import mergeProduct from './separateStore/mergeProduct'
export default {
  components: {
    openProduct,
    mergeProduct
  },
  data () {
    return {
      tabView: 'openProduct',
    }
  },
  methods: {
    tabChange () {},
    saveStore () {},
    hasSelected (e) {}
  }
}
</script>

<style lang="scss" scoped>

</style>
