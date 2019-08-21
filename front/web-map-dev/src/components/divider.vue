<template>
  <hr class="vui-divider" :style="dividerStyle" :class="dividerClass" />
</template>

<script>
export default {
  name: 'vui-divider',
  props: {
    padding: Array,
    margin: Array,
    solid: Boolean
  },
  computed: {
    dividerStyle () {
      return {
        'padding': this.padding ? this.padding.join(' ') : '',
        'margin': this.margin ? this.margin.join(' ') : ''
      }
    },
    dividerClass () {
      return {
        'vui-divider-title': this.title,
        'vui-divider-solid': this.solid
      }
    }
  }
}
</script>

<style lang="less">
.vui-divider {
  border: none;
  position: relative;
  border-top:1px dotted #ddd;
  background: none !important;
  &-solid{
    border-top:1px solid #ddd;
  }
}
</style>
