<template>
  <div class="follow-tag">
    <span v-for="(item, index) in data" :key="index" class="item" :class="{on: item.checked}" @click="handleTagClick(item, index)">{{item.name}}</span>
  </div>
</template>
<script>
export default {
  props: {
    data: Array
  },
  data: () => ({
  }),
  methods: {
    // 选中标签
    handleTagClick (item, index) {
      this.data.forEach(item => {
        item.checked = false
      })
      item.checked = true
      this.$emit('on-click', index)
    }
  }
}
</script>

<style lang="scss" scoped>
.follow-tag{
  .item{
    cursor: pointer;
    display: inline-block;
    vertical-align: middle;
    padding: 4px 10px;
    font-size: 14px;
    font-weight: 700;
  }
  .on{color: #4da473}
}
</style>
