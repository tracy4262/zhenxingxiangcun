<template>
  <span>
    <ul class="vui-classify-wrap scroll" v-if="data && data.length" :style="style">
      <vui-filter-item
        v-for="(item, index) in data"
        :key="index"
        :data="item"
        @click.native.stop="handleClickItem(item)"
        @on-classify-click="onClassifyClick">
        </vui-filter-item>
    </ul>
    <vui-filter-list 
      v-if="sublist && sublist.length" 
      :data="sublist"
      class="vui-classify-wrap"
      @click.native.stop="handleClickItem(sublist)"
      @on-classify-click="onClassifyClick">
    </vui-filter-list>
  </span>
</template>

<script>
import vuiFilterItem from './item'
import { findComponentUpward, findComponentDownward } from '../../utils/assist'
export default {
  name: 'vui-filter-list',
  components: {
    vuiFilterItem
  },
  props: {
    data: Array,
  },
  data () {
    return {
      sublist: [],
      cols: 4
    }
  },
  watch: {
    data () {
      this.sublist = []
    }
  },
  created () {
    this.cols = findComponentUpward(this, 'vui-filter').cols
  },
  computed: {
    style () {
      return {
        'width': 100 / this.cols - .5 + '%'
      }
    }
  },
  methods: {
    handleClickItem (item) {
      this.handleTriggerItem(item)
    },
    handleTriggerItem (item) {
      if (item.loading !== undefined && !item.children.length) {
        const cascader = findComponentUpward(this, 'vui-filter')
        if (cascader && cascader.loadData) {
          cascader.loadData(item, () => {
            if (item.children.length) {
              this.handleTriggerItem(item)
            }
          })
          return
        }
      }

      if (item.children && item.children.length) {
          this.sublist = item.children
      } else {
          this.sublist = []
          this.$Message.error('暂无更多数据！')
      }
    },
    onClassifyClick (data){
      this.$emit('on-classify-click', data)
    }
  }
}
</script>

<style lang="scss">
ul.vui-classify-wrap{
  display: inline-block;
  vertical-align: top;
  height: 240px;
  border-right: 1px solid #ddd;
  overflow: auto;
}
</style>
