<template>
  <div>
    <template v-if="data.length !== 0">
      <ul class="item" v-for="(d, i) in data" :key="i">
        <li>
          <div class="vui-flex vui-flex-middle item" :class="{active: d.checked}" style="padding: 0 5px 0 15px">
            <div class="vui-flex-item">
              <div class="vui-book-tree-title">
                <template v-if="d.edit">
                  <Input
                  :maxlength="15"
                  v-model="d.title"
                  size="small"
                  :ref="`input${i}`"
                  class="pd5"
                  placeholder="请输入小节名称，最多15个字"
                  @on-change="handleChangeTitle(d, i)"
                  @on-keydown.enter="handleSave(d, i)"
                  @on-blur="handleSave(d, i)" />
                </template>
                <template v-else>
                  <div class="vui-flex vui-flex-middle">
                    <Icon type="ios-paper-outline" class="pl15"></Icon>
                    <p class="vui-flex-item ell pd5"
                    @dblclick="handleEdit(d, i)"
                    @click.stop="handleSelected(d, i)">{{d.title}}</p>
                    <Poptip
                      confirm
                      title="您确认删除吗？"
                      @on-ok="handleDel(i)">
                      <div class="oper-btn-wrap">
                        <Icon type="trash-a" size="16" class="ml5"></Icon>
                      </div>
                    </Poptip>
                  </div>
                </template>
              </div>
            </div>
          </div>
        </li>
      </ul>
    </template>
  </div>
</template>
<script>
import {EditNode, CancelNode, FocusNode} from './treeMixins'
export default {
  props: {
    index: Number,
    data: {
      type: Array,
      default () {
        return []
      }
    }
  },
  data () {
    return {
    }
  },
  methods: {
    handleEdit (d, i) {
      EditNode(this, d, i)
    },
    // 保存
    handleSave (d, i) {
      this.$nextTick(() => {
        d.edit = false
      })
      this.$emit('on-select', {node: d, pIndex: this.index, sIndex: i})
    },
    handleChangeTitle (d, i) {
      this.$emit('on-select', {node: d, pIndex: this.index, sIndex: i})
    },
    // 选中节点,
    handleSelected (d, i) {
      CancelNode(this.$parent.data)
      d.checked = true
      this.$emit('on-select', {node: d, pIndex: this.index, sIndex: i})
    },
    // 删除
    handleDel (i) {
      this.$emit('on-del', i)
    }
  },
  updated () {
    FocusNode(this)
  }
}
</script>
<style lang="scss">
</style>
