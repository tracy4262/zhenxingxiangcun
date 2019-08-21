<template>
  <div style="max-height:280px; overflow-y:auto" class="scroll">
    <ul
    v-for="(d, i) in data"
    :key="i">
    <li>
      <div class="vui-flex vui-flex-middle pl5 pr5 item" :class="{active: d.checked}">
        <Icon type="md-arrow-dropright" :class="{active: d.expand}" class="vui-book-arrow" @click.native="handleToggle(d, i)"></Icon>
        <div class="vui-flex-item">
          <div class="vui-book-tree-title">
            <template v-if="d.edit">
              <Input
              :maxlength="15"
              v-model="d.title"
              size="small"
              :ref="`input${i}`"
              class="pd5"
              placeholder="请输入章节名称，最多15个字"
              @on-keydown.enter="handleSave(d)"
              @on-blur="handleSave(d)" />
            </template>
            <template v-else>
              <div class="vui-flex vui-flex-middle">
                <Icon type="ios-bookmarks-outline"></Icon>
                <p class="vui-flex-item pd5 ell"
                @dblclick="handleEdit(d, i)"
                @click="handleSelected(d, i)"
                >{{d.title}}</p>
                <div>
                  <span class="oper-btn-wrap">
                    <Icon type="md-add" size="14" @click.native.stop="handleAdd(d, i)"></Icon>
                  </span>
                  <Poptip
                    confirm
                    title="您确认删除吗？"
                    @on-ok="handleDel(i)">
                    <div class="oper-btn-wrap">
                      <Icon type="ios-trash" size="16" class="ml5"></Icon>
                    </div>
                  </Poptip>
                </div>
              </div>
            </template>
          </div>
        </div>
      </div>
      <transition name="fade">
        <vui-tree-node
        ref="node"
        :data="d.children"
        v-if="d.expand"
        :index="i"
        @on-del="handleDelChild(d.children, $event)"
        @on-select="handleSubSelect"
        ></vui-tree-node>
      </transition>
    </li>
  </ul>
  </div>
</template>
<script>
import vuiTreeNode from './node'
import {EditNode, CancelNode, FocusNode} from './treeMixins'
export default {
  props: {
    index: Number,
    data: {
      type: Array,
      default () {
        return []
      }
    },
    updated: {
      type: Boolean,
      default: false
    }
  },
  components: {
    vuiTreeNode
  },
  data () {
    return {
      state: {
        pIndex: 0,
        sIndex: 0,
        edit: false,
        data: {}
      }
    }
  },
  methods: {
    // 编辑
    handleEdit (d, i) {
      EditNode(this, d, i)
    },
    // 选中父节点,
    handleSelected (d, i) {
      CancelNode(this.data)
      d.checked = true
      this.$emit('on-select', {
        pIndex: this.index,
        sIndex: 0,
        edit: false,
        data: d
      })
    },
    // 选择子节点
    handleSubSelect (data) {
      this.$emit('on-select', {
        pIndex: data.pIndex,
        sIndex: data.sIndex,
        edit: true,
        data: data.node
      })
    },
    // 保存
    handleSave (d) {
      d.edit = false
    },
    // 新增
    handleAdd (d, i) {
      d.expand = true
      CancelNode(this.data)
      this.$nextTick(() => {
        var data = {
          title: `小节${d.children.length + 1}`,
          edit: true,
          checked: true,
          content: '',
          file: []
        }
        d.children.push(data)
        this.$emit('on-select', {
          pIndex: this.index,
          sIndex: d.children.length - 1,
          edit: true,
          data: data
        })
      })
    },
    // 删除
    handleDel (i) {
      this.data.splice(i, 1)
    },
    handleDelChild (d, i) {
      d.splice(i, 1)
    },
    // 折叠
    handleToggle (d, i) {
      d.expand = !d.expand
    }
  },
  updated () {
    if (this.updated) {
      FocusNode(this)
    }
  }
}
</script>
<style lang="scss">
.vui-book-arrow {
  padding: 5px;
  cursor: pointer;
  transition: all .3s;
  &.active{
    transform: rotate(90deg);
    margin-right: 5px;
  }
}
.scroll{
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
