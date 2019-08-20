<template>
  <div :class="{pd20: type === 2}">
    <scrollactive ref="scrollactive"
      :offset="52"
      :always-track="false"
      :duration="600"
      :click-to-scroll="true"
      bezier-easing-value=".5,0,.35,1"
      @itemchanged="handleItemChange">
      <div
      :class="{'vui-affix-vertical-tabs': type === 2, 'wiki-detail-catalog-right': type === 1}"
      :style="height"
      >
        <a
        v-for="(item, index) in data"
        :key="index" :href="`#${item.propertyid}`"
        class="scrollactive-item block">
          <template v-if="type === 1">{{index + 1}}.</template>
          {{item.catalog_name}}
        </a>
      </div>
    </scrollactive>
  </div>
</template>
<script>
export default {
  props: {
    data: Array,
    type: {
      type: Number,
      default: 2
    },
    height: Object
  },
  data: () => ({
    elements: []
  }),
  created () {
  },
  methods: {
    // 滚动改变
    handleItemChange (event, currentItem, lastActiveItem) {
    },
    // 点击
    handleItemClick (index) {
    }
  },
  watch: {
  },
  mounted () {
  },
  updated () {
    this.elements = this.$el.querySelectorAll('.vui-affix-vertical-tabs .scrollactive-item')
    this.elements.forEach((item, index) => {
      this.elements[index].index = index
    })
  }
}
</script>
<style lang="scss" scoped>
.scrollactive-nav{
  position: relative;
}
.vui-affix-vertical-tabs {
  padding-top: 15px;
  &:before{
    content: '';
    position: absolute;
    top: 26px;
    right: 0;
    bottom: 10px;
    left: 10px;
    width: 1px;
    background: #D8D8D8;
  }
  a{
    position: relative;
    padding: 2px 25px;
    color: #333;
    &.is-active{
      color: #3DBD7D;
      &:after{
        border-color: #3DBD7D;
      }
    }
    &:after{
      content: '';
      position: absolute;
      top: 7px;
      left: 6px;
      background: #fff;
      border: 2px solid #D8D8D8;
      border-radius: 100px;
      width: 10px;
      height: 10px;
    }
  }
  .block{
    display: block;
  }
}
</style>
<style lang="scss" scoped>
.wiki-detail-catalog-right {
  display: flex;
  flex-flow: column wrap;
  padding: 10px 0 10px 10px;
  a {
    display: block;
    margin-right: -1px;
    line-height: 28px;
    font-size: 14px;
    padding-left: 10px;
    border-right: 1px solid #f2f2f2;
    &:hover {
      color:#56b07d;
    }
  }
}
</style>
