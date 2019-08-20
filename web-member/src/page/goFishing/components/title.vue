<template>
  <div class="user-service-title mt10 mb10">
    <div class="vui-flex">
      <div style="color:#4a4a4a;">{{name}}</div>
      <div class="vui-flex-item subtitle">
        <span class="t-grey ml20" v-if="subTitle">{{subTitle}}</span>
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
    subTitle: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      name: '',
    }
  },
  mounted() {
    this.name = this.title
  }
}
</script>
<style lang="scss">
.user-service-title{
  padding-left: 10px;
  border-left: 6px solid #00c587;
  position: relative;
  font-weight: 700;
  // &:before{
  //   content: '';
  //   position: absolute;
  //   bottom: -5px;
  //   left: 10px;
  //   right: 0;
  //   height: 1px;
  //   background: #eee;
  // }
  .subtitle{
    font-size: 12px;
    cursor: pointer;
  }
}
</style>
