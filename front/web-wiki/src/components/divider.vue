<template>
  <hr class="vui-divider" :style="dividerStyle" :class="dividerClass" :data-text="text" />
</template>

<script>
export default {
  name: 'vui-divider',
  props: {
    padding: Array,
    margin: Array,
    solid: Boolean,
    text: String
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

<style lang="scss">
.vui-divider {
  border: none;
  position: relative;
  border-top:1px dotted #F3F3F3;
  background: none !important;
  &-solid{
    border-top:1px solid #F3F3F3;
  }
  &:after{
    content: attr(data-text);
    background: #fcfcfc;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate3d(-50%, -50%, 0);
    padding: 10px 20px;
  }
}
</style>
