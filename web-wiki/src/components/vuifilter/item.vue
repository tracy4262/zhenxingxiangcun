<template>
  <li class="vui-flex vui-flex-middle" :class="{'on':data.checked}">
    <span @click.parent.stop="handleClick(data)">
      <Icon :type="data.checked ? 'android-checkbox' : 'android-checkbox-outline-blank'"></Icon>
    </span>
    <span class="ml5 mr5">{{data.label}}</span>
    <i v-if="showArrow" class="ivu-icon ivu-icon-ios-arrow-right"></i>
    <i v-if="showLoading" class="ivu-icon ivu-icon-load-c ivu-load-loop"></i>
  </li>
</template>

<script>
export default {
  name: 'vui-filter-item',
  props: {
    data: Object
  },
  data () {
    return {
    }
  },
  created () {
  },
  methods: {
    handleClick (data) {
      this.$emit('on-classify-click', data)
    }
  },
  computed: {
    showArrow () {
      return (this.data.children && this.data.children.length) || ('loading' in this.data && !this.data.loading)
    },
    showLoading () {
      return 'loading' in this.data && this.data.loading
    }
  }
}
</script>

<style lang="scss">
.vui-classify-wrap{
  li{
    cursor: pointer;
    font-size: 14px;
    overflow-x:hidden;
    overflow-y:auto;
    padding: 6px;
  }
  .on{
    color:#FF7921;
  }
  .ivu-icon{
    font-size: 18px;
    vertical-align: middle;
  }
}
</style>
