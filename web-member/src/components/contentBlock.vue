<template>
  <div class="vui-content-block" :style="contentStyle" :class="contentClass">
    <slot></slot>
  </div>
</template>

<script>
export default {
  name: 'vui-content-block',
  props: {
    padding: Array,
    border: Boolean
  },
  computed: {
    contentStyle () {
      return {
        'padding': this.padding ? this.padding.join(' ') : ''
      }
    },
    contentClass () {
      return {
        'vui-content-block-border': this.border
      }
    }
  }
}
</script>

<style lang="scss">
.vui-content-block{
  padding: 8px 16px;
  &-border{
    border:1px solid #ddd;
  }
  /* p{
    margin-top: 1em;
    margin-bottom: 1em;
    &:first-child{
      margin-top: 0;
    }
    &:last-child{
      margin-bottom: 0;
    }
  } */
}
</style>
