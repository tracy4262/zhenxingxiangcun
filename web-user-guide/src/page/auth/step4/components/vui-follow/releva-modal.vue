<template>
  <div>
    <div class="releva-filter">
      <Row type="flex" style="border-bottom: 1px solid #f0f0f0;" v-for="(item, index) in filter" :key="item.name">
        <Col span="3" class="left">
          <span class="item" :class="{on: item.checked}" @click="handleFilterClick(item)">{{item.name}}</span>
        </Col>
        <Col span="21" class="right">
          <Row>
            <Col span="4" v-for="child in item.children" :key="child.name">
              <span class="item" :class="{on: child.checked}" @click="handleFilterClick(child)">{{child.name}}</span>
            </Col>
          </Row>
        </Col>
      </Row>
    </div>

    <Row class="releva-filter pd15 mt20 mb10">
      <template v-if="data.length">
        <Col span="3" v-for="(item, index) in data" :key="index"><span class="item" :class="{on: item.checked}" @click="handleResultClick(item)">{{item.name}}</span></Col>
      </template>
      <template v-else><div class="tc">暂无数据</div></template>
    </Row>

    <slot></slot>

  </div>
</template>
<script>
export default {
  props: {
    data: Array,
    defaultSel: Array,
    filter: Array,
    index: {
      type: Number,
      default: 0
    }
  },
  data: () => ({
    speciesFilterId: [],
    productFilterId: [],
    serviceFilterId: []
  }),
  methods: {
    // 选中结果
    handleResultClick (item) {
      item.checked = !item.checked
      if (item.checked) {
        this.defaultSel.push({
          name: item.name,
          id: item.id
        })
      } else {
        this.filterSel(item.name)
      }
      this.$emit('on-get-data', this.defaultSel)
    },
    filterSel (name) {
      this.defaultSel.forEach((item, index) => {
        if (item.name === name) {
          this.defaultSel.splice(index, 1)
        }
      })
    },
    // 动植物筛选点击
    handleFilterClick (item) {
      item.checked = !item.checked
      if (this.index === 0) {
        this.filterId(item, this.speciesFilterId)
      } else if (this.index === 1) {
        this.filterId(item, this.productFilterId)
      } else {
        this.filterId(item, this.serviceFilterId)
      }
    },
    filterId (item, arr) {
      if (item.checked) {
        arr.push(item.classId)
      } else {
        arr.forEach((child, index) => {
          if (child === item.classId) {
            arr.splice(index, 1)
          }
        })
      }
      this.$emit('on-get-filter', arr)
    }
  }
}
</script>
<style lang="scss" scoped>
.letter{
  .item{
    cursor: pointer;
  }
  .on{
    color: #4da473;
  }
}
.releva-filter{
  border: 1px solid #E8E8E8;
  .left{
    font-weight: 700;
    background: #f6f6f6;
    font-size: 14px;
    padding: 20px 10px;
    text-align: center;
    border-bottom: 1px solid #f0f0f0;
  }
  .right{
    background: #fff;
  }
  .item{
    cursor: pointer;
    padding: 4px 8px;
    font-size: 12px;
    display: block;
    &.on{
      color: #4da473;
    }
  }
}
</style>
