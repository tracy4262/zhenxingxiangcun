<template>
  <div class="new-gate-title mt10 mb10">
    <div class="vui-flex">
      <div style="color:#4a4a4a;">{{title}}</div>
       <div class="vui-flex-item tr">
        <slot></slot>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    title: String
  },
  data () {
    return {
    }
  },
  methods: {
  },
}
</script>
<style lang="scss">
.new-gate-title{
  padding-left: 10px;
  border-left: 6px solid #00c981;
  position: relative;
  font-weight: 700;
  font-size: 16px;
  // &:before{
  //   content: '';
  //   position: absolute;
  //   bottom: -5px;
  //   left: 10px;
  //   right: 0;
  //   height: 1px;
  //   background: #eee;
  // }
  // .subtitle{
  //   font-size: 12px;
  //   cursor: pointer;
  // }
  a{
    font-weight: 400;
    color:#9B9B9B;
    font-size: 14px;
    &:hover{
      color: #00c587;
    }
  }
}
</style>
