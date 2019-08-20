<template>
  <div>
     <Modal
        v-model="isShow"
        :mask-closable="false"
        width="1000px"
        class="focus-management-layouts relation-manage-modal"
        title="关注好友">
        <div>
          <div class="vui-tabs tc">
            <span @click="active = index" v-for="(item, index) in data" class="vui-tabs-span" :class="active === index ? 'tabs-active' : ''">{{item.label}}</span>
          </div>
          <div  v-for="(item, index) in data">
            <memberAddList :type="item.type" @on-init="onInit" v-show="active === index" :ref="`add${active}`"></memberAddList>
          </div>
        </div>
        <div slot="footer" class="tc">
          <!-- <Button @click="onCancel">取消</Button>
          <Button @click="onSave" type="primary">确定</Button> -->
        </div>
    </Modal>
  </div>
</template>
<script>
import memberAddList from './memberAddList'
export default {
  components: {
    memberAddList
  },
  data () {
    return {
      isShow: false,
      data: [
        {type: '1', label: '全部'},
        {type: '2', label: '个人'},
        {type: '3', label: '企业'},
        {type: '4', label: '机关'},
        {type: '5', label: '专家'},
      ],
      activeTabs: '1',
      active: 0,
    }
  },
  created() {
    this.getInit()
  },
  methods: {
    onInit () {
      this.$emit('on-init')
    },
    // 取消
    onCancel () {
      this.isShow = false
    },
    // 确定
    onSave () {
      console.log('followDataSel', this.followDataSel)
      if (this.followDataSel.length) {
        this.$emit('on-save', this.followDataSel)
      } else {
        this.$Message.warning('请选择！')
      }
    },
    init () {
      this.isShow = true
    },
    getInit () {
    }
  },
  
}
</script>
<style>
.focus-member-add.ivu-tabs{
  overflow: inherit;
}

</style>

