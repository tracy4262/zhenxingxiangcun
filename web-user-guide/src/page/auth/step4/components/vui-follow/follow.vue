<template>
  <div>
    <vui-tabs class="mt20 mb10" :data="tabs" @on-click="handleTabClick"></vui-tabs>
    <followModal ref="follow" :data="followData" :defaultSel="followDataSel" @on-get-data="handleGetFollowData">

      <Row type="flex" class="mt10 mb10" v-if="index === 0 &&  knowledgeSel.length">
        <Col span="2" class="pd5">已关注知识：</Col>
        <Col span="22">
          <Tag v-for="(item, index) in knowledgeSel" :key="index">{{item.name}}</Tag>
        </Col>
      </Row>
      <Row type="flex" class="mt10 mb10" v-if="index === 1 &&  infoSel.length">
        <Col span="2" class="pd5">已关注资讯：</Col>
        <Col span="22">
          <Tag v-for="(item, index) in infoSel" :key="index">{{item.name}}</Tag>
        </Col>
      </Row>
      <Row type="flex" class="mt10 mb10" v-if="index === 2 &&  followDataSel.length">
        <Col span="2" class="pd5">
          <!-- <template v-if="index === 0 &&  knowledgeSel.length">已关注知识：</template>
          <template v-if="index === 1 &&  infoSel.length">已关注资讯：</template> -->
          <template v-if="policySel.length">已关注政策：</template>
        </Col>
        <Col span="22">
          <Tag closable v-for="(item, index) in followDataSel" :key="index" @on-close="handleDel(item)">{{item.name}}</Tag>
        </Col>
      </Row>
    </followModal>
  </div>
</template>
<script>
import vuiTabs from './tabs'
import followModal from './follow-modal'
import {Reset, Filter, Mixins} from './mixins.js'
export default {
  components: {
    vuiTabs,
    followModal
  },
  mixins: [Mixins],
  data: () => ({
    tabs: [{
      name: '知识',
      checked: true
    }, {
      name: '资讯',
      checked: false
    }, {
      name: '政策',
      checked: false
    }],
    followData: [],
    followDataSel: [],
    index: 0,
    knowledge: [],
    knowledgeSel: [],
    info: [],
    infoSel: [],
    policy: [],
    policySel: []
  }),
  created () {
    // 取数据
    this.$api.post('/member-reversion/indivi/findIndividInfo', {
      templateId: this.$template.id,
      follow_type: 'knowledge'
    }).then(res => {
      let d = res.data.FollowListData
      this.followData = d
      this.knowledge = d
    })
    this.$api.post('/member-reversion/indivi/findIndividInfo', {
      templateId: this.$template.id,
      follow_type: 'information'
    }).then(res => {
      this.info = res.data.FollowListData
    })
    this.$api.post('/member-reversion/indivi/findIndividInfo', {
      templateId: this.$template.id,
      follow_type: 'policy'
    }).then(res => {
      this.policy = res.data.FollowListData
    })
    console.log(this.knowledge)
  },
  methods: {
    // 选中标签
    handleTabClick (index) {
      console.log(index)
      this.index = index
      if (index === 0) {
        this.followData = this.knowledge
        this.followDataSel = this.knowledgeSel
      } else if (index === 1) {
        this.followData = this.info
        this.followDataSel = this.infoSel
      } else {
        this.followData = this.policy
        this.followDataSel = this.policySel
      }
    },
    // 删除
    handleDel (data) {
      Filter(this.followData, data.name)
      this.filterSel(this.followDataSel, data.name)
      this.$emit('on-get-data', this.knowledgeSel, this.infoSel, this.policySel)
    },
    // 取关注数据
    handleGetFollowData (data) {
      if (this.index === 0) {
        this.knowledgeSel = data
      } else if (this.index === 1) {
        this.infoSel = data
      } else {
        this.policySel = data
      }
      this.$emit('on-get-data', this.knowledgeSel, this.infoSel, this.policySel)
    },
    // 重置组件
    handleReset () {
      this.followDataSel = []
      this.knowledgeSel = []
      this.infoSel = []
      this.policySel = []
      Reset(this.knowledge)
      Reset(this.info)
      Reset(this.policy)
      this.tabs.forEach((item, index) => {
        item.checked = false
        if (index === 0) {
          item.checked = true
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
