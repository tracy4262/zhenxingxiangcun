<template>
  <FormItem :label="`${label}:`">
    <Tag
    :key="index"
    :color="item.checked === true ? 'green' : 'default'"
    v-for="(item,index) in data"
    @click.native="handleGetFilter(item)">{{item.name}}</Tag>
  </FormItem>
</template>
<script>
export default {
  props: {
    label: String,
    data: Array
  },
  data: () => ({
  }),
  methods: {
    handleGetFilter (item) {
      console.log(this.data)
      this.data.forEach(item => {
        item.checked = false
      })
      item.checked = !item.checked
      this.$emit('on-get-filter', item)
    }
  }
}
</script>
<style lang="scss" scoped>
.ivu-tag{
  height: 26px;
  line-height: 26px;
}
</style>
