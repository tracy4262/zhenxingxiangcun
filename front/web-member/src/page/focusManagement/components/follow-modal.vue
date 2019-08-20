<template>
  <div>
    <div class="follow-filter">
      <Row type="flex" v-for="(item, pindex) in data" :key="pindex">
        <!-- 一级数据 -->
        <Col span="2" class="left">
          <span class="item" :class="{on: item.checked}">{{item.name}}</span>
        </Col>
        <Col span="22" class="right" :style="{'border-bottom': '1px solid #f0f0f0'}">
          <Row v-if="child.children" v-for="(child, cindex) in item.children" :key="cindex" style="border-bottom: 1px solid #f0f0f0">
            <!-- 二级数据 -->
            <Col span="2">
              <span class="item" :class="{on: child.checked}" >{{child.name}} <Icon v-if="child.children" type="ios-arrow-forward"></Icon></span>
            </Col>
            <Col span="22">
              <!-- 三级数据 -->
              <Row v-if="child.children">
                <Col span="2" v-for="(node, nindex) in child.children" :key="nindex">
                  <span class="item" :class="{on: node.checked}" @click="handleResultClicks(node, nindex, child)">{{node.name}}</span>
                </Col>
              </Row>
            </Col>
          </Row>
          <Col v-else span="3">
            <p class="item ell" :class="{on: child.checked}" @click="handleResultClick(child, cindex)">{{child.name}}</p>
          </Col>
        </Col>
      </Row>
    </div>
    <slot></slot>

  </div>
</template>
<script>
export default {
  props: {
    data: Array,
    defaultSel: Array
  },
  data: () => ({
  }),
  methods: {
    // 选中结果
    handleResultClick (item, index) {
      item.checked = !item.checked
      if (item.checked) {
        this.defaultSel.push({
          label: item.name,
          value: item.id,
          parentId: item.parent_id ? item.parent_id : 0
        })
      } else {
        this.filterSel(this.defaultSel, item.name)
      }
      this.$emit('on-get-data', this.defaultSel)
    },
    // 第三级 选中结果
    handleResultClicks (item, index, pItem) {
      item.checked = !item.checked
      if (item.checked) {
        this.defaultSel.push({
          label: item.name,
          value: item.id,
          parentId: pItem.parent_id ? pItem.parent_id : 0
        })
      } else {
        this.filterSel(this.defaultSel, item.name)
      }
      this.$emit('on-get-data', this.defaultSel)
    },
     // 过滤结果
    filterSel (data, name) {
      data.forEach((item, index) => {
        if (item.name === name) {
          data.splice(index, 1)
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.follow-filter{
  border: 1px solid #E8E8E8;
  border-bottom: none;
  .left{
    font-weight: 700;
    background: #f6f6f6;
    font-size: 14px;
    padding: 6px 10px;
    border-bottom: 1px solid #f0f0f0;
  }
  .right{
    padding: 8px;
    background: #fff;
  }
  .fold{
    position: absolute;
    right: 6px;
    top: 6px;
  }
  .item{
    cursor: pointer;
    padding: 4px 5px;
    font-size: 12px;
    display: block;
    &.on{
      color: #4da473;
    }
  }
}
.scroll{
  overflow: auto;
  &::-webkit-scrollbar {
    width: 8px;
    height: 8px;
  }
  &::-webkit-scrollbar-thumb {
    border-radius: 10px;
    background-color: rgba(51,51,51,.15);
  }
}
</style>
