<template>
  <div>
    <div v-if="model.name !== 'root'">
      <div class="border up" :class="{'active': isDragEnterUp}"
        @drop="dropUp"
        @dragenter="dragEnterUp"
        @dragover='dragOverUp'
        @dragleave="dragLeaveUp"></div>

      <div class='tree-node' :id='model.id' :class="{'active': isDragEnterNode}"
        draggable="true"
        @click=""
        @dragstart='dragStart'
        @dragover='dragOver'
        @dragenter='dragEnter'
        @dragleave='dragLeave'
        @drop='drop'
        @dragend='dragEnd'
         >
         <Col span="8" class="tree">
        <span class="caret icon is-small" v-if="model.children && model.children.length > 0">
          <i class="vue-tree-icon ivu-icon" :class="caretClass" @click.prevent.stop="toggle"></i>
        </span>

        <span v-if="model.isLeaf">
          <slot name="leafNodeIcon">
            <i class="vue-tree-icon item-icon icon-file"></i>
          </slot>
        </span>
        <span v-else>
          <slot name="treeNodeIcon">
            <i class="vue-tree-icon item-icon icon-folder"></i>
          </slot>
        </span>

        <div class="node-content tree1" v-if="!editable" >
          {{model.name}}
        </div>
        <input v-else class="vue-tree-input" type="text" ref="nodeInput" :value="model.name" @input="updateName" @blur="setUnEditable">
	 </Col>

        <Col span="6" >
        	 <Input type="text" ref="nodeInput" v-model="remark" @on-change="updateRemark" ></Input>
       </Col>


				<Col span="10" >
        <div class="operation">
           <Button  @click.stop.prevent="setEditable">重命名</Button>
           <Button  @click.stop.prevent="delNode" style="margin: 0 -6px;border-radius: 0">删除</Button>
           <Button  @click.stop.prevent="addChild(false)">增加下级</Button>
        </div>
        </Col>

      </div>

      <div v-if="model.children && model.children.length > 0 && expanded"
        class="border bottom"
        :class="{'active': isDragEnterBottom}"
        @drop="dropBottom"
        @dragenter="dragEnterBottom"
        @dragover='dragOverBottom'
        @dragleave="dragLeaveBottom"></div>
    </div>

    <div :class="{'tree-margin': model.name !== 'root'}" v-show="expanded" v-if="isFolder">
      <item v-for="model in model.children"
        :default-tree-node-name="defaultTreeNodeName"
        :default-leaf-node-name="defaultLeafNodeName"
        :model="model"
        :key='model.id'>
      </item>
    </div>


  </div>
</template>

<script>
  import { Tree, TreeNode } from './Tree.js'
  import $ from 'jquery'

  let fromComp = ''
  export default {
    data: function () {
      return {
        isHover: false,
        editable: false,
        isDragEnterUp: false,
        isDragEnterBottom: false,
        isDragEnterNode: false,
        expanded: true,
        remark:''
      }
    },

    props: {
      model: {
        type: Object
      },
      defaultLeafNodeName: {
        type: String,
        default: 'New leaf node'
      },
      defaultTreeNodeName: {
        type: String,
        default: 'New tree node'
      }

    },
    computed: {
      itemIconClass () {
        return this.model.isLeaf ? 'icon-file' : 'icon-folder'
      },

      caretClass () {
        return this.expanded ? 'ivu-icon-arrow-down-b' : 'ivu-icon-arrow-right-b'
      },

      isFolder() {
        return this.model.children &&
          this.model.children.length
      }
    },
    mounted () {
      const vm = this
      $(window).on('keyup', function (e) {
        // click enter
        if (e.keyCode === 13 && vm.editable) {
          vm.editable = false
        }
      })
    },
    beforeDestroy () {
      $(window).off('keyup')
    },
    methods: {
				check(){
					console.log("this.model----")
					console.log(this.model)
					console.log("this.reamrk----"+this.remark)
				},
      updateName (e) {
        this.model.changeName(e.target.value)
      },
       updateRemark (e) {
        this.model.changeRemark(e.target.value)
      },

      delNode () {
        const vm = this
        if (window.confirm('确定？')) {
          vm.model.remove()
        }
      },

      setEditable () {
        this.editable = true
        this.$nextTick(() => {
          $(this.$refs.nodeInput).trigger('focus')
        })

      },

      setUnEditable () {
        this.editable = false
      },

      toggle() {
        if (this.isFolder) {
          this.expanded = !this.expanded
        }
      },


      addChild(isLeaf) {
        const name = isLeaf ? this.defaultLeafNodeName : this.defaultTreeNodeName
        this.expanded = true
        var remark=this.remark
        console.log("this.reamrk----"+remark)
        var node = new TreeNode(name, isLeaf,remark)
        this.model.addChildren(node, true)

        console.log("this.model----")
					console.log(this.model)
      },

      dragStart(e) {
        fromComp = this

        e.dataTransfer.effectAllowed = 'move'
        return true
      },
      dragEnd(e) {
        fromComp = null
      },
      dragOver(e) {
        e.preventDefault()
        return true
      },
      dragEnter(e) {
        if (this.model.isLeaf) {
          return
        }
        this.isDragEnterNode = true
      },
      dragLeave(e) {
        this.isDragEnterNode = false
      },
      drop(e) {
        fromComp.model.moveInto(this.model)
        this.isDragEnterNode = false
      },

      dragEnterUp () {
        this.isDragEnterUp = true
      },
      dragOverUp (e) {
        e.preventDefault()
        return true
      },
      dragLeaveUp () {
        this.isDragEnterUp = false
      },
      dropUp () {
        fromComp.model.insertBefore(this.model)
        this.isDragEnterUp = false
      },

      dragEnterBottom () {
        this.isDragEnterBottom = true
      },
      dragOverBottom (e) {
        e.preventDefault()
        return true
      },
      dragLeaveBottom () {
        this.isDragEnterBottom = false
      },
      dropBottom () {
        fromComp.model.insertAfter(this.model)
        this.isDragEnterBottom = false
      }
    },
    beforeCreate () {
      this.$options.components.item = require('./VueTreeList.vue')
    }
  }
</script>

<style lang="less" rel="stylesheet/less" scoped>
  .vue-tree-icon {
    /* use !important to prevent issues with browser extensions that change fonts */
    speak: none;
    font-style: normal;
    font-weight: normal;
    font-variant: normal;
    text-transform: none;
    line-height: 1;

    /* Better Font Rendering =========== */
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    &.item-icon {
      margin-right: 4px;
      &:hover {
        color: inherit;
      }
    }
    &:hover {
      color: blue;
    }
  }


  .border {
    height: 5px;
    &.up {
      margin-top: -5px;
      background-color: transparent;
    }
    &.bottom {
      background-color: transparent;
    }
    &.active {
      border-bottom: 3px dashed blue;
      /*background-color: blue;*/
    }
  }

  .tree-node {
    display: flex;
    align-items: center;
    padding: 5px 0 5px 1rem;
    .input {
      border: none;
      max-width: 150px;
      border-bottom: 1px solid blue;
    }
    &:hover {
      background-color: #f0f0f0;
    }
    &.active {
      outline: 2px dashed pink;
    }
    .caret {
      margin-left: -1rem;
    }
    .operation {
      position: absolute;
      right: 10px;
      margin-top: -20px;
    }
  }


  .item {
    cursor: pointer;
  }
  .tree-margin {
    margin-left: 2em;
  }
</style>
