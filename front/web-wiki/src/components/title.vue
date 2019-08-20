<template>
  <div class="user-auth-title mt10 mb10">
    <div class="vui-flex vui-flex-middle pr5">
      <div class="vui-flex-item">{{title}} <Icon type="edit" @click.native="handleEdit" :size="16" class="ml5" color="#9B9B9B"></Icon></div>
      <!-- <Icon :type="icon" :size="16" color="#9B9B9B" @click.native="handleFold"></Icon> -->
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
    icon: {
      type: String,
      default: 'chevron-up'
    }
  },
  data: () => ({
  }),
  methods: {
    handleEdit () {
      this.$emit('on-click')
    },
    handleFold () {
    }
  }
}
</script>
<style lang="scss" scoped>
.user-auth-title{
  font-size: 22px;
  padding-left: 24px;
  position: relative;
  &:after,&:before{
    content: '';
    position: absolute;
  }
  &:after{
    top: 5px;
    bottom: 5px;
    left: 0;
    width: 12px;
    background: #56B07D;
  }
  &:before{
    bottom: -5px;
    left: 24px;
    right: 0;
    height: 1px;
    background: #D8D8D8;
  }
  .ivu-icon{
    cursor: pointer;
  }
}
</style>
