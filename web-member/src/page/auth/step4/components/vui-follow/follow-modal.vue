<template>
  <div>
    <div class="follow-filter">
      <Row type="flex" v-for="(item, pindex) in data" :key="pindex">
        <!-- 一级数据 -->
        <Col span="2" class="left">
          <span class="item" :class="{on: item.checked}" >{{item.name}}</span>
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
                  <span class="item" :class="{on: node.checked}" @click="handleResultClick(node, nindex)">{{node.name}}</span>
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
import {Mixins} from './mixins.js'
export default {
  props: {
    data: Array,
    defaultSel: Array
  },
  mixins: [Mixins],
  data: () => ({
  }),
  methods: {

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
