<template>
  <div class="relation-manage-title mt10 mb10">
    <div class="vui-flex">
      <div class="name">{{title}}</div>
      <div class="vui-flex-item">
      </div>
      <slot></slot>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    title: {
      type: String,
      default: ''
    },
  },
  data () {
    return {
    }
  }
}
</script>
<style lang="scss">
.relation-manage-title{
  .name{
    color:#9B9B9B;
    padding-left: 10px;
    border-left: 6px solid #00c587;
    position: relative;
    font-weight: 700;
    height: 25px;
    line-height: 25px;
    margin-top: 4px;
  }
}
</style>
