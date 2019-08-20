<template>
  <div class="pt10 pb10">
    <div class="user-auth-title">
      {{title}}
    </div>
  </div>
</template>
<script>
export default {
  props: {
    title: {
      type: String,
      default: ''
    }
  },
  data: () => ({
  })
}
</script>
<style lang="scss">
.user-auth-title{
  padding-left: 10px;
  border-left: 6px solid #00c587;
  position: relative;
  font-weight: 700;
  &:before{
    content: '';
    position: absolute;
    bottom: -5px;
    left: 10px;
    right: 0;
    height: 1px;
    background: #eee;
  }
}
</style>
