<template>
  <div class="app-banner" :style="style">
    <h3 class="mb10 b">{{title}}</h3>
    <p style="font-size:12px">
      <slot></slot>
    </p>
  </div>
</template>
<script>
export default {
  props: {
    src: {
      type: String,
      default: ''
    },
    title: String
  },
  data: () => ({
  }),
  computed: {
    style () {
      return {
        'background-image': `url(${this.src})`
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.app-banner{
  padding: 30px 20px;
  color: #fff;
  margin: 20px 0;
  border-radius: 2px;
  background-size: 100%;
  background-repeat: no-repeat;
  background-position: center;
  min-height: 100px;
}
</style>
