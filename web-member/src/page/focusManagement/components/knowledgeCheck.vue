<template>
  <div>
     <Modal
        v-model="isShow"
        :mask-closable="false"
        width="1100px"
        :title="title">
        <followModal ref="follow" :data="followData" :defaultSel="followDataSel" @on-get-data="handleGetFollowData">
        </followModal>
        <div slot="footer" class="tc">
          <Button @click="onCancel">取消</Button>
          <Button @click="onSave" type="primary">确定</Button>
        </div>
    </Modal>
  </div>
</template>
<script>
import followModal from './follow-modal'
export default {
  props: {
    type: {
      type: String,
      default: 'knowledge'
    },
    title: {
      type: String,
      default: '关注知识'
    }
  },
  components: {
    followModal
  },
  data () {
    return {
      isShow: false,
      followData: [],
      followDataSel: []
    }
  },
  created() {
    this.getInit()
  },
  methods: {
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
      // 取数据
      this.$api.post('/member/followManage/findSysFollowDictInfo', {
        follow_type: this.type
      }).then(res => {
        // this.followData = res.data.FollowListData
        this.followData = res.data
        
      })
    },
    handleGetFollowData (data) {
      this.followDataSel = data
      console.log(data)
      // this.$emit('on-get-data', this.knowledgeSel, this.infoSel, this.policySel)
    }
  },
  
}
</script>
<style>


</style>

